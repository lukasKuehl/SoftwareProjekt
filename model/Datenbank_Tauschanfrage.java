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
	 * @info  F�gt eine neue Tauschanfrage in der Tabelle Tauschanfrage hinzu
	 */
	protected void addTauschanfrage(Tauschanfrage tauschanfrage) {

		String sqlStatement;
		sqlStatement = "insert into Tauchanfrage (empf�nger, sender, best�tigungsstatus,"
				+ " schichtnrsender, schichtnrempf�nger, tauschnr) values(?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(sqlStatement);

			String empf�nger = tauschanfrage.getEmpf�nger();
			String sender = tauschanfrage.getSender();;
			boolean best�tigungsstatus= tauschanfrage.isBest�tigungsstatus();
			int schichtnrsender = tauschanfrage.getSchichtnrsender();
			int schichtnrempf�nger = tauschanfrage.getSchichtnrempf�nger();
			int tauschnr = tauschanfrage.getTauschnr();
			
			con.setAutoCommit(false);

			if (checkTauschanfrage(tauschnr)) {
				deleteTauschanfrage(tauschnr);
				addTauschanfrage(tauschanfrage);
			} else {
				pstmt.setString(1, empf�nger);
				pstmt.setString(2, sender);
				pstmt.setBoolean(3, best�tigungsstatus);
				pstmt.setInt(4, schichtnrsender);
				pstmt.setInt(5,schichtnrempf�nger);
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
	 * @info Pr�ft ob es zu der eingegebenen tauschnr eine Tauschtanfrage gibt, bei existenz return true sonst false.
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
	 * @info �ndert den Best�tigungsstatus der �bergebenen Tauschanfrage
	 */
	protected void updateTauschanfrage(Tauschanfrage tauschanfrage) {


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
	protected void best�tigeTauschanfrage(int tauschnr) {


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
	 * @info Auslesen der Tauschanfragen aus der Datenbank und hinzuf�gen in eine Liste, welche den Ausgabewert darstellt 
	 */
	protected LinkedList<Tauschanfrage> getTauschanfrage() {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Empf�nger, Sender, Best�tigungsstatus,"
				+ " Schichtnrsender, Schichtnrempf�nger, Tauschnr from Tauschanfrage";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Tauschanfrage> tauschanfrageList = new LinkedList<>();

			while (rs.next()) {
				Tauschanfrage tanf = new Tauschanfrage(sqlStatement, sqlStatement, false, 0, 0, 0);

				tanf.setEmpf�nger(rs.getString("Empf�nger"));
				tanf.setSender(rs.getString("Sender"));
				tanf.setBest�tigungsstatus(rs.getBoolean("Best�tigunsstatus"));
				tanf.setSchichtnrsender(rs.getInt("Schichtnrsender"));
				tanf.setSchichtnrempf�nger(rs.getInt("Schichtnrempf�nger"));
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
	 * @info L�schen einer Tauschanfrage aus der Datenbank Tabelle Tauschanfrage
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
	 * @info Fragt die h�chste Tauschnr ab und erh�ht diese um 1, sodass bei neu Erstellung
	 * einer Tauschanfrage die n�chste Tauschnr vorliegt.
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
