package org.organizer;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
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
			// nie uda�o si� przetworzyc na date, pewnie String data nie poprawny
			e.getMessage();
			date = null;
		} catch (NullPointerException e) {
			// nie uda�o si� przetworzyc na date, pewnie String data nie poprawny
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
	 * @return Dat� w formie tekstu w podanym formacie lub null dla niepoprwnej daty
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

			date = out.parse(buf.toString());
		} catch (Exception e) {
			// e.printStackTrace();
			date = null;
		}
		return date;
	}

	/**
	 * Dodaje nowe wydarzenie i sprawdza czy juz nie ma wtedy innego wydarzenia
	 * 
	 * @param name      Nazwa wydarzenia
	 * @param desc      Opis wydarzenia
	 * @param place     Miejsce wydarzenia
	 * @param startDate Data pocz�tku wydarzenia
	 * @param endDate   Data ko�ca wydarzenia
	 * @param alarmDate Data alarmu
	 * @throws EventException
	 */
	public static void addEvent(String name, String desc, String place, Date startDate, Date endDate, Date alarmDate)
			throws EventException {

		if (name == "" || name == null || name.isEmpty())
			throw new EventException("Pusta nazwa");

		if (startDate == null)
			throw new EventException("Nieprawid�owa data rozpoczecia wydarzenia.");

		if (endDate == null)
			throw new EventException("Nieprawid�owa data zakonczenia wydarzenia.");

		if (endDate.before(startDate))
			throw new EventException("Data rozpoczecia wydarzenia musi byc przed jego zakonczeniem.");

		for (Event e : Data.AllEvents) {
			if ((startDate.after(e.getStartDate()) || startDate.equals(e.getStartDate()))
							&& (endDate.before(e.getEndDate()) || endDate.equals(e.getEndDate())))
				throw new EventException("Nowe wydarzenie odbywa si� w trakcie innego.");

			if (startDate.before(e.getEndDate()) && endDate.after(e.getEndDate()))
				throw new EventException("Nowe wydarzenie zaczyna si� przed zako�czeniem poprzedniego.");

			if (endDate.after(e.getStartDate()) && startDate.before(e.getStartDate()))
				throw new EventException("Nowe wydarzenie ko�czy si� po rozpocz�ciu nast�pnego.");
		}

		Data.AllEvents.add(new Event(name, desc, place, startDate, endDate, alarmDate));
		sortDate();
	}

	/**
	 * Zwraca list� wydarze� dla danego dnia.
	 * 
	 * @param day Data dnia w formacie "dd-MM-yyyy"
	 * @return Lista wydarze� danego dnia
	 */
	public static List<Event> getEventsForDay(Date day) {
		if (day == null)
			return null;
		
		Date start = Operations.parseDate(day, 0, 0, 0);
		Date end = Operations.parseDate(day, 24, 59, 59);
		
		List<Event> dayEvents = new ArrayList<Event>();
		
		for (Event e : Data.AllEvents) {
			if (e.getEndDate().before(start))
				continue;
			if (e.getStartDate().after(end))
				continue;
			dayEvents.add(e); // przekazujemy przez referencj� �eby mo�na by�o go pozniej edytowa� lub usunac
		}
		return dayEvents;
	}

	/**
	 * Zwraca list� wydarze� dla danego miesiaca.
	 * 
	 * @param calendar Kalendarz, obiekt klasy JCalendar
	 * @param events Lista wydarze� do przeszukania
	 * @return Lista wydarze� z danego miesiaca
	 */
	public static List<Event> getEventsForActualMonth(JCalendar calendar, List<Event> events) {

		Calendar tmpCalendar = (Calendar) calendar.getCalendar().clone();
		tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
		Date start = parseDate(tmpCalendar.getTime());
		
		tmpCalendar.add(Calendar.MONTH, 1);
		tmpCalendar.add(Calendar.DAY_OF_MONTH, -1);
		Date end = parseDate(tmpCalendar.getTime(), 24, 59, 59);
		
		List<Event> monthEvents = new ArrayList<Event>();
		for (Event e : events) {
			if (e.getEndDate().before(start))
				continue;
			if (e.getStartDate().after(end))
				continue;
			monthEvents.add(e); // przekazujemy przez referencj� �eby mo�na by�o go pozniej edytowa� lub usunac
		}
		return monthEvents;
	}
	
	public static void deleteEvent(Event e) {
		Data.AllEvents.remove(e);
	}

	public static List<Event> getEventsAfterDateWithAlarm(Date d) {
		List<Event> found = new ArrayList<Event>();
		
		for(Event e : Data.AllEvents) {
			if (e.getStartDate().after(d) && e.getAlarmDate() != null)
				found.add(new Event(e));
		}
		
		return found;
	}
	
	/**
	 * Usuwa wydarzenia, kt�re zako�cz� si� przed podan� dat� i czy�ci liste szukanych wydarze�.
	 * 
	 * @param d Data
	 * @throws DateTimeException
	 */
	public static void deleteEventsBefore(Date d) throws DateTimeException {
		d = Operations.parseDate(d);
		if (d == null)
			throw new DateTimeException("Niepoprawna data.");

		Data.SearchedEvents = new ArrayList<Event>(Data.AllEvents);
		
		for(Event e : Data.SearchedEvents) {
			if (e.getEndDate().before(d))
				Data.AllEvents.remove(e);
		}
		
		Data.SearchedEvents.clear();
	}

	/**
	 * Sortuje wydarzenia po dacie
	 */
	public static void sortDate() {
		Collections.sort(Data.AllEvents);
	}

	/**
	 * Sortuje wydarzenia po ich nazwie
	 */
	public static void sortName() {
		Collections.sort(Data.AllEvents, Event.NameComparator);
	}

	/**
	 * Wyszukuje Wydarzenia, kt�re maj� w nazwie podan� fraz�.
	 * 
	 * @param phrase Fraza do wyszukania
	 */
	public static void searchEvents(String phrase) {
		Data.SearchedEvents.clear();
		for (Event e : Data.AllEvents)
			if (e.getName().contains(phrase))
				Data.SearchedEvents.add(new Event(e));
	}

	public static String info() {
		return "ORGANIZER\nProgram stworzony na laboratoriach Programowania Komponentowego\nAutorzy: Jakub Klepacz, Jaros�aw Suchi�ski";
	}

	/*
	 * Metody obs�uguj�ce GUI
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
	 * Koloruje na wybrany kolor dni, w kt�rych s� wyszukane wydarzenia.
	 * 
	 * @param calendar Kalendarz, obiekt klasy JCalendar
	 * @param c        Kolor
	 */
	public static void colorEventsInMonth(JCalendar calendar, List<Event> events, Color c) {
		
		List<Event> l = getEventsForActualMonth(calendar, events);
		Calendar start = (Calendar) calendar.getCalendar().clone();
		Calendar end = (Calendar) calendar.getCalendar().clone();
		
		for(Event e : l) {
			start.setTime(e.getStartDate());
			end.setTime(e.getEndDate());
			JButton j = getDayButton(calendar, start.get(Calendar.DAY_OF_MONTH));
			j.setBackground(c);
		}
	}

	/**
	 * Zwraca JButton okre�lonego dnia miesi�ca z Kalendarza.
	 * 
	 * @param calendar Kalendarz, obiekt klasy JCalendar
	 * @param day      Dzie� miesiaca
	 * @return JButton okre�lonego dania miesiaca
	 */
	public static JButton getDayButton(JCalendar calendar, int day) {
		
		Calendar tmpCalendar = (Calendar) calendar.getCalendar().clone(); // aktualna data
		tmpCalendar.set(Calendar.DAY_OF_MONTH, 1); // pierwszy dzie� miesiaca
		
		int offset = tmpCalendar.get(Calendar.DAY_OF_WEEK); // 1==nd
		if (offset == 1)	// jesli niedziela(==1) to 6 buttonow przed nia nieaktywnych
			offset = 6;
		else				// jesli pon(==2) to 0 buttonow przed nia nieaktywnych
			offset -= 2;
		offset += 6;		// 7 buttonow nieaktywnych wyswietlajacych dni tygonia
							// ustawiamy na ostatnim bo jesli day==1 to
		if (day < 1)
			day = 1;

		tmpCalendar.add(Calendar.MONTH, 1);
		tmpCalendar.add(Calendar.DAY_OF_MONTH, -1);
		int daysInCurrentMonth = tmpCalendar.get(Calendar.DAY_OF_MONTH);

		if (day > daysInCurrentMonth)
			day = daysInCurrentMonth;
		offset += day;

		JButton j = (JButton) calendar.getDayChooser().getDayPanel().getComponent(offset);
		return j;
	}

}
