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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
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
	private Color searchedEventColor = Color.YELLOW;

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
					e.printStackTrace();
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
		
// TODO		
//		Operations.getDayButton(calendar, calendar.getDayChooser().getDay()).setBackground(new JButton().getBackground());
		
		calendar.getDayChooser().setAlwaysFireDayProperty(true);
		calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				DayWindow.show(Operations.parseDateToString(calendar.getDate(), "dd-MM-yyyy"), searchedEventColor);
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
				AddEventWindow.show(calendar);
			}
		});
		mnEvents.add(mntmAdd);

		JMenuItem mntmDelOlderThan = new JMenuItem("Usuñ starsze niz...");
		mntmDelOlderThan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteBeforeWindow.show(calendar);
			}
		});

		JMenuItem mntmWyszukaj = new JMenuItem("Wyszukaj");
		mntmWyszukaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchWindow.show(calendar);
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
				try {
					sql.deleteAllEventsSQL();
					sql.writeAllEventsSQL(Data.AllEvents);
					JOptionPane.showMessageDialog(new JFrame(), "Zapis do bazy danych SQL powiód³ siê.", "Zapis", JOptionPane.PLAIN_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnZapisz.add(mntmDoBazySql);

		JMenuItem mntmDoXml = new JMenuItem("do XML");
		mntmDoXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					XMLData.writeXML(Data.AllEvents);
					JOptionPane.showMessageDialog(new JFrame(), "Zapis do pliku XML powiód³ siê.", "Zapis", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "XML Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnZapisz.add(mntmDoXml);

		JMenuItem mntmDoCsv = new JMenuItem("do CSV");
		mntmDoCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CSVData.writeCSV(Data.AllEvents);
					JOptionPane.showMessageDialog(new JFrame(), "Zapis do bazy pliku CSV powiód³ siê.", "Zapis", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "CSV Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnZapisz.add(mntmDoCsv);

		JMenu mnWczytaj = new JMenu("Wczytaj");
		mnData.add(mnWczytaj);

		JMenuItem mntmZBazySql = new JMenuItem("z bazy SQL");
		mntmZBazySql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SQLData sql = new SQLData();
				try {
					Data.AllEvents = sql.readAllEventsSQL();
					System.out.println(Data.AllEvents);
					JOptionPane.showMessageDialog(new JFrame(), "Odczyt z bazy danych SQL powiód³ siê.", "Odczyt", JOptionPane.PLAIN_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
				}
//				Operations.colorSearchedEvents(calendar, searchedEventColor);
			}
		});
		mnWczytaj.add(mntmZBazySql);

		JMenuItem mntmZXml = new JMenuItem("z XML");
		mntmZXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Data.AllEvents = XMLData.readXML();
					JOptionPane.showMessageDialog(new JFrame(), "Odczyt z pliku XML powiód³ siê.", "Odczyt", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "XML Exception", JOptionPane.ERROR_MESSAGE);
				}
//				Operations.colorSearchedEvents(calendar, searchedEventColor);
			}
		});
		mnWczytaj.add(mntmZXml);

		JMenuItem mntmZCsv = new JMenuItem("z CSV");
		mntmZCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Data.AllEvents = CSVData.readCSV();
					JOptionPane.showMessageDialog(new JFrame(), "Odczyt z pliku CSV powiód³ siê.", "Odczyt", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "CSV Exception", JOptionPane.ERROR_MESSAGE);
				}
//				Operations.colorSearchedEvents(calendar, searchedEventColor);
			}
		});
		mnWczytaj.add(mntmZCsv);

		JMenu mnApperance = new JMenu("Wygl¹d");
		menuBar.add(mnApperance);

		JMenuItem mntmChangeColor = new JMenuItem("Zmien kolor");
		mntmChangeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(null, "Wybor koloru", searchedEventColor);
				if (newColor != null) {
					searchedEventColor = newColor;
					calendar.repaint();
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

		Operations.colorEventsInMonth(calendar, Data.SearchedEvents, this.searchedEventColor);
		
		// [W/S] - prze³¹czenie roku
		// [A/D] - prze³¹czenie miesi¹ca
		/*
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_A:
						if (calendar.getMonthChooser().getMonth() == 0) {
							calendar.getYearChooser().setYear(calendar.getYearChooser().getYear() - 1);
							calendar.getMonthChooser().setMonth(11);
						} else
							calendar.getMonthChooser().setMonth(calendar.getMonthChooser().getMonth() - 1);
						Operations.colorSearchedEvents(calendar, searchedEventColor);
						break;
					case KeyEvent.VK_D:
						if (calendar.getMonthChooser().getMonth() == 11) {
							calendar.getYearChooser().setYear(calendar.getYearChooser().getYear() + 1);
							calendar.getMonthChooser().setMonth(0);
						} else
							calendar.getMonthChooser().setMonth(calendar.getMonthChooser().getMonth() + 1);
						Operations.colorSearchedEvents(calendar, searchedEventColor);
						break;
					case KeyEvent.VK_W:
						calendar.getYearChooser().setYear(calendar.getYearChooser().getYear() + 1);
						break;
					case KeyEvent.VK_S:
						calendar.getYearChooser().setYear(calendar.getYearChooser().getYear() - 1);
						break;
					}
				}
				return false;
			}
		});*/
	}
}
