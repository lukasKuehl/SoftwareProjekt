package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import data.TerminBlockierung;

class Datenbank_TerminBlockierung {

	/**
	 * @Thomas Friesen
	 * @info  Fügt einen neuen Termin-Datensatz in die TerminBlockierung Tabelle hinzu.
	 */
	protected boolean addTerminBlockierung(TerminBlockierung terminBlockierung,Connection con) {
		boolean success = false;
	
		
		String sqlStatement;
		sqlStatement = "insert into Terminblockierung (tblocknr, benutzername, bbez, anfangzeitraum, endezeitraum, anfanguhrzeit, endeuhrzeit, grund) values(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		Statement checkInput = null;
		ResultSet checkRS = null;
		int tBlockNr = 0;
		String benutzername = null;
		String bbez = null;
		String anfangzeitraum = null;
		String endezeitraum = null;
		String anfanguhrzeit = null;
		String endeuhrzeit = null;
		String grund = null;
		
		
		try {
			pstmt = con.prepareStatement(sqlStatement);

			//Auslesen der als Parameter übergebenen Mitarbeiter-Schicht-Beziehung
			tBlockNr = terminBlockierung.getTblocknr();
			benutzername = terminBlockierung.getBenutzername();	
			bbez = terminBlockierung.getBbez();
			anfangzeitraum = terminBlockierung.getAnfangzeitraum();
			endezeitraum = terminBlockierung.getEndezeitraum();
			anfanguhrzeit = terminBlockierung.getAnfanguhrzeit();
			endeuhrzeit = terminBlockierung.getEndeuhrzeit();
			grund = terminBlockierung.getGrund();
			
			con.setAutoCommit(false);

			if (checkTerminBlockierung(tBlockNr,con)) {
				System.out.println("Der Termin wurde bereits in die TerminBlockierung-Tabelle eingetragen");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
			
				pstmt.setInt(1, tBlockNr);
				pstmt.setString(2, benutzername);
				pstmt.setString(3, bbez);
				pstmt.setString(4, anfangzeitraum);
				pstmt.setString(5, endezeitraum);
				pstmt.setString(6, anfanguhrzeit);
				pstmt.setString(7, endeuhrzeit);
				pstmt.setString(8, grund);
			
				pstmt.execute();
				con.commit();	
				
			}			
			
			success = true;
			con.setAutoCommit(true);
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im späteren Programm entweder gar nicht oder vielleicht als Dialog(ausgelöst in der View weil der Rückgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einfügen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Erstellung eines neuen TerminBlockierung-Datensatzes:");
			System.out.println("Parameter: tBlockNr = " + tBlockNr + " Mitarbeiter = " + benutzername +
					" Bezeichnung: " + bbez + " Anfangzeitraum: " + anfangzeitraum + " Endezeitraum: " + endezeitraum +
					"Anfanguhrzeit: " + anfanguhrzeit + "Endeuhrzeit; " + endeuhrzeit +"Grund: " + grund);
			sql.printStackTrace();		
			
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTerminBlockierung " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
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
				System.err.println("Methode addTerminBlockierung(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}

	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen Tblocknr eine TerminBlockierung gibt,
	 * bei existenz return true sonst false
	 */
	protected boolean checkTerminBlockierung(int tblocknr,Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select tblocknr from TerminBlockierung where tblocknr = " + tblocknr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkTerminBlockierung SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkTerminBlockierung (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}


	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller TerminBlockierungen aus der Datenbank und erzeugen von TerminBlockierung Objekten.
	 * Diese werden in eine LinkedList abgelegt und ausgegeben.
	 */
	protected LinkedList<TerminBlockierung> getTerminBlockierungen(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Tblocknr, Benutzername, Bbez, Anfangzeitraum, Endezeitraum,Anfanguhrzeit, Endeuhrzeit, Grund from TerminBlockierung";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<TerminBlockierung> terminBlockierungList = new LinkedList<>();
			DateFormat df = new SimpleDateFormat("DD-MM-YYYY");
			while (rs.next()) {
				Date anfangzeitraumsql = rs.getDate("Anfangzeitraum"); 
				Date endezeitraumsql = rs.getDate("Endezeitraum"); 
				String anfangzeitraum = df.format(anfangzeitraumsql);
				String endezeitraum = df.format(endezeitraumsql);
				TerminBlockierung tb = new TerminBlockierung(rs.getInt("Tblocknr"),rs.getString("Benutzername"), rs.getString("Bbez"),
						anfangzeitraum, endezeitraum, rs.getTime("Anfanguhrzeit").toString(),rs.getTime("Endeuhrzeit").toString(),rs.getString("Grund") );

				terminBlockierungList.add(tb);
			}

			rs.close();
			stmt.close();

			return terminBlockierungList;

		} catch (SQLException sql) {
			System.err.println("Methode getTerminBlockierungen SQL-Fehler: " + sql.getMessage());
			return null;
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Löschen der TerminBlockierung aus der Tabelle TerminBlockierung in der Datenbank,
	 * welche die übergebene Tblocknr besitzt.
	 */
	protected boolean deleteTerminBlockierung(int tblocknr,Connection con) {
		Datenbank_Tblock_Tag tblock_tag = new Datenbank_Tblock_Tag();
		
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM TerminBlockierung WHERE Tblocknr = "+tblocknr;
		tblock_tag.deleteTblock_Tag(tblocknr,con);
		try {
			stmt = con.createStatement();
			stmt.execute(sqlQuery);
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
				System.err.println("Methode deleteTerminBlockierung(finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Fragt die höchste Tblocknr ab und erhöht diese um 1, sodass bei neu Erstellung
	 * einer TerminBlockierung die nächste Tblocknr vorliegt.
	 */
	
	protected  int getNewTblocknr(Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select max(tblocknr)+1 from TerminBlockierung";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs.next();
			int maxTblocknr = rs.getInt(1);
			rs.close();
			stmt.close();
			return maxTblocknr;
		} catch (SQLException sql) {
			System.err.println("Methode getNewTblocknr SQL-Fehler: "
					+ sql.getMessage());
			return -1;
		}
	}
}
