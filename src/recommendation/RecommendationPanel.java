package recommendation;

import javax.swing.*;
import java.awt.*;
import front.DatabaseHandler;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RecommendationPanel extends JPanel {
	    private RecommendationService recommendationService;
	    private static final int COLUMNS = 4;

	    public RecommendationPanel(DatabaseHandler dbHandler, String username) {
	    	super(new BorderLayout());
	        this.recommendationService = new RecommendationService(dbHandler);
	        initUI(username);
	    }

	    private void initUI(String username) {
	    	
	    	List<Recommendation> recommendations = recommendationService.getRecommendations(username);
	        if (recommendations.isEmpty()) {
//	        	System.out.println("No recommendations found.");
	        	return;
	        }
	        
	        JPanel columnsPanel = new JPanel(new GridLayout(1, COLUMNS));
	        for(int i=0; i<COLUMNS;i++) {
	        	JPanel column = new JPanel();
	        	column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
	        	columnsPanel.add(column);
	        }
	        
	        // Add columnsPanel to JScrollPane
	        JScrollPane scrollPane = new JScrollPane(columnsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	        this.add(scrollPane, BorderLayout.CENTER); 

	        // assign rec to each column
	        int columnIndex = 0;
	        TMDBFetcher fetcher = new TMDBFetcher();
	        for(Recommendation rec: recommendations) {
	        	
	        	// display images
	        	String coverImageUrl = fetcher.fetchCoverPageUrl(rec.getShowName()).orElse(null);
	        	// if no images for one rec, move on to next
	        	if (coverImageUrl != null) {
	        		int showId = fetcher.fetchShowId(rec.getShowName()).orElse(-1);
	        		String review = fetcher.fetchReview(showId).orElse("No review found.");
	        		
	                try {
	                    ImageIcon icon = new ImageIcon(new URL(coverImageUrl));
	                    JLabel label = new JLabel(icon);
	                    
	                    int rating = rec.getRating(); 
	    	            
	    	            label.addMouseListener(new MouseAdapter() {
	    	                @Override
	    	                public void mouseEntered(MouseEvent e) {
	    	                    JPopupMenu popupMenu = new JPopupMenu();
	    	                    popupMenu.add(new JMenuItem("Rating: " + rating));
	    	                    popupMenu.add(new JMenuItem("Review: " + review));
	    	                    popupMenu.show(label, label.getWidth() - popupMenu.getPreferredSize().width, label.getHeight());
	    	                }

	    	                @Override
	    	                public void mouseExited(MouseEvent e) {}
	    	            });
	    	            
	                    ((JPanel)columnsPanel.getComponent(columnIndex % COLUMNS)).add(label);
	                    columnIndex++;
	                    
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        	
	        	// mouse over review n rating
	        	
	            
	           
	        }
	        
	        columnsPanel.revalidate();
	        columnsPanel.repaint();
	        
	        
	    }
}


