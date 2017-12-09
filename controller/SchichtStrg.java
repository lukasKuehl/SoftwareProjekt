package controller;

import javax.swing.JDialog;

import model.Einsatzplanmodel;

/**
 * @author 
 * @info Die Klasse SchichtStrg dient dazu, jegliche Anfragen zu Schichten durchzuf�hren und zu validieren.
 */
public class SchichtStrg {

	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private JDialog myDialog = null;		
		
	/**
	 * @author 
	 * @info Erzeugen eines Objektes der Klasse SchichtStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb sp�ter zugewiesen.
	 */
	protected SchichtStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}
	
	
	/**
	 * @author 
	 * @info Hilfsmethode zum automatischen erstellen von Schichten innerhalb eines Tages und hinterlegen in der Datenbank. 
	 */
	protected boolean erstelleSchicht(){

		boolean success = false;
		//Ausf�llen
		return success;
	}
		
	/**
	 * @author 
	 * @info Hinzuf�gen einer neuen Schicht f�r einen bestimmten Tag innerhalb eines Wochenplanes und hinterlegen der notwendigen Informationen in der Datenbank. 
	 */
	protected boolean ausf�llenSchicht(String[] mitarbeiternummern){

		boolean success = false;
		
		//ausf�llen
		
		return success;
	}


		
}
