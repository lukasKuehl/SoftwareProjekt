package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import model.Einsatzplanmodel;

import javax.swing.JFormattedTextField;

public class KrankmeldungErstellenView extends JFrame {

	private JPanel contentPane, panelKrankmeldung = null;
	private JFormattedTextField txtBisTermin, txtVonTermin = null;
	private JComboBox comboBoxMA = null;
	private JLabel labelKrankmeldungErstellen, lblMitarbeiterAuswaehlen, lblNotizEintragen, lblVon, lblBis = null;
	private JTextField txtGrund = null;;
	private JButton buttonBestaetigen = null;;
	private String username = null;
	private Einsatzplanview myView = null;
	private Einsatzplanmodel myModel = null;
	private TreeMap<Integer, String> zeitraum = null;
	private Date formatter = null;

	protected KrankmeldungErstellenView(Einsatzplanview myView, Einsatzplanmodel myModel) {
		this.myView = myView;
		this.myModel = myModel;
		formatter = new Date();
		setTitle("Krankmeldung erstellen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800, 600);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panelKrankmeldung = new JPanel();
		panelKrankmeldung.setBounds(29, 30, 1188, 728);
		panelKrankmeldung.setBackground(Color.WHITE);
		panelKrankmeldung.setBorder(UIManager.getBorder("Button.border"));
		contentPane.add(panelKrankmeldung);
		panelKrankmeldung.setLayout(null);

		txtBisTermin = new JFormattedTextField(formatter);
		txtBisTermin.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtBisTermin.setColumns(10);
		txtBisTermin.setBounds(103, 168, 189, 20);
		panelKrankmeldung.add(txtBisTermin);

		labelKrankmeldungErstellen = new JLabel("Krankmeldung erstellen");
		labelKrankmeldungErstellen.setFont(new Font("Verdana", Font.BOLD, 21));
		labelKrankmeldungErstellen.setBounds(61, 58, 302, 28);
		panelKrankmeldung.add(labelKrankmeldungErstellen);

		txtVonTermin = new JFormattedTextField(formatter);
		txtVonTermin.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtVonTermin.setHorizontalAlignment(SwingConstants.LEFT);
		txtVonTermin.setColumns(10);
		txtVonTermin.setBounds(103, 113, 189, 20);
		panelKrankmeldung.add(txtVonTermin);

		comboBoxMA = new JComboBox();
		// comboBoxMA.setModel(new DefaultComboBoxModel();

		// NAMEN DER MITARBEITER EINFÜGEN ÜBER ARRAY LIST

		// FOR EACH SCHLEIFE
		comboBoxMA.setBounds(150, 229, 142, 20);
		panelKrankmeldung.add(comboBoxMA);

		txtGrund = new JTextField();
		txtGrund.setHorizontalAlignment(SwingConstants.CENTER);
		txtGrund.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtGrund.setBounds(61, 311, 277, 143);
		panelKrankmeldung.add(txtGrund);
		txtGrund.setColumns(10);

		lblVon = new JLabel("Von");
		lblVon.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblVon.setBounds(61, 113, 46, 14);
		panelKrankmeldung.add(lblVon);

		lblBis = new JLabel("Bis ");
		lblBis.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblBis.setBounds(61, 171, 26, 14);
		panelKrankmeldung.add(lblBis);

		lblNotizEintragen = new JLabel("Notiz eintragen");
		lblNotizEintragen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNotizEintragen.setBounds(61, 285, 152, 14);
		panelKrankmeldung.add(lblNotizEintragen);

		lblMitarbeiterAuswaehlen = new JLabel("Mitarbeiter");
		lblMitarbeiterAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblMitarbeiterAuswaehlen.setBounds(61, 230, 165, 14);
		panelKrankmeldung.add(lblMitarbeiterAuswaehlen);

		buttonBestaetigen = new JButton("bestätigen");
		buttonBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonBestaetigen.setBounds(558, 431, 89, 23);
		panelKrankmeldung.add(buttonBestaetigen);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonBestaetigen) {
			int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null, JOptionPane.YES_NO_CANCEL_OPTION);

			if (eingabe==0) {
		 zeitraum  = new TreeMap<Integer, String>();
				try {	 
						username = myView.getUsername();
						int terminnr = myModel.getNewTblocknr();
						String grund=	txtGrund.getText().toString();
						String bez= comboBoxMA.getSelectedItem().toString();
						String zeitr= txtVonTermin.getText().toString() + txtBisTermin.getText().toString();
						zeitraum.put(terminnr, zeitr);
	
						myView.erstelleTermin(username, bez, zeitraum, grund);
						System.exit(0);
				}
						       
		       catch (Exception ex) {
		    	   JOptionPane.showConfirmDialog(null, "Daten konnten nicht umgewandelt wrerden, da die Dateiformate nicht stimmen! - Fehle: TerminErstellenView Zeile Button Bestätigen ActionPerformed");
		       }
		}
			else {
				System.exit(0);
			}
		}
	}
					}
