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
import javax.swing.SwingConstants;
/**
 * @author Darius Panteli
 * @info Die Klasse AnmeldungView dient dazu, dem Nutzer eine graphische Obefläche anzubieten, um sich im System
 * anzumelden
 */

public class AnmeldungView extends JFrame{

	private JPanel cpAnmeldung;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblAnmeldung, lblBenutzername, lblPasswort,lblFehlermeldung,lblFehlermeldung1;
	private JButton btnLogin;
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;

	protected AnmeldungView(EinsatzplanController myController, Einsatzplanmodel myModel, Einsatzplanview myView) {

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
		lblAnmeldung.setFont(new Font("Verdana", Font.BOLD, 30));
		lblAnmeldung.setBounds(12, 25, 190, 37);
		cpAnmeldung.add(lblAnmeldung);

		lblBenutzername = new JLabel("Benutzername:");
		lblBenutzername.setFont(new Font("Verdana", Font.BOLD, 15));
		lblBenutzername.setBounds(86, 141, 126, 16);
		cpAnmeldung.add(lblBenutzername);

		textField = new JTextField();
		textField.setBounds(254, 139, 205, 22);
		cpAnmeldung.add(textField);
		textField.setColumns(10);

		lblPasswort = new JLabel("Passwort:");
		lblPasswort.setFont(new Font("Verdana", Font.BOLD, 15));
		lblPasswort.setBounds(86, 202, 110, 16);
		cpAnmeldung.add(lblPasswort);
		
	    passwordField = new JPasswordField();
		passwordField.setBounds(254, 200, 205, 22);
		cpAnmeldung.add(passwordField);
		
		lblFehlermeldung = new JLabel("");
		lblFehlermeldung.setHorizontalAlignment(SwingConstants.CENTER);
		lblFehlermeldung.setForeground(Color.RED);
		lblFehlermeldung.setFont(new Font("Verdana", Font.BOLD, 15));
		lblFehlermeldung.setBounds(48, 310, 527, 37);
		
		lblFehlermeldung1 = new JLabel("");
		lblFehlermeldung1.setHorizontalAlignment(SwingConstants.CENTER);
		lblFehlermeldung1.setForeground(Color.RED);
		lblFehlermeldung1.setFont(new Font("Verdana", Font.BOLD, 15));
		lblFehlermeldung1.setBounds(48, 349, 527, 37);
		
		btnLogin = new JButton("");
		btnLogin.setContentAreaFilled(false);
		btnLogin.setOpaque(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setIcon(new ImageIcon("view/loginsmall.png"));
		//Sobald der Login Button gedrückt wird, werden Benutzername und Passwort übergeben und überprüft, welche Art
		//von Benutzer die Person ist
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pw = String.valueOf(passwordField.getPassword());
				String username = textField.getText();
				
				if (myController.benutzerAnmelden(username, pw)) {
					
					myView.setUsername(username);
					
					
					if (myModel.getMitarbeiter(username).getJob().equalsIgnoreCase("chef")
							|| myModel.getMitarbeiter(username).getJob().equalsIgnoreCase("kassenbüro")) {
						
						// falls Benutzer Admin ist
						new KasseWochenView(myModel, myController, myView);
						dispose();
					} else {						
						//falls der Benutzer kein Admin ist/keine Admin Rechte hat
						new MitarbeiterWochenView(myModel, myController, myView);
						dispose();
					}
					
				}else {
					//Falls es bei der Anmeldung einen Fehler gab
					lblFehlermeldung.setText("Fehler beim Anmelden des Users " + username+"!");
					lblFehlermeldung1.setText("Bitte überprüfen Sie Ihre Eingaben!");
				}
			}
			
		});
		btnLogin.setBounds(316, 248, 57, 47);
		cpAnmeldung.add(btnLogin);
		cpAnmeldung.add(lblFehlermeldung);
		cpAnmeldung.add(lblFehlermeldung1);
		setVisible(true);
	}
}
