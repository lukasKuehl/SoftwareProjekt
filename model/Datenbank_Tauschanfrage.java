package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Mitarbeiter;
import data.Schicht;
import data.Tauschanfrage;
import data.Tblock_Tag;


/**
 * @author Thomas Friesen, Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und Änderung in der Datenbank im Bezug auf die Tabelle Tauschanfrage zu verarbeiten.
 */
class Datenbank_Tauschanfrage {
	

	/**
	 * @Thomas Friesen
	 * @info  Fügt eine neue Tauschanfrage in der Tabelle Tauschanfrage hinzu
	 */
	protected boolean addTauschanfrage(int tauschNr, String senderName, int senderSchichtNr, String empfaengerName, int empfaengerSchichtNr,Connection con ) {
		boolean success = false;
		boolean bestaetigestatus = false;
		PreparedStatement pstmt = null;
		String sqlStatement = "insert into Tauschanfrage(empfänger, sender, bestätigungsstatus,schichtnrsender, schichtnrempfänger, tauschnr) values(?, ?, ?, ?, ?, ?)";
				
		try {
			//Erstellen des prepared Statement Objektes
			pstmt = con.prepareStatement(sqlStatement);
			
			//Überprüfen des PK-Check-Constraints
			if (checkTauschanfrage(tauschNr, con)) {
				System.out.println("Die Tauschnummer befindet sich bereits in der Datenbank!");
			//Überprüfung des FK-Check-Constraints
			}if ((checkTauschanfrageFK(senderName,senderSchichtNr,empfaengerName,empfaengerSchichtNr,con) == false)){
				System.out.println("Der Foreign-Key Constraint  der Tauschanfrage Tabelle wurde verletzt!");
			}
			
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
				//Ausfüllen des prepared Statement Objektes
				pstmt.setString(1, empfaengerName);
				pstmt.setString(2, senderName);
				pstmt.setBoolean(3,bestaetigestatus);
				pstmt.setInt(4,senderSchichtNr);
				pstmt.setInt(5, empfaengerSchichtNr);
				pstmt.setInt(6, tauschNr);
				
				//Ausführen der SQL-Anweisung
				pstmt.execute();
				
				//Übertragung der Daten in die Datenbank
				con.commit();	
				
				success = true;
				
			}			
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im späteren Programm entweder gar nicht oder vielleicht als Dialog(ausgelöst in der View weil der Rückgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einfügen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Zuordnung einer Tauschnummer zu einer Tauschanfrage:");
			System.out.println("Parameter: TauschNr = " + tauschNr);
			sql.printStackTrace();		
			
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schließen der offen gebliebenen Statements & ResultSets
			try {
				if (pstmt != null){
					pstmt.close();
				}			
				
			} catch (SQLException e) {
				System.err.println("Methode addTauschanfrage(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}
	/**
	 * @Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen tauschnr eine Tauschtanfrage gibt, bei existenz return true sonst false.
	 */
	protected boolean checkTauschanfrage(int tauschnr,Connection con) {
		
		Statement stmt = null;
		ResultSet rs = null;
		//Benötigten Sql-Befehlt speichern
		String sqlQuery = "select tauschnr from Tauschanfrage where tauschnr = " + tauschnr;

		try {
			//Statement, Resultset wird erstellt und Sql-Befehl wird ausgeführt, schließend wird der 
			//nächste Datensatz aus dem Resultset ausgegeben
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf false setzen
			System.err.println("Methode checkTauschanfrageSQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				//Schließen der offen gebliebenen Resultsets und Statements
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
	 * @author Thomas Friesen
	 * @info Die Methode prüft, ob die Foreign Keys der Tabelle Ma_Schicht eingehalten werden
	 */
	protected boolean checkTauschanfrageFK(String senderName, int senderSchichtNr, String empfaengerName, int empfaengerSchichtNr,Connection con) {
		
		boolean result = false;
		Statement[] stmt = new Statement[4];
		ResultSet[] rs = new ResultSet[4];
		String sqlQuery[] = new String[4];
		sqlQuery[0] = "select schichtnr from Schicht where schichtnr = "+ senderSchichtNr ;
		sqlQuery[1] = "select benutzername from Mitarbeiter where benutzername = '" + senderName + "'";
		sqlQuery[2] = "select schichtnr from Schicht where schichtnr = "+ empfaengerSchichtNr ;
		sqlQuery[3] = "select benutzername from Mitarbeiter where benutzername = '" + empfaengerName + "'";
		
	
		try {
			//Erstellen der Statement- und Resultsetobjekte
			for (int i=0; i<=3;i++){
				stmt[i] = con.createStatement();
				rs[i] = stmt[i].executeQuery(sqlQuery[i]);
			}
			//Prüfung, ob alle FK-COnstraints eingehalten werden
			if ((rs[0].next()) == true && (rs[1].next())== true && (rs[2].next())== true && (rs[3].next())== true){
				result = true;
			}else{
				result = false;
			}
			
		} catch (SQLException sql) {
			System.err.println("Methode checkMa_SchichtFK SQL-Fehler: " + sql.getMessage());
			return false;
		
		} finally {
			try {
				//Schließen der offenen Resultset und Statementobjekte
				for(int i =0; i<=3;i++){
					if(rs[i] != null){
						rs[i].close();
					}
					if(stmt[i] != null){
						stmt[i].close();
					}
				}
			} catch (SQLException e) {
				System.err.println("Methode checkMa_SchichtFK (finally) SQL-Fehler: " + e.getMessage());
			}
			
		}
		return result;
	}
	
	/**
	 * @Anes Preljevic
	 * @info Ändert den Bestätigungsstatus der übergebenen Tauschanfrage
	 */
	protected void updateTauschanfrage(Tauschanfrage tauschanfrage,Connection con) {


		boolean bestätigungsstatus = tauschanfrage.isBestätigungsstatus();
		int tauschnr = tauschanfrage.getTauschnr();

		String sqlStatement;
		sqlStatement = "UPDATE Tauschanfrage " + "SET Bestätigunsstatus = ? " + "WHERE Tauschnr =" + tauschnr;
				
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(sqlStatement);

			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
			//Preparedstatement füllen und ausführen
			pstmt.setBoolean(1, bestätigungsstatus);
			pstmt.executeUpdate();
			
			//Connection Zustand bestätigen und somit fest in die Datenbank schreiben
			con.commit();


		} catch (SQLException sql) {
			System.err.println("Methode updateTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();			
				
			} catch (SQLException sqlRollback) {
				System.err.println("Methode updateTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				//Schließen der offen gebliebenen Preparedstatements
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode updateTauschanfarage (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Ändert den Bestätigungsstatus der übergebenen Tauschanfrage
	 */
	protected void bestätigeTauschanfrage(String empfänger , int tauschnr,Connection con) {


		String sqlStatement;
		sqlStatement = "UPDATE Tauschanfrage SET Bestätigungsstatus = true WHERE empfänger='"+empfänger+"' and Tauschnr =" + tauschnr;
		Statement stmt = null;

		try {

			stmt = con.createStatement();
			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
			//Preparedstatement ausführen
			stmt.executeUpdate(sqlStatement);
			
			//Connection Zustand bestätigen und somit fest in die Datenbank schreiben
			con.commit();

		} catch (SQLException sql) {
			System.err.println("Methode bestätigeTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
			} catch (SQLException sqlRollback) {
				System.err.println("Methode bestätigeTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schließen der offen gebliebenen Statements
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
	 * @info Auslesen der Tauschanfragen aus der Datenbank und hinzufügen in eine Liste, welche den Ausgabewert darstellt.
	 * Diese beinhaltet die zugehörigen Mitarbeiter und Schichten der Sender und Empfänger.
	 * Es werden Mitarbeiter, Schicht Objekte welche dem Sender/Empfänger entsprechen für jede Tauschanfrage gespeichert.
	 */
	protected LinkedList<Tauschanfrage> getTauschanfragen(Connection con) {
		Datenbank_Tauschanfrage tauschanfrage= new Datenbank_Tauschanfrage();

			
		Statement stmt = null;
		ResultSet rs = null;

		//Benötigten Sql-Befehlt speichern
		String sqlStatement = "select Tauschnr from Tauschanfrage";

		try {
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Tauschanfrage> tauschanfrageList = new LinkedList<>();
			// Solange es einen "nächsten" Datensatz in dem Resultset gibt, mit den Daten des RS 
			// ein neues Tauschanfrage-Objekt erzeugen. Dieses wird anschließend der Liste hinzugefügt.
			while (rs.next()) {
				Tauschanfrage tanf = tauschanfrage.getTauschanfrage(rs.getInt("Tauschnr"),con);

				tauschanfrageList.add(tanf);
			}
			//Liste Tauschanfrage-Objekten zurückgeben
			return tauschanfrageList;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
			System.err.println("Methode getTauschanfragen SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schließen der offen gebliebenen Statements und Resultsets
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getTauschanfragen (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @Anes Preljevic
	 * @info Auslesen einer bestimmten Tauschanfragen aus der Datenbank, Speicherung im Objekt Tauschanfrage welches den Ausgabewert darstellt.
	 * Diese beinhaltet die zugehörigen Mitarbeiter und Schichten der Sender und Empfänger.
	 * Es werden Mitarbeiter, Schicht Objekte welche dem Sender/Empfänger entsprechen für jede Tauschanfrage gespeichert.
	 */
	protected Tauschanfrage getTauschanfrage(int tauschnr, Connection con ) {
		Datenbank_Schicht schicht= new Datenbank_Schicht();
		Datenbank_Mitarbeiter mitarbeiter= new Datenbank_Mitarbeiter();
		LinkedList<Schicht> schichtList = schicht.getSchichten(con);
		LinkedList<Mitarbeiter> mitarbeiterList = mitarbeiter.getAlleMitarbeiter(con);
		
		//Prüfen ob der Tauschanfrage vorhanden ist
		if (!checkTauschanfrage(tauschnr, con)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;

		//Benötigten Sql-Befehlt speichern
		String sqlStatement = "select * from Tauschanfrage WHERE tauschnr="+tauschnr;

		try {
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			
			//Auch wenn es voraussichtlich nur einen Datensatz gibt, den nächsten Datensatz abrufen,
			//um 100% sicherheit zu haben
				rs.next();
				Tauschanfrage tanf = new Tauschanfrage(rs.getString("Empfänger"),rs.getString("Sender"), rs.getBoolean("Bestätigungsstatus")
						,rs.getInt("Schichtnrsender"), rs.getInt("Schichtnrempfänger"), rs.getInt("Tauschnr"));
				
					//Durchsucht alle Mitarbeiter nach dem Sender, Mitarbeiter Objekt des Senders wird
					// im Tauschanfrage Objekt gespeichert.
					Mitarbeiter sender = null;
					for (Mitarbeiter ma : mitarbeiterList) {
						if (ma.getBenutzername().equals(tanf.getSender())) {
							sender=ma;	
						}
					}
					tanf.setMaSender(sender);
	
					//Durchsucht alle Mitarbeiter nach dem Empfänger, Mitarbeiter Objekt des Empfängers wird
					// im Tauschanfrage Objekt gespeichert.
					Mitarbeiter empfänger = null ;
					for (Mitarbeiter ma : mitarbeiterList) {
						if (ma.getBenutzername().equals(tanf.getEmpfänger())) {
							empfänger= ma;
						}
					}
					tanf.setMaEmpfänger(empfänger);
	
	
					//Durchsucht alle Schichten nach der Schichtnrsender, das Schicht-Objekt mit der entsprechenden Schichtnr
					// wird im Tauschanfrage Objekt gespeichert.
					Schicht schichtnrSender = null;
					for (Schicht sch : schichtList) {
						if (sch.getSchichtnr()==tanf.getSchichtnrsender()) {
							schichtnrSender =sch;
						}
					}
					tanf.setSchtSchichtensender(schichtnrSender);
	
	
					//Durchsucht alle Schichten nach der Schichtnrempfänger, das Schicht-Objekt mit der entsprechenden Schichtnr
					// wird im Tauschanfrage Objekt gespeichert.
					Schicht schichtnrEmpfänger = null;
					for (Schicht sch : schichtList) {
						if (sch.getSchichtnr() == tanf.getSchichtnrempfänger()) {
							schichtnrEmpfänger= sch;
						}
					}
					tanf.setSchtSchichtenempfänger(schichtnrEmpfänger);

			
			//Tauschanfrage-Objekt zurückgeben
			return tanf;
		
		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
			System.err.println("Methode getTauschanfrage SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schließen der offen gebliebenen Statements und Resultsets
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getTauschanfrage (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		}
		
	}
	/**
	 * @Anes Preljevic
	 * @info Löschen einer Tauschanfrage aus der Datenbank Tabelle Tauschanfrage
	 */
	protected boolean deleteTauschanfrage(int tauschnr, Connection con) {
		//Überprüfen ob der zu löschende Datensatz existiert
		if (checkTauschanfrage(tauschnr,con)==false){

		//System.out.println("Tauschanfrage kann nicht gelöscht werden, da nicht vorhanden");	
		return true;
		
	}
	else{
		Statement stmt = null;
		
		//Benötigten Sql-Befehlt speichern
		String sqlStatement = "DELETE FROM Tauschanfrage WHERE Tauschnr= "+tauschnr;

		try {
			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
			//Sql-Statement ausführen
			stmt = con.createStatement();
			stmt.execute(sqlStatement);
			//Connection Zustand bestätigen und somit fest in die Datenbank schreiben
			con.commit();

		
			
		
		return true;
		}catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf false setzen
			System.err.println("Methode deleteTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				
				return false;
			} catch (SQLException sqlRollback) {
					System.err.println("Methode deleteTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
					return false;
				}
		} finally {
			//Schließen der offen gebliebenen Statements
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteTauschanfrage (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		
	}
		
	}
	/**
	 * @author Anes Preljevic
	 * @info Fragt die höchste Tauschnr ab und erhöht diese um 1, sodass bei neu Erstellung
	 * einer Tauschanfrage die nächste Tauschnr vorliegt.
	 */
	protected  int getNewTauschnr(Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		//Benötigten Sql-Befehlt speichern
		String sqlQuery = "select max(tauschnr)+1 from Tauschanfrage";

		try {
			//Resultset- und Statement-Objekt erzeugen
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs.next();
			//Speichern der nächsthöheren Tauschnr in maxTauschnr
			int maxTauschnr = rs.getInt(1);
			rs.close();
			stmt.close();
			//Ausgabe der neuen Tauschnr
			return maxTauschnr;
		} catch (SQLException sql) {
			System.err.println("Methode getNewTauschnr SQL-Fehler: "
					+ sql.getMessage());
			return -1;
		} finally {
			//Schließen der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getNewTauschnr (finally) SQL-Fehler: " + e.getMessage());
			}
		}

	}
}
