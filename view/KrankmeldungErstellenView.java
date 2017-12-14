package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class KrankmeldungErstellenView extends JFrame  {

	private JPanel contentPane, panelKrankmeldung = null;
	private JTextField txtBisTermin, txtVonTermin =null;
	private JComboBox comboBoxMA =null;
	private JLabel labelKrankmeldungErstellen, lblMitarbeiterAuswaehlen, lblNotizEintragen =null;
	private JTextField txtGrund = null;;
	private JButton buttonBestaetigen = null;;
	private String username= null;
	private Einsatzplanview myView = null;
	private Einsatzplanmodel myModel = null;
	private TreeMap <Integer, String> zeitraum= null;
	
	protected KrankmeldungErstellenView(Einsatzplanview myView) {
		this.myView= myView;
		
		setTitle("Krankmeldung erstellen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800,600);
				
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
		
		txtBisTermin = new JTextField();
		txtBisTermin.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtBisTermin.setText("05.11.2017");
		txtBisTermin.setColumns(10);
		txtBisTermin.setBounds(61, 184, 189, 20);
		panelKrankmeldung.add(txtBisTermin);
		
		labelKrankmeldungErstellen = new JLabel("Krankmeldung erstellen");
		labelKrankmeldungErstellen.setFont(new Font("Verdana", Font.BOLD, 21));
		labelKrankmeldungErstellen.setBounds(61, 58, 302, 28);
		panelKrankmeldung.add( labelKrankmeldungErstellen);
		
		txtVonTermin = new JTextField();
		txtVonTermin.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtVonTermin.setText("01.11.2017");
		txtVonTermin.setHorizontalAlignment(SwingConstants.LEFT);
		txtVonTermin.setColumns(10);
		txtVonTermin.setBounds(61, 129, 189, 20);
		panelKrankmeldung.add(txtVonTermin);
		
		comboBoxMA = new JComboBox();
//		comboBoxMA.setModel(new DefaultComboBoxModel();
		comboBoxMA.setBounds(253, 243, 152, 20);
		panelKrankmeldung.add(comboBoxMA);
		
		txtGrund = new JTextField();
		txtGrund.setHorizontalAlignment(SwingConstants.CENTER);
		txtGrund.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtGrund.setBounds(61, 311, 277, 143);
		panelKrankmeldung.add(txtGrund);
		txtGrund.setColumns(10);
		
	
		 buttonBestaetigen = new JButton("bestätigen");
		 buttonBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null, JOptionPane.YES_NO_CANCEL_OPTION);
		//		System.out.print(eingabe); Kontrolle
				
				if (eingabe==0) {
					String s[]= new String [4];
			//zeitraum  = new TreeMap <Integer, String>;
					try {	 
		s[0] = username;
		s[1] = myModel.
		s[2] =	txtGrund.getText()+ ";";
		s[3] = (String) comboBoxMA.getSelectedItem();  // Wiedergabe des gewählten Items
		zeitraum.put (txtVonTermin.getText());
   	   	zeitraum.put( txtBisTermin.getText());
			myView.erstelleTermin(username, bez, zeitraum, txtGrund);
			
			//test des Array Inhalts
			
		for(int i=0; i<s.length; ++i) {
			System.out.print(s[i]+" ");  
		}
			System.exit(0);
					}
							       
			       catch (Exception e) {
			    	   // DIalog AusgabeSystem.out.println("Daten konnten nicht umgewandelt wrerden, da die Dateiformate nicht stimmen! - Fehle: TerminErstellenView Zeile Button Bestätigen ActionPerformed");
			    	   e.printStackTrace();
			       }
			}
				else {
					System.exit(0);
				}
			}
			
			}
			);
		 buttonBestaetigen.setBounds(558, 431, 89, 23);
		panelKrankmeldung.add(buttonBestaetigen);
		
		lblNotizEintragen = new JLabel("Notiz eintragen");
		lblNotizEintragen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNotizEintragen.setBounds(61, 285, 152, 14);
		panelKrankmeldung.add(lblNotizEintragen);
		
		lblMitarbeiterAuswaehlen = new JLabel("Mitarbeiter ausw\u00E4hlen");
		lblMitarbeiterAuswaehlen.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblMitarbeiterAuswaehlen.setBounds(61, 244, 165, 14);
		panelKrankmeldung.add(lblMitarbeiterAuswaehlen);

		setVisible(true);
	}
	

 // LOESCHEN
//	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		

				}


