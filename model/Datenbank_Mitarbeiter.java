package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Mitarbeiter;

class Datenbank_Mitarbeiter {

	Datenbank_Connection db_con = new  Datenbank_Connection();
	Connection con = db_con.getCon();
		private Einsatzplanmodel myModel = null;

		protected Datenbank_Mitarbeiter(Einsatzplanmodel mymodel) {
		this.myModel = myModel;
		}



	// Auslesen der Schichten aus der Datenbank und eintragen in eine LinkedList, welche übergeben wird
	protected LinkedList<Mitarbeiter> getMitarbeiter() {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Benutzername, Passwort, Job, Vorname, Name, Maxstunden, Whname  from Mitarbeiter";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Mitarbeiter> mitarbeiterList = new LinkedList<>();

			while (rs.next()) {
				Mitarbeiter m = new Mitarbeiter(sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement, 0, sqlStatement);

				m.setBenutzername(rs.getString("Benutzername"));
				m.setPasswort(rs.getString("Passwort"));
				m.setJob(rs.getString("Job"));
				m.setVorname(rs.getString("Vorname"));
				m.setName(rs.getString("Name"));
				m.setMaxstunden(rs.getInt("Maxstunden"));
				m.setWhname(rs.getString("Whname"));
				mitarbeiterList.add(m);
			}

			rs.close();
			stmt.close();

			return mitarbeiterList;

		} catch (SQLException sql) {
			return null;
		}
	}

	public Mitarbeiter getMitarbeiter(String Benutzername) {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Benutzername, Passwort, Job, Vorname, Name, Maxstunden, Whname  from Mitarbeiter";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			

			
				Mitarbeiter m = new Mitarbeiter(sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement, 0, sqlStatement);

				m.setBenutzername(rs.getString("Benutzername"));
				m.setPasswort(rs.getString("Passwort"));
				m.setJob(rs.getString("Job"));
				m.setVorname(rs.getString("Vorname"));
				m.setName(rs.getString("Name"));
				m.setMaxstunden(rs.getInt("Maxstunden"));
				m.setWhname(rs.getString("Whname"));
				
			

			rs.close();
			stmt.close();

			return m;

		} catch (SQLException sql) {
			return null;
		}
	}

}