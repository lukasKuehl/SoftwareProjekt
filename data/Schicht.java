package data;

import java.util.LinkedList;

public class Schicht {
	private int schichtnr= 0;
	private String tbez =null;
	private int wpnr=0;
	private String anfanguhrzeit=null;
	private String endeuhrzeit=null;
	private LinkedList<Ma_Schicht> ll_ma_schicht = null;
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

	public LinkedList<Ma_Schicht> getLl_ma_schicht() {
		return ll_ma_schicht;
	}

	public void setLl_ma_schicht(LinkedList<Ma_Schicht> ll_ma_schicht) {
		this.ll_ma_schicht = ll_ma_schicht;
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
	public LinkedList<Ma_Schicht> getLl_maschicht() {
		return ll_ma_schicht;
	}

	public void setLinkedListMa_Schicht(LinkedList<Ma_Schicht> ll_ma_schicht) {
		this.ll_ma_schicht=ll_ma_schicht;
		
	}
	
}
