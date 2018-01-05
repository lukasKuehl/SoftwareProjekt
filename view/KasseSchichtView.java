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
import data.Tag;
import data.Wochenplan;
import model.Einsatzplanmodel;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

/**
 * @author Darius Panteli
 * @info Die Klasse KasseSchichtView dient dazu, dem Nutzer eine graphische
 *       Obefläche anzubieten, um Mitarbeiter einem jeweils ausgewählten
 *       Wochenplan einer Schicht hinzuu zu
 */
// Klassenbeschreibung fehlt!

// Kommentare innerhalb der Methoden fehlen!

// Autoren der einzelnen Methoden fehlen!

// Hilfsklassen sind nicht public!
public class KasseSchichtView extends JFrame {
//
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;

	private JLabel lblBitteWochenplanAuswaehlen, lblBitteDenTag, lblBitteDieSchicht, lblBitteDenMitarbeiter,
			lblSchichtBearbeiten = null;

	private JComboBox cbTage, cbWochenplan, cbMitarbeiter, cbSchicht = null;
	private JButton btnPlanBestaetigen, btnTagAuswahl, btnSchichtAuswahl, btnMitarbeiterBestaetigen,btnReset = null;

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

	protected KasseSchichtView(Einsatzplanmodel myModel, Einsatzplanview myView, EinsatzplanController myController) {
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
		setVisible(true);

		lblBitteWochenplanAuswaehlen = new JLabel("Bitte Wochenplan ausw\u00E4hlen:");
		lblBitteWochenplanAuswaehlen.setFont(new Font("Verdana", Font.BOLD, 15));
		lblBitteWochenplanAuswaehlen.setBounds(22, 152, 243, 16);
		getContentPane().add(lblBitteWochenplanAuswaehlen);

		// Siehe KasseBenutzerrolleView!
		cbWochenplan = new JComboBox();
		cbWochenplan.setBounds(32, 181, 233, 22);
		LinkedList<Wochenplan> alleWochenplaene = this.myModel.getWochenplaene();
		alleWochenplaene.forEach((temp) -> {
			cbWochenplan.addItem("KW " + temp.getWpnr());
		});
		getContentPane().add(cbWochenplan);
		//

		// Bestätigen Funktion Ohne Namen und Funktion (Reset Funktion fehlt)
		btnPlanBestaetigen = new JButton("best\u00E4tigen");
		btnPlanBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnPlanBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Wochenplan wochenplan = myModel.getWochenplan(Integer.valueOf(cbWochenplan.getSelectedItem().toString().split(" ")[1]));
				//LinkedList<Tag> tage = wochenplan.getLinkedListTage();
			
				String wochenplanBez = "KW"+wochenplan.getWpnr();
				ArrayList<String> tagesliste = myController.getTage(wochenplanBez);
				
				cbTage.removeAllItems();
				
				for(int i = 0;i<tagesliste.size();i++)
				{
					cbTage.addItem(tagesliste.get(i));
				}
				
			/*	for(Tag tag : tage){
					cbTage.addItem(tag.getTbez());
				} */
				cbTage.setEnabled(true);
				btnTagAuswahl.setEnabled(true);
			}
		});
		btnPlanBestaetigen.setBounds(93,240, 125, 25);
		getContentPane().add(btnPlanBestaetigen);

		lblBitteDenTag = new JLabel("Bitte den Tag ausw\u00E4hlen:");
		lblBitteDenTag.setFont(new Font("Verdana", Font.BOLD, 15));
		lblBitteDenTag.setBounds(22, 309, 243, 16);
		getContentPane().add(lblBitteDenTag);

		// siehe oben (Füllen mit getTage)
		cbTage = new JComboBox();
		cbTage.setBounds(22, 354, 299, 22);
		getContentPane().add(cbTage);
		cbTage.setEnabled(false);

		// siehe oben!
		btnTagAuswahl = new JButton("best\u00E4tigen");
		btnTagAuswahl.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnTagAuswahl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tag =  cbTage.getSelectedItem().toString();
				
				Wochenplan wochenplan =  myModel.getWochenplan(Integer.valueOf(cbWochenplan.getSelectedItem().toString().split(" ")[1]));
				String wochenplanBez = "KW"+wochenplan.getWpnr();
				ArrayList<String> schichten = myController.getTagesSchichten(wochenplanBez, tag);
				
				cbSchicht.removeAllItems();
				
				for(int i = 0;i<schichten.size();i++)
				{
					cbSchicht.addItem(schichten.get(i));
				}
				
				cbSchicht.setEnabled(true);
				btnSchichtAuswahl.setEnabled(true);
			}
		});
		btnTagAuswahl.setBounds(93, 389, 125, 25);
		getContentPane().add(btnTagAuswahl);
		btnTagAuswahl.setEnabled(false);
		//

		lblBitteDieSchicht = new JLabel("Bitte die Schicht ausw\u00E4hlen:");
		lblBitteDieSchicht.setFont(new Font("Verdana", Font.BOLD, 15));
		lblBitteDieSchicht.setBounds(404, 152, 243, 16);
		getContentPane().add(lblBitteDieSchicht);

		// Siehe oben!
		cbSchicht = new JComboBox();
		cbSchicht.setBounds(414, 181, 243, 22);
	/*
		LinkedList<Schicht> alleSchichten = this.myModel.getSchichten();
		alleSchichten.forEach((temp) -> {
			cbSchicht.addItem("Schicht " + temp.getSchichtnr());
		});
		*/
		getContentPane().add(cbSchicht);
		cbSchicht.setEnabled(false);
		//

		// Siehe oben!
		btnSchichtAuswahl = new JButton("best\u00E4tigen");
		btnSchichtAuswahl.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnSchichtAuswahl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String schichtnr = cbSchicht.getSelectedItem().toString();
				schichtnr = schichtnr.substring(0, Math.min(schichtnr.length(), 5));
				//aktuell wg. Testdaten Wert auf 5
				
				int schichtNr = Integer.parseInt(schichtnr);
				ArrayList<String> mitarbeiterliste = myController.getVerfuegbareMitarbeiter(schichtNr);
				
				cbMitarbeiter.removeAllItems();
				
				for(int i = 0;i<mitarbeiterliste.size();i++)
				{
					cbMitarbeiter.addItem(mitarbeiterliste.get(i));
				}
				
				cbMitarbeiter.setEnabled(true);
				btnMitarbeiterBestaetigen.setEnabled(true);
			}
		});
		btnSchichtAuswahl.setBounds(466, 240, 125, 25);
		getContentPane().add(btnSchichtAuswahl);
		btnSchichtAuswahl.setEnabled(false);
		//

		lblBitteDenMitarbeiter = new JLabel("Bitte den Mitarbeiter ausw\u00E4hlen:");
		lblBitteDenMitarbeiter.setFont(new Font("Verdana", Font.BOLD, 15));
		lblBitteDenMitarbeiter.setBounds(404, 309, 299, 16);
		getContentPane().add(lblBitteDenMitarbeiter);

		// Siehe oben!
		cbMitarbeiter = new JComboBox();
		cbMitarbeiter.setBounds(414, 354, 243, 22);
	/*	LinkedList<Mitarbeiter> alleMitarbeiter = this.myModel.getAlleMitarbeiter();//ändern zu verfügbare mitarbeiter
		alleMitarbeiter.forEach((temp) -> {
			cbMitarbeiter.addItem(temp.getBenutzername());
		});*/
		getContentPane().add(cbMitarbeiter);
		cbMitarbeiter.setEnabled(false);
		//

		btnMitarbeiterBestaetigen = new JButton("best\u00E4tigen");
		btnMitarbeiterBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnMitarbeiterBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Sind Sie sicher?", "Warnung",
						JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					
					String schichtnr = cbSchicht.getSelectedItem().toString();
					schichtnr = schichtnr.substring(0, Math.min(schichtnr.length(), 5));
					int schichtNr = Integer.parseInt(schichtnr);
					String[] mitarbeiterArray = new String[] {cbMitarbeiter.getSelectedItem().toString().split(" ")[0]};
					
					if (myController.fülleSchicht(Integer.valueOf(schichtnr), mitarbeiterArray)){
						dispose();
					}
					// welche Methode soll hier ausgeführt werden?

					// --> EinsatzplanController.fülleSchicht(int schichtNr,
					// String[] mitarbeiter)

					
				} else {
					// Fehlermeldung überflüssig, da der Benutzer abgebrochen
					// hat

					// JLabel Fehlermeldung erstellen (siehe)
					// lblFehlermeldung.setText("Fehler beim Erstellen des
					// Wochenplans. Bitte überprüfen Sie Ihre Eingaben.");

				}

			}

		});
		btnMitarbeiterBestaetigen.setBounds(466, 389, 115, 25);
		getContentPane().add(btnMitarbeiterBestaetigen);
		btnMitarbeiterBestaetigen.setEnabled(false);

		lblSchichtBearbeiten = new JLabel("Schicht bearbeiten");
		lblSchichtBearbeiten.setFont(new Font("Verdana", Font.BOLD, 30));
		lblSchichtBearbeiten.setBounds(22, 13, 315, 58);
		getContentPane().add(lblSchichtBearbeiten);
		
		btnReset = new JButton("Reset");
		btnReset.setBounds(305, 471, 97, 25);
		getContentPane().add(btnReset);
	}

	protected String gibMitarbeiter() {
		return cbMitarbeiter.getSelectedItem().toString();
	}

	protected String gibSchicht() {
		// Sinnvolleres Trennungskriterium als ein Leerzeichen bestimmen (z.B. ,
		// | -)
		String[] teile = cbSchicht.getSelectedItem().toString().split(" ");
		return teile[1];
	}

	// Methodenname irreführend, besser gibTag/gibAusgewaehltenTag
	protected String gibTage() {
		return cbTage.getSelectedItem().toString();
	}

	protected String gibWochenplan() {
		// Siehe oben!
		String[] teile = cbWochenplan.getSelectedItem().toString().split(" ");
		return teile[1];
	}
}
