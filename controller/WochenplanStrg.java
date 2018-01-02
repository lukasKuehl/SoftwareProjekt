package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import data.Ma_Schicht;
import data.Mitarbeiter;
import data.Schicht;
import data.Standardeinstellungen;
import data.Tag;
import data.Userrecht;
import data.Wochenplan;
import model.Einsatzplanmodel;

/**
 * @author Lukas
 * @info Die Klasse WochenplanStrg dient dazu, jegliche Anfragen bezüglich eines Wochenplanes im System zu verarbeiten und zu validieren.
 */
class WochenplanStrg {
	
	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private JDialog myDialog = null;
	
	/**
	 * @author 
	 * @info Erzeugen eines Objektes der Klasse WochenplanStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb später zugewiesen.
	 */
	protected WochenplanStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}
	
	/**
	 * @author Lukas Kühl
	 * @info Anlegen eines neuen Wochenplanes nach benuterdefinierten Einstellungen und hinterlegen des Planes in der Datenbank inklusive der spezifischen Restriktionen
	 * @info Keys der "zeiten" Map: "Öffnungszeit", "HauptzeitBeginn", "HauptzeitEnde", "Schließzeit" 
	 * @info Keys der "besetzung" Map: "MinBesetzungKasse", "MinBesetzungInfoWaren", "MinBesetzungInfoTechnik", "MehrbesetzungKasse"
	 */
	protected boolean erstelleWochenplanCustom(String username, String wpbez, TreeMap<String, String> zeiten, TreeMap<String, Integer> besetzung){
		boolean success = false;		
		/*
		Mitarbeiter user = myModel.getMitarbeiter(username);
		
		//muss noch implementiert werden
		Userrecht recht = Einsatzplanmodel.getUserrecht(user.getJob());
				
		if(recht.getBenutzerrolle().equals("Chef")){		
			
			Map<String, Date> zeitenDate = new TreeMap<String, Date>();
			
			// Iteriere durch die TreeMap mit den Angaben zu den Zeiten für den neuen Wochenplan und übertrage die Werte, falls möglich in eine neue Map mit dem Typ java.util.Date
			for(String key : zeiten.keySet()){
				
				String zeitString = zeiten.get(key);
				SimpleDateFormat format = new SimpleDateFormat("hh:mm");
								
				try{
					Date temp = format.parse(zeitString);				
					zeitenDate.put(key, temp);			
					
				}catch(Exception e){
					System.out.println("Fehler beim Konvertieren eines Datums");					
				}				
				
			}
				//Prüfe, ob die Zeiten den Vorgaben entsprechen
			if(checkZeitenWochenplan(zeitenDate)){
					
				boolean checkbesetzung = true;					
			
				//Überprüfung vllt. schon in der View					
				//Prüfe, ob es negative Werte in der vorhandenen Besetzung-Map gibt
				for(String s : besetzung.keySet()){
					if(besetzung.get(s) < 0){
						checkbesetzung = false;
					}						
				}
					
				//Alle Anforderungen wurden erfüllt --> ein neuer Wochenplan kann im System hinterlegt werden
				if(checkbesetzung){
					Wochenplan wp = new Wochenplan(0, false, zeiten.get("Öffnungszeit"), zeiten.get("Schließzeit"), zeiten.get("HauptzeitBeginn"), zeiten.get("HauptzeitEnde"), this.myController.getView().getUsername(), besetzung.get("MinBesetzungInfoTechnik"), besetzung.get("MinBesetzungInfoWaren"), besetzung.get("MinBesetzungKasse"), besetzung.get("MehrbesetzungKasse"));
					this.myModel.addWochenplan(wp);
					
					if(this.myModel.getWochenplan(wp.getWpnr()) != null){
						success = true;
					}
					
				}
				else{
					System.out.println("Fehler beim Erstellen eines neuen Wochenplanes:");
					System.out.println("Die Besetzungsanzahl darf nicht negativ sein!");
				}						
			}
			else{
				System.out.println("Fehler beim Erstellen eines neuen Wochenplanes:");
				System.out.println("Die Angaben zu den Öffnungs- und Hauptzeiten sind fehlerhaft!");
			}					
		}
		else{
			System.out.println("Fehler beim Erstellen eines neuen Wochenplanes:");
			System.out.println("Der Benutzer verfügt nicht über die notwendigen Berechtigungen zum Anlegen eines neuen Einsatzplanes!");
		}	
		*/
	return success;
   }
	
	/**
	 * @author Lukas Kühl
	 * @info Anlegen eines neuen Wochenplanes nach standardmäßigen Einstellungen. Auslesen der momentanen Standardeinstellungen aus der Datenbank und hinterlegen des erstellten Planes in der Datenbank.
	 */
	protected boolean erstelleWochenplanStandard(String username, String wpbez){
		
		boolean success = false;
		
		Mitarbeiter user = myModel.getMitarbeiter(username);
	
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = Integer.parseInt((wpbez.substring(2).trim())); 
		
		Userrecht recht = myModel.getUserrecht(user.getJob());
				
		if(recht.getBenutzerrolle().equals("Chef")){	
			
			try{
				Standardeinstellungen settings = this.myModel.getStandardeinstellungen();				
				Wochenplan wp = new Wochenplan(wpnr, false, settings.getÖffnungszeit(), settings.getSchließzeit(), settings.getHauptzeitbeginn(), settings.getHauptzeitende(), username, settings.getMinanzinfot(), settings.getMinanzinfow(), settings.getMinanzkasse(), settings.getMehrbesetzung());
				this.myModel.addWochenplan(wp);
				
				if(this.myModel.getWochenplan(wp.getWpnr()) != null){
					success = true;
				}				
				
			}catch(Exception e){
				System.out.println("Fehler beim Erstellen eines neuen Wochenplanes nach Standardeinstellungen:");
				System.out.println("Die Standardeinstellungen wurden nicht richtig übernommen!");
				e.printStackTrace();
			}			
		}
		else{
			System.out.println("Fehler beim Erstellen eines neuen Wochenplanes:");
			System.out.println("Der Benutzer verfügt nicht über die notwendigen Berechtigungen zum Anlegen eines neuen Einsatzplanes!");
		}		
		
		return success;
	}	
	
	/**
	 * @author Lukas Kühl
	 * @info Erzeugen eines Einsatzplanes in Form eines JTables zur Ansicht in der View
	 */
	protected JTable generiereWochenplanView(String wpbez){
		JTable wochenplan = null;	
		
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = Integer.parseInt((wpbez.substring(2).trim()));  			
		
		LinkedList<Tag> alleTage = myModel.getTage();
    	TreeMap<Integer, Tag> wochenTage = new TreeMap<Integer, Tag>();
    	//Nummerierung der Tage von 1 aufsteigend
    	int counter = 1;
    	
    	//Sortiere alle Tage aus, die nicht zu der angefragten Woche gehören
    	for(Tag t: alleTage){
    		if(t.getWpnr() == wpnr){
    			wochenTage.put(counter, t);
    			counter++;
    		}    		
    	}  	
    	
		//LinkedList<Mitarbeiter> alleMitarbeiter = myModel.getAlleMitarbeiter();
		TreeMap<String, Mitarbeiter> tempMitarbeiter = new TreeMap<String, Mitarbeiter>();
		LinkedList<Mitarbeiter> wochenMitarbeiter = new LinkedList<Mitarbeiter>();
		
		LinkedList<Schicht> alleSchichten = myModel.getSchichten();
		LinkedList<Ma_Schicht> alleSchichtEinteilungen = myModel.getMa_Schicht();
		
		for(Schicht s: alleSchichten){
			if(s.getWpnr() == wpnr){
				
				for(Ma_Schicht mas : alleSchichtEinteilungen){
					
					if(!tempMitarbeiter.containsKey(mas.getBenutzername())){
						tempMitarbeiter.put(mas.getBenutzername(), this.myModel.getMitarbeiter(mas.getBenutzername()));
					}				
				}						
			}			
		}
		
		for(String s: tempMitarbeiter.keySet()){
			wochenMitarbeiter.add(tempMitarbeiter.get(s));			
		}	
		
		String[] spaltennamen = new String[wochenTage.size()+1];
		String[][] zeilen = new String[wochenMitarbeiter.size()][wochenTage.size()+1];
				
		for(int i = 0; i< spaltennamen.length; i++){	
			
			if(i == 0){				
				spaltennamen[0] = "";
			}
			else{				
				spaltennamen[i] = wochenTage.get(i).getTbez();
			}			
		}
		
		LinkedList <String[]> temp = new LinkedList<String[]>();
		
		for(Mitarbeiter m : wochenMitarbeiter){
			temp.add(generiereMitarbeiterSpalte(wpnr, m, spaltennamen));				
		}	
		
		for(int i = 0; i < zeilen.length; i++){
			zeilen[i]= temp.get(i);			
		}				
		
		try{		
			wochenplan = new JTable(zeilen,spaltennamen);
		}catch(Exception e){
			System.out.println("Fehler beim Erstellen eines neuen JTables für den Wochenplan" + wpbez);
			e.printStackTrace();
		}
		
         return wochenplan;
	}

	
	
	/**
	 * @author Lukas Kühl
	 * @info Hilfsmethode zum Erzeugen einer Zeile in der Wochenplantabelle für einen spezifischen Mitarbeiter
	 */
    private String[] generiereMitarbeiterSpalte(int wpnr, Mitarbeiter ma, String[] wochenTage){
    
    	//Symbolisiert eine Mitarbeiterzeile in der Wochenplantabelle
    	String[] rueckgabe = new String[wochenTage.length];      	  	
    	
    	//Abfrage der gesamten Zuordnung und Suche nach den Schichten, die dem übergebenen Mitarbeiter zugeordet sind
    	LinkedList<Ma_Schicht> einteilung = this.myModel.getMa_Schicht();
    	LinkedList<Ma_Schicht> mitarbeiterEinteilung = new LinkedList<Ma_Schicht>();
    			
    	//Iteriere durch die Einteilungsliste und übernehme die Datensätze in die Mitarbeitereinteilung, die den gesuchten Benuzternamen haben
    	for(Ma_Schicht ms: einteilung){			
    		if(ms.getBenutzername().equals(ma.getBenutzername())){
    			mitarbeiterEinteilung.add(ms);
    		}			
    	}   	
    	
    	//Abfrage vorhandenen Schichten in der Datenbank und Suche nach den Schichtnummern der Einteilung s.o.
		LinkedList<Schicht> alleSchichten = this.myModel.getSchichten();
		LinkedList<Schicht> mitarbeiterSchichten =new LinkedList<Schicht>();
		
		for(Schicht s: alleSchichten){
			
			for(Ma_Schicht mas: mitarbeiterEinteilung){
				
				if(s.getSchichtnr() == mas.getSchichtnr()){
					mitarbeiterSchichten.add(s);
				}			
			}		
		}    	
    	
    	Map<String, LinkedList<Schicht>> tageSchichtenMap = new TreeMap<String, LinkedList<Schicht>>();    	
    	
    	
    	for(int i = 1; i< rueckgabe.length; i++){   		
    		
    		for(Schicht s: mitarbeiterSchichten){
    			if(s.getTbez().equals(wochenTage[i])){
    				
    				if(tageSchichtenMap.get(wochenTage[i]) == null){
    					tageSchichtenMap.put(wochenTage[i], new LinkedList<Schicht>());    					
    				}
    				tageSchichtenMap.get(wochenTage[i]).add(s);    				
    			}
    		}
    		
    	}    	
    	
    	for(int i = 0; i< rueckgabe.length; i++){
    		
    		if(i == 0){
    			rueckgabe[0] = ma.getVorname() + " " + ma.getName();
    		}
    		else{
    			rueckgabe[i] = getTageszeitraum(tageSchichtenMap.get(wochenTage[i]));
    		}
    	} 	
    	
    	return rueckgabe;
    }
		
    
    
    /**
	 * @author Lukas Kühl
	 * @info Hilfsmethode zur Ermittlung der gesamten Arbeitszeit aufgrund der eingeteilten Schichten eines Mitarbeiters an einem bestimmten Tag für die Einstatzplantabelle
	 */
    private String getTageszeitraum(LinkedList<Schicht> schichten){
    	
    	String rueckgabe = null;
    	
    	if(schichten == null){
    		return "-";
    	}
    	else{			
    			
    		LinkedList<Date> zeitenDate = new LinkedList<Date>();
    			
    		// Iteriere durch die übergebene Schichtenliste und konvertiere die vorhanden Zeiten ins Date Format
    		for(Schicht s : schichten){
    				
    			String anfangsZeitString = s.getAnfanguhrzeit().substring(0, 5);
    			String endZeitString = s.getEndeuhrzeit().substring(0, 5);
    		
    			SimpleDateFormat format = new SimpleDateFormat("kk:mm");
    								
    			try{
    				Date tempAnfang = format.parse(anfangsZeitString);    				
    				zeitenDate.add(tempAnfang);			
    				Date tempEnde = format.parse(endZeitString);
    				zeitenDate.add(tempEnde);    				
    					
    			}catch(Exception e){
    				System.out.println("Fehler beim Konvertieren eines Datums");					
    			}			   				
    		}  	   		
    		
    		// Konvertierung war erfolgreich und es ist mindestens eine Schicht vorhanden, da Anfangs- und Endzeit da sind --> mind. 2 Werte
    		if(zeitenDate.size() >= 2){
    			
    			//Gebe die frühste und die späteste Zeit aus der Liste zurück --> Gesamter Zeitraum abgedeckt
    			Collections.sort(zeitenDate);       			
    			
    			//Fülle die offen gebliebenen Stellen mit führenden Nullen auf
    			String startStunden = String.valueOf(zeitenDate.getFirst().getHours());
    			while(startStunden.length() <2){startStunden = "0" + startStunden;}
    			
    			String startMinuten = String.valueOf(zeitenDate.getFirst().getMinutes());
    			while(startMinuten.length() <2){startMinuten = "0" + startMinuten;}
    			
    			String endStunden = String.valueOf(zeitenDate.getLast().getHours());
    			while(endStunden.length() <2){endStunden = "0" + endStunden;}
    			
    			String endMinuten = String.valueOf(zeitenDate.getLast().getMinutes());
    			while(endMinuten.length() <2){endMinuten = "0" + endMinuten;}
    			
    			String start = startStunden + ":" + startMinuten;
    			String ende = endStunden + ":" + endMinuten;    			
    			
    			rueckgabe =  start + "-" + ende;    				
    		}     		
    	}
    	
    	return rueckgabe;
    }  
    
	/**
	 * @author 
	 * @return 
	 * @info Veröffentlichen eines erstellten Wochenplanes im System, so dass er von allen Mitarbeitern und nicht nur vom Kassenbüro/Chef gesehen werden kann.
	 */
	protected boolean publishWochenplan(String username, String wpbez){
		
		boolean success = false;
		//Ausfüllen
		return success;
		
	}
	
	/**
	 * @author
	 * @info Entfernen eines bereits erstellten Wochenplanes aus dem System mit allen daraus resultierenden Informationen über Tage, Schichten und Terminen/Krankheiten/Urlaub
	 */
	protected boolean entferneWochenplan(String username, String wpbez){
		
		boolean success = false;
		//ausfüllen
		return success;
	}
		
	/**
	 * @author Lukas Kühl
	 * @info Die Methode dient dazu einen bestimmten Wochenplan an alle Mitarbeiter der Warenhäuser per E-Mail zu verschicken um Ihnen die Arbeitszeiten mitzuteilen
	 */
	protected boolean verschickeWochenplan(String username ,String wpbez, JTable wochenplan, JTableHeader header){
			
		boolean success = false;				
		
		int w = Math.max(wochenplan.getWidth(), header.getWidth());
        int h = wochenplan.getHeight() + header.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        header.paint(g2);
        g2.translate(0, header.getHeight());
        wochenplan.paint(g2);
        g2.dispose();
        try
        {
        	String filePath = System.getProperty("user.home") + "/Desktop/Kalenderwochenübersicht_" + wpbez +".png";
            ImageIO.write(bi, "png", new File(filePath));
            
            LinkedList<Mitarbeiter> alleMitarbeiter = this.myModel.getAlleMitarbeiter();
            
            MailStrg myMailController = new MailStrg();
            
            final String user = "einsatzplan.team";
            final String password = "";
            final String senderAddress = "einsatzplan.team@web.de";           
            
            for(Mitarbeiter m: alleMitarbeiter){
            	//Festlegung des Textes für die E-Mail
            	String message = "Hallo " + m.getVorname() + " " + m.getName() +",\n" + 
            			"anbei dieser Mail finden Sie den Mitarbeitereinsatzplan für die " + wpbez +".\n" + 
            			"\n" +
            			"Mit freundlichen Grüßen,\n" +
            			"Team der Einsatzplanverwaltung";           	
            	
            	//Nutzung des Gruppenaccounts bei Web.de
            	myMailController.sendMail(user, password, senderAddress, m.getEmail(), "Einsatzplan für " + wpbez, message, filePath);	
            	
            }
            
            success = true;
        }
        catch(IOException ioe)
        {
            System.out.println("Fehler beim Verschicken der Wochenpläne per E-Mail:");
            ioe.printStackTrace();
        }        
        
		return success;
	} 
	
 
	protected ArrayList<String> getWochenplaene(){
		ArrayList<String> rueckgabe = new ArrayList<String>();
	
		LinkedList<Wochenplan> alleWochenplaene = this.myModel.getWochenplaene();
		
		for(Wochenplan wp : alleWochenplaene){			
			rueckgabe.add("KW" + wp.getWpnr());			
		}	
		
		return rueckgabe;
	}
	
	protected ArrayList<String> getTage(String wpbez){
		ArrayList<String> rueckgabe = new ArrayList<String>();
		
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = Integer.parseInt((wpbez.substring(2).trim()));  			
		
		LinkedList<Tag> alleTage = this.myModel.getTage();
		LinkedList<Tag> wochenTage = new LinkedList<Tag>(); 
		
		for(Tag t: alleTage){
			if(t.getWpnr() == wpnr){
				wochenTage.add(t);				
			}
		}
		
		for(Tag t : wochenTage){
			rueckgabe.add(t.getTbez());
		}		
		
		return rueckgabe;
	}
	
	//Hat ramona benötigt
	protected ArrayList<String> getSchichten(String wpbez, String tbez){
		ArrayList<String> rueckgabe = new ArrayList<String>();
		
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = Integer.parseInt((wpbez.substring(2).trim()));  			
		
		LinkedList<Schicht> alleSchichten = this.myModel.getSchichten();
		LinkedList<Schicht> schichtenTag = new LinkedList<Schicht>(); 
		
		for(Schicht s: alleSchichten){
			if(s.getWpnr() == wpnr && s.getTbez().equals(tbez)){
				schichtenTag.add(s);				
			}
		}
		
		for(Schicht s : schichtenTag){

		String Schichtnr = new Integer(s.getSchichtnr()).toString();
			rueckgabe.add(Schichtnr);
		}		
		
		return rueckgabe;
	}
	
	
	
	/**
	 * @author 
	 * @info Hilfsmethode zum ändern der hinterlegten Standardeinstellungen für einen Wochenplan in der Datenbank.
	 */
	private void bearbeiteStandardeinstellungen(Standardeinstellungen settings){
		
		//ausfüllen
		
		
	}
	
	
	/**
	 * @author Lukas Kühl
	 * @info Hilfsmethode zum Prüfen der Struktur von Öffnungs- und Hauptzeiten(die richtige Struktur ist Öffnungszeit --> Hauptzeitbeginn --> HauptzeitEnde --> Schließzeit) 
	 */
	private boolean checkZeitenWochenplan(Map<String, Date> zeitenDate) {
		return ((zeitenDate.get("Öffnungszeit").before(zeitenDate.get("HauptzeitBeginn"))) && (zeitenDate.get("HauptzeitBeginn").before(zeitenDate.get("HauptzeitEnde"))) && (zeitenDate.get("HauptzeitEnde").before(zeitenDate.get("Schließzeit")))); 		
	}	
}
