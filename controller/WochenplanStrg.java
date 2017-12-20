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
 * @info Die Klasse WochenplanStrg dient dazu, jegliche Anfragen bez�glich eines Wochenplanes im System zu verarbeiten und zu validieren.
 */
class WochenplanStrg {
	
	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private JDialog myDialog = null;
	
	/**
	 * @author 
	 * @info Erzeugen eines Objektes der Klasse WochenplanStrg. Setzen des allgemeinen EinsatzplanControllers als Parent. Die Variable myDialog wird erst bei jedem Methodenaufruf neu erzeugt und wird deshalb sp�ter zugewiesen.
	 */
	protected WochenplanStrg(EinsatzplanController myController, Einsatzplanmodel myModel){
		this.myController = myController;
		this.myModel = myModel;
	}
	
	/**
	 * @author Lukas K�hl
	 * @info Anlegen eines neuen Wochenplanes nach benuterdefinierten Einstellungen und hinterlegen des Planes in der Datenbank inklusive der spezifischen Restriktionen
	 * @info Keys der "zeiten" Map: "�ffnungszeit", "HauptzeitBeginn", "HauptzeitEnde", "Schlie�zeit" 
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
			
			// Iteriere durch die TreeMap mit den Angaben zu den Zeiten f�r den neuen Wochenplan und �bertrage die Werte, falls m�glich in eine neue Map mit dem Typ java.util.Date
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
				//Pr�fe, ob die Zeiten den Vorgaben entsprechen
			if(checkZeitenWochenplan(zeitenDate)){
					
				boolean checkbesetzung = true;					
			
				//�berpr�fung vllt. schon in der View					
				//Pr�fe, ob es negative Werte in der vorhandenen Besetzung-Map gibt
				for(String s : besetzung.keySet()){
					if(besetzung.get(s) < 0){
						checkbesetzung = false;
					}						
				}
					
				//Alle Anforderungen wurden erf�llt --> ein neuer Wochenplan kann im System hinterlegt werden
				if(checkbesetzung){
					Wochenplan wp = new Wochenplan(0, false, zeiten.get("�ffnungszeit"), zeiten.get("Schlie�zeit"), zeiten.get("HauptzeitBeginn"), zeiten.get("HauptzeitEnde"), this.myController.getView().getUsername(), besetzung.get("MinBesetzungInfoTechnik"), besetzung.get("MinBesetzungInfoWaren"), besetzung.get("MinBesetzungKasse"), besetzung.get("MehrbesetzungKasse"));
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
				System.out.println("Die Angaben zu den �ffnungs- und Hauptzeiten sind fehlerhaft!");
			}					
		}
		else{
			System.out.println("Fehler beim Erstellen eines neuen Wochenplanes:");
			System.out.println("Der Benutzer verf�gt nicht �ber die notwendigen Berechtigungen zum Anlegen eines neuen Einsatzplanes!");
		}	
		*/
	return success;
   }
	
	/**
	 * @author Lukas K�hl
	 * @info Anlegen eines neuen Wochenplanes nach standardm��igen Einstellungen. Auslesen der momentanen Standardeinstellungen aus der Datenbank und hinterlegen des erstellten Planes in der Datenbank.
	 */
	protected boolean erstelleWochenplanStandard(String username, String wpbez){
		
		boolean success = false;
		/*
		Mitarbeiter user = myModel.getMitarbeiter(username);
	
		//Muss noch implementier werden
		Userrecht recht = myModel.getUserrecht(user.getJob());
				
		if(recht.getBenutzerrolle().equals("Chef")){	
			
			try{
				Standardeinstellungen settings = this.myModel.getStandardeinstellungen();				
				Wochenplan wp = new Wochenplan(0, false, settings.get�ffnungszeit(), settings.getSchlie�zeit(), settings.getHauptzeitbeginn(), settings.getHauptzeitende(), this.myController.getView().getUsername(), settings.getMinanzinfot(), settings.getMinanzinfow(), settings.getMinanzkasse(), settings.getMehrbesetzungkasse());
				this.myModel.addWochenplan(wp);
				
				if(this.myModel.getWochenplan(wp.getWpnr()) != null){
					success = true;
				}				
				
			}catch(Exception e){
				System.out.println("Fehler beim Erstellen eines neuen Wochenplanes nach Standardeinstellungen:");
				System.out.println("Die Standardeinstellungen wurden nicht richtig �bernommen!");
				e.printStackTrace();
			}			
		}
		else{
			System.out.println("Fehler beim Erstellen eines neuen Wochenplanes:");
			System.out.println("Der Benutzer verf�gt nicht �ber die notwendigen Berechtigungen zum Anlegen eines neuen Einsatzplanes!");
		}	
		*/
		
		return success;
	}	
	
	/**
	 * @author Lukas K�hl
	 * @info Erzeugen eines Einsatzplanes in Form eines JTables zur Ansicht in der View
	 */
	protected JTable generiereWochenplanView(String wpbez){
		JTable wochenplan = null;	
		
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = Integer.parseInt((wpbez.substring(2).trim()));  			
		
		LinkedList<Tag> alleTage = myModel.getTage();
    	LinkedList<Tag> wochenTage = new LinkedList<Tag>();
    	
    	//Sortiere alle Tage aus, die nicht zu der angefragten Woche geh�ren
    	for(Tag t: alleTage){
    		if(t.getWpnr() == wpnr){
    			wochenTage.add(t);
    		}    		
    	}		
		
		LinkedList<Mitarbeiter> mitarbeiterList = myModel.getAlleMitarbeiter();
		String[] spaltennamen = null;
		String[][] zeilen = null;
		
		if(wochenTage.size() == 6){
			
			String[] tempSpaltennamen = {"", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"};
			spaltennamen = tempSpaltennamen;
			
			LinkedList <String[]> temp = new LinkedList<String[]>();
			
			for(Mitarbeiter m : mitarbeiterList){
				temp.add(generiereMitarbeiterSpalte(wpnr, m, wochenTage.size()));				
			}
			
			zeilen = new String[temp.size()][];
			
			for(int i = 0; i < zeilen.length; i++){
				zeilen[i]= temp.get(i);			
			}	
		}
		
		if(wochenTage.size() == 7){
			
			String[] tempSpaltennamen = {"", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"};
			spaltennamen = tempSpaltennamen;
			
			LinkedList <String[]> temp = new LinkedList<String[]>();
			
			for(Mitarbeiter m : mitarbeiterList){
				temp.add(generiereMitarbeiterSpalte(wpnr, m, wochenTage.size()));				
			}
			
			zeilen = new String[temp.size()][];
			
			for(int i = 0; i < zeilen.length; i++){
				zeilen[i]= temp.get(i);			
			}	
		}
		
		try{
			wochenplan = new JTable(zeilen,spaltennamen);
		}catch(Exception e){
			e.printStackTrace();
		}
		
         return wochenplan;
	}

	
	
	/**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zum Erzeugen einer Zeile in der Wochenplantabelle f�r einen spezifischen Mitarbeiter
	 */
    private String[] generiereMitarbeiterSpalte(int wpnr, Mitarbeiter ma, int tageAnzahl){
    
    	//Symbolisiert eine Mitarbeiterzeile in der Wochenplantabelle
    	String[] rueckgabe = new String[tageAnzahl+1];     	
    	
    	LinkedList<Tag> alleTage = myModel.getTage();
    	LinkedList<Tag> wochenTage = new LinkedList<Tag>();
    	
    	//Sortiere alle Tage aus, die nicht zu der angefragten Woche geh�ren
    	for(Tag t: alleTage){
    		if(t.getWpnr() == wpnr){
    			wochenTage.add(t);
    		}    		
    	}    	  	
    	
    	//Abfrage der gesamten Zuordnung und Suche nach den Schichten, die dem �bergebenen Mitarbeiter zugeordet sind
    	LinkedList<Ma_Schicht> einteilung = this.myModel.getMa_Schicht();
    	LinkedList<Ma_Schicht> mitarbeiterEinteilung = new LinkedList<Ma_Schicht>();
    			
    	//Iteriere durch die Einteilungsliste und �bernehme die Datens�tze in die Mitarbeitereinteilung, die den gesuchten Benuzternamen haben
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
    	
    	for(Tag t: wochenTage){    	
    		
    		for(Schicht s: mitarbeiterSchichten){  
    			
    			if(s.getTbez().equals(t.getTbez())){
    				
    				if(tageSchichtenMap.get(t.getTbez()) == null){
    					tageSchichtenMap.put(t.getTbez(), new LinkedList<Schicht>());    					
    				}
    				tageSchichtenMap.get(t.getTbez()).add(s);   				
    				   				
    			}   			
    		}    		
    	}       	
    	
    	String montagsSchichten = getTageszeitraum(tageSchichtenMap.get("Montag"));   	
    	String dienstagsSchichten = getTageszeitraum(tageSchichtenMap.get("Dienstag"));
    	String mittwochsSchichten = getTageszeitraum(tageSchichtenMap.get("Mittwoch"));
    	String donnerstagsSchichten = getTageszeitraum(tageSchichtenMap.get("Donnerstag"));
    	String freitagsSchichten = getTageszeitraum(tageSchichtenMap.get("Freitag"));
    	String samstagsSchichten = getTageszeitraum(tageSchichtenMap.get("Samstag"));
    	String sonntagsSchichten = getTageszeitraum(tageSchichtenMap.get("Sonntag"));
    	
    	if(tageAnzahl == 6){
    		rueckgabe[0] = ma.getVorname() + " " + ma.getName();
    		rueckgabe[1] = montagsSchichten;
    		rueckgabe[2] = dienstagsSchichten;
    		rueckgabe[3] = mittwochsSchichten;
    		rueckgabe[4] = donnerstagsSchichten;
    		rueckgabe[5] = freitagsSchichten;
    		rueckgabe[6] = samstagsSchichten;
    	}
    	
    	//Sonderfall: Verkaufsoffener Sonntag
    	if(tageAnzahl == 7){
    		rueckgabe[0] = ma.getVorname() + " " + ma.getName();
    		rueckgabe[1] = montagsSchichten;
    		rueckgabe[2] = dienstagsSchichten;
    		rueckgabe[3] = mittwochsSchichten;
    		rueckgabe[4] = donnerstagsSchichten;
    		rueckgabe[5] = freitagsSchichten;
    		rueckgabe[6] = samstagsSchichten;
    		rueckgabe[7] = sonntagsSchichten;
    	}   	
    	
    	return rueckgabe;
    }
		
    
    
    /**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zur Ermittlung der gesamten Arbeitszeit aufgrund der eingeteilten Schichten eines Mitarbeiters an einem bestimmten Tag f�r die Einstatzplantabelle
	 */
    private String getTageszeitraum(LinkedList<Schicht> schichten){
    	
    	String rueckgabe = null;
    	
    	if(schichten == null){
    		return "-";
    	}
    	else{			
    			
    		LinkedList<Date> zeitenDate = new LinkedList<Date>();
    			
    		// Iteriere durch die �bergebene Schichtenliste und konvertiere die vorhanden Zeiten ins Date Format
    		for(Schicht s : schichten){
    				
    			String anfangsZeitString = s.getAnfanguhrzeit();
    			String endZeitString = s.getEndeuhrzeit();
    			SimpleDateFormat format = new SimpleDateFormat("hh:mm");
    								
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
    			
    			Collections.sort(zeitenDate);    			
    			
    			//Gebe die fr�hste und die sp�teste Zeit aus der Liste zur�ck --> Gesamter Zeitraum abgedeckt
    			rueckgabe = zeitenDate.getFirst() + "-" + zeitenDate.getLast();    				
    		}     		
    	}
    	
    	return rueckgabe;
    }  
    
	/**
	 * @author Thomas Friesen 
	 * @info Ver�ffentlichen eines erstellten Wochenplanes im System, so dass er von allen Mitarbeitern und nicht nur vom Kassenb�ro/Chef gesehen werden kann.
	 */
	protected boolean publishWochenplan(String username, String wpbez){
		
		boolean success = false;
		int wpnr = Integer.parseInt((wpbez.substring(2).trim()));
		myModel.�ffentlichStatustrue(wpnr);
		return success;
		
	}
	
	/**
	 * @author Thomas Friesen
	 * @info Entfernen eines bereits erstellten Wochenplanes aus dem System mit allen daraus resultierenden Informationen �ber Tage, Schichten und Terminen/Krankheiten/Urlaub
	 */
	protected boolean entferneWochenplan(String username, String wpbez){
		
		boolean success = false;
		int wpnr = Integer.parseInt((wpbez.substring(2).trim()));
		myModel.�ffentlichStatustrue(wpnr);
		return success;
	}
		
	/**
	 * @author Lukas K�hl
	 * @info Die Methode dient dazu einen bestimmten Wochenplan an alle Mitarbeiter der Warenh�user per E-Mail zu verschicken um Ihnen die Arbeitszeiten mitzuteilen
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
        	String filePath = System.getProperty("user.home") + "/Desktop/Kalenderwochen�bersicht_" + wpbez +".png";
            ImageIO.write(bi, "png", new File(filePath));
            
            LinkedList<Mitarbeiter> alleMitarbeiter = this.myModel.getAlleMitarbeiter();
            
            MailStrg myMailController = new MailStrg();
            
            final String user = "einsatzplan.team";
            final String password = "";
            final String senderAddress = "einsatzplan.team@web.de";           
            
            for(Mitarbeiter m: alleMitarbeiter){
            	//Festlegung des Textes f�r die E-Mail
            	String message = "Hallo " + m.getVorname() + " " + m.getName() +",\n" + 
            			"anbei dieser Mail finden Sie den Mitarbeitereinsatzplan f�r die " + wpbez +".\n" + 
            			"\n" +
            			"Mit freundlichen Gr��en,\n" +
            			"Team der Einsatzplanverwaltung";           	
            	
            	//Nutzung des Gruppenaccounts bei Web.de
            	myMailController.sendMail(user, password, senderAddress, m.getEmail(), "Einsatzplan f�r " + wpbez, message, filePath);	
            	
            }
            
            success = true;
        }
        catch(IOException ioe)
        {
            System.out.println("Fehler beim Verschicken der Wochenpl�ne per E-Mail:");
            ioe.printStackTrace();
        }        
        
		return success;
	} 
	
 
	protected ArrayList<String> getWochenplaene(){
		ArrayList<String> rueckgabe = new ArrayList<String>();
	
		TreeMap<Integer, Wochenplan> alleWochenplaene = this.myModel.getWochenpl�ne();
		
		for(Integer i : alleWochenplaene.keySet()){			
			rueckgabe.add("KW" + alleWochenplaene.get(i).getWpnr());			
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
	
	/**
	 * @author 
	 * @info Hilfsmethode zum �ndern der hinterlegten Standardeinstellungen f�r einen Wochenplan in der Datenbank.
	 */
	private void bearbeiteStandardeinstellungen(Standardeinstellungen settings){
		
		//ausf�llen
		
		
	}
	
	
	/**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zum Pr�fen der Struktur von �ffnungs- und Hauptzeiten(die richtige Struktur ist �ffnungszeit --> Hauptzeitbeginn --> HauptzeitEnde --> Schlie�zeit) 
	 */
	private boolean checkZeitenWochenplan(Map<String, Date> zeitenDate) {
		return ((zeitenDate.get("�ffnungszeit").before(zeitenDate.get("HauptzeitBeginn"))) && (zeitenDate.get("HauptzeitBeginn").before(zeitenDate.get("HauptzeitEnde"))) && (zeitenDate.get("HauptzeitEnde").before(zeitenDate.get("Schlie�zeit")))); 		
	}	
}
