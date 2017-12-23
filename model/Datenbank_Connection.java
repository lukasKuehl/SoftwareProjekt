package model;
import java.sql.*;


/**
 * @author Anes Preljevic
 * @info 
 */
public class Datenbank_Connection{

	private Connection con = null;	

	public Datenbank_Connection(){
		
	}
	protected  Connection createCon() {
		
		try{
			
			return  DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?autoReconnect=true&useSSL=false", "root", "famasb0b12");

		}catch(SQLException sqle){
			
			System.out.println("Fehler beim Verbindungsaufbau zur Datenbank!");
			System.out.println("SQL-Fehler: ");
			sqle.printStackTrace();
			return null;
		}finally{
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