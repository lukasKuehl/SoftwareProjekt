package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.EinsatzplanController;
import model.Einsatzplanmodel;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;

public class AnmeldungView extends JFrame implements ActionListener {

	private JPanel cpAnmeldung;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblAnmeldung, lblBenutzername, lblPasswort,lblFehlermeldung;
	private JButton btnLogin;
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public AnmeldungView(EinsatzplanController myController, Einsatzplanmodel myModel, Einsatzplanview myView) {

		this.myController = myController;
		this.myModel = myModel;
		this.myView = myView;

		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		cpAnmeldung = new JPanel();
		cpAnmeldung.setBackground(SystemColor.activeCaption);
		cpAnmeldung.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(cpAnmeldung);
		cpAnmeldung.setLayout(null);

		lblAnmeldung = new JLabel("Anmeldung");
		lblAnmeldung.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblAnmeldung.setBounds(12, 13, 110, 37);
		cpAnmeldung.add(lblAnmeldung);

		lblBenutzername = new JLabel("Benutzername:");
		lblBenutzername.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBenutzername.setBounds(86, 141, 126, 16);
		cpAnmeldung.add(lblBenutzername);

		textField = new JTextField();
		textField.setBounds(254, 139, 205, 22);
		cpAnmeldung.add(textField);
		textField.setColumns(10);

		lblPasswort = new JLabel("Passwort:");
		lblPasswort.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPasswort.setBounds(86, 202, 110, 16);
		cpAnmeldung.add(lblPasswort);
		
		lblFehlermeldung = new JLabel("");
		lblFehlermeldung.setForeground(Color.RED);
		lblFehlermeldung.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFehlermeldung.setBounds(86, 310, 489, 37);

		passwordField = new JPasswordField();
		passwordField.setBounds(254, 200, 205, 22);
		cpAnmeldung.add(passwordField);

		btnLogin = new JButton("Ok");
		btnLogin.setContentAreaFilled(false);
		btnLogin.setOpaque(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setIcon(new ImageIcon("D:\\workspace\\EinsatzplanSoftwaretechnik\\src\\GUI\\loginsmall.png"));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * MitarbeiterView nw = new MitarbeiterView(); nw.NewScreen();
				 */
				String pw = passwordField.getPassword().toString();
				String username = textField.getText().toString();
				
				if (myController.benutzerAnmelden(username, pw)) {
					
					if (myModel.getMitarbeiter(username).getJob().equalsIgnoreCase("chef")
							|| myModel.getMitarbeiter(username).getJob().equalsIgnoreCase("kassenbüro")) {
						
						// falls Benutzer Admin ist
						new KasseWochenView(myModel, myController, myView);

					} else {
						
						// falls Benutzer normaler Mitarbeiter ist
						new MitarbeiterWochenView();
						
					}
					
				}else {
					lblFehlermeldung.setText("Fehler beim Anmelden des Users " + username + "!");
				}
			}
			
		});
		btnLogin.setBounds(316, 248, 54, 47);
		cpAnmeldung.add(btnLogin);
		
		
		cpAnmeldung.add(lblFehlermeldung);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
