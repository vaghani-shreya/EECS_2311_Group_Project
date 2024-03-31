package rating;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import front.LoginPage;
import javax.swing.JOptionPane;

public abstract class ratingPanel extends JPanel {

	protected JPanel showPanel;
	protected JScrollPane scrollPane;
	protected rating_DAO rating_dao;
	protected String prevComm;

	public ratingPanel() {
		initComponents();
		loadDataFromDatabase();
	}

	protected void initComponents() {
		setSize(800, 600);

		JPanel searchPanel = new JPanel();
		JTextField searchField = new JTextField(20);
		JButton searchButton = new JButton("Search");

		searchPanel.add(searchField);
		searchPanel.add(searchButton);

		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchFor = searchField.getText();
				searchDatabase(searchFor);
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

	protected abstract void loadDataFromDatabase();
	

	protected abstract void searchDatabase(String searchFor);


	public void rateMedia(String show_id,String title, String dateAdded, String releaseYear, String description,String ratingNAN,String avgRating) {
		String user = LoginPage.getUsernameForDB();
		 int userRating = 0;
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
					rating_dao.updateRatingdb(rating,show_id,"netflix");
					rating_dao.insertIntoUserMediadb(user,show_id,title,releaseYear,ratingNAN,rating);
					JOptionPane.showMessageDialog(detailsFrame, "The Movie/show was rated a rating of " + rating );
					detailsTextArea.append(user + "'s" + " rating:  " + rating + "\n");
					rateField.setText(""); // Clear the text area
					
					
				}
				catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(detailsFrame, "Please enter the rating between 0 and 10.");
				}
				
			}
		});


		detailsPanel.add(detailsTextArea);
		detailsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between components
		detailsPanel.add(RatingPanel);
		detailsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between components
		detailsPanel.add(rating_dao.commentMedia(detailsFrame,title,detailsTextArea,user));
		detailsPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Add space between components

		detailsFrame.add(detailsPanel);
		detailsFrame.setSize(600, 400);
		detailsFrame.setLocationRelativeTo(null);
		detailsFrame.setVisible(true);


	}

}

