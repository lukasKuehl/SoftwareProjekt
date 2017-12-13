package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import data.Mitarbeiter;
import model.Datenbank_Mitarbeiter;

public class KasseBenutzerrolleView extends JFrame{
private JComboBox cbBerechtigung;
//	private JFrame frame;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public KasseBenutzerrolleView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Benutzerrolle Zuweisen");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1538,864);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Benutzer zuweisen");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(83, 124, 301, 73);
		getContentPane().add(lblNewLabel);
		
		JLabel lblMitarbeiterAuswhlen = new JLabel("Mitarbeiter ausw\u00E4hlen:");
		lblMitarbeiterAuswhlen.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMitarbeiterAuswhlen.setBounds(83, 262, 217, 39);
		getContentPane().add(lblMitarbeiterAuswhlen);
		
		JComboBox cbMitarbeiter = new JComboBox();
		cbMitarbeiter.setBounds(381, 274, 128, 20);
		/*Datenbank_Mitarbeiter dbhandler = new Datenbank_Mitarbeiter();
		LinkedList alleMitarbeiter = dbhandler.getMitarbeiter();
		alleMitarbeiter.forEach((temp) -> {
            cbMitarbeiter.addItem(temp);
        });*/
		getContentPane().add(cbMitarbeiter);
		
		JLabel lblBen = new JLabel("Berechtigung ausw\u00E4hlen:");
		lblBen.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblBen.setBounds(83, 325, 233, 20);
		getContentPane().add(lblBen);
		
		JComboBox cbBerechtigung = new JComboBox();
		cbBerechtigung.setBounds(381, 328, 128, 20);
		cbBerechtigung.addItem("Kassenbüro");
		cbBerechtigung.addItem("Mitarbeiter");
		getContentPane().add(cbBerechtigung);
		
		
		JButton btnBestaetigen = new JButton("Best\u00E4tigen");
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, 
				        "Sind Sie sicher?", "Warnung",
				        JOptionPane.YES_NO_OPTION);

				    if (confirmed == JOptionPane.YES_OPTION) {
				    	//String benutzerrolle = gibBenutzerrolle(); wie übergeben ( da protected)
				      dispose(); // hier muss natürlich bei JA dann der controller angesteuert werden
				//Controller.wocheErstellen(a,b,c,d)
				//dispose wenn keine Fehlermeldung kommt und öffne KasseWocheErstellenPlanView
			}}
		});
		btnBestaetigen.setBounds(420, 390, 89, 23);
		getContentPane().add(btnBestaetigen);
		setVisible(true);
		
		/*frame = new JFrame();
		frame.setBounds(100, 100, 1538,864);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); */
	}
	public String gibBenutzerrolle(){
		return cbBerechtigung.getSelectedItem().toString();
	}

}
