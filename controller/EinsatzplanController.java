package controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import data.Mitarbeiter;
import data.Schicht;
import data.Userrecht;
import model.Einsatzplanmodel;
import view.Einsatzplanview;

/**
 * @author Lukas Kühl
 * @info Allgemeiner Controller zum entgegennehmen der Anfragen aus der GUI und weiterleiten an die spezifischen Steuerungen.
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
		
	
	/**
	 * @author Lukas Kühl
	 * @info Erzeugen eines neuen Objektes der Klasse EinsatzplanController und Erzeugen der benötigten Steuerungen, um die Anfragen der View bearbeiten zu können.
	 */
	public EinsatzplanController(Einsatzplanmodel epm){
		
		this.model = epm;
		this.view = new Einsatzplanview(this, this.model);
		this.benutzerSteuerung = new BenutzerStrg(this, this.model);
		this.schichtSteuerung = new SchichtStrg(this, this.model);
		this.tauschanfrageSteuerung = new TauschanfrageStrg(this, this.model);
		this.terminSteuerung = new TerminStrg(this, this.model);
		this.wochenplanSteuerung = new WochenplanStrg(this, this.model);
		
	}	
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anmeldungsanfrage aus der View(mit den Parametern benutzername und passwort) und Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean benutzerAnmelden(String username, String pw){
		
		boolean result = false;
		try{
			result = this.benutzerSteuerung.benutzerAnmelden(username, pw);
			
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode benutzerAnmelden:\n" + e.getMessage();
			this.printErrorMessage(fehler);					
		}	
		
		return result;
	}
	
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Abmeldungsanfrage aus der View(mit dem Parameter benutzername) und Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean benutzerAbmelden(String username){
		
		boolean result = false;
		try{
			result = this.benutzerSteuerung.benutzerAbmelden(username);
			
		}catch(Exception e){
			e.printStackTrace();
			
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode benutzerAbmelden:\n" + e.getMessage();
			this.printErrorMessage(fehler);				
		}	
		
		return result;		
	}	
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage aus der View zum Anlegen eines neuen Mitarbeiters im System. Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean benutzerErstellen(Mitarbeiter m){
		
		boolean result = false;
		try{
			result = this.benutzerSteuerung.benutzerErstellen(m);
			
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode benutzerErstellen:\n" + e.getMessage();
			this.printErrorMessage(fehler);					
		}	
		
		return result;		
	}	
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Löschanfrage aus der View zum Entfernen eines nicht mehr benötigten Mitarbeiteraccounts aus dem System. Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean benutzerEntfernen(String username){
		
		boolean result = false;
		try{
			result = this.benutzerSteuerung.benutzerEntfernen(username);
			
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode benutzerEntfernen:\n" + e.getMessage();
			this.printErrorMessage(fehler);	
			
		}	
		
		return result;		
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage aus der View zum Ändern der Rechte eines Mitarbeiters (z.B. durch Wechsel der Funktion von Kassierer --> Kassenbüro). Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean benutzerRechteWechsel(String username){
		
		boolean result = false;
		try{
			result = this.benutzerSteuerung.benutzerRechteÄndern(username);
			
		}catch(Exception e){			
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode benutzerRechteWechsel:\n" + e.getMessage();
			this.printErrorMessage(fehler);					
		}	
		
		return result;			
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage aus der View zum Hinzufügen eines oder mehrerer Mitarbeiter zu einer bestimmten Schicht im Einsatzplan. Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean fülleSchicht(int schichtNr, String[] mitarbeiternamen){
		
		boolean result = false;
		try{
			result = this.schichtSteuerung.ausfüllenSchicht(schichtNr, mitarbeiternamen);
			
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode fülleSchicht:\n" + e.getMessage();
			this.printErrorMessage(fehler);					
		}	
		
		return result;			
	}
	

	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage aus der View zum Hinzufügen einer Tauschanfrage zum Tausch von Schichten zwischen zwei Mitarbeitern innerhalb eines Wochenplanes. Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean erstelleTauschanfrage(String senderName, int senderSchichtNr, String empfaengerName, int empfaengerSchichtNr ){
		
		boolean result = false;
		try{
			result = this.tauschanfrageSteuerung.erstelleTauschanfrage(senderName, senderSchichtNr, empfaengerName, empfaengerSchichtNr);
			
		}catch(Exception e){			
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode erstelleTauschanfrage:\n" + e.getMessage();
			this.printErrorMessage(fehler);						
		}	
		
		return result;		
	}
	
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Löschanfrage aus der View zum Entfernen einer bereits erstellten Tauschanfrage aus dem System (z.B. falls der Empfänger der Tauschanfrage nicht bereit ist die Schicht zu tauschen). Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean entferneTauschanfrage(int tauschanfrageNr){
		
		boolean result = false;
		try{
			result = this.tauschanfrageSteuerung.entferneTauschanfrage(tauschanfrageNr);
			
		}catch(Exception e){			
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode entferneTauschanfrage:\n" + e.getMessage();
			this.printErrorMessage(fehler);					
		}	
		
		return result;			
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Bestätigung für eine bestimmte Tauschanfrage aus der View. Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean akzeptiereTauschanfrage(String empfaengerName, int tauschanfrageNr){
		
		boolean result = false;
		try{
			result = this.tauschanfrageSteuerung.akzeptiereTauschanfrage(empfaengerName, tauschanfrageNr);
			
		}catch(Exception e){			
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode akzeptiereTauschanfrage:\n" + e.getMessage();
			this.printErrorMessage(fehler);							
		}	
		
		return result;			
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Erstellen einer neuen Terminblockierung im System (Eine Terminblockierung bildet die Funktionen Krankheit/Termin/Urlaub ab). Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean erstelleTermin(String username, String bez, TreeMap<String, String> zeitraum, String grund){
		
		boolean result = false;
		try{
			result = this.terminSteuerung.erstelleTermin(username, bez, zeitraum, grund);
			
		}catch(Exception e){			
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode erstelleTermin:\n" + e.getMessage();
			this.printErrorMessage(fehler);					
		}	
		
		return result;		
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Löschanfrage zum Entfernen eines bereits erstellten Termines aus dem System. Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean entferneTermin(int tblocknr, String username){
		
		boolean result = false;
		try{
			result = this.terminSteuerung.entferneTermin(tblocknr, username);
			
		}catch(Exception e){			
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode entferneTermin:\n" + e.getMessage();
			this.printErrorMessage(fehler);					
		}	
		
		return result;		
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Erstellen eines JTables mit den Einteilungen für die betroffenen Mitarbeiter, abhängig von der übergebenen Kalenderwoche. Falls kein Wochenplan für die angefragte Woche verfügbar ist, wird null zurück gegeben.
	 */
	public JTable generiereWochenplanView(String wpbez){
		
		JTable result = null;
		try{
			result = this.wochenplanSteuerung.generiereWochenplanView(wpbez);
			
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode generiereWochenplanView:\n" + e.getMessage();
			this.printErrorMessage(fehler);						
		}	
		
		return result;		
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Erstellen eines neuen benutzerdefinierten Wochenplanes im System. Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean erstelleWochenplanCustom(String username, String wpbez, TreeMap<String, String> zeiten, TreeMap<String, Integer> besetzung  ){
		
		boolean success = false;
		try{
			success = this.wochenplanSteuerung.erstelleWochenplanCustom(username, wpbez, zeiten, besetzung);
			
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode erstelleWochenplanCustom:\n" + e.getMessage();
			this.printErrorMessage(fehler);				
		}	
		
		return success;		
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Erstellen eines neuen standardmäßigen Wochenplanes im System. Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean erstelleWochenplanStandard(String username, String wpbez){
		
		boolean success = false;
		try{
			success = this.wochenplanSteuerung.erstelleWochenplanStandard(username, wpbez);
			
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode erstelleWochenplanStandard:\n" + e.getMessage();
			this.printErrorMessage(fehler);					
		}	
		
		return success;		
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Veröffentlichten eines bisher privat hinterlegten Wochenplanes im System. Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean publishWochenplan(String username, String wpbez){
		
		boolean result = false;
		try{
			result = this.wochenplanSteuerung.publishWochenplan(username, wpbez);
			
		}catch(Exception e){			
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode publishWochenplan:\n" + e.getMessage();
			this.printErrorMessage(fehler);						
		}	
		
		return result;			
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Löschanfrage zum Entfernen eines bereits erstellten Wochenplanes aus dem System. Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean entferneWochenplan(String username, String wpbez){
		
		boolean result = false;
		try{
			result = this.wochenplanSteuerung.entferneWochenplan(username, wpbez);
			
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode entferneWochenplan:\n" + e.getMessage();
			this.printErrorMessage(fehler);					
		}	
		
		return result;					
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Verschicken eines Wochenplanes an alle Mitarbeiter, die auch im System hinterlegt sind(per E-Mail). Rückmeldung an die View über Erfolg/Misserfolg.
	 */
	public boolean verschickeWochenplan(String username, String wpbez, JTable wochenplan){
		
		boolean result = false;
		try{
			result = this.wochenplanSteuerung.verschickeWochenplan(username, wpbez, wochenplan, wochenplan.getTableHeader());
			
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode verschickeWochenplan:\n" + e.getMessage();
			this.printErrorMessage(fehler);						
		}	
		
		return result;			
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Ausgeben aller hinterlegten Termine aus dem System, zum Prüfen durch einen berechtigten Nutzer(Admin). Rückmeldung an die View über Erfolg/Misserfolg.
	 */	
	public ArrayList<String> getAlleTermine(String username){
		ArrayList<String> rueckgabe = null;
		try{
			rueckgabe = this.terminSteuerung.getAlleTermine(username);
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode getAlleTermine:\n" + e.getMessage();
			this.printErrorMessage(fehler);						
		}	
		return rueckgabe;
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Ausgeben aller hinterlegten Termine für einen bestimmten Mitarbeiter aus dem System. Falls dieser Mitarbeiter keine Termine hinterlegt hat, eine leere ArrayList<String> zurück gegeben.
	 */	
	public ArrayList<String> getMitarbeiterTermine(String username){
		ArrayList<String> rueckgabe = null;
		try{
			rueckgabe = this.terminSteuerung.getMitarbeiterTermine(username);
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode getMitarbeiterTermine:\n" + e.getMessage();
			this.printErrorMessage(fehler);						
		}	
		return rueckgabe;
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Ausgeben aller hinterlegten Tage für einen bestimmten Wochenplan aus dem System. Falls dieser Wochenplan noch nicht erstellt wurde, wird eine leere ArrayList<String> zurück gegeben.
	 */		
	public ArrayList<String> getTage(String wpbez){
		ArrayList<String> rueckgabe = null;
		try{
			rueckgabe = this.wochenplanSteuerung.getTage(wpbez);
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode getTage:\n" + e.getMessage();
			this.printErrorMessage(fehler);							
		}	
		return rueckgabe;		
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Ausgeben aller hinterlegten Schichten eines Mitarbeiters für einen bestimmten Tag innerhalb eines Wochenplanes im System. Falls der/die Mitarbeiter/-in an diesem Tag in keiner Schicht eingeteilt wurde, wird eine leere ArrayList<String> zurück gegeben.
	 */		
	public ArrayList<String> getMitarbeiterSchichten(String wpbez, String tagbez, String username){
		ArrayList<String> rueckgabe = null;
		try{
			rueckgabe = this.schichtSteuerung.getMitarbeiterSchichten(wpbez, tagbez, username);
		}catch(Exception e){		
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode getMitarbeiterSchichten:\n" + e.getMessage();
			this.printErrorMessage(fehler);						
		}	
		return rueckgabe;
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Ausgeben aller hinterlegten Schichten anderer Mitarbeiter innerhalb einer Woche (Aufzeigen von alternativen Schichten zum erstellen einer Tauschanfrage). Falls es keine anderen Schichteinteilungen innerhalb der übergebenen Woche gibt, wird eine leere ArrayList<String> zurück gegeben.
	 */	
	public ArrayList<String> getAndereMitarbeiterSchichten(String wpbez, String username, int schichtNr){
		ArrayList<String> rueckgabe = null;
		try{
			rueckgabe = this.schichtSteuerung.getAndereMitarbeiterSchichten(wpbez, username, schichtNr);
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode getAndereMitarbeiterSchichten:\n" + e.getMessage();
			this.printErrorMessage(fehler);				
		}	
		return rueckgabe;
	}	
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Ausgeben aller hinterlegten Wochenpläne aus dem System. Falls es keine Wochenpläne im System gibt, wird eine leere ArrayList<String> zurück gegeben.
	 */
	public ArrayList<String> getWochenplaene(){
		ArrayList<String> rueckgabe = null;
		try{
			rueckgabe = this.wochenplanSteuerung.getWochenplaene();
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode getWochenplaene:\n" + e.getMessage();
			this.printErrorMessage(fehler);						
		}	
		return rueckgabe;
	}	
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Ausgeben aller hinterlegten Schichten für einen bestimmten Tag innerhalb eines Wochenplanes.
	 */
	public ArrayList<String> getTagesSchichten(String wpbez, String tagBez){
		
		ArrayList<String> rueckgabe = null;
		
		try{
			rueckgabe = this.schichtSteuerung.getTagesSchichten(wpbez, tagBez);
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode getTagesSchichten:\n" + e.getMessage();
			this.printErrorMessage(fehler);							
		}	
		
		return rueckgabe;		
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Ausgeben aller hinterlegten Tauschanfragen aus dem System, bei denen der übergebene Mitarbeiter als Empfänger eingetragen wurde. Falls es keine Tauschanfragen im System gibt, bei denen der übergebene Mitarbeiter als Empfänger eingetragen wurde, wird eine leere ArrayList<String> zurück gegeben.
	 */
	public ArrayList<String> getTauschanfragen(String username){
		ArrayList<String> rueckgabe = null;
		
		try{
			rueckgabe = this.tauschanfrageSteuerung.getTauschanfragen(username);
		}catch(Exception e){			
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode getTauschanfragen:\n" + e.getMessage();
			this.printErrorMessage(fehler);					
		}	
		
		return rueckgabe;
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Weiterleitung einer Anfrage zum Ausgeben aller verfügbaren Mitarbeiter für eine bereits hinterlegt Schicht innerhalb des Systems. Falls es keine verfügbaren Mitarbeiter für die übergebene Schicht gibt, wird eine leere ArrayList<String> zurück gegeben.
	 */
	public ArrayList<String> getVerfuegbareMitarbeiter(int schichtNr){
		ArrayList<String> rueckgabe = null;
		
		try{
			rueckgabe = this.schichtSteuerung.getVerfügbareMitarbeiter(schichtNr);
		}catch(Exception e){
			e.printStackTrace();
			String fehler = "Fehler innerhalb des Controllers:\n" + "Fehler beim Aufruf der Methode getVerfuegbareMitarbeiter:\n" + e.getMessage();
			this.printErrorMessage(fehler);				
		}	
		
		return rueckgabe;
	}	
	
	/**
	 * @author Lukas Kühl
	 * @info Hilfsmethode zum Auslesen der hinterlegten View innerhalb des Controllers.
	 */
	protected Einsatzplanview getView(){
		return this.view;		
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Hilfsmethode zum Konvertieren einer wpbez zu einer wpnr.
	 */	
	protected int getWpnr(String wpbez){	
		
		try{
			//Umwandeln der Wpbez in die eindeutige Wochennummer
	    	int wpnr = Integer.parseInt((wpbez.substring(2).trim())); 
	    	return wpnr;
			
		}catch(NumberFormatException nfe){
			this.printErrorMessage("Die Wpbez konnte nicht konvertiert werden!");			
			return 0;
		}	
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Hilfsmethode zum Prüfen, ob der übergebene Nutzer über Administrationsrechte verfügt.
	 */	
	protected boolean isUserAdmin(String username){
		
		Mitarbeiter user = model.getMitarbeiter(username);
		
		if(user != null){
			//Rufe die zugehörige Berechtigung ab, die dem Job des Mitarbeiters zugeordnet wurde.
			Userrecht recht = model.getUserrecht(user.getJob());			
			
			if(recht.getBenutzerrolle().equals("Admin")){	
				return true;
			}			
		}
		return false;		
	}	
	
	protected void printErrorMessage(String message){
		
		String info = "<html><font color=red>Fehlermeldung:</font></html>\n";
		info = info + message;
		
		JOptionPane.showMessageDialog(null, info, "Error", JOptionPane.ERROR_MESSAGE);
			
	}
	
	
}
