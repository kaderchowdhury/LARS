package recommendation_api;

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
	
	public Node(){

	}
	@Override
	public int compareTo(Node o) {
		return dist.compareTo(o.dist);
	}
}