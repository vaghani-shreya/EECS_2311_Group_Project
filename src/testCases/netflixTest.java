package testCases;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class netflixTest {
	
	//Testing a show that exists in the database
	@Test
	void test1() {
		 String path = "jdbc:sqlite:database/Netflix.db";
	     String query = "SELECT * FROM netflix_titles WHERE title LIKE ?;";
	     String expected = "My Little Pony: A New Generation";

	        try {
	            Class.forName("org.sqlite.JDBC");
	            Connection conn = DriverManager.getConnection(path);
	            PreparedStatement pstmt = conn.prepareStatement(query);
	            pstmt.setString(1, "%" + expected + "%");

	            ResultSet resultSet = pstmt.executeQuery();  
	           String title = resultSet.getString("title");
	           assertEquals(expected,title,"Expected show was but show was not found in netflix database");
	           

	            conn.close();
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	}
	//Testing a show that does not exist in the database
	@Test
	void test2() {
		 String path = "jdbc:sqlite:database/Netflix.db";
	     String query = "SELECT * FROM netflix_titles WHERE title LIKE ?;";
	     String SearchFor = "Ben and Jerry";
	     boolean expected = false;
		

	        try {
	            Class.forName("org.sqlite.JDBC");
	            Connection conn = DriverManager.getConnection(path);
	            PreparedStatement pstmt = conn.prepareStatement(query);
	            pstmt.setString(1, "%" + SearchFor + "%");
	            ResultSet resultSet = pstmt.executeQuery(); 
	           
	            while (resultSet.next()) {
	            
	                expected = true;
	            }
	          
	           assertFalse(expected, "The show does exist within the database" );
	           

	            conn.close();
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	}
	//Test that the movies/shows are ordered in descending order by release year
	@Test
	void test3() {
		 String path = "jdbc:sqlite:database/Netflix.db";
	     String query = "SELECT * FROM netflix_titles ORDER BY release_year DESC;"; 
	    boolean expected = false;
		

	        try {
	            Class.forName("org.sqlite.JDBC");
	            Connection conn = DriverManager.getConnection(path);
	            PreparedStatement pstmt = conn.prepareStatement(query);
	            ResultSet resultSet = pstmt.executeQuery(); 
	            int Release_year = resultSet.getInt("release_year");
	            while (resultSet.next()) {
	            
	                int current_release_year = resultSet.getInt("release_year");
	                if(Release_year>current_release_year) {
	                	 expected = true;
	                }
	                Release_year = current_release_year;
	            }
	          assertTrue(expected,"The release year is expected to be greater then current release year" );
	          
	           

	            conn.close();
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	}
	 
	
	}


