package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Userrecht;

class Datenbank_Userrecht {

	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();




	/**
	 * @author Anes Preljevic
	 * @info Ändert die Benutzerrolle auf den Wert des übergebenen Userrechts.
	 */
	
	
	protected void updateUserrecht(Userrecht userrecht) {

		String job= userrecht.getJob();
		String benutzerrolle=userrecht.getBenutzerrolle();
		String sqlStatement;
		sqlStatement = "UPDATE Userrecht " + "SET Benutzerrolle = ? " + "WHERE Job =" + job;
				
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(sqlStatement);

			con.setAutoCommit(false);
			pstmt.setString(1, job);
			pstmt.setString(2, benutzerrolle);

			pstmt.executeUpdate();
			con.commit();

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode updateUserrecht SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode updateUserrecht " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode updateUserrecht (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Ändert die Benutzerrolle von allen Mitarbeitern mit dem Job Kassenbüro oder Chef auf Admin.
	 */
	
	protected void setzeBenutzerrolleAdmin() {

		Statement stmt = null;
		String sqlStatement;
		sqlStatement = "UPDATE Userrecht SET Benutzerrolle = Admin WHERE Job = Kassenbüro or Job = Chef";
				
		

		try {
			stmt = con.createStatement();
			con.setAutoCommit(false);
			stmt.executeUpdate(sqlStatement);
			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode setzeBenutzerrolleAdmin SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode setzeBenutzerrolleAdmin " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode updateUserrecht (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	}

