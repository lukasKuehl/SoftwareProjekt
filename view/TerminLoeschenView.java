package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

/**
 * 
 * @author Ramona Gerke
 * @Info Die Klasse der View Termin l�schen.
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
	private WindowListener windowListener;

	/**
	 * @ author Ramona Gerke
	 * 
	 * @Info Der Konstruktor der View Termin loeschen.
	 */
	public TerminLoeschenView(Einsatzplanview myView, Einsatzplanmodel myModel, EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Termin l�schen");
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

		lblTerninLoeschen = new JLabel("Termin l�schen");
		lblTerninLoeschen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTerninLoeschen.setBounds(51, 55, 199, 26);
		contentPane.add(lblTerninLoeschen);

		listTermin = new JList<String>();
		listTermin.setFont(new Font("Verdana", Font.PLAIN, 13));
		// Ausgeben einer Arraylist f�r alle Termine des Mitarbeiters
		tl = myController.getMitarbeiterTermine(myView.getUsername());
		model = new DefaultListModel<String>();
		for (String m : tl) {
			model.addElement(m);
		}
		listTermin.setModel(model);
		listTermin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTermin.setBounds(51, 118, 622, 343);
		listTermin.getModel();
		contentPane.add(listTermin);

		lblTerminAuswaehlen = new JLabel("Termin ausw�hlen:");
		lblTerminAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblTerminAuswaehlen.setBounds(51, 93, 152, 14);
		contentPane.add(lblTerminAuswaehlen);

		btnBestaetigen = new JButton("L�schen");
		btnBestaetigen.setHorizontalAlignment(SwingConstants.LEFT);
		btnBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnBestaetigen.setBounds(570, 499, 95, 25);
		contentPane.add(btnBestaetigen);

		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methode die nach dem Bet�tigen des Buttons
		 *       "best�tigen" das ausgew�hlte Element anhand der Termin Nr und
		 *       dessen Usernamen l�scht.
		 */
		// ACTION PERFROMED Methode
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abfrage, ob kein Element in der Liste ausgew�hlt ist
				// weiter bei ja 
				if (listTermin.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Es wurde keine Eingabe get�tigt", "Error",
							JOptionPane.ERROR_MESSAGE);
					//weiter bei nein 
				} else {
					try {
						// Meldung, ob die Daten wirklich gel�scht werden soll ( Ja, Nein , Abbrechen
						// Abfrage)
						int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie den Termin l�schen?", null,
								JOptionPane.YES_NO_CANCEL_OPTION);
						// weiter bei ja
						if (eingabe == JOptionPane.YES_OPTION) {
							// ausgew�hltes Element f�r die �bergabe an den Controller aufbereiten
							String s =listTermin.getSelectedValue().toString();
							String [] temp =s.split("-");
							temp[0] = temp[0].trim();	
							int terminnr = Integer.parseInt(temp[0]);
							String wpbez = temp [1];
							String date = temp[2];
							String anfangsUhrzeit = temp[3];
							String endUhrzeit = temp [4];
						
							//Erfolgsmeldung und �bergabe an den Controller
							if (myController.entferneTermin(terminnr, myView.getUsername())){
							JOptionPane.showMessageDialog(null, "Termin erfolgreich gel�scht", "", JOptionPane.INFORMATION_MESSAGE);
							}
							myView.update();
							dispose();
							
						} else {
							JOptionPane.showMessageDialog(null, "W�hlen Sie einen anderen Termin aus!", " ",
									JOptionPane.INFORMATION_MESSAGE);
						}

					} catch (Exception a) {
						JOptionPane.showMessageDialog(null,
								"Die Liste konnte nicht �bergeben werden. - Methode ActionPerformed (btnBest�tigen, TerminLoeschen)",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});

		setVisible(true);
	}

}
