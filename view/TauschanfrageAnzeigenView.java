package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

/**
 * 
 * @author Ramona Gerke
 * @Info Die Klasse der View Tauschanfrage anzeigen.
 * @info Diese beinhaltet seinen Konstruktor und die ActionPerformed Methode.
 *
 */
class TauschanfrageAnzeigenView extends JFrame {
	// Initialisierung der Instanzvariablen
	private JPanel contentPane = null;
	private JTextField textFieldDatum = null;
	private JLabel lblTauschanfrageAnzeigen = null;
	private JButton btnAnnehmen = null;
	private JList<String> listTauschanfragen = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;
	private DefaultListModel<Object> modelTauschanfrage = null;
	private ArrayList<String> tl = null;

	/**
	 * @author Ramona Gerke
	 * @info Konstruktor der View Tauschanfrage anzeigen.
	 */

	protected TauschanfrageAnzeigenView(Einsatzplanview myView, Einsatzplanmodel myModel,
			EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Tauschanfrage annehmen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800, 600);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		listTauschanfragen = new JList<String>();
		tl = myController.getTauschanfragen(myView.getUsername());
		modelTauschanfrage = new DefaultListModel<Object>();
		listTauschanfragen.setBounds(72, 140, 315, 336);
		contentPane.add(listTauschanfragen);

		lblTauschanfrageAnzeigen = new JLabel("Tauschanfragen");
		lblTauschanfrageAnzeigen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTauschanfrageAnzeigen.setBounds(62, 91, 198, 26);
		contentPane.add(lblTauschanfrageAnzeigen);

		btnAnnehmen = new JButton("Annehmen");
		btnAnnehmen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnAnnehmen.setBounds(500, 500, 127, 25);
		contentPane.add(btnAnnehmen);

		setVisible(true);

		/**
		 * @author Ramona Gerke
		 * @info Action Performed Methode, die nach dem Best�tigen des Buttons
		 *       "Annehmen" ausgef�hrt wird, Sie �bergibt die TauschanfrageNr. der
		 *       ausgw�hlten Tauschanfrage an den Controller.
		 * 
		 */

		// Action Perfomed Methode

		btnAnnehmen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Ausgabe der Fehlermeldung bei leerer Liste
				if (listTauschanfragen.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Es wurde keine Tauschanfrage ausgew�hlt. Bitte w�hlen Sie eine Tauschanfrage aus.",
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					// Meldung, ob die Daten wirklich gel�scht werden soll ( Ja, Nein , Abbrechen
					// Abfrage)
					try {
						int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Tauschanfrage annehmen?",
								null, JOptionPane.YES_NO_CANCEL_OPTION);
						// weiter bei ja
						if (eingabe == JOptionPane.YES_OPTION) {
							String s = listTauschanfragen.getSelectedValue().toString().trim();
							String[] temp = s.split("-");
							String tauschanfrageNrS = temp[0].substring(10); // substring l�nge bestimmen
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
							myController.akzeptiereTauschanfrage(empfaengerName, tauschanfrageNr);
							JOptionPane.showConfirmDialog(null, "Tauschanfrage erfolgreich angenommen",  "", JOptionPane.INFORMATION_MESSAGE);
							dispose();

						} else {
							JOptionPane.showMessageDialog(null,
									"W�hlen Sie eine andere Tauschanfrage zum Annehmen aus!", "  ",
									JOptionPane.WARNING_MESSAGE);
						}
					}

					catch (Exception a) {
						JOptionPane.showMessageDialog(null,
								"Die Liste konnte nicht �bergeben werden. - Methode ActionPerformed (btnBest�tigen, TerminLoeschen)",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
}