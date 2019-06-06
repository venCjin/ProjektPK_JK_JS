package org.organizer;

import java.awt.EventQueue;

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
		Logic.RingAllAlerts();
		*/
	}

}
