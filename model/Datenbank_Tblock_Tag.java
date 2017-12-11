package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Tblock_Tag;

public class Datenbank_Tblock_Tag {

	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();



	/**
	 * @Anes Preljevic
	 * @info 
	 */
	public void addTblock_Tag(Tblock_Tag tblock_tag) {

		String sqlStatement;
		sqlStatement = "insert into Tblock_Tag (tblocknr,tbez, wpnr) values(?, ?, ?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(sqlStatement);

			int tblocknr = tblock_tag.getTblocknr();
			String tbez = tblock_tag.getTbez();;
			int wpnr = tblock_tag.getWpnr();
		
			
			con.setAutoCommit(false);

			if (checkTblock_Tag(tblocknr, tbez, wpnr)) {
				deleteTblock_Tag(tblocknr, tbez, wpnr);
				addTblock_Tag(tblock_tag);
			} else {
				pstmt.setInt(1, tblocknr);
				pstmt.setString(2, tbez);
				pstmt.setInt(3, wpnr);
			
				pstmt.execute();
				con.commit();
			}

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode addtblocktag SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addtblocktag " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode addtblocktag(finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	// Schicht vorhanden?
	public boolean checkTblock_Tag(int tblocknr, String tbez, int wpnr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select tblocknr, tbez from Tblock_Tag where tblocknr = '"+tblocknr+"' "
				+ "and tbez= '"+tbez+"'and wpnr '"+wpnr+"'";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkTblock_TagSQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkTblock_Tag (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}



	// Auslesen der Schichten aus der Datenbank und eintragen in eine LinkedList, welche übergeben wird
	public LinkedList<Tblock_Tag> getTblock_Tag() {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Tblocknr, Tbez, Wpnr from Tblock_Tag";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Tblock_Tag> tblock_tagList = new LinkedList<>();

			while (rs.next()) {
				Tblock_Tag tbt = new Tblock_Tag(0, sqlStatement,0);

				tbt.setTblocknr(rs.getInt("Tblocknr"));
				tbt.setTbez(rs.getString("Tbez"));
				tbt.setWpnr(rs.getInt("Wpnr"));
				
				tblock_tagList.add(tbt);
			}

			rs.close();
			stmt.close();

			return tblock_tagList;

		} catch (SQLException sql) {
			return null;
		}
	}

	// Mitarbeiter aus der Schicht löschen, Datensatz aus MA_Schicht entfernen
	public boolean deleteTblock_Tag(int tblocknr, String tbez, int wpnr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM Tblock_Tag WHERE Tblocknr = "+tblocknr+""
				+ " and Tbez= "+tbez+"and Wpnr= "+wpnr+"";

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
				System.err.println("Methode deleteTblock_Tag (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
}
