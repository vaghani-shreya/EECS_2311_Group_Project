package Favourites;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import front.*;

public class FavouritesPage extends JPanel {
	
	String[] filterNames = {"Name", "Length", "Genre", "Date Added", "Rating", "Release Date"};
	JComboBox filterList = new JComboBox(filterNames);
	
//	JSearchBar search = new JSearchBar();
	
	static DefaultTableModel favouritesTableModel;
	static JTable favouritesTable;
	static JTable table;

	public FavouritesPage(LoginPage login) {
		
		// Retrieves Favourites list with filter from the database
		FavouritesPageDatabaseHandler fpdbHandler = new FavouritesPageDatabaseHandler();
//		tableModel = new DefaultTableModel(filterNames[filterList.getSelectedIndex()], filterNames);
//		table = new JTable(tableModel);
		JTable list = new JTable(fpdbHandler.retrieveFavouritesList(filterNames[filterList.getSelectedIndex()]), filterNames);
		list.setBounds(10, 20, 200, 100);
		
		// Adding Text and Buttons
		JButton refreshPageButton = new JButton("Refresh Page");
		JPanel tab4 = new JPanel();
		tab4.add(new JLabel("Sorted By:"));
		tab4.setLayout(null);
		
		// Adds drop down menu for filtering
		tab4.add(filterList);
		
		// Refresh Button Does Not Work
		tab4.add(refreshPageButton);
		
		// Adds list to tab
		tab4.add(list);
		
//		tabbedPane.add(tab4, "Favourites");
		
	    // Refresh Page Button's action
	    refreshPageButton.addActionListener(new ActionListener() {
	   
	   	 @Override 
	   	 public void actionPerformed(ActionEvent e) {
	   		 fpdbHandler.retrieveFavouritesList(filterNames[filterList.getSelectedIndex()]);
	   	 }
	   	 
	    });	
		
	}
	
	public static void main(String[] args) {
		
		JPanel panel = new JPanel();
		JFrame frame = new JFrame();
		
		FavouritesPageDatabaseHandler fpdbHandler = new FavouritesPageDatabaseHandler();
		
		frame.setSize(2000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		panel.setLayout(null);
		
		JLabel sortedBy = new JLabel("Sorted By:");
		sortedBy.setBounds(100, 100, 100, 50);
		panel.add(sortedBy);
		
		String[] filterNames = {"Name", 
				"Length", 
				"Genre", 
				"Date Added", 
				"Rating", 
				"Release Date"};
		
		JComboBox<String> filterList = new JComboBox<String>(filterNames);
		filterList.setBounds(175, 100, 100, 50);
		panel.add(filterList);
		
		table = new JTable(fpdbHandler.retrieveFavouritesList(filterNames[filterList.getSelectedIndex()]), filterNames);
		table.setBounds(500, 100, 1000, 1000);
		panel.add(table);
		
		
		
//		favouritesTableModel = new DefaultTableModel(fpdbHandler.retrieveFavouritesList(filterNames[filterList.getSelectedIndex()]), filterNames);
//		favouritesTable = new JTable(favouritesTableModel);
//		favouritesTable.setBounds(500, 100, 1000, 1000);
//		panel.add(favouritesTable);
		
		JScrollPane scrollPane = new JScrollPane(favouritesTable);
		panel.add(scrollPane);
		
		JButton refreshButton = new JButton("Refresh Table");
		refreshButton.setBounds(100, 200, 250, 50);
		panel.add(refreshButton);
		

		
	    refreshButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Fetch data from JDBC and refresh the table
        	
        	fpdbHandler.retrieveFavouritesList(filterNames[filterList.getSelectedIndex()]);
//        	fpdbHandler.refreshTable(filterNames[filterList.getSelectedIndex()]);
        }
    });
		
//		dashBoard dashBoardInstance = new dashBoard(null);
//		dashBoardInstance.add(panel);
	    
		frame.setVisible(true);
		
	}
	
//	public class RefreshTableJDBCExample {
//
//	    private static JTable table;
//	    private static DefaultTableModel tableModel;
//
//	    public static void main(String[] args) {
//	        JFrame frame = new JFrame("Refresh Table from JDBC Example");
//	        frame.setSize(400, 300);
//	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//	        // Create the table model and table
//	        tableModel = new DefaultTableModel();
//	        table = new JTable(tableModel);
//
//	        // Create a scroll pane for the table
//	        JScrollPane scrollPane = new JScrollPane(table);
//
//	        // Create the refresh button
//	        JButton refreshButton = new JButton("Refresh Table");
//
//	        // Set the action for the refresh button
//	        refreshButton.addActionListener(new ActionListener() {
//	            @Override
//	            public void actionPerformed(ActionEvent e) {
//	                // Fetch data from JDBC and refresh the table
//	                performJDBCRefresh();
//	            }
//	        });
//
//	        // Create a panel to hold the components
//	        JPanel panel = new JPanel();
//	        panel.add(scrollPane);
//	        panel.add(refreshButton);
//
//	        // Add the panel to the frame
//	        frame.getContentPane().add(panel);
//
//	        // Make the frame visible
//	        frame.setVisible(true);
//	    }
//	    
//	    public void performJDBCRefresh() {
//	
//    	try {
//    		// Load the JDBC driver
//    		Class.forName("org.sqlite.JDBC");
//
//    		// Connect to the database
//    		Connection conn = DriverManager.getConnection(path);
//
//    		// Execute a query to fetch data
//   		String query = "SELECT * FROM FavouriteMovies ORDER BY " + filter + ";";
//  		PreparedStatement pstmt = conn.prepareStatement(query);
//    		ResultSet resultSet = pstmt.executeQuery();
//
//    		// Clear the existing data in the table model
//    		tableModel.setRowCount(0);
//
//    		// Populate the table model with new data from the result set
//   		while (resultSet.next()) {
//       		Object[] rowData = {
//                	resultSet.getString("column1"),
//                	resultSet.getInt("column2"),
//                	resultSet.getString("column3")
//                	// Add more columns as needed
//        		};
//        	tableModel.addRow(rowData);
//    		}
//
//    		// Close resources
//    		resultSet.close();
//    		statement.close();
//    		connection.close();
//			} 
//		catch (ClassNotFoundException | SQLException e) {
//   	 		e.printStackTrace();
//		}
//	}
//
//	}

}
