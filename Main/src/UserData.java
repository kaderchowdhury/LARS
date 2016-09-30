import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;


public class UserData {

	public static void main(String[] args) throws IOException {
		//total number of nodes 98048
		//creating 10000 users and allocation each users 100 random nodes
		//so there will be 1000000 nodes used in total and there will be overlap
		ArrayList<Node>nodes = new ArrayList<Node>();
		ArrayList<String>users = new ArrayList<String>();
		//			System.out.println(Math.random());
		//		try {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/user_data_v2.xml"))));
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/categorized_data_v2.xml"))));

		/*
		 * 
		 * I'm doing it for 10 user's only, assigning each one of them 10 nodes
		 * The data format is:
		 * <user_id>;<place_id>;<lat>;<lng>
		 * 
		 * */


		//			for(int i=0;i<1000000;i++){
		//				String number = String.valueOf(Math.random()).substring(2);
		//				if(number.length()>15)
		//					number=number.substring(0, 15);
		//				if(number.startsWith("0")){
		//					number = number.replaceFirst("0", "1");
		//				}
		//				writer.append(number);
		//				writer.append("\n");
		//			}

		//BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data/nodes.txt"))));
		String line = "";
		float my_lat = 52.407999f;
		float my_lon = -1.505722f;

		while((line = reader.readLine())!=null){
			Node node = new Node();
			//				String id = line.substring(0, line.indexOf(";"));
			node.place_id = line.substring(line.indexOf("id=")+4, line.indexOf("lat=")-2);
			node.lat = line.substring(line.indexOf("lat=")+5, line.indexOf("lon=")-2);
			node.lon = line.substring(line.indexOf("lon=")+5, line.indexOf(" n=\"")-2);
			node.name = line.substring(line.indexOf(" n=\"")+4, line.indexOf(" t=\"")-1);
			node.type = line.substring(line.indexOf(" t=\"")+4, line.indexOf(" st=\"")-1);
			node.sub_type = line.substring(line.indexOf(" st=\"")+5, line.indexOf("class=\"")-1);
			node.class_id = line.substring(line.indexOf(" class=\"")+8, line.indexOf("/>")-1);
			node.timestamp = Util.getRandomTimeStamp();
			//System.out.println(id+" "+lat+" "+lng+" "+name+" "+type+" "+sub_type);

			if(Util.getDistanceFromLatLonInKm(my_lat, my_lon, Float.valueOf(node.lat), Float.valueOf(node.lon))<=1){
				nodes.add(node);
			}

			//				System.out.println(id);
			//			break;
		}

		reader.close();
		//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data/users.txt"))));
		//			line = "";
		//
		//			while((line = reader.readLine())!=null){
		//				users.add(line);
		//				//				System.out.println(id);
		//				//				break;
		//			}
		//
		//			reader.close();
		//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("data/checkins.txt"))));
		//
		//10 users
		writer.append("<user_data>");
		writer.append("\n");
		int userid = 1;
		for(int i=0;i<6;i++){			
			//user_id;place_id;name;type;date_time
//			userid = userid+1;
			Collections.shuffle(nodes);
			//	do it for 1 user only
//			for(int x=0;x<100;x++)
				for(int k=0;k<7;k++){
									Collections.shuffle(nodes);
					for(int p = 0;p<3;p++){
						String day = Util.days[k];
						String time = Util.times[p];
						String activity = Util.getRandomValue(Util.activities);
						String emotion = Util.getRandomValue(Util.emotions);
						String feeling = Util.getRandomValue(Util.feelings);
						writer.append("\t<user>");
						writer.append("\n");
						//					User user = new User();
						Node node = nodes.get(p);

						//					user.node = node;
						//					writer.append("\t\t<user_id>"+(k+1)+"</user_id>");
						writer.append("\t\t<user_id>"+userid+"</user_id>");
						writer.append("\n");
						writer.append("\t\t<place_id>"+node.place_id+"</place_id>");
						writer.append("\n");
						writer.append("\t\t<name>"+node.name+"</name>");
						writer.append("\n");
						writer.append("\t\t<lat>"+node.lat+"</lat>");
						writer.append("\n");
						writer.append("\t\t<lon>"+node.lon+"</lon>");
						writer.append("\n");
						writer.append("\t\t<class>"+node.class_id+"</class>");
						writer.append("\n");

						writer.append("\t\t<time>"+time+"</time>");
						writer.append("\n");
						writer.append("\t\t<day>"+day+"</day>");
						writer.append("\n");
						writer.append("\t\t<activity>"+activity+"</activity>");
						writer.append("\n");
						writer.append("\t\t<emotion>"+emotion+"</emotion>");
						writer.append("\n");
						writer.append("\t\t<feeling>"+feeling+"</feeling>");
						writer.append("\n");

						if((int)(Math.random()*1000)%2==0)
							writer.append("\t\t<rating>"+1+"</rating>");
						else
							writer.append("\t\t<rating>"+0+"</rating>");
						writer.append("\n");

						if((int)(Math.random()*1000)%2==0)
							writer.append("\t\t<with_friends>"+0+"</with_friends>");
						else
							writer.append("\t\t<with_friends>"+1+"</with_friends>");
						writer.append("\n");

						writer.append("\t\t<timestamp>"+node.timestamp+"</timestamp>");
						writer.append("\n");
						writer.append("\t</user>");
						writer.append("\n");	
						System.out.println(node.toString());
					}

					//				String ln = users.get(i)+";"+nodes.get(k)+";"+getRandomTimeStamp();
					//				writer.append(ln);
					//				writer.append("\n");
				}

			//
		}
		writer.append("</user_data>");
		writer.flush();
		writer.close();
		//		} catch (FileNotFoundException e) {
		//			e.printStackTrace();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
	}



}