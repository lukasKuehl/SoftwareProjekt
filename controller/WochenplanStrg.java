package controller;

import javax.swing.JDialog;

import data.Wochenplan;
import model.Einsatzplanmodel;

/**
 * @author Lukas
 * @info Die Klasse WochenplanStrg dient dazu, jegliche Anfragen bezüglich eines Wochenplanes im System zu verarbeiten und zu validieren.
 */
public class WochenplanStrg {
	
	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private JDialog myDialog = null;
	
	/**
	 * @author 
	 * @info Erzeugen eines Objektes der Klasse WochenplanStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb später zugewiesen.
	 */
	protected WochenplanStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}
	
	/**
	 * @author 
	 * @info Anlegen eines neuen Wochenplanes und hinterlegen des Planes in der Datenbank inklusive der spezifischen Restriktionen für die zu erstellende Woche(benutzerdefiniert/Standard)
	 */
	protected boolean erstelleWochenplan(){
		boolean success = false;
		//ausfüllen
		return success;
	}
		
	/**
	 * @author 
	 * @info Veröffentlichen eines erstellten Wochenplanes im System, so dass er von allen Mitarbeitern und nicht nur vom Kassenbüro/Chef gesehen werden kann.
	 */
	protected void publishWochenplan(){
		
		//ausfüllen
		
	}
	
	/**
	 * @author
	 * @info Entfernen eines bereits erstellten Wochenplanes aus dem System mit allen daraus resultierenden Informationen über Tage, Schichten und Terminen/Krankheiten/Urlaub
	 */
	protected boolean entferneWochenplan(){
		
		boolean success = false;
		//ausfüllen
		return success;
	}
		
	/**
	 * @author 
	 * @info Die Methode dient dazu einen bestimmten Wochenplan an alle Mitarbeiter der Warenhäuser per E-Mail zu verschicken um Ihnen die Arbeitszeiten mitzuteilen
	 */
	protected boolean verschickeWochenplan(){
		boolean success = false;
		
		//ausfüllen
		
		return success;
	}
		
	/**
	 * @author 
	 * @info Hinterlegen eines Wochenplanes mit dem derzeitigen Stand und Veröffentlichungsstatus im System
	 */
	protected boolean sichereWochenplan(){
		boolean success = false;
		//ausfüllen
		
		return success;
	}
	
	
	/**
	 * @author 
	 * @info Hilfsmethode zum erstellen von Vorgaben für einen Wochenplan 
	 */
	private void erstelleVorgaben(Wochenplan wp, boolean standard){
		
		//ausfüllen
		
		
	}
	
}
