package front;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.*;

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

		// Create tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();
		//   cardLayout = new CardLayout();

		// Create tabs
		JPanel tab1 = new JPanel();
		tab1.add(new JLabel("This is our main dashboard"));

		JPanel tab2 = new JPanel();
		tab2.add(new JLabel("These are the recommendations for the user"));

		JPanel tab3 = new JPanel();
		tab3.add(new JLabel("User can rate here"));
		
/******************************************************************************************************************************/
		
// Favourites Page
		
		// Retrieves Favourites list with filter from the database
		DatabaseHandler dbHandler = new DatabaseHandler();
		JTable list = new JTable(dbHandler.retrieveFavouritesList(filterNames[filterList.getSelectedIndex()]), filterNames);
		
		// Adding Text and Buttons
		JButton refreshPageButton = new JButton("Refresh Page");
		JPanel tab4 = new JPanel();
		tab4.add(new JLabel("Sorted By:"));
		
		// Adds drop down menu for filtering
		tab4.add(filterList);
		
		// Refresh Button Does Not Work
		tab4.add(refreshPageButton);
		
		// Adds list to tab
		tab4.add(list);
		
//		tabbedPane.add(tab4, "Favourites");
//		
//	    // Refresh Page Button's action
//	    refreshPageButton.addActionListener(new ActionListener() {
//	   
//	   	 @Override 
//	   	 public void actionPerformed(ActionEvent e) {
//	   		 tabbedPane.show();
//	   	 }
//	   	 
//	    });		
		
/******************************************************************************************************************************/	

		JPanel tab5 = new JPanel();
		tab5.add(new JLabel("User's Analytics are displayed here"));

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
		add(tabbedPane, BorderLayout.CENTER);

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

