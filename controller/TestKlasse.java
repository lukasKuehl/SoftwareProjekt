package controller;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.TreeMap;

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
		
		//Testen der Methode erstelleTermin
		
		TreeMap<String, String> zeitraum = new TreeMap<String, String>();
		
		zeitraum.put("wpbez", "KW1000");
		zeitraum.put("anfZeitraumTag", "Montag");
		zeitraum.put("endZeitraumTag", "Mittwoch");
		zeitraum.put("anfangsUhrzeit", null);
		zeitraum.put("endUhrzeit", null);
		 
		myController.erstelleTermin("Fhimmelmann", "Test-Termin", zeitraum, "Testen");
		
		*/
		
		/*
		
		//Testen hinzufügen einer Mitarbeitergruppe zu einer Schicht
		
		String[] mitarbeiter = {"Fhimmelmann","Gschmidt","Hmüller"};
		int schichtNr = 10000;
		
		myController.fülleSchicht(schichtNr, mitarbeiter);
		
		*/
		
		/*
		
		//Testen Erstellen eines Wochenplanes nach benutzerdefinierten Einstellungen
		
		 TreeMap<String, String> zeiten = new TreeMap<String, String>();
		 TreeMap<String, Integer> besetzung = new TreeMap<String, Integer>();
		 
		 zeiten.put("Öffnungszeit", "08:00");
		 zeiten.put("HauptzeitBeginn", "10:00");
		 zeiten.put("HauptzeitEnde", "18:00");
		 zeiten.put("Schließzeit", "20:00");
		 
		 besetzung.put("MinBesetzungKasse", 3);
		 besetzung.put("MinBesetzungInfoWaren", 1);
		 besetzung.put("MinBesetzungInfoTechnik", 1);
		 besetzung.put("MehrbesetzungKasse", 2);
		 
		 

				
		myController.erstelleWochenplanCustom("Dgöring", "KW1020", zeiten, besetzung);
		
		//myController.entferneWochenplan("Dgöring", "KW1020");			
		
		*/		
		
		/*
		
		//Testen der Fehlermeldung
		
		myController.printErrorMessage("Dies ist ein Testfehler!\n" + "2.Zeile");
		
		*/
		
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
