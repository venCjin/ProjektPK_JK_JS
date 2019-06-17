package org.organizer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 */
public class Alarm extends Timer {

	private Date date;
	private List<Event> coming;
	private boolean toAlarm[];
	private static final int checkTime = 1000; // 1 sekunda
	private boolean showAlarm;
	private Event thisEvent;

	/**
	 * Alarm dla CLI
	 */
	public Alarm() {
		super();
		date = Calendar.getInstance().getTime();
		coming = Operations.getEventsFromDateWithAlarm(date);
		toAlarm = new boolean[coming.size()];
		showAlarm = false;
		for(int i = 0; i < coming.size(); ++i) toAlarm[i] = true;
		this.schedule(new ShowAlarm(), 0, checkTime);
	}

	/**
     * Zwraca flage o informacji, czy nalezy pokazac alarm na terminalu zblizajace sie wydarzenie.
     * 
     * @return Czy pokazac alarm na terminalu
     */
    public boolean getShow() {
    	return showAlarm;
    }
    
    /**
     * Zwraca wydarzenie, ktorego termin rozpoczecia sie zbliza.
     * 
     * @return Nadchodzace wydarzenie.
     */
    public Event getEvent() {
    	return thisEvent;
    }
	
    public void seenAlarm() {
    	showAlarm = false;
    }
    
	private class ShowAlarm extends TimerTask {
		
		/**
		 * (non-Javadoc)
		 * 
		 * @see java.util.TimerTask#run()
		 */
		public void run() {
			for (int i = 0; i < coming.size(); ++i) {
				if(toAlarm[i]) {
					if (checkTime < Math.abs(date.getTime() - coming.get(i).getAlarmDate().getTime())) {
						showAlarm = true;
						thisEvent = new Event(coming.get(i));
						toAlarm[i] = false;
						break;
					}
				}
			}
		}
	}
}