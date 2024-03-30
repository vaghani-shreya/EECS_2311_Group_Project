package testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Order;
import java.sql.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WatchHistoryTest {

	private static final String JDBC_URL = "jdbc:sqlite:database/WatchHistory.db";
	private Connection connection;

	@BeforeEach
	public void setUp() throws Exception {
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
	@Order(1)
	public void testAddToWatchedList() throws SQLException, Exception {
		String selectQuery = "SELECT COUNT(*) FROM WatchedList WHERE username = ? AND showID = ? AND title = ?";
		String insertQuery = "INSERT INTO WatchedList (username, showId, title) VALUES (?, ?, ?)";
		PreparedStatement selectStatement = connection.prepareStatement(selectQuery); 
		PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

		// Checks if the show/movie has been already added by the user
		selectStatement.setString(1, "test");
		selectStatement.setString(2, "12");
		selectStatement.setString(3, "testMovie");
		ResultSet resultSet = selectStatement.executeQuery();

		// Get the count of rows returned
		resultSet.next();
		int count = resultSet.getInt(1);

		// Assert that no rows are returned
		assertEquals(0, count, "Entry is present in the database");
		selectStatement.close();

		//Add the movie to the watched database if show doesn't exist in database
		insertStatement.setString(1, "test");
		insertStatement.setString(2, "12");
		insertStatement.setString(3, "testMovie");
		int rowsUpdated = insertStatement.executeUpdate();
		insertStatement.close();

		// Verify that the UPDATE command affected the expected number of rows
		assertEquals(1, rowsUpdated);
		resultSet.close();

		//Check if the movie was added into the database
		String selectSql = "SELECT * FROM WatchedList WHERE username = ? AND showID = ? AND title = ?";
		selectStatement = connection.prepareStatement(selectSql);
		selectStatement.setString(1, "test");
		selectStatement.setString(2, "12");
		selectStatement.setString(3, "testMovie");
		ResultSet resultSet2 = selectStatement.executeQuery();
		if (resultSet2.next()) {
			String user = resultSet2.getString("username");
			assertEquals("test", user);
			String newshowId = resultSet2.getString("showId");
			assertEquals("12", newshowId);
			String newTitle = resultSet2.getString("title");
			assertEquals("testMovie", newTitle);
		} else {
			// User not found, fail the test
			assertEquals("Entry not found", 1, 0);
		}
		resultSet2.close();
		selectStatement.close();

	}

	@Test
	@Order(2)
	public void testCheckWatchList() throws SQLException, Exception {
		String query = "SELECT * FROM WatchedList WHERE username = ? AND showID = ? AND title = ?;";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, "test");
			pstmt.setString(2, "12");
			pstmt.setString(3, "testMovie");
			ResultSet resultSet = pstmt.executeQuery();

			assertTrue(resultSet.next());
			assertEquals("test", resultSet.getString("username"));
			assertEquals("12", resultSet.getString("showId"));
			assertEquals("testMovie", resultSet.getString("title"));
			pstmt.close();

		}
	}

	@Test
	@Order(3)
	public void testDeleteShowFromWatchList() throws SQLException, Exception {
		String deleteQuery = "DELETE FROM WatchedList WHERE username = ? AND showId = ? AND title = ?";
		PreparedStatement statement = connection.prepareStatement(deleteQuery);
		statement.setString(1, "test");
		statement.setString(2, "12");
		statement.setString(3, "testMovie");
		int rowsAffected = statement.executeUpdate();
		statement.close();

		// Assert that one row was affected (deleted)
		assertEquals(1, rowsAffected, "Expected one row to be deleted");

		//Check to see movie is no longer in database
		String selectSql = "SELECT COUNT(*) FROM WatchedList WHERE username = ? AND showId = ? AND title = ?";
		statement = connection.prepareStatement(selectSql);
		statement.setString(1, "test");
		statement.setString(2, "12");
		statement.setString(3, "testMovie");
		ResultSet resultSet = statement.executeQuery();
		// Get the count of rows returned
		resultSet.next();
		int count = resultSet.getInt(1);
		// Assert that no rows are returned
		assertEquals(0, count, "Entry is present in the database");
		statement.close();
	}


}
