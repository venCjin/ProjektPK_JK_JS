package org.organizer;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import com.toedter.calendar.JCalendar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

/**
 * Organizer. G³ówne okno programu, wyœwietla kalendarz i menu. 
 */
public class OrganizerWindow {
	private JFrame frame;
	private Color eventDayColor = Color.CYAN;

	/**
	 * Tworzy okno programu Organizera.
	 */
	public static void show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrganizerWindow window = new OrganizerWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.getMessage();
				}
			}
		});
	}
	
	private OrganizerWindow() {
		initialize();
	}

	/**
	 * Inicjalizuje zawartoœæ okna Organizera.
	 */
	private void initialize() {
		frame = new JFrame("Organizer");
		frame.setBounds(350, 100, 438, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		JCalendar calendar = new JCalendar();
		calendar.setTodayButtonVisible(true);
		
//		Operations.getDayButton(calendar, calendar.getDayChooser().getDay()).setBackground(new JButton().getBackground());
		
		calendar.getDayChooser().setAlwaysFireDayProperty(true);
		calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				DayWindow.show(Operations.parseDateToString(calendar.getDate(), "dd-MM-yyyy"), eventDayColor);
			}
		});
		calendar.setBounds(0, 0, 432, 250);
		frame.getContentPane().add(calendar);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnEvents = new JMenu("Wydarzenia");
		menuBar.add(mnEvents);

		JMenuItem mntmAdd = new JMenuItem("Dodaj");
		mntmAdd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mntmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddEventWindow.show();
			}
		});
		mnEvents.add(mntmAdd);

		JMenuItem mntmDelOlderThan = new JMenuItem("Usuñ starsze niz...");
		mntmDelOlderThan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteBeforeWindow.show();
				Operations.colorSearchedEvents(calendar, eventDayColor);
			}
		});

		JMenuItem mntmWyszukaj = new JMenuItem("Wyszukaj");
		mntmWyszukaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchWindow.show();
				Operations.colorSearchedEvents(calendar, eventDayColor);
			}
		});
		mnEvents.add(mntmWyszukaj);
		mnEvents.add(mntmDelOlderThan);

		JMenu mnData = new JMenu("Dane");
		menuBar.add(mnData);

		JMenu mnZapisz = new JMenu("Zapisz");
		mnData.add(mnZapisz);

		JMenuItem mntmDoBazySql = new JMenuItem("do bazy SQL");
		mntmDoBazySql.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmDoBazySql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SQLData sql = new SQLData();
				sql.writeAllEventsSQL(Data.AllEvents);
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
		mnData.add(mnWczytaj);

		JMenuItem mntmZBazySql = new JMenuItem("z bazy SQL");
		mntmZBazySql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO odczyt z SQL (zapis do AllEvents)
				SQLData sql = new SQLData();
				Data.AllEvents = sql.readAllEventsSQL();
//				Operations.colorSearchedEvents(calendar, eventDayColor);
			}
		});
		mnWczytaj.add(mntmZBazySql);

		JMenuItem mntmZXml = new JMenuItem("z XML");
		mntmZXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO odczyt z XML (zapis do AllEvents oraz Searched)
				
				Operations.colorSearchedEvents(calendar, eventDayColor);
			}
		});
		mnWczytaj.add(mntmZXml);

		JMenuItem mntmZCsv = new JMenuItem("z CSV");
		mntmZCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO odczyt z CSV (zapis do AllEvents oraz Searched)
				
				Operations.colorSearchedEvents(calendar, eventDayColor);
			}
		});
		mnWczytaj.add(mntmZCsv);

		JMenu mnApperance = new JMenu("Wygl¹d");
		menuBar.add(mnApperance);

		JMenuItem mntmChangeColor = new JMenuItem("Zmien kolor");
		mntmChangeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(null, "Wybor koloru", eventDayColor);
				if (newColor != null) {
					eventDayColor = newColor;
					Operations.colorSearchedEvents(calendar, eventDayColor);
				}
			}
		});
		mnApperance.add(mntmChangeColor);

		JMenu mnInfo = new JMenu("Info");
		menuBar.add(mnInfo);

		JMenuItem mntmInfo = new JMenuItem("O programie");
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						Operations.info(),
						"Informacje o programie", 1);
			}
		});
		mnInfo.add(mntmInfo);

		// KEYBOARD
		// TODO chyba do wyrzucenia bo jak kliknie siê dzieñ to lewo prawo zmiania równie¿ dni
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						if (calendar.getMonthChooser().getMonth() == 0) {
							calendar.getYearChooser().setYear(calendar.getYearChooser().getYear() - 1);
							calendar.getMonthChooser().setMonth(11);
						} else
							calendar.getMonthChooser().setMonth(calendar.getMonthChooser().getMonth() - 1);
						Operations.colorSearchedEvents(calendar, eventDayColor);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						if (calendar.getMonthChooser().getMonth() == 11) {
							calendar.getYearChooser().setYear(calendar.getYearChooser().getYear() + 1);
							calendar.getMonthChooser().setMonth(0);
						} else
							calendar.getMonthChooser().setMonth(calendar.getMonthChooser().getMonth() + 1);
						Operations.colorSearchedEvents(calendar, eventDayColor);
					}
				}
				return false;
			}
		});
	}
}
