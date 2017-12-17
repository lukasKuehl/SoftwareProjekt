package data;
	
public class Ma_Schicht {
	private String benutzername=null;
	private int schichtnr =0;
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Ma_Schicht und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter für alle Variablen.
	 */
	public Ma_Schicht(String benutzername, int schichtnr){
		this.benutzername=benutzername;
		this.schichtnr=schichtnr;
		
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public int getSchichtnr() {
		return schichtnr;
	}

	public void setSchichtnr(int schichtnr) {
		this.schichtnr = schichtnr;
	}
	
}
