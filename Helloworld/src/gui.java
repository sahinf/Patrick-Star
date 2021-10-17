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
	
	public String textField_1_string;
	public String textField_2_string;
	public String textField_3_string;
	public String textField_5_string;
	public String textField_6_string;
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_5;
	private JTextField textField_6;

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
	
	
//	public String getPersonalRec(String userFavGenres) throws Exception {
//		String cus_lname = "<html>";
//		ResultSet result;
//		 
//	     try{
//	     //create a statement object
//	       Statement stmt = connection.createStatement();
//	       //Collecting 4+ ratings from the user
//	       String sqlStatement = "select titles.originaltitle, titles.genres, titles.averagerating, titles.numvotes"
//	       		+ " from titles"
//	       		+ " where titles.genres != '' and titles.originaltitle != 'originalTitle' "
//	       		+ " order by titles.averagerating desc;"; // change
//	       //send statement to DBMS
//	       result = stmt.executeQuery(sqlStatement);
//	       
//	       // titles.averagerating > '7.0' and titles.numvotes > '500' and 
//	       //Generating recommended title list, only looking for titles that have a credible rating.
//	       //Definition of credible rating according to our group: Rating that is 7.0+ AND has more than 50 votes.
//	       String placeholder = "";
//	       Integer aCount = 0;
//	       while (result.next()) {
//	    	   if ((result.getDouble("averagerating") < 7.0) || (result.getInt("numvotes") < 50)) {
//	    		   continue;
//	    	   }
//	    	   placeholder = result.getString("genres");
//	    	   if (placeholder.contentEquals(userFavGenres)) {
//	    		   cus_lname += result.getString("originaltitle") + "<br>";
//	    		   ++aCount;
//	    		   if (aCount == 20) {
//	    			   cus_lname += "</html>";
//	    			   return cus_lname;
//	    			  }
//	    		   continue;
//	    		   }
//	       }
//	       cus_lname += "</html>";
//	       return cus_lname;
//	   } catch (Exception e){
//	     //JOptionPane.showMessageDialog(null,e);
//	     throw new Exception("Error in accessing data");
//	   }
//		
//		
//	}
//	
//	
//	
//	public String curateViewersChoice(String user) throws Exception {
//		String cus_lname = "<html>";
//		Map<String,List<String>> userGenres = new HashMap<>();
//		ResultSet result;
//		 
//	     try{
//	     //create a statement object
//	       Statement stmt = connection.createStatement();
//	       //Collecting 4+ ratings from the user
//	       String sqlStatement = "select ratings4plus.titleid,titles.originaltitle, titles.genres, ratings4plus.rating"
//	       		+ " from ratings4plus"
//	       		+ " left join titles on ratings4plus.titleid = titles.titleid"
//	       		+ " where ratings4plus.customerid = '"+user+"' "
//	       		+ " group by ratings4plus.titleid,titles.originaltitle,titles.genres, ratings4plus.rating"
//	       		+ " order by ratings4plus.rating desc;"; // change
//	       //send statement to DBMS
//	       result = stmt.executeQuery(sqlStatement);
//	       
//	       //Populating hashmap of each title and their genres
//	       while (result.next()) {
//	         userGenres.put(result.getString("titleid"), Arrays.asList(result.getString("genres")));
//	       }
//	       
//	       //Creating a hashmap which collects the frequency of genres in each category.
//		   Map<String, Integer> genreCount = new HashMap<>();
//		   for (Map.Entry<String, List<String> > set : userGenres.entrySet()) {
//			   List<String> placeholder = set.getValue();
//			   for (int i = 0; i < placeholder.size(); ++i) {
//				   if (!genreCount.containsKey(placeholder.get(i))) {
//					   genreCount.put(placeholder.get(i), 1);
//				   } else {
//					   genreCount.merge(placeholder.get(i),1,Integer::sum);
//				   }
//			   }
//		   }
//		   
//		   System.out.println(genreCount);
//		   //Find most occuring genre
//		   Integer mostOccuring = 0;
//		   String mostGenre = null;
//		   for (Map.Entry<String,Integer> set : genreCount.entrySet()) {
//			   if ((set.getValue() > mostOccuring) && (set.getKey().contains(","))) {
//				   mostOccuring = set.getValue();
//				   mostGenre = set.getKey();
//			   }
//		   }
//		   
//		   System.out.println(mostGenre);
//		   System.out.println(mostOccuring);
//		   return mostGenre;
//	       
//	   } catch (Exception e){
//	     //JOptionPane.showMessageDialog(null,e);
//	     throw new Exception("Error in accessing data");
//	   }
//		
//		
//	}

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
		
		JLabel lblNewLabel_3 = new JLabel("Start Date");
		lblNewLabel_3.setBounds(228, 160, 89, 16);
		panel_1.add(lblNewLabel_3);
		
		textField_2 = new JTextField();
		textField_2.setBounds(197, 188, 130, 26);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("End Date");
		lblNewLabel_4.setBounds(228, 226, 61, 16);
		panel_1.add(lblNewLabel_4);
		
		textField_3 = new JTextField();
		textField_3.setBounds(197, 254, 130, 26);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("Submit");
		btnNewButton_2.setBounds(200, 290, 117, 29);
		panel_1.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1_string = textField_1.getText();
				System.out.println(textField_1_string);
				textField_2_string = textField_2.getText();
				System.out.println(textField_2_string);
				textField_3_string = textField_3.getText();
				System.out.println(textField_3_string);
				
				if (textField_2_string.equals("")) {
					textField_2_string = "1980-01-01";
				}
				if (textField_3_string.equals("")) {
					textField_3_string = "2022-01-01";
				}
				
//				String UserList = getWatchHistory(textField_2_string,textField_3_string,textField_1_string);
				DefaultListModel DLM = new DefaultListModel();
				try {
					viewersChoice ViewersChoice = new viewersChoice();
					String userRecGenres = ViewersChoice.curateViewersChoice(textField_1_string);
					System.out.println("I'm working");
					String allTitles = ViewersChoice.getPersonalRec(userRecGenres);
					DLM.addElement(allTitles);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				list.setModel(DLM);
				
//				DefaultListModel DLM = new DefaultListModel();
//				DLM.addElement(UserRec);
//				//DLM2.addElement(textField_5_string);
//				//DLM2.addElement(textField_6_string);
//				
//				list.setModel(DLM);
				
				
				
			}
		});
		
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
				textField_2_string = "2005-11-30";
				textField_3_string = "2005-12-31";
			}
		});
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Last 3 Months");
		rdbtnNewRadioButton_1.setBounds(631, 177, 141, 23);
		panel_1.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_2_string = "2005-09-30";
				textField_3_string = "2005-12-31";
			}
		});
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Last 6 Months");
		rdbtnNewRadioButton_2.setBounds(631, 210, 141, 23);
		panel_1.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_2_string = "2005-06-30";
				textField_3_string = "2005-12-31";
			}
		});
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Last Year");
		rdbtnNewRadioButton_3.setBounds(631, 243, 141, 23);
		panel_1.add(rdbtnNewRadioButton_3);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_2_string = "2004-12-31";
				textField_3_string = "2005-12-31";
			}
		});
		
		JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("All Time");
		rdbtnNewRadioButton_4.setBounds(631, 278, 141, 23);
		panel_1.add(rdbtnNewRadioButton_4);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_6_string = "2005-11-30";
				textField_6_string = "2005-12-31";
			}
		});
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		tabbedPane.addTab("Analytics", null, panel_1_1, null);
		
		JList list_1 = new JList();
		list_1.setBounds(351, 127, 265, 329);
		panel_1_1.add(list_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("Start Date");
		lblNewLabel_3_1.setBounds(231, 163, 89, 16);
		panel_1_1.add(lblNewLabel_3_1);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(190, 191, 130, 26);
		panel_1_1.add(textField_5);
		
		JLabel lblNewLabel_4_1 = new JLabel("End Date");
		lblNewLabel_4_1.setBounds(231, 241, 61, 16);
		panel_1_1.add(lblNewLabel_4_1);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(190, 269, 130, 26);
		panel_1_1.add(textField_6);
		
		JButton btnNewButton_2_1 = new JButton("Submit");
		btnNewButton_2_1.setBounds(203, 319, 117, 29);
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
				textField_5_string = textField_5.getText();
				if (textField_5_string.equals("")) {
					textField_5_string = "1980-01-01";
				}
				textField_6_string = textField_6.getText();
				if (textField_6_string.equals("")) {
					textField_6_string = "2022-01-01";
				}
				
				String AllUsersList = getTopContent(textField_5_string,textField_6_string,"");
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
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_5_string = textField_5.getText();
				textField_6_string = textField_6.getText();
				
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
