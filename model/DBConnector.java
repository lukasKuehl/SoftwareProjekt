package model;
import java.sql.*;


public class DBConnector {

	private Connection con = null;	
	private Statement st = null;
	private ResultSet rs = null;
	
	private final String url = "jdbc:mysql://localhost:3306/softwareprojekt";
	private final String reconnectSSL = "?autoReconnect=true&useSSL=false";
	private final String user = "root";
	private final String pw = "";
	
	public static void main(String[] args){
		new DBConnector();
	}
	
	public DBConnector(){
		createCon();	
	}
	
	private void createCon() {
		
		try{
			
			con = DriverManager.getConnection(url+ reconnectSSL, user, pw);
			
			st = con.createStatement();
			
			st.execute("select * from warenhaus");
			//st.execute("drop table test");		
			System.out.println("Verbindungsaufbau erfolgreich!");
			
		}catch(SQLException sqle){
			System.out.println("Fehler beim Verbindungsaufbau zur Datenbank!");
			System.out.println("SQL-Fehler: ");
			sqle.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();
				
				if(st != null)
					st.close();
				
				if(con != null)
					con.close();
			}catch(SQLException sqlefinal){
				System.out.println("Fehler beim Schlieﬂen der Verbindung:");
				sqlefinal.printStackTrace();
			}			
			
		}		
	}
}
