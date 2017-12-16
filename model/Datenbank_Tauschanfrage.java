package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Tauschanfrage;

class Datenbank_Tauschanfrage {



	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();




	/**
	 * @Anes Preljevic 
	 * @info  Fügt eine neue Tauschanfrage in der Tabelle Tauschanfrage hinzu
	 */
	protected void addTauschanfrage(Tauschanfrage tauschanfrage) {

		String sqlStatement;
		sqlStatement = "insert into Tauchanfrage (empfänger, sender, bestätigungsstatus,"
				+ " schichtnrsender, schichtnrempfänger, tauschnr) values(?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(sqlStatement);

			String empfänger = tauschanfrage.getEmpfänger();
			String sender = tauschanfrage.getSender();;
			boolean bestätigungsstatus= tauschanfrage.isBestätigungsstatus();
			int schichtnrsender = tauschanfrage.getSchichtnrsender();
			int schichtnrempfänger = tauschanfrage.getSchichtnrempfänger();
			int tauschnr = tauschanfrage.getTauschnr();
			
			con.setAutoCommit(false);

			if (checkTauschanfrage(tauschnr)) {
				deleteTauschanfrage(tauschnr);
				addTauschanfrage(tauschanfrage);
			} else {
				pstmt.setString(1, empfänger);
				pstmt.setString(2, sender);
				pstmt.setBoolean(3, bestätigungsstatus);
				pstmt.setInt(4, schichtnrsender);
				pstmt.setInt(5,schichtnrempfänger);
				pstmt.setInt(6, tauschnr);
				pstmt.execute();
				con.commit();
			}

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode addTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode addTauschanfrage(finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	/**
	 * @Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen tauschnr eine Tauschtanfrage gibt, bei existenz return true sonst false.
	 */
	private boolean checkTauschanfrage(int tauschnr) {
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
	protected void updateTauschanfrage(Tauschanfrage tauschanfrage) {


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
	protected void bestätigeTauschanfrage(int tauschnr) {


		String sqlStatement;
		sqlStatement = "UPDATE Tauschanfrage " + "SET Bestätigunsstatus = true " + "WHERE Tauschnr =" + tauschnr;
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
	 * @info Auslesen der Tauschanfragen aus der Datenbank und hinzufügen in eine Liste, welche den Ausgabewert darstellt 
	 */
	protected LinkedList<Tauschanfrage> getTauschanfrage() {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Empfänger, Sender, Bestätigungsstatus,"
				+ " Schichtnrsender, Schichtnrempfänger, Tauschnr from Tauschanfrage";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Tauschanfrage> tauschanfrageList = new LinkedList<>();

			while (rs.next()) {
				Tauschanfrage tanf = new Tauschanfrage(sqlStatement, sqlStatement, false, 0, 0, 0);

				tanf.setEmpfänger(rs.getString("Empfänger"));
				tanf.setSender(rs.getString("Sender"));
				tanf.setBestätigungsstatus(rs.getBoolean("Bestätigunsstatus"));
				tanf.setSchichtnrsender(rs.getInt("Schichtnrsender"));
				tanf.setSchichtnrempfänger(rs.getInt("Schichtnrempfänger"));
				tanf.setTauschnr(rs.getInt("Tauschnr"));
				
				tauschanfrageList.add(tanf);
			}

			rs.close();
			stmt.close();

			return tauschanfrageList;

		} catch (SQLException sql) {
			return null;
		}
	}

	/**
	 * @Anes Preljevic
	 * @info Löschen einer Tauschanfrage aus der Datenbank Tabelle Tauschanfrage
	 */
	protected void deleteTauschanfrage(int tauschnr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM Tauschanfrage WHERE Tauschnr= "+tauschnr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			
			con.commit();

			con.setAutoCommit(true);
			
		} catch (SQLException sql) {
			System.err.println("Methode deleteTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				
				con.rollback();
				con.setAutoCommit(true);
			
			} catch (SQLException sqlRollback) {
					System.err.println("Methode deleteTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
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
	/**
	 * @author Anes Preljevic
	 * @info Fragt die höchste Tauschnr ab und erhöht diese um 1, sodass bei neu Erstellung
	 * einer Tauschanfrage die nächste Tauschnr vorliegt.
	 */
	protected  int getNewTauschnr() {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select max (tauschnr)+1 from Tauschanfrage";

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
