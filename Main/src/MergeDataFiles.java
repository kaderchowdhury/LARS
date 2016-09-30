import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MergeDataFiles {

	static BufferedWriter writer;
	public static void main(String[] args) throws IOException {
		//iteratively merge all 18 files and put the contents in categorized_data.xml
		//make a new xml file to store the categories

		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data/categorized_data_1.xml"))));
//		for(int i=1;i<=18;i++){
//			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data/"+i+".xml"))));
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data/categorized_data.xml"))));
			String line = "";
			while((line=reader.readLine())!=null){
				line = line.replaceAll("&#34;", "");
				writer.append(line);
				writer.append("\n");
			}
			reader.close();
//		}
		writer.flush();
		writer.close();
	}
}