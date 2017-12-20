package view;

import java.util.TreeMap;

import javax.swing.JOptionPane;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

public class Einsatzplanview {

	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private String username = null;
	
	
	public Einsatzplanview(String username, EinsatzplanController einsatzplanController, Einsatzplanmodel model) {
		this.myController = einsatzplanController;
		this.myModel = model;
		this.username = username;
	}
	
	/**
	 * @author - Ramona Gerke
	 * @info Die Parameter werden an den Controller mit den definierten Übergabeparametern übergeben.
	 */
	protected boolean erstelleTermin(String username, String bez, TreeMap<Integer, String> zeitraum, String grund) {
		boolean erfolg = false;
		
		try {
			erfolg = this.myController.erstelleTermin(username, bez, zeitraum, grund);  // TREEMAP MUSS AUF STRING STRING GEÄNDERT WERDEN
			JOptionPane.showConfirmDialog(null, "Termin erfolgreich erstellt");
		}catch (Exception e) {
			
		}
		if (erfolg == false) {
			JOptionPane.showConfirmDialog(null, "Der Termin konnte erstellt werden. Bitte die Eingaben prüfen");
		}
		
		return erfolg;
	}
	
	protected boolean entferneWochenplan(String username, String wpbez){ {
		boolean erfolg = false;
		
		try {
			erfolg = this.myController.entferneWochenplan(username, wpbez);
					
		}catch (Exception e) {
			
		}
		if (erfolg == false) {
			JOptionPane.showConfirmDialog(null, "Der Termin konnte nicht erstellt werden. Bitte die Eingaben prüfen");
		}
		
		return erfolg;
	}
	}
	
	protected boolean entferneTermin(int terminnr, String username){ {
		boolean erfolg = false;
		
		try {
			erfolg = this.myController.entferneTermin(terminnr, username);
					
		}catch (Exception e) {
			
		}
		if (erfolg == false) {
			JOptionPane.showConfirmDialog(null, "Der Termin konnte nicht gelöscht werden. Bitte die Auswahl prüfen");
		}
		
		return erfolg;
	}
	}

	protected boolean erstelleTauschanfrage(String senderName, int senderSchichtNr, String empfaengerName, int empfaengerSchichtNr ) {
		boolean erfolg = false;
		
		try {
			erfolg = this.myController.erstelleTauschanfrage(senderName, senderSchichtNr, empfaengerName, empfaengerSchichtNr);
					
		}catch (Exception e) {
			
		}
		if (erfolg == false) {
			JOptionPane.showConfirmDialog(null, "Die Tauschanfrage  konnte nicht angelegt werden. Bitte die Eingaben prüfen");
		}
		
		return erfolg;
	}
	
	protected boolean entferneTauschanfrage(int tauschanfrageNr) {
		boolean erfolg = false;
		
		try {
			erfolg = this.myController.entferneTauschanfrage(tauschanfrageNr);
					
		}catch (Exception e) {
			
		}
		if (erfolg == false) {
			JOptionPane.showConfirmDialog(null, "Die Tauschanfrage konnte nicht entfernt werden. Bitte die Eingaben prüfen");
		}
		
		return erfolg;
	}
	
	protected String getUsername(){
		return username;
	}
	
	protected void setUsername(String username){
		this.username = username;
	}
	
}

