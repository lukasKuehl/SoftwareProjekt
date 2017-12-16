package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Ma_Schicht;
import data.Schicht;
import data.Tag;

class Datenbank_Schicht {

	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();




	// Schichten in Tabelle Schicht hinzufg
	protected void addSchicht(Schicht schicht) {

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

	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen Schichtnr eine Schicht gibt,
	 * bei existenz return true sonst false
	 */
	
	protected boolean checkSchicht(int schichtnr) {
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


	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller Schichten aus der Datenbank und erzeugen von Schicht Objekten.
	 * Diese werden in eine LinkedList abgelegt und ausgegeben.
	 */
	protected LinkedList<Schicht> getSchichten() {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Schichtnr from Schicht";

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
	
	/**
	 * @author Anes Preljevic
	 * @info Auslesen einer bestimmten Schicht aus der Datenbank und erzeugen eines Schicht Objektes,
	 * welches anschließend ausgegeben wird.
	 */
	protected Schicht getSchicht(int schichtnr) {
		if (!checkSchicht(schichtnr)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Schichtnr from Schicht where schichtnr="+schichtnr;

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			

			
				Schicht s = new Schicht(0, sqlStatement, 0);

				s.setSchichtnr(rs.getInt("Schichtnr"));
				s.setTbez(rs.getString("Tbez"));
				s.setWpnr(rs.getInt("Wpnr"));
				

			rs.close();
			stmt.close();

			return s;

		} catch (SQLException sql) {
			return null;
		}
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info Löschen einer Schicht mit zugehörigen Ma_Schicht (Mitarbeitern in Schichten)aus den Datenbank Tabellen 
	 * Schicht, Ma-Schicht.
	 */
	protected boolean deleteSchicht(int wpnr) {
		Datenbank_Schicht schicht = new Datenbank_Schicht();
		LinkedList<Schicht> schichtList = schicht.getSchichten();
		Datenbank_Ma_Schicht masch = new Datenbank_Ma_Schicht();
		LinkedList<Ma_Schicht> maschichtList = masch.getMa_Schicht();;

		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM Schicht WHERE wpnr= "+wpnr;
		for (Schicht sch : schichtList) {
			if (sch.getWpnr() == wpnr) {

		
		for (Ma_Schicht ms : maschichtList) {
			if (ms.getSchichtnr() == sch.getSchichtnr()) {
				masch.deleteMa_SchichtWochenplan(sch.getSchichtnr());
			}
			}
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
				System.err.println("Methode deleteSchicht (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Fragt die höchste Schichtnr und erhöht diese um 1, sodass bei neu Erstellung
	 * einer Schicht die nächste Schichtnr vorliegt.
	 */
	protected  int getNewSchichtnr() {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select max (schichtnr)+1 from Schicht";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs.next();
			int maxSchichtnr = rs.getInt(1);
			rs.close();
			stmt.close();
			return maxSchichtnr;
		} catch (SQLException sql) {
			System.err.println("Methode getNewSchichtnrSQL-Fehler: "
					+ sql.getMessage());
			return -1;
		}
	}
}
