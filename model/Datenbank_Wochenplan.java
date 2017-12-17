  package model;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.TreeMap;



import data.Tag;


import data.Wochenplan;

class Datenbank_Wochenplan {
	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();


	

	// Wochenplan in tabelle Wochenplan hinzuf
	protected void addWochenplan(Wochenplan wochenplan) {

		String sqlStatement;
		sqlStatement = "insert into WOCHENPLAN (Wpnr, Oeffentlichstatus, Oeffnungszeit, Schlie�zeit,"
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
			boolean �ffentlichstatus = wochenplan.is�ffentlichstatus();
			String �ffnungszeit = wochenplan.get�ffnungszeit();
			String schlie�zeit = wochenplan.getSchlie�zeit();
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
				
				pstmt.setBoolean(2, �ffentlichstatus);
				pstmt.setString(3, �ffnungszeit);
				pstmt.setString(4, schlie�zeit);
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

	
	/**
	 * @author Anes Preljevic
	 * @info Pr�ft ob es zu der eingegebenen Wochenplannr einen Wochenplan gibt,
	 * bei existenz return true sonst false
	 */
	
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
	/**
	 * @author Anes Preljevic
	 * @info �ndert den �ffentlichstatus auf den Wert der �bergebenen Woche
	 */
	
	
	protected void updateWochenplan(Wochenplan wochenplan) {

		int wpnr = wochenplan.getWpnr();
		boolean �ffentlichstatus = wochenplan.is�ffentlichstatus();


		String sqlStatement;

		sqlStatement = "UPDATE WOCHENPLAN " + "SET Oeffentlichstatus = ? WHERE Wpnr =" + wpnr;
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(sqlStatement);

			con.setAutoCommit(false);
			pstmt.setBoolean(1, �ffentlichstatus);
			
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
	/**
	 * @author Anes Preljevic
	 * @info �ndert den �ffentlichstatus auf true, bei der Woche mit der �bergebenen Wochenplannr
	 */
	protected void setze�ffentlichstatustrue(int wpnr) {
		
		Statement stmt = null;
		String sqlStatement;

		sqlStatement = "UPDATE WOCHENPLAN " + "SET Oeffentlichstatus = true WHERE Wpnr =" + wpnr;
		
		
		try {

			stmt = con.createStatement();

			con.setAutoCommit(false);
			stmt.executeUpdate(sqlStatement);
			con.commit();

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode updateWochenplan SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode setze�ffentlichstatustrue " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode setze�ffentlichstatustrue (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info �ndert den �ffentlichstatus auf false, bei der Woche mit der �bergebenen Wochenplannr
	 */
	
	protected void setze�ffentlichstatusfalse(int wpnr) {
		
		Statement stmt = null;
		String sqlStatement;
		sqlStatement = "UPDATE WOCHENPLAN SET Oeffentlichstatus = false WHERE Wpnr =" + wpnr;
		
		
		try {

			stmt = con.createStatement();

			con.setAutoCommit(false);
			stmt.executeUpdate(sqlStatement);
			con.commit();

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode updateWochenplan SQL-Fehler: " + sql.getMessage());
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode setze�ffentlichstatusfalse " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode setze�ffentlichstatusfalse (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller Wochenpl�ne aus der Datenbank und erzeugen von Wochenplan Objekten.
	 * Diese werden in eine TreeMap abgelegt. Die zugeh�rigen Tage  
	 * werden in einer LinkedList gespeichert.( in diesen werden zus�tzlich die zugeh�rigen Schichten ausgelesen) 
	 * Diese Liste ist in der TreeMap enthalten welche au�erdem den Ausgabewert darstellt.
	 */
	protected TreeMap<Integer , Wochenplan> getWochenpl�ne() {

		Datenbank_Tag tag = new Datenbank_Tag();
		//Datenbank_Schicht schicht = new Datenbank_Schicht();

		LinkedList<Tag> tageList = tag.getTage();;
		//LinkedList<Schicht> schichtList = schicht.getSchicht();

		Statement stmt = null;
		ResultSet rs = null;
		String sqlStatement = "select Wpnr, Oeffentlichstatus, Oeffnungszeit, Schlie�zeit, Hauptzeitbeginn, Hauptzeitende, Benutzername,"
				+ "Minanzinfot, Minanzinfow, Minanzkasse, Mehrbesetzung from Wochenplan";

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			TreeMap<Integer, Wochenplan> wochenplanList = new TreeMap<>();

			while (rs.next()) {
				Wochenplan wp = new Wochenplan(0, false, sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement, 0, 0, 0, 0);

				wp.setWpnr(rs.getInt("Wpnr"));
				wp.set�ffentlichstatus(rs.getBoolean("Oeffentlichstatus"));
				wp.set�ffnungszeit(rs.getString("Oeffnungszeit"));
				wp.setSchlie�zeit(rs.getString("Schlie�zeit"));
				wp.setHauptzeitbeginn(rs.getString("Hauptzeitbeginn"));
				wp.setHauptzeitende(rs.getString("Hauptzeitende"));
				wp.setBenutzername(rs.getString("Benutzername"));
				wp.setMinanzinfot(rs.getInt("Minanzinfot"));
				wp.setMinanzinfow(rs.getInt("Minanzinfow"));
				wp.setMinanzkasse(rs.getInt("Minanzkasse"));
				wp.setMehrbesetzung(rs.getInt("Mehrbesetzung"));
				for (Tag ta : tageList) {
					if (ta.getWpnr() == wp.getWpnr()) {
						wp.setLinkedListTage(ta);
				

			//			for (Schicht sch : schichtList) {
					
				//			if (sch.getWpnr() == ta.getWpnr()&&(sch.getWpnr() == ta.getWpnr()&& sch.getTbez() == ta.getTbez())) {
					//			s.setLinkedListSchichten(sch);
						//	}
						//}
					}
				}

				wochenplanList.put(wp.getWpnr(), wp);
			}

			rs.close();
			stmt.close();

			return wochenplanList;

		} catch (SQLException sql) {
			return null;
		}
	}
	/**
		* @author Anes Preljevic
		* @info Auslesen des Wochenplans mit der �bergebenen Wochenplannr aus der Datenbank, erstellen eines Objektes Wochenplan mit den Daten aus der Datenbank.
		*  Die zugeh�rigen Tage werden in einer LinkedList gespeichert.( in diesen werden zus�tzlich die zugeh�rigen Schichten ausgelesen) 
		* Diese Liste ist in dem Objekt enthalten welches au�erdem den Ausgabewert darstellt.
		*/
	protected Wochenplan getWochenplan(int wpnr) {

		Datenbank_Tag tag = new Datenbank_Tag();
		LinkedList<Tag> tageList = tag.getTage();;
	
		
		if (!checkWochenplan(wpnr)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;
		String sqlStatement = "select Wpnr, Oeffentlichstatus, Oeffnungszeit, Schlie�zeit, Hauptzeitbeginn, Hauptzeitende, Benutzername,"
				+ "Minanzinfot, Minanzinfow, Minanzkasse, Mehrbesetzung from Wochenplan where Wpnr ="+wpnr;

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			
				Wochenplan wp = new Wochenplan(0, false, sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement, 0, 0, 0, 0);

				wp.setWpnr(rs.getInt("Wpnr"));
				wp.set�ffentlichstatus(rs.getBoolean("Oeffentlichstatus"));
				wp.set�ffnungszeit(rs.getString("Oeffnungszeit"));
				wp.setSchlie�zeit(rs.getString("Schlie�zeit"));
				wp.setHauptzeitbeginn(rs.getString("Hauptzeitbeginn"));
				wp.setHauptzeitende(rs.getString("Hauptzeitende"));
				wp.setBenutzername(rs.getString("Benutzername"));
				wp.setMinanzinfot(rs.getInt("Minanzinfot"));
				wp.setMinanzinfow(rs.getInt("Minanzinfow"));
				wp.setMinanzkasse(rs.getInt("Minanzkasse"));
				wp.setMehrbesetzung(rs.getInt("Mehrbesetzung"));
				if (rs.wasNull())
					sqlStatement = "--";
				for (Tag ta : tageList) {
					if (ta.getWpnr() == wp.getWpnr()) {
						wp.setLinkedListTage(ta);
				

			

			rs.close();
			stmt.close();
					}
				}

			return wp;
		}
		catch (SQLException sql) {
			return null;
		}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info L�schen eines Wochenplans mit zugeh�rigen Tagen (schichten)  aus den Datenbank Tabellen 
	 * Wochenplan, Tag, Schicht, Ma-Schicht ( Schicht und Ma-Schicht werden �ber die Tag/Schicht - deleteMethode gel�scht).
	 */
	
	protected boolean deleteWochenplan(int wpnr) {
		Datenbank_Tag tag = new Datenbank_Tag();
		LinkedList<Tag> tageList = tag.getTage();

		if (!checkWochenplan(wpnr)){
			return false;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "DELETE FROM WOCHENPLAN WHERE Wpnr = " + wpnr;
		for (Tag ta : tageList) {
			if (ta.getWpnr() == wpnr) {
				tag.deleteTag(wpnr);
			}
			}
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			con.commit();

			con.setAutoCommit(true);
			return true;
			}
			catch (SQLException sql) {
			System.err.println("Methode deleteTauschanfrage SQL-Fehler: " + sql.getMessage());
			try {
				
				con.rollback();
				con.setAutoCommit(true);
				return false;
			} catch (SQLException sqlRollback) {
					System.err.println("Methode deleteTauschanfrage " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
					return false;
				}
		} 
			finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode deleteWochenplan (finally) SQL-Fehler: " + e.getMessage());
			}
			}}
		}
	

//	if(tmp[3].equalsIgnoreCase("null"))
//		pstmt.setNull(4, java.sql.Types.INTEGER);
//	else
//		pstmt.setInt(4, Integer.parseInt(tmp[3]));
	/**
	 * @author Anes Preljevic
	 * @info Fragt die h�chste Wpnr ab und erh�ht diese um 1, sodass bei neu Erstellung
	 * eines Wochenplans die n�chste Wpnr vorliegt.
	 */
	
	protected  int getNewWpnr() {
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
