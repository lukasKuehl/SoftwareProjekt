package controller;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JDialog;

import data.Ma_Schicht;
import data.Mitarbeiter;
import data.Schicht;
import data.Tauschanfrage;
import data.TerminBlockierung;
import model.Einsatzplanmodel;

/**
 * @author Lukas K�hl
 * @info Die Klasse TauschanfrageStrg dient dazu Anfragen, welche sich auf
 *       Tauschanfragen beziehen zu bearbeiten und zu validieren.
 */
class TauschanfrageStrg {

	// Initialsierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private JDialog myDialog = null;

	/**
	 * @author Lukas K�hl
	 * @info Erzeugen eines Objektes der Klasse TauschanfrageStrg. Setzen des
	 *       allgemeinen EinsatzplanControllers als Parent. Die Variable
	 *       myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird
	 *       deshalb sp�ter zugewiesen.
	 */
	protected TauschanfrageStrg(EinsatzplanController myController, Einsatzplanmodel myModel) {
		this.myController = myController;
		this.myModel = myModel;
	}

	/**
	 * @author Anes Preljevic
	 * @info Anlegen einer neuen Tauschanfrage zum Tausch einer Schicht eines
	 *       Mitarbeiters mit der Schicht eines anderen Mitarbeiters
	 */
	protected boolean erstelleTauschanfrage(String senderName, int senderSchichtNr, String empfaengerName,
			int empfaengerSchichtNr) {
		boolean success = false;
		boolean valid = false;

		Schicht senderSchicht = this.myModel.getSchicht(senderSchichtNr);
		Schicht empfaengerSchicht = this.myModel.getSchicht(empfaengerSchichtNr);
		Mitarbeiter sender = this.myModel.getMitarbeiter(senderName);
		Mitarbeiter empfaenger = this.myModel.getMitarbeiter(empfaengerName);
		LinkedList<Tauschanfrage> alleTauschanfragen = this.myModel.getTauschanfragen();

		int tauschNr = this.myModel.getNewTauschnr();
		// �berpr�fen ob die �bergebenen Variablen leer sind, wenn nicht
		// weiterleiten an den n�chsten Test und valid auf true setzen
		if (senderSchicht != null && empfaengerSchicht != null && sender != null && empfaenger != null) {
			valid = true;
			// �berpr�fen ob es zu den �bergebenen Werten bereits eine
			// Tauschanfrage gibt, wenn ja setze valid auf false
			for (Tauschanfrage ta : alleTauschanfragen) {
				if (ta.getEmpf�nger().equals(empfaengerName) && ta.getSender().equals(senderName)
						&& ta.getSchichtnrempf�nger() == empfaengerSchichtNr
						&& ta.getSchichtnrsender() == senderSchichtNr) {
					valid = false;
				}
			}
		}
		// Wenn die Tests erfolgreich waren und somit valid=true ist kann die
		// Tauschanfrage hinzugef�gt werden
		// Werte nicht null und nicht bereits vorhanden
		if (valid) {
			try {
				this.myModel.addTauschanfrage(tauschNr, senderName, senderSchichtNr, empfaengerName,
						empfaengerSchichtNr);
				success = true;
			} catch (Exception e) {

				String fehler = "Controller: Fehler beim Erstellen einer Tauschanfrage:\n" + e.getMessage();
				myController.printErrorMessage(fehler);
			}
		}
		// Fehlermeldung, dass die �bergebenen Werte entweder null sind oder die Tauschanfrage
		// bereits existiert
		else {

			String fehler = "Tauschanfrage kann nicht erstellt werden Tauschanfrage existiert bereits oder Sender/Empf�nger Name und Schicht falsch . \n";
			myController.printErrorMessage(fehler);
		}

		return success;
	}

	/**
	 * @author Anes Preljevic
	 * @info Eine bereits existierende Tauschanfrage soll aus dem System
	 *       entfernt werden
	 */
	protected boolean entferneTauschanfrage(int tauschanfrageNr) {

		boolean success = false;
		// Ruft die deleteTauschanfrage- Methode aus dem Einsatzplanmodel auf,
		// mit der �bergebenen
		// Tauschnr aus der View.
		try {
			this.myModel.deleteTauschanfrage(tauschanfrageNr);
			success = true;
		} catch (Exception e) {
			String fehler = "Controller: Fehler beim Entfernen einer Tauschanfrage aus der Datenbank:\n"
					+ e.getMessage();
			myController.printErrorMessage(fehler);
		}
		return success;
	}

	/**
	 * @Anes Preljevic
	 * @info Der Empf�nger einer Tauschanfrage m�chte diese annehmen, um
	 *       seine/ihre Schicht mit einer anderen zu tauschen
	 */
	protected boolean akzeptiereTauschanfrage(String empfaengerName, int tauschanfrageNr) {
		boolean success = false;
		boolean valid = false;
		boolean valid2 = false;
		LinkedList<Ma_Schicht> maschichtList = myModel.getMa_Schicht();
		System.out.println(empfaengerName+";"+ tauschanfrageNr);
		LinkedList<Tauschanfrage> alleTauschanfragen = this.myModel.getTauschanfragen();
		// Tauschanfragen nach der zu Best�tigenden Tauschanfrage durchsuchen.
		// Falls die Tauschanfrage nicht vorhanden ist wird die Best�tigung �ber
		// "valid" nicht gegeben, da es false bleibt
		for (Tauschanfrage ta : alleTauschanfragen) {
			if ((ta.getTauschnr() == tauschanfrageNr) && (ta.getEmpf�nger().equals(empfaengerName))) {
				System.out.println(ta.getTauschnr()+""+ta.getEmpf�nger());
				// �berpr�fen ob die Mitarbeiter tats�chlich in den Angegebenen
				// Schichten sind
				for (Ma_Schicht masch : maschichtList) {
					
					// Gibt es eine Ma_Schicht-Relation in welcher, der
					// Empf�nger in der �bergebenen Schicht zu finden ist?
					if (masch.getBenutzername().equals(ta.getEmpf�nger())
							&& masch.getSchichtnr() == ta.getSchichtnrempf�nger()){
						System.out.println(masch.getBenutzername()+";"+ta.getEmpf�nger()+";"+masch.getSchichtnr()+""+ ta.getSchichtnrempf�nger());
						valid = true;
					}
					// Gibt es eine Ma_Schicht-Relation in welcher, der Sender
					// in der �bergebenen Schicht zu finden ist?
					if (masch.getBenutzername().equals(ta.getSender())
							&& masch.getSchichtnr() == ta.getSchichtnrsender()) {
						System.out.println(masch.getBenutzername()+";"+ta.getSender()+";"+masch.getSchichtnr()+""+ ta.getSchichtnrsender());
						valid2 = true;
					}
				}
			}
		}
		// Wenn im vorherigen Schritt die Tauschanfrage existiert hat, werden
		// die Variablen �bergeben und die Tauschanfrage best�tigt
		if (valid && valid2) {
			try {
				this.myModel.best�tigeTauschanfrage(empfaengerName, tauschanfrageNr);
				System.out.println(empfaengerName+";"+ tauschanfrageNr);
				success = true;
			} catch (Exception e) {

				String fehler = "Controller: Fehler beim Best�tigen einer Tauschanfrage:\n" + e.getMessage();
				myController.printErrorMessage(fehler);
			}
		}
		// Fehlermeldung, dass es zu dem �bergebenen Empf�nger und der Tauschnr
		// keine g�ltige Tauschanfrage gibt
		else {

			String fehler = "Tauschanfrage kann nicht best�tigt werden, User ist nicht der Empf�nger, Tauschanfrage nicht vorhanden oder Mitarbeiter nicht in der vorgebenen Schicht. \n";
			myController.printErrorMessage(fehler);
		}
		return success;
	}

	/**
	 * @author Lukas K�hl
	 * @info Auslesen aller Tauschanfragen, die an einen bestimmten Mitarbeiter
	 *       gerichtet sind (Mitarbeiter ist somit als Empf�nger eingetragen)
	 *       und Aufbereitung f�r Anzeige in der View
	 */
	protected ArrayList<String> getTauschanfragen(String username) {
		ArrayList<String> rueckgabe = new ArrayList<String>();

		LinkedList<Tauschanfrage> alleTauschanfragen = this.myModel.getTauschanfragen();
		LinkedList<Tauschanfrage> mitarbeiterTauschanfragen = new LinkedList<Tauschanfrage>();

		// Suche alle Tauschanfragen, die f�r den �bergebenen Mitarbeiternamen
		// bestimmt sind, f�r die die Person als Empf�nger eingetragen ist.
		for (Tauschanfrage ta : alleTauschanfragen) {
			if (ta.getEmpf�nger().equals(username)) {
				mitarbeiterTauschanfragen.add(ta);
			}
		}

		// Generiere f�r jede Tauschanfrage eine Zeile nach folgendem Muster
		// Sendername - SenderSchichtNr - KW - TagSender - ZeitraumSender ||
		// Empf�ngername - Empf�ngerSchichtNr - KW - TagEmpf�nger -
		// ZeitraumEmpf�nger
		// --> Ein Tausch ist somit nur innerhalb einer Woche m�glich
		for (Tauschanfrage t : mitarbeiterTauschanfragen) {

			Mitarbeiter sender = this.myModel.getMitarbeiter(t.getSender());
			Schicht senderSchicht = this.myModel.getSchicht(t.getSchichtnrsender());
			Mitarbeiter empfaenger = this.myModel.getMitarbeiter(t.getEmpf�nger());
			Schicht empfaengerSchicht = this.myModel.getSchicht(t.getSchichtnrempf�nger());

			// Hinterlegen der individuellen Tausch-Nr
			String temp = "TauschNr: " + t.getTauschnr() + " - ";

			// Eintragen Sender-Informationen
			temp = temp + sender.getVorname() + " " + sender.getName() + " - " + senderSchicht.getSchichtnr() + " - "
					+ "KW" + senderSchicht.getWpnr() + " - " + senderSchicht.getTbez() + " - "
					+ senderSchicht.getAnfanguhrzeit() + "-" + senderSchicht.getEndeuhrzeit() + "\n --> ";

			// Eintragen Empf�nger-Informationen
			temp = temp + empfaenger.getBenutzername() + " - " + empfaengerSchicht.getSchichtnr() + " - " + "KW"
					+ empfaengerSchicht.getWpnr() + " - " + empfaengerSchicht.getTbez() + " - "
					+ empfaengerSchicht.getAnfanguhrzeit() + ":" + empfaengerSchicht.getEndeuhrzeit();

			rueckgabe.add(temp);
		}

		return rueckgabe;
	}

}
