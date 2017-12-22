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

class Datenbank_Standardeinstellungen {


	/**
	 * @author Anes Preljevic
	 * @info Auslesen der Standardeinstellungen aus der Datenbank und erzeugen eines Standardeinstellungen Objektes,
	 * welches ausgegeben wird.
	 */
	protected Standardeinstellungen getStandardeinstellungen(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;
		String sqlStatement = "select �ffnungszeit, Schlie�zeit, Hauptzeitbeginn,"
				+ " Hauptzeitende, Mehrbesetzung,"
				+ "Minanzinfot, Minanzinfow, Minanzkasse from Standardeinstellungen";

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);



				rs.next();
				Standardeinstellungen s = new Standardeinstellungen
						(rs.getTime("�ffnungszeit").toString(),rs.getTime("Schlie�Zeit").toString(),
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
	}

	/**
	 * @author Anes Preljevic
	 * @info �ndern der Standardeinstellungen
	 */
	
	
	protected void updateStandardeinstellungen(Standardeinstellungen standardeinstellungen,Connection con) {

		String  �ffnungszeit  = standardeinstellungen.get�ffnungszeit();
		String  schlie�zeit  = standardeinstellungen.getSchlie�zeit();
		String  hauptzeitbeginn  = standardeinstellungen.getHauptzeitbeginn();
		String  hauptzeitende = standardeinstellungen.getHauptzeitende();
		int mehrbesetzung=standardeinstellungen.getMehrbesetzung();
		int minanzinfot=standardeinstellungen.getMinanzinfot();
		int minanzinfow=standardeinstellungen.getMinanzinfow();
		int minanzkasse=standardeinstellungen.getMinanzkasse();
		String sqlStatement;

		sqlStatement = "UPDATE Standardeinstellungen " + "SET �ffnungszeit = ?"
				+ "SET Schlie�zeit= ?"+ "SET hauptzeitbeginn = ?";
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(sqlStatement);

			con.setAutoCommit(false);
			//pstmt.setBoolean(1, );
			
			pstmt.executeUpdate();
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
