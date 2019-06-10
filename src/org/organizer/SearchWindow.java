package org.organizer;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class SearchWindow {

	private JFrame frame;

	/**
	 * 
	 */
	public static void show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchWindow window = new SearchWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 
	 */
	public SearchWindow() {
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
	}

}
