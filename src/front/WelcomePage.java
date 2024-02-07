package front;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage extends JPanel {
	public WelcomePage(LoginPage loginPage) {
		setBackground(new Color(45,45,45)); // Set the background colour

		JLabel welcomeLabel = new JLabel("Welcome to the new page!");
		welcomeLabel.setForeground(Color.WHITE);
		add(welcomeLabel);

		JButton signOutButton = new JButton("Sign Out");
		add(signOutButton);

		signOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Call the signOut method in LoginPage to switch to the login page
				loginPage.signOut();
			}
		});
	}
}