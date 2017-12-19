package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JDialog;

import data.Mitarbeiter;
import data.Tag;
import data.Tblock_Tag;
import data.TerminBlockierung;
import data.Userrecht;
import data.Wochenplan;
import model.Einsatzplanmodel;

/**
 * @author 
 * @info Die Klasse TerminStrg dient dazu, jegliche Anfragen zu Terminen durchzuf�hren und zu validieren.
 */
class TerminStrg {
	
	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private JDialog myDialog = null;
		
	/**
	 * @author 
	 * @info Erzeugen eines Objektes der Klasse TerminStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb sp�ter zugewiesen.
	 */
	protected TerminStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}
		
	/**
	 * @author Lukas K�hl
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
				
				//Pr�fe, ob die Reihenfolge der Tage vertauscht wurde, z.B. Termin beginnt am Mittwoch und endet am Dienstag
				
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
				System.out.println("Fehler beim Konvertieren der �ffnungs- und Schlie�zeiten");
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
	 * @author Lukas K�hl
	 * @info Entfernen eines bereits angelegten Termines aus der Datenbank.
	 */
	protected boolean entferneTermin(int tblocknr, String username){

		boolean success = false;
		boolean valid = false;
		
		LinkedList<TerminBlockierung> alleTerminblockierungen = this.myModel.getTerminBlockierungen();
		
		for(TerminBlockierung tb: alleTerminblockierungen){
			//Pr�fe, ob der Termin existiert und ob der Nutzer die Berechtigung zum L�schen besitzt(Bedingung: Ersteller/in des Termins)
			if((tb.getTblocknr() == tblocknr) && tb.getBenutzername().equals(username)){
				valid = true;				
			}
		}
		
		if(valid){
			
			this.myModel.deleteTerminBlockierung(tblocknr);			
			
			if(this.myModel.getTblock_TagTB(tblocknr) != null){
				this.myModel.deleteTblock_Tag(tblocknr);			
			}		
			success = true;
		}
		else{
			System.out.println("Der Termin konnte nicht gel�scht werden!");		
		}	
		
		return success;
	}
	
	
	/**
	 * @author Lukas K�hl
	 * @info Ausgabe einer Liste mit den Terminen eines Mitarbeiters f�r die Anzeige in dem TermineDialog der View
	 */
	protected ArrayList<String> getMitarbeiterTermine(String username){
		ArrayList<String> rueckgabe = new ArrayList<String>();
		
		LinkedList<TerminBlockierung> alleTermine = this.myModel.getTerminBlockierungen();
		LinkedList<TerminBlockierung> mitarbeiterTermine = new LinkedList<TerminBlockierung>();
		
		//Suche nach den Terminen, die dem �bergebenen Mitarbeiter zugeordnet wurden
		for(TerminBlockierung tb : alleTermine){
			if(tb.getBenutzername().equals(username)){
				mitarbeiterTermine.add(tb);
			}
		}		
		
		LinkedList<Tblock_Tag> alleTerminZuordnungen = this.myModel.getAlleTblock_Tag();
		// Zuordnung von Terminen zu einem Wochenplan --> Key TerminNr; Value WochenplanNr
		TreeMap<Integer, Integer> mitarbeiterTerminZuordnungen = new TreeMap<Integer, Integer>();
		
		for(TerminBlockierung tb: mitarbeiterTermine){
			
			for(Tblock_Tag tbt : alleTerminZuordnungen){
				
				if(tb.getTblocknr() == tbt.getTblocknr())
					mitarbeiterTerminZuordnungen.put(tbt.getTblocknr(), tbt.getWpnr());			
			}		
		}		
		
		//�bertrage die Termine im Format Terminbezeichnung - KW - Anfangstag - Endtag - Anfangsuhrzeit-Enduhrzeit in die R�ckgabeListe
		for(TerminBlockierung tb : mitarbeiterTermine){
			String temp = tb.getBbez() + " - KW" + mitarbeiterTerminZuordnungen.get(String.valueOf(tb.getTblocknr())) + " - ";
			
			//Falls es sich um einen eint�gigen Termin handelt, wird der Anfangs- und Endtag zusammengefasst
			if(tb.getAnfzeitraum().equals(tb.getEndzeitraum())){
				temp = temp + tb.getAnfzeitraum() + " - ";
			}
			else{
				temp = temp + tb.getAnfzeitraum() + "-" + tb.getEndzeitraum() + " - ";
			}
			
			temp = temp + tb.getAnfanguhrzeit() + "-" + tb.getEndeuhrzeit() + " Uhr";
			
			//Datensatz ist vollst�ndig und kann in die R�ckgabeliste eingetragen werden
			rueckgabe.add(temp);
			
		}
		
		return rueckgabe;
	}
	
	protected ArrayList<String> getAlleTermine(String username){
		ArrayList<String> rueckgabe = null;		
		/*
		boolean admin = false;
		LinkedList<Userrecht> rechte = this.myModel.getUserrechte();
		Mitarbeiter ma = this.myModel.getMitarbeiter(username);		
		
		for(Userrecht u: rechte){
			//Durchsuche die Job --> Recht Zuordnung nach dem Job des Mitarbeiters
			if(u.getJob().equals(ma.getJob())){
				//Pr�fe, ob die zugeh�rige Rolle Admin ist
				if(u.getBenutzerrolle().equals("Admin")){
					//Der/Die Mitarbeiter/in hat die notwendigen Berechtigungen
					admin = true;
				}			
			}		
		}
		
		if(admin){
			
			LinkedList<TerminBlockierung> alleTermine = this.myModel.getTerminBlockierungen();
						
			LinkedList<Tblock_Tag> alleTerminZuordnungen = this.myModel.getAlleTblock_Tag();
			
			//�bertrage die Termine im Format Terminbezeichnung - Mitarbeitername - KW - Anfangstag - Endtag - Anfangsuhrzeit-Enduhrzeit in die R�ckgabeListe
			for(TerminBlockierung tb : alleTermine){
				
				for(Tblock_Tag tbt : alleTerminZuordnungen){
					
					if(tbt.getTblocknr() == tb.getTblocknr()){
						
						String temp = tb.getBbez() + " - " + this.myModel.getMitarbeiter(tb.getBenutzername()).getVorname() + " " + this.myModel.getMitarbeiter(tb.getBenutzername()).getName() +" - KW" + tbt.getWpnr() + " - ";
						
						//Falls es sich um einen eint�gigen Termin handelt, wird der Anfangs- und Endtag zusammengefasst
						if(tb.getAnfzeitraum().equals(tb.getEndzeitraum())){
							temp = temp + tb.getAnfzeitraum() + " - ";
						}
						else{
							temp = temp + tb.getAnfzeitraum() + "-" + tb.getEndzeitraum() + " - ";
						}
						
						temp = temp + tb.getAnfanguhrzeit() + "-" + tb.getEndeuhrzeit() + " Uhr";
						
						//Datensatz ist vollst�ndig und kann in die R�ckgabeliste eingetragen werden
						rueckgabe.add(temp);						
					}					
				}			
			}		
		}
		else{
			throw new Exception("Die notwendigen Berechtigungen sind nicht vorhanden, bitte wenden Sie sich an den Systemadministrator.");
		}
		*/
		
		return rueckgabe;
	}	
}
