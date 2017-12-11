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

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class AnmeldungView extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

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
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1538,864);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAnmeldung = new JLabel("Anmeldung");
		lblAnmeldung.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblAnmeldung.setBounds(12, 13, 110, 37);
		contentPane.add(lblAnmeldung);
		
		JLabel lblBenutzername = new JLabel("Benutzername:");
		lblBenutzername.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBenutzername.setBounds(66, 114, 126, 16);
		contentPane.add(lblBenutzername);
		
		textField = new JTextField();
		textField.setBounds(191, 112, 116, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPasswort = new JLabel("Passwort:");
		lblPasswort.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPasswort.setBounds(66, 145, 110, 16);
		contentPane.add(lblPasswort);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(191, 147, 116, 22);
		contentPane.add(passwordField);
		
		JButton btnLogin = new JButton("Ok");
		btnLogin.setContentAreaFilled(false);
		btnLogin.setOpaque(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setIcon(new ImageIcon("D:\\workspace\\EinsatzplanSoftwaretechnik\\src\\GUI\\loginsmall.png"));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*MitarbeiterView nw = new MitarbeiterView();
				nw.NewScreen(); */
				String pwd = new String(passwordField.getPassword());
				String user = new String(textField.getText());
				//BenutzerStrg.anmelden(pwd,user); je nachdem wie die "Steuerung"heiﬂt
				//Abfrage der Benutzerrrolle in der Strg/Controller
				//dispose wenn fertig
			}});
		btnLogin.setBounds(213, 182, 54, 47);
		contentPane.add(btnLogin);
	}
}
