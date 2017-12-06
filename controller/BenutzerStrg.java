package controller;

import javax.swing.JDialog;

import model.Einsatzplanmodel;

/**
 * @author 
 * @info Die Klasse BenutzerStrg dient dazu, jegliche Anfragen zu Benutzern durchzuführen und zu validieren.
 */
public class BenutzerStrg {

	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	

	private JDialog myDialog = null;
		
		
	/**
	 * @author 
	 * @info Erzeugen eines Objektes der Klasse BenutzerStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb später zugewiesen.
	 */
	protected BenutzerStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}	
	
	/**
	 * @author 
	 * @info Anlegen eines neuen Benutzers für einen Mitarbeiter eines Warenhauses im System. 
	 */
	protected boolean benutzerErstellen(){

		boolean success = false;
		//Ausfüllen
		return success;
	}
		
	/**
	 * @author 
	 * @info Entfernen eines nicht mehr benötigten Users für einen Mitarbeiter eines Warenhauses
	 */
	protected boolean benutzerEntfernen(){

		boolean success = false;
		//Ausfüllen
		return success;
	}
		
	/**
	 * @author 
	 * @info Login Angaben für einen spezifischen Nutzer überprüfen und bei korrekten Angaben einloggen des Benutzers ins System
	 */
	protected boolean benutzerAnmelden(){

		boolean success = false;
		//Ausfüllen
		return success;
		
	}
	
	/**
	 * @author 
	 * @info Ausloggen eines Nutzers aus dem System
	 */
	protected boolean benutzerAbmelden(){
		boolean success = false;
		//Ausfüllen
		return success;
	}
	
	/**
	 * @author 
	 * @info Ändern der jeweiligen Rechte eines Mitarbeiters von Mitarbeiter- zu Kassenbüro-Rechten oder umgekehrt(je nach derzeitigen Berechtigungen) 
	 */
	protected boolean benutzerRechteÄndern(){
		boolean success = false;
		//Ausfüllen
		return success;
	}

}
