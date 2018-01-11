package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.TreeMap;

import data.Ma_Schicht;
import data.Mitarbeiter;
import data.Schicht;
import data.Tblock_Tag;
import data.TerminBlockierung;
import model.Einsatzplanmodel;

/**
 * @author Lukas K�hl
 * @info Die Klasse SchichtStrg dient dazu, jegliche Anfragen zu Schichten durchzuf�hren und zu validieren.
 */
class SchichtStrg {

	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;			
		
	/**
	 * @author Lukas K�hl
	 * @info Erzeugen eines Objektes der Klasse SchichtStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb sp�ter zugewiesen.
	 */
	protected SchichtStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}	
		
	/**
	 * @author Lukas K�hl
	 * @info Hinzuf�gen einer neuen Schicht f�r einen bestimmten Tag innerhalb eines Wochenplanes und hinterlegen der notwendigen Informationen in der Datenbank. 
	 */
	protected boolean ausf�llenSchicht(int schichtNr, String[] mitarbeiter){

		boolean success = false;
		
		//Abruf der gesamten Schichteinteilung aus der Datenbank
		LinkedList<Ma_Schicht> einteilung= this.myModel.getMa_Schicht();
		LinkedList<Ma_Schicht> schichtEinteilung= new LinkedList<Ma_Schicht>();
		
		for(Ma_Schicht ma : einteilung){
			
			//Suche nach der zugeh�rigen Schichteinteilung zur �bergebenen Schicht
			if(ma.getSchichtnr() == schichtNr){				
				//Die Schichtnummer stimmt �berein --> die Schichteinteilung geh�rt zu der �bergebenen Schicht
				schichtEinteilung.add(ma);	
			}						
		}	
		
		//Gehe jeden eingetragenen Mitarbeiter durch und pr�fe, ob dieser schon in einer Einteilung zu dieser Schicht auftaucht. Falls nein, wird ein neuer Eintrag erstellt.
		for(String s: mitarbeiter){			
			boolean checkEingeteilt = true;
			
			for(Ma_Schicht mas: schichtEinteilung){
				
				if(mas.getBenutzername().equals(s)){
					//Mitarbeiter bereits in der Schicht eingeteilt --> weiter
					checkEingeteilt = false;
					break;
				}				
			}	
			
			if(checkEingeteilt){
				//Pr�fe, ob der Mitarbeiter im System vorhanden ist und somit eingeteilt werden kann
				if(this.myModel.getMitarbeiter(s) != null){							
					//Teile den Mitarbeiter in die gew�nschte Schicht ein
					this.myModel.addMa_Schicht(new Ma_Schicht(s, schichtNr));
					success = true;
				}				
			}	
			else{
				String fehler = "Der Mitarbeiter " + s + "wurde bereits in der Schicht mit der Nummer " + schichtNr + " eingeteilt!";
				myController.printErrorMessage(fehler);			
			}
		}	
		
		return success;
	}	

	/**
	 * @author Lukas K�hl
	 * @info Methode zum Ausgeben aller hinterlegten Schichten eines bestimmten Tages im Wochenplan.
	 */
	protected ArrayList<String> getTagesSchichten(String wpbez, String tagBez) {
		
		ArrayList<String> rueckgabe = new ArrayList<String>();
		
		//Konvertieren der �bergebenen Wpbez in eine eindeutige Wpnr
		int wpnr = this.myController.getWpnr(wpbez);
		
		LinkedList<Schicht> alleSchichten = this.myModel.getSchichten();
		LinkedList<Schicht> tagesSchichten = new LinkedList<Schicht>();
		
		for(Schicht s: alleSchichten){
			
			//Pr�fe, ob Tagbezeichnung und Wochenplan gleich sind. Wenn ja, wurde eine Schicht f�r den gesuchten Tag gefunden 
			if((s.getWpnr() == wpnr) && (tagBez.equals(s.getTbez()))){				
				tagesSchichten.add(s);				
			}
		}
		
		//Aufbereitung der Informationen aus der Schicht nach folgendem Muster Schichtnr - Zeitraum
		for(Schicht s: tagesSchichten){			
			rueckgabe.add(s.getSchichtnr() +" - " + s.getAnfanguhrzeit().substring(0, 5) + "-" + s.getEndeuhrzeit().substring(0, 5) +" Uhr");	
		}		
		
		return rueckgabe;
	}

	/**
	 * @author Lukas K�hl
	 * @info Methode zur Ermittlung aller verf�gbaren Mitarbeiter f�r eine Schicht des Wochenplanes.
	 */
	protected ArrayList<String> getVerf�gbareMitarbeiter(int schichtNr){
		ArrayList<String> verfuegbareMitarbeiter = new ArrayList<String>();
		
		//Ermittle die den Wochenplan zu der �bergebenen Schichtnr
		int wpnr = myModel.getSchicht(schichtNr).getWpnr();
		
		
		LinkedList<Mitarbeiter> alleMitarbeiter = this.myModel.getAlleMitarbeiter();
				
		for(Mitarbeiter m: alleMitarbeiter){		
			
			//Suche nach den Schichteinteilungen, die den aktuellen Mitarbeiter betreffen.
			LinkedList<Schicht> alleSchichten = this.myModel.getSchichten();
			LinkedList<Ma_Schicht> alleSchichtEinteilungen = this.myModel.getMa_Schicht();
			LinkedList<Schicht> mitarbeiterSchichten = new LinkedList<Schicht>();
			
			//Durchsuche alle Schichten, um die Schichten zu ermitteln, in die der Mitarbeiter eingeteilt wurde
			for(Schicht s : alleSchichten){
				
				for(Ma_Schicht mas: alleSchichtEinteilungen){					
					//Pr�fe, ob ein zugeh�riger Schichteinteilungseintrag zu der aktuellen Schicht gefunden wurde.
					if(s.getSchichtnr() == mas.getSchichtnr()){
						//Pr�fe, ob der Schichteinteilungseintrag zu dem aktuellen Mitarbeiter geh�rt, falls ja wird die aktuelle Schicht zu den Schichten des Mitarbeiters hinzugef�gt.
						if(m.getBenutzername().equals(mas.getBenutzername())){
							mitarbeiterSchichten.add(s);
						}						
					}				
				}			
			}		
			
			int maxDauer, dauer = 0; 
			
			//Abfrage der maximal zul�ssigen Wochenarbeitsstunden f�r den aktuellen Mitarbeiter 
			maxDauer = m.getMaxstunden();
					
			//Bereits vorhande Schichten werden addiert um momentane Auslastung zu ermitteln
			for(Schicht schicht: mitarbeiterSchichten){					
				//Die maximale Arbeitszeit gilt f�r eine Woche, somit werden nur Schichten der betroffenen Woche addiert
				if(schicht.getWpnr() == wpnr){
					dauer = dauer + getSchichtDauer(schicht);	
				}
			
			}
			
			//Addieren der zus�tzlich notwendigen Auslastung f�r die neue Schicht
			
			Schicht neueSchicht = this.myModel.getSchicht(schichtNr);			
			dauer = dauer + getSchichtDauer(neueSchicht);
			
			//Pr�fe, ob durch die zus�tzliche Schicht nicht die maximale Kapazit�t f�r den Mitarbeiter �berschritten wurde.
			if(dauer <= maxDauer ){
								
				//Es muss noch �berpr�ft werden, ob eine Terminblockierung vorliegt, welche die Einteilung des Mitarbeiters in die �bergebene Schicht verhindern w�rde.
				LinkedList<TerminBlockierung> alleTerminBlockierungen = this.myModel.getTerminBlockierungen();
				LinkedList<TerminBlockierung> mitarbeiterTerminBlockierungen = new LinkedList<TerminBlockierung>();
				
				//Sortiere die TerminBlockierungen aus, die nicht zu dem jeweiligen Mitarbeiter geh�ren
				for(TerminBlockierung tb: alleTerminBlockierungen){
					if(tb.getBenutzername().equals(m.getBenutzername())){
						mitarbeiterTerminBlockierungen.add(tb);
					}					
				}			
				
				boolean frei = true;
				for(TerminBlockierung tb : mitarbeiterTerminBlockierungen){
				
					//Suche den Tag + Wochenplan, an dem der Mitarbeiter einen Termin hat
					String tbezTermin = tb.getBbez();					
					Tblock_Tag temp = this.myModel.getTblock_TagTB(tb.getTblocknr());								
					int wpnrTermin = temp.getWpnr();
					
					//Suche den Tag + Wochenplan, in dem sich die Schicht befindet
					String tbezSchicht = this.myModel.getSchicht(schichtNr).getTbez();
					int wpnrSchicht = this.myModel.getSchicht(schichtNr).getWpnr();
					
					if((tbezTermin.equals(tbezSchicht)) &&(wpnrTermin == wpnrSchicht)){
						//Mitarbeiter hat an dem Tag, in dem sich die Schicht befindet, bereits einen Termin eingetragen und ist somit nicht verf�gbar
						frei = false;
					}								
				}		
				
				if(frei){
					//Der Mitarbeiter ist f�r die �bergebene Schicht verf�gbar und kann eingeteilt werden.
					verfuegbareMitarbeiter.add(m.getBenutzername() +" - " + m.getVorname() + " " + m.getName());
				}	
			}			
		}		
		
		return verfuegbareMitarbeiter;
	}
	
	
	/**
	 * @author Lukas K�hl
	 * @info Methode zur Ermittlung der Schichten eines Mitarbeiters f�r einen bestimmten Tag innerhalb eines Wochenplanes
	 */
	protected ArrayList<String> getMitarbeiterSchichten(String wpbez, String tagbez, String username){
				
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = myController.getWpnr(wpbez); 		
		
    	//Abfrage der gesamten Zuordnung und Suche nach den Schichten, die dem �bergebenen benuzternamen zugeordnet sind
		LinkedList<Ma_Schicht> einteilung = this.myModel.getMa_Schicht();
		LinkedList<Ma_Schicht> mitarbeiterEinteilung = new LinkedList<Ma_Schicht>();
		
		//Iteriere durch die Einteilungsliste und �bernehme die Datens�tze in die Mitarbeitereinteilung, die den gesuchten Benuzternamen haben
		for(Ma_Schicht ms: einteilung){			
			if(ms.getBenutzername().equals(username)){
				mitarbeiterEinteilung.add(ms);
			}			
		}
		
		//Abfrage vorhandenen Schichten in der Datenbank und Suche nach den Schichtnummern der Einteilung s.o.
		LinkedList<Schicht> alleSchichten = this.myModel.getSchichten();
		LinkedList<Schicht> mitarbeiterSchichtenAlle =new LinkedList<Schicht>();
		
		for(Schicht s: alleSchichten){
			
			for(Ma_Schicht mas: mitarbeiterEinteilung){
				
				if(s.getSchichtnr() == mas.getSchichtnr()){
					mitarbeiterSchichtenAlle.add(s);
				}			
			}		
		}
		
		LinkedList<Schicht> mitarbeiterTagesSchichten = new LinkedList<Schicht>();
		
		//Suche nach den Schichten eines bestimmten Tages eines vorhanden Wochenplanes
		for(Schicht s: mitarbeiterSchichtenAlle){
			
			if((s.getTbez().equals(tagbez)) && (s.getWpnr() == wpnr)){
				mitarbeiterTagesSchichten.add(s);
			}		
		}	
		//Bereite die Informationen �ber die MitarbeiterTagesSchichten zus�tzlich auf und gebe Sie anschlie�end zur�ck.
		return getSchichteinteilungView(mitarbeiterTagesSchichten);	
	}	
	
	/**
	 * @author Lukas K�hl
	 * @info Methode zur Ermittlung von anderen Schichten innerhalb eines Wochenplanes, mit denen die �bergebene Schicht getauscht werden k�nnte
	 */
	protected ArrayList<String> getAndereMitarbeiterSchichten(String wpbez, String username, int schichtNr){
		LinkedList<Schicht> uebergabe = new LinkedList<Schicht>();    	
		
		//Umwandeln der Wochenplanbezeichnung in eine eindeutige Wochenplannummer
		int wpnr = myController.getWpnr(wpbez);
		
    	LinkedList<Ma_Schicht> einteilung = this.myModel.getMa_Schicht();
		LinkedList<Ma_Schicht> andereMitarbeiterEinteilung = new LinkedList<Ma_Schicht>();
		LinkedList<Integer> belegteSchichtnummern = new LinkedList<Integer>();
		
		for(Ma_Schicht mas : einteilung){			
			//Der Mitarbeiterbenutzername ist gleich dem Anfrager --> Tausch w�re nicht sinnvoll, da Schicht bereits belegt
			if(mas.getBenutzername().equals(username)){				
				if(!belegteSchichtnummern.contains(mas.getSchichtnr())){
					belegteSchichtnummern.add(mas.getSchichtnr());
				}							
			}
		}
		
		//Sortiere alle Schichteinteilungen aus, in denen eine Schicht auftaucht, die vom �bergebenen Mitarbeiter selbst belegt ist
		for(Ma_Schicht mas: einteilung){
			boolean frei = true;
			for(Integer i: belegteSchichtnummern){
				if(i == mas.getSchichtnr()){
					frei = false;
				}			
			}
			if(frei){
				andereMitarbeiterEinteilung.add(mas);
			}	
		}	
		
		//Abfrage vorhandenen Schichten in der Datenbank und Suche nach den Schichtnummern der Einteilung s.o.
		LinkedList<Schicht> alleSchichten = this.myModel.getSchichten();
		// Potentielle Schichten zum Tauschen, in denen der �bergebene Mitarbeiter selbst nicht vorhanden ist
		TreeMap <Integer, Schicht> andereMitarbeiterSchichtenAlle = new TreeMap<Integer, Schicht>();
				
		for(Schicht s: alleSchichten){			
			
			//�berpr�fe, ob sich die Schicht in der �bergebenen Woche befindet
			if(s.getWpnr() == wpnr){
				
				for(Ma_Schicht mas: andereMitarbeiterEinteilung){
					
					//Pr�fe, ob die Schicht zu der zugeh�rigen Schichteinteilung geh�rt und noch nicht in der Map mit potentiellen Schichten zum Tauschen vorhanden ist
					if(s.getSchichtnr() == mas.getSchichtnr() && (!andereMitarbeiterSchichtenAlle.containsKey(s.getSchichtnr()))){
						//Hinterlegen der Schicht in der Map
						andereMitarbeiterSchichtenAlle.put(s.getSchichtnr(),s);
					}			
				}	
			}			
		}			
		
		//Umwandeln der Map in eine Liste zur Aufbereitung der Darstellung der ermittelten Schichten
		for(Integer i: andereMitarbeiterSchichtenAlle.keySet()){
			uebergabe.add(andereMitarbeiterSchichtenAlle.get(i));
		}	
		
		//Aufbereitung der Informationen zu den ermittelten Schichten und R�ckgabe der Informationen
		return getSchichteinteilungView(uebergabe);
	}
	
	
	/**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zur Generierung eines Eintrages in der Schicht�bersicht innerhalb der View
	 */
	private ArrayList<String> getSchichteinteilungView(LinkedList<Schicht> mitarbeiterSchichten){
		
		ArrayList<String> rueckgabe = new ArrayList<String>();
		
		for(Schicht s: mitarbeiterSchichten){
			
			//Struktur eines Schichteintrages in der R�ckgabeliste: SchichtNr - KW - Tag - Zeitraum - Mitarbeitername
			
			String temp = String.valueOf(s.getSchichtnr()) + " - KW"  + s.getWpnr() + " - " + s.getTbez() + " - " + s.getAnfanguhrzeit()+ "-" + s.getEndeuhrzeit() +" Uhr - ";
			
			LinkedList<Ma_Schicht> alleSchichtZuordnungen = this.myModel.getMa_Schicht();
			Mitarbeiter ma = null;
			
			for(Ma_Schicht mas: alleSchichtZuordnungen){
				
				//Betroffene Schichzuordnung gefunden, entnahme des Mitarbeiters zum Auslesen des Namens
				if(s.getSchichtnr() == mas.getSchichtnr()){					
					ma = this.myModel.getMitarbeiter(mas.getBenutzername());				
					rueckgabe.add(temp + ma.getBenutzername());
				}		
			}		
		}
		
		return rueckgabe;
		
	}	
	
	/**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zur Berechnung der Dauer einen bestimmten Schicht(in Std.)
	 */
	private int getSchichtDauer(Schicht s){
		
		int dauer = 0;
		
		//Lese den Zeitraum f�r die Schicht aus dem Schichtobjekt
		String anfangsZeit = s.getAnfanguhrzeit();
		String endZeit = s.getEndeuhrzeit();
		
		try{
			
			//Konvertiere die zwei Zeitpunkte ins Date-Format
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			Date anfangDate = sdf.parse(anfangsZeit);
			Date endDate = sdf.parse(endZeit);
			
			//Berechne die Differenz der beiden Zeitpunkt
			long dauerSchichtLong = endDate.getTime() - anfangDate.getTime();
			
			//Rechne den Zeitunterschied in Std. um
			int dauerSchichtInt  =  Math.round((long) dauerSchichtLong / (1000 * 60 * 60));
			
			dauer = dauer + dauerSchichtInt;
			
			
		}catch(Exception e){
			
			String fehler = "Fehler beim Bestimmen der Dauer von der Schicht: " + s.getSchichtnr() + "\n" + e.getMessage();
			myController.printErrorMessage(fehler);				
		}			
		
		return dauer;
	}	
}