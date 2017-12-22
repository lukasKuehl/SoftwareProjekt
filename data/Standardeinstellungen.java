package data;

import java.util.LinkedList;

public class Standardeinstellungen {
	
	private String öffnungszeit=null;
	private String schließzeit=null;
	private String hauptzeitbeginn=null;
	private String hauptzeitende=null;
	private int mehrbesetzung=0;
	private int minanzkasse=0;
	private int minanzinfot=0;
	private int minanzinfow=0;
	
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Standardeinstellungen und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter für alle Variablen.
	 */
	
	public Standardeinstellungen(String öffnungszeit, String schließzeit, String hauptzeitbeginn,
			String hauptzeitende,  int mehrbesetzung, int minanzkasse, int minanzinfot, int minanzinfow) {
		this.öffnungszeit=öffnungszeit;
		this.schließzeit=schließzeit;
		this.hauptzeitbeginn=hauptzeitbeginn;
		this.hauptzeitende=hauptzeitende;
		this.mehrbesetzung=mehrbesetzung;
		this.minanzkasse=minanzkasse;
		this.minanzinfot=minanzinfot;
		this.minanzinfow=minanzinfow;
	}

	public String getÖffnungszeit() {
		return öffnungszeit;
	}

	public void setÖffnungszeit(String öffnungszeit) {
		this.öffnungszeit = öffnungszeit;
	}

	public String getSchließzeit() {
		return schließzeit;
	}

	public void setSchließzeit(String schließzeit) {
		this.schließzeit = schließzeit;
	}

	public String getHauptzeitbeginn() {
		return hauptzeitbeginn;
	}

	public void setHauptzeitbeginn(String hauptzeitbeginn) {
		this.hauptzeitbeginn = hauptzeitbeginn;
	}

	public String getHauptzeitende() {
		return hauptzeitende;
	}

	public void setHauptzeitende(String hauptzeitende) {
		this.hauptzeitende = hauptzeitende;
	}


	public int getMehrbesetzung() {
		return mehrbesetzung;
	}

	public void setMehrbesetzung(int mehrbesetzung) {
		this.mehrbesetzung = mehrbesetzung;
	}

	public int getMinanzkasse() {
		return minanzkasse;
	}

	public void setMinanzkasse(int minanzkasse) {
		this.minanzkasse = minanzkasse;
	}

	public int getMinanzinfot() {
		return minanzinfot;
	}

	public void setMinanzinfot(int minanzinfot) {
		this.minanzinfot = minanzinfot;
	}

	public int getMinanzinfow() {
		return minanzinfow;
	}

	public void setMinanzinfow(int minanzinfow) {
		this.minanzinfow = minanzinfow;
	}
	
}
