package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import javax.swing.JTable;

import data.Ma_Schicht;
import data.Mitarbeiter;
import data.Observable;
import data.Observer;
import data.Schicht;
import data.Standardeinstellungen;
import data.Tag;
import data.Tauschanfrage;
import data.Tblock_Tag;
import data.TerminBlockierung;
import data.Userrecht;
import data.Warenhaus;
import data.Wochenplan;
import controller.EinsatzplanController;
import view.Einsatzplanview;
import java.sql.Connection;

/**
 * @author Anes Preljevic
 * @info Allgemeines Model zum entgegennehmen der Anfragen aus der GUI und dem
 *       Controller und weiterleiten an die spezifischen Models. Informiert alle
 *       Observer �ber ver�nderungen
 */
public class Einsatzplanmodel implements Observable {

	// Initialiserung der Instanzvariablen
	private ArrayList<Observer> observers = null;

	private Datenbank_Connection dataConnection = null;
	private Connection con = null;
	private Datenbank_Ma_Schicht dataMa_Schicht = null;
	private Datenbank_Mitarbeiter dataMitarbeiter = null;
	private Datenbank_Schicht dataSchicht = null;
	private Datenbank_Standardeinstellungen dataStandardeinstellungen = null;
	private Datenbank_Tag dataTag = null;
	private Datenbank_Tauschanfrage dataTauschanfrage = null;
	private Datenbank_Tblock_Tag dataTblock_Tag = null;
	private Datenbank_TerminBlockierung dataTerminBlockierung = null;
	private Datenbank_Userrecht dataUserrecht = null;
	private Datenbank_Warenhaus dataWarenhaus = null;
	private Datenbank_Wochenplan dataWochenplan = null;

	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines neuen Objektes der Klasse Einsatzplanmodel und
	 *       erzeugen der ben�tigten Model-Hilfsklassen um diese abrufen zu
	 *       k�nnen. Au�erdem wird �ber Datenbank_Connection die
	 *       Datenbankverbindung hergestellt, welche nur hier erzeugt wird f�r
	 *       das ganze Programm.
	 */
	public Einsatzplanmodel() {

		this.observers = new ArrayList<Observer>();
		this.dataConnection = new Datenbank_Connection();
		this.con = dataConnection.createCon();
		this.dataWochenplan = new Datenbank_Wochenplan(this);
		this.dataMa_Schicht = new Datenbank_Ma_Schicht();
		this.dataMitarbeiter = new Datenbank_Mitarbeiter(this);
		this.dataSchicht = new Datenbank_Schicht(this);
		this.dataStandardeinstellungen = new Datenbank_Standardeinstellungen();
		this.dataTag = new Datenbank_Tag(this);
		this.dataTauschanfrage = new Datenbank_Tauschanfrage(this);
		this.dataTblock_Tag = new Datenbank_Tblock_Tag(this);
		this.dataTerminBlockierung = new Datenbank_TerminBlockierung(this);
		this.dataUserrecht = new Datenbank_Userrecht();
		this.dataWarenhaus = new Datenbank_Warenhaus(this);

	}

	/**
	 * @author Anes Preljevic
	 * @info F�gt einen neuen Observer in die Arraylist observers hinzu, f�r den
	 *       �bergebenen Oberserver, benutzernamen und passwort
	 */
	public void register(Observer newViewobserver, String benutzername, String passwort) {
		observers.add(newViewobserver);

	}

	/**
	 * @author Anes Preljevic
	 * @info Entfernen eines Observers
	 */
	public void removeObserver(Observer deleteViewobserver) {
		int observerIndex = observers.indexOf(deleteViewobserver);
		System.out.println("Observer" + (observerIndex + 1) + "deleted");
		observers.remove(observerIndex);
	}

	/**
	 * @author Anes Preljevic
	 * @info Liste der Observer durchgehen und alle Oberserver benachrichtigen.
	 */
	public void notifyObservers() {
		for (Observer observer : observers) {

			observer.update();
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<Wochenplan> getWochenplaene() {

		LinkedList<Wochenplan> result = null;
		;
		try {
			result = this.dataWochenplan.getWochenplaene(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getWochenplaene:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */

	public Wochenplan getWochenplan(int wpnr) {

		Wochenplan result = null;
		;
		try {
			result = this.dataWochenplan.getWochenplan(wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getWochenplan");
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info Hinzuf�gen eines Wochenplans in die Datenbank
	 */
	public boolean addWochenplan(Wochenplan wochenplan) {

		boolean result = false;
		try {
			result = this.dataWochenplan.addWochenplan(wochenplan, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addWochenplan:");
			e.printStackTrace();
			return false;
		}
		//+notifyObservers();
		return result;

	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public void oeffentlichStatusaendern(Wochenplan wochenplan) {

		try {
			this.dataWochenplan.updateWochenplan(wochenplan, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode updateWochenplan:");
			e.printStackTrace();
		}
		//+notifyObservers();
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public void oeffentlichStatustrue(int wpnr) {

		try {
			this.dataWochenplan.setzeOeffentlichstatustrue(wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode setze�ffentlichtrue:");
			e.printStackTrace();
		}
		//+notifyObservers();
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public void oeffentlichStatusfalse(int wpnr) {

		try {
			this.dataWochenplan.setzeOeffentlichstatusfalse(wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode setze�ffentlichfalse:");
			e.printStackTrace();
		}
		//+notifyObservers();
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean checkWochenplan(int wpnr) {
		boolean result = false;
		try {
			result = this.dataWochenplan.checkWochenplan(wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkWochenplan");
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info �berpr�fen der Foreign-Key-Constrains der WochenplanTabelle
	 */
	public boolean checkWochenplanFK(String benutzername) {
		boolean result = false;
		try {
			result = this.dataWochenplan.checkWochenplanFK(benutzername, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkWochenplanFK:");
			e.printStackTrace();
			return false;
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean deleteWochenplan(int wpnr) {
		boolean result = false;
		try {
			result = this.dataWochenplan.deleteWochenplan(wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteWochenplan:");
			e.printStackTrace();
		}
		//+notifyObservers();
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public int getNewWpnr() {
		int result = 0;
		try {
			result = this.dataWochenplan.getNewWpnr(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getNewWpnr:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<Ma_Schicht> getMitarbeiterausderSchicht(int schichtnr) {

		LinkedList<Ma_Schicht> result = null;
		;
		try {
			result = this.dataMa_Schicht.getMitarbeiterausderSchicht(schichtnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode MitarbeiterausderSchicht:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<Ma_Schicht> getSchichteneinesMitarbeiters(String benutzername) {

		LinkedList<Ma_Schicht> result = null;
		;
		try {
			result = this.dataMa_Schicht.getSchichteneinesMitarbeiters(benutzername, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getSchichteneinesMitarbeiters:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<Ma_Schicht> getMa_Schicht() {

		LinkedList<Ma_Schicht> result = null;
		;
		try {
			result = this.dataMa_Schicht.getMa_Schicht(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getMa_schicht:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info Hinzuf�gen eines Ma_Schicht-Datensatzes in die Datenbank
	 */
	public boolean addMa_Schicht(Ma_Schicht ma_schicht) {

		boolean result = false;
		try {
			result = this.dataMa_Schicht.addMa_Schicht(ma_schicht, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addMa_Schicht:");
			e.printStackTrace();
			return false;
		}

		// notifyObservers();
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean checkMa_Schicht(int schichtnr, String benutzername) {
		boolean result = false;
		try {
			result = this.dataMa_Schicht.checkMa_Schicht(schichtnr, benutzername, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkMa_Schicht:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info �berpr�fen der FK-Constraints der Ma-Schicht-Tabelle
	 */
	public boolean checkMa_SchichtFK(int schichtnr, String benutzername) {
		boolean result = false;
		try {
			result = this.dataMa_Schicht.checkMa_SchichtFK(schichtnr, benutzername, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkMa_SchichtFK:");
			e.printStackTrace();
			return false;
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean deleteMa_Schicht(int schichtnr, String benutzername) {
		boolean result = false;
		try {
			result = this.dataMa_Schicht.deleteMa_Schicht(schichtnr, benutzername, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteMa_Schicht:");
			e.printStackTrace();
		}
		// notifyObservers();
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean deleteMa_SchichtWochenplan(int schichtnr) {
		boolean result = false;
		try {
			result = this.dataMa_Schicht.deleteMa_SchichtWochenplan(schichtnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteMa_SchichWochenplan:");
			e.printStackTrace();
		}
		// notifyObservers();
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<Mitarbeiter> getAlleMitarbeiter() {
		LinkedList<Mitarbeiter> result = null;
		;
		try {
			result = this.dataMitarbeiter.getAlleMitarbeiter(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getAlleMitarbeiter:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public Mitarbeiter getMitarbeiter(String benutzername) {
		Mitarbeiter result = null;
		;
		try {
			result = this.dataMitarbeiter.getMitarbeiter(benutzername, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getMitarbeiter:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean checkMitarbeiter(String benutzername) {
		boolean result = false;
		try {
			result = this.dataMitarbeiter.checkMitarbeiter(benutzername, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkMitarbeiter:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info �berpr�fen der FK-Constraints der Mitarbeiter-Tabelle
	 */
	public boolean checkMitarbeiterFK(String job, String whname) {
		boolean result = false;
		try {
			result = this.dataMitarbeiter.checkMitarbeiterFK(job, whname, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkMitarbeiterFK:");
			e.printStackTrace();
			return false;
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<Schicht> getSchichten() {

		LinkedList<Schicht> result = null;
		;
		try {
			result = this.dataSchicht.getSchichten(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getSchichten:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public Schicht getSchicht(int schichtnr) {

		Schicht result = null;
		;
		try {
			result = this.dataSchicht.getSchicht(schichtnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getSchicht:");
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info Hinzuf�gen eines Schicht-Datensatzes in die Datenbank
	 */
	public boolean addSchicht(Schicht schicht) {

		boolean result = false;
		try {
			result = this.dataSchicht.addSchicht(schicht, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addSchicht:");
			e.printStackTrace();
			return false;
		}

		//+notifyObservers();
		return result;

	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean checkSchicht(int schichtnr) {
		boolean result = false;
		try {
			result = this.dataSchicht.checkSchicht(schichtnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkSchicht:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info �berpr�fen der FK-Constraints der Schicht-Tabelle
	 */
	public boolean checkSchichtFK(String tbez, int wpnr) {
		boolean result = false;
		try {
			result = this.dataSchicht.checkSchichtFK(tbez, wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkSchichtFK:");
			e.printStackTrace();
			return false;
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean deleteSchicht(int wpnr) {
		boolean result = false;
		try {
			result = this.dataSchicht.deleteSchichtvonWp(wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteSchicht:");
			e.printStackTrace();
		}
		// notifyObservers();
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public int getNewSchichtnr() {
		int result = 0;
		try {
			result = this.dataSchicht.getNewSchichtnr(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getNewSchichtnr:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public Standardeinstellungen getStandardeinstellungen() {
		Standardeinstellungen result = null;
		;
		try {
			result = this.dataStandardeinstellungen.getStandardeinstellungen(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getStandardeinstellungen:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public void updateStanadardeinstellungen(Standardeinstellungen standardeinstellungen) {

		try {
			this.dataStandardeinstellungen.updateStandardeinstellungen(standardeinstellungen, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode updateStandardeinstellungen:");
			e.printStackTrace();
		}
		//notifyObservers();
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<Tauschanfrage> getTauschanfragen() {

		LinkedList<Tauschanfrage> result = null;
		;
		try {
			result = this.dataTauschanfrage.getTauschanfragen(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTauschanfragen:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public Tauschanfrage getTauschanfrage(int tauschnr) {
		Tauschanfrage result = null;
		;
		try {
			result = this.dataTauschanfrage.getTauschanfrage(tauschnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTauschanfrage:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info Hinzuf�gen eines Tauschanfrage-Datensatzes in die Datenbank
	 */
	public boolean addTauschanfrage(int tauschNr, String senderName, int senderSchichtNr, String empfaengerName,
			int empfaengerSchichtNr) {
		boolean result = false;

		try {
			result = this.dataTauschanfrage.addTauschanfrage(tauschNr, senderName, senderSchichtNr, empfaengerName,
					empfaengerSchichtNr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addTauschanfrage:");
			e.printStackTrace();
			return false;
		}

		//+notifyObservers();
		return result;

	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean checkTauschanfrage(int tauschnr) {
		boolean result = false;
		try {
			result = this.dataTauschanfrage.checkTauschanfrage(tauschnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTauschanfrage:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info �berpr�fen der FK-Constraints der Tauschanfrage-Tabelle
	 */
	public boolean checkTauschanfrageFK(String senderName, int senderSchichtNr, String empfaengerName,
			int empfaengerSchichtNr) {
		boolean result = false;
		try {
			result = this.dataTauschanfrage.checkTauschanfrageFK(senderName, senderSchichtNr, empfaengerName,
					empfaengerSchichtNr, con);
			result = true;

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTauschanfrageFK:");
			e.printStackTrace();
			return false;
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean deleteTauschanfrage(int tauschnr) {
		boolean result = false;
		try {
			result = this.dataTauschanfrage.deleteTauschanfrage(tauschnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteTauschanfrage:");
			e.printStackTrace();
		}
		// notifyObservers();
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public void updateTauschanfrage(Tauschanfrage tauschanfrage) {

		try {
			this.dataTauschanfrage.updateTauschanfrage(tauschanfrage, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode updateTauschanfrage:");
			e.printStackTrace();
		}
		//+notifyObservers();
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public void best�tigeTauschanfrage(String empf�nger, int tauschnr) {

		try {
			this.dataTauschanfrage.best�tigeTauschanfrage(empf�nger, tauschnr);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode best�tigeTauschanfrage:");
			e.printStackTrace();
		}
		//+notifyObservers();
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public int getNewTauschnr() {
		int result = 0;
		try {
			result = this.dataTauschanfrage.getNewTauschnr(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getNewTauschnr:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<Tag> getTage() {
		LinkedList<Tag> result = null;
		;
		try {
			result = this.dataTag.getTage(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTage:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<Tag> getTagewp(int wpnr) {
		LinkedList<Tag> result = null;
		;
		try {
			result = this.dataTag.getTagewp(wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTagewp:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info Hinzuf�gen eines Tag-Datensatzes in die Datenbank
	 */
	public boolean addTag(Tag tag, String oeffnungszeit, String schlie�zeit, String hauptzeitbeginn,
			String hauptzeitende) {

		boolean result = false;
		try {
			result = this.dataTag.addTag(tag, oeffnungszeit, schlie�zeit, hauptzeitbeginn, hauptzeitende, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addTag:");
			e.printStackTrace();
			return false;
		}

		//+notifyObservers();
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean checkTag(String tbez, int wpnr) {
		boolean result = false;
		try {
			result = this.dataTag.checkTag(tbez, wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTag:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info �berpr�fen der FK-Constraints der Tagt-Tabelle
	 */
	public boolean checkTagFK(int wpnr) {
		boolean result = false;

		try {
			result = this.dataTag.checkTagFK(wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTagFK:");
			e.printStackTrace();
			return false;
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean deleteTag(int wpnr) {
		boolean result = false;
		try {
			result = this.dataTag.deleteTag(wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteTag:");
			e.printStackTrace();
		}
		// notifyObservers();
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public void setzeFeiertagtrue(String tbez, int wpnr) {

		try {
			this.dataTag.setzeFeiertagtrue(tbez, wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode setzeFeiertagtrue:");
			e.printStackTrace();
		}
		//+notifyObservers();
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public void setzeFeiertagfalse(String tbez, int wpnr) {

		try {
			this.dataTag.setzeFeiertagfalse(tbez, wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode setzeFeiertagfalse:");
			e.printStackTrace();
		}
		//+notifyObservers();
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<Tblock_Tag> getAlleTblock_Tag() {
		LinkedList<Tblock_Tag> result = null;
		;
		try {
			result = this.dataTblock_Tag.getAlleTblock_Tag(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getAlleTblock_Tag:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<Tblock_Tag> getTblock_TagTB(int tblocknr) {
		LinkedList<Tblock_Tag> result = null;
		;
		try {
			result = this.dataTblock_Tag.getTblock_TagTB(tblocknr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTblock_TagTB:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public Tblock_Tag getTblock_TagT(String tbez, int wpnr) {
		Tblock_Tag result = null;
		;
		try {
			result = this.dataTblock_Tag.getTblock_TagT(tbez, wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTblock_TagT:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info Hinzuf�gen eines Tblock_Tag-Datensatzes in die Datenbank
	 */
	public void addTblock_Tag(Tblock_Tag tblocktag) {

		try {
			this.dataTblock_Tag.addTblock_Tag(tblocktag, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addTblock_Tag:");
			e.printStackTrace();
		}
		//+notifyObservers();
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean checkTblock_TagTA(String tbez, int wpnr) {
		boolean result = false;
		try {
			result = this.dataTblock_Tag.checkTblock_TagTA(tbez, wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTblock_TagTA:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info �berpr�fen der FK-Constraints der Tblock_Tag-Tabelle
	 */
	public boolean checkTblock_TagFK(int tBlockNr, String tbez, int wpnr) {
		boolean result = false;
		try {
			result = this.dataTblock_Tag.checkTblock_TagFK(tBlockNr, tbez, wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTblock_TagFK:");
			e.printStackTrace();
			return false;
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean deleteTblock_Tag(int tblocknr) {
		boolean result = false;
		try {
			result = this.dataTblock_Tag.deleteTblock_Tag(tblocknr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteTblock_Tag:");
			e.printStackTrace();
		}
		// notifyObservers();
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<TerminBlockierung> getTerminBlockierungen() {

		LinkedList<TerminBlockierung> result = null;
		;
		try {
			result = this.dataTerminBlockierung.getTerminBlockierungen(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTerminBlockierungen:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info Hinzuf�gen eines Terminblockierung-Datensatzes in die Datenbank
	 */
	public boolean addTerminBlockierung(TerminBlockierung terminblockierung, int wpnr) {
		boolean result = false;

		try {
			result = this.dataTerminBlockierung.addTerminBlockierung(terminblockierung, wpnr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addTerminBlockierung:");
			e.printStackTrace();
			return false;
		}
		//+notifyObservers();
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean checkTerminBlockierung(int tblocknr) {
		boolean result = false;
		try {
			result = this.dataTerminBlockierung.checkTerminBlockierung(tblocknr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTerminBlockierung:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info �berpr�fen der FK-Constraints der Terminblockierung-Tabelle
	 */
	public boolean checkTerminBlockierungFK(String benutzername) {
		boolean result = false;
		try {
			result = this.dataTerminBlockierung.checkTerminBlockierungFK(benutzername, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTerminBlockierungFK:");
			e.printStackTrace();
			return false;
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public boolean deleteTerminBlockierung(int tblocknr) {
		boolean result = false;
		try {
			result = this.dataTerminBlockierung.deleteTerminBlockierung(tblocknr, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteTerminBlockierung:");
			e.printStackTrace();
		}
		//+notifyObservers();
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public int getNewTblocknr() {
		int result = 0;
		try {
			result = this.dataTerminBlockierung.getNewTblocknr(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getNewTblocknr:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public LinkedList<Warenhaus> getWarenhaus() {
		LinkedList<Warenhaus> result = null;
		;
		try {
			result = this.dataWarenhaus.getWarenhaus(con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getWarenhaus:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Tr�gt den selben oder �hnlichen Namen wie die Methode in der
	 *       Model-Hilfsklasse. Aufrufen der Methode der Model-Hilfsklasse,
	 *       sichern der Ergebnisse/R�ckgabe und diese ebenfalls
	 *       zur�ckgeben.(Alle Methoden in dem Einsatzplanmodel sind von der
	 *       Funktion gleich, es werden nur die R�ckgabewerte angepasst etc.)
	 */
	public Warenhaus geteinWarenhaus(String whname) {
		Warenhaus result = null;
		;
		try {
			result = this.dataWarenhaus.geteinWarenhaus(whname, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getWarenhaus:");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Thomas Friesen
	 * @info Hinzuf�gen eines Warenhaus-Datensatzes in die Datenbank
	 */
	public void addWarenhaus(Warenhaus warenhaus) {

		try {
			this.dataWarenhaus.addWarenhaus(warenhaus, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addWarenhaus:");
			e.printStackTrace();
		}
		//+notifyObservers();
	}

	/**
	 * @author Thomas Friesen
	 * @info Hinzuf�gen eines Mitarbeiter-Datensatzes in die Datenbank
	 */
	public boolean addMitarbeiter(Mitarbeiter mitarbeiter) {

		boolean result = false;

		try {
			result = this.dataMitarbeiter.addMitarbeiter(mitarbeiter, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addMitarbeiter:");
			e.printStackTrace();
			return false;
		}
		//+notifyObservers();
		return result;

	}

	public void wechselBenutzerrolle(String benutzername) {
		try {
			this.dataMitarbeiter.wechselBenutzerrolle(benutzername, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode wechselBenutzerrolle:");
			e.printStackTrace();
		}
		notifyObservers();
	}

	public Userrecht getUserrecht(String job) {
		Userrecht result = null;
		try {
			result = this.dataUserrecht.getUserrecht(job, con);

		} catch (Exception e) {
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode wechselBenutzerrolle:");
			e.printStackTrace();
		}
		return result;
	}
}
