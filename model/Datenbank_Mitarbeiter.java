package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Mitarbeiter;

//Klassenbeschreibung fehlt!

//Bei fast allen Methoden fehlen Kommentare zu den einzelnen Anweisungen

//Bei vielen Mehtoden von Anes fehlen die finally-Blöcke

class Datenbank_Mitarbeiter {

	
	/**
	 * @Thomas Friesen
	 * @info Die Methode fügt einen Mitarbeiter in die Datenbank hinein
	 */
	public boolean addMitarbeiter(Mitarbeiter ma, Connection con) {
		boolean success = false;
	
		//Eine Anweisung
		String sqlStatement;
		sqlStatement = "insert into Mitarbeiter (benutzername, passwort, job, vorname, name, maxstunden, whname,email) values(?, ?, ?, ?, ?, ?, ?,?)";
		
		PreparedStatement pstmt = null;
		
		//Checks werden nicht benutzt! siehe DatenbankMa_Schicht
		Statement checkInput = null;
		ResultSet checkRS = null;
		
		String benutzername = null;
		String passwort = null;
		String job = null;
		String vorname = null;
		String name = null;
		int maxstunden = 0;
		String whname = null;
		String email = null;
		
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
			email = ma.getEmail();
			
			// Verhindert das Commit nach jeder Anweisung. Nicht zwangsläufig notwendig bei einem einzelnen SQL-Befehl
			con.setAutoCommit(false);
			
			
			if (checkMitarbeiter(benutzername,con)) {
				System.out.println("Der Mitarbeiter wurde bereits in die Schicht eingeteilt!");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
			
				//Wieder nur PK_Check-Überprüfung! --> FK-Warenhaus, FK-Benuzerrolle/Job fehlt!
				
				pstmt.setString(1, benutzername);
				pstmt.setString(2, passwort);
				pstmt.setString(3, job);
				pstmt.setString(4, vorname);
				pstmt.setString(5, name);
				pstmt.setInt(6, maxstunden);
				pstmt.setString(7, whname);
				pstmt.setString(8, email);
			
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
				
				//Beide checks wurden nicht benutzt und brauchen nicht geschlossen werden!
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
	protected LinkedList<Mitarbeiter> getAlleMitarbeiter(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		//Select * hat den selben Effekt --> übersichtlicher
		String sqlStatement = "select Benutzername, Passwort, Job, Vorname, Name, Maxstunden, Whname, Email  from Mitarbeiter";

		try {
			//zusätzliche Anweisungen für das Statement werden nie verwendet!
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Mitarbeiter> mitarbeiterList = new LinkedList<>();

			while (rs.next()) {
				
				//Erzeugen eines neuen Mitarbeiter-Objektes überflüssig --> getMitarbeiter(benutzername, con) hat den selben Effekt und ist kürzer
				Mitarbeiter m = new Mitarbeiter(rs.getString("Benutzername"), rs.getString("Passwort"),
						rs.getString("Job"), rs.getString("Vorname"), rs.getString("Name"),
						rs.getInt("Maxstunden"),rs.getString("Whname"),rs.getString("Email"));

				mitarbeiterList.add(m);
			}

			rs.close();
			stmt.close();

			return mitarbeiterList;

		} catch (SQLException sql) {
			System.err.println("Methode getAlleMitarbeiter SQL-Fehler: " + sql.getMessage());
			return null;
		}
		
		//Finally-Block fehlt!
	}
	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu dem eingegebenen Benutzernamen einen Mitarbeiter gibt,
	 * bei existenz return true sonst false
	 */
	
	protected boolean checkMitarbeiter(String benutzername,Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select Benutzername from Mitarbeiter where benutzername = '"+benutzername+"'";

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
	public Mitarbeiter getMitarbeiter(String benutzername, Connection con) {
		if (!checkMitarbeiter(benutzername, con)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Benutzername, Passwort, Job, Vorname, Name, Maxstunden, Whname,Email  from Mitarbeiter where benutzername='"+benutzername+"'";

		try {
			//siehe oben
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			
				rs.next();
			
				Mitarbeiter m = new Mitarbeiter(rs.getString("Benutzername"),
						rs.getString("Passwort"), rs.getString("Job"),
						rs.getString("Vorname"),rs.getString("Name"),rs.getInt("Maxstunden"),
						rs.getString("Whname"), rs.getString("Email"));


			rs.close();
			stmt.close();

			return m;

		} catch (SQLException sql) {
			System.err.println("Methode getMitarbeiter SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteTblock_Tag (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		//Finally-Block fehlt!
	}
	}
		protected void wechselBenutzerrolle(String benutzername, Connection con) {

			Datenbank_Mitarbeiter ma = new Datenbank_Mitarbeiter();
			Mitarbeiter m = ma.getMitarbeiter(benutzername,con);
			Statement stmt = null;
			String sqlStatement1;
			String sqlStatement2;


			try {
				stmt = con.createStatement();
				
				//siehe oben!
				con.setAutoCommit(false);
				
				//m könnte auch null sein --> Überprüfung notwendig!
				if(m.getJob().equalsIgnoreCase("Kassierer")|| m.getJob().equalsIgnoreCase("Information")){
					sqlStatement1 = "UPDATE Mitarbeiter SET Job = 'Kassenbüro' WHERE benutzername='"+benutzername+"'";
				stmt.execute(sqlStatement1);
				}
					else{
						sqlStatement2="UPDATE Mitarbeiter SET Job = 'Kassierer' WHERE benutzername='"+benutzername+"'";
						stmt.execute(sqlStatement2);
					}	
					

				
				con.commit();
				con.setAutoCommit(true);

			} catch (SQLException sql) {
				System.err.println("Methode wechselBenutzerrolle SQL-Fehler: " + sql.getMessage());
				try {
					con.rollback();
					con.setAutoCommit(true);
				} catch (SQLException sqlRollback) {
					System.err.println("Methode wechselBenutzerrolle " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
				}
			} finally {
				try {
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					System.err.println("Methode wechselBenutzerrolle (finally) SQL-Fehler: " + e.getMessage());
				}
			}
		}
		/**
		 * @author Anes Preljevic
		 * @info Löschen eines Wochenplans mit zugehörigen Tagen (schichten)  aus den Datenbank Tabellen 
		 * Wochenplan, Tag, Schicht, Ma-Schicht ( Schicht und Ma-Schicht werden über die Tag/Schicht - deleteMethode gelöscht).
		 */
		
		protected boolean deleteMitarbeiter(String benutzername,Connection con) {

			if (!checkMitarbeiter(benutzername, con)){
				//wenn es den Mitarbeiter gar nicht gibt ist doch auch alles ok --> Rückgabewert sollte true sein. False nur wenn er/sie da ist und nicht gelöscht werden kann!
				return false;
			}
			else{
			Statement stmt = null;
			ResultSet rs = null;
			String sqlStatement = "DELETE FROM Mitarbeiter WHERE benutzername = " + benutzername;
	
			
			try {	
				
				stmt = con.createStatement();
				stmt.execute(sqlStatement);
				con.commit();

				//AutoCommit ist schon true --> Anweisung überflüssig
				con.setAutoCommit(true);
				return true;
				}
				catch (SQLException sql) {
				System.err.println("Methode deleteMitarbeiter SQL-Fehler: " + sql.getMessage());
				try {
					
					con.rollback();
					con.setAutoCommit(true);
					return false;
				} catch (SQLException sqlRollback) {
						System.err.println("Methode deleteMitarbeiter " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
						return false;
					}
			} 
				finally {
				try {
					if (rs != null)
						rs.close();
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					System.err.println("Methode deleteMitarbeiter (finally) SQL-Fehler: " + e.getMessage());
				}
				}
			}
		}

}