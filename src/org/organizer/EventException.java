package org.organizer;

/**
 * Klasa reprezentujaca pojedynczy blad w wydarzeniu.
 */
public class EventException extends Exception {

	private static final long serialVersionUID = 1L;

	EventException() {
		this("");
	}

	/**
	 * Przekazuje wiadomosc o bledzie.
	 * 
	 * @param msg Wiadomosc wyjatka
	 */
	EventException(String msg) {
		super(msg);
	}
}
