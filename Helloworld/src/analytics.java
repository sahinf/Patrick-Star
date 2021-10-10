import java.awt.EventQueue;

import javax.swing.JFrame;

public class analytics {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void AnalyticsViewer() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					analytics window = new analytics();
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
	public analytics() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1252, 824);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
