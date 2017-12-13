package model;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.JFileChooser;



import data.Standardeinstellungen;

class Datenbank_Standardeinstellungen {

	Datenbank_Connection db_con = new Datenbank_Connection();
	Connection con = db_con.getCon();

	private Einsatzplanmodel myModel = null;

	protected Datenbank_Standardeinstellungen(Einsatzplanmodel mymodel) {
	this.myModel = myModel;
	}



	// Auslesen der Wochenpl‰ne aus der Datenbank und eintragen in eine TreeMap, welche ‹bergeben wird
	protected LinkedList<Standardeinstellungen> getStandardeinstellungen() {

		Statement stmt = null;
		ResultSet rs = null;
		String sqlStatement = "select ÷ffnungszeit, Schlieﬂzeit, Hauptzeitbeginn,"
				+ " Hauptzeitende, Anzschicht, Mehrbesetzungkasse,"
				+ "Minanzinfot, Minanzinfow, Minanzkasse from Standardeinstellungen";

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);

			LinkedList<Standardeinstellungen> standardeinstellungenList = new LinkedList();

			while (rs.next()) {
				Standardeinstellungen s = new Standardeinstellungen(sqlStatement, sqlStatement, sqlStatement, sqlStatement, 0, 0, 0, 0, 0);


				s.set÷ffnungszeit(rs.getString("Oeffnungszeit"));
				s.setSchlieﬂzeit(rs.getString("Schlieﬂzeit"));
				s.setHauptzeitbeginn(rs.getString("Hauptzeitbeginn"));
				s.setHauptzeitende(rs.getString("Hauptzeitende"));
				s.setAnzschicht(rs.getInt("Anzschicht"));
				s.setMehrbesetzungkasse(rs.getInt("Mehrbesetzungkasse"));
				s.setMinanzinfot(rs.getInt("Minanzinfot"));
				s.setMinanzinfow(rs.getInt("Minanzinfow"));
				s.setMinanzkasse(rs.getInt("Minanzkasse"));
				
				

				
			}

			rs.close();
			stmt.close();

			return standardeinstellungenList;

		} catch (SQLException sql) {
			return null;
		}
	}



	
}
