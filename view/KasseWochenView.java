package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class KasseWochenView extends JFrame implements ActionListener{

	private JPanel contentPane , pnlMenuBar;
	private JTable table;
	private JTextField txtBisTermin, txtVonTermin, textUhrzeitTerminV, textUhrzeitTerminB;
	private JMenuBar menuBar;
	private JMenu mnNewMenuIcon, mnWoche, mnTermin , mnKrankmeldung, mnBenutzerrolle;
	private JMenuItem mntmWocheAnzeigen, mntmWocheLoeschen, mntmWocheVerschicken, mntmWocheErstellen, 
	mntmTerminErstellen, mntmTerminLoeschen ,  mntmKrankErstellen, mntemKrankLoeschen, mntmBenutzerZuweisen;
	public JLabel lblKW1;

	private JFrame frame;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KasseWochenView window = new KasseWochenView();
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
	public KasseWochenView() {
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
		setBounds(100, 100, 1538,864);
		 
		// MenuBar mit den ganzen Unterpunkten   - Variablen sind public
		
		menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(62, 0, 0, 0));
		menuBar.setFont(new Font("Verdana", Font.PLAIN, 26));
		setJMenuBar(menuBar);
		
		/*mnNewMenuIcon = new JMenu("");
		mnNewMenuIcon.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Chrysanthemum.jpg"));
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
		
		mntmWocheErstellen = new JMenuItem("erstellen");
		mntmWocheErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KasseWocheErstellenView nw = new KasseWocheErstellenView();
			}
		});
		mntmWocheErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmWocheErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnWoche.add(mntmWocheErstellen);
		
		mntmWocheLoeschen = new JMenuItem("löschen");
		mntmWocheLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmWocheLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnWoche.add(mntmWocheLoeschen);
		
		mntmWocheVerschicken = new JMenuItem("verschicken");
		mntmWocheVerschicken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KasseWocheSendenView nw = new KasseWocheSendenView();
			}
		});
		mntmWocheVerschicken.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmWocheVerschicken.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnWoche.add(mntmWocheVerschicken);
		
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
		
		mnKrankmeldung = new JMenu("Krankmeldung");
		mnKrankmeldung.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnKrankmeldung);
		
		mntmKrankErstellen = new JMenuItem("erstellen");
		mntmKrankErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmKrankErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnKrankmeldung.add(mntmKrankErstellen);
		
		mntemKrankLoeschen = new JMenuItem("löschen");
		mntemKrankLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntemKrankLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnKrankmeldung.add(mntemKrankLoeschen);
		
		mnBenutzerrolle = new JMenu("Benutzerrolle");
		mnBenutzerrolle.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnBenutzerrolle);
		
		
		mntmBenutzerZuweisen = new JMenuItem("zuweisen");
		mntmBenutzerZuweisen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			KasseBenutzerrolleView nw = new KasseBenutzerrolleView();
			}
		});
		mntmBenutzerZuweisen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmBenutzerZuweisen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnBenutzerrolle.add(mntmBenutzerZuweisen);
		getContentPane().setLayout(null);
		
		lblKW1 = new JLabel("");
		lblKW1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblKW1.setBounds(109, 11, 136, 30);
		getContentPane().add(lblKW1);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setOpaque(false);
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Admin\\git\\SoftwareProjekt\\view\\right.png"));
		btnNewButton.setBounds(255, 11, 32, 23);
		getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("");
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\Admin\\git\\SoftwareProjekt\\view\\left.png"));
		btnNewButton_1.setBounds(49, 11, 50, 23);
		getContentPane().add(btnNewButton_1);
		
		
		setVisible(true);
	}
}
