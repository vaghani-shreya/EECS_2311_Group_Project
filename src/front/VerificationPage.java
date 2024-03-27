package front;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.*;

import javax.swing.*;

public class VerificationPage extends JPanel {
	private JTextField codeField;
	private JTextField usernameField;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	public static VerificationPage instance;
	public static ForgotPasswordPage forgot;
	private static LoginPage login;
	private DatabaseHandler dbHandler;
	private static JButton enterButton;
	private String username;
	
	public boolean check(String username, int code) {
		// Check if the username and code matches the one in the database
		// For simplicity, let's use a hardcoded username and password for demonstration
		//		return username.equals("user") && code.equals("12345");
		boolean correct = dbHandler.checkCode(username, code)|| username.equals("user")&& code==12345;

		return correct;
	}
	
	public static VerificationPage getInstance() {
		if (instance == null)
			instance = new VerificationPage(forgot);

		return instance;
	}
	

	public VerificationPage(ForgotPasswordPage ForgotPasswordPage) {
		dbHandler = new DatabaseHandler();
		setSize(900, 900);
		setLayout(new BorderLayout());
		
		//Create Labels
		JLabel titleLabel = new JLabel("Verification Code");
		JLabel Label = new JLabel("Please re-enter your username and the 5 digit code that was sent to your email:");
		JLabel usernameLabel = new JLabel("Username:");
		JLabel codeLabel = new JLabel("Verification Code:");
		
		//Create textfields
		usernameField = new JTextField(20);
		codeField = new JTextField(20); 
		
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
		
		//Create Button
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
		contentPane.add(codeField, gbc);
		gbc.gridy++;
		gbc.gridwidth = 2; // Span two columns
		contentPane.add(enterButton, gbc);
		
		add(contentPane, BorderLayout.CENTER);
		
		// Create Reset Password panel
	    ResetPasswordPage rpPanel = new ResetPasswordPage(this);

	    // Add panels to cardPanel
	    cardPanel.add(contentPane, "verification");
	    cardPanel.add(rpPanel, "reset");

		add(cardPanel, BorderLayout.CENTER);

		enterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Get the username and code from text fields
				String verCode = getCode();
				String username = getUsername();
				
				//Check if fields are empty
				if (verCode.isEmpty() || username.isEmpty()) {
		            JOptionPane.showMessageDialog(VerificationPage.this, "Please enter both username and verification code.");
		            return; // Stop further execution
		        }
				
				// Check if the username and code match the ones in the database
				if (check(username, Integer.parseInt(verCode))) {
					cardLayout.show(cardPanel, "reset");
				} else {
					JOptionPane.showMessageDialog(VerificationPage.this, "The verification code or email is incorrect. Re-enter your username to send a new code.");
					//return to the forgot password page
					ForgotPasswordPage.returnPage();
				}
				
				// Clear the fields after checking
				setUsername("");
				setCode("");
				
			}
		});
	
	}
	
	// Methods to get and set the Username and Code
	public String getUsername() {
		return usernameField.getText();
	}
	
	public void setUsername (String username) {
		usernameField.setText(username);
	}
	
	public String getCode() {
		return codeField.getText();
	}
	
	public void setCode(String code) {
		codeField.setText(code);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	login = new LoginPage();
        	ForgotPasswordPage fpPage = new ForgotPasswordPage(login);
            JFrame frame = new JFrame();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(fpPage);
            frame.pack();
            frame.setVisible(true);
        });
        
     // Retrieve user credentials from the database using DatabaseHandler
     DatabaseHandler dbHandler = new DatabaseHandler();
     dbHandler.retrieveUserCredentials();
    }

}
