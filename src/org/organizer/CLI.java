package org.organizer;

import java.util.List;
import java.util.Scanner;

public class CLI {

//	static boolean isGUI = false;
	static Scanner terminal = new Scanner(System.in);

	/**
	 * Terminalowe menu do dodawania nowego wydarzenia.
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
	 * Terminalowe menu do wyszukiwania wydarze� zawieracjacych podan� fraze.
	 */
	private static void search() { // TODO wyszukaj

	}

	/**
	 * Terminalowe menu do usuwania wydarze� starszych niz podana data.
	 */
	private static void delBefore() { // TODO usun starsze
		
	}
	
	/**
	 * Terminalowe menu do wy�wietlania wszystkich wydarze� trwaj�cych danego dnia.
	 */
	private static void showDay() {
		String data = null;
		System.out.println("Podaj date (dd-MM-yyyy): ");
		data = terminal.next();
		System.out.println(data);

		List<Event> dayEvents = Operations.getEventsForDay(Operations.parseStringToDate(data, "dd-MM-yyyy"));

		if (dayEvents == null)
			System.out.println("Nie ma wydarze� tego dnia.");
		else {
			System.out.println("Wydarzenia trwaj�ce tego dnia: ");
			for (Event e : dayEvents) {
				System.out.println(" " + e.toString());
			}
			// TODO opcja edycji wydarzenia

			// TODO opcja usuwania wydarzenia

		}
	}

	/**
	 * Terminalowe menu do zapisu wydarze�.
	 */
	private static void save() {
		int option;
		System.out.println("Gdzie chcesz wyeksportowa� dane?");
		System.out.println("[1] baza danych SQL");
		System.out.println("[2] plik XML");
		System.out.println("[3] plik CSV");
		option = terminal.nextInt();
		
		switch(option) {
		case 1:
			SQLData sql = new SQLData();
			sql.writeAllEventsSQL(Data.AllEvents);
			break;
		case 2:
			// TODO xml
			
			break;
		case 3:
			// TODO csv
			
			break;
		default:
			System.out.println("B��dna opcja");
		}
	}
	
	/**
	 * Terminalowe menu do wczytywania wydarze�.
	 */
	private static void load() {
		int option;
		System.out.println("Sk�d chcesz zaimportowa� dane?");
		System.out.println("[1] baza danych SQL");
		System.out.println("[2] plik XML");
		System.out.println("[3] plik CSV");
		option = terminal.nextInt();
		
		switch(option) {
		case 1:
			SQLData sql = new SQLData();
			Data.AllEvents = sql.readAllEventsSQL();
			break;
		case 2:
			// TODO xml
			
			break;
		case 3:
			// TODO csv
			
			break;
		default:
			System.out.println("B��dna opcja");
		}
	}
	
	/**
	 * Wy�wietla help na konsoli.
	 */
	private static void printHelp() { // TODO help
		System.out.println("Dostepne komendy:");
		System.out.println("> dodaj"); // ok
		System.out.println("> wyszukaj");
		System.out.println("> usunStarsze");
		System.out.println("> pokazDzien");
		System.out.println("> zapisz");
		System.out.println("> wczytaj");
		System.out.println("> info");
		System.out.println("> help");
//		if (!isGUI)
//			System.out.println("> gui");
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
			
		case "usunStarsze":
			delBefore();
			break;
			
		case "pokazDzien":
			showDay();
			break;

		case "zapisz":
			save();
			break;
			
		case "wczytaj":
			load();
			break;
			
		case "info":
			System.out.println(Operations.info());
			break;
			
		case "help":
			printHelp();
			break;
			
		case "exit":
			return;
			
//		case "gui":
//			if (!isGUI) {
//				showGUI();
//				System.out.println("Uruchmiam GUI\n");
//			} else
//				System.out.println("GUI jest ju� w��czone!\n");
//			break;
			
		default:
			System.out.println("Nie znaleziono polecenia: " + cmd + "\n");
			printHelp();
		}
	}

	/**
	 * Wy�wietla interfejs okienkowy / GUI
	 */
//	private static void showGUI() {
//		OrganizerWindow.show();
//	}

	/**
	 * Uruchamia program z argumentami lub bez
	 * 
	 * @param args Opcje uruchomienia programu
	 */
	public static void main(String[] args) {

/* dane wstepne */
/*  do test�w   */
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
/*  do test�w   */
		
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
