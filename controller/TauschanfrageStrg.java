package controller;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JDialog;

import data.Mitarbeiter;
import data.Schicht;
import data.Tauschanfrage;
import data.TerminBlockierung;
import model.Einsatzplanmodel;

/**
 * @author Lukas Kühl
 * @info Die Klasse TauschanfrageStrg dient dazu Anfragen, welche sich auf Tauschanfragen beziehen zu bearbeiten und zu validieren. 
 */
class TauschanfrageStrg {
	
	//Initialsierung der Instanzvariablen
	private EinsatzplanController myController = null;	
	private Einsatzplanmodel myModel = null;
	private JDialog myDialog = null;	
	
	/**
	 * @author Lukas Kühl
	 * @info Erzeugen eines Objektes der Klasse TauschanfrageStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb später zugewiesen.
	 */
	protected TauschanfrageStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;		
		this.myModel = myModel;
	}	
	
	/**
	 * @author Anes Preljevic
	 * @info Anlegen einer neuen Tauschanfrage zum Tausch einer Schicht eines Mitarbeiters mit der Schicht eines anderen Mitarbeiters
	 */
	protected boolean erstelleTauschanfrage(String senderName, int senderSchichtNr, String empfaengerName, int empfaengerSchichtNr ){
		boolean success = false;
		boolean valid=false;
		
		Schicht senderSchicht=this.myModel.getSchicht(senderSchichtNr);
		Schicht empfaengerSchicht=this.myModel.getSchicht(empfaengerSchichtNr);
		Mitarbeiter sender=this.myModel.getMitarbeiter(senderName);
		Mitarbeiter empfaenger=this.myModel.getMitarbeiter(empfaengerName);
		LinkedList<Tauschanfrage> alleTauschanfragen=this.myModel.getTauschanfragen();
		
		int tauschNr=this.myModel.getNewTauschnr();
		//Überprüfen ob die Übergebenen Variablen leer sind, wenn nicht weiterleiten an den nächsten Test und valid auf true setzen
		if(senderSchicht!=null && empfaengerSchicht!=null && sender!=null && empfaenger!=null){
			valid=true;
			//Überprüfen ob es zu den Übergebenen Werten bereits eine Tauschanfrage gibt, wenn ja setze valid auf false
			for(Tauschanfrage ta: alleTauschanfragen){
				if(ta.getEmpfänger().equals(empfaengerName) && ta.getSender().equals(senderName) && ta.getSchichtnrempfänger()==empfaengerSchichtNr && ta.getSchichtnrsender()==senderSchichtNr){
					valid=false;
				}
			}
		}
		//Wenn die Tests erfolgreich waren und somit valid=true ist kann die Tauschanfrage hinzugefügt werden
		//Werte nicht null und nicht bereits vorhanden
		if(valid){
		try{
			this.myModel.addTauschanfrage(tauschNr, senderName, senderSchichtNr, empfaengerName, empfaengerSchichtNr);
			success=true;
		}catch(Exception e){			

			String fehler = "Controller: Fehler beim Erstellen einer Tauschanfrage:\n" + e.getMessage();
			myController.printErrorMessage(fehler);				
		}
		}
		//Fehlermeldung, dass die Werte entweder null oder die Tauschanfrage bereits existiert
		else{
			
			String fehler = "Tauschanfrage kann nicht . \n";
			myController.printErrorMessage(fehler);			
		}
		
			
		return success;
	}	
	
	/**
	 * @author Anes Preljevic
	 * @info Eine bereits existierende Tauschanfrage soll aus dem System entfernt werden
	 */
	protected boolean entferneTauschanfrage(int tauschanfrageNr){
		
		boolean success = false;
			//Ruft die deleteTauschanfrage- Methode aus dem Einsatzplanmodel auf, mit der Übergebenen 
			// Tauschnr aus der View.
			try{				
				this.myModel.deleteTauschanfrage(tauschanfrageNr);
				success = true;
			}catch(Exception e){				
				String fehler = "Controller: Fehler beim Entfernen einer Tauschanfrage aus der Datenbank:\n" + e.getMessage();
				myController.printErrorMessage(fehler);
			}		
		return success;
	}	
	
	/**
	 * @Anes Preljevic 
	 * @info Der Empfänger einer Tauschanfrage möchte diese annehmen, um seine/ihre Schicht mit einer anderen zu tauschen
	 */
	protected boolean akzeptiereTauschanfrage(String empfaengerName, int tauschanfrageNr){	
		boolean success = false;
		boolean valid = false;

		
		LinkedList<Tauschanfrage> alleTauschanfragen=this.myModel.getTauschanfragen();
		//Tauschanfragen nach der zu Bestätigenden Tauschanfrage durchsuchen.
		//Falls die Tauschanfrage nicht vorhanden ist wird die Bestätigung über "valid" nicht gegeben, da es false bleibt
			for(Tauschanfrage ta: alleTauschanfragen){
				if((ta.getTauschnr() == tauschanfrageNr) && (ta.getEmpfänger().equals(empfaengerName))){
					valid = true;	
			}
				}
		//Wenn im vorherigen Schritt die Tauschanfrage existiert hat, werden die Variablen übergeben und die Tauschanfrage bestätigt
		if(valid){
		try{				
			this.myModel.bestätigeTauschanfrage(empfaengerName,tauschanfrageNr);
			success=true;
		}
		catch(Exception e){
			
			String fehler = "Controller: Fehler beim Bestätigen einer Tauschanfrage:\n" + e.getMessage();
			myController.printErrorMessage(fehler);
		}
		}	
		//Fehlermeldung, dass es zu dem übergebenen Empfänger und der Tauschnr keine gültige Tauschanfrage gibt 
		else{
			
			String fehler = "Tauschanfrage kann nicht bestätigt werden, User ist nicht der Empfänger oder Tauschanfrage nicht vorhanden. \n";
			myController.printErrorMessage(fehler);			
		}
		return success;
	}
	
	
		
	/**
	 * @author Lukas Kühl
	 * @info Auslesen aller Tauschanfragen, die an einen bestimmten Mitarbeiter gerichtet sind (Mitarbeiter ist somit als Empfänger eingetragen) und Aufbereitung für Anzeige in der View
	 */
	protected ArrayList<String> getTauschanfragen(String username){
		ArrayList<String> rueckgabe = new ArrayList<String>();
		
		LinkedList<Tauschanfrage> alleTauschanfragen = this.myModel.getTauschanfragen(); 
		LinkedList<Tauschanfrage> mitarbeiterTauschanfragen = new LinkedList<Tauschanfrage>();
		
		//Suche alle Tauschanfragen, die für den übergebenen Mitarbeiternamen bestimmt sind, für die die Person als Empfänger eingetragen ist.		
		for(Tauschanfrage ta: alleTauschanfragen){			
			if(ta.getEmpfänger().equals(username)){
				mitarbeiterTauschanfragen.add(ta);		
			}			
		}	
				
		//Generiere für jede Tauschanfrage eine Zeile nach folgendem Muster Sendername - SenderSchichtNr - KW - TagSender - ZeitraumSender || Empfängername - EmpfängerSchichtNr - KW - TagEmpfänger - ZeitraumEmpfänger
		//--> Ein Tausch ist somit nur innerhalb einer Woche möglich
		for(Tauschanfrage t: mitarbeiterTauschanfragen){
			
			Mitarbeiter sender = this.myModel.getMitarbeiter(t.getSender());
			Schicht senderSchicht = this.myModel.getSchicht(t.getSchichtnrsender());
			Mitarbeiter empfaenger = this.myModel.getMitarbeiter(t.getEmpfänger());
			Schicht empfaengerSchicht = this.myModel.getSchicht(t.getSchichtnrempfänger());			
						
			//Hinterlegen der individuellen Tausch-Nr
			String temp = "TauschNr: " + t.getTauschnr() + " - ";
			
			//Eintragen Sender-Informationen
			temp = temp + sender.getVorname() + " " + sender.getName() + " - " + senderSchicht.getSchichtnr() + " - " + "KW" + senderSchicht.getWpnr() + " - " + senderSchicht.getTbez() + " - " + senderSchicht.getAnfanguhrzeit() + "-" + senderSchicht.getEndeuhrzeit() + "\n --> ";
									
			//Eintragen Empfänger-Informationen
			temp = temp + empfaenger.getBenutzername() + " - " + empfaengerSchicht.getSchichtnr() + " - " + "KW" + empfaengerSchicht.getWpnr() + " - " + empfaengerSchicht.getTbez() + " - " + empfaengerSchicht.getAnfanguhrzeit() + ":" + empfaengerSchicht.getEndeuhrzeit();
			
			rueckgabe.add(temp);			
		}	
		
		return rueckgabe;
	}
	
}
