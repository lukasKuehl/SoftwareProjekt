package data;
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
	
}
