package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import model.Einsatzplanmodel;
import controller.EinsatzplanController;
import java.awt.event.*;
import java.util.ArrayList;


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
	private JList<String> listKrankmeldung = null;
	private JButton btnBestaetigen = null;
	private String mitarbeitername = null;
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

		windowListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				myView.update();
				dispose();
			}
		};
		addWindowListener(windowListener);

		lblKrankmeldungLoeschen = new JLabel("Krankmeldung löschen");
		lblKrankmeldungLoeschen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblKrankmeldungLoeschen.setBounds(62, 74, 395, 26);
		contentPane.add(lblKrankmeldungLoeschen);

		// Liste der Krankmeldungen einem DefaultListModel hinzufügen und einer
		// JComboBox zuweisen
		listKrankmeldung = new JList<String>();
		listKrankmeldung.setFont(new Font("Verdana", Font.PLAIN, 13));
		kl = myController.getAlleTermine(myView.getUsername()); 

		model = new DefaultListModel<String>();
		for (String m : kl) {
			model.addElement(m);
		}
		listKrankmeldung.setModel(model);
		listKrankmeldung.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listKrankmeldung.setBounds(62, 143, 659, 302);
		listKrankmeldung.getModel();
		contentPane.add(listKrankmeldung);

		btnBestaetigen = new JButton("Löschen");
		btnBestaetigen.setHorizontalAlignment(SwingConstants.LEFT);
		btnBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnBestaetigen.setBounds(668, 507, 99, 25);
		contentPane.add(btnBestaetigen);

		lblBitteAuswaehlen = new JLabel("Bitte auswählen:");
		lblBitteAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblBitteAuswaehlen.setBounds(62, 121, 147, 18);
		contentPane.add(lblBitteAuswaehlen);

		setVisible(true);

		/**
		 * @author Ramona Gerke
		 * @Info Die ActionPerformed Methode wird nach dem Dücken des Buttons
		 *       "bestätigen" ausgeführt. Diese fragt den Nutzer, ob die Daten korrekt
		 *       sind und wandelt das ausgewählte Element aus der Liste für die Übergabe
		 *       an den Controller um.
		 * 
		 */

		// ACTION PERFROMED METHODE
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Abfrage, ob ein Element ausgewählt worden ist
					// weiter bei ja
					if (listKrankmeldung.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null, "Es wurde keine Eingabe getätigt", "Error",
								JOptionPane.ERROR_MESSAGE);
						// weiter bei nein
					} else {
						// Meldung, ob die Daten wirklich gelöscht werden soll ( Ja, Nein , Abbrechen
						// Abfrage)
						int eingabe = JOptionPane.showConfirmDialog(null,
								"Wollen Sie die die Krankmeldung wirklich löschen?", null,
								JOptionPane.YES_NO_CANCEL_OPTION);

						// weiter bei ja
						if (eingabe == JOptionPane.YES_OPTION) {
							// ausgwählte Element wird für die Übergabe an den Controller aufbereitet
							String s = listKrankmeldung.getSelectedValue().toString();
							System.out.println(s);
							String[] temp = s.split("-");
							String nr = temp[0];
							nr = nr.trim();
							tblocknr = Integer.parseInt(nr);
							String grund = temp[1];
							mitarbeitername = temp[2].trim();
							String kw = temp[3];
							String tag = temp[4];
							String anfangsUhrzeit = temp[5];
							String endUhrzeit = temp[6];

						
							// Erfolgsmeldung und Übergabe an den Controller
							if (myController.entferneTermin(tblocknr,mitarbeitername)) {
							JOptionPane.showMessageDialog(null, "Krankmeldung erfolgreich gelöscht!", "",
									JOptionPane.INFORMATION_MESSAGE);
							}
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "Wählen Sie eine andere Krankmeldug aus!", "",
									JOptionPane.INFORMATION_MESSAGE);
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
