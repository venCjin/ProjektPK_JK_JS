package org.organizer;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;

import com.toedter.calendar.JCalendar;

public class Operations {

	/**
	 * Zamienia String na date.
	 * 
	 * @param d Data zapisana jako tekst (String)
	 * @param p Wzorzec formatu daty
	 * @return Date ze Stringa
	 */
	public static Date parseStringToDate(String d, String p) {
		Date date = null;
		try {
			SimpleDateFormat out = new SimpleDateFormat(p);
			date = out.parse(d);
		} catch (IllegalArgumentException e) {
			// wzorzec jest niepoprwany
			e.getMessage();
			date = null;
		} catch (ParseException e) {
			// nie uda³o siê przetworzyc na date, pewni String data nie poprawny
			e.getMessage();
			date = null;
		} catch (NullPointerException e) {
			// nie uda³o siê przetworzyc na date, pewni String data nie poprawny
			e.getMessage();
			date = null;
		}
		return date;
	}

	/**
	 * Zamienia date na String.
	 * 
	 * @param d Data
	 * @param p Wzorzec formatu daty
	 * @return Datê w formie tekstu w podanym formacie lub null dla niepoprwnej daty
	 */
	public static String parseDateToString(Date d, String p) {
		String date = null;
		try {
			SimpleDateFormat out = new SimpleDateFormat(p);
			date = out.format(d).toString();
		} catch (IllegalArgumentException e) {
			// wzorzec jest niepoprwany
			e.getMessage();
			date = null;
		} catch (NullPointerException e) {
			// data jest nullem
			e.getMessage();
			date = null;
		}
		return date;
	}

	/**
	 * Scala podana date z czasem 00:00:00.
	 * 
	 * @param d Data wejsciowa
	 * @return Data w formacie "dd-MM-yyyy 00:00:00" lub null dla niepoprwnej daty
	 */
	public static Date parseDate(Date d) {
		return parseDate(d, 0, 0, 0);
	}

	/**
	 * Scala podana date z podanym czasem.
	 * 
	 * @param d Data wejsciowa
	 * @param h Godziny
	 * @param m Minuty
	 * @param s Sekundy
	 * @return Data w formacie "dd-MM-yyyy HH:mm:ss" lub null dla niepoprwnej daty
	 */
	public static Date parseDate(Date d, int h, int m, int s) {
		SimpleDateFormat in = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat out = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		StringBuilder buf = new StringBuilder("");
		Date date = null;

		if (d == null)
			return date;

		try {
			buf.append(in.format(d));

			buf.append(" ");

			if (h < 10)
				buf.append("0");
			buf.append(h);

			buf.append(":");

			if (m < 10)
				buf.append("0");
			buf.append(m);

			buf.append(":");

			if (s < 10)
				buf.append("0");
			buf.append(s);

//			System.out.println("end: " + buf.toString());
			date = out.parse(buf.toString());
		} catch (Exception e) {
			// e.printStackTrace();
			date = null;
		}
		return date;
	}

	/**
	 * Dodaje nowe Wydarzenie i sprawdza czy juz nie ma wtedy innego Wydarzenia
	 * 
	 * @param name       Nazwa Wydarzenia
	 * @param desc       Opis Wydarzenia
	 * @param place      Miejsce Wydarzenia
	 * @param startDate  Data pocz¹tku Wydarzenia
	 * @param endDate    Data koñca Wydarzenia
	 * @param alarmDate  Data alarmu
	 * @param importance Wa¿noœæ Wydarzenia
	 * @throws EventError
	 */
	public static void addEvent(String name, String desc, String place, Date startDate, Date endDate, Date alarmDate,
			int importance) throws EventError {

		if (name == "" || name == null || name.isEmpty())
			throw new EventError("Pusta nazwa");

		if (startDate == null)
			throw new EventError("Nieprawid³owa data rozpoczecia wydarzenia.");

		if (endDate == null)
			throw new EventError("Nieprawid³owa data zakonczenia wydarzenia.");

		if (endDate.before(startDate))
			throw new EventError("Data rozpoczecia wydarzenia musi byc przed jego zakonczeniem.");

		// TODO sprawdziæ bo coœ mi nie pasi
		for (Event e : Data.AllEvents) {
			if (startDate.after(e.startDate) && endDate.before(e.endDate))
				throw new EventError("Nowe wydarzenie odbywa siê w trakcie innego.");

			if (startDate.before(e.endDate) && endDate.after(e.endDate))
				throw new EventError("Nowe wydarzenie zaczyna siê przed zakoñczeniem poprzedniego.");

			if (endDate.after(e.startDate) && startDate.before(e.startDate))
				throw new EventError("Nowe wydarzenie koñczy siê po rozpoczêciu nastêpnego.");
		}

		Data.AllEvents.add(new Event(name, desc, place, startDate, endDate, alarmDate, importance));
		sortDate();
	}

	/**
	 * Zwraca listê Wydarzeñ dla danego dnia.
	 * 
	 * @param day Data dnia w formacie "dd-MM-yyyy"
	 * @return Lista wydarzeñ danego dnia
	 */
	public static List<Event> getEventsForDay(Date day) {
		if (day == null)
			return null;
		List<Event> dayEvents = new ArrayList<Event>();
		for (Event e : Data.AllEvents) {
			if (e.endDate.before(Operations.parseDate(day, 0, 0, 0)))
				continue;
			if (e.startDate.after(Operations.parseDate(day, 24, 59, 59)))
				continue;
			dayEvents.add(e); // przekazujemy przez referencjê ¿eby mo¿na by³o go pozniej edytowaæ lub usunac
		}
		return dayEvents;
	}

	public static void deleteEvent(Event e) {
		Data.AllEvents.remove(e);
//		Data.SearchedEvents.remove(e);
	}

	/**
	 * Usuwa Wydarzenia, które zakoñcz¹ siê przed podan¹ dat¹.
	 * 
	 * @param d Data
	 * @throws EventError
	 */
	public static void deleteEventsBefore(Date d) {
		Date del = Operations.parseDate(d);
		for (Event e : Data.AllEvents)
			if (e.endDate.before(del))
				Data.AllEvents.remove(e);
		Data.SearchedEvents = new ArrayList<>(Data.AllEvents);
	}

	/**
	 * Sortuje Wydarzenia po dacie
	 */
	public static void sortDate() {
		Collections.sort(Data.AllEvents);
	}

	/**
	 * Sortuje Wydarzenia po ich wa¿nosci
	 */
	public static void sortImportance() {
		Collections.sort(Data.AllEvents, Event.ImportanceComparator);
	}

	/**
	 * Wyszukuje Wydarzenia, które maj¹ w nazwie podan¹ frazê.
	 * 
	 * @param phrase Fraza do wyszukania
	 */
	public static void searchEvents(String phrase) {
		Data.SearchedEvents.clear();
		for (Event e : Data.AllEvents)
			if (e.getName().contains(phrase))
				Data.SearchedEvents.add(e);
	}

	public static String info() {
		return "ORGANIZER\nProgram stworzony na laboratoriach Programowania Komponentowego\nAutorzy: Jakub Klepacz, Jaros³aw Suchiñski";
	}

	/*
	 * Metody obs³uguj¹ce GUI
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	/**
	 * Koloruje na wybrany kolor dni, w których s¹ wyszukane wydarzenia.
	 * 
	 * @param calendar Kalendarz, obiekt klasy JCalendar
	 * @param c        Kolor
	 */
	public static void colorSearchedEvents(JCalendar calendar, Color c) {
		JButton j = getDayButton(calendar, 1);
		j.setBackground(c);

		// TODO eee reszta kodu

//		System.out.println(offset);
		// 49 = 7(decoration) + hidden(x1) + (28/30/31 monthDays) + hidden(x2)
//		for(int i=0; i<49 ; ++i)
//			System.out.println(j.getComponent(i));
	}

	/**
	 * Zwraca JButton okreœlonego dnia miesi¹ca z Kalendarza.
	 * 
	 * @param calendar Kalendarz, obiekt klasy JCalendar
	 * @param day      Dzieñ miesiaca
	 * @return JButton okreœlonego dania miesiaca
	 */
	public static JButton getDayButton(JCalendar calendar, int day) {
		String input = "1/" + (calendar.getMonthChooser().getMonth() + 1) + "/" + calendar.getYearChooser().getYear();
		Calendar temp = Calendar.getInstance();
		temp.setTime(Operations.parseStringToDate(input, "d/M/yyyy"));
		int offset = temp.get(Calendar.DAY_OF_WEEK); // 1==nd
		if (offset == 1) // jesli niedziela(==1) to 6 buttonow przed nia nieaktywnych
			offset = 6;
		else // jesli pon(==2) to 0 buttonow przed nia nieaktywnych
			offset -= 2;
		offset += 6; // 7 buttonow nieaktywnych wyswietlajacych dni tygonia
						// ustawiamy na ostatnim bo jesli day==1 to
		if (day < 1)
			day = 1;

		int daysInCurrentMonth = 1;
		switch (calendar.getMonthChooser().getMonth() + 1) {
		case 2:
			if (calendar.getYearChooser().getYear() % 4 == 0)
				daysInCurrentMonth = 29;
			else
				daysInCurrentMonth = 28;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			daysInCurrentMonth = 30;
			break;
		default:
			daysInCurrentMonth = 31;
		}

		if (day > daysInCurrentMonth)
			day = daysInCurrentMonth;
		offset += day;

		JButton j = (JButton) calendar.getDayChooser().getDayPanel().getComponent(offset);
		return j;
	}

}
