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
	private JTextField txtBisTermin = null, txtVonTermin,textUhrzeitTerminV,textUhrzeitTerminB,txtGrund=null;
	private JComboBox comboBoxTerminGrund=null;
	private JCheckBox chckbxGanztig=null;
	private JLabel labelTerminErstellen=null, lblGrund= null;
	private JPanel panelTermin = null;
	private String username= null;
	private TreeMap<Integer, String>zeitraum = null;
	private String zeitr , bez, grund = null;
	private JButton btnBestaetigen=null;
	private Einsatzplanmodel myModel= null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;

		public TerminErstellenView(Einsatzplanmodel myModel, Einsatzplanview MyView) {
		this.myModel = myModel;
		this.myView = myView;
		this.myController= myController;
		setTitle("Termin erstellen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800,600);
		
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
		
		txtBisTermin = new JTextField();
		txtBisTermin.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtBisTermin.setText("01.11.2017");
		txtBisTermin.setColumns(10);
		txtBisTermin.setBounds(64, 224, 189, 20);
		panelTermin.add(txtBisTermin);
		
		labelTerminErstellen = new JLabel("Termin erstellen:");
		labelTerminErstellen.setFont(new Font("Verdana", Font.PLAIN, 22));
		labelTerminErstellen.setBounds(64, 100, 200, 28);
		panelTermin.add(labelTerminErstellen);
		
		txtVonTermin = new JTextField(); // Java Kalender einfügen
		txtVonTermin.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtVonTermin.setText("01.11.2017");
		txtVonTermin.setHorizontalAlignment(SwingConstants.LEFT);
		txtVonTermin.setColumns(10);
		txtVonTermin.setBounds(64, 169, 189, 20);
		panelTermin.add(txtVonTermin);
		
		textUhrzeitTerminV = new JTextField();
		textUhrzeitTerminV.setFont(new Font("Verdana", Font.PLAIN, 14));
		textUhrzeitTerminV.setText("10:00");
		textUhrzeitTerminV.setColumns(10);
		textUhrzeitTerminV.setBounds(292, 169, 86, 20);
		panelTermin.add(textUhrzeitTerminV);
		
		textUhrzeitTerminB = new JTextField(); // Uhrzeit in zwei separte Felder schreiben
		textUhrzeitTerminB.setFont(new Font("Verdana", Font.PLAIN, 14));
		textUhrzeitTerminB.setText("15:00");  // if eingabe Standardinhalt = null wert abfrage schreiben
		textUhrzeitTerminB.setColumns(10);
		textUhrzeitTerminB.setBounds(292, 224, 86, 20);
		panelTermin.add(textUhrzeitTerminB);
		
		comboBoxTerminGrund = new JComboBox();  
		comboBoxTerminGrund.setFont(new Font("Verdana", Font.PLAIN, 14));
		comboBoxTerminGrund.setModel(new DefaultComboBoxModel(new String[] {"privater Termin", "Krankheit", "Urlaub"})); 
		comboBoxTerminGrund.setBounds(64, 302, 189, 20);
		panelTermin.add(comboBoxTerminGrund);
		
		chckbxGanztig = new JCheckBox("ganztägig");
		chckbxGanztig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (chckbxGanztig.isSelected() == true) {
					textUhrzeitTerminV.setEnabled(false);
					textUhrzeitTerminB.setEnabled(false);
				}
				else {
			textUhrzeitTerminV.setEnabled(true);
			textUhrzeitTerminB.setEnabled(true);
			}
				}
		});
		chckbxGanztig.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxGanztig.setSelected(false);
		chckbxGanztig.setBounds(292, 303, 97, 23);
		panelTermin.add(chckbxGanztig);
		
		btnBestaetigen = new JButton("bestätigen");
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null, JOptionPane.YES_NO_CANCEL_OPTION);
		//		System.out.print(eingabe); Kontrolle
				
				
				if (eingabe==0) {
					zeitraum = new TreeMap<Integer, String>();
	
					try {
						
	// Nutzername des aktuellen MA 
			username = anmeldungView.getUsername();
			int index =  Integer.parseInt(myModel.getMaxTBlocknr());
			String bez = (String)(comboBoxTerminGrund.getSelectedItem());
			String grund=	txtGrund.getText() ;
		
			
			zeitr = txtVonTermin.getText() +txtBisTermin.getText() + textUhrzeitTerminV.getText() +textUhrzeitTerminB.getText() ;
			zeitraum.put(index, zeitr); 
			
			///TEST LoESCHEN
			for (Integer id : zeitraum.keySet()) {
				System.out.println(id + " - " + zeitraum.get(id));}
			// TEST LOESCHEN
			
			System.exit(0);
					}
							       
			       catch (Exception e) {
			    	   JOptionPane.showMessageDialog(null,"Daten konnten nicht umgewandelt werden, da die Dateiformate nicht stimmen! - Fehler: TerminErstellenView Zeile Button Bestätigen ActionPerformed");
			    	   e.printStackTrace();
			       }
			}
				else {
					System.exit(0);
				}
			}
				}
			);
		// Methode für die Überprüfung des Formates schreiben   
		btnBestaetigen.setBounds(448, 557, 89, 23);
		panelTermin.add(btnBestaetigen);
		
		lblGrund = new JLabel("Bitte tragen Sie hier den Grund ein:");
		lblGrund.setBounds(64, 354, 353, 26);
		panelTermin.add(lblGrund);
		
		txtGrund = new JTextField();
		txtGrund.setHorizontalAlignment(SwingConstants.CENTER);
		txtGrund.setBounds(64, 385, 353, 195);
		panelTermin.add(txtGrund);
		txtGrund.setColumns(10);
		
		setVisible(true);
	}
}

