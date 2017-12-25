package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Mitarbeiter;
import data.Schicht;
import data.Tauschanfrage;
import data.Tblock_Tag;

class Datenbank_Tauschanfrage {



	/**
	 * @Thomas Friesen
	 * @info  Fügt eine neue Tauschanfrage in der Tabelle Tauschanfrage hinzu
	 */

	protected boolean addTauschanfrage(int tauschNr, String senderName, int senderSchichtNr, String empfaengerName, int empfaengerSchichtNr,Connection con ) {
		boolean success = false;
	
		
		String sqlStatement;
		sqlStatement = "insert into Tauschanfrage(empfänger, sender, bestätigungsstatus,schichtnrsender, schichtnrempfänger, tauschnr) values(?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		Statement checkInput = null;
		ResultSet checkRS = null;
		boolean bestaetigestatus = false;
		
		
		try {
			pstmt = con.prepareStatement(sqlStatement);
			
			con.setAutoCommit(false);

			if (checkTauschanfrage(tauschNr, con)) {
				System.out.println("Die Tauschnummer befindet sich bereits in der Datenbank!");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
			
				pstmt.setString(1, empfaengerName);
				pstmt.setString(2, senderName);
				pstmt.setBoolean(3,bestaetigestatus);
				pstmt.setInt(4,senderSchichtNr);
				pstmt.setInt(5, empfaengerSchichtNr);
				pstmt.setInt(6, tauschNr);
			
				pstmt.execute();
				con.commit();	
				
			}			
			
			success = true;
			con.setAutoCommit(true);
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im späteren Programm entweder gar nicht oder vielleicht als Dialog(ausgelöst in der View weil der Rückgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einfügen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Zuordnung einer Tauschnummer zu einer Tauschanfrage:");
			System.out.println("Parameter: TauschNr = " + tauschNr);
			sql.printStackTrace();		
			
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
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
				System.err.println("Methode addTauschanfrage(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}
	/**
	 * @Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen tauschnr eine Tauschtanfrage gibt, bei existenz return true sonst false.
	 */
	protected boolean checkTauschanfrage(int tauschnr,Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select tauschnr from Tauschanfrage where tauschnr = " + tauschnr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkTauschanfrageSQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkTauschanfrage(finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	/**
	 * @Anes Preljevic
	 * @info Ändert den Bestätigungsstatus der übergebenen Tauschanfrage
	 */
	protected void updateTauschanfrage(Tauschanfrage tauschanfrage,Connection con) {


		boolean bestätigungsstatus = tauschanfrage.isBestätigungsstatus();
		int tauschnr = tauschanfrage.getTauschnr();

		String sqlStatement;
		sqlStatement = "UPDATE Tauschanfrage " + "SET Bestätigunsstatus = ? " + "WHERE Tauschnr =" + tauschnr;
				
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(sqlStatement);

			con.setAutoCommit(false);
			pstmt.setBoolean(1, bestätigungsstatus);
			pstmt.executeUpdate();
			con.commit();

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode updateTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode updateTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode updateTauschanfarage (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @Anes Preljevic
	 * @info Ändert den Bestätigungsstatus der übergebenen Tauschanfrage
	 */
	protected void bestätigeTauschanfrage(int tauschnr,Connection con) {


		String sqlStatement;
		sqlStatement = "UPDATE Tauschanfrage SET Bestätigungsstatus = true WHERE Tauschnr =" + tauschnr;
		Statement stmt = null;

		try {

			stmt = con.createStatement();
			con.setAutoCommit(false);
			stmt.executeUpdate(sqlStatement);
			con.commit();

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode bestätigeTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode bestätigeTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode bestätigeTauschanfrage (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @Anes Preljevic
	 * @info Auslesen der Tauschanfragen aus der Datenbank und hinzufügen in eine Liste, welche den Ausgabewert darstellt.
	 * Diese beinhaltet die zugehörigen Mitarbeiter und Schichten der Sender und Empfänger.
	 * Es werden Mitarbeiter, Schicht Objekte welche dem Sender/Empfänger entsprechen für jede Tauschanfrage gespeichert.
	 */
	protected LinkedList<Tauschanfrage> getTauschanfragen(Connection con) {
		Datenbank_Schicht schicht = new Datenbank_Schicht();
		LinkedList<Schicht> schichtList = schicht.getSchichten(con);
		Datenbank_Mitarbeiter mitarbeiter = new Datenbank_Mitarbeiter();
		LinkedList<Mitarbeiter> mitarbeiterList = mitarbeiter.getAlleMitarbeiter(con);
		
		
		
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Empfänger, Sender, Bestätigungsstatus,"
				+ " Schichtnrsender, Schichtnrempfänger, Tauschnr from Tauschanfrage";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Tauschanfrage> tauschanfrageList = new LinkedList<>();

			while (rs.next()) {
				Tauschanfrage tanf = new Tauschanfrage(rs.getString("Empfänger"),rs.getString("Sender"), rs.getBoolean("Bestätigungsstatus")
						,rs.getInt("Schichtnrsender"), rs.getInt("Schichtnrempfänger"), rs.getInt("Tauschnr"));
				LinkedList<Mitarbeiter> senderList = new LinkedList<>();
				for (Mitarbeiter ma : mitarbeiterList) {
					if (ma.getBenutzername().equals(tanf.getSender())) {
						senderList.add(ma);	
					}
				}
				tanf.setLinkedListSender(senderList);
				
				LinkedList<Mitarbeiter> empfängerList = new LinkedList<>();
				for (Mitarbeiter ma : mitarbeiterList) {
					if (ma.getBenutzername().equals(tanf.getEmpfänger())) {
						empfängerList.add(ma);
					}
				}
				tanf.setLinkedListEmpfänger(empfängerList);
				
				LinkedList<Schicht> schichtnrSenderList = new LinkedList<>();
				for (Schicht sch : schichtList) {
					if (sch.getSchichtnr()==tanf.getSchichtnrsender()) {
						schichtnrSenderList.add(sch);
					}
				}
				tanf.setLinkedListSchichtensender(schichtnrSenderList);
				
				LinkedList<Schicht> schichtnrEmpfängerList = new LinkedList<>();
				for (Schicht sch : schichtList) {
					if (sch.getSchichtnr() == tanf.getSchichtnrempfänger()) {
						schichtnrEmpfängerList.add(sch);
					}
				}
				tanf.setLinkedListSchichtenempfänger(schichtnrEmpfängerList);

				tauschanfrageList.add(tanf);
			}

			rs.close();
			stmt.close();

			return tauschanfrageList;

		} catch (SQLException sql) {
			System.err.println("Methode getTauschanfragen SQL-Fehler: " + sql.getMessage());
			return null;
		}
	}
	/**
	 * @Anes Preljevic
	 * @info Auslesen einer bestimmten Tauschanfragen aus der Datenbankl, Speicherung im Objekt Tauschanfrage welches den Ausgabewert darstellt.
	 * Diese beinhaltet die zugehörigen Mitarbeiter und Schichten der Sender und Empfänger.
	 * Es werden Mitarbeiter, Schicht Objekte welche dem Sender/Empfänger entsprechen für jede Tauschanfrage gespeichert.
	 */
	protected Tauschanfrage getTauschanfrage(int tauschnr, Connection con ) {
		Datenbank_Schicht schicht = new Datenbank_Schicht();
		LinkedList<Schicht> schichtList = schicht.getSchichten(con);
		Datenbank_Mitarbeiter mitarbeiter = new Datenbank_Mitarbeiter();
		LinkedList<Mitarbeiter> mitarbeiterList = mitarbeiter.getAlleMitarbeiter(con);
		
		
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Empfänger, Sender, Bestätigungsstatus,"
				+ " Schichtnrsender, Schichtnrempfänger, Tauschnr from Tauschanfrage WHERE tauschnr="+tauschnr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			

				rs.next();
				Tauschanfrage tanf = new Tauschanfrage(rs.getString("Empfänger"),rs.getString("Sender"), rs.getBoolean("Bestätigungsstatus")
						,rs.getInt("Schichtnrsender"), rs.getInt("Schichtnrempfänger"), rs.getInt("Tauschnr"));

				for (Mitarbeiter ma : mitarbeiterList) {
					
					if (ma.getBenutzername().equals(tanf.getSender())) {
						LinkedList<Mitarbeiter> senderList = new LinkedList<>();
						//System.out.println(ma.getBenutzername()+" "+ tanf.getSender());	
						senderList.add(ma);
						tanf.setLinkedListSender(senderList);
						
					}
				}
				for (Mitarbeiter ma : mitarbeiterList) {
					if (ma.getBenutzername().equals(tanf.getEmpfänger())) {
						
						LinkedList<Mitarbeiter> empfängerList = new LinkedList<>();
						empfängerList.add(ma);
						tanf.setLinkedListEmpfänger(empfängerList);
					}
				}
				
				for (Schicht sch : schichtList) {
					LinkedList<Schicht> schichtnrSenderList = new LinkedList<>();
					if (sch.getSchichtnr() == tanf.getSchichtnrsender()) {
						tanf.setLinkedListSchichtensender(schichtnrSenderList);
					}
				}
				for (Schicht sch : schichtList) {
					LinkedList<Schicht> schichtnrEmpfängerList = new LinkedList<>();
					if (sch.getSchichtnr() == tanf.getSchichtnrempfänger()) {
						tanf.setLinkedListSchichtenempfänger(schichtnrEmpfängerList);
					}
				}
			

			rs.close();
			stmt.close();

			return tanf;

		} catch (SQLException sql) {
			System.err.println("Methode getTauschanfrage SQL-Fehler: " + sql.getMessage());
			return null;
		}
	}
	/**
	 * @Anes Preljevic
	 * @info Löschen einer Tauschanfrage aus der Datenbank Tabelle Tauschanfrage
	 */
	protected boolean deleteTauschanfrage(int tauschnr, Connection con) {
	if (checkTauschanfrage(tauschnr,con)==false){
			return false;
	}
	else{
		Statement stmt = null;
		ResultSet rs = null;
		String sqlStatement = "DELETE FROM Tauschanfrage WHERE Tauschnr= "+tauschnr;

		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();
			stmt.execute(sqlStatement);
			
			con.commit();

			con.setAutoCommit(true);
			
		
		return true;
		}catch (SQLException sql) {
			System.err.println("Methode deleteTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				
				con.rollback();
				con.setAutoCommit(true);
				return false;
			} catch (SQLException sqlRollback) {
					System.err.println("Methode deleteTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
					return false;
				}
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteTauschanfrage (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		
	}
		
	}
	/**
	 * @author Anes Preljevic
	 * @info Fragt die höchste Tauschnr ab und erhöht diese um 1, sodass bei neu Erstellung
	 * einer Tauschanfrage die nächste Tauschnr vorliegt.
	 */
	protected  int getNewTauschnr(Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select max(tauschnr)+1 from Tauschanfrage";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs.next();
			int maxTauschnr = rs.getInt(1);
			rs.close();
			stmt.close();
			return maxTauschnr;
		} catch (SQLException sql) {
			System.err.println("Methode getNewEmpno SQL-Fehler: "
					+ sql.getMessage());
			return -1;
		}
	}
}
