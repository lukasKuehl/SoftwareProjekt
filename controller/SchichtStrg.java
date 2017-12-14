package controller;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JDialog;

import data.Mitarbeiter;
import data.Schicht;
import model.Einsatzplanmodel;

/**
 * @author Lukas Kühl
 * @info Die Klasse SchichtStrg dient dazu, jegliche Anfragen zu Schichten durchzuführen und zu validieren.
 */
class SchichtStrg {

	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private JDialog myDialog = null;		
		
	/**
	 * @author Lukas Kühl
	 * @info Erzeugen eines Objektes der Klasse SchichtStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb später zugewiesen.
	 */
	protected SchichtStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}
	
	
	/**
	 * @author Lukas Kühl
	 * @info Hilfsmethode zum automatischen erstellen von Schichten innerhalb eines Tages und hinterlegen in der Datenbank. 
	 */
	protected boolean erstelleSchicht(){

		boolean success = false;
		//Ausfüllen
		return success;
	}
		
	/**
	 * @author Lukas Kühl
	 * @info Hinzufügen einer neuen Schicht für einen bestimmten Tag innerhalb eines Wochenplanes und hinterlegen der notwendigen Informationen in der Datenbank. 
	 */
	protected boolean ausfüllenSchicht(int schichtNr, String[] mitarbeiter){

		boolean success = false;		
		
		/*
		LinkedList<Mitarbeiter> einteilung= this.myModel.getMA_Schicht(schichtNr);
		
		for(Mitarbeiter m : einteilung){
			
			for(String s : mitarbeiter){
				if(m.getUsername().equals(s)){
					//Mitarbeiter bereits in der Schicht eingeteilt --> weiter
				}
				else{
					if(this.myModel.mitarbeiterVorhanden(s)){				
						
						this.myModel.schichtAddMitarbeiter(schichtNr, s);
						
					}
				}
			}			
		}	
		*/
		return success;
	}

	/**
	 * @author Lukas Kühl
	 * @info Methode zur Ermittlung aller verfügbaren Mitarbeiter für eine Schicht des Wochenplanes.
	 */
	protected LinkedList<String> getVerfügbareMitarbeiter(int schichtNr){
		LinkedList<String> verfuegbareMitarbeiter = new LinkedList<String>();
		
		/*
		LinkedList<Mitarbeiter> alleMitarbeiter = this.myModel.getMitarbeiter();
		
		for(Mitarbeiter m: alleMitarbeiter){
			LinkedList<Schicht> schichten = this.myModel.getSchichten(m.getUsername());
			
			int maxDauer, dauer = 0; 
			
			maxDauer = this.myModel.getMaxArbeitszeitMA(s);
			
			//Bereits vorhande Schichten werden addiert um momentane Auslastung zu ermitteln
			for(Schicht schicht: schichten){
				dauer = dauer + schicht.getDauer();
			}
			
			//Addieren der zusätzlich notwendigen Auslastung für die neue Schicht
			dauer = dauer + this.myModel.getSchichtDauer(schichtNr);
			
			if(dauer <= maxDauer ){
				
				LinkedList<TerminBlockierung> terminblockierungen = this.myModel.getTerminBlockierungen(m.getUsername());
					
				boolean frei = true;
				for(TerminBlockierung tb : terminblockierungen){
					
					//Suche den Tag + Wochenplan, an dem der Mitarbeiter einen Termin hat
					String tbezTermin = this.myModel.getTBlock_Tag(tb.getTblockNr()).getTbez();
					int wpnrTermin = this.myModel.getTBlock_Tag(tb.getTblockNr()).getWpnr();
					
					//Suche den Tag + Wochenplan, in dem sich die Schicht befindet
					String tbezSchicht = this.myModel.getSchicht(schichtNr).getTbez();
					int wpnrSchicht = this.myModel.getSchicht(schichtNr).getWochenplanNr();
					
					if((tbezTermin.equals(tbezSchicht)) &&(wpnrTermin == wpnrSchicht)){
						//Mitarbeiter hat an dem Tag, in dem sich die Schicht befindet, bereits einen Termin eingetragen und ist somit nicht verfügbar
						frei = false;
					}								
				}		
				
				if(frei){
					verfuegbareMitarbeiter.add(m.getUsername());
				}	
			}			
		}
		*/
		
		return verfuegbareMitarbeiter;
	}
	
	protected ArrayList<String> getMitarbeiterSchichten(String wpbez, String tagbez, String username){
		ArrayList<String> rueckgabe = null;
		return rueckgabe;
	}
	
	protected ArrayList<String> getAndereMitarbeiterSchichten(String wpbez, String tagbez, String username, int schichtNr){
		ArrayList<String> rueckgabe = null;
		return rueckgabe;
	}
	
}
