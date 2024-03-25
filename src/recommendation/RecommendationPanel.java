package recommendation;
import javax.swing.*;
import java.awt.*;
import front.DatabaseHandler;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// image size adjusting
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

// monitor window size
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class RecommendationPanel extends JPanel {
	    private RecommendationService recommendationService;
	    private static final int COLUMNS = 4;
	    private JPanel columnsPanel;
	    private List<Recommendation> recommendations;
	    
	    public RecommendationPanel(DatabaseHandler dbHandler, String username) {
	    	super(new BorderLayout());
	        this.recommendationService = new RecommendationService(dbHandler);
	        this.recommendations = recommendationService.getRecommendations(username);
	        initComponents();
	        addComponentListener(new ComponentAdapter() {
	            @Override
	            public void componentResized(ComponentEvent e) {
	                displayImages(); 
	            }
	        });
	    }

	    public void initComponents() {
	    	
	    	if (recommendations.isEmpty()) {
	        	return;
	        }
	        
	    	columnsPanel = new JPanel(new GridLayout(1, COLUMNS));
	        for(int i=0; i<COLUMNS;i++) {
	        	JPanel column = new JPanel();
	        	column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
	        	columnsPanel.add(column);
	        }
	        
	        // Add columnsPanel to JScrollPane
	        JScrollPane scrollPane = new JScrollPane(columnsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	        add(scrollPane, BorderLayout.CENTER); 

	    }
	    
	    public void displayImages() {
	    	
	    	// every time the window size changes
	    	// clear out what was displayed before then resizing
	    	for (Component column : columnsPanel.getComponents()) {
	            ((JPanel) column).removeAll();
	        }
	    	
	    	int windowWidth = getWidth(); 
	        int resizedImgWidth = windowWidth / COLUMNS; 

	    	
	    	int columnIndex = 0;
	    	TMDBFetcher fetcher = new TMDBFetcher();
	        for(Recommendation rec: recommendations) {
	        	
	        	// display images
	        	String coverImageUrl = fetcher.fetchCoverPageUrl(rec.getShowName()).orElse(null);
	        	
	        	// if no images for one rec, move on to next
	        	if (coverImageUrl != null) {
	        		int showId = fetcher.fetchShowId(rec.getShowName()).orElse(-1);
	        		String review = fetcher.fetchReview(showId).orElse("No review found.");
	        		Image resizedImage = null; 
	        		
	        		
                	BufferedImage originalImage = null;
//                	BufferedImage enlargedImage = null;
                	
	                try {
                	
	                	// original size
	                	originalImage = ImageIO.read(new URL(coverImageUrl));
	                	
	                	if (originalImage != null) {	                	
	                		
	                        int width = originalImage.getWidth();
	                        int height = originalImage.getHeight();                       
	                       
	                        // resize after checking the original dimensions and targetWidth are non-zero
	                        if (width > 0 && height > 0 && windowWidth > 0) {
	                            int resizedImgHeight = (resizedImgWidth * height) / width;
	                            resizedImage = originalImage.getScaledInstance(resizedImgWidth, resizedImgHeight, Image.SCALE_SMOOTH);	                  
//	                            enlargedImage = enlarge(resizedImage);
			                	
	                        } else {
	                            // when dimensions are invalid
	                            System.err.println("Invalid dimensions for image resize.");
	                            continue;
		                    }
	                    } else {
	                        // The image could not be loaded.
	                        System.err.println("Image could not be loaded: " + coverImageUrl);
	                        continue;
	                    }
                	
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    continue;
	                }
	                	
                if (resizedImage != null) {
                    JLabel label = new JLabel(new ImageIcon(resizedImage));
                    ((JPanel)columnsPanel.getComponent(columnIndex % COLUMNS)).add(label);
//                    ImageIcon enlargedIcon = new ImageIcon(enlargedImage);
                    columnIndex++;
                    
//	                    int rating = rec.getRating(); 
                    int releaseYear = rec.getReleaseYear(); 
    	                               
                    // mouse over review n rating
    	        	label.addMouseListener(new MouseAdapter() {
    	                @Override
    	                public void mouseEntered(MouseEvent e) {
    	                	
//    	                	label.setIcon(enlargedIcon);
    	                    JPopupMenu popupMenu = new JPopupMenu();
//	    	                    popupMenu.add(new JMenuItem("Rating: " + rating));
    	                    popupMenu.add(new JMenuItem("Release Year: " + releaseYear));
    	                    popupMenu.add(new JMenuItem("Review: " + review));
    	                    popupMenu.show(label, label.getWidth() - popupMenu.getPreferredSize().width, label.getHeight());
    	                }

	                @Override
	                public void mouseExited(MouseEvent e) {}
    	                
    	            });	    	           
	                    
	                }
	            }
	        }
	        
	        columnsPanel.revalidate();
	        columnsPanel.repaint();
	        
	        
	    }
	    
//	    public BufferedImage enlarge(BufferedImage originalImage) {
	        
//	        int newWidth = (int) (originalImage.getWidth() * 1.05);
//	        int newHeight = (int) (originalImage.getHeight() * 1.05);
//	        
//	        Image enlargedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
//	        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
//
//	        Graphics2D g2d = resizedImage.createGraphics();
//	        
//	        g2d.drawImage(enlargedImage, 0, 0, null);
//	        g2d.dispose();
//
//	        return resizedImage;
//	    }
}



