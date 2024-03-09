package Favourites;

import java.awt.BorderLayout;
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
import javax.swing.table.DefaultTableModel;

import front.*;

public class Favourites extends JFrame {
	
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
	
	private static String username = user.getUsername();
	
	
	
	public Favourites(LoginPage loginPage) {
		
		// Creates a Favourites List if not already created; Retrieves Favourites List from database
		createUserFavouritesTable(username);
		retrieveFavouritesList(username);
		
		testFrame = new JFrame("Favourites Frame");
		testFrame.setSize(900, 900);
		
		JPanel favouritesPanel = new JPanel();
		dashboard.add(favouritesPanel);
		
		testFrame.add(favouritesPanel);
		
		JLabel sortedBy = new JLabel("Sorted By:");
		sortedBy.setBounds(10, 20, 80, 25);
		favouritesPanel.add(sortedBy, BorderLayout.NORTH);
		
		JComboBox<String> filterList = new JComboBox<String>(filterNames);
		filterList.setBounds(30, 20, 80, 25);
		favouritesPanel.add(filterList, BorderLayout.NORTH);
		
		DefaultTableModel movieList = new DefaultTableModel(null, filterNames) {
			
			// Makes the table uneditable
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
			
		};
		
		JTable favouritedMovieList = new JTable(movieList);
		favouritedMovieList.setBounds(0, 500, 300, 300);
		favouritesPanel.add(favouritedMovieList);
		favouritesPanel.add(new JScrollPane(favouritedMovieList));
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(10, 30, 80, 25);
		favouritesPanel.add(deleteButton);
		
		dashboard.setVisible(true);
		testFrame.setVisible(true);
		
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
	
	public void retrieveFavouritesList(String username) {
		
		String path = "jdbc:sqlite:database/Favourites.db";
		String query = "SELECT * FROM '" + username + " Favourited Movies'";
		
		try {
				//call the JDBC driver
				Class.forName("org.sqlite.JDBC");
				Connection connection = DriverManager.getConnection(path);
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery(query);
	            
	            // Put the database data into a Java Table
	            while(resultSet.next()) {
	                String id = resultSet.getString("show_id");
	                String title = resultSet.getString("title");
	                String dateAdded = resultSet.getString("date_added");
	                String releaseYear = resultSet.getString("release_year");
	                String director = resultSet.getString("director");
	                String cast = resultSet.getString("cast");
	                String description = resultSet.getString("description");
	                
	                JLabel showLabel = new JLabel("Show ID: " + id 
	                + ", Title: " + title	                 
	                + ", Date Added: " + dateAdded
	                + ", Release Year: " + releaseYear
	                + ", Director: " + director
	                + ", Cast: " + cast
	                + ", Description: " + description
	                );
	                
	            }

	            connection.close();
	            statement.close();
	            resultSet.close();
	            
	       	} catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	}
	
	
	public void addToFavouritesList(String username, String title) {
		// If there is no favourites table, make a new one then add the movie to it
		
		String path = "jdbc:sqlite:database/Favourites.db";
		String query = "CREATE TABLE IF NOT EXISTS '" + username + " Favourited Movies' ("
				+ "'Show ID' VARCHAR(255),"
				+ "Title VARCHAR(255),"
				+ "'Date Added' VARCHAR(255),"
				+ "'Release Year' VARCHAR(255),"
				+ "Director VARCHAR(255),"
				+ "Cast VARCHAR(255),"
				+ "Description VARACHR(255)"
				+ ");"
				
				+ "INSERT INTO '" + username + " Favourited Movies'"
				+ "VALUES ("
				+ "" // Show ID
				+ title // Title
				+ "" // Date Added
				+ "" // Release Year
				+ "" // Director
				+ "" // Cast
				+ "" // Description
				+ ");";
		
		// If you added the movie name to the list, add the movie's attributes along with it
		// Pull from the Netflix Database the movie's attributes and place them into the Favourites Database
	}
	
	public void searchFavouritedMovie(String username, String title) {
		
	}
	
	public void deleteFromFavouritesList(String username, String title) {
		
		String path = "jdbc:sqlite:database/Favourites.db";
		String query = "DELETE FROM '" + username + " Favourited Movies' WHERE Title = '" + title + "';";
	}
	
	public void clearFavouritesList(String username) {

		String path = "jdbc:sqlite:database/Favourites.db";
		String query = "DROP TABLE " + username + ";";
		
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

	public static void main(String[] args) {
		
		// Must Create New Instance of Object in Order to Show The Page
		Favourites favouritesTab = new Favourites(user);
	}

}
