package data;

public class Schicht {
	private int schichtnr;
	private String tbez;
	private int wpnr;
	
	public Schicht(int schichtnr, String tbez, int wpnr){
		this.schichtnr=schichtnr;
		this.tbez=tbez;
		this.wpnr=wpnr;
	}

	public int getSchichtnr() {
		return schichtnr;
	}

	public void setSchichtnr(int schichtnr) {
		this.schichtnr = schichtnr;
	}


	public String getTbez() {
		return tbez;
	}

	public void setTbez(String tbez) {
		this.tbez = tbez;
	}

	public int getWpnr() {
		return wpnr;
	}

	public void setWpnr(int wpnr) {
		this.wpnr = wpnr;
	}
	
}
