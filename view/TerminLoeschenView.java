package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.TerminStrg;
import data.Termin;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TerminLoeschenView extends JFrame {

	private JPanel contentPane;
	private JLabel labelTerminLoeschen;
	private JPanel panelTermin;
	private JList list =null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TerminLoeschenView frame = new TerminLoeschenView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TerminLoeschenView() {
		setTitle("Einsatzplan");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1538,864);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTerninLschen = new JLabel("Termin löschen");
		lblTerninLschen.setBounds(76, 158, 199, 26);
		contentPane.add(lblTerninLschen);
		
		MaListeAnzeigen();
		
		JButton btnBesttigen = new JButton("bestätigen");
		btnBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null, JOptionPane.YES_NO_CANCEL_OPTION);
				//		System.out.print(eingabe); Kontrolle
						
						if (eingabe==0) {
								
						if (list.isSelectionEmpty()) {
							
			//			JOptionPane.ERROR_MESSAGE();		
						}
						
						else {
							
						}
							
							
							// an controller übergeben das er den ausgewählten Termin löscht 
							
						}
							
			
				
				
			}
		});
		btnBesttigen.setBounds(456, 485, 141, 35);
		contentPane.add(btnBesttigen);
	
		
		

	}
	
	protected void MaListeAnzeigen () {
	String s [] = {"Termin 1", "Termin 2"};  // Test 	s
	list = new JList(s);
	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	list.setBounds(76, 218, 282, 298);
	list.getModel();
	contentPane.add(list);
	}
}
