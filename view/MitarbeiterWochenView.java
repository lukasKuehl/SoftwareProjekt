package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;
import java.awt.Color;
/**
 * @author Darius Panteli
 * @info Die Klasse MitarbeiterWochenView dient dazu, einem Nutzer eine graphische
 *       Obefläche anzubieten, um Zugriff auf all die Möglichen Funktionen
 *       zu bekommen, die ein Nutzer ohne weitere besonderen Rechte hat. Davon abgesehen
 *       wird dem Nutzer der Wochenplan angezeigt
 */


	class MitarbeiterWochenView extends JFrame{

	private JPanel contentPane, pnlMenuBar;
	private JTable tbleWochenplan;
	private JTextField txtBisTermin, txtVonTermin, textUhrzeitTerminV, textUhrzeitTerminB;
	private JMenuBar menuBar;
	private JMenu mnNewMenuIcon, mnWoche, mnTermin, mnTauschanfrage;
	private JMenuItem mntmWocheAnzeigen, mntmTerminErstellen, mntmTerminLoeschen, mntmTauschanfrageErstellen,
			mntmTauschanfrageLoeschen, mntmTauschanfrageAnzeigen;
	private JLabel lblKW;
	private JButton btnRechts, btnLinks;
	private Einsatzplanmodel myModel = null;
	private EinsatzplanController myController = null;
	private Einsatzplanview myView = null;
	private int currentKW = 0;
	private String username;
	private JScrollPane jsp;
	private JMenu mnAbmelden;
	private JMenuItem mntmBenutzerAbmelden;

	protected MitarbeiterWochenView(Einsatzplanmodel myModel, EinsatzplanController myController, Einsatzplanview myView) {
		getContentPane().setBackground(Color.WHITE);
		this.myController = myController;
		this.myModel = myModel;
		this.myView = myView;
		initialize();
	}


	private void initialize() {
		setTitle("Einsatzplan Mitarbeiter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1538, 864);

		menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(62, 0, 0, 0));
		menuBar.setFont(new Font("Verdana", Font.PLAIN, 26));
		setJMenuBar(menuBar);

		mnTermin = new JMenu("Termin");
		mnTermin.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnTermin);

		mntmTerminErstellen = new JMenuItem("Erstellen");
		mntmTerminErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TerminErstellenView(myView, myModel, myController);
				dispose();
			}
		});
		mntmTerminErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTerminErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTermin.add(mntmTerminErstellen);

		mntmTerminLoeschen = new JMenuItem("Löschen");
		mntmTerminLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TerminLoeschenView(myView, myModel, myController);
				dispose();
			}
		});
		mntmTerminLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTerminLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTermin.add(mntmTerminLoeschen);

		mnTauschanfrage = new JMenu("Tauschanfrage");
		mnTauschanfrage.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnTauschanfrage);

		mntmTauschanfrageAnzeigen = new JMenuItem("Anzeigen");
		mntmTauschanfrageAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TauschanfrageAnzeigenView(myView, myModel, myController);
				dispose();
			}
		});
		mntmTauschanfrageAnzeigen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTauschanfrageAnzeigen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTauschanfrage.add(mntmTauschanfrageAnzeigen);

		mntmTauschanfrageErstellen = new JMenuItem("Erstellen");
		mntmTauschanfrageErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TauschanfrageErstellenView(myModel, myView, myController);
				dispose();
			}
		});
		mntmTauschanfrageErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTauschanfrageErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTauschanfrage.add(mntmTauschanfrageErstellen);

		mntmTauschanfrageLoeschen = new JMenuItem("Löschen");
		mntmTauschanfrageLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TauschanfrageLoeschenView(myView, myModel, myController);
				dispose();
			}
		});
		mntmTauschanfrageLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTauschanfrageLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTauschanfrage.add(mntmTauschanfrageLoeschen);
		
		mnAbmelden = new JMenu("Abmelden");
		mnAbmelden.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnAbmelden);
		
		mntmBenutzerAbmelden = new JMenuItem("Benutzer abmelden");
		mntmBenutzerAbmelden.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				myController.benutzerAbmelden(username);
				dispose();
				new AnmeldungView(myController, myModel, myView);
				
			}
		});
		mntmBenutzerAbmelden.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnAbmelden.add(mntmBenutzerAbmelden);
		getContentPane().setLayout(null);

		////Dynamisches Label, damit jeweils der Name des aktuell angezeigten Wochenplans zu sehen ist
		lblKW = new JLabel("");
		lblKW.setBounds(109, 11, 136, 30);
		ArrayList<String> wochenplaene = myController.getWochenplaene();
		lblKW.setText(wpStart(wochenplaene));
		getContentPane().add(lblKW);

		
		btnRechts = new JButton("");
		btnRechts.setBorderPainted(false);
		btnRechts.setContentAreaFilled(false);
		btnRechts.setBounds(255, 11, 32, 23);
		btnRechts.setIcon(new ImageIcon("view/right.png"));
		btnRechts.addActionListener(new ActionListener() {
			//Sofern es einen nachfolgenden Wochenplan gibt, wird der nachfolgende Wochenplan angezeigt
			//nach betätigung des Rechten Buttons
			@Override
			public void actionPerformed(ActionEvent e) {				
				currentKW++;
				if (wochenplaene.size() <= currentKW) {
					currentKW--;
				} else {
					generiereTabelle();
				}
			}
		});
		getContentPane().add(btnRechts);

		btnLinks = new JButton("");
		btnLinks.setContentAreaFilled(false);
		btnLinks.setBorderPainted(false);
		btnLinks.setBounds(49, 11, 50, 23);
		btnLinks.setIcon(new ImageIcon("view/left.png"));
		btnLinks.addActionListener(new ActionListener() {
			//Sofern es einen vorherigen Wochenplan gibt, wird der vorherige Wochenplan angezeigt
			//nach betätigung des Linken Buttons
			@Override
			public void actionPerformed(ActionEvent e) {			
				currentKW--;
				if (currentKW < 0) {
					currentKW++;
				} else {
					generiereTabelle();
				}

			}
		});
		getContentPane().add(btnLinks);
		generiereTabelle();		
		setVisible(true);
	}
	
	//Methode prüft beim Start die Wochenpläne, wenn keine Vorhanden sind wird Platzhalter eingefügt	
	private String wpStart(ArrayList<String> wochenplaene){
		String a = null;
		if(wochenplaene.isEmpty() == false){
		wochenplaene.get(0).toString();
		}else{
			a = "Keine Wochenpläne";
		}
		return a;
	}
	
	//Methode um den Wochenplan (inkl. Daten) in der View anzeigen zu lassen 
	private void generiereTabelle(){
		ArrayList<String> wochenplaene = myController.getWochenplaene();
		if(wochenplaene.isEmpty() == false){
		lblKW.setText(wochenplaene.get(currentKW).toString());
		tbleWochenplan = myController.generiereWochenplanView(wochenplaene.get(currentKW));
		tbleWochenplan.getTableHeader().setSize(tbleWochenplan.getTableHeader().getPreferredSize());
		tbleWochenplan.setSize(tbleWochenplan.getPreferredSize());
		tbleWochenplan.setPreferredScrollableViewportSize(tbleWochenplan.getPreferredScrollableViewportSize());
		tbleWochenplan.setFillsViewportHeight(true);
		JScrollPane jsp = new JScrollPane(tbleWochenplan);			
		jsp.setBounds(24, 81, 1439, 676);
		getContentPane().add(jsp);
		}else{
		}
	}
	
	

	
}
