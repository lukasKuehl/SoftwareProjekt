package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Tag;
import data.TerminBlockierung;
import data.Tblock_Tag;

class Datenbank_Tblock_Tag {

	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();


	/**
	 * @Anes Preljevic
	 * @info 
	 */
	protected void addTblock_Tag(Tblock_Tag tblock_tag) {

		String sqlStatement;
		sqlStatement = "insert into Tblock_Tag (tblocknr,tbez, wpnr) values(?, ?, ?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(sqlStatement);

			int tblocknr = tblock_tag.getTblocknr();
			String tbez = tblock_tag.getTbez();;
			int wpnr = tblock_tag.getWpnr();
		
			
			con.setAutoCommit(false);

				pstmt.setInt(1, tblocknr);
				pstmt.setString(2, tbez);
				pstmt.setInt(3, wpnr);
			
				pstmt.execute();
				con.commit();
			

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
			}}
		}
	

	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen Tblocknr eine Beziehung von Blockierungen zu Tagen  gibt,
	 * bei existenz return true sonst false
	 */
	protected boolean checkTblock_TagTB(int tblocknr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select tblocknr from Tblock_TagTB where tblocknr = "+tblocknr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkTblock_TagTB SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkTblock_TagTB (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen Tagbez und der Wochenplannr eine Beziehung von Tagen zu Blockierungen  gibt,
	 * bei existenz return true sonst false
	 */
	protected boolean checkTblock_TagTA(String tbez, int wpnr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select tbez, wpnr from Tblock_TagTA where tbez= '"+tbez+"'and wpnr '"+wpnr+"'";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkTblock_TagTA SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkTblock_TagTA (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}


	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller Beziehungen von TerminBlockierungen zu Tagen  aus der Datenbank und erzeugen von Tblock_Tag Objekten.
	 * Diese und eine Liste mit zugehörigen TerminBlockierungen werden in eine LinkedList abgelegt und ausgegeben.
	 */
	protected LinkedList<Tblock_Tag> getAlleTblock_Tag() {
		Datenbank_TerminBlockierung terminblockierung = new Datenbank_TerminBlockierung();
		LinkedList<TerminBlockierung> terminList = terminblockierung.getTerminBlockierungen();;
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
				for (TerminBlockierung tb : terminList) {
					if (tb.getTblocknr() == tbt.getTblocknr()) {
						tbt.setLinkedList_termine(tb);
					}
					}
				tblock_tagList.add(tbt);
			}

			rs.close();
			stmt.close();

			return tblock_tagList;

		} catch (SQLException sql) {
			return null;
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Auslesen eines bestimmter bestimmten Termin zu Tag Beziehung aus der Datenbank und erzeugen eines Tblock_Tag Objektes,
	 * welches anschließend ausgegeben wird mit einer Liste zugehöriger TerminBlockierungen.
	 */
	protected Tblock_Tag getTblock_Tag(int tblocknr) {
		Datenbank_TerminBlockierung terminblockierung = new Datenbank_TerminBlockierung();
		LinkedList<TerminBlockierung> terminList = terminblockierung.getTerminBlockierungen();;
		if (!checkTblock_TagTB(tblocknr)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Tblocknr, Tbez, Wpnr from Tblock_Tag where tblocknr"+tblocknr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

				Tblock_Tag tbt = new Tblock_Tag(0, sqlStatement,0);

				tbt.setTblocknr(rs.getInt("Tblocknr"));
				tbt.setTbez(rs.getString("Tbez"));
				tbt.setWpnr(rs.getInt("Wpnr"));
				for (TerminBlockierung tb : terminList) {
					if (tb.getTblocknr() == tbt.getTblocknr()) {
						tbt.setLinkedList_termine(tb);
					}
					}
			rs.close();
			stmt.close();

			return tbt;

		} catch (SQLException sql) {
			return null;
		}
		}
	}
	protected Tblock_Tag getTblock_Tag(String tbez, int wpnr) {
		Datenbank_TerminBlockierung terminblockierung = new Datenbank_TerminBlockierung();
		LinkedList<TerminBlockierung> terminList = terminblockierung.getTerminBlockierungen();;
		if (!checkTblock_TagTA(tbez, wpnr)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Tblocknr, Tbez, Wpnr from Tblock_Tag where tbez='"+tbez+"'and wpnr '"+wpnr+"'";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

				Tblock_Tag tbt = new Tblock_Tag(0, sqlStatement,0);

				tbt.setTblocknr(rs.getInt("Tblocknr"));
				tbt.setTbez(rs.getString("Tbez"));
				tbt.setWpnr(rs.getInt("Wpnr"));
				for (TerminBlockierung tb : terminList) {
					if (tb.getTblocknr() == tbt.getTblocknr()) {
						tbt.setLinkedList_termine(tb);
					}
					}
			rs.close();
			stmt.close();

			return tbt;

		} catch (SQLException sql) {
			return null;
		}
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info Löscht die Blockierung eines Tages welche zu der Tblocknr gehört. Der Datensatz 
	 * wird aus der Tabelle Tblock_Tag gelöscht. Löscht die zugehörigen TerminBlockierungen.
	 */
	protected boolean deleteTblock_Tag(int tblocknr) {
		Datenbank_TerminBlockierung terminblockierung = new Datenbank_TerminBlockierung();
		LinkedList<TerminBlockierung> terminList = terminblockierung.getTerminBlockierungen();;
		if (!checkTblock_TagTB(tblocknr)){
			return false;
		}
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM Tblock_Tag WHERE Tblocknr = "+tblocknr;
		for (TerminBlockierung tb : terminList) {
			if (tb.getTblocknr() == tblocknr) {
				terminblockierung.deleteTerminBlockierung(tblocknr);
			}
			}
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
