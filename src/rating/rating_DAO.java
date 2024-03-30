package rating;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import front.LoginPage;

public class rating_DAO {
	public int userRating;
	public int updaterate;
	public String comment;
	
	public rating_DAO() {}
		
	
	 public int updateRatingdb(int userRating, String id, String platform) {
		 updaterate = userRating;
		  
		 String netflix = "netflix";
		 String amazon = "amazon";
		 String disney = "disney";
		 String path = null;
		 String query = null;
		 
		if(platform.equals(netflix)) {			
		  path = "jdbc:sqlite:database/Netflix.db";
	        //Finds the specified title and extracts from database
	         query = "UPDATE netflix_titles SET NumRatings = ? WHERE show_id = ?;";
		} 
		if(platform.equals(amazon)) {			
			  path = "jdbc:sqlite:database/Amazon.db";
		        //Finds the specified title and extracts from database
		         query = "UPDATE amazon_prime_titles SET NumRatings = ? WHERE show_id = ?;";
			}
		if(platform.equals(disney)) {			
			  path = "jdbc:sqlite:database/Disney.db";
		        //Finds the specified title and extracts from database
		         query = "UPDATE disney_plus_titles SET NumRatings = ? WHERE show_id = ?;";
			}
	        
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
		 int rowsAffected = 0;
		 userRating = numRating;
		 
		 String path = "jdbc:sqlite:database/userDetails.db";
		 String query1 = "INSERT INTO userMedia (name, showid, title, releaseYear, rating, numRating) VALUES (?, ?, ?, ?, ?, ?)";
		 String query2 =  "SELECT * FROM userMedia WHERE title = ? AND name = ?;";
		 String query3 = "UPDATE userMedia SET NumRating = ? WHERE title = ? AND name = ?;";
		 
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
				pstmt2.setString(2, userName);
				
				
				pstmt3.setInt(1, numRating);
				pstmt3.setString(2, title);
				pstmt3.setString(3,userName);
				
				
				ResultSet resultSet = pstmt2.executeQuery();
				
				if(!resultSet.next()) {
					 rowsAffected = pstmt1.executeUpdate();
					
				}else {
					rowsAffected = pstmt3.executeUpdate();
				}
					
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}       			
		 		 
		  return userRating;
	 }
	 
	 
	 
	 public JPanel commentMedia(JFrame frame,String title,JTextArea detailsTextArea,String username) {
		  String user = LoginPage.getUsernameForDB();
			
	    	JPanel commentPanel = new JPanel();
	        JLabel commentLabel = new JLabel("Add Comment:");
	        JTextField commentField = new JTextField(30);
	        JButton saveCommentButton = new JButton("Save Comment");
	        
	        commentPanel.add(commentLabel);
	        commentPanel.add(commentField);
	        commentPanel.add(saveCommentButton);
	        

	        saveCommentButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                 comment = commentField.getText();
	                // Save the comment here
	                 saveCommentToUserDB(comment,user,title);
	                JOptionPane.showMessageDialog(frame, "Comment saved: " + comment);
	                detailsTextArea.append(username + "'s" + " comment: " + comment + "\n");
	                commentField.setText("");
	            }
	        });
	                
	        frame.add(commentPanel);       
	        return commentPanel;
	              	
	 }
	 	 
	 public void saveCommentToUserDB(String comment,String user,String title) {
		 String path = "jdbc:sqlite:database/userDetails.db";
		 String query = "UPDATE userMedia SET userComments = ? WHERE name = ? AND title = ?;";
		 
		 try {
				Class.forName("org.sqlite.JDBC");
				Connection conn = DriverManager.getConnection(path);
				PreparedStatement pstmt = conn.prepareStatement(query);

				pstmt.setString(1, comment);
				pstmt.setString(2,user);
				pstmt.setString(3, title);
			
			    pstmt.executeUpdate();		

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	        	 	 
	 }	

}
