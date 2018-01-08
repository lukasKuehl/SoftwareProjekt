package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import data.Mitarbeiter;
import model.Einsatzplanmodel;
import controller.EinsatzplanController;
import java.awt.Color;

/**
 * @author Darius Panteli
 * @info Die Klasse KasseBenutzerrolleView dient dazu, dem Nutzer eine
 *       graphische Obefläche anzubieten, um die Rechte eines Nutzers ändern zu
 *       können
 */

    class KasseBenutzerrolleView extends JFrame {
	private JComboBox<String> cbMitarbeiter;
	private JButton btnBestaetigen;
	private JLabel lblBenutzerZuweisen, lblMitarbeiterAuswaehlen;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;
	private WindowAdapter windowListener;
	
	
	public KasseBenutzerrolleView(Einsatzplanmodel myModel, Einsatzplanview myView,
			EinsatzplanController myController) {
		getContentPane().setBackground(Color.WHITE);
		this.myView = myView;
		this.myModel = myModel;
		this.myController = myController;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Benutzerrolle Zuweisen");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 640, 480);
		getContentPane().setLayout(null);
		
		//Falls der Benutzer das Fenster über den "X" Button schließen sollte, wird die vorherhige View wieder
		//geöffnet
		windowListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				myView.update();
				dispose();
			}
		};
		addWindowListener(windowListener);
		
		lblBenutzerZuweisen = new JLabel("Benutzer zuweisen");
		lblBenutzerZuweisen.setFont(new Font("Verdana", Font.BOLD, 30));
		lblBenutzerZuweisen.setBounds(64, 71, 364, 73);
		getContentPane().add(lblBenutzerZuweisen);

		lblMitarbeiterAuswaehlen = new JLabel("Mitarbeiter ausw\u00E4hlen:");
		lblMitarbeiterAuswaehlen.setFont(new Font("Verdana", Font.BOLD, 18));
		lblMitarbeiterAuswaehlen.setBounds(64, 192, 249, 39);
		getContentPane().add(lblMitarbeiterAuswaehlen);

		cbMitarbeiter = new JComboBox<String>();
		cbMitarbeiter.setBounds(360, 204, 128, 20);
		LinkedList<Mitarbeiter> alleMitarbeiter = this.myModel.getAlleMitarbeiter();

		for (Mitarbeiter mitarbeiter : alleMitarbeiter) {
			cbMitarbeiter.addItem(mitarbeiter.getBenutzername());
		}
		getContentPane().add(cbMitarbeiter);

		btnBestaetigen = new JButton("Best\u00E4tigen");
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Sind Sie sicher?", "Warnung",
						JOptionPane.YES_NO_OPTION);
				//Wenn der Benutzer auf "Ja"klickt, wird die ausgewählte Person ausder ComboBox übergeben.
				//Im Controller selbst wird dann überprüft welche Art von Benutzerrolle die ausgewählte
				//Person hat und dementsprechend geändert
				if (confirmed == JOptionPane.YES_OPTION) {
					if (myController.benutzerRechteWechsel(gibAusgewaehltenMitarbeiter())) {
						dispose();
					} else {

					}

				}
			}
		});
		btnBestaetigen.setBounds(389, 264, 99, 23);
		getContentPane().add(btnBestaetigen);
		setVisible(true);

	}

	protected String gibAusgewaehltenMitarbeiter() {
		return cbMitarbeiter.getSelectedItem().toString();
	}
}
