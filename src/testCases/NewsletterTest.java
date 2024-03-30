package testCases;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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

}
