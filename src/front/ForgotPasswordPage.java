package front;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.*;

import javax.swing.*;

import java.util.Random;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class ForgotPasswordPage extends JPanel {
	private JTextField usernameField;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	public static ForgotPasswordPage instance;
	private static LoginPage login;
	private DatabaseHandler dbHandler;
	private static JButton backButton;
	private static JButton enterButton;
	private int code;
	
	public boolean username(String username) {
		// Check if username exists in the database
		boolean login = dbHandler.checkUser(username);

		return login;
	}
	
	public void changeCode(String username, int code) {
		// assign a verification code to the user
		
		dbHandler.assignCode(username, code);

	}
	
	public static ForgotPasswordPage getInstance() {
		if (instance == null)
			instance = new ForgotPasswordPage(login);

		return instance;
	}
	
	public ForgotPasswordPage(LoginPage loginPage) {
		dbHandler = new DatabaseHandler();
		setSize(900, 900);
		setLayout(new BorderLayout());
		
		//Create Labels
		JLabel titleLabel = new JLabel("Forgot my Password");
		JLabel usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(20); 
		
		// Label Properties
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.PLAIN, 30));
		usernameLabel.setForeground(Color.WHITE); 
		
		// Set layout manager for content pane
		cardPanel = new JPanel();
		cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);
		cardPanel.setBackground(new Color(45, 45, 45));
		
		backButton = new JButton("Back");
		enterButton = new JButton("Enter");

		JPanel contentPane = new JPanel(new GridBagLayout());
		contentPane.setBackground(new Color(45, 45, 45));
		// Create constraints for centering
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new java.awt.Insets(5, 5, 5, 5); // Add some padding

		// Add components to the content pane
		contentPane.add(titleLabel, gbc);
		gbc.gridy++;
		gbc.fill = GridBagConstraints.HORIZONTAL; // Allow the component to expand horizontally
		contentPane.add(usernameLabel, gbc);
		gbc.gridy++;
		gbc.weightx = 0; // Do not allow the component to stretch
		contentPane.add(usernameField, gbc);
		gbc.gridy++;
		gbc.gridwidth = 2; // Span two columns
		contentPane.add(enterButton, gbc);
		gbc.gridy++;
		gbc.gridwidth = 2; // Span two columns
		contentPane.add(backButton, gbc);
		
		add(contentPane, BorderLayout.CENTER);
		
		// Create verification panel
	    VerificationPage verificationPanel = new VerificationPage(this);

	    // Add panels to cardPanel
	    cardPanel.add(contentPane, "forgetPassword");
	    cardPanel.add(verificationPanel, "verification");

		// Set contentPane to cardPanel
		add(cardPanel, BorderLayout.CENTER);
		
		enterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Check username
				String username = getUsername();

				// Check if user is correct
				if (username(username)) {
					//Get a random 5 digit code
					code = generateRandomCode();
					//Change the old code in the database to the new one
					changeCode(username, code);
					//Send and email to the user with the new code
					//sendEmail(username, "Verification Code", "Your verification code is");
					//Show the verification panel
					cardLayout.show(cardPanel, "verification");
				} else {
					JOptionPane.showMessageDialog(ForgotPasswordPage.this, "An account with this username does not exist.");
				}

				// Clear the fields after checking
				setUsername("");
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Call the signOut method in LoginPage to switch to the login page
				loginPage.signOut();
			}
		});
		
	}
	
	// Methods to Set and Get the Username
	public void setUsername (String username) {
		usernameField.setText(username);
	}
	
	public String getUsername() {
		return usernameField.getText();
	}
	
	//Method to generate random 5 digit code
	public static int generateRandomCode() {
        Random random = new Random();
        return random.nextInt(90000) + 10000; // Generates a random number between 10000 and 99999
    }
	
	//method to return to forget password page
	public void returnPage(){
		cardLayout.show(cardPanel, "forgetPassword");
	}
	
	//Send an email
	public static void sendEmail(String to, String subject, String body) {
        final String from = "your-email@gmail.com";
        final String password = "your-password";

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
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ForgotPasswordPage fpPage = new ForgotPasswordPage(login);
            JFrame frame = new JFrame();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(fpPage);
            frame.pack();
            frame.setVisible(true);
           // fpPage.setVisible(true);
        });
        
     // Retrieve user credentials from the database using DatabaseHandler
     DatabaseHandler dbHandler = new DatabaseHandler();
     dbHandler.retrieveUserCredentials();
    }

}
