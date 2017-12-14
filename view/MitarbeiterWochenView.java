package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MitarbeiterWochenView extends JFrame implements ActionListener{


	private JPanel contentPane , pnlMenuBar;
	private JTable table;
	private JTextField txtBisTermin, txtVonTermin, textUhrzeitTerminV, textUhrzeitTerminB;
	private JMenuBar menuBar;
	private JMenu mnNewMenuIcon, mnWoche, mnTermin, mnTauschanfrage;
	private JMenuItem mntmWocheAnzeigen,
	mntmTerminErstellen, mntmTerminLoeschen, mntmTauschanfrageErstellen, mntmTauschanfrageLoeschen,
	mntmTauschanfrageAnzeigen;
	private JButton btnRechts,btnLinks;
	
	public JLabel lblKW1;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MitarbeiterWochenView window = new MitarbeiterWochenView();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MitarbeiterWochenView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Einsatzplan Mitarbeiter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1538,864);
		
		menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(62, 0, 0, 0));
		menuBar.setFont(new Font("Verdana", Font.PLAIN, 26));
		setJMenuBar(menuBar);
		
		/*mnNewMenuIcon = new JMenu("");
		mnNewMenuIcon.setIcon(new ImageIcon("C:\\Users\\Ramona\\Desktop\\returns icon.png"));
		mnNewMenuIcon.setFont(new Font("Segoe UI", Font.PLAIN, 5));
		menuBar.add(mnNewMenuIcon);*/
		

		
		mnTermin = new JMenu("Termin");
		mnTermin.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnTermin);
		
		mntmTerminErstellen = new JMenuItem("erstellen");
		mntmTerminErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TerminErstellenView(null, null);
			}
		});
		mntmTerminErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTerminErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTermin.add(mntmTerminErstellen);
		
		mntmTerminLoeschen = new JMenuItem("löschen");
		mntmTerminLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TerminLoeschenView(null, null);
			}
		});
		mntmTerminLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTerminLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTermin.add(mntmTerminLoeschen);
		
		mnTauschanfrage = new JMenu("Tauschanfrage");
		mnTauschanfrage.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnTauschanfrage);
		
		mntmTauschanfrageAnzeigen = new JMenuItem("anzeigen");
		mntmTauschanfrageAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new TauschanfrageAnzeigenView();
			}
		});
		mntmTauschanfrageAnzeigen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTauschanfrageAnzeigen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTauschanfrage.add(mntmTauschanfrageAnzeigen);
		
		mntmTauschanfrageErstellen = new JMenuItem("erstellen");
		mntmTauschanfrageErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TauschanfrageErstellenView();
			}
		});
		mntmTauschanfrageErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTauschanfrageErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTauschanfrage.add(mntmTauschanfrageErstellen);
		
		mntmTauschanfrageLoeschen = new JMenuItem("löschen");
		mntmTauschanfrageLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TauschanfrageLoeschenView();
			}
		});
		mntmTauschanfrageLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTauschanfrageLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTauschanfrage.add(mntmTauschanfrageLoeschen);
		
		lblKW1 = new JLabel("");
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
