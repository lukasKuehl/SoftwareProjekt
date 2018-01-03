package data;

import java.util.LinkedList;

/**
 * @Anes Preljevic 
 * @info  Fügt eine neue Tauschanfrage in der Tabelle Tauschanfrage hinzu
 */
public class Tauschanfrage {
	private String empfänger=null;
	private String sender=null;
	private boolean bestätigungsstatus=false;
	private int schichtnrsender=0;
	private int schichtnrempfänger=0;
	private int tauschnr=0;
	
	private Schicht schtSchichtsender = null;
	private Schicht schtSchichtempfänger = null;
	
	private Mitarbeiter maEmpfänger =null;
	private Mitarbeiter maSender =null;
	
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Tauschanfrage und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter für alle Variablen.
	 */
	public Tauschanfrage (String empfänger,String sender,boolean bestätigungsstatus,
			int schichtnrsender,int schichtnrempfänger, int tauschnr) {
		this.empfänger= empfänger;
		this.sender=sender;
		this.bestätigungsstatus=bestätigungsstatus;
		this.schichtnrsender=schichtnrsender;
		this.schichtnrempfänger=schichtnrempfänger;
		this.tauschnr=tauschnr;
	
	}


	public String getEmpfänger() {
		return empfänger;
	}


	public void setEmpfänger(String empfänger) {
		this.empfänger = empfänger;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public boolean isBestätigungsstatus() {
		return bestätigungsstatus;
	}


	public void setBestätigungsstatus(boolean bestätigungsstatus) {
		this.bestätigungsstatus = bestätigungsstatus;
	}


	public int getSchichtnrsender() {
		return schichtnrsender;
	}


	public void setSchichtnrsender(int schichtnrsender) {
		this.schichtnrsender = schichtnrsender;
	}
	public int getSchichtnrempfänger() {
		return schichtnrempfänger;
	}


	public void setSchichtnrempfänger(int schichtnrempfänger) {
		this.schichtnrempfänger = schichtnrempfänger;
	}


	public int getTauschnr() {
		return tauschnr;
	}


	public void setTauschnr(int tauschnr) {
		this.tauschnr = tauschnr;
	}
	public Schicht getSchtSchichtsender() {
		return schtSchichtsender;
	}

	public void setSchtSchichtensender(Schicht schtSchichtsender) {
		this.schtSchichtsender=schtSchichtsender;
		
	}
	public Mitarbeiter getMaSender() {
		return maSender;
	}

	public void setMaSender(Mitarbeiter maSender) {
		this.maSender=maSender;
		 
	}
	public Schicht getSchtSchichtempfänger() {
		return schtSchichtempfänger;
	}

	public void setSchtSchichtenempfänger(Schicht schtSchichtempfänger) {
		this.schtSchichtempfänger=schtSchichtempfänger;
		
	}
	public Mitarbeiter getMaEmpfänger() {
		return maEmpfänger;
	}

	public void setMaEmpfänger(Mitarbeiter maEmpfänger) {
		this.maEmpfänger=maEmpfänger;
		
	}
	
	
}

