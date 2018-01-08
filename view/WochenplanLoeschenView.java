package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import controller.EinsatzplanController;
import model.Einsatzplanmodel;

class WochenplanLoeschenView extends JFrame {

	// Initialisierung der Instanzvariablen
	private JPanel contentPane = null;;
	private JLabel lblWochenplanLoeschen, lblBitteAusaewhlen = null;
	private JList<String> listWochenplaene = null;
	private JButton btnLoeschen = null;
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private String username = null;
	private DefaultListModel<String> model = null;
	private ArrayList<String> wpl = null;
	private ArrayList<String> wpli = null;
	private String wpbez = null;
	private WindowListener windowListener;

	/**
	 * 
	 * @author Ramona Gerke
	 * @Info Konstruktor der die View Wochenplan löschen .
	 */
	public WochenplanLoeschenView(Einsatzplanview myView, Einsatzplanmodel myModel,
			EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Wochenplan löschen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800, 600);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		windowListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				myView.update();
				dispose();
			}
		};
		addWindowListener(windowListener);

		lblWochenplanLoeschen = new JLabel("Wochenplan löschen");
		lblWochenplanLoeschen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblWochenplanLoeschen.setBounds(51, 55, 243, 27);
		contentPane.add(lblWochenplanLoeschen);

		listWochenplaene = new JList<String>();
		listWochenplaene.setFont(new Font("Verdana", Font.PLAIN, 13));
		// Ausgeben einer ArrayList mit allen Wochenplänen und in einer JComboBox
		// hinterlegen
		wpl = myController.getWochenplaene();
		model = new DefaultListModel<String>();
		for (String m : wpl) {
			model.addElement(m);
		}
		listWochenplaene.setModel(model);
		listWochenplaene.setBorder(new EmptyBorder(0, 0, 0, 0));
		listWochenplaene.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listWochenplaene.setBounds(51, 127, 362, 399);
		contentPane.add(listWochenplaene);

		btnLoeschen = new JButton("L\u00F6schen");
		btnLoeschen.setHorizontalAlignment(SwingConstants.LEFT);
		btnLoeschen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnLoeschen.setBounds(500, 500, 101, 25);
		contentPane.add(btnLoeschen);

		lblBitteAusaewhlen = new JLabel("Wochenplan ausw\u00E4hlen:");
		lblBitteAusaewhlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblBitteAusaewhlen.setBounds(51, 93, 205, 26);
		contentPane.add(lblBitteAusaewhlen);

		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methode die nach dem Bestätigen des Buttons
		 *       "bestätigen", dass ausgewählte Element anhand dem Username und der
		 *       Wochenplanbez löscht..
		 */
		// ACTION PERFROMED Methode
		btnLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abfrage, ob kein Element ausgewähl ist
				// weiter bei ja
				if (listWochenplaene.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Es wurde keine Eingabe getätigt", "Error",
							JOptionPane.ERROR_MESSAGE);
					// weiter bei nein
				} else {
					try {
						// Meldung, ob die Daten wirklich gelöscht werden soll ( Ja, Nein , Abbrechen
						// Abfrage)
						int eingabe = JOptionPane.showConfirmDialog(null,
								"Wollen Sie die den Wochenplan wirklich löschen?", null,
								JOptionPane.YES_NO_CANCEL_OPTION);
						// weiter bei ja
						if (eingabe == JOptionPane.YES_OPTION) {
							// ausgewähltes Element für die Übergabe an den Controller vorbereiten
							String s = listWochenplaene.getSelectedValue().toString();
						
							// Übergabe an den Controller
							myController.entferneWochenplan(myView.getUsername(), s);
							// Erfolgsmeldung
							JOptionPane.showMessageDialog(null, "Wochenplan erfolgreich gelöscht", "  ",
									JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "Wählen Sie einen anderen Wochenplan aus!", "  ",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception a) {
						JOptionPane.showMessageDialog(null,
								"Die Liste konnte nicht übergeben werden. - Methode ActionPerformed (btnBLoeschen, WPLoeschen)",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		setVisible(true);
	}
}
