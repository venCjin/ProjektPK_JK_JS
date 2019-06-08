package org.organizer;

import javax.swing.JFrame;
import com.toedter.calendar.JCalendar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Organizer {

	JFrame window;

	/**
	 * Create the main application window.
	 */
	public Organizer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		window = new JFrame("Organizer");
		window.setBounds(100, 100, 450, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(null);
		window.setResizable(false);

		JCalendar calendar = new JCalendar();
		calendar.getDayChooser().setAlwaysFireDayProperty(true);

		// klikniecie w dzieñ
		calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
//				DayWindow.show(new SimpleDateFormat("dd-MM-yyyy").format(calendar.getDate()));
				DayWindow.show(Operations.parseDateToString(calendar.getDate(), "dd-MM-yyyy"));
			}
		});

		calendar.setBounds(0, 0, 434, 240);
		window.getContentPane().add(calendar);

		JMenuBar menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);

		JMenu mnWydarzenia = new JMenu("Wydarzenia");
		menuBar.add(mnWydarzenia);

		JMenuItem mntmDodaj = new JMenuItem("Dodaj");
		mntmDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddEventWindow.show();
			}
		});
		mnWydarzenia.add(mntmDodaj);

		JMenuItem mntmUsuStarszeNiz = new JMenuItem("Usuñ starsze niz...");
		mntmUsuStarszeNiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteBeforeWindow.show();
			}
		});
		mnWydarzenia.add(mntmUsuStarszeNiz);

		JMenu mnDane = new JMenu("Dane");
		menuBar.add(mnDane);

		JMenu mnZapisz = new JMenu("Zapisz");
		mnDane.add(mnZapisz);

		JMenuItem mntmDoBazySql = new JMenuItem("do bazy SQL");
		mnZapisz.add(mntmDoBazySql);

		JMenuItem mntmDoXml = new JMenuItem("do XML");
		mnZapisz.add(mntmDoXml);

		JMenuItem mntmDoCsv = new JMenuItem("do CSV");
		mnZapisz.add(mntmDoCsv);

		JMenu mnWczytaj = new JMenu("Wczytaj");
		mnDane.add(mnWczytaj);

		JMenuItem mntmZBazySql = new JMenuItem("z bazy SQL");
		mnWczytaj.add(mntmZBazySql);

		JMenuItem mntmZXml = new JMenuItem("z XML");
		mnWczytaj.add(mntmZXml);

		JMenuItem mntmZCsv = new JMenuItem("z CSV");
		mnWczytaj.add(mntmZCsv);

		JMenu mnInfo = new JMenu("Info");
		menuBar.add(mnInfo);

		JMenuItem mntmInfo = new JMenuItem("O programie");
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"ORGANIZER\nProgram stworzony na laboratoriach Programowania Komponentowego\nAutorzy: Jakub Klepacz, Jaros³aw Suchiñski",
						"Informacje o programie", 1);
			}
		});
		mnInfo.add(mntmInfo);

	}

	private static void addPopup(final Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
