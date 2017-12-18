package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Ma_Schicht;

 class Datenbank_Ma_Schicht {

	Datenbank_Connection db_con = new  Datenbank_Connection();
	Connection con = db_con.getCon();



	/**
	 * @Thomas Friesen
	 * @info Die Methode f�gt einen Datensatz in die Ma_Schicht Tabelle ein.
	 */
	public boolean addMa_Schicht(Ma_Schicht ma_schicht) {
		boolean success = false;
	
		String sqlStatement;
		sqlStatement = "insert into Ma_Schicht (Schichtnr,Benutzername) values(?, ?)";
		PreparedStatement pstmt = null;
		Statement checkInput = null;
		ResultSet checkRS = null;
		int schichtnr = 0;
		String benutzername = null;
		
		
		try {
			pstmt = con.prepareStatement(sqlStatement);

			//Auslesen der als Parameter �bergebenen Mitarbeiter-Schicht-Beziehung
			schichtnr = ma_schicht.getSchichtnr();
			benutzername = ma_schicht.getBenutzername();		
			
			con.setAutoCommit(false);

			if (checkMa_Schicht(schichtnr, benutzername)) {
				System.out.println("Der Mitarbeiter wurde bereits in die Schicht eingeteilt!");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
			
				pstmt.setInt(1, schichtnr);
				pstmt.setString(2, benutzername);
			
				pstmt.execute();
				con.commit();	
				
			}			
			
			success = true;
			con.setAutoCommit(true);
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im sp�teren Programm entweder gar nicht oder vielleicht als Dialog(ausgel�st in der View weil der R�ckgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einf�gen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Zuordnung eines Mitarbeiters zu einer Schicht:");
			System.out.println("Parameter: Schichtnr = " + schichtnr + " Mitarbeiter = " + benutzername);
			sql.printStackTrace();		
			
			try {
				//Zur�cksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addMa_Schicht " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
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
				System.err.println("Methode addMa_Schicht(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}

	/**
	 * @author Anes Preljevic
	 * @info Pr�ft ob es zu der eingegebenen schichtnr und dem benutzernamen bereits einen Mitarbeiter in der Schicht gibt, bei existenz return true, sonst false
	 */
	protected boolean checkMa_Schicht(int schichtnr, String benutzername) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select schichtnr, benutzernaeme from Ma_Schicht where schichtnr = '"+schichtnr+"' and benutzername= '"+benutzername+"'";

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
	 * @author Anes Preljevic
	 * @info Auslesen der Mitarbeiter mit den zugeh�rigen Schichten aus der Datenbank und hinzuf�gen in eine Liste, welche den Ausgabewert darstellt 
	 */
	protected LinkedList<Ma_Schicht> getMa_Schicht() {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Schichtnr, Benutzername from Ma_Schicht";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Ma_Schicht> ma_schichtList = new LinkedList<>();

			while (rs.next()) {
				Ma_Schicht ms = new Ma_Schicht(sqlStatement,0);

				ms.setSchichtnr(rs.getInt("Schichtnr"));
				ms.setBenutzername(rs.getString("Benutzername"));
				
				
				ma_schichtList.add(ms);
			}

			rs.close();
			stmt.close();

			return ma_schichtList;

		} catch (SQLException sql) {
			return null;
		}
	}

	/**
	* @author Anes Preljevic
	* @info Auslesen der Mitarbeiter die in eine Schicht geh�ren mit der �bergebenen Schichtnr aus der Datenbank,
	*  erstellen eines Objektes Ma_Schicht mit den Daten aus der Datenbank. Liste mit den Ma_Schicht Objekten
	*  wird ausgegeben.
	*/
	protected LinkedList<Ma_Schicht> getMitarbeiterausderSchicht(int schichtnr) {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "SELECT Schichtnr, Benutzername from Ma_Schicht WHERE schichtnr="+schichtnr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Ma_Schicht> ma_schichtList = new LinkedList<>();

			while (rs.next()) {
				Ma_Schicht ms = new Ma_Schicht(sqlStatement,0);

				ms.setSchichtnr(rs.getInt("Schichtnr"));
				ms.setBenutzername(rs.getString("Benutzername"));
				
				
				ma_schichtList.add(ms);
			}

			rs.close();
			stmt.close();

			return ma_schichtList;

		} catch (SQLException sql) {
			return null;
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info L�schen eines Mitarbeiters aus einer Schicht
	 */
	protected boolean deleteMa_Schicht(int schichtnr, String benutzername) {
		if(!checkMa_Schicht(schichtnr, benutzername)){
			return false;
		}
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM Ma_Schicht WHERE Schichtnr = "+schichtnr+" and Benutzername= "+benutzername+"";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return true;
		} catch (SQLException sql) {
			return false;
		} finally {
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
	/**
	 * @author Anes Preljevic
	 * @info L�scht den Ma_Schicht Datensatz aus der Tabelle Ma_Schicht, mit dem die �bergebene Schichtnr �bereinstimmt,
	 * wenn die Schicht gel�scht wurde.
	 */
	protected boolean deleteMa_SchichtWochenplan(int schichtnr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM Ma_Schicht WHERE Schichtnr = "+schichtnr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return true;
		} catch (SQLException sql) {
			return false;
		} finally {
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
