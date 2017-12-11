package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javafx.scene.control.RadioButton;
import javax.swing.JRadioButton;

public class KrankmeldungLoeschenView extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KrankmeldungLoeschenView frame = new KrankmeldungLoeschenView();
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
	protected KrankmeldungLoeschenView() {
		setTitle("Einsatzplan");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1538,864);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
	
//	leseKrankmeldungen();
		
		
		
			setVisible(true);
			
		}
		

		
//	}
	
//	public void leseKrankmeldungen(HashMap krankmeldungen) {
//		int s;
//		JRadioButton rdbtnKrankmeldungen = null;
//		for (s: krankmeldungen.keySet();) {
//			krankmeldungen.setKeyset(new JRadioButton(krankmeldungen.keySet()));
//			rdbtnNewRadioButton.setBounds(43, 163, 109, 23);
//			contentPane.add(rdbtnNewRadioButton);
	
	
	
/**  neuen Radiobutton mit den inhalten der Methode erzeuegen 
*/

	
			
//		}
//	}
	
		
		
	
	}

