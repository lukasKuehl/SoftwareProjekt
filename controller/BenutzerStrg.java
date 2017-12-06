package controller;

import javax.swing.JDialog;

import model.Einsatzplanmodel;

/**
 * @author 
 * @info Die Klasse BenutzerStrg dient dazu, jegliche Anfragen zu Benutzern durchzuf�hren und zu validieren.
 */
public class BenutzerStrg {

	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	

	private JDialog myDialog = null;
		
		
	/**
	 * @author 
	 * @info Erzeugen eines Objektes der Klasse BenutzerStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb sp�ter zugewiesen.
	 */
	protected BenutzerStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}	
	
	/**
	 * @author 
	 * @info Anlegen eines neuen Benutzers f�r einen Mitarbeiter eines Warenhauses im System. 
	 */
	protected boolean benutzerErstellen(){

		boolean success = false;
		//Ausf�llen
		return success;
	}
		
	/**
	 * @author 
	 * @info Entfernen eines nicht mehr ben�tigten Users f�r einen Mitarbeiter eines Warenhauses
	 */
	protected boolean benutzerEntfernen(){

		boolean success = false;
		//Ausf�llen
		return success;
	}
		
	/**
	 * @author 
	 * @info Login Angaben f�r einen spezifischen Nutzer �berpr�fen und bei korrekten Angaben einloggen des Benutzers ins System
	 */
	protected boolean benutzerAnmelden(){

		boolean success = false;
		//Ausf�llen
		return success;
		
	}
	
	/**
	 * @author 
	 * @info Ausloggen eines Nutzers aus dem System
	 */
	protected boolean benutzerAbmelden(){
		boolean success = false;
		//Ausf�llen
		return success;
	}
	
	/**
	 * @author 
	 * @info �ndern der jeweiligen Rechte eines Mitarbeiters von Mitarbeiter- zu Kassenb�ro-Rechten oder umgekehrt(je nach derzeitigen Berechtigungen) 
	 */
	protected boolean benutzerRechte�ndern(){
		boolean success = false;
		//Ausf�llen
		return success;
	}

}
