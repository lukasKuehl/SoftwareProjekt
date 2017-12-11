package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.TerminBlockierung;

public class Datenbank_TerminBlockierung {

	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();





	// Schichten in Tabelle Schicht hinzufg
	public void addTerminBlockierung(TerminBlockierung terminBlockierung) {

		String sqlStatement;
		sqlStatement = "insert into TerminBlockierung (Tblocknr, Benutzername, Bbez, Anfzeitraum, Endzeitraum,Anfanguhrzeit, Endeuhrzeit, Grund) values(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(sqlStatement);

			int tblocknr=terminBlockierung.getTblocknr();
			String  benutzername=terminBlockierung.getBenutzername();
			String bbez=terminBlockierung.getBbez();
			String anfzeitraum=terminBlockierung.getAnfzeitraum();
			String endzeitraum=terminBlockierung.getEndzeitraum();
			String anfanguhrzeit=terminBlockierung.getAnfanguhrzeit();
			String endeuhrzeit =terminBlockierung.getEndeuhrzeit();
			String  grund = terminBlockierung.getGrund();
			
			con.setAutoCommit(false);

			if (checkTerminBlockierung(tblocknr)) {
				deleteTerminBlockierung(tblocknr);
				addTerminBlockierung(terminBlockierung);
			} else {
				pstmt.setInt(1, tblocknr);
				pstmt.setString(2, benutzername);
				pstmt.setString(3, bbez);
				pstmt.setString(4, anfzeitraum);
				pstmt.setString(5,endzeitraum);
				pstmt.setString(6, anfanguhrzeit);
				pstmt.setString(7, endeuhrzeit);
				pstmt.setString(8, grund);
				pstmt.execute();
				con.commit();
			}

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode addTerminBLockierung SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTerminBlokierung" + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode addTerminBlockierung(finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	// Schicht vorhanden?
	public boolean checkTerminBlockierung(int tblocknr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select tblocknr from TerminBlockierung where tblocknr = " + tblocknr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkTerminBlockierung SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkTerminBlockierung (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}



	// Auslesen der Schichten aus der Datenbank und eintragen in eine LinkedList, welche übergeben wird
	public LinkedList<TerminBlockierung> getTerminBlockierung() {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Tblocknr, Benutzername, Bbez, Anfzeitraum, Endzeitraum,Anfanguhrzeit, Endeuhrzeit, Grund from TerminBlockierung";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<TerminBlockierung> terminBlockierungList = new LinkedList<>();

			while (rs.next()) {
				TerminBlockierung tb = new TerminBlockierung(0, sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement);

				tb.setTblocknr(rs.getInt("Tauschnr"));
				tb.setBenutzername(rs.getString("Benutzername"));
				tb.setBbez(rs.getString("Bbez"));
				tb.setAnfzeitraum(rs.getString("Anfzeitraum"));
				tb.setEndzeitraum(rs.getString("Endzeitraum"));
				tb.setAnfanguhrzeit(rs.getString("Anfanguhrzeit"));
				tb.setEndeuhrzeit(rs.getString("Endeuhrzeit"));
				tb.setGrund(rs.getString("Grund"));
				
				terminBlockierungList.add(tb);
			}

			rs.close();
			stmt.close();

			return terminBlockierungList;

		} catch (SQLException sql) {
			return null;
		}
	}

	// Schicht aus einem Tag löschen/wp
	public boolean deleteTerminBlockierung(int tblocknr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM TerminBlockierung WHERE Tblocknr = "+tblocknr;

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
				System.err.println("Methode deleteTerminBlockierung(finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
}
