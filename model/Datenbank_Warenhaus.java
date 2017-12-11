package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Warenhaus;

public class Datenbank_Warenhaus {

	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();


	// Auslesen der Schichten aus der Datenbank und eintragen in eine LinkedList, welche übergeben wird
	public LinkedList<Warenhaus> getWarenhaus() {

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
