package recommendation;

public class Recommendation {
	private String showName; 
    private int rating; 
    private String review;

    public Recommendation(String showName, int rating) {
        this.showName = showName;
        this.rating = rating;
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

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public void setReview(String review) {
    	this.review = review;
    }
}
