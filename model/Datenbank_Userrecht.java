package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import data.Ma_Schicht;
import data.Userrecht;

class Datenbank_Userrecht {


	
	
	/**
	 * @author Anes Preljevic
	 * @info Auslesen der Userrecht aus der Datenbank und hinzufügen in eine Liste, welche den Ausgabewert darstellt 
	 */
	public LinkedList<Userrecht> getUserrecht(Connection con) {

		Statement stmt = null;
		ResultSet rs = null;

		String sqlStatement = "SELECT Job,Benutzerrolle  FROM Userrecht";

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlStatement);
			
			LinkedList<Userrecht> userrechtList = new LinkedList<>();

			while (rs.next()) {

				Userrecht u = new Userrecht(rs.getString("Job"),rs.getString("Userrecht"));

				userrechtList.add(u);
			}

			rs.close();
			stmt.close();

			return userrechtList;

		} catch (SQLException sql) {
			System.err.println("Methode getUserrecht SQL-Fehler: " + sql.getMessage());
			return null;
		}
	}
	}

