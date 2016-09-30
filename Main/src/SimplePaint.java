import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


/**
 * A simple applet where the user can sketch curves in a variety of
 * colors.  A color palette is shown along the right edge of the applet.
 * The user can select a drawing color by clicking on a color in the
 * palette.  Under the colors is a "Clear button" that the user
 * can press to clear the sketch.  The user draws by clicking and
 * dragging in a large white area that occupies most of the applet.
 * The user's drawing is not persistent.  It is lost whenever
 * the applet is repainted for any reason.
 * <p>The drawing that is done in this example violates the rule
 * that all drawing should be done in the paintComponent() method.
 * Although it works, it is NOT good style.
 * <p>This class also contains a main program, and it can be run as
 * a stand-alone application that has exactly the same functionality
 * as the applet.
 */
public class SimplePaint extends JApplet {
//	public static int data[] = {320,693,319,681,319,681,319,681,321,707,321,743,327,783,327,838,332,895,335,957,337,1017,339,1073,339,1118,339,1154,337,1179,337,1190,337,1190,339,1181,340,1160,341,1128,343,1089,343,1036,342,977,343,914,345,847,348,783,354,719,364,662,379,614,399,580,424,555,452,545,482,546,512,556,537,574,557,600,569,631,573,667,569,705,557,742,536,778,513,810,486,839,462,862,438,881,419,895,403,903,394,907,394,907,394,907,394,907,405,915,423,919,446,926,475,936,510,950,548,967,590,987,631,1009,668,1035,700,1060,724,1090,738,1117,740,1144,731,1168,711,1189,684,1207,649,1222,611,1231,571,1238,529,1238,494,1231,458,1225,431,1214,411,1201,397,1189,390,1175
//	};
//
//	public static int testData1[] = {352,635,352,635,362,625,362,625,368,638,377,644,377,668,382,693,379,734,376,777,367,830,355,891,338,953,321,1017,305,1076,292,1128,283,1171,282,1203,285,1221,294,1225,310,1214,329,1193,352,1162,378,1123,407,1080,433,1032,458,984,482,938,505,896,528,859,548,825,567,795,586,770,605,749,625,733,648,720,668,709,688,701,706,695,721,695,743,689,753,694,765,695,765,695,777,694,777,694,509,849,502,870,501,893,506,917,526,940,537,968,556,991,573,1018,593,1048,613,1078,626,1109,640,1131,654,1158,664,1180,679,1200
//		};
//	public static int testData2[] = {398,773,404,752,405,739,408,728,408,728,408,728,408,728,388,759,374,787,349,832,325,883,298,940,274,998,253,1055,239,1106,235,1150,242,1181,259,1193,287,1189,320,1171,359,1141,401,1098,442,1044,481,984,514,922,542,864,566,812,581,767,592,733,593,714,593,714,581,725,571,755,558,796,547,845,537,895,532,943,537,986,551,1014,575,1023,606,1016,648,995,694,958,744,913,794,869,842,827,881,791,912,769,934,757,948,758,953,773,946,800,933,839,915,885,895,935,874,989,855,1040,839,1089,825,1136,825,1168,827,1192,832,1209,841,1215,851,1216
//	};
//	public static int testData3[] = {455,706,454,695,455,685,455,685,452,676,452,676,426,691,404,710,366,751,326,795,282,850,244,907,213,967,196,1021,197,1069,217,1106,259,1127,318,1133,386,1126,462,1106,538,1077,609,1038,672,995,726,946,765,895,790,845,803,798,804,755,790,718,772,689,747,667,713,654,679,647,641,653,609,658,577,671,552,691,531,710,517,896,517,896,503,898,502,911,510,925,518,954,536,983,559,1019,582,1061,613,1099,649,1133,684,1163,721,1190
//	};
//	public static int testData4[] = {215,724,217,706,218,694,222,679,234,658,240,643,258,622,258,622,281,607,289,616,298,631,299,664,295,711,288,770,279,838,267,909,256,982,245,1048,236,1102,230,1146,229,1173,231,1183,231,1183,247,1149,258,1108,271,1055,289,993,306,926,329,861,352,799,373,744,398,697,422,661,448,637,475,625,507,619,539,617,572,621,608,628,643,638,675,650,701,664,717,680,721,700,709,724,686,752,651,783,606,820,557,858,508,896,468,934,436,971,420,1002,418,1029,432,1052,465,1067,506,1083,561,1092,621,1096,680,1099
//	};
	
	
	public static int data[] = {490,717,494,708,499,699,499,699,486,706,482,721,464,759,450,804,427,865,406,936,385,1010,371,1084,358,1150,353,1205,354,1242,362,1258,375,1255,391,1234,407,1199,420,1150,430,1092,434,1025,433,959,429,895,421,838,411,788,402,747,395,715,392,696,395,687,404,686,422,689,450,694,486,697,527,700,577,700,633,697,687,692,741,686,792,681,837,677,871,679,894,687,906,702,905,724,894,749,870,780,835,813,793,846,747,878,705,907,666,931,635,949,615,964,615,964,625,979,651,984,688,985,734,990,783,996,831,1007,871,1022,897,1041,908,1069,904,1102,885,1138,848,1178,803,1217,749,1252,694,1283,644,1307,598,1322,567,1328,543,1336,533,1331,533,1331,538,1318
	};

	public static int testData1[] = {343,649,348,632,350,623,350,623,358,613,353,629,354,647,346,686,342,728,334,783,326,841,318,906,310,967,304,1025,301,1073,297,1109,295,1132,294,1143,294,1143,299,1126,302,1099,307,1059,311,1011,318,956,323,897,331,837,341,780,350,728,360,680,372,641,385,611,398,590,415,575,433,568,456,567,482,570,514,576,550,583,589,590,632,597,672,606,711,615,744,625,771,636,788,648,794,662,789,679,774,696,749,715,715,734,679,753,642,770,606,788,576,803,557,817,551,829,560,840,583,850,614,856,653,865,693,877,733,890,767,907,792,928,804,953,801,980,785,1012,756,1044,719,1076,676,1105,631,1130,588,1150,548,1162,517,1168,490,1171,482,1162,482,1162,490,1150,504,1141
	};

	public static int testData2[] = {375,810,375,810,383,801,383,801,383,801,383,801,383,801,383,801,388,819,384,848,382,878,374,922,370,961,360,1011,355,1062,346,1114,341,1156,337,1192,333,1222,330,1245,330,1262,334,1271,334,1271,346,1277,356,1274,365,1271,379,1265,397,1261,418,1255,440,1249,466,1246,494,1242,524,1239,554,1238,584,1239,613,1239,638,1240,659,1242,672,1245,681,1247,678,1256,678,1256,660,1263,641,1269,620,1271,374,768,374,768,392,754,402,756,415,764,440,763,463,771,494,773,525,778,554,781,582,784,604,789,627,786,640,791,649,791,649,791,649,791,635,801,351,1044,362,1047,374,1051,388,1053,410,1047,425,1053,451,1050,473,1051,493,1053,514,1056,536,1053,551,1050,572,1041
	};
	public static int testData3[] = {583,763,572,762,561,769,549,780,541,793,525,820,522,843,514,877,513,907,509,942,515,974,521,1010,533,1044,545,1082,559,1117,575,1150,595,1181,616,1209,638,1230,661,1248,685,1261,710,1267,738,1268,767,1265,793,1258,821,1248,847,1241,875,1227,893,1219,912,1210,930,1198,939,1184,948,1171,536,1047,506,1039,486,1032,483,1020,498,1009,521,999,556,984,599,972,642,960,692,947,740,933,777,921,812,905,834,892,461,753,429,748,401,747,383,739,383,739,369,728,380,716,394,712,422,702,455,698,502,692,557,690,613,690,667,687,722,685,770,684,815,680
	};
	public static int testData4[] = {442,868,433,852,426,839,426,839,413,842,415,852,410,877,411,899,407,934,410,966,412,1006,416,1042,421,1080,426,1114,431,1147,441,1174,453,1200,467,1219,486,1236,512,1245,543,1250,578,1248,617,1243,658,1232,701,1219,744,1205,781,1188,812,1175,845,1158,869,1146,885,1135,895,1126,895,1126,500,1059,479,1051,457,1050,457,1050,464,1041,482,1036,508,1030,542,1026,578,1023,613,1020,656,1009,690,1003,727,985,383,722,349,714,321,711,304,705,299,697,299,697,329,691,361,691,407,687,457,689,516,690,578,691,636,699,690,705,737,708,773,711
	};
	/**
	 * The main routine opens a window that displays a drawing area
	 * and color palette.  This main routine allows this class to
	 * be run as a stand-alone application as well as as an applet.
	 * The main routine has nothing to do with the function of this
	 * class as an applet.
	 */
	public static void main(String[] args) {
		JFrame window = new JFrame("Simple Paint");
		final SimplePaintPanel content = new SimplePaintPanel();
		window.setContentPane(content);
		window.setSize(1200,878);
		window.setLocation(100,100);
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setVisible(true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int i=0;
				int rx = (int)(1200f/1360f);
				int ry = (int)(878f/2040f);
				rx = 1;
				ry = 1;
				content.setupDrawingFromData();
				while(i<data.length){
					content.drawFromDataSet((int)(data[i]/3f), (int)(data[i+1]/3f),0);
					i+=2;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
//				i = 0;
//				while(i<testData1.length){
//					content.drawFromDataSet((int)(testData1[i]/3f), (int)(testData1[i+1]/3f),1);
//					i+=2;
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//				i = 0;
//				while(i<testData2.length){
//					content.drawFromDataSet((int)(testData2[i]/3f), (int)(testData2[i+1]/3f),2);
//					i+=2;
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//				i = 0;
//				while(i<testData3.length){
//					content.drawFromDataSet((int)(testData3[i]/3f), (int)(testData3[i+1]/3f),3);
//					i+=2;
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//				i = 0;
//				while(i<testData4.length){
//					content.drawFromDataSet((int)(testData4[i]/3f), (int)(testData4[i+1]/3f),4);
//					i+=2;
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
				content.finishedDrawingFromData();

			}
			
		}).start();;
	}
	
	/**
	 * The init method of the applet simply creates a SimplePaintPanel and
	 * uses it as the content pane of the applet.  All the work is done
	 * by the SimplePaintPanel.
	 */
	public void init() {
		setContentPane( new SimplePaintPanel() );
	}


	/**
	 * A simple paint panel contains a large white drawing surface where
	 * the user can draw curves and a color palette that the user can click
	 * to select the color to be used for drawing.  When this class is used
	 * as an applet, the content pane of the applet is a SimplePaintPanel.
	 * When this class is run as a standalone application, the content pane
	 * is a SimplePaintPanel.  All the real work is done in the
	 * SimplePaintPanel class.
	 */
	public static class SimplePaintPanel extends JPanel
	implements MouseListener, MouseMotionListener {

		/**
		 * Some constants to represent the color selected by the user.
		 */
		private final static int BLACK = 0,
				RED = 1,     
				GREEN = 2,   
				BLUE = 3, 
				CYAN = 4,   
				MAGENTA = 5,
				YELLOW = 6;

		private int currentColor = BLACK;  // The currently selected drawing color,
		//   coded as one of the above constants.


		/* The following variables are used when the user is sketching a
       curve while dragging a mouse. */

		private int prevX, prevY;     // The previous location of the mouse.

		private boolean dragging;      // This is set to true while the user is drawing.

		private Graphics graphicsForDrawing;  // A graphics context for the panel
		// that is used to draw the user's curve.


		/**
		 * Constructor for SimplePaintPanel class sets the background color to be
		 * white and sets it to listen for mouse events on itself.
		 */
		SimplePaintPanel() {
			setBackground(Color.WHITE);
			addMouseListener(this);
			addMouseMotionListener(this);
		}


		/**
		 * Draw the contents of the panel.  Since no information is
		 * saved about what the user has drawn, the user's drawing
		 * is erased whenever this routine is called.
		 */
		public void paintComponent(Graphics g) {

			super.paintComponent(g);  // Fill with background color (white).

			int width = getWidth();    // Width of the panel.
			int height = getHeight();  // Height of the panel.

			int colorSpacing = (height - 56) / 7;
			// Distance between the top of one colored rectangle in the palette
			// and the top of the rectangle below it.  The height of the
			// rectangle will be colorSpacing - 3.  There are 7 colored rectangles,
			// so the available space is divided by 7.  The available space allows
			// for the gray border and the 50-by-50 CLEAR button.

			/* Draw a 3-pixel border around the applet in gray.  This has to be
          done by drawing three rectangles of different sizes. */

			g.setColor(Color.GRAY);
			//			g.drawRect(0, 0, width-1, height-1);
			//			g.drawRect(1, 1, width-3, height-3);
			//			g.drawRect(2, 2, width-5, height-5);

			/* Draw a 56-pixel wide gray rectangle along the right edge of the applet.
          The color palette and Clear button will be drawn on top of this.
          (This covers some of the same area as the border I just drew. */

			//g.fillRect(width - 56, 0, 56, height);

			/* Draw the "Clear button" as a 50-by-50 white rectangle in the lower right
          corner of the applet, allowing for a 3-pixel border. */

			//			g.setColor(Color.WHITE);
			//			g.fillRect(width-53,  height-53, 50, 50);
			//			g.setColor(Color.BLACK);
			//			g.drawRect(width-53, height-53, 49, 49);
			//			g.drawString("CLEAR", width-48, height-23); 

			/* Draw the seven color rectangles. */

			//			g.setColor(Color.BLACK);
			//			g.fillRect(width-53, 3 + 0*colorSpacing, 50, colorSpacing-3);
			//			g.setColor(Color.RED);
			//			g.fillRect(width-53, 3 + 1*colorSpacing, 50, colorSpacing-3);
			//			g.setColor(Color.GREEN);
			//			g.fillRect(width-53, 3 + 2*colorSpacing, 50, colorSpacing-3);
			//			g.setColor(Color.BLUE);
			//			g.fillRect(width-53, 3 + 3*colorSpacing, 50, colorSpacing-3);
			//			g.setColor(Color.CYAN);
			//			g.fillRect(width-53, 3 + 4*colorSpacing, 50, colorSpacing-3);
			//			g.setColor(Color.MAGENTA);
			//			g.fillRect(width-53, 3 + 5*colorSpacing, 50, colorSpacing-3);
			//			g.setColor(Color.YELLOW);
			//			g.fillRect(width-53, 3 + 6*colorSpacing, 50, colorSpacing-3);

			/* Draw a 2-pixel white border around the color rectangle
          of the current drawing color. */

			//			g.setColor(Color.WHITE);
			//			g.drawRect(width-55, 1 + currentColor*colorSpacing, 53, colorSpacing);
			//			g.drawRect(width-54, 2 + currentColor*colorSpacing, 51, colorSpacing-2);

		} // end paintComponent()


		/**
		 * Change the drawing color after the user has clicked the
		 * mouse on the color palette at a point with y-coordinate y.
		 * (Note that I can't just call repaint and redraw the whole
		 * panel, since that would erase the user's drawing!)
		 */
		private void changeColor(int y) {

			int width = getWidth();           // Width of applet.
			int height = getHeight();         // Height of applet.
			int colorSpacing = (height - 56) / 7;  // Space for one color rectangle.
			int newColor = y / colorSpacing;       // Which color number was clicked?

			if (newColor < 0 || newColor > 6)      // Make sure the color number is valid.
				return;

			/* Remove the hilite from the current color, by drawing over it in gray.
          Then change the current drawing color and draw a hilite around the
          new drawing color.  */

			Graphics g = getGraphics();
			g.setColor(Color.GRAY);
			g.drawRect(width-55, 1 + currentColor*colorSpacing, 53, colorSpacing);
			g.drawRect(width-54, 2 + currentColor*colorSpacing, 51, colorSpacing-2);
			currentColor = newColor;
			g.setColor(Color.WHITE);
			g.drawRect(width-55, 1 + currentColor*colorSpacing, 53, colorSpacing);
			g.drawRect(width-54, 2 + currentColor*colorSpacing, 51, colorSpacing-2);
			g.dispose();

		} // end changeColor()


		/**
		 * This routine is called in mousePressed when the user clicks on the drawing area.
		 * It sets up the graphics context, graphicsForDrawing, to be used to draw the user's 
		 * sketch in the current color.
		 */
		private void setUpDrawingGraphics() {
			graphicsForDrawing = getGraphics();
			switch (currentColor) {
			case BLACK:
				graphicsForDrawing.setColor(Color.BLACK);
				break;
			case RED:
				graphicsForDrawing.setColor(Color.RED);
				break;
			case GREEN:
				graphicsForDrawing.setColor(Color.GREEN);
				break;
			case BLUE:
				graphicsForDrawing.setColor(Color.BLUE);
				break;
			case CYAN:
				graphicsForDrawing.setColor(Color.CYAN);
				break;
			case MAGENTA:
				graphicsForDrawing.setColor(Color.MAGENTA);
				break;
			case YELLOW:
				graphicsForDrawing.setColor(Color.YELLOW);
				break;
			}
		} // end setUpDrawingGraphics()


		/**
		 * This is called when the user presses the mouse anywhere in the applet.  
		 * There are three possible responses, depending on where the user clicked:  
		 * Change the current color, clear the drawing, or start drawing a curve.  
		 * (Or do nothing if user clicks on the border.)
		 */
		public void mousePressed(MouseEvent evt) {

			int x = evt.getX();   // x-coordinate where the user clicked.
			int y = evt.getY();   // y-coordinate where the user clicked.

			int width = getWidth();    // Width of the panel.
			int height = getHeight();  // Height of the panel.

			if (dragging == true)  // Ignore mouse presses that occur
				return;            //    when user is already drawing a curve.
			//    (This can happen if the user presses
			//    two mouse buttons at the same time.)

			if (x > width - 53) {
				// User clicked to the right of the drawing area.
				// This click is either on the clear button or
				// on the color palette.
				if (y > height - 53)
					repaint();       //  Clicked on "CLEAR button".
				else
					changeColor(y);  // Clicked on the color palette.
			}
			else if (x > 3 && x < width - 56 && y > 3 && y < height - 3) {
				// The user has clicked on the white drawing area.
				// Start drawing a curve from the point (x,y).
				prevX = x;
				prevY = y;
				dragging = true;
				setUpDrawingGraphics();
			}

		} // end mousePressed()


		/**
		 * Called whenever the user releases the mouse button. If the user was drawing 
		 * a curve, the curve is done, so we should set drawing to false and get rid of
		 * the graphics context that we created to use during the drawing.
		 */
		public void mouseReleased(MouseEvent evt) {
			if (dragging == false)
				return;  // Nothing to do because the user isn't drawing.
			dragging = false;
			graphicsForDrawing.dispose();
			graphicsForDrawing = null;
		}
		public void finishedDrawingFromData(){
			graphicsForDrawing.dispose();
			graphicsForDrawing = null;
		}

		public void setupDrawingFromData(){
			prevX = data[0];
			prevY = data[1];
			// dragging = true;
			setUpDrawingGraphics();
		}

		/**
		 * Called whenever the user moves the mouse while a mouse button is held down.  
		 * If the user is drawing, draw a line segment from the previous mouse location 
		 * to the current mouse location, and set up prevX and prevY for the next call.  
		 * Note that in case the user drags outside of the drawing area, the values of
		 * x and y are "clamped" to lie within this area.  This avoids drawing on the color 
		 * palette or clear button.
		 */
		public void mouseDragged(MouseEvent evt) {

			if (dragging == false)
				return;  // Nothing to do because the user isn't drawing.

			int x = evt.getX();   // x-coordinate of mouse.
			int y = evt.getY();   // y-coordinate of mouse.

			if (x < 3)                          // Adjust the value of x,
				x = 3;                           //   to make sure it's in
			if (x > getWidth() - 57)       //   the drawing area.
				x = getWidth() - 57;

			if (y < 3)                          // Adjust the value of y,
				y = 3;                           //   to make sure it's in
			if (y > getHeight() - 4)       //   the drawing area.
				y = getHeight() - 4;
			//			if(Math.abs(prevX-x)>12&&Math.abs(prevY-y)>12){
			//			graphicsForDrawing.drawLine(prevX, prevY, x, y);  // Draw the line.
			graphicsForDrawing.drawString("o", x, y);

			prevX = x;  // Get ready for the next line segment in the curve.
			prevY = y;
			//			}

		} // end mouseDragged()
		public void drawFromDataSet(int x, int y,int p){
			//    	  if (dragging == false)
			//              return;  // Nothing to do because the user isn't drawing.

			//           int x = evt.getX();   // x-coordinate of mouse.
			//           int y = evt.getY();   // y-coordinate of mouse.

			//			if (x < 3)                          // Adjust the value of x,
			//				x = 3;                           //   to make sure it's in
			//			if (x > getWidth() - 57)       //   the drawing area.
			//				x = getWidth() - 57;
			//
			//			if (y < 3)                          // Adjust the value of y,
			//				y = 3;                           //   to make sure it's in
			//			if (y > getHeight() - 4)       //   the drawing area.
			//				y = getHeight() - 4;

			//	graphicsForDrawing.drawLine(prevX, prevY, x, y);  // Draw the line.
			if(p == 0){
				graphicsForDrawing.setColor(Color.BLACK);
				graphicsForDrawing.drawString("o", x, y);
			}else if (p == 1){
				graphicsForDrawing.setColor(Color.RED);
				graphicsForDrawing.drawString("x", x, y);
			}
			else if (p == 2){
				graphicsForDrawing.setColor(Color.BLUE);
				graphicsForDrawing.drawString("o", x, y);
			}
			else if (p == 3){
				graphicsForDrawing.setColor(Color.MAGENTA);
				graphicsForDrawing.drawString("o", x, y);
			}
			else if (p == 4){
				graphicsForDrawing.setColor(Color.ORANGE);
				graphicsForDrawing.drawString("o", x, y);
			}
			prevX = x;  // Get ready for the next line segment in the curve.
			prevY = y;
		}

		public void mouseEntered(MouseEvent evt) { }   // Some empty routines.
		public void mouseExited(MouseEvent evt) { }    //    (Required by the MouseListener
		public void mouseClicked(MouseEvent evt) { }   //    and MouseMotionListener
		public void mouseMoved(MouseEvent evt) { }     //    interfaces).


	}  // End class SimplePaintPanel

} // end class SimplePaint