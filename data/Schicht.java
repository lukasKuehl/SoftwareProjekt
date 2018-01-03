package data;

import java.util.LinkedList;

public class Schicht {
	private int schichtnr= 0;
	private String tbez =null;
	private int wpnr=0;
	private String anfanguhrzeit=null;
	private String endeuhrzeit=null;
	private LinkedList<Mitarbeiter> ll_mitarbeiter = null;
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Schicht und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter für alle Variablen.
	 */
	public Schicht(int schichtnr, String tbez, int wpnr,String anfanguhrzeit,String endeuhrzeit){
		this.schichtnr=schichtnr;
		this.tbez=tbez;
		this.wpnr=wpnr;
		this.anfanguhrzeit=anfanguhrzeit;
		this.endeuhrzeit=endeuhrzeit;
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

	public LinkedList<Mitarbeiter> getLl_mitarbeiter() {
		return ll_mitarbeiter;
	}

	public void setLl_mitarbeiter(LinkedList<Mitarbeiter> ll_ma_schicht) {
		this.ll_mitarbeiter = ll_mitarbeiter;
	}

	public int getSchichtnr() {
		return schichtnr;
	}

	public void setSchichtnr(int schichtnr) {
		this.schichtnr = schichtnr;
	}


	public String getTbez() {
		return tbez;
	}

	public void setTbez(String tbez) {
		this.tbez = tbez;
	}

	public int getWpnr() {
		return wpnr;
	}

	public void setWpnr(int wpnr) {
		this.wpnr = wpnr;
	}

}
