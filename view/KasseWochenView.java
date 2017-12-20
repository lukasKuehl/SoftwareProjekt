package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

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

	private JFrame frame;
	private JButton btnRechts, btnLinks;
	private Einsatzplanmodel myModel = null;
	private EinsatzplanController myController = null;
	private Einsatzplanview myView = null;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KasseWochenView window = new KasseWochenView(null, null, null, null);
					// window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KasseWochenView(String username, Einsatzplanmodel myModel, EinsatzplanController myController,
			Einsatzplanview myView) {
		this.username = username;
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

		/*
		 * mnNewMenuIcon = new JMenu(""); mnNewMenuIcon.setIcon(new
		 * ImageIcon("C:\\Users\\Admin\\Desktop\\Chrysanthemum.jpg"));
		 * mnNewMenuIcon.setFont(new Font("Segoe UI", Font.PLAIN, 5));
		 * menuBar.add(mnNewMenuIcon);
		 */

		mnWoche = new JMenu("Wochenplan");
		mnWoche.setFont(new Font("VerdanaI", Font.PLAIN, 26));
		menuBar.add(mnWoche);

		mntmWocheErstellen = new JMenuItem("erstellen");
		mntmWocheErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new KasseWocheErstellenView(myModel, myView, myController);
			}
		});
		mntmWocheErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmWocheErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnWoche.add(mntmWocheErstellen);

		mntmWocheLoeschen = new JMenuItem("löschen");
		mntmWocheLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new WochenplanLoeschenView();
				// WochenplanLoeschenView frame = new WochenplanLoeschenView();
				// frame.setVisible(true); Muss im Konstruktor auf visible
				// gestellt werden
			}
		});
		mntmWocheLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmWocheLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnWoche.add(mntmWocheLoeschen);

		mntmWocheVerschicken = new JMenuItem("verschicken");
		mntmWocheVerschicken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new KasseWocheSendenView();
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
				new KasseSchichtView();
			}
		});
		mntmSchichtBearbeiten.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmSchichtBearbeiten.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnSchicht.add(mntmSchichtBearbeiten);

		mnTermin = new JMenu("Termin");
		mnTermin.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnTermin);

		mntmTerminErstellen = new JMenuItem("erstellen");
		mntmTerminErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TerminErstellenView(myModel, myController);
			}
		});
		mntmTerminErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTerminErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTermin.add(mntmTerminErstellen);

		mntmTerminLoeschen = new JMenuItem("löschen");
		mntmTerminLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TerminLoeschenView(myModel, myController);
			}
		});
		mntmTerminLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTerminLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTermin.add(mntmTerminLoeschen);

		mnKrankmeldung = new JMenu("Krankmeldung");
		mnKrankmeldung.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnKrankmeldung);

		mntmKrankErstellen = new JMenuItem("erstellen");
		mntmKrankErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new KrankmeldungErstellenView(myView, myModel);
			}
		});
		mntmKrankErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmKrankErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnKrankmeldung.add(mntmKrankErstellen);

		mntmKrankLoeschen = new JMenuItem("löschen");
		mntmKrankLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new KrankmeldungLoeschenView();
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
				new KasseBenutzerrolleView(null, null);
			}
		});
		mntmBenutzerZuweisen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmBenutzerZuweisen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnBenutzerrolle.add(mntmBenutzerZuweisen);
		getContentPane().setLayout(null);

		lblKW1 = new JLabel("");
		//myController.getWochenplaene();
		//ArrayList<String> WochenPlaene = myController.getWochenplaene();
		//TreeMap<Integer, String> WochenMap = new TreeMap<Integer, String>();
		//for (int i = 0; i < WochenPlaene.size(); i++){
			
		//}
			// Map füllen, Jlabel variable setzen um diese zu erneuern

			lblKW1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblKW1.setBounds(109, 11, 136, 30);
		getContentPane().add(lblKW1);

		btnRechts = new JButton("");
		btnRechts.setContentAreaFilled(false);
		btnRechts.setBorderPainted(false);
		btnRechts.setOpaque(false);
		btnRechts.setIcon(new ImageIcon("C:\\Users\\Admin\\git\\SoftwareProjekt\\view\\right.png"));
		btnRechts.setBounds(255, 11, 32, 23);
		getContentPane().add(btnRechts);

		btnLinks = new JButton("");
		btnLinks.setBorderPainted(false);
		btnLinks.setContentAreaFilled(false);
		btnLinks.setIcon(new ImageIcon("C:\\Users\\Admin\\git\\SoftwareProjekt\\view\\left.png"));
		btnLinks.setBounds(49, 11, 50, 23);
		getContentPane().add(btnLinks);

		setVisible(true);
	}
}
