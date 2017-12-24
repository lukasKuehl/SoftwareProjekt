package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.EinsatzplanController;
import data.Mitarbeiter;
import data.Schicht;
import data.Tauschanfrage;
import model.Einsatzplanmodel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

public class TauschanfrageErstellenView extends JFrame {
	private JPanel contentPane = null;
	private JLabel lblTauschanfrageStellen, labelTagAndererMA, lblWochenplanAuswhlen, lblMitWemWollen,
			lblSchichtAuswhlen, lblTagAuswhlen, labelSchichtAndererMA = null;
	private JComboBox<String> cmbBoxWP, cmbBoxTag, cmbBoxSchicht, comboBoxTagAndererMA, comboBoxSchichtAndererMA = null;
	private ArrayList<String> wp, tagMa, SchichtAndererMa, TagAndererMa, SchichtMa = null;
	private JButton btnErstellen = null;
	private EinsatzplanController myController = null;
	private Einsatzplanview myView = null;
	private Einsatzplanmodel myModel = null;
	private Schicht mySchicht = null;
	private int senderSchichtnr = 0;
	String temp[] = new String[14];
	
	/**
	 * @author Ramona Gerke	
	 * @Info Konstruktor der  View TauschanfrageErstellenView. Beinhaltet die gesamten Swing Komponenten. 
	 */
	
	protected TauschanfrageErstellenView(Einsatzplanmodel myModel, Einsatzplanview myView, EinsatzplanController myController) {
		 this.myView = myView;
		 this.myController = myController;
		 this.myModel = myModel;
		setTitle("Tauschanfrage erstellen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800, 600);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblTauschanfrageStellen = new JLabel("Tauschanfrage stellen");
		lblTauschanfrageStellen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTauschanfrageStellen.setBounds(62, 91, 385, 26);
		contentPane.add(lblTauschanfrageStellen);

		btnErstellen = new JButton("erstellen");
		btnErstellen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnErstellen.setBounds(532, 455, 136, 36);
		btnErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		}});
		contentPane.add(btnErstellen);

		cmbBoxWP = new JComboBox<String>();
		 wp = myView.getWochenplaene();
		for (String m : wp) {
			cmbBoxWP.addItem(m);
		}
		cmbBoxWP.setBounds(249, 174, 136, 26);
		cmbBoxWP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(cmbBoxWP);
		
		cmbBoxTag = new JComboBox<String>();
		tagMa = myView.getTage(cmbBoxWP.getSelectedItem().toString());
		for (String s : tagMa) {
		cmbBoxTag.addItem(s);
		}
		cmbBoxTag.setEnabled(false);
		cmbBoxTag.setFont(new Font("Verdana", Font.PLAIN, 15));
		cmbBoxTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cmbBoxTag.setBounds(249, 211, 136, 26);
		contentPane.add(cmbBoxTag);

		cmbBoxSchicht = new JComboBox<String>();
		SchichtMa = myView.getMitarbeiterSchichten(cmbBoxWP.getSelectedItem().toString(), cmbBoxTag.getSelectedItem().toString(), myView.getUsername());
		for (String v : SchichtMa) {
		cmbBoxSchicht.addItem(v);
		}
		cmbBoxSchicht.setEnabled(false);
		cmbBoxSchicht.setFont(new Font("Verdana", Font.PLAIN, 15));
		cmbBoxSchicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cmbBoxSchicht.setBounds(249, 248, 136, 26);
		contentPane.add(cmbBoxSchicht);
		
		lblWochenplanAuswhlen = new JLabel("Wochenplan auswählen");
		lblWochenplanAuswhlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblWochenplanAuswhlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblWochenplanAuswhlen.setBounds(62, 178, 188, 22);
		contentPane.add(lblWochenplanAuswhlen);

		lblTagAuswhlen = new JLabel("Tag auswählen");
		lblTagAuswhlen.setEnabled(false);
		lblTagAuswhlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTagAuswhlen.setHorizontalAlignment(SwingConstants.LEFT);
		lblTagAuswhlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblTagAuswhlen.setBounds(62, 211, 178, 26);
		contentPane.add(lblTagAuswhlen);

		lblSchichtAuswhlen = new JLabel("Schicht auswählen");
		lblSchichtAuswhlen.setEnabled(false);
		lblSchichtAuswhlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSchichtAuswhlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblSchichtAuswhlen.setBounds(62, 248, 136, 26);
		contentPane.add(lblSchichtAuswhlen);
		
		lblMitWemWollen = new JLabel("Tauschdaten");
		lblMitWemWollen.setFont(new Font("Verdana", Font.BOLD, 15));
		lblMitWemWollen.setBounds(62, 347, 222, 14);
		contentPane.add(lblMitWemWollen);

		labelTagAndererMA = new JLabel("Tag auswählen");
		labelTagAndererMA.setEnabled(false);
		labelTagAndererMA.setVerticalAlignment(SwingConstants.BOTTOM);
		labelTagAndererMA.setHorizontalAlignment(SwingConstants.LEFT);
		labelTagAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelTagAndererMA.setBounds(62, 372, 178, 26);
		contentPane.add(labelTagAndererMA);

		comboBoxTagAndererMA = new JComboBox<String>();
		TagAndererMa = myView.getTage(cmbBoxWP.getSelectedItem().toString());
		for (String n : TagAndererMa) {
		comboBoxTagAndererMA.addItem(n);
		}
		comboBoxTagAndererMA.setEnabled(false);
		comboBoxTagAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxTagAndererMA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		comboBoxTagAndererMA.setBounds(249, 372, 136, 26);
		contentPane.add(comboBoxTagAndererMA);

		labelSchichtAndererMA = new JLabel("Schicht auswählen");
		labelSchichtAndererMA.setEnabled(false);
		labelSchichtAndererMA.setVerticalAlignment(SwingConstants.BOTTOM);
		labelSchichtAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelSchichtAndererMA.setBounds(62, 409, 136, 26);
		contentPane.add(labelSchichtAndererMA);

		
		
		comboBoxSchichtAndererMA = new JComboBox<String>();
		
			
		ArrayList <String>al = myView.getTauschanfragen(myView.getUsername());
		for (String m : al) {
			m.toString();
			m.trim();
			temp = m.split("-");
			// tauschanfrageNrS = temp [0];
			String senderVorname = temp[0];
			String senderName = temp[1];
			String senderSchichtNr = temp[2];
			senderSchichtnr =  Integer.parseInt(temp[2]);
			String senderWpNr = temp[3];
			String senderTbez = temp[4];
			String senderAnfangsuhrzeit = temp[5];
			String senderEnduhrzeit = temp[6];
			String empfaengerVorname = temp[7];
			String empfaengerName = temp[8];
			String empfaengerSchichtNr = temp[9];
			String empfaengerWpBez = temp[10];
			String empfaengerTagBez = temp[11];
			String empfaengerAnfangsuhrzeit = temp[12];
			String emfaengerEnduhrzeit = temp[13];
		}
		
		
		 myView.getAndereMitarbeiterSchichten(cmbBoxWP.getSelectedItem().toString(), cmbBoxTag.getSelectedItem().toString(), myView.getUsername(), senderSchichtnr);
				for (String c : SchichtAndererMa) {
					comboBoxSchichtAndererMA.addItem(c);
		}
		comboBoxSchichtAndererMA.setEnabled(false);
		comboBoxSchichtAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxSchichtAndererMA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		comboBoxSchichtAndererMA.setBounds(249, 409, 136, 26);
		contentPane.add(comboBoxSchichtAndererMA);

/**
 * @author Ramona Gerke
 * @Info Action Performed Methoden, die die Sichtbarkeit der ComboBoxen ändert.
 */
		// ACTION PERFORMED METHODEN  
		cmbBoxWP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == cmbBoxWP) {
					cmbBoxTag.setEnabled(true);
					lblTagAuswhlen.setEnabled(true);
				}
			}
		});
		
		cmbBoxTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == cmbBoxTag) {
					cmbBoxSchicht.setEnabled(true);
					lblSchichtAuswhlen.setEnabled(true);
				}
			}
		});

		cmbBoxSchicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == cmbBoxSchicht) {
					comboBoxTagAndererMA.setEnabled(true);
					labelTagAndererMA.setEnabled(true);
				}
			}

		});
		comboBoxTagAndererMA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == comboBoxTagAndererMA) {
					comboBoxSchichtAndererMA.setEnabled(true);
					labelSchichtAndererMA.setEnabled(true);

				}
			}
		});
		
		btnErstellen.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnErstellen) {
				
				String senderName = myView.getUsername();
				int senderSchichtnr = Integer.parseInt(temp [2]);
				String empfaengerName= temp [8];
				int empfaengerSchichtNr= Integer.parseInt(temp [9]);
				myView.erstelleTauschanfrage(senderName, senderSchichtnr, empfaengerName, empfaengerSchichtNr );
				
			}
		}});
				
		setVisible(true);

	}
	
	
	
}

