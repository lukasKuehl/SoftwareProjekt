package controller;

import java.util.Observer;
import java.util.TreeMap;

import javax.swing.JDialog;

import model.Einsatzplanmodel;
import view.Einsatzplanview;

/**
 * @author 
 * @info Die Klasse BenutzerStrg dient dazu, jegliche Anfragen zu Benutzern durchzuführen und zu validieren.
 */
public class BenutzerStrg {

	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	
	//private JDialog myDialog = null;
			
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
	protected boolean benutzerErstellen(String username, String pw){

		boolean success = false;
		
		/*
		if(this.myModel.getMitarbeiter(username) == null){
			this.myModel.addMitarbeiter(username, pw);
			success = true;
		}		
		*/
		return success;
	}
		
	/**
	 * @author 
	 * @info Entfernen eines nicht mehr benötigten Users für einen Mitarbeiter eines Warenhauses
	 */
	protected boolean benutzerEntfernen(String username){

		boolean success = false;
		
		/*
		if(this.myModel.getMitarbeiter(username) != null){
			
			try{
				this.myModel.entferneMitarbeiter(username);
				success = true;
			}catch(Exception e){
				System.out.println("Controller: Fehler beim Entfernen eines Mitarbeiters aus der Datenbank: ");
				e.printStackTrace();
			}	
		}		
		*/		
		
		return success;
	}
		
	/**
	 * @author 
	 * @info Login Angaben für einen spezifischen Nutzer überprüfen und bei korrekten Angaben einloggen des Benutzers ins System
	 */
	protected boolean benutzerAnmelden(String username, String pw){

		boolean success = false;
		/*
		if(this.myModel.getMitarbeiter(username) != null){
			try{
				this.myModel.mitarbeiterAnmelden(username, pw, this.myController.getView());	
				success = true;
			}catch(Exception e){
				System.out.println("Fehler beim Anmelden des Users " + username + ", bitte Eingaben überprüfen :");
				e.printStackTrace();
			}
		
		}		
		*/
		
		return success;
		
	}
	
	/**
	 * @author 
	 * @info Ausloggen eines Nutzers aus dem System
	 */
	protected boolean benutzerAbmelden(String username){
		boolean success = false;

		/*
		if(this.myModel.getMitarbeiter(username) != null){
			try{
				this.myModel.deabonniere(username, this.myController.getView());		
			}catch(Exception e){
				System.out.println("Fehler beim Abmelden des Users " + username + " :");
				e.printStackTrace();
			}
		
		}			
		*/		
		
		
		return success;
	}
	
	/**
	 * @author 
	 * @info Ändern der jeweiligen Rechte eines Mitarbeiters von Mitarbeiter- zu Kassenbüro-Rechten oder umgekehrt(je nach derzeitigen Berechtigungen) 
	 */
	protected boolean benutzerRechteÄndern(String username){
		boolean success = false;
		/*
		if(this.myModel.getMitarbeiter(username) != null){
			try{
				this.myModel.rechteWechsel(username);				
			}catch(Exception e){
				System.out.println("Fehler beim Ändern der Rechte des Nutzers " + username + " :");
				e.printStackTrace();
			}			
		}
		
		*/			
		
		return success;
	}

}
