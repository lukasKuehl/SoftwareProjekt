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

	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();
	private Einsatzplanmodel myModel = null;

	protected Datenbank_Warenhaus(Einsatzplanmodel mymodel) {
	this.myModel = myModel;
	}


	protected void addWarenhaus(Warenhaus warenhaus) {

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
	// Auslesen der Schichten aus der Datenbank und eintragen in eine LinkedList, welche übergeben wird
	protected LinkedList<Warenhaus> getWarenhaus() {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Whname, anzkasse, anzinfo from Warenhaus";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Warenhaus> warenhausList = new LinkedList<>();

			while (rs.next()) {
				Warenhaus wh = new Warenhaus(sqlStatement, 0, 0);

				wh.setWhname(rs.getString("Whname"));
				wh.setAnzkasse(rs.getInt("Anzkasse"));
				wh.setAnzinfo(rs.getInt("Anzinfo"));
				
				warenhausList.add(wh);
			}

			rs.close();
			stmt.close();

			return warenhausList;

		} catch (SQLException sql) {
			return null;
		}
	}


}
