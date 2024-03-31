package rating;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.JLabel;


public class ratingAmazon extends ratingPanel{
	 private static ratingAmazon instance;
	
	 public static ratingAmazon getInstance() {
  
	if (instance == null)
       instance = new ratingAmazon();
   return instance;
}
	  

	@Override
	public void loadDataFromDatabase() {
		
		 String path = "jdbc:sqlite:database/Amazon.db";
	     // extract data from netflix database by descending order in terms of release year
	        String query = "SELECT * FROM amazon_prime_titles ORDER BY release_year DESC LIMIT 10;";
	       

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
	            	  //ratingNetflix.getInstance();
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

	@Override
	public void searchDatabase(String searchFor) {
		
       showPanel.removeAll(); // Clear existing shows/movies  
       //Finds the specified title and extracts from database
       String path = "jdbc:sqlite:database/Amazon.db";
       String query = "SELECT * FROM amazon_prime_titles WHERE title LIKE ?;";

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
       
		
	}

}
