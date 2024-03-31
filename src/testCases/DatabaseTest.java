package testCases;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DatabaseTest {

	private static final String JDBC_URL = "jdbc:sqlite:database/UserCredentials.db";
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
    public void testInsertUserCredentials() throws SQLException, Exception {
        // Prepare SQL statement
        String insertSql = "INSERT INTO UserCred (username, password) VALUES (?, ?);";
        PreparedStatement statement = connection.prepareStatement(insertSql);
        statement.setString(1, "userTest");
        statement.setString(2, "1234");

        int rowsUpdated = statement.executeUpdate();
        statement.close();

        // Verify that the INSERT command affected the expected number of rows
        assertEquals(1, rowsUpdated);

        // Check if the user is added to the database
        String selectSql = "SELECT * FROM UserCred WHERE username = ?";
        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        selectStatement.setString(1, "userTest");
        ResultSet resultSet = selectStatement.executeQuery();

        // Assert that the user exists
        assertTrue(resultSet.next());

        // Clean up
        resultSet.close();
        selectStatement.close();
    }
    
    @Test
    @Order(2)
    public void testAuthenticateUser() throws SQLException, Exception {
        // Prepare SQL statement
        String sql = "SELECT * FROM UserCred WHERE username = ? AND password = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "userTest");
            statement.setString(2, "1234");
            // Execute query
            ResultSet resultSet = statement.executeQuery();
            // Check if query returned results
            assertTrue(resultSet.next());
            // Check if the retrieved username matches
            assertEquals("userTest", resultSet.getString("username"));
            assertEquals("1234", resultSet.getString("password"));
            statement.close();
        }
    }
    
    @Test
    @Order(3)
    public void testCheckUser() throws SQLException, Exception {
        // Prepare SQL statement
        String sql = "SELECT * FROM UserCred WHERE username = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "userTest");
         // Execute query
            ResultSet resultSet = statement.executeQuery();
            // Check if query returned results
            assertTrue(resultSet.next());
            // Check if the retrieved username matches
            assertEquals("userTest", resultSet.getString("username"));
            statement.close();
        }
    }
    
    @Test
    @Order(4)
    public void testAssignCode() throws SQLException, Exception {
    	// Execute the UPDATE command
        String updateSql = "UPDATE UserCred SET code = ? WHERE username = ?;";
        PreparedStatement statement = connection.prepareStatement(updateSql);
        statement.setString(1, "11111");
        statement.setString(2, "userTest");
        int rowsUpdated = statement.executeUpdate();
        statement.close();

        // Verify that the UPDATE command affected the expected number of rows
        assertEquals(1, rowsUpdated);

        // Verify that the data in the database has been updated as expected
        String selectSql = "SELECT * FROM UserCred WHERE username = ?";
        statement = connection.prepareStatement(selectSql);
        statement.setString(1, "userTest");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String updatedPassword = resultSet.getString("code");
            assertEquals("11111", updatedPassword);
        } else {
            // User not found, fail the test
            assertEquals("User not found", 1, 0);
        }
        statement.close();
    }
    
    @Test
    @Order(5)
    public void testCheckCode() throws SQLException, Exception {
        // Prepare SQL statement
        String sql = "SELECT * FROM UserCred WHERE username = ? AND code = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "userTest");
            statement.setString(2, "11111");
         // Execute query
            ResultSet resultSet = statement.executeQuery();
            // Check if query returned results
            assertTrue(resultSet.next());
            // Check if the retrieved username matches
            assertEquals("userTest", resultSet.getString("username"));
            assertEquals("11111", resultSet.getString("code"));
            statement.close();
        }
    }
    
    @Test
    @Order(6)
    public void testRetrieveUserCredentials() throws SQLException, Exception {
        // Prepare SQL statement
        String sql = "SELECT * FROM UserCred WHERE username = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "userTest");
         // Execute query
            ResultSet resultSet = statement.executeQuery();
            // Check if query returned results
            assertTrue(resultSet.next());
            // Check if the retrieved username matches
            assertEquals("userTest", resultSet.getString("username"));
            statement.close();
        }
    }
    
    @Test
    @Order(7)
    public void testResetPassword() throws SQLException, Exception {
    	// Execute the UPDATE command
        String updateSql = "UPDATE UserCred SET password = ? WHERE username = ?;";
        PreparedStatement statement = connection.prepareStatement(updateSql);
        statement.setString(1, "newpassword123");
        statement.setString(2, "userTest");
        int rowsUpdated = statement.executeUpdate();
        statement.close();

        // Verify that the UPDATE command affected the expected number of rows
        assertEquals(1, rowsUpdated);

        // Verify that the data in the database has been updated as expected
        String selectSql = "SELECT * FROM UserCred WHERE username = ?";
        statement = connection.prepareStatement(selectSql);
        statement.setString(1, "userTest");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String updatedPassword = resultSet.getString("password");
            assertEquals("newpassword123", updatedPassword);
        } else {
            // User not found, fail the test
            assertEquals("User not found", 1, 0);
        }
        statement.close();
    }
    
    //Added this test to delete the test entry and check if it was deleted
    @Test
    @Order(8)
	public void deleteTestEntry() throws SQLException, Exception {
		String deleteQuery = "DELETE FROM UserCred WHERE username = ? AND password = ?";
		PreparedStatement statement = connection.prepareStatement(deleteQuery);
		statement.setString(1, "userTest");
		statement.setString(2, "newpassword123");
		int rowsAffected = statement.executeUpdate();

		// Assert that one row was affected (deleted)
		assertEquals(1, rowsAffected, "Expected one row to be deleted");
		statement.close();

		//Check to see movie is no longer in database
		String selectSql = "SELECT COUNT(*) FROM UserCred WHERE username = ? AND password = ?";
		statement = connection.prepareStatement(selectSql);
		statement.setString(1, "userTest");
		statement.setString(2, "newpassword123");
		ResultSet resultSet = statement.executeQuery();
		// Get the count of rows returned
		resultSet.next();
		int count = resultSet.getInt(1);
		// Assert that no rows are returned
		assertEquals(0, count, "Entry is present in the database");
		statement.close();
	}

}
