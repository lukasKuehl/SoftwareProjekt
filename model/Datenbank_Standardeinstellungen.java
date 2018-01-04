package model;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.JFileChooser;



import data.Standardeinstellungen;

/**
 * @author Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und Änderung in der Datenbank im Bezug auf die Tabelle Standardeinstellungen zu verarbeiten.
 */

//Klassenname Einstellungen 
class Datenbank_Standardeinstellungen {


	/**
	 * @author Anes Preljevic
	 * @info Auslesen der Standardeinstellungen aus der Datenbank und erzeugen eines Standardeinstellungen Objektes,
	 * welches ausgegeben wird.
	 */
	protected Standardeinstellungen getStandardeinstellungen(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;
		//Benötigten Sql-Befehlt speichern
		String sqlStatement = "select * from Standardeinstellung";

		try {
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);



			//Datumsformat auf 0-23 und stunden:minuten setzen
			DateFormat df = new SimpleDateFormat("HH:mm");
			//Auch wenn es voraussichtlich nur einen Datensatz gibt, den nächsten Datensatz abrufen,
			//um 100% sicherheit zu haben
			rs.next();
				//Das Uhrzeit Forman von hh:mm:ss.00 auf hh:mm ändern
				Time dbHauptzeitbeginn = rs.getTime("Hauptzeitbeginn");
				Time dbHauptzeitende = rs.getTime("Hauptzeitende");			
				String hauptzeitbeginn = df.format(dbHauptzeitbeginn);
				String hauptzeitende =df.format(dbHauptzeitende);
				Time dbÖffnungszeit = rs.getTime("Öffnungszeit");
				Time dbSchließzeit = rs.getTime("Schließzeit");			
				String öffnungszeit = df.format(dbÖffnungszeit);
				String schließzeit =df.format(dbSchließzeit);
				
				Standardeinstellungen s = new Standardeinstellungen
						(öffnungszeit,schließzeit,hauptzeitbeginn,hauptzeitende,rs.getInt("Mehrbesetzung"),rs.getInt("Minanzinfot"),
						rs.getInt("Minanzinfow"),rs.getInt("Minanzkasse"));
	
				//Standardeinstellungen-Objekt zurückgeben
			return s;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
			System.err.println("Methode getStandardeinstellungen SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schließen der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getStandardeinstellungen (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	
	}

	/**
	 * @author Anes Preljevic
	 * @info Ändern der Standardeinstellungen
	 */
	
	
	protected void updateStandardeinstellungen(Standardeinstellungen standardeinstellungen,Connection con) {
		//Daten aus dem Standardeinstellungen-Objekt rauslesen
		String  öffnungszeit  = standardeinstellungen.getÖffnungszeit();
		String  schließzeit  = standardeinstellungen.getSchließzeit();
		String  hauptzeitbeginn  = standardeinstellungen.getHauptzeitbeginn();
		String  hauptzeitende = standardeinstellungen.getHauptzeitende();
		int mehrbesetzung=standardeinstellungen.getMehrbesetzung();
		int minanzinfot=standardeinstellungen.getMinanzinfot();
		int minanzinfow=standardeinstellungen.getMinanzinfow();
		int minanzkasse=standardeinstellungen.getMinanzkasse();
		String sqlStatement;
		//Benötigten Sql-Befehlt speichern
		sqlStatement = "UPDATE Standardeinstellungen " + "SET Öffnungszeit = ?"
				+ "SET Schließzeit= ?"+ "SET hauptzeitbeginn = ?"+ "SET hauptzeitende = ?"
						+ "SET mehrbsetzung = ?"+ "SET minanzinfot = ?"+ "SET minanzinfow = ?"+ "SET minanzkasse = ?";
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(sqlStatement);

			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
			//Preparedstatements füllen
			pstmt.setString(1, öffnungszeit);
			pstmt.setString(2,schließzeit );
			pstmt.setString(3, hauptzeitbeginn);
			pstmt.setString(4,hauptzeitende );
			pstmt.setInt(5,mehrbesetzung );
			pstmt.setInt(6,minanzinfot );
			pstmt.setInt(7, minanzinfow);
			pstmt.setInt(8,minanzkasse );
			
			
			pstmt.execute();
			con.commit();


		} catch (SQLException sql) {
			System.err.println("Methode updateStandardeinstellungen SQL-Fehler: " + sql.getMessage());
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				
			} catch (SQLException sqlRollback) {
				System.err.println("Methode updateStandardeinstellungen " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				//Schließen von offen gebliebenen Preparedstatements
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode updateStandardeinstellungen (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	
}
