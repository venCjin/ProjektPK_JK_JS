package org.organizer;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Okno dnia. Wyœwietla wszystkie wydarzenia trwaj¹ce w ci¹gu tego dnia.
 * Pozwala usuwaæ i edytowaæ wydarzenia.
 */
public class DayWindow {
	private JFrame frame;
	private Color searchedEventColor;

	/**
	 * Tworzy okno dnia.
	 * 
	 * @param day Dzieñ w formie tekstu
	 * @param color Kolor wyszukanych wydarzeñ
	 */
	public static void show(String day, Color color) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DayWindow window = new DayWindow(day, color);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private DayWindow(String day, Color color) {
		this.searchedEventColor = color;
		initialize(day);
	}

	/**
	 * Inicjalizuje zawartoœæ okna dnia.
	 * 
	 * @param day Dzieñ w formie tekstu
	 */
	private void initialize(String day) {
		frame = new JFrame(day);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(50, 10, 240, 653); // wysokoœæ obszaru ContentPane = 624px
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		Date today = Operations.parseStringToDate(day, "dd-MM-yyyy");
		int y, duration;
		
		List<Event> todayEvents = Operations.getEventsForDay(today);

		for (Event e : todayEvents) {
			JButton eventBtn = new JButton(e.getName());

			//pokoloruj jesli byl wyszukany
			for(Event se : Data.SearchedEvents) {
				if(se.equals(e)) {
					eventBtn.setBackground(this.searchedEventColor);
					break;
				}
			}
			
			eventBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent btn) {
					String[] options = new String[] { "OK", "USUÑ" };
					int choice = JOptionPane.showOptionDialog(null, e.toString(), "Wydarzenie: " + e.getName(), JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
					if (choice == 1) {
						Operations.deleteEvent(e);
						frame.remove((Component) btn.getSource());
						frame.repaint();
					}
				}
			});

			Calendar startTime = Calendar.getInstance();
			startTime.setTime(e.getStartDate());
			int sH = startTime.get(Calendar.HOUR_OF_DAY);
			int sM = startTime.get(Calendar.MINUTE);

			Calendar endTime = Calendar.getInstance();
			endTime.setTime(e.getEndDate());
			int eH = endTime.get(Calendar.HOUR_OF_DAY);
			int eM = endTime.get(Calendar.MINUTE);

			if (e.getStartDate().before(Operations.parseDate(today, 0, 0, 0))) {
				y = 0;
			} else {
				y = getButtonHeight(sH, sM);
			}

			if (e.getEndDate().after(Operations.parseDate(today, 24, 59, 59)))
				duration = 624 - y;
			else if(y==0)
				duration = getButtonHeight(eH, eM);
			else
				duration = getButtonHeight(eH - sH, eM - sM);

			eventBtn.setBounds(34, y, 200, duration);
			frame.add(eventBtn);
		}
		
		JLabel[] hours = new JLabel[24];
		JLabel[] separators = new JLabel[24];
		StringBuilder s = new StringBuilder("");

		for (int i = 0; i < 24; ++i) {
			if (i < 10)
				s.append("0");
			s.append(i).append(":00");
			hours[i] = new JLabel(s.toString());
			hours[i].setBounds(0, i * 26 - 7, 240, 26);

			separators[i] = new JLabel("__________________________________");
			separators[i].setBounds(0, i * 26 - 12 - 7, 240, 26);

			frame.getContentPane().add(hours[i]);
			frame.getContentPane().add(separators[i]);
			s.setLength(0);
		}
	}

	/**
	 * Oblicza wysokoœæ przycisku Wydarzenia w oknie dnia
	 * 
	 * @param hours Iloœæ godzin trwania wydarzenia
	 * @param minutes Iloœæ minut (dodatkowo ponad godziny)
	 * @return Wysokosc dla przycisku w pikselach
	 */
	private int getButtonHeight(int hours, int minutes) {
		return hours * 26 + (int) ((float) minutes * 26f / 60f); // 26px = 1h
	}
}
