  package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.JFileChooser;

import data.Tag;


import data.Wochenplan;

class Datenbank_Wochenplan {

	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();
	private Einsatzplanmodel myModel = null;

	protected Datenbank_Wochenplan(Einsatzplanmodel mymodel) {
	this.myModel = myModel;
	}


	

	// Wochenplan in tabelle Wochenplan hinzuf
	protected void addWochenplan(Wochenplan wochenplan) {

		String sqlStatement;
		sqlStatement = "insert into WOCHENPLAN (Wpnr, Oeffentlichstatus, Oeffnungszeit, Schlieﬂzeit,"
				+ " Hauptzeitbeginn, Hauptzeitende, Benutzername, Minanzinfot, Minanzinfow,"
				+ " Minanzkasse, Mehrbesetzung ) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(sqlStatement);
			String wp=Integer.toString(wochenplan.getWpnr());
			while(wp.length()!=4){
				break;
			}
			
			int wpnr= Integer.parseInt(wp);
			boolean ˆffentlichstatus = wochenplan.is÷ffentlichstatus();
			String ˆffnungszeit = wochenplan.get÷ffnungszeit();
			String schlieﬂzeit = wochenplan.getSchlieﬂzeit();
			String hauptzeitbeginn = wochenplan.getHauptzeitbeginn();
			String hauptzeitende = wochenplan.getHauptzeitende();
			String benutzername = wochenplan.getBenutzername();
			int Minanzinfot = wochenplan.getMinanzinfot();
			int Minanzinfow = wochenplan.getMinanzinfow();
			int Minanzkasse = wochenplan.getMinanzkasse();
			int Mehrbesetzung = wochenplan.getMehrbesetzung();

			con.setAutoCommit(false);

			if (checkWochenplan(wpnr)) {
				deleteWochenplan(wpnr);
				addWochenplan(wochenplan);
			} else {
				
				pstmt.setInt(1, wpnr);
				
				pstmt.setBoolean(2, ˆffentlichstatus);
				pstmt.setString(3, ˆffnungszeit);
				pstmt.setString(4, schlieﬂzeit);
				pstmt.setString(5, hauptzeitbeginn);
				pstmt.setString(6, hauptzeitende);
				pstmt.setString(7, benutzername);
				pstmt.setInt(8, Minanzinfot);
				pstmt.setInt(9, Minanzinfow);
				pstmt.setInt(10, Minanzkasse);
				pstmt.setInt(11, Mehrbesetzung);

				pstmt.execute();
				con.commit();
				

				if (checkWochenplan(wpnr)) {


					System.out.println("Mitarbeiter wurde angelegt.");

				} else {
					System.err.println("Wochenplan kann "
							+ "nicht angelegt werden!");
					con.rollback();
				}
			}

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode addWochenplan SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addWochenplan " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode addWochenplan(finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	// Kontrolle ob Wochenplan vorhanden 
	protected boolean checkWochenplan(int wpnr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select wpnr from Wochenplan where wpnr = " + wpnr;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs.next();

		} catch (SQLException sql) {
			System.err.println("Methode checkWochenplan SQL-Fehler: " + sql.getMessage());
			return false;
		} finally {
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

	// Wochenplan in der Tabelle Wochenplan bearbeiten
	protected void updateWochenplan(Wochenplan wochenplan) {

		int wpnr = wochenplan.getWpnr();
		boolean ˆffentlichstatus = wochenplan.is÷ffentlichstatus();


		String sqlStatement;

		sqlStatement = "UPDATE WOCHENPLAN " + "SET Oeffentlichstatus = ? WHERE Wpnr =" + wpnr;
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(sqlStatement);

			con.setAutoCommit(false);
			
			pstmt.setBoolean(1, ˆffentlichstatus);
			



			pstmt.executeUpdate();
			con.commit();

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode updateWochenplan SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode updateWochenplan " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.err.println("Methode updateWochenplan (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	// Auslesen der Wochenpl‰ne aus der Datenbank und eintragen in eine TreeMap, welche ‹bergeben wird
	protected TreeMap<Integer , Wochenplan> getWochenpl‰ne() {

		//Datenbank_Tag tag = new Datenbank_Tag();
		//Datenbank_Schicht schicht = new Datenbank_Schicht();

		//LinkedList<Tag> tageList = tag.getTag();;
		//LinkedList<Schicht> schichtList = schicht.getSchicht();

		Statement stmt = null;
		ResultSet rs = null;
		String sqlStatement = "select Wpnr, Oeffentlichstatus, Oeffnungszeit, Schlieﬂzeit, Hauptzeitbeginn, Hauptzeitende, Benutzername,"
				+ "Minanzinfot, Minanzinfow, Minanzkasse, Mehrbesetzung from Wochenplan";

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			TreeMap<Integer, Wochenplan> wochenplanList = new TreeMap<>();

			while (rs.next()) {
				Wochenplan wp = new Wochenplan(0, false, sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement, 0, 0, 0, 0);

				wp.setWpnr(rs.getInt("Wpnr"));
				wp.set÷ffentlichstatus(rs.getBoolean("Oeffentlichstatus"));
				wp.set÷ffnungszeit(rs.getString("Oeffnungszeit"));
				wp.setSchlieﬂzeit(rs.getString("Schlieﬂzeit"));
				wp.setHauptzeitbeginn(rs.getString("Hauptzeitbeginn"));
				wp.setHauptzeitende(rs.getString("Hauptzeitende"));
				wp.setBenutzername(rs.getString("Benutzername"));
				wp.setMinanzinfot(rs.getInt("Minanzinfot"));
				wp.setMinanzinfow(rs.getInt("Minanzinfow"));
				wp.setMinanzkasse(rs.getInt("Minanzkasse"));
				wp.setMehrbesetzung(rs.getInt("Mehrbesetzung"));
				//for (Tag ta : tageList) {
					//if (ta.getWpnr() == s.getWpnr()) {
						//s.setLinkedListTage(ta);
				

			//			for (Schicht sch : schichtList) {
					
				//			if (sch.getWpnr() == ta.getWpnr()&&(sch.getWpnr() == ta.getWpnr()&& sch.getTbez() == ta.getTbez())) {
					//			s.setLinkedListSchichten(sch);
						//	}
						//}
				//	}
				//}

				wochenplanList.put(wp.getWpnr(), wp);
			}

			rs.close();
			stmt.close();

			return wochenplanList;

		} catch (SQLException sql) {
			return null;
		}
	}
	// Auslesen der Wochenpl‰ne aus der Datenbank und eintragen in eine TreeMap, welche ‹bergeben wird
	protected Wochenplan getWochenplan(int wpnr) {

		//Datenbank_Tag tag = new Datenbank_Tag();
		//Datenbank_Schicht schicht = new Datenbank_Schicht();

		//LinkedList<Tag> tageList = tag.getTag();;
		//LinkedList<Schicht> schichtList = schicht.getSchicht();

		Statement stmt = null;
		ResultSet rs = null;
		String sqlStatement = "select Wpnr, Oeffentlichstatus, Oeffnungszeit, Schlieﬂzeit, Hauptzeitbeginn, Hauptzeitende, Benutzername,"
				+ "Minanzinfot, Minanzinfow, Minanzkasse, Mehrbesetzung from Wochenplan where Wpnr ="+wpnr;

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			
				Wochenplan wp = new Wochenplan(0, false, sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement, 0, 0, 0, 0);

				wp.setWpnr(rs.getInt("Wpnr"));
				wp.set÷ffentlichstatus(rs.getBoolean("Oeffentlichstatus"));
				wp.set÷ffnungszeit(rs.getString("Oeffnungszeit"));
				wp.setSchlieﬂzeit(rs.getString("Schlieﬂzeit"));
				wp.setHauptzeitbeginn(rs.getString("Hauptzeitbeginn"));
				wp.setHauptzeitende(rs.getString("Hauptzeitende"));
				wp.setBenutzername(rs.getString("Benutzername"));
				wp.setMinanzinfot(rs.getInt("Minanzinfot"));
				wp.setMinanzinfow(rs.getInt("Minanzinfow"));
				wp.setMinanzkasse(rs.getInt("Minanzkasse"));
				wp.setMehrbesetzung(rs.getInt("Mehrbesetzung"));
				//for (Tag ta : tageList) {
					//if (ta.getWpnr() == s.getWpnr()) {
						//s.setLinkedListTage(ta);
				

			//			for (Schicht sch : schichtList) {
					
				//			if (sch.getWpnr() == ta.getWpnr()&&(sch.getWpnr() == ta.getWpnr()&& sch.getTbez() == ta.getTbez())) {
					//			s.setLinkedListSchichten(sch);
						//	}
						//}
				//	}
				//}

			

			rs.close();
			stmt.close();

			return wp;

		} catch (SQLException sql) {
			return null;
		}
	}

	// Student aus der tabelle Wochenplan lˆschen 
	protected boolean deleteWochenplan(int wpnr) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM WOCHENPLAN WHERE Wpnr = " + wpnr;
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
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
				System.err.println("Methode deleteWochenplan (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

//	if(tmp[3].equalsIgnoreCase("null"))
//		pstmt.setNull(4, java.sql.Types.INTEGER);
//	else
//		pstmt.setInt(4, Integer.parseInt(tmp[3]));
	protected  int getNewWpnr(Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select max (wpnr)+1 from Wochenplan";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs.next();
			int maxWpnr = rs.getInt(1);
			rs.close();
			stmt.close();
			return maxWpnr;
		} catch (SQLException sql) {
			System.err.println("Methode getNewEmpno SQL-Fehler: "
					+ sql.getMessage());
			return -1;
		}
	}
}
