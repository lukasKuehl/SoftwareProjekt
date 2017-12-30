  package model;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.TreeMap;

import data.Schicht;
import data.Tag;


import data.Wochenplan;

//Klassenbeschreibung fehlt!

//Kommentare innerhalb der Methode fehlen!

//Finally Bl�cke fehlen!

class Datenbank_Wochenplan {

	/**
	 * @Thomas Friesen
	 * @info Die Methode f�gt einen Datensatz in die Wochenplan Tabelle hinzu.
	 */
	protected Boolean addWochenplan(Wochenplan wochenplan,Connection con) {
		boolean success = false;
		
		String sqlStatement = null;
		sqlStatement = "insert into WOCHENPLAN (Wpnr, Oeffentlichstatus, Oeffnungszeit, Schlie�zeit,"
				+ " Hauptzeitbeginn, Hauptzeitende, Benutzername, Minanzinfot, Minanzinfow,"
				+ " Minanzkasse, Mehrbesetzung ) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		
		//Siehe vorherige Klassen!
		Statement checkInput = null;
		ResultSet checkRS = null;
		
		int wpnr = 0;
		boolean �ffentlichstatus = false;
		String �ffnungszeit = null;
		String schlie�zeit = null;
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
			�ffentlichstatus = wochenplan.is�ffentlichstatus();
			�ffnungszeit = wochenplan.get�ffnungszeit();
			schlie�zeit = wochenplan.getSchlie�zeit();
			hauptzeitbeginn = wochenplan.getHauptzeitbeginn();
			hauptzeitende = wochenplan.getHauptzeitende();
			benutzername = wochenplan.getBenutzername();
			Minanzinfot = wochenplan.getMinanzinfot();
			Minanzinfow = wochenplan.getMinanzinfow();
			Minanzkasse = wochenplan.getMinanzkasse();
			Mehrbesetzung = wochenplan.getMehrbesetzung();
			
			con.setAutoCommit(false);

			if (checkWochenplan(wpnr,con)) {
				System.out.println("Der Wochenplan ist bereits in der Tabelle eingetragen");
			}
			else{
				//Es wurde sichergestellt, dass die PK- und FK-Check-Constraints nicht verletzt werden --> Der Datensatz kann erzeugt werden
			
				//Nein, siehe vorherige Klassen!
				
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
				
				Datenbank_Tag dtag= new Datenbank_Tag();
				dtag.addTag(new Tag ("Montag", wpnr, false), �ffnungszeit, schlie�zeit, hauptzeitbeginn, hauptzeitende,con);
				dtag.addTag(new Tag ("Dienstag", wpnr, false), �ffnungszeit, schlie�zeit, hauptzeitbeginn, hauptzeitende,con);
				dtag.addTag(new Tag ("Mittwoch", wpnr, false), �ffnungszeit, schlie�zeit, hauptzeitbeginn, hauptzeitende,con);
				dtag.addTag(new Tag ("Donnerstag", wpnr, false), �ffnungszeit, schlie�zeit, hauptzeitbeginn, hauptzeitende,con);
				dtag.addTag(new Tag ("Freitag", wpnr, false), �ffnungszeit, schlie�zeit, hauptzeitbeginn, hauptzeitende,con);
				dtag.addTag(new Tag ("Samstag", wpnr, false), �ffnungszeit, schlie�zeit, hauptzeitbeginn, hauptzeitende,con);
				con.commit();	
				
			}			
			//Das success muss mit in den else Block, da eine Best�tigung nur sinnvoll ist, wenn auch wirklich ein neuer Wochenplan erstellt worden ist			
			success = true;
			
			con.setAutoCommit(true);
			
			
		} catch (SQLException sql) {
			//Die Ausgaben dienen zur Ursachensuche und sollten im sp�teren Programm entweder gar nicht oder vielleicht als Dialog(ausgel�st in der View weil der R�ckgabewert false ist) angezeigt werden
			System.out.println("Fehler beim Einf�gen eines neuen Datensatzes in die Datenbank!");
			System.out.println("Fehler bei der Erstellung eines neuen TerminBlockierung-Datensatzes:");
			System.out.println("Parameter: Wochenplannummer = " + wpnr + " �ffentlich = " + �ffentlichstatus +
					" �ffnungszeit: " + �ffnungszeit + " Schlie�zeit: " + schlie�zeit + " Hauptzeitbeginn: " + hauptzeitbeginn +
					"Hauptzeitende: " + hauptzeitende + "Benutzer; " + benutzername +"Minanzinfot: " + Minanzinfot +
					"Minanzinfow: " + Minanzinfow + "Minanzkasse: " + Minanzkasse + "Mehrbesetzung: " + Mehrbesetzung);
			sql.printStackTrace();		
			
			try {
				//Zur�cksetzen des Connection Zustandes auf den Ursprungszustand
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException sqlRollback) {
				System.err.println("Methode addWochenplan " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
			}
		} finally {
			//Schlie�en der offen gebliebenen Statements & ResultSets
			try {
	
				//Siehe vorherige Klassen!
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
				System.err.println("Methode addWochenplan(finally) SQL-Fehler: " + e.getMessage());
			}
		}
		return success;
	}

	/**
	 * @author Anes Preljevic
	 * @info Pr�ft ob es zu der eingegebenen Wochenplannr einen Wochenplan gibt,
	 * bei existenz return true sonst false
	 */
	
	protected boolean checkWochenplan(int wpnr,Connection con) {
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
	protected void updateWochenplan(Wochenplan wochenplan,Connection con) {

		int wpnr = wochenplan.getWpnr();
		boolean �ffentlichstatus = wochenplan.is�ffentlichstatus();

		//Siehe vorherige Klassen!
		String sqlStatement;
		sqlStatement = "UPDATE WOCHENPLAN " + "SET �ffentlichkeitsstatus = ? WHERE Wpnr =" + wpnr;
		
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(sqlStatement);

			//Siehe vorherige Klassen!
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
	protected void setzeOeffentlichstatustrue(int wpnr,Connection con) {
		
		Statement stmt = null;
		String sqlStatement;

		sqlStatement = "UPDATE WOCHENPLAN " + "SET �ffentlichkeitsstatus = true WHERE Wpnr =" + wpnr;
				
		try {

			stmt = con.createStatement();

			//Siehe vorherige Klassen!
			con.setAutoCommit(false);
			stmt.executeUpdate(sqlStatement);
			con.commit();

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode setze�ffentlichstatustrue SQL-Fehler: " + sql.getMessage());
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
	
	protected void setzeOeffentlichstatusfalse(int wpnr,Connection con) {
		
		Statement stmt = null;
		String sqlStatement;
		sqlStatement = "UPDATE WOCHENPLAN SET �ffentlichkeitsstatus = false WHERE Wpnr =" + wpnr;
		
		
		try {

			stmt = con.createStatement();

			//Siehe vorherige Klassen!
			con.setAutoCommit(false);
		
			stmt.executeUpdate(sqlStatement);
			con.commit();

			con.setAutoCommit(true);

		} catch (SQLException sql) {
			System.err.println("Methode setze�ffentlichstatusfalse SQL-Fehler: " + sql.getMessage());
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
	protected LinkedList<Wochenplan> getWochenplaene(Connection con) {

		Datenbank_Tag tag = new Datenbank_Tag();
		LinkedList<Tag> tageList = tag.getTage(con);;

		Statement stmt = null;
		ResultSet rs = null;
	
		//Select *
		String sqlStatement = "select Wpnr, �ffentlichkeitsstatus, �ffnungszeit, Schlie�zeit, Hauptzeitbeginn, Hauptzeitende, Benutzername,"
				+ "Minanzinfot, Minanzinfow, Minanzkasse, Mehrbesetzung from Wochenplan";

		try {
			//Siehe vorherige Klassen!
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Wochenplan> wochenplanList = new LinkedList<>();

			while (rs.next()) {
				
			//Warum wird nicht einfach die Methode getWochenplan(wpnr, con) verwendet? Die Zeilen stehen da zwei mal
				Wochenplan wp = new Wochenplan(rs.getInt("Wpnr"),rs.getBoolean("�ffentlichkeitsstatus"),rs.getTime("�ffnungszeit").toString(),
						rs.getTime("Schlie�Zeit").toString(),rs.getTime("Hauptzeitbeginn").toString(),rs.getTime("Hauptzeitende").toString(),
						rs.getString("Benutzername"),rs.getInt("Minanzinfot"),
						rs.getInt("Minanzinfow"),rs.getInt("Minanzkasse"),rs.getInt("Mehrbesetzung"));
				
				//Siehe vorherige Klassen!
				LinkedList<Tag> tagewp=new LinkedList<>();
				for (Tag ta : tageList) {
					if (ta.getWpnr() == wp.getWpnr()) {
						tagewp.add(ta);
					}
				}
				wp.setLinkedListTage(tagewp);
				wochenplanList.add(wp);
			
			}
			rs.close();
			stmt.close();

			return wochenplanList;

		} catch (SQLException sql) {
			System.err.println("Methode getWochenplaene SQL-Fehler: " + sql.getMessage());
			return null;
		}
		//Finally-Block fehlt!
		
	}
	/**
		* @author Anes Preljevic
		* @info Auslesen des Wochenplans mit der �bergebenen Wochenplannr aus der Datenbank, erstellen eines Objektes Wochenplan mit den Daten aus der Datenbank.
		*  Die zugeh�rigen Tage werden in einer LinkedList gespeichert.( in diesen werden zus�tzlich die zugeh�rigen Schichten ausgelesen) 
		* Diese Liste ist in dem Objekt enthalten welches au�erdem den Ausgabewert darstellt.
		*/
	protected Wochenplan getWochenplan(int wpnr,Connection con) {

		Datenbank_Tag tag = new Datenbank_Tag();
		LinkedList<Tag> tageList = tag.getTage(con);;
			
		if (!checkWochenplan(wpnr,con)){
			return null;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;
	
		//Siehe vorherige Klassen!
		String sqlStatement = "select Wpnr, �ffentlichkeitsstatus, �ffnungszeit, Schlie�zeit, Hauptzeitbeginn, Hauptzeitende, Benutzername,"
				+ "Minanzinfot, Minanzinfow, Minanzkasse, Mehrbesetzung from Wochenplan where Wpnr ="+wpnr;

		try {
			//Siehe vorherige Klassen!
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			rs = stmt.executeQuery(sqlStatement);
	
			rs.next();
			int mehrb=rs.getInt("Mehrbesetzung");
			if (rs.wasNull())
				mehrb = 0;
				
				//Siehe oben!
				Wochenplan wp = new Wochenplan(rs.getInt("Wpnr"),rs.getBoolean("�ffentlichkeitsstatus"),rs.getTime("�ffnungszeit").toString(),
						rs.getTime("Schlie�Zeit").toString(),rs.getTime("Hauptzeitbeginn").toString(),rs.getTime("Hauptzeitende").toString(),
						rs.getString("Benutzername"),rs.getInt("Minanzinfot"),
						rs.getInt("Minanzinfow"),rs.getInt("Minanzkasse"),mehrb);
				
				//Siehe vorherige Klassen!
				LinkedList<Tag> tagewp=new LinkedList<>();
				for (Tag ta : tageList) {
					if (ta.getWpnr() == wp.getWpnr()) {					
						tagewp.add(ta);			
					}
				}
				wp.setLinkedListTage(tagewp);
			rs.close();
			stmt.close();		

			return wp;
		}
		catch (SQLException sql) {
			System.err.println("Methode getWochenplan SQL-Fehler: " + sql.getMessage());
		}
			return null;
		}
		
		//Finally Block fehlt!
		}
	//}
	/**
	 * @author Anes Preljevic
	 * @info L�schen eines Wochenplans mit zugeh�rigen Tagen (schichten)  aus den Datenbank Tabellen 
	 * Wochenplan, Tag, Schicht, Ma-Schicht ( Schicht und Ma-Schicht werden �ber die Tag/Schicht - deleteMethode gel�scht).
	 */
	
	protected boolean deleteWochenplan(int wpnr,Connection con) {
		Datenbank_Tag tag = new Datenbank_Tag();
		

		if (!checkWochenplan(wpnr, con)){
			//Siehe vorherige Klassen!
			return false;
		}
		else{
		Statement stmt = null;
		ResultSet rs = null;
		String sqlStatement = "DELETE FROM WOCHENPLAN WHERE Wpnr = " + wpnr;
		tag.deleteTag(wpnr,con);
			
			
		
		try {
			//Siehe vorherige Klassen!
			con.setAutoCommit(false);
			
			stmt = con.createStatement();
			stmt.execute(sqlStatement);
			con.commit();

			con.setAutoCommit(true);
			return true;
			}
			catch (SQLException sql) {
			System.err.println("Methode deleteWochenplan SQL-Fehler: " + sql.getMessage());
			try {
				
				con.rollback();
				con.setAutoCommit(true);
				return false;
			} catch (SQLException sqlRollback) {
					System.err.println("Methode deleteWochenplan " + "- Rollback -  SQL-Fehler: " + sqlRollback.getMessage());
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
			}
		}
	}
	
	//Code kann gel�scht werden!

//	if(tmp[3].equalsIgnoreCase("null"))
//		pstmt.setNull(4, java.sql.Types.INTEGER);
//	else
//		pstmt.setInt(4, Integer.parseInt(tmp[3]));
	/**
	 * @author Anes Preljevic
	 * @info Fragt die h�chste Wpnr ab und erh�ht diese um 1, sodass bei neu Erstellung
	 * eines Wochenplans die n�chste Wpnr vorliegt.
	 */
	
	public int getNewWpnr(Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlQuery = "select max(wpnr)+1 from Wochenplan";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			rs.next();
			int maxWpnr = rs.getInt(1);
			rs.close();
			stmt.close();
			return maxWpnr;
		} catch (SQLException sql) {
			System.err.println("Methode getNewWpnr SQL-Fehler: "
					+ sql.getMessage());
			return -1;
		}
	}
}

	