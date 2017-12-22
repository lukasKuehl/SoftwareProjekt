package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Schicht;
import data.Warenhaus;

class Datenbank_Warenhaus {



	protected void addWarenhaus(Warenhaus warenhaus, Connection con) {

		String sqlStatement;
		sqlStatement = "insert into Warenhaus (Whname,Anzkasse,Anzinfo) values( ?, ?, ?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(sqlStatement);

			String whname = warenhaus.getWhname();
			int anzkasse = warenhaus.getAnzkasse();
			int anzinfo = warenhaus.getAnzinfo();
			
			con.setAutoCommit(false);

		
				pstmt.setString(1, whname);
				pstmt.setInt(2,anzkasse);
				pstmt.setInt(3, anzinfo);
				pstmt.execute();
				con.commit();
			

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
	 * @info Prüft ob es zu der eingegebenen schichtnr und dem benutzernamen bereits einen Mitarbeiter in der Schicht gibt, bei existenz return true, sonst false
	 */
	public boolean checkWarenhaus(String whname,Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select whname, anzkasse, anzinfo from Ma_Schicht where whname ="+whname;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkWarenhaus SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkWarenhaus (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info Auslesen der Warenhäuser aus der Datenbank und erzeugen von Warenhaus Objekten.
	 * Diese werden in eine LinkedList abgelegt und ausgegeben.
	 */
	protected LinkedList<Warenhaus> getWarenhaus(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Whname, anzkasse, anzinfo from Warenhaus";

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Warenhaus> warenhausList = new LinkedList<>();

			while (rs.next()) {
				Warenhaus wh = new Warenhaus(rs.getString("Whname"),rs.getInt("Anzkasse"), rs.getInt("Anzinfo"));

				warenhausList.add(wh);
			}

			rs.close();
			stmt.close();

			return warenhausList;

		} catch (SQLException sql) {
			System.err.println("Methode getWarenhaus SQL-Fehler: " + sql.getMessage());
			return null;
		}
	}


}
