package org.organizer;

import java.util.List;
import java.util.Scanner;

public class CLI {

	static boolean isGUI = false;
	static Scanner terminal = new Scanner(System.in);

	/**
	 * Dodaje nowe wydarzenie.
	 */
	private static void add() {
		String name = null;
		String desc = null;
		String place = null;
		String startDate = null;
		String endDate = null;
		String alarmDate = null;
		// TODO importance
		int importance = 0;
		name = terminal.nextLine();

		System.out.println("Podaj nazwe wydarzenia : ");
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
		if (terminal.next().equalsIgnoreCase("t")) {
			System.out.println("Podaj date alarmu w formacie dd-MM-yyyy  : ");
//			System.out.println("Podaj date alarmu w formacie dd-MM-yyyy HH:mm:ss : ");
			alarmDate = terminal.next();
//			alarmDate += " " + terminal.next();
		}

//		System.out.println("Podaj waznosc wydarzenia : ");
//		importance = terminal.nextInt();

		try {
			Operations.addEvent(name, desc, place, Operations.parseStringToDate(startDate, "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate(endDate, "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate(alarmDate, "dd-MM-yyyy"),
//					Operations.parseStringToDate(alarmDate, "dd-MM-yyyy HH:mm:ss"),
					importance);
		} catch (EventError e) {
			e.getMessage();
		}
	}

	/**
	 * Wyszukuje wydarzenia zawieracjace podan¹ fraze.
	 */
	private static void search() { // TODO wyszukaj

	}

	/**
	 * Wyœwietla wszystkie wydarzenia trwaj¹ce danego dnia.
	 */
	private static void showDay() {
		String data = null;
		System.out.println("Podaj date (dd-MM-yyyy): ");
		data = terminal.next();
		System.out.println(data);

		List<Event> dayEvents = Operations.getEventsForDay(Operations.parseStringToDate(data, "dd-MM-yyyy"));

		if (dayEvents == null)
			System.out.println("Nie ma wydarzeñ tego dnia.");
		else {
			System.out.println("Wydarzenia trwaj¹ce tego dnia: ");
			for (Event e : dayEvents) {
				System.out.println(" " + e.toString());
			}
			// TODO opcja edycji wydarzenia

			// TODO opcja usuwania wydarzenia

		}
	}

	/**
	 * Wyœwietla help na konsoli.
	 */
	private static void printHelp() { // TODO help
		System.out.println("Dostepne komendy:");
		System.out.println("> dodaj"); // ok
		System.out.println("> wyszukaj");
		System.out.println("> pokazDzien");
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
			add();
			break;
			
		case "wyszukaj":
			search();
			break;
			
		case "pokazDzien":
			showDay();
			break;

		case "dunno":

			break;
			
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
		OrganizerWindow.show();
	}

	/**
	 * Uruchamia program z argumentami lub bez
	 * 
	 * @param args Opcje uruchomienia programu
	 */
	public static void main(String[] args) {

/* dane wstepne */
/*  do testów   */
		try {
			Operations.addEvent("1", "", "",
					Operations.parseStringToDate("16-06-2019 01:00:00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("16-06-2019 02:30:00", "dd-MM-yyyy HH:mm:ss"),
					null, 0);
		} catch (EventError e) {
			e.printStackTrace();
		}
		try {
			Operations.addEvent("2", "", "",
					Operations.parseStringToDate("16-06-2019 03:00:00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("16-06-2019 05:00:00", "dd-MM-yyyy HH:mm:ss"),
					null, 0);
		} catch (EventError e) {
			e.printStackTrace();
		}
		try {
			Operations.addEvent("3", "", "",
					Operations.parseStringToDate("16-06-2019 05:00:00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("16-06-2019 07:11:00", "dd-MM-yyyy HH:mm:ss"),
					null, 0);
		} catch (EventError e) {
			e.printStackTrace();
		}
		Data.SearchedEvents.add(Data.AllEvents.get(0));
		Data.SearchedEvents.add(Data.AllEvents.get(1));
/* dane wstepne */
/*  do testów   */
		
		if (args.length > 0) {
			if (args[0].equals("GUI")) {
				showGUI();
			}
		}

//		System.out.println("Witaj w Organizerze");
//
//		while (true) {
//			System.out.print("\n> ");
//			command(terminal.next());
//		}

	}

}
