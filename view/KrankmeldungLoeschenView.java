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

/**
 * 
 * @author Ramona Gerke
 * @Info Die Klasse der View Krankmeldung löschen. Diese beinhaltet seinen
 *       Konstruktor und die ActionPerformed Methode.
 *
 */
class KrankmeldungLoeschenView extends JFrame {

	// Initialisierung der Instanzvariablen
	private JPanel contentPane, panelKrankmeldung = null;
	private JLabel lblKrankmeldungLoeschen = null;
	private JList<String> listKrankmeldung = null;
	private JButton btnBestaetigen = null;
	private String username = null;
	private JLabel lblBitteAuswaehlen = null;
	private Einsatzplanview myView = null;
	private Einsatzplanmodel myModel = null;
	private EinsatzplanController myController = null;
	private ArrayList<String> kl = null;
	private DefaultListModel<String> model = null;
	private int tblocknr = 0;

	/**
	 * @author RamonaGerke
	 * @Info Der Konstruktor der View Krankmeldung löschen. Dieser erstellt die
	 *       gesamten Komponenten der
	 */

	public KrankmeldungLoeschenView(Einsatzplanview myView, Einsatzplanmodel myModel,
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

		// Liste der Krankmeldungen wird einem DefaultListModel hinzugefügt und dann in
		// einer ComboBox ausgegeben.
		listKrankmeldung = new JList<String>();
		kl = myController.getAlleTermine(myView.getUsername());
		model = new DefaultListModel<String>();
		for (String m : kl) {
			model.addElement(m);
		}
		listKrankmeldung.setModel(model);
		listKrankmeldung.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listKrankmeldung.setBounds(95, 126, 362, 399);
		listKrankmeldung.getModel();
		contentPane.add(listKrankmeldung);

		btnBestaetigen = new JButton("bestätigen");
		btnBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnBestaetigen.setBounds(500, 500, 121, 25);
		contentPane.add(btnBestaetigen);

		lblBitteAuswaehlen = new JLabel("Bitte auswählen:");
		lblBitteAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblBitteAuswaehlen.setBounds(95, 128, 113, 14);
		contentPane.add(lblBitteAuswaehlen);

		setVisible(true);

		/**
		 * @author Ramona Gerke
		 * @Info Die ActionPerformed Methode wird nach dem drücken des Buttons
		 *       "bestätigen" ausgeführt. Diese fragt den Nutzer, ob die Daten korrekt
		 *       sind und liest eine ArrayList aus und wandelt die benötigten Werte für
		 *       die Methode myController.entferneTermin um.
		 */

		// ACTION PERFROMED METHODE
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Abfrage, ob ein Element ausgewäjlt worden ist
					if (listKrankmeldung.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null, "Es wurde keine Eingabe getätigt", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Meldung, ob die Daten wirklich gelöscht werden soll ( Ja, Nein , Abbrechen
						// Abfrage)
						int eingabe = JOptionPane.showConfirmDialog(null,
								"Wollen Sie die die Krankmeldung wirklich löschen?", null,
								JOptionPane.YES_NO_CANCEL_OPTION);

						// weiter bei ja
						if (eingabe == JOptionPane.YES_OPTION) {

							String temp[] = new String[6];
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
								String anfangsuhrzeit = temp[4];
								String enduhrzeit = temp[5];

								// Übergabe an den Controller
								myController.entferneTermin(tblocknr, grund);

								dispose();
							}
						} else {
							dispose();
						}
					}
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null,
							"Die Liste konnte nicht übergeben werden. - Methode ActionPerformed (btnBestätigen, TerminLoeschen)",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

}
