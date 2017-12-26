package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javafx.scene.control.RadioButton;
import model.Einsatzplanmodel;

import java.awt.Color;
import controller.EinsatzplanController;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class KrankmeldungLoeschenView extends JFrame {

	// Initialisierung der Instanzvariablen
	private JPanel contentPane, panelKrankmeldung = null;
	private JLabel lblKrankmeldungLoeschen = null;
	private JList<Object> listKrankmeldung = null;
	private JButton btnBestaetigen = null;
	private String username = null;
	private JLabel lblBitteAuswaehlen = null;
	private Einsatzplanview myView = null;
	private Einsatzplanmodel myModel = null;
	private EinsatzplanController myController = null;
	private ArrayList<String> kl = null;
	private DefaultListModel<Object> model = null;

	/**
	 * @author RamonaGerke
	 * @Info Konstruktor der View Krankmeldung löschen.
	 */

	protected KrankmeldungLoeschenView(Einsatzplanview myView, Einsatzplanmodel myModel,
			EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Krankmeldung löschen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800, 600);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblKrankmeldungLoeschen = new JLabel("Krankmeldung löschen");
		lblKrankmeldungLoeschen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblKrankmeldungLoeschen.setBounds(95, 74, 362, 26);
		contentPane.add(lblKrankmeldungLoeschen);

		/**
		 * @author Ramona Gerke
		 * @info Liste der Krankmeldungen wird einem DefaultListModel hinzugefügt und
		 *       dann in einer ComboBox ausgegeben.
		 */

		listKrankmeldung = new JList<Object>();
		// String grund = "Krankheit";
		// kl = myController.getAlleTermin(myView.getUsername()); // bei TerminStrg noch
		// die TBez ändern
		// if ( tb.getBbez() == "Krankheit"){

		model = new DefaultListModel<Object>();
		for (String m : kl) {
			model.addElement(m);
		}
		listKrankmeldung.setModel(model);
		listKrankmeldung.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listKrankmeldung.setBounds(95, 126, 362, 399);
		listKrankmeldung.getModel();
		contentPane.add(listKrankmeldung);

		btnBestaetigen = new JButton("best\u00E4tigen");
		btnBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnBestaetigen.setBounds(500, 500, 110, 25);
		contentPane.add(btnBestaetigen);

		lblBitteAuswaehlen = new JLabel("Bitte auswählen:");
		lblBitteAuswaehlen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBitteAuswaehlen.setBounds(95, 128, 113, 14);
		contentPane.add(lblBitteAuswaehlen);

		/**
		 * @author Ramona Gerke
		 * @Info Die ActionPerformed Methode wird nach dem drücken des Buttons
		 *       "bestätigen" ausgeführt. Die liest eine ArrayList aus und wandelt die
		 *       benötigten Werte für die Methode myController.entferneTermin um.
		 */

		// ACTION PERFROMED METHODE
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == btnBestaetigen) {
					int eingabe = JOptionPane.showConfirmDialog(null,
							"Wollen Sie die die Krankmeldung wirklich löschen?", null,
							JOptionPane.YES_NO_CANCEL_OPTION);
					if (eingabe == 0) {
						if (listKrankmeldung.isSelectionEmpty()) {
							JOptionPane.showMessageDialog(null, "Es wurde keine Eingabe getätigt", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							try {
								int tblocknr = 0;
								String temp[] = new String[5];
								kl = myController.getAlleTermine(myView.getUsername());
								for (String m : kl) {
									m.toString();
									m.trim();
									temp = m.split("-");
									String grund = temp[0];
									String wpBez = temp[1];
									tblocknr = Integer.parseInt(wpBez.substring(2));
									String anfangstag = temp[2];
									String endtag = temp[3];
									String nfangsuhrzeit = temp[4];
									String enduhrzeit = temp[5];

									myController.entferneTermin(tblocknr, grund);
									System.exit(0);
								}
							} catch (Exception a) {
								JOptionPane.showMessageDialog(null,
										"Die Liste konnte nicht übergeben werden. - Methode ActionPerformed (btnBestätigen, TerminLoeschen)",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
						}
					} else {
						System.exit(0);
					}
				}
			}
		});

		setVisible(true);
	}
}
