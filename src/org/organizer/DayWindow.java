package org.organizer;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class DayWindow {

	private JFrame frame;

	/**
	 * TODO
	 */
	public static void show(String day) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DayWindow window = new DayWindow(day);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * TODO
	 */
	protected DayWindow(String day) {
		initialize(day);
	}

	/**
	 * TODO
	 */
	private void initialize(String day) {
		frame = new JFrame(day);
		frame.setBounds(100, 100, 320, 500);
		frame.setResizable(false);
	}

}
