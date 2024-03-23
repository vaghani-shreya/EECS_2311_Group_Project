package recommendation;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TMDBFetcher {

	private static final String API_KEY = "436d078ec2369d3f3800fd5de3fa6464"; // Use TMDb API
    private static final String BASE_URL = "https://api.themoviedb.org/3"; // TMDb API URL
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"; // width 500

    private HttpClient client;
    private ObjectMapper mapper;
    
    public TMDBFetcher() {
    	client = HttpClient.newHttpClient();
    	mapper = new ObjectMapper();
    }
    
    // fetch cover page
    public Optional<String> fetchCoverPageUrl(String showName) {
        String encodedShowName = URLEncoder.encode(showName, StandardCharsets.UTF_8);
        String requestUrl = BASE_URL + "/search/tv?api_key=" + API_KEY + "&query=" + encodedShowName;
        
        Optional<JsonNode> jsonResponse = sendRequest(requestUrl);
        return jsonResponse.flatMap(json -> {
        	if (json.has("results") && json.get("results").size() > 0) {
        		JsonNode firstResult = json.path("results").get(0);
        		if (firstResult.hasNonNull("poster_path")) {
        			String posterPath = firstResult.path("poster_path").asText();
        			return Optional.of(IMAGE_BASE_URL + posterPath);
            }
        }
        return Optional.empty(); 
    });
        
    }
    
    // fetch review for mouseover
    public Optional<String> fetchReview(int showId){
    	String requestUrl = BASE_URL + "/tv/" + showId + "/reviews?api_key=" + API_KEY;
    	
    	// catch errors that are caused by things other than sendRequest
    	try {
            Optional<JsonNode> jsonResponse = sendRequest(requestUrl);
            
            if (jsonResponse.isPresent()) {
                JsonNode json = jsonResponse.get();
                if (json.has("results") && json.get("results").size() > 0) {
                    JsonNode firstReview = json.path("results").get(0);
                    if (firstReview.hasNonNull("content")) {
                        String reviewContent = firstReview.path("content").asText();
                        return Optional.of(reviewContent);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    // review needs show_id to avoid confusion 
    // fetch showId
    public Optional<Integer> fetchShowId(String showName) {
        String encodedShowName = URLEncoder.encode(showName, StandardCharsets.UTF_8);
        String searchUrl = BASE_URL + "/search/tv?api_key=" + API_KEY + "&query=" + encodedShowName;
        
        Optional<JsonNode> jsonResponse = sendRequest(searchUrl);
        
        if(jsonResponse.isPresent()) {
        	JsonNode root = jsonResponse.get();
            if (root.has("results") && root.get("results").size() > 0) {
                JsonNode firstResult = root.path("results").get(0);
                int showId = firstResult.path("id").asInt();
                return Optional.of(showId);
            }
        } 
        return Optional.empty();
    }

    // request sending process for each fetch type
	private Optional<JsonNode> sendRequest(String requestUrl){
		HttpRequest request = HttpRequest.newBuilder(URI.create(requestUrl)).build();
    
	    try {
	    	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	    	return Optional.of(mapper.readTree(response.body()));
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	return Optional.empty();
	    }
	} 
}
