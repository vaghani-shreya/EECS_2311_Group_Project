package testCases;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.*;
import java.sql.*;


class DatabaseTest {

	private static final String JDBC_URL = "jdbc:sqlite:database/UserCredentials.db";
    private Connection connection;
    

    public void setUp() throws Exception {
        // Load SQLite JDBC driver
        Class.forName("org.sqlite.JDBC");
        // Establish database connection
        connection = DriverManager.getConnection(JDBC_URL);
    }

    
    public void tearDown() throws Exception {
        // Close the database connection
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testAuthenticateUser() throws SQLException, Exception {
    	setUp();
        // Prepare SQL statement
        String sql = "SELECT * FROM UserCred WHERE username = ? AND password = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "user");
            statement.setString(2, "password");
         // Execute query
            ResultSet resultSet = statement.executeQuery();
            // Check if query returned results
            assertTrue(resultSet.next());
            // Check if the retrieved username matches
            assertEquals("user", resultSet.getString("username"));
            assertEquals("password", resultSet.getString("password"));
            statement.close();
        }
        tearDown();
    }
    
    @Test
    public void testCheckUser() throws SQLException, Exception {
    	setUp();
        // Prepare SQL statement
        String sql = "SELECT * FROM UserCred WHERE username = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "user");
         // Execute query
            ResultSet resultSet = statement.executeQuery();
            // Check if query returned results
            assertTrue(resultSet.next());
            // Check if the retrieved username matches
            assertEquals("user", resultSet.getString("username"));
            statement.close();
        }
        tearDown();
    }
    
    @Test
    public void testCheckCode() throws SQLException, Exception {
    	setUp();
        // Prepare SQL statement
        String sql = "SELECT * FROM UserCred WHERE username = ? AND code = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "admin");
            statement.setString(2, "11111");
         // Execute query
            ResultSet resultSet = statement.executeQuery();
            // Check if query returned results
            assertTrue(resultSet.next());
            // Check if the retrieved username matches
            assertEquals("admin", resultSet.getString("username"));
            assertEquals("11111", resultSet.getString("code"));
            statement.close();
        }
        tearDown();
    }
    
    @Test
    public void testRetrieveUserCredentials() throws SQLException, Exception {
    	setUp();
        // Prepare SQL statement
        String sql = "SELECT * FROM UserCred WHERE username = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "user");
         // Execute query
            ResultSet resultSet = statement.executeQuery();
            // Check if query returned results
            assertTrue(resultSet.next());
            // Check if the retrieved username matches
            assertEquals("user", resultSet.getString("username"));
            statement.close();
        }
        tearDown();
    }
    
    @Test
    public void testAssignCode() throws SQLException, Exception {
    	setUp();
    	// Execute the UPDATE command
        String updateSql = "UPDATE UserCred SET code = ? WHERE username = ?;";
        PreparedStatement statement = connection.prepareStatement(updateSql);
        statement.setString(1, "11111");
        statement.setString(2, "admin");
        int rowsUpdated = statement.executeUpdate();
        statement.close();

        // Verify that the UPDATE command affected the expected number of rows
        assertEquals(1, rowsUpdated);

        // Verify that the data in the database has been updated as expected
        String selectSql = "SELECT * FROM UserCred WHERE username = ?";
        statement = connection.prepareStatement(selectSql);
        statement.setString(1, "admin");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String updatedPassword = resultSet.getString("code");
            assertEquals("11111", updatedPassword);
        } else {
            // User not found, fail the test
            assertEquals("User not found", 1, 0);
        }
        statement.close();
        tearDown();
    }
    
    @Test
    public void testResetPassword() throws SQLException, Exception {
    	setUp();
    	// Execute the UPDATE command
        String updateSql = "UPDATE UserCred SET password = ? WHERE username = ?;";
        PreparedStatement statement = connection.prepareStatement(updateSql);
        statement.setString(1, "newpassword123");
        statement.setString(2, "admin");
        int rowsUpdated = statement.executeUpdate();
        statement.close();

        // Verify that the UPDATE command affected the expected number of rows
        assertEquals(1, rowsUpdated);

        // Verify that the data in the database has been updated as expected
        String selectSql = "SELECT * FROM UserCred WHERE username = ?";
        statement = connection.prepareStatement(selectSql);
        statement.setString(1, "admin");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String updatedPassword = resultSet.getString("password");
            assertEquals("newpassword123", updatedPassword);
        } else {
            // User not found, fail the test
            assertEquals("User not found", 1, 0);
        }
        statement.close();
        tearDown();
    }


}
