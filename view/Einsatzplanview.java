package view;

import java.util.ArrayList;
import java.util.Observer;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

public class Einsatzplanview {

	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private String username = null;

	public Einsatzplanview(EinsatzplanController einsatzplanController, Einsatzplanmodel model) {
		this.myController = einsatzplanController;
		this.myModel = model;		
		AnmeldungView view = new AnmeldungView();
	}
	

	/**
	 * @author - Ramona Gerke
	 * @info Die Methoden übergeben die Parameter an den Einsatzplancontroller oder
	 *       die Views greifen auf den EInsatzplancontroller über die Methoden zu.
	 */

	protected String getUsername() {
		return username;
	}

	protected void setUsername(String username) {
		this.username = username;
	}

	protected boolean erstelleTermin(String username, String bez, TreeMap<String, String> zeitraum, String grund) {
		boolean erfolg = false;

		try {
			erfolg = this.myController.erstelleTermin(username, bez, zeitraum, grund);
			JOptionPane.showConfirmDialog(null, "Termin erfolgreich erstellt");
		} catch (Exception e) {

		}
		if (erfolg == false) {
			JOptionPane.showConfirmDialog(null, "Der Termin konnte erstellt werden. Bitte die Eingaben prüfen");
		}

		return erfolg;
	}

	protected boolean entferneTermin(int terminnr, String username) {
		{
			boolean erfolg = false;

			try {
				erfolg = this.myController.entferneTermin(terminnr, username);

			} catch (Exception e) {

			}
			if (erfolg == false) {
				JOptionPane.showConfirmDialog(null,
						"Der Termin konnte nicht gelöscht werden. Bitte die Auswahl prüfen");
			}

			return erfolg;
		}
	}

	public ArrayList<String> getAlleTermine(String username) {
		ArrayList<String> rueckgabe = null;
		try {
			rueckgabe = this.myController.getAlleTermine(username);
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null,
					"Die Termine konnten nicht aufgerufen werden. Bitte die Eingaben prüfen");
		}
		return rueckgabe;
	}

	protected boolean erstelleTauschanfrage(String senderName, int senderSchichtNr, String empfaengerName,
			int empfaengerSchichtNr) {
		boolean erfolg = false;

		try {
			erfolg = this.myController.erstelleTauschanfrage(senderName, senderSchichtNr, empfaengerName,
					empfaengerSchichtNr);

		} catch (Exception e) {

		}
		if (erfolg == false) {
			JOptionPane.showConfirmDialog(null,
					"Die Tauschanfrage konnte nicht angelegt werden. Bitte die Eingaben prüfen");
		}

		return erfolg;
	}

	protected boolean entferneTauschanfrage(int tauschanfrageNr) {
		boolean erfolg = false;

		try {
			erfolg = this.myController.entferneTauschanfrage(tauschanfrageNr);

		} catch (Exception e) {

		}
		if (erfolg == false) {
			JOptionPane.showConfirmDialog(null,
					"Die Tauschanfrage konnte nicht entfernt werden. Bitte die Eingaben prüfen");
		}

		return erfolg;
	}

	public boolean akzeptiereTauschanfrage(String empfaengerName, int tauschanfrageNr) {

		boolean erfolg = false;
		try {
			erfolg = this.myController.akzeptiereTauschanfrage(empfaengerName, tauschanfrageNr);

		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null,
					"Die Tauschanfrage konnte nicht angenommen werden. Bitte die Eingaben prüfen");
		}

		return erfolg;
	}

	protected boolean entferneWochenplan(String username, String wpbez) {
		{
			boolean erfolg = false;

			try {
				erfolg = this.myController.entferneWochenplan(username, wpbez);

			} catch (Exception e) {

			}
			if (erfolg == false) {
				JOptionPane.showConfirmDialog(null,
						"Der Termin konnte nicht erstellt werden. Bitte die Eingaben prüfen");
			}

			return erfolg;
		}
	}

	public ArrayList<String> getMitarbeiterTermine(String username) {
		ArrayList<String> rueckgabe = null;
		try {
			rueckgabe = this.myController.getMitarbeiterTermine(username);
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null,
					"Die Termine des Mitarbeiters konnten nicht angezeigt werden. Bitte die Eingaben prüfen");
		}
		return rueckgabe;
	}

	public ArrayList<String> getTage(String wpbez) {
		ArrayList<String> rueckgabe = null;
		try {
			rueckgabe = this.myController.getTage(wpbez);
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null,
					"Die Tage des Wochenplans konnten nicht angezeigt werden. Bitte die Eingaben prüfen");
		}
		return rueckgabe;
	}

	public ArrayList<String> getMitarbeiterSchichten(String wpbez, String tagbez, String username) {
		ArrayList<String> rueckgabe = null;
		try {
			rueckgabe = this.myController.getMitarbeiterSchichten(wpbez, tagbez, username);
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null,
					"Die Schichten des Mitarbeiters konnten nicht ausgegeben werden. Bitte Eingaben prüfen!");
		}
		return rueckgabe;
	}

	public ArrayList<String> getAndereMitarbeiterSchichten(String wpbez, String tagbez, String username,
			int schichtNr) {
		ArrayList<String> rueckgabe = null;
		try {
			rueckgabe = this.myController.getAndereMitarbeiterSchichten(wpbez, tagbez, username, schichtNr);
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null,
					"Die Schichten des Mitarbeiters konnten nicht ausgegeben werden. Bitte Eingaben prüfen!");
		}
		return rueckgabe;
	}

	public ArrayList<String> getWochenplaene() {
		ArrayList<String> rueckgabe = null;
		try {
			rueckgabe = this.myController.getWochenplaene();
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null,
					"Die Wochenpläne konnten nicht ausgegeben werden. Bitte Eingaben prüfen!");
		}
		return rueckgabe;
	}

	public void update() {

		// suche aktuelle Woche und dann generie die Wochenplan
		// ALLE EINGABEN FÜR DIE ÄNDERUNG IN DER DATENBANK HINTERLEGEN - Welche Daten
		// müssen hinterlegt werden ? Alle Textfelder je
		// semesterLabel.setText(myModel.getSemesterString());
	}
}
