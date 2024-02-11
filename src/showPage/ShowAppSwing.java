package showPage;
import javax.swing.*;
import java.awt.*;

public class ShowAppSwing {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShowAppSwing::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Show Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        // 添加组件到frame中
        addComponentsToPane(frame.getContentPane());
        frame.setVisible(true);
    }

    private static void addComponentsToPane(Container pane) {
        String showName = "Good Wife";
        String showDescription = "The Good Fight is an American legal and political drama television series produced for CBS's streaming service CBS All Access. It is CBS All Access's first original scripted series. The series—created by Robert King, Michelle King, and Phil Alden Robinson—is a spin-off and sequel to The Good Wife, which was created by the Kings. The first season contains 10 episodes, and premiered on February 19, 2017, with the first episode airing on CBS and the following nine episodes on CBS All Access. The series was initially scheduled to air in May 2017 but was moved up after production delays forced CBS to postpone the premiere of the new series Star Trek: Discovery. The series' second season premiered on March 4, 2018, and its third on March 14, 2019. The series was renewed for a fourth season on April 23, 2019, which premiered on April 9, 2020. The series was renewed for a fifth season on October 28, 2020, which premiered on June 24, 2021. The series was renewed for a sixth season on November 24, 2021.";
        double rating = 4.5;
        String releaseDate = "February 19, 2017";
        String genres = "Legal Drama, Political Drama";

        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(showName);
        JLabel ratingLabel = new JLabel(rating + "/5");
        JLabel releaseDateLabel = new JLabel("Release Date: " + releaseDate);
        JLabel genresLabel = new JLabel("Genres: " + genres);

        JTextArea descArea = new JTextArea(showDescription);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(descArea);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        pane.add(nameLabel);
        pane.add(Box.createRigidArea(new Dimension(0, 5))); // 添加组件之间的间距
        pane.add(ratingLabel);
        pane.add(Box.createRigidArea(new Dimension(0, 5)));
        pane.add(scrollPane);
        pane.add(releaseDateLabel);
        pane.add(genresLabel);
    }
}
