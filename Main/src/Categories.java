import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Categories {
	static BufferedWriter writer;
	static HashMap<String, ArrayList<String>>categories = new HashMap<String, ArrayList<String>>();
	public static void main(String[] args) throws IOException {
		Categories main = new Categories();
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data/categories.xml"))));
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data/categorized_data.xml"))));
		String line = "";
		while((line=reader.readLine())!=null){
			String type = line.substring(line.indexOf(" t=\"")+4, line.indexOf(" st=\"")-1);
			String sub_type = line.substring(line.indexOf(" st=\"")+5, line.indexOf("/>")-1);
			//			System.out.println(type+"\n"+sub_type);
			ArrayList<String>list = categories.get(type);
			if(list==null){
				list = new ArrayList<String>();
				categories.put(type, list);
			}
			if(!list.contains(sub_type)){
				if(!sub_type.equals(""))
					list.add(sub_type);
			}
		}
		Iterator it = categories.entrySet().iterator();
		writer.append("<categories>");
		writer.append("\n");
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			String type = (String)pair.getKey();
			ArrayList<String>list = (ArrayList<String>)pair.getValue();
			writer.append("\t<"+type+">");
			writer.append("\n");
			for(int i=0;i<list.size();i++){
				writer.append("\t\t<"+list.get(i)+"/>");
				writer.append("\n");
			}
			writer.append("\t</"+type+">");
			writer.append("\n");
			//System.out.println(pair.getKey() + " = " + pair.getValue());
			//it.remove(); // avoids a ConcurrentModificationException
		}
		writer.append("</categories>");
		writer.flush();
		writer.close();
	}
}