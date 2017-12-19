package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javafx.scene.control.RadioButton;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class TauschanfrageLoeschenView extends JFrame {

	private JPanel contentPane, panelKrankmeldung= null;
	private JLabel lblTauschanfrageLoeschen = null;
	private JList listKrankmeldung = null;
	private JButton btnBestaetigen = null;
	private Einsatzplanview myView= null;

		protected TauschanfrageLoeschenView() {
		
		setTitle("Tauschanfrage löschen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800,600);
			
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelKrankmeldung = new JPanel();
		panelKrankmeldung.setBackground(Color.WHITE);
		panelKrankmeldung.setBounds(29, 30, 1188, 728);
		panelKrankmeldung.setBorder(UIManager.getBorder("Button.border"));
		contentPane.add(panelKrankmeldung);
		panelKrankmeldung.setLayout(null);
		

		lblTauschanfrageLoeschen = new JLabel("Tauschanfrage loeschen:");
		lblTauschanfrageLoeschen.setFont(new Font("Verdana", Font.BOLD, 20));
		lblTauschanfrageLoeschen.setBounds(70, 89, 284, 26);
		panelKrankmeldung.add(lblTauschanfrageLoeschen);
		
		listKrankmeldung = new JList<Object>();
	 // Linked List mit For Each Schleife ausgeben
		
	// Termine löschen, Krankmeldungen löschen und Tauschanfragen löschen evtl. doch in Linked List, da ID mit angegeben sollte
	
		listKrankmeldung.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listKrankmeldung.setBounds(71, 126, 340, 392);
		panelKrankmeldung.add(listKrankmeldung);
		
		btnBestaetigen = new JButton("Bestätigen");
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						}
					});
		btnBestaetigen.setBounds(519, 483, 141, 35);
		panelKrankmeldung.add(btnBestaetigen);
		
			setVisible(true);
			
		}
		//ACTION PERFORMED METHODEN 
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==btnBestaetigen) {
				
			int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Auswahl bestätigen?", null, JOptionPane.YES_NO_CANCEL_OPTION);
								
					if (eingabe==0) {	
						try {
						int id =	listKrankmeldung.getSelectedIndex();
						String s = (String) listKrankmeldung.getSelectedValue();
//						myView.entferneTermin(id);	
						}
						catch(Exception ex) {
							ex.getStackTrace();
							System.err.println("Fehler in der Eingabe der Daten - ActionPerformed, Button Bestätigen und Klasse TauschanfrageLoeschen");
							}
					}
						else {
							System.exit(0);
						}
					
				};
	}
}
