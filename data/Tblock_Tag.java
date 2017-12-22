package data;

import java.util.LinkedList;

public class Tblock_Tag {
	private int tblocknr=0;
	private String tbez=null;
	private int wpnr=0;
	private LinkedList<TerminBlockierung> ll_termin = new LinkedList<TerminBlockierung>();
	
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Tblock_Tag und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter für alle Variablen.
	 */
	public Tblock_Tag(int tblocknr, String tbez, int wpnr){
		this.tblocknr=tblocknr;
		this.tbez=tbez;
		this.wpnr=wpnr;
		
	}
	public LinkedList<TerminBlockierung> getLl_termin() {
		return ll_termin;
	}

	public void setLinkedList_termine(TerminBlockierung terminblockierung) {
		this.ll_termin = ll_termin;
	}


	public int getWpnr() {
		return wpnr;
	}

	public void setWpnr(int wpnr) {
		this.wpnr = wpnr;
	}

	public int getTblocknr() {
		return tblocknr;
	}

	public void setTblocknr(int tblocknr) {
		this.tblocknr = tblocknr;
	}

	public String getTbez() {
		return tbez;
	}

	public void setTbez(String tbez) {
		this.tbez = tbez;
	}
	
}
