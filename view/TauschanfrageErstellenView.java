package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TauschanfrageErstellenView extends JFrame {

	private JPanel contentPane = null;
	private JTextField textFieldDatum =null;
	private JLabel lblTauschanfrageStellen, lblMitarbeiterAuswaehlen  =null;
	private JButton btnVersenden = null;
	private JComboBox cmbBoxWP, cmbBoxMa = null;
	private JLabel lblWochenplanAuswhlen;
	private JLabel lblTagAuswhlen;
	private JLabel lblSchichtAuswhlen;
	
		protected TauschanfrageErstellenView() {
		setTitle("Tauschanfrage erstellen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800,600);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		 lblTauschanfrageStellen = new JLabel("Tauschanfrage stellen");
		lblTauschanfrageStellen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTauschanfrageStellen.setBounds(62, 91, 385, 26);
		contentPane.add(lblTauschanfrageStellen);
		
		JComboBox cmbBoxTag = new JComboBox();
		cmbBoxTag.setFont(new Font("Verdana", Font.PLAIN, 15));
	
		// Mitarbeiter in der JComboBox hinterlegen
		//Array List
		// 
		cmbBoxTag.setBounds(249, 211, 136, 26);
		contentPane.add(cmbBoxTag);
		
		btnVersenden = new JButton("versenden");
		btnVersenden.setFont(new Font("Verdana", Font.PLAIN, 18));
		btnVersenden.setBounds(590, 495, 136, 36);
		contentPane.add(btnVersenden);
		
		cmbBoxWP = new JComboBox();
		// Schichten einfügen - aus Datenbank ziehen? 
		// Array List
		
		cmbBoxWP.setBounds(249, 174, 136, 26);
		contentPane.add(cmbBoxWP);
		
		lblWochenplanAuswhlen = new JLabel("Wochenplan auswählen");
		lblWochenplanAuswhlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblWochenplanAuswhlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblWochenplanAuswhlen.setBounds(62, 178, 188, 22);
		contentPane.add(lblWochenplanAuswhlen);
		
		lblTagAuswhlen = new JLabel("Tag \r\nausw\u00E4hlen");
		lblTagAuswhlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTagAuswhlen.setHorizontalAlignment(SwingConstants.LEFT);
		lblTagAuswhlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblTagAuswhlen.setBounds(62, 211, 178, 26);
		contentPane.add(lblTagAuswhlen);
		
		lblSchichtAuswhlen = new JLabel("Schicht ausw\u00E4hlen");
		lblSchichtAuswhlen.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSchichtAuswhlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblSchichtAuswhlen.setBounds(62, 248, 136, 26);
		contentPane.add(lblSchichtAuswhlen);
		
		JComboBox comboBoxSchicht = new JComboBox();
		comboBoxSchicht.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBoxSchicht.setBounds(249, 248, 136, 26);
		contentPane.add(comboBoxSchicht);
		
		JLabel lblMitWemWollen = new JLabel("Tauschdaten");
		lblMitWemWollen.setFont(new Font("Verdana", Font.BOLD, 15));
		lblMitWemWollen.setBounds(62, 347, 222, 14);
		contentPane.add(lblMitWemWollen);
		
		JLabel labelTagAndererMA = new JLabel("Tag \r\nausw\u00E4hlen");
		labelTagAndererMA.setVerticalAlignment(SwingConstants.BOTTOM);
		labelTagAndererMA.setHorizontalAlignment(SwingConstants.LEFT);
		labelTagAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelTagAndererMA.setBounds(62, 372, 178, 26);
		contentPane.add(labelTagAndererMA);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBox.setBounds(249, 372, 136, 26);
		contentPane.add(comboBox);
		
		JLabel labelSchichtAndererMA = new JLabel("Schicht ausw\u00E4hlen");
		labelSchichtAndererMA.setVerticalAlignment(SwingConstants.BOTTOM);
		labelSchichtAndererMA.setFont(new Font("Verdana", Font.PLAIN, 15));
		labelSchichtAndererMA.setBounds(62, 409, 136, 26);
		contentPane.add(labelSchichtAndererMA);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		comboBox_1.setBounds(249, 409, 136, 26);
		contentPane.add(comboBox_1);
		
		setVisible(true);
		
	}
}
