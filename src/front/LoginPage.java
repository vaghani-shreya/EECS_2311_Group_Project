package front;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame {
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	private DatabaseHandler dbHandler;
	private static LoginPage instance;
	private static String usernameForDB;

	public boolean login(String username, String password) {
		// For simplicity, let's use a hardcoded username and password for demonstration
		//		return username.equals("user") && password.equals("password");
		boolean login = dbHandler.authenticateUser(username, password) || username.equals("user") && password.equals("password");

		return login;
	}

	public static LoginPage getInstance() {
		if (instance == null)
			instance = new LoginPage();

		return instance;
	}

	public LoginPage() {
		dbHandler = new DatabaseHandler();
		setTitle("Show Tracking Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create components
		JLabel titleLabel = new JLabel("Shows Tracking Application");
		JLabel usernameLabel = new JLabel("Username:");
		JLabel passwordLabel = new JLabel("Password:");
		JButton registerButton = new JButton("Register");
		usernameField = new JTextField(20); 
		passwordField = new JPasswordField(20); 
		JButton loginButton = new JButton("Login");
		JButton ForgotPasswordButton = new JButton("Forgot my password");

		// Label Properties
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.PLAIN, 30));
		usernameLabel.setForeground(Color.WHITE); 
		passwordLabel.setForeground(Color.WHITE);

		// Set layout manager for content pane
		cardPanel = new JPanel();
		cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);
		cardPanel.setBackground(new Color(45, 45, 45));

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
		contentPane.add(passwordLabel, gbc);
		gbc.gridy++;
		contentPane.add(passwordField, gbc);
		gbc.gridy++;
		gbc.gridwidth = 2; // Span two columns
		contentPane.add(loginButton, gbc);
		gbc.gridy++;
		gbc.gridwidth = 2; // Span two columns
		contentPane.add(ForgotPasswordButton, gbc);
		gbc.gridy++;
		contentPane.add(registerButton, gbc);

		// Create welcome panel
		WelcomePage welcomePanel = new WelcomePage(this);
		dashBoard dbPanel = new dashBoard(this);
		ForgotPasswordPage fpPanel = new ForgotPasswordPage(this);

		// Add panels to cardPanel
		cardPanel.add(contentPane, "login");
		cardPanel.add(welcomePanel, "welcome");
		cardPanel.add(dbPanel, "dashBoard");
		cardPanel.add(fpPanel, "ForgotPasswordPage");

		// Set contentPane to cardPanel
		setContentPane(cardPanel);

		pack();
		setLocationRelativeTo(null); // Center the frame on the screen


		// Add action listener to the login button
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Check username and password
				String username = getUsername();
				String password = getPassword();

				usernameForDB = username;
				// Perform login action
				if (login(username, password)) {
					cardLayout.show(cardPanel, "dashBoard");
				} else {
					JOptionPane.showMessageDialog(LoginPage.this, "Login failed. Please try again.");
				}

				// Clear the fields after checking
				setUsername("");
				setPassword("");
			}
		});

		// Add action listener to the forget password button
		ForgotPasswordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "ForgotPasswordPage");
			}
		});

		// Add action listener to the register button
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Retrieve username and password 
				String username = getUsername();
				String password = getPassword();

				// Check if username or password is empty
				if (username.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(LoginPage.this, "Please enter both username and password.");
					return; // Exit the action listener if fields are empty
				}

				// Check if the username already exists in the database
				boolean usernameExists = dbHandler.checkUser(username);
				if (usernameExists) {
					JOptionPane.showMessageDialog(LoginPage.this, "Username already exists. Please choose a different username.");
					return; // Exit the action listener if username already exists
				}

				// Insert new user credentials into the database
				boolean success = dbHandler.insertUserCredentials(username, password);
				if (success) {
					JOptionPane.showMessageDialog(LoginPage.this, "Registration successful. You can now login.");
				} else {
					JOptionPane.showMessageDialog(LoginPage.this, "Registration failed. Please try again.");
				}

				// Clear the fields after registration
				setUsername("");
				setPassword("");
			}
		});
	}

	public void signOut(){
		cardLayout.show(cardPanel, "login");
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

	public static String getUsernameForDB() {
		return usernameForDB;
	}

	public static void setUsernameForDB(String usernameForDB) {
		LoginPage.usernameForDB = usernameForDB;
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			LoginPage loginPage = new LoginPage();
			loginPage.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the frame to full size
			loginPage.setVisible(true);

		});

		// Retrieve user credentials from the database using DatabaseHandler
		DatabaseHandler dbHandler = new DatabaseHandler();
		dbHandler.retrieveUserCredentials();
	}
}

