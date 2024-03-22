package recommendation;

import java.util.ArrayList;
import java.util.List;

import front.DatabaseHandler;

public class RecommendationService {

	private DatabaseHandler dbHandler;

    public RecommendationService(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }
    
    public List<Recommendation> getRecommendations(String username) {
    	List<Recommendation> recommendations = new ArrayList<>();
        
    	try {
    		Object[][] rawData = dbHandler.retrieveRecommendations(username);
	 
	        for (Object[] row : rawData) {
	        	try {
	        		String title = (String) row[0];
	            	int rating = (int) row[1];
	            	recommendations.add(new Recommendation(title, rating));
	        	}catch(ClassCastException e) {
	        		System.err.println("Type converstion error in recommendation data: " + e.getMessage());
	        		continue;
	        	}
	        }
    	}catch(Exception e) {
    		System.err.println("Error retrieving recommendations: " + e.getMessage());
    	}
        return recommendations;
    }
    
}
