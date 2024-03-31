package front;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WatchHistory {
	
	//Initialize path to database
	static String path = "jdbc:sqlite:database/WatchHistory.db";
	/**
	 * This method is responsible for adding the show to the WatchHistory.db
	 * It is used when the user clicks on the "Mark as Watched" button
	 * It takes in the username, showId, and title of the movie as input and add it to the WatchedList table in the database
	 */
	public void addToWatchedList(String username, String showId, String title) {
		//Initialize Queries
		String selectQuery = "SELECT COUNT(*) FROM WatchedList WHERE username = ? AND showID = ? AND title = ?";
		String insertQuery = "INSERT INTO WatchedList (username, showId, title) VALUES (?, ?, ?)";

		//Connect to database
		try (Connection connection = DriverManager.getConnection(path);
				PreparedStatement selectStatement = connection.prepareStatement(selectQuery); 
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

			// Checks if the show/movie has been already added by the user
			selectStatement.setString(1, username);
			selectStatement.setString(2, showId);
			selectStatement.setString(3, title);
			ResultSet resultSet = selectStatement.executeQuery();
			resultSet.next();
			int count = resultSet.getInt(1);

			if (count == 0) {
				//Add the movie to the watched database if show doesn't exist in database
				insertStatement.setString(1, username);
				insertStatement.setString(2, showId);
				insertStatement.setString(3, title);
				insertStatement.executeUpdate();

			} else {
				//do nothing
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Delete the show from WatchHistory.DB when user clicks on the "Watched" button
	/**
	 * This method is responsible for deleting the show from the WatchHistory.db
	 * It is used when the user clicks on the "Watched" button
	 * It takes in the username, showId, and title of the movie as input and removes it from the WatchedList table in the database
	 */
	public void deleteShowFromWatchList(String username, String showId, String title) {
		//Initialize Query
		String deleteQuery = "DELETE FROM WatchedList WHERE username = ? AND showId = ? AND title = ?";

		try (Connection connection = DriverManager.getConnection(path);
				PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
			
			//Delete the row from WatchedList
			statement.setString(1, username);
			statement.setString(2, showId);
			statement.setString(3, title);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Check if Show is in WatchHistory Database
	/**
	 * This method is responsible for checking if the show is in the WatchHistory.db
	 * It is used when the user clicks on the show details of a specific movie/show
	 * It takes in the username, showId, and title of the movie as input and checks if it is in the WatchedList table in the database
	 */
	public boolean checkWatchList(String username, String showId, String title) {
		//Initialize query
		String query = "SELECT * FROM WatchedList WHERE username = ? AND showId = ? AND title = ?";

		try (Connection conn = DriverManager.getConnection(path);

				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, username);
			pstmt.setString(2, showId);
			pstmt.setString(3, title);

			ResultSet resultSet = pstmt.executeQuery();

			// If a row with the given movie exists, the result is true
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;		//If the row does not exist, the result is false
		}
	}

}
