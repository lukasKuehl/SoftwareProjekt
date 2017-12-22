package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Ma_Schicht;
import data.Schicht;
import data.Tag;
import data.TerminBlockierung;

class Datenbank_Schicht {


	/**
	 * @Thomas Friesen
	 * @info Die Methode fügt einen Datensatz in die Schicht Tabelle hinzu.
	 */
	public boolean addSchicht(Schicht schicht,Connection con) {
		boolean success = false;
		
		
		String sqlStatement;
		sqlStatement = "insert into Schicht(schichtnr, tbez, wpnr, anfanguhrzeit, endeuhrzeit) values(?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		Statement checkInput = null;
		ResultSet checkRS = null;
		int schichtnr = 0;
		String tbez = null;
		int wpnr = 0;
		String anfanguhrzeit = null;
		String endeuhrzeit = null;
		
		
		try {
			pstmt = con.prepareStatement(sqlStatement);

			//Auslesen der als Parameter übergebenen Mitarbeiter-Schicht-Beziehung
			schichtnr = schicht.getSchichtnr();
			tbez = schicht.getTbez();
			wpnr = schicht.getWpnr();
			anfanguhrzeit = schicht.getAnfanguhrzeit();
			endeuhrzeit = schicht.getEndeuhrzeit();
			
			con.setAutoCommit(false);

			if (checkSchicht(schichtnr,con)) {
				System.out.println("Diese schichtnr existiert bereits in der Tabelle Schicht!");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
			
				pstmt.setInt(1, schichtnr);
				pstmt.setString(2, tbez);
				pstmt.setInt(3, wpnr);
				pstmt.setString(4, anfanguhrzeit);
				pstmt.setString(5, endeuhrzeit);
			
				pstmt.execute();
				con.commit();	
				
			}			
			
			success = true;
			con.setAutoCommit(true);
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im späteren Programm entweder gar nicht oder vielleicht als Dialog(ausgelöst in der View weil der Rückgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einfügen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler beim Hinzufügen einer neuen Schicht:");
			System.out.println("Parameter: schichtnr = " + schichtnr);
			sql.printStackTrace();		
			
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addSchicht " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
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
				System.err.println("Methode addSchicht(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}

	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen Schichtnr eine Schicht gibt,
	 * bei existenz return true sonst false
	 */
	
	protected boolean checkSchicht(int schichtnr,Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select schichtnr from Schicht where schichtnr = " + schichtnr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkSchicht SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkSchicht (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}


	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller Schichten aus der Datenbank und erzeugen von Schicht Objekten.
	 * Diese werden in eine LinkedList abgelegt und ausgegeben.
	 * Die zugehörigen Mitarbeiter und Schicht Beziehungen werden als LinkedList in den Schichten gespeichert.
	 */
	protected LinkedList<Schicht> getSchichten(Connection con) {
		Datenbank_Ma_Schicht ma_schicht = new Datenbank_Ma_Schicht();
		LinkedList<Ma_Schicht> maschichtList = ma_schicht.getMa_Schicht(con);;
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Schichtnr,Tbez, wpnr, Anfanguhrzeit, Endeuhrzeit from Schicht";

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Schicht> schichtList = new LinkedList<>();

			while (rs.next()) {
				Schicht s = new Schicht(rs.getInt("Schichtnr"), rs.getString("Tbez"),
						rs.getInt("Wpnr"), rs.getString("Anfanguhrzeit").toString(),
						rs.getString("Endeuhrzeit").toString());

				for (Ma_Schicht mas : maschichtList) {
					if (mas.getSchichtnr() == s.getSchichtnr()) {
						s.setLinkedListMa_Schicht(mas);
					}
					}
				
				schichtList.add(s);
			}

			rs.close();
			stmt.close();

			return schichtList;

		} catch (SQLException sql) {
			System.err.println("Methode getSchicht SQL-Fehler: " + sql.getMessage());
			return null;
		}
	}
	
	/**
	 * @author Anes Preljevic
	 * @info Auslesen einer bestimmten Schicht aus der Datenbank und erzeugen eines Schicht Objektes,
	 * welches anschließend ausgegeben wird.
	 */
	protected Schicht getSchicht(int schichtnr,Connection con) {
		Datenbank_Ma_Schicht ma_schicht = new Datenbank_Ma_Schicht();
		LinkedList<Ma_Schicht> maschichtList = ma_schicht.getMa_Schicht(con);;
		if (!checkSchicht(schichtnr,con)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Schichtnr,Tbez, Anzschicht, Anfanguhrzeit, Endeuhrzeit from Schicht Where Schichtnr ="+schichtnr;

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);
			rs.next();
			Schicht s = new Schicht(rs.getInt("Schichtnr"), rs.getString("Tbez"),
					rs.getInt("Wpnr"), rs.getString("Anfanguhrzeit").toString(),
					rs.getString("Endeuhrzeit").toString());

				for (Ma_Schicht mas : maschichtList) {
					if (mas.getSchichtnr() == s.getSchichtnr()) {
						s.setLinkedListMa_Schicht(mas);
					}
					}

			rs.close();
			stmt.close();

			return s;

		} catch (SQLException sql) {
			System.err.println("Methode getSchicht SQL-Fehler: " + sql.getMessage());
			return null;
		}
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info Löschen einer Schicht mit zugehörigen Ma_Schicht (Mitarbeitern in Schichten)aus den Datenbank Tabellen 
	 * Schicht, Ma-Schicht.
	 */
	protected boolean deleteSchicht(int wpnr,Connection con) {
		Datenbank_Schicht schicht = new Datenbank_Schicht();
		LinkedList<Schicht> schichtList = schicht.getSchichten(con);
		Datenbank_Ma_Schicht masch = new Datenbank_Ma_Schicht();
		LinkedList<Ma_Schicht> maschichtList = masch.getMa_Schicht(con);;

		Statement stmt = null;
		ResultSet rs = null;
		String sqlStatement = "DELETE FROM Schicht WHERE wpnr= "+wpnr;
		for (Schicht sch : schichtList) {
			if (sch.getWpnr() == wpnr) {

		
		for (Ma_Schicht ms : maschichtList) {
			if (ms.getSchichtnr() == sch.getSchichtnr()) {
				masch.deleteMa_SchichtWochenplan(sch.getSchichtnr(),con);
			}
			}
		}
		}	
		try {
			stmt = con.createStatement();
			stmt.execute(sqlStatement);

			
			return true;
		} catch (SQLException sql) {
			System.err.println("Methode deleteSchicht SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteSchicht (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Fragt die höchste Schichtnr und erhöht diese um 1, sodass bei neu Erstellung
	 * einer Schicht die nächste Schichtnr vorliegt.
	 */
	protected  int getNewSchichtnr(Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select max(schichtnr)+1 from Schicht";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs.next();
			int maxSchichtnr = rs.getInt(1);
			rs.close();
			stmt.close();
			return maxSchichtnr;
		} catch (SQLException sql) {
			System.err.println("Methode getNewSchichtnrSQL-Fehler: "
					+ sql.getMessage());
			return -1;
		}
	}
}
