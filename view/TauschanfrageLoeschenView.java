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

import javax.swing.border.EmptyBorder;

/**
 * 
 * @author Ramona Gerke
 * @Info Die Klasse der View Tauschanfrage löschen. Diese beinhaltet seinen
 *       Konstruktor und die ActionPerformed Methode.
 *
 */
class TauschanfrageLoeschenView extends JFrame {
	// Initialisierung der Instanzvariablen
	private JPanel contentPane, panelTauschanfrage = null;
	private JLabel lblTauschanfrageLoeschen = null;
	private JList<String> listTauschanfragen = null;
	private JButton btnBestaetigen = null;
	private ArrayList<String> tl, al = null;
	private DefaultListModel<String> modelTauschanfrage = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private int tauschanfrageNr = 0;

	/**
	 * @ author Ramona Gerke
	 * 
	 * @Info Der Konstruktor der View Tauschanfrage erstellen.
	 */
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

		listTauschanfragen = new JList<String>();

		// Ausgeben einer ArrayList für die Tauschanfragen anhand des Usernamens
		tl = myController.getTauschanfragen(myView.getUsername());
		modelTauschanfrage = new DefaultListModel<String>();
		for (String m : tl) {
			modelTauschanfrage.addElement(m);
		}
		listTauschanfragen.setModel(modelTauschanfrage);
		listTauschanfragen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTauschanfragen.setBounds(95, 126, 362, 399);
		panelTauschanfrage.add(listTauschanfragen);

		btnBestaetigen = new JButton("Bestätigen");
		btnBestaetigen.setBounds(519, 483, 141, 35);
		panelTauschanfrage.add(btnBestaetigen);

		setVisible(true);

		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methode die nach dem bestätigen des Buttons
		 *       "bestätigen", dass das ausgewählte Element anhand der Tauschanfrage Nr
		 *       gelöscht.
		 */
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Abfrage, ob ein Element ausgewählt worden ist
					if (listTauschanfragen.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null,
								"Es wurde keine Tauschanfrage ausgewählt. Bitte wählen Sie eine Tauschanfrage aus.",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						// Meldung, ob die Daten wirklich gelöscht werden soll ( Ja, Nein , Abbrechen
						// Abfrage)
						int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Auswahl bestätigen?", null,
								JOptionPane.YES_NO_CANCEL_OPTION);
						// weiter bei ja
						if (eingabe == JOptionPane.YES_OPTION) {
							String s = listTauschanfragen.getSelectedValue().toString().trim();
							String[] temp = s.split("-");
							String tauschanfrageNrS = temp[0].substring(10); // substring länge bestimmen
							int tauschanfrageNr = Integer.parseInt(tauschanfrageNrS);
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
							// Übergabe an den Controller
							myController.entferneTauschanfrage(tauschanfrageNr);
							JOptionPane.showConfirmDialog(null, "Tauschanfrage erfolgreich gelöscht", "",
									JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else {
							JOptionPane.showConfirmDialog(null, "Wählen Sie eine andere Tauschanfrage", "",
									JOptionPane.INFORMATION_MESSAGE);

						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,
							"Fehler in der Eingabe der Daten - ActionPerformed Button Bestätigen und Klasse TauschanfrageLoeschen");
				}

			}
		});

		setVisible(true);
	}
}
