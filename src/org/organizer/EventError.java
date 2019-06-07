package org.organizer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EventError extends Exception {
	/**
	 * Klasa reprezentujaca pojedynczy blad w wydarzeniu.
	 */
	private static final long serialVersionUID = 1L;

	EventError() {
		this("");
	}

	/**
	 * Konstruktor tworzacy okno dialogowe z opisem bledu w Wydazrzeniu.
	 * 
	 * @param msg Wiadowosc z informacja o bledzie
	 */
	EventError(String msg) {
		JOptionPane.showMessageDialog(new JFrame(), msg, "EventError", JOptionPane.ERROR_MESSAGE);
	}
}
