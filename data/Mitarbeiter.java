package data;

public class Mitarbeiter {
	private String benutzername=null;
	private String passwort=null;
	private String job=null;
	private String vorname=null;
	private String name=null;
	private int maxstunden=0;
	private String whname = null;
	private String email=null;
	
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Mitarbeiter und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter f�r alle Variablen.
	 */
	public Mitarbeiter(String benutzername, String passwort, String job, String vorname, String name, int maxstunden, String whname, String email){
		this.benutzername = benutzername;
		this.passwort = passwort;
		this.job = job;
		this.vorname = vorname;
		this.name= name;
		this.maxstunden=maxstunden;
		this.whname = whname;
		this.email=email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxstunden() {
		return maxstunden;
	}

	public void setMaxstunden(int maxstunden) {
		this.maxstunden = maxstunden;
	}

	public String getWhname() {
		return whname;
	}

	public void setWhname(String whname) {
		this.whname = whname;
	}
	
}
