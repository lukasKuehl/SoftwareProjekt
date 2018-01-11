 package model;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.TreeMap;

import data.Schicht;
import data.Tag;


import data.Wochenplan;



/**
 * @author Thomas Friesen, Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und Änderung in der Datenbank im Bezug auf die Tabelle Wochenplan zu verarbeiten.
 */
class Datenbank_Wochenplan {
	//Initialisierung der Instanzvariablen
	private Einsatzplanmodel myModel = null;	
	
	/**
	 * @author Anes Preljevic
	 * @info Beim erstellen der Hilfsklasse soll das Einsatzplanmodel übergeben werden.
	 * Das soll vermeiden, dass die Datenbankverbindung häufiger erstellt wird, das Einsatzplanmodel unnötig öfter erstellt wird
	 * und die Hilfsklassen andere Model-Hilfsklassen übers Einsatzplanmodel nutzen können, was unnötigen Code entfernt und die Kopplung verringert.
	 */
	protected Datenbank_Wochenplan(Einsatzplanmodel myModel){
		this.myModel=myModel;
	}

	/**
	 * @Thomas Friesen
	 * @info Die Methode fügt einen Datensatz in die Wochenplan Tabelle hinzu.
	 */
	protected boolean addWochenplan(Wochenplan wochenplan,Connection con) {
		
		boolean success = false;
		boolean öffentlichstatus = false;
		PreparedStatement pstmt = null;
		Datenbank_Tag dtag= new Datenbank_Tag(myModel);
		int wpnr = 0;
		int Minanzinfot = 0;
		int Minanzinfow = 0;
		int Minanzkasse = 0;
		int Mehrbesetzung = 0;
		String öffnungszeit = null;
		String schließzeit = null;
		String hauptzeitbeginn = null;
		String hauptzeitende = null;
		String benutzername = null;
		String sqlStatement = "insert into WOCHENPLAN (Wpnr, Öffentlichkeitsstatus, Öffnungszeit, Schließzeit,"
				+ " Hauptzeitbeginn, Hauptzeitende, Benutzername, Minanzinfot, Minanzinfow,"
				+ " Minanzkasse, Mehrbesetzung ) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		
		try {
			//Erstellen des prepared Statement Objektes
			pstmt = con.prepareStatement(sqlStatement);

			//Auslesen der Parameter des übergebenen Wochenplan-Objektes
			wpnr= wochenplan.getWpnr();
			öffentlichstatus = wochenplan.isÖffentlichstatus();
			öffnungszeit = wochenplan.getÖffnungszeit();
			schließzeit = wochenplan.getSchließzeit();
			hauptzeitbeginn = wochenplan.getHauptzeitbeginn();
			hauptzeitende = wochenplan.getHauptzeitende();
			benutzername = wochenplan.getBenutzername();
			Minanzinfot = wochenplan.getMinanzinfot();
			Minanzinfow = wochenplan.getMinanzinfow();
			Minanzkasse = wochenplan.getMinanzkasse();
			Mehrbesetzung = wochenplan.getMehrbesetzung();
			
			//Änderung der Grundeinstellung von AutoCommit auf false
			con.setAutoCommit(false);

			//Prüfung des PK-Constraints
			if (checkWochenplan(wpnr,con)) {
				System.out.println("Der Wochenplan ist bereits in der Tabelle eingetragen");
			}
			//Prüfung des FK-COnstraints
			if (checkWochenplanFK(benutzername, con) == false){
				System.out.println("Der angegebene Benutzer existiert nicht in der Mitarbeitertabelle");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
				//Füllen des prepared Statement Objektes
				pstmt.setInt(1, wpnr);
				pstmt.setBoolean(2, öffentlichstatus);
				pstmt.setString(3, öffnungszeit);
				pstmt.setString(4, schließzeit);
				pstmt.setString(5, hauptzeitbeginn);
				pstmt.setString(6, hauptzeitende);
				pstmt.setString(7, benutzername);
				pstmt.setInt(8, Minanzinfot);
				pstmt.setInt(9, Minanzinfow);
				pstmt.setInt(10, Minanzkasse);
				pstmt.setInt(11, Mehrbesetzung);
			
				//Ausführen der SQL-Anweisung
				pstmt.execute();
				
				//Einfügen der Tage in den Wochenplan, da ein Wochenplan immer im Zusammenhang mit den dazugehörigenden Tagen erstellt wird
				dtag.addTag(new Tag ("Montag", wpnr, false), öffnungszeit, schließzeit, hauptzeitbeginn, hauptzeitende,con);
				dtag.addTag(new Tag ("Dienstag", wpnr, false), öffnungszeit, schließzeit, hauptzeitbeginn, hauptzeitende,con);
				dtag.addTag(new Tag ("Mittwoch", wpnr, false), öffnungszeit, schließzeit, hauptzeitbeginn, hauptzeitende,con);
				dtag.addTag(new Tag ("Donnerstag", wpnr, false), öffnungszeit, schließzeit, hauptzeitbeginn, hauptzeitende,con);
				dtag.addTag(new Tag ("Freitag", wpnr, false), öffnungszeit, schließzeit, hauptzeitbeginn, hauptzeitende,con);
				dtag.addTag(new Tag ("Samstag", wpnr, false), öffnungszeit, schließzeit, hauptzeitbeginn, hauptzeitende,con);
				con.commit();	
				
				success = true;
			}						
			// Änderung der AutoCommit Einstellung auf den Ursprung
			con.setAutoCommit(true);
			return success;
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im späteren Programm entweder gar nicht oder vielleicht als Dialog(ausgelöst in der View weil der Rückgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einfügen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Erstellung eines neuen Wochenplan-Datensatzes:");
			System.out.println("Parameter: Wochenplannummer = " + wpnr + " Öffentlich = " + öffentlichstatus +
					" Öffnungszeit: " + öffnungszeit + " Schließzeit: " + schließzeit + " Hauptzeitbeginn: " + hauptzeitbeginn +
					"Hauptzeitende: " + hauptzeitende + "Benutzer; " + benutzername +"Minanzinfot: " + Minanzinfot +
					"Minanzinfow: " + Minanzinfow + "Minanzkasse: " + Minanzkasse + "Mehrbesetzung: " + Mehrbesetzung);
			sql.printStackTrace();		
			
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
				return success;
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addWochenplan " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
				return success;
			}
		} finally {
			//Schließen der offen gebliebenen Statements & ResultSets
			try {
				if (pstmt != null){
					pstmt.close();
				}			
				
			} catch (SQLException e) {
				System.err.println("Methode addWochenplan(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		
	}
	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen Wochenplannr einen Wochenplan gibt,
	 * bei Existenz return true sonst false
	 */
	
	protected boolean checkWochenplan(int wpnr,Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		//Benötigten Sql-Befehlt speichern
		String sqlQuery = "select wpnr from Wochenplan where wpnr = " + wpnr;
		//Statement, Resultset wird erstellt und Sql-Befehl wird ausgeführt, schließend wird der 
		//nächste Datensatz aus dem Resultset ausgegeben
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf false setzen
			System.err.println("Methode checkWochenplan SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			//Schließen der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkWochenplan (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	
	/**
	 * @author Thomas Friesen
	 * @info Die Methode prüft, ob der Foreign-Key-Constraint der Tabelle TerminBlockierung eingehalten werden
	 */
	protected boolean checkWochenplanFK(String benutzername,Connection con) {
		boolean result = false;
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select benutzername from Mitarbeiter where benutzername = '"+ benutzername +"'" ;
		
		
		try {
			// Erstellen der Statement und Resultsetobjekte
				stmt = con.createStatement();
				rs = stmt.executeQuery(sqlQuery);
			
				//Prüfung der FK-Constraints
			if ((rs.next()) == true){
				result = true;
			}else{
				result = false;
			}
			
			
		} catch (SQLException sql) {
			System.err.println("Methode checkWochenplanFK SQL-Fehler: " + sql.getMessage());
			return false;
			
		} finally {
			//Schließen der offenen Statement und ResultSetobjekte
			try {
					if(rs != null){
						rs.close();
					
					if(stmt != null){
						stmt.close();
					}
						
				}
			} catch (SQLException e) {
				System.err.println("Methode checkWochenplanFK (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * @author Anes Preljevic
	 * @info Ändert den Öffentlichstatus auf den Wert der übergebenen Woche
	 */	
	protected void updateWochenplan(Wochenplan wochenplan,Connection con) {
		
		if(wochenplan!=null){
			int wpnr = wochenplan.getWpnr();
			boolean öffentlichstatus = wochenplan.isÖffentlichstatus();
		String sqlStatement;
		sqlStatement = "UPDATE WOCHENPLAN " + "SET Öffentlichkeitsstatus = ? WHERE Wpnr =" + wpnr;
		
		PreparedStatement pstmt = null;

		try {
			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
			//Füllen und erzeugen des Preparedstatements
			pstmt = con.prepareStatement(sqlStatement);
			pstmt.setBoolean(1, öffentlichstatus);
			//Ausführen des PSTMTS
			pstmt.executeUpdate();
			
			//Connection Zustand bestätigen und somit fest in die Datenbank schreiben
			con.commit();
		
		} catch (SQLException sql) {
			System.err.println("Methode updateWochenplan SQL-Fehler: " + sql.getMessage());
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
			} catch (SQLException sqlRollback) {
				System.err.println("Methode updateWochenplan " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				//Schließen der offen gebliebenen Statements
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode updateWochenplan (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Ändert den Öffentlichstatus auf true, bei der Woche mit der übergebenen Wochenplannr
	 */
	protected void setzeOeffentlichstatustrue(int wpnr,Connection con) {
		
		Statement stmt = null;
		String sqlStatement;
		
		//SQL-Statement Öffentlichkeitsstatus wird auf true gesetzt
		sqlStatement = "UPDATE WOCHENPLAN " + "SET Öffentlichkeitsstatus = true WHERE Wpnr =" + wpnr;
				
		try {

			stmt = con.createStatement();
			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
			// Ausführen des Preparedstatements
			stmt.executeUpdate(sqlStatement);
			//Connection Zustand bestätigen und somit fest in die Datenbank schreiben
			con.commit();


		} catch (SQLException sql) {
			//Connection Zustand bestätigen und somit fest in die Datenbank schreiben
			System.err.println("Methode setzeÖffentlichstatustrue SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
			} catch (SQLException sqlRollback) {
				System.err.println("Methode setzeÖffentlichstatustrue " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schließen der offen gebliebenen Statements
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode setzeÖffentlichstatustrue (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Ändert den Öffentlichstatus auf false, bei der Woche mit der übergebenen Wochenplannr
	 */
	
	protected void setzeOeffentlichstatusfalse(int wpnr,Connection con) {
		
		Statement stmt = null;
		String sqlStatement;
		//SQL-Statement Öffentlichkeitsstatus wird auf false gesetzt
		sqlStatement = "UPDATE WOCHENPLAN SET Öffentlichkeitsstatus = false WHERE Wpnr =" + wpnr;
		
		
		try {

			stmt = con.createStatement();
			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
		
			stmt.executeUpdate(sqlStatement);
			//Connection Zustand bestätigen und somit fest in die Datenbank schreiben
			con.commit();

		} catch (SQLException sql) {
			System.err.println("Methode setzeÖffentlichstatusfalse SQL-Fehler: " + sql.getMessage());
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				
			} catch (SQLException sqlRollback) {
				System.err.println("Methode setzeÖffentlichstatusfalse " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schließen der offen gebliebenen Statements
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode setzeÖffentlichstatusfalse (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller Wochenpläne aus der Datenbank und erzeugen von Wochenplan Objekten.
	 * Diese werden in eine TreeMap abgelegt. Die zugehörigen Tage  
	 * werden in einer LinkedList gespeichert.( in diesen werden zusätzlich die zugehörigen Schichten ausgelesen) 
	 * Diese Liste ist in der TreeMap enthalten welche außerdem den Ausgabewert darstellt.
	 */
	protected LinkedList<Wochenplan> getWochenplaene(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;
	
		//Benötigten Sql-Befehlt speichern
		String sqlStatement = "select Wpnr from Wochenplan";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Wochenplan> wochenplanList = new LinkedList<>();
			// Solange es einen "nächsten" Datensatz in dem Resultset gibt, mit den Daten des RS 
			// ein neues Wochenplan-Objekt erzeugen. Dieses wird anschließend der Liste hinzugefügt.
			while (rs.next()) {
				
				//Mit der Methode getWochenplan, für die ausgelesene wpnr Wochenplan-Objekte auslesen
				Wochenplan wp = myModel.getWochenplan(rs.getInt("Wpnr"));
				
				wochenplanList.add(wp);
			
			}

			return wochenplanList;

		} catch (SQLException sql) {
			System.err.println("Methode getWochenplaene SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schließen der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getWochenplaene (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		
		
	}
	/**
		* @author Anes Preljevic
		* @info Auslesen des Wochenplans mit der übergebenen Wochenplannr aus der Datenbank, erstellen eines Objektes Wochenplan mit den Daten aus der Datenbank.
		*  Die zugehörigen Tage werden in einer LinkedList gespeichert.( in diesen werden zusätzlich die zugehörigen Schichten ausgelesen) 
		* Diese Liste ist in dem Objekt enthalten welches außerdem den Ausgabewert darstellt.
		*/
	protected Wochenplan getWochenplan(int wpnr,Connection con) {

		LinkedList<Tag> tageList = myModel.getTage();;
			
		if (!checkWochenplan(wpnr,con)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;
	
		
		String sqlStatement = "select * from Wochenplan where Wpnr ="+wpnr;

		try {
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sqlStatement);
			//Datumsformat auf 0-23 und stunden:minuten setzen
			DateFormat df = new SimpleDateFormat("HH:mm");
			//Auch wenn es voraussichtlich nur einen Datensatz gibt, den nächsten Datensatz abrufen,
			//um 100% sicherheit zu haben
			
			rs.next();
				//Das Uhrzeit Forman von hh:mm:ss.00 auf hh:mm ändern
				Time dbHauptzeitbeginn = rs.getTime("Hauptzeitbeginn");
				Time dbHauptzeitende = rs.getTime("Hauptzeitende");			
				String hauptzeitbeginn = df.format(dbHauptzeitbeginn);
				String hauptzeitende =df.format(dbHauptzeitende);
				Time dbÖffnungszeit = rs.getTime("Öffnungszeit");
				Time dbSchließzeit = rs.getTime("Schließzeit");			
				String öffnungszeit = df.format(dbÖffnungszeit);
				String schließzeit =df.format(dbSchließzeit);
			
			//Da Mehrbesetzung nicht not null, muss geprüft werden ob das Resultset null ist,
			//damit kein Fehlerauftritt. Wenn Mehrb null ist wird automatisch der Wert 0 zugewiesen.
			int mehrb=rs.getInt("Mehrbesetzung");
			if (rs.wasNull())
				mehrb = 0;
				
				
				Wochenplan wp = new Wochenplan(rs.getInt("Wpnr"),rs.getBoolean("Öffentlichkeitsstatus"),öffnungszeit,schließzeit,
						hauptzeitbeginn, hauptzeitende,
						rs.getString("Benutzername"),rs.getInt("Minanzinfot"),
						rs.getInt("Minanzinfow"),rs.getInt("Minanzkasse"),mehrb);
				
				
				LinkedList<Tag> tagewp=new LinkedList<Tag>();
				for (Tag ta : tageList) {
					if (ta.getWpnr() == wp.getWpnr()) {					
						tagewp.add(ta);			
					}
				}
				wp.setLinkedListTage(tagewp);
				
			return wp;
		}
		catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
			System.err.println("Methode getWochenplan SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schließen der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getWochenplan (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		
			
		}
		}
	
	/**
	 * @author Anes Preljevic
	 * @info Löschen eines Wochenplans mit zugehörigen Tagen (schichten)  aus den Datenbank Tabellen 
	 * Wochenplan, Tag, Schicht, Ma-Schicht ( Schicht und Ma-Schicht werden über die Tag/Schicht - deleteMethode gelöscht).
	 */
	
	protected boolean deleteWochenplan(int wpnr,Connection con) {
		//Prüfen ob der Wochenplan existiert
		if (!checkWochenplan(wpnr, con)){
			
			return true;
		}
		else{
		Statement stmt = null;

		String sqlStatement = "DELETE FROM WOCHENPLAN WHERE Wpnr = " + wpnr;
			//löscht alle Tage mit der Wpnr des zu löschenden Wochenplans, verhinderung von
			//FK verletzung und Inkonsistenz.
			myModel.deleteTag(wpnr);
			
		try {
			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
			stmt = con.createStatement();
			stmt.execute(sqlStatement);
			con.commit();

			con.setAutoCommit(true);
			return true;
			}
			catch (SQLException sql) {
			System.err.println("Methode deleteWochenplan SQL-Fehler: " + sql.getMessage());
			try {
				
				con.rollback();
				con.setAutoCommit(true);
				return false;
			} catch (SQLException sqlRollback) {
					System.err.println("Methode deleteWochenplan " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
					return false;
				}
		} 
			finally {
			try {
				//Schließen der offen gebliebenen Statements

				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteWochenplan (finally) SQL-Fehler: " + e.getMessage());
			}
			}
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info Fragt die höchste Wpnr ab und erhöht diese um 1, sodass bei neu Erstellung
	 * eines Wochenplans die nächste Wpnr vorliegt.
	 */
	
	protected int getNewWpnr(Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select max(wpnr)+1 from Wochenplan";

		try {
			//Resultset- und Statement-Objekt erzeugen
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs.next();
			//Wenn kein Datensatz vorhanden ist, manuell den Startwert setzen
			if(rs.getInt(1)==0){
			int maxWpnr2=1000;
			return maxWpnr2;
			}
			else{
			//Speichern der nächsthöheren Schichtnr in maxSchichtnr
			int maxWpnr = rs.getInt(1);
			rs.close();
			stmt.close();
			//Ausgabe der neuen Wochenplannr(höchste wpnr+1)
			return maxWpnr;
			}
		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf -1 setzen
			System.err.println("Methode getNewWpnr SQL-Fehler: "
					+ sql.getMessage());
			return -1;
		}finally {
			//Schließen der offen gebliebenen Statements und Resultsets
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getNewWpnr (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
}

	