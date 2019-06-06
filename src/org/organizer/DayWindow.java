package org.organizer;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class DayWindow {

	private JFrame frame;

	/**
	 * Shows the window with plan of a day.
	 */
	public void show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DayWindow window = new DayWindow();
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
	public DayWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 320, 500);
		frame.setResizable(false);
	}

}
