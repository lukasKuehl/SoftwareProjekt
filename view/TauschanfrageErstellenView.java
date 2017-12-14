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
	private JLabel lblSchichtAuswhlen,  lblTauschanfrageStellen, lblDatumEintragen, lblMitarbeiterAuswaehlen  =null;
	private JButton btnVersenden = null;
	private JComboBox cmbBoxSchicht, cmbBoxMa = null;
	
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
		
		lblMitarbeiterAuswaehlen = new JLabel("Tauschmitarbeiter auswähhlen");
		lblMitarbeiterAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 21));
		lblMitarbeiterAuswaehlen.setBounds(62, 235, 385, 26);
		contentPane.add(lblMitarbeiterAuswaehlen);
		
		JComboBox cmbBoxMa = new JComboBox();
	
		// Mitarbeiter in der JComboBox hinterlegen
		//Array List
		// 
		cmbBoxMa.setBounds(499, 306, 136, 32);
		contentPane.add(cmbBoxMa);
		
		lblSchichtAuswhlen = new JLabel("Schicht auswählen");
		lblSchichtAuswhlen.setFont(new Font("Verdana", Font.PLAIN, 21));
		lblSchichtAuswhlen.setBounds(62, 304, 308, 26);
		contentPane.add(lblSchichtAuswhlen);
		
		lblDatumEintragen = new JLabel("Datum eintragen");
		lblDatumEintragen.setFont(new Font("Verdana", Font.PLAIN, 21));
		lblDatumEintragen.setBounds(62, 160, 265, 26);
		contentPane.add(lblDatumEintragen);
		
		btnVersenden = new JButton("versenden");
		btnVersenden.setFont(new Font("Verdana", Font.PLAIN, 18));
		btnVersenden.setBounds(499, 419, 136, 38);
		contentPane.add(btnVersenden);
		
		textFieldDatum = new JTextField();
		textFieldDatum.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDatum.setFont(new Font("Verdana", Font.PLAIN, 21));
		textFieldDatum.setText("01.01.2018");
		textFieldDatum.setBounds(449, 157, 186, 32);
		contentPane.add(textFieldDatum);
		textFieldDatum.setColumns(10);
		
		cmbBoxSchicht = new JComboBox();
		// Schichten einfügen - aus Datenbank ziehen? 
		// Array List
		
		cmbBoxSchicht.setBounds(499, 237, 136, 32);
		contentPane.add(cmbBoxSchicht);
		
	}
}
