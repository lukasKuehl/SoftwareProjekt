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
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

/**
 * 
 * @author Ramona Gerke
 * @Info Die Klasse der View Termin l�schen.
 * @info Diese beinhaltet seinen Konstruktor und die ActionPerformed Methode.
 *
 */
class TauschanfrageErstellenView extends JFrame {
	// Initialisierung der Instanzvariablen
	private JPanel contentPane = null;
	private JLabel lblTauschanfrageStellen, labelTagAndererMA, lblWochenplanAuswaehlen, lblMitWemWollen,
			lblSchichtAuswaehlen, lblTagAuswaehlen, labelSchichtAndererMA, lblMitarbeiter = null;
	private JComboBox<String> cmbBoxWP, cmbBoxTag, cmbBoxSchicht, comboBoxTagAndererMA, comboBoxSchichtAndererMA,
			comboBoxEmpfaengerName = null;
	private ArrayList<String> wp, tagMa, schichtAndererMa, tagAndererMa, schichtMa = null;
	private JButton btnErstellen = null;
	private EinsatzplanController myController = null;
	private Einsatzplanview myView = null;
	private Einsatzplanmodel myModel = null;
	private Schicht mySchicht = null;
	private String empfaengerName, sendername = null;
	private int senderSchichtnr, empfaengerSchichtNr = 0;

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

		lblTauschanfrageStellen = new JLabel("Tauschanfrage stellen");
		lblTauschanfrageStellen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTauschanfrageStellen.setBounds(62, 91, 385, 26);
		contentPane.add(lblTauschanfrageStellen);

		btnErstellen = new JButton("Erstellen");
		btnErstellen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnErstellen.setBounds(638, 503, 110, 25);
		contentPane.add(btnErstellen);

		cmbBoxWP = new JComboBox<String>();
		cmbBoxWP.setFont(new Font("Verdana", Font.PLAIN, 15));
		// Ausgeben der ArrayListe Wochenpl�ne und in einer JComboBox hinterlegen
		wp = myController.getWochenplaene();
		for (String m : wp) {
			cmbBoxWP.addItem(m);
		}
		cmbBoxWP.setBounds(249, 174, 136, 26);
		contentPane.add(cmbBoxWP);

		cmbBoxTag = new JComboBox<String>();
		// Ausgeben der ArrayListe f�r die Tage der Wochenpl�ne und in einer JComboBox
		// hinterlegen
		tagMa = myController.getTage(cmbBoxWP.getSelectedItem().toString());
		for (String s : tagMa) {
			cmbBoxTag.addItem(s);
		}
		cmbBoxTag.setEnabled(false);
		cmbBoxTag.setFont(new Font("Verdana", Font.PLAIN, 15));
		cmbBoxTag.setBounds(249, 211, 136, 26);
		contentPane.add(cmbBoxTag);

		cmbBoxSchicht = new JComboBox<String>();
		cmbBoxSchicht.setEnabled(false);
		cmbBoxSchicht.setFont(new Font("Verdana", Font.PLAIN, 15));
		cmbBoxSchicht.setBounds(249, 248, 499, 26);
		contentPane.add(cmbBoxSchicht);

		lblWochenplanAuswaehlen = new JLabel("Wochenplan ausw�hlen");
		lblWochenplanAuswaehlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblWochenplanAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblWochenplanAuswaehlen.setBounds(62, 178, 188, 22);
		contentPane.add(lblWochenplanAuswaehlen);

		lblTagAuswaehlen = new JLabel("Tag ausw�hlen");
		lblTagAuswaehlen.setEnabled(false);
		lblTagAuswaehlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTagAuswaehlen.setHorizontalAlignment(SwingConstants.LEFT);
		lblTagAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblTagAuswaehlen.setBounds(62, 211, 178, 26);
		contentPane.add(lblTagAuswaehlen);

		lblSchichtAuswaehlen = new JLabel("Schicht ausw�hlen");
		lblSchichtAuswaehlen.setEnabled(false);
		lblSchichtAuswaehlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSchichtAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblSchichtAuswaehlen.setBounds(62, 248, 136, 26);
		contentPane.add(lblSchichtAuswaehlen);

		lblMitWemWollen = new JLabel("Tauschdaten");
		lblMitWemWollen.setFont(new Font("Verdana", Font.BOLD, 15));
		lblMitWemWollen.setBounds(62, 347, 222, 14);
		contentPane.add(lblMitWemWollen);

		labelTagAndererMA = new JLabel("Tag ausw�hlen");
		labelTagAndererMA.setEnabled(false);
		labelTagAndererMA.setVerticalAlignment(SwingConstants.BOTTOM);
		labelTagAndererMA.setHorizontalAlignment(SwingConstants.LEFT);
		labelTagAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelTagAndererMA.setBounds(62, 372, 178, 26);
		contentPane.add(labelTagAndererMA);

		comboBoxTagAndererMA = new JComboBox<String>();
		// Ausgeben einer ArrayList mit den Tagen der Mitarbeiter in einer JComboBox
		// hinterlegen
		tagAndererMa = myController.getTage(cmbBoxWP.getSelectedItem().toString());
		for (String n : tagAndererMa) {
			comboBoxTagAndererMA.addItem(n);
		}
		comboBoxTagAndererMA.setEnabled(false);
		comboBoxTagAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxTagAndererMA.setBounds(249, 372, 136, 26);
		contentPane.add(comboBoxTagAndererMA);

		labelSchichtAndererMA = new JLabel("Schicht ausw�hlen");
		labelSchichtAndererMA.setEnabled(false);
		labelSchichtAndererMA.setVerticalAlignment(SwingConstants.BOTTOM);
		labelSchichtAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelSchichtAndererMA.setBounds(62, 409, 136, 26);
		contentPane.add(labelSchichtAndererMA);

		comboBoxSchichtAndererMA = new JComboBox<String>();
		comboBoxSchichtAndererMA.setEnabled(false);
		comboBoxSchichtAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxSchichtAndererMA.setBounds(249, 409, 499, 26);
		contentPane.add(comboBoxSchichtAndererMA);
		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methoden, die die Sichtbarkeit der ComboBoxen �ndert.
		 */

		// ACTION PERFORMED METHODEN
		cmbBoxWP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmbBoxTag.setEnabled(true);
				lblTagAuswaehlen.setEnabled(true);

			}
		});

		/**
		 * @author Ramona Gerke
		 * @info Action Performed Methode f�r die JcomboBox Tag ausw�hlen. @ info nach
		 * dem Ausw�hlen des Tages, wird die ComboBox Schicht sichtbar und f�llt diese
		 * mit der Schicht f�r den Mitarbeiter.
		 */
		cmbBoxTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Auslesen einer Array List mit den Schichten
				try {
					schichtMa = myController.getMitarbeiterSchichten(cmbBoxWP.getSelectedItem().toString(),
							cmbBoxTag.getSelectedItem().toString(), myView.getUsername());
					
					 String s = schichtMa.toString(); System.out.println(s);
					// String nach "," splitten und in zwei Strings speichern
					String[] temp = s.split(",");
					String sender = temp[0];
					String empfaenger = temp[1];
					// String sender splitt f�r die �bergabe am Controller und Aufbereitung der
					// Parameter
					String[] tempSender = sender.split("-");
					String schichtNrsMa = tempSender[0].substring(1, 6);
					senderSchichtnr = Integer.parseInt(schichtNrsMa);
					String kw = tempSender[1];
					String tag = tempSender[2];
					String anfangszeit = tempSender[3];
					String endzeit = tempSender[4];
					sendername = tempSender[5];
					cmbBoxSchicht.addItem(kw + " " + tag + " " + anfangszeit + " " + endzeit + " ");
					
					cmbBoxSchicht.setEnabled(true);
					lblSchichtAuswaehlen.setEnabled(true);
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null,
							"Keine Schicht vorhanden. W�hlen Sie einen anderen Tag oder Wochenplan", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		cmbBoxSchicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxTagAndererMA.setEnabled(true);
				labelTagAndererMA.setEnabled(true);
			}

		});
		comboBoxTagAndererMA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					schichtAndererMa = myController.getAndereMitarbeiterSchichten(cmbBoxWP.getSelectedItem().toString(),
							 myView.getUsername(), senderSchichtnr);
					String s = schichtAndererMa.toString(); System.out.println(s);
					// String nach "," splitten und in zwei Strings speichern
					String[] temp = s.split(",");
					String sender = temp[0];
					String empfaenger = temp[1];
					// String empfaenger splittten f�r die �bergabe an den Controller und
					// Aufbereitung der Parameter
					String[] tempEmpfaenger = empfaenger.split("-");
					String schichtNrsAMa = tempEmpfaenger[0].trim();
					empfaengerSchichtNr = Integer.parseInt(schichtNrsAMa);
					String kwAndererMa = tempEmpfaenger[1];
					String tagAndererMa = tempEmpfaenger[2];
					String anfangszeitAndererMa = tempEmpfaenger[3];
					String endzeitAndererMa = tempEmpfaenger[4];
					String empfaengerName = tempEmpfaenger[5];
					comboBoxSchichtAndererMA.addItem(kwAndererMa + " " + tagAndererMa + " " + anfangszeitAndererMa + " "
							+ endzeitAndererMa + " " + empfaengerName);
					
					comboBoxSchichtAndererMA.setEnabled(true);
					labelSchichtAndererMA.setEnabled(true);
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null,
							"Kein Mitarbeiter zum Wechseln der Schicht vorhanden. W�hlen Sie einen anderen Tag!",
							"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		;

		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methode die nach dem Bet�tigen des Buttons
		 *       "erstellen", die Tauschanfrage anhand des SendernNamens, der
		 *       SenderSchichtNr, des Emf�ngerNamens und der Empf�nger SchichtNr
		 *       erstellt.
		 */

		btnErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Meldung, ob die Daten wirklich genommen werden soll ( Ja, Nein , Abbrechen
					// Abfrage)
					int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten best�tigen?", null,
							JOptionPane.YES_NO_CANCEL_OPTION);
					// weiter bei ja
					if (eingabe == JOptionPane.YES_OPTION) {

						// �bergabe an den Controller
						myController.erstelleTauschanfrage(myView.getUsername(), senderSchichtnr, empfaengerName,
								empfaengerSchichtNr);
						JOptionPane.showConfirmDialog(null, "Tauschanfrage erfolgreich erstellt", "",
								JOptionPane.PLAIN_MESSAGE);
						dispose();

					} else {
						JOptionPane.showMessageDialog(null, "W�hlen Sie andere Daten aus!", "",
								JOptionPane.PLAIN_MESSAGE);
					}

				} catch (Exception a) {
					JOptionPane.showMessageDialog(null,
							"Die Tauschanfrage konnte nicht erstellt werden. - Methode ActionPerformed (btnBest�tigen, TerminErstellen)",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		setVisible(true);

	}
}
