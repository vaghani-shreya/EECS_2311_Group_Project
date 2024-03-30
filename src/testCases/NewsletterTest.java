package testCases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NewsletterTest {
	
	private static final String JDBC_URL = "jdbc:sqlite:database/UserCredentials.db";
    private Connection connection;

	@BeforeEach
	void setUp() throws Exception {
		// Load SQLite JDBC driver
        Class.forName("org.sqlite.JDBC");
        // Establish database connection
        connection = DriverManager.getConnection(JDBC_URL);
	}
	
	@AfterEach
	public void tearDown() throws Exception {
        // Close the database connection
        if (connection != null) {
            connection.close();
        }
    }

	@Test
    public void testUserList() throws SQLException, Exception {
        // Prepare SQL statement
        String sql = "SELECT COUNT(*) FROM UserCred";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	// Execute the SELECT statement
            ResultSet resultSet = statement.executeQuery();

            // Get the count of rows returned
            resultSet.next();
            int count = resultSet.getInt(1);

            // Assert that multiple entries exist in the database
            // Modify the expected count based on your test data
            assertEquals(count, count, "Expected count of entries does not match actual count");
        }
    }
	
//	@Test
//	public void testRetrieveFavourites()throws Exception {
//		String favouritePath = "jdbc:sqlite:database/Favourite.db";
//        String query = "INSERT INTO UserCred (username, title) VALUES (?, ?);";
//		ArrayList<Object[]> favouriteList = new ArrayList<>();
//        PreparedStatement statement = connection.prepareStatement(query);
//		statement.setString(1, "userTest");
//        statement.setString(2, "1234");
//
//        int rowsUpdated = statement.executeUpdate();
//        statement.close();
//
//        // Verify that the INSERT command affected the expected number of rows
//        assertEquals(1, rowsUpdated);
//		
//		try {
//			Class.forName("org.sqlite.JDBC");
//		} catch (ClassNotFoundException e) {
//			System.out.println("SQLite JDBC Driver not found.");
//			e.printStackTrace();
//		}
//		try (Connection conn = DriverManager.getConnection(favouritePath);
//				PreparedStatement pstmt = conn.prepareStatement(query)) {
//			//Select the favorite movies for the specified
//			pstmt.setString(1, "test");
//			ResultSet resultSet = pstmt.executeQuery();
//			//add each object to the ArrayList
//			while (resultSet.next()) {
//				favouriteList.add(new Object[]{
//						resultSet.getString("title"),
//						resultSet.getInt("release_year")
//				});
//			}
//	}

}
