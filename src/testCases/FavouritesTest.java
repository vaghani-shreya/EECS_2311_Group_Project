package testCases;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;

import front.Favourites;

class FavouritesTest {

	Favourites favourites;
	String username, showId, title, dateAdded, releaseYear, director, cast, description;
	String path, query;
	
	@BeforeEach
	void setUp() {

		path = "jdbc:sqlite:database/Favourite.db";
		username = "For User";
		showId = "12345";
		title = "Utilities Movie";
		dateAdded = "99/99/9999";
		releaseYear = "00/00/0000";
		director = "Carrying Director";
		cast = "Test Cast";
		description = "Knowledge Description";
		
		favourites = new Favourites();
		favourites.addToFavouritesList(username, showId, title, dateAdded, releaseYear, director, cast, description);
		
	}
	
	@AfterEach
	void tearDown() {
		favourites.deleteShowFromFavourites(username, showId, title);
	}
	
	@Test
	void testAddToFavouritesList() {
		
		String query = "SELECT * FROM Favourites WHERE"
						+ " username = " + username
						+ " AND showId = " + showId
						+ " AND title = " + title
						+ " AND date_added = " + dateAdded
						+ " AND release_year = " + releaseYear
						+ " AND director = " + director
						+ " AND cast = " + cast
						+ " AND description = " + description; 
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(path);
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        ResultSet resultSet = pstmt.executeQuery();
	        
	        while (resultSet.next()) {
	        	
	        	assertEquals(username, resultSet.getString("username"));
	        	assertEquals(showId, resultSet.getString("showId"));
	        	assertEquals(title, resultSet.getString("title"));
	        	assertEquals(dateAdded, resultSet.getString("date_added"));
	        	assertEquals(releaseYear, resultSet.getString("release_year"));
	        	assertEquals(director, resultSet.getString("director"));
	        	assertEquals(cast, resultSet.getString("cast"));
	        	assertEquals(description, resultSet.getString("description"));
	        	
	        }
	        
			conn.close();
	        
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void testDeleteShowFromFavourites() {
		
		favourites.deleteShowFromFavourites(username, showId, title);
		
		String query = "DELETE FROM Favourites WHERE"
						+ " username = " + username
						+ " AND showId = " + showId
						+ " AND title = " + title
						+ " AND date_added = " + dateAdded
						+ " AND release_year = " + releaseYear
						+ " AND director = " + director
						+ " AND cast = " + cast
						+ " AND description = " + description; 
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(path);
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        ResultSet resultSet = pstmt.executeQuery();
	        
	        while (resultSet.next()) {
	        	
	        	assertNull(resultSet.getString("username"));
	        	assertNull(resultSet.getString("showId"));
	        	assertNull(resultSet.getString("title"));
	        	assertNull(resultSet.getString("dateAdded"));
	        	assertNull(resultSet.getString("releaseYear"));
	        	assertNull(resultSet.getString("director"));
	        	assertNull(resultSet.getString("cast"));
	        	assertNull(resultSet.getString("description"));
	        	
	        }
	        
	        conn.close();
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void testSearchFavourites() {
		
		favourites.searchFavourites(title);
		
		String query = "SELECT * FROM Favourites WHERE"
						+ " username = " + username
						+ " AND showId = " + showId
						+ " AND title = " + title
						+ " AND date_added = " + dateAdded
						+ " AND release_year = " + releaseYear
						+ " AND director = " + director
						+ " AND cast = " + cast
						+ " AND description = " + description;
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(path);
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        ResultSet resultSet = pstmt.executeQuery();
	        
	        while (resultSet.next()) {
	        	
	        	assertEquals(username, resultSet.getString("username"));
	        	assertEquals(showId, resultSet.getString("showId"));
	        	assertEquals(title, resultSet.getString("title"));
	        	assertEquals(dateAdded, resultSet.getString("date_added"));
	        	assertEquals(releaseYear, resultSet.getString("release_year"));
	        	assertEquals(director, resultSet.getString("director"));
	        	assertEquals(cast, resultSet.getString("cast"));
	        	assertEquals(description, resultSet.getString("description"));
	        	
	        }
	        
	        conn.close();
	        
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void testDifferentUserFavouritesList() {
		
		String u = "The User";
		String i = "67890";
		String t = "History Movie";
		String a = "88/88/8888";
		String r = "22/22/2222";
		String di = "Is Director";
		String c = "Test Cast";
		String de = "Set Description";
		
		Favourites f2 = new Favourites();
		f2.addToFavouritesList(u, i, t, a, r, di, c, de);
		
		String query = "SELECT * FROM Favourites WHERE"
						+ " username = " + u
						+ " AND showId = " + i
						+ " AND title = " + t
						+ " AND date_added = " + a
						+ " AND release_year = " + r
						+ " AND director = " + di
						+ " AND cast = " + c
						+ " AND description = " + de;
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(path);
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        ResultSet resultSet = pstmt.executeQuery();
	        
	        while (resultSet.next()) {
	        	
	        	assertEquals(u, resultSet.getString("username"));
	        	assertEquals(i, resultSet.getString("showId"));
	        	assertEquals(t, resultSet.getString("title"));
	        	assertEquals(a, resultSet.getString("date_added"));
	        	assertEquals(r, resultSet.getString("release_year"));
	        	assertEquals(di, resultSet.getString("director"));
	        	assertEquals(c, resultSet.getString("cast"));
	        	assertEquals(de, resultSet.getString("description"));
	        	
	        }
	        
	        conn.close();
	        
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		f2.deleteShowFromFavourites(u, i, t);
		
	}

}
