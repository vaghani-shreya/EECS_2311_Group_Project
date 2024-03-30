package front;

//Extending the BrowsingDashboard 
public class Amazon extends BrowsingDashboard {
	private static Amazon instance;

	private Amazon() {
		initComponents(); // Initialize components
        Database(); // Load data from the database
	}
	 // Ensuring a single instance is created
	public static Amazon getInstance() {
		if (instance == null)
			instance = new Amazon();
		return instance;
	}
	// Allows the user to see the shows in descending order of release year
	 @Override
	    protected void Database() {
		String path = "jdbc:sqlite:database/Amazon.db";
		// extract data from Amazon database by descending order in terms of release year
		String query = "SELECT * FROM amazon_prime_titles ORDER BY release_year DESC LIMIT 10;";
		databaseHelper(path, query);
	}
	 
	 
	// Allows the user to search for a specific show/movie
	 @Override
	protected void searchDatabase(String searchFor, String filterBy) {
		showPanel.removeAll(); // Clear existing shows/movies

		String path = "jdbc:sqlite:database/Amazon.db";
		//Finds the specified title and extracts from database
		String query;

		switch(filterBy.toLowerCase()) {
		case "title":
			query = "SELECT * FROM amazon_prime_titles WHERE title LIKE ? LIMIT 10;";
			break;
		case "genre":
			query = "SELECT * FROM amazon_prime_titles WHERE listed_in LIKE ? LIMIT 10;";
			break;
		case "type":
			query = "SELECT * FROM amazon_prime_titles WHERE type LIKE ? LIMIT 10;";
			break;
		case "rating":
			query = "SELECT * FROM amazon_prime_titles WHERE rating LIKE ? LIMIT 10;";
			break;
		default:
			query = "SELECT * FROM amazon_prime_titles WHERE title LIKE ?;";
			break;
		}
		
		searchDBHelper(path,query,searchFor);
	}

	 
	 
	// Allows the user to sort show/movie by criteria
	 @Override
	protected void sortDatabase(String sortBy) {
		String path = "jdbc:sqlite:database/Amazon.db";
		String query;

		switch (sortBy.toLowerCase()) {
		case "title":
			query = "SELECT * FROM amazon_prime_titles ORDER BY title DESC LIMIT 10;";
			break;
		case "release date":
			query = "SELECT * FROM amazon_prime_titles ORDER BY release_year DESC LIMIT 10;";
			break;
		case "date added":
			query = "SELECT * FROM amazon_prime_titles ORDER BY date_added DESC LIMIT 10;";
			break;
		default:
			query = "SELECT * FROM amazon_prime_titles ORDER BY release_year DESC LIMIT 10;";
			break;
		}		
		sortDBHelper(path,query);	
	}
}

