package front;

import java.sql.*;

public class LoginPageDb {
    private static final String URL = "jdbc:sqlite:C:/Users/elzig/Desktop/EECS 2311/EECS_2311_Group_Project/UserCredentials.db";

	public static Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL);
			System.out.println("Connected to the database.");
		} catch (SQLException e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
		}
		return conn;
	}

	public static void main(String[] args) {
		// Test the database connection
		Connection conn = connect();
		if (conn != null) {
			try {
				conn.close();
				System.out.println("Connection closed.");
			} catch (SQLException e) {
				System.out.println("Error closing the connection: " + e.getMessage());
			}
		}
	}
}
