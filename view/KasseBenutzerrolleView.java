package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class KasseBenutzerrolleView extends JFrame{

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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(381, 274, 128, 20);
		getContentPane().add(comboBox);
		
		JLabel lblBen = new JLabel("Berechtigung ausw\u00E4hlen:");
		lblBen.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblBen.setBounds(83, 325, 233, 20);
		getContentPane().add(lblBen);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(381, 328, 128, 20);
		getContentPane().add(comboBox_1);
		
		JButton btnBestaetigen = new JButton("Best\u00E4tigen");
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, 
				        "Sind Sie sicher?", "Warnung",
				        JOptionPane.YES_NO_OPTION);

				    if (confirmed == JOptionPane.YES_OPTION) {
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
}
