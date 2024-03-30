package rating;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import front.Amazon;
import front.Disney;
import front.LoginPage;
import front.netflix;

import javax.swing.JOptionPane;


public class ratings  extends JFrame {
	
	
	
	public ratings() {
		
		setSize(900, 900);
		setLayout(new BorderLayout());
				
		JPanel tab1 = new JPanel();
		JTabbedPane subTabbedPanel = new JTabbedPane();
		tab1.setLayout(new BorderLayout());
			
		ratingPanel net =  ratingNetflix.getInstance();
		ratingPanel net2 = ratingAmazon.getInstance();
		ratingPanel  net3 = ratingDisney.getInstance();
		//ratingNetflix net = ratingNetflix.getInstance();
		//ratingAmazon net2 = ratingAmazon.getInstance();
		//ratingDisney net3 = ratingDisney.getInstance();
		 
		subTabbedPanel.addTab("Discover on Netflix",net);
		subTabbedPanel.addTab("Discover on Amazon",net2);
		subTabbedPanel.addTab("Discover on Disney",net3);
//		tab1.add(subTabbedPanel);
//		maintabbedPane.addTab("Discover on Netflix", subTabbedPanel);
		add(subTabbedPanel, BorderLayout.CENTER);
		
	}
	
	

	

	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new ratings().setVisible(true);
	            }
	        });
	    }

}
