package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import controller.EinsatzplanController;

import java.awt.event.*;
import java.io.IOException;
import java.util.TreeMap;
import model.Einsatzplanmodel;

public class TerminErstellenView extends JFrame {

	// Initialisierung der Instanzvariablen
	private JPanel contentPane = null;
	private JTable table = null;
	private JTextField txtFldBisTermin, txtFldVonTerminA, txtFldUhrzeitTerminV, txtFldUhrzeitTerminB, txtFldGrund, txtFldUhrzeitBisA, txtFldUhrzeitBisB = null;
	private JComboBox comboBoxTerminGrund = null;
	private JCheckBox chckbxGanztig = null;
	private JLabel labelTerminErstellen, lblGrund, labelUhrzeitBis, lblDoppelpunkt2, lblDoppelpunkt1, lblVon, lblVon1, lblTagBis = null;
	private JPanel panelTermin = null;
	private TreeMap<Integer, String> zeitraum = null;
	private String zeitr, bez, grund, username = null;
	private JButton btnErstellen = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;
	
	// Konstruktoraufruf
	public TerminErstellenView(Einsatzplanmodel myModel, EinsatzplanController MyController) {
		this.myModel = myModel;
		this.myController = myController;
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

		txtFldBisTermin = new JTextField();
		txtFldBisTermin.setBounds(106, 165, 110, 20);
		txtFldBisTermin.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtFldBisTermin.setText("01.11.2017");
		txtFldBisTermin.setColumns(10);
		panelTermin.add(txtFldBisTermin);

		labelTerminErstellen = new JLabel("Termin erstellen");
		labelTerminErstellen.setBounds(64, 43, 200, 28);
		labelTerminErstellen.setFont(new Font("Verdana", Font.PLAIN, 22));
		panelTermin.add(labelTerminErstellen);

		txtFldVonTerminA = new JTextField(); // Java Kalender einfügen
		txtFldVonTerminA.setText("01.11.2017");
		txtFldVonTerminA.setBounds(106, 114, 110, 20);
		txtFldVonTerminA.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtFldVonTerminA.setHorizontalAlignment(SwingConstants.LEFT);
		txtFldVonTerminA.setColumns(10);
		panelTermin.add(txtFldVonTerminA);

		txtFldUhrzeitTerminV = new JTextField();
		txtFldUhrzeitTerminV.setBounds(102, 264, 34, 20);
		txtFldUhrzeitTerminV.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtFldUhrzeitTerminV.setColumns(10);
		panelTermin.add(txtFldUhrzeitTerminV);

		txtFldUhrzeitTerminB = new JTextField(); // Uhrzeit in zwei separte Felder schreiben
		txtFldUhrzeitTerminB.setBounds(162, 264, 34, 20);
		txtFldUhrzeitTerminB.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtFldUhrzeitTerminB.setColumns(10);
		panelTermin.add(txtFldUhrzeitTerminB);

		comboBoxTerminGrund = new JComboBox();
		comboBoxTerminGrund.setBackground(Color.WHITE);
		comboBoxTerminGrund.setBounds(255, 225, 157, 20);
		comboBoxTerminGrund.setFont(new Font("Verdana", Font.PLAIN, 14));
		comboBoxTerminGrund
				.setModel(new DefaultComboBoxModel(new String[] { "privater Termin", "Krankheit", "Urlaub" }));
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
		lblVon1.setBounds(64, 117, 46, 14);
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
		
		txtFldUhrzeitBisA = new JTextField();
		txtFldUhrzeitBisA.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtFldUhrzeitBisA.setColumns(10);
		txtFldUhrzeitBisA.setBounds(102, 299, 34, 20);
		panelTermin.add(txtFldUhrzeitBisA);
		
		lblDoppelpunkt2 = new JLabel(":");
		lblDoppelpunkt2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDoppelpunkt2.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblDoppelpunkt2.setBounds(131, 295, 41, 28);
		panelTermin.add(lblDoppelpunkt2);
		
		txtFldUhrzeitBisB = new JTextField();
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

				if (chckbxGanztig.isSelected() == true) {
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
		btnErstellen.setBounds(453, 507, 110, 23);
		btnErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null,
						JOptionPane.YES_NO_CANCEL_OPTION);

				if (eingabe == 0) {
					zeitraum = new TreeMap<Integer, String>();

					try {

						// Nutzername des aktuellen MA übergeben
						username = anmeldungView.getUsername();
						int terminnr = myModel.getMaxTBlocknr();
						String bez = (String) (comboBoxTerminGrund.getSelectedItem());
						String grund = txtGrund.getText();
						zeitr = txtVonTerminA.getText() + txtBisTermin.getText() + textUhrzeitTerminV.getText()
								+ textUhrzeitTerminB.getText();
						zeitraum.put(terminnr, zeitr);

						myView.erstelleTermin(username, bez, zeitraum, grund);

						System.exit(0);
					}

					catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Daten konnten nicht umgewandelt werden, da die Dateiformate nicht stimmen! - Fehler: TerminErstellenView Zeile Button Bestätigen ActionPerformed");
						e.printStackTrace();
					}
				} else {
					System.exit(0);
				}
			}
		});
		panelTermin.add(btnErstellen);
		
		contentPane.setVisible(true);
	}

}
