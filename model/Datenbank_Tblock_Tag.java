package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Tag;
import data.TerminBlockierung;
import data.Tblock_Tag;

class Datenbank_Tblock_Tag {

	
	/**
	 * @Thomas Friesen
	 * @info Die Methode fügt einen Datensatz in die Tblock_Tag Tabelle ein.
	 */
	protected boolean addTblock_Tag(Tblock_Tag tblock_tag,Connection con) {
		boolean success = false;
		
		
		String sqlStatement;
		sqlStatement = "insert into Tblock_Tag (tblocknr, tbez, wpnr) values(?, ?, ?)";
		PreparedStatement pstmt = null;
		Statement checkInput = null;
		ResultSet checkRS = null;
		int tBlockNr = 0;
		String tbez = null;
		int wpnr = 0;
		
		
		try {
			pstmt = con.prepareStatement(sqlStatement);

			//Auslesen der als Parameter übergebenen TerminBlockierung-Tag Beziehung
			tBlockNr = tblock_tag.getTblocknr();
			tbez = tblock_tag.getTbez();
			wpnr = tblock_tag.getTblocknr();
			
			con.setAutoCommit(false);

			if (checkTblock_TagTB(tBlockNr,con)) {
				System.out.println("Keine Beziehung von Terminblockierung zu Tagen!");
			}
			if (checkTblock_TagTA(tbez,wpnr,con)) {
				System.out.println("Keine Beziehung von Terminbezeichnung und Wochenplannummern zu Tagen vorhanden!");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
			
				pstmt.setInt(1, tBlockNr);
				pstmt.setString(2, tbez);
				pstmt.setInt(3, wpnr);
			
				pstmt.execute();
				con.commit();	
				
			}			
			
			success = true;
			con.setAutoCommit(true);
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im späteren Programm entweder gar nicht oder vielleicht als Dialog(ausgelöst in der View weil der Rückgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einfügen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Zuordnung einer Terminblockierung zu einem Tag:");
			System.out.println("Parameter: tBlockNr = " + tBlockNr + " Tagbezeichnung = " + tbez+ " WochenplanNr = " + wpnr);
			sql.printStackTrace();		
			
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTblock_Tag " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
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
				System.err.println("Methode addTBlock_Tag(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}
	

	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen Tblocknr eine Beziehung von Blockierungen zu Tagen  gibt,
	 * bei Existenz return true sonst false
	 */
	protected boolean checkTblock_TagTB(int tblocknr, Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select tblocknr from Tblock_Tag where tblocknr = "+tblocknr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkTblock_TagTB SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
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
	 * @info Prüft ob es zu der eingegebenen Tagbez und der Wochenplannr eine Beziehung von Tagen zu Blockierungen  gibt,
	 * bei existenz return true sonst false
	 */
	protected boolean checkTblock_TagTA(String tbez, int wpnr, Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select tbez, wpnr from Tblock_Tag where tbez= '"+tbez+"'and wpnr ="+wpnr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkTblock_TagTA SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
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
	 * @author Anes Preljevic
	 * @info Auslesen aller Beziehungen von TerminBlockierungen zu Tagen  aus der Datenbank und erzeugen von Tblock_Tag Objekten.
	 * Diese und eine Liste mit zugehörigen TerminBlockierungen werden in eine LinkedList abgelegt und ausgegeben.
	 */
	protected LinkedList<Tblock_Tag> getAlleTblock_Tag(Connection con) {
		Datenbank_TerminBlockierung terminblockierung = new Datenbank_TerminBlockierung();
		LinkedList<TerminBlockierung> terminList = terminblockierung.getTerminBlockierungen(con);;
		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "select Tblocknr, Tbez, Wpnr from Tblock_Tag";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Tblock_Tag> tblock_tagList = new LinkedList<>();

			while (rs.next()) {
				Tblock_Tag tbt = new Tblock_Tag(rs.getInt("Tblocknr"),rs.getString("Tbez"),rs.getInt("Wpnr"));

				LinkedList<TerminBlockierung> tbtbt=new LinkedList<>();
				for (TerminBlockierung tb : terminList) {
					if (tb.getTblocknr() == tbt.getTblocknr()) {
						tbtbt.add(tb);
					}
					}
				tbt.setLinkedList_termine(tbtbt);
				
				tblock_tagList.add(tbt);
			}

			rs.close();
			stmt.close();

			return tblock_tagList;

		} catch (SQLException sql) {
			System.err.println("Methode getAlleTblock_Tag SQL-Fehler: " + sql.getMessage());
			return null;
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Auslesen einer bestimmten Termin zu Tag Beziehung aus der Datenbank und erzeugen eines Tblock_Tag Objektes,
	 * welches anschließend ausgegeben wird mit einer Liste zugehöriger TerminBlockierungen.
	 */
	protected Tblock_Tag getTblock_TagTB(int tblocknr,Connection con) {
		Datenbank_TerminBlockierung terminblockierung = new Datenbank_TerminBlockierung();
		LinkedList<TerminBlockierung> terminList = terminblockierung.getTerminBlockierungen(con);;
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


				LinkedList<TerminBlockierung> tbtbt=new LinkedList<>();
				for (TerminBlockierung tb : terminList) {
					if (tb.getTblocknr() == tbt.getTblocknr()) {
						tbtbt.add(tb);
					}
					}
				tbt.setLinkedList_termine(tbtbt);
				
			rs.close();
			stmt.close();

			return tbt;

		} catch (SQLException sql) {
			System.err.println("Methode getTblock_TagTB SQL-Fehler: " + sql.getMessage());
			return null;
		}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Auslesen einer bestimmten Termin zu Tag Beziehung aus der Datenbank und erzeugen eines Tblock_Tag Objektes,
	 * welches anschließend ausgegeben wird mit einer Liste zugehöriger TerminBlockierungen.
	 */
	protected Tblock_Tag getTblock_TagT(String tbez, int wpnr,Connection con) {
		Datenbank_TerminBlockierung terminblockierung = new Datenbank_TerminBlockierung();
		LinkedList<TerminBlockierung> terminList = terminblockierung.getTerminBlockierungen(con);;
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

				LinkedList<TerminBlockierung> tbtbt=new LinkedList<>();
				for (TerminBlockierung tb : terminList) {
					if (tb.getTblocknr() == tbt.getTblocknr()) {
						tbtbt.add(tb);
					}
					}
				tbt.setLinkedList_termine(tbtbt);
				
			rs.close();
			stmt.close();

			return tbt;

		} catch (SQLException sql) {
			System.err.println("Methode getTblock_TagT SQL-Fehler: " + sql.getMessage());
			return null;
		}
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info Löscht die Blockierung eines Tages welche zu der Tblocknr gehört. Der Datensatz 
	 * wird aus der Tabelle Tblock_Tag gelöscht. Löscht die zugehörigen TerminBlockierungen.
	 */
	protected boolean deleteTblock_Tag(int tblocknr,Connection con) {


		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM Tblock_Tag WHERE tblocknr = "+tblocknr;
	
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
				System.err.println("Methode deleteTblock_Tag (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
}
