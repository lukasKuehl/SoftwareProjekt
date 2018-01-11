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
import javax.swing.*;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
	private DefaultListModel<String> modelTauschanfrage = null;
	private ArrayList<String> tl = null;
	private String empfaengername = null;
	private WindowListener windowListener;

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
		setBounds(250, 250, 1000, 700);

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

		listTauschanfragen = new JList<String>();
		tl = myController.getTauschanfragen(myView.getUsername());
		modelTauschanfrage = new DefaultListModel<String>();
		for (String m : tl) {
			modelTauschanfrage.addElement(m);
		}
		listTauschanfragen.setModel(modelTauschanfrage);
		listTauschanfragen.setBounds(51, 127, 860, 333);
		contentPane.add(listTauschanfragen);

		lblTauschanfrageAnzeigen = new JLabel("Tauschanfragen");
		lblTauschanfrageAnzeigen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTauschanfrageAnzeigen.setBounds(51, 55, 198, 26);
		contentPane.add(lblTauschanfrageAnzeigen);

		btnAnnehmen = new JButton("Annehmen");
		btnAnnehmen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnAnnehmen.setBounds(784, 509, 127, 25);
		contentPane.add(btnAnnehmen);

		JLabel label = new JLabel("Bitte auswählen:");
		label.setFont(new Font("Verdana", Font.PLAIN, 14));
		label.setBounds(51, 93, 147, 18);
		contentPane.add(label);

		setVisible(true);

		/**
		 * @author Ramona Gerke
		 * @info Action Performed Methode, die nach dem Bestätigen des Buttons
		 *       "Annehmen" ausgeführt wird, Sie übergibt die TauschanfrageNr. der
		 *       ausgwählten Tauschanfrage an den Controller.
		 * 
		 */

		// Action Perfomed Methode

		btnAnnehmen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abfrage, ob kein Element aus der List ausgewählt worden ist
				// weiter bei ja
				if (listTauschanfragen.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Es wurde keine Tauschanfrage ausgewählt. Bitte wählen Sie eine Tauschanfrage aus.",
							"Error", JOptionPane.ERROR_MESSAGE);
					// weiter bei nein
				} else {
					// Meldung, ob die Daten wirklich gelöscht werden soll
					try {
						int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Tauschanfrage annehmen?",
								null, JOptionPane.YES_NO_CANCEL_OPTION);
						// weiter bei ja
						if (eingabe == JOptionPane.YES_OPTION) {
							// das gewählte Element für die Übergabe an den Controller vorbereiten
							String s = listTauschanfragen.getSelectedValue().toString();
							String[] temp = s.split("-");
							temp[0] = temp[0].substring(10);
							String tauschanfrageNrS = temp[0].trim();
							int tauschanfrageNr = Integer.parseInt(tauschanfrageNrS);
							String empfaengername = temp[8].substring(2, 9);
						
							// Erfolgsmeldung und Übergabe an den Controller
							if (myController.akzeptiereTauschanfrage(empfaengername, tauschanfrageNr)) {
							JOptionPane.showMessageDialog(null, "Tauschanfrage erfolgreich angenommen", "",
									JOptionPane.INFORMATION_MESSAGE);
							dispose();
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"Wählen Sie eine andere Tauschanfrage zum Annehmen aus!", "  ",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception a) {
						JOptionPane.showMessageDialog(null,
								"Die Liste konnte nicht übergeben werden. - Methode ActionPerformed (btnBestätigen, TerminLoeschen)",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
}