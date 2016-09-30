package recommendation_api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

public class PredictionInputAPI {
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
				//				String id = data[1];
				//				String lat = data[2];
				//				String lon = data[3];

				if (httpMethod.equals("GET")) {
					//ResultSet theresultset = session.execute("select * from creative where height = "+height+" and width= "+width+" and publisher_id="+publisher_id);
					String theurl = "";
					String line  = "";
					if(list.isEmpty()){
						BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data/user_data_v2.xml"))));
						line = "";
						UserModel user = new UserModel();
						while((line=reader.readLine())!=null){

							if(line.contains("<user>"))
								user= new UserModel();
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
								list.add(user);
						}
						reader.close();
					}
					//places = new ArrayList<String>();

					//http://localhost:4444/query=[id:1,time:morning,day:mon,friends:0,activity:work,feeling:optimism,emotion:surprise]

					String id = "";
					String time = "";
					String day = "";
					String friends = "";
					String activity = "";
					String feeling = "";
					String emotion = "";
					data[1] = data[1].replace("%5B", "[");
					data[1] = data[1].replace("%5D", "]");	
					String query = data[1].substring(data[1].indexOf('[')+1, data[1].indexOf(']'));
					String label = "id,day,time,friends,activity,feelings,emotion,class";
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
					}
					
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
					//inputString +="</br>";
					ArrayList<String>activity_morning = new ArrayList<String>();
					ArrayList<String>activity_noon = new ArrayList<String>();
					ArrayList<String>activity_evening = new ArrayList<String>();
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
					if(time.equals("morning")){
						inputString = inputString+
								id+","+day+","+"morning"+","+friends+","+activity+","+feeling+","+emotion;
						inputString = inputString + "</br>";
						for(int i=0;i<activity_morning.size();i++){
							inputString = inputString+
									id+","+day+","+"morning"+","+friends+","+activity_morning.get(i)+","+feeling+","+emotion;
							inputString = inputString + "</br>";
						}
						for(int i=0;i<activity_noon.size();i++){
							inputString = inputString+
									id+","+day+","+"noon"+","+friends+","+activity_noon.get(i)+","+feeling+","+emotion;
							inputString = inputString + "</br>";
						}
					}if(time.equals("noon")){
						inputString = inputString+
								id+","+day+","+"noon"+","+friends+","+activity+","+feeling+","+emotion;
						inputString = inputString + "</br>";
						for(int i=0;i<activity_noon.size();i++){
							inputString = inputString+
									id+","+day+","+"noon"+","+friends+","+activity_noon.get(i)+","+feeling+","+emotion;
							inputString = inputString + "</br>";
						}
						for(int i=0;i<activity_evening.size();i++){
							inputString = inputString+
									id+","+day+","+"evening"+","+friends+","+activity_evening.get(i)+","+feeling+","+emotion;
							inputString = inputString + "</br>";
						}
					}if(time.equals("evening")){
						inputString = inputString+
								id+","+day+","+"evening"+","+friends+","+activity+","+feeling+","+emotion;
						inputString = inputString + "</br>";
						for(int i=0;i<activity_evening.size();i++){
							inputString = inputString+
									id+","+day+","+"evening"+","+friends+","+activity_evening.get(i)+","+feeling+","+emotion;
							inputString = inputString + "</br>";
						}
					}
					

					
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/testdata_v2.csv"))));
					writer.write(label+"\n"+inputString.replaceAll("</br>", "\n").toUpperCase());
					writer.flush();
					writer.close();
					sendResponse(200, "<p><font size='4'>"+label+"</br>"+inputString.toUpperCase()+"</font></p>", false);
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
		ArrayList<UserModel>list = new ArrayList<UserModel>();
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
				responseString = PredictionInputAPI.HTML_START + responseString + PredictionInputAPI.HTML_END;
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