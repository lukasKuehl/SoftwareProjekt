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
import javax.swing.JTextArea;

public class TauschanfrageAnzeigenView extends JFrame {

	private JPanel contentPane = null;
	private JTextField textFieldDatum =null;
	private JLabel lblTauschanfrageAnzeigen, lblMitarbeiterAuswaehlen  =null;
	private JButton btnVersenden = null;
	private JButton btnAnnehmen;
	private JComboBox cmbBoxMa = null;
	
		protected TauschanfrageAnzeigenView() {
		setTitle("Tauschanfrage annehmen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800,600);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		 lblTauschanfrageAnzeigen = new JLabel("Tauschanfragen");
		lblTauschanfrageAnzeigen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTauschanfrageAnzeigen.setBounds(62, 91, 198, 26);
		contentPane.add(lblTauschanfrageAnzeigen);
		
		btnAnnehmen = new JButton("annehmen");
		btnAnnehmen.setFont(new Font("Verdana", Font.PLAIN, 18));
		btnAnnehmen.setBounds(499, 419, 136, 38);
		contentPane.add(btnAnnehmen);
		
		setVisible(true);
		
		
	}
	}