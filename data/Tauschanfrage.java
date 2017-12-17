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
	
	private LinkedList<Schicht> ll_schichtsender = new LinkedList<Schicht>();
	private LinkedList<Schicht> ll_schichtempfänger = new LinkedList<Schicht>();
	
	private LinkedList<Mitarbeiter> ll_empfänger = new LinkedList<Mitarbeiter>();
	private LinkedList<Mitarbeiter> ll_sender = new LinkedList<Mitarbeiter>();
	
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
	public LinkedList<Schicht> getLl_schichtsender() {
		return ll_schichtsender;
	}

	public void setLinkedListSchichtensender(Schicht sch) {
		this.ll_schichtsender=ll_schichtsender;
		
	}
	public LinkedList<Mitarbeiter> getLl_Sender() {
		return ll_sender;
	}

	public void setLinkedListSender(Mitarbeiter ma) {
		this.ll_sender=ll_sender;
		
	}
	public LinkedList<Schicht> getLl_schichtempfänger() {
		return ll_schichtempfänger;
	}

	public void setLinkedListSchichtenempfänger(Schicht sch) {
		this.ll_schichtempfänger=ll_schichtempfänger;
		
	}
	public LinkedList<Mitarbeiter> getLl_empfänger() {
		return ll_empfänger;
	}

	public void setLinkedListEmpfänger(Mitarbeiter ma) {
		this.ll_empfänger=ll_empfänger;
		
	}
	
	
}
