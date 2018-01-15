package model;

import java.sql.*;
import java.util.LinkedList;
import data.*;

/**
 * @author Thomas Friesen
 * @info Whitebox-Test zur überprüfen der Methoden im System und auf der Konsole.
 */
public class TestModelThomas {

	public static void main(String[] args) {

		Einsatzplanmodel wps = new Einsatzplanmodel();

		/*
		// CheckMa_CheckMa_SchichtFK
		boolean checkfk = wps.checkMa_SchichtFK(10004, "Fhimmelmann");
		if (checkfk == true) {
			System.out.println("success check");
		} else {
			System.out.println("fail check");
		}

		// CheckMa_Schicht Test
		boolean schicht = wps.addMa_Schicht(new Ma_Schicht("Kmuster", 10004));
		if (schicht == true) {
			System.out.println("success checkMa_Schicht");
		} else {
			System.out.println("fail checkMa_Schicht");
		}

		// CheckTauschanfrage Test
		boolean tauschanfrage = wps.checkTauschanfrageFK("Cfriese", 10004, "Hmustermann", 10001);
		if (tauschanfrage == true) {
			System.out.println("success checkTauschanfrageFK");
		} else {
			System.out.println("fail checkTauschanfrageFK");
		}

		// AddTauschanfrage Test
		boolean tausch = wps.addTauschanfrage(100007, "Gschmidt", 10000, "Pchmidtmann", 10000);
		if (tausch == true) {
			System.out.println("success checkTauschanfrageFK");
		} else {
			System.out.println("fail checkTauschanfrageFK");
		}

		// CheckTerminBlockierungFK Test
		boolean tauschfk = wps.checkTerminBlockierungFK("Cfriese");
		if (tauschfk == true) {
			System.out.println("success checkTerminBlockierungFK");
		} else {
			System.out.println("fail checkTerminBlockierungFK");
		}

		// CheckWochenplanFK Test
		boolean wochenplanfk = wps.checkWochenplanFK("Cfriese");
		if (wochenplanfk == true) {
			System.out.println("success checkTerminBlockierungFK");
		} else {
			System.out.println("fail checkTerminBlockierungFK");
		}

		// CheckSchichtFK Test
		boolean schichtfk = wps.checkSchichtFK("Montag", 1000);
		if (schichtfk == true) {
			System.out.println("success checkSchichtFK");
		} else {
			System.out.println("fail checkSchichtFK");
		}

		// AddSchicht Test
		boolean addschicht = wps.addSchicht(new Schicht(10002, "Mittwoch", 1000, "07:30", "12:00"));
		if (addschicht == true) {
			System.out.println("success checkSchichtFK");
		} else {
			System.out.println("fail checkSchichtFK");
		}

		// AddWochenplan Test
		boolean wochenplan = wps.addWochenplan(
				new Wochenplan(wps.getNewWpnr(), false, "07:30", "22:30", "11:00", "19:00", "Dgöring", 1, 1, 4, 0));
		if (wochenplan == true) {
			System.out.println("succes addWochenplan");
		} else {
			System.out.println("fail addWochenplan");
		}
		
		//Test CheckTagFK
		boolean wochenplan = wps.checkTagFK(1000);
		if (wochenplan == true) {
			System.out.println("succes addWochenplan");
		} else {
			System.out.println("fail addWochenplan");
		}

		// Test checkTerminBlockierungFK
		boolean checktermin = wps.checkTerminBlockierungFK("Fhimmelman");
		if (checktermin == true) {
			System.out.println("succes check termin");
		} else {
			System.out.println("fail checktermin");
		}

		// Test addTerminBlockierung
		boolean addtermin = wps.addTerminBlockierung(new TerminBlockierung(107, "Cfriese", "Block3", "Montag", "Montag",
				"08:00:00", "12:00:00", "Krankheit"), 1000);
		if (addtermin == true) {
			System.out.println("succes addtermin");
		} else {
			System.out.println("fail addtermin");
		}

		// Test CheckTBlock_TagFK
		boolean checktermin = wps.checkTblock_TagFK(100, "Montag", 1001);
		if (checktermin == true) {
			System.out.println("succes checktermintag");
		} else {
			System.out.println("fail checktermintag");
		}

		// Test addMitarbeiter
		boolean addmitarbeiter = wps.addMitarbeiter(new Mitarbeiter("Tfriese", "passwort9", "Kassierer", "Thomas",
				"Friesen", 40, "Lebensmittelwarenhaus", "thomas@web.de"));
		if (addmitarbeiter == true) {
			System.out.println("succes addMitarbeiter");
		} else {
			System.out.println("fail addMitarbeiter");
		}

		// Test addTauschanfrage
		boolean tausch = wps.addTauschanfrage(100010, "Cfriese", 10000, "Fhimmelmann", 10000);
		if (tausch == true) {
			System.out.println("success checkTauschanfrageFK");
		} else {
			System.out.println("fail checkTauschanfrageFK");
		}

		// Test TerminBlockierung
		boolean addtermin = wps.addTerminBlockierung(new TerminBlockierung(108, "Cfriese", "Block3", "Montag", "Montag",
				"08:00:00", "12:00:00", "Krankheit"), 1000);
		if (addtermin == true) {
			System.out.println("succes addtermin");
		} else {
			System.out.println("fail addtermin");
		}

		// Test Warenhaus
				boolean warenhaus = wps.addWarenhaus(new Warenhaus());
				if (addtermin == true) {
					System.out.println("succes addWarenhaus");
				} else {
					System.out.println("fail addWarenhaus");
				}
				*/
	}

}
