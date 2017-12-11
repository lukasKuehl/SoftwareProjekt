package data;

public class Tblock_Tag {
	private int tblocknr;
	private String tbez;
	private int wpnr;
	
	public Tblock_Tag(int tblocknr, String tbez, int wpnr){
		this.tblocknr=tblocknr;
		this.tbez=tbez;
		this.wpnr=wpnr;
		
	}

	public int getWpnr() {
		return wpnr;
	}

	public void setWpnr(int wpnr) {
		this.wpnr = wpnr;
	}

	public int getTblocknr() {
		return tblocknr;
	}

	public void setTblocknr(int tblocknr) {
		this.tblocknr = tblocknr;
	}

	public String getTbez() {
		return tbez;
	}

	public void setTbez(String tbez) {
		this.tbez = tbez;
	}
	
}
