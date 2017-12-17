package data;

public class Warenhaus {
	private String whname;
	private int anzkasse;
	private int anzinfo;
	/**
	 * @author Anes Preljevic
	 * @info Erzeugen eines Objektes vom Typ Warenhaus und 
	 * Wertzuweisung der Instanzvariablen. Getter/Setter für alle Variablen.
	 */
	public Warenhaus(String whname, int anzkasse, int anzinfo){
		this.whname=whname;
		this.anzkasse=anzkasse;
		this.anzinfo=anzinfo;
		
	}

	public String getWhname() {
		return whname;
	}

	public void setWhname(String whname) {
		this.whname = whname;
	}

	public int getAnzkasse() {
		return anzkasse;
	}

	public void setAnzkasse(int anzkasse) {
		this.anzkasse = anzkasse;
	}

	public int getAnzinfo() {
		return anzinfo;
	}

	public void setAnzinfo(int anzinfo) {
		this.anzinfo = anzinfo;
	}
	
}
