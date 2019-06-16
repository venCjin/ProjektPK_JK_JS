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
			// nie uda³o siê przetworzyc na date, pewnie String data nie poprawny
			e.getMessage();
			date = null;
		} catch (NullPointerException e) {
			// nie uda³o siê przetworzyc na date, pewnie String data nie poprawny
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
	 * @param startDate Data pocz¹tku wydarzenia
	 * @param endDate   Data koñca wydarzenia
	 * @param alarmDate Data alarmu
	 * @throws EventException
	 */
	public static void addEvent(String name, String desc, String place, Date startDate, Date endDate, Date alarmDate)
			throws EventException {

		if (name == "" || name == null || name.isEmpty())
			throw new EventException("Pusta nazwa");

		if (startDate == null)
			throw new EventException("Nieprawid³owa data rozpoczecia wydarzenia.");

		if (endDate == null)
			throw new EventException("Nieprawid³owa data zakonczenia wydarzenia.");

		if (endDate.before(startDate))
			throw new EventException("Data rozpoczecia wydarzenia musi byc przed jego zakonczeniem.");

		for (Event e : Data.AllEvents) {
			if (startDate.after(e.getStartDate()) && endDate.before(e.getEndDate()))
				throw new EventException("Nowe wydarzenie odbywa siê w trakcie innego.");

			if (startDate.before(e.getEndDate()) && endDate.after(e.getEndDate()))
				throw new EventException("Nowe wydarzenie zaczyna siê przed zakoñczeniem poprzedniego.");

			if (endDate.after(e.getStartDate()) && startDate.before(e.getStartDate()))
				throw new EventException("Nowe wydarzenie koñczy siê po rozpoczêciu nastêpnego.");
		}

		Data.AllEvents.add(new Event(name, desc, place, startDate, endDate, alarmDate));
		sortDate();
	}

	/**
	 * Zwraca listê wydarzeñ dla danego dnia.
	 * 
	 * @param day Data dnia w formacie "dd-MM-yyyy"
	 * @return Lista wydarzeñ danego dnia
	 */
	public static List<Event> getEventsForDay(Date day) {
		if (day == null)
			return null;
		List<Event> dayEvents = new ArrayList<Event>();
		for (Event e : Data.AllEvents) {
			if (e.getEndDate().before(Operations.parseDate(day, 0, 0, 0)))
				continue;
			if (e.getStartDate().after(Operations.parseDate(day, 24, 59, 59)))
				continue;
			dayEvents.add(e); // przekazujemy przez referencjê ¿eby mo¿na by³o go pozniej edytowaæ lub usunac
		}
		return dayEvents;
	}

	public static void deleteEvent(Event e) {
		Data.AllEvents.remove(e);
	}

	/**
	 * Usuwa wydarzenia, które zakoñcz¹ siê przed podan¹ dat¹ i czyœci liste
	 * szukanych wydarzeñ.
	 * 
	 * @param d Data
	 * @throws DateTimeException
	 */
	public static void deleteEventsBefore(Date d) throws DateTimeException {
		Date del = Operations.parseDate(d);
		if (del == null)
			throw new DateTimeException("Niepoprawna data.");

		for (int i = 0; i < Data.AllEvents.size(); i++)
			if (Data.AllEvents.get(i).getEndDate().before(del))
				Data.AllEvents.remove(i);

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

//		int daysInCurrentMonth = 1;
//		switch (calendar.getMonthChooser().getMonth() + 1) {
//		case 2:
//			if (calendar.getYearChooser().getYear() % 4 == 0)
//				daysInCurrentMonth = 29;
//			else
//				daysInCurrentMonth = 28;
//			break;
//		case 4:
//		case 6:
//		case 9:
//		case 11:
//			daysInCurrentMonth = 30;
//			break;
//		default:
//			daysInCurrentMonth = 31;
//		}

		Calendar tmpCalendar = (Calendar) calendar.getCalendar().clone();
		tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
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
