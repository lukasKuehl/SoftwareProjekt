package controller;

import java.util.LinkedList;

import data.Mitarbeiter;
import data.Schicht;
import data.Tag;
import data.Termin;
import data.Warenhaus;
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
	
	/*
	
	private LinkedList<Mitarbeiter> getMitarbeiter(Schicht s){
		
		LinkedList<Mitarbeiter> mitarbeiterList = new LinkedList<Mitarbeiter>();		
		
		return mitarbeiterList;
	}
	
	
	private LinkedList<Mitarbeiter> getVerf�gbareMitarbeiter(Schicht s){
		
		
		LinkedList<Mitarbeiter> mitarbeiterList = new LinkedList<Mitarbeiter>();
	
		return mitarbeiterList;
	}

	 */

}
