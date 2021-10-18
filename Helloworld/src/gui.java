import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import javax.sound.midi.SysexMessage;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
import java.awt.List;
import java.awt.TextArea;

public class gui {
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
	


	/**
	 * Create the application.
	 */
	public gui() {
		initialize();
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
		layeredPane.setBounds(10, 121, 1509, 902);
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
		
		JPanel ViewersChoice = new JPanel();
		Viewer.addTab("Viewer's Choice", null, ViewersChoice, null);
		ViewersChoice.setLayout(null);
		
		JTabbedPane Analyst = new JTabbedPane(JTabbedPane.TOP);
		layeredPane.add(Analyst, "name_108438982228100");
		
		JPanel Top10 = new JPanel();
		Analyst.addTab("Top 10", null, Top10, null);
		Top10.setLayout(null);
		
		JPanel HollywoodPairs = new JPanel();
		Analyst.addTab("Hollywood Pairs", null, HollywoodPairs, null);
		HollywoodPairs.setLayout(null);
		
		JButton getPairsBtn = new JButton("Get Pairs");
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
		
		List FTchainList = new List();
		FTchainList.setBounds(567, 88, 302, 351);
		FreshTomato.add(FTchainList);
		
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
