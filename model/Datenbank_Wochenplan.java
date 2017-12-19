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



	/**
	 * @Thomas Friesen
	 * @info Die Methode fügt einen Datensatz in die Wochenplan Tabelle hinzu.
	 */
	protected Boolean addWochenplan(Wochenplan wochenplan) {
		boolean success = false;
		
		String sqlStatement = null;
		sqlStatement = "insert into WOCHENPLAN (Wpnr, Oeffentlichstatus, Oeffnungszeit, Schließzeit,"
				+ " Hauptzeitbeginn, Hauptzeitende, Benutzername, Minanzinfot, Minanzinfow,"
				+ " Minanzkasse, Mehrbesetzung ) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		Statement checkInput = null;
		ResultSet checkRS = null;
		int wpnr = 0;
		boolean öffentlichstatus = false;
		String öffnungszeit = null;
		String schließzeit = null;
		String hauptzeitbeginn = null;
		String hauptzeitende = null;
		String benutzername = null;
		int Minanzinfot = 0;
		int Minanzinfow = 0;
		int Minanzkasse = 0;
		int Mehrbesetzung = 0;
		
		
		try {
			pstmt = con.prepareStatement(sqlStatement);


			wpnr= wochenplan.getWpnr();
			öffentlichstatus = wochenplan.isÖffentlichstatus();
			öffnungszeit = wochenplan.getÖffnungszeit();
			schließzeit = wochenplan.getSchließzeit();
			hauptzeitbeginn = wochenplan.getHauptzeitbeginn();
			hauptzeitende = wochenplan.getHauptzeitende();
			benutzername = wochenplan.getBenutzername();
			Minanzinfot = wochenplan.getMinanzinfot();
			Minanzinfow = wochenplan.getMinanzinfow();
			Minanzkasse = wochenplan.getMinanzkasse();
			Mehrbesetzung = wochenplan.getMehrbesetzung();
			
			con.setAutoCommit(false);

			if (checkWochenplan(wpnr)) {
				System.out.println("Der Wochenplan ist bereits in der Tabelle eingetragen");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
			
				pstmt.setInt(1, wpnr);
				pstmt.setBoolean(2, öffentlichstatus);
				pstmt.setString(3, öffnungszeit);
				pstmt.setString(4, schließzeit);
				pstmt.setString(5, hauptzeitbeginn);
				pstmt.setString(6, hauptzeitende);
				pstmt.setString(7, benutzername);
				pstmt.setInt(8, Minanzinfot);
				pstmt.setInt(9, Minanzinfow);
				pstmt.setInt(10, Minanzkasse);
				pstmt.setInt(11, Mehrbesetzung);
			
				pstmt.execute();
				con.commit();	
				
			}			
			
			success = true;
			con.setAutoCommit(true);
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im späteren Programm entweder gar nicht oder vielleicht als Dialog(ausgelöst in der View weil der Rückgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einfügen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Erstellung eines neuen TerminBlockierung-Datensatzes:");
			System.out.println("Parameter: Wochenplannummer = " + wpnr + " Öffentlich = " + öffentlichstatus +
					" Öffnungszeit: " + öffnungszeit + " Schließzeit: " + schließzeit + " Hauptzeitbeginn: " + hauptzeitbeginn +
					"Hauptzeitende: " + hauptzeitende + "Benutzer; " + benutzername +"Minanzinfot: " + Minanzinfot +
					"Minanzinfow: " + Minanzinfow + "Minanzkasse: " + Minanzkasse + "Mehrbesetzung: " + Mehrbesetzung);
			sql.printStackTrace();		
			
			try {
				//Zurücksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addTerminBlockierung " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
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
				System.err.println("Methode addTerminBlockierung(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}

	
	/**
	 * @author Anes Preljevic
	 * @info Prüft ob es zu der eingegebenen Wochenplannr einen Wochenplan gibt,
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
	 * @info Ändert den Öffentlichstatus auf den Wert der übergebenen Woche
	 */
	
	
	protected void updateWochenplan(Wochenplan wochenplan) {

		int wpnr = wochenplan.getWpnr();
		boolean öffentlichstatus = wochenplan.isÖffentlichstatus();


		String sqlStatement;

		sqlStatement = "UPDATE WOCHENPLAN " + "SET Oeffentlichstatus = ? WHERE Wpnr =" + wpnr;
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(sqlStatement);

			con.setAutoCommit(false);
			pstmt.setBoolean(1, öffentlichstatus);
			
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
	 * @info Ändert den Öffentlichstatus auf true, bei der Woche mit der übergebenen Wochenplannr
	 */
	protected void setzeÖffentlichstatustrue(int wpnr) {
		
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
				System.err.println("Methode setzeÖffentlichstatustrue " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode setzeÖffentlichstatustrue (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}
	/**
	 * @author Anes Preljevic
	 * @info Ändert den Öffentlichstatus auf false, bei der Woche mit der übergebenen Wochenplannr
	 */
	
	protected void setzeÖffentlichstatusfalse(int wpnr) {
		
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
				System.err.println("Methode setzeÖffentlichstatusfalse " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.err.println("Methode setzeÖffentlichstatusfalse (finally) SQL-Fehler: " + e.getMessage());
			}
		}
	}

	/**
	 * @author Anes Preljevic
	 * @info Auslesen aller Wochenpläne aus der Datenbank und erzeugen von Wochenplan Objekten.
	 * Diese werden in eine TreeMap abgelegt. Die zugehörigen Tage  
	 * werden in einer LinkedList gespeichert.( in diesen werden zusätzlich die zugehörigen Schichten ausgelesen) 
	 * Diese Liste ist in der TreeMap enthalten welche außerdem den Ausgabewert darstellt.
	 */
	protected TreeMap<Integer , Wochenplan> getWochenpläne() {

		Datenbank_Tag tag = new Datenbank_Tag();
		//Datenbank_Schicht schicht = new Datenbank_Schicht();

		LinkedList<Tag> tageList = tag.getTage();;
		//LinkedList<Schicht> schichtList = schicht.getSchicht();

		Statement stmt = null;
		ResultSet rs = null;
		String sqlStatement = "select Wpnr, Oeffentlichstatus, Oeffnungszeit, Schließzeit, Hauptzeitbeginn, Hauptzeitende, Benutzername,"
				+ "Minanzinfot, Minanzinfow, Minanzkasse, Mehrbesetzung from Wochenplan";

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			TreeMap<Integer, Wochenplan> wochenplanList = new TreeMap<>();

			while (rs.next()) {
				Wochenplan wp = new Wochenplan(0, false, sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement, 0, 0, 0, 0);

				wp.setWpnr(rs.getInt("Wpnr"));
				wp.setÖffentlichstatus(rs.getBoolean("Oeffentlichstatus"));
				wp.setÖffnungszeit(rs.getString("Oeffnungszeit"));
				wp.setSchließzeit(rs.getString("Schließzeit"));
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
		* @info Auslesen des Wochenplans mit der übergebenen Wochenplannr aus der Datenbank, erstellen eines Objektes Wochenplan mit den Daten aus der Datenbank.
		*  Die zugehörigen Tage werden in einer LinkedList gespeichert.( in diesen werden zusätzlich die zugehörigen Schichten ausgelesen) 
		* Diese Liste ist in dem Objekt enthalten welches außerdem den Ausgabewert darstellt.
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
		String sqlStatement = "select Wpnr, Oeffentlichstatus, Oeffnungszeit, Schließzeit, Hauptzeitbeginn, Hauptzeitende, Benutzername,"
				+ "Minanzinfot, Minanzinfow, Minanzkasse, Mehrbesetzung from Wochenplan where Wpnr ="+wpnr;

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			
				Wochenplan wp = new Wochenplan(0, false, sqlStatement, sqlStatement, sqlStatement, sqlStatement, sqlStatement, 0, 0, 0, 0);

				wp.setWpnr(rs.getInt("Wpnr"));
				wp.setÖffentlichstatus(rs.getBoolean("Oeffentlichstatus"));
				wp.setÖffnungszeit(rs.getString("Oeffnungszeit"));
				wp.setSchließzeit(rs.getString("Schließzeit"));
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
	 * @info Löschen eines Wochenplans mit zugehörigen Tagen (schichten)  aus den Datenbank Tabellen 
	 * Wochenplan, Tag, Schicht, Ma-Schicht ( Schicht und Ma-Schicht werden über die Tag/Schicht - deleteMethode gelöscht).
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
	 * @info Fragt die höchste Wpnr ab und erhöht diese um 1, sodass bei neu Erstellung
	 * eines Wochenplans die nächste Wpnr vorliegt.
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
