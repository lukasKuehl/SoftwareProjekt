package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javafx.scene.control.RadioButton;
import model.Einsatzplanmodel;
import controller.EinsatzplanController;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * 
 * @author Ramona Gerke
 * @Info Die Klasse der View Krankmeldung l�schen. Diese beinhaltet seinen
 *       Konstruktor und die ActionPerformed Methode.
 *
 */
class KrankmeldungLoeschenView extends JFrame {

	// Initialisierung der Instanzvariablen
	private JPanel contentPane, panelKrankmeldung = null;
	private JList<String> listKrankmeldung = null;
	private JButton btnBestaetigen = null;
	private String username = null;
	private JLabel lblBitteAuswaehlen, lblKrankmeldungLoeschen = null;
	private Einsatzplanview myView = null;
	private Einsatzplanmodel myModel = null;
	private EinsatzplanController myController = null;
	private ArrayList<String> kl = null;
	private DefaultListModel<String> model = null;
	private int tblocknr = 0;
	private WindowListener windowListener;

	/**
	 * @author RamonaGerke
	 * @Info Der Konstruktor der View Krankmeldung l�schen. Dieser erstellt die
	 *       gesamten Komponenten der
	 */

	public KrankmeldungLoeschenView(Einsatzplanview myView, Einsatzplanmodel myModel,
			EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Krankmeldung l�schen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800, 600);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		windowListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				myView.update();
				dispose();
			}
		};
		addWindowListener(windowListener);

		lblKrankmeldungLoeschen = new JLabel("Krankmeldung l�schen");
		lblKrankmeldungLoeschen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblKrankmeldungLoeschen.setBounds(62, 74, 395, 26);
		contentPane.add(lblKrankmeldungLoeschen);

		// Liste der Krankmeldungen einem DefaultListModel hinzuf�gen und einer
		// JComboBox zuweisen
		listKrankmeldung = new JList<String>();
		listKrankmeldung.setFont(new Font("Verdana", Font.PLAIN, 21));
		kl = myController.getAlleTermine(myView.getUsername()); 
		model = new DefaultListModel<String>();
		for (String m : kl) {
			model.addElement(m);
		}
		listKrankmeldung.setModel(model);
		listKrankmeldung.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listKrankmeldung.setBounds(62, 143, 575, 382);
		listKrankmeldung.getModel();
		contentPane.add(listKrankmeldung);

		btnBestaetigen = new JButton("best�tigen");
		btnBestaetigen.setHorizontalAlignment(SwingConstants.LEFT);
		btnBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnBestaetigen.setBounds(647, 496, 109, 25);
		contentPane.add(btnBestaetigen);

		lblBitteAuswaehlen = new JLabel("Bitte ausw�hlen:");
		lblBitteAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblBitteAuswaehlen.setBounds(62, 121, 147, 18);
		contentPane.add(lblBitteAuswaehlen);

		setVisible(true);

		/**
		 * @author Ramona Gerke
		 * @Info Die ActionPerformed Methode wird nach dem D�cken des Buttons
		 *       "best�tigen" ausgef�hrt. Diese fragt den Nutzer, ob die Daten korrekt
		 *       sind und wandelt das ausgew�hlte Element aus der Liste f�r die �bergabe
		 *       an den Controller um.
		 * 
		 */

		// ACTION PERFROMED METHODE
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Abfrage, ob ein Element ausgew�hlt worden ist
					// weiter bei ja
					if (listKrankmeldung.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null, "Es wurde keine Eingabe get�tigt", "Error",
								JOptionPane.ERROR_MESSAGE);
						// weiter bei nein
					} else {
						// Meldung, ob die Daten wirklich gel�scht werden soll ( Ja, Nein , Abbrechen
						// Abfrage)
						int eingabe = JOptionPane.showConfirmDialog(null,
								"Wollen Sie die die Krankmeldung wirklich l�schen?", null,
								JOptionPane.YES_NO_CANCEL_OPTION);

						// weiter bei ja
						if (eingabe == JOptionPane.YES_OPTION) {
							// ausgw�hlte Element wird f�r die �bergabe an den Controller aufbereitet
							String s = listKrankmeldung.getSelectedValue().toString();
							String[] temp = s.split("-");
							temp[0] = temp[0].trim();
							int terminnr = Integer.parseInt(temp[0]);
							String wpbez = temp[1];
							String date = temp[2];
							String anfangsUhrzeit = temp[3];
							String endUhrzeit = temp[4];

							// �bergabe an den Controller
							myController.entferneTermin(tblocknr, myView.getUsername());
							// Erfolgsmeldung
							JOptionPane.showMessageDialog(null, "Krankmeldung erfolgreich gel�scht!", "",
									JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "W�hlen Sie eine andere Krankmeldug aus!", "",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null,
							"Die Liste konnte nicht �bergeben werden. - Methode ActionPerformed (btnBest�tigen, TerminLoeschen)",
							"Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
	}

}
