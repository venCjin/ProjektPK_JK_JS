package org.organizer;

import java.awt.Toolkit;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

/**
 * 
 */
public class Alarm extends Timer {

	private static final int checkTime = 1000; // 1 sekunda
	private boolean isGUI;

	/**
	 * Alarm
	 */
	public Alarm(boolean gui) {
		super();
		isGUI = gui;
		this.schedule(new CheckAlarm(), 0, checkTime);
	}
    
	private class CheckAlarm extends TimerTask {
		
		/**
		 * (non-Javadoc)
		 * 
		 * @see java.util.TimerTask#run()
		 */
		public void run() {
			List<Event> coming = Operations.getEventsAfterDateWithAlarm(Calendar.getInstance().getTime());
			long actualTime = Calendar.getInstance().getTime().getTime();
			System.out.println("--------");
			for (int i = 0; i < coming.size(); ++i) {
				System.out.println("aktu: " + actualTime);
				System.out.println("evnt: " + coming.get(i).getAlarmDate().getTime());
				System.out.println("diff: " + Math.abs(actualTime - coming.get(i).getAlarmDate().getTime()));
				System.out.println("========");
				if (checkTime > Math.abs(actualTime - coming.get(i).getAlarmDate().getTime())) {
					Toolkit.getDefaultToolkit().beep();
					if(isGUI) {
						System.out.println("Masz nadchodz¹ce wydarzenie:\n" + coming.get(i).toString());
						JOptionPane.showMessageDialog(null, "Masz nadchodz¹ce wydarzenie:\n" + coming.get(i).toString(),
							"Alarm", JOptionPane.WARNING_MESSAGE);
					} else {
						System.out.println("Masz nadchodz¹ce wydarzenie:\n" + coming.get(i).toString());
					}
				}
			}
		}
	}
}