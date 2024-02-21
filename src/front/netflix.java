package front;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class netflix extends JFrame {
    private JPanel showPanel;
    private JScrollPane scrollPane;

    public netflix() {
        setTitle("Netflix Discover Weekly");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        //create a search panel
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        // create search button
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add action listener to the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchFor = searchField.getText();
                searchNetflixDatabase(searchFor);
            }
        });

        add(searchPanel, BorderLayout.NORTH);

        scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        showPanel = new JPanel();
        showPanel.setLayout(new BoxLayout(showPanel, BoxLayout.Y_AXIS));
        scrollPane.setViewportView(showPanel);

        NetflixDataBase();
    }
    // Allows the user to see the shows in descending order of release year
    private void NetflixDataBase() {
        String path = "jdbc:sqlite:database/Netflix.db";
     // extract data from netflix database by descending order in terms of release year
        String query = "SELECT * FROM netflix_titles ORDER BY release_year DESC;"; 

        try {
        	//call the JDBC driver
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(path);
            Statement stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(query);
          //loop through all data required from the database
            while (resultSet.next()) {
                String id = resultSet.getString("show_id");
                String title = resultSet.getString("title");
                String dateAdded = resultSet.getString("date_added");
                String releaseYear = resultSet.getString("release_year");
                String director = resultSet.getString("director");
                String cast = resultSet.getString("cast");
                String description = resultSet.getString("description");
                String date_added = resultSet.getString("date_added");
              //prints the specified show / movie and the corresponding information
                JLabel showLabel = new JLabel("Show ID: " + id + ", Title: " + title + ", Date Added: " + dateAdded + ", Release Year: " + releaseYear);
              showLabel.addMouseListener(new MouseAdapter() {
              @Override
              public void mouseClicked(MouseEvent e) {
                  showDetails(id, title, dateAdded, releaseYear,director, description, cast, date_added);
              }
          });
                showLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                showLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                showPanel.add(showLabel);
                showPanel.add(Box.createVerticalStrut(10)); // Add vertical spacing between shows
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Allows the user to search for a specific show/movie
    private void searchNetflixDatabase(String searchFor) {
        showPanel.removeAll(); // Clear existing shows/movies

        String path = "jdbc:sqlite:database/Netflix.db";
        //Finds the specified title and extracts from database
        String query = "SELECT * FROM netflix_titles WHERE title LIKE ?;";

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(path);
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + searchFor + "%");

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("show_id");
                String title = resultSet.getString("title");
                String dateAdded = resultSet.getString("date_added");
                String releaseYear = resultSet.getString("release_year");
                String director = resultSet.getString("director");
                String cast = resultSet.getString("cast");
                String description = resultSet.getString("description");
                String date_added = resultSet.getString("date_added");
                //prints the specified show / movie and the corresponding information
            
                JLabel showLabel = new JLabel("ID: " + id + ", Title: " + title + ", Date Added: " + dateAdded + ", Release Year: " + releaseYear);
                showLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        showDetails(id, title, dateAdded, releaseYear, director, description, cast, date_added);
                    }
                });
                
                showLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                showLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                showPanel.add(showLabel);
                showPanel.add(Box.createVerticalStrut(10)); 
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        showPanel.revalidate(); // Refresh layout
        showPanel.repaint(); // Repaint the panel
    }
    
  private void showDetails(String showId, String title, String dateAdded, String releaseYear, String director, String description, String cast, String date_added) {
  // Open a new page to display more details about a specific show/movie
  JFrame detailsFrame = new JFrame("Show Details");
  JPanel detailsPanel = new JPanel(new GridLayout(0, 1)); // Use a grid layout
  JTextArea detailsTextArea = new JTextArea();
  detailsTextArea.append("Show ID: " + showId + "\n");
  detailsTextArea.append("Title: " + title + "\n");
  detailsTextArea.append("Date Added: " + dateAdded + "\n");
  detailsTextArea.append("Release Year: " + releaseYear + "\n");
  detailsTextArea.append("Director: " + director + "\n");
  detailsTextArea.append("Description : " + description + "\n");
  detailsTextArea.append("Cast: " + cast + "\n");


  detailsPanel.add(detailsTextArea);
  detailsFrame.add(detailsPanel);

  detailsFrame.setSize(300, 200);
  detailsFrame.setLocationRelativeTo(null);
  detailsFrame.setVisible(true);
}



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new netflix().setVisible(true);
                
            }
        });
    }
}


