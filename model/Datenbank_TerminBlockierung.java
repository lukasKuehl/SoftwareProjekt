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



/**
 * @author Thomas Friesen, Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und Änderung in der Datenbank im Bezug auf die Tabelle TerminBlockierung zu verarbeiten.
 */
class Datenbank_TerminBlockierung {

	/**
	 * @Thomas Friesen
	 * @info  Fügt einen neuen Termin-Datensatz in die TerminBlockierung Tabelle hinzu.
	 */
	protected boolean addTerminBlockierung(TerminBlockierung terminBlockierung,Connection con) {
		boolean success = false;
		PreparedStatement pstmt = null;
		int tBlockNr = 0;
		String benutzername = null;
		String bbez = null;
		String anfangzeitraum = null;
		String endezeitraum = null;
		String anfanguhrzeit = null;
		String endeuhrzeit = null;
		String grund = null;
		String sqlStatement = "insert into Terminblockierung (tblocknr, benutzername, bbez, anfangzeitraum, endezeitraum, anfanguhrzeit, endeuhrzeit, grund) values(?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			//Erstellen des prepared Statement Objektes
			pstmt = con.prepareStatement(sqlStatement);

			//Auslesen der Parameter des übergebenen TerminBlockierung-Objektes
			tBlockNr = terminBlockierung.getTblocknr();
			benutzername = terminBlockierung.getBenutzername();	
			bbez = terminBlockierung.getBbez();
			anfangzeitraum = terminBlockierung.getAnfangzeitraum();
			endezeitraum = terminBlockierung.getEndezeitraum();
			anfanguhrzeit = terminBlockierung.getAnfanguhrzeit();
			endeuhrzeit = terminBlockierung.getEndeuhrzeit();
			grund = terminBlockierung.getGrund();
			
			// Da es sich um mehrere SQL-Anweisungen handelt, muss die Einstellung AutoCommit auf false gesetzt werden
			con.setAutoCommit(false);

			//Prüfung der PK-Constraints
			if (checkTerminBlockierung(tBlockNr,con)) {
				System.out.println("Der Termin wurde bereits in die TerminBlockierung-Tabelle eingetragen");
			}
			//Prüfung der FK-Constraints
			if (checkTerminBlockierungFK(benutzername,con)){
				System.out.println("Der Benutzername existiert nicht in der Mitarbeitertabelle");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
				//Füllen des prepared Statement Objektes
				pstmt.setInt(1, tBlockNr);
				pstmt.setString(2, benutzername);
				pstmt.setString(3, bbez);
				pstmt.setString(4, anfangzeitraum);
				pstmt.setString(5, endezeitraum);
				pstmt.setString(6, anfanguhrzeit);
				pstmt.setString(7, endeuhrzeit);
				pstmt.setString(8, grund);
			
				//Ausführen der SQL-Anweisung
				pstmt.execute();
				
				//Übertragung der Daten in die Datenbanktabelle TBlock_Tag und TerminBlockierung
				con.commit();
				
				success = true;
			}			
			//AutoCommit Grundeinstellung wieder aktivieren
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
			//Statement, Resultset wird erstellt und Sql-Befehl wird ausgeführt, schließend wird der 
			//nächste Datensatz aus dem Resultset ausgegeben
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf false setzen
			System.err.println("Methode checkTerminBlockierung SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			//Schließen der offen gebliebenen Resultsets und Statements
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
	 * @author Thomas Friesen
	 * @info Die Methode prüft, ob der Foreign-Key-Constraint der Tabelle TerminBlockierung eingehalten werden
	 */
	protected boolean checkTerminBlockierungFK(String benutzername,Connection con) {
		boolean result = false;
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select benutzername from Mitarbeiter where benutzername = '"+ benutzername +"'" ;
		
		try {
			// Erstellen der Statement und Resultsetobjekte
				stmt = con.createStatement();
				rs = stmt.executeQuery(sqlQuery);
			
			//Prüfung des FK-Constraints
			if ((rs.next()) == true){
				result = true;
			}else{
				result = false;
			}
			
			
		} catch (SQLException sql) {
			System.err.println("Methode checkTerminBlockierungFK SQL-Fehler: " + sql.getMessage());
			return false;
			
		} finally {
			//Schließen der offenen Resultset- und Statementobjekte
			try {
					if(rs != null){
						rs.close();
					
					if(stmt != null){
						stmt.close();
					}
						
				}
			} catch (SQLException e) {
				System.err.println("Methode checkTerminBlockierungFK (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return result;
	}

	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller TerminBlockierungen aus der Datenbank und erzeugen von TerminBlockierung Objekten.
	 * Diese werden in eine LinkedList abgelegt und ausgegeben.
	 */
	protected LinkedList<TerminBlockierung> getTerminBlockierungen(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;
		//Benötigten Sql-Befehlt speichern
		String sqlStatement = "select * from TerminBlockierung";

		try {
			//Statement,Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			//LinkedList erstellen, in dieser werden die  TerminBlockierung-Objekte gespeichert
			LinkedList<TerminBlockierung> terminBlockierungList = new LinkedList<TerminBlockierung>();
			// Solange es einen "nächsten" Datensatz in dem Resultset gibt, mit den Daten des RS 
			// ein neues TerminBlockierung-Objekt erzeugen. Dieses wird anschließend der Liste hinzugefügt.
			while (rs.next()) {
				
				//neues TerminBLockierung-Objekt erzeugen, Daten aus dem Resultset ziehen
				TerminBlockierung tb = new TerminBlockierung(rs.getInt("Tblocknr"),rs.getString("Benutzername"), rs.getString("Bbez"),
						rs.getString("Anfangzeitraum"), rs.getString("Endezeitraum"),
						rs.getTime("Anfanguhrzeit").toString(),rs.getTime("Endeuhrzeit").toString(),rs.getString("Grund") );

				terminBlockierungList.add(tb);
			}

			rs.close();
			stmt.close();

			return terminBlockierungList;

		} catch (SQLException sql) {
			System.err.println("Methode getTerminBlockierungen SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getTerminBlockierung(finally) SQL-Fehler: " + e.getMessage());
			}
		}

	}
	/**
	 * @author Anes Preljevic
	 * @info Löschen der TerminBlockierung aus der Tabelle TerminBlockierung in der Datenbank,
	 * welche die übergebene Tblocknr besitzt. Sowie die zugehörigen child Datensätze Tblock_Tag.
	 */
	protected boolean deleteTerminBlockierung(int tblocknr,Connection con) {
		Datenbank_Tblock_Tag tblock_tag = new Datenbank_Tblock_Tag();
		
		Statement stmt = null;
	
		String sqlQuery = "DELETE FROM TerminBlockierung WHERE Tblocknr = "+tblocknr;
		//Löschen der child Beziehungen
		tblock_tag.deleteTblock_Tag(tblocknr,con);
			
		try {
			stmt = con.createStatement();
			stmt.execute(sqlQuery);
			return true;
		} catch (SQLException sql) {
				//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf false setzen
				System.err.println("Methode deleteTerminBlockierung(finally) SQL-Fehler: " + sql.getMessage());
				return false;
			}finally {
				try {
					//Schließen der offen gebliebenen Statements

					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					System.err.println("Methode deleteTblock_Tag (finally) SQL-Fehler: " + e.getMessage());
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
		//Benötigten Sql-Befehlt speichern
		String sqlQuery = "select max(tblocknr)+1 from TerminBlockierung";

		try {
			//Resultset- und Statement-Objekt erzeugen
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs.next();
			//Speichern der nächsthöheren Tblocknr in maxTblocknr
			int maxTblocknr = rs.getInt(1);
			rs.close();
			stmt.close();
			//Ausgabe der neuen Tblocknr
			return maxTblocknr;
		} catch (SQLException sql) {
			System.err.println("Methode getNewTblocknr SQL-Fehler: "
					+ sql.getMessage());
			return -1;
		}finally {
			//Schließen der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getNewTblocknr (finally) SQL-Fehler: " + e.getMessage());
			}
		}

	}
}
