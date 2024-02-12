package front;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.*;

import javax.swing.*;

public class ResetPasswordPage extends JPanel {
	private JTextField passwordField;
	private JTextField usernameField;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	public static ResetPasswordPage instance;
	public static VerificationPage verify;
	public static ForgotPasswordPage forgot;
	private static LoginPage loginPage;
	private DatabaseHandler dbHandler;
	private static JButton enterButton;
	
	public void newPassword(String username, String password) {
		//reset the password in database
		dbHandler.resetPassword(username, password);
	}
	
	public static ResetPasswordPage getInstance() {
		if (instance == null)
			instance = new ResetPasswordPage(verify);

		return instance;
	}

	public ResetPasswordPage(VerificationPage verificationPage) {
		dbHandler = new DatabaseHandler();
		setSize(900, 900);
		setLayout(new BorderLayout());
		
		//Create Labels
		JLabel titleLabel = new JLabel("New Password");
		JLabel Label = new JLabel("Please re-enter your username and a new password:");
		JLabel usernameLabel = new JLabel("Username:");
		JLabel codeLabel = new JLabel("New Password:");
		usernameField = new JTextField(20);
		passwordField = new JTextField(20); 
		
		// Label Properties
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.PLAIN, 30));
		Label.setForeground(Color.WHITE); 
		usernameLabel.setForeground(Color.WHITE); 
		codeLabel.setForeground(Color.WHITE); 
		
		// Set layout manager for content pane
		cardPanel = new JPanel();
		cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);
		cardPanel.setBackground(new Color(45, 45, 45));
		
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
		contentPane.add(Label, gbc);
		gbc.gridy++;
		gbc.weightx = 0; // Do not allow the component to stretch
		contentPane.add(usernameLabel, gbc);
		gbc.gridy++;
		gbc.weightx = 0; // Do not allow the component to stretch
		contentPane.add(usernameField, gbc);
		gbc.gridy++;
		contentPane.add(codeLabel, gbc);
		gbc.gridy++;
		gbc.weightx = 0; // Do not allow the component to stretch
		contentPane.add(passwordField, gbc);
		gbc.gridy++;
		gbc.gridwidth = 2; // Span two columns
		contentPane.add(enterButton, gbc);
		
		add(contentPane, BorderLayout.CENTER);

		enterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Get the string from textfields
				String password = getPassword();
				String username = getUsername();
				
				//Check if fields are empty
				if (password.isEmpty() || username.isEmpty()) {
		            JOptionPane.showMessageDialog(ResetPasswordPage.this, "Please enter username and new password.");
		            return; // Stop further execution
		        }
				newPassword(username, password);
				JOptionPane.showMessageDialog(ResetPasswordPage.this, "Your password was successfully changed.");
				
				// Get the parent window (LoginPage) and dispose the current window
	            Window parentWindow = SwingUtilities.getWindowAncestor(ResetPasswordPage.this);
	            if (parentWindow instanceof JFrame) {
	                parentWindow.dispose(); // Close the current window (ResetPasswordPage)
	            }

	            // Show the LoginPage frame
	            //loginPage.setVisible(true);
				
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

	// Methods to Set and Get the Password
	public void setPassword (String password) {
		passwordField.setText(password);
	}

	public String getPassword() {
		return passwordField.getText();
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	loginPage = new LoginPage();
        	VerificationPage vPage = new VerificationPage(forgot);
            JFrame frame = new JFrame();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(vPage);
            frame.pack();
            frame.setVisible(true);
           // fpPage.setVisible(true);
        });
        
     // Retrieve user credentials from the database using DatabaseHandler
     DatabaseHandler dbHandler = new DatabaseHandler();
     dbHandler.retrieveUserCredentials();
    }
	
}
