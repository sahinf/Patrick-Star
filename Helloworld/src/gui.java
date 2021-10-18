import java.awt.EventQueue;

import javax.swing.JFrame;
import java.lang.Throwable;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import javax.sound.midi.SysexMessage;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Vector;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Button;
import javax.swing.JTabbedPane;
import javax.swing.JRadioButton;

public class gui {
	
	public String startDateViewer;
	public String endDateViewer;
	public String startDateAna;
	public String endDateAna;
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	Connection connection = null;
	/**
	 * Create the application.
	 */
	public gui() {
		connection = dbConnector();
		initialize();
		
	}
	
	public static final String user = "csce315_913_3_user";
	  public static final String pswd = "sikewrongnumber";
//static //Building the connection
 Connection conn = null;
	
	public Connection dbConnector() {
		try {
	        Class.forName("org.postgresql.Driver");
	        
	        conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_3_db",
	           user, pswd);
	        JOptionPane.showMessageDialog(null,"Opened database successfully");
	        return conn;
	     } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        return null;
	     }//end try catch
		
	}
	
	public String getTopContent(String startDate,String endDate, String user) {
		 String cus_lname = "<html>";
		 String customerid = "";
		  //startDate  = "2005-09-06";
		  //endDate  = "2005-09-15";
		 if (user.equals("")) {
			 customerid="";
		 }
		 else {
			 customerid = " and ratings.customerid = '"+user+"'";
		 }
	     try{
	     //create a statement object
	       Statement stmt = connection.createStatement();
	       //create an SQL statement
	       String sqlStatement = "select ratings.titleid,titles.originaltitle,count(ratings.titleid) as total"
	       		+ " from ratings"
	       		+ " left join titles on ratings.titleid = titles.titleid"
	       		+ " where ratings.date > '"+startDate +"' and ratings.date <= '"+endDate+"'"+customerid
	       		+ " group by ratings.titleid,titles.originaltitle"
	       		+ " order by total desc"
	       		+ " limit 10;"; // change
	       //send statement to DBMS
	       ResultSet result = stmt.executeQuery(sqlStatement);

	       //OUTPUT
	       //JOptionPane.showMessageDialog(null,"something bout crew.");
	       //System.out.println("______________________________________");
	       while (result.next()) {
	         //System.out.println(result.getString("cus_lname"));
	         cus_lname += result.getString("originaltitle")+"<br>"; // change
	         //cus_lname += result.getString("total")+"<br>";
	       }
	       cus_lname += "</html>";
	       //JOptionPane.showMessageDialog(null,cus_lname);
	       return cus_lname;
	   } catch (Exception e){
	     //JOptionPane.showMessageDialog(null,e);
	     return "error in accessing data";
	   }
	}
	
	public String getWatchHistory(String startDate, String endDate,String user) {
		String cus_lname = "<html>";
		 
		System.out.println(startDate);
		System.out.println(endDate);
	     try{
	     //create a statement object
	       Statement stmt = connection.createStatement();
	       //create an SQL statement
	       String sqlStatement = "select ratings.titleid,titles.originaltitle, ratings.date"
	       		+ " from ratings"
	       		+ " left join titles on ratings.titleid = titles.titleid"
	       		+ " where ratings.date > '"+startDate +"' and ratings.date <= '"+endDate+"' and ratings.customerid = '"+user+"'"
	       		+ " order by ratings.date desc;"; // change
	       //send statement to DBMS
	       ResultSet result = stmt.executeQuery(sqlStatement);

	       //OUTPUT
	       //JOptionPane.showMessageDialog(null,"something bout crew.");
	       //System.out.println("______________________________________");
	       while (result.next()) {
	         //System.out.println(result.getString("cus_lname"));
	         cus_lname += result.getString("originaltitle")+"<br>"; // change
	         //cus_lname += result.getString("total")+"<br>";
	       }
	       cus_lname += "</html>";
	       //JOptionPane.showMessageDialog(null,cus_lname);
	       return cus_lname;
	   } catch (Exception e){
	     //JOptionPane.showMessageDialog(null,e);
	     return "error in accessing data";
	   }
		
		
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.PINK);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(928, 25, 78, 21);
		frame.getContentPane().add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(1215, 10, 46, 51);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("User Id");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(1121, 28, 51, 13);
		frame.getContentPane().add(lblNewLabel_1);

		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(217, 80, 968, 626);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Viewer", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(353, 124, 266, 329);
		panel_1.add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		JButton btnNewButton_1 = new JButton("Watch History");
		btnNewButton_1.setBounds(394, 66, 175, 46);
		panel_1.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				DefaultListModel DLM = new DefaultListModel();
//				DLM.addElement("Minions");
//				DLM.addElement("Better Call Saul");
//				DLM.addElement("Arrow");
//				DLM.addElement("Italian Job");
//				list.setModel(DLM);
//				System.out.println(textField_3_string);
//				textField_1_string = textField_1.getText();
//				System.out.println(textField_1_string);
//				textField_2_string = textField_2.getText();
//				System.out.println(textField_2_string);
//				textField_3_string = textField_3.getText();
//				System.out.println(textField_3_string);
			}
		});
		
		
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setActionCommand("");
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Last Month");
		rdbtnNewRadioButton.setBounds(631, 144, 141, 23);
		panel_1.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateViewer = "2005-11-30";
				endDateViewer = "2005-12-31";
			}
		});
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Last 3 Months");
		rdbtnNewRadioButton_1.setBounds(631, 177, 141, 23);
		panel_1.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateViewer = "2005-09-30";
				endDateViewer = "2005-12-31";
				System.out.println(startDateViewer);
			}
		});
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Last 6 Months");
		rdbtnNewRadioButton_2.setBounds(631, 210, 141, 23);
		panel_1.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateViewer = "2005-06-30";
				endDateViewer = "2005-12-31";
			}
		});
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Last Year");
		rdbtnNewRadioButton_3.setBounds(631, 243, 141, 23);
		panel_1.add(rdbtnNewRadioButton_3);
		rdbtnNewRadioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateViewer = "2004-12-31";
				endDateViewer = "2005-12-31";
			}
		});
		
		JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("All Time");
		rdbtnNewRadioButton_4.setBounds(631, 278, 141, 23);
		panel_1.add(rdbtnNewRadioButton_4);
		rdbtnNewRadioButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateViewer = "1999-12-31";
				endDateViewer = "2005-12-31";
			}
		});
		
		JButton btnNewButton_2 = new JButton("Submit");
		btnNewButton_2.setBounds(641, 313, 117, 29);
		panel_1.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel viewerSubmit = new DefaultListModel();
				String userWatchHistory = getWatchHistory(startDateViewer,endDateViewer, textField_1.getText());
				viewerSubmit.addElement(userWatchHistory);
				list.setModel(viewerSubmit);
			}
		});
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		tabbedPane.addTab("Analytics", null, panel_1_1, null);
		
		JList list_1 = new JList();
		list_1.setBounds(351, 127, 265, 329);
		panel_1_1.add(list_1);
		
		JButton btnNewButton_2_1 = new JButton("Submit");
		btnNewButton_2_1.setBounds(628, 314, 117, 29);
		panel_1_1.add(btnNewButton_2_1);
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				DefaultListModel DLM = new DefaultListModel();
//				DLM.addElement("Minions");
//				DLM.addElement("Better Call Saul");
//				DLM.addElement("Arrow");
//				DLM.addElement("Italian Job");
//				list.setModel(DLM);
//				System.out.println(textField_3_string);
				
				String AllUsersList = getTopContent(startDateAna,endDateAna,"");
				DefaultListModel DLM2 = new DefaultListModel();
				DLM2.addElement(AllUsersList);
				//DLM2.addElement(textField_5_string);
				//DLM2.addElement(textField_6_string);
				
				list_1.setModel(DLM2);
			}
		});
		
		JButton btnNewButton_1_1 = new JButton("Top 10 Movies From All Users");
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1_1.setActionCommand("");
		btnNewButton_1_1.setBounds(370, 68, 246, 46);
		panel_1_1.add(btnNewButton_1_1);
		
		JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("Last Month");
		rdbtnNewRadioButton_5.setBounds(628, 137, 141, 23);
		panel_1_1.add(rdbtnNewRadioButton_5);
		rdbtnNewRadioButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateAna = "2005-11-30";
				endDateAna = "2005-12-31";
			}
		});
		
		JRadioButton rdbtnNewRadioButton_6 = new JRadioButton("Last 3 Months");
		rdbtnNewRadioButton_6.setBounds(628, 172, 141, 23);
		panel_1_1.add(rdbtnNewRadioButton_6);
		rdbtnNewRadioButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateAna = "2005-09-30";
				endDateAna = "2005-12-31";
			}
		});
		
		
		JRadioButton rdbtnNewRadioButton_7 = new JRadioButton("Last 6 Months");
		rdbtnNewRadioButton_7.setBounds(628, 207, 141, 23);
		panel_1_1.add(rdbtnNewRadioButton_7);
		rdbtnNewRadioButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateAna = "2005-06-30";
				endDateAna = "2005-12-31";
			}
		});
		
		JRadioButton rdbtnNewRadioButton_8 = new JRadioButton("Last Year");
		rdbtnNewRadioButton_8.setBounds(628, 242, 141, 23);
		panel_1_1.add(rdbtnNewRadioButton_8);
		rdbtnNewRadioButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateAna = "2004-12-31";
				endDateAna = "2005-12-31";
			}
		});
		
		JRadioButton rdbtnNewRadioButton_9 = new JRadioButton("All Time");
		rdbtnNewRadioButton_9.setBounds(628, 277, 141, 23);
		panel_1_1.add(rdbtnNewRadioButton_9);
		rdbtnNewRadioButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateAna = "1999-12-31";
				endDateAna = "2005-12-31";
			}
		});
		
		JLabel lblNewLabel = new JLabel("PATNIX");
		lblNewLabel.setBounds(40, 6, 194, 76);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		
		textField = new JTextField();
		textField.setBounds(801, 23, 115, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("User ID");
		lblNewLabel_2.setBounds(86, 115, 61, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(56, 143, 130, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		frame.setBounds(100, 100, 1284, 759);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
