package org.organizer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Operations {

	/**
	 * Scala podana date z czasem
	 * 
	 * @param d Data wejsciowa
	 * @param h Godziny
	 * @param m Minuty
	 * @param s Sekundy
	 * @return date Data w formacie "dd-MM-yyyy HH:mm:ss"
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
}
