package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import model.Einsatzplanmodel;
import controller.EinsatzplanController;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;

public class TauschanfrageLoeschenView extends JFrame {

	private JPanel contentPane, panelTauschanfrage = null;
	private JLabel lblTauschanfrageLoeschen = null;
	private JList<Object> listTauschanfragen = null;
	private JButton btnBestaetigen = null;
	private ArrayList<String> tl, al = null;
	private DefaultListModel<Object> modelTauschanfrage = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;

	protected TauschanfrageLoeschenView(Einsatzplanview myView, Einsatzplanmodel myModel,
			EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Tauschanfrage löschen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800, 600);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panelTauschanfrage = new JPanel();
		panelTauschanfrage.setBackground(Color.WHITE);
		panelTauschanfrage.setBounds(29, 30, 1188, 728);
		panelTauschanfrage.setBorder(UIManager.getBorder("Button.border"));
		contentPane.add(panelTauschanfrage);
		panelTauschanfrage.setLayout(null);

		lblTauschanfrageLoeschen = new JLabel("Tauschanfrage loeschen:");
		lblTauschanfrageLoeschen.setFont(new Font("Verdana", Font.BOLD, 20));
		lblTauschanfrageLoeschen.setBounds(70, 89, 284, 26);
		panelTauschanfrage.add(lblTauschanfrageLoeschen);

		listTauschanfragen = new JList<Object>();
		tl = myController.getTauschanfragen(myView.getUsername());
		modelTauschanfrage = new DefaultListModel<Object>();
		for (String m : tl) {
			modelTauschanfrage.addElement(m);
		}
		listTauschanfragen.setModel(modelTauschanfrage);
		listTauschanfragen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTauschanfragen.setBounds(71, 126, 340, 392);
		panelTauschanfrage.add(listTauschanfragen);

		btnBestaetigen = new JButton("Bestätigen");
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBestaetigen.setBounds(519, 483, 141, 35);
		panelTauschanfrage.add(btnBestaetigen);

		setVisible(true);

	}

	/**
	 * @author Ramona Gerke
	 * @Info Action Perfomed Methode für den Button "bestätigen". Nach der
	 *       Ausführung des Button wird die ArralyList al von der Methode
	 *       getTaususchanfragen aus dem Controller aufgerufen und die wichtigen
	 *       Methoden werden dann n einen String gewandelt und in ein Array
	 *       gespeichert
	 * @Info Die gespeicherten Informationen werden dann für die Methode
	 *       entferneTauschanfrage weitergeben, somit diese Methode die
	 *       Tasuchanfrage aus der Datenbank löschen kann.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnBestaetigen) {

			int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Auswahl bestätigen?", null,
					JOptionPane.YES_NO_CANCEL_OPTION);

			if (eingabe == 0) {
				try {
					if (listTauschanfragen.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null,
								"Es wurde keine Tauschanfrage ausgewählt. Bitte wählen Sie eine Tauschanfrage aus.",
								"Error", JOptionPane.ERROR_MESSAGE);

					} else {
						String temp[] = new String[14];
						al = myView.getTauschanfragen(myView.getUsername());
						for (String m : al) {
							m.toString();
							m.trim();
							temp = m.split("-");
							// tauschanfrageNrS = temp [0];
							String senderVorname = temp[0];
							String senderName = temp[1];
							String senderSchichtnr = temp[2];
							String senderWpNr = temp[3];
							String senderTbez = temp[4];
							String senderAnfangsuhrzeit = temp[5];
							String senderEnduhrzeit = temp[6];
							String empfaengerVorname = temp[7];
							String empfaengerName = temp[8];
							String empfaengerSchichtNr = temp[9];
							String empfaengerWpBez = temp[10];
							String empfaengerTagBez = temp[11];
							String empfaengerAnfangsuhrzeit = temp[12];
							String emfaengerEnduhrzeit = temp[13];
						}
						// Integer.parseInt(tauschanfrageNrS);
						int tauschanfrageNr = 0;
						myController.entferneTauschanfrage(tauschanfrageNr);
						System.exit(0);
					}
				} catch (Exception ex) {
					ex.getStackTrace();
					System.err.println(
							"Fehler in der Eingabe der Daten - ActionPerformed Button Bestätigen und Klasse TauschanfrageLoeschen");
				}
			}
		} else {
			System.exit(0);
		}

		setVisible(true);
	};
}
