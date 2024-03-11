package Favourites;

import java.sql.*;

public class FavouritesPageDatabaseHandler {
	
	String path = "jdbc:sqlite:database/Favourites.db";
	
	 public Object[][] retrieveFavouritesList(String filter) {
	    	
	    	Object[][] movies = new Object[10][6];
	    	
	    	String query = "SELECT * FROM FavouriteMovies ORDER BY " + filter + ";";
	    	
	        try {
	            Class.forName("org.sqlite.JDBC");
	            Connection conn = DriverManager.getConnection(path);
	            PreparedStatement pstmt = conn.prepareStatement(query);

	            ResultSet resultSet = pstmt.executeQuery();
	            
//	            String categories[] = {"Name", "Length", "Genre", "DateAdded", "Rating", "ReleaseDate"};
	            
	            int i = 1;
	            while (resultSet.next()) {
	            	
	            	String name = resultSet.getString("Name");
	            	double length = resultSet.getDouble("Length");
	            	String genre = resultSet.getString("Genre");
	            	Date da = resultSet.getDate("DateAdded");
	            	String rating = resultSet.getString("Rating");
	            	Date rd = resultSet.getDate("ReleaseDate");
	            	
	            	movies[0][0] = "Name";
	            	movies[0][1] = "Length";
	            	movies[0][2] = "Genre";
	            	movies[0][3] = "Date Added";
	            	movies[0][4] = "Rating";
	            	movies[0][5] = "Release Date";
	            	
	            	movies[i][0] = name;
	            	movies[i][1] = length;
	            	movies[i][2] = genre;
	            	movies[i][3] = da;
	            	movies[i][4] = rating;
	            	movies[i][5] = rd;
	            	
	            	i++;
	            	
	            }
	            
	            resultSet.close();
	            pstmt.close();
	            conn.close();
	        
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
	    public Object[][] refreshTable(String filter) {
	    	
	    	Object[][] movies = new Object[10][6];
	    	
	    	String query = "SELECT * FROM FavouriteMovies ORDER BY " + filter + ";";
	    	
	    	try {
	    		
	    		Class.forName("org.sqlite.JDBC");
	            Connection conn = DriverManager.getConnection(path);
	            PreparedStatement pstmt = conn.prepareStatement(query);

	            ResultSet resultSet = pstmt.executeQuery();
	            
	           FavouritesPage.favouritesTableModel.setRowCount(0);
	           
	           while (resultSet.next()) {
	        	
	        	   Object[] rowData = {
	        			   resultSet.getString("Name"),
	        			   resultSet.getDouble("Length"),
	        			   resultSet.getString("Genre"),
	        			   resultSet.getDate("DateAdded"),
	        			   resultSet.getString("Rating"),
	        			   resultSet.getDate("ReleaseDate")
	        	   };
	        	   
	        	   FavouritesPage.favouritesTableModel.addRow(rowData);
	           }
	           
	            resultSet.close();
	            pstmt.close();
	            conn.close();
	            
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        }   

	    	return movies;
	}
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
