package data;

import java.util.LinkedList;

import data.Tag;

public class Wochenplan {

	private int wpnr;
	private boolean öffentlichstatus;
	private String öffnungszeit;
	private String schließzeit;
	private String hauptzeitbeginn;
	private String hauptzeitende;
	private String benutzername;
	private int minanzinfot;
	private int minanzinfow;
	private int minanzkasse;
	private int mehrbesetzung;
	
	private LinkedList<Tag> ll_tag = new LinkedList<Tag>();
	
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Wochenplan und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter für alle Variablen.
	 */
	public Wochenplan (int wpnr,
	boolean öffentlichstatus,String öffnungszeit,String schließzeit,
	 String hauptzeitbeginn,String hauptzeitende,String benutzername,
	 int minanzinfot,int minanzinfow,int minanzkasse,int mehrbesetzung) {

		

		this.wpnr = wpnr;
		this.öffentlichstatus = öffentlichstatus;
		this.öffnungszeit = öffnungszeit;
		this.schließzeit = schließzeit;
		this.hauptzeitbeginn = hauptzeitbeginn;
		this.hauptzeitende = hauptzeitende;
		this.benutzername=benutzername;
		this.minanzinfot=minanzinfot;
		this.minanzinfow=minanzinfow;
		this.mehrbesetzung=mehrbesetzung;
		
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


	public int getMinanzkasse() {
		return minanzkasse;
	}


	public void setMinanzkasse(int minanzkasse) {
		this.minanzkasse = minanzkasse;
	}


	public int getMehrbesetzung() {
		return mehrbesetzung;
	}


	public void setMehrbesetzung(int mehrbesetzung) {
		this.mehrbesetzung = mehrbesetzung;
	}


	public LinkedList<Tag> getLl_tag() {
		return ll_tag;
	}


	public void setLl_tag(LinkedList<Tag> ll_tag) {
		this.ll_tag = ll_tag;
	}


	public int getWpnr() {
		return wpnr;
	}


	public void setWpnr(int wpnr) {
		this.wpnr = wpnr;
	}


	public boolean isÖffentlichstatus() {
		return öffentlichstatus;
	}


	public void setÖffentlichstatus(boolean öffentlichstatus) {
		this.öffentlichstatus = öffentlichstatus;
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


	public String getBenutzername() {
		return benutzername;
	}


	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}


	public LinkedList<Tag> getLinkedListTage() {
		return ll_tag;
	}



	public void setLinkedListTage(Tag ta) {
		
		this.ll_tag= ll_tag;
		
	}



}
