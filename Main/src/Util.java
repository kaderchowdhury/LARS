import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

public class Util {

	//user data must contain longitude latitude, frequency, date and time
	//DDMMYYYY.HHmm
	public static float USER_1[][]={
		{52.4257118f,-1.8082672f,2,12062016.1303f},
		{52.5870047f,-2.1265116f,2,12062016.1413f},
		{52.4721808f,-2.0825964f,1,13062016.1404f},
		{52.5454798f,-1.8130886f,2,14062016.1305f},
		{52.4217283f,-1.8082592f,3,14062016.1506f},
		{52.3969472f,-1.6696247f,1,15062016.0908f},
		{52.4300993f,-1.4980597f,1,15062016.1709f},
		{52.5353855f,-2.1643548f,1,16062016.1901f},
		{52.5198369f,-2.0525865f,1,16062016.2000f},
		{52.4011545f,-1.9284803f,2,16062016.2220f},
		{52.5753885f,-1.7839682f,3,16062016.1025f},
		{52.5193188f,-2.0870021f,1,17062016.1130f},
		{52.42907f,-1.8823848f,2,18062016.1345f},
		{52.4539488f,-1.6063214f,1,18062016.1550f},
		{52.4955799f,-1.8531113f,2,18062016.1844f},
	};

	public static float USER_2[][]={
		{52.5037825f,-2.0167906f,2,12062016.1303f},
		{52.4097316f,-1.573341f,1,12062016.1404f},
		{52.5016554f,-2.0170363f,1,12062016.1408f},
		{52.4697478f,-2.0090591f,2,12062016.1413f},
		{52.5858458f,-2.1261513f,3,12062016.1333f},
		{52.4116726f,-1.7791599f,3,12062016.1545f},
		{52.4779301f,-1.9115762f,3,13062016.1850f},
		{52.4901567f,-1.824091f,2,13062016.1800f},
		{52.4318028f,-1.8336574f,2,13062016.2010f},
		{52.425582f,-1.9292078f,1,13062016.1045f},
		{52.5255063f,-1.7305188f,1,14062016.1011f},
		{52.5209161f,-1.7857978f,1,14062016.2204f}
	};

	public static float USER_3[][]={
		{52.4064789f,-1.8779458f,2,12062016.1303f},
		{52.4380275f,-1.4478197f,2,12062016.1304f},
		{52.4441326f,-1.7946402f,2,12062016.1410f},
		{52.403643f,-1.5152925f,2,12062016.1411f},
		{52.5559749f,-2.0209166f,2,13062016.1512f},
		{52.3740124f,-1.7520251f,2,13062016.1600f},
		{52.4133914f,-1.7786473f,2,13062016.1645f},
		{52.4776844f,-2.0794922f,2,14062016.1110f},
		{52.4506929f,-1.7996124f,2,14062016.1705f},
		{52.4751798f,-1.7997868f,2,15062016.1705f},
		{52.5062396f,-2.0708335f,2,15062016.1818f},
		{52.4920286f,-1.8591327f,2,15062016.1920f},
		{52.4366089f,-1.9299316f,2,16062016.2021f}

	};


	public static double getDistanceFromLatLonInKm(float lat1,float lon1,float lat2,float lon2) {
		int R = 6371; // Radius of the earth in km
		double dLat = deg2rad(lat2-lat1);  // deg2rad below
		double dLon = deg2rad(lon2-lon1); 
		double a = 
				Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
				Math.sin(dLon/2) * Math.sin(dLon/2)
				; 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double d = R * c; // Distance in km
		return d;
	}

	public static double deg2rad(float deg) {
		return deg * (Math.PI/180d);
	}
	public static String makeSHA1Hash(String input)
			throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.reset();
		byte[] buffer = input.getBytes("UTF-8");
		md.update(buffer);
		byte[] digest = md.digest();

		String hexStr = "";
		for (int i = 0; i < digest.length; i++) {
			hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return hexStr;
	}
	public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
		Map<String, String> query_pairs = new LinkedHashMap<String, String>();
		String query = url.getQuery();
		String[] pairs = query.split("&");
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
			query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
		}
		return query_pairs;
	}
	public static String getRandomTimeStamp(){
		long offset = Timestamp.valueOf("2016-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2016-06-30 00:00:00").getTime();
		long diff = end - offset + 1;
		Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
		return rand.toString();
	}
	//randomize rating and friends
	public static String []days = {"MON","TUE","WED","THU","FRI","SAT","SUN"};
	public static String []times = {"MORNING","NOON","EVENING"};
	public static String [] emotions={"LIKING","JOY","SURPRISE","ANGER","SADNESS","FEAR"};
	public static String [] feelings={"OPTIMISM","LOVE","SUBMISSION","AWARE","DISAPPROVAL","REMORSE","CONTEMPT","AGGRESSIVENESS"};
	public static String [] activities={"WORK/HOUSEWORK","PREPARING MEALS/EATING","TAKING MEDICATION","RELIGIOUS OBSERVANCES","SHOPPING","USE OF TELEPHONE","COMMUNITY MOBILITY"};
//	public static String [] emotions={"LIKING","JOY"};
//	public static String [] feelings={"OPTIMISM","LOVE"};
//	public static String [] activities={"WORK/HOUSEWORK","PREPARING MEALS/EATING"};

	//public static String[]
	public static String getRandomValue(String array[]){
		int length = array.length;
		int index = (int)(Math.random()*100)%length;
		return array[index];
	}
}