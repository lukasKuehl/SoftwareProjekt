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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * @author Darius Panteli
 * @info Die Klasse KasseSchichtView dient dazu, dem Nutzer eine graphische
 *       Obefl�che anzubieten, um Mitarbeiter einem jeweils ausgew�hlten
 *       Wochenplan einer Schicht hinzu zuf�gen
 */


	class KasseSchichtView extends JFrame {
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;

	private JLabel lblBitteWochenplanAuswaehlen, lblBitteDenTag, lblBitteDieSchicht, lblBitteDenMitarbeiter,
			lblSchichtBearbeiten = null;

	private JComboBox cbTage, cbWochenplan, cbMitarbeiter, cbSchicht = null;
	private JButton btnPlanBestaetigen, btnTagAuswahl, btnSchichtAuswahl, btnMitarbeiterBestaetigen, btnReset = null;
	private WindowListener windowListener;


	protected KasseSchichtView(Einsatzplanmodel myModel, Einsatzplanview myView, EinsatzplanController myController) {
		getContentPane().setBackground(Color.WHITE);
		this.myView = myView;
		this.myModel = myModel;
		this.myController = myController;
		initialize();
	}


	private void initialize() {
		setTitle("Schicht bearbeiten");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
		setVisible(true);
		
		//Falls der Benutzer das Fenster �ber den "X" Button schlie�en sollte, wird eine neue KasseWochenView
		//ge�ffnet
		windowListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				myView.update();
				dispose();
			}
		};
		addWindowListener(windowListener);

		lblBitteWochenplanAuswaehlen = new JLabel("Bitte Wochenplan ausw\u00E4hlen:");
		lblBitteWochenplanAuswaehlen.setFont(new Font("Verdana", Font.BOLD, 15));
		lblBitteWochenplanAuswaehlen.setBounds(22, 152, 243, 16);
		getContentPane().add(lblBitteWochenplanAuswaehlen);

		// Alle aktuell verf�gbaren bzw. erstellten und in der Datenbank hinterlegten Wochenpl�ne
		//werden geladen und in die ComboBox gef�llt
		cbWochenplan = new JComboBox();
		cbWochenplan.setBounds(32, 181, 233, 22);
		LinkedList<Wochenplan> alleWochenplaene = this.myModel.getWochenplaene();
		alleWochenplaene.forEach((temp) -> {
			cbWochenplan.addItem("KW " + temp.getWpnr());
		});
		getContentPane().add(cbWochenplan);
		
		
		btnPlanBestaetigen = new JButton("best\u00E4tigen");
		btnPlanBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnPlanBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Damit die Tage aus dem ausgew�hlten Wochenplans geladen werden, �bergeben wir den ausgew�hlten Wochenplan
				//und �bergeben bei der Abfrage der Tagesliste auch den jeweiligen Wochenplan
				Wochenplan wochenplan = myModel.getWochenplan(Integer.valueOf(cbWochenplan.getSelectedItem().toString().split(" ")[1]));
				String wochenplanBez = "KW" + wochenplan.getWpnr();
				ArrayList<String> tagesliste = myController.getTage(wochenplanBez);
				//Falls es bereits schon dazu gekommen sein sollte, dass man bereits mehrere Sachen ausgew�hlt hat,
				//aber man die Auswahl des Wochenplans �ndern m�chte, werden nach dem Klicken des Buttons
				//alle anderen Felder zur�ckgesetzt und deaktiviert ( auch wenn diese nat�rlich schon deaktiviert waren)
				cbTage.removeAllItems();
				cbMitarbeiter.removeAllItems();
				cbSchicht.removeAllItems();
				cbMitarbeiter.removeAllItems();
				btnMitarbeiterBestaetigen.setEnabled(false);
				btnSchichtAuswahl.setEnabled(false);
				//ComboBox wird mit den Tagen gef�llt
				for (int i = 0; i < tagesliste.size(); i++) {
					cbTage.addItem(tagesliste.get(i));
				}

				cbTage.setEnabled(true);
				btnTagAuswahl.setEnabled(true);
			}
		});
		btnPlanBestaetigen.setBounds(93, 240, 125, 25);
		getContentPane().add(btnPlanBestaetigen);

		lblBitteDenTag = new JLabel("Bitte den Tag ausw\u00E4hlen:");
		lblBitteDenTag.setFont(new Font("Verdana", Font.BOLD, 15));
		lblBitteDenTag.setBounds(22, 309, 243, 16);
		getContentPane().add(lblBitteDenTag);

		cbTage = new JComboBox<String>();
		cbTage.setBounds(22, 354, 299, 22);
		getContentPane().add(cbTage);
		cbTage.setEnabled(false);

		btnTagAuswahl = new JButton("best\u00E4tigen");
		btnTagAuswahl.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnTagAuswahl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tag = cbTage.getSelectedItem().toString();
				//Damit die Schicht/en aus dem ausgew�hlten Tag geladen werden, �bergeben wir den vom User ausgew�hlten Tag 
				//und �bergeben bei der Abfrage der Schicht auch den jeweiligen Tag
				//damit in der ComboBox auch nur die Schichten angezeigt werden, die dazu geh�ren
				Wochenplan wochenplan = myModel
						.getWochenplan(Integer.valueOf(cbWochenplan.getSelectedItem().toString().split(" ")[1]));
				String wochenplanBez = "KW" + wochenplan.getWpnr();
				ArrayList<String> schichten = myController.getTagesSchichten(wochenplanBez,tag);

				cbSchicht.removeAllItems();
				//ComboBox wird mit den Schichten
				for (int i = 0; i < schichten.size(); i++) {
					cbSchicht.addItem(schichten.get(i));
				}

				cbSchicht.setEnabled(true);
				btnSchichtAuswahl.setEnabled(true);
				cbMitarbeiter.removeAllItems();
				btnMitarbeiterBestaetigen.setEnabled(false);
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

		cbSchicht = new JComboBox<String>();
		cbSchicht.setBounds(414, 181, 243, 22);
		getContentPane().add(cbSchicht);
		cbSchicht.setEnabled(false);
		
		btnSchichtAuswahl = new JButton("best\u00E4tigen");
		btnSchichtAuswahl.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnSchichtAuswahl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Damit alle verf�gbare Mitarbeiter geladen werden, �bergeben wir die ausgew�hlte Schicht
				//und �bergeben bei der Abfrage der verf�gbaren Mitarbeiter die ausgew�hlte Schicht
				String schichtnr = cbSchicht.getSelectedItem().toString();
				schichtnr = schichtnr.substring(0, Math.min(schichtnr.length(), 5));
				// aktuell wg. Testdaten Wert auf 5 ( da KW10000) 

				int schichtNr = Integer.parseInt(schichtnr);
				ArrayList<String> mitarbeiterliste = myController.getVerfuegbareMitarbeiter(schichtNr);

				cbMitarbeiter.removeAllItems();
				//ComboBox werden mit den verf�gbaren Mitarbeitern bef�llt
				for (int i = 0; i < mitarbeiterliste.size(); i++) {
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


		cbMitarbeiter = new JComboBox<String>();
		cbMitarbeiter.setBounds(414, 354, 243, 22);

		getContentPane().add(cbMitarbeiter);
		cbMitarbeiter.setEnabled(false);

		btnMitarbeiterBestaetigen = new JButton("best\u00E4tigen");
		btnMitarbeiterBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnMitarbeiterBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Sind Sie sicher?", "Warnung",
						JOptionPane.YES_NO_OPTION);
				//Sofern der Benutzer auf Ja klickt, wird der Mitarbeiter in die Schicht eingetragen
				if (confirmed == JOptionPane.YES_OPTION) {

					String schichtnr = cbSchicht.getSelectedItem().toString();
					schichtnr = schichtnr.substring(0, Math.min(schichtnr.length(), 5));
					int schichtNr = Integer.parseInt(schichtnr);
					String[] mitarbeiterArray = new String[] {
							cbMitarbeiter.getSelectedItem().toString().split(" ")[0] };
					//Da am Anfang die �berlegung im Raum stand, mehrere Mitarbeiter gleichzeitig eintragen zu k�nnen
					//wird vom vom Controller ein Array erwartet. Diesen
					//bekommt er auch aber aktuell nur mit einer Person.
					if (myController.f�lleSchicht(Integer.valueOf(schichtnr), mitarbeiterArray)) {
						myView.update();
						dispose();
					}

				} else {
					// Fehlermeldung kommt vom Controller
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
		btnReset.addActionListener(new ActionListener() {
			//Hat einen psychologischen Effekt: Da durch neu Auswahl eines Wochenplans, Tages oder einer Schicht
			//jeweils die darauf folgenden Felder sowieso zur�ck gesetzt/ erneuert werden, braucht man in der Theorie
			//den Reset Button nicht. Falls man aber gerne von vorne Anfangen m�chte, kann man dies auch mit dem Reset
			//Button erledigen
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cbMitarbeiter.removeAllItems();
				cbMitarbeiter.setEnabled(false);
				cbSchicht.removeAllItems();
				cbSchicht.setEnabled(false);
				cbTage.removeAllItems();
				cbTage.setEnabled(false);
				cbWochenplan.setSelectedIndex(0);
				btnMitarbeiterBestaetigen.setEnabled(false);
				btnSchichtAuswahl.setEnabled(false);
				btnTagAuswahl.setEnabled(false);

			}
		});
		getContentPane().add(btnReset);
	}

	protected String gibMitarbeiter() {
		return cbMitarbeiter.getSelectedItem().toString();
	}

	//Da nur die SchichtNr erwartet wird, aber immer "Schicht XY" �bergeben wird,
	//dient das Leerzeichen dazu nur das "XY" zu �bergeben ohne die "Schicht"
	protected String gibSchicht() {
		String[] teile = cbSchicht.getSelectedItem().toString().split(" ");
		return teile[1];
	}
	//Mit gibTage wird der jeweilige Tag aus der ComboBox �bergeben
	protected String gibTage() {
		return cbTage.getSelectedItem().toString();
	}

	//Siehe gibSchicht. Selbes Prinzip
	//Theoretisch �berfl�ssig, da wir am Ende "KW"+ bei der �bergabe �bergeben.
	//Hatte aber zu Fehlermeldungen gef�hrt weshalb wir es so "gel�st" haben
	protected String gibWochenplan() {
		String[] teile = cbWochenplan.getSelectedItem().toString().split(" ");
		return teile[1];
	}
}
