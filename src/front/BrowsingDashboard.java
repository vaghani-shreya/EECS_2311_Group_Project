package front;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public abstract class BrowsingDashboard extends JPanel{
	    protected JPanel showPanel;
	    protected JScrollPane scrollPane;
	    protected JComboBox<String> filterComboBox;
	    protected JComboBox<String> sortComboBox;

	    protected abstract void initComponents();
	    protected abstract void Database();
	    protected abstract void searchDatabase(String searchFor, String filterBy);
	    protected abstract void sortDatabase(String sortBy);
	    protected abstract void showDetails(String showId, String title, String dateAdded, String releaseYear, String director, String description, String cast, String date_added);	 
}