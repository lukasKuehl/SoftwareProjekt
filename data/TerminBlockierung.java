package data;

public class TerminBlockierung {
	private int tblocknr;
	private String benutzername;
	private String bbez;
	private String anfzeitraum;
	private String endzeitraum;
	private String anfanguhrzeit;
	private String endeuhrzeit;
	private String grund;
	
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ TerminBlockierung und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter für alle Variablen.
	 */
	public TerminBlockierung(int tblocknr, String benutzername,
			String bbez, String anfzeitraum, String endzeitraum, 
			 String anfanguhrzeit, String endeuhrzeit, String grund){
			
		this.tblocknr=tblocknr;
		this.benutzername=benutzername;
		this.bbez=bbez;
		this.anfzeitraum=anfzeitraum;
		this.endzeitraum=endzeitraum;
		this.anfanguhrzeit=anfanguhrzeit;
		this.endeuhrzeit=endeuhrzeit;
	}

	public int getTblocknr() {
		return tblocknr;
	}

	public void setTblocknr(int tblocknr) {
		this.tblocknr = tblocknr;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getBbez() {
		return bbez;
	}

	public void setBbez(String bbez) {
		this.bbez = bbez;
	}

	public String getAnfzeitraum() {
		return anfzeitraum;
	}

	public void setAnfzeitraum(String anfzeitraum) {
		this.anfzeitraum = anfzeitraum;
	}

	public String getEndzeitraum() {
		return endzeitraum;
	}

	public void setEndzeitraum(String endzeitraum) {
		this.endzeitraum = endzeitraum;
	}

	public String getAnfanguhrzeit() {
		return anfanguhrzeit;
	}

	public void setAnfanguhrzeit(String anfanguhrzeit) {
		this.anfanguhrzeit = anfanguhrzeit;
	}

	public String getEndeuhrzeit() {
		return endeuhrzeit;
	}

	public void setEndeuhrzeit(String endeuhrzeit) {
		this.endeuhrzeit = endeuhrzeit;
	}

	public String getGrund() {
		return grund;
	}

	public void setGrund(String grund) {
		this.grund = grund;
	}
	
}
