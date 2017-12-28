package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class TerminLoeschenView extends JFrame {

	private JPanel contentPane, panelTermin = null;
	private JLabel lblTerninLoeschen, lblTerminAuswaehlen = null;
	private JList<Object> listTermin = null;
	private String username = null;
	private DefaultListModel<Object> model = null;
	private ArrayList<String> tl = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;
	private EinsatzplanController myController = null;
	private JButton btnBesttigen = null;

	protected TerminLoeschenView(Einsatzplanview myView, Einsatzplanmodel myModel, EinsatzplanController myController) {
		this.myView = myView;
		this.myController = myController;
		this.myModel = myModel;
		setTitle("Termin löschen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800, 600);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblTerninLoeschen = new JLabel("Termin löschen");
		lblTerninLoeschen.setFont(new Font("Verdana", Font.BOLD, 21));
		lblTerninLoeschen.setBounds(60, 83, 199, 26);
		contentPane.add(lblTerninLoeschen);

		listTermin = new JList<Object>();
//		tl = myController.getMitarbeiterTermine(myView.getUsername());
		model = new DefaultListModel<Object>();
//		for (String m : tl) {
//			model.addElement(m);
//		}
		listTermin.setModel(model);
		listTermin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTermin.setBounds(65, 145, 282, 355);
		listTermin.getModel();
		contentPane.add(listTermin);

		lblTerminAuswaehlen = new JLabel("Termin auswählen");
		lblTerminAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblTerminAuswaehlen.setBounds(62, 120, 152, 14);
		contentPane.add(lblTerminAuswaehlen);

		btnBesttigen = new JButton("bestätigen");
		btnBesttigen.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnBesttigen.setBounds(500,500,110,25);
		contentPane.add(btnBesttigen);

		/**
		 * @author Ramona Gerke
		 * @Info Action Performed Methode die nach dem bestätigen des Buttons "bestätigen", das ausgewählte Element anhand der Tauschanfrage Nr löscht.
		 */
		// ACTION PERFROMED Methode	
		btnBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null,
						JOptionPane.YES_NO_CANCEL_OPTION);

				if (eingabe == 0) {

					if (listTermin.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null, "Es wurde keine Eingabe getätigt", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							String s;
							s = listTermin.getSelectedValue().toString();
							s.trim();
							s.split("-");
							s.substring(0, 2);
							int terminnr = Integer.parseInt(s);
							myView.entferneTermin(terminnr, myView.getUsername());
							System.exit(0);

						} catch (Exception a) {
							JOptionPane.showMessageDialog(null,
									"Die Liste konnte nicht übergeben werden. - Methode ActionPerformed (btnBestätigen, TerminLoeschen)",
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
