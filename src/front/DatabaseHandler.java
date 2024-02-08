package front;

import java.sql.*;

public class DatabaseHandler {
    public boolean authenticateUser(String username, String password) {
        String path = "jdbc:sqlite:database/UserCredentials.db";
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
            return false;
        }
    }
    public void retrieveUserCredentials() {
        String path = "jdbc:sqlite:database/UserCredentials.db";
        String query = "SELECT * FROM UserCred;";

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(path);
            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

            //    System.out.println("Username: " + username + ", Password: " + password);
                // You can add more details here if needed
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}