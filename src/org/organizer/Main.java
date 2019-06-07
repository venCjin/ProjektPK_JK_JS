package org.organizer;

import java.awt.EventQueue;
//import java.text.SimpleDateFormat;
//import java.util.Date;

//import org.organizer.Logic;

public class Main {

	/**
	 * Shows GUI
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Organizer window = new Organizer();
					window.monthWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		// TODO Warstwa Logki
		/*
		 * Logic.RingAllAlerts();
		 */

		// TESTS AND SHIT
		// Instantiate a Date object
		/*
		 * Date date = new Date(); SimpleDateFormat dateFormat = new
		 * SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		 * System.out.println("Today's date is: "+dateFormat.format(date));
		 */
	}

}
