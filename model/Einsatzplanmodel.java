package model;

import java.util.LinkedList;
import java.util.TreeMap;
import javax.swing.JTable;

import data.Schicht;
import data.Wochenplan;
import controller.EinsatzplanController;
import view.Einsatzplanview;


/**
 * @author Anes Preljevic
 * @info 
 */
public class Einsatzplanmodel {

	//Initialiserung der Instanzvariablen
	private EinsatzplanController controller = null;
	private Einsatzplanview view = null;
	private Datenbank_Connection dataConnection = null;
	private Datenbank_Ma_Schicht dataMa_Schicht = null;
	private Datenbank_Mitarbeiter dataMitarbeiter = null;
	private Datenbank_Schicht dataSchicht=null;
	private Datenbank_Standardeinstellungen dataStandardeinstellungen=null;
	private Datenbank_Tag dataTag=null;
	private Datenbank_Tauschanfrage dataTauschanfrage = null;
	private Datenbank_Tblock_Tag dataTblock_Tag=null;
	private Datenbank_TerminBlockierung dataTerminBlockierung = null;
	private Datenbank_Userrecht dataUserrecht = null;
	private Datenbank_Warenhaus dataWarenhaus = null;
	private Datenbank_Wochenplan dataWochenplan=null;
	
	public Einsatzplanmodel(){
		
		
		
		this.dataWochenplan= new Datenbank_Wochenplan(this);
		
	}
	
	public Wochenplan getWochenplan(int wpnr){
		
		Wochenplan result = null;;
		try{
			result = this.dataWochenplan.getWochenplan(wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		
		
		return result;
	}
	public void addWochenplan(Wochenplan wochenplan){
		
		
		try{
			this.dataWochenplan.addWochenplan(wochenplan);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		
		
		
	}
	public void öffentlichStatusändern(Wochenplan wochenplan){
		
		
		try{
			this.dataWochenplan.updateWochenplan(wochenplan);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		
		
		
	}
}
