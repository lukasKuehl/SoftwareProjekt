package controller;

import data.Mitarbeiter;
import data.Userrecht;
import model.Einsatzplanmodel;
import view.Einsatzplanview;

/**
 * @author Lukas Kühl
 * @info Die Klasse BenutzerStrg dient dazu, jegliche Anfragen zu Benutzern durchzuführen und zu validieren.
 */
class BenutzerStrg {

	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
			
	/**
	 * @author Lukas Kühl	
	 * @info Erzeugen eines Objektes der Klasse BenutzerStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Zuweisen des Models, um entsprechende Anfragen weiterleiten zu können.
	 */
	protected BenutzerStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}	
	
	/**
	 * @author Lukas Kühl	
	 * @info Anlegen eines neuen Benutzers für einen Mitarbeiter eines Warenhauses im System. 
	 */
	protected boolean benutzerErstellen(Mitarbeiter m){

		boolean success = false;	
		
		//Prüfe, ob der Mitarbeiter bereits vorhanden ist. Falls nein, wird ein neuer Mitarbeiter in die Datenbank eingefügt.
		if(this.myModel.getMitarbeiter(m.getBenutzername()) == null){
			success = this.myModel.addMitarbeiter(m);			
		}		
		
		return success;
	}
		
	//Wird zur Zeit noch nicht verwendet!
	/**
	 * @author Lukas Kühl	
	 * @info Entfernen eines nicht mehr benötigten Users für einen Mitarbeiter eines Warenhauses
	 */
	protected boolean benutzerEntfernen(String username){

		boolean success = false;
		
		/*
		if(this.myModel.getMitarbeiter(username) != null){
			
			try{				
				success = this.myModel.entferneMitarbeiter(username);				
			}catch(Exception e){
				System.out.println("Controller: Fehler beim Entfernen eines Mitarbeiters aus der Datenbank: ");
				e.printStackTrace();
			}	
		}		
		*/		
		
		return success;
	}
		
	
	/**
	 * @author Lukas Kühl
	 * @info Login Angaben für einen spezifischen Nutzer überprüfen und bei korrekten Angaben anmelden des Benutzers im System und hinterlegen der View in der Observer-Liste des Models.
	 */
	protected boolean benutzerAnmelden(String username, String pw){

		boolean success = false;
		
		//Prüfe, ob es einen Mitarbeiter zu dem übergebenen Benutzernamen gibt.
		if(this.myModel.getMitarbeiter(username) != null){
			try{								
				Mitarbeiter m = myModel.getMitarbeiter(username);				
				
				//SQL ist nicht case-sensitiv --> zusätzliche Überprüfung des eingegebenen Usernamen				
				if(m.getBenutzername().equals(username)){
					
					//Prüfe, ob das Passwort korrekt ist, falls ja wird der Benutzer angemeldet.
					if(m.getPasswort().equals(pw)){
						this.myModel.register(myController.getView(), username, pw);
						success = true;
					}
					else{
						myController.printErrorMessage("Das eingegebene Passwort ist nicht korrekt!");					
					}					
				}
				else{
					System.out.println("Bitte Benutzernamen überprüfen!");
				}				
				
			}catch(Exception e){
				
				String fehler = "Fehler beim Anmelden des Users " + username + ", bitte Eingaben überprüfen:\n" + e.getMessage();
				myController.printErrorMessage(fehler);				
			}		
		}			
		
		return success;		
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Ausloggen eines Nutzers aus dem System.
	 */
	protected boolean benutzerAbmelden(String username){
		boolean success = false;	
		
		if(this.myModel.getMitarbeiter(username) != null){
			try{
				//Entferne die View aus der Liste der Observer beim Model.
				this.myModel.removeObserver(this.myController.getView());
				success = true;
			}catch(Exception e){
				
				String fehler = "Fehler beim Abmelden des Users " + username + " :\n" + e.getMessage();
				myController.printErrorMessage(fehler);					
			}	
		}				
		return success;
	}
	
	/**
	 * @author Lukas Kühl	
	 * @info Ändern der jeweiligen Rechte eines Mitarbeiters von Mitarbeiter- zu Kassenbüro-Rechten oder umgekehrt(je nach derzeitigen Berechtigungen) 
	 */
	protected boolean benutzerRechteÄndern(String username){
		boolean success = false;		
		
		//Prüfe, ob der Mitarbeiter einen Eintrag in der Mitarbeitertabelle besitzt und somit eine aktuelle Berechtigung besitzt.
		if(this.myModel.getMitarbeiter(username) != null){
			
			Userrecht alt = this.myModel.getUserrecht(this.myModel.getMitarbeiter(username).getJob());
					
			try{
				//Wechsel der Benutzerrolle des übergebenen Mitarbeiters.
				this.myModel.wechselBenutzerrolle(username);	
			
				Userrecht neu = this.myModel.getUserrecht(this.myModel.getMitarbeiter(username).getJob());
				
				if(!alt.getBenutzerrolle().equals(neu.getBenutzerrolle())){
					success = true;
				}		
			}catch(Exception e){
				String fehler = "Fehler beim Ändern der Rechte des Nutzers " + username + " :\n" + e.getMessage();
				myController.printErrorMessage(fehler);					
			}			
		}				
		
		return success;
	}

}
