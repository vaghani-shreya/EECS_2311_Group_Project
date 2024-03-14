package recommendation;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import front.DatabaseHandler;

public class RecommendationPanel extends JPanel {
	    private JTabbedPane tabbedPane;
	    private RecommendationService recommendationService;

	    public RecommendationPanel(JTabbedPane tabbedPane, DatabaseHandler dbHandler, String username) {
	    	super(new BorderLayout());
	    	this.tabbedPane = tabbedPane;
	        this.recommendationService = new RecommendationService(dbHandler);
	        initUI(username);
	    }

	    private void initUI(String username) {
	        Object[][] recommendationsData = recommendationService.getRecommendations(username);
	        String[] columnNames = {"Title", "Rating", "Description"};

	        JTable recommendationsTable = new JTable(recommendationsData, columnNames);
	        recommendationsTable.setDefaultEditor(Object.class, null); // Disable cell editing
//	        recommendationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//	        recommendationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	        TableColumnModel columnModel = recommendationsTable.getColumnModel();
	        columnModel.getColumn(0).setPreferredWidth(20); // title column width
	        columnModel.getColumn(1).setPreferredWidth(20); // genre column width
	        columnModel.getColumn(2).setPreferredWidth(600);
	        
	        JScrollPane scrollPane = new JScrollPane(recommendationsTable);
	        add(scrollPane, BorderLayout.CENTER);

	        
	    }
}

