package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javafx.scene.control.RadioButton;
import java.awt.Color;
import controller.EinsatzplanController;
import java.awt.event.*;


public class KrankmeldungLoeschenView extends JFrame {

	
	/**
	 * @RamonaGerke
	 * @Info
	 */
	
	private JPanel contentPane,  panelKrankmeldung = null;
	private JLabel lblKrankmeldungLoeschen = null;
	private JList listKrankmeldung=null;
	private JButton btnBestaetigen=null;
	private EinsatzplanController myController = null;
	private String username= null;
	private JLabel lblBitteAuswaehlen=null;
	
	
		protected KrankmeldungLoeschenView() {
		setTitle("Krankmeldung löschen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800,600);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lblKrankmeldungLoeschen = new JLabel("Krankmeldung löschen");
		lblKrankmeldungLoeschen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblKrankmeldungLoeschen.setBounds(95, 74, 362, 26);
		contentPane.add(lblKrankmeldungLoeschen);
		
		
		listKrankmeldung = new JList(myController.getAlleTermine(username).toArray());
		listKrankmeldung.setBounds(95, 126, 443, 437);
		panelKrankmeldung.add(listKrankmeldung);
		
		btnBestaetigen = new JButton("Bestätigen");
		btnBestaetigen.setFont(new Font("Verdana", Font.PLAIN, 18));
		
		//ACTIONPERFORMED METHODE für den bestätigen Button
		btnBestaetigen.setBounds(497, 445, 141, 35);
		contentPane.add(btnBestaetigen);
		
		listKrankmeldung = new JList();
		listKrankmeldung.setBorder(new LineBorder(new Color(0, 0, 0)));
		listKrankmeldung.setBounds(95, 144, 315, 336);
		contentPane.add(listKrankmeldung);
		
		lblBitteAuswaehlen = new JLabel("Bitte auswählen:");
		lblBitteAuswaehlen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBitteAuswaehlen.setBounds(95, 128, 113, 14);
		contentPane.add(lblBitteAuswaehlen);
	
		setVisible(true);
			
		}
	}

