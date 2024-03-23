package front;

import java.util.ArrayList;
import java.sql.*;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.*;
import javax.mail.internet.*;

import com.google.gson.Gson;
import java.util.Arrays;

public class Newsletter {

	public static void main(String[] args) {
        //Database connections

        String path = "jdbc:sqlite:database/UserCredentials.db";
        String query = "SELECT username FROM UserCred";
        
        // Create a scheduled executor service
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the email sending task to run every day
        scheduler.scheduleAtFixedRate(() -> {
            try (Connection conn = DriverManager.getConnection(path);
            		
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                // Connect to the database
            	//Connection conn = DriverManager.getConnection(path);

                // Execute query to fetch email addresses
            	//PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet resultSet = pstmt.executeQuery(query);

                // Iterate over the results and send email for each address
                while (resultSet.next()) {
                    String to = resultSet.getString("email");
					DatabaseHandler dbHandler = new DatabaseHandler();
					//Object[][] message = dbHandler.retrieveRecommendations(to);
                    //sendEmail(to, "Newsletter", message);
                }

                // Close JDBC resources
                resultSet.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }, 0, 7, TimeUnit.DAYS); // Run every week

        // Shutdown the scheduler gracefully when the program exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown();
            try {
                scheduler.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        

        // Schedule the email sender to run every week
//		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//		DatabaseHandler dbHandler = new DatabaseHandler();
//		Object[][] message = dbHandler.retrieveRecommendations("anusham@my.yorku.ca");
		
//        scheduler.scheduleAtFixedRate(() -> {sendEmail("anusham@my.yorku.ca", "Newsletter", message);System.exit(0);}, 0, 7, TimeUnit.DAYS);
//		sendEmail("anusham@my.yorku.ca", "Newsletter", message);
    }
	
	//Send an email
		public static void sendEmail(String to, String subject, Object[][] body) {
	        final String from = "eecs2311group1@gmail.com";
	        final String password = "enter-password";

	        // Setup mail server properties
	        Properties properties = new Properties();
	        properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP server
	        properties.put("mail.smtp.port", "587"); // Replace with your SMTP server port
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");

	        // Get the default Session object
	        Session session = Session.getInstance(properties, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(from, password);
	            }
	        });
   
	     // Convert 2D array to JSON string
	        Gson gson = new Gson();
	        String json = gson.toJson(body);
	        
	        try {
	            // Create a default MimeMessage object
	            MimeMessage message = new MimeMessage(session);

	            // Set From: header field of the header
	            message.setFrom(new InternetAddress(from));

	            // Set To: header field of the header
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	            // Set Subject: header field
	            message.setSubject(subject);
	            
	            // Now set the actual message
	            String text = "Here are some reccomended movies/tv shows for you: \n\n " + formatData(body);
	            message.setText(text);

	            // Send message
	            Transport.send(message);
	            System.out.println("Email sent successfully!");
	        } catch (MessagingException e) {
	            System.out.println("Failed to send email: " + e.getMessage());
	        }
		}

		 // Format data to display each object on a new line
	    private static String formatData(Object[][] data) {
	        StringBuilder sb = new StringBuilder();
	        for (Object[] row : data) {
	            for (Object obj : row) {
	                sb.append(obj).append("\n");
	            }
	            sb.append("\n"); // Add a new line between rows
	        }
	        return sb.toString();
	    }

}
