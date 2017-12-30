package model;
import java.sql.*;

//Klasse wird nicht verwendet und kann weg!

public class DBConnector {

	private Connection con = null;	

	
	private final String url = "jdbc:mysql://localhost:3306/softwareprojekt";
	private final String reconnectSSL = "?autoReconnect=true&useSSL=false";
	private final String user = "root";
	private final String pw = "famasb0b12";
	

	
	public DBConnector(){
		createCon();	
	}
	
	private void createCon() {
		
		try{
			
			con = DriverManager.getConnection(url+ reconnectSSL, user, pw);
			
	
			
		}catch(SQLException sqle){
			System.out.println("Fehler beim Verbindungsaufbau zur Datenbank!");
			System.out.println("SQL-Fehler: ");
			sqle.printStackTrace();
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
	public Connection getCon(){
		return con;
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
