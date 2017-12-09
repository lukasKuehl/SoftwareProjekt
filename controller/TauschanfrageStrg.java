package controller;

import javax.swing.JDialog;

import data.Schicht;
import model.Einsatzplanmodel;

/**
 * @author Lukas
 * @info Die Klasse TauschanfrageStrg dient dazu Anfragen, welche sich auf Tauschanfragen beziehen zu bearbeiten und zu validieren. 
 */
public class TauschanfrageStrg {
	
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
		
}
