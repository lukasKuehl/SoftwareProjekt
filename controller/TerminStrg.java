package controller;

import javax.swing.JDialog;

import model.Einsatzplanmodel;

/**
 * @author 
 * @info Die Klasse TerminStrg dient dazu, jegliche Anfragen zu Terminen durchzuführen und zu validieren.
 */
public class TerminStrg {
	
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
	 * @author 
	 * @info Hinterlegen eines neuen Termin/Krankmeldung/Urlaubstages im System 
	 */
	protected boolean erstelleTermin(){
		boolean success = false;	
		//Ausfüllen
		return success;
	}
	
	/**
	 * @author 
	 * @info Entfernen eines bereits angelegten Termines aus dem System.
	 */
	protected boolean entferneTermin(){

		boolean success = false;
		//Ausfüllen
		return success;
	}
	
}
