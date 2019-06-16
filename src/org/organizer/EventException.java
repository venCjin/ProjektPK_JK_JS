package org.organizer;

public class EventException extends Exception {
	/**
	 * Klasa reprezentujaca pojedynczy blad w wydarzeniu.
	 */
	private static final long serialVersionUID = 1L;

	EventException() {
		this("");
	}

	/**
	 * Konstruktor tworzacy //okno dialogowe// z opisem bledu w Wydazrzeniu.
	 * 
	 * @param msg Wiadowosc z informacja o bledzie
	 */
	EventException(String msg) {
		super(msg);
	}
}
