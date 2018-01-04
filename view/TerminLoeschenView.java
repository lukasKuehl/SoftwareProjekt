package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

/**
 * 
 * @author Ramona Gerke
 * @Info Die Klasse der View Termin löschen.
 * @info Diese beinhaltet seinen Konstruktor und die ActionPerformed Methode.
 *
 */
class TerminLoeschenView extends JFrame {
	// Initialisierung der Instanzvariablen
	private JPanel contentPane = null;
	private JLabel lblTerninLoeschen, lblTerminAuswaehlen = null;
	private JList<String> listTermin = null;
	private String username = null;
	private DefaultListModel<String> model = null;
	private ArrayList<String> tl = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;
	private JButton btnBestaetigen = null;

	/**
	 * @ author Ramona Gerke
	 * 
	 * @Info Der Konstruktor der View Termin loeschen..
	 */
	public TerminLoeschenView(Einsatzplanview myView, Einsatzplanmodel myModel, EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Termin löschen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800, 600);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblTerninLoeschen = new JLabel("Termin löschen");
		lblTerninLoeschen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTerninLoeschen.setBounds(60, 83, 199, 26);
		contentPane.add(lblTerninLoeschen);

		listTermin = new JList<String>();
		listTermin.setFont(new Font("Verdana", Font.PLAIN, 21));
		// Ausgeben einer Arraylist für alle Termine des Mitarbeiters
		tl = myController.getMitarbeiterTermine(myView.getUsername());
		model = new DefaultListModel<String>();
		for (String m : tl) {
			model.addElement(m);
		}
		listTermin.setModel(model);
		listTermin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTermin.setBounds(65, 145, 622, 355);
		listTermin.getModel();
		contentPane.add(listTermin);

		lblTerminAuswaehlen = new JLabel("Termin auswählen");
		lblTerminAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblTerminAuswaehlen.setBounds(62, 120, 152, 14);
		contentPane.add(lblTerminAuswaehlen);

		btnBestaetigen = new JButton("best\u00E4tigen");
		btnBestaetigen.setHorizontalAlignment(SwingConstants.LEFT);
		btnBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnBestaetigen.setBounds(500, 500, 110, 25);
		contentPane.add(btnBestaetigen);

		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methode die nach dem bestätigen des Buttons
		 *       "bestätigen", dass das ausgewählte Element anhand der Termin Nr und
		 *       dessen Usernamen gelöscht.
		 */
		// ACTION PERFROMED Methode
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abfrage, ob kein Element in der Liste ausgewählt ist
				if (listTermin.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Es wurde keine Eingabe getätigt", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						// Meldung, ob die Daten wirklich gelöscht werden soll ( Ja, Nein , Abbrechen
						// Abfrage)
						int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie den Termin löschen?", null,
								JOptionPane.YES_NO_CANCEL_OPTION);
						// weiter bei ja
						if (eingabe == JOptionPane.YES_OPTION) {
							String s =listTermin.getSelectedValue().toString();
							String [] temp =s.split("-");
							temp[0] = temp[0].trim();	
							int terminnr = Integer.parseInt(temp[0]);
							String wpbez = temp [1];
							String date = temp[2];
							String anfangsUhrzeit = temp[3];
							String endUhrzeit = temp [4];
							JOptionPane.showMessageDialog(null, "Termin erfolgreich gelöscht", "", JOptionPane.INFORMATION_MESSAGE);
							dispose();
							
						} else {
							JOptionPane.showMessageDialog(null, "Wählen Sie einen anderen Termin aus!", " ",
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

		setVisible(true);
	}

}
