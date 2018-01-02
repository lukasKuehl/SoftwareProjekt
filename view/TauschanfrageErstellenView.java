package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.EinsatzplanController;
import data.Mitarbeiter;
import data.Schicht;
import data.Tauschanfrage;
import model.Einsatzplanmodel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
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
			lblSchichtAuswaehlen, lblTagAuswaehlen, labelSchichtAndererMA, lblMitarbeiter = null;
	private JComboBox<String> cmbBoxWP, cmbBoxTag, cmbBoxSchicht, comboBoxTagAndererMA, comboBoxSchichtAndererMA,
			comboBoxEmpfaengerName = null;
	private ArrayList<String> wp, tagMa, SchichtAndererMa, TagAndererMa, SchichtMa, empfaenger = null;
	private JButton btnErstellen = null;
	private EinsatzplanController myController = null;
	private Einsatzplanview myView = null;
	private Einsatzplanmodel myModel = null;
	private Schicht mySchicht = null;
	private int senderSchichtnr = 0;
	String temp[] = new String[14];

	// JComboBox Inhalt festlegen!
	private JComboBox comboBox;

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
		btnErstellen.setBounds(500, 500, 110, 25);
		contentPane.add(btnErstellen);

		cmbBoxWP = new JComboBox<String>();
		// Ausgeben der ArrayListe Wochenpläne und in einer JComboBox hinterlegen
		wp = myController.getWochenplaene();
		for (String m : wp) {
			cmbBoxWP.addItem(m);
		}
		cmbBoxWP.setBounds(249, 174, 136, 26);
		contentPane.add(cmbBoxWP);

		cmbBoxTag = new JComboBox<String>();
		// Ausgeben der ArrayListe für die Tage der Wochenpläne und in einer JComboBox
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
		// Ausgeben der ArrayListe mit den Schichten des Mitarbeiters und in einer
		// JComboBox hinterlegen
		SchichtMa = myController.getMitarbeiterSchichten(cmbBoxWP.getSelectedItem().toString(),
				cmbBoxTag.getSelectedItem().toString(), myView.getUsername());
		for (String v : SchichtMa) {
			cmbBoxSchicht.addItem(v);
		}
		cmbBoxSchicht.setEnabled(false);
		cmbBoxSchicht.setFont(new Font("Verdana", Font.PLAIN, 15));
		cmbBoxSchicht.setBounds(249, 248, 136, 26);
		contentPane.add(cmbBoxSchicht);

		lblWochenplanAuswaehlen = new JLabel("Wochenplan auswählen");
		lblWochenplanAuswaehlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblWochenplanAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblWochenplanAuswaehlen.setBounds(62, 178, 188, 22);
		contentPane.add(lblWochenplanAuswaehlen);

		lblTagAuswaehlen = new JLabel("Tag auswählen");
		lblTagAuswaehlen.setEnabled(false);
		lblTagAuswaehlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTagAuswaehlen.setHorizontalAlignment(SwingConstants.LEFT);
		lblTagAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblTagAuswaehlen.setBounds(62, 211, 178, 26);
		contentPane.add(lblTagAuswaehlen);

		lblSchichtAuswaehlen = new JLabel("Schicht auswählen");
		lblSchichtAuswaehlen.setEnabled(false);
		lblSchichtAuswaehlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSchichtAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblSchichtAuswaehlen.setBounds(62, 248, 136, 26);
		contentPane.add(lblSchichtAuswaehlen);

		lblMitWemWollen = new JLabel("Tauschdaten");
		lblMitWemWollen.setFont(new Font("Verdana", Font.BOLD, 15));
		lblMitWemWollen.setBounds(62, 347, 222, 14);
		contentPane.add(lblMitWemWollen);

		labelTagAndererMA = new JLabel("Tag auswählen");
		labelTagAndererMA.setEnabled(false);
		labelTagAndererMA.setVerticalAlignment(SwingConstants.BOTTOM);
		labelTagAndererMA.setHorizontalAlignment(SwingConstants.LEFT);
		labelTagAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelTagAndererMA.setBounds(62, 372, 178, 26);
		contentPane.add(labelTagAndererMA);

		comboBoxTagAndererMA = new JComboBox<String>();
		// Ausgeben einer ArrayList mit den Tagen der Mitarbeiter in einer JComboBox hinterlegen
		TagAndererMa = myController.getTage(cmbBoxWP.getSelectedItem().toString());
		for (String n : TagAndererMa) {
			comboBoxTagAndererMA.addItem(n);
		}
		comboBoxTagAndererMA.setEnabled(false);
		comboBoxTagAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxTagAndererMA.setBounds(249, 372, 136, 26);
		contentPane.add(comboBoxTagAndererMA);

		labelSchichtAndererMA = new JLabel("Schicht auswählen");
		labelSchichtAndererMA.setEnabled(false);
		labelSchichtAndererMA.setVerticalAlignment(SwingConstants.BOTTOM);
		labelSchichtAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelSchichtAndererMA.setBounds(62, 409, 136, 26);
		contentPane.add(labelSchichtAndererMA);

		comboBoxSchichtAndererMA = new JComboBox<String>();
		ArrayList<String> al = myController.getTauschanfragen(myView.getUsername());
		//  Ausgeben einer ArrayList mit allen Schichten in einer JComboBox
		 SchichtAndererMa = myController.getSchichten(cmbBoxWP.getSelectedItem().toString(),
		 cmbBoxTag.getSelectedItem().toString()); 
		 // Methode Get Schichten noch in den
		 // EinsatzplanController integrieren
		for (String c : SchichtAndererMa) {
			comboBoxSchichtAndererMA.addItem(c);
		}
		comboBoxSchichtAndererMA.setEnabled(false);
		comboBoxSchichtAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxSchichtAndererMA.setBounds(249, 409, 136, 26);
		contentPane.add(comboBoxSchichtAndererMA);

		lblMitarbeiter = new JLabel("Mitarbeiter ");
		lblMitarbeiter.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblMitarbeiter.setBounds(62, 452, 124, 26);
		contentPane.add(lblMitarbeiter);

		// Ausgeben einer ArraysList mit den Mitarbeiter der ausgewählten Schicht
		int schichtNr = Integer.parseInt(comboBoxSchichtAndererMA.getSelectedItem().toString());
		 empfaenger = myController.getVerfuegbareMitarbeiter(schichtNr);
		 comboBoxEmpfaengerName = new JComboBox();
		 for (String c : empfaenger) {
		 comboBoxEmpfaengerName.addItem(c);
		 }
		comboBoxEmpfaengerName.setBounds(249, 450, 136, 26);
		contentPane.add(comboBoxEmpfaengerName);

		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methoden, die die Sichtbarkeit der ComboBoxen ändert.
		 */

		// ACTION PERFORMED METHODEN
		cmbBoxWP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == cmbBoxWP) {
					cmbBoxTag.setEnabled(true);
					lblTagAuswaehlen.setEnabled(true);
				}
			}
		});

		cmbBoxTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == cmbBoxTag) {
					cmbBoxSchicht.setEnabled(true);
					lblSchichtAuswaehlen.setEnabled(true);
				}
			}
		});

		cmbBoxSchicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == cmbBoxSchicht) {
					comboBoxTagAndererMA.setEnabled(true);
					labelTagAndererMA.setEnabled(true);
				}
			}

		});
		comboBoxTagAndererMA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == comboBoxTagAndererMA) {
					comboBoxSchichtAndererMA.setEnabled(true);
					labelSchichtAndererMA.setEnabled(true);

				}
			}
		});

		comboBoxSchichtAndererMA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == comboBoxSchichtAndererMA) {
					comboBoxEmpfaengerName.setEnabled(true);
					lblMitarbeiter.setEnabled(true);

				}
			}
		});

		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methode die nach dem bestätigen des Buttons
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

						String senderName = myView.getUsername();
						int senderSchichtnr = Integer.parseInt(temp[2]);
						String empfaengerName = comboBoxEmpfaengerName.getSelectedItem().toString();
						String empfaengerSchichtNrString = comboBoxSchichtAndererMA.getSelectedItem().toString();
						int empfaengerSchichtNr = Integer.parseInt(empfaengerSchichtNrString);

						// Übergabe an den Controller
						myController.erstelleTauschanfrage(senderName, senderSchichtnr, empfaengerName,
								empfaengerSchichtNr);
						JOptionPane.showConfirmDialog(null, "Tauschanfrage erfolgreich erstellt");
						dispose();

					} else {
						JOptionPane.showMessageDialog(null, "Wählen Sie andere Daten aus!", "  ",
								JOptionPane.WARNING_MESSAGE);
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
