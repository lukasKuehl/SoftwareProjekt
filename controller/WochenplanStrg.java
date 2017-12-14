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

import data.Mitarbeiter;
import data.Standardeinstellungen;
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
		Mitarbeiter user = Einsatzplanmodel.getMitarbeiter(username);
		
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
		/*
		Mitarbeiter user = Einsatzplanmodel.getMitarbeiter(username);
		
		Userrecht recht = Einsatzplanmodel.getUserrecht(user.getJob());
				
		if(recht.getBenutzerrolle().equals("Chef")){	
			
			try{
				Standardeinstellungen settings = this.myModel.getStandardeinstellungen();				
				Wochenplan wp = new Wochenplan(0, false, settings.getÖffnungszeit(), settings.getSchließzeit(), settings.getHauptzeitbeginn(), settings.getHauptzeitende(), this.myController.getView().getUsername(), settings.getMinanzinfot(), settings.getMinanzinfow(), settings.getMinanzkasse(), settings.getMehrbesetzungkasse());
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
		*/
		
		return success;
	}
	
	
	
	/**
	 * @author 
	 * @info Erzeugen eines Einsatzplanes in Form eines JTables zur Ansicht in der View
	 */
	protected JTable generiereWochenplanView(String wpbez){
		JTable wochenplan = null;	
			//Ausfüllen
		
         return wochenplan;
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
		/*
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
            ImageIO.write(bi, "png", new File(System.getProperty("user.home") + "/Desktop/Kalenderwochenübersicht_" + wpbez +".png" ));
            
            LinkedList<Mitarbeiter> alleMitarbeiter = this.myModel.getWochenplaene();
            
            MailStrg myMailController = new MailStrg();
            for(Mitarbeiter m: alleMitarbeiter){
            	//ToDo Anhang und username + password klären
            	myMailController.sendMail(user, password, senderAddress, recipientsAddress, subject);
            	
            	
            }
            
            success = true;
        }
        catch(IOException ioe)
        {
            System.out.println("write: " + ioe.getMessage());
        }       
        
        */
        
		return success;
	} 
	
 
	protected ArrayList<String> getWochenplaene(){
		ArrayList<String> rueckgabe = null;
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
