package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class TerminErstellenView extends JFrame implements ActionListener {
	
	// Initialisierung der Instanzvariablen
	private JPanel contentPane;
	private JTable table;
	private JTextField txtBisTermin, txtVonTermin, textUhrzeitTerminV, textUhrzeitTerminB, txtGrund;
	private JComboBox comboBoxTerminGrund;
	private JCheckBox chckbxGanztig;
	private JLabel labelTerminErstellen;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TerminErstellenView frame = new TerminErstellenView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Erstellen der View Termin erstellen
	 */
	protected TerminErstellenView() {
		setTitle("Einsatzplan");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1538,864);
		
		
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new LineBorder(new Color(204, 204, 204), 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelTermin = new JPanel();
		panelTermin.setBounds(29, 30, 1188, 728);
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
		
		JButton btnBesttigen = new JButton("bestätigen");
		btnBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null, JOptionPane.YES_NO_CANCEL_OPTION);
		//		System.out.print(eingabe); Kontrolle
				
				
				if (eingabe==0) {
					String termin[]= new String [6];
					int terminnr=0;
					terminnr++;  // vom controller die nöchste Id holen 
	
					try {
	
			termin[0] = txtVonTermin.getText();
    	   	termin[1] = txtBisTermin.getText();	   
			termin [2] =	txtGrund.getText();
			termin [3]  =textUhrzeitTerminV.getText();
			termin [4] = textUhrzeitTerminB.getText();
			termin [5] = (String)(comboBoxTerminGrund.getSelectedItem());
//			termin[0] = String.valueOf(terminnr); TerminNr anzeigen
			
			//test des Array Inhalts
			for(int i=0; i<termin.length; ++i) {
				System.out.print(termin[i]+" ");
			}

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
		btnBesttigen.setBounds(448, 557, 89, 23);
		panelTermin.add(btnBesttigen);
		
		txtGrund = new JTextField();
		txtGrund.setHorizontalAlignment(SwingConstants.CENTER);
		txtGrund.setText("Bitte tragen Sie ggf. den genauen Grund f\u00FCr den Termin ein.");
		txtGrund.setBounds(64, 385, 353, 195);
		panelTermin.add(txtGrund);
		txtGrund.setColumns(10);
		
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void TermineUebergeben() {
		
	}

	
}

