package data;

public class Userrecht {
	private String job=null;
	private String benutzerrolle=null;
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Userrecht und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter für alle Variablen.
	 */
	public Userrecht(String job, String benutzerrolle){
		this.job=job;
		this.benutzerrolle=benutzerrolle;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getBenutzerrolle() {
		return benutzerrolle;
	}

	public void setBenutzerrolle(String benutzerrolle) {
		this.benutzerrolle = benutzerrolle;
	}
	
}
