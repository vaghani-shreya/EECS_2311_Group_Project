package Favourites;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import front.*;

public class Favourites {
	
	private static JFrame testFrame;
	
	private String[] filterNames = {
			"Show ID",
			"Title",
			"Date Added",
			"Release Year",
			"Director",
			"Cast",
			"Description"
	};
	
	// Create an Instance of LoginPage and dashBoard
	private static LoginPage user = new LoginPage();
	private static dashBoard dashboard = new dashBoard(user);
	
	private static String userName = user.getUsername();
	
	
	
	public Favourites(LoginPage loginPage) {
		
		// Creates a Favourites List if not already created; Retrieves Favourites List from database
		createUserFavouritesTable(userName);
		retrieveFavouritesList(userName);
		
		testFrame = new JFrame("Favourites Frame");
		testFrame.setSize(900, 900);
		
		JPanel favouritesPanel = new JPanel();
		dashboard.add(favouritesPanel);
		
		testFrame.add(favouritesPanel);
		
		JLabel sortedBy = new JLabel("Sorted By:");
		sortedBy.setBounds(10, 20, 80, 25);
		favouritesPanel.add(sortedBy);
		
		JComboBox<String> filterList = new JComboBox<String>(filterNames);
		filterList.setBounds(30, 20, 80, 25);
		favouritesPanel.add(filterList);
		
		JTable favouritedMovieList = new JTable();
		favouritedMovieList.setBounds(0, 30, 100, 100);
		favouritesPanel.add(favouritedMovieList);
		
		dashboard.setVisible(true);
		testFrame.setVisible(true);
		
	}
	
	public void addToFavouritesList() {
		
	}
	
	public Object[][] retrieveFavouritesList(String user) {
		
		String path = "jdbc:sqlite:database/Favourites.db";
		String query = "SELECT * FROM '" + user + " Favourited Movies'";
		
		try {
				//call the JDBC driver
				Class.forName("org.sqlite.JDBC");
				Connection connection = DriverManager.getConnection(path);
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery(query);
	            
	            while(resultSet.next()) {
	            	
	            	
	            	
	            }

	            connection.close();
	            statement.close();
	            resultSet.close();
	            
	       	} catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
		return null;
	}
	
	public void createUserFavouritesTable(String user) {
		
		String path = "jdbc:sqlite:database/Favourites.db";
		String query = "CREATE TABLE IF NOT EXISTS '" + user + " Favourited Movies' ("
				+ "'Show ID' VARCHAR(255),"
				+ "Title VARCHAR(255),"
				+ "'Date Added' VARCHAR(255),"
				+ "'Release Year' VARCHAR(255),"
				+ "Director VARCHAR(255),"
				+ "Cast VARCHAR(255),"
				+ "Description VARCHAR(255)"
				+ ");";
		
        try {
        	//call the JDBC driver
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(path);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            connection.close();
            statement.close();
            resultSet.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
		
	}
	
	public void deleteFromFavouritesList(String movieName) {
		
	}
	
	public void clearFavouritesList() {
		
	}

	public static void main(String[] args) {
		
		// Must Create New Instance of Object in Order to Show The Page
		Favourites favouritesTab = new Favourites(user);
	}

}
