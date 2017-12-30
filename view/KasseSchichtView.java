package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;

import controller.EinsatzplanController;
import data.Mitarbeiter;
import data.Schicht;
import data.Wochenplan;
import model.Einsatzplanmodel;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

//Klassenbeschreibung fehlt!

//Kommentare innerhalb der Methoden fehlen!

//Autoren der einzelnen Methoden fehlen!

//Hilfsklassen sind nicht public!
public class KasseSchichtView extends JFrame {

	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	
	//Umlaute fehlen!
	private JLabel lblBitteWochenplanAuswhlen, lblBitteDenTag, lblBitteDieSchicht, lblBitteDenMitarbeiter = null;
	
	private JComboBox cbTage, cbWochenplan, cbMitarbeiter, cbSchicht = null;
	private JButton btnPlanBestaetigen, btnTagAuswahl, btnSchichtAuswahl, btnMitarbeiterBestaetigen = null;

	//Siehe AnmeldungView!
	/**
	 * Create the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KasseSchichtView kasseSchichtView = new KasseSchichtView(new Einsatzplanmodel(), null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public KasseSchichtView(Einsatzplanmodel myModel, Einsatzplanview myView, EinsatzplanController myController) {
		this.myView = myView;
		this.myModel = myModel;
		this.myController = myController;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Schicht bearbeiten");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1538, 864);
		getContentPane().setLayout(null);
		setVisible(true);

		lblBitteWochenplanAuswhlen = new JLabel("Bitte Wochenplan ausw\u00E4hlen:");
		lblBitteWochenplanAuswhlen.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBitteWochenplanAuswhlen.setBounds(22, 152, 243, 16);
		getContentPane().add(lblBitteWochenplanAuswhlen);

		//Siehe KasseBenutzerrolleView!
		cbWochenplan = new JComboBox();
		cbWochenplan.setBounds(32, 181, 233, 22);
		LinkedList<Wochenplan> alleWochenplaene = this.myModel.getWochenplaene();
		alleWochenplaene.forEach((temp) -> { 
			cbWochenplan.addItem("KW " +temp.getWpnr()); 
			});
		getContentPane().add(cbWochenplan);
		//
		
		//Bestätigen Button ohne Funktion und ohne Namen :)
		btnPlanBestaetigen = new JButton("New button");
		btnPlanBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbTage.setEnabled(true);
			}
		});
		btnPlanBestaetigen.setBounds(168, 301, 97, 25);
		getContentPane().add(btnPlanBestaetigen);

		lblBitteDenTag = new JLabel("Bitte den Tag ausw\u00E4hlen:");
		lblBitteDenTag.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBitteDenTag.setBounds(22, 334, 243, 16);
		getContentPane().add(lblBitteDenTag);

		//siehe oben
		cbTage = new JComboBox();
		cbTage.setBounds(22, 383, 243, 22);
		cbTage.addItem("Montag");
		cbTage.addItem("Dienstag");
		cbTage.addItem("Mittwoch");
		cbTage.addItem("Donnerstag");
		cbTage.addItem("Freitag");
		cbTage.addItem("Samstag");
		getContentPane().add(cbTage);
		cbTage.setEnabled(false);

		//siehe oben!
		btnTagAuswahl = new JButton("New button");
		btnTagAuswahl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbSchicht.setEnabled(true);
				System.out.println(gibWochenplan());
			}
		});
		btnTagAuswahl.setBounds(168, 418, 97, 25);
		getContentPane().add(btnTagAuswahl);
		//
		
		lblBitteDieSchicht = new JLabel("Bitte die Schicht ausw\u00E4hlen:");
		lblBitteDieSchicht.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBitteDieSchicht.setBounds(22, 454, 243, 16);
		getContentPane().add(lblBitteDieSchicht);

		//Siehe oben!
		cbSchicht = new JComboBox();
		cbSchicht.setBounds(22, 503, 243, 22);
		LinkedList<Schicht> alleSchichten = this.myModel.getSchichten();
		alleSchichten.forEach((temp) -> { 
			cbSchicht.addItem("Schicht " +temp.getSchichtnr()); 
			});
		getContentPane().add(cbSchicht);
		cbSchicht.setEnabled(false);
		//
		
		//Siehe oben!
		btnSchichtAuswahl = new JButton("New button");
		btnSchichtAuswahl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbMitarbeiter.setEnabled(true);
				btnMitarbeiterBestaetigen.setEnabled(true);
			}
		});
		btnSchichtAuswahl.setBounds(168, 647, 97, 25);
		getContentPane().add(btnSchichtAuswahl);
		//
		
		lblBitteDenMitarbeiter = new JLabel("Bitte den Mitarbeiter ausw\u00E4hlen:");
		lblBitteDenMitarbeiter.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBitteDenMitarbeiter.setBounds(644, 153, 299, 16);
		getContentPane().add(lblBitteDenMitarbeiter);

		//Siehe oben!
		cbMitarbeiter = new JComboBox();
		cbMitarbeiter.setBounds(644, 215, 368, 22);
		LinkedList<Mitarbeiter> alleMitarbeiter = this.myModel.getAlleMitarbeiter();
		alleMitarbeiter.forEach((temp) -> { 
			cbMitarbeiter.addItem(temp.getBenutzername()); 
			});
		getContentPane().add(cbMitarbeiter);
		cbMitarbeiter.setEnabled(false);
		//
		
		btnMitarbeiterBestaetigen = new JButton("best\u00E4tigen");
		btnMitarbeiterBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Sind Sie sicher?", "Warnung",
						JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
						
						//welche Methode soll hier ausgeführt werden?
						
						// --> EinsatzplanController.fülleSchicht(int schichtNr, String[] mitarbeiter)	
						
					dispose();
					} else {
						//Fehlermeldung überflüssig, da der Benutzer abgebrochen hat
						
						//JLabel Fehlermeldung erstellen (siehe)
						//lblFehlermeldung.setText("Fehler beim Erstellen des Wochenplans. Bitte überprüfen Sie Ihre Eingaben.");

					}

				}
			
		});
		btnMitarbeiterBestaetigen.setBounds(846, 714, 97, 25);
		getContentPane().add(btnMitarbeiterBestaetigen);
		btnMitarbeiterBestaetigen.setEnabled(false);

	}
	
	public String gibMitarbeiter() {
		return cbMitarbeiter.getSelectedItem().toString();
	}
	
	public String gibSchicht() {
		//Sinnvolleres Trennungskriterium als ein Leerzeichen bestimmen (z.B. , | -)
		String[] teile = cbSchicht.getSelectedItem().toString().split(" ");
		return teile[1];
	}
	
	//Methodenname irreführend, besser gibTag/gibAusgewaehltenTag
	public String gibTage() {
		return cbTage.getSelectedItem().toString();
	}
	
	public String gibWochenplan() {
		//Siehe oben!
		String[] teile = cbWochenplan.getSelectedItem().toString().split(" ");
		return teile[1];
	}	
}
