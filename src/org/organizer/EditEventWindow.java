package org.organizer;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class EditEventWindow {

	private JFrame frame;

	/**
	 * Tworzy okno edycji wydarzenia.
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

	private EditEventWindow(Event e) {
		initialize(e);
	}

	/**
	 * Inicjalizuje zawartoœæ okna edycji wydarzenia.
	 */
	private void initialize(Event e) {
		frame = new JFrame("Edycja wydarzenia");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		
		// TODO rzeczy
		
		
		
		
		
		
	}

}
