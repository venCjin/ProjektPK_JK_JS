package org.organizer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Operations {

	/**
	 * Zamienia date na String
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

	public static Date parseDate(Date d) {
		return parseDate(d, 0, 0, 0);
	}

	/**
	 * Scala podana date z czasem
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
	 * Usuwa Wydarzenia, które zakoñcz¹ siê przed podan¹ dat¹
	 * @param d data
	 */
	public static void deleteEventsBefore(Date d) {
		Date del = Operations.parseDate(d);
		for (Event e : Data.AllEvents)
			if (e.endDate.before(del))
				Data.AllEvents.remove(e);
		//TODO refresh ??
	}
}
