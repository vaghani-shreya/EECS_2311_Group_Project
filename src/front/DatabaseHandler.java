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
    public boolean checkUser(String username) {
        String path = "jdbc:sqlite:database/UserCredentials.db";
        String query = "SELECT * FROM UserCred WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);

            ResultSet resultSet = pstmt.executeQuery();

            // If a row with the given username and password exists, the user is valid
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkCode(String username, int code) {
        String path = "jdbc:sqlite:database/UserCredentials.db";
        String query = "SELECT * FROM UserCred WHERE username = ? AND code = ?;";

        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	pstmt.setString(1, username);
            pstmt.setInt(2, code);
            
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

    public void assignCode(String username, int verCode) {
        String path = "jdbc:sqlite:database/UserCredentials.db";
        String query = "UPDATE UserCred SET code = ? WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, verCode);
            pstmt.setString(2, username);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void resetPassword(String username, String password) {
        String path = "jdbc:sqlite:database/UserCredentials.db";
        String query = "UPDATE UserCred SET password = ? WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, password);
            pstmt.setString(2, username);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
//    public Object[][] deleteFavourites(String filter) {
//    	
//    	Object[][] movies = null;
//    	
//    	String path = "jdbc:sqlite:database/Favourites.db";
//    	String query = "DELETE FROM FavouriteMovies WHERE Name = " + filter + ";";
//    	
//        try {
//            Class.forName("org.sqlite.JDBC");
//            Connection conn = DriverManager.getConnection(path);
//            PreparedStatement pstmt = conn.prepareStatement(query);
//
//            ResultSet resultSet = pstmt.executeQuery();
//            
//            movies = new Object[10][6];
//            String categories[] = {"Name", "Length", "Genre", "DateAdded", "Rating", "ReleaseDate"};
//            
//            int i = 0;
//            while (resultSet.next()) {
//            	
//            	String name = resultSet.getString("Name");
//            	double length = resultSet.getDouble("Length");
//            	String genre = resultSet.getString("Genre");
//            	Date da = resultSet.getDate("DateAdded");
//            	String rating = resultSet.getString("Rating");
//            	Date rd = resultSet.getDate("ReleaseDate");
//            	
//            	movies[i][0] = name;
//            	movies[i][1] = length;
//            	movies[i][2] = genre;
//            	movies[i][3] = da;
//            	movies[i][4] = rating;
//            	movies[i][5] = rd;
//            	
//            	i++;
//            	
////            	System.out.println(movie_name);
////                String movie_name = resultSet.getString("username");
////                String password = resultSet.getString("password");
//
//            //    System.out.println("Username: " + username + ", Password: " + password);
//                // You can add more details here if needed
//            }
//           
//        } 
//        catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        
//		return movies;
//    }

}