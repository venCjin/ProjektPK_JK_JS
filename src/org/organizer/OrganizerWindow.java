package org.organizer;

import javax.swing.JFrame;
import com.toedter.calendar.JCalendar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;

public class OrganizerWindow {

	JFrame window;

	/**
	 * Create the main application window.
	 */
	public OrganizerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JCalendar calendar = new JCalendar();
		window = new JFrame("Organizer");
		window.setBounds(100, 100, 450, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(null);
		window.setResizable(false);

		calendar.getDayChooser().setAlwaysFireDayProperty(true);
		// klikniecie w dzieñ
		calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
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
		mntmDodaj.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
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
		mntmDoBazySql.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmDoBazySql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO zapis do SQL
			}
		});
		mnZapisz.add(mntmDoBazySql);

		JMenuItem mntmDoXml = new JMenuItem("do XML");
		mntmDoXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO zapis do XML
			}
		});
		mnZapisz.add(mntmDoXml);

		JMenuItem mntmDoCsv = new JMenuItem("do CSV");
		mntmDoCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO zapis do CSV
			}
		});
		mnZapisz.add(mntmDoCsv);

		JMenu mnWczytaj = new JMenu("Wczytaj");
		mnDane.add(mnWczytaj);

		JMenuItem mntmZBazySql = new JMenuItem("z bazy SQL");
		mntmZBazySql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO odczyt z SQL
			}
		});
		mnWczytaj.add(mntmZBazySql);

		JMenuItem mntmZXml = new JMenuItem("z XML");
		mntmZXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO odczyt z XML
			}
		});
		mnWczytaj.add(mntmZXml);

		JMenuItem mntmZCsv = new JMenuItem("z CSV");
		mntmZCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO odczyt z CSV
			}
		});
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

		// KEYBOARD
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					if (e.getID() == KeyEvent.KEY_PRESSED) {
						if (e.getKeyCode() == KeyEvent.VK_LEFT) {
							if (calendar.getMonthChooser().getMonth() == 0) {
								calendar.getYearChooser().setYear(calendar.getYearChooser().getYear() - 1);
								calendar.getMonthChooser().setMonth(11);
							} else
								calendar.getMonthChooser().setMonth(calendar.getMonthChooser().getMonth() - 1);
						} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
							if (calendar.getMonthChooser().getMonth() == 11) {
								calendar.getYearChooser().setYear(calendar.getYearChooser().getYear() + 1);
								calendar.getMonthChooser().setMonth(0);
							} else
								calendar.getMonthChooser().setMonth(calendar.getMonthChooser().getMonth() + 1);
						}
					}
				}
				return false;
			}
		});
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
