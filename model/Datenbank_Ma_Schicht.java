package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Ma_Schicht;

//Klassenbeschreibung fehlt!

//Innerhalb fast aller Methoden ist kein einziger Kommentar zur Erklärung was ihr mit den einzelnen Anweisungen macht!
 
//Bei vielen Methoden von Anes fehlen die finally-Blöcke

 class Datenbank_Ma_Schicht {


	 /**
		 * @Thomas Friesen
		 * @info Die Methode fügt einen Datensatz in die Ma_Schicht Tabelle ein.
		 */
		protected boolean addMa_Schicht(Ma_Schicht ma_schicht,Connection con) {
			boolean success = false;
			String sqlStatement = "insert into Ma_Schicht (Schichtnr,Benutzername) values(?, ?)";
			PreparedStatement pstmt = null;		
			int schichtnr = 0;
			String benutzername = null;
			
			
			try {
				pstmt = con.prepareStatement(sqlStatement);

				//Auslesen der als Parameter übergebenen Mitarbeiter-Schicht-Beziehung
				schichtnr = ma_schicht.getSchichtnr();
				benutzername = ma_schicht.getBenutzername();		
				
				//Überprüfung, dass die PK-Check-Constrains nicht verletzt werden
				if (checkMa_Schicht(schichtnr, benutzername,con)) {
					System.out.println("Der Mitarbeiter wurde bereits in die Schicht eingeteilt!");
					
				//Überprüfung, dass die FK-Check-Constraints nicht verletzt werden
				}else if ((checkMa_SchichtFK(schichtnr,benutzername,con) == false)){
					System.out.println("Der Foreign-Key Constraint  der Ma_Schicht Tabelle wurde verletzt!");
				}
				else{
					//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
					
					//Ausfüllen und ausführen des Prepared Statements
					pstmt.setInt(1, schichtnr);
					pstmt.setString(2, benutzername);
					pstmt.execute();
					con.commit();	
					
				}			
				
				success = true;
				
				
			} catch (SQLException sql) {
				//Die Ausgaben dienen zur Ursachensuche und sollten im späteren Programm entweder gar nicht oder vielleicht als Dialog(ausgelöst in der View weil der Rückgabewert false ist) angezeigt werden
				System.out.println("Fehler beim Einfügen eines neuen Datensatzes in die Datenbank!");
				System.out.println("Fehler bei der Zuordnung eines Mitarbeiters zu einer Schicht:");
				System.out.println("Parameter: Schichtnr = " + schichtnr + " Mitarbeiter = " + benutzername);
				sql.printStackTrace();		
				
				try {
					//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
					con.rollback();
					con.setAutoCommit(true);
				} catch (SQLException sqlRollback) {
					System.err.println("Methode addMa_Schicht " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
				}
			} finally {
				//Schließen der offen gebliebenen Statements & ResultSets
				try {		
					if (pstmt != null){
						pstmt.close();
					}			
					
				} catch (SQLException e) {
					System.err.println("Methode addMa_Schicht(finally) SQL-Fehler: " + e.getMessage());
				}
			}
			return success;
		}

	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen schichtnr und dem benutzernamen bereits einen Mitarbeiter in der Schicht gibt, bei existenz return true, sonst false
	 */
	protected boolean checkMa_Schicht(int schichtnr, String benutzername,Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		
		//Ein Integer darf nicht in Anführungszeichen stehen, sonst wird er als String erkannt!
		String sqlQuery = "select schichtnr, benutzername from Ma_Schicht where schichtnr = '"+schichtnr+"' and benutzername= '"+benutzername+"'";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkMa_Schicht SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkMa_Schicht (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	/**
	 * @author Thomas Friesen
	 * @info Die Methode prüft, ob die Foreign Keys der Tabelle Ma_Schicht eingehalten werden
	 */
	protected boolean checkMa_SchichtFK(int schichtnr, String benutzername,Connection con) {
		boolean result = false;
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String sqlQuery = "select schichtnr from Schicht where schichtnr = "+ schichtnr ;
		String sqlQuery2 = "select benutzername from Mitarbeiter where benutzername = '" + benutzername + "'";
		
		try {
			stmt = con.createStatement();
			stmt2 = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs2 = stmt2.executeQuery(sqlQuery2);
			
			if ((rs.next()) == true && (rs2.next())== true){
				result = true;
			}else{
				result = false;
			}
			return result;
				

		} catch (SQLException sql) {
			System.err.println("Methode checkMa_SchichtFK SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null){
						rs.close();
				}
				if (rs2 != null){
						rs2.close();
				}
				if (stmt != null){
						stmt.close();
				}
				if (stmt2 != null){
						stmt2.close();
				}
			} catch (SQLException e) {
				System.err.println("Methode checkMa_SchichtFK (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	
	/**
	 * @author Anes Preljevic
	 * @info Auslesen der Mitarbeiter mit den zugehörigen Schichten aus der Datenbank und hinzufügen in eine Liste, welche den Ausgabewert darstellt 
	 */
	protected LinkedList<Ma_Schicht> getMa_Schicht(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "SELECT Benutzername, Schichtnr FROM Ma_Schicht";

		try {
			//Die zusätzlichen Anweisungen sind zwar nicht verkehrt, du nutzt die zusätzlichen Möglichkeiten aber nicht, also können die auch weg --> ist verwirrend
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);
			
			LinkedList<Ma_Schicht> ma_schichtList = new LinkedList<>();

			while (rs.next()) {

				Ma_Schicht ms = new Ma_Schicht(rs.getString("Benutzername"),rs.getInt("Schichtnr"));

				ma_schichtList.add(ms);
			}

			rs.close();
			stmt.close();

			return ma_schichtList;

		} catch (SQLException sql) {
			System.err.println("Methode getMa_Schicht SQL-Fehler: " + sql.getMessage());
			return null;
		}
		//Finally-Block fehlt!
	}

	
	//Finde die beiden unteren Methoden an sich nicht unbedingt sinnvoll, besser wäre LinkedList<Mitarbeiter> getMitarbeiterausderSchicht und LinkedList<Schicht> getSchichtenEinesMitarbeiters --> Im Moment kommt man mit getAlleMa_Schicht und zwei for-Schleifen direkt zu LinkedList<Mitarbeiter>/LinkedList<Schicht> und mit der Schicht-Beziehung brauche ich trotzdem eine for-Schleife um an die Mitarbeiter/Schichten zu kommen.
	
	
	/**
	* @author Anes Preljevic
	* @info Auslesen der Mitarbeiter die in eine Schicht gehören mit der übergebenen Schichtnr aus der Datenbank,
	*  erstellen eines Objektes Ma_Schicht mit den Daten aus der Datenbank. Liste mit den Ma_Schicht Objekten
	*  wird ausgegeben.
	*/
	protected LinkedList<Ma_Schicht> getMitarbeiterausderSchicht(int schichtnr,Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "SELECT Benutzername, Schichtnr from Ma_Schicht WHERE schichtnr="+schichtnr;

		try {
			//siehe vorherige Methode
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Ma_Schicht> ma_schichtList = new LinkedList<>();

			while (rs.next()) {
				Ma_Schicht ms = new Ma_Schicht(rs.getString("Benutzername"),rs.getInt("Schichtnr"));

				ma_schichtList.add(ms);
			}

			rs.close();
			stmt.close();

			return ma_schichtList;

		} catch (SQLException sql) {
			System.err.println("Methode getMitarbeiterausderSchicht SQL-Fehler: " + sql.getMessage());
			return null;
		}
		//Finally-Block fehlt!
	}
	
	
	/**
	* @author Anes Preljevic
	* @info Auslesen der Mitarbeiter die in eine Schicht gehören mit der übergebenen Schichtnr aus der Datenbank,
	*  erstellen eines Objektes Ma_Schicht mit den Daten aus der Datenbank. Liste mit den Ma_Schicht Objekten
	*  wird ausgegeben.
	*/
	protected LinkedList<Ma_Schicht> getSchichteneinesMitarbeiters(String benutzername,Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "SELECT Benutzername, Schichtnr from Ma_Schicht WHERE benutzername='"+benutzername+"'";

		try {
			//siehe vorherige Methode
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Ma_Schicht> ma_schichtList = new LinkedList<>();

			while (rs.next()) {
				Ma_Schicht ms = new Ma_Schicht(rs.getString("Benutzername"),rs.getInt("Schichtnr"));

				
				ma_schichtList.add(ms);
			}

			rs.close();
			stmt.close(); 

			return ma_schichtList;

		} catch (SQLException sql) {
			System.err.println("Methode getSchichteneinesMitarbeiters SQL-Fehler: " + sql.getMessage());
			return null;
		}
		//Finally-Block fehlt!
	}
	/**
	 * @author Anes Preljevic
	 * @info Löschen eines Mitarbeiters aus einer Schicht
	 */
	protected boolean deleteMa_Schicht(int schichtnr, String benutzername, Connection con) {
		if(checkMa_Schicht(schichtnr, benutzername,con)==false){
			return false;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;
		
		//siehe oben, eine SchichtNr ist ein Integer!
		String sqlStatement = "DELETE from Ma_Schicht where schichtnr = '"+schichtnr+"' and benutzername= '"+benutzername+"'";
		
		try {
			//Das AutoCommit kann aktiviert bleiben, es ist nur eine DML-Anweisung!
			con.setAutoCommit(false);
			stmt = con.createStatement();
			stmt.execute(sqlStatement);
			con.commit();
			
			
			con.setAutoCommit(true);
			return true;
		}			catch (SQLException sql) {
			System.err.println("Methode deleteMa_Schicht SQL-Fehler: " + sql.getMessage());
			try {
				
				con.rollback();
				con.setAutoCommit(true);
				return false;
			} catch (SQLException sqlRollback) {
					System.err.println("Methode deleteMa_schicht " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
					return false;
				}
		}  finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteMa_Schicht (finally) SQL-Fehler: " + e.getMessage());
			}
		}}
	}
	/**
	 * @author Anes Preljevic
	 * @info Löscht den Ma_Schicht Datensatz aus der Tabelle Ma_Schicht, mit dem die übergebene Schichtnr übereinstimmt,
	 * wenn die Schicht gelöscht wurde.
	 */
	protected boolean deleteMa_SchichtWochenplan(int schichtnr,Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlStatement = "DELETE FROM Ma_Schicht WHERE Schichtnr = "+schichtnr;

		try {
			//siehe obere Methode!
			con.setAutoCommit(false);
			stmt = con.createStatement();
			stmt.execute(sqlStatement);
			
			con.commit();
			con.setAutoCommit(true);
			
			return true;
			
			
		}			catch (SQLException sql) {
			System.err.println("Methode deleteWochenplan SQL-Fehler: " + sql.getMessage());
			try {
				
				con.rollback();
				con.setAutoCommit(true);
				return false;
			} catch (SQLException sqlRollback) {
					System.err.println("Methode deleteWochenplan " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
					return false;
				}
		}  finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteMa_Schicht (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
}
