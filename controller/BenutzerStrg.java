package controller;

import data.Mitarbeiter;
import data.Userrecht;
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
	 * @info Erzeugen eines Objektes der Klasse BenutzerStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Zuweisen des Models, um entsprechende Anfragen weiterleiten zu k�nnen.
	 */
	protected BenutzerStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}	
	
	/**
	 * @author Lukas K�hl	
	 * @info Anlegen eines neuen Benutzers f�r einen Mitarbeiter eines Warenhauses im System. 
	 */
	protected boolean benutzerErstellen(Mitarbeiter m){

		boolean success = false;	
		
		//Pr�fe, ob der Mitarbeiter bereits vorhanden ist. Falls nein, wird ein neuer Mitarbeiter in die Datenbank eingef�gt.
		if(this.myModel.getMitarbeiter(m.getBenutzername()) == null){
			success = this.myModel.addMitarbeiter(m);			
		}		
		
		return success;
	}
		
	//Wird zur Zeit noch nicht verwendet!
	/**
	 * @author Lukas K�hl	
	 * @info Entfernen eines nicht mehr ben�tigten Users f�r einen Mitarbeiter eines Warenhauses
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
	 * @author Lukas K�hl
	 * @info Login Angaben f�r einen spezifischen Nutzer �berpr�fen und bei korrekten Angaben anmelden des Benutzers im System und hinterlegen der View in der Observer-Liste des Models.
	 */
	protected boolean benutzerAnmelden(String username, String pw){

		boolean success = false;
		
		//Pr�fe, ob es einen Mitarbeiter zu dem �bergebenen Benutzernamen gibt.
		if(this.myModel.getMitarbeiter(username) != null){
			try{								
				Mitarbeiter m = myModel.getMitarbeiter(username);				
				
				//SQL ist nicht case-sensitiv --> zus�tzliche �berpr�fung des eingegebenen Usernamen				
				if(m.getBenutzername().equals(username)){
					
					//Pr�fe, ob das Passwort korrekt ist, falls ja wird der Benutzer angemeldet.
					if(m.getPasswort().equals(pw)){
						this.myModel.register(myController.getView(), username, pw);
						success = true;
					}
					else{
						myController.printErrorMessage("Das eingegebene Passwort ist nicht korrekt!");					
					}					
				}
				else{
					System.out.println("Bitte Benutzernamen �berpr�fen!");
				}				
				
			}catch(Exception e){
				
				String fehler = "Fehler beim Anmelden des Users " + username + ", bitte Eingaben �berpr�fen:\n" + e.getMessage();
				myController.printErrorMessage(fehler);				
			}		
		}			
		
		return success;		
	}
	
	/**
	 * @author Lukas K�hl
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
	 * @author Lukas K�hl	
	 * @info �ndern der jeweiligen Rechte eines Mitarbeiters von Mitarbeiter- zu Kassenb�ro-Rechten oder umgekehrt(je nach derzeitigen Berechtigungen) 
	 */
	protected boolean benutzerRechte�ndern(String username){
		boolean success = false;		
		
		//Pr�fe, ob der Mitarbeiter einen Eintrag in der Mitarbeitertabelle besitzt und somit eine aktuelle Berechtigung besitzt.
		if(this.myModel.getMitarbeiter(username) != null){
			
			Userrecht alt = this.myModel.getUserrecht(this.myModel.getMitarbeiter(username).getJob());
					
			try{
				//Wechsel der Benutzerrolle des �bergebenen Mitarbeiters.
				this.myModel.wechselBenutzerrolle(username);	
			
				Userrecht neu = this.myModel.getUserrecht(this.myModel.getMitarbeiter(username).getJob());
				
				if(!alt.getBenutzerrolle().equals(neu.getBenutzerrolle())){
					success = true;
				}		
			}catch(Exception e){
				String fehler = "Fehler beim �ndern der Rechte des Nutzers " + username + " :\n" + e.getMessage();
				myController.printErrorMessage(fehler);					
			}			
		}				
		
		return success;
	}

}
