package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import data.Mitarbeiter;
import model.Einsatzplanmodel;
import controller.EinsatzplanController;

class KasseBenutzerrolleView extends JFrame {
	private JComboBox cbMitarbeiter;
	private JButton btnBestaetigen;
	private JLabel lblBenutzerZuweisen, lblMitarbeiterAuswaehlen;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController=null;

	// private JFrame frame;

	/**
	 * Launch the application.
	 * 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KasseBenutzerrolleView kasseBenutzerrolleView = new KasseBenutzerrolleView(new Einsatzplanmodel(),
							null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});}
	/**
	 * Create the application.
	 */
	public KasseBenutzerrolleView(Einsatzplanmodel myModel, Einsatzplanview myView) {
		this.myView = myView;
		this.myModel = myModel;
		this.myController=myController;

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
		setBounds(100, 100, 1538, 864);
		getContentPane().setLayout(null);

		lblBenutzerZuweisen = new JLabel("Benutzer zuweisen");
		lblBenutzerZuweisen.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblBenutzerZuweisen.setBounds(83, 124, 301, 73);
		getContentPane().add(lblBenutzerZuweisen);

		lblMitarbeiterAuswaehlen = new JLabel("Mitarbeiter ausw\u00E4hlen:");
		lblMitarbeiterAuswaehlen.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMitarbeiterAuswaehlen.setBounds(83, 262, 217, 39);
		getContentPane().add(lblMitarbeiterAuswaehlen);

		cbMitarbeiter = new JComboBox();
		cbMitarbeiter.setBounds(381, 274, 128, 20);
		LinkedList<Mitarbeiter> alleMitarbeiter = this.myModel.getAlleMitarbeiter();
		alleMitarbeiter.forEach((temp) -> { 
			cbMitarbeiter.addItem(temp.getBenutzername()); 
			});
		getContentPane().add(cbMitarbeiter);

		btnBestaetigen = new JButton("Best\u00E4tigen");
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Sind Sie sicher?", "Warnung",
						JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
				myController.benutzerRechteWechsel(gibAusgewaehltenMitarbeiter());
				dispose();
				}
			}
		});
		btnBestaetigen.setBounds(420, 390, 89, 23);
		getContentPane().add(btnBestaetigen);
		setVisible(true);

		/*
		 * frame = new JFrame(); frame.setBounds(100, 100, 1538,864);
		 * frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 */
	}

	protected String gibAusgewaehltenMitarbeiter(){
		return cbMitarbeiter.getSelectedItem().toString();
	}
	
}


