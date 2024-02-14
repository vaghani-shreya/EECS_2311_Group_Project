import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    public class netflix {
        public static void main(String[] args) {
            // TODO Auto-generated method stub
            netflix n1 = new netflix();
            n1.netflix_data();
    
        }
    public void netflix_data() {


    String path = "jdbc:sqlite:database/netflix.db";
    String query = "SELECT * FROM netflix data;";

    try {Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(path);
            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet resultSet = pstmt.executeQuery();


            while (resultSet.next()) {
                String id = resultSet.getString("show_id");
                String title = resultSet.getString("title");

                System.out.println("Show_id: " + id + ", title: " + title);
                // You can add more details here if needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
    
}
}
