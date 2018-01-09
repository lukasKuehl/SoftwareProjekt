package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.TreeMap;
import model.Datenbank_Connection;


import data.Tag;
import data.Tblock_Tag;
import data.Mitarbeiter;
import data.Schicht;

/**
 * @author Thomas Friesen, Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und Änderung in der Datenbank im Bezug auf die Tabelle Tag zu verarbeiten.
 */

class Datenbank_Tag {
	private Einsatzplanmodel myModel = null;	
	
	/**
	 * @author Anes Preljevic
	 * @info Beim erstellen der Hilfsklasse soll das Einsatzplanmodel übergeben werden.
	 * Das soll vermeiden, dass die Datenbankverbindung häufiger erstellt wird, das Einsatzplanmodel unnötig öfter erstellt wird
	 * und die Hilfsklassen andere Model-Hilfsklassen übers Einsatzplanmodel nutzen können, was unnötigen Code entfernt und die Kopplung verringert.
	 */
	protected Datenbank_Tag(Einsatzplanmodel myModel){
		this.myModel=myModel;
	}
	/**
	 * @Thomas Friesen
	 * @info Die Methode fügt einen Datensatz in die Tag Tabelle ein.
	 */
	protected boolean addTag(Tag tag,String oeffnungszeit, String schließzeit, String hauptzeitbeginn, String hauptzeitende, Connection con) {	

		boolean success = false;
		boolean feiertag = false;
		int wpnr = 0;
		PreparedStatement pstmt = null;
		String tbez = null;
		String sqlStatement  = "insert into Tag(tbez, wpnr, feiertag) values(?, ?, ?)";
		Datenbank_Schicht dschicht= new Datenbank_Schicht(myModel);
		
		
		try {
			//Erstellen des prepared Statement Objektes
			pstmt = con.prepareStatement(sqlStatement);

			//Auslesen der als Parameter übergebenen Mitarbeiter-Schicht-Beziehung
			tbez = tag.getTbez();
			wpnr = tag.getWpnr();

			//Überprüfung des PK-Constraints
			if (checkTag(tbez,wpnr,con)) {
				System.out.println("Dieser Tag existiert bereits in dem angegebenen Wochenplan!");
			}
			//Überprüfung des FK-Constraints
			if (checkTagFK(wpnr,con) == false){
				System.out.println("Die wpnr existiert nicht in der Wochenplan-Tabelle");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
								
				//Prepared Statement füllen
				pstmt.setString(1, tbez);
				pstmt.setInt(2, wpnr);
				pstmt.setBoolean(3, feiertag);
				
				//Ausführen der SQL-Anweisung
				pstmt.execute();
				
				//Einfügen von Schichten in einen Tag
				dschicht.addSchicht(new Schicht(dschicht.getNewSchichtnr(con),tbez, wpnr,oeffnungszeit, hauptzeitbeginn),con);
				dschicht.addSchicht(new Schicht(dschicht.getNewSchichtnr(con),tbez, wpnr,hauptzeitbeginn, hauptzeitende),con);
				dschicht.addSchicht(new Schicht(dschicht.getNewSchichtnr(con),tbez, wpnr,hauptzeitende, schließzeit),con);
					
				success = true;
				
			}			
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im späteren Programm entweder gar nicht oder vielleicht als Dialog(ausgelöst in der View weil der Rückgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einfügen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Zuordnung eines Tages zu einem Wochenplan:");
			System.out.println("Parameter: Tagbezeichnung = " + tbez + " Wochenplannummer = " + wpnr);
			sql.printStackTrace();		
			
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTag " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schließen der offen gebliebenen Statements & ResultSets
			try {
			
				if (pstmt != null){
					pstmt.close();
				}			
				
			} catch (SQLException e) {
				System.err.println("Methode addTag(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}
	
	
	/**
	 * @Anes Preljevic
	 * @info Die Methode überprüft, ob ein Datensatz mit den angegebenen Parametern bereits in der Datenbank existiert
	 */
	protected boolean checkTag(String tbez, int wpnr, Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		
		//Benötigten Sql-Befehlt speichern
		String sqlQuery = "select tbez,wpnr from tag where tbez = '"+tbez+"' and wpnr= "+wpnr;

		try {
			//Statement, Resultset wird erstellt und Sql-Befehl wird ausgeführt, schließend wird der 
			//nächste Datensatz aus dem Resultset ausgegeben
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf false setzen
			System.err.println("Methode checkTag SQL-Fehler: "
					+ sql.getMessage());
			return false;
		} finally {
			//Schließen der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkTag(finally) SQL-Fehler: "
						+ e.getMessage());
			}
		}
	}
	

	/**
	 * @author Thomas Friesen
	 * @info Die Methode prüft, ob die Foreign Keys der Tabelle Ma_Schicht eingehalten werden
	 */
	protected boolean checkTagFK(int wpnr, Connection con) {
		boolean result = false;
		Statement[] stmt = new Statement[1];
		ResultSet[] rs = new ResultSet[1];
		String[] sqlQuery = new String[1]; 
		sqlQuery[0] = "select wpnr from Wochenplan where wpnr = "+ wpnr ;
		
		try {
			for (int i=0;i<1;i++){
				stmt[i] = con.createStatement();
				rs[i] = stmt[i].executeQuery(sqlQuery[i]);
			}
			if ((rs[0].next()) == true){
				result = true;
			}else{
				result = false;
			}
			
		} catch (SQLException sql) {
			System.err.println("Methode checkTagFK SQL-Fehler: " + sql.getMessage());
			return false;
			
		} finally {
			try {
				for(int i=0;i<1;i++){
					if(rs[i] != null){
						rs[i].close();
					}
					if(stmt[i] != null){
						stmt[i].close();
					}
						
				}
			} catch (SQLException e) {
				System.err.println("Methode checkTagFK (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * @author Anes Preljevic
	 * @info Ändert den Feiertag status auf true, bei dem Tag mit der übergebenen Tbez und wpnr.
	 */
	protected void setzeFeiertagtrue(String tbez, int wpnr, Connection con) {
		
		Statement stmt = null;
		String sqlStatement;
		sqlStatement = "UPDATE Tag Set Feiertag=true  WHERE Tbez = '"+tbez+"' and Wpnr="+wpnr;
		
		
		try {	
			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
			//Überprüfung ob es den Tag gibt
			if(checkTag(tbez,wpnr,con)==true){
		
			
			stmt = con.createStatement();
			con.setAutoCommit(false);
			stmt.executeUpdate(sqlStatement);
			
			//Connection Zustand bestätigen und somit fest in die Datenbank schreiben
			con.commit();			
			}
		} catch (SQLException sql) {
			//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
			System.err.println("Methode setzeFeiertagtrue SQL-Fehler: "
					+ sql.getMessage());
			try {
				con.rollback();
				
			} catch (SQLException sqlRollback) {
				System.err.println("Methode setzeFeiertagtrue "
						+ "- Rollback -  SQL-Fehler: "
						+ sqlRollback.getMessage());
			}
		} finally {
			//Schließen der offen gebliebenen Statements
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode setzeFeiertagtrue(finally) SQL-Fehler: "
						+ e.getMessage());
			}
		}
	}	
	/**
	 * @author Anes Preljevic
	 * @info Ändert den Feiertag status auf false, bei dem Tag mit der übergebenen Tbez und wpnr.
	 */
	
	protected void setzeFeiertagfalse(String tbez, int wpnr,Connection con) {
		Statement stmt = null;
		String sqlStatement;
		sqlStatement ="UPDATE Tag Set Feiertag=false WHERE Tbez = '"+tbez+"' and Wpnr="+wpnr;
		
		
		try {	
			
			//Wenn aus der vorherigen Verbindung das AutoCommit noch auf true ist, auf false setzen
			
			if(con.getAutoCommit()!= false);
			{
				con.setAutoCommit(false);
			}
			//Überprüfung ob es den Tag gibt
			if(checkTag(tbez,wpnr,con)==true){
		
			
			
			stmt = con.createStatement();
			con.setAutoCommit(false);
			stmt.executeUpdate(sqlStatement);
			
			//Connection Zustand bestätigen und somit fest in die Datenbank schreiben
			con.commit();			

			}
		} catch (SQLException sql) {
			System.err.println("Methode setzeFeiertagfalse SQL-Fehler: "
					+ sql.getMessage());
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
			} catch (SQLException sqlRollback) {
				System.err.println("Methode setzeFeiertagfalse "
						+ "- Rollback -  SQL-Fehler: "
						+ sqlRollback.getMessage());
			}
		} finally {
			//Schließen der offen gebliebenen Statements
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode setzeFeiertagfalse(finally) SQL-Fehler: "
						+ e.getMessage());
			}
		}
	}	
	
	
	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller Tage aus der Datenbank und erzeugen von Tag Objekten.
	 * Diese werden in eine LinkedList abgelegt. Die zugehörigen Schichten  
	 * werden in einer LinkedList gespeichert.
	 * Diese Liste ist in der Tag List enthalten welche außerdem den Ausgabewert darstellt.
	 */
	protected LinkedList<Tag> getTage(Connection con) {

			LinkedList<Schicht> schichtList = myModel.getSchichten();
			LinkedList<Tblock_Tag> tblocktagList = myModel.getAlleTblock_Tag();
			
			Statement stmt = null;
			ResultSet rs = null;
			
			//Benötigten Sql-Befehlt speichern
			String sqlStatement = "SELECT tbez, wpnr, Feiertag FROM Tag ORDER BY wpnr , FIELD(tbez, 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag','Freitag','Samstag','Sonntag')";

			try {
				//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
				stmt = con.createStatement();
				rs = stmt.executeQuery(sqlStatement);

				LinkedList<Tag> tagList = new LinkedList<>();
				// Solange es einen "nächsten" Datensatz in dem Resultset gibt, mit den Daten des RS 
				// ein neues Tag-Objekt erzeugen. Dieses wird anschließend der Liste hinzugefügt.

				while (rs.next()) {
					Tag t = new Tag(rs.getString("Tbez"),rs.getInt("Wpnr"),rs.getBoolean("Feiertag"));
					
					//Schichten des Tages in der SchichttagList speichern(soll Controller, View die Suche ersparen, falls sie diese Funktion nutzen möchten)
					LinkedList<Schicht> schichttagList = new LinkedList<>();	
					for (Schicht sch : schichtList) {
						if (sch.getWpnr() == t.getWpnr()&& sch.getTbez().equals(t.getTbez())) {	
							schichttagList.add(sch);
						}
					}
					t.setLinkedListSchichten(schichttagList);
				
				LinkedList<Tblock_Tag> tblocktagtList = new LinkedList<>();
			
				
				//Blockierungen des Tages in  der tblocktagList speichern
				for (Tblock_Tag tbt : tblocktagList) {
				
						if (tbt.getWpnr() == t.getWpnr()&& tbt.getTbez().equals(t.getTbez())) {
							tblocktagtList.add(tbt);
						}
				}
				t.setLinkedListTblock_Tag(tblocktagtList);
				
				tagList.add(t);
				}
				//Liste mit Tag-Objekten zurückgeben
				return tagList;

			} catch (SQLException sql) {
				//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
				System.err.println("Methode getTage SQL-Fehler: " + sql.getMessage());
				return null;
			}finally {
				//Schließen der offen gebliebenen Statements und Resultsets
				try {
					if (rs != null)
						rs.close();
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					System.err.println("Methode getTage(finally) SQL-Fehler: " + e.getMessage());
				}
			}
		}
	
	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller Tage aus der Datenbank und erzeugen von Tag Objekten.
	 * Diese werden in eine LinkedList abgelegt. Die zugehörigen Schichten  
	 * werden in einer LinkedList gespeichert.
	 * Diese Liste ist in der Tag List enthalten welche außerdem den Ausgabewert darstellt.
	 */
	protected LinkedList<Tag> getTagewp(int wpnr, Connection con) {
			LinkedList<Schicht> schichtList = myModel.getSchichten();
			LinkedList<Tblock_Tag> tblocktagList = myModel.getAlleTblock_Tag();

			Statement stmt = null;
			ResultSet rs = null;
			
			String sqlStatement = "SELECT tbez, wpnr, feiertag FROM Tag where wpnr="+wpnr+" ORDER BY FIELD(tbez, 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag','Freitag','Samstag','Sonntag')";

			try {
				//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgeführt und im Resultset gespeichert
				stmt = con.createStatement();
				rs = stmt.executeQuery(sqlStatement);

				LinkedList<Tag> tagList = new LinkedList<>();

				// Solange es einen "nächsten" Datensatz in dem Resultset gibt, mit den Daten des RS 
				// ein neues Mitarbeiter-Objekt erzeugen. Dieses wird anschließend der Liste hinzugefügt.
				while (rs.next()) {
					Tag t = new Tag(rs.getString("Tbez"),rs.getInt("Wpnr"),rs.getBoolean("Feiertag"));

					//Schichten des Tages in der SchichttagList speichern
					for (Schicht sch : schichtList) {
						if (sch.getWpnr() == t.getWpnr()&& sch.getTbez().equals(t.getTbez())) {
							LinkedList<Schicht> schichttagList = new LinkedList<>();	
							schichttagList.add(sch);
							t.setLinkedListSchichten(schichttagList);
							
						}
					}
				
				for (Tblock_Tag tbt : tblocktagList) {
						if (tbt.getWpnr() == t.getWpnr()&& tbt.getTbez() == t.getTbez()) {
							LinkedList<Tblock_Tag> tblocktagtList = new LinkedList<>();
							tblocktagtList.add(tbt);
							t.setLinkedListTblock_Tag(tblocktagList);
							
						}
				
				}
					
					tagList.add(t);
				}


				//Liste mit Tag-Objekten zurückgeben
				return tagList;

			} catch (SQLException sql) {
				//Fehlerhandling, Ausgaben zur Ursachensuche und Rückgabewert auf null setzen
				System.err.println("Methode getTagewp SQL-Fehler: " + sql.getMessage());
				return null;
			}finally {
				//Schließen der offen gebliebenen Statements und Resultsets
				try {
					if (rs != null)
						rs.close();
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					System.err.println("Methode getTagewp (finally) SQL-Fehler: " + e.getMessage());
				}
			}

		}
	
	/**
	 * @author Anes Preljevic
	 * @info Löschen eines Tages mit zugehörigen Schichten  aus den Datenbank Tabellen 
	 * Tag, Schicht, Ma-Schicht (  Ma-Schicht wird über die Schicht - deleteMethode gelöscht).
	 */
	
	protected boolean deleteTag(int wpnr,Connection con) {
		Datenbank_Schicht schicht = new Datenbank_Schicht(myModel);
		LinkedList<Tblock_Tag> tblocktagList = myModel.getAlleTblock_Tag();;

		
		Statement stmt = null;
		
		
		String sqlQuery = "DELETE FROM tag WHERE wpnr= "+wpnr;
		//Alle Tblock_Tag mit der Wpnr auslesen, die tblocknr der zutreffenden Tblock_Tag-Objekte
		// stellt die TerminBlockierung da welche, ebenfalls gelöscht werden muss
		//Tblock_Tag wird in der Methode deleteTerminBlockierung mitgelöscht
		for (Tblock_Tag tbt : tblocktagList) {
			if (tbt.getWpnr() == wpnr) {
				myModel.deleteTerminBlockierung(tbt.getTblocknr());;
			}
		}
		//Schichten der Woche löschen
		schicht.deleteSchichtvonWp(wpnr,con);;
		try {
			//Sql-Statement ausführen
			stmt = con.createStatement();
			stmt.execute(sqlQuery);
			
			return true;
			
		} catch (SQLException sql) {
			System.err.println("Methode deleteTag SQL-Fehler: "
					+ sql.getMessage());
			return false;
		} finally {
			//Schließen der offen gebliebenen Statements
			try {

				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteTag (finally) SQL-Fehler: "
						+ e.getMessage());
			}
		}
	}	
	
	
}