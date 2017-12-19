package controller;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JDialog;

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
	protected LinkedList<String> getVerf�gbareMitarbeiter(int schichtNr){
		LinkedList<String> verfuegbareMitarbeiter = new LinkedList<String>();
		/*
		LinkedList<Mitarbeiter> alleMitarbeiter = this.myModel.getAlleMitarbeiter();
		
		for(Mitarbeiter m: alleMitarbeiter){		
			LinkedList<Schicht> schichten = this.myModel.getSchichten(m.getUsername());
			
			int maxDauer, dauer = 0; 
			
			maxDauer = m.getMaxstunden();
			
			//Bereits vorhande Schichten werden addiert um momentane Auslastung zu ermitteln
			for(Schicht schicht: schichten){
				dauer = dauer + schicht.getDauer();
			}
			
			//Addieren der zus�tzlich notwendigen Auslastung f�r die neue Schicht
			dauer = dauer + this.myModel.getSchichtDauer(schichtNr);
			
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
		*/
		
		return verfuegbareMitarbeiter;
	}
	
	
	/**
	 * @author Lukas K�hl
	 * @info Methode zur Ermittlung der Schichten eines Mitarbeiters f�r einen bestimmten Tag innerhalb eines Wochenplanes
	 */
	protected ArrayList<String> getMitarbeiterSchichten(String wpbez, String tagbez, String username){
		ArrayList<String> rueckgabe = null;
		
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
		
		for(Schicht s: mitarbeiterTagesSchichten){
			rueckgabe.add(String.valueOf(s.getSchichtnr()));
		}
		
		return rueckgabe;
	}
	
	protected ArrayList<String> getAndereMitarbeiterSchichten(String wpbez, String tagbez, String username, int schichtNr){
		ArrayList<String> rueckgabe = null;
		
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = Integer.parseInt((wpbez.substring(2).trim())); 	
    	
    	
    	
		
		
		
		
		return rueckgabe;
	}
	
}
