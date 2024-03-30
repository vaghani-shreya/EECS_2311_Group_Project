package front;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import analytics.chartAnalytics;
import rating.ratings;

public class dashBoard extends JPanel{
	
	private static JButton signOutButton;
	public static dashBoard instance;
	private static LoginPage login;
	private DatabaseHandler dbHandler;
	

	public static dashBoard getInstance() {
		if (instance == null)
			instance = new dashBoard(login);

		return instance;
	}

	public dashBoard(LoginPage loginPage) {
		setSize(900, 900);
		setLayout(new BorderLayout());

		//initialize a databaseHandler instance
		dbHandler = new DatabaseHandler();
		// Create tabbed pane
		JTabbedPane maintabbedPane = new JTabbedPane();

		// Create tabs
		JPanel tab1 = new JPanel();
		JTabbedPane subTabbedPanel = new JTabbedPane();
		tab1.setLayout(new BorderLayout());
		
		BrowsingDashboard net = netflix.getInstance();
		BrowsingDashboard net2 = Disney.getInstance();
		BrowsingDashboard net3 = Amazon.getInstance();
		
		subTabbedPanel.addTab("Discover on Netflix",net);
		subTabbedPanel.addTab("Discover on Disney",net2);
		subTabbedPanel.addTab("Discover on Amazon",net3);
		tab1.add(subTabbedPanel);
		maintabbedPane.addTab("Dashboard", tab1);
		add(maintabbedPane, BorderLayout.CENTER);
		
		
		JPanel tab3 = new JPanel();
		tab3.add(new JLabel("User can rate here"));
		tab3.setLayout(new BorderLayout());
		ratings rate = new ratings();
		tab3.add(rate.getRootPane());
		maintabbedPane.add("User Ratings", tab3);
		add(maintabbedPane, BorderLayout.CENTER);
		
		JPanel tab4 = new JPanel();
		Favourites fav = new Favourites();
		tab4.add(fav.getContentPane());

		JPanel tab5 = new JPanel();
		chartAnalytics ratingChart = new chartAnalytics();
		tab5.add(ratingChart.getContentPane());

		maintabbedPane.addTab("Ratings", tab3);
		maintabbedPane.addTab("Favourites", tab4);
		maintabbedPane.addTab("Analytics", tab5);

		signOutButton = new JButton("Sign Out");

		JPanel signOutPanel = new JPanel();
		signOutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		signOutPanel.add(signOutButton);

		// Add sign-out button panel to the frame
		add(signOutPanel, BorderLayout.NORTH);

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
			

		});
	}

}

