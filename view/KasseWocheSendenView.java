package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class KasseWocheSendenView extends JFrame {

private JFrame frame;


	public KasseWocheSendenView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setTitle("Wochenplan versenden");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 800,600);
		setVisible(true);
	}

}
