package model;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.JFileChooser;



import data.Standardeinstellungen;

/**
 * @author Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und Änderung in der Datenbank im Bezug auf die Tabelle Standardeinstellungen zu verarbeiten.
 */

//Finally-Block bei getStandardeinstellungen fehlt!

//Kommentare innerhalb der Methoden fehlen!

//Klassenname Einstellungen wäre sinnvoller, die kann man ja vielleicht später mal ändern. Die Methode Eistellungen getStandardeinstellungen() ist ja aussagekräftig genug.
class Datenbank_Standardeinstellungen {


	/**
	 * @author Anes Preljevic
	 * @info Auslesen der Standardeinstellungen aus der Datenbank und erzeugen eines Standardeinstellungen Objektes,
	 * welches ausgegeben wird.
	 */
	protected Standardeinstellungen getStandardeinstellungen(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;
		//siehe vorherige Klassen!
		String sqlStatement = "select Öffnungszeit, Schließzeit, Hauptzeitbeginn,"
				+ " Hauptzeitende, Mehrbesetzung,"
				+ "Minanzinfot, Minanzinfow, Minanzkasse from Standardeinstellung";

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);



				rs.next();
				Standardeinstellungen s = new Standardeinstellungen
						(rs.getTime("Öffnungszeit").toString(),rs.getTime("SchließZeit").toString(),
						rs.getTime("Hauptzeitbeginn").toString(),rs.getTime("Hauptzeitende").toString(),
						rs.getInt("Mehrbesetzung"),rs.getInt("Minanzinfot"),
						rs.getInt("Minanzinfow"),rs.getInt("Minanzkasse"));
	
				

			rs.close();
			stmt.close();

			return s;

		} catch (SQLException sql) {
			System.err.println("Methode getStandardeinstellungen SQL-Fehler: " + sql.getMessage());
			return null;
		}
		//finally-Block fehlt!
	}

	/**
	 * @author Anes Preljevic
	 * @info Ändern der Standardeinstellungen
	 */
	
	
	protected void updateStandardeinstellungen(Standardeinstellungen standardeinstellungen,Connection con) {

		String  öffnungszeit  = standardeinstellungen.getÖffnungszeit();
		String  schließzeit  = standardeinstellungen.getSchließzeit();
		String  hauptzeitbeginn  = standardeinstellungen.getHauptzeitbeginn();
		String  hauptzeitende = standardeinstellungen.getHauptzeitende();
		int mehrbesetzung=standardeinstellungen.getMehrbesetzung();
		int minanzinfot=standardeinstellungen.getMinanzinfot();
		int minanzinfow=standardeinstellungen.getMinanzinfow();
		int minanzkasse=standardeinstellungen.getMinanzkasse();
		String sqlStatement;

		sqlStatement = "UPDATE Standardeinstellungen " + "SET Öffnungszeit = ?"
				+ "SET Schließzeit= ?"+ "SET hauptzeitbeginn = ?"+ "SET hauptzeitende = ?"
						+ "SET mehrbsetzung = ?"+ "SET minanzinfot = ?"+ "SET minanzinfow = ?"+ "SET minanzkasse = ?";
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(sqlStatement);

			//siehe vorherige Klassen
			con.setAutoCommit(false);
			
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

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode updateStandardeinstellungen SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode updateStandardeinstellungen " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode updateStandardeinstellungen (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	
}
