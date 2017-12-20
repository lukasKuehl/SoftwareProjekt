package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class TauschanfrageAnzeigenView extends JFrame {

	private JPanel contentPane = null;
	private JTextField textFieldDatum =null;
	private JLabel lblTauschanfrageAnzeigen, lblMitarbeiterAuswaehlen  =null;
	private JButton btnVersenden, btnAnnehmen = null;
	private JList listTauschanfragen = null;
	private JComboBox cmbBoxMa = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;
	
	
		protected TauschanfrageAnzeigenView(Einsatzplanview myView, Einsatzplanmodel myModel, EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Tauschanfrage annehmen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800,600);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		listTauschanfragen = new JList();
		
		// LINKED LIST MIT TERMIN NR  // AUSGABE DER LISTE
		listTauschanfragen.setBorder(new LineBorder(new Color(0, 0, 0)));
		listTauschanfragen.setBounds(95, 144, 315, 336);
		contentPane.add(listTauschanfragen);
		
		//ANZEIGE ÜBER JLIST mit TauschanfrageID und NAMEN
		
		
		lblTauschanfrageAnzeigen = new JLabel("Tauschanfragen");
		lblTauschanfrageAnzeigen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTauschanfrageAnzeigen.setBounds(62, 91, 198, 26);
		contentPane.add(lblTauschanfrageAnzeigen);
		
		btnAnnehmen = new JButton("annehmen");
		btnAnnehmen.setFont(new Font("Verdana", Font.PLAIN, 18));
		btnAnnehmen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnAnnehmen) {
					int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Tauschanfrage annehmen?", null,
							JOptionPane.YES_NO_CANCEL_OPTION);
					if (eingabe == 0) {
					// LOGIK
					}
			}
			}});
		btnAnnehmen.setBounds(499, 419, 136, 38);
		contentPane.add(btnAnnehmen);
		
				
		setVisible(true);
		
		
	}
	}