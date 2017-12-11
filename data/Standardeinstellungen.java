package data;

import java.util.LinkedList;

public class Standardeinstellungen {
	
	private String öffnungszeit;
	private String schließzeit;
	private String hauptzeitbeginn;
	private String hauptzeitende;
	private int anzschicht;
	private int mehrbesetzungkasse;
	private int minanzkasse;
	private int minanzinfot;
	private int minanzinfow;
	
	
	public Standardeinstellungen(String öffnungszeit, String schließzeit, String hauptzeitbeginn,
			String hauptzeitende, int anzschicht, int mehrbesetzungkasse, int minanzkasse, int minanzinfot, int minanzinfow) {
		this.öffnungszeit=öffnungszeit;
		this.schließzeit=schließzeit;
		this.hauptzeitbeginn=hauptzeitbeginn;
		this.hauptzeitende=hauptzeitende;
		this.anzschicht=anzschicht;
		this.mehrbesetzungkasse=mehrbesetzungkasse;
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

	public int getAnzschicht() {
		return anzschicht;
	}

	public void setAnzschicht(int anzschicht) {
		this.anzschicht = anzschicht;
	}

	public int getMehrbesetzungkasse() {
		return mehrbesetzungkasse;
	}

	public void setMehrbesetzungkasse(int mehrbesetzungkasse) {
		this.mehrbesetzungkasse = mehrbesetzungkasse;
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
