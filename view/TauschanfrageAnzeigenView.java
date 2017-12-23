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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class TauschanfrageAnzeigenView extends JFrame {

	private JPanel contentPane = null;
	private JTextField textFieldDatum =null;
	private JLabel lblTauschanfrageAnzeigen  =null;
	private JButton  btnAnnehmen = null;
	private JList<Object> listTauschanfragen = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;
	private DefaultListModel<Object> modelTauschanfrage = null;
	private ArrayList <String> tl = null;
	
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
				
		listTauschanfragen = new JList<Object>();
		tl = myController.getTauschanfragen(myView.getUsername());
		modelTauschanfrage = new DefaultListModel<Object>();
		for (String m : tl) {
			modelTauschanfrage.addElement(m);
		}
		listTauschanfragen.setBorder(new LineBorder(new Color(0, 0, 0)));
		listTauschanfragen.setBounds(95, 144, 315, 336);
		contentPane.add(listTauschanfragen);
		
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
						if (tl.isEmpty()) {
							JOptionPane.showMessageDialog(null,"Es wurde keine Tauschanfrage ausgewählt. Bitte wählen Sie eine Tauschanfrage aus.", "Error", JOptionPane.ERROR_MESSAGE);
								}
						else {
							String empfaengerName = null;
							int tauschanfrageNr = 0;
							String temp [] = new String [14];
							tl = myController.getTauschanfragen(myView.getUsername());
							for (String m : tl) {
								m.toString();
								m.trim();
								temp = m.split("-");
								// tauschanfrageNrS = temp [0];
								String senderVorname = temp[0];
								String senderName = temp[1];
								String senderSchichtnr = temp[2];
								String senderWpNr = temp[3];
								String senderTbez = temp[4];
								String senderAnfangsuhrzeit = temp[5];
								String senderEnduhrzeit = temp[6];
								String empfaengerVorname = temp[7];
								empfaengerName = temp[8];
								String empfaengerSchichtNr = temp[9];
								String empfaengerWpBez = temp[10];
								String empfaengerTagBez = temp[11];
								String empfaengerAnfangsuhrzeit = temp[12];
								String emfaengerEnduhrzeit = temp[13];
							}
							myView.akzeptiereTauschanfrage(empfaengerName, tauschanfrageNr);
						}
			}
				}
			}});
		btnAnnehmen.setBounds(499, 419, 136, 38);
		contentPane.add(btnAnnehmen);
		
				
		setVisible(true);
		
		
	}
	}