package data;

import java.util.LinkedList;
import data.Schicht;

public class Tag {
	private String tbez=null;
	private int wpnr=0;
	private boolean feiertag=false;
	private LinkedList<Schicht> ll_schicht = null;
	private LinkedList<Tblock_Tag> ll_tblocktag = null;
	
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Tag und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter für alle Variablen.
	 */
	public Tag(String tbez, int wpnr, boolean feiertag){
		this.tbez=tbez;
		this.wpnr=wpnr;
		this.feiertag=feiertag;
		
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



	public boolean isFeiertag() {
		return feiertag;
	}

	public void setFeiertag(boolean feiertag) {
		this.feiertag = feiertag;
	}
	public LinkedList<Schicht> getLl_Schicht() {
		return ll_schicht;
	}

	public void setLinkedListSchichten(LinkedList<Schicht> ll_schicht) {
		this.ll_schicht=ll_schicht;
		
	}


	public LinkedList<Tblock_Tag> getLl_Tblocktag() {
		return ll_tblocktag;
	}

	public void setLinkedListTblock_Tag(LinkedList<Tblock_Tag> ll_tblocktag) {
		this.ll_tblocktag=ll_tblocktag;
		
	}


	
}
