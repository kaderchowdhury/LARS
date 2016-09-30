package recommendation_api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
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

public class HTTPServerAPI {
	static final String HTML_START =
			"<html>" +
					"<title>HTTP Server</title>" +
					"<body>";

	static final String HTML_END =
			"</body>" +
					"</html>";
	public static void main(String [] args) throws IOException{
		ServerSocket server_socket = new ServerSocket (4444, 10, InetAddress.getByName("127.0.0.1"));
		System.out.println ("TCPServer Waiting for client on port 4444");
		//create keyspace "demo" WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : '1'};
		// Connect to the cluster and keyspace "demo"

		while(true) {
			Socket connected = server_socket.accept();
			Thread thread = new ServerThread(connected);
			thread.start();
		}
		//server_socket.close();
	}


	public static class ServerThread extends Thread{
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
//				HashMap<String, String> data = (HashMap<String, String>) Util.splitQuery(new URL(httpQueryString));

				//				String clicks = "0";//clicks are not identified 
				//				String ip = data.get("data");
				//				String latitude  = data.get("latitude");
				//				String longitude  = data.get("longitude");
				//				String user_agent = data.get("user_agent");
				//				String height = data.get("height");
				//				String width = data.get("width");
				//				String publisher_id = data.get("publisher_id");

				String data[] = httpQueryString.split("/");
//				String id = data.get("id");
//				String lat = data.get("lat");
//				String lon = data.get("lon");
				String id = data[1];
				String lat = data[2];
				String lon = data[3];

				if (httpMethod.equals("GET")) {
					//ResultSet theresultset = session.execute("select * from creative where height = "+height+" and width= "+width+" and publisher_id="+publisher_id);
					String theurl = "";
					String line  = "";
					if(node_list.isEmpty()){
						BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data/categorized_data.xml"))));
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
						}
						reader.close();
					}
					places = new ArrayList<String>();
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data/data_model.csv"))));
					line = "";
					while((line=reader.readLine())!=null){
						line = line.trim();
						String temp[] = line.split(",");
						if(temp[0].equals(id)){
							if(Float.valueOf(temp[2])>0.0f){
								places.add(temp[1]);
								ratings.add(temp[2]);
							}
						}
					}
					reader.close();
					reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data/user_recommendations.csv"))));
					line = "";
					while((line=reader.readLine())!=null){
						line = line.trim();
						String temp[] = line.split(",");
						if(temp[0].equals(id)){
							if(Float.valueOf(temp[2])>0.0f){
								places.add(temp[1]);
								ratings.add(temp[2]);
							}
						}
					}
					reader.close();

					ArrayList<Node>user_nodes = new ArrayList<Node>();
					for(int i=0;i<node_list.size();i++){
						Node node = node_list.get(i);
						for(int k=0;k<places.size();k++){
							
							if(node.place_id.equals(places.get(k))){
								Float my_lat = Float.valueOf(lat);
								Float my_lon = Float.valueOf(lon);
								Float new_lat = Float.valueOf(node_list.get(i).lat);
								Float new_lon = Float.valueOf(node_list.get(i).lon);
								node.dist = Util.getDistanceFromLatLonInKm(my_lat, my_lon, new_lat, new_lon);
								node.rank = ratings.get(k);
								user_nodes.add(node);
							}
						}
					}
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
					sendResponse(200, prettyJsonString, false);

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
		ArrayList<String>places = new ArrayList<String>();
		ArrayList<String>ratings = new ArrayList<String>();
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
				responseString = HTTPServerAPI.HTML_START + responseString + HTTPServerAPI.HTML_END;
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
	}

}