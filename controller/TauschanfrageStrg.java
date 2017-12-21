package controller;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JDialog;

import data.Mitarbeiter;
import data.Schicht;
import data.Tauschanfrage;
import model.Einsatzplanmodel;

/**
 * @author Lukas
 * @info Die Klasse TauschanfrageStrg dient dazu Anfragen, welche sich auf Tauschanfragen beziehen zu bearbeiten und zu validieren. 
 */
class TauschanfrageStrg {
	
	//Initialsierung der Instanzvariablen
	private EinsatzplanController myController = null;	
	private Einsatzplanmodel myModel = null;
	private JDialog myDialog = null;
	//ToDo verfügbare Dialoge als Variablen festhalten	
	
	/**
	 * @author Lukas
	 * @info Erzeugen eines Objektes der Klasse TauschanfrageStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb später zugewiesen.
	 */
	protected TauschanfrageStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;		
		this.myModel = myModel;
	}	
	
	/**
	 * @author 
	 * @info Anlegen einer neuen Tauschanfrage zum Tausch einer Schicht eines Mitarbeiters mit der Schicht eines anderen Mitarbeiters
	 */
	protected boolean erstelleTauschanfrage(String senderName, int senderSchichtNr, String empfaengerName, int empfaengerSchichtNr ){
		
		boolean success = false;	
		//Ausfüllen
		return success;
	}	
	
	/**
	 * @author 
	 * @info Eine bereits existierende Tauschanfrage soll aus dem System entfernt werden
	 */
	protected boolean entferneTauschanfrage(int tauschanfrageNr){
		
		boolean success = false;	
		//Ausfüllen
		return success;
	}	
	
	/**
	 * @author 
	 * @info Der Empfänger einer Tauschanfrage möchte diese annehmen, um seine/ihre Schicht mit einer anderen zu tauschen
	 */
	protected boolean akzeptiereTauschanfrage(String emfaengerName, int tauschanfrageNr){	
		boolean success = false;	
		//Ausfüllen
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
			
			//Eintragen Sender-Informationen
			String temp = sender.getVorname() + " " + sender.getName() + " - " + senderSchicht.getSchichtnr() + " - " + "KW" + senderSchicht.getWpnr() + " - " + senderSchicht.getTbez() + " - " + senderSchicht.getAnfanguhrzeit() + "-" + senderSchicht.getEndeuhrzeit() + "\n --> ";
									
			//Eintragen Empfänger-Informationen
			temp = temp + empfaenger.getVorname() + " " + empfaenger.getName() + " - " + empfaengerSchicht.getSchichtnr() + " - " + "KW" + empfaengerSchicht.getWpnr() + " - " + empfaengerSchicht.getTbez() + " - " + empfaengerSchicht.getAnfanguhrzeit() + ":" + empfaengerSchicht.getEndeuhrzeit();
			
			rueckgabe.add(temp);			
		}	
		
		return rueckgabe;
	}
	
}
