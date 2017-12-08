package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
		
		mnWoche = new JMenu("Wochenplan");
		mnWoche.setFont(new Font("VerdanaI", Font.PLAIN, 26));
		menuBar.add(mnWoche);
		
		mntmWocheAnzeigen = new JMenuItem("anzeigen");
		mntmWocheAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new Test();
			}
		});
		mntmWocheAnzeigen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmWocheAnzeigen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnWoche.add(mntmWocheAnzeigen);
		

				
		mnTermin = new JMenu("Termin");
		mnTermin.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnTermin);
		
		mntmTerminErstellen = new JMenuItem("erstellen");
		mntmTerminErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTerminErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTermin.add(mntmTerminErstellen);
		
		mntmTerminLoeschen = new JMenuItem("löschen");
		mntmTerminLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTerminLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTermin.add(mntmTerminLoeschen);
		
		mnTauschanfrage = new JMenu("Tauschanfrage");
		mnTauschanfrage.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnTauschanfrage);
		
		mntmTauschanfrageErstellen = new JMenuItem("erstellen");
		mntmTauschanfrageErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTauschanfrageErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTauschanfrage.add(mntmTauschanfrageErstellen);
		
		mntmTauschanfrageLoeschen = new JMenuItem("löschen");
		mntmTauschanfrageLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTauschanfrageLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTauschanfrage.add(mntmTauschanfrageLoeschen);
		
		mntmTauschanfrageAnzeigen = new JMenuItem("anzeigen");
		
		
		
		setVisible(true);
		
	}

}
