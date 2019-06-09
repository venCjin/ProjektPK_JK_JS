package org.organizer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

import java.awt.EventQueue;

import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;

import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class AddEventWindow {
	/**
	 * Klasa reprezentujaca okno JFrame dodawania Wydarzenia do kalendarza.
	 */
	private JFrame frame;
	private JTextField nameField;
	private JTextField descField;
	private JTextField placeField;

	/**
	 * Pokazuje okno do dodania nowego Wydarzenia.
	 */
	public static void show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddEventWindow window = new AddEventWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Inicjalizuje okno do dodania nowego Wydarzenia.
	 */
	protected AddEventWindow() {
		initialize();
	}

	/**
	 * Inicjalizuje zawartoœæ okna do dodania nowego Wydarzenia.
	 */
	private void initialize() {
		frame = new JFrame("Dodaj wydarzenie");
		frame.setBounds(100, 100, 450, 300);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		JLabel lblName = new JLabel("Nazwa");
		lblName.setBounds(8, 14, 101, 14);
		frame.getContentPane().add(lblName);

		nameField = new JTextField();
		lblName.setLabelFor(nameField);
		nameField.setBounds(119, 11, 305, 20);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);

		JLabel lblDesc = new JLabel("Opis");
		lblDesc.setBounds(8, 39, 101, 14);
		frame.getContentPane().add(lblDesc);

		descField = new JTextField();
		lblDesc.setLabelFor(descField);
		descField.setBounds(119, 37, 305, 62);
		frame.getContentPane().add(descField);
		descField.setColumns(10);

		JLabel lblPlace = new JLabel("Miejsce");
		lblPlace.setLabelFor(lblPlace);
		lblPlace.setBounds(8, 108, 101, 14);
		frame.getContentPane().add(lblPlace);

		placeField = new JTextField();
		placeField.setBounds(119, 105, 305, 20);
		frame.getContentPane().add(placeField);
		placeField.setColumns(10);

		JLabel lblStartDate = new JLabel("Data rozpoczêcia");
		lblStartDate.setBounds(8, 139, 101, 14);
		frame.getContentPane().add(lblStartDate);

		JDateChooser startDateChooser = new JDateChooser();
		startDateChooser.setDateFormatString("dd-MM-yyyy");
		startDateChooser.setBounds(119, 136, 158, 20);
		frame.getContentPane().add(startDateChooser);

		JSpinner startHourSpin = new JSpinner();
		startHourSpin.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		startHourSpin.setBounds(287, 136, 44, 20);
		frame.getContentPane().add(startHourSpin);

		JLabel lbl1 = new JLabel(":");
		lbl1.setBounds(334, 136, 11, 20);
		frame.getContentPane().add(lbl1);

		JSpinner startMinSpin = new JSpinner();
		startMinSpin.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		startMinSpin.setBounds(341, 136, 44, 20);
		frame.getContentPane().add(startMinSpin);

		JLabel lblEndDate = new JLabel("Data zakoñczenia");
		lblEndDate.setBounds(8, 165, 101, 14);
		frame.getContentPane().add(lblEndDate);

		JDateChooser endDateChooser = new JDateChooser();
		endDateChooser.setDateFormatString("dd-MM-yyyy");
		endDateChooser.setBounds(119, 162, 158, 20);
		frame.getContentPane().add(endDateChooser);

		JCheckBox alarmChckbx = new JCheckBox("Alarm");
		alarmChckbx.setHorizontalAlignment(SwingConstants.LEFT);
		alarmChckbx.setBounds(8, 188, 101, 23);
		frame.getContentPane().add(alarmChckbx);

		JDateChooser alarmChooser = new JDateChooser();
		alarmChooser.setDateFormatString("dd-MM-yyyy");
		alarmChooser.setBounds(119, 188, 158, 23);
		alarmChooser.setEnabled(false);
		frame.getContentPane().add(alarmChooser);

		JButton btnCancel = new JButton("Anuluj");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCancel.setBounds(265, 237, 85, 23);
		frame.getContentPane().add(btnCancel);

		JSpinner endHourSpin = new JSpinner();
		endHourSpin.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		endHourSpin.setBounds(287, 162, 44, 20);
		frame.getContentPane().add(endHourSpin);

		JLabel lbl2 = new JLabel(":");
		lbl2.setBounds(334, 162, 11, 20);
		frame.getContentPane().add(lbl2);

		JSpinner endMinSpin = new JSpinner();
		endMinSpin.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		endMinSpin.setBounds(341, 162, 44, 20);
		frame.getContentPane().add(endMinSpin);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String desc = descField.getText();
				String place = placeField.getText();

				// TODO importance
				int impotranece = 0;

				Event event = null;
				try {
					if (alarmChckbx.isSelected()) {
						event = new Event(name, desc, place,
								Operations.parseDate(startDateChooser.getDate(), (int) startHourSpin.getValue(),
										(int) startMinSpin.getValue(), 0),
								Operations.parseDate(endDateChooser.getDate(), (int) endHourSpin.getValue(),
										(int) endMinSpin.getValue(), 0),
								Operations.parseDate(alarmChooser.getDate()), impotranece);
					} else
						event = new Event(name, desc, place,
								Operations.parseDate(startDateChooser.getDate(), (int) startHourSpin.getValue(),
										(int) startMinSpin.getValue(), 0),
								Operations.parseDate(endDateChooser.getDate(), (int) endHourSpin.getValue(),
										(int) endMinSpin.getValue(), 0),
								null, impotranece);
					Operations.addEvent(event);
//					System.out.println(event.toString());
//					System.out.println(Data.AllEvents.toString());
					frame.dispose();
				} catch (EventError e1) {
					e1.printStackTrace();
				}
			}
		});
		btnOk.setBounds(356, 237, 68, 23);
		frame.getContentPane().add(btnOk);

		alarmChckbx.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (alarmChckbx.isSelected()) {
					alarmChooser.setEnabled(true);
					// godz

					// min

				} else {
					alarmChooser.setEnabled(false);
					// godz

					// min

				}
			}
		});
	}
}
