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
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

//Klassenbeschreibung fehlt!

//Kommentare innerhalb der Methoden fehlen!

//Hilfsklassen sind nicht public!
public class TauschanfrageAnzeigenView extends JFrame {

	private JPanel contentPane = null;
	private JTextField textFieldDatum = null;
	private JLabel lblTauschanfrageAnzeigen = null;
	private JButton btnAnnehmen = null;
	
	//JList<String> oder JList<Tauschanfrage> besser
	private JList<Object> listTauschanfragen = null;
	
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;
	private DefaultListModel<Object> modelTauschanfrage = null;
	private ArrayList<String> tl = null;

	/**
	 * @author Ramona Gerke
	 * @info Konstruktor der View Tauschanfrage anzeigen.
	 */

	protected TauschanfrageAnzeigenView(Einsatzplanview myView, Einsatzplanmodel myModel,
			EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Tauschanfrage annehmen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800, 600);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//Siehe oben!		
		listTauschanfragen = new JList<Object>();
		
		// tl = myController.getTauschanfragen(myView.getUsername());
		modelTauschanfrage = new DefaultListModel<Object>();
		listTauschanfragen.setBounds(72, 140, 315, 336);
		contentPane.add(listTauschanfragen);

		lblTauschanfrageAnzeigen = new JLabel("Tauschanfragen");
		lblTauschanfrageAnzeigen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTauschanfrageAnzeigen.setBounds(62, 91, 198, 26);
		contentPane.add(lblTauschanfrageAnzeigen);

		btnAnnehmen = new JButton("Annehmen");
		btnAnnehmen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnAnnehmen.setBounds(500, 500, 127, 25);
		contentPane.add(btnAnnehmen);

		setVisible(true);

		/**
		 * @author Ramona Gerke
		 * @info Action Performed Methode, die nach dem Bestätigen des Buttons
		 *       "Annehmen" ausgeführt wird,
		 * 		 Sie übergibt die TauschanfrageNr. der ausgwählten Tauschanfrage an den
		 *       Controller.
		 * 
		 */

		// Action Perfomed Methode

		btnAnnehmen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnAnnehmen) {
					int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Tauschanfrage annehmen?", null,
							JOptionPane.YES_NO_CANCEL_OPTION);
					
					//Siehe vorherige Klassen!
					if (eingabe == 0) {

						if (tl.isEmpty()) {
							JOptionPane.showMessageDialog(null,
									"Es wurde keine Tauschanfrage ausgewählt. Bitte wählen Sie eine Tauschanfrage aus.",
									"Error", JOptionPane.ERROR_MESSAGE);
						} else {
							
							//siehe TauschanfrageLoeschenView() IndexOutOfBound!
							
							String empfaengerName = null;
							int tauschanfrageNr = 0;
							String temp[] = new String[14];
							tl = myController.getTauschanfragen(myView.getUsername());
							for (String m : tl) {
								m.toString();
								m.trim();
								temp = m.split("-");
								String tauschanfrageNrS = temp[0].substring(10);
								tauschanfrageNr = Integer.parseInt(tauschanfrageNrS);
								String senderVorname = temp[1];
								String senderName = temp[2];
								String senderSchichtNr = temp[3];
								String senderWpNr = temp[4];
								String senderTbez = temp[5];
								String senderAnfangsuhrzeit = temp[6];
								String senderEnduhrzeit = temp[7];
								String empfaengerVorname = temp[8];
								empfaengerName = temp[9];
								String empfaengerSchichtNr = temp[10];
								String empfaengerWpBez = temp[11];
								String empfaengerTagBez = temp[12];
								String empfaengerAnfangsuhrzeit = temp[13];
								String emfaengerEnduhrzeit = temp[14];
							}
							myView.akzeptiereTauschanfrage(empfaengerName, tauschanfrageNr);
							
							//Erfolgsdialog/Rückmeldung für den Benutzer fehlt!
						}

					}

				}
			}

		});

	}
}