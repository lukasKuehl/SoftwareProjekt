package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class KasseSchichtView extends JFrame {
	
private EinsatzplanController myController=null;
private Einsatzplanmodel myModel=null;
private Einsatzplanview myView=null;
private JLabel lblBitteWochenplanAuswhlen,lblBitteDenTag,lblBitteDieSchicht,lblBitteDenMitarbeiter=null;
private JList listAlleMitarbeiter,listWochenplaene,listSchichtAuswahl=null;
private JComboBox cbTage=null;
private JButton btnPlanBestaetigen,btnTagAuswahl,btnSchichtAuswahl,btnMitarbeiterBestaetigen=null;
	/**
	 * Create the application.
	 */
	public KasseSchichtView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Schicht bearbeiten");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1538,864);
		getContentPane().setLayout(null);
		setVisible(true);
		
		lblBitteWochenplanAuswhlen = new JLabel("Bitte Wochenplan ausw\u00E4hlen:");
		lblBitteWochenplanAuswhlen.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBitteWochenplanAuswhlen.setBounds(22, 152, 243, 16);
		getContentPane().add(lblBitteWochenplanAuswhlen);
		
		listWochenplaene = new JList();
		listWochenplaene.setBounds(22, 183, 243, 105);
		getContentPane().add(listWochenplaene);
		
		btnPlanBestaetigen = new JButton("New button");
		btnPlanBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPlanBestaetigen.setBounds(168, 301, 97, 25);
		getContentPane().add(btnPlanBestaetigen);
		
		lblBitteDenTag = new JLabel("Bitte den Tag ausw\u00E4hlen:");
		lblBitteDenTag.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBitteDenTag.setBounds(22, 334, 243, 16);
		getContentPane().add(lblBitteDenTag);
		
		cbTage = new JComboBox();
		cbTage.setBounds(22, 383, 243, 22);
		getContentPane().add(cbTage);
		
		btnTagAuswahl = new JButton("New button");
		btnTagAuswahl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTagAuswahl.setBounds(168, 418, 97, 25);
		getContentPane().add(btnTagAuswahl);
		
		lblBitteDieSchicht = new JLabel("Bitte die Schicht ausw\u00E4hlen:");
		lblBitteDieSchicht.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBitteDieSchicht.setBounds(22, 454, 243, 16);
		getContentPane().add(lblBitteDieSchicht);
		
		listSchichtAuswahl = new JList();
		listSchichtAuswahl.setBounds(22, 496, 243, 128);
		getContentPane().add(listSchichtAuswahl);
		
		btnSchichtAuswahl = new JButton("New button");
		btnSchichtAuswahl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSchichtAuswahl.setBounds(168, 647, 97, 25);
		getContentPane().add(btnSchichtAuswahl);
		
		lblBitteDenMitarbeiter = new JLabel("Bitte den Mitarbeiter ausw\u00E4hlen:");
		lblBitteDenMitarbeiter.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBitteDenMitarbeiter.setBounds(644, 153, 299, 16);
		getContentPane().add(lblBitteDenMitarbeiter);
		
		listAlleMitarbeiter = new JList();
		listAlleMitarbeiter.setBounds(644, 183, 299, 489);
		getContentPane().add(listAlleMitarbeiter);
		
		btnMitarbeiterBestaetigen = new JButton("best\u00E4tigen");
		btnMitarbeiterBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnMitarbeiterBestaetigen.setBounds(846, 714, 97, 25);
		getContentPane().add(btnMitarbeiterBestaetigen);
		
	}
}
