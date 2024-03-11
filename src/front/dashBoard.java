package front;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

import analytics.ratingAnalytics;

import Favourites.Favourites;

import recommendation.RecommendationPanel;

public class dashBoard extends JPanel{
	private JPanel cardPanel;
	private static JButton signOutButton;
	private CardLayout cardLayout;
	public static dashBoard instance;
	private static LoginPage login;
	private DatabaseHandler dbHandler;
	private String[] filterNames = {"Name", "Length", "Genre", "Date Added", "Rating", "Release Date"};
	private JComboBox filterList = new JComboBox(filterNames);

	public static dashBoard getInstance() {
		if (instance == null)
			instance = new dashBoard(login);

		return instance;
	}

	public dashBoard(LoginPage loginPage) {
		// setTitle("Dashboard");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 900);
		setLayout(new BorderLayout());
		//  setLocationRelativeTo(null); // Center the window

		//initialize a databaseHandler instance
		dbHandler = new DatabaseHandler();
		// Create tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();

		// Create tabs
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BorderLayout());
		netflix net = new netflix();
		tab1.add(net.getContentPane());
		tabbedPane.add("Dashboard Movies/Shows", tab1);
		add(tabbedPane, BorderLayout.CENTER);

		JPanel tab2 = new JPanel();
		RecommendationPanel recommendationPanel = new RecommendationPanel(tabbedPane, dbHandler, loginPage.getUsername());
		tab2.add(recommendationPanel); 
		tab2.setLayout(new BoxLayout(tab2, BoxLayout.Y_AXIS));
		
		JPanel tab3 = new JPanel();
		tab3.add(new JLabel("User can rate here"));
		
		JPanel tab4 = new JPanel();
		Favourites fav = new Favourites(loginPage.getUsername());
		tab4.add(fav.getContentPane());

		JPanel tab5 = new JPanel();
		ratingAnalytics ratingChart = new ratingAnalytics();

	    tab5.add(ratingChart.getContentPane());

		// Add tabs to tabbed pane
		tabbedPane.addTab("Dashboard Movies/Shows", tab1);
		tabbedPane.addTab("Recommendations", tab2);
		tabbedPane.addTab("Ratings", tab3);
		tabbedPane.addTab("Favourites", tab4);
		tabbedPane.addTab("Analytics", tab5);


		signOutButton = new JButton("Sign Out");

		JPanel signOutPanel = new JPanel();
		signOutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		signOutPanel.add(signOutButton);

		// Add sign-out button panel to the frame
		add(signOutPanel, BorderLayout.NORTH);

		// Add tabbed pane to content pane
		//	add(tabbedPane, BorderLayout.CENTER);

		signOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Call the signOut method in LoginPage to switch to the login page
				loginPage.signOut();
			}
		});
	}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            dashBoard dashboard = new dashBoard(login);
            JFrame frame = new JFrame();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(dashboard);
            frame.pack();
            frame.setVisible(true);
           // dashboard.setVisible(true);
           
        });
    }

}

