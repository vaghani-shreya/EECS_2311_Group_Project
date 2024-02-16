package analytics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.List;

import front.netflix_trial;

public class ratingAnalytics extends JFrame {

    public ratingAnalytics() {
        netflix_trial dataNetflix = new netflix_trial();
        dataNetflix.netflix_data();

        List<String> ListRatings = dataNetflix.getListOfRating();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < ListRatings.size(); i++) {
            String rating = ListRatings.get(i);
            int count = dataNetflix.getCountOfRating(rating);
            dataset.addValue(count, "Rating", rating);
        }

        JFreeChart chart = ChartFactory.createBarChart("Rating Analysis", "Rating", "Count", dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);

        // Set frame properties
        setTitle("Bar Chart from ArrayList");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ratingAnalytics();
            frame.setVisible(true);
        });
    }
}