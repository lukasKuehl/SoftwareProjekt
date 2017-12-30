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
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JButton;

//Klassenbeschreibung fehlt!

//Autoren der einzelnen Methoden fehlen!

//Kommentare innerhalb der Methoden fehlen!

//Hilfsklassen sind nicht public!
public class KasseWocheSendenView extends JFrame {
	private JComboBox cbWochenplaene;
	private JLabel lblWochenplanVersenden,lblAuswahl;
	private JButton btnBesttigen;
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	
	//Siehe AnmeldungView!
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KasseWocheSendenView kasseWocheSendenView = new KasseWocheSendenView(new Einsatzplanmodel(), null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//


	public KasseWocheSendenView(Einsatzplanmodel myModel, Einsatzplanview myView, EinsatzplanController myController) {
		this.myView = myView;
		this.myModel = myModel;
		this.myController = myController;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setTitle("Wochenplan versenden");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 600,480);
		getContentPane().setLayout(null);
		
		lblWochenplanVersenden = new JLabel("Wochenplan versenden");
		lblWochenplanVersenden.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblWochenplanVersenden.setBounds(12, 13, 363, 71);
		getContentPane().add(lblWochenplanVersenden);
		
		lblAuswahl = new JLabel("Bitte w\u00E4hlen Sie den zu versendeten Wochenplan aus:");
		lblAuswahl.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAuswahl.setBounds(90, 181, 451, 16);
		getContentPane().add(lblAuswahl);
		
		//siehe vorherige Klassen!
		cbWochenplaene = new JComboBox();
		cbWochenplaene.setBounds(192, 225, 231, 22);
		LinkedList<Wochenplan> alleWochenplaene = this.myModel.getWochenplaene();
		alleWochenplaene.forEach((temp) -> { 
			cbWochenplaene.addItem("KW " +temp.getWpnr()); 
			});
		getContentPane().add(cbWochenplaene);
		//
		
		btnBesttigen = new JButton("best\u00E4tigen");
		btnBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Sind Sie sicher?", "Warnung",
						JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
						//Methode im Controller muss aufgerufen werden!
						//Außerdem muss kontrolliert werden, ob das Senden erfolgreich war, bevor das Fenster geschlossen wird
					
						//myController.verschickeWochenplan(myView.getUsername(), gibWochenplan(), wochenplan)
						dispose();
					} else {
						//Fehlermeldung überflüssig --> Benutzer hat abgebrochen
						
						//JLabel Fehlermeldung erstellen (siehe)
						//lblFehlermeldung.setText("Fehler beim Erstellen des Wochenplans. Bitte überprüfen Sie Ihre Eingaben.");

					}
				}
		});
		btnBesttigen.setBounds(254, 280, 97, 25);
		getContentPane().add(btnBesttigen);
		setVisible(true);
	}
	public String gibWochenplan() {
		
		//.trim() ist besser
		String[] teile = cbWochenplaene.getSelectedItem().toString().split(" ");
		return teile[1];
	}
}
