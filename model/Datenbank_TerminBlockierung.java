package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.TreeMap;

import data.Tblock_Tag;
import data.TerminBlockierung;



/**
 * @author Thomas Friesen, Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und �nderung in der Datenbank im Bezug auf die Tabelle TerminBlockierung zu verarbeiten.
 */
class Datenbank_TerminBlockierung {
	private Einsatzplanmodel myModel = null;	
	
	/**
	 * @author Anes Preljevic
	 * @info Beim erstellen der Hilfsklasse soll das Einsatzplanmodel �bergeben werden.
	 * Das soll vermeiden, dass die Datenbankverbindung h�ufiger erstellt wird, das Einsatzplanmodel unn�tig �fter erstellt wird
	 * und die Hilfsklassen andere Model-Hilfsklassen �bers Einsatzplanmodel nutzen k�nnen, was unn�tigen Code entfernt und die Kopplung verringert.
	 */
	protected Datenbank_TerminBlockierung(Einsatzplanmodel myModel){
		this.myModel=myModel;
	}
	/**
	 * @Thomas Friesen
	 * @info  F�gt einen neuen Termin-Datensatz in die TerminBlockierung Tabelle hinzu.
	 */
	
	protected boolean addTerminBlockierung(TerminBlockierung terminBlockierung, int wpnr, Connection con) {
		boolean success = false;
		int tBlockNr = 0;
		PreparedStatement pstmt = null;
		Datenbank_Tblock_Tag dtblocktag = new Datenbank_Tblock_Tag(myModel);
		String benutzername = null;
		String bbez = null;
		String anfangzeitraum = null;
		String endezeitraum = null;
		String anfanguhrzeit = null;
		String endeuhrzeit = null;
		String grund = null;
		String sqlStatement = "insert into Terminblockierung (tblocknr, benutzername, bbez, anfangzeitraum, endezeitraum, anfanguhrzeit, endeuhrzeit, grund) values(?, ?, ?, ?, ?, ?, ?, ?)";
		
		
		try {
			//Erstellen eines prepared Statement
			pstmt = con.prepareStatement(sqlStatement);

			//Auslesen der Parameter aus dem TerminBlockierung-Objekt
			tBlockNr = terminBlockierung.getTblocknr();
			benutzername = terminBlockierung.getBenutzername();	
			bbez = terminBlockierung.getBbez();
			anfangzeitraum = terminBlockierung.getAnfangzeitraum();
			endezeitraum = terminBlockierung.getEndezeitraum();
			anfanguhrzeit = terminBlockierung.getAnfanguhrzeit();
			endeuhrzeit = terminBlockierung.getEndeuhrzeit();
			grund = terminBlockierung.getGrund();
			
			// Einstellung, welche verhindert, dass nach jeder SQL-Anweisung automatisch commitet wird
			con.setAutoCommit(false);

			//Pr�fung des PK-Constraints
			if (checkTerminBlockierung(tBlockNr,con)) {
				System.out.println("Der Termin wurde bereits in die TerminBlockierung-Tabelle eingetragen");
				return false;
			}
			//Pr�fung des FK-Constraints
			if (checkTerminBlockierungFK(benutzername,con) == false){
				System.out.println("Der Benutzername existiert nicht in der Mitarbeitertabelle");
				return false;
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
			
				//F�llen des prepared Statements
				pstmt.setInt(1, tBlockNr);
				pstmt.setString(2, benutzername);
				pstmt.setString(3, bbez);
				pstmt.setString(4, anfangzeitraum);
				pstmt.setString(5, endezeitraum);
				pstmt.setString(6, anfanguhrzeit);
				pstmt.setString(7, endeuhrzeit);
				pstmt.setString(8, grund);
			
				//Ausf�hrung der SQL-Anweisung
				pstmt.execute();
				
				
				//Zuordnung einer Nummer zu jedem Tag, um zu �berpr�fen, wie viele Tage in die TerminBlock_Tag Tabelle hinzugef�gt werden m�ssen
				TreeMap<String, Integer> reihenfolgeTag1 = new TreeMap<String, Integer>();
				reihenfolgeTag1.put("Montag", 1);
				reihenfolgeTag1.put("Dienstag", 2);
				reihenfolgeTag1.put("Mittwoch", 3);
				reihenfolgeTag1.put("Donnerstag", 4);
				reihenfolgeTag1.put("Freitag", 5);
				reihenfolgeTag1.put("Samstag", 6);
				reihenfolgeTag1.put("Sonntag", 7);
				
				// Zuordnung der Values aus der reihenfolgeTag1 Treemap zu den zugeh�rigenden Tagen
				TreeMap<Integer, String> reihenfolgeTag2 = new TreeMap<Integer, String>();
				reihenfolgeTag2.put(1, "Montag");
				reihenfolgeTag2.put(2, "Dienstag");
				reihenfolgeTag2.put(3,"Mittwoch");
				reihenfolgeTag2.put(4,"Donnerstag");
				reihenfolgeTag2.put(5,"Freitag");
				reihenfolgeTag2.put(6,"Samstag");
				reihenfolgeTag2.put(7,"Sonntag");
				
				//Hinzuf�gen der TBlock_Tage in die TBlock_Tag Tabelle
				for (int i = reihenfolgeTag1.get(anfangzeitraum); i<= reihenfolgeTag1.get(endezeitraum);i++){
					dtblocktag.addTblock_Tag(new Tblock_Tag(tBlockNr,reihenfolgeTag2.get(i), wpnr), con);
				}
				//�bertragung der gesamten TBlock_Tage und TerminBlockierungen in die Datenbank
				con.commit();
				success = true;
			}			
			
			//Einstellung des Autocommit auf den Ursprungszustand zur�cksetzen
			con.setAutoCommit(true);
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im sp�teren Programm entweder gar nicht oder vielleicht als Dialog(ausgel�st in der View weil der R�ckgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einf�gen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Erstellung eines neuen TerminBlockierung-Datensatzes:");
			System.out.println("Parameter: tBlockNr = " + tBlockNr + " Mitarbeiter = " + benutzername +
					" Bezeichnung: " + bbez + " Anfangzeitraum: " + anfangzeitraum + " Endezeitraum: " + endezeitraum +
					"Anfanguhrzeit: " + anfanguhrzeit + "Endeuhrzeit; " + endeuhrzeit +"Grund: " + grund);
			sql.printStackTrace();		
			
			try {
				//Zur�cksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTerminBlockierung " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schlie�en der offen gebliebenen Statements & ResultSets
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
	 * @info Pr�ft ob es zu der eingegebenen Tblocknr eine TerminBlockierung gibt,
	 * bei existenz return true sonst false
	 */
	protected boolean checkTerminBlockierung(int tblocknr,Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select tblocknr from TerminBlockierung where tblocknr = " + tblocknr;

		try {
			//Statement, Resultset wird erstellt und Sql-Befehl wird ausgef�hrt, schlie�end wird der 
			//n�chste Datensatz aus dem Resultset ausgegeben
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf false setzen
			System.err.println("Methode checkTerminBlockierung SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			//Schlie�en der offen gebliebenen Resultsets und Statements
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
	 * @info Die Methode pr�ft, ob der Foreign-Key-Constraint der Tabelle TerminBlockierung eingehalten werden
	 */
	protected boolean checkTerminBlockierungFK(String benutzername,Connection con) {
		boolean result = false;
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select benutzername from Mitarbeiter where benutzername = '"+ benutzername +"'" ;
		
		//Erstellen der Statement- und Resultsetobjekte
		try {
				stmt = con.createStatement();
				rs = stmt.executeQuery(sqlQuery);
			
			//�berpr�fung, ob das Resultset gef�llt ist und der FK-Constraint eingehalten worden ist
			if ((rs.next()) == true){
				result = true;
			}else{
				result = false;
			}
			
			
		} catch (SQLException sql) {
			System.err.println("Methode checkTerminBlockierungFK SQL-Fehler: " + sql.getMessage());
			return false;
			
		} finally {
			//Schlie�en der offenen Statements und Resultsets
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
		//Ben�tigten Sql-Befehlt speichern
		String sqlStatement = "select * from TerminBlockierung";

		try {
			//Statement,Resultset wird erstellt, der Sql-Befehl wird ausgef�hrt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			//LinkedList erstellen, in dieser werden die  TerminBlockierung-Objekte gespeichert
			LinkedList<TerminBlockierung> terminBlockierungList = new LinkedList<TerminBlockierung>();
			//Datumsformat auf 0-23 und stunden:minuten setzen
			DateFormat df = new SimpleDateFormat("HH:mm");
			// Solange es einen "n�chsten" Datensatz in dem Resultset gibt, mit den Daten des RS 
			// ein neues TerminBlockierung-Objekt erzeugen. Dieses wird anschlie�end der Liste hinzugef�gt.

			
			while (rs.next()) {

				Time dbanfangUhrzeit = rs.getTime("Anfanguhrzeit");
				Time dbendUhrzeit = rs.getTime("Endeuhrzeit");			
				String anfangUhrzeit = df.format(dbanfangUhrzeit);
				String endUhrzeit =df.format(dbendUhrzeit);
				
				//neues TerminBLockierung-Objekt erzeugen, Daten aus dem Resultset ziehen
				TerminBlockierung tb = new TerminBlockierung(rs.getInt("Tblocknr"),rs.getString("Benutzername"), rs.getString("Bbez"),
						rs.getString("Anfangzeitraum"), rs.getString("Endezeitraum"),
						anfangUhrzeit,endUhrzeit,rs.getString("Grund") );

				terminBlockierungList.add(tb);
			}


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
	 * @info L�schen der TerminBlockierung aus der Tabelle TerminBlockierung in der Datenbank,
	 * welche die �bergebene Tblocknr besitzt. Sowie die zugeh�rigen child Datens�tze Tblock_Tag.
	 */
	protected boolean deleteTerminBlockierung(int tblocknr,Connection con) {
		
		Statement stmt = null;
	
		String sqlQuery = "DELETE FROM TerminBlockierung WHERE Tblocknr = "+tblocknr;
		//L�schen der child Beziehungen
		myModel.deleteTblock_Tag(tblocknr);
			
		try {
			stmt = con.createStatement();
			stmt.execute(sqlQuery);
			return true;
		} catch (SQLException sql) {
				//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf false setzen
				System.err.println("Methode deleteTerminBlockierung(finally) SQL-Fehler: " + sql.getMessage());
				return false;
			}finally {
				try {
					//Schlie�en der offen gebliebenen Statements

					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					System.err.println("Methode deleteTblock_Tag (finally) SQL-Fehler: " + e.getMessage());
				}
			}
		}
	
	/**
	 * @author Anes Preljevic
	 * @info Fragt die h�chste Tblocknr ab und erh�ht diese um 1, sodass bei neu Erstellung
	 * einer TerminBlockierung die n�chste Tblocknr vorliegt.
	 */
	
	protected  int getNewTblocknr(Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		//Ben�tigten Sql-Befehlt speichern
		String sqlQuery = "select max(tblocknr)+1 from TerminBlockierung";

		try {
			//Resultset- und Statement-Objekt erzeugen
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);	
			rs.next();
			if(rs.getInt(1)==0){
			int maxTblocknr2=100;
			return maxTblocknr2;
			}
			else{
			//Speichern der n�chsth�heren Tblocknr in maxTblocknr
			int maxTblocknr = rs.getInt(1);
			rs.close();
			stmt.close();
			//Ausgabe der neuen Tblocknr
			return maxTblocknr;
			}
			
		} catch (SQLException sql) {
			System.err.println("Methode getNewTblocknr SQL-Fehler: "
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
				System.err.println("Methode getNewTblocknr (finally) SQL-Fehler: " + e.getMessage());
			}
		}

	}
}
