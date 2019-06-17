package org.organizer;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.awt.event.ActionEvent;

/**
 * Okno usuwania wydarze� dziej�cych si� przed podan� dat�.
 */
public class DeleteBeforeWindow {

	private JFrame frame;

	/**
	 * Tworzy okno usuwania wydarze�.
	 */
	public static void show(JCalendar calendar) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteBeforeWindow window = new DeleteBeforeWindow(calendar);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private DeleteBeforeWindow(JCalendar calendar) {
		initialize(calendar);
	}

	/**
	 * Inicjalizuje zawarto�� okna usuwania wydarze�.
	 */
	private void initialize(JCalendar calendar) {
		frame = new JFrame();
		frame.setBounds(100, 100, 330, 110);
		frame.getContentPane().setLayout(null);

		JLabel lblUsun = new JLabel("Usu� wydarzenia starsze ni�");
		lblUsun.setBounds(10, 10, 167, 20);
		frame.getContentPane().add(lblUsun);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setBounds(174, 10, 130, 20);
		frame.getContentPane().add(dateChooser);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Operations.deleteEventsBefore(dateChooser.getDate());
				} catch (DateTimeException ex) {
					JOptionPane.showMessageDialog(null, "Nie uda�o si� usun�� wydarze�\nOpis b��du: " + ex.getMessage(),
							"B��d usuwania wydarze� starszych ni�...", JOptionPane.ERROR_MESSAGE);
				}
				JOptionPane.showMessageDialog(null, "Operacja zako�czona powodzeniem.",
						"Usuwanie wydarze� starszych ni�...", JOptionPane.INFORMATION_MESSAGE);
				calendar.repaint();
				frame.dispose();
			}
		});
		btnOk.setBounds(219, 37, 85, 23);
		frame.getContentPane().add(btnOk);

		JButton btnCancel = new JButton("Anuluj");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCancel.setBounds(132, 37, 85, 23);
		frame.getContentPane().add(btnCancel);
	}
}
