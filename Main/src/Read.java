import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Read {

	public static void main(String [] args){
		HashMap<String, Integer>tags=new HashMap<String, Integer>();
		ArrayList<String>cc=new ArrayList<String>();
		ArrayList<String>nodes=new ArrayList<String>();
		double last_dist = 10000;
		String last_id = "0";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/Downloads/west-midlands-latest.osm"))));
			float start_time = System.currentTimeMillis();
			String line = "";
			double count = 0;
			double count_tags=0;
			String line_tag="";
			boolean store = false;
			String line_node = null;
			String id="";
			String lat="";
			String lon="";
			String last_lat="";
			String last_lon="";
			while((line = reader.readLine())!=null){
				count++;

				//		System.out.print(".");
				//		if(count%1000==0)
				//			System.out.println();
				if(line.trim().startsWith("<tag")){
					//					String key = line.substring(line.indexOf("k=")+2, line.indexOf("v="));
					//					String val = line.substring(line.indexOf("v=")+2, line.indexOf("/>"));
					//					//			System.out.println("key="+key+" "+"val="+val);
					//					//			break;
					//					Integer old = tags.get(key);
					//					if(old==null)
					//						tags.put(key, new Integer(1));
					//					else
					//						tags.put(key, old+1);
					//					if(key.contains("cuisine"))
					//						cc.add(val);
					//
					//line_tag = line;
					if(line.contains("created_by"))continue;
					if(((long)(Math.random()*10000))%2==0){
						if(last_lat.equals(lat)&&last_lon.equals(lon)){

						}else{
							nodes.add("{"+lat+","+lon+"}"+",");
							last_lat = lat;
							last_lon = lon;
						}

					}
				}

				//			count_tags++;
				//given lat lon cv1 5fb
				float my_lat=52.407999f;
				float my_lon=-1.505721699999981f;
				//				System.out.println(getDistanceFromLatLonInKm(my_lat, my_lon, 52.4074605f, -1.4971396f));
				//				if(my_lat==52.407483f)
				//				break;

				//the difference between latlon from the map and latlon from the phone was 0.013297263284368343
				//which is 1.3 meters approx
				//next job is to find the place id given a lat lon, note that the lat lon will not be the same, so we have to check 
				//the distance from all the points and the least one should be the place


				if(line.trim().startsWith("<node")){
					if(line.trim().endsWith("/>"))continue;
					id = line.substring(line.indexOf("id=")+4, line.indexOf("lat=")-2);
					lat = line.substring(line.indexOf("lat=")+5, line.indexOf("lon=")-2);
					lon = line.substring(line.indexOf("lon=")+5, line.indexOf("version=")-2);
					System.out.println("id="+id+"\n"+"lat="+lat+"\n"+"lon="+lon);
					break;
					//					double dis = getDistanceFromLatLonInKm(my_lat, my_lon, Float.parseFloat(lat.trim()), Float.parseFloat(lon.trim()));
					//					if(dis<last_dist){
					//						last_dist=dis;
					//						last_id = id;
					//					}
					//					if(getDistanceFromLatLonInKm(my_lat, my_lon, Float.parseFloat(lat.trim()), Float.parseFloat(lon.trim()))<=.25){
					//						nodes.add(id+";"+lat+";"+lon);	
					//					}
				}
			}//end while
			//			printMap(tags);
			//			printList(nodes);
			//			saveList(nodes);

			ArrayList<String>rand_nodes = new ArrayList<String>();
			int size = nodes.size();

			for(int i=0;i<10;i++){

				int index = (int)(Math.random()*10000000)%size;
				//	System.out.println(index);
				rand_nodes.add(nodes.get(index));
			}
			printList(rand_nodes);
			//			System.out.println(last_id);

			/*
			 * The task is to list all the places that are in 1Km radius from a given point 
			 * */

			System.out.println();
			float end_time = System.currentTimeMillis();
			//		System.out.println("Number of lines:="+count+"\nExecution time="+(end_time-start_time));
			//		System.out.println("Total Tag="+count_tags);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void printMap(Map mp) {
		Iterator it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	public static void printList(ArrayList<String>list){
		for(int i=0;i<list.size();i++)
			System.out.println(list.get(i));
	}

	public static void saveList(ArrayList<String>list){
		for(int i=0;i<list.size();i++){
			try {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(""))));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//			System.out.println(list.get(i));
	}

	public static double getDistanceFromLatLonInKm(float lat1,float lon1,float lat2,float lon2) {
		int R = 6371; // Radius of the earth in km
		double dLat = deg2rad(lat2-lat1);  // deg2rad below
		double dLon = deg2rad(lon2-lon1); 
		double a = 
				Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
				Math.sin(dLon/2) * Math.sin(dLon/2)
				; 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double d = R * c; // Distance in km
		return d;
	}

	public static double deg2rad(float deg) {
		return deg * (Math.PI/180d);
	}
}
