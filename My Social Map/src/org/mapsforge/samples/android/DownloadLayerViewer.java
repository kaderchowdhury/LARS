/*
 * Copyright 2010, 2011, 2012, 2013 mapsforge.org
 * Copyright 2013-2014 Ludwig M Brinckmann
 * Copyright 2015-2016 devemux86
 * Copyright 2015 Andreas Schildbach
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.mapsforge.samples.android;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.MapPosition;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.layer.download.TileDownloadLayer;
import org.mapsforge.map.layer.download.tilesource.OpenStreetMapMapnik;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.rendertheme.XmlRenderTheme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Shows how to use a tile download layer.
 * The important thing here is that the downloadLayer needs
 * to be paused and resumed to fit into the Android life cycle.
 */
public class DownloadLayerViewer extends SamplesBaseActivity {
	protected TileDownloadLayer downloadLayer;

	/**
	 * We do not need storage permission as we do not have a map file here.
	 */
	@Override
	protected void checkPermissionsAndCreateLayersAndControls() {
		createLayers();
		createControls();
	}

	@Override
	protected void createLayers() {
		this.downloadLayer = new TileDownloadLayer(this.tileCaches.get(0),
				this.mapView.getModel().mapViewPosition, OpenStreetMapMapnik.INSTANCE,
				AndroidGraphicFactory.INSTANCE);
		mapView.getLayerManager().getLayers().add(this.downloadLayer);

		mapView.setZoomLevelMin(OpenStreetMapMapnik.INSTANCE.getZoomLevelMin());
		mapView.setZoomLevelMax(OpenStreetMapMapnik.INSTANCE.getZoomLevelMax());


		plotrecommendations();
	}

	private void createPositionMarker(double paramDouble1, double paramDouble2,double rank){
		final LatLong localLatLong = new LatLong(paramDouble1, paramDouble2);
		TappableMarker positionmarker;
		if(rank==0.0)
			positionmarker = new TappableMarker(R.drawable.my_marker_red, localLatLong);
		if(rank == 1.0)
			positionmarker = new TappableMarker(R.drawable.my_marker_orange, localLatLong);
		else
			positionmarker = new TappableMarker(R.drawable.my_marker_green, localLatLong);
		//		positionmarker = new TappableMarker(getMarkerBitmapFromView(R.drawable.avatar), localLatLong);

		mapView.getLayerManager().getLayers().add(positionmarker);
	}


	private class TappableMarker extends Marker{
		//		public TappableMarker(int icon2, LatLong localLatLong) {
		//			org.mapsforge.core.graphics.Bitmap icon2 = (org.mapsforge.core.graphics.Bitmap)icon;
		//			super(localLatLong,icon2,
		//					1*(icon.getWidth())/2,
		//					-1*(icon.getHeight())/2);
		//		}
		//		public TappableMarker(Bitmap icon, LatLong localLatLong) { 
		//			super(localLatLong,AndroidGraphicFactory.convertToBitmap(new BitmapDrawable(getResources(), icon)),
		//					1*(AndroidGraphicFactory.convertToBitmap(new BitmapDrawable(getResources(), icon)).getWidth())/2,
		//					-1*(AndroidGraphicFactory.convertToBitmap(new BitmapDrawable(getResources(), icon)).getHeight())/2);
		//		}
		public TappableMarker(int icon, LatLong localLatLong) { 
			super(localLatLong,AndroidGraphicFactory.convertToBitmap(getResources().getDrawable(icon)),
					1*(AndroidGraphicFactory.convertToBitmap(getResources().getDrawable(icon)).getWidth())/2,
					-1*(AndroidGraphicFactory.convertToBitmap(getResources().getDrawable(icon)).getHeight())/2);
		}
		@Override
		public boolean onTap(LatLong tapLatLong, Point layerXY, Point tapXY) {


			double centerX = layerXY.x + getHorizontalOffset();
			double centerY = layerXY.y + getVerticalOffset();

			double radiusX = (getBitmap().getWidth() / 2) *1.1;
			double radiusY = (getBitmap().getHeight() / 2) *1.1;


			double distX = Math.abs(centerX - tapXY.x);
			double distY = Math.abs(centerY - tapXY.y);


			if( distX < radiusX && distY < radiusY){

				//loadPhoto(R.drawable.info);
				//Toast.makeText(DownloadLayerViewer.this, "yay!!!", Toast.LENGTH_LONG).show();
				//	            if(action != null){
				//	                action.run();
				//	                return true;
				//	            }
			}


			return false;
		}
	}
	private void loadPhoto(int photo) {

		//        ImageView tempImageView = imageView;


		AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

		View layout = inflater.inflate(R.layout.custom_fullimage_dialog,
				(ViewGroup) findViewById(R.id.layout_root));
		ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
		image.setImageDrawable(getResources().getDrawable(photo));
		imageDialog.setView(layout);
		imageDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}

		});


		imageDialog.create();
		imageDialog.show();     
	}
	private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

		View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
		ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
		markerImageView.setImageResource(resId);
		customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
		customMarkerView.buildDrawingCache();
		Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);
		canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
		Drawable drawable = customMarkerView.getBackground();
		if (drawable != null)
			drawable.draw(canvas);
		customMarkerView.draw(canvas);
		return returnedBitmap;
	}

	@Override
	protected void createMapViews() {
		super.createMapViews();
		// we need to set a fixed size tile as the raster tiles come at a fixed size and not being blurry
		this.mapView.getModel().displayModel.setFixedTileSize(256);
	}

	@Override
	protected MapPosition getInitialPosition() {
		return getDefaultInitialPosition();
	}

	@Override
	protected XmlRenderTheme getRenderTheme() {
		// no render theme needed here
		return null;
	}

	@Override
	protected byte getZoomLevelMax() {
		return mapView.getModel().mapViewPosition.getZoomLevelMax();
	}

	@Override
	protected byte getZoomLevelMin() {
		return mapView.getModel().mapViewPosition.getZoomLevelMin();
	}

	@Override
	public void onPause() {
		this.downloadLayer.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		this.downloadLayer.onResume();
	}
	public void plotrecommendations(){
		//createPositionMarker(52.407999, -1.505722);
		try {
			JSONArray m_jArry = new JSONArray(loadJSONFromAsset());
			ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> m_li;
			Double lat = 51.6704834;
			Double lon = -0.0407202;

			for (int i = 0; i < m_jArry.length(); i++) {
				JSONObject jo_inside = m_jArry.getJSONObject(i);
				//	            Log.d("Details-->", jo_inside.getString("formule"));
				lat = jo_inside.getDouble("lat");
				lon = jo_inside.getDouble("lon");
				//Double rank = jo_inside.getDouble("rank");
				//Add your values in your `ArrayList` as below:
				//	            m_li = new HashMap<String, String>();
				//	            m_li.put("formule", formula_value);
				//	            m_li.put("url", url_value);
				
				if(i%10 ==0){
					createPositionMarker(lat, lon,2.0d);
//					count++;
				}
				else
					createPositionMarker(lat, lon,1.0d);
				//	            formList.add(m_li);
			}
			changePosition(new LatLong(lat, lon));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	int count = 0;
	public String loadJSONFromAsset() {
		String json = null;
		try {
			InputStream is = getAssets().open("data.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}

}
