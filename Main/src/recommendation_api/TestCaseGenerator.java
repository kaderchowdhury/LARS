package recommendation_api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TestCaseGenerator {

	public static void main(String[] args) throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/kaderchowdhury/University/Project/data/user_data_v2.xml"))));
		String line = "";
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
		if(line.contains("</user>"));
			//list.add(user);
	}
	reader.close();
	}
}
