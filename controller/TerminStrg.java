package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JDialog;

import data.Ma_Schicht;
import data.Mitarbeiter;
import data.Schicht;
import data.Tag;
import data.Tblock_Tag;
import data.TerminBlockierung;
import data.Userrecht;
import data.Wochenplan;
import model.Einsatzplanmodel;

/**
 * @author Lukas K�hl
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
		
		String anfZeitraum = null;
		String endZeitraum = null;
		
		//Konvertieren der �bergebenen wpbez in eine wpnr
		int wpnr = myController.getWpnr(zeitraum.get("wpbez")); 		
		
		try{
			
			Wochenplan wp = null;
			
			if(this.myModel.getWochenplan(wpnr) != null){
				wp = this.myModel.getWochenplan(wpnr);
				
				LinkedList<Tag> alleTage = this.myModel.getTage();
				
				boolean checkAnfangstag = false;
				boolean checkEndtag = false;
				
				//Abfrage des Anfangs- und EndZeitraumes(Wochentage) aus der �bergebenen zeitraum-Map
				anfZeitraum = zeitraum.get("anfZeitraumTag");
				endZeitraum = zeitraum.get("endZeitraumTag");			
				
				//Pr�fe, ob es die ausgew�hlten Tage in dem �bergebenen Wochenplan gibt
				if((anfZeitraum != null) && (endZeitraum != null)){
					
					for(Tag t: alleTage){
						//Pr�fe, ob der ausgew�hlte Anfangstag in der Woche vorhanden ist
						if((t.getWpnr() == wpnr) && (t.getTbez().equals(anfZeitraum))){
							checkAnfangstag = true;						
						}
						
						//Pr�fe, ob der ausgew�hlte Endtag in der Woche vorhanden ist
						if((t.getWpnr() == wpnr)&&(t.getTbez().equals(endZeitraum))){
							checkEndtag = true;
						}				
					}	
					
				}						
				//Werfe Fehlermeldung, wenn die ausgew�hlten Tage nicht in der �bergebenen Woche vorhanden sind.
				if(!(checkAnfangstag && checkEndtag)){
					throw new Exception("Die eingetragenen Tage sind im Wochenplan KW" + wpnr + " nicht vorhanden, bitte Wochenplan erneut erstellen");
				}		
				
				//Pr�fe, ob die Reihenfolge der Tage vertauscht wurde, z.B. Termin beginnt am Mittwoch und endet am Dienstag
				
				String reihenfolgeFehlermeldung = "Die Reihenfolge der Anfangs- und Endtage des Termins sind vertauscht!";
				
				//Zuordnung einer Nummer zu jedem Tag, um zu �berpr�fen, ob der Anfangstag nach dem Endtag liegt
				TreeMap<String, Integer> checkTage = new TreeMap<String, Integer>();
				
				checkTage.put("Montag", 1);
				checkTage.put("Dienstag", 2);
				checkTage.put("Mittwoch", 3);
				checkTage.put("Donnerstag", 4);
				checkTage.put("Freitag", 5);
				checkTage.put("Samstag", 6);
				checkTage.put("Sonntag", 7);
				
				//Pr�fe, ob die Tage den Vorgaben entsprechen
				if((anfZeitraum != null) && (endZeitraum != null)){				
					
					//Pr�fe, ob der Anfangstag nach dem Endtag liegt
					if(checkTage.get(anfZeitraum) > checkTage.get(endZeitraum)){
						throw new Exception(reihenfolgeFehlermeldung);
					}				
				}
				else{
					System.out.println("Bitte die Zeitangaben �berpr�fen!");
				}
				
				//-->Der Zeitraum ist korrekt, da keine Exception geworfen wurde
			}
			
			String anfangsUhrzeit = zeitraum.get("anfangsUhrzeit");
			String endUhrzeit = zeitraum.get("endUhrzeit");	
			
			
			//Pr�fe, ob Uhrzeiten mit �bergeben wurden. Falls nicht, wird der Termin/ die Krankmeldung ganzt�gig eingetragen.
			if(anfangsUhrzeit.startsWith("") || endUhrzeit.startsWith("")){				
				
				//�berschreibe die nicht hinterlegten Anfangs- und Enduhrzeiten mit den �ffnungs- und Schlie�zeiten des Wochenplanes --> Abdeckung des gesamten Tages
				if(wp != null){
					anfangsUhrzeit = wp.get�ffnungszeit().substring(0, 5);
					endUhrzeit = wp.getSchlie�zeit().substring(0,5);			
				}
				else{
					System.out.println("Der ausgew�hlte Wochenplan existiert nicht, bitte Eingaben �berpr�fen!");					
				}			
			}		
			
			Date anfangsUhrzeitDate = null;
			Date endUhrzeitDate = null;			
			
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			
			//Konvertierung der �bergebenen Uhrzeiten ins Date-Format 
			try{
				anfangsUhrzeitDate = sdf.parse(anfangsUhrzeit);
				endUhrzeitDate = sdf.parse(endUhrzeit);
				
			}catch(Exception e){
				System.out.println("Fehler beim Erstellen eines neuen Termins:");				
				System.out.println("Fehler beim Konvertieren der �ffnungs- und Schlie�zeiten");
				e.printStackTrace();
			}
			
			//Pr�fe, ob die Daten in der richtigen Reihenfolge sind(Anfangszeit darf nicht hinter der Endzeit liegen)
			if(anfangsUhrzeitDate.before(endUhrzeitDate) || anfangsUhrzeitDate.equals(endUhrzeitDate)){
				
				int newTbNr = this.myModel.getNewTblocknr();				
				
				//Erstelle eine neue TerminBlockierung und f�ge diese in die Datenbank ein
				TerminBlockierung tb = new TerminBlockierung(newTbNr, username, bez, zeitraum.get("anfZeitraumTag"), zeitraum.get("endZeitraumTag"), anfangsUhrzeit, endUhrzeit, grund);
				this.myModel.addTerminBlockierung(tb);				
				
				//Da nun eine Terminblockierung vorliegt, muss �berpr�ft werden, ob der Mitarbeiter nun f�r bereits eingeteilte Schichten nicht mehr zur Verf�gung steht und somit aus der Zurodnung entfernt werden muss
						
				//Ermittlung der Schichten eines Mitarbeiters
				LinkedList<Schicht> alleSchichten = this.myModel.getSchichten();
				LinkedList<Schicht> mitarbeiterSchichten = new LinkedList<Schicht>();
				LinkedList<Ma_Schicht> mitarbeiterEinteilung = this.myModel.getSchichteneinesMitarbeiters(username);
								
				for(Schicht s: alleSchichten){
					
					for(Ma_Schicht mas : mitarbeiterEinteilung){
						if(s.getSchichtnr() == mas.getSchichtnr()){
							mitarbeiterSchichten.add(s);							
						}						
					}						
				}			
				
				//Ermittlung der Terminzuordnungen eines Mitarbeiters
				LinkedList<Tblock_Tag> alleTerminZuordnungen = this.myModel.getAlleTblock_Tag();
				LinkedList<Tblock_Tag> betroffeneTerminZuordnungen = new LinkedList<Tblock_Tag>();
				
				for(Tblock_Tag tbl: alleTerminZuordnungen){
					if(newTbNr == tbl.getTblocknr()){
						betroffeneTerminZuordnungen.add(tbl);
					}				
				}
				
				//Ermittlung der Termine eines Mitarbeiters
				LinkedList<TerminBlockierung> alleTerminBlockierungen = this.myModel.getTerminBlockierungen();
				LinkedList<TerminBlockierung> mitarbeiterTerminBlockierungen = new LinkedList<TerminBlockierung>();
				
				for(TerminBlockierung t: alleTerminBlockierungen){
					if(tb.getBenutzername().equals(username)){
						mitarbeiterTerminBlockierungen.add(t);
					}
				}			
				
				for(Schicht s: mitarbeiterSchichten){
					
					for(Tblock_Tag tbt: betroffeneTerminZuordnungen){
						
						for(TerminBlockierung t: mitarbeiterTerminBlockierungen){
							//Abgleich zum Ausschluss ungleicher Termine & Terminzuordungen
							if(t.getTblocknr() == tbt.getTblocknr()){
								
								//Suche die TerminBlockierungen f�r den zugeh�rigen Wochenplan
								if(s.getWpnr() == tbt.getWpnr()){									
									//Pr�fe, ob der Mitarbeiter an dem Tag einen neuen Termin hinterlegt hat und somit aus der Schicht entfernt werden muss
									if(s.getTbez().equals(tbt.getTbez())){
										this.myModel.deleteMa_Schicht(s.getSchichtnr(), username);
									}									
								}							
							}					
						}					
					}						
				}			
				
				success = true;
			}
			else{
				System.out.println("Die Anfangsuhrzeit liegt hinter der Enduhrzeit! Bitte Eingaben �berpr�fen.");
			}
			
		}catch(Exception e){
			System.out.println("Fehler beim Erstellen eines neuen Termins:");
			e.printStackTrace();			
		}	
	
		return success;
	}
	
	/**
	 * @author Lukas K�hl
	 * @info Entfernen einer bereits angelegten Terminblockierung aus der Datenbank.
	 */
	protected boolean entferneTermin(int tblocknr, String username){

		boolean success = false;
		boolean valid = false;			
		
		LinkedList<TerminBlockierung> alleTerminblockierungen = this.myModel.getTerminBlockierungen();
		
		for(TerminBlockierung tb: alleTerminblockierungen){
			//Pr�fe, ob der Termin existiert und ob der Nutzer die Berechtigung zum L�schen besitzt(Bedingung: Ersteller/in des Termins oder Benutzer besitzt Adminrechte)
			if(((tb.getTblocknr() == tblocknr) && tb.getBenutzername().equals(username)) || myController.isUserAdmin(username)){
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
		
		// Zuordnung von Terminen zu einem Wochenplan --> Key TerminNr; Value WochenplanNr --> Zus�tzliche Informationen, in welchem Wochenplan sich der Termin befindet
		TreeMap<Integer, Integer> mitarbeiterTerminZuordnungen = new TreeMap<Integer, Integer>();
		
		for(TerminBlockierung tb: mitarbeiterTermine){
			
			for(Tblock_Tag tbt : alleTerminZuordnungen){
				
				if(tb.getTblocknr() == tbt.getTblocknr())
					mitarbeiterTerminZuordnungen.put(tbt.getTblocknr(), tbt.getWpnr());			
			}		
		}		
		
		//�bertrage die Termine im Format Terminbezeichnung - KW - Anfangstag - Endtag - Anfangsuhrzeit-Enduhrzeit in die R�ckgabeListe
		for(TerminBlockierung tb : mitarbeiterTermine){
			String temp = tb.getBbez() + " - KW" + mitarbeiterTerminZuordnungen.get(tb.getTblocknr()) + " - ";
			
			//Falls es sich um einen eint�gigen Termin handelt, wird der Anfangs- und Endtag zusammengefasst
			if(tb.getAnfangzeitraum().equals(tb.getEndezeitraum())){
				temp = temp + tb.getAnfangzeitraum() + " - ";
			}
			else{
				temp = temp + tb.getAnfangzeitraum() + "-" + tb.getEndezeitraum() + " - ";
			}
		
			temp = temp + tb.getAnfanguhrzeit() + "-" + tb.getEndeuhrzeit() + " Uhr";
			
			//Datensatz ist vollst�ndig und kann in die R�ckgabeliste eingetragen werden
			rueckgabe.add(temp);
			
		}
		
		return rueckgabe;
	}
	
	/**
	 * @author Lukas K�hl
	 * @info Ausgabe einer Liste mit allen hinterlegten Terminen innerhalb des Systems zur Pr�fung durch einen berechtigten Nutzer(Admin)
	 */
	protected ArrayList<String> getAlleTermine(String username){
		ArrayList<String> rueckgabe = new ArrayList<String>();	
		
		//Pr�fe, ob der Nutzer berechtigt ist, alle Termine abzufragen
		if(myController.isUserAdmin(username)){
			
			LinkedList<TerminBlockierung> alleTermine = this.myModel.getTerminBlockierungen();
						
			LinkedList<Tblock_Tag> alleTerminZuordnungen = this.myModel.getAlleTblock_Tag();
			
			//�bertrage die Termine im Format Terminbezeichnung - Mitarbeitername - KW - Anfangstag - Endtag - Anfangsuhrzeit-Enduhrzeit in die R�ckgabeListe
			for(TerminBlockierung tb : alleTermine){
				
				for(Tblock_Tag tbt : alleTerminZuordnungen){
					
					//Terminblockierung und Terminblockierungszuordnung sind gleich --> zus�tzliche Informationen zum zugeh�rigen Wochenplan sind verf�gbar
					if(tbt.getTblocknr() == tb.getTblocknr()){
						
						String temp = tb.getBbez() + " - " + this.myModel.getMitarbeiter(tb.getBenutzername()).getVorname() + " " + this.myModel.getMitarbeiter(tb.getBenutzername()).getName() +" - KW" + tbt.getWpnr() + " - ";
						
						//Falls es sich um einen eint�gigen Termin handelt, wird der Anfangs- und Endtag zusammengefasst
						if(tb.getAnfangzeitraum().equals(tb.getEndezeitraum())){
							temp = temp + tb.getAnfangzeitraum() + " - ";
						}
						else{
							temp = temp + tb.getAnfangzeitraum() + "-" + tb.getEndezeitraum() + " - ";
						}
						
						temp = temp + tb.getAnfanguhrzeit() + "-" + tb.getEndeuhrzeit() + " Uhr";
						
						//Datensatz ist vollst�ndig und kann in die R�ckgabeliste eingetragen werden
						rueckgabe.add(temp);						
					}					
				}			
			}		
		}
		else{			
			System.out.println("Die notwendigen Berechtigungen sind nicht vorhanden, bitte wenden Sie sich an den Systemadministrator.");
		}
		
		
		return rueckgabe;
	}	
}
