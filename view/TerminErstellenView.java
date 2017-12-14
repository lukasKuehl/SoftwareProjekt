package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.TreeMap;


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
	private String zeitr = null;
	private JButton btnBestaetigen=null;

		protected TerminErstellenView() {
		setTitle("Termin erstellen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1538,864);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);;
		
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
		
		txtVonTermin = new JTextField();
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
		
		textUhrzeitTerminB = new JTextField();
		textUhrzeitTerminB.setFont(new Font("Verdana", Font.PLAIN, 14));
		textUhrzeitTerminB.setText("15:00");
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
					String termin [] = new String [2];
					zeitraum = new TreeMap<Integer, String>();
	
					try {
						
	// Nutzername des aktuellen MA 
//			username = anmeldungView.username();
//			termin[0] =  termin.getNextID(); 
			termin [0] = (String)(comboBoxTerminGrund.getSelectedItem());
			termin [1] =	txtGrund.getText() ;
			
			
					//test des Array Inhalts
			for(int i=0; i<termin.length; ++i) {
				System.out.print(termin[i]+" ");
			}
			
			zeitr = txtVonTermin.getText() +txtBisTermin.getText() + textUhrzeitTerminV.getText() +textUhrzeitTerminB.getText() ;
			zeitraum.put(getTerminId(), zeitr); 
			
			for (Integer id : zeitraum.keySet()) {
				System.out.println(id + " - " + zeitraum.get(id));}

		// Fenster mit Dispose schließen
			System.exit(0);
					}
							       
			       catch (Exception e) {
			    	   System.out.println("Daten konnten nicht umgewandelt werden, da die Dateiformate nicht stimmen! - Fehler: TerminErstellenView Zeile Button Bestätigen ActionPerformed");
			    	   e.printStackTrace();
			       }
			}
				else {
					System.exit(0);
				}
			}
				}
			);
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
		//INTERN NOCH LOESCHEN UND ID AUS DATENBANK HOLEN
		public int getTerminId() {
			return 5;
		}
}

