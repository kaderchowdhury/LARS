import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

	public static void main(String[] args) {
		try{
			
			ServerSocket server_socket = new ServerSocket (4444, 10, InetAddress.getByName("127.0.0.1"));
			System.out.println ("TCPServer Waiting for client on port 4444");
			//create keyspace "demo" WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : '1'};
			// Connect to the cluster and keyspace "demo"

			while(true) {
				try{
					Socket connected = server_socket.accept();
					Thread thread = new ServerThread(connected);
					thread.start();
				}catch(Exception e){}
			}
			//server_socket.close();		
		}catch(Exception e){}

	}

}


/*
 *  The total project put together
 *
 * 1st step Data Preparation, no longer needed here
 * 2nd step Generating User Data, also no longer needed
 * 			categoriged_data_v2 contains all the locations of midlands and london
 * 			user_data_v2 contains generated data for user_1 which comes from the mobile app
 * 3rd step from user data make the data model for decision tree
 * 4rth set encode the features
 * 5th step take inputs from get request and prepare the test data
 * 6th step run the python program and write predictions into prediction.txt file
 * 7th step list all items similar to predictions
 * 8th apply colaborative filtering only on checkin items 
 * 9th step merge results from step 7 and 8
 * 10th step responde the result as json
 * 11th step put the json in the map and show the results with markers and images
 * 
 * */