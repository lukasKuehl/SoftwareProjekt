package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class TerminLoeschenView extends JFrame {

	private JPanel contentPane, panelTermin = null;
	private JLabel lblTerninLoeschen =null;
	private JList list =null;
	private String username = null;


	protected TerminLoeschenView() {
		setTitle("Einsatzplan");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800,600);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTerninLoeschen = new JLabel("Termin löschen");
		lblTerninLoeschen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTerninLoeschen.setBounds(65, 83, 199, 26);
		contentPane.add(lblTerninLoeschen);
		
		MaListeAnzeigen();
		
		JButton btnBesttigen = new JButton("bestätigen");
		btnBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null, JOptionPane.YES_NO_CANCEL_OPTION);
				//		System.out.print(eingabe); Kontrolle
						
						if (eingabe==0) {
								
							if  (list.isSelectionEmpty()) {
							
							JOptionPane.showMessageDialog(null, "Es wurde keine Eingabe getätigt", "Error", JOptionPane.ERROR_MESSAGE);
							}
							 else {
								 try {
									 list.getSelectedIndex();
								// myController.entferneTermin();
							// an controller übergeben das er den ausgewählten Termin löscht 
									 System.exit(0);
								 }
								 catch (Exception a) {
									 a.getMessage();
									 System.out.println("Die Liste konnte nicht übergeben werden. IOException - Methode ActionPerformed (btnBestätigen)");
								 }
							 }
							}
						else {
								System.exit(0);
						}
							
			}});
		btnBesttigen.setBounds(437, 465, 141, 35);
		contentPane.add(btnBesttigen);
		
		JLabel lblTerminAuswhlen = new JLabel("Termin ausw\u00E4hlen");
		lblTerminAuswhlen.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblTerminAuswhlen.setBounds(62, 120, 152, 14);
		contentPane.add(lblTerminAuswhlen);

		setVisible(true);
	}
	
	
	//Aktuelle testmethode für die Anzeige der Termine
	private void MaListeAnzeigen () {
	String s [] = {"Termin 1", "Termin 2"};  // Test 	s
	list = new JList(s);
	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	list.setBounds(65, 145, 282, 355);
	list.getModel();
	contentPane.add(list);
	}
	
}
