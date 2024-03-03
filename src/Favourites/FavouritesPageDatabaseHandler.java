package Favourites;

import java.sql.*;

public class FavouritesPageDatabaseHandler {
	
	 public Object[][] retrieveFavouritesList(String filter) {
	    	
	    	Object[][] movies = new Object[10][6];
	    	
	    	String path = "jdbc:sqlite:database/Favourites.db";
	    	String query = "SELECT * FROM FavouriteMovies ORDER BY " + filter + ";";
	    	
	        try {
	            Class.forName("org.sqlite.JDBC");
	            Connection conn = DriverManager.getConnection(path);
	            PreparedStatement pstmt = conn.prepareStatement(query);

	            ResultSet resultSet = pstmt.executeQuery();
	            
	            String categories[] = {"Name", "Length", "Genre", "DateAdded", "Rating", "ReleaseDate"};
	            
	            int i = 0;
	            while (resultSet.next()) {
	            	
	            	String name = resultSet.getString("Name");
	            	double length = resultSet.getDouble("Length");
	            	String genre = resultSet.getString("Genre");
	            	Date da = resultSet.getDate("DateAdded");
	            	String rating = resultSet.getString("Rating");
	            	Date rd = resultSet.getDate("ReleaseDate");
	            	
	            	movies[i][0] = name;
	            	movies[i][1] = length;
	            	movies[i][2] = genre;
	            	movies[i][3] = da;
	            	movies[i][4] = rating;
	            	movies[i][5] = rd;
	            	
	            	i++;
	            	
	            }
	           
	        } 
	        catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        
			return movies;
	    }
	
//  public Object[][] deleteFavourites(String filter) {
//	
//	Object[][] movies = null;
//	
//	String path = "jdbc:sqlite:database/Favourites.db";
//	String query = "DELETE FROM FavouriteMovies WHERE Name = " + filter + ";";
//	
//    try {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(path);
//        PreparedStatement pstmt = conn.prepareStatement(query);
//
//        ResultSet resultSet = pstmt.executeQuery();
//       
//        }
//       
//    } 
//    catch (SQLException | ClassNotFoundException e) {
//        e.printStackTrace();
//    }
//    
//	return movies;
//}
	 
//	    // Example of a simple JDBC refresh function
//	    private static void performJDBCRefresh() {
//	        try {
//	            // Load the JDBC driver
//	            Class.forName("com.mysql.cj.jdbc.Driver");
//
//	            // Connect to the database
//	            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/exampledb", "username", "password");
//
//	            // Execute a query to fetch data
//	            String query = "SELECT * FROM exampletable";
//	            PreparedStatement statement = connection.prepareStatement(query);
//	            ResultSet resultSet = statement.executeQuery();
//
//	            // Clear the existing data in the table model
//	            tableModel.setRowCount(0);
//
//	            // Populate the table model with new data from the result set
//	            while (resultSet.next()) {
//	                Object[] rowData = {
//	                        resultSet.getString("column1"),
//	                        resultSet.getInt("column2"),
//	                        resultSet.getString("column3")
//	                        // Add more columns as needed
//	                };
//	                tableModel.addRow(rowData);
//	            }
//
//	            // Close resources
//	            resultSet.close();
//	            statement.close();
//	            connection.close();
//	        } catch (ClassNotFoundException | SQLException e) {
//	            e.printStackTrace();
//	        }
//	    }
//	}
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
