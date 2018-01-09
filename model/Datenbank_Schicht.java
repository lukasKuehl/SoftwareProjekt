package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import data.Ma_Schicht;
import data.Mitarbeiter;
import data.Schicht;
import data.Tag;
import data.Tauschanfrage;
import data.TerminBlockierung;

/**
 * @author Thomas Friesen, Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und Änderung in der Datenbank im Bezug auf die Tabelle Schicht zu verarbeiten.
 */

class Datenbank_Schicht {
	
	//Initialisierung der Instanzvariablen
	private Einsatzplanmodel myModel = null;	
	
	/**
	 * @author Anes Preljevic
	 * @info Beim erstellen der Hilfsklasse soll das Einsatzplanmodel übergeben werden.
	 * Das soll vermeiden, dass die Datenbankverbindung häufiger erstellt wird, das Einsatzplanmodel unnötig öfter erstellt wird
	 * und die Hilfsklassen andere Model-Hilfsklassen übers Einsatzplanmodel nutzen können, was unnötigen Code entfernt und die Kopplung verringert.
	 */
	protected Datenbank_Schicht(Einsatzplanmodel myModel){
		this.myModel=myModel;
	}

	/**
	 * @Thomas Friesen
	 * @info Die Methode fügt einen Datensatz in die Schicht Tabelle hinzu.
	 */
	protected boolean addSchicht(Schicht schicht,Connection con) {
		
		boolean success = false;
		int schichtnr = 0;
		int wpnr = 0;
		PreparedStatement pstmt = null;
		String tbez = null;
		String anfanguhrzeit = null;
		String endeuhrzeit = null;
		String sqlStatement = "insert into Schicht(schichtnr, tbez, wpnr, anfanguhrzeit, endeuhrzeit) values(?, ?, ?, ?, ?)";
		
		
		try {
			//Erstellen eines prepared Statements
			pstmt = con.prepareStatement(sqlStatement);

			//Auslesen der als Parameter übergebenen Mitarbeiter-Schicht-Beziehung
			schichtnr = schicht.getSchichtnr();
			tbez = schicht.getTbez();
			wpnr = schicht.getWpnr();
			anfanguhrzeit = schicht.getAnfanguhrzeit();
			endeuhrzeit = schicht.getEndeuhrzeit();
		

			//Überprüfung der PK-Constraints
			if (checkSchicht(schichtnr,con)) {
				System.out.println("Diese schichtnr existiert bereits in der Tabelle Schicht!");
			}
			//Überprüfung der FK-Constraints
			if (checkSchichtFK(tbez,wpnr,con) == false){
				System.out.println("Die übergebenen Parameter verletztn die Foreign-Key-Constraints der Schichttabelle");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
				
				pstmt.setInt(1, schichtnr);
				pstmt.setString(2, tbez);
				pstmt.setInt(3, wpnr);
				pstmt.setString(4, anfanguhrzeit);
				pstmt.setString(5, endeuhrzeit);
			
				//Ausführen der SQL-Anweisung
				pstmt.execute();
				
				success = true;
			}			
			
			
			
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im späteren Programm entweder gar nicht oder vielleicht als Dialog angezeigt werden
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
		//Benötigten Sql-Befehlt speichern
		String sqlQuery = "select schichtnr from Schicht where schichtnr = " + schichtnr;

		try {
			//Statement, Resultset wird erstellt und Sql-Befehl wird ausgeführt, schließend wird der 
			//nächste Datensatz aus dem Resultset ausgegeben
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf false setzen
			System.err.println("Methode checkSchicht SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			//Schließen der offen gebliebenen Resultsets und Statements
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
	 * @author Thomas Friesen
	 * @info Die Methode prüft, ob die Foreign Keys der Tabelle Schicht eingehalten werden
	 */
	protected boolean checkSchichtFK(String tbez, int wpnr,Connection con) {
		boolean result = false;
		Statement stmt = null;;
		ResultSet rs = null;
		String sqlQuery = "select tbez,wpnr from Tag where tbez = '"+ tbez + "' and wpnr =" +wpnr ;
		
		try {
			// Erstellen des Statement- und Resultsetobjektes
				stmt = con.createStatement();
				rs = stmt.executeQuery(sqlQuery);
			
			//Überprüfung der FK-COnstraints
			if ((rs.next()) == true){
				result = true;
			}else{
				result = false;
			}
			
		} catch (SQLException sql) {
			System.err.println("Methode checkSchichtFK SQL-Fehler: " + sql.getMessage());
			return false;
			
		} finally {
			//Schließen des offenen Statement- und Resultsetobjektes
				try {
					if(rs != null){
						rs.close();
					}
					if(stmt != null){
						stmt.close();
					}
						
				
			} catch (SQLException e) {
				System.err.println("Methode checkSchichtFK (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return result;
	}
	
	
	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller Schichten aus der Datenbank und Erzeugen von Schicht-Objekten.
	 * Diese werden in einer LinkedList abgelegt und ausgegeben.
	 * Die zugehörigen Mitarbeiter einer Schicht werden als LinkedList in den Schichten gespeichert.
	 */
	protected LinkedList<Schicht> getSchichten(Connection con) {
		
		Statement stmt = null;
		ResultSet rs = null;
		//Benötigten Sql-Befehlt speichern
		String sqlStatement = "select schichtnr from Schicht";

		try {
			//Statement,Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Schicht> schichtList = new LinkedList<Schicht>();
			// Solange es einen "nächsten" Datensatz in dem Resultset gibt, mit den Daten des RS 
			// ein neues Schicht-Objekt erzeugen. Dieses wird anschließend der Liste hinzugefügt.
			while (rs.next()) {
				Schicht s = myModel.getSchicht(rs.getInt("Schichtnr"));
		
				schichtList.add(s);
			}

			//Liste mit Schicht-Objekten zurückgeben
			return schichtList;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
			System.err.println("Methode getSchichten SQL-Fehler: " + sql.getMessage());
			return null;
		}
		finally {
			//Schließen der offen gebliebenen Statements und Resultsets
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getSchichten(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		
	}
	
	/**
	 * @author Anes Preljevic
	 * @info Auslesen einer bestimmten Schicht aus der Datenbank und erzeugen eines Schicht Objektes,
	 * welches anschließend ausgegeben wird.
	 *  Die zugehörigen Mitarbeiter einer Schicht werden als LinkedList in den Schichten gespeichert.
	 */
	protected Schicht getSchicht(int schichtnr,Connection con) {

		LinkedList<Ma_Schicht> maschichtList = myModel.getMa_Schicht();
		//Prüfen ob die Schicht vorhanden ist
		if (!checkSchicht(schichtnr,con)){
			return null;
		}
		
		else{
		Statement stmt = null;
		ResultSet rs = null;
		//Benötigten Sql-Befehlt speichern
		
		String sqlStatement = "select * from Schicht Where Schichtnr ="+schichtnr;

		try {
			
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			
			//Datumsformat auf 0-23 und stunden:minuten setzen
			DateFormat df = new SimpleDateFormat("HH:mm");
			//Auch wenn es voraussichtlich nur einen Datensatz gibt, den nächsten Datensatz abrufen,
			//um 100% sicherheit zu haben
			rs.next() ;

				Time dbanfangUhrzeit = rs.getTime("Anfanguhrzeit");
				Time dbendUhrzeit = rs.getTime("Endeuhrzeit");			
				String anfangUhrzeit = df.format(dbanfangUhrzeit);
				String endUhrzeit =df.format(dbendUhrzeit);
			
				Schicht s = new Schicht(rs.getInt("Schichtnr"), rs.getString("Tbez"),
					rs.getInt("Wpnr"), anfangUhrzeit,endUhrzeit);
				
				//Alle Ma_Schicht Datensätze durchsuchen, welche zu der aktuellen Schicht gehören
				//Über die Ma_Schicht beziehung den Benutzernamen des Mitarbeiters herausfinden
				//Mit diesem Benutzernamen ein Mitarbeiter-Objekt erzeugen und dieses in der Mitarbeiterliste
				//der Schicht speichern
			LinkedList<Mitarbeiter> mal= new LinkedList<>();
				for (Ma_Schicht mas : maschichtList) {
					
					if (mas.getSchichtnr() == s.getSchichtnr()) {
						Mitarbeiter masch = myModel.getMitarbeiter(mas.getBenutzername());
						
						mal.add(masch);
						s.setLl_mitarbeiter(mal);
					}
					}

				//Schicht-Objekt zurückgeben
			return s;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
			System.err.println("Methode getSchicht SQL-Fehler: " + sql.getMessage());
			return null;
		}		finally {
			//Schließen der offen gebliebenen Statements und Resultsets
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getSchicht(finally) SQL-Fehler: " + e.getMessage());
			}
			}
		}	
	}

	
	/**
	 * @author Anes Preljevic
	 * @info Löschen der Schichten eines Wochenplans mit zugehörigen Tauschanfragen und Ma_Schicht (Mitarbeitern in Schichten)aus den Datenbank Tabellen 
	 * Schicht, Ma-Schicht.
	 */
	protected boolean deleteSchichtvonWp(int wpnr,Connection con) {

		LinkedList<Tauschanfrage> tauschList = myModel.getTauschanfragen();
		LinkedList<Schicht> schichtList = myModel.getSchichten();
		LinkedList<Ma_Schicht> maschichtList = myModel.getMa_Schicht();;

		Statement stmt = null;
		
		String sqlStatement = "DELETE FROM Schicht WHERE wpnr= "+wpnr;
		
			
		//Löschen Aller child Datensätze um die Foreignkeys in der Datenbank nicht zu verletzen
		//Alle Schichten durchsuchen, um die betroffenen Schichten herauszufinden
		for (Schicht sch : schichtList) {
			if (sch.getWpnr() == wpnr) {
				//Alle Tauschanfragen die sich auf die Schicht bezogen haben werden gelöscht
				for (Tauschanfrage tausch : tauschList) {
					if (tausch.getSchichtnrsender() == sch.getSchichtnr()||tausch.getSchichtnrempfänger() == sch.getSchichtnr()) {
						myModel.deleteTauschanfrage(tausch.getTauschnr());
					}
				}
				//Alle Ma_Schicht Datensätze mit der zu löschenden Schicht werden gelöscht
				for (Ma_Schicht ms : maschichtList) {
					if (ms.getSchichtnr() == sch.getSchichtnr()) {
						myModel.deleteMa_SchichtWochenplan(sch.getSchichtnr());
					}
				}
			}
		}	
		try {
			stmt = con.createStatement();
			stmt.execute(sqlStatement);

			
			return true;
		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf false setzen
			System.err.println("Methode deleteSchichtvonwp SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			//Schließen der offen gebliebenen Statements
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteSchichtvonwp (finally) SQL-Fehler: " + e.getMessage());
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
		//Benötigten Sql-Befehlt speichern
		String sqlQuery = "select max(schichtnr)+1 from Schicht";

		try {
			//Resultset- und Statement-Objekt erzeugen
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs.next();
			//Speichern der nächsthöheren Schichtnr in maxSchichtnr
			int maxSchichtnr = rs.getInt(1);
			
			//Ausgabe der neuen Schichtnr
			return maxSchichtnr;
		} catch (SQLException sql) {
			System.err.println("Methode getNewSchichtnrSQL-Fehler: "
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
				System.err.println("Methode getNewSchichtnr (finally) SQL-Fehler: " + e.getMessage());
			}
		}

	}
}
