package data;

public class Tauschanfrage {
	private String empfänger;
	private String sender;
	private boolean bestätigungsstatus;
	private int schichtnrsender;
	private int schichtnrempfänger;
	private int tauschnr;
	
	
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
	
}
