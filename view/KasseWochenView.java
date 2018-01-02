package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;
import model.Einsatzplanmodel;

//Klassenbeschreibung fehlt!

//Autoren der Methoden fehlen!

//Kommentare innerhalb der Methoden fehlen!

//Implementierung von ActionListener überflüssig, siehe vorherige Klassen!

//Hilfsklassen sind nicht public!
public class KasseWochenView extends JFrame implements ActionListener {

	private JPanel contentPane, pnlMenuBar;
	private JTable table;
	private JTextField txtBisTermin, txtVonTermin, textUhrzeitTerminV, textUhrzeitTerminB;
	private JMenuBar menuBar;
	private JMenu mnNewMenuIcon, mnWoche, mnSchicht, mnTermin, mnKrankmeldung, mnBenutzerrolle;
	private JMenuItem mntmWocheLoeschen, mntmWocheVerschicken, mntmWocheErstellen, mntmSchichtBearbeiten,
			mntmTerminErstellen, mntmTerminLoeschen, mntmKrankErstellen, mntmKrankLoeschen, mntmBenutzerZuweisen;
	public JLabel lblKW1;
	private String username;
	private int currentKW = 0;
	
	private JButton btnRechts, btnLinks;
	private Einsatzplanmodel myModel = null;
	private EinsatzplanController myController = null;
	private Einsatzplanview myView = null;
	private JTable tbleWochenplan;

	//siehe AnmeldungView!
	
	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KasseWochenView window = new KasseWochenView(new Einsatzplanmodel(), 
							new EinsatzplanController(new Einsatzplanmodel()), null);
					// window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//
	
	/**
	 * Create the application.
	 */
	public KasseWochenView(Einsatzplanmodel myModel, EinsatzplanController myController, Einsatzplanview myView) {
		this.myController = myController;
		this.myModel = myModel;
		this.myView = myView;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Einsatzplan Kassenbüro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1538, 864);

		// MenuBar mit den ganzen Unterpunkten - Variablen sind public

		menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(62, 0, 0, 0));
		menuBar.setFont(new Font("Verdana", Font.PLAIN, 26));
		setJMenuBar(menuBar);

		//Auskommentierte Anweisungen können weg
		
		/*
		 * mnNewMenuIcon = new JMenu(""); mnNewMenuIcon.setIcon(new
		 * ImageIcon("C:\\Users\\Admin\\Desktop\\Chrysanthemum.jpg"));
		 * mnNewMenuIcon.setFont(new Font("Segoe UI", Font.PLAIN, 5));
		 * menuBar.add(mnNewMenuIcon);
		 */

		mnWoche = new JMenu("Wochenplan");
		mnWoche.setFont(new Font("VerdanaI", Font.PLAIN, 26));
		menuBar.add(mnWoche);

		mntmWocheErstellen = new JMenuItem("Erstellen");
		mntmWocheErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new KasseWocheErstellenView(myModel, myView, myController);
			}
		});
		mntmWocheErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmWocheErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnWoche.add(mntmWocheErstellen);

		mntmWocheLoeschen = new JMenuItem("Löschen");
		mntmWocheLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new WochenplanLoeschenView(myView, myModel, myController);
			}
		});
		mntmWocheLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmWocheLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnWoche.add(mntmWocheLoeschen);

		mntmWocheVerschicken = new JMenuItem("Verschicken");
		mntmWocheVerschicken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new KasseWocheSendenView(myModel, myView, myController);
			}
		});
		mntmWocheVerschicken.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmWocheVerschicken.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnWoche.add(mntmWocheVerschicken);

		mnSchicht = new JMenu("Schicht");
		mnSchicht.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnSchicht);

		mntmSchichtBearbeiten = new JMenuItem("Schicht bearbeiten");
		mntmSchichtBearbeiten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new KasseSchichtView(myModel, myView, myController);
			}
		});
		mntmSchichtBearbeiten.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmSchichtBearbeiten.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnSchicht.add(mntmSchichtBearbeiten);

		mnTermin = new JMenu("Termin");
		mnTermin.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnTermin);

		mntmTerminErstellen = new JMenuItem("Erstellen");
		mntmTerminErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TerminErstellenView(myView, myModel, myController);
			}
		});
		mntmTerminErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTerminErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTermin.add(mntmTerminErstellen);

		mntmTerminLoeschen = new JMenuItem("Löschen");
		mntmTerminLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TerminLoeschenView(myView, myModel, myController);
			}
		});
		mntmTerminLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTerminLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTermin.add(mntmTerminLoeschen);

		mnKrankmeldung = new JMenu("Krankmeldung");
		mnKrankmeldung.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnKrankmeldung);

		mntmKrankErstellen = new JMenuItem("Erstellen");
		mntmKrankErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new KrankmeldungErstellenView(myView, myModel, myController);
			}
		});
		mntmKrankErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmKrankErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnKrankmeldung.add(mntmKrankErstellen);

		mntmKrankLoeschen = new JMenuItem("Löschen");
		mntmKrankLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new KrankmeldungLoeschenView(myView, myModel, myController);
			}
		});
		mntmKrankLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmKrankLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnKrankmeldung.add(mntmKrankLoeschen);

		mnBenutzerrolle = new JMenu("Benutzerrolle");
		mnBenutzerrolle.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnBenutzerrolle);

		mntmBenutzerZuweisen = new JMenuItem("zuweisen");
		mntmBenutzerZuweisen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new KasseBenutzerrolleView(myModel, myView, myController);
			}
		});
		mntmBenutzerZuweisen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmBenutzerZuweisen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnBenutzerrolle.add(mntmBenutzerZuweisen);
		getContentPane().setLayout(null);

		//Warum KW 1? Es gibt doch keine anderen Label in denen die KW steht
		lblKW1 = new JLabel("");
		lblKW1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblKW1.setBounds(109, 11, 136, 30);
		ArrayList<String> wochenplaene = myController.getWochenplaene();
		lblKW1.setText(wochenplaene.get(0).toString());
		getContentPane().add(lblKW1);

		btnRechts = new JButton("");
		btnRechts.setContentAreaFilled(false);
		btnRechts.setBorderPainted(false);
		btnRechts.setOpaque(false);
		btnRechts.setIcon(new ImageIcon("view/right.png"));
		btnRechts.setBounds(255, 11, 32, 23);
		btnRechts.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {						
				currentKW++;
				if(wochenplaene.size() <= currentKW){
					currentKW--;
				}else{
					lblKW1.setText(wochenplaene.get(currentKW).toString());
					
					getContentPane().remove(tbleWochenplan);
					tbleWochenplan = myController.generiereWochenplanView(wochenplaene.get(currentKW));
					tbleWochenplan.setBounds(24, 81, 1439, 676);
					
					//folgende Anweisungen fehlen um die columns etc. anzuzeigen
										
					tbleWochenplan.getTableHeader().setSize(tbleWochenplan.getTableHeader().getPreferredSize());
					tbleWochenplan.setSize(tbleWochenplan.getPreferredSize());
					tbleWochenplan.setPreferredScrollableViewportSize(tbleWochenplan.getPreferredScrollableViewportSize());
					tbleWochenplan.setFillsViewportHeight(true);
					JScrollPane jsp = new JScrollPane(tbleWochenplan);						
					
					getContentPane().add(jsp);
				}
			}
		});
		getContentPane().add(btnRechts);

		btnLinks = new JButton("");
		btnLinks.setBorderPainted(false);
		btnLinks.setContentAreaFilled(false);
		btnLinks.setIcon(new ImageIcon("view/left.png"));
		btnLinks.setBounds(49, 11, 50, 23);
		btnLinks.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				currentKW--;
				if(currentKW < 0){
					currentKW++;
				}else{
					lblKW1.setText(wochenplaene.get(currentKW).toString());
					
					getContentPane().remove(tbleWochenplan);
					tbleWochenplan = myController.generiereWochenplanView(wochenplaene.get(currentKW));
					tbleWochenplan.setBounds(24, 81, 1439, 676);
					
					//siehe oben!
					
					getContentPane().add(tbleWochenplan);
				}
					
			}
		});
		
		getContentPane().add(btnLinks);
		
		//Für das anpassen der JTable solltest du ne eingene Methode machen, dass steht hier jetzt schon 3 mal --> Fehleranfälligkeit und doppelter Code wird verbessert
		
		tbleWochenplan = myController.generiereWochenplanView(wochenplaene.get(currentKW));
		tbleWochenplan.setBounds(24, 81, 1439, 676);
		
		getContentPane().add(tbleWochenplan);
		
		setVisible(true);
	}

	//Überflüssige actionPerformed-Methode
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
