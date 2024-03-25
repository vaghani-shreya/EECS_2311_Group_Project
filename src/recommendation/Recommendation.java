package recommendation;

public class Recommendation {
	private final String showName; 
    private int rating; 
    private String review;
    private final int releaseYear;

//    public Recommendation(String showName, int rating) {
//        this.showName = showName;
//        this.rating = rating;
//    }

    public Recommendation(String showName, int releaseYear) {
      this.showName = showName;
      this.releaseYear = releaseYear;
      
    }
    public String getShowName() {
        return showName;
    }

    public int getRating() {
        return rating;
    }
    
    public String getReview() {
    	return review;
    }
    
    public int getReleaseYear() {
    	return releaseYear;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public void setReview(String review) {
    	this.review = review;
    }
    
}