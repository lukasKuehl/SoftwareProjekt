package controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import data.Mitarbeiter;
import data.Schicht;
import data.Userrecht;
import model.Einsatzplanmodel;
import view.Einsatzplanview;

/**
 * @author Lukas K�hl
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
			result = this.benutzerSteuerung.benutzerRechte�ndern(username);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerRechte�ndern:");
			e.printStackTrace();			
		}	
		
		return result;			
	}
	
	public boolean f�lleSchicht(int schichtNr, String[] mitarbeiternummern){
		
		boolean result = false;
		try{
			result = this.schichtSteuerung.ausf�llenSchicht(schichtNr, mitarbeiternummern);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode f�lleSchicht:");
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
	
	public boolean erstelleWochenplanStandard(String username, String wpbez){
		
		boolean success = false;
		try{
			success = this.wochenplanSteuerung.erstelleWochenplanStandard(username, wpbez);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode erstelleWochenplanStandard:");
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
	
	public boolean verschickeWochenplan(String username, String wpbez, JTable wochenplan){
		
		boolean result = false;
		try{
			result = this.wochenplanSteuerung.verschickeWochenplan(username, wpbez, wochenplan, wochenplan.getTableHeader());
			
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
	
	public ArrayList<String> getTage(String wpbez){
		ArrayList<String> rueckgabe = null;
		try{
			rueckgabe = this.wochenplanSteuerung.getTage(wpbez);
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode getTage:");
			e.printStackTrace();			
		}	
		return rueckgabe;		
	}
	
	//Aussortierung bereits verplanter Mitarbeiter f�r die Senderschicht
	public ArrayList<String> getMitarbeiterSchichten(String wpbez, String tagbez, String username){
		ArrayList<String> rueckgabe = null;
		try{
			rueckgabe = this.schichtSteuerung.getMitarbeiterSchichten(wpbez, tagbez, username);
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode getMitarbeiterSchichten:");
			e.printStackTrace();			
		}	
		return rueckgabe;
	}
	
	public ArrayList<String> getAndereMitarbeiterSchichten(String wpbez, String tagbez, String username, int schichtNr){
		ArrayList<String> rueckgabe = null;
		try{
			rueckgabe = this.schichtSteuerung.getAndereMitarbeiterSchichten(wpbez, username, schichtNr);
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode getAndereMitarbeiterSchichten:");
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
	
	public ArrayList<String> getTauschanfragen(String username){
		ArrayList<String> rueckgabe = null;
		
		try{
			rueckgabe = this.tauschanfrageSteuerung.getTauschanfragen(username);
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode getTauschanfragen:");
			e.printStackTrace();			
		}	
		
		return rueckgabe;
	}
	
	public ArrayList<String> getVerfuegbareMitarbeiter(int schichtNr){
		ArrayList<String> rueckgabe = null;
		
		try{
			rueckgabe = this.schichtSteuerung.getVerf�gbareMitarbeiter(schichtNr);
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode getTauschanfragen:");
			e.printStackTrace();			
		}	
		
		return rueckgabe;
	}	
	
	protected Einsatzplanview getView(){
		return this.view;		
	}
	
	
	//Hilfsmethode zum konvertieren einer wpbez zu einer wpnr
	protected int getWpnr(String wpbez){	
		
		try{
			//Umwandeln der Wpbez in die eindeutige Wochennummer
	    	int wpnr = Integer.parseInt((wpbez.substring(2).trim())); 
	    	return wpnr;
			
		}catch(NumberFormatException nfe){
			return 0;
		}	
	}
	
	protected boolean isUserAdmin(String username){
		
		Mitarbeiter user = model.getMitarbeiter(username);
		
		if(user != null){
			Userrecht recht = model.getUserrecht(user.getJob());
			
			if(recht.getBenutzerrolle().equals("Admin")){	
				return true;
			}			
		}
		return false;		
	}
}
