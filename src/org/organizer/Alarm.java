package org.organizer;

import java.awt.Toolkit;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

/**
 * Tworzy alarmy dla wydarzen
 */
public class Alarm extends Timer {

	private boolean isGUI;
	private List<Event> coming;

	/**
	 * Tworzy alarmy dla wydarzen
	 * 
	 * @param gui Czy wyswietlic okno dialogowe
	 */
	public Alarm(boolean gui) {
		super();
		isGUI = gui;

		coming = Operations.getEventsAfterDateWithAlarm(Calendar.getInstance().getTime());

		for (Event e : coming)
			this.schedule(new RingAlarm(e), e.getAlarmDate());
	}

	public void eventWithAlarmAdded(Event e) {
		this.schedule(new RingAlarm(e), e.getAlarmDate());
	}

	private class RingAlarm extends TimerTask {

		private Event e;

		public RingAlarm(Event e) {
			this.e = e;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see java.util.TimerTask#run()
		 */
		public void run() {
			Toolkit.getDefaultToolkit().beep();
			if (isGUI) {
				System.out.println("Masz nadchodz¹ce wydarzenie:\n" + this.e.toString());
				JOptionPane.showMessageDialog(null, "Masz nadchodz¹ce wydarzenie:\n" + this.e.toString(), "Alarm",
						JOptionPane.WARNING_MESSAGE);
			} else {
				System.out.println("Masz nadchodz¹ce wydarzenie:\n" + this.e.toString());
			}
		}

	}

}