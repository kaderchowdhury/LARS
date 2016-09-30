import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;


public class DoMahout {
	public static void main(String args[]){

		execute();

	}
	public static void execute(){
		try{
			//Creating data model
			DataModel datamodel = new FileDataModel(new File("/Users/kaderchowdhury/University/Project/data_v2/data_model_recommendation.csv")); //data

			//Creating UserSimilarity object.
			UserSimilarity usersimilarity = new PearsonCorrelationSimilarity(datamodel);

			//Creating ItemSimilarity object
			//ItemSimilarity sim = new LogLikelihoodSimilarity(datamodel);

			//	         GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(datamodel, sim);
			//	         for(LongPrimitiveIterator items=datamodel.getItemIDs();items.hasNext();){
			//	        	 long itemId = items.nextLong();
			//	        	 List<RecommendedItem>recommendations = recommender.mostSimilarItems(itemId, 5);
			//	        	 for (RecommendedItem recommendation : recommendations) {
			//	 	            System.out.println(itemId+","+recommendation.getItemID()+","+recommendation.getValue());
			//	 	         }
			//	         }
			//Creating UserNeighbourHHood object.
			UserNeighborhood userneighborhood = new ThresholdUserNeighborhood(0.1, usersimilarity, datamodel);

			//Create UserRecomender
			UserBasedRecommender recommender = new GenericUserBasedRecommender(datamodel, userneighborhood, usersimilarity);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kaderchowdhury/University/Project/data_v2/user_recommendations.csv"))));
			//iterating through all the users
			//			for(int i=1;i<=10;i++){
			List<RecommendedItem> recommendations = recommender.recommend(1, 3);
			for (RecommendedItem recommendation : recommendations) {
				System.out.println(recommendation.getItemID()+","+recommendation.getValue());
				//				System.out.println(recommendation);
				writer.append(recommendation.getItemID()+","+recommendation.getValue());
				writer.append("\n");
			}
			//			}
			writer.flush();
			writer.close();
			//user_id,number of recommendations




		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
