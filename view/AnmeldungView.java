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
/**
 * @author Darius Panteli
 * @info Die Klasse AnmeldungView dient dazu, dem Nutzer eine graphische Obefläche anzubieten, um sich im System
 * anzumeldemn
 */

//Kommentare innerhalb der Methoden fehlen!

//Autoren der einzelnen Methoden fehlen!

//Die Klasse muss nicht den ActionListener implementieren wenn in der ActionPerformed Methode nix drin steht
//Die Hilfsklasse soll nicht public sein --> Entwurfsschema
public class AnmeldungView extends JFrame{

	private JPanel cpAnmeldung;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblAnmeldung, lblBenutzername, lblPasswort,lblFehlermeldung;
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
		
		lblFehlermeldung = new JLabel("");
		lblFehlermeldung.setForeground(Color.RED);
		lblFehlermeldung.setFont(new Font("Verdana", Font.BOLD, 15));
		lblFehlermeldung.setBounds(86, 310, 489, 37);

		passwordField = new JPasswordField();
		passwordField.setBounds(254, 200, 205, 22);
		cpAnmeldung.add(passwordField);

		btnLogin = new JButton("");
		btnLogin.setContentAreaFilled(false);
		btnLogin.setOpaque(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setIcon(new ImageIcon("view/loginsmall.png"));
				
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

					} else {						
						
						new MitarbeiterWochenView(myModel, myController, myView);
						
					}
					
				}else {
					//Das ist doch eigentlich gar kein Fehler(Benutzer hat vielleicht nur sein pw falsch eingegeben)
					//evtl unterscheiden ob PW oder Username Falsch war
					
					lblFehlermeldung.setText("Fehler beim Anmelden des Users " + username + "!");
				}
			}
			
		});
		btnLogin.setBounds(316, 248, 57, 47);
		cpAnmeldung.add(btnLogin);
		
		
		cpAnmeldung.add(lblFehlermeldung);
		setVisible(true);
	}

	//Die actionPerformed Methode ist überflüssig, wenn sie nicht gefüllt ist
	
}
