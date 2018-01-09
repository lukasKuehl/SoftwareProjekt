package view;

import java.awt.EventQueue;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.EinsatzplanController;
import data.Observer;
import model.Einsatzplanmodel;

/**
 * 
 * @author Darius Panteli
 * @Info 
 *  Allgemeine View zum weiterleiten der Anfragen und weiterleiten an die spezifischen Steuerungen.
 */

public class Einsatzplanview implements Observer {

	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private String username = null;
	private static Einsatzplanmodel epm = new Einsatzplanmodel();

	public Einsatzplanview(EinsatzplanController einsatzplanController, Einsatzplanmodel model) {
		this.myController = einsatzplanController;
		this.myModel = model;
		AnmeldungView anmeldungView = new AnmeldungView(einsatzplanController, model, this);
	}
	
	protected String getUsername() {
		return username;
	}
	
	protected void setUsername(String username) {
		this.username = username;
	}	
	
	
	/**
	 *@author Darius Panteli
	 *@Info Update Methode, um die View auf den aktuellen Stand zu halten sofern sich Daten gändert haben
	 *(z.B. Neuer Wochenplan)
	 */
	public void update() {
		
		if (myModel.getMitarbeiter(username).getJob().equalsIgnoreCase("chef")
				|| myModel.getMitarbeiter(username).getJob().equalsIgnoreCase("kassenbüro")){
			
			new KasseWochenView(myModel, myController, this);
			
			
		}else{
			new MitarbeiterWochenView(myModel, myController, this);
		}
	}


}
