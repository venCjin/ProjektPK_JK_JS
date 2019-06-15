package org.organizer;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class EditEventWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void show(Event e) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditEventWindow window = new EditEventWindow(e);
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
	private EditEventWindow(Event e) {
		initialize(e);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Event e) {
		frame = new JFrame("Edycja wydarzenia");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		
		
		
		
		
		
		
		
	}

}
