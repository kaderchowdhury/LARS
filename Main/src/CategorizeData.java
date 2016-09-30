import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Locale.Category;


public class CategorizeData {

	public static void main(String[] args) throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data/categorized_data.xml"))));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data/categorized_data_v2.xml"))));
		HashMap<String,Integer>categiry_id=new HashMap<String, Integer>();
		String line = "";
		int id = 1;
		while((line = reader.readLine())!=null){
			Node node = new Node();
			//				String id = line.substring(0, line.indexOf(";"));
			node.place_id = line.substring(line.indexOf("id=")+4, line.indexOf("lat=")-2);
			node.lat = line.substring(line.indexOf("lat=")+5, line.indexOf("lon=")-2);
			node.lon = line.substring(line.indexOf("lon=")+5, line.indexOf(" n=\"")-2);
			node.name = line.substring(line.indexOf(" n=\"")+4, line.indexOf(" t=\"")-1);
			node.type = line.substring(line.indexOf(" t=\"")+4, line.indexOf(" st=\"")-1);
			node.sub_type = line.substring(line.indexOf(" st=\"")+5, line.indexOf("/>")-1);
			node.timestamp = Util.getRandomTimeStamp();
			//System.out.println(id+" "+lat+" "+lng+" "+name+" "+type+" "+sub_type);
			String key = node.type+";"+node.sub_type;
			Integer value = categiry_id.get(key);
			if(value == null){
				value = id;
				id++;
				categiry_id.put(key, value);
			}
//			line = line.replace("  n=\"", " n=\"");
			line = line.replace("/>", "");
			writer.append(line+" "+"class=\""+value+"\"/>");
			writer.append("\n");
//			nodes.add(node);
			//				System.out.println(id);
			//			break;
		}
		writer.flush();
		writer.close();
	}	

}