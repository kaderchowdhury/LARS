

public class Node implements Comparable<Node>{
	String id;
	String place_id;
	String name;
	String lat;
	String lon;
	String type;
	String sub_type;
	String timestamp;
	Double dist;
	String rank;
	String class_id;
	
	public Node(){

	}
	@Override
	public int compareTo(Node o) {
		return dist.compareTo(o.dist);
	}
}