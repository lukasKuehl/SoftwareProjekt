package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Ma_Schicht;
import data.Tauschanfrage;

/**
 * @author Thomas Friesen, Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und �nderung in der Datenbank
 *       im Bezug auf die Tabelle Ma_Schicht zu verarbeiten.
 */

class Datenbank_Ma_Schicht {

	/**
	 * @Thomas Friesen
	 * @info Die Methode f�gt einen Datensatz in die Ma_Schicht Tabelle ein.
	 */
	protected boolean addMa_Schicht(Ma_Schicht ma_schicht, Connection con) {
		boolean success = false;
		int schichtnr = 0;
		PreparedStatement pstmt = null;
		String benutzername = null;
		String sqlStatement = "insert into Ma_Schicht (Schichtnr,Benutzername) values(?, ?)";

		try {
			pstmt = con.prepareStatement(sqlStatement);

			// Auslesen der als Parameter �bergebenen
			// Mitarbeiter-Schicht-Beziehung
			schichtnr = ma_schicht.getSchichtnr();
			benutzername = ma_schicht.getBenutzername();

			con.setAutoCommit(false);

			// �berpr�fung der PK-Check-Constrains
			if (checkMa_Schicht(schichtnr, benutzername, con)) {
				System.out.println("Der Mitarbeiter wurde bereits in die Schicht eingeteilt!");

				// �berpr�fung der FK-Check-Constraints
			}
			if ((checkMa_SchichtFK(schichtnr, benutzername, con) == false)) {
				System.out.println("Der Foreign-Key Constraint  der Ma_Schicht Tabelle wurde verletzt!");
			} else {
				// Es wurde sichergestellt, dass die PK- und
				// FK-Check-Constraints nicht verletzt werden --> Der Datensatz
				// kann erzeugt werden

				// Ausf�llen und ausf�hren des Prepared Statements
				pstmt.setInt(1, schichtnr);
				pstmt.setString(2, benutzername);
				pstmt.execute();

				// �bertragung der Daten in die Datenbank
				con.commit();
				success = true;

			}

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			// Die Ausgaben dienen zur Ursachensuche und sollten im sp�teren
			// Programm entweder gar nicht oder eventuell als Dialog angezeigt
			// werden
			System.out.println("Fehler beim Einf�gen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Zuordnung eines Mitarbeiters zu einer Schicht:");
			System.out.println("Parameter: Schichtnr = " + schichtnr + " Mitarbeiter = " + benutzername);
			sql.printStackTrace();

			try {
				// Zur�cksetzen des Connection Zustandes auf den
				// Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addMa_Schicht " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			// Schlie�en der offen gebliebenen Statements
			try {
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				System.err.println("Methode addMa_Schicht(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}

	/**
	 * @author Anes Preljevic
	 * @info Pr�ft ob es zu der eingegebenen schichtnr und dem benutzernamen
	 *       bereits einen Mitarbeiter in der Schicht gibt, bei Existenz return
	 *       true, sonst false.
	 */
	protected boolean checkMa_Schicht(int schichtnr, String benutzername, Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		// Ben�tigten Sql-Befehlt speichern
		String sqlQuery = "select schichtnr, benutzername from Ma_Schicht where schichtnr = " + schichtnr
				+ " and benutzername= '" + benutzername + "'";

		try {
			// Statement wird erstellt und Sql-Befehl wird ausgef�hrt,
			// schlie�end wird der n�chste Datensatz aus dem Resultset
			// ausgegeben.
			// Wenn kein Datensatz vorhanden ist wird false zur�ck gegeben, wenn doch true.
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			// Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf
			// false setzen
			System.err.println("Methode checkMa_Schicht SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			// Schlie�en der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkMa_Schicht (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	/**
	 * @author Thomas Friesen
	 * @info Die Methode pr�ft, ob die Foreign-Key-Constraints der Tabelle
	 *       Ma_Schicht eingehalten werden
	 */
	protected boolean checkMa_SchichtFK(int schichtnr, String benutzername, Connection con) {
		boolean result = false;
		Statement[] stmt = new Statement[2];
		ResultSet[] rs = new ResultSet[2];
		String[] sqlQuery = new String[2];
		sqlQuery[0] = "select schichtnr from Schicht where schichtnr = " + schichtnr;
		sqlQuery[1] = "select benutzername from Mitarbeiter where benutzername = '" + benutzername + "'";

		try {
			// Erstellen der Statement- und Resultsetobjekte
			for (int i = 0; i < 2; i++) {
				stmt[i] = con.createStatement();
				rs[i] = stmt[i].executeQuery(sqlQuery[i]);
			}
			// �berpr�fung, ob beide FK-Constrains eingehaltenw werden
			if ((rs[0].next()) == true && (rs[1].next()) == true) {
				result = true;
			} else {
				result = false;
			}

		} catch (SQLException sql) {
			System.err.println("Methode checkMa_SchichtFK SQL-Fehler: " + sql.getMessage());
			return false;

		} finally {
			// Schlie�en der offen gebliebenen Statements und Resultsets
			try {
				for (int i = 0; i < 2; i++) {
					if (rs[i] != null) {
						rs[i].close();
					}
					if (stmt[i] != null) {
						stmt[i].close();
					}

				}
				// Abfangen von Fehlern, beim �berpr�fen der Datenbank
			} catch (SQLException e) {
				System.err.println("Methode checkMa_SchichtFK (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Auslesen der Mitarbeiter mit den zugeh�rigen Schichten aus der
	 *       Datenbank und hinzuf�gen in eine Liste, welche den Ausgabewert
	 *       darstellt . Im Fehlerfall Ausgaben zur Fehlersuche erzeugen und
	 *       null zur�ckgeben.
	 */
	protected LinkedList<Ma_Schicht> getMa_Schicht(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		// Ben�tigten Sql-Befehlt speichern
		String sqlStatement = "SELECT * FROM Ma_Schicht";

		try {

			// Statement wird erstellt, der Sql-Befehl wird ausgef�hrt und im
			// Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			// Setzt den Zeiger vor die erste Position, Zeigerposition ist 0.
			// Vermeiden das der Zeiger an letzer Stelle ist durch mehrfach
			// Befehle
			// --> w�rde das Ergebnis ver�ndern
			rs.beforeFirst();

			LinkedList<Ma_Schicht> ma_schichtList = new LinkedList<>();

			// Solange es einen "n�chsten" Datensatz in dem Resultset gibt, mit
			// den Daten des RS
			// ein neues Ma_Schicht-Objekt erzeugen. Dieses wird anschlie�end
			// der Liste hinzugef�gt.
			while (rs.next()) {

				Ma_Schicht ms = new Ma_Schicht(rs.getString("Benutzername"), rs.getInt("Schichtnr"));

				ma_schichtList.add(ms);
			}

			// Liste mit Ma_Schicht-Objekten zur�ckgeben
			return ma_schichtList;

		} catch (SQLException sql) {
			// Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf
			// null setzen
			System.err.println("Methode getMa_Schicht SQL-Fehler: " + sql.getMessage());
			return null;
		} finally {
			// Schlie�en der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getMa_Schicht (finally) SQL-Fehler: " + e.getMessage());
			}
		}

	}

	/**
	 * @author Anes Preljevic
	 * @info Auslesen der Mitarbeiter die in eine Schicht geh�ren, mit der
	 *       �bergebenen Schichtnr aus der Datenbank. Erstellen eines Objektes
	 *       Ma_Schicht mit den Daten aus der Datenbank. Liste mit den
	 *       Ma_Schicht-Objekten wird ausgegeben.
	 */
	protected LinkedList<Ma_Schicht> getMitarbeiterausderSchicht(int schichtnr, Connection con) {

		Statement stmt = null;
		ResultSet rs = null;
		// Ben�tigten Sql-Befehlt speichern
		String sqlStatement = "SELECT * from Ma_Schicht WHERE schichtnr=" + schichtnr;

		try {
			// Statement wird erstellt, der Sql-Befehl wird ausgef�hrt und im
			// Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);
			// Setzt den Zeiger vor die erste Position, Zeigerposition ist 0.
			// Vermeiden das der Zeiger an letzer Stelle ist durch mehrfach
			// Befehle
			// --> w�rde das Ergebnis ver�ndern
			rs.beforeFirst();
			LinkedList<Ma_Schicht> ma_schichtList = new LinkedList<>();

			// Solange es einen "n�chsten" Datensatz in dem Resultset gibt, mit
			// den Daten des RS
			// ein neues Ma_Schicht-Objekt erzeugen. Dieses wird anschlie�end
			// der Liste hinzugef�gt.
			while (rs.next()) {
				Ma_Schicht ms = new Ma_Schicht(rs.getString("Benutzername"), rs.getInt("Schichtnr"));

				ma_schichtList.add(ms);
			}

			// Liste der Ma_Schicht-Objekte mit "Schichtnr" zur�ckgeben
			return ma_schichtList;

		} catch (SQLException sql) {
			// Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf
			// null setzen
			System.err.println("Methode getMitarbeiterausderSchicht SQL-Fehler: " + sql.getMessage());
			return null;
		} finally {
			// Schlie�en der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getMitarbeiterausderSchicht (finally) SQL-Fehler: " + e.getMessage());
			}
		}

	}

	/**
	 * @author Anes Preljevic
	 * @info Auslesen der Mitarbeiter die in eine Schicht geh�ren, mit der
	 *       �bergebenen Schichtnr aus der Datenbank, erstellen eines Objektes
	 *       Ma_Schicht mit den Daten aus der Datenbank. Liste mit den
	 *       Ma_Schicht-Objekten wird ausgegeben.
	 */
	protected LinkedList<Ma_Schicht> getSchichteneinesMitarbeiters(String benutzername, Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		// Ben�tigten Sql-Befehlt speichern
		String sqlStatement = "SELECT * from Ma_Schicht WHERE benutzername='" + benutzername + "'";

		try {
			// Statement wird erstellt, der Sql-Befehl wird ausgef�hrt und im
			// Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Ma_Schicht> ma_schichtList = new LinkedList<>();
			// Setzt den Zeiger vor die erste Position, Zeigerposition ist 0.
			// Vermeiden das der Zeiger an letzer Stelle ist durch mehrfach
			// Befehle
			// --> w�rde das Ergebnis ver�ndern
			rs.beforeFirst();
			// Solange es einen "n�chsten" Datensatz in dem Resultset gibt, mit
			// den Daten des RS
			// ein neues Ma_Schicht-Objekt erzeugen. Dieses wird anschlie�end
			// der Liste hinzugef�gt.
			while (rs.next()) {
				Ma_Schicht ms = new Ma_Schicht(rs.getString("Benutzername"), rs.getInt("Schichtnr"));

				ma_schichtList.add(ms);
			}

			// Liste der Ma_Schicht-Objekte mit "benutzername" zur�ckgeben
			return ma_schichtList;

		} catch (SQLException sql) {
			// Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf
			// null setzen
			System.err.println("Methode getSchichteneinesMitarbeiters SQL-Fehler: " + sql.getMessage());
			return null;
		} finally {
			// Schlie�en der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getSchichteneinesMitarbeiters (finally) SQL-Fehler: " + e.getMessage());
			}
		}

	}

	/**
	 * @author Anes Preljevic
	 * @info L�schen eines Mitarbeiters aus einer Schicht
	 */
	protected boolean deleteMa_Schicht(int schichtnr, String benutzername, Connection con) {
		// �berpr�fen ob der zu l�schende Datensatz existiert
		// Wenn dieser nicht existiert, trotzdem true zur�ckgeben,
		// da der Datensatz gel�scht werden sollte und der gewollte Zustand
		// vom jetzigen nicht abweicht.
		if (checkMa_Schicht(schichtnr, benutzername, con) == false) {
			return true;
		} else {
			Statement stmt = null;

			// Ben�tigten Sql-Befehlt speichern
			String sqlStatement = "DELETE from Ma_Schicht where schichtnr = " + schichtnr + " and benutzername= '"
					+ benutzername + "'";

			try {
				// Wenn aus der vorherigen Verbindung das AutoCommit noch auf
				// true ist, auf false setzen
				if (con.getAutoCommit() != false)
					;
				{
					con.setAutoCommit(false);
				}

				// Sql-Statement ausf�hren
				stmt = con.createStatement();
				stmt.execute(sqlStatement);

				// Connection Zustand best�tigen und somit fest in die Datenbank
				// schreiben
				con.commit();
				return true;
			} catch (SQLException sql) {
				System.err.println("Methode deleteMa_Schicht SQL-Fehler: " + sql.getMessage());
				try {
					// Zur�cksetzen des Connection Zustandes auf den
					// Ursprungszustand
					con.rollback();
					return false;
				} catch (SQLException sqlRollback) {
					System.err.println(
							"Methode deleteMa_schicht " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
					return false;
				}
			} finally {
				// Schlie�en der offen gebliebenen Statements
				try {

					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					System.err.println("Methode deleteMa_Schicht (finally) SQL-Fehler: " + e.getMessage());
				}
			}
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info L�scht den Ma_Schicht Datensatz aus der Tabelle Ma_Schicht, mit dem
	 *       die �bergebene Schichtnr �bereinstimmt, wenn die Schicht gel�scht
	 *       wurde.
	 */
	protected boolean deleteMa_SchichtWochenplan(int schichtnr, Connection con) {

		Statement stmt = null;

		// Ben�tigten Sql-Befehlt speichern
		String sqlStatement = "DELETE from Ma_Schicht where schichtnr = " + schichtnr;

		try {
			// Wenn aus der vorherigen Verbindung das AutoCommit noch auf true
			// ist, auf false setzen
			if (con.getAutoCommit() != false)
				;
			{
				con.setAutoCommit(false);
			}

			// Sql-Statement ausf�hren
			stmt = con.createStatement();
			stmt.execute(sqlStatement);

			// Connection Zustand best�tigen und somit fest in die Datenbank
			// schreiben
			con.commit();
			return true;
		} catch (SQLException sql) {
			System.err.println("Methode deleteMa_SchichtWochenplan SQL-Fehler: " + sql.getMessage());
			try {
				// Zur�cksetzen des Connection Zustandes auf den
				// Ursprungszustand
				con.rollback();
				return false;
			} catch (SQLException sqlRollback) {
				System.err.println("Methode deleteMa_schichtWochenplan " + "- Rollback -  SQL-Fehler: "
						+ sqlRollback.getMessage());
				return false;
			}
		} finally {
			// Schlie�en der offen gebliebenen Statements
			try {

				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteMa_SchichtWochenplan (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
}
