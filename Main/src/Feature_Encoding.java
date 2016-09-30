import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Feature_Encoding {
	public static void main(String[] args) throws Exception{
	
		//test model encoding
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/testdata_encoded_v2.csv"))));
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/testdata_v2.csv"))));
		//datamodel encoding
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/datamodel_encoded_v2.csv"))));
//		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/datamodel_v2.csv"))));

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
	}
}
