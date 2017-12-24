package data;

public class TerminBlockierung {
	private int tblocknr=0;
	private String benutzername=null;
	private String bbez=null;
	private String anfzeitraum=null;
	private String endzeitraum=null;
	private String anfanguhrzeit=null;
	private String endeuhrzeit=null;
	private String grund=null;
	
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
		this.grund=grund;
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

	public String getAnfangzeitraum() {
		return anfzeitraum;
	}

	public void setAnfangzeitraum(String anfzeitraum) {
		this.anfzeitraum = anfzeitraum;
	}

	public String getEndezeitraum() {
		return endzeitraum;
	}

	public void setEndezeitraum(String endzeitraum) {
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
