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
 * @Info Die Klasse der View Tauschanfrage l�schen. Diese beinhaltet seinen
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
		setTitle("Tauschanfrage l�schen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 1000, 700);

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
		lblTauschanfrageLoeschen.setBounds(62, 89, 284, 26);
		panelTauschanfrage.add(lblTauschanfrageLoeschen);

		listTauschanfragen = new JList<String>();

		// Ausgeben einer ArrayList f�r die Tauschanfragen anhand des Usernamens
		tl = myController.getTauschanfragen(myView.getUsername());
		modelTauschanfrage = new DefaultListModel<String>();
		for (String m : tl) {
			modelTauschanfrage.addElement(m);
	
		}
		listTauschanfragen.setModel(modelTauschanfrage);
		listTauschanfragen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTauschanfragen.setBounds(62, 161, 860, 333);
		panelTauschanfrage.add(listTauschanfragen);

		btnBestaetigen = new JButton("Best�tigen");
		btnBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnBestaetigen.setBounds(802, 531, 120, 25);
		panelTauschanfrage.add(btnBestaetigen);
		
		JLabel label = new JLabel("Bitte ausw\u00E4hlen:");
		label.setFont(new Font("Verdana", Font.PLAIN, 14));
		label.setBounds(62, 127, 147, 18);
		panelTauschanfrage.add(label);

		setVisible(true);

		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methode die nach dem best�tigen des Buttons
		 *       "best�tigen", dass das ausgew�hlte Element anhand der Tauschanfrage Nr
		 *       gel�scht.
		 */
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Abfrage, ob ein Element ausgew�hlt worden ist
					if (listTauschanfragen.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null,
								"Es wurde keine Tauschanfrage ausgew�hlt. Bitte w�hlen Sie eine Tauschanfrage aus.",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						// Meldung, ob die Daten wirklich gel�scht werden soll ( Ja, Nein , Abbrechen
						// Abfrage)
						int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Auswahl best�tigen?", null,
								JOptionPane.YES_NO_CANCEL_OPTION);
						// weiter bei ja
						if (eingabe == JOptionPane.YES_OPTION) {
							String s = listTauschanfragen.getSelectedValue().toString();
							String[] temp = s.split("-");
							 temp[0] = temp[0].substring(10); 
							 String tauschanfrageNrS = temp[0].trim();
							int tauschanfrageNr = Integer.parseInt(tauschanfrageNrS);	
							
							// �bergabe an den Controller
							myController.entferneTauschanfrage(tauschanfrageNr);
							JOptionPane.showConfirmDialog(null, "Tauschanfrage erfolgreich gel�scht", "",
									JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else {
							JOptionPane.showConfirmDialog(null, "W�hlen Sie eine andere Tauschanfrage", "",
									JOptionPane.INFORMATION_MESSAGE);

						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,
							"Fehler in der Eingabe der Daten - ActionPerformed Button Best�tigen und Klasse TauschanfrageLoeschen");
				}

			}
		});

		setVisible(true);
	}
}
