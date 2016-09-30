import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PlaceRank {

	static ArrayList<User>user_list = new ArrayList<User>();
	static ArrayList<Node>node_list = new ArrayList<Node>();
	public static void main(String[] args) throws Exception{	execute();}
	public static void execute()throws Exception {


		//BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data/user_data.xml"))));
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/user_data_v2.xml"))));

		User user = null;
		String line = "";
		//parse user_data.xml get the users
		while((line=reader.readLine())!=null){
			//			if(line!=null)
			//				break;
			line = line.trim();
			if(line.startsWith("<user>")){
				user = new User();
				user.node = new Node();
			}

			if(line.startsWith("<user_id>")){
				user.user_id = line.substring(line.indexOf("<user_id>"), line.indexOf("</user_id>"));
				user.user_id = user.user_id.replace("<user_id>", "");
			}

			if(line.startsWith("<place_id>")){
				user.node.place_id = line.substring(line.indexOf("<place_id>"), line.indexOf("</place_id>"));
				user.node.place_id = user.node.place_id.replace("<place_id>", "");
			}

			if(line.startsWith("<lat>")){
				user.node.lat = line.substring(line.indexOf("<lat>"), line.indexOf("</lat>"));
				user.node.lat = user.node.lat.replace("<lat>", "");
			}

			if(line.startsWith("<lon>")){
				user.node.lon = line.substring(line.indexOf("<lon>"), line.indexOf("</lon>"));
				user.node.lon = user.node.lon.replace("<lon>", "");
			}

			if(line.startsWith("<name>")){
				user.node.name = line.substring(line.indexOf("<name>"), line.indexOf("</name>"));
				user.node.name = user.node.name.replace("<name>", "");
			}

			if(line.startsWith("<type>")){
				user.node.type = line.substring(line.indexOf("<type>"), line.indexOf("</type>"));
				user.node.type = user.node.type.replace("<type>", "");
			}

			if(line.startsWith("<sub_type>")){
				user.node.sub_type = line.substring(line.indexOf("<sub_type>"), line.indexOf("</sub_type>"));
				user.node.sub_type = user.node.sub_type.replace("<sub_type>", "");
			}

			if(line.startsWith("<timestamp>")){
				user.node.timestamp = line.substring(line.indexOf("<timestamp>"), line.indexOf("</timestamp>"));
				user.node.timestamp = user.node.timestamp.replace("<timestamp>", "");
			}

			if(line.startsWith("</user>")){
				//				System.out.println(user.user_id);
				//				System.out.println(user.node.place_id);
				//				System.out.println(user.node.name);
				//				System.out.println(user.node.lat);
				//				System.out.println(user.node.lon);
				//				System.out.println(user.node.type);
				//				System.out.println(user.node.sub_type);
				//				System.out.println(user.node.timestamp);
				//				break;
				user_list.add(user);
			}
		}
		reader.close();


		//parse categorized_data.xml get the list of nodes
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/categorized_data_v2.xml"))));
		line = "";
		while((line=reader.readLine())!=null){
			Node node = new Node();
			node.type = line.substring(line.indexOf(" t=\"")+4, line.indexOf(" st=\"")-1);
			node.sub_type = line.substring(line.indexOf(" st=\"")+5, line.indexOf("/>")-1);
			node.place_id = line.substring(line.indexOf("id=")+4, line.indexOf("lat=")-2);
			node.lat = line.substring(line.indexOf("lat=")+5, line.indexOf("lon=")-2);
			node.lon = line.substring(line.indexOf("lon=")+5, line.indexOf(" n=\"")-2);
			node.name = line.substring(line.indexOf(" n=\"")+4, line.indexOf(" t=\"")-1);
			node_list.add(node);
			//			System.out.println(id);
			//			System.out.println(name);
			//			System.out.println(lat);
			//			System.out.println(lon);
			//			System.out.println(type);
			//			System.out.println(sub_type);
			//			break;
		}
		/*
		 * Now that I have list of users and places time to apply the logic
		 * the args are user_id,lat,lon
		 * args are not important at this point
		 * 1. from users list make another list that contains only the users with user_id= given user id
		 * 2. make a list of nodes for the user, iterate through all the nodes and if the distance is less then 1km
		 * from any of the checkin nodes of current lat lon then add that node to his list of nodes
		 *  
		 * */

		//		let the arguments be id=1
		//		given lat lon cv1 5fb
		//		float my_lat=52.407999f;
		//		float my_lon=-1.505721699999981f;
		//		ArrayList<User>_user=new ArrayList<User>();
		//		ArrayList<Node>user_node=new ArrayList<Node>();
		ArrayList<DataModel>data_model = new ArrayList<DataModel>();
		ArrayList<DataModel>checkins = new ArrayList<DataModel>();
		//prepare data model
		//data_model array contains all the places within 1km from users checkin locations
		//the rank is put the number of checkins
		for(int i=0;i<node_list.size();i++){

			for(int k=0;k<user_list.size();k++){
				float node_lat = Float.valueOf(node_list.get(i).lat);
				float node_lon = Float.valueOf(node_list.get(i).lon);

				float user_lat = Float.valueOf(user_list.get(k).node.lat);
				float user_lon = Float.valueOf(user_list.get(k).node.lon);

				if(Util.getDistanceFromLatLonInKm(node_lat, node_lon, user_lat, user_lon)<=1){
					int _index = -1;
					for(int p=0;p<data_model.size();p++){
						if(data_model.get(p).user_id.equals(user_list.get(k).user_id)
								&&
								data_model.get(p).place_id.equals(node_list.get(i).place_id))
							_index = p;
					}
					DataModel model;
					if(_index == -1){
						model = new DataModel();
						data_model.add(model);
					}else
						model = data_model.get(_index);

					model.place_id = node_list.get(i).place_id;
					model.user_id = user_list.get(k).user_id;
					model.type = node_list.get(i).type;
					model.sub_type = node_list.get(i).sub_type;

					if(node_list.get(i).place_id.equals(user_list.get(k).node.place_id)){
						//rank +1
						if(model.rank<4)
							model.rank = model.rank+1;
						checkins.add(model);
					}

				}
			}
		}//end

		//add 1 to existing ratings
		for(int i=0;i<data_model.size();i++){
			if(data_model.get(i).rank!=0.0f)
				data_model.get(i).rank = data_model.get(i).rank+1;
		}

		//find similar items and put rating 1.0 to similar items
		for(int i=0;i<checkins.size();i++){

			for(int k=0;k<data_model.size();k++){
				if(checkins.get(i).user_id.equals(data_model.get(k).user_id)
						&&
						checkins.get(i).type.equals(data_model.get(k).type)
						&&
						checkins.get(i).sub_type.equals(data_model.get(k).sub_type)){
					if(data_model.get(k).rank == 0.0f)
						data_model.get(k).rank = 1.0f;
				}
			}
		}

		Collections.sort((List)data_model, new Comparator<DataModel>() {

			@Override
			public int compare(DataModel o1, DataModel o2) {

				return o1.user_id.compareTo(o2.user_id);
			}
		});

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/data_model_recommendation-v2.csv"))));
		for(int t = 0;t<data_model.size();t++){
			//if(data_model.get(t).rank==0.0f)continue;
			writer.append(data_model.get(t).user_id+","+data_model.get(t).place_id+","+data_model.get(t).rank);
			writer.append("\n");
			//		System.out.println(data_model.get(t).user_id+","+data_model.get(t).place_id+","+data_model.get(t).rank+"||"+data_model.get(t).type+";"+data_model.get(t).sub_type);
		}

		writer.flush();
		writer.close();

	}
}