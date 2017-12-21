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

public class AnmeldungView extends JFrame implements ActionListener {

	private JPanel cpAnmeldung;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblAnmeldung, lblBenutzername, lblPasswort;
	private JButton btnLogin;
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private Einsatzplanview myView = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnmeldungView frame = new AnmeldungView();
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
	public AnmeldungView() {

		this.myController = myController;
		this.myModel = myModel;
		this.myView = myView;

		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1538, 864);
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
		lblBenutzername.setBounds(66, 114, 126, 16);
		cpAnmeldung.add(lblBenutzername);

		textField = new JTextField();
		textField.setBounds(191, 112, 116, 22);
		cpAnmeldung.add(textField);
		textField.setColumns(10);

		lblPasswort = new JLabel("Passwort:");
		lblPasswort.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPasswort.setBounds(66, 145, 110, 16);
		cpAnmeldung.add(lblPasswort);

		passwordField = new JPasswordField();
		passwordField.setBounds(191, 147, 116, 22);
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
				String pw = new String(passwordField.getPassword());
				String username = new String(textField.getText());
				if (myController.benutzerAnmelden(username, pw)) {
					if (true) {
						// falls Benutzer normaler Mitarbeiter ist
						new MitarbeiterWochenView();
					} else {
						// falls der Benutzer Chef ist
						new KasseWochenView(username, myModel, myController, myView);
					}
				} else {

				}
			}
		});
		btnLogin.setBounds(213, 182, 54, 47);
		cpAnmeldung.add(btnLogin);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
