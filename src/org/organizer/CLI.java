package org.organizer;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class CLI {

	private static Scanner terminal = new Scanner(System.in);

	/**
	 * Terminalowe menu do dodawania nowego wydarzenia.
	 */
	private static void add() {
		String name;
		String desc;
		String place;
		String startDate;
		String endDate;
		String alarmDate = null;
		terminal.nextLine();

		System.out.println("Podaj nazwe wydarzenia : ");
		System.out.print("nazwa> ");
		name = terminal.nextLine();

		System.out.println("Podaj opis wydarzenia (opcjonalne) : ");
		System.out.print("opis> ");
		desc = terminal.nextLine();

		System.out.println("Podaj miejsce wydarzenia (opcjonalne) : ");
		System.out.print("miejsce> ");
		place = terminal.nextLine();

		System.out.println("Podaj date rozpoczecia wydarzenia w formacie dd-MM-yyyy HH:mm:ss : ");
		System.out.print("data rozpoczecia> ");
		startDate = terminal.next();
		startDate += " " + terminal.next();

		System.out.println("Podaj date zakonczenia wydarzenia w formacie dd-MM-yyyy HH:mm:ss : ");
		System.out.print("data zakonczenia> ");
		endDate = terminal.next();
		endDate += " " + terminal.next();

		System.out.println("Czy chcesz ustwaic alarm? [t/n]");
		if (terminal.next().equalsIgnoreCase("t")) {
			System.out.println("Podaj date alarmu w formacie dd-MM-yyyy HH:mm:ss : ");
			System.out.print("data alarmu> ");
			alarmDate = terminal.next();
			alarmDate += " " + terminal.next();
		}

		try {
			Operations.addEvent(name, desc, place, Operations.parseStringToDate(startDate, "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate(endDate, "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate(alarmDate, "dd-MM-yyyy HH:mm:ss"));
		} catch (EventException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("Dodano wydarzenie.");
	}

	/**
	 * Terminalowe menu do wyszukiwania wydarze� zawieracjacych podan� fraze.
	 */
	private static void search() {
		String phrase;
		terminal.nextLine();
		System.out.println("Podaj fraz� do wyszukania w nazwie wydarzenia :");
		System.out.print("fraza> ");
		phrase = terminal.nextLine();

		Operations.searchEvents(phrase);

		delEvt(Data.SearchedEvents, "Wyszukane wydarzenia:");
	}

	/**
	 * Terminalowe menu do usuwania wydarze� starszych niz podana data.
	 */
	private static void delBefore() {
		String date;
		System.out.println("Podaj date (dd-MM-yyyy): ");
		System.out.print("data> ");
		date = terminal.next();
		try {
			Operations.deleteEventsBefore(Operations.parseStringToDate(date, "dd-MM-yyyy"));
		} catch (DateTimeException ex) {
			System.err.println("Nie uda�o si� usun�� wydarze� starszych ni� " + date);
			System.err.println("Opis b��du: " + ex.getMessage());
		}
		System.out.println("Operacja zako�czona powodzeniem.");
	}

	/**
	 * Terminalowe menu do wy�wietlania wszystkich wydarze� trwaj�cych danego dnia.
	 */
	private static void showDay() {
		String date;
		System.out.println("Podaj date (dd-MM-yyyy):");
		System.out.print("data> ");
		date = terminal.next();

		List<Event> dayEvents = Operations.getEventsForDay(Operations.parseStringToDate(date, "dd-MM-yyyy"));

		delEvt(dayEvents, "Wydarzenia trwaj�ce tego dnia:");
	}

	/**
	 * Terminalowe menu do zapisu wydarze�.
	 */
	private static void save() {
		String option;
		System.out.println("Gdzie chcesz wyeksportowa� dane?");
		System.out.println("[1] baza danych SQL");
		System.out.println("[2] plik XML");
		System.out.println("[3] plik CSV");
		System.out.print("opcja> ");
		option = terminal.next();

		switch (option) {
		case "1":
			SQLData sql = new SQLData();
			try {
				sql.deleteAllEventsSQL();
				sql.writeAllEventsSQL(Data.AllEvents);
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			System.out.println("Wyeksportowano do SQL.");
			break;
		case "2":
			try {
				XMLData.writeXML(Data.AllEvents);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			System.out.println("Wyeksportowano do XML.");
			break;
		case "3":
			try {
				CSVData.writeCSV(Data.AllEvents);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			System.out.println("Wyeksportowano do CSV.");
			break;
		default:
			System.out.println("B��dna opcja");
		}
	}

	/**
	 * Terminalowe menu do wczytywania wydarze�.
	 */
	private static void load() {
		String option;
		System.out.println("Sk�d chcesz zaimportowa� dane?");
		System.out.println("[1] baza danych SQL");
		System.out.println("[2] plik XML");
		System.out.println("[3] plik CSV");
		System.out.print("opcja> ");
		option = terminal.next();

		switch (option) {
		case "1":
			SQLData sql = new SQLData();
			try {
				Data.AllEvents = sql.readAllEventsSQL();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			System.out.println("Zaimportowano z SQL.");
			break;
		case "2":
			try {
				Data.AllEvents = XMLData.readXML();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			System.out.println("Zaimportowano z XML.");
			break;
		case "3":
			try {
				Data.AllEvents = CSVData.readCSV();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			System.out.println("Zaimportowano z CSV.");
			break;
		default:
			System.out.println("B��dna opcja");
		}
	}

	/**
	 * Wy�wietla help na konsoli.
	 */
	private static void printHelp() {
		System.out.println("Dostepne komendy:");
		System.out.println("> dodaj");
		System.out.println("> szukaj");
		System.out.println("> usunStarsze");
		System.out.println("> pokazDzien");
		System.out.println("> zapisz");
		System.out.println("> wczytaj");
		System.out.println("> info");
		System.out.println("> help");
	}

	/**
	 * Interpretuje komendy z konsoli.
	 * 
	 * @param cmd Komenda z konsoli
	 * @return Czy kontynuowac wykonywanie polece�
	 */
	private static boolean command(String cmd) {
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
			return false;

		default:
			System.out.println("Nie znaleziono polecenia: '" + cmd + "'\n");
			printHelp();
		}
		return true;
	}

	/**
	 * Pozwala usuwa� i edytowa� wybrane wydarzenia z przekazanej listy.
	 * 
	 * @param events Lista wydarze� do edycji lub usuniecia
	 * @param text   Napis przed wyswietlan� list� wydarze�
	 */
	private static void delEvt(List<Event> events, String text) {
		String option;
		while (true) {
			if (events.size() < 1) {
				System.out.println("Nie ma wydarze� tego dnia.");
				return;
			} else {
				System.out.println(text);
				int i = -1;
				for (Event e : events) {
					System.out.println("[" + ++i + "] " + e.toString());
				}

				System.out.println("Czy chcesz usun�� jedno z wydarzen?");
				System.out.println("[u] - usun");
				System.out.println("[q] - wyjd�");
				System.out.print("opcja> ");
				option = terminal.next();

				if (option.equals("q"))
					return;
				if (!option.equals("u")) {
					System.err.println("Niepoprawna opcja");
					continue;
				}

				System.out.println("Podaj index wydarzenia [i]:");
				System.out.print("index> ");
				try {
					i = terminal.nextInt();
				} catch (Exception e) {
					System.err.println("Niepoprawny index wydarzenia.");
					continue;
				}

				Event e;
				try {
					e = events.get(i);
				} catch (IndexOutOfBoundsException ex) {
					System.err.print("Niepoprawny index.\n" + ex.getMessage());
					continue;
				}

				Operations.deleteEvent(e);
				events.remove(i);
				System.out.println("Operacja zako�czona powodzeniem.");

			}
		}
	}

	/**
	 * Uruchamia program z argumentami
	 * 
	 * @param args Opcje uruchomienia programu ["GUI", "CLI"]
	 */
	public static void main(String[] args) {

		/* dane wstepne */
		/* do test�w */
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.set(Calendar.MINUTE, tmpCalendar.get(Calendar.MINUTE));
		try {
			Operations.addEvent("1", "", "",
					Operations.parseStringToDate("17-06-2019 " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + ":"
							+ tmpCalendar.get(Calendar.MINUTE) + ":00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + 1 + ":"
							+ tmpCalendar.get(Calendar.MINUTE) + ":00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + ":"
							+ tmpCalendar.get(Calendar.MINUTE) + ":30", "dd-MM-yyyy HH:mm:ss"));
		} catch (EventException e) {
			e.printStackTrace();
		}
		tmpCalendar.set(Calendar.MINUTE, tmpCalendar.get(Calendar.MINUTE) + 1);
		try {
			Operations.addEvent("2", "", "",
					Operations.parseStringToDate("17-06-2019 " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + 1 + ":"
							+ tmpCalendar.get(Calendar.MINUTE) + ":00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + 2 + ":"
							+ tmpCalendar.get(Calendar.MINUTE) + ":00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + ":"
							+ tmpCalendar.get(Calendar.MINUTE) + ":00", "dd-MM-yyyy HH:mm:ss"));
		} catch (EventException e) {
			e.printStackTrace();
		}
		tmpCalendar.set(Calendar.MINUTE, tmpCalendar.get(Calendar.MINUTE) + 1);
		try {
			Operations.addEvent("3", "", "",
					Operations.parseStringToDate("17-06-2019 " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + 2 + ":"
							+ tmpCalendar.get(Calendar.MINUTE) + ":00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + 3 + ":"
							+ tmpCalendar.get(Calendar.MINUTE) + ":00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + ":"
							+ tmpCalendar.get(Calendar.MINUTE) + ":00", "dd-MM-yyyy HH:mm:ss"));
		} catch (EventException e) {
			e.printStackTrace();
		}

		try {
			Operations.addEvent("as", "", "",
					Operations.parseStringToDate("17-06-2019 07:20:00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 07:40:00", "dd-MM-yyyy HH:mm:ss"), null);
		} catch (EventException e) {
			e.printStackTrace();
		}
		tmpCalendar.set(Calendar.MINUTE, tmpCalendar.get(Calendar.MINUTE) + 1);
		try {
			Operations.addEvent("alolblee kk", "", "",
					Operations.parseStringToDate("17-06-2019 " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + 3 + ":"
							+ tmpCalendar.get(Calendar.MINUTE) + ":00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + 4 + ":"
							+ tmpCalendar.get(Calendar.MINUTE) + ":00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + ":"
							+ tmpCalendar.get(Calendar.MINUTE) + ":00", "dd-MM-yyyy HH:mm:ss"));
		} catch (EventException e) {
			e.printStackTrace();
		}
//		Data.SearchedEvents.add(Data.AllEvents.get(0));
//		Data.SearchedEvents.add(Data.AllEvents.get(1));
		/* dane wstepne */
		/* do test�w */

		if (args.length > 0) {
			if (args[0].equals("GUI")) {

				OrganizerWindow.show();

			} else if (args[0].equals("CLI")) {

				System.out.println("Witaj w Organizerze");
				System.out.println("Napisz 'help' by uzyskac pomoc");

				Alarm alarm = new Alarm(false);
				do {
					System.out.print("polecenie> ");
				} while (command(terminal.next()));

			} else {
				throw new RuntimeException("Niepoprawne argumenty uruchamiania programu.");
			}
		}
	}
}
