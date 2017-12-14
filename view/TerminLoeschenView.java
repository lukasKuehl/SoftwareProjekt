package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class TerminLoeschenView extends JFrame {

	private JPanel contentPane, panelTermin = null;
	private JLabel lblTerninLoeschen, lblTerminAuswaehlen = null;
	private JList list = null;
	private String username = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;
	private JButton btnBesttigen = null;

	protected TerminLoeschenView(Einsatzplanmodel myModel, EinsatzplanController myController) {
		this.myModel = myModel;
		this.myController = myController;
		setTitle("Termin löschen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800, 600);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblTerninLoeschen = new JLabel("Termin löschen");
		lblTerninLoeschen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTerninLoeschen.setBounds(60, 83, 199, 26);
		contentPane.add(lblTerninLoeschen);

		list = new JList(myController.getAlleTermine(username).toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(65, 145, 282, 355);
		list.getModel();
		contentPane.add(list);

		lblTerminAuswaehlen = new JLabel("Termin auswählen");
		lblTerminAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblTerminAuswaehlen.setBounds(62, 120, 152, 14);
		contentPane.add(lblTerminAuswaehlen);
		
		btnBesttigen = new JButton("bestätigen");
		btnBesttigen.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null,
						JOptionPane.YES_NO_CANCEL_OPTION);

				if (eingabe == 0) {

					if (list.isSelectionEmpty()) {

						JOptionPane.showMessageDialog(null, "Es wurde keine Eingabe getätigt", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							String s;

							// Terminnr muss aus der Array List gezogen werden und Nummer als terminnr
							// hinterlgen
							// vielleicht ist eine Map besser ? wegen der Nr zuweisung und dem primitiven
							// Datentyp

							list.getSelectedIndex();
							s = list.toString();
							int terminnr = Integer.parseInt(s);
							myView.entferneTermin(terminnr, username);
							System.exit(0);
						} catch (Exception a) {
							JOptionPane.showMessageDialog(null,
									"Die Liste konnte nicht übergeben werden. - Methode ActionPerformed (btnBestätigen)");
						}
					}
				} else {
					System.exit(0);
				}
			}
		});
		btnBesttigen.setBounds(437, 465, 119, 26);
		contentPane.add(btnBesttigen);

		setVisible(true);
	}

	// Aktuelle testmethode für die Anzeige der Termine
	private void MaListeAnzeigen() {
		String s[] = { "Termin 1", "Termin 2" }; // Test s

	}

}
