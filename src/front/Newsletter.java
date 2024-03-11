package front;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


public class Newsletter {

	public static void main(String[] args) {
        //Database connections
//        String path = "jdbc:sqlite:database/UserCredentials.db";
//        String query = "SELECT email FROM users";
        
        // Create a scheduled executor service
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the email sending task to run every day
//        scheduler.scheduleAtFixedRate(() -> {
//            try {
                // Connect to the database
//            	Connection conn = DriverManager.getConnection(path);

                // Execute query to fetch email addresses
//            	PreparedStatement pstmt = conn.prepareStatement(query);
//               ResultSet resultSet = pstmt.executeQuery(query);

                // Iterate over the results and send email for each address
//                while (resultSet.next()) {
//                    String to = resultSet.getString("email");
//                    sendEmail(to, "Newsletter", "empty");
//                }

                // Close JDBC resources
//                resultSet.close();
//                pstmt.close();
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }, 0, 7, TimeUnit.DAYS); // Run every week

        // Shutdown the scheduler gracefully when the program exits
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            scheduler.shutdown();
//            try {
//                scheduler.awaitTermination(5, TimeUnit.SECONDS);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }));
        

        // Schedule the email sender to run every week
        //scheduler.scheduleAtFixedRate(() -> sendEmail(), 0, 7, TimeUnit.DAYS);
    }
	
	//Send an email
		public static void sendEmail(String to, String subject, String body) {
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
	            message.setText(body);

	            // Send message
	            Transport.send(message);
	            System.out.println("Email sent successfully!");
	        } catch (MessagingException e) {
	            System.out.println("Failed to send email: " + e.getMessage());
	        }
		}
}
