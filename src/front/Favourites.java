package front;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class Favourites extends JFrame {
	public static Favourites instance;
	private JPanel showPanel;
	
	//Favourites constructor
    public Favourites() {
    	initComponents();
    	displayUserFavourites();
    	RefreshUserFavouritesDisplay();
    }
    //Ensuring only one instance is created
    public static Favourites getInstance() {
        if (instance == null)
            instance = new Favourites();
        return instance;
    }
    //Initialize GUI components
	public void initComponents() {
	
		setSize(800, 600);
    	setLocationRelativeTo(null);
		//create a search panel
		JPanel searchPanel = new JPanel();
		JTextField searchField = new JTextField(20);
		// create search button
		JButton searchButton = new JButton("Search");

		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		
		// Add action listener to the search button
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchFor = searchField.getText();
				searchFavourites(searchFor);
			}
		});


		 showPanel = new JPanel();
		 showPanel.setLayout(new BoxLayout(showPanel, BoxLayout.Y_AXIS));
		 JScrollPane scrollPane = new JScrollPane(showPanel);

		  getContentPane().add(searchPanel, BorderLayout.NORTH);
		  // Add the favorites panel to the center section of the frame
		  getContentPane().add(scrollPane, BorderLayout.CENTER);
		  revalidate();
		  repaint();
	    }
	//Refresh display of favourites every 10 seconds
	 private void RefreshUserFavouritesDisplay() {
	        Timer timer = new Timer(10000, new ActionListener() { 
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                displayUserFavourites(); 
	            }
	        });
	        timer.setRepeats(true); 
	        timer.start(); 
	    }

	//Search for a specified title from the user's favourites
	public void searchFavourites(String searchFor) {
	    showPanel.removeAll(); // Clear existing shows/movies
	    String username = LoginPage.getUsernameForDB();
	    String path = "jdbc:sqlite:database/Favourite.db";
	    String query = "SELECT * FROM favourites WHERE username = ? AND title LIKE ?";

	    try {
	        Class.forName("org.sqlite.JDBC");
	        Connection conn = DriverManager.getConnection(path);
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, username);
	        pstmt.setString(2, "%" + searchFor + "%"); 

	        ResultSet resultSet = pstmt.executeQuery();

	        while (resultSet.next()) {
	            String id = resultSet.getString("show_id");
	            String title = resultSet.getString("title");
	            String dateAdded = resultSet.getString("date_added");
	            String releaseYear = resultSet.getString("release_year");
	            String director = resultSet.getString("director");
	            String cast = resultSet.getString("cast");
	            String description = resultSet.getString("description");
	            
	            //prints the specified show / movie and the corresponding information
	            JLabel showLabel = new JLabel("ID: " + id + ", Title: " + title + ", Date Added: " + dateAdded + ", Release Year: " + releaseYear);
	            showLabel.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseClicked(MouseEvent e) {
	                    showDetails(id, title, dateAdded, releaseYear, director, cast, description);
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

	//Adds the the show to favourite.db when user clicks on the add to favourites button
	public void addToFavouritesList(String username, String showId, String title, String dateAdded, String releaseYear, String director, String cast, String description) {
	    
	    String path = "jdbc:sqlite:database/Favourite.db";
	    String selectQuery = "SELECT COUNT(*) FROM Favourites WHERE Username = ? AND title = ?";
	    String insertQuery = "INSERT INTO Favourites (Username, show_id, title, date_added, release_year, director, cast, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection connection = DriverManager.getConnection(path);
	         PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
	         PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

	        // Checks if the show/movie has been already added by the user
	        selectStatement.setString(1, username);
	        selectStatement.setString(2, title);
	        ResultSet resultSet = selectStatement.executeQuery();
	        resultSet.next();
	        int count = resultSet.getInt(1);
	        
	        if (count == 0) {
	         //if the show doesn't exist in the record add the details to the favourite database
	            insertStatement.setString(1, username);
	            insertStatement.setString(2, showId);
	            insertStatement.setString(3, title);
	            insertStatement.setString(4, dateAdded);
	            insertStatement.setString(5, releaseYear);
	            insertStatement.setString(6, director);
	            insertStatement.setString(7, cast);
	            insertStatement.setString(8, description);
	            insertStatement.executeUpdate();

	            //The show/movie has been added pop up message
	            JOptionPane.showMessageDialog(null, "The show/movie has been added to your favourites!", "Show/movie added", JOptionPane.INFORMATION_MESSAGE);
	        
	        } else {
	            	//the show already exists in your favourites pop up message
	        	 JOptionPane.showMessageDialog(null, "The show/movie already exists in your favourites!", "Duplicate show/movie", JOptionPane.INFORMATION_MESSAGE);
	            }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    displayUserFavourites();
	}
	
	//displays all the current user's favourited shows/movies from the database
	public void displayUserFavourites() {
		String username = LoginPage.getUsernameForDB();
        String path = "jdbc:sqlite:database/Favourite.db";
        String query = "SELECT * FROM Favourites WHERE username = ?";
        
        try (Connection connection = DriverManager.getConnection(path);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            showPanel.removeAll();
        //Iterate through all the details saved in the database
            while (resultSet.next()) {
                String showId = resultSet.getString("show_id");
                String title = resultSet.getString("title");
                String dateAdded = resultSet.getString("date_added");
                String releaseYear = resultSet.getString("release_year");
                String director = resultSet.getString("director");
                String cast = resultSet.getString("cast");
                String description = resultSet.getString("description");
                
                // add the show details from database to the display
                JLabel showLabel = new JLabel("Show ID: " + showId + ", Title: " + title + ", Date Added: " + dateAdded);
                showLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				showLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				showPanel.add(showLabel);
				showPanel.add(Box.createVerticalStrut(10)); 
				
				//Listener to click to see more details about the show
				showLabel.addMouseListener(new MouseAdapter() {
				 @Override
				    public void mouseClicked(MouseEvent e) {
				        showDetails(showId, title, dateAdded, releaseYear, director,cast,description);
				    }
				});
            }
            showPanel.revalidate();
            showPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	//Delete a specified show from user's favourites
		public void deleteShowFromFavourites(String username, String showId, String title) {
				String path = "jdbc:sqlite:database/Favourite.db";
			    String deleteQuery = "DELETE FROM Favourites WHERE username = ? AND show_id = ? AND title = ?";

			    try (Connection connection = DriverManager.getConnection(path);
			         PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

			        statement.setString(1, username);
			        statement.setString(2, showId);
			        statement.setString(3, title);
			        int rowsDeleted = statement.executeUpdate();
			        if (rowsDeleted > 0) {
			            JOptionPane.showMessageDialog(null, "Show deleted successfully!", "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
			            displayUserFavourites();
			        } else {
			            JOptionPane.showMessageDialog(null, "Failed to delete show.", "Deletion Failed", JOptionPane.ERROR_MESSAGE);
			        }

			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
	 
			}
	//Display more details about the Show/Movie
	private void showDetails(String showId, String title, String dateAdded, String releaseYear, String director, String cast, String description) {
		String username = LoginPage.getUsernameForDB();
		// Open a new page to display more details about a specific show/movie
		JFrame detailsFrame = new JFrame("Show Details");
		JPanel detailsPanel = new JPanel(); // Use a grid layout
		detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
		JTextArea detailsTextArea = new JTextArea();
		detailsTextArea.append("Show ID: " + showId + "\n");
		detailsTextArea.append("Title: " + title + "\n");
		detailsTextArea.append("Date Added: " + dateAdded + "\n");
		detailsTextArea.append("Release Year: " + releaseYear + "\n");
		detailsTextArea.append("Director: " + director + "\n");
		detailsTextArea.append("Description : " + description + "\n");
		detailsTextArea.append("Cast: " + cast + "\n");
		 JButton deleteButton = new JButton("Delete");
		 deleteButton.setPreferredSize(new Dimension(30, 30));
		    deleteButton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this show?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
		            if (choice == JOptionPane.YES_OPTION) {
		                // Delete the show from the database/user favourites
		                deleteShowFromFavourites(username, showId ,title);
		                detailsFrame.dispose();
		            }
		        }
		    });
		  

		    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		    buttonPanel.add(deleteButton);
		    detailsPanel.add(detailsTextArea, BorderLayout.CENTER);
		    detailsPanel.add(buttonPanel, BorderLayout.SOUTH); 
		    detailsPanel.add(detailsTextArea);
		    detailsPanel.add(deleteButton); 
		    detailsFrame.add(detailsPanel);
			detailsFrame.setSize(300, 200);
			detailsFrame.setLocationRelativeTo(null);
			detailsFrame.setVisible(true);
	}

	public static void main(String[] args) {	
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	JFrame frame = new Favourites();
                frame.setVisible(true);
                  
            }
        });
	}
}



