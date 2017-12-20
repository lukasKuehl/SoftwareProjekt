package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WochenplanLoeschenView extends JFrame {

	private JPanel contentPane =null;;
	private JLabel lblWochenplanLschen, lblBitteAuswhlen =null;
	private  JList listWochenplaene =null;
	private JButton  btnLschen = null;
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;

	
		public WochenplanLoeschenView(Einsatzplanview myView, Einsatzplanmodel myModel, EinsatzplanController myController) {
			this.myView = myView;
			this.myController = myController;
			this.myModel = myModel;
		setTitle("Wochenplan löschen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800,600);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//JLISTE MIT WP NR ERSTELLEN 
		// Linked List mit For EACH SCHLEIFE AUSGEBEN
		
		lblWochenplanLschen = new JLabel("Wochenplan l\u00F6schen");
		lblWochenplanLschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		lblWochenplanLschen.setBounds(51, 61, 243, 27);
		contentPane.add(lblWochenplanLschen);
		
		listWochenplaene = new JList();
		listWochenplaene.setBorder(new LineBorder(new Color(0, 0, 0)));
		listWochenplaene.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listWochenplaene.setBounds(51, 139, 323, 356);
		contentPane.add(listWochenplaene);
		
		btnLschen = new JButton("löschen");
		btnLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if( e.getSource() == btnLschen) {
			int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die den Wochenplan wirklich löschen?", null, JOptionPane.YES_NO_CANCEL_OPTION);
			if (eingabe== 0) {
				
				//ACTION PERFORMED METHODE + EINSATZPLAN VIEW übergabe der index Nr
			}else {
				System.exit(0);
			}
			}
			}});
		btnLschen.setBounds(426, 468, 131, 27);
		contentPane.add(btnLschen);
		
		lblBitteAuswhlen = new JLabel("Bitte auswählen");
		lblBitteAuswhlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblBitteAuswhlen.setBounds(51, 109, 143, 26);
		contentPane.add(lblBitteAuswhlen);
		
		
		
		setVisible(true);
	

		
	}
}
