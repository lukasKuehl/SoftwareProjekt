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
 * @info Die Klasse dient dazu, jegliche Abfragen und Änderung in der Datenbank im Bezug auf die Tabelle Warenhaus zu verarbeiten.
 */
class Datenbank_Warenhaus {

	
	//Methodenbeschreibung fehlt!
	protected void addWarenhaus(Warenhaus warenhaus, Connection con) {

		//Eine Anweisung sinnvoller
		String sqlStatement;
		sqlStatement = "insert into Warenhaus (Whname,Anzkasse,Anzinfo) values( ?, ?, ?)";
		
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(sqlStatement);

			String whname = warenhaus.getWhname();
			int anzkasse = warenhaus.getAnzkasse();
			int anzinfo = warenhaus.getAnzinfo();

			//Siehe vorherige Klassen!
			con.setAutoCommit(false);
		
			//Abfrage mit CheckWarenhaus fehlt!
			
			
				pstmt.setString(1, whname);
				pstmt.setInt(2,anzkasse);
				pstmt.setInt(3, anzinfo);
				pstmt.execute();
				con.commit();
			

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode addSchicht SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addSchicht " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schließen der offen gebliebenen Preparedstatements
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode addSchicht(finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen schichtnr und dem benutzernamen bereits einen Mitarbeiter in der Schicht gibt, bei existenz return true, sonst false
	 */
	public boolean checkWarenhaus(String whname,Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select whname, anzkasse, anzinfo from Ma_Schicht where whname ="+whname;

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
	 * @info Auslesen der Warenhäuser aus der Datenbank und erzeugen von Warenhaus Objekten.
	 * Diese werden in eine LinkedList abgelegt und ausgegeben.
	 */
	protected LinkedList<Warenhaus> getWarenhaus(Connection con) {
		Datenbank_Warenhaus wha=new Datenbank_Warenhaus();
		Statement stmt = null;
		ResultSet rs = null;

		//Benötigten Sql-Befehlt speichern
		String sqlStatement = "select whname from Warenhaus";

		try {
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			
			LinkedList<Warenhaus> warenhausList = new LinkedList<>();
			// Solange es einen "nächsten" Datensatz in dem Resultset gibt, mit den Daten des RS 
			// ein neues Warenhaus-Objekt erzeugen. Dieses wird anschließend der Liste hinzugefügt.
			while (rs.next()) {
				Warenhaus wh = wha.geteinWarenhaus(rs.getString("Whname"),con);
				warenhausList.add(wh);
			}

		
			//Liste mit Warenhaus-Objekten zurückgeben
			return warenhausList;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
			System.err.println("Methode getWarenhaus SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schließen der offen gebliebenen Resultsets und Statements
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
	 * @info Auslesen eines bestimmten Warenhauses, erzeugen eines WH-Objektes und dieses ausgeben
	 */
	protected Warenhaus geteinWarenhaus(String whname, Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		//Benötigten Sql-Befehlt speichern
		String sqlStatement = "select * from Warenhaus";

		try {
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			
			rs.next();
				Warenhaus wh = new Warenhaus(rs.getString("Whname"),rs.getInt("Anzkasse"), rs.getInt("Anzinfo"));
			//Warenhaus-Objektzurückgeben
			return wh;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
			System.err.println("Methode geteinWarenhaus SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schließen der offen gebliebenen Resultsets und Statements
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
