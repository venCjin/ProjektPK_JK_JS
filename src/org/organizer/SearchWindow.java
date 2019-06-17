package org.organizer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Okno do wyszukiwania wydarzen. Koloruje wydarzenia w kalendarzu.
 */
public class SearchWindow {

	private JFrame frame;
	private JTextField phraseText;

	/**
	 * Tworzy okno wyszukiwania wydarzeñ.
	 * 
	 * @param calendar Kalendarz w ktorym bêd¹ kolorowane wyszukane wydarzenia
	 */
	public static void show(JCalendar calendar) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchWindow window = new SearchWindow(calendar);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private SearchWindow(JCalendar calendar) {
		initialize(calendar);
	}

	/**
	 * Inicjalizuje zawartoœæ okna wyszukiwania wydarzeñ.
	 */
	private void initialize(JCalendar calendar) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 360, 120);
		frame.getContentPane().setLayout(null);

		phraseText = new JTextField();
		phraseText.setBounds(138, 11, 196, 25);
		frame.getContentPane().add(phraseText);
		phraseText.setColumns(10);

		JLabel lblPhrase = new JLabel("Fraza do wyszukania:");
		lblPhrase.setBounds(10, 11, 131, 25);
		frame.getContentPane().add(lblPhrase);

		JButton btnSearch = new JButton("Szukaj");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Operations.searchEvents(phraseText.getText());
				calendar.repaint();
				frame.dispose();
			}
		});
		btnSearch.setBounds(245, 47, 89, 23);
		frame.getContentPane().add(btnSearch);
	}
}
