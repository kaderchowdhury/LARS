import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Dataset {
	public static void main(String[] args) throws Exception{
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
				System.out.println(temp);
				writer.append(temp);
				writer.append("\n");
			}
		}
		reader.close();
		writer.flush();
		writer.close();
	}
}
