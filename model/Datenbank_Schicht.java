package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Schicht;

public class Datenbank_Schicht {

	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();


	// Schichten in Tabelle Schicht hinzufg
	public void addSchicht(Schicht schicht) {

		String sqlStatement;
		sqlStatement = "insert into Schicht (Schichtnr,Tbez,Wpnr) values( ?, ?, ?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(sqlStatement);

			int schichtnr = schicht.getSchichtnr();
			String tbez = schicht.getTbez();
			int wpnr = schicht.getWpnr();
			
			con.setAutoCommit(false);

			if (checkSchicht(schichtnr)) {
				deleteSchicht(schichtnr);
				addSchicht(schicht);
			} else {
				pstmt.setInt(1, schichtnr);
				pstmt.setString(5,tbez);
				pstmt.setInt(6, wpnr);
				pstmt.execute();
				con.commit();
			}

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
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode addSchicht(finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	// Schicht vorhanden?
	public boolean checkSchicht(int schichtnr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select schichtnr from Schicht where schichtnr = " + schichtnr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkSchicht SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkSchicht (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}



	// Auslesen der Schichten aus der Datenbank und eintragen in eine LinkedList, welche übergeben wird
	public LinkedList<Schicht> getSchicht() {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Schichtnr from Schicht";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Schicht> schichtList = new LinkedList<>();

			while (rs.next()) {
				Schicht s = new Schicht(0, sqlStatement, 0);

				s.setSchichtnr(rs.getInt("Schichtnr"));
				s.setTbez(rs.getString("Tbez"));
				s.setWpnr(rs.getInt("Wpnr"));
				
				schichtList.add(s);
			}

			rs.close();
			stmt.close();

			return schichtList;

		} catch (SQLException sql) {
			return null;
		}
	}
	public LinkedList<Schicht> getdieSchicht(int schichtnr) {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Schichtnr from Schicht where schichtnr="+schichtnr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Schicht> schichtList = new LinkedList<>();

			
				Schicht s = new Schicht(0, sqlStatement, 0);

				s.setSchichtnr(rs.getInt("Schichtnr"));
				s.setTbez(rs.getString("Tbez"));
				s.setWpnr(rs.getInt("Wpnr"));
				
				schichtList.add(s);
			

			rs.close();
			stmt.close();

			return schichtList;

		} catch (SQLException sql) {
			return null;
		}
	}

	// Schicht aus einem Tag löschen/wp
	public boolean deleteSchicht(int schichtnr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM Schicht WHERE Schichtnr= "+schichtnr;

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
				System.err.println("Methode deleteSchicht (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
}
