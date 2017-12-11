package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Ma_Schicht;

public class Datenbank_Ma_Schicht {

	Datenbank_Connection db_con = new  Datenbank_Connection();
	
	
	
	
	Connection con = db_con.getCon();



	/**
	 * @Anes Preljevic
	 * @info 
	 */
	public void addMa_Schicht(Ma_Schicht ma_schicht) {

		String sqlStatement;
		sqlStatement = "insert into Ma_Schicht (Schichtnr,Benutzername) values(?, ?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(sqlStatement);

			int schichtnr = ma_schicht.getSchichtnr();
			String benutzername = ma_schicht.getBenutzername();;
		
			
			con.setAutoCommit(false);

			if (checkMa_Schicht(schichtnr, benutzername)) {
				deleteMa_Schicht(schichtnr,benutzername);
				addMa_Schicht(ma_schicht);
			} else {
				pstmt.setInt(1, schichtnr);
				pstmt.setString(2, benutzername);
				
			
				pstmt.execute();
				con.commit();
			}

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode addMa_Schicht SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addMa_Schicht " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode addMa_Schicht(finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	// Schicht vorhanden?
	public boolean checkMa_Schicht(int schichtnr, String benutzername) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select schichtnr, benutzernaeme from Ma_Schicht where schichtnr = '"+schichtnr+"' and benutzername= '"+benutzername+"'";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkMa_Schicht SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
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



	// Auslesen der Schichten aus der Datenbank und eintragen in eine LinkedList, welche übergeben wird
	public LinkedList<Ma_Schicht> getMa_Schicht() {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Schichtnr, Benutzername from Ma_Schicht";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Ma_Schicht> ma_schichtList = new LinkedList<>();

			while (rs.next()) {
				Ma_Schicht ms = new Ma_Schicht(sqlStatement,0);

				ms.setSchichtnr(rs.getInt("Schichtnr"));
				ms.setBenutzername(rs.getString("Benutzername"));
				
				
				ma_schichtList.add(ms);
			}

			rs.close();
			stmt.close();

			return ma_schichtList;

		} catch (SQLException sql) {
			return null;
		}
	}

	// Mitarbeiter aus der Schicht löschen, Datensatz aus MA_Schicht entfernen
	public boolean deleteMa_Schicht(int schichtnr, String benutzername) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM Ma_Schicht WHERE Schichtnr = "+schichtnr+" and Benutzername= "+benutzername+"";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return true;
		} catch (SQLException sql) {
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteMa_Schicht (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
}
