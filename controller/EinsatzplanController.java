package controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import data.Schicht;
import model.Einsatzplanmodel;
import view.Einsatzplanview;


/**
 * @author Lukas Kühl
 * @info Allgemeiner Controller zum entgegennehmen der Anfragen aus der GUI und weiterleiten an die spezifischen Steuerungen
 */
public class EinsatzplanController {

	//Initialiserung der Instanzvariablen
	private Einsatzplanmodel model = null;
	private Einsatzplanview view = null;
	private BenutzerStrg benutzerSteuerung = null;
	private SchichtStrg schichtSteuerung = null;
	private TauschanfrageStrg tauschanfrageSteuerung = null;
	private TerminStrg terminSteuerung = null;
	private WochenplanStrg wochenplanSteuerung = null;
		
	public EinsatzplanController(Einsatzplanmodel epm){
		
		this.model = epm;
		this.view = new Einsatzplanview(this, this.model);
		this.benutzerSteuerung = new BenutzerStrg(this, this.model);
		this.schichtSteuerung = new SchichtStrg(this, this.model);
		this.tauschanfrageSteuerung = new TauschanfrageStrg(this, this.model);
		this.terminSteuerung = new TerminStrg(this, this.model);
		this.wochenplanSteuerung = new WochenplanStrg(this, this.model);
		
	}
	
	public boolean benutzerAnmelden(String username, String pw){
		
		boolean result = false;
		try{
			result = this.benutzerSteuerung.benutzerAnmelden(username, pw);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		
		
		return result;
	}
	
	public boolean benutzerAbmelden(String username){
		
		boolean result = false;
		try{
			result = this.benutzerSteuerung.benutzerAbmelden(username);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAbmelden:");
			e.printStackTrace();			
		}	
		
		return result;		
	}
	
	public boolean benutzerAnlegen(String username, String pw){
		
		boolean result = false;
		try{
			result = this.benutzerSteuerung.benutzerErstellen(username, pw);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerErstellen:");
			e.printStackTrace();			
		}	
		
		return result;		

	}
	
	public boolean benutzerEntfernen(String username){
		
		boolean result = false;
		try{
			result = this.benutzerSteuerung.benutzerEntfernen(username);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerEntfernen:");
			e.printStackTrace();			
		}	
		
		return result;	
		
	}
	
	public boolean benutzerRechteWechsel(String username){
		
		boolean result = false;
		try{
			result = this.benutzerSteuerung.benutzerRechteÄndern(username);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerRechteÄndern:");
			e.printStackTrace();			
		}	
		
		return result;			
	}
	
	public boolean fülleSchicht(int schichtNr, String[] mitarbeiternummern){
		
		boolean result = false;
		try{
			result = this.schichtSteuerung.ausfüllenSchicht(schichtNr, mitarbeiternummern);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode fülleSchicht:");
			e.printStackTrace();			
		}	
		
		return result;			
	}
	
	public boolean erstelleTauschanfrage(String senderName, int senderSchichtNr, String empfaengerName, int empfaengerSchichtNr ){
		
		boolean result = false;
		try{
			result = this.tauschanfrageSteuerung.erstelleTauschanfrage(senderName, senderSchichtNr, empfaengerName, empfaengerSchichtNr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode erstelleTauschanfrage:");
			e.printStackTrace();			
		}	
		
		return result;		
	
	}
	
	public boolean entferneTauschanfrage(int tauschanfrageNr){
		
		boolean result = false;
		try{
			result = this.tauschanfrageSteuerung.entferneTauschanfrage(tauschanfrageNr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode entferneTauschanfrage:");
			e.printStackTrace();			
		}	
		
		return result;		
		
	}
	
	public boolean akzeptiereTauschanfrage(String empfaengerName, int tauschanfrageNr){
		
		boolean result = false;
		try{
			result = this.tauschanfrageSteuerung.akzeptiereTauschanfrage(empfaengerName, tauschanfrageNr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode akzeptiereTauschanfrage:");
			e.printStackTrace();			
		}	
		
		return result;			
	}
	
	public boolean erstelleTermin(String username, String bez, TreeMap<String, String> zeitraum, String grund){
		
		boolean result = false;
		try{
			result = this.terminSteuerung.erstelleTermin(username, bez, zeitraum, grund);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode erstelleTermin:");
			e.printStackTrace();			
		}	
		
		return result;		
	}
	
	public boolean entferneTermin(int tblocknr, String username){
		
		boolean result = false;
		try{
			result = this.terminSteuerung.entferneTermin(tblocknr, username);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode entferneTermin:");
			e.printStackTrace();			
		}	
		
		return result;		
	}
	
	public JTable generiereWochenplanView(String wpbez){
		
		JTable result = null;
		try{
			result = this.wochenplanSteuerung.generiereWochenplanView(wpbez);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode generiereWochenplanView:");
			e.printStackTrace();			
		}	
		
		return result;	
	
	}
	
	public boolean erstelleWochenplanCustom(String username, String wpbez, TreeMap<String, String> zeiten, TreeMap<String, Integer> besetzung  ){
		
		boolean success = false;
		try{
			success = this.wochenplanSteuerung.erstelleWochenplanCustom(username, wpbez, zeiten, besetzung);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode erstelleWochenplanCustom:");
			e.printStackTrace();			
		}	
		
		return success;		
	}
	
	public boolean publishWochenplan(String username, String wpbez){
		
		boolean result = false;
		try{
			result = this.wochenplanSteuerung.publishWochenplan(username, wpbez);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode publishWochenplan:");
			e.printStackTrace();			
		}	
		
		return result;		
		
	}
	
	public boolean entferneWochenplan(String username, String wpbez){
		
		boolean result = false;
		try{
			result = this.wochenplanSteuerung.entferneWochenplan(username, wpbez);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode entferneWochenplan:");
			e.printStackTrace();			
		}	
		
		return result;					
	}
	
	public boolean verschickeWochenplan(String username, String wpbez, JTable wochenplan, JTableHeader wochenplanHeader){
		
		boolean result = false;
		try{
			result = this.wochenplanSteuerung.verschickeWochenplan(username, wpbez, wochenplan,wochenplanHeader);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode verschickeWochenplan:");
			e.printStackTrace();			
		}	
		
		return result;			
		
	}
	
	public ArrayList<String> getAlleTermine(String username){
		ArrayList<String> rueckgabe = null;
		try{
			rueckgabe = this.terminSteuerung.getAlleTermine(username);
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode getAlleTermine:");
			e.printStackTrace();			
		}	
		return rueckgabe;
	}
	
	public ArrayList<String> getMitarbeiterTermine(String username){
		ArrayList<String> rueckgabe = null;
		try{
			rueckgabe = this.terminSteuerung.getMitarbeiterTermine(username);
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode getMitarbeiterTermine:");
			e.printStackTrace();			
		}	
		return rueckgabe;
	}
	
	public ArrayList<String> getWochenplaene(){
		ArrayList<String> rueckgabe = null;
		try{
			rueckgabe = this.wochenplanSteuerung.getWochenplaene();
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode getWochenplaene:");
			e.printStackTrace();			
		}	
		return rueckgabe;
	}
	
	
	protected Einsatzplanview getView(){
		return this.view;		
	}	
}
