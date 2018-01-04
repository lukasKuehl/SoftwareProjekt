package model;
import java.sql.*;


/**
 * @author Anes Preljevic
 * @info Die Klasse Datenbank_Connection soll eine Verbindung zur Datenbank herstellen,
 *  gegebenenfalls die Verbindung verwalten, pr�fen, entfernen.
 */
class Datenbank_Connection{
	
	//Initialsierung der Instanzvariable
	private Connection con = null;	

	/**
	 * @author Anes Preljevic
	 * @info Die Methode createCon soll zu einer Datenbank eine Verbindung herstellen.
	 * Bei erfolgreichem Verbindungsaufbau soll ein Connection-Objekt zur�ckgegeben werden,
	 * bei fehlschlag wird null zur�ckgegeben und eine Ausgabe zur Fehlersuche.
	 */
	protected  Connection createCon() {
		
		try{
			//Gibt die Datenbankverbindung wieder, welche �ber den angegebenen Link abgefragt wird(url,name,pw)
			return  DriverManager.getConnection("jdbc:mysql://localhost:3306/apreljevic?autoReconnect=true&useSSL=false", "root", "famasb0b12");

		}catch(SQLException sqle){
			//Fehlerhandling, Ausgaben zur Ursachensuche und R�ckgabewert auf null setzen
			System.out.println("Fehler beim Verbindungsaufbau zur Datenbank!");
			System.out.println("SQL-Fehler: ");
			sqle.printStackTrace();
			return null;
		}finally{
			//Schlie�en der offen gebliebenen Datenverbindung
			closeDBCon(con);
			
		}
				
	}
	/**
	 * @author Anes Preljevic
	 * @info Normale get-Methode f�r Connection-Objekte.
	 */
	protected Connection getCon(){
		return con;
	}
	/**
	 * @author Anes Preljevic
	 * @info Die Methode closeDBcon soll die aktuelle Datenbankverbindung schlie�en, falls
	 * diese Vorhanden ist. Ansonsten wird eine Ausgabe zur Fehlersuche erzeugt.
	 */
	protected static void closeDBCon(Connection con){
		try{
			//Wenn die Datenbankverbindung nicht null ist, wird diese geschlossen
			if(con != null)
				con.close();
		}catch(SQLException sqlexception){
			//Fehlerhandling, Ausgaben zur Ursachensuche
			System.err.println("MethodecloseDBCon SQL-Fehler: "
					+ sqlexception.getMessage());
		}
	}
	
}