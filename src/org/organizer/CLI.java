package org.organizer;

import java.awt.Toolkit;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

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
//			System.out.println("Podaj date alarmu w formacie dd-MM-yyyy  : ");
			System.out.println("Podaj date alarmu w formacie dd-MM-yyyy HH:mm:ss : ");
			System.out.print("data alarmu> "); alarmDate = terminal.next();
			alarmDate += " " + terminal.next();
		}

		try {
			Operations.addEvent(
					name,
					desc,
					place,
					Operations.parseStringToDate(startDate, "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate(endDate, "dd-MM-yyyy HH:mm:ss"),
//					Operations.parseStringToDate(alarmDate, "dd-MM-yyyy")
					Operations.parseStringToDate(alarmDate, "dd-MM-yyyy HH:mm:ss")
					);
		} catch (EventException e) {
			System.err.println(e.getMessage());
		}
		
		System.out.println("Dodano wydarzenie.");
	}

	/**
	 * Terminalowe menu do wyszukiwania wydarzeñ zawieracjacych podan¹ fraze.
	 */
	private static void search() {
		String phrase;
		terminal.nextLine();
		System.out.println("Podaj frazê do wyszukania w nazwie wydarzenia :");
		System.out.print("fraza> "); phrase = terminal.nextLine();
		
		Operations.searchEvents(phrase);
		
		delOrEdit(Data.SearchedEvents, "Wyszukane wydarzenia:");
	}

	/**
	 * Terminalowe menu do usuwania wydarzeñ starszych niz podana data.
	 */
	private static void delBefore() {
		String date;
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
		String date;
		System.out.println("Podaj date (dd-MM-yyyy):");
		System.out.print("data> "); date = terminal.next();

		List<Event> dayEvents = Operations.getEventsForDay(Operations.parseStringToDate(date, "dd-MM-yyyy"));

		delOrEdit(dayEvents, "Wydarzenia trwaj¹ce tego dnia:");
	}

	/**
	 * Terminalowe menu do zapisu wydarzeñ.
	 */
	private static void save() {
		String option;
		System.out.println("Gdzie chcesz wyeksportowaæ dane?");
		System.out.println("[1] baza danych SQL");
		System.out.println("[2] plik XML");
		System.out.println("[3] plik CSV");
		System.out.print("opcja> "); option = terminal.next();

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
			System.out.println("B³êdna opcja");
		}
	}

	/**
	 * Terminalowe menu do wczytywania wydarzeñ.
	 */
	private static void load() {
		String option;
		System.out.println("Sk¹d chcesz zaimportowaæ dane?");
		System.out.println("[1] baza danych SQL");
		System.out.println("[2] plik XML");
		System.out.println("[3] plik CSV");
		System.out.print("opcja> "); option = terminal.next();

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
			System.out.println("B³êdna opcja");
		}
	}

	/**
	 * Wyœwietla help na konsoli.
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
	 * @return Czy kontynuowac wykonywanie poleceñ
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
	 * Pozwala usuwaæ i edytowaæ wybrane wydarzenia z przekazanej listy.
	 * 
	 * @param events Lista wydarzeñ do edycji lub usuniecia
	 * @param text Napis przed wyswietlan¹ list¹ wydarzeñ
	 */
	private static void delOrEdit(List<Event> events, String text) {
		String option;
		while (true) {
			if (events.size() < 1) {
				System.out.println("Nie ma wydarzeñ tego dnia.");
				return;
			} else {
				System.out.println(text);
				int i = -1;
				for (Event e : events) {
					System.out.println("[" + ++i + "] " + e.toString());
				}

				System.out.println("Czy chcesz usun¹æ lub edytowaæ jedno z wydarzen?");
				System.out.println("[u] - usun");
				System.out.println("[e] - edytuj");
				System.out.println("[q] - wyjdŸ");
				System.out.print("opcja> "); option = terminal.next();

				if (option.equals("q"))
					return;
				if (!option.equals("u") && !option.equals("e")) {
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

				switch (option) {
				case "u":
					Operations.deleteEvent(e);
					events.remove(i);
					System.out.println("Operacja zakoñczona powodzeniem.");
					break;
				case "e":
					// TODO edit

					break;
				default:
					System.err.println("Niepoprawna opcja");
				}
			}
		}
	}
	
	private static void alarm(Alarm alarm, boolean gui) {
		if(alarm.getShow()) {
			Toolkit.getDefaultToolkit().beep();
			if(gui)
				JOptionPane.showMessageDialog(null, "Masz nadchodz¹ce wydarzenie:\n" + alarm.getEvent().toString(),
					"Alarm", JOptionPane.WARNING_MESSAGE);
			else
				System.out.println("Masz nadchodz¹ce wydarzenie:\n" + alarm.getEvent().toString());
			alarm.seenAlarm();
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
			Operations.addEvent("1", "", "", Operations.parseStringToDate("17-06-2019 01:00:00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 02:30:00", "dd-MM-yyyy HH:mm:ss"), Operations.parseStringToDate("16-06-2019 11:28:00", "dd-MM-yyyy HH:mm:ss"));
		} catch (EventException e) {
			e.printStackTrace();
		}
		try {
			Operations.addEvent("2", "", "", Operations.parseStringToDate("17-06-2019 03:00:00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 05:00:00", "dd-MM-yyyy HH:mm:ss"), null);
		} catch (EventException e) {
			e.printStackTrace();
		}
		try {
			Operations.addEvent("3", "", "", Operations.parseStringToDate("17-06-2019 05:00:00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 07:11:00", "dd-MM-yyyy HH:mm:ss"), null);
		} catch (EventException e) {
			e.printStackTrace();
		}
		
		try {
			Operations.addEvent("as", "", "", Operations.parseStringToDate("17-06-2019 07:20:00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("17-06-2019 07:40:00", "dd-MM-yyyy HH:mm:ss"), null);
		} catch (EventException e) {
			e.printStackTrace();
		}
		try {
			Operations.addEvent("alolblee kk", "", "", Operations.parseStringToDate("17-06-2019 08:00:00", "dd-MM-yyyy HH:mm:ss"),
					Operations.parseStringToDate("18-06-2019 07:11:00", "dd-MM-yyyy HH:mm:ss"), null);
		} catch (EventException e) {
			e.printStackTrace();
		}
//		Data.SearchedEvents.add(Data.AllEvents.get(0));
//		Data.SearchedEvents.add(Data.AllEvents.get(1));
/* dane wstepne */
/* do testów */

		if (args.length > 0) {
			if (args[0].equals("GUI")) {
				
				Alarm alarm = new Alarm();
				OrganizerWindow.show();
				alarm(alarm, true);
				
			} else if (args[0].equals("CLI")) {
				
				System.out.println("Witaj w Organizerze");
				System.out.println("Napisz 'help' by uzyskac pomoc");
				
				Alarm alarm = new Alarm();
				do {
					alarm(alarm, false);
				} while (command(terminal.next()));
				
			} else {
				throw new RuntimeException("Niepoprawne argumenty uruchamiania programu.");
			}
		}
	}
}
