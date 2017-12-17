package data;

import java.util.LinkedList;

/**
 * @Anes Preljevic 
 * @info  F�gt eine neue Tauschanfrage in der Tabelle Tauschanfrage hinzu
 */
public class Tauschanfrage {
	private String empf�nger=null;
	private String sender=null;
	private boolean best�tigungsstatus=false;
	private int schichtnrsender=0;
	private int schichtnrempf�nger=0;
	private int tauschnr=0;
	
	private LinkedList<Schicht> ll_schichtsender = new LinkedList<Schicht>();
	private LinkedList<Schicht> ll_schichtempf�nger = new LinkedList<Schicht>();
	
	private LinkedList<Mitarbeiter> ll_empf�nger = new LinkedList<Mitarbeiter>();
	private LinkedList<Mitarbeiter> ll_sender = new LinkedList<Mitarbeiter>();
	
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Tauschanfrage und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter f�r alle Variablen.
	 */
	public Tauschanfrage (String empf�nger,String sender,boolean best�tigungsstatus,
			int schichtnrsender,int schichtnrempf�nger, int tauschnr) {
		this.empf�nger= empf�nger;
		this.sender=sender;
		this.best�tigungsstatus=best�tigungsstatus;
		this.schichtnrsender=schichtnrsender;
		this.schichtnrempf�nger=schichtnrempf�nger;
		this.tauschnr=tauschnr;
	}


	public String getEmpf�nger() {
		return empf�nger;
	}


	public void setEmpf�nger(String empf�nger) {
		this.empf�nger = empf�nger;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public boolean isBest�tigungsstatus() {
		return best�tigungsstatus;
	}


	public void setBest�tigungsstatus(boolean best�tigungsstatus) {
		this.best�tigungsstatus = best�tigungsstatus;
	}


	public int getSchichtnrsender() {
		return schichtnrsender;
	}


	public void setSchichtnrsender(int schichtnrsender) {
		this.schichtnrsender = schichtnrsender;
	}
	public int getSchichtnrempf�nger() {
		return schichtnrempf�nger;
	}


	public void setSchichtnrempf�nger(int schichtnrempf�nger) {
		this.schichtnrempf�nger = schichtnrempf�nger;
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
	public LinkedList<Schicht> getLl_schichtempf�nger() {
		return ll_schichtempf�nger;
	}

	public void setLinkedListSchichtenempf�nger(Schicht sch) {
		this.ll_schichtempf�nger=ll_schichtempf�nger;
		
	}
	public LinkedList<Mitarbeiter> getLl_empf�nger() {
		return ll_empf�nger;
	}

	public void setLinkedListEmpf�nger(Mitarbeiter ma) {
		this.ll_empf�nger=ll_empf�nger;
		
	}
	
	
}
