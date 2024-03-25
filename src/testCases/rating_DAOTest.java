package testCases;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import rating.rating_DAO;

class rating_DAOTest {

	private rating_DAO ratingDAO;

    @Before
    public void setUp() {
        ratingDAO = new rating_DAO();
    }
    
    @Test
    public void testUpdateRatingdb() {
        // Test update rating for a show
    	 ratingDAO = new rating_DAO();
        int userRating = 8;
        String id = "s4";
        String platform = "netflix";
        int updatedRating = ratingDAO.updateRatingdb(userRating, id, platform);

        // Assert that the rating is updated successfully
        assertEquals("Rating should be updated to 8", userRating, updatedRating);
    }
    
    
    
    @Test
    public void testInsertIntoUserMediadb() {
        // Test inserting user rating for a show
    	ratingDAO = new rating_DAO();
        String userName = "John";
        String showid = "s1";
        String title = "himym";
        String releaseYear = "2000";
        String rating = "TV-18";
        int numRating = 6;
        int insertedNumRating = ratingDAO.insertIntoUserMediadb(userName, showid, title, releaseYear, rating, numRating);

        // Assert that the rating is inserted successfully
        assertEquals("NumRating should be inserted as 6", numRating, insertedNumRating);
    }
    
    @Test
    public void testInsertIntoUserMediadbIllegalArgumentException() {
        // Test inserting user rating with numRating outside the range
        String userName = "user";
        String showid = "s1";
        String title = "himym";
        String releaseYear = "2000";
        String rating = "TV-18";
        int numRating = 15; // Value outside the range
       ;
        assertThrows(NullPointerException.class, () ->{  ratingDAO.insertIntoUserMediadb(userName, showid, title, releaseYear, rating, numRating);
    	});
    }
    
    @Test
    public void testSaveCommentToUserDB() {
    	
    	ratingDAO = new rating_DAO();
    	String username = "Test User";
    	String showid = "12345";
    	String title = "Test Title";
    	String releaseYear = "0000";
    	String rating = "Test Rating";
    	int numrating = 3;
    	
    	ratingDAO.insertIntoUserMediadb(username, showid, title, releaseYear, rating, numrating);
    	
    	String comment = "Check FavouritesTest then RecommendationsTest";
    	
    	ratingDAO.saveCommentToUserDB(comment, username, title);
    	
    	String path = "jdbc:sqlite:database/userDetails.db";
		String query = "SELECT * FROM userMedia WHERE"
						+ " userComments = " + comment
						+ " AND name = " + username
						+ " AND title = " + title;
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(path);
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) {
				
				assertEquals(comment, resultSet.getString("userComments"));
				assertEquals(username, resultSet.getString("name"));
				assertEquals(title, resultSet.getString("title"));
				
			}
			
			conn.close();
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    }
    
}
