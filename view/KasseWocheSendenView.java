package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controller.EinsatzplanController;
import data.Wochenplan;
import model.Einsatzplanmodel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
/**
 * @author Darius Panteli
 * @info Die Klasse KasseWocheSendenView dient dazu, dem Nutzer eine graphische
 *       Obefläche anzubieten, um einen jeweils ausgewählten
 *       Wochenplan an das gesamte Team zu schicken
 */
	class KasseWocheSendenView extends JFrame {
	private JComboBox cbWochenplaene;
	private JLabel lblWochenplanVersenden,lblAuswahl;
	private JButton btnBesttigen;
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private WindowListener windowListener;
	
	protected KasseWocheSendenView(Einsatzplanmodel myModel, Einsatzplanview myView, EinsatzplanController myController) {
		getContentPane().setBackground(Color.WHITE);
		this.myView = myView;
		this.myModel = myModel;
		this.myController = myController;
		initialize();
	}

	private void initialize() {
		
		setTitle("Wochenplan versenden");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 600,480);
		getContentPane().setLayout(null);
		//Falls der Benutzer das Fenster über den "X" Button schließen sollte, wird eine neue KasseWochenView
		//geöffnet
		windowListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				myView.update();
				dispose();
			}
		};
		addWindowListener(windowListener);
		
		lblWochenplanVersenden = new JLabel("Wochenplan versenden");
		lblWochenplanVersenden.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblWochenplanVersenden.setBounds(12, 13, 363, 71);
		getContentPane().add(lblWochenplanVersenden);
		
		lblAuswahl = new JLabel("Bitte w\u00E4hlen Sie den zu versendeten Wochenplan aus:");
		lblAuswahl.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAuswahl.setBounds(90, 181, 451, 16);
		getContentPane().add(lblAuswahl);
		
		cbWochenplaene = new JComboBox<Object>();
		cbWochenplaene.setBounds(192, 225, 231, 22);
		//Es werden alle aktuell hinterlegten Wochenpläne in der Datenbank abgerufen
		//und die ComboBox damit befüllt
		LinkedList<Wochenplan> alleWochenplaene = this.myModel.getWochenplaene();
		alleWochenplaene.forEach((temp) -> { 
			cbWochenplaene.addItem("KW " +temp.getWpnr()); 
			});
		getContentPane().add(cbWochenplaene);
		
		btnBesttigen = new JButton("best\u00E4tigen");
		btnBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Sind Sie sicher?", "Warnung",
						JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					//Bei Bestätigung wird der ausgewählte Wochenplan übergeben
					//Der Controller erwartet den Usernamen (welcher er sich aus der EinsatzplanView entnimmt),
					//und jeweils den ausgewählten Wochenplan
				
						if(myController.verschickeWochenplan(myView.getUsername(), "KW" + gibWochenplan(), myController.generiereWochenplanView("KW" + gibWochenplan()))){
							myView.update();
							dispose();
						}else{
					
						}
						
					} else {
						
					}
				}
		});
		btnBesttigen.setBounds(254, 280, 97, 25);
		getContentPane().add(btnBesttigen);
		setVisible(true);
	}
	
	// Früher entstanden Fehler, weshalb wir es so belassen haben und "KW" einzelnd oben hinzugefügt haben
	protected String gibWochenplan() {
		
		String[] teile = cbWochenplaene.getSelectedItem().toString().split(" ");
		return teile[1];
	}
}
