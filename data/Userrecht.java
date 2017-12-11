package data;

public class Userrecht {
	private String job;
	private String benutzerrolle;
	
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
