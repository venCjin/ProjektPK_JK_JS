package org.organizer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteBeforeWindow {

	private JFrame frame;

	/**
	 * 
	 */
	public static void show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteBeforeWindow window = new DeleteBeforeWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 
	 */
	protected DeleteBeforeWindow() {
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 330, 110);
		frame.getContentPane().setLayout(null);

		JLabel lblUsun = new JLabel("Usuñ wydarzenia starsze ni¿");
		lblUsun.setBounds(10, 10, 167, 20);
		frame.getContentPane().add(lblUsun);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setBounds(174, 10, 130, 20);
		frame.getContentPane().add(dateChooser);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operations.deleteEventsBefore(dateChooser.getDate());
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
