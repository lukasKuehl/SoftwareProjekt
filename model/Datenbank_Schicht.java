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
 * @info Die Klasse dient dazu, jegliche Abfragen und �nderung in der Datenbank im Bezug auf die Tabelle Schicht zu verarbeiten.
 */

class Datenbank_Schicht {
	
	//Initialisierung der Instanzvariablen
	private Einsatzplanmodel myModel = null;	
	
	/**
	 * @author Anes Preljevic
	 * @info Beim erstellen der Hilfsklasse soll das Einsatzplanmodel �bergeben werden.
	 * Das soll vermeiden, dass die Datenbankverbindung h�ufiger erstellt wird, das Einsatzplanmodel unn�tig �fter erstellt wird
	 * und die Hilfsklassen andere Model-Hilfsklassen �bers Einsatzplanmodel nutzen k�nnen, was unn�tigen Code entfernt und die Kopplung verringert.
	 */
	protected Datenbank_Schicht(Einsatzplanmodel myModel){
		this.myModel=myModel;
	}

	/**
	 * @Thomas Friesen
	 * @info Die Methode f�gt einen Datensatz in die Schicht Tabelle hinzu.
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

			//Auslesen der als Parameter �bergebenen Mitarbeiter-Schicht-Beziehung
			schichtnr = schicht.getSchichtnr();
			tbez = schicht.getTbez();
			wpnr = schicht.getWpnr();
			anfanguhrzeit = schicht.getAnfanguhrzeit();
			endeuhrzeit = schicht.getEndeuhrzeit();
		

			//�berpr�fung der PK-Constraints
			if (checkSchicht(schichtnr,con)) {
				System.out.println("Diese schichtnr existiert bereits in der Tabelle Schicht!");
			}
			//�berpr�fung der FK-Constraints
			if (checkSchichtFK(tbez,wpnr,con) == false){
				System.out.println("Die �bergebenen Parameter verletztn die Foreign-Key-Constraints der Schichttabelle");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
				
				pstmt.setInt(1, schichtnr);
				pstmt.setString(2, tbez);
				pstmt.setInt(3, wpnr);
				pstmt.setString(4, anfanguhrzeit);
				pstmt.setString(5, endeuhrzeit);
			
				//Ausf�hren der SQL-Anweisung
				pstmt.execute();
				
				success = true;
			}			
			
			
			
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im sp�teren Programm entweder gar nicht oder vielleicht als Dialog angezeigt werden
			System.out.println("Fehler beim Einf�gen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler beim Hinzuf�gen einer neuen Schicht:");
			System.out.println("Parameter: schichtnr = " + schichtnr);
			sql.printStackTrace();		
			
			try {
				//Zur�cksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addSchicht " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schlie�en der offen gebliebenen Statements & ResultSets
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
	 * @info Pr�ft ob es zu der eingegebenen Schichtnr eine Schicht gibt,
	 * bei existenz return true sonst false
	 */
	
	protected boolean checkSchicht(int schichtnr,Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		//Ben�tigten Sql-Befehlt speichern
		String sqlQuery = "select schichtnr from Schicht where schichtnr = " + schichtnr;

		try {
			//Statement, Resultset wird erstellt und Sql-Befehl wird ausgef�hrt, schlie�end wird der 
			//n�chste Datensatz aus dem Resultset ausgegeben
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf false setzen
			System.err.println("Methode checkSchicht SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			//Schlie�en der offen gebliebenen Resultsets und Statements
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
	 * @info Die Methode pr�ft, ob die Foreign Keys der Tabelle Schicht eingehalten werden
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
			
			//�berpr�fung der FK-COnstraints
			if ((rs.next()) == true){
				result = true;
			}else{
				result = false;
			}
			
		} catch (SQLException sql) {
			System.err.println("Methode checkSchichtFK SQL-Fehler: " + sql.getMessage());
			return false;
			
		} finally {
			//Schlie�en des offenen Statement- und Resultsetobjektes
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
	 * Die zugeh�rigen Mitarbeiter einer Schicht werden als LinkedList in den Schichten gespeichert.
	 */
	protected LinkedList<Schicht> getSchichten(Connection con) {
		
		Statement stmt = null;
		ResultSet rs = null;
		//Ben�tigten Sql-Befehlt speichern
		String sqlStatement = "select schichtnr from Schicht";

		try {
			//Statement,Resultset wird erstellt, der Sql-Befehl wird ausgef�hrt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Schicht> schichtList = new LinkedList<Schicht>();
			// Solange es einen "n�chsten" Datensatz in dem Resultset gibt, mit den Daten des RS 
			// ein neues Schicht-Objekt erzeugen. Dieses wird anschlie�end der Liste hinzugef�gt.
			while (rs.next()) {
				Schicht s = myModel.getSchicht(rs.getInt("Schichtnr"));
		
				schichtList.add(s);
			}

			//Liste mit Schicht-Objekten zur�ckgeben
			return schichtList;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf null setzen
			System.err.println("Methode getSchichten SQL-Fehler: " + sql.getMessage());
			return null;
		}
		finally {
			//Schlie�en der offen gebliebenen Statements und Resultsets
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
	 * welches anschlie�end ausgegeben wird.
	 *  Die zugeh�rigen Mitarbeiter einer Schicht werden als LinkedList in den Schichten gespeichert.
	 */
	protected Schicht getSchicht(int schichtnr,Connection con) {

		LinkedList<Ma_Schicht> maschichtList = myModel.getMa_Schicht();
		//Pr�fen ob die Schicht vorhanden ist
		if (!checkSchicht(schichtnr,con)){
			return null;
		}
		
		else{
		Statement stmt = null;
		ResultSet rs = null;
		//Ben�tigten Sql-Befehlt speichern
		
		String sqlStatement = "select * from Schicht Where Schichtnr ="+schichtnr;

		try {
			
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgef�hrt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			
			//Datumsformat auf 0-23 und stunden:minuten setzen
			DateFormat df = new SimpleDateFormat("HH:mm");
			//Auch wenn es voraussichtlich nur einen Datensatz gibt, den n�chsten Datensatz abrufen,
			//um 100% sicherheit zu haben
			rs.next() ;

				Time dbanfangUhrzeit = rs.getTime("Anfanguhrzeit");
				Time dbendUhrzeit = rs.getTime("Endeuhrzeit");			
				String anfangUhrzeit = df.format(dbanfangUhrzeit);
				String endUhrzeit =df.format(dbendUhrzeit);
			
				Schicht s = new Schicht(rs.getInt("Schichtnr"), rs.getString("Tbez"),
					rs.getInt("Wpnr"), anfangUhrzeit,endUhrzeit);
				
				//Alle Ma_Schicht Datens�tze durchsuchen, welche zu der aktuellen Schicht geh�ren
				//�ber die Ma_Schicht beziehung den Benutzernamen des Mitarbeiters herausfinden
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

				//Schicht-Objekt zur�ckgeben
			return s;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf null setzen
			System.err.println("Methode getSchicht SQL-Fehler: " + sql.getMessage());
			return null;
		}		finally {
			//Schlie�en der offen gebliebenen Statements und Resultsets
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
	 * @info L�schen der Schichten eines Wochenplans mit zugeh�rigen Tauschanfragen und Ma_Schicht (Mitarbeitern in Schichten)aus den Datenbank Tabellen 
	 * Schicht, Ma-Schicht.
	 */
	protected boolean deleteSchichtvonWp(int wpnr,Connection con) {

		LinkedList<Tauschanfrage> tauschList = myModel.getTauschanfragen();
		LinkedList<Schicht> schichtList = myModel.getSchichten();
		LinkedList<Ma_Schicht> maschichtList = myModel.getMa_Schicht();;

		Statement stmt = null;
		
		String sqlStatement = "DELETE FROM Schicht WHERE wpnr= "+wpnr;
		
			
		//L�schen Aller child Datens�tze um die Foreignkeys in der Datenbank nicht zu verletzen
		//Alle Schichten durchsuchen, um die betroffenen Schichten herauszufinden
		for (Schicht sch : schichtList) {
			if (sch.getWpnr() == wpnr) {
				//Alle Tauschanfragen die sich auf die Schicht bezogen haben werden gel�scht
				for (Tauschanfrage tausch : tauschList) {
					if (tausch.getSchichtnrsender() == sch.getSchichtnr()||tausch.getSchichtnrempf�nger() == sch.getSchichtnr()) {
						myModel.deleteTauschanfrage(tausch.getTauschnr());
					}
				}
				//Alle Ma_Schicht Datens�tze mit der zu l�schenden Schicht werden gel�scht
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
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf false setzen
			System.err.println("Methode deleteSchichtvonwp SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			//Schlie�en der offen gebliebenen Statements
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
	 * @info Fragt die h�chste Schichtnr und erh�ht diese um 1, sodass bei neu Erstellung
	 * einer Schicht die n�chste Schichtnr vorliegt.
	 */
	protected  int getNewSchichtnr(Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		//Ben�tigten Sql-Befehlt speichern
		String sqlQuery = "select max(schichtnr)+1 from Schicht";

		try {
			//Resultset- und Statement-Objekt erzeugen
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs.next();
			//Speichern der n�chsth�heren Schichtnr in maxSchichtnr
			int maxSchichtnr = rs.getInt(1);
			
			//Ausgabe der neuen Schichtnr
			return maxSchichtnr;
		} catch (SQLException sql) {
			System.err.println("Methode getNewSchichtnrSQL-Fehler: "
					+ sql.getMessage());
			return -1;
		}finally {
			//Schlie�en der offen gebliebenen Resultsets und Statements
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
