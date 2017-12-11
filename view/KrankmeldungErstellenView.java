package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class KrankmeldungErstellenView extends JFrame implements ActionListener {

	private JPanel contentPane, panelKrankmeldung;
	private JTextField txtBisTermin, txtVonTermin;
	private JComboBox comboBoxMA;
	private JLabel labelKrankmeldungErstellen;
	private JTextField txtGrund;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KrankmeldungErstellenView frame = new KrankmeldungErstellenView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public KrankmeldungErstellenView() {
		setTitle("Einsatzplan");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1538,864);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelKrankmeldung = new JPanel();
		panelKrankmeldung.setBounds(29, 30, 1188, 728);
		panelKrankmeldung.setBorder(UIManager.getBorder("Button.border"));
		contentPane.add(panelKrankmeldung);
		panelKrankmeldung.setLayout(null);
		
		txtBisTermin = new JTextField();
		txtBisTermin.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtBisTermin.setText("05.11.2017");
		txtBisTermin.setColumns(10);
		txtBisTermin.setBounds(64, 224, 189, 20);
		panelKrankmeldung.add(txtBisTermin);
		
		labelKrankmeldungErstellen = new JLabel("Krankmeldung erstellen:");
		 labelKrankmeldungErstellen.setFont(new Font("Verdana", Font.PLAIN, 22));
		 labelKrankmeldungErstellen.setBounds(64, 100, 302, 28);
		panelKrankmeldung.add( labelKrankmeldungErstellen);
		
		txtVonTermin = new JTextField();
		txtVonTermin.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtVonTermin.setText("01.11.2017");
		txtVonTermin.setHorizontalAlignment(SwingConstants.LEFT);
		txtVonTermin.setColumns(10);
		txtVonTermin.setBounds(64, 169, 189, 20);
		panelKrankmeldung.add(txtVonTermin);
		
		JComboBox comboBoxMA = new JComboBox();
//		comboBoxMA.setModel(new DefaultComboBoxModel(new Mitarbeiter[]);
				// Liste mit den Mitarbeiter wird vom Controller 
				// Array Struktur 
			
	
		comboBoxMA.setBounds(64, 289, 98, 20);
		panelKrankmeldung.add(comboBoxMA);
		
		txtGrund = new JTextField();
		txtGrund.setHorizontalAlignment(SwingConstants.CENTER);
		txtGrund.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtGrund.setText("ggf. Grund");
		txtGrund.setBounds(200, 289, 277, 301);
		panelKrankmeldung.add(txtGrund);
		txtGrund.setColumns(10);
		
		JButton button = new JButton("bestätigen");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Daten bestätigen?", null, JOptionPane.YES_NO_CANCEL_OPTION);
		//		System.out.print(eingabe); Kontrolle
				
				if (eingabe==0) {
					String s[]= new String [3];
					int terminnr=0;
					terminnr++;
	
					try {
	
			s[0] = txtVonTermin.getText();
    	   	s[1] = txtBisTermin.getText();	  
    	   	
//    	   	vergleichGrundInhalt(txtGrund.getText());
			s [2] =	txtGrund.getText();
			
//			s[3] = (String) comboBoxMA.getSelectedItem();  / Wiedergabe des gewählten Items
//			s[3] = String.valueOf(terminnr); TerminNr anzeigen
			
			//test des Array Inhalts
			for(int i=0; i<s.length; ++i) {
				System.out.print(s[i]+" ");  
			}

				// Fenster mit Dispose schließen
			System.exit(0);
					}
							       
			       catch (NumberFormatException e) {
			    	   System.out.println("Daten konnten nicht umgewandelt werden, da die Dateiformate nicht stimmen! - Fehler: TerminErstellenView Zeile Button Bestätigen ActionPerformed");
			    	   e.printStackTrace();
			       }
			}
				else {
					System.exit(0);
				}
			}
			
			}
			);
		button.setBounds(550, 567, 89, 23);
		panelKrankmeldung.add(button);

		setVisible(true);
	}

	// Vergleich es Inhaltes vom JTextfield txtGrund
//	private String vergleichGrundInhalt(String grund) {
//		String eingabe = txtGrund.getText();
//		if (eingabe == "ggf.Grund" ) {
//			txtGrund.setText(null);
//		}return txtGrund.getText();
//	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
		
	
	
				}


