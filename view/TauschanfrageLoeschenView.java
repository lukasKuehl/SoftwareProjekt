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

//Klassenbeschreibung fehlt!

//Kommentare innerhalb der Methoden fehlen!

//Hilfsklassen sind nicht public!
public class TauschanfrageLoeschenView extends JFrame {

	private JPanel contentPane, panelTauschanfrage = null;
	private JLabel lblTauschanfrageLoeschen = null;
	
	//Siehe vorherige Klassen!
	private JList<Object> listTauschanfragen = null;
	
	private JButton btnBestaetigen = null;
	private ArrayList<String> tl, al = null;
	private DefaultListModel<Object> modelTauschanfrage = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private int tauschanfrageNr=0;

	protected TauschanfrageLoeschenView(Einsatzplanview myView, Einsatzplanmodel myModel, EinsatzplanController myController) {
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
		listTauschanfragen.setBounds(95, 126, 362, 399);
		panelTauschanfrage.add(listTauschanfragen);

		btnBestaetigen = new JButton("Bestätigen");
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Wird später gefüllt --> Dieses Kommando kann weg!
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
						
						//Ein String Array mit einer Länge von 14 geht von 0 bis 13 --> IndexOutOfBoundsException --> entweder das Array länger machen oder nochmal die Werte prüfen
						String temp[] = new String[14];
						al = myView.getTauschanfragen(myView.getUsername());
						for (String m : al) {
							m.toString();
							m.trim();
							temp = m.split("-");
							String  tauschanfrageNrS = temp [0].substring(10);
							tauschanfrageNr =Integer.parseInt(tauschanfrageNrS);
							String senderVorname = temp[1];
							String senderName = temp[2];
							String senderSchichtNr = temp[3];
							String senderWpNr = temp[4];
							String senderTbez = temp[5];
							String senderAnfangsuhrzeit = temp[6];
							String senderEnduhrzeit = temp[7];
							String empfaengerVorname = temp[8];
							String empfaengerName = temp[9];
							String empfaengerSchichtNr = temp[10];
							String empfaengerWpBez = temp[11];
							String empfaengerTagBez = temp[12];
							String empfaengerAnfangsuhrzeit = temp[13];
							String emfaengerEnduhrzeit = temp[14];
						}
					
						myController.entferneTauschanfrage(tauschanfrageNr);
					
						//Siehe vorherige Klassen!
						System.exit(0);
					
					}
				} catch (Exception ex) {
					ex.getStackTrace();
					System.err.println(
							"Fehler in der Eingabe der Daten - ActionPerformed Button Bestätigen und Klasse TauschanfrageLoeschen");
				}
			}
		} else {		
			//Wenn abbruch = Keine Tauschanfragen sollen gelöscht werden, dann passt das, ansonsten kann der else Teil komplett weg.
			
			//Siehe vorherige Klassen!
			System.exit(0);
		}

		setVisible(true);
	};
}
