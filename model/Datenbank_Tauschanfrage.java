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
 * @info Die Klasse dient dazu, jegliche Abfragen und �nderung in der Datenbank im Bezug auf die Tabelle Tauschanfrage zu verarbeiten.
 */
class Datenbank_Tauschanfrage {
	

	/**
	 * @Thomas Friesen
	 * @info  F�gt eine neue Tauschanfrage in der Tabelle Tauschanfrage hinzu
	 */
	protected boolean addTauschanfrage(int tauschNr, String senderName, int senderSchichtNr, String empfaengerName, int empfaengerSchichtNr,Connection con ) {
		boolean success = false;
		boolean bestaetigestatus = false;
		PreparedStatement pstmt = null;
		String sqlStatement = "insert into Tauschanfrage(empf�nger, sender, best�tigungsstatus,schichtnrsender, schichtnrempf�nger, tauschnr) values(?, ?, ?, ?, ?, ?)";
				
		try {
			//Erstellen des prepared Statement Objektes
			pstmt = con.prepareStatement(sqlStatement);
			
			//�berpr�fen des PK-Check-Constraints
			if (checkTauschanfrage(tauschNr, con)) {
				System.out.println("Die Tauschnummer befindet sich bereits in der Datenbank!");
			//�berpr�fung des FK-Check-Constraints
			}if ((checkTauschanfrageFK(senderName,senderSchichtNr,empfaengerName,empfaengerSchichtNr,con) == false)){
				System.out.println("Der Foreign-Key Constraint  der Tauschanfrage Tabelle wurde verletzt!");
			}
			
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
				//Ausf�llen des prepared Statement Objektes
				pstmt.setString(1, empfaengerName);
				pstmt.setString(2, senderName);
				pstmt.setBoolean(3,bestaetigestatus);
				pstmt.setInt(4,senderSchichtNr);
				pstmt.setInt(5, empfaengerSchichtNr);
				pstmt.setInt(6, tauschNr);
				
				//Ausf�hren der SQL-Anweisung
				pstmt.execute();
				
				//�bertragung der Daten in die Datenbank
				con.commit();	
				
				success = true;
				
			}			
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im sp�teren Programm entweder gar nicht oder vielleicht als Dialog(ausgel�st in der View weil der R�ckgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einf�gen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Zuordnung einer Tauschnummer zu einer Tauschanfrage:");
			System.out.println("Parameter: TauschNr = " + tauschNr);
			sql.printStackTrace();		
			
			try {
				//Zur�cksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schlie�en der offen gebliebenen Statements & ResultSets
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
	 * @info Pr�ft ob es zu der eingegebenen tauschnr eine Tauschtanfrage gibt, bei existenz return true sonst false.
	 */
	protected boolean checkTauschanfrage(int tauschnr,Connection con) {
		
		Statement stmt = null;
		ResultSet rs = null;
		//Ben�tigten Sql-Befehlt speichern
		String sqlQuery = "select tauschnr from Tauschanfrage where tauschnr = " + tauschnr;

		try {
			//Statement, Resultset wird erstellt und Sql-Befehl wird ausgef�hrt, schlie�end wird der 
			//n�chste Datensatz aus dem Resultset ausgegeben
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf false setzen
			System.err.println("Methode checkTauschanfrageSQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			try {
				//Schlie�en der offen gebliebenen Resultsets und Statements
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
	 * @info Die Methode pr�ft, ob die Foreign Keys der Tabelle Ma_Schicht eingehalten werden
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
			//Pr�fung, ob alle FK-COnstraints eingehalten werden
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
				//Schlie�en der offenen Resultset und Statementobjekte
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
	 * @info �ndert den Best�tigungsstatus der �bergebenen Tauschanfrage
	 */
	protected void updateTauschanfrage(Tauschanfrage tauschanfrage,Connection con) {


		boolean best�tigungsstatus = tauschanfrage.isBest�tigungsstatus();
		int tauschnr = tauschanfrage.getTauschnr();

		String sqlStatement;
		sqlStatement = "UPDATE Tauschanfrage " + "SET Best�tigunsstatus = ? " + "WHERE Tauschnr =" + tauschnr;
				
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(sqlStatement);

			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
			//Preparedstatement f�llen und ausf�hren
			pstmt.setBoolean(1, best�tigungsstatus);
			pstmt.executeUpdate();
			
			//Connection Zustand best�tigen und somit fest in die Datenbank schreiben
			con.commit();


		} catch (SQLException sql) {
			System.err.println("Methode updateTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				//Zur�cksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();			
				
			} catch (SQLException sqlRollback) {
				System.err.println("Methode updateTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				//Schlie�en der offen gebliebenen Preparedstatements
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode updateTauschanfarage (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info �ndert den Best�tigungsstatus der �bergebenen Tauschanfrage
	 */
	protected void best�tigeTauschanfrage(String empf�nger , int tauschnr,Connection con) {


		String sqlStatement;
		sqlStatement = "UPDATE Tauschanfrage SET Best�tigungsstatus = true WHERE empf�nger='"+empf�nger+"' and Tauschnr =" + tauschnr;
		Statement stmt = null;

		try {

			stmt = con.createStatement();
			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
			//Preparedstatement ausf�hren
			stmt.executeUpdate(sqlStatement);
			
			//Connection Zustand best�tigen und somit fest in die Datenbank schreiben
			con.commit();

		} catch (SQLException sql) {
			System.err.println("Methode best�tigeTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				//Zur�cksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
			} catch (SQLException sqlRollback) {
				System.err.println("Methode best�tigeTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schlie�en der offen gebliebenen Statements
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
	 * @info Auslesen der Tauschanfragen aus der Datenbank und hinzuf�gen in eine Liste, welche den Ausgabewert darstellt.
	 * Diese beinhaltet die zugeh�rigen Mitarbeiter und Schichten der Sender und Empf�nger.
	 * Es werden Mitarbeiter, Schicht Objekte welche dem Sender/Empf�nger entsprechen f�r jede Tauschanfrage gespeichert.
	 */
	protected LinkedList<Tauschanfrage> getTauschanfragen(Connection con) {
		Datenbank_Tauschanfrage tauschanfrage= new Datenbank_Tauschanfrage();

			
		Statement stmt = null;
		ResultSet rs = null;

		//Ben�tigten Sql-Befehlt speichern
		String sqlStatement = "select Tauschnr from Tauschanfrage";

		try {
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgef�hrt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Tauschanfrage> tauschanfrageList = new LinkedList<>();
			// Solange es einen "n�chsten" Datensatz in dem Resultset gibt, mit den Daten des RS 
			// ein neues Tauschanfrage-Objekt erzeugen. Dieses wird anschlie�end der Liste hinzugef�gt.
			while (rs.next()) {
				Tauschanfrage tanf = tauschanfrage.getTauschanfrage(rs.getInt("Tauschnr"),con);

				tauschanfrageList.add(tanf);
			}
			//Liste Tauschanfrage-Objekten zur�ckgeben
			return tauschanfrageList;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf null setzen
			System.err.println("Methode getTauschanfragen SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schlie�en der offen gebliebenen Statements und Resultsets
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
	 * Diese beinhaltet die zugeh�rigen Mitarbeiter und Schichten der Sender und Empf�nger.
	 * Es werden Mitarbeiter, Schicht Objekte welche dem Sender/Empf�nger entsprechen f�r jede Tauschanfrage gespeichert.
	 */
	protected Tauschanfrage getTauschanfrage(int tauschnr, Connection con ) {
		Datenbank_Schicht schicht= new Datenbank_Schicht();
		Datenbank_Mitarbeiter mitarbeiter= new Datenbank_Mitarbeiter();
		LinkedList<Schicht> schichtList = schicht.getSchichten(con);
		LinkedList<Mitarbeiter> mitarbeiterList = mitarbeiter.getAlleMitarbeiter(con);
		
		//Pr�fen ob der Tauschanfrage vorhanden ist
		if (!checkTauschanfrage(tauschnr, con)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;

		//Ben�tigten Sql-Befehlt speichern
		String sqlStatement = "select * from Tauschanfrage WHERE tauschnr="+tauschnr;

		try {
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgef�hrt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			
			//Auch wenn es voraussichtlich nur einen Datensatz gibt, den n�chsten Datensatz abrufen,
			//um 100% sicherheit zu haben
				rs.next();
				Tauschanfrage tanf = new Tauschanfrage(rs.getString("Empf�nger"),rs.getString("Sender"), rs.getBoolean("Best�tigungsstatus")
						,rs.getInt("Schichtnrsender"), rs.getInt("Schichtnrempf�nger"), rs.getInt("Tauschnr"));
				
					//Durchsucht alle Mitarbeiter nach dem Sender, Mitarbeiter Objekt des Senders wird
					// im Tauschanfrage Objekt gespeichert.
					Mitarbeiter sender = null;
					for (Mitarbeiter ma : mitarbeiterList) {
						if (ma.getBenutzername().equals(tanf.getSender())) {
							sender=ma;	
						}
					}
					tanf.setMaSender(sender);
	
					//Durchsucht alle Mitarbeiter nach dem Empf�nger, Mitarbeiter Objekt des Empf�ngers wird
					// im Tauschanfrage Objekt gespeichert.
					Mitarbeiter empf�nger = null ;
					for (Mitarbeiter ma : mitarbeiterList) {
						if (ma.getBenutzername().equals(tanf.getEmpf�nger())) {
							empf�nger= ma;
						}
					}
					tanf.setMaEmpf�nger(empf�nger);
	
	
					//Durchsucht alle Schichten nach der Schichtnrsender, das Schicht-Objekt mit der entsprechenden Schichtnr
					// wird im Tauschanfrage Objekt gespeichert.
					Schicht schichtnrSender = null;
					for (Schicht sch : schichtList) {
						if (sch.getSchichtnr()==tanf.getSchichtnrsender()) {
							schichtnrSender =sch;
						}
					}
					tanf.setSchtSchichtensender(schichtnrSender);
	
	
					//Durchsucht alle Schichten nach der Schichtnrempf�nger, das Schicht-Objekt mit der entsprechenden Schichtnr
					// wird im Tauschanfrage Objekt gespeichert.
					Schicht schichtnrEmpf�nger = null;
					for (Schicht sch : schichtList) {
						if (sch.getSchichtnr() == tanf.getSchichtnrempf�nger()) {
							schichtnrEmpf�nger= sch;
						}
					}
					tanf.setSchtSchichtenempf�nger(schichtnrEmpf�nger);

			
			//Tauschanfrage-Objekt zur�ckgeben
			return tanf;
		
		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf null setzen
			System.err.println("Methode getTauschanfrage SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schlie�en der offen gebliebenen Statements und Resultsets
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
	 * @info L�schen einer Tauschanfrage aus der Datenbank Tabelle Tauschanfrage
	 */
	protected boolean deleteTauschanfrage(int tauschnr, Connection con) {
		//�berpr�fen ob der zu l�schende Datensatz existiert
		if (checkTauschanfrage(tauschnr,con)==false){

		//System.out.println("Tauschanfrage kann nicht gel�scht werden, da nicht vorhanden");	
		return true;
		
	}
	else{
		Statement stmt = null;
		
		//Ben�tigten Sql-Befehlt speichern
		String sqlStatement = "DELETE FROM Tauschanfrage WHERE Tauschnr= "+tauschnr;

		try {
			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
			//Sql-Statement ausf�hren
			stmt = con.createStatement();
			stmt.execute(sqlStatement);
			//Connection Zustand best�tigen und somit fest in die Datenbank schreiben
			con.commit();

		
			
		
		return true;
		}catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf false setzen
			System.err.println("Methode deleteTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				//Zur�cksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				
				return false;
			} catch (SQLException sqlRollback) {
					System.err.println("Methode deleteTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
					return false;
				}
		} finally {
			//Schlie�en der offen gebliebenen Statements
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
	 * @info Fragt die h�chste Tauschnr ab und erh�ht diese um 1, sodass bei neu Erstellung
	 * einer Tauschanfrage die n�chste Tauschnr vorliegt.
	 */
	protected  int getNewTauschnr(Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		//Ben�tigten Sql-Befehlt speichern
		String sqlQuery = "select max(tauschnr)+1 from Tauschanfrage";

		try {
			//Resultset- und Statement-Objekt erzeugen
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs.next();
			//Speichern der n�chsth�heren Tauschnr in maxTauschnr
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
			//Schlie�en der offen gebliebenen Resultsets und Statements
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
