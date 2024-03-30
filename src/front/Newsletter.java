package front;

import java.sql.*;
import java.util.concurrent.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
/**
 * Newsletter class is responsible for sending out emails to all of the users in the UserCred database 
 * This includes: Getting each user from the UserCred database and sending an email with the recommendations list
 */
public class Newsletter {
	/**
	 * This method is responsible calling each user from the UserCred database 
	 * It takes each username and send an email to them with their recommendations list
	 * *Currently recommendations list is generic because it could not be implemented due to group changes*
	 */
	public static void main(String[] args) {
		// Create a scheduled executor service
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		// Schedule the email sending task to run every day
		scheduler.scheduleAtFixedRate(() -> {
			// Load the SQLite JDBC driver
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				System.out.println("SQLite JDBC Driver not found.");
				e.printStackTrace();
				return;
			}
			//Database connections
			String path = "jdbc:sqlite:database/UserCredentials.db";
			String query = "SELECT username FROM UserCred";
			try (Connection conn = DriverManager.getConnection(path);

					Statement stmt = conn.createStatement()) {

				//Execute Query
				ResultSet resultSet = stmt.executeQuery(query);

				// Iterate over the results and send email for each address
				while (resultSet.next()) {
					String to = resultSet.getString("username");
//					DatabaseHandler dbHandler = new DatabaseHandler();
					//retrieve recommendations for user
					Object[][] message = retrieveFavourites(to);

					//Send email to user
					sendEmail(to, "Newsletter", message);
				}

				// Close JDBC resources
				resultSet.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

	}

	/**
	 * This method is responsible for send the email to the specified user 
	 * It takes the username (email) and sends an email to the user with the recommendations list as the body of the email
	 */
	public static void sendEmail(String to, String subject, Object[][] body) {
		//Initialize Variables
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
			String text = "Here are some of your favourite movies and tv shows: \n\n " + formatData(body);
			message.setText(text);

			// Send message
			Transport.send(message);
			System.out.println("Email sent successfully!");
		} catch (MessagingException e) {
			System.out.println("Failed to send email: " + e.getMessage());
		}
	}

	/**
	 * This method is responsible for formatting the body of the email
	 * It takes the body (recommendations list) of the email and formats it so that a recommended movie/show can show up on a separate line
	 */
	// Format data to display each object on a new line
	private static String formatData(Object[][] data) {
		StringBuilder sb = new StringBuilder();
		//Go through each object and format
		for (Object[] row : data) {
			for (Object obj : row) {
				sb.append(obj).append("\n");
			}
			sb.append("\n"); // Add a new line between rows
		}
		//return formatted email body
		return sb.toString();
	}
	
	/**
	 * This method is responsible for retrieving the user's favourites list
	 * It is used by the newsletter feature, it returns only 10 items from the user's favourites list
	 */
	public static Object[][] retrieveFavourites(String username) {
		String favouritePath = "jdbc:sqlite:database/Favourite.db";
		String query = "SELECT * FROM Favourites WHERE username = ?";
		ArrayList<Object[]> favouriteList = new ArrayList<>();
		
		// Load the SQLite JDBC driver
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("SQLite JDBC Driver not found.");
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection(favouritePath);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			//Select the favorite movies for the specified
			pstmt.setString(1, username);
			ResultSet resultSet = pstmt.executeQuery();
			//add each object to the ArrayList
			while (resultSet.next()) {
				favouriteList.add(new Object[]{
						resultSet.getString("title"),
						resultSet.getInt("release_year")
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Return ArrayList with only 10 objects
		return favouriteList.stream().limit(10).toArray(Object[][]::new);
	}

}
