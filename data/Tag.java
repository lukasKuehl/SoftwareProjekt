package data;

import java.util.LinkedList;
import data.Schicht;

public class Tag {
	private String tbez;
	private int wpnr;
	private int anzschicht;
	private boolean feiertag;
	private LinkedList<Schicht> ll_schicht = new LinkedList<Schicht>();
	private LinkedList<Tblock_Tag> ll_tblocktag = new LinkedList<Tblock_Tag>();
	
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Tag und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter für alle Variablen.
	 */
	public Tag(String tbez, int wpnr, int anzschicht, boolean feiertag){
		this.wpnr=wpnr;
		this.anzschicht=anzschicht;
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

	public int getAnzschicht() {
		return anzschicht;
	}

	public void setAnzschicht(int anzschicht) {
		this.anzschicht = anzschicht;
	}

	public boolean isFeiertag() {
		return feiertag;
	}

	public void setFeiertag(boolean feiertag) {
		this.feiertag = feiertag;
	}
	public LinkedList<Schicht> getLl_schicht() {
		return ll_schicht;
	}

	public void setLinkedListSchichten(Schicht sch) {
		this.ll_schicht=ll_schicht;
		
	}


	public LinkedList<Tblock_Tag> getLl_tblocktag() {
		return ll_tblocktag;
	}

	public void setLinkedListTblock_Tag(Tblock_Tag tbt) {
		this.ll_tblocktag=ll_tblocktag;
		
	}


	
}
