package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JDialog;

import data.Tag;
import data.Tblock_Tag;
import data.TerminBlockierung;
import data.Wochenplan;
import model.Einsatzplanmodel;

/**
 * @author 
 * @info Die Klasse TerminStrg dient dazu, jegliche Anfragen zu Terminen durchzuführen und zu validieren.
 */
class TerminStrg {
	
	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private JDialog myDialog = null;
		
	/**
	 * @author 
	 * @info Erzeugen eines Objektes der Klasse TerminStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb später zugewiesen.
	 */
	protected TerminStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}
		
	/**
	 * @author Lukas Kühl
	 * @info Hinterlegen eines neuen Termin/Krankmeldung/Urlaubstages im System 
	 * Keys der "zeitraum" Map: "wpbez", "anfZeitraumTag", "endZeitraumTag", "anfangsUhrzeit", "endUhrzeit"
	 */
	protected boolean erstelleTermin(String username, String bez, TreeMap<String,String> zeitraum, String grund ){
		boolean success = false;	
		
		TreeMap<String, Date> zeitraumDate = new TreeMap<String, Date>();
		String anfZeitraum = null;
		String endZeitraum = null;
		int wpnr = 0;
		
		try{
			//Umwandeln der Wpbez in die eindeutige Wochennummer
	    	wpnr = Integer.parseInt((zeitraum.get("wpbez").substring(2).trim())); 
		}catch(Exception e){
			System.out.println("Fehler bei der Erstellung eines Termins:");
			System.out.println("Die Wochenplanbezeichnung entspricht nicht den Vorgaben (KWXX)!");
		}		
		
		try{
			
			if(this.myModel.getWochenplan(wpnr) != null){
				Wochenplan wp = this.myModel.getWochenplan(wpnr);
				
				LinkedList<Tag> alleTage = this.myModel.getTage();
				
				boolean checkAnfangstag = false;
				boolean checkEndtag = false;
				
				anfZeitraum = zeitraum.get("anfZeitraumTag");
				endZeitraum = zeitraum.get("endZeitraumTag");
				
				for(Tag t: alleTage){
					
					if((t.getWpnr() == wpnr) && (t.getTbez().equals(anfZeitraum))){
						checkAnfangstag = true;						
					}
					
					if((t.getWpnr() == wpnr)&&(t.getTbez().equals(endZeitraum))){
						checkEndtag = true;
					}				
				}				
				
				if(!(checkAnfangstag && checkEndtag)){
					throw new Exception("Die eingetragenen Tage sind im Wochenplan KW" + wpnr + " nicht vorhanden, bitte Wochenplan erneut erstellen");
				}		
				
				//Prüfe, ob die Reihenfolge der Tage vertauscht wurde, z.B. Termin beginnt am Mittwoch und endet am Dienstag
				
				String reihenfolgeFehlermeldung = "Die Reihenfolge der Anfangs- und Endtage des Termins sind vertauscht!";
				
				if(anfZeitraum.equals("Dienstag")){
					if(endZeitraum.equals("Montag")){
						throw new Exception(reihenfolgeFehlermeldung);
					}
				}
				if(anfZeitraum.equals("Mittwoch")){
					if((endZeitraum.equals("Dienstag")) || (endZeitraum.equals("Montag"))){
						throw new Exception(reihenfolgeFehlermeldung);
					}
				}
				
				if(anfZeitraum.equals("Donnerstag")){
					if((endZeitraum.equals("Mittwoch")) || (endZeitraum.equals("Dienstag")) || (endZeitraum.equals("Montag"))){
						throw new Exception(reihenfolgeFehlermeldung);
					}
				}
				
				if(anfZeitraum.equals("Freitag")){
					if((endZeitraum.equals("Donnerstag")) ||(endZeitraum.equals("Mittwoch")) || (endZeitraum.equals("Dienstag")) || (endZeitraum.equals("Montag"))){
						throw new Exception(reihenfolgeFehlermeldung);
					}
				}
				
				if(anfZeitraum.equals("Samstag")){
					if((endZeitraum.equals("Freitag"))||(endZeitraum.equals("Donnerstag")) ||(endZeitraum.equals("Mittwoch")) || (endZeitraum.equals("Dienstag")) || (endZeitraum.equals("Montag"))){
						throw new Exception(reihenfolgeFehlermeldung);
					}
				}
				
				if(anfZeitraum.equals("Sonntag")){
					if((endZeitraum.equals("Samstag"))||(endZeitraum.equals("Freitag"))||(endZeitraum.equals("Donnerstag")) ||(endZeitraum.equals("Mittwoch")) || (endZeitraum.equals("Dienstag")) || (endZeitraum.equals("Montag"))){
						throw new Exception(reihenfolgeFehlermeldung);
					}
				}		
				//Der Zeitraum ist korrekt
			}
			
			String anfangsUhrzeit = zeitraum.get("anfangsUhrzeit");
			String endUhrzeit = zeitraum.get("endUhrzeit");
			Date anfangsUhrzeitDate = null;
			Date endUhrzeitDate = null;
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("hh:ss");
			
			try{
				anfangsUhrzeitDate = sdf.parse(anfangsUhrzeit);
				endUhrzeitDate = sdf.parse(endUhrzeit);
				
			}catch(Exception e){
				System.out.println("Fehler beim erstellen eines neuen Termins:");				
				System.out.println("Fehler beim Konvertieren der Öffnungs- und Schließzeiten");
				e.printStackTrace();
			}
			
			if(anfangsUhrzeitDate.before(endUhrzeitDate)){
				
				int newTbNr = this.myModel.getNewTblocknr();
				
				TerminBlockierung tb = new TerminBlockierung(newTbNr, username, bez, zeitraum.get("anfangsZeitraum"), zeitraum.get("endZeitraum"), zeitraum.get("anfangsUhrzeit"), zeitraum.get("endZeitraum"), grund);
				this.myModel.addTerminBlockierung(tb);	
				success = true;
			}
		}catch(Exception e){
			System.out.println("Fehler beim erstellen eines neuen Termins:");
			e.printStackTrace();			
		}	
	
		return success;
	}
	
	/**
	 * @author 
	 * @info Entfernen eines bereits angelegten Termines aus dem System.
	 */
	protected boolean entferneTermin(int tblocknr, String username){

		boolean success = false;
		//Ausfüllen
		return success;
	}
	
	protected ArrayList<String> getMitarbeiterTermine(String username){
		ArrayList<String> rueckgabe = null;
		return rueckgabe;
	}
	
	protected ArrayList<String> getAlleTermine(String username){
		ArrayList<String> rueckgabe = null;
		return rueckgabe;
	}
	
	
	private boolean checkZeitraum(TreeMap<String, Date> times){
		boolean valid = false;
		
		
		
		return valid;
	}
	
	private boolean checkZeiten(TreeMap<String, Date> times){
		boolean valid = false;
		
		return valid;
	}
	
	
}
