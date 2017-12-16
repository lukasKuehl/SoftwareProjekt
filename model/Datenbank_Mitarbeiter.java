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

	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller Mitarbeiter aus der Datenbank und erzeugen von Mitarbeiter Objekten.
	 * Diese werden in eine LinkedList abgelegt und ausgegeben.
	 */
	protected LinkedList<Mitarbeiter> getAlleMitarbeiter() {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Benutzername, Passwort, Job, Vorname, Name, Maxstunden, Whname, Email  from Mitarbeiter";

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Mitarbeiter> mitarbeiterList = new LinkedList<>();

			while (rs.next()) {
				Mitarbeiter m = new Mitarbeiter(sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement, 0, sqlStatement, sqlStatement);

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
	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu dem eingegebenen Benutzernamen einen Mitarbeiter gibt,
	 * bei existenz return true sonst false
	 */
	
	protected boolean checkMitarbeiter(String benutzername) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select Benutzername from Mitarbeiter where benutzername = " + benutzername;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkMitarbeiter SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkMitarbeiter (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Auslesen eines bestimmten Mitarbeiters aus der Datenbank und erzeugen eines Mitarbeiter Objektes,
	 * welches anschließend ausgegeben wird.
	 */
	public Mitarbeiter getMitarbeiter(String benutzername) {
		if (!checkMitarbeiter(benutzername)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Benutzername, Passwort, Job, Vorname, Name, Maxstunden, Whname,Email  from Mitarbeiter";

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			

			
				Mitarbeiter m = new Mitarbeiter(sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement, 0, sqlStatement, sqlStatement);

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

}