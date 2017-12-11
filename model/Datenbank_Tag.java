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
import data.Schicht;


public class Datenbank_Tag {
	
	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();

	
	
	//Erstellen der Tabelle Tag 
	public boolean createModule() {
		Statement stmt = null;
		String sqlStatement = "CREATE TABLE Tag (Tbez VARCHAR(15) NOT NULL PRIMARY KEY,"
				+ "Wpnr int(5) NOT NULL PRIMARY KEY FOREIGN KEY ," +"Anzschicht int(2) NOT NULL," +"Feiertag TINYINT(1) NOT NULL )";
		try {
			stmt = con.createStatement();
			stmt.execute(sqlStatement);
			stmt.close();
			return true;
		} catch (SQLException sql) {
			return false;
		}
	}
	
	//Tage in der Tabelle Tag hinzufügen 
	public void addTag(Tag tag) {	
		
		String sqlStatement;

		sqlStatement = "insert into Tag (Tbez, Wpnr, Anzschicht, Feiertag)values(?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		
		try {		
			pstmt = con.prepareStatement(sqlStatement);
			
			String tbez = tag.getTbez();
			int wpnr = tag.getWpnr();
			int anzschicht = tag.getAnzschicht();
			boolean feiertag = tag.isFeiertag();
			con.setAutoCommit(false);
			
			if(checkTag(tbez,wpnr)){
				updateTag(tag);
			}
			else{			
			pstmt.setString(1, tbez);
			pstmt.setInt(2, wpnr);
			pstmt.setInt(3, anzschicht);
			pstmt.setBoolean(4, feiertag);	
			pstmt.execute();
			con.commit();

			}
			
			con.setAutoCommit(true);
			
		} catch (SQLException sql) {
			System.err.println("Methode addTag SQL-Fehler: "
					+ sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTag "
						+ "- Rollback -  SQL-Fehler: "
						+ sqlRollback.getMessage());
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode addTag(finally) SQL-Fehler: "
						+ e.getMessage());
			}
		}
	}
		
	//Kontrolle ob Tag in der Tabelle schon vorhanden ist 
	public boolean checkTag(String tbez, int wpnr) {
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
	
	//Tage in der Tabelle Tag bearbeiten
	private void updateTag(Tag tag) {
		
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
	
	
		
	public LinkedList<Tag> getTag() {

			
			Datenbank_Schicht schicht = new Datenbank_Schicht();
			LinkedList<Schicht> schichtList = schicht.getSchicht();

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

				
					

					
				}

				rs.close();
				stmt.close();

				return tagList;

			} catch (SQLException sql) {
				return null;
			}
		}
	
	
	//Tag aus der Tabelle Tag löschen
	public boolean deleteTag(String tbez, int wpnr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM tag WHERE tbez = '"+tbez+"' and tag.wpnr= '"+wpnr+"'";

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
