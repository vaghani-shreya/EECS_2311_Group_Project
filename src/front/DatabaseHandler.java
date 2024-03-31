package front;

import java.sql.*;

public class DatabaseHandler {
	static String path = "jdbc:sqlite:database/UserCredentials.db";

	/**
	 * This method is responsible to authenticating the user's credentials
	 * It is used when the user login 
	 * It takes in the username and password as input and confirms the data with the UserCredential database
	 */
	public boolean authenticateUser(String username, String password) {
		String query = "SELECT * FROM UserCred WHERE username = ? AND password = ?;";

		try (Connection conn = DriverManager.getConnection(path);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet resultSet = pstmt.executeQuery();

			// If a row with the given username and password exists, the user is valid
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;	// return false, if the username is invalid
		}
	}

	/**
	 * This method is responsible for registering new user's credentials
	 * It is used when the user registers 
	 * It takes in the username and password as input and inserts the new user's credentials
	 */
	public boolean insertUserCredentials (String username, String password) {
		String query = "INSERT INTO UserCred (username, password) VALUES (?, ?);";

		try (Connection conn = DriverManager.getConnection(path);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			// Valid user registration - database changed, returns true
			int rowsAffected = pstmt.executeUpdate();	
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;	// Invalid user registration 
		}
	}

	/**
	 * This method is responsible to confirm that the username exits 
	 * It is used when the user registers and when they use reset password feature
	 * Used when register to confirm no duplicate username can be created
	 * Used in reset password feature to check if the username exists in the database 
	 * It takes in the username as input and confirms the data with the UserCredential database 
	 */
	public boolean checkUser(String username) {
		String query = "SELECT * FROM UserCred WHERE username = ?;";

		try (Connection conn = DriverManager.getConnection(path);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, username);

			ResultSet resultSet = pstmt.executeQuery();

			return resultSet.next();	// if username exists, returns true
		} catch (SQLException e) {
			e.printStackTrace();
			return false;				// Username doesn't exists
		}
	}

	/**
	 * This method is responsible to confirm that the authentication code sent to the user's email 
	 * matches the one in the database.
	 * It is used by the reset password feature
	 * It takes in the username and code as input and confirms the data matches with the UserCredential database 
	 */
	public boolean checkCode(String username, int code) {
		String query = "SELECT * FROM UserCred WHERE username = ? AND code = ?;";

		try (Connection conn = DriverManager.getConnection(path);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, username);
			pstmt.setInt(2, code);

			ResultSet resultSet = pstmt.executeQuery();

			// If a row with the given username and authentication code matches withthe database, password can be reset
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;	// username or code is incorrrect, password can not be reset
		}
	}
	
	/**
	 * This method is responsible to assiging an authentication code sent to the user's email 
	 * It is used by the reset password feature
	 * It updates the verification code for the user in the database
	 */
	public void assignCode(String username, int verCode) {
		String query = "UPDATE UserCred SET code = ? WHERE username = ?;";

		try (Connection conn = DriverManager.getConnection(path);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			//Update the new code into database
			pstmt.setInt(1, verCode);
			pstmt.setString(2, username);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is responsible for reseting the password for a user
	 * It is used by the reset password feature
	 * it update the user's password field in the database
	 */
	public void resetPassword(String username, String password) {
		String query = "UPDATE UserCred SET password = ? WHERE username = ?;";
		try (Connection conn = DriverManager.getConnection(path);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			//Update the new password into database
			pstmt.setString(1, password);
			pstmt.setString(2, username);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is responsible for retrieving the user's credentials (usrname and password)
	 * It is used by the login, register, and reset password features
	 */
	public void retrieveUserCredentials() {
		String query = "SELECT * FROM UserCred;";
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(path);
			PreparedStatement pstmt = conn.prepareStatement(query);

			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}