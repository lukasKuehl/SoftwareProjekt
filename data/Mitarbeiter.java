package data;

public class Mitarbeiter {
	private String benutzername;
	private String passwort;
	private String job;
	private String vorname;
	private String name;
	private int maxstunden;
	private String whname;
	
	public Mitarbeiter(String benutzername, String passwort, String job, String vorname, String name, int Maxstunden, String Whname){
		this.benutzername = benutzername;
		this.passwort = passwort;
		this.job = job;
		this.vorname = vorname;
		this.name= name;
		this.maxstunden=maxstunden;
		this.whname= whname;
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
