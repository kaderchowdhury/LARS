import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;



public class ServerThread extends Thread{
	String id = "";
	String time = "";
	String day = "";
	String friends = "";
	String activity = "";
	String feeling = "";
	String emotion = "";
	String lat = "";
	String lon = "";

	static final String HTML_START =
			"<html>" +
					"<title>HTTP Server</title>" +
					"<body>";

	static final String HTML_END =
			"</body>" +
					"</html>";
	BufferedReader inputStream;
	DataOutputStream outputStream;
	Socket client;
	public ServerThread(Socket socket){
		this.client = socket;
	}
	@Override
	public void run() {
		super.run();
		System.out.println("The Client: "+client.getInetAddress()+":"+client.getPort()+" is connected");
		try {
			inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
			outputStream = new DataOutputStream(client.getOutputStream());

			String request = inputStream.readLine();
			String header = request;

			StringTokenizer tok = new StringTokenizer(header);
			String httpMethod = tok.nextToken();
			String httpQueryString = tok.nextToken();

			//hack
			//httpQueryString = httpQueryString.replace("/request?", "");

			StringBuffer response = new StringBuffer();
			response.append("<b> This is the HTTP Server Home Page.... </b><BR>");
			response.append("The HTTP Client request is ....<BR>");

			System.out.println("The HTTP request string is ....");
			while (inputStream.ready())
			{
				// Read the HTTP complete HTTP Query
				response.append(request + "<BR>");
				System.out.println(request);
				request = inputStream.readLine();
			}

			String data[] = httpQueryString.split("/");

			if (httpMethod.equals("GET")) {
				//ResultSet theresultset = session.execute("select * from creative where height = "+height+" and width= "+width+" and publisher_id="+publisher_id);
				String theurl = "";
				String line  = "";
				makeUserPlaceList();
				//places = new ArrayList<String>();

				//http://localhost:4444/query=[id:1,time:morning,day:mon,friends:0,activity:work,feeling:optimism,emotion:surprise,lat:52.4079056,lon:-1.514289]

				//%5B/%5D
				data[1] = data[1].replace("%5B", "[");
				data[1] = data[1].replace("%5D", "]");
				String query = data[1].substring(data[1].indexOf('[')+1, data[1].indexOf(']'));

				//the query is comming from the browser, in practice it will come from mobile device
				processQuery(query);
				//convert userdata xml into corresponding csv to feed into rf model
				prepareRFDataModel();
				//encode characters into numbers
				encodeDataModel(DATATYPE.TRAIN_DATA);
				//prepare the data based on which the model will predict
				prepareTestData();
				//encode the test data now
				encodeDataModel(DATATYPE.TEST_DATA);
				//train and test, execute the python code here

				System.out.println(executeCommand("python /Users/kaderchowdhury/temp.py"));

				//prepare placerank data model for recommendations
				new PlaceRank().execute();
				//run recommendaton system
				new DoMahout().execute();

				//parse categorized_data.xml to get list of all nodes
				makeNodeList();
				//list similar items from prediction
				findAllSimilarClass();
				//Aggregate results from previous method

				sendResponse(200, "<p><font size='4'>"+JsonifyList(simnodes)+"</font></p>", false);

			}
			else sendResponse(404, "404 NOT FOUND", false);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				sendResponse(404, "404 NOT FOUND", false);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}


	}
	ArrayList<Node>node_list = new ArrayList<Node>();
	public void makeNodeList(){
		if(!node_list.isEmpty())return;
		//add class
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/categorized_data_v2.xml"))));
			String line = "";
			while((line=reader.readLine())!=null){
				Node node = new Node();
				node.type = line.substring(line.indexOf(" t=\"")+4, line.indexOf(" st=\"")-1);
				node.sub_type = line.substring(line.indexOf(" st=\"")+5, line.indexOf("/>")-1);
				node.place_id = line.substring(line.indexOf("id=")+4, line.indexOf("lat=")-2);
				node.lat = line.substring(line.indexOf("lat=")+5, line.indexOf("lon=")-2);
				node.lon = line.substring(line.indexOf("lon=")+5, line.indexOf(" n=\"")-2);
				node.name = line.substring(line.indexOf(" n=\"")+4, line.indexOf(" t=\"")-1);
				node.class_id = line.substring(line.indexOf(" class=\"")+8, line.indexOf("/>")-1);
				node_list.add(node);
				//			System.out.println(id);
				//			System.out.println(name);
				//			System.out.println(lat);
				//			System.out.println(lon);
				//			System.out.println(type);
				//			System.out.println(sub_type);
				//			break;
			}
		}catch(Exception e){}
	}
	ArrayList<UserModel>user_place_list = new ArrayList<UserModel>();
	public void sendResponse (int statusCode, String responseString, boolean isFile) throws Exception {

		String status = null;
		//server details
		String details = "Server: HTTPServer";
		String contentLength = null;
		String fileName = null;
		String contentType = "Content-Type: text/html" + "\r\n";
		FileInputStream fin = null;

		if (statusCode == 200)
			status = "HTTP/1.1 200 OK" + "\r\n";
		else
			status = "HTTP/1.1 404 Not Found" + "\r\n";

		if (isFile) {
			fileName = responseString;
			fin = new FileInputStream(fileName);
			contentLength = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
			if (!fileName.endsWith(".htm") && !fileName.endsWith(".html"))
				contentType = "Content-Type: \r\n";
		}
		else {
			responseString = HTML_START + responseString + HTML_END;
			contentLength = "Content-Length: " + responseString.length() + "\r\n";
		}

		outputStream.writeBytes(status);
		outputStream.writeBytes(details);
		outputStream.writeBytes(contentType);
		outputStream.writeBytes(contentLength);
		outputStream.writeBytes("Connection: close");
		outputStream.writeBytes("\r\n\r\n");

		if (isFile) sendFile(fin, outputStream);
		else outputStream.writeBytes(responseString);

		outputStream.close();
	}

	public void sendFile (FileInputStream fin, DataOutputStream out) throws Exception {
		byte[] buffer = new byte[1024] ;
		int bytesRead;

		while ((bytesRead = fin.read(buffer)) != -1 ) {
			out.write(buffer, 0, bytesRead);
		}
		fin.close();
	}

	public void processQuery(String query){
		String temp [] = query.split(",");
		for(int i=0;i<temp.length;i++){
			if(temp[i].contains("id"))
				id = temp[i].replace("id:", "");
			if(temp[i].contains("day"))
				day = temp[i].replace("day:", "");
			if(temp[i].contains("time"))
				time = temp[i].replace("time:", "");
			if(temp[i].contains("friends"))
				friends = temp[i].replace("friends:", "");
			if(temp[i].contains("activity"))
				activity = temp[i].replace("activity:", "");
			if(temp[i].contains("feeling"))
				feeling = temp[i].replace("feeling:", "");
			if(temp[i].contains("emotion"))
				emotion = temp[i].replace("emotion:", "");
			if(temp[i].contains("lat"))
				lat = temp[i].replace("lat:", "");
			if(temp[i].contains("lon"))
				lon = temp[i].replace("lon:", "");
		}
	}
	public void prepareTestData(){
		for(int i=0;i<Util.activities.length;i++){
			if(Util.activities[i].contains(activity.toUpperCase())){
				activity = Util.activities[i];
				break;
			}

		}
		for(int i=0;i<Util.feelings.length;i++){
			if(Util.feelings[i].contains(feeling.toUpperCase())){
				feeling = Util.feelings[i];
				break;
			}

		}
		for(int i=0;i<Util.emotions.length;i++){
			if(Util.emotions[i].contains(emotion.toUpperCase())){
				emotion = Util.emotions[i];
				break;
			}

		}
		String inputString = "";
		//		inputString +="\n";
		ArrayList<String>activity_morning = new ArrayList<String>();
		ArrayList<String>activity_noon = new ArrayList<String>();
		ArrayList<String>activity_evening = new ArrayList<String>();
		ArrayList<UserModel>list = new ArrayList<UserModel>();
		for(int i=0;i<list.size();i++){
			UserModel user = list.get(i);
			if(day.equalsIgnoreCase(user.day)){
				if(user.time.equalsIgnoreCase("morning"))
					activity_morning.add(user.activity);
				if(user.time.equalsIgnoreCase("noon"))
					activity_noon.add(user.activity);
				if(user.time.equalsIgnoreCase("evening"))
					activity_evening.add(user.activity);
			}
		}
		String class_id = "0";
		if(time.equals("morning")){
			inputString = inputString+
					id+","+day+","+"morning"+","+friends+","+activity+","+feeling+","+emotion+","+class_id;
			inputString = inputString + "\n";
			for(int i=0;i<activity_morning.size();i++){
				inputString = inputString+
						id+","+day+","+"morning"+","+friends+","+activity_morning.get(i)+","+feeling+","+emotion+","+class_id;
				inputString = inputString + "\n";
			}
			for(int i=0;i<activity_noon.size();i++){
				inputString = inputString+
						id+","+day+","+"noon"+","+friends+","+activity_noon.get(i)+","+feeling+","+emotion+","+class_id;
				inputString = inputString + "\n";
			}
		}if(time.equals("noon")){
			inputString = inputString+
					id+","+day+","+"noon"+","+friends+","+activity+","+feeling+","+emotion+","+class_id;
			inputString = inputString + "\n";
			for(int i=0;i<activity_noon.size();i++){
				inputString = inputString+
						id+","+day+","+"noon"+","+friends+","+activity_noon.get(i)+","+feeling+","+emotion+","+class_id;
				inputString = inputString + "\n";
			}
			for(int i=0;i<activity_evening.size();i++){
				inputString = inputString+
						id+","+day+","+"evening"+","+friends+","+activity_evening.get(i)+","+feeling+","+emotion+","+class_id;
				inputString = inputString + "\n";
			}
		}if(time.equals("evening")){
			inputString = inputString+
					id+","+day+","+"evening"+","+friends+","+activity+","+feeling+","+emotion+","+class_id;
			inputString = inputString + "\n";
			for(int i=0;i<activity_evening.size();i++){
				inputString = inputString+
						id+","+day+","+"evening"+","+friends+","+activity_evening.get(i)+","+feeling+","+emotion+","+class_id;
				inputString = inputString + "\n";
			}
		}
		try{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/testdata_v2.csv"))));
			writer.write(Util.label+"\n"+inputString.toUpperCase());
			writer.flush();
			writer.close();
		}catch(Exception e){}
	}
	public void prepareRFDataModel(){
		try{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/datamodel_v2.csv"))));
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/user_data_v2.xml"))));
			String line = "";
			String label = "id,day,time,friends,activity,feelings,emotion,class";
			writer.append(label);
			writer.append("\n");
			String temp = "";
			String user_id = "";
			String day = "";
			String time = "";
			String friends = "";
			String activity = "";
			String feeling = "";
			String emotion = "";
			String class_id = "";
			//parse user_data.xml get the users
			while((line=reader.readLine())!=null){
				//			if(line!=null)
				//				break;

				line = line.trim();
				if(line.startsWith("<user>")){
					//				user = new User();
					//				user.node = new Node();
					temp = "";
				}

				if(line.startsWith("<user_id>")){
					user_id = line.substring(line.indexOf("<user_id>")+"<user_id>".length(), line.indexOf("</user_id>"));
				}

				if(line.startsWith("<day>")){
					day = line.substring(line.indexOf("<day>")+"<day>".length(), line.indexOf("</day>"));
				}

				if(line.startsWith("<time>")){
					time = line.substring(line.indexOf("<time>")+"<time>".length(), line.indexOf("</time>"));
				}

				if(line.startsWith("<with_friends>")){
					friends = line.substring(line.indexOf("<with_friends>")+"<with_friends>".length(), line.indexOf("</with_friends>"));
				}

				if(line.startsWith("<activity>")){
					activity = line.substring(line.indexOf("<activity>")+"<activity>".length(), line.indexOf("</activity>"));
				}

				if(line.startsWith("<feeling>")){
					feeling = line.substring(line.indexOf("<feeling>")+"<feeling>".length(), line.indexOf("</feeling>"));
				}

				if(line.startsWith("<emotion>")){
					emotion = line.substring(line.indexOf("<emotion>")+"<emotion>".length(), line.indexOf("</emotion>"));
				}

				if(line.startsWith("<class>")){
					class_id = line.substring(line.indexOf("<class>")+"<class>".length(), line.indexOf("</class>"));
				}

				if(line.startsWith("</user>")){
					temp = user_id+","+day+","+time+","+friends+","+activity+","+feeling+","+emotion+","+class_id;
					writer.append(temp);
					writer.append("\n");
				}
			}
			reader.close();
			writer.flush();
			writer.close();
		}catch(Exception e){}
	}
	enum DATATYPE{
		TRAIN_DATA,
		TEST_DATA
	}
	public void encodeDataModel(DATATYPE type){
		try{
			BufferedReader reader = null;
			BufferedWriter writer = null;
			if(type == DATATYPE.TRAIN_DATA){
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/datamodel_v2_encoded.csv"))));
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/datamodel_v2.csv"))));
			}else if(type == DATATYPE.TEST_DATA){
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/testdata_v2_encoded.csv"))));
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/testdata_v2.csv"))));
			}
			String line = "";
			String out = "";
			while((line=reader.readLine())!=null){
				String temp [] = line.split(",");
				for(int i =0;i<temp.length;i++){

					for(int k = 0;k<Util.days.length;k++){
						if(Util.days[k].equals(temp[1])){
							temp[1] = k+"";
							continue;
						}
					}
					for(int k = 0;k<Util.times.length;k++){
						if(Util.times[k].equals(temp[2])){
							temp[2] = k+"";
							continue;
						}
					}
					for(int k = 0;k<Util.activities.length;k++){
						if(Util.activities[k].equals(temp[4])){
							temp[4] = k+"";
							continue;
						}
					}
					for(int k = 0;k<Util.feelings.length;k++){
						if(Util.feelings[k].equals(temp[5])){
							temp[5] = k+"";
							continue;
						}
					}
					for(int k = 0;k<Util.emotions.length;k++){
						if(Util.emotions[k].equals(temp[6])){
							temp[6] = k+"";
							continue;
						}
					}
				}
				out = "";
				for(int i=0;i<temp.length;i++)
					out = out+","+temp[i];
				out = out.replaceFirst(",", "");
				writer.append(out);
				writer.append("\n");
			}
			writer.flush();
			writer.close();
		}catch(Exception e){}
	}
	public static String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
					new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

		return output.toString();

	}
	public void makeUserPlaceList(){
		try{
			if(user_place_list.isEmpty()){
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/user_data_v2.xml"))));
				String line = "";
				UserModel user = new UserModel();
				while((line=reader.readLine())!=null){

					if(line.contains("<user>"))
						user= new UserModel();
					if(line.contains("class"))
						user.class_id = line.substring(line.indexOf("<class>"), line.indexOf("</class>")).replace("<class>", "");
					if(line.contains("user_id"))
						user.id = line.substring(line.indexOf("<user_id>"), line.indexOf("</user_id>")).replace("<user_id>", "");
					if(line.contains("<time>"))
						user.time = line.substring(line.indexOf("<time>"), line.indexOf("</time>")).replace("<time>", "");
					if(line.contains("day"))
						user.day = line.substring(line.indexOf("<day>"), line.indexOf("</day>")).replace("<day>", "");
					if(line.contains("friends"))
						user.friends = line.substring(line.indexOf("<with_friends>"), line.indexOf("</with_friends>")).replace("<with_friends>", "");
					if(line.contains("activity"))
						user.activity = line.substring(line.indexOf("<activity>"), line.indexOf("</activity>")).replace("<activity>", "");
					if(line.contains("feeling"))
						user.feeling = line.substring(line.indexOf("<feeling>"), line.indexOf("</feeling>")).replace("<feeling>", "");
					if(line.contains("emotion"))
						user.emotion = line.substring(line.indexOf("<emotion>"), line.indexOf("</emotion>")).replace("<emotion>", "");
					if(line.contains("</user>"))
						user_place_list.add(user);
				}
				reader.close();
			}
		}catch(Exception e){}
	}

	ArrayList<Node>simnodes = new ArrayList<Node>();
	public void findAllSimilarClass(){
		try{
			simnodes = new ArrayList<Node>();
			ArrayList<String>predictions = new ArrayList<String>();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/prediction.txt"))));
			String line = "";
			while((line=reader.readLine())!=null){
				if(!predictions.contains(line))predictions.add(line.trim());
			}
			//list all similar places within 1km radius
			for(int i=0;i<node_list.size();i++){

				for(int k=0;k<predictions.size();k++){
					if(node_list.get(i).class_id.equals(predictions.get(k))){
						float node_lat = Float.valueOf(node_list.get(i).lat);
						float node_lon = Float.valueOf(node_list.get(i).lon);

						float user_lat = Float.valueOf(lat);
						float user_lon = Float.valueOf(lon);
						Node node = node_list.get(i);
						if(isVisited(Long.valueOf(node_list.get(i).place_id))){
							node.rank = "2.0";
						}else{
							node.rank = "1.0";
						}
						node.dist = Util.getDistanceFromLatLonInKm(node_lat, node_lon, user_lat, user_lon);
						if(node.dist<=1){
							simnodes.add(node);	
						}
					}

				}

			}
			aggregate_task();
		}catch(Exception e){}

	}
	public String JsonifyList(ArrayList<Node>user_nodes){
		Collections.sort(user_nodes);

		JSONArray array = new JSONArray();

		for(int i=0;i<user_nodes.size();i++){
			Node node = user_nodes.get(i);
			JSONObject object = new JSONObject();
			object.put("user_id", id);
			object.put("place_id",node.place_id);
			object.put("name", node.name);
			object.put("lat",node.lat);
			object.put("lon", node.lon);
			object.put("type", node.type);
			object.put("sub_type", node.sub_type);
			object.put("distance", node.dist+"km");
			object.put("rank", node.rank);
			array.add(object);
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(array.toJSONString());
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}
	public boolean isVisited(long id)throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/user_data_v2.xml"))));
		String line = "";
		while((line=reader.readLine())!=null){
			if(line.trim().startsWith("<place_id>")){
				try{
					long place_id = Long.valueOf(line.substring(line.indexOf("<place_id>"), line.indexOf("</place_id>")).replace("<place_id>", ""));
					if(place_id == id)
						return true;	
				}catch(Exception e){
					e.printStackTrace();
				}

			}
		}
		return false;
	}
	public void aggregate_task()throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/user_recommendations.csv"))));
		String line = "";
		while((line=reader.readLine())!=null){
			for(int i=0;i<node_list.size();i++){
				String [] temp = line.split(",");
				String rank = temp[1];
				Node node = node_list.get(i);
				if(node.place_id.equals(temp[0])){
					float node_lat = Float.valueOf(node_list.get(i).lat);
					float node_lon = Float.valueOf(node_list.get(i).lon);

					float user_lat = Float.valueOf(lat);
					float user_lon = Float.valueOf(lon);
					node.dist = Util.getDistanceFromLatLonInKm(node_lat, node_lon, user_lat, user_lon);
					node.rank = rank;
					simnodes.add(node);
				}
			}
		}
	}
}