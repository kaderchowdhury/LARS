import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Main {


	public static void main(String [] args) throws Exception{

		HashMap<String, String>testset = new HashMap<String, String>();
		HashMap<String, String>dataset = new HashMap<String, String>();
		for(int i=0;i<10;i++){
			if((int)(Math.random()*1000)%2==0)System.out.println("even");
			else System.out.println("odd");
			//System.out.println((int)(Math.random()*1000));
		}
		System.exit(0);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("ujipenchars2.txt"))));
		String line;
		String word = "";
		String numbers = "";
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("data_old.csv"))));
		BufferedWriter group_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("group.csv"))));
		boolean skip = false;
		while((line = reader.readLine())!=null){
			if(line.startsWith("WORD")){
				String words[] = line.split(" ");

				if(!numbers.isEmpty()){
					//write
					//					String out = numbers.trim()+" "+word;
					if((word.charAt(0)>='a'&&word.charAt(0)<='z')||(word.charAt(0)>='A'&&word.charAt(0)<='Z')||(word.charAt(0)>='0'&&word.charAt(0)<='9')){
						if(testset.get("TEST_"+word)==null){
							testset.put("TEST_"+word, numbers.trim());
						}else{
							String oldNumbers  = dataset.get(word);
							if(oldNumbers == null)
								oldNumbers = "";
							oldNumbers = oldNumbers+" "+numbers.trim();
							dataset.put(word, oldNumbers.trim());
						}
						skip = false;
					}else{
						skip = true;
					}
					String out = numbers.trim()+" "+word;
					out = out.replaceAll(" ", ",");
					writer.write(out);
					writer.write("\n");
				}
				word = words[1];
				//				word += words[1]+" ";
				numbers = "";
			}
			//			if(!skip){
			if(line.contains("#")){
				numbers = numbers.trim() +" "+line.substring(line.indexOf("#")+1).trim();
				numbers = numbers.trim();
			}
			//			}
		}
		word = word.trim();
		word = word.replaceAll(" ", ",");
		group_writer.write(word);
		group_writer.flush();
		group_writer.close();

		//		String out = numbers.trim()+" "+word;
		String out = numbers.trim();
		out = out.replaceAll(" ", ",");
		writer.write(out);
		writer.write("\n");
		reader.close();
		writer.flush();
		writer.close();
		//write

		writeData(dataset);
		writeTestData(testset);
	}

	public static void writeData(HashMap<String, String>mp){
		StringBuffer groups = new StringBuffer();
		StringBuffer buffer = new StringBuffer();
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data.csv")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Iterator it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			String key = (String) pair.getKey();
			String val = (String) pair.getValue();
			//	        System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
			val = val.trim().replaceAll(" ", ",");
			buffer.append(val);
			buffer.append("\n");
			groups.append(key);
			groups.append("\n");
		}
		try {
			out.write(buffer.toString());
			out.flush();
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("groups.csv")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			out.write(groups.toString());
			out.flush();
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static void writeTestData(HashMap<String, String>mp){
		Iterator it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			String key = (String) pair.getKey();
			String val = (String) pair.getValue();
			//	        System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
			try {
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(key+".csv")));
				val = val.trim().replaceAll(" ", ",");
				out.write(val);
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}