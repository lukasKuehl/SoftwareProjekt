package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class KasseWocheErstellenView extends JFrame {
	private JTextField txtOeffnungAnfangA, txtOeffnungAnfangB, txtOeffnungEndeA, txtOeffnungEndeB,
	txtHauptAnfangA, txtHauptAnfangB, txtHauptEndeA,txtHauptEndeB;
	private JComboBox cbMinTechInfo, cbMinHausInfo, cbMinHausKasse, cbExtraHausInfo, cbExtraKassen,
	cbExtraTechInfo, cbKW;
	private JRadioButton rdbtnStandardeinstellungen;

	public KasseWocheErstellenView() {
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

		JLabel lblWochenplanErstellen = new JLabel("Wochenplan erstellen");
		lblWochenplanErstellen.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblWochenplanErstellen.setBounds(39, 127, 327, 52);
		getContentPane().add(lblWochenplanErstellen);

		JLabel lblKalenderwocheKw = new JLabel("Kalenderwoche KW:");
		lblKalenderwocheKw.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblKalenderwocheKw.setBounds(39, 358, 160, 16);
		getContentPane().add(lblKalenderwocheKw);

		cbKW = new JComboBox();
		cbKW.setBounds(211, 356, 50, 22);
		getContentPane().add(cbKW);
		String[] werte1 = setField(52, 1);
		setComboBox(cbKW, werte1);

		JLabel lblffnungszeiten = new JLabel("\u00D6ffnungszeiten:");
		lblffnungszeiten.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblffnungszeiten.setBounds(39, 441, 160, 16);
		getContentPane().add(lblffnungszeiten);

		txtOeffnungAnfangA = new JTextField();
		txtOeffnungAnfangA.setBounds(211, 439, 39, 22);
		getContentPane().add(txtOeffnungAnfangA);
		txtOeffnungAnfangA.setColumns(10);

		JLabel label = new JLabel(":");
		label.setBounds(255, 445, 39, 16);
		getContentPane().add(label);

		txtOeffnungAnfangB = new JTextField();
		txtOeffnungAnfangB.setBounds(272, 439, 39, 22);
		getContentPane().add(txtOeffnungAnfangB);
		txtOeffnungAnfangB.setColumns(10);

		JLabel lblBis = new JLabel("     bis");
		lblBis.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBis.setBounds(236, 474, 56, 16);
		getContentPane().add(lblBis);

		txtOeffnungEndeA = new JTextField();
		txtOeffnungEndeA.setBounds(211, 503, 39, 22);
		getContentPane().add(txtOeffnungEndeA);
		txtOeffnungEndeA.setColumns(10);

		JLabel label_1 = new JLabel(":");
		label_1.setBounds(262, 503, 56, 16);
		getContentPane().add(label_1);

		txtOeffnungEndeB = new JTextField();
		txtOeffnungEndeB.setBounds(272, 503, 39, 22);
		getContentPane().add(txtOeffnungEndeB);
		txtOeffnungEndeB.setColumns(10);

		JLabel lblHauptzeiten = new JLabel("Hauptzeiten:");
		lblHauptzeiten.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblHauptzeiten.setBounds(39, 574, 160, 16);
		getContentPane().add(lblHauptzeiten);

		txtHauptAnfangA = new JTextField();
		txtHauptAnfangA.setBounds(211, 572, 39, 22);
		getContentPane().add(txtHauptAnfangA);
		txtHauptAnfangA.setColumns(10);

		JLabel label_2 = new JLabel(":");
		label_2.setBounds(262, 575, 5, 16);
		getContentPane().add(label_2);

		txtHauptAnfangB = new JTextField();
		txtHauptAnfangB.setBounds(272, 572, 39, 22);
		getContentPane().add(txtHauptAnfangB);
		txtHauptAnfangB.setColumns(10);

		JLabel lblBis_1 = new JLabel("bis");
		lblBis_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBis_1.setBounds(255, 627, 56, 16);
		getContentPane().add(lblBis_1);

		txtHauptEndeA = new JTextField();
		txtHauptEndeA.setBounds(211, 672, 39, 22);
		getContentPane().add(txtHauptEndeA);
		txtHauptEndeA.setColumns(10);

		JLabel label_3 = new JLabel(":");
		label_3.setBounds(262, 675, 5, 16);
		getContentPane().add(label_3);

		txtHauptEndeB = new JTextField();
		txtHauptEndeB.setBounds(272, 672, 39, 22);
		getContentPane().add(txtHauptEndeB);
		txtHauptEndeB.setColumns(10);

		JLabel lblMindestbesetzung = new JLabel("Mindestbesetzung f\u00FCr Technikwarenaus: Info");
		lblMindestbesetzung.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMindestbesetzung.setBounds(487, 431, 509, 16);
		getContentPane().add(lblMindestbesetzung);

		JLabel lblMindestbesetzungFrLebensmittel = new JLabel(
				"Mindestbesetzung f\u00FCr Lebensmittel- / Haushaltswarenhaus: Info");
		lblMindestbesetzungFrLebensmittel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMindestbesetzungFrLebensmittel.setBounds(487, 474, 509, 16);
		getContentPane().add(lblMindestbesetzungFrLebensmittel);

		JLabel lblMindestbesetzungFrLebensmittel_1 = new JLabel(
				"Mindestbesetzung f\u00FCr Lebensmittel- / Haushaltswarenhaus Kasse:");
		lblMindestbesetzungFrLebensmittel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMindestbesetzungFrLebensmittel_1.setBounds(487, 524, 509, 16);
		getContentPane().add(lblMindestbesetzungFrLebensmittel_1);

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

		JLabel lblNewLabel = new JLabel("Anzahl zus\u00E4tzlicher Mitarbeiter f\u00FCr:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(487, 575, 509, 16);
		getContentPane().add(lblNewLabel);

		JLabel lblLebensmittelHaushaltswarenhaus = new JLabel("Lebensmittel- / Haushaltswarenhaus Info:");
		lblLebensmittelHaushaltswarenhaus.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLebensmittelHaushaltswarenhaus.setBounds(487, 627, 509, 16);
		getContentPane().add(lblLebensmittelHaushaltswarenhaus);

		JLabel lblTechnikwarenhausInfo = new JLabel("Technikwarenhaus Info:");
		lblTechnikwarenhausInfo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTechnikwarenhausInfo.setBounds(487, 675, 509, 16);
		getContentPane().add(lblTechnikwarenhausInfo);

		JLabel lblKassen = new JLabel("Kassen:");
		lblKassen.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblKassen.setBounds(487, 727, 509, 16);
		getContentPane().add(lblKassen);

		cbExtraHausInfo = new JComboBox();
		cbExtraHausInfo.setBounds(1040, 636, 50, 22);
		getContentPane().add(cbExtraHausInfo);
		String[] werte5 = setField(10, 0);
		setComboBox(cbExtraHausInfo, werte5);

		cbExtraTechInfo = new JComboBox();
		cbExtraTechInfo.setBounds(1040, 675, 50, 22);
		getContentPane().add(cbExtraTechInfo);
		String[] werte6 = setField(10, 0);
		setComboBox(cbExtraTechInfo, werte6);

		cbExtraKassen = new JComboBox();
		cbExtraKassen.setBounds(1040, 725, 50, 22);
		getContentPane().add(cbExtraKassen);
		String[] werte7 = setField(10, 0);
		setComboBox(cbExtraKassen, werte7);

		JButton btnBesttigen = new JButton("best\u00E4tigen");
		btnBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Sind Sie sicher?", "Warnung",
						JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					
					String wpbez = gibWochenplanbezeichnung();
					
					TreeMap<String,String> eingabeOeffnungszeiten = gibOeffnungszeiten();
					TreeMap<String, Integer> eingabeBesetzung = gibBesetzung();
					
					dispose(); // // auch hier muss natürlich es noch bei JA es
								// angepasst werden
					// Controller.wocheErstellen(a,b,c,d)
					// dispose wenn keine Fehlermeldung kommt und öffne
					// KasseWocheErstellenPlanView
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

			}
		});
		getContentPane().add(rdbtnStandardeinstellungen);
		setVisible(true);

		/*
		 * frame = new JFrame(); frame.setBounds(100, 100, 450, 300);
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 */
	}

	public TreeMap<String, String> gibOeffnungszeiten() {

		TreeMap<String, String> zeiten = new TreeMap<String, String>();
		zeiten.put("OeffnunAnfangA", txtOeffnungAnfangA.getText());
		zeiten.put("OeffnunAnfangB", txtOeffnungAnfangB.getText());
		zeiten.put("OeffnungEndeA", txtOeffnungEndeA.getText());
		zeiten.put("OeffnungEndeB", txtOeffnungEndeB.getText());
		zeiten.put("AnfangA", txtHauptAnfangA.getText());
		zeiten.put("AnfangB", txtHauptAnfangB.getText());
		zeiten.put("EndeA", txtHauptEndeA.getText());
		zeiten.put("EndeB", txtHauptEndeB.getText());
		return zeiten;

	}

	public TreeMap<String, Integer> gibBesetzung() {

		TreeMap<String,Integer> besetzung = new TreeMap<String, Integer>();
		besetzung.put("MinBesetzungKasse", Integer.parseInt(cbMinHausKasse.getSelectedItem().toString()));
		besetzung.put("MinBesetzungInfoWaren", Integer.parseInt(cbMinHausInfo.getSelectedItem().toString()));
		besetzung.put("MinBesetzungInfoTechnik",Integer.parseInt(cbMinTechInfo.getSelectedItem().toString()));
		besetzung.put("MehrbesetzungKasse",Integer.parseInt(cbExtraKassen.getSelectedItem().toString()));
		besetzung.put("MehrbesetzungInfoTechnik",Integer.parseInt(cbExtraTechInfo.getSelectedItem().toString()));
		besetzung.put("MehrfachbesetzungInfoWaren",Integer.parseInt(cbExtraHausInfo.getSelectedItem().toString()));
		return besetzung;
	}
	
	public String gibWochenplanbezeichnung(){
		return cbKW.getSelectedItem().toString();
	}

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
