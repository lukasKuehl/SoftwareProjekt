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
import data.Schicht;


class Datenbank_Tag {
	
	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();
	
	

	
	/**
	 * @Thomas Friesen
	 * @info Die Methode fügt einen Datensatz in die Tag Tabelle ein.
	 */
	protected boolean addTag(Tag tag) {	
		boolean success = false;
		
		
		String sqlStatement;
		sqlStatement = "insert into Tag(tbez, wpnr, anzschicht, feiertag) values(?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		Statement checkInput = null;
		ResultSet checkRS = null;
		String tbez = null;
		int wpnr = 0;
		int anzschicht = 0;
		boolean feiertag = false;
		
		
		try {
			pstmt = con.prepareStatement(sqlStatement);

			//Auslesen der als Parameter übergebenen Mitarbeiter-Schicht-Beziehung
			tbez = tag.getTbez();
			wpnr = tag.getWpnr();
			anzschicht = tag.getAnzschicht();
			
			
			con.setAutoCommit(false);

			if (checkTag(tbez,wpnr)) {
				System.out.println("Dieser Tag existiert bereits in dem angegebenen Wochenplan!");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
			
				pstmt.setString(1, tbez);
				pstmt.setInt(2, wpnr);
				pstmt.setInt(3, anzschicht);
				pstmt.setBoolean(4, feiertag);
			
				pstmt.execute();
				con.commit();	
				
			}			
			
			success = true;
			con.setAutoCommit(true);
			
			
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
				System.err.println("Methode addMa_Schicht " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
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
				System.err.println("Methode addMa_Schicht(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}
	
	/**
	 * @Thomas Friesen
	 * @info Die Methode überprüft, ob ein Datensatz mit den angegebenen Parametern bereits in der Datenbank existiert
	 */
	protected boolean checkTag(String tbez, int wpnr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select tbez,wpnr from tag where tbez = '"+tbez+"' and wpnr= '"+wpnr+"'";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkTag SQL-Fehler: "
					+ sql.getMessage());
			return false;
		} finally {
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
	 * @author Anes Preljevic
	 * @info Ändert den Feiertag status auf den Wert des übergebenen Tages.
	 */
	
	
	protected void updateTag(Tag tag) {
		
		String tbez = tag.getTbez();
		int wpnr = tag.getWpnr();
		int anzschicht = tag.getAnzschicht();
		boolean feiertag = tag.isFeiertag();
		
		String sqlStatement;
		
		sqlStatement = "UPDATE Tag "
					+ "SET Anzschicht = ? " +"Set Feiertag = ?"
					+ "WHERE where tbez = '"+tbez+"' and wpnr= '"+wpnr+"'";
		PreparedStatement pstmt = null;
		
		try {	
			
			pstmt = con.prepareStatement(sqlStatement);		

			con.setAutoCommit(false);			
			pstmt.setInt(1, anzschicht);
			pstmt.setBoolean(2, feiertag);
			pstmt.executeUpdate();
			con.commit();			

			con.setAutoCommit(true);
			
		} catch (SQLException sql) {
			System.err.println("Methode updateTag SQL-Fehler: "
					+ sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode updateTag "
						+ "- Rollback -  SQL-Fehler: "
						+ sqlRollback.getMessage());
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode updateTag (finally) SQL-Fehler: "
						+ e.getMessage());
			}
		}
	}	
	/**
	 * @author Anes Preljevic
	 * @info Ändert den Feiertag status auf true, bei dem Tag mit der übergebenen Tbez und wpnr.
	 */
	protected void setzeFeiertagtrue(String tbez, int wpnr) {
		Statement stmt = null;
		String sqlStatement;
		sqlStatement = "UPDATE Tag Set Feiertag = true"
					+ "WHERE where tbez = '"+tbez+"' and wpnr= '"+wpnr+"'";
		
		
		try {	
			
			stmt = con.createStatement();
			con.setAutoCommit(false);
			stmt.executeUpdate(sqlStatement);
			con.commit();			

			con.setAutoCommit(true);
			
		} catch (SQLException sql) {
			System.err.println("Methode setzeFeiertagtrue SQL-Fehler: "
					+ sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode setzeFeiertagtrue "
						+ "- Rollback -  SQL-Fehler: "
						+ sqlRollback.getMessage());
			}
		} finally {
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
	
	protected void setzeFeiertagfalse(String tbez, int wpnr) {
		Statement stmt = null;
		String sqlStatement;
		sqlStatement = "UPDATE Tag Set Feiertag = false"
					+ "WHERE where tbez = '"+tbez+"' and wpnr= '"+wpnr+"'";
		
		
		try {	
			
			stmt = con.createStatement();
			con.setAutoCommit(false);
			stmt.executeUpdate(sqlStatement);
			con.commit();			

			con.setAutoCommit(true);
			
		} catch (SQLException sql) {
			System.err.println("Methode setzeFeiertagfalse SQL-Fehler: "
					+ sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode setzeFeiertagfalse "
						+ "- Rollback -  SQL-Fehler: "
						+ sqlRollback.getMessage());
			}
		} finally {
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
	protected LinkedList<Tag> getTage() {

			
			Datenbank_Schicht schicht = new Datenbank_Schicht();
			LinkedList<Schicht> schichtList = schicht.getSchichten();
			
			Datenbank_Tblock_Tag tblock_tag = new Datenbank_Tblock_Tag();
			LinkedList<Tblock_Tag> tblocktagList = tblock_tag.getAlleTblock_Tag();
			
			Statement stmt = null;
			ResultSet rs = null;
			String sqlStatement = "select Tbez, Wpnr, Anzschicht, Feiertag from Tag";

			try {
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs = stmt.executeQuery(sqlStatement);

				LinkedList<Tag> tagList = new LinkedList<>();

				while (rs.next()) {
					Tag t = new Tag(sqlStatement, 0, 0, false);

					t.setTbez(rs.getString("Tbez"));
					t.setWpnr(rs.getInt("Wpnr"));
					t.setAnzschicht(rs.getInt("Anzschicht"));
					t.setFeiertag(rs.getBoolean("Feiertag"));
			
					
					for (Schicht sch : schichtList) {
						if (sch.getWpnr() == t.getWpnr()&& sch.getTbez() == t.getTbez()) {
							t.setLinkedListSchichten(sch);
						}
					}
					for (Tblock_Tag tbt : tblocktagList) {
						if (tbt.getWpnr() == t.getWpnr()&& tbt.getTbez() == t.getTbez()) {
							t.setLinkedListTblock_Tag(tbt);
						}
					}
					

				}

				rs.close();
				stmt.close();

				return tagList;

			} catch (SQLException sql) {
				return null;
			}
		}
	
	
	/**
	 * @author Anes Preljevic
	 * @info Löschen eines Tages mit zugehörigen Schichten  aus den Datenbank Tabellen 
	 * Tag, Schicht, Ma-Schicht (  Ma-Schicht wird über die Schicht - deleteMethode gelöscht).
	 */
	
	protected boolean deleteTag(int wpnr) {
		Datenbank_Schicht schicht = new Datenbank_Schicht();
		LinkedList<Schicht> schichtList = schicht.getSchichten();
		
		Datenbank_Tblock_Tag tblock_tag = new Datenbank_Tblock_Tag();
		LinkedList<Tblock_Tag> tblocktagList = tblock_tag.getAlleTblock_Tag();
		
		Datenbank_Tag tag = new Datenbank_Tag();
		LinkedList<Tag> tageList = tag.getTage();
		
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM tag WHERE tag.wpnr= "+wpnr;
		for (Schicht sch : schichtList) {
			if (sch.getWpnr() == wpnr) {
				schicht.deleteSchicht(wpnr);;
			}
			for (Tag t : tageList) {
				if (t.getWpnr() == wpnr) {

			for (Tblock_Tag tbt : tblocktagList) {
				if (tbt.getWpnr() == wpnr && tbt.getTbez() == t.getTbez()) {
					tblock_tag.deleteTblock_Tag(wpnr);;
				}
			}
				}}
		}
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return true;
		} catch (SQLException sql) {
			System.err.println("Methode deleteTag SQL-Fehler: "
					+ sql.getMessage());
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteTag (finally) SQL-Fehler: "
						+ e.getMessage());
			}
		}
	}	
	
	
}