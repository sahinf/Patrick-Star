import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JCheckBox;

public class gui {

	private JFrame frame;
	private JTextField textField;

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
		
		textField = new JTextField();
		textField.setBounds(832, 25, 115, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(945, 24, 78, 21);
		frame.getContentPane().add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(1182, 10, 46, 51);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("PATNIX");
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		lblNewLabel.setBounds(61, 10, 194, 76);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(1121, 28, 51, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(508, 251, 327, 377);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JList list = new JList();
		list.setBounds(0, 0, 327, 377);
		panel_1.add(list);
		
		JButton btnNewButton_1 = new JButton("Watch History");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel DLM = new DefaultListModel();
				DLM.addElement("Minions");
				DLM.addElement("Better Call Saul");
				DLM.addElement("Arrow");
				DLM.addElement("Italian Job");
				list.setModel(DLM);
			}
		});
		
		
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setActionCommand("");
		btnNewButton_1.setBounds(579, 193, 175, 46);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Content Viewer");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(508, 22, 117, 29);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Content Analyst");
		btnNewButton_3.setBounds(637, 22, 117, 29);
		frame.getContentPane().add(btnNewButton_3);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("This Week");
		chckbxNewCheckBox.setBounds(862, 251, 128, 23);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("This Month");
		chckbxNewCheckBox_1.setBounds(862, 286, 128, 23);
		frame.getContentPane().add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("This Year");
		chckbxNewCheckBox_2.setBounds(862, 341, 128, 23);
		frame.getContentPane().add(chckbxNewCheckBox_2);
		
		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("Last 6 Months");
		chckbxNewCheckBox_3.setBounds(862, 316, 128, 23);
		frame.getContentPane().add(chckbxNewCheckBox_3);
		frame.setBounds(100, 100, 1284, 759);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
