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
import java.awt.Button;
import javax.swing.JTabbedPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import javax.swing.DropMode;
import javax.swing.JMenu;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JSeparator;
import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import java.awt.TextArea;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;

public class gui {
	public String startDateViewer;
	public String endDateViewer;
	public String startDateAna;
	public String endDateAna;
	private JFrame frmPatnix;
	private JPanel Login;
	private JTextField userIDlogin;
	private JTextField analystIDlogin;
	private JTextField IndirectActor;
	private JTextField FTMovie1;
	private JTextField FTMovie2;

	/**
	 * @wbp.nonvisual location=111,794
	 */
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frmPatnix.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	
	Connection connection = null;
	graph Graph = null;
	indirectDirector indirector = null;
	bollywood_bairs pairs = null;
	HollywoodPairs somePairs = null;
	
	/**
	 * Create the application.
	 */
	public gui() {
		connection = dbConnector();
		initialize();
		Graph = new graph();
		indirector = new indirectDirector();
		pairs = new bollywood_bairs();
		somePairs = new HollywoodPairs();
		
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
	
	public Boolean validateTitle(String title) {
		 try{
		     //create a statement object
		       Statement stmt = connection.createStatement();
		       //create an SQL statement
		       String sqlStatement = "select ratings.titleid"
		       		+ " from ratings"
		       		+ " where ratings.titleid = '"+ title +"' limit 1;";
		       //send statement to DBMS
		       ResultSet result = stmt.executeQuery(sqlStatement);
		       String aTitle = "";
		       while (result.next()) {		       
		         aTitle += result.getString("titleid"); // change		         
		       }
		       if (aTitle == "") {
		    	   return false;
		       }
		       else {
		    	   return true;
		       }		      
		   } catch (Exception e){
		     return false;
		   }
		
	}
	
	public Boolean validateActor(String name) {
		 try{
		     //create a statement object
		       Statement stmt = connection.createStatement();
		       //create an SQL statement
		       String sqlStatement = "select principals.nconst"
		       		+ " from principals"
		       		+ " where nconst = '"+ name +"' and (category = 'actor' or category = 'actress') limit 1;";
		       //send statement to DBMS
		       ResultSet result = stmt.executeQuery(sqlStatement);
		       String aName = "";
		       while (result.next()) {
		         aName += result.getString("nconst");
		       }
		       if (aName == "") {
		    	   return false;
		       }
		       else {
		    	   return true;
		       }
		   } catch (Exception e){
			   return false;
		   }
		
	}
	
	public Boolean validateCustomer(String customerid) {
		 try{
		     //create a statement object
		       Statement stmt = connection.createStatement();
		       //create an SQL statement
		       String sqlStatement = "select ratings.customerid"
		       		+ " from ratings"
		       		+ " where ratings.customerid = '"+ customerid +"' limit 1;";
		       //send statement to DBMS
		       ResultSet result = stmt.executeQuery(sqlStatement);
		       String aCustomer = "";
		       while (result.next()) {		       
		         aCustomer += result.getString("customerid"); // change		         
		       }
		       if (aCustomer == "") {
		    	   return false;
		       }
		       else {
		    	   return true;
		       }		      
		   } catch (Exception e){
		     return false;
		   }
		
	}
	
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {	
		frmPatnix = new JFrame();
		frmPatnix.setTitle("PATNIX");
		frmPatnix.getContentPane().setBackground(Color.PINK);
		frmPatnix.getContentPane().setLayout(null);
		
		// PATNIX LOGO 
		JLabel logo = new JLabel("PATNIX");
		logo.setBounds(42, 0, 194, 76);
		frmPatnix.getContentPane().add(logo);
		logo.setForeground(Color.GREEN);
		logo.setFont(new Font("Tahoma", Font.PLAIN, 48));
		
		//layered pane
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.PINK);
		layeredPane.setBounds(6, 132, 1420, 751);
		frmPatnix.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		//log in page 
		Login = new JPanel();
		Login.setBackground(Color.PINK);
		layeredPane.add(Login, "name_98509380987100");
		Login.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome!");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(55, 30, 118, 84);
		Login.add(lblNewLabel);
		
		 
		JButton btnViewerLogin = new JButton("Go to Viewer Portal!");
		btnViewerLogin.setBounds(117, 196, 155, 21);
		Login.add(btnViewerLogin);
		
		
		JButton analystLogInButton = new JButton("Go to Analyst Portal!");
		analystLogInButton.setBounds(510, 196, 163, 21);
		Login.add(analystLogInButton);
		
		userIDlogin = new JTextField();
		userIDlogin.setBounds(117, 154, 155, 31);
		Login.add(userIDlogin);
		userIDlogin.setColumns(10);
		
		analystIDlogin = new JTextField();
		analystIDlogin.setBounds(510, 154, 163, 31);
		Login.add(analystIDlogin);
		analystIDlogin.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Customer ID:");
		lblNewLabel_2.setBounds(117, 129, 95, 14);
		Login.add(lblNewLabel_2);
		
		JLabel lblNewLabel_5 = new JLabel("Enter Name:");
		lblNewLabel_5.setBounds(510, 129, 89, 14);
		Login.add(lblNewLabel_5);
		
		JLabel customerIdErrorLabel = new JLabel("");
		customerIdErrorLabel.setForeground(Color.RED);
		customerIdErrorLabel.setBounds(117, 228, 226, 21);
		Login.add(customerIdErrorLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.PINK);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.BLACK);
		separator.setBounds(390, 11, 2, 362);
		Login.add(separator);
		
		JTabbedPane Viewer = new JTabbedPane(JTabbedPane.TOP);
		layeredPane.add(Viewer, "name_108384137906000");
		
		JPanel WatchHistory = new JPanel();
		Viewer.addTab("Watch History", null, WatchHistory, null);
		WatchHistory.setLayout(null);
		
		JRadioButton ThisMonthButton = new JRadioButton("This Month");
		ThisMonthButton.setBounds(1021, 107, 141, 23);
		WatchHistory.add(ThisMonthButton);
		ThisMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateViewer = "2005-11-30";
				endDateViewer = "2005-12-31";
			}
		});
		
		JRadioButton last3MonthsButton = new JRadioButton("Last 3 Months");
		last3MonthsButton.setBounds(1021, 142, 141, 23);
		WatchHistory.add(last3MonthsButton);
		last3MonthsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateViewer = "2005-09-30";
				endDateViewer = "2005-12-31";
			}
		});
		
		JRadioButton last6MonthsButton = new JRadioButton("Last 6 Months");
		last6MonthsButton.setBounds(1021, 177, 141, 23);
		WatchHistory.add(last6MonthsButton);
		last6MonthsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateViewer = "2004-06-30";
				endDateViewer = "2005-12-31";
			}
		});
		
		JRadioButton lastYearButton = new JRadioButton("Last Year");
		lastYearButton.setBounds(1021, 212, 141, 23);
		WatchHistory.add(lastYearButton);
		lastYearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateViewer = "2004-12-31";
				endDateViewer = "2005-12-31";
			}
		});
		
		JRadioButton allTimeButton = new JRadioButton("All Time ");
		allTimeButton.setBounds(1021, 247, 141, 23);
		WatchHistory.add(allTimeButton);
		allTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateViewer = "1999-12-31";
				endDateViewer = "2005-12-31";
			}
		});
		
		ButtonGroup WatchHistButtons = new ButtonGroup();
		WatchHistButtons.add(ThisMonthButton);
		WatchHistButtons.add(last3MonthsButton);
		WatchHistButtons.add(last6MonthsButton);
		WatchHistButtons.add(lastYearButton);
		WatchHistButtons.add(allTimeButton);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.setBounds(1031, 282, 117, 29);
		WatchHistory.add(btnNewButton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(494, 64, 489, 545);
		WatchHistory.add(scrollPane_1);
		
		JList list = new JList();
		scrollPane_1.setViewportView(list);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel watchHistModel = new DefaultListModel();
				String userWatchHistory = getWatchHistory(startDateViewer,endDateViewer, userIDlogin.getText());
				watchHistModel.addElement(userWatchHistory);
				list.setModel(watchHistModel);
			}
		});
		
		JPanel ViewersChoice = new JPanel();
		Viewer.addTab("Viewer's Choice", null, ViewersChoice, null);
		ViewersChoice.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(503, 65, 516, 549);
		ViewersChoice.add(scrollPane);
		
		
		JList list_1 = new JList();
		scrollPane.setViewportView(list_1);
		JTabbedPane Analyst = new JTabbedPane(JTabbedPane.TOP);
		layeredPane.add(Analyst, "name_108438982228100");
		
		JButton viewersChoiceButton = new JButton("Get Niche Recommendations");
		viewersChoiceButton.setBounds(1070, 275, 203, 92);
		ViewersChoice.add(viewersChoiceButton);
		viewersChoiceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel viewerSubmit = new DefaultListModel();
				viewersChoice ViewersChoice = new viewersChoice();
				
				try {
					String curatedGenre = ViewersChoice.curateViewersChoice(userIDlogin.getText());
					String curatedTitles = ViewersChoice.getPersonalRec(curatedGenre);
					viewerSubmit.addElement(curatedTitles);
					list_1.setModel(viewerSubmit);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		JPanel Top10 = new JPanel();
		Analyst.addTab("Top 10", null, Top10, null);
		Top10.setLayout(null);
		
		JList list_2 = new JList();
		list_2.setBounds(463, 65, 462, 573);
		Top10.add(list_2);
		
		
		JRadioButton lastMonthTop10But = new JRadioButton("Last Month");
		lastMonthTop10But.setBounds(963, 125, 141, 23);
		Top10.add(lastMonthTop10But);
		lastMonthTop10But.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateAna = "2005-11-30";
				endDateAna = "2005-12-31";
			}
		});
		
		
		
		JRadioButton last3MonthsTop10But = new JRadioButton("Last 3 Months");
		last3MonthsTop10But.setBounds(963, 160, 141, 23);
		Top10.add(last3MonthsTop10But);
		last3MonthsTop10But.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateAna = "2005-09-30";
				endDateAna = "2005-12-31";
			}
		});
		
		JRadioButton last6MonthsTop10But = new JRadioButton("Last 6 Months");
		last6MonthsTop10But.setBounds(963, 195, 141, 23);
		Top10.add(last6MonthsTop10But);
		last6MonthsTop10But.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateAna = "2004-06-30";
				endDateAna = "2005-12-31";
			}
		});
		
		
		JRadioButton lastYearTop10But = new JRadioButton("Last Year");
		lastYearTop10But.setBounds(963, 230, 141, 23);
		Top10.add(lastYearTop10But);
		lastYearTop10But.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateAna = "2004-12-31";
				endDateAna = "2005-12-31";
			}
		});
		
		JRadioButton allTimeTop10But = new JRadioButton("All Time");
		allTimeTop10But.setBounds(963, 265, 141, 23);
		Top10.add(allTimeTop10But);
		allTimeTop10But.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startDateAna = "1999-12-31";
				endDateAna = "2005-12-31";
			}
		});
		
		ButtonGroup Top10Buttons = new ButtonGroup();
		Top10Buttons.add(lastMonthTop10But);
		Top10Buttons.add(last3MonthsTop10But);
		Top10Buttons.add(last6MonthsTop10But);
		Top10Buttons.add(lastYearTop10But);
		Top10Buttons.add(allTimeTop10But);
		
		JButton Top10Submit = new JButton("Submit");
		Top10Submit.setBounds(963, 300, 114, 29);
		Top10.add(Top10Submit);
		Top10Submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel Top10AllModel = new DefaultListModel();
				String Top10AllUsers = getTopContent(startDateAna,endDateAna,"");
				Top10AllModel.addElement(Top10AllUsers);
				
				list_2.setModel(Top10AllModel);
				
			}
		});
		
//		JButton getFTButton = new JButton("Get Fresh Tomato");
//		getFTButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (validateTitle(FTMovie1.getText()) && validateTitle(FTMovie2.getText())) {
//					errorMessagePlaceholder.setText("");
//					DefaultListModel FTModel = new DefaultListModel();
//					List<String> chain = Graph.findShortestPath(FTMovie1.getText(),FTMovie2.getText());
//					for (String id: chain) {
//						FTModel.addElement(id);
//					}
//					FTChainList.setModel(FTModel);
//					FreshTomatoNumber.setText(Graph.getFTNumber(chain));
//				}
//				else {
//					System.out.println("titles not validated");
//					errorMessagePlaceholder.setText("Titles not validated. Please try again.");
//				}
//				
//				
//			}
//		});
		
		JPanel HollywoodPairs = new JPanel();
		Analyst.addTab("Hollywood Pairs", null, HollywoodPairs, null);
		HollywoodPairs.setLayout(null);
		
		JLabel HPair = new JLabel("");
		HPair.setBackground(Color.WHITE);
		HPair.setBounds(735, 55, 100, 19);
		HollywoodPairs.add(HPair);
		
		JList HWPairsList = new JList();
		HWPairsList.setBounds(415, 128, 334, 406);
		HollywoodPairs.add(HWPairsList);
		
		JButton getPairsBtn = new JButton("Get Pairs");
		getPairsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try {
					//String pairs_10 = pairs.doWork();
					//HPair.setText(pairs_10);
				     DefaultListModel HWModel = new DefaultListModel();
					List<String> pairsList = somePairs.getPairs();
					for (String pairs: pairsList) {
						HWModel.addElement(pairs);
					}
					HWPairsList.setModel(HWModel);
					//FreshTomatoNumber.setText(Graph.getFTNumber(chain));
					
					
				
					
				}
			catch(Exception re){
				re.printStackTrace();
			}
			}
		});
		getPairsBtn.setBounds(97, 87, 85, 21);
		HollywoodPairs.add(getPairsBtn);
		
		JLabel lblNewLabel_6 = new JLabel("Top 10 Hollywood Pairs ");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(415, 91, 237, 13);
		HollywoodPairs.add(lblNewLabel_6);
		
		
		
		JPanel FreshTomato = new JPanel();
		Analyst.addTab("Fresh Tomato", null, FreshTomato, null);
		FreshTomato.setLayout(null);
		
		JLabel Movie1Label = new JLabel("Movie One");
		Movie1Label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Movie1Label.setBounds(107, 88, 94, 19);
		FreshTomato.add(Movie1Label);
		
		FTMovie1 = new JTextField();
		FTMovie1.setBounds(105, 117, 192, 34);
		FreshTomato.add(FTMovie1);
		FTMovie1.setColumns(10);
		
		JLabel Movie2Label = new JLabel("Movie Two");
		Movie2Label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Movie2Label.setBounds(107, 195, 94, 19);
		FreshTomato.add(Movie2Label);
		
		FTMovie2 = new JTextField();
		FTMovie2.setForeground(Color.BLACK);
		FTMovie2.setColumns(10);
		FTMovie2.setBounds(105, 224, 192, 40);
		FreshTomato.add(FTMovie2);
		
		JLabel lblNewLabel_3 = new JLabel("The CHAIN");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(479, 53, 88, 19);
		FreshTomato.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Fresh Tomato #");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(630, 56, 120, 13);
		FreshTomato.add(lblNewLabel_4);
		
		
		JLabel FreshTomatoNumber = new JLabel("");
		FreshTomatoNumber.setBackground(Color.WHITE);
		FreshTomatoNumber.setBounds(735, 55, 100, 19);
		FreshTomato.add(FreshTomatoNumber);
		
		JList FTChainList = new JList();
		FTChainList.setBounds(479, 88, 319, 322);
		FreshTomato.add(FTChainList);
		
		JLabel errorMessagePlaceholder = new JLabel("");
		errorMessagePlaceholder.setForeground(Color.RED);
		errorMessagePlaceholder.setBounds(107, 347, 190, 49);
		FreshTomato.add(errorMessagePlaceholder);
		
		JButton getFTButton = new JButton("Get Fresh Tomato");
		getFTButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateTitle(FTMovie1.getText()) && validateTitle(FTMovie2.getText())) {
					errorMessagePlaceholder.setText("");
					DefaultListModel FTModel = new DefaultListModel();
					List<String> chain = Graph.findShortestPath(FTMovie1.getText(),FTMovie2.getText());
					for (String id: chain) {
						FTModel.addElement(id);
					}
					FTChainList.setModel(FTModel);
					FreshTomatoNumber.setText(Graph.getFTNumber(chain));
				}
				else {
					System.out.println("titles not validated");
					errorMessagePlaceholder.setText("Titles not validated. Please try again.");
				}
				
				
			}
		});
		getFTButton.setBounds(105, 305, 192, 23);
		FreshTomato.add(getFTButton);
		
		
		
		JPanel IndirectDirector = new JPanel();
		Analyst.addTab("Indirect Director", null, IndirectDirector, null);
		IndirectDirector.setLayout(null);
		
		IndirectActor = new JTextField();
		IndirectActor.setBounds(106, 133, 132, 34);
		IndirectDirector.add(IndirectActor);
		IndirectActor.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Actor");
		lblNewLabel_1.setBounds(106, 110, 45, 13);
		IndirectDirector.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Director");
		lblNewLabel_1_1.setBounds(427, 110, 56, 13);
		IndirectDirector.add(lblNewLabel_1_1);
		
		JList indirectList = new JList();
		indirectList.setBounds(427, 133, 132, 34);
		IndirectDirector.add(indirectList);
		
		JLabel indirectorErrorLabel = new JLabel("");
		indirectorErrorLabel.setForeground(Color.RED);
		indirectorErrorLabel.setBounds(103, 178, 148, 28);
		IndirectDirector.add(indirectorErrorLabel);
		
		JButton getIndirector = new JButton("Get Indirect Director");
		getIndirector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateActor(IndirectActor.getText())) {
					indirectorErrorLabel.setText("");
					DefaultListModel IAModel = new DefaultListModel();
					String inDirId = indirector.getIndirectDirector(IndirectActor.getText());					
					IAModel.addElement(inDirId);					
					indirectList.setModel(IAModel);
				}
				else {
					System.out.println("Actor not validated");
					indirectorErrorLabel.setText("Actor not Valid. Try Again.");
				}
				
			}
		});
		getIndirector.setBounds(270, 139, 133, 23);
		IndirectDirector.add(getIndirector);
		
		
		
		JButton changeUserBtn = new JButton("Change User");
		changeUserBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		changeUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(Login);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		changeUserBtn.setBounds(1056, 32, 126, 28);
		frmPatnix.getContentPane().add(changeUserBtn);
		analystLogInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(Analyst);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		btnViewerLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if (validateCustomer(userIDlogin.getText())) {
					customerIdErrorLabel.setText("");
					layeredPane.removeAll();
					layeredPane.add(Viewer);
					layeredPane.repaint();
					layeredPane.revalidate();
				}
				else {
					System.out.println("customer not validated");
					customerIdErrorLabel.setText("Customer not validated. Please try again.");
				}
			}
		});
		
		
		
	}

	
	
	
	
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
