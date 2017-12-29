package model;
import java.sql.*;

//Klassenbeschreibung fehlt --> Erl‰uterung warum wir daf¸r eine eigene Klasse brauchen
/**
 * @author Anes Preljevic
 * @info 
 */
public class Datenbank_Connection{

	private Connection con = null;	

	//Leerer Konstruktor kann einfach weg --> selber Effekt
	public Datenbank_Connection(){
		
	}
	protected  Connection createCon() {
		
		try{
			//Das sind die spezifischen Settings mit denen alle anderen direkt eine Fehlermeldung bekommen, bitte einfach frei lassen, sodass die erst jeder ausf¸llen muss. Dann aber anschlieﬂend diese Klasse nicht mehr committen
			return  DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?autoReconnect=true&useSSL=false", "root", "famasb0b12");

		}catch(SQLException sqle){
			
			System.out.println("Fehler beim Verbindungsaufbau zur Datenbank!");
			System.out.println("SQL-Fehler: ");
			sqle.printStackTrace();
			return null;
		}finally{
			//Aufruf der Methode closeDBCon h‰tte den selben Effekt --> Vermeidung von doppeltem Code
			try{
				if(con != null)
					con.close();
			}catch(SQLException sqlefinal){
				System.out.println("Fehler beim Schlieﬂen der Verbindung:");
				sqlefinal.printStackTrace();
			}			
			
		}
				
	}

	public static void closeDBCon(Connection con){
		try{
			if(con != null)
				con.close();
		}catch(SQLException sqlexception){
			System.err.println("MethodecloseDBCon SQL-Fehler: "
					+ sqlexception.getMessage());
		}
	}
	
}