package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	 * @author 
	 * @info Anlegen eines neuen Wochenplanes und hinterlegen des Planes in der Datenbank inklusive der spezifischen Restriktionen für die zu erstellende Woche(benutzerdefiniert/Standard)
	 */
	protected boolean erstelleWochenplanCustom(String username, String wpbez, TreeMap<String, String> zeiten, TreeMap<String, Integer> besetzung){
		boolean success = false;
		
		//Standardeinstellungen settings = new Standardeinstellungen(zeiten.get("Öffnungszeit"), zeiten.get("Schließzeit"), zeiten.get("Hauptzeitbeginn"), zeiten.get("Hauptzeitende"), 18, besetzung.get("MehrBesetzungKasse"), besetzung.get("MinBesetzungKasse"), besetzung.get("MinBesetzungWarenInfo"), besetzung.get("MinBesetzungTechnikInfo"));
		/*
		
		Mitarbeiter user = Einsatzplanmodel.getMitarbeiter(username);
		
		Userrecht recht = Einsatzplanmodel.getUserrecht(user.getJob());
				
		if(recht.getBenutzerrolle().equals("Chef")){
					
			
			
			Map<String, Date> zeitenDate = new TreeMap<String, Date>();
			
			for(String key : zeiten.keySet()){
				
				String zeitString = zeiten.get(key);
				SimpleDateFormat format = new SimpleDateFormat("hh:mm");
								
				try{
					Date temp = format.parse(zeitString);				
					zeitenDate.put(key, temp);			
					
				}catch(Exception e){
					System.out.println("Fehler beim Konvertieren eines Datums");					
				}
				
				//Prüfe, ob die Zeiten den Vorgaben entsprechen
				boolean zeitenOk = checkZeiten(zeitenDate);
				
				
				//
				if(zeitenOk){
					
				}
					
				
				
				
				
			}		
		}
		else{
			System.out.println("Der Benutzer verfügt nicht über die notwendigen Berechtigungen zum Anlegen eines neuen Einsatzplanes!");
		}		
		
		
		//ausfüllen
		
		
		Wochenplan wp = new Wochenplan("lkuehl", "08:00", "20:00", "10:00", "18:00", 4, 1,1,6,1,1);
		
		String[] title = {"Mitarbeiter", "Montag", "Dienstag","Mittwoch", "Donnerstag", "Freitag", "Samstag"};
        
        // 2D array is used for data in table
        String[][] data = {{"Lukas Kühl", "08:00-15:00" + "-" +"13:00-20:00", "-", "08:00-15:00", "-", "13:00-20:00", "-"},
                };

        // Creates Table
        wochenplan = new JTable(data, title)
        {       	  
      	  
             // Determines if data can be entered by users
             public boolean isCellEditable(int data, int columns)
             {
                 return false;
             }
 
             //  Creates cells for the table         
             public Component prepareRenderer(
                          TableCellRenderer r, int rows, int columns)
             {
                 Component c = super.prepareRenderer(r, rows, columns);                
                
                 if(data[rows][columns].equals("noch nicht belegt!")){
                  	   c.setBackground(Color.RED);
                 }
                     
                 if(data[rows][columns].startsWith("Kasse")){                	  
              	   //jt.setRowHeight(rows, 3);
              	   c.setBackground(Color.GREEN);
                 }
                    
                 if( data[rows][columns].startsWith("0") || data[rows][columns].startsWith("1")){                	  
              	   c.setBackground(Color.WHITE);
                 } 
  
                 return c;
             }
       };

       // Set size of table     
       wochenplan.setPreferredScrollableViewportSize(new Dimension(770, 340));         
       // This will resize the height of the table automatically 
       // to all data without scrolling. 
       wochenplan.setFillsViewportHeight(true);         
       */
       
       return success;
   }

	/**
	 * @author 
	 * @info Anlegen eines neuen Wochenplanes und hinterlegen des Planes in der Datenbank inklusive der spezifischen Restriktionen für die zu erstellende Woche(benutzerdefiniert/Standard)
	 */
	protected JTable generiereWochenplanView(String wpbez){
		JTable wochenplan = null;
		
		//Standardeinstellungen settings = Einsatzplanmodel.getStandardeinstellungen();
		
		
		
		
		
		
		
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
	 * @author 
	 * @info Die Methode dient dazu einen bestimmten Wochenplan an alle Mitarbeiter der Warenhäuser per E-Mail zu verschicken um Ihnen die Arbeitszeiten mitzuteilen
	 */
	protected boolean verschickeWochenplan(String username ,String wpbez){
		boolean success = false;
		
		//ausfüllen
		
		return success;
	}	
	
	/**
	 * @author 
	 * @info Hilfsmethode zum erstellen von Vorgaben für einen Wochenplan 
	 */
	private void erstelleVorgaben(Wochenplan wp, boolean standard){
		
		//ausfüllen
		
		
	}
	
	
	/**
	 * @author Lukas Kühl
	 * @info Hilfsmethode zum Prüfen der Struktur von Öffnungs- und Hauptzeiten
	 */
	private boolean checkZeiten(Map<String, Date> zeitenDate) {
		return ((zeitenDate.get("Öffnungszeit").before(zeitenDate.get("HauptzeitBeginn"))) && (zeitenDate.get("HauptzeitBeginn").before(zeitenDate.get("HauptzeitEnde"))) && (zeitenDate.get("HauptzeitEnde").before(zeitenDate.get("Schließzeit")))); 
			
	}
	
	
	
	
}
