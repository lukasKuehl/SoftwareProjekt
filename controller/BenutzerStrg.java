package controller;

import java.util.Observer;
import java.util.TreeMap;

import javax.swing.JDialog;

import data.Mitarbeiter;
import model.Einsatzplanmodel;
import view.Einsatzplanview;

/**
 * @author Lukas K�hl
 * @info Die Klasse BenutzerStrg dient dazu, jegliche Anfragen zu Benutzern durchzuf�hren und zu validieren.
 */
class BenutzerStrg {

	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
			
	/**
	 * @author Lukas K�hl	
	 * @info Erzeugen eines Objektes der Klasse BenutzerStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb sp�ter zugewiesen.
	 */
	protected BenutzerStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}	
	
	/**
	 * @author Lukas K�hl	
	 * @info Anlegen eines neuen Benutzers f�r einen Mitarbeiter eines Warenhauses im System. 
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
	 * @author Lukas K�hl	
	 * @info Entfernen eines nicht mehr ben�tigten Users f�r einen Mitarbeiter eines Warenhauses
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
	 * @author Lukas K�hl
	 * @info Login Angaben f�r einen spezifischen Nutzer �berpr�fen und bei korrekten Angaben einloggen des Benutzers ins System
	 */
	protected boolean benutzerAnmelden(String username, String pw){

		boolean success = false;
		
		if(this.myModel.getMitarbeiter(username) != null){
			try{				
				
				Mitarbeiter m = myModel.getMitarbeiter(username);				
				
				//SQL ist nicht case-sensitiv --> zus�tzliche �berpr�fung des eingegebenen Usernamen				
				if(m.getBenutzername().equals(username)){
					
					if(m.getPasswort().equals(pw)){
						this.myModel.register(myController.getView(), username, pw);
						success = true;
					}
					else{
						System.out.println("Das eingegebene Passwort ist nicht korrekt!");
					}					
				}
				else{
					System.out.println("Bitte Benutzernamen �berpr�fen!");
				}
				
				
			}catch(Exception e){
				System.out.println("Fehler beim Anmelden des Users " + username + ", bitte Eingaben �berpr�fen :");
				e.printStackTrace();
			}		
		}			
		
		return success;		
	}
	
	/**
	 * @author Lukas K�hl
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
	 * @author Lukas K�hl	
	 * @info �ndern der jeweiligen Rechte eines Mitarbeiters von Mitarbeiter- zu Kassenb�ro-Rechten oder umgekehrt(je nach derzeitigen Berechtigungen) 
	 */
	protected boolean benutzerRechte�ndern(String username){
		boolean success = false;
		/*
		if(this.myModel.getMitarbeiter(username) != null){
			try{
				this.myModel.benutzerRechteWechsel(username);				
			}catch(Exception e){
				System.out.println("Fehler beim �ndern der Rechte des Nutzers " + username + " :");
				e.printStackTrace();
			}			
		}
		
		*/			
		
		return success;
	}

}
