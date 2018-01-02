package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.EinsatzplanController;
import data.Mitarbeiter;
import model.Einsatzplanmodel;

/**
 * 
 * @author Ramona Gerke
 * @Info Die Klasse der View Krankmeldung löschen. Diese beinhaltet seinen
 *       Konstruktor und die ActionPerformed Methode.
 *
 */
class KrankmeldungErstellenView extends JFrame {

	// Initialisierung der Instanzvariablen
	private JPanel contentPane, panelKrankmeldung, panelTermin = null;
	private JFormattedTextField txtBisTermin, txtVonTermin = null;
	private JComboBox<String> comboBoxMA, comboBoxWochenplaene, comboBoxAnfang, comboBoxEnd = null;
	private JLabel labelKrankmeldungErstellen, lblMitarbeiterAuswaehlen, lblWochenplan, lblNotizEintragen, lblVon,
			lblBis = null;
	private JTextField txtGrund = null;
	private JButton buttonBestaetigen = null;;
	private String username = null;
	private Einsatzplanview myView = null;
	private Einsatzplanmodel myModel = null;
	private EinsatzplanController myController = null;
	private TreeMap<String, String> zeitraum = null;
	private ArrayList<String> wp = null;
	private LinkedList<Mitarbeiter> ma = null;

	/**
	 * 
	 * @author Ramona Gerke
	 * @Info Ein Kronstruktor der die View der Krankmeldung erstellen erstellt.
	 *
	 */
	public KrankmeldungErstellenView(Einsatzplanview myView, Einsatzplanmodel myModel,
			EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Krankmeldung erstellen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800, 600);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panelKrankmeldung = new JPanel();
		panelKrankmeldung.setBounds(29, 30, 1188, 728);
		panelKrankmeldung.setBackground(Color.WHITE);
		panelKrankmeldung.setBorder(UIManager.getBorder("Button.border"));
		contentPane.add(panelKrankmeldung);
		panelKrankmeldung.setLayout(null);

		labelKrankmeldungErstellen = new JLabel("Krankmeldung erstellen");
		labelKrankmeldungErstellen.setFont(new Font("Verdana", Font.BOLD, 21));
		labelKrankmeldungErstellen.setBounds(61, 58, 302, 28);
		panelKrankmeldung.add(labelKrankmeldungErstellen);

		comboBoxWochenplaene = new JComboBox<String>();
		// Ausgeben einer ArrayListe für die gesamten Wochenpläne
		wp = myController.getWochenplaene();
		for (String m : wp) {
			comboBoxWochenplaene.addItem(m);
		}
		comboBoxWochenplaene.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxWochenplaene.setBounds(162, 118, 110, 20);
		panelKrankmeldung.add(comboBoxWochenplaene);

		lblWochenplan = new JLabel("Wochenplan");
		lblWochenplan.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblWochenplan.setBounds(60, 118, 113, 20);
		panelKrankmeldung.add(lblWochenplan);

		comboBoxAnfang = new JComboBox<String>();
		comboBoxAnfang.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag" }));
		comboBoxAnfang.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxAnfang.setBounds(162, 146, 110, 20);
		panelKrankmeldung.add(comboBoxAnfang);

		comboBoxEnd = new JComboBox<String>();
		comboBoxEnd.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag" }));
		comboBoxEnd.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxEnd.setBounds(162, 177, 110, 20);
		panelKrankmeldung.add(comboBoxEnd);

		comboBoxMA = new JComboBox<String>();
		// Ausgeben einer Arraylist der gesamten Mitarbeiter
		ma = myModel.getAlleMitarbeiter();
		for (Mitarbeiter m : ma) {
			comboBoxMA.addItem(m.getBenutzername() + m.getVorname() + " " + m.getName());
		}
		comboBoxMA.setBounds(150, 229, 180, 20);
		panelKrankmeldung.add(comboBoxMA);

		txtGrund = new JTextField();
		txtGrund.setHorizontalAlignment(SwingConstants.CENTER);
		txtGrund.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtGrund.setBounds(61, 317, 277, 208);
		panelKrankmeldung.add(txtGrund);
		txtGrund.setColumns(10);

		lblVon = new JLabel("Von");
		lblVon.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblVon.setBounds(61, 148, 46, 14);
		panelKrankmeldung.add(lblVon);

		lblBis = new JLabel("Bis ");
		lblBis.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblBis.setBounds(61, 180, 26, 14);
		panelKrankmeldung.add(lblBis);

		lblNotizEintragen = new JLabel("Notiz eintragen");
		lblNotizEintragen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNotizEintragen.setBounds(61, 285, 152, 28);
		panelKrankmeldung.add(lblNotizEintragen);

		lblMitarbeiterAuswaehlen = new JLabel("Mitarbeiter");
		lblMitarbeiterAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblMitarbeiterAuswaehlen.setBounds(61, 230, 165, 14);
		panelKrankmeldung.add(lblMitarbeiterAuswaehlen);

		buttonBestaetigen = new JButton("Bestätigen");
		buttonBestaetigen.setHorizontalAlignment(SwingConstants.LEFT);
		buttonBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 15));
		buttonBestaetigen.setBounds(500, 500, 118, 25);
		panelKrankmeldung.add(buttonBestaetigen);

		setVisible(true);

		/**
		 * 
		 * @author Ramona Gerke
		 * @Info Eine ActionPerformedMethode, welche für die Ausführung des Buttons
		 *       "Bestätigen" zuständig ist. @ Info Nach dem betätigen des Buttons
		 *       werden die zuvor eingegebenen Daten für die Übergabe an den Controller
		 *       aufgearbeitet.
		 *
		 */
		buttonBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Meldung, ob die Daten wirklich gelöscht werden soll ( Ja, Nein , Abbrechen
				// Abfrage)
				int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null,
						JOptionPane.YES_NO_CANCEL_OPTION);
				// weiter bei ja
				if (eingabe == JOptionPane.YES_OPTION) {
					// Treemap für den Zeitraum
					zeitraum = new TreeMap<String, String>();
					try {
						username = comboBoxMA.getSelectedItem().toString();
						username.trim();
						username.split("-");
						username.substring(0);
						String bez = "Krankheit";
						String grund = txtGrund.getText().toString();
						zeitraum.put("wpbez", comboBoxWochenplaene.getSelectedItem().toString());
						zeitraum.put("anfZeitraumTag", comboBoxAnfang.getSelectedItem().toString());
						zeitraum.put("endZeitraumTag", comboBoxEnd.getSelectedItem().toString());

						// Übergabe an den Controller
						myController.erstelleTermin(username, bez, zeitraum, grund);
						JOptionPane.showMessageDialog(null, "Krankmeldung wurde erfolgreich erstellt!");
						dispose();
					}

					catch (Exception ex) {
						JOptionPane.showMessageDialog(null,
								"Daten konnten nicht umgewandelt werden, da die Dateiformate nicht stimmen! - Fehler: TerminErstellenView Zeile Button Bestätigen ActionPerformed",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Daten können geändert werden.");
				}
			}
		});
	}
}
