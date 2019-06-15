package org.organizer;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
			e.printStackTrace();
			date = null;
		} catch (Exception e) {
			e.printStackTrace();
			date = null;
		}
		return date;
	}

	/**
	 * Zamienia date na String.
	 * 
	 * @param d Data
	 * @param p Wzorzec formatu daty
	 * @return String daty w podanym formacie
	 */
	public static String parseDateToString(Date d, String p) {
		String date = null;
		try {
			SimpleDateFormat out = new SimpleDateFormat(p);
			date = out.format(d);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			date = null;
		} catch (Exception e) {
			e.printStackTrace();
			date = null;
		}
		return date;
	}

	/**
	 * Scala podana date z czasem 00:00:00.
	 * 
	 * @param d Data wejsciowa
	 * @return Data w formacie "dd-MM-yyyy 00:00:00"
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
	 * @return Data w formacie "dd-MM-yyyy HH:mm:ss"
	 */
	public static Date parseDate(Date d, int h, int m, int s) {
		SimpleDateFormat in = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat out = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		StringBuilder buf = new StringBuilder("");
		Date date = null;

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
			e.printStackTrace();
			date = null;
		}
		return date;
	}

	/**
	 * Usuwa Wydarzenia, które zakoñcz¹ siê przed podan¹ dat¹.
	 * 
	 * @param d Data
	 */
	public static void deleteEventsBefore(Date d) {
		Date del = Operations.parseDate(d);
		if (del == null) {
			JOptionPane.showMessageDialog(new JFrame(), "Niepoprawna data.", "EventError", JOptionPane.ERROR_MESSAGE);
//			return 
		}
		for (Event e : Data.AllEvents)
			if (e.endDate.before(del))
				Data.AllEvents.remove(e);
		Data.SearchedEvents = new ArrayList<>(Data.AllEvents);
	}

	/**
	 * Dodaje nowe Wydarzenie i sprawdza czy juz nie ma wtedy innego Wydarzenia
	 * 
	 * @param e Wydarzenie do dodania
	 * @throws EventError
	 */
	
	
	/**
	 * 
	 * @param name Nazwa Wydarzenia
	 * @param desc Opis Wydarzenia
	 * @param place Miejsce 
	 * @param startDate Data pocz¹tku Wydarzenia
	 * @param endDate Data koñca Wydarzenia
	 * @param alarmDate Data alarmu
	 * @param importance Wa¿noœæ Wydarzenia
	 * @throws EventError
	 */
	public static void addEvent(String name, String desc, String place, Date startDate, Date endDate, Date alarmDate,
			int importance) throws EventError {
		Event e = null;
		e = new Event(name, desc, place, startDate, endDate, alarmDate, importance);

		// TODO sprawdziæ bo coœ mi nie pasi
		for (int i = 0; i < Data.AllEvents.size(); i++) {
			if (e.startDate.after(Data.AllEvents.get(i).startDate) && e.startDate.before(Data.AllEvents.get(i).endDate))
				throw new EventError("Nowe wydarzenie odbywa siê w trakcie innego.");
			if (e.startDate.before(Data.AllEvents.get(i).endDate))
				throw new EventError("Nowe wydarzenie zaczyna siê przed zakoñczeniem poprzedniego.");
			if (e.endDate.after(Data.AllEvents.get(i).startDate))
				throw new EventError("Nowe wydarzenie koñczy siê po rozpoczêciu nastêpnego.");
		}
		Data.AllEvents.add(e);
		sortDate();
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
	 * @param p Fraza do wyszukania
	 */
	public static void searchEvents(String p) {
		Data.SearchedEvents.clear();
		for (Event e : Data.AllEvents)
			if (e.getName().contains(p))
				Data.SearchedEvents.add(e);
	}

	/* Metody obs³uguj¹ce GUI */
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
