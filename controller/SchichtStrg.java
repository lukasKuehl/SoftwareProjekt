package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.JDialog;

import org.omg.CORBA.MARSHAL;

import data.Ma_Schicht;
import data.Mitarbeiter;
import data.Schicht;
import data.Tblock_Tag;
import data.TerminBlockierung;
import model.Einsatzplanmodel;

/**
 * @author Lukas K�hl
 * @info Die Klasse SchichtStrg dient dazu, jegliche Anfragen zu Schichten durchzuf�hren und zu validieren.
 */
class SchichtStrg {

	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private JDialog myDialog = null;		
		
	/**
	 * @author Lukas K�hl
	 * @info Erzeugen eines Objektes der Klasse SchichtStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb sp�ter zugewiesen.
	 */
	protected SchichtStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}
	
	
	/**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zum automatischen erstellen von Schichten innerhalb eines Tages und hinterlegen in der Datenbank. 
	 */
	protected boolean erstelleSchicht(){

		boolean success = false;
		//Ausf�llen
		return success;
	}
		
	/**
	 * @author Lukas K�hl
	 * @info Hinzuf�gen einer neuen Schicht f�r einen bestimmten Tag innerhalb eines Wochenplanes und hinterlegen der notwendigen Informationen in der Datenbank. 
	 */
	protected boolean ausf�llenSchicht(int schichtNr, String[] mitarbeiter){

		boolean success = false;			
		LinkedList<Ma_Schicht> einteilung= this.myModel.getMitarbeiterausderSchicht(schichtNr);
		
		for(Ma_Schicht ma : einteilung){
			
			for(String s : mitarbeiter){
				if(ma.getBenutzername().equals(s)){
					//Mitarbeiter bereits in der Schicht eingeteilt --> weiter
				}
				else{
					//Pr�fe, ob der Mitarbeiter im System vorhanden ist und somit eingeteilt werden kann
					if(this.myModel.checkMitarbeiter(s)){							
						//Teile den Mitarbeiter in die gew�nschte Schicht ein
						this.myModel.addMa_Schicht(new Ma_Schicht(s, schichtNr));					
					}
				}
			}			
		}	
		success = true;
		
		return success;
	}

	/**
	 * @author Lukas K�hl
	 * @info Methode zur Ermittlung aller verf�gbaren Mitarbeiter f�r eine Schicht des Wochenplanes.
	 */
	protected ArrayList<String> getVerf�gbareMitarbeiter(int schichtNr){
		ArrayList<String> verfuegbareMitarbeiter = new ArrayList<String>();
		
		LinkedList<Mitarbeiter> alleMitarbeiter = this.myModel.getAlleMitarbeiter();
				
		for(Mitarbeiter m: alleMitarbeiter){		
			
			
			LinkedList<Schicht> alleSchichten = this.myModel.getSchichten();
			LinkedList<Ma_Schicht> alleSchichtEinteilungen = this.myModel.getMa_Schicht();
			LinkedList<Schicht> mitarbeiterSchichten = new LinkedList<Schicht>();
			
			
			for(Schicht s : alleSchichten){
				
				for(Ma_Schicht mas: alleSchichtEinteilungen){
					if(s.getSchichtnr() == mas.getSchichtnr()){
						if(m.getBenutzername().equals(mas.getBenutzername())){
							mitarbeiterSchichten.add(s);
						}						
					}				
				}			
			}		
			
			int maxDauer, dauer = 0; 
			
			maxDauer = m.getMaxstunden();
					
			//Bereits vorhande Schichten werden addiert um momentane Auslastung zu ermitteln
			for(Schicht schicht: mitarbeiterSchichten){			
				dauer = dauer + getSchichtDauer(schicht);	
			}
			
			//Addieren der zus�tzlich notwendigen Auslastung f�r die neue Schicht
			
			Schicht neueSchicht = this.myModel.getSchicht(schichtNr);			
			dauer = dauer + getSchichtDauer(neueSchicht);
			
			//Pr�fe, ob durch die zus�tzliche Schicht nicht die maximale Kapazit�t f�r den Mitarbeiter �berschritten wurde.
			if(dauer <= maxDauer ){
								
				LinkedList<TerminBlockierung> alleTerminBlockierungen = this.myModel.getTerminBlockierungen();
				LinkedList<TerminBlockierung> mitarbeiterTerminBlockierungen = new LinkedList<TerminBlockierung>();
				
				//Sortiere die TerminBlockierungen aus, die nicht zu dem jeweiligen Mitarbeiter geh�ren
				for(TerminBlockierung tb: alleTerminBlockierungen){
					if(tb.getBenutzername().equals(m.getBenutzername())){
						mitarbeiterTerminBlockierungen.add(tb);
					}
					
				}			
				
				boolean frei = true;
				for(TerminBlockierung tb : mitarbeiterTerminBlockierungen){
				
					//Suche den Tag + Wochenplan, an dem der Mitarbeiter einen Termin hat
					String tbezTermin = tb.getBbez();					
					Tblock_Tag temp = this.myModel.getTblock_TagTB(tb.getTblocknr());								
					int wpnrTermin = temp.getWpnr();
					
					//Suche den Tag + Wochenplan, in dem sich die Schicht befindet
					String tbezSchicht = this.myModel.getSchicht(schichtNr).getTbez();
					int wpnrSchicht = this.myModel.getSchicht(schichtNr).getWpnr();
					
					if((tbezTermin.equals(tbezSchicht)) &&(wpnrTermin == wpnrSchicht)){
						//Mitarbeiter hat an dem Tag, in dem sich die Schicht befindet, bereits einen Termin eingetragen und ist somit nicht verf�gbar
						frei = false;
					}								
				}		
				
				if(frei){
					verfuegbareMitarbeiter.add(m.getBenutzername());
				}	
			}			
		}
		
		
		return verfuegbareMitarbeiter;
	}
	
	
	/**
	 * @author Lukas K�hl
	 * @info Methode zur Ermittlung der Schichten eines Mitarbeiters f�r einen bestimmten Tag innerhalb eines Wochenplanes
	 */
	protected ArrayList<String> getMitarbeiterSchichten(String wpbez, String tagbez, String username){
		ArrayList<String> rueckgabe = new ArrayList<String>();
		
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = Integer.parseInt((wpbez.substring(2).trim())); 		
		
    	//Abfrage der gesamten Zuordnung und Suche nach den Schichten, die dem �bergebenen benuzternamen zugeordnet sind
		LinkedList<Ma_Schicht> einteilung = this.myModel.getMa_Schicht();
		LinkedList<Ma_Schicht> mitarbeiterEinteilung = new LinkedList<Ma_Schicht>();
		
		//Iteriere durch die Einteilungsliste und �bernehme die Datens�tze in die Mitarbeitereinteilung, die den gesuchten Benuzternamen haben
		for(Ma_Schicht ms: einteilung){			
			if(ms.getBenutzername().equals(username)){
				mitarbeiterEinteilung.add(ms);
			}			
		}
		
		//Abfrage vorhandenen Schichten in der Datenbank und Suche nach den Schichtnummern der Einteilung s.o.
		LinkedList<Schicht> alleSchichten = this.myModel.getSchichten();
		LinkedList<Schicht> mitarbeiterSchichtenAlle =new LinkedList<Schicht>();
		
		for(Schicht s: alleSchichten){
			
			for(Ma_Schicht mas: mitarbeiterEinteilung){
				
				if(s.getSchichtnr() == mas.getSchichtnr()){
					mitarbeiterSchichtenAlle.add(s);
				}			
			}		
		}
		
		LinkedList<Schicht> mitarbeiterTagesSchichten = new LinkedList<Schicht>();
		
		//Suche nach den Schichten eines bestimmten Tages eines vorhanden Wochenplanes
		for(Schicht s: mitarbeiterSchichtenAlle){
			
			if((s.getTbez().equals(tagbez)) && (s.getWpnr() == wpnr)){
				mitarbeiterTagesSchichten.add(s);
			}		
		}	
		
		return getSchichteinteilungView(mitarbeiterTagesSchichten);	
	}	
	
	protected ArrayList<String> getAndereMitarbeiterSchichten(String wpbez, String username, int schichtNr){
		LinkedList<Schicht> uebergabe = new LinkedList<Schicht>();
		
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = Integer.parseInt((wpbez.substring(2).trim())); 	
    	
    	LinkedList<Ma_Schicht> einteilung = this.myModel.getMa_Schicht();
		LinkedList<Ma_Schicht> andereMitarbeiterEinteilung = new LinkedList<Ma_Schicht>();
		LinkedList<Integer> belegteSchichtnummern = new LinkedList<Integer>();
		
		for(Ma_Schicht mas : einteilung){			
			//Der Mitarbeiterbenutzername ist gleich dem Anfrager --> Tausch w�re nicht sinnvoll, da Schicht bereits belegt
			if(mas.getBenutzername().equals(username)){				
				if(!belegteSchichtnummern.contains(mas.getSchichtnr())){
					belegteSchichtnummern.add(mas.getSchichtnr());
				}							
			}
		}
		
		//Sortiere alle Schichteinteilungen aus, in denen eine Schicht auftaucht, die vom �bergebenen Mitarbeiter selbst belegt ist
		for(Ma_Schicht mas: einteilung){
			boolean frei = true;
			for(Integer i: belegteSchichtnummern){
				if(i == mas.getSchichtnr()){
					frei = false;
				}			
			}
			if(frei){
				andereMitarbeiterEinteilung.add(mas);
			}	
		}	
		
		//Abfrage vorhandenen Schichten in der Datenbank und Suche nach den Schichtnummern der Einteilung s.o.
		LinkedList<Schicht> alleSchichten = this.myModel.getSchichten();
		// Potentielle Schichten zum Tauschen, in denen der �bergebene Mitarbeiter selbst nicht vorhanden ist
		TreeMap <Integer, Schicht> andereMitarbeiterSchichtenAlle = new TreeMap<Integer, Schicht>();
				
		for(Schicht s: alleSchichten){				
			for(Ma_Schicht mas: andereMitarbeiterEinteilung){
						
				if(s.getSchichtnr() == mas.getSchichtnr() && (!andereMitarbeiterSchichtenAlle.containsKey(s.getSchichtnr()))){
					andereMitarbeiterSchichtenAlle.put(s.getSchichtnr(),s);
				}			
			}		
		}			
		
		 for(Integer i: andereMitarbeiterSchichtenAlle.keySet()){
			 uebergabe.add(andereMitarbeiterSchichtenAlle.get(i));
		 }	
		
		return getSchichteinteilungView(uebergabe);
	}
	
	
	/**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zur Generierung eines Eintrages in der Schicht�bersicht innerhalb der View
	 */
	private ArrayList<String> getSchichteinteilungView(LinkedList<Schicht> mitarbeiterSchichten){
		
		ArrayList<String> rueckgabe = new ArrayList<String>();
		
		for(Schicht s: mitarbeiterSchichten){
			
			//Struktur eines Schichteintrages in der R�ckgabeliste: SchichtNr - KW - Tag - Zeitraum - Mitarbeitername
			
			String temp = String.valueOf(s.getSchichtnr()) + " - KW"  + s.getWpnr() + " - " + s.getTbez() + " - " + s.getAnfanguhrzeit()+ "-" + s.getEndeuhrzeit() +" Uhr - ";
			
			LinkedList<Ma_Schicht> alleSchichtZuordnungen = this.myModel.getMa_Schicht();
			Mitarbeiter ma = null;
			
			for(Ma_Schicht mas: alleSchichtZuordnungen){
				
				//Betroffene Schichzuordnung gefunden, entnahme des Mitarbeiters zum Auslesen des Namens
				if(s.getSchichtnr() == mas.getSchichtnr()){					
					ma = this.myModel.getMitarbeiter(mas.getBenutzername());				
					rueckgabe.add(temp + ma.getVorname() + " " + ma.getName());
				}		
			}		
		}
		
		return rueckgabe;
		
	}	
	
	/**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zur Berechnung der Dauer einen bestimmten Schicht(in Std.)
	 */
	private int getSchichtDauer(Schicht s){
		
		int dauer = 0;
		
		//Lese den Zeitraum f�r die Schicht aus dem Schichtobjekt
		String anfangsZeit = s.getAnfanguhrzeit();
		String endZeit = s.getEndeuhrzeit();
		
		try{
			
			//Konvertiere die zwei Zeitpunkte ins Date-Format
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			Date anfangDate = sdf.parse(anfangsZeit);
			Date endDate = sdf.parse(endZeit);
			
			//Berechne die Differenz der beiden Zeitpunkt
			long dauerSchichtLong = endDate.getTime() - anfangDate.getTime();
			
			//Rechne den Zeitunterschied in Std. um
			int dauerSchichtInt  =  Math.round((long) dauerSchichtLong / (1000 * 60 * 60));
			
			dauer = dauer + dauerSchichtInt;
			
			
		}catch(Exception e){
			System.out.println("Fehler beim Bestimmen der Dauer von der Schicht: " + s.getSchichtnr());
		}			
		
		return dauer;
	}
	
}