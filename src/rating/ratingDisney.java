package rating;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import front.LoginPage;

public class ratingDisney extends JPanel{
	private JPanel showPanel;
	private JScrollPane scrollPane;
	private rating_DAO rating_dao;
	private static ratingDisney instance;
	public String prevComm;

	public static ratingDisney getInstance() {
		if (instance == null)
			instance = new ratingDisney();
		return instance;
	}


	public ratingDisney() {
		initComponents();
		DisneyDataBase();
	}



	private void initComponents(){

		setSize(800, 600);


		JPanel searchPanel = new JPanel();
		JTextField searchField = new JTextField(20);
		// create search button
		JButton searchButton = new JButton("Search");

		searchPanel.add(searchField);
		searchPanel.add(searchButton);


		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchFor = searchField.getText();
				searchDisneyDatabase(searchFor);
			}
		});

		add(searchPanel, BorderLayout.NORTH);


		scrollPane = new JScrollPane();
		showPanel = new JPanel();
		showPanel.setLayout(new BoxLayout(showPanel, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(showPanel);

		// Add components to this JPanel
		setLayout(new BorderLayout());
		add(searchPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

	}
	private void DisneyDataBase() {
		//rating_dao = new rating_DAO();
		String path = "jdbc:sqlite:database/Disney.db";
		// extract data from netflix database by descending order in terms of release year
		String query = "SELECT * FROM disney_plus_titles ORDER BY release_year DESC LIMIT 10;"; 

		try {
			//call the JDBC driver
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(path);
			Statement stmt = conn.createStatement();

			ResultSet resultSet = stmt.executeQuery(query);
			//loop through all data required from the database
			while (resultSet.next()) {
				String id = resultSet.getString("show_id");
				String title = resultSet.getString("title");
				String dateAdded = resultSet.getString("date_added");
				String releaseYear = resultSet.getString("release_year");
				String description = resultSet.getString("description");
				String ratingNAN = resultSet.getString("rating");
				String avgRating = resultSet.getString("avgRating");
				//prints the specified show / movie and the corresponding information
				JLabel showLabel = new JLabel(  "Title: " + title + ", Date Added: " + dateAdded + ", Release Year: " + releaseYear);
				showLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						rateMedia(id,title, dateAdded, releaseYear, description,ratingNAN, avgRating);
					}
				});
				showLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				showLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				showPanel.add(showLabel);
				showPanel.add(Box.createVerticalStrut(10)); // Add vertical spacing between shows
			}

			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	// Allows the user to search for a specific show/movie
	private void searchDisneyDatabase(String searchFor) {
		showPanel.removeAll(); // Clear existing shows/movies
	//	rating_dao = new rating_DAO();
		String path = "jdbc:sqlite:database/Disney.db";
		//Finds the specified title and extracts from database
		String query = "SELECT * FROM disney_plus_titles WHERE title LIKE ? LIMIT 10;";

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
				String description = resultSet.getString("description");
				String ratingNAN = resultSet.getString("rating");
				String avgRating = resultSet.getString("avgRating");
				//prints the specified show / movie and the corresponding information
				JLabel showLabel = new JLabel("Title: " + title + ", Date Added: " + dateAdded + ", Release Year: " + releaseYear);
				showLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						rateMedia(id,title, dateAdded, releaseYear, description,ratingNAN, avgRating);

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

	public void rateMedia(String show_id,String title, String dateAdded, String releaseYear, String description,String ratingNAN,String avgRating) {
		 String user = LoginPage.getUsernameForDB();
		 rating_dao = new rating_DAO();
		
		JFrame detailsFrame = new JFrame("Add Ratings");
		JPanel detailsPanel = new JPanel();
		detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS)); // Stack components vertically
	        
		JTextArea detailsTextArea = new JTextArea();
		detailsTextArea.append("Title: " + title + "\n");
		detailsTextArea.append("Date Added: " + dateAdded + "\n");
		detailsTextArea.append("Release Year: " + releaseYear + "\n");
		if(description == null) {
			detailsTextArea.append("Description : NO DESCRIPTION AVAILABLE"  + "\n");
		}else {
			detailsTextArea.append("Description : " + description + "\n");
		}
	   
		 detailsTextArea.append("Average Rating: " + avgRating + "\n");
		  
		
		 JPanel RatingPanel = new JPanel();
		  JLabel ratingLabel = new JLabel("Add Rating: ");
		  JTextField rateField = new JTextField(10);
		  JButton saveButton = new JButton("Save");
		     
		     RatingPanel.add(ratingLabel);
		     RatingPanel.add(rateField);    
		     RatingPanel.add(saveButton);
		     
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ratingText = rateField.getText();	

				try {

					int rating = Integer.parseInt(ratingText);
					if(rating < 0 || rating > 10) {
						throw new NumberFormatException();
					}
					//System.out.println(rating);
					rating_dao.updateRatingdb(rating,show_id,"disney");
					rating_dao.insertIntoUserMediadb(user,show_id,title,releaseYear,ratingNAN,rating);
					JOptionPane.showMessageDialog(detailsFrame, "The Movie/show was rated a rating of " + rating );
					//detailsFrame.dispose();
					
				}
				catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(detailsFrame, "Please enter the rating between 0 and 10.");
				}
			}
		});

//        add(RatingPanel, BorderLayout.NORTH);     

     detailsPanel.add(detailsTextArea);
     detailsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between components
     detailsPanel.add(RatingPanel);
     detailsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between components
     detailsPanel.add(rating_dao.commentMedia(detailsFrame,title,detailsTextArea));
     detailsPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Add space between components

     detailsFrame.add(detailsPanel);
     detailsFrame.setSize(600, 400);
     detailsFrame.setLocationRelativeTo(null);
     detailsFrame.setVisible(true);

	}






}
