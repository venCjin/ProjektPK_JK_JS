package org.organizer;

import java.awt.EventQueue;

public class CLI {

	static boolean isGUI;

	/**
	 * Wyœwietla help na konsoli.
	 */
	private void printHelp() {
		System.out.println("Dostepne komendy:");
		System.out.println("> dodaj");
		System.out.println("> wyszukaj");
		System.out.println("> ");
		System.out.println("> ");
		System.out.println("> ");
		System.out.println("> ");
		System.out.println("> ");
		System.out.println("> help");
		if (!isGUI)
			System.out.println("> gui");
	}

	/**
	 * Interpretuje komendy z konsoli.
	 * 
	 * @param cmd Komenda z konsoli
	 */
	public void command(String cmd) {
		switch (cmd) {
		case "dodaj":
//                System.out.println("one"); 
			break;
		case "...":
//                System.out.println("two"); 
			break;
		case "gui":
			if (!isGUI) {
				showGUI();
				System.out.println("Uruchmiam GUI\n");
			} else
				System.out.println("GUI jest ju¿ w³¹czone!\n");
			break;
		case "help":
			printHelp();
			break;
		default:
			System.out.println("Nie znaleziono polecenia.\n");
			printHelp();
		}
	}

	/**
	 * Wyœwietla interfejs okienkowy / GUI
	 */
	private static void showGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrganizerWindow window = new OrganizerWindow();
					window.window.setVisible(true);
					isGUI = true;
				} catch (Exception e) {
					e.toString();
//					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Uruchamia program z argumentami lub bez
	 * 
	 * @param args Opcje uruchomienia programu
	 */
	public static void main(String[] args) {

		if (args.length > 0) {
			if (args[0].equals("GUI")) {
				showGUI();
			}
		}
		// klasa CLI lub ca³y kod tutaj

		// TODO Warstwa Logki
		/*
		 * Logic.RingAllAlerts();
		 */

		// TESTS AND SHIT
		// Instantiate a Date object
		/*
		 * Date date = new Date(); SimpleDateFormat dateFormat = new
		 * SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		 * System.out.println("Today's date is: "+dateFormat.format(date));
		 */
	}

}
