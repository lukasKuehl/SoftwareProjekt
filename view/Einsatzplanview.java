package view;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import controller.EinsatzplanController;
import data.Observer;
import model.Einsatzplanmodel;

/**
 * 
 * @author 
 * @Info 
 */

//Kommentare innerhalb der Methoden fehlen!

//Update-Methode wurde noch nicht ausgefüllt!(siehe Beschreibung in der Methode)

public class Einsatzplanview implements Observer {

	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private String username = null;
	private static Einsatzplanmodel epm = new Einsatzplanmodel();

	
	//Die main Methode muss raus --> Nur eine main-Methode im gesamten Programm möglich und Einsatzplanview kann erst nach dem Model erzeugt werden
	
	


	
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
	 *@author Ramona Gerke
	 *@Info Update Methode, sobald sich die Daten aus dem aktuellen geändert haben, wird ein neur Wochenplan generiert.
	 */
	public void update() {
		

		//Muss noch ausgefüllt werden --> zu Programmstart die erste Woche in der Wochenliste, andernfalls die aktuelle die in dem Label in der jeweiligen Wochenview liegt
		
		
		
	}


}
