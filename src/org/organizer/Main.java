package org.organizer;

import java.awt.EventQueue;

public class Main {

	/**
	 * Shows GUI
	 */
	public static void main(String[] args) {

		if (args.length > 0)
			if (args[0].equals("XML")) {}
//				Data.AllEvents = Input.AutoImportXML();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrganizerWindow window = new OrganizerWindow();
					window.window.setVisible(true);
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
