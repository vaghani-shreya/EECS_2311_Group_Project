package front;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public abstract class BrowsingDashboard extends JPanel{
	    protected JPanel showPanel;
	    protected JScrollPane scrollPane;
	    protected JComboBox<String> filterComboBox;
	    protected JComboBox<String> sortComboBox;
	    
	    
	    protected abstract void Database();
	    
	    protected abstract void sortDatabase(String sortBy);
	    
	    protected abstract void searchDatabase(String searchFor, String filterBy);
	    

	    protected  void initComponents(){
	    		setSize(800, 600);
	    		
	    		//create a search panel
	    		JPanel searchPanel = new JPanel();
	    		JTextField searchField = new JTextField(20);
	    		// create search button
	    		JButton searchButton = new JButton("Search");
	    		// create sort button
	    		JButton sortButton = new JButton("Sort");

	    		// Filter menu feature
	    		String[] filterOptions = {"Title", "Genre", "Type", "Ratings"};
	    		filterComboBox = new JComboBox<>(filterOptions);

	    		// Sort menu feature
	    		String[] sortOptions = {"Release Date", "Title", "Date Added"};
	    		sortComboBox = new JComboBox<>(sortOptions);

	    		// Add components to the search panel
	    		searchPanel.add(searchField);
	    		searchPanel.add(filterComboBox);
	    		searchPanel.add(searchButton);
	    		searchPanel.add(new JLabel("Sort By:"));
	    		searchPanel.add(sortComboBox);
	    		searchPanel.add(sortButton);


	    		// Add action listener to the search button
	    		searchButton.addActionListener(new ActionListener() {
	    			@Override
	    			public void actionPerformed(ActionEvent e) {
	    				String searchFor = searchField.getText();
	    				String selectedFilter = (String) filterComboBox.getSelectedItem();
	    				searchDatabase(searchFor, selectedFilter);
	    			}
	    		});

	    		// Add action listener to the sort button
	    		sortButton.addActionListener(new ActionListener() {
	    			@Override
	    			public void actionPerformed(ActionEvent e) {
	    				String selectedSort = (String) sortComboBox.getSelectedItem();
	    				sortDatabase(selectedSort);
	    			}
	    		});
	    		
	    		// Scroll pane for displaying shows
	    		scrollPane = new JScrollPane();
	    		showPanel = new JPanel();
	    		showPanel.setLayout(new BoxLayout(showPanel, BoxLayout.Y_AXIS));
	    		scrollPane.setViewportView(showPanel);

	    		// Add components to this JPanel
	    		setLayout(new BorderLayout());
	    		add(searchPanel, BorderLayout.NORTH);
	    		add(scrollPane, BorderLayout.CENTER);

	    	}	
	    
	    
	    public void databaseHelper(String path,String query) {
	    	try {
				//call the JDBC driver
				Class.forName("org.sqlite.JDBC");
				Connection conn = DriverManager.getConnection(path);
				Statement stmt = conn.createStatement();

				ResultSet resultSet = stmt.executeQuery(query);
				retriveDBdetails(resultSet);

				conn.close();
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
	    }
	    	    
	    //Retrieves database details
	    public void retriveDBdetails(ResultSet resultSet) {
	    	
	    	try {
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
					JLabel showLabel = new JLabel("Show ID: " + id + ", Title: " + title + ", Date Added: " + dateAdded + ", Release Year: " + releaseYear);
					showLabel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							showDetails(id, title, dateAdded, releaseYear,director, description, cast, date_added);
						}
					});
					showLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					showLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					showPanel.add(showLabel);
					showPanel.add(Box.createVerticalStrut(10)); // Add vertical spacing between shows
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    }
	        
	 // Allows the user to search for a specific show/movie
	    protected void searchDBHelper(String path,String query,String searchFor) {
	    	try {
				Class.forName("org.sqlite.JDBC");
				Connection conn = DriverManager.getConnection(path);
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + searchFor + "%");

				ResultSet resultSet = pstmt.executeQuery();
				retriveDBdetails(resultSet);
				conn.close();
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}

			showPanel.revalidate(); // Refresh layout
			showPanel.repaint(); // Repaint the panel
	    }
	    
	    
	    public void sortDBHelper(String path,String query) {
			try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(path);
			Statement stmt = conn.createStatement();

			ResultSet resultSet = stmt.executeQuery(query);
			showPanel.removeAll();
			retriveDBdetails(resultSet);

			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
			
			showPanel.revalidate(); // Refresh layout
			showPanel.repaint(); // Repaint the panel
	    }
	    
	    
	    //Displays more specific details about the show/movie
	    public void showDetails(String showId, String title, String dateAdded, String releaseYear, String director, String description, String cast, String date_added) {
	    	String username = LoginPage.getUsernameForDB();
			Favourites favourites = new Favourites();


			WatchHistory watchedList = new WatchHistory();

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

			JButton likeButton = new JButton("Add to Favourites");

			likeButton.setPreferredSize(new Dimension(20,40));
			likeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Call addToFavouritesList method when the button is clicked
					favourites.addToFavouritesList(username,showId, title, dateAdded, releaseYear, director, cast, description);
				}
			});

		    detailsPanel.add(likeButton);

		    
		    //Mark as Watched/Watched Button
		    JButton watchedButton = new JButton("");
		    //call db to check if movie is in watch history
		    boolean watch = watchedList.checkWatchList(username, showId, title);
			if(watch == true) {
				watchedButton.setText("Watched");

			} else {
				watchedButton.setText("Mark as Watched");
			}
			watchedButton.setPreferredSize(new Dimension(20,40));
			// Set the button's bounds (x, y, width, height)
			watchedButton.setBounds(50, 50, 100, 30);
			watchedButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(watchedList.checkWatchList(username, showId, title) == true) {
						watchedList.deleteShowFromWatchList(username, showId, title);
						watchedButton.setText("Mark As Watched");

					} else {
						watchedList.addToWatchedList(username, showId, title);
						watchedButton.setText("Watched");

					}
				}
			});
		    
		    watch = watchedList.checkWatchList(username, showId, title);
		    
		    detailsPanel.add(likeButton);
		    detailsPanel.add(watchedButton);
			detailsPanel.add(detailsTextArea);
			detailsFrame.add(detailsPanel);

			detailsFrame.setSize(300, 200);
			detailsFrame.setLocationRelativeTo(null);
			detailsFrame.setVisible(true);
	    };	 
}