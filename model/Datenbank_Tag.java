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


class Datenbank_Tag {

	/**
	 * @Thomas Friesen
	 * @info Die Methode fügt einen Datensatz in die Tag Tabelle ein.
	 */
	protected boolean addTag(Tag tag,Connection con) {	
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

			
			
			con.setAutoCommit(false);

			if (checkTag(tbez,wpnr, con)) {
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
	 * @Anes Preljevic
	 * @info Die Methode überprüft, ob ein Datensatz mit den angegebenen Parametern bereits in der Datenbank existiert
	 */
	protected boolean checkTag(String tbez, int wpnr, Connection con) {
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
	 * @info Ändert den Feiertag status auf true, bei dem Tag mit der übergebenen Tbez und wpnr.
	 */
	protected void setzeFeiertagtrue(String tbez, int wpnr, Connection con) {
		Statement stmt = null;
		String sqlStatement;
		sqlStatement = "UPDATE Tag Set Feiertag=true  WHERE Tbez = '"+tbez+"' and Wpnr="+wpnr;
		
		
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
	
	protected void setzeFeiertagfalse(String tbez, int wpnr,Connection con) {
		Statement stmt = null;
		String sqlStatement;
		sqlStatement ="UPDATE Tag Set Feiertag=false WHERE Tbez = '"+tbez+"' and Wpnr="+wpnr;
		
		
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
	protected LinkedList<Tag> getTage(Connection con) {

			
			Datenbank_Schicht schicht = new Datenbank_Schicht();
			LinkedList<Schicht> schichtList = schicht.getSchichten(con);
			
			Datenbank_Tblock_Tag tblock_tag = new Datenbank_Tblock_Tag();
			LinkedList<Tblock_Tag> tblocktagList = tblock_tag.getAlleTblock_Tag(con);
			
			Statement stmt = null;
			ResultSet rs = null;
			String sqlStatement = "SELECT tbez, wpnr, Feiertag FROM Tag ORDER BY wpnr , FIELD(tbez, 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag','Freitag','Samstag','Sonntag')";

			try {
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs = stmt.executeQuery(sqlStatement);

				LinkedList<Tag> tagList = new LinkedList<>();

				while (rs.next()) {
					Tag t = new Tag(rs.getString("Tbez"),rs.getInt("Wpnr"),rs.getBoolean("Feiertag"));

					LinkedList<Schicht> schichttagList = new LinkedList<>();	
					for (Schicht sch : schichtList) {
						if (sch.getWpnr() == t.getWpnr()&& sch.getTbez().equals(t.getTbez())) {	
							schichttagList.add(sch);
						}
					}
					t.setLinkedListSchichten(schichttagList);
				
				LinkedList<Tblock_Tag> tblocktagtList = new LinkedList<>();
				
				for (Tblock_Tag tbt : tblocktagList) {
					// && tbt.getTbez().equals(t.getTbez())
						if (tbt.getWpnr() == t.getWpnr()&& tbt.getTbez().equals(t.getTbez())) {
							tblocktagtList.add(tbt);
							//System.out.println(tbt.getTbez() + "  " + tbt.getWpnr()+ "   " +tbt.getTblocknr());
							//System.out.println(t.getTbez() + "  " + t.getWpnr());
						}
				}
				t.setLinkedListTblock_Tag(tblocktagtList);
				
				tagList.add(t);
				}

				rs.close();
				stmt.close();

				return tagList;

			} catch (SQLException sql) {
				System.err.println("Methode getTage SQL-Fehler: " + sql.getMessage());
				return null;
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

			Datenbank_Schicht schicht = new Datenbank_Schicht();
			LinkedList<Schicht> schichtList = schicht.getSchichten(con);
			Datenbank_Tblock_Tag tblock_tag = new Datenbank_Tblock_Tag();
			LinkedList<Tblock_Tag> tblocktagList = tblock_tag.getAlleTblock_Tag(con);
			
			Statement stmt = null;
			ResultSet rs = null;
			String sqlStatement = "SELECT tbez, wpnr, feiertag FROM Tag where wpnr='"+wpnr+"' ORDER BY FIELD(tbez, 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag','Freitag','Samstag','Sonntag')";

			try {
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs = stmt.executeQuery(sqlStatement);

				LinkedList<Tag> tagList = new LinkedList<>();

				while (rs.next()) {
					Tag t = new Tag(rs.getString("Tbez"),rs.getInt("Wpnr"),rs.getBoolean("Feiertag"));

					
					for (Schicht sch : schichtList) {
						if (sch.getWpnr() == t.getWpnr()&& sch.getTbez() == t.getTbez()) {
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

				rs.close();
				stmt.close();

				return tagList;

			} catch (SQLException sql) {
				System.err.println("Methode getTage SQL-Fehler: " + sql.getMessage());
				return null;
			}
		}
	
	/**
	 * @author Anes Preljevic
	 * @info Löschen eines Tages mit zugehörigen Schichten  aus den Datenbank Tabellen 
	 * Tag, Schicht, Ma-Schicht (  Ma-Schicht wird über die Schicht - deleteMethode gelöscht).
	 */
	
	protected boolean deleteTag(int wpnr,Connection con) {
		Datenbank_Schicht schicht = new Datenbank_Schicht();
		Datenbank_TerminBlockierung terminblockierung = new Datenbank_TerminBlockierung();
		Datenbank_Tblock_Tag tblock_tag = new Datenbank_Tblock_Tag();
		LinkedList<Tblock_Tag> tblocktagList = tblock_tag.getAlleTblock_Tag(con);
		

		
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM tag WHERE tag.wpnr= "+wpnr;

		for (Tblock_Tag tbt : tblocktagList) {
			if (tbt.getWpnr() == wpnr) {
				terminblockierung.deleteTerminBlockierung(tbt.getTblocknr(),con);;
			}
		}
			

		schicht.deleteSchicht(wpnr,con);;
		
	
	
		try {
			stmt = con.createStatement();
			stmt.execute(sqlQuery);
			System.out.println("Tag gelöscht");
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