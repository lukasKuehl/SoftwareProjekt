package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//Klassenbeschreibung fehlt!

//Kommentare innerhalb der Methoden fehlen!

//Autoren der einzelnen Methoden fehlen!

//Hilfsklassen sind nicht public!
public class KasseWocheErstellenView extends JFrame {
	private JTextField txtOeffnungAnfangA, txtOeffnungAnfangB, txtOeffnungEndeA, txtOeffnungEndeB, txtHauptAnfangA,
			txtHauptAnfangB, txtHauptEndeA, txtHauptEndeB;
	private JComboBox cbMinTechInfo, cbMinHausInfo, cbMinHausKasse, cbExtraKassen, cbKW;
	private JRadioButton rdbtnStandardeinstellungen;
	private JButton btnBesttigen;
	private JLabel lblWochenplanErstellen, lblKalenderwocheKw, lblOeffnungszeiten, doppelpunkt, lblBis, doppelpunkt_1,
			lblHauptzeiten, doppelpunkt_2, lblBis_1, doppelpunkt_3, lblMindestbesetzung,
			lblMindestbesetzungFrLebensmittel, lblMindestbesetzungFrLebensmittel_1, lblKassen,
			//Umlaute fehlen!
			lblAnzahlZustzlicherMitarbeiter, lblUhr, lblUhr_1, lblUhr_2, lblUhr_3, lblFehlermeldung;
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;

	
	//Siehe AnmeldungView!
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KasseWocheErstellenView kasseWocheErstellenView = new KasseWocheErstellenView(
							new Einsatzplanmodel(), null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//
	
	public KasseWocheErstellenView(Einsatzplanmodel myModel, Einsatzplanview myView, EinsatzplanController myController) {
		this.myController = myController;
		this.myModel = myModel;
		this.myView = myView;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setTitle("Wochenplan Erstellen");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1538, 864);
		getContentPane().setLayout(null);

		lblWochenplanErstellen = new JLabel("Wochenplan erstellen");
		lblWochenplanErstellen.setFont(new Font("Verdana", Font.BOLD, 30));
		lblWochenplanErstellen.setBounds(39, 127, 370, 52);
		getContentPane().add(lblWochenplanErstellen);

		lblKalenderwocheKw = new JLabel("Kalenderwoche KW:");
		lblKalenderwocheKw.setFont(new Font("Verdana", Font.BOLD, 15));
		lblKalenderwocheKw.setBounds(39, 358, 160, 16);
		getContentPane().add(lblKalenderwocheKw);

		
		//siehe vorherige Klassen!
		cbKW = new JComboBox();
		cbKW.setBounds(211, 356, 50, 22);
		getContentPane().add(cbKW);
		String[] werte1 = setField(52, 1);
		setComboBox(cbKW, werte1);
		//
		
		lblOeffnungszeiten = new JLabel("\u00D6ffnungszeiten:");
		lblOeffnungszeiten.setFont(new Font("Verdana", Font.BOLD, 15));
		lblOeffnungszeiten.setBounds(39, 441, 160, 16);
		getContentPane().add(lblOeffnungszeiten);

		txtOeffnungAnfangA = new JTextField();
		
		//Solltest du erklären können falls dich wer fragt!!!
		txtOeffnungAnfangA.addKeyListener(new KeyAdapter() {
			@Override

			public void keyTyped(KeyEvent arg0) {
				char vchar = arg0.getKeyChar();
				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE)) {
					arg0.consume();
				}

			}
		});
		txtOeffnungAnfangA.setBounds(185, 439, 39, 22);
		getContentPane().add(txtOeffnungAnfangA);
		txtOeffnungAnfangA.setColumns(2);

		doppelpunkt = new JLabel(":");
		doppelpunkt.setBounds(236, 442, 39, 16);
		getContentPane().add(doppelpunkt);

		txtOeffnungAnfangB = new JTextField();
		txtOeffnungAnfangB.addKeyListener(new KeyAdapter() {
			@Override
			//siehe oben!
			public void keyTyped(KeyEvent arg0) {
				char vchar = arg0.getKeyChar();
				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE)) {
					arg0.consume();
				}

			}
		});
		txtOeffnungAnfangB.setBounds(253, 439, 39, 22);
		getContentPane().add(txtOeffnungAnfangB);
		txtOeffnungAnfangB.setColumns(2);

		lblBis = new JLabel("     bis");
		lblBis.setFont(new Font("Verdana", Font.BOLD, 13));
		lblBis.setBounds(211, 474, 56, 16);
		getContentPane().add(lblBis);

		txtOeffnungEndeA = new JTextField();
		txtOeffnungEndeA.addKeyListener(new KeyAdapter() {
			@Override
			//siehe oben!
			public void keyTyped(KeyEvent arg0) {
				char vchar = arg0.getKeyChar();
				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE)) {
					arg0.consume();
				}

			}
		});
		txtOeffnungEndeA.setBounds(185, 500, 39, 22);
		getContentPane().add(txtOeffnungEndeA);
		txtOeffnungEndeA.setColumns(2);

		doppelpunkt_1 = new JLabel(":");
		doppelpunkt_1.setBounds(236, 503, 56, 16);
		getContentPane().add(doppelpunkt_1);

		txtOeffnungEndeB = new JTextField();
		txtOeffnungEndeB.addKeyListener(new KeyAdapter() {
			@Override
			//siehe Oben, für die späteren KeyEvents ebenfalls :)
			public void keyTyped(KeyEvent arg0) {
				char vchar = arg0.getKeyChar();
				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE)) {
					arg0.consume();
				}

			}
		});
		txtOeffnungEndeB.setBounds(253, 500, 39, 22);
		getContentPane().add(txtOeffnungEndeB);
		txtOeffnungEndeB.setColumns(2);

		lblHauptzeiten = new JLabel("Hauptzeiten:");
		lblHauptzeiten.setFont(new Font("Verdana", Font.BOLD, 15));
		lblHauptzeiten.setBounds(39, 574, 160, 16);
		getContentPane().add(lblHauptzeiten);

		txtHauptAnfangA = new JTextField();
		txtHauptAnfangA.addKeyListener(new KeyAdapter() {
			@Override

			public void keyTyped(KeyEvent arg0) {
				char vchar = arg0.getKeyChar();
				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE)) {
					arg0.consume();
				}

			}
		});
		txtHauptAnfangA.setBounds(185, 572, 39, 22);
		getContentPane().add(txtHauptAnfangA);
		txtHauptAnfangA.setColumns(2);

		doppelpunkt_2 = new JLabel(":");
		doppelpunkt_2.setBounds(236, 575, 14, 16);
		getContentPane().add(doppelpunkt_2);

		txtHauptAnfangB = new JTextField();
		txtHauptAnfangB.addKeyListener(new KeyAdapter() {
			@Override

			public void keyTyped(KeyEvent arg0) {
				char vchar = arg0.getKeyChar();
				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE)) {
					arg0.consume();
				}

			}
		});
		txtHauptAnfangB.setBounds(253, 572, 39, 22);
		getContentPane().add(txtHauptAnfangB);
		txtHauptAnfangB.setColumns(2);

		lblBis_1 = new JLabel("bis");
		lblBis_1.setFont(new Font("Verdana", Font.BOLD, 13));
		lblBis_1.setBounds(222, 609, 56, 16);
		getContentPane().add(lblBis_1);

		txtHauptEndeA = new JTextField();
		txtHauptEndeA.addKeyListener(new KeyAdapter() {
			@Override

			public void keyTyped(KeyEvent arg0) {
				char vchar = arg0.getKeyChar();
				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE)) {
					arg0.consume();
				}

			}
		});
		txtHauptEndeA.setBounds(185, 638, 39, 22);
		getContentPane().add(txtHauptEndeA);
		txtHauptEndeA.setColumns(2);

		doppelpunkt_3 = new JLabel(":");
		doppelpunkt_3.setBounds(236, 640, 5, 16);
		getContentPane().add(doppelpunkt_3);

		txtHauptEndeB = new JTextField();
		txtHauptEndeB.addKeyListener(new KeyAdapter() {
			@Override

			public void keyTyped(KeyEvent arg0) {
				char vchar = arg0.getKeyChar();
				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE)) {
					arg0.consume();
				}

			}
		});
		txtHauptEndeB.setBounds(253, 638, 39, 22);
		getContentPane().add(txtHauptEndeB);
		txtHauptEndeB.setColumns(2);

		lblMindestbesetzung = new JLabel("Mindestbesetzung f\u00FCr Technikwarenaus: Info");
		lblMindestbesetzung.setFont(new Font("Verdana", Font.BOLD, 15));
		lblMindestbesetzung.setBounds(487, 431, 509, 16);
		getContentPane().add(lblMindestbesetzung);

		lblMindestbesetzungFrLebensmittel = new JLabel(
				"Mindestbesetzung f\u00FCr Lebensmittel- / Haushaltswarenhaus: Info");
		lblMindestbesetzungFrLebensmittel.setFont(new Font("Verdana", Font.BOLD, 15));
		lblMindestbesetzungFrLebensmittel.setBounds(487, 474, 509, 16);
		getContentPane().add(lblMindestbesetzungFrLebensmittel);

		lblMindestbesetzungFrLebensmittel_1 = new JLabel(
				"Mindestbesetzung f\u00FCr Lebensmittel- / Haushaltswarenhaus Kasse:");
		lblMindestbesetzungFrLebensmittel_1.setFont(new Font("Verdana", Font.BOLD, 15));
		lblMindestbesetzungFrLebensmittel_1.setBounds(487, 524, 509, 16);
		getContentPane().add(lblMindestbesetzungFrLebensmittel_1);

		//siehe vorherige Klassen!
		cbMinTechInfo = new JComboBox();
		cbMinTechInfo.setBounds(1040, 428, 50, 22);
		getContentPane().add(cbMinTechInfo);
		String[] werte2 = setField(10, 0);
		setComboBox(cbMinTechInfo, werte2);
		
		
		cbMinHausInfo = new JComboBox();
		cbMinHausInfo.setBounds(1040, 471, 50, 22);
		getContentPane().add(cbMinHausInfo);
		String[] werte3 = setField(10, 0);
		setComboBox(cbMinHausInfo, werte3);

		cbMinHausKasse = new JComboBox();
		cbMinHausKasse.setBounds(1040, 522, 50, 22);
		getContentPane().add(cbMinHausKasse);
		String[] werte4 = setField(10, 0);
		setComboBox(cbMinHausKasse, werte4);
		//
		
		lblKassen = new JLabel("Kassen:");
		lblKassen.setFont(new Font("Verdana", Font.BOLD, 15));
		lblKassen.setBounds(487, 626, 509, 16);
		getContentPane().add(lblKassen);

		lblFehlermeldung = new JLabel("");
		lblFehlermeldung.setFont(new Font("Verdana", Font.BOLD, 15));
		lblFehlermeldung.setBounds(487, 626, 509, 16);
		getContentPane().add(lblFehlermeldung);
		
		// Farbe rot ändern, Position anpassen
		//Geht über HTML(JLabel.setText("<html> Fehlermeldung: <font color='red'>Hier den Fehlermeldungstext rein</font></html>"))
				
		//siehe vorherige Klassen!
		cbExtraKassen = new JComboBox();
		cbExtraKassen.setBounds(1040, 624, 50, 22);
		getContentPane().add(cbExtraKassen);
		String[] werte5 = setField(10, 0);
		setComboBox(cbExtraKassen, werte5);
		//
		
		btnBesttigen = new JButton("best\u00E4tigen");
		btnBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Sind Sie sicher?", "Warnung",
						JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					if (myController.erstelleWochenplanCustom(myView.getUsername(), gibWochenplanbezeichnung(),
							gibOeffnungszeiten(), gibBesetzung())) {
						myView.update();
						dispose();
					} else {
						//Benutzer hat von sich aus abgebrochen --> Keine Fehlermeldung notwendig
						lblFehlermeldung.setText("Fehler beim Erstellen des Wochenplans. Bitte überprüfen Sie Ihre Eingaben.");

					}

				}
			}
		});
		btnBesttigen.setBounds(1246, 793, 97, 25);
		getContentPane().add(btnBesttigen);

		ButtonGroup gruppe = new ButtonGroup();
		JRadioButton rdbtnBenutzerdefiniert = new JRadioButton("Benutzerdefiniert");
		rdbtnStandardeinstellungen = new JRadioButton("Standardeinstellungen");
		gruppe.add(rdbtnBenutzerdefiniert);
		gruppe.add(rdbtnStandardeinstellungen);
		rdbtnBenutzerdefiniert.setSelected(true);
		rdbtnBenutzerdefiniert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				txtOeffnungAnfangA.setEnabled(true);
				txtOeffnungAnfangB.setEnabled(true);
				txtOeffnungEndeA.setEnabled(true);
				txtOeffnungEndeB.setEnabled(true);
				txtHauptAnfangA.setEnabled(true);
				txtHauptAnfangB.setEnabled(true);
				txtHauptEndeA.setEnabled(true);
				txtHauptEndeB.setEnabled(true);
				cbMinTechInfo.setEnabled(true);
				cbMinHausInfo.setEnabled(true);
				cbMinHausKasse.setEnabled(true);
				cbExtraKassen.setEnabled(true);
			}
		});
		rdbtnBenutzerdefiniert.setBounds(211, 399, 127, 25);
		getContentPane().add(rdbtnBenutzerdefiniert);

		rdbtnStandardeinstellungen.setBounds(39, 399, 160, 25);
		rdbtnStandardeinstellungen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				txtOeffnungAnfangA.setEnabled(false);
				txtOeffnungAnfangB.setEnabled(false);
				txtOeffnungEndeA.setEnabled(false);
				txtOeffnungEndeB.setEnabled(false);
				txtHauptAnfangA.setEnabled(false);
				txtHauptAnfangB.setEnabled(false);
				txtHauptEndeA.setEnabled(false);
				txtHauptEndeB.setEnabled(false);

				txtOeffnungAnfangA.setText("");
				txtOeffnungAnfangB.setText("");
				txtOeffnungEndeA.setText("");
				txtOeffnungEndeB.setText("");
				txtHauptAnfangA.setText("");
				txtHauptAnfangB.setText("");
				txtHauptEndeA.setText("");
				txtHauptEndeB.setText("");

				cbMinTechInfo.setEnabled(false);
				cbMinHausInfo.setEnabled(false);
				cbMinHausKasse.setEnabled(false);
				cbExtraKassen.setEnabled(false);

			}
		});
		getContentPane().add(rdbtnStandardeinstellungen);

		lblAnzahlZustzlicherMitarbeiter = new JLabel("Anzahl zus\u00E4tzlicher Mitarbeiter:");
		lblAnzahlZustzlicherMitarbeiter.setFont(new Font("Verdana", Font.BOLD, 15));
		lblAnzahlZustzlicherMitarbeiter.setBounds(487, 575, 509, 16);
		getContentPane().add(lblAnzahlZustzlicherMitarbeiter);

		lblUhr = new JLabel("Uhr");
		lblUhr.setFont(new Font("Verdana", Font.BOLD, 13));
		lblUhr.setBounds(310, 442, 56, 16);
		getContentPane().add(lblUhr);

		lblUhr_1 = new JLabel("Uhr");
		lblUhr_1.setFont(new Font("Verdana", Font.BOLD, 13));
		lblUhr_1.setBounds(310, 503, 56, 16);
		getContentPane().add(lblUhr_1);

		lblUhr_2 = new JLabel("Uhr");
		lblUhr_2.setFont(new Font("Verdana", Font.BOLD, 13));
		lblUhr_2.setBounds(310, 575, 56, 16);
		getContentPane().add(lblUhr_2);

		lblUhr_3 = new JLabel("Uhr");
		lblUhr_3.setFont(new Font("Verdana", Font.BOLD, 13));
		lblUhr_3.setBounds(310, 641, 56, 16);
		getContentPane().add(lblUhr_3);
		setVisible(true);

	}

	protected TreeMap<String, String> gibOeffnungszeiten() {

		//Auskommentierte Zeilen können weg --> nicht mehr notwendig
		
		TreeMap<String, String> zeiten = new TreeMap<String, String>();
		zeiten.put("Öffnungszeit", txtOeffnungAnfangA.getText() + ":" + txtOeffnungAnfangB.getText());
		zeiten.put("Schließzeit", txtOeffnungEndeA.getText() + ":" + txtOeffnungEndeB.getText());
		zeiten.put("HauptzeitBeginn", txtHauptAnfangA.getText() + ":" + txtHauptAnfangB.getText());
		zeiten.put("HauptzeitEnde", txtHauptEndeA.getText() + ":" + txtHauptEndeB.getText());

		return zeiten;

	}

	protected TreeMap<String, Integer> gibBesetzung() {

		TreeMap<String, Integer> besetzung = new TreeMap<String, Integer>();
		besetzung.put("MinBesetzungKasse", Integer.parseInt(cbMinHausKasse.getSelectedItem().toString()));
		besetzung.put("MinBesetzungInfoWaren", Integer.parseInt(cbMinHausInfo.getSelectedItem().toString()));
		besetzung.put("MinBesetzungInfoTechnik", Integer.parseInt(cbMinTechInfo.getSelectedItem().toString()));
		besetzung.put("MehrbesetzungKasse", Integer.parseInt(cbExtraKassen.getSelectedItem().toString()));
		return besetzung;
	}

	protected String gibWochenplanbezeichnung() {
		return "KW" + cbKW.getSelectedItem().toString();
	}

	//Die beiden Mehtoden sind ohne Kommentare nicht wirklich nachvollziehbar!
	
	protected String[] setField(int a, int b) {
		String[] werte = new String[a];
		for (int i = 0; i < werte.length; i++) {
			werte[i] = b + "";
			b++;
		}
		return werte;
	}

	protected void setComboBox(JComboBox cb, String[] werte) {
		for (int i = 0; i < werte.length; i++) {
			cb.addItem(werte[i]);
		}

	}
}
