package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Schicht;
import data.Tag;
import data.TerminBlockierung;
import data.Tblock_Tag;


/**
 * @author Thomas Friesen, Anes Preljevic
 * @info Die Klasse dient dazu, jegliche Abfragen und �nderung in der Datenbank im Bezug auf die Tabelle TBlock_Tag zu verarbeiten.
 */

class Datenbank_Tblock_Tag {
	private Einsatzplanmodel myModel = null;	
	
	/**
	 * @author Anes Preljevic
	 * @info Beim erstellen der Hilfsklasse soll das Einsatzplanmodel �bergeben werden.
	 * Das soll vermeiden, dass die Datenbankverbindung h�ufiger erstellt wird, das Einsatzplanmodel unn�tig �fter erstellt wird
	 * und die Hilfsklassen andere Model-Hilfsklassen �bers Einsatzplanmodel nutzen k�nnen, was unn�tigen Code entfernt und die Kopplung verringert.
	 */
	protected Datenbank_Tblock_Tag(Einsatzplanmodel myModel){
		this.myModel=myModel;
	}
	
	/**
	 * @Thomas Friesen
	 * @info Die Methode f�gt einen Datensatz in die Tblock_Tag Tabelle ein.
	 */
	protected boolean addTblock_Tag(Tblock_Tag tblock_tag,Connection con) {
		boolean success = false;
		int wpnr = 0;
		int tBlockNr = 0;
		PreparedStatement pstmt = null;
		String tbez = null;
		String sqlStatement;
		sqlStatement = "insert into Tblock_Tag (tblocknr, tbez, wpnr) values(?, ?, ?)";
		
		
		try {
			//Erstellen des prepared Statements
			pstmt = con.prepareStatement(sqlStatement);

			//Auslesen der der Paramter aus der TerminBlockierung_Tag Objekt
			tBlockNr = tblock_tag.getTblocknr();
			tbez = tblock_tag.getTbez();
			wpnr = tblock_tag.getWpnr();
			
			//Pr�fung des PK-Constraints
			if (checkTblock_TagTB(tBlockNr,con)) {
				System.out.println("Keine Beziehung von Terminblockierung zu Tagen!");
			}
			//Pr�fung des PK-Constraints
			if (checkTblock_TagTA(tbez,wpnr,con) == false) {
				System.out.println("Keine Beziehung von Terminbezeichnung und Wochenplannummern zu Tagen vorhanden!");
			}
			//�berpr�fung der FK-Constraints
			if(checkTblock_TagFK(tBlockNr,tbez,wpnr,con) == false){
				System.out.println("Die Foreign-Key-Constraints der Tabelle TBlock_Tag wurden verletzt" );
				System.out.println(tBlockNr+ " " + tbez + " "+ wpnr);
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
			
				//F�llen des prepared Statements
				pstmt.setInt(1, tBlockNr);
				pstmt.setString(2, tbez);
				pstmt.setInt(3, wpnr);
			
				// Ausf�hren der SQL-Anweisung
				pstmt.execute();
					
				success = true;
			}			
			
			
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im sp�teren Programm entweder gar nicht oder vielleicht als Dialog(ausgel�st in der View weil der R�ckgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einf�gen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Zuordnung einer Terminblockierung zu einem Tag:");
			System.out.println("Parameter: tBlockNr = " + tBlockNr + " Tagbezeichnung = " + tbez+ " WochenplanNr = " + wpnr);
			sql.printStackTrace();		
			
			try {
				//Zur�cksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTblock_Tag " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schlie�en der offen gebliebenen Statements & ResultSets
			try {
				if (pstmt != null){
					pstmt.close();
				}			
				
			} catch (SQLException e) {
				System.err.println("Methode addTBlock_Tag(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}
	


	/**
	 * @author Anes Preljevic
	 * @info Pr�ft ob es zu der eingegebenen Tblocknr eine Beziehung von Blockierungen zu Tagen  gibt,
	 * bei Existenz return true sonst false
	 */
	protected boolean checkTblock_TagTB(int tblocknr, Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		//Ben�tigten Sql-Befehlt speichern
		String sqlQuery = "select tblocknr from Tblock_Tag where tblocknr = "+tblocknr;

		try {
			//Statement, Resultset wird erstellt und Sql-Befehl wird ausgef�hrt, anschlie�end wird der 
			//n�chste Datensatz aus dem Resultset ausgegeben
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			//wenn rs.next() !=null ---> true somit ist der Datensatz vorhanden
			return rs.next();

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf false setzen
			System.err.println("Methode checkTblock_TagTB SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			//Schlie�en der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkTblock_TagTB (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Pr�ft ob es zu der eingegebenen Tagbez und der Wochenplannr eine Beziehung von Tagen zu Blockierungen  gibt,
	 * bei existenz return true sonst false
	 */
	protected boolean checkTblock_TagTA(String tbez, int wpnr, Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		//Ben�tigten Sql-Befehlt speichern
		String sqlQuery = "select tbez, wpnr from Tblock_Tag where tbez= '"+tbez+"'and wpnr ="+wpnr;

		try {
			//Statement, Resultset wird erstellt und Sql-Befehl wird ausgef�hrt, anschlie�end wird der 
			//n�chste Datensatz aus dem Resultset ausgegeben
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			//wenn rs.next() !=null ---> true somit ist der Datensatz vorhanden
			return rs.next();

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf false setzen
			System.err.println("Methode checkTblock_TagTA SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
			//Schlie�en der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode checkTblock_TagTA (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	
	/**
	 * @author Thomas Friesen
	 * @info Die Methode pr�ft, ob die Foreign Keys der Tabelle Tblock_Tag eingehalten werden
	 */
	protected boolean checkTblock_TagFK(int tblocknr, String tbez, int wpnr,Connection con) {
		
		boolean result = false;
		Statement[] stmt = new Statement[2];
		ResultSet[] rs = new ResultSet[2];
		String sqlQuery[] = new String[2];
		sqlQuery[0] = "select tblocknr from TerminBlockierung where tblocknr = "+ tblocknr ;
		sqlQuery[1] = "select tbez, wpnr from Tag where tbez = '" + tbez + "' and wpnr =" + wpnr;
		boolean checktblocknr =false;
		boolean checktag = false;
		
	
		try {
			//Erstellen der Statement- und Resultsetobjekte
			for (int i=0; i< 2;i++){
				stmt[i] = con.createStatement();
				rs[i] = stmt[i].executeQuery(sqlQuery[i]);
			}
			//Zuweisen, der boolschen Variabeln, ob die Resultsets gef�llt sind (gef�llt = true)
			checktblocknr = rs[0].next();
			checktag = rs[1].next();
			
			//Pr�fung, ob alle FK-Constraints eingehalten werden
			if (checktblocknr == true & checktag == true){
				result = true;
			}else{
				result = false;
			}
			
		} catch (SQLException sql) {
			System.err.println("Methode checkTblock_TagFK SQL-Fehler: " + sql.getMessage());
			return false;
		
		} finally {
			try {
				//Schlie�en der offenen Resultset und Statementobjekte
				for(int i =0; i<=1;i++){
					if(rs[i] != null){
						rs[i].close();
					}
					if(stmt[i] != null){
						stmt[i].close();
					}
				}
			} catch (SQLException e) {
				System.err.println("Methode checkTblock_TagFK (finally) SQL-Fehler: " + e.getMessage());
			}
			
		}
		return result;
		
	}
	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller Beziehungen von TerminBlockierungen zu Tagen  aus der Datenbank und erzeugen von Tblock_Tag Objekten.
	 * Diese und eine Liste mit zugeh�rigen TerminBlockierungen werden in eine LinkedList abgelegt und ausgegeben.
	 */
	protected LinkedList<Tblock_Tag> getAlleTblock_Tag(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;
		//Ben�tigten Sql-Befehlt speichern
		String sqlStatement = "select Tblocknr from Tblock_Tag";

		try {
			//Statement/Resultset wird erstellt, der Sql-Befehl wird ausgef�hrt und im Resultset gespeichert
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Tblock_Tag> tblock_tagList = new LinkedList<Tblock_Tag>();
			// Solange es einen "n�chsten" Datensatz in dem Resultset gibt, mit den Daten des RS 
			// ein neues Tblock_Tag-Objekt erzeugen. Dieses wird anschlie�end der Liste hinzugef�gt.
			while (rs.next()) {
				Tblock_Tag tbt = myModel.getTblock_TagTB(rs.getInt("Tblocknr"));

				
				tblock_tagList.add(tbt);
			}
			//Liste mit Mitarbeiter-Objekten zur�ckgeben
			return tblock_tagList;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf null setzen
			System.err.println("Methode getAlleTblock_Tag SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			try {
				//Schlie�en der offen gebliebenen Statements und Resultsets
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getAlleTblock_tag (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		
	}
	/**
	 * @author Anes Preljevic
	 * @info Auslesen einer bestimmten Termin zu Tag Beziehung aus der Datenbank und erzeugen eines Tblock_Tag Objektes,
	 * welches anschlie�end ausgegeben wird mit einer Liste zugeh�riger TerminBlockierungen.
	 */
	protected Tblock_Tag getTblock_TagTB(int tblocknr,Connection con) {
		
		LinkedList<TerminBlockierung> terminList = myModel.getTerminBlockierungen();
		//Pr�fen ob der erwartete Datensatz existiert
		if (!checkTblock_TagTB(tblocknr,con)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Tblocknr, Tbez, Wpnr from Tblock_Tag where tblocknr="+tblocknr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);
				rs.next();
				Tblock_Tag tbt = new Tblock_Tag(rs.getInt("Tblocknr"), rs.getString("Tbez"),rs.getInt("Wpnr"));
				
				//Alle TerminBlockierung-Objekte durchsuchen, nach der selben tblocknr.
				//Zugeh�rige TerminBlockierung-Objekte in Tblock_Tag-Objekten speichern um
				// den Suchaufwand zu verringern
				LinkedList<TerminBlockierung> tbtbt=new LinkedList<TerminBlockierung>();

				for (TerminBlockierung tb : terminList) {
					if (tb.getTblocknr() == tbt.getTblocknr()) {
						tbtbt.add(tb);
					}
					}
				tbt.setLinkedList_termine(tbtbt);
				
				//Tblock_Tag-Objekt zur�ckgeben
			return tbt;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf null setzen
			System.err.println("Methode getTblock_TagTB SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schlie�en der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getTlock_TagTB (finally) SQL-Fehler: " + e.getMessage());
			}
		}
			
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Auslesen einer bestimmten Termin zu Tag Beziehung aus der Datenbank und erzeugen eines Tblock_Tag Objektes,
	 * welches anschlie�end ausgegeben wird mit einer Liste zugeh�riger TerminBlockierungen.
	 */
	protected Tblock_Tag getTblock_TagT(String tbez, int wpnr,Connection con) {

		LinkedList<TerminBlockierung> terminList = myModel.getTerminBlockierungen();
		//Pr�fen ob der erwartete Datensatz existiert
		if (!checkTblock_TagTA(tbez, wpnr,con)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Tblocknr, Tbez, Wpnr from Tblock_Tag where tbez='"+tbez+"'and wpnr="+wpnr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);
				rs.next();
				Tblock_Tag tbt = new Tblock_Tag(rs.getInt("Tblocknr"), rs.getString("Tbez"),rs.getInt("Wpnr"));
				
				//Alle TerminBlockierung-Objekte durchsuchen, nach der selben tblocknr.
				//Zugeh�rige TerminBlockierung-Objekte in Tblock_Tag-Objekten speichern um
				// den Suchaufwand zu verringern
				LinkedList<TerminBlockierung> tbtbt=new LinkedList<TerminBlockierung>();
				for (TerminBlockierung tb : terminList) {
					if (tb.getTblocknr() == tbt.getTblocknr()) {
						tbtbt.add(tb);
					}
					}
				tbt.setLinkedList_termine(tbtbt);
				

			//Tblock_Tag-Objekt zur�ckgeben
			return tbt;

		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf null setzen
			System.err.println("Methode getTblock_TagT SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
			//Schlie�en der offen gebliebenen Resultsets und Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode getTblock_TagT (finally) SQL-Fehler: " + e.getMessage());
			}
		}
		
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info L�scht die Blockierung eines Tages welche zu der Tblocknr geh�rt. Der Datensatz 
	 * wird aus der Tabelle Tblock_Tag gel�scht. L�scht die zugeh�rigen TerminBlockierungen.
	 */
	protected boolean deleteTblock_Tag(int tblocknr,Connection con) {


		Statement stmt = null;
		ResultSet rs = null;
		//Ben�tigten Sql-Befehlt speichern
		String sqlQuery = "DELETE FROM Tblock_Tag WHERE tblocknr = "+tblocknr;
	
		try {
			//Sql-Statement erstellen und ausf�hren
			stmt = con.createStatement();
			stmt.execute(sqlQuery);
			return true;
		} catch (SQLException sql) {
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf false setzen
			System.err.println("Methode deleteTblock_Tag SQL-Fehler: " + sql.getMessage());
			
			return false;
		} finally {
			//Schlie�en der offen gebliebenen Statements
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteTblock_Tag (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
}
