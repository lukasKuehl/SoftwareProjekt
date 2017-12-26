package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import controller.EinsatzplanController;
import model.Einsatzplanmodel;

public class WochenplanLoeschenView extends JFrame {

	// Initialisierung der Instanzvariablen
	private JPanel contentPane = null;;
	private JLabel lblWochenplanLschen, lblBitteAuswhlen = null;
	private JList<Object> listWochenplaene = null;
	private JButton btnLschen = null;
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private String username = null;
	private DefaultListModel<Object> model = null;
	private ArrayList<String> wpl = null;
	private ArrayList<String> wpli = null;

	/**
	 * 
	 * @author Ramona Gerke
	 * @Info Konstruktor der die View Krankmeldung löschen erstellt.
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

		lblWochenplanLschen = new JLabel("Wochenplan l\u00F6schen");
		lblWochenplanLschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		lblWochenplanLschen.setBounds(51, 61, 243, 27);
		contentPane.add(lblWochenplanLschen);

		listWochenplaene = new JList<Object>();
		wpl = myController.getWochenplaene();
		model = new DefaultListModel<Object>();
		for (String m : wpl) {
			model.addElement(m);
		}
		listWochenplaene.setModel(model);
		listWochenplaene.setBorder(new LineBorder(new Color(0, 0, 0)));
		listWochenplaene.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listWochenplaene.setBounds(95, 126, 362, 399);
		contentPane.add(listWochenplaene);

		btnLschen = new JButton("löschen");
		btnLschen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnLschen.setBounds(500,500,110,25);
		contentPane.add(btnLschen);

		lblBitteAuswhlen = new JLabel("Bitte auswählen");
		lblBitteAuswhlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblBitteAuswhlen.setBounds(51, 109, 143, 26);
		contentPane.add(lblBitteAuswhlen);

		btnLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnLschen) {
					int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die den Wochenplan wirklich löschen?",
							null, JOptionPane.YES_NO_CANCEL_OPTION);

					if (listWochenplaene.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null, "Es wurde keine Eingabe getätigt", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							if (eingabe == 0) {
								int wpbez = 0;
								String temp[] = new String[4];
								wpli = myController.getWochenplaene();
								for (String m : wpli) {
									m.toString();
									m.trim();
									temp = m.split("-");
									wpbez = Integer.parseInt(temp[0].substring(2));
									String oeffnungszeitenAnfang = temp[1];
									String oeffnungszeitenEnd = temp[2];
									String hauptzeitAnfang = temp[3];
									String hauptzeitEnd = temp[4];

//									// Methode entferne Wochenplan WpBez bereits in Int gewandelt
//									myController.entferneWochenplan(myView.getUsername(), wpbez);
									System.exit(0);
								}
							}
						} catch (Exception a) {
							JOptionPane.showMessageDialog(null,
									"Die Liste konnte nicht übergeben werden. - Methode ActionPerformed (btnBLoeschen, WPLoeschen)",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					System.exit(0);
				}
			}

		});

		setVisible(true);
	}
}
