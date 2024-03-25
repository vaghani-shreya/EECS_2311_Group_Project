package front;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class WatchHistory {
		//Adds the the show to WatchHistory.db when user clicks on the "Mark as Watched" button
		public void addToWatchedList(String username, String showId, String title) {
		    
		    String path = "jdbc:sqlite:database/WatchHistory.db";
		    String selectQuery = "SELECT COUNT(*) FROM WatchedList WHERE username = ? AND showID = ? AND title = ?";
		    String insertQuery = "INSERT INTO WatchedList (username, showId, title) VALUES (?, ?, ?)";

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
		public void deleteShowFromWatchList(String username, String showId, String title) {
			 String path = "jdbc:sqlite:database/WatchHistory.db";
			 String deleteQuery = "DELETE FROM WatchedList WHERE username = ? AND showId = ? AND title = ?";

			 try (Connection connection = DriverManager.getConnection(path);
			      PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

			      statement.setString(1, username);
			      statement.setString(2, showId);
			      statement.setString(3, title);
			      statement.executeUpdate();
//			      int rowsDeleted = statement.executeUpdate();
//			      if (rowsDeleted > 0) {
//			          JOptionPane.showMessageDialog(null, "Show deleted successfully!", "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
//			      } else {
//			          JOptionPane.showMessageDialog(null, "Failed to delete show.", "Deletion Failed", JOptionPane.ERROR_MESSAGE);
//			      }

			  } catch (SQLException e) {
			      e.printStackTrace();
			  }
	 
		}
		
		//Check if Show is in WatchHistory Database
		public boolean checkWatchList(String username, String showId, String title) {
			String path = "jdbc:sqlite:database/WatchHistory.db";
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
				return false;
			}
		}

}
