import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class KNN {
	public static void main(String [] args)throws Exception{


		int K = 5;//initializing K

		//
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data.csv"))));
		BufferedReader test_reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("TEST_i.csv"))));
		BufferedReader group_reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("groups.csv"))));
		String line = null;
		line = test_reader.readLine();
		String [] test_set = line.split(",");
		ArrayList<Double>total_mean = new ArrayList<Double>();
		ArrayList<String>group_names = new ArrayList<String>();
		while((line = group_reader.readLine())!=null){
			group_names.add(line);
		}
		//for each coordinate of test set iterate through all training set
		int i=0;
		while(i<test_set.length){

			int testX = Integer.valueOf(test_set[i]);
			int testY = Integer.valueOf(test_set[i+1]);
			i+=2;
			int line_no = 0;
			while(		
					(line = reader.readLine())!=null){
				String train_set [] = line.split(",");
				ArrayList<Double>dists_ = new ArrayList<Double>();
				int t = 0;
				while(t<train_set.length){
					int trainX = Integer.valueOf(train_set[t]);
					int trainY = Integer.valueOf(train_set[t+1]);
					t+=2;
					double dist = Math.sqrt(Math.pow(trainX-testX, 2)+(Math.pow(trainY-testY, 2)));
					dists_.add(dist);

				}
				Collections.sort(dists_);
				double sum = 0;
				for(int p=0;p<K;p++){
					sum+=dists_.get(p);
				}
				double mean_dist = sum/K;
				if(line_no<total_mean.size())
					total_mean.set(line_no, total_mean.get(line_no)+mean_dist);
				else
					total_mean.add(mean_dist);	
			line_no++;
			}//while ends

		}//test set iteration
		for(int q=0;q<total_mean.size();q++){
			System.out.println(group_names.get(q)+" "+total_mean.get(q)/(test_set.length/2));
		}
	}
}