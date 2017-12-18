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
	 * @Thomas Friesen
	 * @info Die Methode fügt einen Mitarbeiter in die Datenbank hinein
	 */
	public boolean addMitarbeiter(Mitarbeiter ma) {
		boolean success = false;
	
		String sqlStatement;
		sqlStatement = "insert into Mitarbeiter (benutzername, passwort, job, vorname, name, maxstunden, whname) values(?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		Statement checkInput = null;
		ResultSet checkRS = null;
		String benutzername = null;
		String passwort = null;
		String job = null;
		String vorname = null;
		String name = null;
		int maxstunden = 0;
		String whname = null;
		
		try {
			pstmt = con.prepareStatement(sqlStatement);

			//Auslesen der Parameter aus dem Mitarbeiterobjekt
			benutzername = ma.getBenutzername();
			passwort = ma.getPasswort();
			job = ma.getJob();
			vorname = ma.getVorname();
			name = ma.getName();
			maxstunden = ma.getMaxstunden();
			whname = ma.getWhname();
			
			// Verhindert das Commit nach jeder Anweisung. Nicht zwangsläufig notwendig bei einem einzelnen SQL-Befehl
			con.setAutoCommit(false);

			if (checkMitarbeiter(benutzername)) {
				System.out.println("Der Mitarbeiter wurde bereits in die Schicht eingeteilt!");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
			
				pstmt.setString(1, benutzername);
				pstmt.setString(2, passwort);
				pstmt.setString(3, job);
				pstmt.setString(4, vorname);
				pstmt.setString(5, name);
				pstmt.setInt(6, maxstunden);
				pstmt.setString(7, whname);
			
				pstmt.execute();
				con.commit();	
				
			}			
			
			success = true;
			con.setAutoCommit(true);
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im späteren Programm entweder gar nicht oder vielleicht als Dialog(ausgelöst in der View weil der Rückgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einfügen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Hinzufügen eines Mitarbeiters:");
			System.out.println("Parameter: benutzername = " + benutzername);
			sql.printStackTrace();		
			
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addMitarbeiter " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schließen der offen gebliebenen Statements & ResultSets
			try {
				
				if(checkRS != null){
					checkRS.close();
				}				
				
				if(checkInput != null){
					checkInput.close();
				}			
				
				if (pstmt != null){
					pstmt.close();
				}			
				
			} catch (SQLException e) {
				System.err.println("Methode addMitarbeiter(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}

	
	
	
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