package org.organizer;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 */
public class Alarm extends Timer {

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
		coming = Operations.getEventsAfterDateWithAlarm(Calendar.getInstance().getTime());
		toAlarm = new boolean[coming.size()];
		showAlarm = false;
		for(int i = 0; i < coming.size(); ++i) toAlarm[i] = true;
		this.schedule(new CheckAlarm(), 0, checkTime*2);
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
    	coming.remove(this.thisEvent);
    }
    
	private class CheckAlarm extends TimerTask {
		
		/**
		 * (non-Javadoc)
		 * 
		 * @see java.util.TimerTask#run()
		 */
		public void run() {
			long actualTime = Calendar.getInstance().getTime().getTime();
			System.out.println("--------");
			for (int i = 0; i < coming.size(); ++i) {
//				if(toAlarm[i]) {
					System.out.println("aktu: " + actualTime);
					System.out.println("evnt: " + coming.get(i).getAlarmDate().getTime());
					System.out.println("diff: " + Math.abs(actualTime - coming.get(i).getAlarmDate().getTime()));
					System.out.println("========");
					if (60*checkTime > Math.abs(actualTime - coming.get(i).getAlarmDate().getTime())) {
						showAlarm = true;
//						thisEvent = new Event(coming.get(i));
						thisEvent = coming.get(i);
//						toAlarm[i] = false;
						break;
//					}
				}
			}
		}
	}
}