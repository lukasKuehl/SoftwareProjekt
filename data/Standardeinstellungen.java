package data;

import java.util.LinkedList;

public class Standardeinstellungen {
	
	private String �ffnungszeit;
	private String schlie�zeit;
	private String hauptzeitbeginn;
	private String hauptzeitende;
	private int anzschicht;
	private int mehrbesetzungkasse;
	private int minanzkasse;
	private int minanzinfot;
	private int minanzinfow;
	
	
	public Standardeinstellungen(String �ffnungszeit, String schlie�zeit, String hauptzeitbeginn,
			String hauptzeitende, int anzschicht, int mehrbesetzungkasse, int minanzkasse, int minanzinfot, int minanzinfow) {
		this.�ffnungszeit=�ffnungszeit;
		this.schlie�zeit=schlie�zeit;
		this.hauptzeitbeginn=hauptzeitbeginn;
		this.hauptzeitende=hauptzeitende;
		this.anzschicht=anzschicht;
		this.mehrbesetzungkasse=mehrbesetzungkasse;
		this.minanzkasse=minanzkasse;
		this.minanzinfot=minanzinfot;
		this.minanzinfow=minanzinfow;
	}

	public String get�ffnungszeit() {
		return �ffnungszeit;
	}

	public void set�ffnungszeit(String �ffnungszeit) {
		this.�ffnungszeit = �ffnungszeit;
	}

	public String getSchlie�zeit() {
		return schlie�zeit;
	}

	public void setSchlie�zeit(String schlie�zeit) {
		this.schlie�zeit = schlie�zeit;
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
