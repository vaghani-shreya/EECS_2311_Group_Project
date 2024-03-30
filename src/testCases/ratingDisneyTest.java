package testCases;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import rating.ratingDisney;
import rating.rating_DAO;

class ratingDisneyTest {

	@Test
	void testLoadFromDatabase() {
		
		ratingDisney ratingDisney = new ratingDisney();
		ratingDisney.loadDataFromDatabase();
		
		String path = "jdbc:sqlite:database/Disney.db";
		String query = "SELECT * FROM disney_plus_titles ORDER BY release_year DESC LIMIT 10;"; 
		
		try {
			
			Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(path);
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();
            
            while (resultSet.next()) {
                
            	assertNotNull(resultSet.getString("show_id"));
            	assertNotNull(resultSet.getString("title"));
            	assertNotNull(resultSet.getString("date_added"));
            	assertNotNull(resultSet.getString("release_year"));
            	assertNotNull(resultSet.getString("description"));
            	assertNotNull(resultSet.getString("rating"));
            	assertNotNull(resultSet.getString("avgRating"));
            	
            }
            
            conn.close();
			
		} catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
		
	}
	
	@Test
	void testRateMedia() {
		
		String show = "Test Show";
		String title = "Test Title";
		String date = "Test Date";
		String year = "Test Year";
		String description = "Test Description";
		String ratingNAN = "Test RatingNAN";
		String avgRating = "Test AVGRating";
		
		ratingDisney ratingDisney = new ratingDisney();
		ratingDisney.rateMedia(show, title, date, year, description, ratingNAN, avgRating);
		
		rating_DAO ratingDAO = new rating_DAO();
		
		ratingDAO.updateRatingdb(0, "12345", "netflix");
		ratingDAO.insertIntoUserMediadb("Test User", "12345", title, year, avgRating, 0);
		
		String path = "jdbc:sqlite:database/Disney.db";
		String query = "UPDATE disney_plus_titles SET NumRatings = 0 WHERE show_id = 12345;";
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(path);
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery(); 
			
			while (resultSet.next()) {
				
				assertEquals(0, resultSet.getString("NumRating"));
				assertEquals("12345", resultSet.getString("show_id"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	void testSearchFor() {
		
		String path = "jdbc:sqlite:database/Disney.db";
		String query = "SELECT * FROM disney_plus_titles WHERE title LIKE ?;";
		
		// Change this title to a title from the Disney database
		String expected = "My Little Pony: A New Generation";
		
		ratingDisney ratingDisney = new ratingDisney();
		ratingDisney.searchDatabase("My Little Pony: A New Generation");
		
		 try {
	            Class.forName("org.sqlite.JDBC");
	            Connection conn = DriverManager.getConnection(path);
	            PreparedStatement pstmt = conn.prepareStatement(query);
	            pstmt.setString(1, "%" + expected + "%");
	            ResultSet resultSet = pstmt.executeQuery();
	            
	            while (resultSet.next()) {
	            	assertEquals(expected, resultSet.getString("title"));	
	            }
	            conn.close();
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
		
	}

}
