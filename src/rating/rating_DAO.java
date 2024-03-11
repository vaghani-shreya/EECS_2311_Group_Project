package rating;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class rating_DAO {
	public int userRating;
	public int updaterate;
	
	
	public rating_DAO() {
		
	}
	
	
	 public int updateRatingdb(int userRating, String id) {
		 updaterate = userRating;
		  
		  String path = "jdbc:sqlite:database/Netflix.db";
	        //Finds the specified title and extracts from database
	        String query = "UPDATE netflix_titles SET NumRatings = ? WHERE show_id = ?;";
	        
	        try {
				Class.forName("org.sqlite.JDBC");
				Connection conn = DriverManager.getConnection(path);
				PreparedStatement pstmt = conn.prepareStatement(query);

				pstmt.setInt(1, userRating);
				pstmt.setString(2,id);
			
				int rowsAffected = pstmt.executeUpdate();		

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	        
	        
	        return updaterate;
		  
		  
	  }
	 
	 // rating is the general TV rating such as "TV-18", "TV-MA".
	 //numRating is the numerical rating by the user.
	 
	 public int insertIntoUserMediadb(String userName,String showid, String title, String releaseYear, String rating, int numRating) {
		 int numRate = -1;
		 int rowsAffected = 0;
		 userRating = numRating;
		
		 
		 String path = "jdbc:sqlite:database/userDetails.db";
		 String query1 = "INSERT INTO userMedia (name, showid, title, releaseYear, rating, numRating) VALUES (?, ?, ?, ?, ?, ?)";
		 String query2 = "SELECT numRating FROM userMedia WHERE title = ?;";
		 String query3 = "UPDATE userMedia SET NumRating = ? WHERE title = ?;";
		 
		  try {
			  
			  if(userRating > 10 || userRating < 0) {
				  throw new IllegalArgumentException();
			  }
				Class.forName("org.sqlite.JDBC");
				Connection conn = DriverManager.getConnection(path);
				PreparedStatement pstmt1 = conn.prepareStatement(query1);
				PreparedStatement pstmt2 = conn.prepareStatement(query2);
				PreparedStatement pstmt3 = conn.prepareStatement(query3);
				
				
				
				
				pstmt1.setString(1,userName);
				pstmt1.setString(2,showid);
				pstmt1.setString(3,title);
				pstmt1.setString(4,releaseYear);
				pstmt1.setString(5,rating);
				pstmt1.setInt(6, numRating);
				
				pstmt2.setString(1, title);
				
				pstmt3.setInt(1, numRating);
				pstmt3.setString(2, title);
				
				ResultSet resultSet = pstmt2.executeQuery();
									
				while (resultSet.next()) {
					 numRate = resultSet.getInt("numRating");
				}				
				 
				if(numRate == -1) {
					 rowsAffected = pstmt1.executeUpdate();
					
				}
				else {
					 rowsAffected = pstmt3.executeUpdate();
					
				}
					
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}       			
		 		 
		  return userRating;
	 }

	 
//	 public static void main(String[] args) {
////		 rating_DAO r = new rating_DAO();
////		// r.updateRatingdb(2, "s100");
////		 
////	 }
//	 rating_DAO r = new rating_DAO();
//	 r.insertIntoUserMediadb("user", "s1", "himym", "2000", "TV-18", 6);
//	 
//	 }
}


