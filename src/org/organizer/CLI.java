package org.organizer;

import java.awt.EventQueue;
import java.util.Scanner;

public class CLI {

	static boolean isGUI = false;
	static Scanner terminal = new Scanner(System.in);

	/**
	 * Wyœwietla help na konsoli.
	 */
	private static void printHelp() {
		System.out.println("Dostepne komendy:");
		System.out.println("> dodaj"); //ok
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
	public static void command(String cmd) {
		switch (cmd) {
		case "dodaj":
			String name = null;
			String desc = null;
			String place = null;
			String startDate = null;
			String endDate = null;
			String alarmDate = null;
			// TODO importance
			int importance = 0;
			
			System.out.println("Podaj nazwe wydarzenia : ");
			System.out.print("");
			name = terminal.nextLine();
			
			System.out.println("Podaj opis wydarzenia (opcjonalne) : ");
			desc = terminal.nextLine();
			
			System.out.println("Podaj miejsce wydarzenia (opcjonalne) : ");
			place = terminal.nextLine();
			
			System.out.println("Podaj date rozpoczecia wydarzenia w formacie dd-MM-yyyy HH:mm:ss : ");
			startDate = terminal.next();
			startDate += " " + terminal.next();

			System.out.println("Podaj date zakonczenia wydarzenia w formacie dd-MM-yyyy HH:mm:ss : ");
			endDate = terminal.next();
			endDate += " " + terminal.next();

			System.out.println("Czy chcesz ustwaic alarm? [t/n]");
			if(terminal.next().equalsIgnoreCase("t")) {
				System.out.println("Podaj date alarmu w formacie dd-MM-yyyy  : ");
//				System.out.println("Podaj date alarmu w formacie dd-MM-yyyy HH:mm:ss : ");
				alarmDate = terminal.next();
//				alarmDate += " " + terminal.next();
			}
			
//			System.out.println("Podaj waznosc wydarzenia : ");
//			importance = terminal.nextInt();

			try {
				Operations.addEvent(name, desc, place,
						Operations.parseStringToDate(startDate, "dd-MM-yyyy HH:mm:ss"),
						Operations.parseStringToDate(endDate, "dd-MM-yyyy HH:mm:ss"),
						Operations.parseStringToDate(alarmDate, "dd-MM-yyyy"),
//						Operations.parseStringToDate(alarmDate, "dd-MM-yyyy HH:mm:ss"),
						importance);
			} catch (EventError e) {
				e.getMessage();
			}
			break;
		case "wyszukaj":
//                System.out.println("two"); 
			break;
//		case "...":
//          System.out.println("two"); 
//		break;
//		case "...":
//          System.out.println("two"); 
//		break;
//		case "...":
//          System.out.println("two"); 
//		break;
//		case "...":
//          System.out.println("two"); 
//		break;
//		case "...":
//          System.out.println("two"); 
//		break;
//		case "...":
//          System.out.println("two"); 
//		break;
		case "gui":
			if (!isGUI) {
				showGUI();
				System.out.println("Uruchmiam GUI\n");
			} else
				System.out.println("GUI jest ju¿ w³¹czone!\n");
			break;
		case "info":
			System.out.println(Operations.info());
			break;
		case "help":
			printHelp();
			break;
		case "exit":
			return;
		default:
			System.out.println("Nie znaleziono polecenia: " + cmd + "\n");
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

		System.out.println("Witaj w Organizerze");
		
		while (true) {
			System.out.print("\n> ");
			command(terminal.next());
		}

	}

}
