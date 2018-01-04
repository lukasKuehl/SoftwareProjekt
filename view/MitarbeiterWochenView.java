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

//Klassendokumentation fehlt!

//Kommentare innerhalb der Methoden fehlen!

//Autoren der einzelnen Methoden fehlen!

//Klasse braucht das implements ActionListener nicht --> kann weg
//Hilfsklassen sind nicht public!
public class MitarbeiterWochenView extends JFrame{

	private JPanel contentPane, pnlMenuBar;
	private JTable tbleWochenplan;
	private JTextField txtBisTermin, txtVonTermin, textUhrzeitTerminV, textUhrzeitTerminB;
	private JMenuBar menuBar;
	private JMenu mnNewMenuIcon, mnWoche, mnTermin, mnTauschanfrage;
	private JMenuItem mntmWocheAnzeigen, mntmTerminErstellen, mntmTerminLoeschen, mntmTauschanfrageErstellen,
			mntmTauschanfrageLoeschen, mntmTauschanfrageAnzeigen;
	private JLabel lblKW1;
	private JButton btnRechts, btnLinks;
	private Einsatzplanmodel myModel = null;
	private EinsatzplanController myController = null;
	private Einsatzplanview myView = null;
	private int currentKW = 0;
	private JScrollPane jsp;
	//Siehe AnmeldungView
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MitarbeiterWochenView window = new MitarbeiterWochenView(new Einsatzplanmodel(), 
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
	public MitarbeiterWochenView(Einsatzplanmodel myModel, EinsatzplanController myController, Einsatzplanview myView) {
		this.myController = myController;
		this.myModel = myModel;
		this.myView = myView;
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

		mnTauschanfrage = new JMenu("Tauschanfrage");
		mnTauschanfrage.setFont(new Font("Dialog", Font.PLAIN, 26));
		menuBar.add(mnTauschanfrage);

		mntmTauschanfrageAnzeigen = new JMenuItem("Anzeigen");
		mntmTauschanfrageAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TauschanfrageAnzeigenView(myView, myModel, myController);
			}
		});
		mntmTauschanfrageAnzeigen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTauschanfrageAnzeigen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTauschanfrage.add(mntmTauschanfrageAnzeigen);

		mntmTauschanfrageErstellen = new JMenuItem("Erstellen");
		mntmTauschanfrageErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TauschanfrageErstellenView(myModel, myView, myController);
			}
		});
		mntmTauschanfrageErstellen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTauschanfrageErstellen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTauschanfrage.add(mntmTauschanfrageErstellen);

		mntmTauschanfrageLoeschen = new JMenuItem("Löschen");
		mntmTauschanfrageLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TauschanfrageLoeschenView(myView, myModel, myController);
			}
		});
		mntmTauschanfrageLoeschen.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmTauschanfrageLoeschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		mnTauschanfrage.add(mntmTauschanfrageLoeschen);
		getContentPane().setLayout(null);

		lblKW1 = new JLabel("");
		lblKW1.setBounds(109, 11, 136, 30);
		ArrayList<String> wochenplaene = myController.getWochenplaene();
		lblKW1.setText(wochenplaene.get(0).toString());
		getContentPane().add(lblKW1);

		// Die Anmerkungen aus der Klasse KasseWochenView gelten auch für den Rest dieser Klasse!!
		
		btnRechts = new JButton("New button");
		btnRechts.setBorderPainted(false);
		btnRechts.setContentAreaFilled(false);
		btnRechts.setBounds(255, 11, 32, 23);
		btnRechts.setIcon(new ImageIcon("view/right.png"));
		btnRechts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {				
				currentKW++;
				if (wochenplaene.size() <= currentKW) {
					currentKW--;
				} else {
					generiereTabelle();
					/*lblKW1.setText(wochenplaene.get(currentKW).toString());

					getContentPane().remove(tbleWochenplan);
					tbleWochenplan = myController.generiereWochenplanView(wochenplaene.get(currentKW));
					tbleWochenplan.setBounds(24, 81, 1439, 676);
					getContentPane().add(tbleWochenplan);*/
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

			@Override
			public void actionPerformed(ActionEvent e) {			
				currentKW--;
				if (currentKW < 0) {
					currentKW++;
				} else {
					generiereTabelle();
					/*lblKW1.setText(wochenplaene.get(currentKW).toString());

					getContentPane().remove(tbleWochenplan);
					tbleWochenplan = myController.generiereWochenplanView(wochenplaene.get(currentKW));
					tbleWochenplan.setBounds(24, 81, 1439, 676);
					
					tbleWochenplan.getTableHeader().setSize(tbleWochenplan.getTableHeader().getPreferredSize());
					tbleWochenplan.setSize(tbleWochenplan.getPreferredSize());
					tbleWochenplan.setPreferredScrollableViewportSize(tbleWochenplan.getPreferredScrollableViewportSize());
					tbleWochenplan.setFillsViewportHeight(true);
					JScrollPane jsp = new JScrollPane(tbleWochenplan);						
					
					getContentPane().add(jsp);*/
				}

			}
		});
		getContentPane().add(btnLinks);
		generiereTabelle();
		/*tbleWochenplan = myController.generiereWochenplanView(wochenplaene.get(currentKW));
		tbleWochenplan.setBounds(24, 81, 1439, 676);
		
		tbleWochenplan = myController.generiereWochenplanView(wochenplaene.get(currentKW));
		tbleWochenplan.setBounds(24, 81, 1439, 676);
		
		tbleWochenplan.getTableHeader().setSize(tbleWochenplan.getTableHeader().getPreferredSize());
		tbleWochenplan.setSize(tbleWochenplan.getPreferredSize());
		tbleWochenplan.setPreferredScrollableViewportSize(tbleWochenplan.getPreferredScrollableViewportSize());
		tbleWochenplan.setFillsViewportHeight(true);
		JScrollPane jsp = new JScrollPane(tbleWochenplan);						
		
		//setContentPane(jsp);
		*/
		
		setVisible(true);
	}
	private void generiereTabelle(){
		ArrayList<String> wochenplaene = myController.getWochenplaene();
		lblKW1.setText(wochenplaene.get(currentKW).toString());
		
		tbleWochenplan = myController.generiereWochenplanView(wochenplaene.get(currentKW));
		
		tbleWochenplan.getTableHeader().setSize(tbleWochenplan.getTableHeader().getPreferredSize());
		tbleWochenplan.setSize(tbleWochenplan.getPreferredSize());
		tbleWochenplan.setPreferredScrollableViewportSize(tbleWochenplan.getPreferredScrollableViewportSize());
		tbleWochenplan.setFillsViewportHeight(true);
		JScrollPane jsp = new JScrollPane(tbleWochenplan);			
		jsp.setBounds(24, 81, 1439, 676);
		
		getContentPane().add(jsp);
		
	}
	
	

	
}
