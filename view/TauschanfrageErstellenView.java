package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.EinsatzplanController;
import data.Schicht;
import model.Einsatzplanmodel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.awt.event.ActionEvent;


import controller.EinsatzplanController;
import model.Einsatzplanmodel;

/**
 * 
 * @author Ramona Gerke
 * @Info Die Klasse der View Termin löschen.
 * @info Diese beinhaltet seinen Konstruktor und die ActionPerformed Methode.
 *
 */
class TauschanfrageErstellenView extends JFrame {
	// Initialisierung der Instanzvariablen
	private JPanel contentPane = null;
	private JLabel lblTauschanfrageStellen, labelTagAndererMA, lblWochenplanAuswaehlen, lblMitWemWollen,
			lblSchichtAuswaehlen, lblTagAuswaehlen, labelSchichtAndererMA, lblBitteAuswhlen = null;
	private JComboBox<String> cmbBoxWP, cmbBoxTag, cmbBoxSchicht, comboBoxTagAndererMA, comboBoxSchichtAndererMA = null;
	private ArrayList<String> wp,  schichtAndererMa,  schichtMa  =null;
	private JButton btnErstellen, btnOKwochenplan, btnOkSchichtAndererMa, btnOKTagMa, btnOKSchichtMa,
			btnOKTagAndererMa = null;
	private EinsatzplanController myController = null;
	private Einsatzplanview myView = null;
	private Einsatzplanmodel myModel = null;
	private Schicht mySchicht = null;
	private String empfaengerName, sendername , tem= null;
	private int senderSchichtnr, empfaengerSchichtNr = 0;
	private WindowListener windowListener;

	/**
	 * @author Ramona Gerke
	 * @Info Konstruktor der View TauschanfrageErstellenView.
	 */

	protected TauschanfrageErstellenView(Einsatzplanmodel myModel, Einsatzplanview myView,
			EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Tauschanfrage erstellen");
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

		lblTauschanfrageStellen = new JLabel("Tauschanfrage erstellen");
		lblTauschanfrageStellen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTauschanfrageStellen.setBounds(62, 55, 385, 26);
		contentPane.add(lblTauschanfrageStellen);

		btnErstellen = new JButton("Erstellen");
		btnErstellen.setHorizontalAlignment(SwingConstants.LEFT);
		btnErstellen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnErstellen.setBounds(653, 503, 100, 25);
		contentPane.add(btnErstellen);

		cmbBoxWP = new JComboBox<String>();
		cmbBoxWP.setFont(new Font("Verdana", Font.PLAIN, 15));
		// Ausgeben der ArrayListe Wochenpläne und in einer JComboBox hinterlegen
		wp = myController.getWochenplaene();
		for (String m : wp) {
			cmbBoxWP.addItem(m);
		}
		cmbBoxWP.setBounds(249, 140, 136, 26);
		contentPane.add(cmbBoxWP);

		cmbBoxTag = new JComboBox<String>();
		cmbBoxTag.setEnabled(false);
		cmbBoxTag.setFont(new Font("Verdana", Font.PLAIN, 15));
		cmbBoxTag.setBounds(249, 187, 136, 26);
		contentPane.add(cmbBoxTag);

		cmbBoxSchicht = new JComboBox<String>();
		cmbBoxSchicht.setEnabled(false);
		cmbBoxSchicht.setFont(new Font("Verdana", Font.PLAIN, 15));
		cmbBoxSchicht.setBounds(248, 234, 505, 26);
		contentPane.add(cmbBoxSchicht);

		lblWochenplanAuswaehlen = new JLabel("Wochenplan auswählen");
		lblWochenplanAuswaehlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblWochenplanAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblWochenplanAuswaehlen.setBounds(62, 144, 188, 22);
		contentPane.add(lblWochenplanAuswaehlen);

		lblTagAuswaehlen = new JLabel("Tag auswählen");
		lblTagAuswaehlen.setEnabled(false);
		lblTagAuswaehlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTagAuswaehlen.setHorizontalAlignment(SwingConstants.LEFT);
		lblTagAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblTagAuswaehlen.setBounds(62, 187, 178, 26);
		contentPane.add(lblTagAuswaehlen);

		lblSchichtAuswaehlen = new JLabel("Schicht auswählen");
		lblSchichtAuswaehlen.setEnabled(false);
		lblSchichtAuswaehlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSchichtAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblSchichtAuswaehlen.setBounds(61, 234, 136, 26);
		contentPane.add(lblSchichtAuswaehlen);

		lblMitWemWollen = new JLabel("Tauschdaten");
		lblMitWemWollen.setFont(new Font("Verdana", Font.BOLD, 15));
		lblMitWemWollen.setBounds(62, 340, 222, 14);
		contentPane.add(lblMitWemWollen);

		labelTagAndererMA = new JLabel("Tag auswählen");
		labelTagAndererMA.setEnabled(false);
		labelTagAndererMA.setVerticalAlignment(SwingConstants.BOTTOM);
		labelTagAndererMA.setHorizontalAlignment(SwingConstants.LEFT);
		labelTagAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelTagAndererMA.setBounds(62, 365, 178, 26);
		contentPane.add(labelTagAndererMA);

		comboBoxTagAndererMA = new JComboBox<String>();
		comboBoxTagAndererMA.setEnabled(false);
		comboBoxTagAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxTagAndererMA.setBounds(249, 365, 136, 26);
		contentPane.add(comboBoxTagAndererMA);

		labelSchichtAndererMA = new JLabel("Schicht auswählen");
		labelSchichtAndererMA.setEnabled(false);
		labelSchichtAndererMA.setVerticalAlignment(SwingConstants.BOTTOM);
		labelSchichtAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelSchichtAndererMA.setBounds(62, 409, 136, 26);
		contentPane.add(labelSchichtAndererMA);

		comboBoxSchichtAndererMA = new JComboBox<String>();
		comboBoxSchichtAndererMA.setEnabled(false);
		comboBoxSchichtAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxSchichtAndererMA.setBounds(249, 409, 504, 26);
		contentPane.add(comboBoxSchichtAndererMA);

		btnOKwochenplan = new JButton("OK");
		btnOKwochenplan.setFont(new Font("Verdana", Font.PLAIN, 10));
		btnOKwochenplan.setBounds(406, 142, 59, 26);
		contentPane.add(btnOKwochenplan);

		btnOKTagMa = new JButton("OK");
		btnOKTagMa.setFont(new Font("Verdana", Font.PLAIN, 10));
		btnOKTagMa.setBounds(406, 187, 59, 26);
		contentPane.add(btnOKTagMa);

		btnOKSchichtMa = new JButton("OK");
		btnOKSchichtMa.setFont(new Font("Verdana", Font.PLAIN, 10));
		btnOKSchichtMa.setBounds(693, 269, 59, 26);
		contentPane.add(btnOKSchichtMa);

		btnOKTagAndererMa = new JButton("OK");
		btnOKTagAndererMa.setFont(new Font("Verdana", Font.PLAIN, 10));
		btnOKTagAndererMa.setBounds(406, 362, 59, 26);
		contentPane.add(btnOKTagAndererMa);

		btnOkSchichtAndererMa = new JButton("OK");
		btnOkSchichtAndererMa.setFont(new Font("Verdana", Font.PLAIN, 10));
		btnOkSchichtAndererMa.setBounds(693, 444, 59, 26);
		contentPane.add(btnOkSchichtAndererMa);

		lblBitteAuswhlen = new JLabel("Bitte ausw\u00E4hlen");
		lblBitteAuswhlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblBitteAuswhlen.setBounds(62, 93, 222, 26);
		contentPane.add(lblBitteAuswhlen);
		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methode der JComboBox Wochenpläne . Diese ändert nach
		 *       Auswahl des Wochenplans die Sichtbarkeit der ComboBox Tag und des
		 *       Labels Tag .
		 */
		btnOKwochenplan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<String> tagesliste = myController.getTage(cmbBoxWP.getSelectedItem().toString());

				cmbBoxTag.removeAllItems();
				comboBoxTagAndererMA.removeAllItems();
				cmbBoxSchicht.removeAllItems();
				comboBoxSchichtAndererMA.removeAllItems();
				// Ausgeben der ArrayListe für die Tage der Wochenpläne und in einer JComboBox
				// hinterlegen
				for (int i = 0; i < tagesliste.size(); i++) {
					cmbBoxTag.addItem(tagesliste.get(i));
				}

				cmbBoxTag.setEnabled(true);
				lblTagAuswaehlen.setEnabled(true);

			}
		});

		/**
		 * @author Ramona Gerke
		 * @info Action Performed Methode für die JcomboBox Tag auswählen. Nach dem
		 *       Auswählen des Tages, wird die ComboBox Schicht sichtbar und füllt diese
		 *       mit der Schicht des Mitarbeiters
		 */
		btnOKTagMa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Auslesen einer Array List mit den Schichten
				try {

					// Ausgabe einer ArrayList mit den Schichten des Mitarbeiters
					schichtMa = myController.getMitarbeiterSchichten(cmbBoxWP.getSelectedItem().toString(),
							cmbBoxTag.getSelectedItem().toString(), myView.getUsername());

					// Aufbereitung der ArrayList für die JComboBox Schicht des Mitarbeiters und die
					// Übergabe an den Controller
					String s = schichtMa.toString();
					String[] temp = s.split(",");
					String sender = temp[0];
					String empfaenger = temp[1];

					String[] tempSender = sender.split("-");
					String schichtNrsMa = tempSender[0].substring(1, 6);
					System.out.println(schichtNrsMa);
					senderSchichtnr = Integer.parseInt(schichtNrsMa);
					String kw = tempSender[1];
					String tag = tempSender[2];
					String anfangszeit = tempSender[3];
					String endzeit = tempSender[4];
					sendername = tempSender[5];
					// Notwendigen Parameter für die Anzeige der Schicht des Mitarbeiters der
					// JComboBox hinzufügen
					cmbBoxSchicht.addItem(kw + " " + tag + " " + anfangszeit + " " + endzeit + " ");
					
					//Zwischenspeicher für die Variablen der Übergabe an den Controller

					
					// Sichtbarkeit der JComboBx Schicht und Label Schicht des Mitarbeiters ändern
					cmbBoxSchicht.setEnabled(true);
					lblSchichtAuswaehlen.setEnabled(true);
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null,
							"Keine Schicht vorhanden. Wählen Sie einen anderen Tag oder Wochenplan", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methode - Ändert die Sichtbarkeit der ComboBox Tag und
		 *       des Labels Tag des auszuwählenden Mitarbeiters
		 */
		btnOKSchichtMa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				comboBoxTagAndererMA.removeAllItems();
				comboBoxSchichtAndererMA.removeAllItems();

				ArrayList<String> tageslisteAndererMA = myController.getTage(cmbBoxWP.getSelectedItem().toString());

				// Ausgeben der ArrayListe für die Tage der Wochenpläne und in einer JComboBox
				// hinterlegen
				for (int i = 0; i < tageslisteAndererMA.size(); i++) {
					comboBoxTagAndererMA.addItem(tageslisteAndererMA.get(i));
				}

				comboBoxTagAndererMA.setEnabled(true);
				labelTagAndererMA.setEnabled(true);
			}

		});
		/**
		 * @author Ramona Gerke
		 * @info Action Performed Methode für die JcomboBox Tag des anderen
		 *       Mitarbeiters. Nach dem Auswählen des Tages, wird die ComboBox Schicht
		 *       sichtbar und füllt diese mit der Schicht des anderen Mitarbeiters.
		 */
		btnOKTagAndererMa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					comboBoxSchichtAndererMA.removeAllItems();
					// Ausgabe einer ArrayList für die Schichten des anderen Mitarbeiters
					schichtAndererMa = myController.getAndereMitarbeiterSchichten(cmbBoxWP.getSelectedItem().toString(),
							myView.getUsername(), senderSchichtnr);
					// Schleife für die Anzeige die möglichen Mitarbeiter aus der ArrayList
					for (String n : schichtAndererMa) {
						// Aufbereitung der ArrayList für die JComboBox Schicht des anderen Mitarbeiters
						// und die Übergabe an den Controller
						String s = n.toString();
						System.out.println(s);
						String[] tempEmpfaenger = s.split("-");
						int empfaengerSchichtNr = Integer.parseInt(tempEmpfaenger[0]);
						String kwAndererMa = tempEmpfaenger[1];
						String tagAndererMa = tempEmpfaenger[2];
						String anfangszeitAndererMa = tempEmpfaenger[3];
						String endzeitAndererMa = tempEmpfaenger[4];
						String empfaengerName = tempEmpfaenger[5];
						//Zwischenspeicher für die Übergabe an den Controller
						
						// Notwendigen Parameter für die Anzeige der Schicht des anderen Mitarbeiters
						// der JComboBox hinzufügen
						comboBoxSchichtAndererMA.addItem(kwAndererMa + "- " + tagAndererMa + "-" + anfangszeitAndererMa
								+ "-" + endzeitAndererMa + "-" + empfaengerName);
					}
					// Sichtbarkeit der Schicht des anderen Mitarbeiters ändern
					comboBoxSchichtAndererMA.setEnabled(true);
					labelSchichtAndererMA.setEnabled(true);
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null,
							"Kein Mitarbeiter zum Wechseln der Schicht vorhanden. Wählen Sie einen anderen Tag!",
							"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnOkSchichtAndererMa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = comboBoxSchichtAndererMA.getSelectedItem().toString();
				String[] tempEmpfaenger = s.split("-");
				int empfaengerSchichtNr = Integer.parseInt(tempEmpfaenger[0]);
				String kwAndererMa = tempEmpfaenger[1];
				String tagAndererMa = tempEmpfaenger[2];
				String anfangszeitAndererMa = tempEmpfaenger[3];
				String endzeitAndererMa = tempEmpfaenger[4];
				String empfaengerName = tempEmpfaenger[5].trim();
		
				tem =senderSchichtnr +"-"+ sendername +"-"+ empfaengerSchichtNr +"-"+ empfaengerName ;
				System.out.println(tem);
		
			}
		});

		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methode die nach dem Betätigen des Buttons
		 *       "erstellen", die Tauschanfrage anhand des SendernNamens, der
		 *       SenderSchichtNr, des EmfängerNamens und der Empfänger SchichtNr
		 *       erstellt.
		 */

		btnErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Meldung, ob die Daten wirklich genommen werden soll ( Ja, Nein , Abbrechen
					// Abfrage)
					int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null,
							JOptionPane.YES_NO_CANCEL_OPTION);
					// weiter bei ja
					if (eingabe == JOptionPane.YES_OPTION) {
						
						//Übergabe der Variablen funktioniert nicht 
						String[]s;
						tem = tem.trim();
						System.out.println(tem);
						s =tem.split("-");

						int senderSchichtNummer =Integer.parseInt(s[0]);
						String senderName = s[1].trim();					
						int empfSchichtNummer = Integer.parseInt(s[2]);
						String empfName = s[3].trim();
						
						// Übergabe an den Controller
						myController.erstelleTauschanfrage(senderName, senderSchichtNummer, empfName,
								empfSchichtNummer);

						// Erfolgsmeldung
						if (myController.erstelleTauschanfrage(myView.getUsername(), senderSchichtNummer, empfName,
								empfSchichtNummer) != false) {
							JOptionPane.showConfirmDialog(null, "Tauschanfrage erfolgreich erstellt", "",
									JOptionPane.PLAIN_MESSAGE);
							dispose();
							myView.update();
						} else {
							JOptionPane.showMessageDialog(null,
									"Tauschanfrage konnte nicht gestellt werden! Bitte versuchen Sie es erneut!", "",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Wählen Sie andere Daten aus!", "",
								JOptionPane.PLAIN_MESSAGE);
					}

				} catch (Exception a) {
					JOptionPane.showMessageDialog(null,
							"Die Tauschanfrage konnte nicht erstellt werden. - Methode ActionPerformed (btnBestätigen, TerminErstellen)",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		setVisible(true);

	}
}
