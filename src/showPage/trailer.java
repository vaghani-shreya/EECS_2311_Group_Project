package showPage;

import java.util.List;

public class trailer {
    private List<String> trailerUrls;

    public trailer(List<String> trailerUrls) {
        this.trailerUrls = trailerUrls;
    }

    public List<String> getTrailerUrls() {
        return trailerUrls;
    }

    public void setTrailerUrls(List<String> trailerUrls) {
        this.trailerUrls = trailerUrls;
    }

    public void removeTrailerUrls() {
        this.trailerUrls = null;
    }
}
