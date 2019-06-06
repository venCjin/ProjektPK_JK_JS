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
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class AddEventWindow {

	private JFrame frame;
	private JTextField nazwaField;
	private JTextField opisField;
	private JTextField miejsceField;

	/**
	 * Launch the window to add new event.
	 */
	public void show() {
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
	 * Create the application.
	 */
	protected AddEventWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Dodaj wydarzenie");
		frame.setBounds(100, 100, 450, 300);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNazwa = new JLabel("Nazwa");
		lblNazwa.setBounds(8, 14, 101, 14);
		frame.getContentPane().add(lblNazwa);
		
		nazwaField = new JTextField();
		nazwaField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		lblNazwa.setLabelFor(nazwaField);
		nazwaField.setBounds(119, 11, 305, 20);
		frame.getContentPane().add(nazwaField);
		nazwaField.setColumns(10);
		
		JLabel lblOpis = new JLabel("Opis");
		lblOpis.setBounds(8, 39, 101, 14);
		frame.getContentPane().add(lblOpis);
		
		opisField = new JTextField();
		lblOpis.setLabelFor(opisField);
		opisField.setBounds(119, 37, 305, 62);
		frame.getContentPane().add(opisField);
		opisField.setColumns(10);
		
		JLabel lblMiejsce = new JLabel("Miejsce");
		lblMiejsce.setLabelFor(lblMiejsce);
		lblMiejsce.setBounds(8, 108, 101, 14);
		frame.getContentPane().add(lblMiejsce);
		
		miejsceField = new JTextField();
		miejsceField.setBounds(119, 105, 305, 20);
		frame.getContentPane().add(miejsceField);
		miejsceField.setColumns(10);
		
		JLabel lblDataRozpoczcia = new JLabel("Data rozpoczêcia");
		lblDataRozpoczcia.setBounds(8, 136, 101, 14);
		frame.getContentPane().add(lblDataRozpoczcia);
		
		JDateChooser dataRozpChooser = new JDateChooser();
		dataRozpChooser.setBounds(119, 136, 158, 20);
		frame.getContentPane().add(dataRozpChooser);
		
		JSpinner godzRozpSpin = new JSpinner();
		godzRozpSpin.setBounds(287, 136, 44, 20);
		frame.getContentPane().add(godzRozpSpin);
		
		JLabel lbl1 = new JLabel(":");
		lbl1.setBounds(334, 136, 11, 20);
		frame.getContentPane().add(lbl1);
		
		JSpinner minRozpSpin = new JSpinner();
		minRozpSpin.setBounds(341, 136, 44, 20);
		frame.getContentPane().add(minRozpSpin);
		
		JLabel lblDataZakoczenia = new JLabel("Data zakoñczenia");
		lblDataZakoczenia.setBounds(8, 162, 101, 14);
		frame.getContentPane().add(lblDataZakoczenia);
		
		JDateChooser dataZakChooser = new JDateChooser();
		dataZakChooser.setBounds(119, 162, 158, 20);
		frame.getContentPane().add(dataZakChooser);
		
		JCheckBox chckbxAlarm = new JCheckBox("Alarm");
		chckbxAlarm.setBounds(8, 185, 101, 23);
		frame.getContentPane().add(chckbxAlarm);
		
		JDateChooser alarmChooser = new JDateChooser();
		alarmChooser.setBounds(119, 188, 158, 23);
		frame.getContentPane().add(alarmChooser);
		
		JButton btnAnuluj = new JButton("Anuluj");
		btnAnuluj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		JSpinner godzZakSpin = new JSpinner();
		godzZakSpin.setBounds(287, 162, 44, 20);
		frame.getContentPane().add(godzZakSpin);
		
		JLabel lbl2 = new JLabel(":");
		lbl2.setBounds(334, 162, 11, 20);
		frame.getContentPane().add(lbl2);
		
		JSpinner minZakSpin = new JSpinner();
		minZakSpin.setBounds(341, 162, 44, 20);
		frame.getContentPane().add(minZakSpin);
		btnAnuluj.setBounds(265, 237, 85, 23);
		frame.getContentPane().add(btnAnuluj);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				//TODO dodanie Eventu
				String name = nazwaField.getText();
				String desc = opisField.getText();
				String place = miejsceField.getText();
				
				Date dataRozp = dataRozpChooser.getDate();
				dataRozp.setHours((int) godzRozpSpin.getValue());
				dataRozp.setMinutes((int) minRozpSpin.getValue());
//				int godzRozp = (int) godzRozpSpin.getValue();
//				int minRozp = (int) minRozpSpin.getValue();
				
				Date dataZak = dataZakChooser.getDate();
				dataZak.setHours((int) godzZakSpin.getValue());
				dataZak.setMinutes((int) minZakSpin.getValue());
//				int godzZak = (int) godzZakSpin.getValue();
//				int minZak = (int) minZakSpin.getValue();

				
			}
		});
		btnOk.setBounds(356, 237, 68, 23);
		frame.getContentPane().add(btnOk);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		chckbxAlarm.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(chckbxAlarm.isSelected()) {
					alarmChooser.setEnabled(true);
					//godz
					
					//min
					
				}
				else {
					alarmChooser.setEnabled(false);
					//godz
					
					//min
					
				}
			}
		});
	}
}

