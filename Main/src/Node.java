
public class Node{
		String place_id;
		String name;
		String lat;
		String lon;
		String type;
		String sub_type;
		String timestamp;
		String class_id;
		public Node(){
			
		}
		public String toString(){
			return place_id+","+name+","+lat+","+lon+","+type+","+sub_type+","+timestamp+","+class_id;
		}
	}