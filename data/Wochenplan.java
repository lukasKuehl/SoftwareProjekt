package data;

import java.util.LinkedList;

import data.Tag;

public class Wochenplan {

	private int wpnr=0;
	private boolean �ffentlichstatus=false;
	private String �ffnungszeit=null;
	private String schlie�zeit=null;
	private String hauptzeitbeginn=null;
	private String hauptzeitende=null;
	private String benutzername=null;
	private int minanzinfot=0;
	private int minanzinfow=0;
	private int minanzkasse=0;
	private int mehrbesetzung=0;
	
	private LinkedList<Tag> ll_tag = new LinkedList<Tag>();
	
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Wochenplan und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter f�r alle Variablen.
	 */
	public Wochenplan (int wpnr,
	boolean �ffentlichstatus,String �ffnungszeit,String schlie�zeit,
	 String hauptzeitbeginn,String hauptzeitende,String benutzername,
	 int minanzinfot,int minanzinfow,int minanzkasse,int mehrbesetzung) {

		

		this.wpnr = wpnr;
		this.�ffentlichstatus = �ffentlichstatus;
		this.�ffnungszeit = �ffnungszeit;
		this.schlie�zeit = schlie�zeit;
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


	public boolean is�ffentlichstatus() {
		return �ffentlichstatus;
	}


	public void set�ffentlichstatus(boolean �ffentlichstatus) {
		this.�ffentlichstatus = �ffentlichstatus;
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
