package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;

import controller.EinsatzplanController;

import java.awt.event.*;
import java.io.IOException;
import java.text.*;
import java.util.*;
import model.Einsatzplanmodel;

public class TerminErstellenView extends JFrame {

	// Initialisierung der Instanzvariablen
	private JPanel contentPane = null;
	private JTable table = null;
	private JFormattedTextField txtFldUhrzeitTerminV, txtFldUhrzeitTerminB, txtFldUhrzeitBisA, txtFldUhrzeitBisB = null;
	private JTextField txtFldGrund = null;
	private JComboBox<String> comboBoxTerminGrund, comboBoxWochenplaene, comboBoxAnfang, comboBoxEnd = null;
	private JCheckBox chckbxGanztig = null;
	private JLabel labelTerminErstellen, lblGrund, labelUhrzeitBis, lblDoppelpunkt2, lblDoppelpunkt1, lblVon, lblVon1,
			lblTagBis, lblWochenplan = null;
	private JPanel panelTermin = null;
	private ArrayList<String> wp = null;
	private TreeMap<String, String> zeitraum = null;
	private String zeitr, bez, grund, username = null;
	private JButton btnErstellen = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;

	public TerminErstellenView(Einsatzplanview myView, Einsatzplanmodel myModel, EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Termin erstellen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panelTermin = new JPanel();
		panelTermin.setBounds(29, 30, 1188, 728);
		panelTermin.setBackground(Color.WHITE);
		panelTermin.setBorder(UIManager.getBorder("Button.border"));
		contentPane.add(panelTermin);
		panelTermin.setLayout(null);

		labelTerminErstellen = new JLabel("Termin erstellen");
		labelTerminErstellen.setBounds(64, 43, 200, 28);
		labelTerminErstellen.setFont(new Font("Verdana", Font.PLAIN, 22));
		panelTermin.add(labelTerminErstellen);

		try {
			txtFldUhrzeitTerminV = new JFormattedTextField(new MaskFormatter("##"));
		} catch (ParseException eUhrzeitTerminV) {
			JOptionPane.showConfirmDialog(null,
					"Die Eingabe konnte nicht gewandelt werden. Bitte überprüfen Sie die Eingaben!! - TerminErstellen - txtFldUhrzeitBisA"
							+ eUhrzeitTerminV);
			eUhrzeitTerminV.printStackTrace();
		}
		txtFldUhrzeitTerminV.setBounds(102, 264, 34, 20);
		txtFldUhrzeitTerminV.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtFldUhrzeitTerminV.setColumns(10);
		panelTermin.add(txtFldUhrzeitTerminV);

		try {
			txtFldUhrzeitTerminB = new JFormattedTextField(new MaskFormatter("##"));
		} catch (ParseException eUhrzeitTerminB) {
			JOptionPane.showConfirmDialog(null,
					"Die Eingabe konnte nicht gewandelt werden. Bitte überprüfen Sie die Eingaben!! - TerminErstellen - txtFldUhrzeitBisA"
							+ eUhrzeitTerminB);
			eUhrzeitTerminB.printStackTrace();
		}
		txtFldUhrzeitTerminB.setBounds(162, 264, 34, 20);
		txtFldUhrzeitTerminB.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtFldUhrzeitTerminB.setColumns(10);
		panelTermin.add(txtFldUhrzeitTerminB);

		comboBoxTerminGrund = new JComboBox<String>();
		comboBoxTerminGrund.setBackground(Color.WHITE);
		comboBoxTerminGrund.setBounds(255, 264, 157, 20);
		comboBoxTerminGrund.setFont(new Font("Verdana", Font.PLAIN, 14));
		comboBoxTerminGrund
				.setModel(new DefaultComboBoxModel<String>(new String[] { "privater Termin", "Krankheit", "Urlaub" }));
		panelTermin.add(comboBoxTerminGrund);

		lblGrund = new JLabel("Bitte tragen Sie hier den Grund ein:");
		lblGrund.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblGrund.setBounds(59, 387, 353, 26);
		panelTermin.add(lblGrund);

		txtFldGrund = new JTextField();
		txtFldGrund.setBounds(59, 414, 353, 116);
		txtFldGrund.setHorizontalAlignment(SwingConstants.CENTER);
		panelTermin.add(txtFldGrund);
		txtFldGrund.setColumns(10);

		lblVon1 = new JLabel("Von");
		lblVon1.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblVon1.setBounds(64, 137, 46, 14);
		panelTermin.add(lblVon1);

		lblTagBis = new JLabel("Bis");
		lblTagBis.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblTagBis.setBounds(64, 168, 46, 14);
		panelTermin.add(lblTagBis);

		lblDoppelpunkt1 = new JLabel(":");
		lblDoppelpunkt1.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblDoppelpunkt1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDoppelpunkt1.setBounds(131, 260, 41, 28);
		panelTermin.add(lblDoppelpunkt1);

		lblVon = new JLabel("Von ");
		lblVon.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblVon.setBounds(64, 267, 46, 14);
		panelTermin.add(lblVon);

		try {
			txtFldUhrzeitBisA = new JFormattedTextField(new MaskFormatter("##"));
		} catch (ParseException eUhrzeitBisB) {
			JOptionPane.showConfirmDialog(null,
					"Die Eingabe konnte nicht gewandelt werden. BItte überprüfen Sie die Eingaben!! - TerminErstellen - txtFldUhrzeitBisA"
							+ eUhrzeitBisB);
		}
		txtFldUhrzeitBisA.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtFldUhrzeitBisA.setColumns(10);
		txtFldUhrzeitBisA.setBounds(102, 299, 34, 20);
		panelTermin.add(txtFldUhrzeitBisA);

		lblDoppelpunkt2 = new JLabel(":");
		lblDoppelpunkt2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDoppelpunkt2.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblDoppelpunkt2.setBounds(131, 295, 41, 28);
		panelTermin.add(lblDoppelpunkt2);

		comboBoxWochenplaene = new JComboBox<String>();
		wp = myView.getWochenplaene();
		for (String m : wp) {
			comboBoxWochenplaene.addItem(m);
		}
		comboBoxWochenplaene.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxWochenplaene.setBounds(162, 105, 110, 20);
		panelTermin.add(comboBoxWochenplaene);

		lblWochenplan = new JLabel("Wochenplan");
		lblWochenplan.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblWochenplan.setBounds(59, 108, 113, 14);
		panelTermin.add(lblWochenplan);

		comboBoxAnfang = new JComboBox<String>();
		comboBoxAnfang.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag" }));
		comboBoxAnfang.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxAnfang.setBounds(162, 136, 110, 20);
		panelTermin.add(comboBoxAnfang);

		comboBoxEnd = new JComboBox<String>();
		comboBoxEnd.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag" }));
		comboBoxEnd.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxEnd.setBounds(162, 167, 110, 20);
		panelTermin.add(comboBoxEnd);

		try {
			txtFldUhrzeitBisB = new JFormattedTextField(new MaskFormatter("##"));
		} catch (ParseException eUhrzeitBisB) {
			JOptionPane.showConfirmDialog(null,
					"Die Eingabe konnte nicht gewandelt werden. BItte überprüfen Sie die Eingaben!! - TerminErstellen - txtFldUhrzeitBisB"
							+ eUhrzeitBisB.getStackTrace());
		}
		txtFldUhrzeitBisB.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtFldUhrzeitBisB.setColumns(10);
		txtFldUhrzeitBisB.setBounds(162, 299, 34, 20);
		panelTermin.add(txtFldUhrzeitBisB);

		labelUhrzeitBis = new JLabel("Bis");
		labelUhrzeitBis.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelUhrzeitBis.setBounds(64, 302, 21, 14);
		panelTermin.add(labelUhrzeitBis);

		chckbxGanztig = new JCheckBox("ganztägig");
		chckbxGanztig.setBackground(Color.WHITE);
		chckbxGanztig.setBounds(64, 225, 97, 23);
		chckbxGanztig.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxGanztig.isSelected()) {
					txtFldUhrzeitTerminV.setEnabled(false);
					txtFldUhrzeitTerminB.setEnabled(false);
					labelUhrzeitBis.setEnabled(false);
					txtFldUhrzeitBisB.setEnabled(false);
					lblDoppelpunkt1.setEnabled(false);
					lblDoppelpunkt2.setEnabled(false);
					txtFldUhrzeitBisA.setEnabled(false);
					lblVon.setEnabled(false);

				} else {
					txtFldUhrzeitTerminV.setEnabled(true);
					txtFldUhrzeitTerminB.setEnabled(true);
					labelUhrzeitBis.setEnabled(true);
					txtFldUhrzeitBisB.setEnabled(true);
					lblDoppelpunkt1.setEnabled(true);
					lblDoppelpunkt2.setEnabled(true);
					txtFldUhrzeitBisA.setEnabled(true);
					lblVon.setEnabled(true);

				}
			}
		});
		chckbxGanztig.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxGanztig.setSelected(false);
		panelTermin.add(chckbxGanztig);

		btnErstellen = new JButton("erstellen");
		btnErstellen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnErstellen.setBounds(500, 500, 110, 25);
		btnErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelTermin.add(btnErstellen);

		setVisible(true);
	}

	// KONTROLLE DER MINUTEN UND STUNDEN FÜR DIE UHRZEITEINGABE

	public void kontrolleStunden(JFormattedTextField txtFldUhrzeitBisA) {
		this.txtFldUhrzeitBisA = txtFldUhrzeitBisA;
		int i;
		i = Integer.parseInt(txtFldUhrzeitBisA.getText().toString());
		if (i >= 24) {
			JOptionPane.showConfirmDialog(null,
					"Die Eingabe liegt über 24 Stunden. Bitte geben Sie eine Zahl zwischen 0 und 24 ein.");
		}

	}

	public void kontrolleMinuten(JFormattedTextField txtFldUhrzeitBisB) {
		this.txtFldUhrzeitBisB = txtFldUhrzeitBisB;
		int i;
		i = Integer.parseInt(txtFldUhrzeitBisB.getText().toString());
		if (i >= 60) {
			JOptionPane.showConfirmDialog(null,
					"Die Eingabe liegt über 60 Minuten. Bitte geben Sie eine Zahl zwischen 0 und 60 ein.");
		}
	}

	// ACTION PERFORMED METHODE
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnErstellen) {
			int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null,
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (eingabe == 0) {
				try {
					
					kontrolleStunden(txtFldUhrzeitBisA);
					kontrolleStunden(txtFldUhrzeitTerminV);
					kontrolleMinuten(txtFldUhrzeitBisB);
					kontrolleMinuten(txtFldUhrzeitBisB);

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Die Eingabe der Uhrzeit konnte nicht geprüft werden!" ,"Error", JOptionPane.ERROR_MESSAGE);
				}
				zeitraum = new TreeMap<String, String>();
				try {
					username = myView.getUsername();
					int terminnr = myModel.getNewTblocknr();
					
					String bez = comboBoxTerminGrund.getSelectedItem().toString();
					String grund = txtFldGrund.getText().toString();
					String wpbez = comboBoxWochenplaene.getSelectedItem().toString() + String.valueOf(terminnr);  
					zeitr = comboBoxAnfang.getSelectedItem().toString() + comboBoxEnd.getSelectedItem().toString()
							+ txtFldUhrzeitTerminV.getText().toString() + ":"+ txtFldUhrzeitTerminB.getText().toString()
							+ txtFldUhrzeitBisA.getText().toString() + ":" + txtFldUhrzeitBisB.getText().toString();
					zeitraum.put(wpbez, zeitr);

					myView.erstelleTermin(username, bez, zeitraum, grund);
					System.exit(0);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,
							"Daten konnten nicht umgewandelt werden, da die Dateiformate nicht stimmen! -"
									+ " Fehler: TerminErstellenView Zeile Button Bestätigen ActionPerformed","Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				System.exit(0);
			}
		}

	}
}
