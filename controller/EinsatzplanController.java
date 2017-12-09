package controller;

import java.util.LinkedList;
import java.util.TreeMap;
import javax.swing.JTable;

import data.Schicht;
import model.Einsatzplanmodel;
import view.Einsatzplanview;


/**
 * @author Lukas
 * @info Allgemeiner Controller zum entgegennehmen der Anfragen aus der GUI und weiterleiten an die spezifischen Steuerungen
 */
public class EinsatzplanController {

	//Initialiserung der Instanzvariablen
	private Einsatzplanmodel model = null;
	private Einsatzplanview view = null;
	private BenutzerStrg benutzerSteuerung = null;
	private SchichtStrg schichtSteuerung = null;
	private TauschanfrageStrg tauschanfrageSteuerung = null;
	private TerminStrg terminSteuerung = null;
	private WochenplanStrg wochenplanSteuerung = null;
		
	public EinsatzplanController(Einsatzplanmodel epm){
		
		this.model = epm;
		this.view = new Einsatzplanview(this, this.model);
		this.benutzerSteuerung = new BenutzerStrg(this, this.model);
		this.schichtSteuerung = new SchichtStrg(this, this.model);
		this.tauschanfrageSteuerung = new TauschanfrageStrg(this, this.model);
		this.terminSteuerung = new TerminStrg(this, this.model);
		this.wochenplanSteuerung = new WochenplanStrg(this, this.model);
		
	}
	
	public boolean benutzerAnmelden(String username, String pw){
		return this.benutzerSteuerung.benutzerAnmelden(username, pw);
	}
	
	public boolean benutzerAbmelden(String username){
		return this.benutzerSteuerung.benutzerAbmelden(username);
	}
	
	public boolean benutzerAnlegen(String username, String pw){
		return this.benutzerSteuerung.benutzerErstellen(username, pw);
	}
	
	public boolean benutzerEntfernen(String username){
		return this.benutzerSteuerung.benutzerEntfernen(username);
	}
	
	public boolean benutzerRechteWechsel(String username){
		return this.benutzerSteuerung.benutzerRechteÄndern(username);
	}
	
	public boolean fülleSchicht(String[] mitarbeiternummern){
		return this.schichtSteuerung.ausfüllenSchicht(mitarbeiternummern);
	}
	
	public boolean erstelleTauschanfrage(String senderName, int senderSchichtNr, String empfaengerName, int empfaengerSchichtNr ){
		return this.tauschanfrageSteuerung.erstelleTauschanfrage(senderName, senderSchichtNr, empfaengerName, empfaengerSchichtNr);
	}
	
	public boolean entferneTauschanfrage(int tauschanfrageNr){
		return this.tauschanfrageSteuerung.entferneTauschanfrage(tauschanfrageNr);		
	}
	
	public boolean akzeptiereTauschanfrage(String empfaengerName, int tauschanfrageNr){
		return this.tauschanfrageSteuerung.akzeptiereTauschanfrage(empfaengerName, tauschanfrageNr);
	}
	
	public boolean erstelleTermin(String username, String bez, TreeMap<String, String> zeitraum, String grund){
		return this.terminSteuerung.erstelleTermin(username, bez, zeitraum, grund);
	}
	
	public boolean entferneTermin(int tblocknr, String username){
		return this.terminSteuerung.entferneTermin(tblocknr, username);
	}
	
	public JTable erstelleWochenplanStandard(String username){
		return this.wochenplanSteuerung.erstelleWochenplanStandard(username);
	}
	
	public JTable erstelleWochenplan(String username, TreeMap<String, String> zeiten, TreeMap<String, Integer> besetzung  ){
		return this.wochenplanSteuerung.erstelleWochenplanCustom(username, zeiten, besetzung);
	}
	
	public boolean publishWochenplan(String username, String wpbez){
		return this.wochenplanSteuerung.publishWochenplan(username, wpbez);
	}
	
	public boolean entferneWochenplan(String username, String wpbez){
		return this.wochenplanSteuerung.entferneWochenplan(username, wpbez);
	}
	
	public boolean verschickeWochenplan(String username, String wpbez){
		return this.wochenplanSteuerung.verschickeWochenplan(username, wpbez);
	}
	
	protected Einsatzplanview getView(){
		return this.view;		
	}	
}
