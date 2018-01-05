package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Mitarbeiter;

/**
 * @author Thomas Friesen, Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und Änderung in der Datenbank im Bezug auf die Tabelle Mitarbeiter zu verarbeiten.
 */


class Datenbank_Mitarbeiter {

	/**
	 * @Thomas Friesen
	 * @info Die Methode fügt einen Mitarbeiter in die Datenbank hinein
	 */
	public boolean addMitarbeiter(Mitarbeiter ma, Connection con) {
		
		boolean success = false;
		PreparedStatement pstmt = null;
		String benutzername = null;
		String passwort = null;
		String job = null;
		String vorname = null;
		String name = null;
		int maxstunden = 0;
		String whname = null;
		String email = null;
		String sqlStatement = "insert into Mitarbeiter (benutzername, passwort, job, vorname, name, maxstunden, whname,email) values(?, ?, ?, ?, ?, ?, ?,?)";
		
		try {
			//Erstellen eines prepared Statements
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
			
			con.setAutoCommit(false);
			
			//Überprüfung der PK-Constraints
			if (checkMitarbeiter(benutzername,con)) {
				System.out.println("Der Mitarbeiter wurde bereits in die Schicht eingeteilt!");
			}
			if (checkMitarbeiterFK(job,whname,con) == false){
				System.out.println("Die Foreign-Key-Constraints der Mitarbeitertabelle wurden verletzt");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
				
				//Ausfüllen des prepared Statements mit den Variabeln aus dem übergebenen Mitarbeiterobjekt
				pstmt.setString(1, benutzername);
				pstmt.setString(2, passwort);
				pstmt.setString(3, job);
				pstmt.setString(4, vorname);
				pstmt.setString(5, name);
				pstmt.setInt(6, maxstunden);
				pstmt.setString(7, whname);
				pstmt.setString(8, email);
			
				//Ausführen der SQL-Anweisung
				pstmt.execute();
				//Übertragung der aktuellen Daten in die Datenbanktabelle Mitarbeiter
				con.commit();	
				
				success = true;
				
			}			
			
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
				return false;
			}
		} finally {
			//Schließen der offen gebliebenen Statements
			try {
			
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
		Datenbank_Mitarbeiter mitarbeiter=new Datenbank_Mitarbeiter();
		Statement stmt = null;
		ResultSet rs = null;

		//Benötigten Sql-Befehlt speichern
		String sqlStatement = "select benutzername from Mitarbeiter";

		try {
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Mitarbeiter> mitarbeiterList = new LinkedList<>();

			// Solange es einen "nächsten" Datensatz in dem Resultset gibt, mit den Daten des RS 
			// ein neues Mitarbeiter-Objekt erzeugen. Dieses wird anschließend der Liste hinzugefügt.
			while (rs.next()) {
				
				Mitarbeiter m =mitarbeiter.getMitarbeiter(rs.getString("Benutzername"),con); 

				mitarbeiterList.add(m);
			}


			//Liste mit Mitarbeiter-Objekten zurückgeben
			return mitarbeiterList;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
			System.err.println("Methode getAlleMitarbeiter SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schließen der offen gebliebenen Statements und Resultsets
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getAlleMitarbeiter (finally) SQL-Fehler: " + e.getMessage());
			}
		}

	}
	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu dem eingegebenen Benutzernamen einen Mitarbeiter gibt,
	 * bei existenz return true sonst false
	 */
	
	protected boolean checkMitarbeiter(String benutzername,Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		//Benötigten Sql-Befehlt speichern
		String sqlQuery = "select Benutzername from Mitarbeiter where benutzername = '"+benutzername+"'";

		try {
			//Statement, Resultset wird erstellt und Sql-Befehl wird ausgeführt, anschließend wird der 
			//nächste Datensatz aus dem Resultset ausgegeben
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf false setzen
			System.err.println("Methode checkMitarbeiter SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			//Schließen der offen gebliebenen Resultsets und Statements
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
	 * @author Thomas Friesen
	 * @info Die Methode prüft, ob die Foreign-Key-Constraints der Tabelle Mitarbeiter eingehalten werden
	 */
	protected boolean checkMitarbeiterFK(String job, String whname,Connection con) {
		boolean result = false;
		Statement[] stmt = new Statement[2];
		ResultSet[] rs = new ResultSet[2];
		String[] sqlQuery = new String[2]; 
		sqlQuery[0] = "select job from Userrecht where job = '"+ job +"'" ;
		sqlQuery[1] = "select whname from Warenhaus where whname = '" + whname + "'";
		
		try {
			//Erstellen der Statement- und Resultsetobjekte
			for (int i=0;i<2;i++){
				stmt[i] = con.createStatement();
				rs[i] = stmt[i].executeQuery(sqlQuery[i]);
			}
			//Überprüfung, ob beide FK-Constraints eingehalten werden
			if ((rs[0].next()) == true && (rs[1].next())== true){
				result = true;
			}else{
				result = false;
			}
			
		} catch (SQLException sql) {
			System.err.println("Methode checkMitarbeiterFK SQL-Fehler: " + sql.getMessage());
			return false;
			
		} finally {
			//Schließen der offen gebliebenen Statements und Resultsets
			try {
				for(int i=0;i<2;i++){
					if(rs[i] != null){
						rs[i].close();
					}
					if(stmt[i] != null){
						stmt[i].close();
					}
						
				}
				//Abfangen von Fehlern, beim Überprüfen der Datenbank
			} catch (SQLException e) {
				System.err.println("Methode checkMitarbeiterFK (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return result;
	}

	

	

	/**
	 * @author Anes Preljevic
	 * @info Auslesen eines bestimmten Mitarbeiters aus der Datenbank und erzeugen eines Mitarbeiter Objektes,
	 * welches anschließend ausgegeben wird.
	 */
	public Mitarbeiter getMitarbeiter(String benutzername, Connection con) {
		//Prüfen ob der Mitarbeiter vorhanden ist
		if (!checkMitarbeiter(benutzername, con)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;
		//Benötigten Sql-Befehlt speichern
		String sqlStatement = "select * from Mitarbeiter where benutzername='"+benutzername+"'";

		try {
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			//Auch wenn es voraussichtlich nur einen Datensatz gibt, den nächsten Datensatz abrufen,
			//um 100% sicherheit zu haben
				rs.next();
			
				Mitarbeiter m = new Mitarbeiter(rs.getString("Benutzername"),
						rs.getString("Passwort"), rs.getString("Job"),
						rs.getString("Vorname"),rs.getString("Name"),rs.getInt("Maxstunden"),
						rs.getString("Whname"), rs.getString("Email"));


				//Mitarbeiter-Objekt zurückgeben
			return m;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
			System.err.println("Methode getMitarbeiter SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schließen der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getMitarbeiter (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		
	}
	}
	
	/**
	 * @author Anes Preljevic
	 * @info Wechselt die Benutzerrolle eines Mitarbeiters der Job verändert wird.
	 * 	Kassenbüro steht für die Admin Benutzerrolle und Kassierer für Mitarbeiter.
	 */
		protected void wechselBenutzerrolle(String benutzername, Connection con) {

			Datenbank_Mitarbeiter mitarbeiter=new Datenbank_Mitarbeiter();
			Mitarbeiter m = mitarbeiter.getMitarbeiter(benutzername,con);
			Statement stmt = null;
			String sqlStatement=null;
			


			try {
				//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
				stmt = con.createStatement();
				if(con.getAutoCommit()!= false);
				{
					con.setAutoCommit(false);
				}
				//Wenn der Mitarbeiter existiert wechsel fortsetzen
				if(m!=null){
					
				// Wenn der Job des Mitarbeiters Kassierer oder Information ist, wird dieser auf Kassenbüro gesetzt.
				// Da dieser über die Benutzerrolle Admin verfügt und nicht Mitarbeiter.
				// Trifft dieser Fall nicht ein wird der Job auf Kassierer gesetzt und die Rolle ändert sich
				// von Admin auf Mitarbeiter.
				if(m.getJob().equalsIgnoreCase("Kassierer")|| m.getJob().equalsIgnoreCase("Information")){
					sqlStatement = "UPDATE Mitarbeiter SET Job = 'Kassenbüro' WHERE benutzername='"+benutzername+"'";
				stmt.execute(sqlStatement);
				}
					else{
						sqlStatement="UPDATE Mitarbeiter SET Job = 'Kassierer' WHERE benutzername='"+benutzername+"'";
						stmt.execute(sqlStatement);
					}	
					
				}

				//Connection Zustand bestätigen und somit fest in die Datenbank schreiben
				con.commit();
				

			} catch (SQLException sql) {
				System.err.println("Methode wechselBenutzerrolle SQL-Fehler: " + sql.getMessage());
				try {
					//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
					con.rollback();
					
				} catch (SQLException sqlRollback) {
					System.err.println("Methode wechselBenutzerrolle " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
				}
			} finally {
				//Schließen der offen gebliebenen Statements
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
		 * @info Löschen eines Mitarbeiters aus der Datenbanktabelle Mitarbeiter
		 */
		
		protected boolean deleteMitarbeiter(String benutzername,Connection con) {
			//Überprüfen ob der zu löschende Datensatz existiert
			if (!checkMitarbeiter(benutzername, con)){
				return true;
			}
			else{
			Statement stmt = null;

			
			//Benötigten Sql-Befehlt speichern
			String sqlStatement = "DELETE FROM Mitarbeiter WHERE benutzername = " + benutzername;
	
			
			try {
				//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
				if(con.getAutoCommit()!= false);
				{
					con.setAutoCommit(false);
				}
				//Sql-Statement ausführen
				stmt = con.createStatement();
				stmt.execute(sqlStatement);
				
				//Connection Zustand bestätigen und somit fest in die Datenbank schreiben
				con.commit();

				return true;
				}
				catch (SQLException sql) {
				System.err.println("Methode deleteMitarbeiter SQL-Fehler: " + sql.getMessage());
				try {
					//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
					con.rollback();
					return false;
				} catch (SQLException sqlRollback) {
						System.err.println("Methode deleteMitarbeiter " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
						return false;
					}
			} 
				finally {
				try {
					//Schließen der offen gebliebenen Statements
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					System.err.println("Methode deleteMitarbeiter (finally) SQL-Fehler: " + e.getMessage());
				}
				}
			}
		}
}