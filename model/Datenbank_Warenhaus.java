package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Schicht;
import data.Warenhaus;

/**
 * @author Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und �nderung in der Datenbank
 *       im Bezug auf die Tabelle Warenhaus zu verarbeiten.
 */
class Datenbank_Warenhaus {

	private Einsatzplanmodel myModel = null;

	/**
	 * @author Anes Preljevic
	 * @info Beim erstellen der Hilfsklasse soll das Einsatzplanmodel �bergeben
	 *       werden. Das soll vermeiden, dass die Datenbankverbindung h�ufiger
	 *       erstellt wird, das Einsatzplanmodel unn�tig �fter erstellt wird und
	 *       die Hilfsklassen andere Model-Hilfsklassen �bers Einsatzplanmodel
	 *       nutzen k�nnen, was unn�tigen Code entfernt und die Kopplung
	 *       verringert.
	 */
	protected Datenbank_Warenhaus(Einsatzplanmodel myModel) {
		this.myModel = myModel;
	}

	/**
	 * @author Thomas Friesen
	 * @info Die Methode dient dazu, einen Warenhaus-Datensatz in die Datenbank
	 *       hinzu zuf�gen
	 */
	protected void addWarenhaus(Warenhaus warenhaus, Connection con) {

		String sqlStatement = "insert into Warenhaus (Whname,Anzkasse,Anzinfo) values( ?, ?, ?)";
		PreparedStatement pstmt = null;

		try {
			// Erstellen eines prepared Statement Objekts
			pstmt = con.prepareStatement(sqlStatement);
			// Zuweisen der Parameter aus dem �bergebenen Warenhaus-Objekt
			String whname = warenhaus.getWhname();
			int anzkasse = warenhaus.getAnzkasse();
			int anzinfo = warenhaus.getAnzinfo();

			con.setAutoCommit(false);

			// �berpr�fung der PK-Check-Constrains
			if (checkWarenhaus(whname, con)) {
				System.out.println("Das Warenhaus befindet sich bereits in der Datenbank!");
			} else {
				// Ausf�llen des prepared Statements
				pstmt.setString(1, whname);
				pstmt.setInt(2, anzkasse);
				pstmt.setInt(3, anzinfo);
				// Ausf�hren der SQL-Anweisung
				pstmt.execute();
				// �bertragung der Daten in die Datenbank
				con.commit();
			}

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode addWarenhaus SQL-Fehler: " + sql.getMessage());
			try {
				// F�r den Fall einer SQL-Exception soll der urspr�ngliche
				// Datenbankzustand wiederhergestellt werden
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addWarenhaus " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			// Schlie�en der offen gebliebenen Preparedstatements
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode addWarenhaus(finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info Pr�ft ob es zu der eingegebenen schichtnr und dem benutzernamen
	 *       bereits einen Mitarbeiter in der Schicht gibt, bei existenz return
	 *       true, sonst false
	 */
	public boolean checkWarenhaus(String whname, Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select whname, anzkasse, anzinfo from Ma_Schicht where whname =" + whname;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkWarenhaus SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkWarenhaus (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info Auslesen der Warenh�user aus der Datenbank und erzeugen von
	 *       Warenhaus Objekten. Diese werden in eine LinkedList abgelegt und
	 *       ausgegeben.
	 */
	protected LinkedList<Warenhaus> getWarenhaus(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		// Ben�tigten Sql-Befehlt speichern
		String sqlStatement = "select whname from Warenhaus";

		try {
			// Statement/Resultset wird erstellt, der Sql-Befehl wird ausgef�hrt
			// und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Warenhaus> warenhausList = new LinkedList<>();
			// Setzt den Zeiger vor die erste Position, Zeigerposition ist 0.
			// Vermeiden das der Zeiger an letzer Stelle ist durch mehrfach
			// Befehle
			// --> w�rde das Ergebnis ver�ndern
			rs.beforeFirst();
			// Solange es einen "n�chsten" Datensatz in dem Resultset gibt, mit
			// den Daten des RS
			// ein neues Warenhaus-Objekt erzeugen. Dieses wird anschlie�end der
			// Liste hinzugef�gt.
			while (rs.next()) {
				Warenhaus wh = myModel.geteinWarenhaus(rs.getString("Whname"));
				warenhausList.add(wh);
			}

			// Liste mit Warenhaus-Objekten zur�ckgeben
			return warenhausList;

		} catch (SQLException sql) {
			// Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf
			// null setzen
			System.err.println("Methode getWarenhaus SQL-Fehler: " + sql.getMessage());
			return null;
		} finally {
			// Schlie�en der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getWarenhaus (finally) SQL-Fehler: " + e.getMessage());
			}
		}

	}

	/**
	 * @author Anes Preljevic
	 * @info Auslesen eines bestimmten Warenhauses, erzeugen eines WH-Objektes
	 *       und dieses ausgeben
	 */
	protected Warenhaus geteinWarenhaus(String whname, Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		// Ben�tigten Sql-Befehlt speichern
		String sqlStatement = "select * from Warenhaus where whname='" + whname + "'";

		try {
			// Statement/Resultset wird erstellt, der Sql-Befehl wird ausgef�hrt
			// und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			// Setzt den Zeiger vor die erste Position, Zeigerposition ist 0.
			// Vermeiden das der Zeiger an letzer Stelle ist durch mehrfach
			// Befehle
			// --> w�rde das Ergebnis ver�ndern
			rs.beforeFirst();
			rs.next();
			Warenhaus wh = new Warenhaus(rs.getString("Whname"), rs.getInt("Anzkasse"), rs.getInt("Anzinfo"));
			// Warenhaus-Objektzur�ckgeben
			return wh;

		} catch (SQLException sql) {
			// Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf
			// null setzen
			System.err.println("Methode geteinWarenhaus SQL-Fehler: " + sql.getMessage());
			return null;
		} finally {
			// Schlie�en der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode geteinWarenhaus (finally) SQL-Fehler: " + e.getMessage());
			}
		}

	}

}
