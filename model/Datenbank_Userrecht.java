package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Ma_Schicht;
import data.Userrecht;


//Klassenbeschreibung fehlt!



//Kommentare innerhalb der Methode fehlen

class Datenbank_Userrecht {
	
	/**
	 * @author Anes Preljevic
	 * @info Auslesen der Userrecht aus der Datenbank und hinzufügen in eine Liste, welche den Ausgabewert darstellt 
	 */
	public Userrecht getUserrecht(String job ,Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "SELECT Job,Benutzerrolle  FROM Userrecht where job ='"+job+"'";

		try {
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sqlStatement);
			
			rs.next();

			Userrecht u = new Userrecht(rs.getString("Job"),rs.getString("Benutzerrolle"));

			rs.close();
			stmt.close();

			return u;

		} catch (SQLException sql) {
			System.err.println("Methode getUserrecht SQL-Fehler: " + sql.getMessage());
			return null;
		}finally {
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

