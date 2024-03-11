package analytics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import front.LoginPage;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ratingAnalytics extends JFrame {
	private static JButton signOutButton;
	private LoginPage loginPage;
	
	
	public ratingAnalytics() {
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	     
        
        String path = "jdbc:sqlite:database/Netflix.db";
		String query = "SELECT rating,COUNT(*) AS count FROM netflix_titles GROUP BY rating;";

		try {Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection(path);
		PreparedStatement pstmt = conn.prepareStatement(query);

		ResultSet resultSet = pstmt.executeQuery();


		while (resultSet.next()) {
			String rating = resultSet.getString("rating");
            int count = resultSet.getInt("count");
            if(rating == null || rating.equals("66 min") || rating.equals("74 min") || rating.equals("84 min")) {
            	continue;
            }
            dataset.addValue(count, "Count", rating);
            //System.out.println( "rating: " + rating + "  count: " + count);
		
		}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

    	 JFreeChart chart = ChartFactory.createBarChart("Rating Analysis", "Rating", "Count", dataset);

         ChartPanel chartPanel = new ChartPanel(chart);
         setContentPane(chartPanel);

         // Set frame properties
         setTitle("Bar Chart from Database");
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setSize(1200, 1000);
         setLocationRelativeTo(null);
         
         
         
 //    	signOutButton = new JButton("Sign Out");
//
//		JPanel signOutPanel = new JPanel();
//		signOutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
//		signOutPanel.add(signOutButton);
//
//		// Add sign-out button panel to the frame
//		add(signOutPanel, BorderLayout.NORTH);
//
//		// Add tabbed pane to content pane
//		add(tabbedPane, BorderLayout.CENTER);
//
//		signOutButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// Call the signOut method in LoginPage to switch to the login page
//				loginPage.signOut();
//			}
//		});
    	
    }
	
	


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ratingAnalytics();
            frame.setVisible(true);
        });
    }
    
}