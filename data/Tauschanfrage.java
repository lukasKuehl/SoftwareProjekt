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
	
	private LinkedList<Schicht> ll_schichtsender = null;
	private LinkedList<Schicht> ll_schichtempfänger = null;
	
	private LinkedList<Mitarbeiter> ll_empfänger =null;
	private LinkedList<Mitarbeiter> ll_sender =null;
	
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
		//this.ll_schichtsender= new LinkedList<Schicht>();
		//this.ll_schichtempfänger= new LinkedList<Schicht>();
		//this.ll_sender= new LinkedList<Mitarbeiter>();
		//this.ll_empfänger= new LinkedList<Mitarbeiter>();
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
	public LinkedList<Schicht> getLl_Schichtsender() {
		return ll_schichtsender;
	}

	public void setLinkedListSchichtensender(LinkedList<Schicht> ll_schichtsender) {
		this.ll_schichtsender=ll_schichtsender;
		
	}
	public LinkedList<Mitarbeiter> getLl_Sender() {
		return ll_sender;
	}

	public void setLinkedListSender(LinkedList<Mitarbeiter> ll_sender) {
		this.ll_sender=ll_sender;
		 
	}
	public LinkedList<Schicht> getLl_Schichtempfänger() {
		return ll_schichtempfänger;
	}

	public void setLinkedListSchichtenempfänger(LinkedList<Schicht> ll_schichtempfänger) {
		this.ll_schichtempfänger=ll_schichtempfänger;
		
	}
	public LinkedList<Mitarbeiter> getLl_Empfänger() {
		return ll_empfänger;
	}

	public void setLinkedListEmpfänger(LinkedList<Mitarbeiter> ll_empfänger) {
		this.ll_empfänger=ll_empfänger;
		
	}
	
	
}
