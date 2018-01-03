package controller;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Einsatzplanmodel;

public class TestKlasse {

	private static EinsatzplanController myController = null;
	private static Einsatzplanmodel myModel = null;
	
	public static void main(String[] args) {
		
		myModel = new Einsatzplanmodel();			
		myController = new EinsatzplanController(myModel);		
		
		
		/*
		
		//Testen Hinzufügen eines neuen Mitarbeiters zu einer Schicht
		
		String[] data = {"Cfriese", "Dgöring", "Kmuster", "r"};
		
		myController.fülleSchicht(10000, data);
		
		*/
	
		/*
		
		//Testen Erstellen eines Wochenplanes nach Standardeinstellungen
		
		
		myController.erstelleWochenplanStandard("Dgöring", "KW1002");
		
		myController.entferneWochenplan("Dgöring", "KW1002");			
		
		*/		
		
		/*
		
		//Testen der Methode getAlleTermine
		
		ArrayList<String> alleTermine = myController.getAlleTermine("Dgöring");
		
		
		if(alleTermine != null){
			
			for(String s: alleTermine){
				System.out.println(s);
			}	
			
		}
		
		*/
		
		/*
		
		//Testen der BenutzerAnmelden Funktion
		
		System.out.println(myController.benutzerAnmelden("Kmuster", "passwort1"));
		
		*/
		
		/*
		
		//Testen der Einsatzplanübersicht
		
		JTable jt = myController.generiereWochenplanView("KW1001");
		
		
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(1,1));
		
		jt.getTableHeader().setSize(jt.getTableHeader().getPreferredSize());
        jt.setSize(jt.getPreferredSize());
        // Set size of table     
        jt.setPreferredScrollableViewportSize(jt.getPreferredScrollableViewportSize());         
        // This will resize the height of the table automatically 
        // to all data without scrolling. 
        jt.setFillsViewportHeight(true);         
        JScrollPane jps = new JScrollPane(jt);
        frame.add(jps);	
		
		frame.pack();
		frame.setVisible(true);	
		
		
		*/
		
		/*
		ArrayList<String> mitarbeiterTermine = myController.getMitarbeiterTermine("Oenns");
		
		for(String s: mitarbeiterTermine){
			System.out.println(s);
		}
		*/
		/*
		ArrayList<String> mitarbeiterTauschanfragen = myController.getTauschanfragen("Fhimmelmann");
		
		for(String s: mitarbeiterTauschanfragen){
			System.out.println(s);
		}
		
		*/
		/*
		ArrayList<String
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		> wochenTage = myController.getTage("KW1001");
		
		for(String s: wochenTage){
			System.out.println(s);
		}
		
		*/
		
		/*
		ArrayList<String> andereMitarbeiterSchichten = myController.getAndereMitarbeiterSchichten("KW1001", "Tag1", "Kmuster", 10000);
		
		for(String s: andereMitarbeiterSchichten){
			System.out.println(s);
		}	
		
		*/
		/*
		
		ArrayList<String> mitarbeiterSchichten = myController.getMitarbeiterSchichten("KW1001", "Tag1", "Kmuster");
		
		for(String s: mitarbeiterSchichten){
			System.out.println(s);
		}	
		*/		
		
		/*
		ArrayList<String> mitarbeiter = myController.getVerfuegbareMitarbeiter(10000);
		
		for(String s: mitarbeiter){
			System.out.println(s);
		}
		*/
		
		/*		
		ArrayList<String> wochenplaene = myController.getWochenplaene();
		
		for(String s: wochenplaene){
			System.out.println(s);
		}
		*/
		

	}

}
