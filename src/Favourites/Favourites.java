package Favourites;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import front.*;

public class Favourites extends JFrame {
	
//	private String username;
	private JPanel showPanel;
	private JScrollPane scrollPane;
	private JComboBox<String> filterComboBox;
//	String username = LoginPage.getUsername();
	
	// Create an Instance of LoginPage and dashBoard
//	private static LoginPage user = new LoginPage();
//	private static String username = user.getUsername();
	
	public Favourites(String username) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		// Creates a Favourites List if not already created; Retrieves Favourites List from database
		createUserFavouritesTable(username);
		retrieveFavouritesList(username);
		
		JPanel optionsPanel = new JPanel();
		JTextField searchField = new JTextField(20);
		JButton searchButton = new JButton("Search");
		JButton deleteButton = new JButton("Delete");
		
		String[] filterNames = {"Show ID", "Title", "Date Added", "Release Year", "Director", "Cast", "Description"};
		filterComboBox = new JComboBox<>(filterNames);

		optionsPanel.add(new JLabel("Sort By:"));
		optionsPanel.add(filterComboBox);
		optionsPanel.add(searchField);
		optionsPanel.add(searchButton);
		optionsPanel.add(deleteButton);
		
		filterComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String selectedSort = (String) filterComboBox.getSelectedItem();
				sortFavouritesList(username, selectedSort);
			}
		});
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchFor = searchField.getText();
				searchFavouritedMovie(username, searchFor);
			}
		});
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchFor = searchField.getText();
				deleteFromFavouritesList(username, searchFor);
			}
		});
		
		add(optionsPanel, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		showPanel = new JPanel();
		showPanel.setLayout(new BoxLayout(showPanel, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(showPanel);
		
	}
	
	public void createUserFavouritesTable(String username) {
		
		String path = "jdbc:sqlite:database/Favourites.db";
		String query = "CREATE TABLE IF NOT EXISTS '" + username + " Favourited Movies' ("
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
	                String date_added = resultSet.getString("date_added");
	                
	                JLabel showLabel = new JLabel("Show ID: " + id 
	                + ", Title: " + title	                 
	                + ", Date Added: " + dateAdded
	                + ", Release Year: " + releaseYear
	                + ", Director: " + director
	                + ", Cast: " + cast
	                + ", Description: " + description
	                + ", Date Added: " + date_added
	                );
	                
					showLabel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							showDetails(id, title, dateAdded, releaseYear, director, description, cast, date_added);
						}
					});
					
					showLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					showLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					showPanel.add(showLabel);
					showPanel.add(Box.createVerticalStrut(10)); 
	                
	            }

	            connection.close();
	            
	       	} catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	}
	
	
	public void addToFavouritesList(String username, String showId, String title, String dateAdded, String releaseYear, String director, String cast, String description) {
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
				+ showId + ", " // Show ID
				+ title + ", " // Title
				+ dateAdded + ", " // Date Added
				+ releaseYear + ", " // Release Year
				+ director + ", " // Director
				+ cast + ", " // Cast
				+ description // Description
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
		// If you added the movie name to the list, add the movie's attributes along with it
		// Pull from the Netflix Database the movie's attributes and place them into the Favourites Database
	}
	
	public void searchFavouritedMovie(String username, String searchFor) {
		showPanel.removeAll(); // Clear existing shows/movies
		
		String path = "jdbc:sqlite:database/Favourites.db";
		String query = "SELECT * FROM '" + username + " Favourited Movies' WHERE '" + searchFor + "' LIKE ?;";

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(path);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + searchFor + "%");

			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString("show_id");
				String title = resultSet.getString("title");
				String dateAdded = resultSet.getString("date_added");
				String releaseYear = resultSet.getString("release_year");
				String director = resultSet.getString("director");
				String cast = resultSet.getString("cast");
				String description = resultSet.getString("description");
				String date_added = resultSet.getString("date_added");
				//prints the specified show / movie and the corresponding information

				JLabel showLabel = new JLabel("ID: " + id + ", Title: " + title + ", Date Added: " + dateAdded + ", Release Year: " + releaseYear);
				showLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						showDetails(id, title, dateAdded, releaseYear, director, description, cast, date_added);
					}
				});

				showLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				showLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				showPanel.add(showLabel);
				showPanel.add(Box.createVerticalStrut(10)); 
			}

			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		showPanel.revalidate(); // Refresh layout
		showPanel.repaint(); // Repaint the panel
	}
	
	public void sortFavouritesList(String username, String filterName) {
		
		String path = "jdbc:sqlite:database/Favourites.db";
		String query = "SELECT * FROM '" + username + " Favourited Movies' ORDER BY " + filterName + " DESC;";

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(path);
			Statement stmt = conn.createStatement();

			ResultSet resultSet = stmt.executeQuery(query);
			showPanel.removeAll();

			while (resultSet.next()) {
				String id = resultSet.getString("show_id");
				String title = resultSet.getString("title");
				String dateAdded = resultSet.getString("date_added");
				String releaseYear = resultSet.getString("release_year");
				String director = resultSet.getString("director");
				String cast = resultSet.getString("cast");
				String description = resultSet.getString("description");
				String date_added = resultSet.getString("date_added");
				//prints the specified show / movie and the corresponding information

				JLabel showLabel = new JLabel("ID: " + id + ", Title: " + title + ", Date Added: " + dateAdded + ", Release Year: " + releaseYear);
				showLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						showDetails(id, title, dateAdded, releaseYear, director, description, cast, date_added);
					}
				});

				showLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				showLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				showPanel.add(showLabel);
				showPanel.add(Box.createVerticalStrut(10)); 
			}

			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		showPanel.revalidate(); // Refresh layout
		showPanel.repaint(); // Repaint the panel
	}
	
	public void deleteFromFavouritesList(String username, String searchFor) {
		
		String path = "jdbc:sqlite:database/Favourites.db";
		String query = "DELETE FROM '" + username + " Favourited Movies' WHERE Title = '" + searchFor + "';";
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(path);
			Statement stmt = conn.createStatement();

			ResultSet resultSet = stmt.executeQuery(query);
			showPanel.removeAll();

			while (resultSet.next()) {
				String id = resultSet.getString("show_id");
				String title = resultSet.getString("title");
				String dateAdded = resultSet.getString("date_added");
				String releaseYear = resultSet.getString("release_year");
				String director = resultSet.getString("director");
				String cast = resultSet.getString("cast");
				String description = resultSet.getString("description");
				String date_added = resultSet.getString("date_added");
				//prints the specified show / movie and the corresponding information

				JLabel showLabel = new JLabel("ID: " + id + ", Title: " + title + ", Date Added: " + dateAdded + ", Release Year: " + releaseYear);
				showLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						showDetails(id, title, dateAdded, releaseYear, director, description, cast, date_added);
					}
				});

				showLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				showLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				showPanel.add(showLabel);
				showPanel.add(Box.createVerticalStrut(10)); 
			}

			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		showPanel.revalidate(); // Refresh layout
		showPanel.repaint(); // Repaint the panel
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
	
	private void showDetails(String showId, String title, String dateAdded, String releaseYear, String director, String description, String cast, String date_added) {
		// Open a new page to display more details about a specific show/movie
		JFrame detailsFrame = new JFrame("Show Details");
		JPanel detailsPanel = new JPanel(new GridLayout(0, 1)); // Use a grid layout
		JTextArea detailsTextArea = new JTextArea();
		detailsTextArea.append("Show ID: " + showId + "\n");
		detailsTextArea.append("Title: " + title + "\n");
		detailsTextArea.append("Date Added: " + dateAdded + "\n");
		detailsTextArea.append("Release Year: " + releaseYear + "\n");
		detailsTextArea.append("Director: " + director + "\n");
		detailsTextArea.append("Description : " + description + "\n");
		detailsTextArea.append("Cast: " + cast + "\n");


		detailsPanel.add(detailsTextArea);
		detailsFrame.add(detailsPanel);

		detailsFrame.setSize(300, 200);
		detailsFrame.setLocationRelativeTo(null);
		detailsFrame.setVisible(true);
	}

//	public static void main(String[] args) {	
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new Favourites(username).setVisible(true);     
//            }
//        });
//	}

}
