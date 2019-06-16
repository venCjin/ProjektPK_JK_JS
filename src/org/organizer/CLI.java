package org.organizer;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.util.List;
import java.util.Scanner;

public class CLI {

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
		name = terminal.nextLine();

		System.out.println("Podaj nazwe wydarzenia : ");
		System.out.print("nazwa> "); name = terminal.nextLine();

		System.out.println("Podaj opis wydarzenia (opcjonalne) : ");
		System.out.print("opis> "); desc = terminal.nextLine();

		System.out.println("Podaj miejsce wydarzenia (opcjonalne) : ");
		System.out.print("miejsce> "); place = terminal.nextLine();

		System.out.println("Podaj date rozpoczecia wydarzenia w formacie dd-MM-yyyy HH:mm:ss : ");
		System.out.print("data rozpoczecia> "); startDate = terminal.next();
		startDate += " " + terminal.next();

		System.out.println("Podaj date zakonczenia wydarzenia w formacie dd-MM-yyyy HH:mm:ss : ");
		System.out.print("data zakonczenia> "); endDate = terminal.next();
		endDate += " " + terminal.next();

		System.out.println("Czy chcesz ustwaic alarm? [t/n]");
		if (terminal.next().equalsIgnoreCase("t")) {
			System.out.println("Podaj date alarmu w formacie dd-MM-yyyy  : ");
//			System.out.println("Podaj date alarmu w formacie dd-MM-yyyy HH:mm:ss : ");
			System.out.print("data alarmu> "); alarmDate = terminal.next();
//			alarmDate += " " + terminal.next();
		}

		try {
			Operations.addEvent(
					name,
					desc,
					place,
					Operations.parseStringToDate(startDate, "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate(endDate, "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate(alarmDate, "dd-MM-yyyy")
//					Operations.parseStringToDate(alarmDate, "dd-MM-yyyy HH:mm:ss")
					);
		} catch (EventException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Terminalowe menu do wyszukiwania wydarzeñ zawieracjacych podan¹ fraze.
	 */
	private static void search() { // TODO szukaj

	}

	/**
	 * Terminalowe menu do usuwania wydarzeñ starszych niz podana data.
	 */
	private static void delBefore() {
		String date = null;
		System.out.println("Podaj date (dd-MM-yyyy): ");
		System.out.print("data> "); date = terminal.next();
		try {
			Operations.deleteEventsBefore(Operations.parseStringToDate(date, "dd-MM-yyyy"));
		} catch (DateTimeException ex) {
			System.err.println("Nie uda³o siê usun¹æ wydarzeñ starszych ni¿ " + date);
			System.err.println("Opis b³êdu: " + ex.getMessage());
		}
		System.out.println("Operacja zakoñczona powodzeniem.");
	}

	/**
	 * Terminalowe menu do wyœwietlania wszystkich wydarzeñ trwaj¹cych danego dnia.
	 */
	private static void showDay() {
		String date = null;
		System.out.println("Podaj date (dd-MM-yyyy):");
		System.out.print("data> "); date = terminal.next();

		List<Event> dayEvents = Operations.getEventsForDay(Operations.parseStringToDate(date, "dd-MM-yyyy"));

		while (true) {
			if (dayEvents == null)
				System.out.println("Nie ma wydarzeñ tego dnia.");
			else {
				System.out.println("Wydarzenia trwaj¹ce tego dnia: ");
				int i = -1;
				for (Event e : dayEvents) {
					System.out.println("[" + ++i + "] " + e.toString());
				}

				System.out.println("Czy chcesz usun¹æ lub edytowaæ jedno z wydarzen?");
				System.out.println("[u] - usun");
				System.out.println("[e] - edytuj");
				System.out.println("[q] - wyjdŸ");
				System.out.print("opcja> "); date = terminal.next();

				if (date.equals("q"))
					return;
				if (!date.equals("u") && !date.equals("e")) {
					System.err.println("Niepoprawna opcja");
					continue;
				}

				System.out.println("Podaj index wydarzenia [i]:");
				System.out.print("index> "); i = terminal.nextInt();

				Event e;
				try {
					e = dayEvents.get(i);
				} catch (IndexOutOfBoundsException ex) {
					System.err.print("Niepoprawny index.\n" + ex.getMessage());
					continue;
				}

				switch (date) {
				case "u":
					Operations.deleteEvent(e);
					dayEvents.remove(i);
					System.out.println("Operacja zakoñczona powodzeniem.");
					break;
				case "e":
					// TODO edit

					break;
//				default:
//					System.err.println("Niepoprawna opcja");
				}
			}
		}
	}

	/**
	 * Terminalowe menu do zapisu wydarzeñ.
	 */
	private static void save() { // TODO wyj¹tki
		int option;
		System.out.println("Gdzie chcesz wyeksportowaæ dane?");
		System.out.println("[1] baza danych SQL");
		System.out.println("[2] plik XML");
		System.out.println("[3] plik CSV");
		System.out.print("opcja> "); option = terminal.nextInt();

		switch (option) {
		case 1:
			SQLData sql = new SQLData();
			try {
				sql.deleteAllEventsSQL();
				sql.writeAllEventsSQL(Data.AllEvents);
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			break;
		case 2:
			try {
				XMLData.writeXML(Data.AllEvents);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			break;
		case 3:
			try {
				CSVData.writeCSV(Data.AllEvents);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			break;
		default:
			System.out.println("B³êdna opcja");
		}
	}

	/**
	 * Terminalowe menu do wczytywania wydarzeñ.
	 */
	private static void load() { // TODO wyj¹tki
		int option;
		System.out.println("Sk¹d chcesz zaimportowaæ dane?");
		System.out.println("[1] baza danych SQL");
		System.out.println("[2] plik XML");
		System.out.println("[3] plik CSV");
		System.out.print("opcja> "); option = terminal.nextInt();

		switch (option) {
		case 1:
			SQLData sql = new SQLData();
			try {
				Data.AllEvents = sql.readAllEventsSQL();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			break;
		case 2:
			try {
				Data.AllEvents = XMLData.readXML();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			break;
		case 3:
			try {
				Data.AllEvents = CSVData.readCSV();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			break;
		default:
			System.out.println("B³êdna opcja");
		}
	}

	/**
	 * Wyœwietla help na konsoli.
	 */
	private static void printHelp() {
		System.out.println("Dostepne komendy:");
		System.out.println("> dodaj");
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
		case "d":
		case "dodaj":
			add();
			break;

		case "s":
		case "szukaj":
			search();
			break;

		case "u":
		case "usunStarsze":
			delBefore();
			break;

		case "p":
		case "pokazDzien":
			showDay();
			break;

		case "z":
		case "zapisz":
			save();
			break;

		case "w":
		case "wczytaj":
			load();
			break;

		case "i":
		case "info":
			System.out.println(Operations.info());
			break;

		case "h":
		case "help":
			printHelp();
			break;

		case "q":
		case "quit":
			return;

		default:
			System.out.println("Nie znaleziono polecenia: '" + cmd + "'\n");
			printHelp();
		}
	}


	/**
	 * Uruchamia program z argumentami
	 * 
	 * @param args Opcje uruchomienia programu ["GUI", "CLI"]
	 */
	public static void main(String[] args) {

/* dane wstepne */
/* do testów */
		try {
			Operations.addEvent("1", "", "", Operations.parseStringToDate("16-06-2019 01:00:00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("16-06-2019 02:30:00", "dd-MM-yyyy HH:mm:ss"), Operations.parseStringToDate("16-06-2019 01:30:00", "dd-MM-yyyy HH:mm:ss"));
		} catch (EventException e) {
			e.printStackTrace();
		}
		try {
			Operations.addEvent("2", "", "", Operations.parseStringToDate("16-06-2019 03:00:00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("16-06-2019 05:00:00", "dd-MM-yyyy HH:mm:ss"), null);
		} catch (EventException e) {
			e.printStackTrace();
		}
		try {
			Operations.addEvent("3", "", "", Operations.parseStringToDate("16-06-2019 05:00:00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("16-06-2019 07:11:00", "dd-MM-yyyy HH:mm:ss"), null);
		} catch (EventException e) {
			e.printStackTrace();
		}
		Data.SearchedEvents.add(Data.AllEvents.get(0));
		Data.SearchedEvents.add(Data.AllEvents.get(1));
/* dane wstepne */
/* do testów */

		if (args.length > 0) {
			if (args[0].equals("GUI")) {
				OrganizerWindow.show();
			} else if (args[0].equals("CLI")) {
				System.out.println("Witaj w Organizerze");
				System.out.println("Napisz 'help' by uzyskac pomoc");
				while (true) {
					System.out.print("\npolecenie> ");
					command(terminal.next());
				}
			} else {
				throw new RuntimeException("Niepoprawne argumenty uruchamiania programu.");
			}
		}
	}
}
