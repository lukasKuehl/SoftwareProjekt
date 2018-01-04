package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Ma_Schicht;
import data.Userrecht;


/**
 * @author Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und Änderung in der Datenbank im Bezug auf die Tabelle Userrecht zu verarbeiten.
 */
class Datenbank_Userrecht {
	
	/**
	 * @author Anes Preljevic
	 * @info Auslesen der Userrecht aus der Datenbank und hinzufügen in eine Liste, welche den Ausgabewert darstellt 
	 */
	public Userrecht getUserrecht(String job ,Connection con) {

		Statement stmt = null;
		ResultSet rs = null;
		//Benötigten Sql-Befehlt speichern
		String sqlStatement = "SELECT Job,Benutzerrolle  FROM Userrecht where job ='"+job+"'";

		try {
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);
			//Auch wenn es voraussichtlich nur einen Datensatz gibt, den nächsten Datensatz abrufen,
			//um 100% sicherheit zu haben
			rs.next();

			Userrecht u = new Userrecht(rs.getString("Job"),rs.getString("Benutzerrolle"));


			//USerrecht-Objekt zurückgeben
			return u;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
			System.err.println("Methode getUserrecht SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schließen der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("MethodegetUserrecht Finally) SQL-Fehler: " + e.getMessage());
			}
		}
		
	}
	}

