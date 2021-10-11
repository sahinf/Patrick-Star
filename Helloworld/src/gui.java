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
	public gui() {
		initialize();
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
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(1121, 28, 51, 13);
		frame.getContentPane().add(lblNewLabel_1);

		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(217, 80, 968, 626);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Viewer", null, panel_1, null);
		panel_1.setLayout(null);
		
		JList list = new JList();
		list.setBounds(353, 124, 265, 329);
		panel_1.add(list);
		
		JLabel lblNewLabel_2 = new JLabel("User ID");
		lblNewLabel_2.setBounds(230, 148, 61, 16);
		panel_1.add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(187, 176, 130, 26);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Start Date");
		lblNewLabel_3.setBounds(216, 214, 89, 16);
		panel_1.add(lblNewLabel_3);
		
		textField_2 = new JTextField();
		textField_2.setBounds(187, 242, 130, 26);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("End Date");
		lblNewLabel_4.setBounds(216, 292, 61, 16);
		panel_1.add(lblNewLabel_4);
		
		textField_3 = new JTextField();
		textField_3.setBounds(187, 320, 130, 26);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("Submit");
		btnNewButton_2.setBounds(187, 370, 117, 29);
		panel_1.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1_string = textField_1.getText();
				System.out.println(textField_1_string);
				textField_2_string = textField_2.getText();
				System.out.println(textField_2_string);
				textField_3_string = textField_3.getText();
				System.out.println(textField_3_string);
				
			}
		});
		
		JButton btnNewButton_1 = new JButton("Watch History");
		btnNewButton_1.setBounds(394, 66, 175, 46);
		panel_1.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel DLM = new DefaultListModel();
				DLM.addElement("Minions");
				DLM.addElement("Better Call Saul");
				DLM.addElement("Arrow");
				DLM.addElement("Italian Job");
				list.setModel(DLM);
				System.out.println(textField_3_string);
			}
		});
		
		
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setActionCommand("");
		
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
		frame.setBounds(100, 100, 1284, 759);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
