package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.AbstractListModel;

public class WochenplanLoeschenView extends JFrame {

	private JPanel contentPane =null;;
	private JLabel lblWochenplanLschen =null;
	private  JList listWochenplaene =null;
	private JButton  btnLschen = null;

	
	protected WochenplanLoeschenView() {
		
		setTitle("Wochenplan löschen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 250, 800,600);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblWochenplanLschen = new JLabel("Wochenplan l\u00F6schen");
		lblWochenplanLschen.setFont(new Font("Verdana", Font.PLAIN, 21));
		lblWochenplanLschen.setBounds(51, 61, 243, 27);
		contentPane.add(lblWochenplanLschen);
		
		listWochenplaene = new JList();
		
		//Wochenpläne in der Liste hinterlegen
		listWochenplaene.setModel(new AbstractListModel() {
			String[] values = new String[] {"hdaskj\tjsdf\t\t", "djfskld"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listWochenplaene.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listWochenplaene.setBounds(51, 139, 323, 356);
		contentPane.add(listWochenplaene);
		
		btnLschen = new JButton("löschen");
		btnLschen.setBounds(426, 472, 89, 23);
		contentPane.add(btnLschen);
		
		setVisible(true);
	}
}
