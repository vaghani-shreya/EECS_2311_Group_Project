package testCases;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

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
        int updatedRating = ratingDAO.updateRatingdb(userRating, id);

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
    
}
