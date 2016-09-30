import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class DataPrep {

	static BufferedWriter writer;
	static BufferedWriter out;
	public static void main(String[] args) {
		//merge london and midlands data in one file
		//keep the nodes only
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/london_and_midlands_old.xml"))));
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/london_and_midlands.xml"))));
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/18.xml"))));

			//			int count = 0;
			writer.append("<osm>\n");
			//			readFile(reader);
			//writer.append("</osm>");
			//			writer.flush();
			//			writer.close();
			//			reader.close();
			//			
			//			writer = null;
			//readling london data
			//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/greater-london-latest.osm"))));
			//writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/london_and_midlands.xml"))));

			readFile(reader);
			writer.append("</osm>");
			writer.flush();
			writer.close();
			out.flush();
			out.close();
			reader.close();
			//			System.out.println(count);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void readFile(BufferedReader reader) throws IOException{
		String buffer = null;
		String line = "";
		while((line=reader.readLine())!=null){
			//if line starts with <node and ends with /> then skip that line
			line = line.trim();//remove whitespaces
			if(line.startsWith("<node")&&line.endsWith("/>"))continue;
			else if(line.startsWith("<node")){
				//start recording
				buffer = "";
				//				String  batil = line.substring(line.indexOf("version"), line.indexOf(">"));
				//				line = line.replace(batil, "");
				//				
				//				System.out.println(line);

			}
			if(buffer!=null){
				//				if(!line.contains("source")){
				buffer += line;
				buffer+="\n";
				//				}
			}
			if(line.equals("</node>")){

				//				
				//				if(temp.length==3){
				//					//System.out.println(buffer);
				//					//break;
				//					//					if(buffer.contains("created_by")||buffer.contains("highway")||buffer.contains("barrier")){
				//					buffer = null;
				//					continue;
				//					//					}
				//				}
				if(buffer.contains("k=\"amenity\"")){
					String temp[] = buffer.split("\n");	
					temp[0] = temp[0].replace(">", "");
					//find cuisin
					String val = "";
					String name = "";
					for(int i=temp.length-1;i>0;i--){
						if(temp[i].contains("k=\"amenity\"")){
							val = temp[i];
							val = val.substring(val.indexOf("v=")+3, val.indexOf("/>")-1);
						}
						if(temp[i].contains("name")){
							name = temp[i];
							name = name.substring(name.indexOf("v=")+3, name.indexOf("/>")-1);
						}
					}

//					buffer = temp[0]+" "+"n=\""+name+"\""+" "+"t=\"leisure\" st=\""+""+"\""+"/>";
					buffer = temp[0]+" "+"n=\""+name+"\""+" "+"t=\""+val+"\" "+"st=\""+""+"\""+"/>";
					out.append(buffer+"\n");
//					System.out.println(buffer);
				}
				else
					writer.append(buffer);
				buffer = null;

			}
		}
	}
}