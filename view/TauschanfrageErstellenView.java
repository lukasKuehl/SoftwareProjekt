package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.EinsatzplanController;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class TauschanfrageErstellenView extends JFrame {

	private JPanel contentPane = null;
	private JLabel lblTauschanfrageStellen, labelTagAndererMA,  lblWochenplanAuswhlen,
			lblMitWemWollen, lblSchichtAuswhlen, lblTagAuswhlen, labelSchichtAndererMA = null;
	private JComboBox<String> cmbBoxWP, cmbBoxTag, cmbBoxSchicht, comboBoxTagAndererMA,
			comboBoxSchichtAndererMA = null;
	private ArrayList<String> wp = null;
	private JButton btnVersenden = null;
	private EinsatzplanController myController = null;
	private Einsatzplanview myView = null;

	protected TauschanfrageErstellenView(EinsatzplanController myController) {
		this.myController = myController;
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

		cmbBoxTag = new JComboBox<String>();
		cmbBoxTag.setEnabled(false);
		cmbBoxTag.setModel(new DefaultComboBoxModel<String>(new String[] { "jashda", "hdaskd" })); // TEST DER COMBOBOX
		cmbBoxTag.setFont(new Font("Verdana", Font.PLAIN, 15));
		cmbBoxTag.setBounds(249, 211, 136, 26);
		contentPane.add(cmbBoxTag);

		btnVersenden = new JButton("versenden");
		btnVersenden.setFont(new Font("Verdana", Font.PLAIN, 15));}
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnVersenden) {
				
				String senderName = getUsername();
				int senderSchichtNr = myModel.getSchichtNr(username);
				String wp = cmbBoxWP.getSelectedItem().toString();
//				String empfaengerName = // Username des Mitarbeiter hinzufügen
				String tag= cmbBoxTag.getSelectedItem().toString();
				int empfaengerSchichtNr = getUsernameAndererMA();
				String schicht = cmbBoxSchicht.getSelectedItem().toString();
				String tagAndererMA = comboBoxTagAndererMA.getSelectedItem().toString();
				String schichtAndererMA = comboBoxSchichtAndererMA.getSelectedItem().toString();
				
				myView.erstelleTauschanfrage(String senderName, int senderSchichtNr, String empfaengerName, int empfaengerSchichtNr );
				
			}
		btnVersenden.setBounds(532, 455, 136, 36);
		contentPane.add(btnVersenden);

		ArrayList<String> hhh = new ArrayList<String>();
		hhh.add("Hallo");
		hhh.add("hajf");

		cmbBoxWP = new JComboBox<String>((new String[] { "jashda", "hdaskd" }));
		// wp = getWochenplaene();
		for (String m : hhh) {
			cmbBoxWP.addItem(m);
		}
		cmbBoxWP.setBounds(249, 174, 136, 26);
		contentPane.add(cmbBoxWP);

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

		cmbBoxSchicht = new JComboBox<String>((new String[] { "jashda", "hdaskd" }));
		cmbBoxSchicht.setEnabled(false);
		cmbBoxSchicht.setFont(new Font("Verdana", Font.PLAIN, 15));
		cmbBoxSchicht.setBounds(249, 248, 136, 26);
		contentPane.add(cmbBoxSchicht);

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

		comboBoxTagAndererMA = new JComboBox<String>((new String[] { "jashda", "hdaskd" }));
		comboBoxTagAndererMA.setEnabled(false);
		comboBoxTagAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		
		comboBoxTagAndererMA.setBounds(249, 372, 136, 26);
		contentPane.add(comboBoxTagAndererMA);

		labelSchichtAndererMA = new JLabel("Schicht auswählen");
		labelSchichtAndererMA.setEnabled(false);
		labelSchichtAndererMA.setVerticalAlignment(SwingConstants.BOTTOM);
		labelSchichtAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelSchichtAndererMA.setBounds(62, 409, 136, 26);
		contentPane.add(labelSchichtAndererMA);

		comboBoxSchichtAndererMA = new JComboBox<String>((new String[] { "jashda", "hdaskd" }));
		comboBoxSchichtAndererMA.setEnabled(false);
		comboBoxSchichtAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxSchichtAndererMA.setBounds(249, 409, 136, 26);
		contentPane.add(comboBoxSchichtAndererMA);

		
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
		
		setVisible(true);

	}
}
