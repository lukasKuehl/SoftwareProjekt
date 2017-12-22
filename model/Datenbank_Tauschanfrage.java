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
	 * @info  F�gt eine neue Tauschanfrage in der Tabelle Tauschanfrage hinzu
	 */

	protected boolean addTauschanfrage(int tauschNr, String senderName, int senderSchichtNr, String empfaengerName, int empfaengerSchichtNr,Connection con ) {
		boolean success = false;
	
		
		String sqlStatement;
		sqlStatement = "insert into Tauschanfrage(empf�nger, sender, best�tigungsstatus,schichtnrsender, schichtnrempf�nger, tauschnr) values(?, ?, ?, ?, ?, ?)";
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
			//Die Ausgaben dienen zur Ursachensuche und sollten im sp�teren Programm entweder gar nicht oder vielleicht als Dialog(ausgel�st in der View weil der R�ckgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einf�gen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Zuordnung einer Tauschnummer zu einer Tauschanfrage:");
			System.out.println("Parameter: TauschNr = " + tauschNr);
			sql.printStackTrace();		
			
			try {
				//Zur�cksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schlie�en der offen gebliebenen Statements & ResultSets
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
	 * @info Pr�ft ob es zu der eingegebenen tauschnr eine Tauschtanfrage gibt, bei existenz return true sonst false.
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
	 * @info �ndert den Best�tigungsstatus der �bergebenen Tauschanfrage
	 */
	protected void updateTauschanfrage(Tauschanfrage tauschanfrage,Connection con) {


		boolean best�tigungsstatus = tauschanfrage.isBest�tigungsstatus();
		int tauschnr = tauschanfrage.getTauschnr();

		String sqlStatement;
		sqlStatement = "UPDATE Tauschanfrage " + "SET Best�tigunsstatus = ? " + "WHERE Tauschnr =" + tauschnr;
				
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(sqlStatement);

			con.setAutoCommit(false);
			pstmt.setBoolean(1, best�tigungsstatus);
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
	 * @info �ndert den Best�tigungsstatus der �bergebenen Tauschanfrage
	 */
	protected void best�tigeTauschanfrage(int tauschnr,Connection con) {


		String sqlStatement;
		sqlStatement = "UPDATE Tauschanfrage " + "SET Best�tigunsstatus = true " + "WHERE Tauschnr =" + tauschnr;
		Statement stmt = null;

		try {

			stmt = con.createStatement();
			con.setAutoCommit(false);
			stmt.executeUpdate(sqlStatement);
			con.commit();

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode best�tigeTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode best�tigeTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode best�tigeTauschanfrage (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @Anes Preljevic
	 * @info Auslesen der Tauschanfragen aus der Datenbank und hinzuf�gen in eine Liste, welche den Ausgabewert darstellt.
	 * Diese beinhaltet die zugeh�rigen Mitarbeiter und Schichten der Sender und Empf�nger.
	 * Es werden Mitarbeiter, Schicht Objekte welche dem Sender/Empf�nger entsprechen f�r jede Tauschanfrage gespeichert.
	 */
	protected LinkedList<Tauschanfrage> getTauschanfragen(Connection con) {
		Datenbank_Schicht schicht = new Datenbank_Schicht();
		LinkedList<Schicht> schichtsenderList = schicht.getSchichten(con);
		LinkedList<Schicht> schichtempf�ngerList = schicht.getSchichten(con);
		
		Datenbank_Mitarbeiter mitarbeiter = new Datenbank_Mitarbeiter();
		LinkedList<Mitarbeiter> senderList = mitarbeiter.getAlleMitarbeiter(con);
		LinkedList<Mitarbeiter> empf�ngerList = mitarbeiter.getAlleMitarbeiter(con);
		
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Empf�nger, Sender, Best�tigungsstatus,"
				+ " Schichtnrsender, Schichtnrempf�nger, Tauschnr from Tauschanfrage";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Tauschanfrage> tauschanfrageList = new LinkedList<>();

			while (rs.next()) {
				Tauschanfrage tanf = new Tauschanfrage(rs.getString("Empf�nger"),rs.getString("Sender"), rs.getBoolean("Best�tigunsstatus")
						,rs.getInt("Schichtnrsender"), rs.getInt("Schichtnrempf�nger"), rs.getInt("Tauschnr"));

				for (Schicht sch : schichtsenderList) {
					if (sch.getSchichtnr() == tanf.getSchichtnrsender()) {
						tanf.setLinkedListSchichtensender(sch);
					}
				}
				for (Schicht sch : schichtempf�ngerList) {
					if (sch.getSchichtnr() == tanf.getSchichtnrempf�nger()) {
						tanf.setLinkedListSchichtenempf�nger(sch);
					}
				}
				for (Mitarbeiter ma : senderList) {
					if (ma.getBenutzername() == tanf.getSender()) {
						tanf.setLinkedListSender(ma);
					}
				}
				for (Mitarbeiter ma : empf�ngerList) {
					if (ma.getBenutzername() == tanf.getEmpf�nger()) {
						tanf.setLinkedListEmpf�nger(ma);
					}
				}
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
	 * Diese beinhaltet die zugeh�rigen Mitarbeiter und Schichten der Sender und Empf�nger.
	 * Es werden Mitarbeiter, Schicht Objekte welche dem Sender/Empf�nger entsprechen f�r jede Tauschanfrage gespeichert.
	 */
	protected Tauschanfrage getTauschanfrage(int tauschnr, Connection con ) {
		Datenbank_Schicht schicht = new Datenbank_Schicht();
		LinkedList<Schicht> schichtsenderList = schicht.getSchichten(con);
		LinkedList<Schicht> schichtempf�ngerList = schicht.getSchichten(con);
		
		Datenbank_Mitarbeiter mitarbeiter = new Datenbank_Mitarbeiter();
		LinkedList<Mitarbeiter> senderList = mitarbeiter.getAlleMitarbeiter(con);
		LinkedList<Mitarbeiter> empf�ngerList = mitarbeiter.getAlleMitarbeiter(con);
		
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Empf�nger, Sender, Best�tigungsstatus,"
				+ " Schichtnrsender, Schichtnrempf�nger, Tauschnr from Tauschanfrage WHERE tauschnr="+tauschnr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			

				rs.next();
				Tauschanfrage tanf = new Tauschanfrage(rs.getString("Empf�nger"),rs.getString("Sender"), rs.getBoolean("Best�tigunsstatus")
						,rs.getInt("Schichtnrsender"), rs.getInt("Schichtnrempf�nger"), rs.getInt("Tauschnr"));


				for (Schicht sch : schichtsenderList) {
					if (sch.getSchichtnr() == tanf.getSchichtnrsender()) {
						tanf.setLinkedListSchichtensender(sch);
					}
				}
				for (Schicht sch : schichtempf�ngerList) {
					if (sch.getSchichtnr() == tanf.getSchichtnrempf�nger()) {
						tanf.setLinkedListSchichtenempf�nger(sch);
					}
				}
				for (Mitarbeiter ma : senderList) {
					if (ma.getBenutzername() == tanf.getSender()) {
						tanf.setLinkedListSender(ma);
					}
				}
				for (Mitarbeiter ma : empf�ngerList) {
					if (ma.getBenutzername() == tanf.getEmpf�nger()) {
						tanf.setLinkedListEmpf�nger(ma);
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
	 * @info L�schen einer Tauschanfrage aus der Datenbank Tabelle Tauschanfrage
	 */
	protected boolean deleteTauschanfrage(int tauschnr, Connection con) {
		if (!checkTauschanfrage(tauschnr,con)){
			return false;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM Tauschanfrage WHERE Tauschnr= "+tauschnr;

		try {
			stmt = con.createStatement();
			stmt.execute(sqlQuery);
			
			con.commit();

			con.setAutoCommit(true);
			
		
		return true;
		}
		catch (SQLException sql) {
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
	 * @info Fragt die h�chste Tauschnr ab und erh�ht diese um 1, sodass bei neu Erstellung
	 * einer Tauschanfrage die n�chste Tauschnr vorliegt.
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
