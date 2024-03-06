package front;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.category.DefaultCategoryDataset;

public class netflix_trial {
	public List<String> listOfRating;
	public Map<String, Integer> ratingCountMap;
	

	public netflix_trial() {
		ratingCountMap = new HashMap<>();
		
	}

	public void netflix_data() {
		//	 listOfRating = new ArrayList<>();
		//	 ratingCountMap = new HashMap<>();


		String path = "jdbc:sqlite:database/Netflix.db";
		String query = "SELECT * FROM netflix_titles;";

		try {Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection(path);
		PreparedStatement pstmt = conn.prepareStatement(query);

		ResultSet resultSet = pstmt.executeQuery();


		while (resultSet.next()) {
			String id = resultSet.getString("show_id");
			String title = resultSet.getString("title");
			String rating = resultSet.getString("rating");
			listOfRating.add(rating);
			ratingCountMap.put(rating, ratingCountMap.getOrDefault(rating, 0) + 1);

			System.out.println("Show_id: " + id + ", title: " + title + "rating" + rating);
			//System.out.println(listOfRating.size());
			// You can add more details here if needed
		}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}


	}
	

		





	public static void main(String[] args) {
		// TODO Auto-generated method stub
		netflix_trial n1 = new netflix_trial();

	}

}
