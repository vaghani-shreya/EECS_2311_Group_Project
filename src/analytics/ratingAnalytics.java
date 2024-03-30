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
   
    public ratingAnalytics() {
        JPanel containerPanel = new JPanel(new GridLayout(1, 3));

        String[] paths = {
                "jdbc:sqlite:database/Netflix.db",
                "jdbc:sqlite:database/Amazon.db",
                "jdbc:sqlite:database/Disney.db"
        };

        String[] queries = {
                "SELECT rating, COUNT(*) AS count FROM (SELECT rating FROM netflix_titles ORDER BY release_year DESC LIMIT 10) AS Aquery GROUP BY rating;",
                "SELECT rating, COUNT(*) AS count FROM (SELECT rating FROM amazon_prime_titles ORDER BY release_year DESC LIMIT 10) AS Aquery GROUP BY rating;",
                "SELECT rating, COUNT(*) AS count FROM (SELECT rating FROM disney_plus_titles ORDER BY release_year DESC LIMIT 10) AS Aquery GROUP BY rating;"
        };

        String[] titles = {"Netflix", "Amazon Prime", "Disney Plus"};

        for (int i = 0; i < queries.length; i++) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            try {
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection(paths[i]);
                PreparedStatement pstmt = conn.prepareStatement(queries[i]);
                ResultSet resultSet = pstmt.executeQuery();

                while (resultSet.next()) {
                    String categories = resultSet.getString("rating");
                    int count = resultSet.getInt("count");
                    if (categories == null || categories.equals("66 min") || categories.equals("74 min") || categories.equals("84 min")) {
                        continue;
                    }
                    dataset.addValue(count, "Number of TV Shows/Movies", categories);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            JFreeChart chart = ChartFactory.createBarChart(titles[i], "Category", "Media Number", dataset);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(500, 700));
            containerPanel.add(chartPanel);
        }

        JScrollPane scrollPanel = new JScrollPane(containerPanel);
        setContentPane(scrollPanel);

        setTitle("Rating Analysis");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1000);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ratingAnalytics();
            frame.setVisible(true);
        });
    }
}




