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
	private JTextField textField_4;

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
		
		 
		JButton btnViewerLogin = new JButton("Viewer");
		btnViewerLogin.setBounds(117, 214, 85, 21);
		Login.add(btnViewerLogin);
		
		
		JButton analystLogInButton = new JButton("Analyst");
		analystLogInButton.setBounds(898, 214, 85, 21);
		Login.add(analystLogInButton);
		
		userIDlogin = new JTextField();
		userIDlogin.setBounds(117, 154, 96, 19);
		Login.add(userIDlogin);
		userIDlogin.setColumns(10);
		
		analystIDlogin = new JTextField();
		analystIDlogin.setBounds(898, 154, 96, 19);
		Login.add(analystIDlogin);
		analystIDlogin.setColumns(10);
		
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
		
		JPanel HollywoodPairs = new JPanel();
		Analyst.addTab("Hollywood Pairs", null, HollywoodPairs, null);
		HollywoodPairs.setLayout(null);
		
		JButton getPairsBtn = new JButton("Get Pairs");
		getPairsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		getPairsBtn.setBounds(97, 87, 85, 21);
		HollywoodPairs.add(getPairsBtn);
		
		TextArea HllyWdPairsList = new TextArea();
		HllyWdPairsList.setBounds(415, 141, 440, 324);
		HollywoodPairs.add(HllyWdPairsList);
		
		JLabel lblNewLabel_6 = new JLabel("Top 10 Hollywood Pairs ");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(415, 91, 237, 13);
		HollywoodPairs.add(lblNewLabel_6);
		
		JPanel FreshTomato = new JPanel();
		Analyst.addTab("Fresh Tomato", null, FreshTomato, null);
		FreshTomato.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Movie One");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(107, 88, 94, 19);
		FreshTomato.add(lblNewLabel_2);
		
		FTMovie1 = new JTextField();
		FTMovie1.setBounds(105, 117, 96, 19);
		FreshTomato.add(FTMovie1);
		FTMovie1.setColumns(10);
		
		JLabel FTMovie2 = new JLabel("Movie Two");
		FTMovie2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		FTMovie2.setBounds(107, 195, 94, 19);
		FreshTomato.add(FTMovie2);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(105, 224, 96, 19);
		FreshTomato.add(textField_4);
		
		JLabel lblNewLabel_3 = new JLabel("The CHAIN");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(567, 53, 88, 19);
		FreshTomato.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Fresh Tomato #");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(739, 53, 120, 13);
		FreshTomato.add(lblNewLabel_4);
		
		
		JLabel lblNewLabel_5 = new JLabel("FreshTomato Number");
		lblNewLabel_5.setBackground(Color.WHITE);
		lblNewLabel_5.setBounds(853, 53, 100, 19);
		FreshTomato.add(lblNewLabel_5);
		
		JPanel IndirectDirector = new JPanel();
		Analyst.addTab("Indirect Director", null, IndirectDirector, null);
		IndirectDirector.setLayout(null);
		
		IndirectActor = new JTextField();
		IndirectActor.setBounds(106, 133, 96, 19);
		IndirectDirector.add(IndirectActor);
		IndirectActor.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Actor");
		lblNewLabel_1.setBounds(106, 110, 45, 13);
		IndirectDirector.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Director");
		lblNewLabel_1_1.setBounds(106, 208, 56, 13);
		IndirectDirector.add(lblNewLabel_1_1);
		
		TextArea IndirectDirectorList = new TextArea();
		IndirectDirectorList.setBounds(106, 227, 167, 66);
		IndirectDirector.add(IndirectDirectorList);
		
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
				layeredPane.removeAll();
				layeredPane.add(Viewer);
				layeredPane.repaint();
				layeredPane.revalidate();
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
