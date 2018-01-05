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
import javax.swing.table.TableColumn;

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
 * @author Lukas K�hl
 * @info Die Klasse WochenplanStrg dient dazu, jegliche Anfragen bez�glich eines Wochenplanes im System zu verarbeiten und zu validieren.
 */
class WochenplanStrg {
	
	//Initialisierung der Instanzvariablen
	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	private JDialog myDialog = null;
	
	/**
	 * @author Lukas K�hl
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
		
		//Generiere eine eindeutige Wochenplannummer aus der Wochenplanbezeichnung		
		int wpnr = myController.getWpnr(wpbez);
		
		//Pr�fe, ob der Benutzer �ber die notwendigen Berechtigungen zum Erstellen eines neuen Wochenplanes verf�gt
		if(myController.isUserAdmin(username)){		
			
			Map<String, Date> zeitenDate = new TreeMap<String, Date>();
			
			// Iteriere durch die TreeMap mit den Angaben zu den Zeiten f�r den neuen Wochenplan und �bertrage die Werte, falls m�glich in eine neue Map mit dem Typ java.util.Date
			for(String key : zeiten.keySet()){
				
				String zeitString = zeiten.get(key);
				SimpleDateFormat format = new SimpleDateFormat("hh:mm");
								
				try{							
					Date temp = format.parse(zeitString);				
					zeitenDate.put(key, temp);			
					
				}catch(Exception e){					
					String fehler = "Fehler beim Konvertieren einer Uhrzeit! \n";
					myController.printErrorMessage(fehler);										
				}					
			}
			
			//Pr�fe, ob die Zeiten den Vorgaben entsprechen
			if(checkZeitenWochenplan(zeitenDate)){
					
				boolean checkbesetzung = true;				
									
				//Pr�fe, ob es negative Werte in der vorhandenen Besetzung-Map gibt
				for(String s : besetzung.keySet()){
					if(besetzung.get(s) < 0){
						checkbesetzung = false;
					}						
				}
					
				//Alle Anforderungen wurden erf�llt --> ein neuer Wochenplan kann im System hinterlegt werden
				if(checkbesetzung){					
					
					if(myModel.getWochenplan(wpnr) == null){					
						
						Wochenplan wp = new Wochenplan(wpnr, false, zeiten.get("�ffnungszeit"), zeiten.get("Schlie�zeit"), zeiten.get("HauptzeitBeginn"), zeiten.get("HauptzeitEnde"), username, besetzung.get("MinBesetzungInfoTechnik"), besetzung.get("MinBesetzungInfoWaren"), besetzung.get("MinBesetzungKasse"), besetzung.get("MehrbesetzungKasse"));
												
						this.myModel.addWochenplan(wp);
						
						if(this.myModel.getWochenplan(wp.getWpnr()) != null){
							success = true;
						}
					}
					else{
						String fehler = "Der ausgew�hlte Wochenplan wurde bereits erstellt! \n";
						myController.printErrorMessage(fehler);	
						
					}					
				}
				else{
					String fehler = "Fehler beim Erstellen eines neuen Wochenplanes:\n" +"Die Besetzungsanzahl darf nicht negativ sein! \n";
					myController.printErrorMessage(fehler);						
				}						
			}
			else{				
				String fehler = "Fehler beim Erstellen eines neuen Wochenplanes: \n" + "Die Angaben zu den �ffnungs- und Hauptzeiten sind fehlerhaft! \n";
				myController.printErrorMessage(fehler);	
			}					
		}
		else{	
			String fehler = "Fehler beim Erstellen eines neuen Wochenplanes: \n" + "Der Benutzer verf�gt nicht �ber die notwendigen Berechtigungen zum Anlegen eines neuen Einsatzplanes! \n";
			myController.printErrorMessage(fehler);			
		}	
		
	return success;
   }
	
	/**
	 * @author Lukas K�hl
	 * @info Anlegen eines neuen Wochenplanes nach standardm��igen Einstellungen. Auslesen der momentanen Standardeinstellungen aus der Datenbank und hinterlegen des erstellten Planes in der Datenbank.
	 */
	protected boolean erstelleWochenplanStandard(String username, String wpbez){
		
		boolean success = false;		
	
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = myController.getWpnr(wpbez);
				
    	//Pr�fe, ob der Benutzer �ber die notwendigen Berechtigungen verf�gt, um einen neuen Wochenplan zu Erstellen
		if(myController.isUserAdmin(username)){	
			
			try{
				Standardeinstellungen settings = this.myModel.getStandardeinstellungen();				
				
				if(myModel.getWochenplan(wpnr) == null){
					//Erstelle einen neuen Wochenplan mit den Daten der Standardeinstellung und hinterlege diesen in der Datenbank
					Wochenplan wp = new Wochenplan(wpnr, false, settings.get�ffnungszeit(), settings.getSchlie�zeit(), settings.getHauptzeitbeginn(), settings.getHauptzeitende(), username, settings.getMinanzinfot(), settings.getMinanzinfow(), settings.getMinanzkasse(), settings.getMehrbesetzung());
					this.myModel.addWochenplan(wp);	
					
					if(this.myModel.getWochenplan(wp.getWpnr()) != null){
						success = true;
					}	
				}
				else{
					String fehler = "Der ausgew�hlte Wochenplan wurde bereits erstellt! \n";
					myController.printErrorMessage(fehler);	
					
				}							
				
			}catch(Exception e){
				
				String fehler = "Fehler beim Erstellen eines neuen Wochenplanes nach Standardeinstellungen: \n" + "Die Standardeinstellungen wurden nicht richtig �bernommen! \n" + e.getMessage();
				myController.printErrorMessage(fehler);					
			}			
		}
		else{
			
			String fehler = "Fehler beim Erstellen eines neuen Wochenplanes nach Standardeinstellungen: \n" + "Der Benutzer verf�gt nicht �ber die notwendigen Berechtigungen zum Anlegen eines neuen Einsatzplanes! \n";
			myController.printErrorMessage(fehler);				
		}		
		
		return success;
	}	
	
	/**
	 * @author Lukas K�hl
	 * @info Erzeugen eines Einsatzplanes in Form eines JTables zur Ansicht in der View
	 */
	protected JTable generiereWochenplanView(String wpbez){
		JTable wochenplan = null;	
		
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = myController.getWpnr(wpbez);			
		
		LinkedList<Tag> alleTage = myModel.getTage();
    	TreeMap<Integer, Tag> wochenTage = new TreeMap<Integer, Tag>();
    	//Nummerierung der Tage von 1 aufsteigend
    	int counter = 1;
    	
    	//Sortiere alle Tage aus, die nicht zu der angefragten Woche geh�ren
    	for(Tag t: alleTage){
    		if(t.getWpnr() == wpnr){
    			wochenTage.put(counter, t);
    			counter++;
    		}    		
    	}    	
		
    	//Suche nach allen Mitarbeitern, die innerhalb mind. einer Schicht des Wochenplanes eingeteilt wurden. 
		TreeMap<String, Mitarbeiter> tempMitarbeiter = new TreeMap<String, Mitarbeiter>(); 
		LinkedList<Mitarbeiter> wochenMitarbeiter = new LinkedList<Mitarbeiter>();
		
		LinkedList<Schicht> alleSchichten = myModel.getSchichten();
		LinkedList<Ma_Schicht> alleSchichtEinteilungen = myModel.getMa_Schicht();
		
		for(Schicht s: alleSchichten){
			if(s.getWpnr() == wpnr){
				
				for(Ma_Schicht mas : alleSchichtEinteilungen){
					//Pr�fe, ob der Mitarbeiter bereits in einer Schichteinteilung zu der Woche aufgetaucht ist. Falls nein, wird ein neuer Eintrag hinzugef�gt.
					if(!tempMitarbeiter.containsKey(mas.getBenutzername())){
						tempMitarbeiter.put(mas.getBenutzername(), this.myModel.getMitarbeiter(mas.getBenutzername()));
					}				
				}						
			}			
		}
		//Wandle die tempMitarbeiter-map zu einer LinkedList<Mitarbeiter> um
		for(String s: tempMitarbeiter.keySet()){
			wochenMitarbeiter.add(tempMitarbeiter.get(s));			
		}	
		
		String[] spaltennamen = new String[wochenTage.size()+1];
		String[][] zeilen = new String[wochenMitarbeiter.size()][wochenTage.size()+1];
				
		for(int i = 0; i< spaltennamen.length; i++){	
			
			//Leerzeile in der oberen Linken Ecke des Wochenplanes
			if(i == 0){				
				spaltennamen[0] = "";
			}
			else{				
				//Hinterlegen des Tages als Spaltenname
				spaltennamen[i] = wochenTage.get(i).getTbez();
			}			
		}
		
		LinkedList <String[]> temp = new LinkedList<String[]>();
		
		for(Mitarbeiter m : wochenMitarbeiter){
			//Erzeuge f�r jeden Mitarbeiter die zugeh�rige Spalte innerhalb der Wochenplan�bersicht
			temp.add(generiereMitarbeiterSpalte(wpnr, m, spaltennamen));				
		}	
		
		//R�ckgabe aus der tempor�ren Liste in das zwei dimensionale Array
		for(int i = 0; i < zeilen.length; i++){
			zeilen[i]= temp.get(i);			
		}				
		
		try{		
			//Erzeuge eine neue JTable mit den erhobenen Daten, welche die Wochen�bersicht in der View repr�sentiert
			wochenplan = new JTable(zeilen,spaltennamen){
				
				//Zellen lassen sich nicht bearbeiten --> �nderungen nur �ber System und Datenbank
				 public boolean isCellEditable(int data, int title)
	               {
	                   return false;
	               }				 
			};
		}catch(Exception e){			

			String fehler = "Fehler beim Erstellen eines neuen JTables f�r den Wochenplan" + wpbez + "\n" + e.getMessage();
			myController.printErrorMessage(fehler);			
		}	
		
         return wochenplan;
	}	
	
	/**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zum Erzeugen einer Zeile in der Wochenplantabelle f�r einen spezifischen Mitarbeiter
	 */
    private String[] generiereMitarbeiterSpalte(int wpnr, Mitarbeiter ma, String[] wochenTage){
    
    	//Symbolisiert eine Mitarbeiterzeile in der Wochenplantabelle
    	String[] rueckgabe = new String[wochenTage.length];      	  	
    	
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
    	
		//Map zur Speicherung aller vorhandenen Schichten zu einem bestimmten Tag aus dem Wochenplan um den gesamten Tageszeitraum, in dem der Mitarbeiter eingeteilt ist abzubilden
    	Map<String, LinkedList<Schicht>> tageSchichtenMap = new TreeMap<String, LinkedList<Schicht>>();   	
    	
    	for(int i = 1; i< rueckgabe.length; i++){   		
    		
    		for(Schicht s: mitarbeiterSchichten){
    			if(s.getTbez().equals(wochenTage[i])){
    				//Falls noch kein Eintrag mit einer SchichtenListe f�r den jeweiligen Tag erzeugt wurde, wird ein neuer Eintrag in der tageSchichtenMap erzeugt
    				if(tageSchichtenMap.get(wochenTage[i]) == null){
    					tageSchichtenMap.put(wochenTage[i], new LinkedList<Schicht>());    					
    				}
    				//F�ge die Schicht zu dem aktuellen Tag hinzu
    				tageSchichtenMap.get(wochenTage[i]).add(s);    				
    			}
    		}
    		
    	}    	
    	
    	for(int i = 0; i< rueckgabe.length; i++){
    		
    		if(i == 0){
    			//In der ersten Spalte wird der Name des Mitarbeiters eingetragen
    			rueckgabe[0] = ma.getVorname() + " " + ma.getName();
    		}
    		else{
    			//Trage den gesamten Tageszeitraum zu dem jeweiligen Tag (Tag X Mitarbeiter --> Tageszeitraum) ein
    			rueckgabe[i] = getTageszeitraum(tageSchichtenMap.get(wochenTage[i]));
    		}
    	} 	
    	
    	return rueckgabe;
    }    
    
    /**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zur Ermittlung der gesamten Arbeitszeit aufgrund der eingeteilten Schichten eines Mitarbeiters an einem bestimmten Tag f�r die Einstatzplantabelle
	 */
    private String getTageszeitraum(LinkedList<Schicht> schichten){
    	
    	String rueckgabe = null;
    	
    	//Ein Mitarbeiter ist an dem jeweiligen Tag keiner Schicht zugeteilt worden
    	if(schichten == null){
    		return "-";
    	}
    	else{			
    			
    		LinkedList<Date> zeitenDate = new LinkedList<Date>();
    			
    		// Iteriere durch die �bergebene Schichtenliste und konvertiere die vorhanden Zeiten ins Date Format
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
    			
    			//Gebe die fr�hste und die sp�teste Zeit aus der Liste zur�ck --> Gesamter Zeitraum abgedeckt
    			Collections.sort(zeitenDate);       			
    			
    			//F�lle die offen gebliebenen Stellen mit f�hrenden Nullen auf
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
	 * @author Thomas Friesen
	 * @return 
	 * @info Ver�ffentlichen eines erstellten Wochenplanes im System, so dass er von allen Mitarbeitern und nicht nur vom Kassenb�ro/Chef gesehen werden kann.
	 */
	protected boolean publishWochenplan(String username, String wpbez){
		
	boolean success = false;
		
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = myController.getWpnr(wpbez);  
		
		if(myController.isUserAdmin(username)){	
			
			try{
				
				if(myModel.getWochenplan(wpnr) != null){
					this.myModel.oeffentlichStatustrue(wpnr);
					success = true;
				}			
				
			}catch(Exception e){
				
				String fehler = "Fehler beim Ver�ffentlichen eines Wochenplanes \n" + e.getMessage();
				myController.printErrorMessage(fehler);								
			}		
		}	
		
		return success;
	}
	
	/**
	 * @author Lukas K�hl
	 * @info Entfernen eines bereits erstellten Wochenplanes aus dem System mit allen daraus resultierenden Informationen �ber Tage, Schichten und Terminen/Krankheiten/Urlaub
	 */
	protected boolean entferneWochenplan(String username, String wpbez){
		
		boolean success = false;
		
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = myController.getWpnr(wpbez);  
		
    	//Pr�fe, ob der Benutzer �ber die notwendigen Berechtigungen zum L�schen eines Wochenplanes aus dem System verf�gt
		if(myController.isUserAdmin(username)){	
			
			try{
				//Pr�fe, ob der Wochenplan noch in der Datenbank vorhanden ist
				if(myModel.getWochenplan(wpnr) != null){
					this.myModel.deleteWochenplan(wpnr);
					success = true;
				}			
				
			}catch(Exception e){				

				String fehler = "Fehler beim L�schen eines Wochenplanes \n" + e.getMessage();
				myController.printErrorMessage(fehler);									
			}		
		}	
		else{
			String fehler = "Sie verf�gen nicht �ber die notwendigen Berechtigungen, zum Entfernen eines Wochenplanes. Bitte wenden Sie sich an den Systemadministrator.\n";
			myController.printErrorMessage(fehler);			
		}
		
		return success;
	}
		
	/**
	 * @author Lukas K�hl
	 * @info Die Methode dient dazu einen bestimmten Wochenplan an alle Mitarbeiter der Warenh�user per E-Mail zu verschicken um Ihnen die Arbeitszeiten mitzuteilen
	 */
	protected boolean verschickeWochenplan(String username ,String wpbez, JTable wochenplan, JTableHeader header){
			
		boolean success = false;		
		
		wochenplan.getTableHeader().setSize(wochenplan.getTableHeader().getPreferredSize());
		wochenplan.setSize(wochenplan.getPreferredSize());
		wochenplan.setPreferredScrollableViewportSize(wochenplan.getPreferredScrollableViewportSize());
		wochenplan.setFillsViewportHeight(true);				
		
		if(myController.isUserAdmin(username)){
			
			//Ermittle die ben�tigte H�he und Breite f�r ein Bild, mit der �bergebenen JTable
			int weite = Math.max(wochenplan.getWidth(), header.getWidth());
	        int hoehe = wochenplan.getHeight() + header.getHeight();
	        
	        //Erstelle eine Maske, in der die JTable eingef�gt werden kann
	        BufferedImage bi = new BufferedImage(weite, hoehe, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2 = bi.createGraphics();
	        header.paint(g2);
	        g2.translate(0, header.getHeight());
	        
	        //�bertrage die JTable in die erstellte Maske        
	        wochenplan.paint(g2);	        
	       
	        g2.dispose();
	        
	        try
	        {
	        	//Speichern des Bildes der �bergebenen JTable auf dem Desktop des Benutzers
	        	String filePath = System.getProperty("user.home") + "\\Desktop\\Kalenderwochen�bersicht_" + wpbez +".png";
	            ImageIO.write(bi, "png", new File(filePath));
	            
	            LinkedList<Mitarbeiter> alleMitarbeiter = this.myModel.getAlleMitarbeiter();
	            
	            //Erstelle eine neue MailStrg zum Versenden der Mails mit einem Bild der JTable
	            MailStrg myMailController = new MailStrg();
	            
	            //Parameter E-Mail-Account der Einsatzplanverwaltung
	            final String user = "einsatzplan.team";
	            final String password = "FHBIEinsatzplanteam";
	            final String senderAddress = "einsatzplan.team@web.de";           
	            
	            for(Mitarbeiter m: alleMitarbeiter){
	            	//Festlegung des Textes f�r die E-Mail
	            	String message = "Hallo " + m.getVorname() + " " + m.getName() +",\n" + 
	            			"anbei dieser Mail finden Sie den Mitarbeitereinsatzplan f�r die " + wpbez +".\n" + 
	            			"\n" +
	            			"Mit freundlichen Gr��en,\n" +
	            			"Team der Einsatzplanverwaltung";           	
	            	
	            	//Nutzung des Gruppenaccounts bei Web.de	
	            	
	            	//Demoversion zum vermeiden von Spam
	            	if(m.getBenutzername().equals("Hmustermann")){       	
	            		//System.out.println("Mail senden");
	            		myMailController.sendMail(user, password, senderAddress, m.getEmail(), "Einsatzplan f�r " + wpbez, message, filePath);	
	            		
	            	}
	            		
	            	
	            }
	            success = true;
	        }catch(IOException ioe)
	        {
	        	String fehler = "Fehler beim Verschicken der Wochenpl�ne per E-Mail:\n" + ioe.getMessage();
				myController.printErrorMessage(fehler);		        
	        }              
        }
		else{
			String fehler = "Sie verf�gen nicht �ber die notwendigen Berechtigungen zum Verschicken eines Wochenplanes, bitte wenden Sie sich an den Systemadministrator.\n";
			myController.printErrorMessage(fehler);		
		}
        
		return success;
	} 
	
 
	/**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zum Ausgeben aller vorhandenen Wochenpl�ne aus der Datenbank.
	 */
	protected ArrayList<String> getWochenplaene(){
		ArrayList<String> rueckgabe = new ArrayList<String>();
	
		LinkedList<Wochenplan> alleWochenplaene = this.myModel.getWochenplaene();
		
		//Abfrage der Wochenplannummern und hinzuf�gen der Bezeichnung KW zum Ausw�hlen in der View
		for(Wochenplan wp : alleWochenplaene){			
			rueckgabe.add("KW" + wp.getWpnr());			
		}	
		
		return rueckgabe;
	}
	
	
	/**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zum Anzeigen von den zugeh�rigen Tagen eines Wochenplanes.
	 */
	protected ArrayList<String> getTage(String wpbez){
		ArrayList<String> rueckgabe = new ArrayList<String>();
		
		//Umwandeln der Wpbez in die eindeutige Wochennummer
    	int wpnr = myController.getWpnr(wpbez);  			
		
		LinkedList<Tag> alleTage = this.myModel.getTage();
		LinkedList<Tag> wochenTage = new LinkedList<Tag>(); 
		
		//Suche alle Tage der �bergebenen Woche
		for(Tag t: alleTage){
			if(t.getWpnr() == wpnr){
				wochenTage.add(t);				
			}
		}
		
		//Suche die Tagbezeichnungen der �bergebenen Woche
		for(Tag t : wochenTage){
			rueckgabe.add(t.getTbez());
		}		
		
		return rueckgabe;
	}	
	
	/**
	 * @author Thomas Friesen
	 * @info Hilfsmethode zum �ndern der hinterlegten Standardeinstellungen f�r einen Wochenplan in der Datenbank.
	 */
	protected boolean bearbeiteStandardeinstellungen(Standardeinstellungen settings){
				  
			boolean result = false;
			
			try{
				this.myModel.updateStanadardeinstellungen(settings);	
				result = true;
				
			}catch(Exception e){
				String fehler = "Fehler beim Update der Standardeinstellungen \n";
				myController.printErrorMessage(fehler);				
			}	
			return result;
		}	
	
	
	/**
	 * @author Lukas K�hl
	 * @info Hilfsmethode zum Pr�fen der Struktur von �ffnungs- und Hauptzeiten(die richtige Struktur ist �ffnungszeit --> Hauptzeitbeginn --> HauptzeitEnde --> Schlie�zeit) 
	 */
	private boolean checkZeitenWochenplan(Map<String, Date> zeitenDate) {
		
		boolean datenVollstaendig = true;	
		
		for(String s: zeitenDate.keySet()){				
						
			if(zeitenDate.get(s) == null){
				datenVollstaendig = false;
			}
		
		}
		
		//Wenn die Daten vollst�ndig eingetragen wurden, �berpr�fe die Reihenfolge
		if(datenVollstaendig){
			return ((zeitenDate.get("�ffnungszeit").before(zeitenDate.get("HauptzeitBeginn"))) && (zeitenDate.get("HauptzeitBeginn").before(zeitenDate.get("HauptzeitEnde"))) && (zeitenDate.get("HauptzeitEnde").before(zeitenDate.get("Schlie�zeit")))); 		
		}
		else{
			return false;
		}		
	}	
}
