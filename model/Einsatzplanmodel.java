package model;

import java.util.LinkedList;
import java.util.TreeMap;
import javax.swing.JTable;

import data.Ma_Schicht;
import data.Mitarbeiter;
import data.Schicht;
import data.Standardeinstellungen;
import data.Tag;
import data.Tauschanfrage;
import data.Tblock_Tag;
import data.TerminBlockierung;
import data.Warenhaus;
import data.Wochenplan;
import controller.EinsatzplanController;
import view.Einsatzplanview;


/**
 * @author Anes Preljevic
 * @info Allgemeines Model zum entgegennehmen der Anfragen aus der GUI und dem Controller und weiterleiten an die spezifischen Models.
 */
public class Einsatzplanmodel {

	//Initialiserung der Instanzvariablen
	private EinsatzplanController controller = null;
	private Einsatzplanview view = null;
	private Datenbank_Connection dataConnection = null;
	private Datenbank_Ma_Schicht dataMa_Schicht = null;
	private Datenbank_Mitarbeiter dataMitarbeiter = null;
	private Datenbank_Schicht dataSchicht=null;
	private Datenbank_Standardeinstellungen dataStandardeinstellungen=null;
	private Datenbank_Tag dataTag=null;
	private Datenbank_Tauschanfrage dataTauschanfrage = null;
	private Datenbank_Tblock_Tag dataTblock_Tag=null;
	private Datenbank_TerminBlockierung dataTerminBlockierung = null;
	private Datenbank_Userrecht dataUserrecht = null;
	private Datenbank_Warenhaus dataWarenhaus = null;
	private Datenbank_Wochenplan dataWochenplan=null;
	
	public Einsatzplanmodel(){
		
		
		
		this.dataWochenplan= new Datenbank_Wochenplan();
		
	}

	public TreeMap<Integer, Wochenplan> getWochenpläne(){
		
		TreeMap<Integer, Wochenplan> result = null;;
		try{
			result = this.dataWochenplan.getWochenpläne();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		return result;
	}	
		
	public Wochenplan getWochenplan(int wpnr){
		
		Wochenplan result = null;;
		try{
			result = this.dataWochenplan.getWochenplan(wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		
		
		return result;
	}
	public void addWochenplan(Wochenplan wochenplan){
		
		
		try{
			this.dataWochenplan.addWochenplan(wochenplan);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		
		
		
	}
	public void öffentlichStatusändern(Wochenplan wochenplan){
		
		
		try{
			this.dataWochenplan.updateWochenplan(wochenplan);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
	}
	public void öffentlichStatustrue(int wpnr){
		
		
		try{
			this.dataWochenplan.setzeÖffentlichstatustrue(wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
	}
	public void öffentlichStatusfalse(int wpnr){
		
		
		try{
			this.dataWochenplan.setzeÖffentlichstatusfalse(wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
	}
	public boolean checkWochenplan(int wpnr) {
		boolean result =false;
		try{
			result = this.dataWochenplan.checkWochenplan(wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		return result;
	}
	public boolean deleteWochenplan(int wpnr) {
		boolean result =false;
		try{
			result = this.dataWochenplan.deleteWochenplan(wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		return result;
	}
	public  int getNewWpnr() {
		int result = 0;
		try{
			result = this.dataWochenplan.getNewWpnr();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		return result;
	}
	public LinkedList<Ma_Schicht> getMitarbeiterausderSchicht(int schichtnr){
		
		LinkedList<Ma_Schicht>  result = null;;
		try{
			result = this.dataMa_Schicht.getMitarbeiterausderSchicht(schichtnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		return result;
	}	
	public LinkedList<Ma_Schicht> getMa_Schicht(){
		
		LinkedList<Ma_Schicht>  result = null;;
		try{
			result = this.dataMa_Schicht.getMa_Schicht();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		return result;
	}	
		

	public void addMa_Schicht(Ma_Schicht ma_schicht){
		
		
		try{
			this.dataMa_Schicht.addMa_Schicht(ma_schicht);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		
		
		
	}
	
	public boolean checkMa_Schicht(int schichtnr, String benutzername) {
		boolean result =false;
		try{
			result = this.dataMa_Schicht.checkMa_Schicht(schichtnr, benutzername);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		return result;
	}
	public boolean deleteMa_Schicht(int schichtnr, String benutzername) {
		boolean result =false;
		try{
			result = this.dataMa_Schicht.deleteMa_Schicht(schichtnr, benutzername);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		return result;
	}
	public LinkedList<Mitarbeiter> getAlleMitarbeiter() {
		LinkedList<Mitarbeiter>  result = null;;
		try{
			result = this.dataMitarbeiter.getAlleMitarbeiter();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		return result;
	}
	public Mitarbeiter getMitarbeiter(String benutzername) {
		Mitarbeiter  result = null;;
		try{
			result = this.dataMitarbeiter.getMitarbeiter(benutzername);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		return result;
	}
	public boolean checkMitarbeiter(String benutzername) {
		boolean result =false;
		try{
			result = this.dataMitarbeiter.checkMitarbeiter(benutzername);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Controllers:");
			System.out.println("Fehler beim Aufruf der Methode benutzerAnmelden:");
			e.printStackTrace();			
		}
		return result;
	}
	public LinkedList<Schicht> getSchichten(){
		
		LinkedList<Schicht> result = null;;
		try{
			result = this.dataSchicht.getSchichten();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getSchichten:");
			e.printStackTrace();			
		}
		return result;
	}	
		
	public Schicht getSchicht(int schichtnr){
		
		Schicht result = null;;
		try{
			result = this.dataSchicht.getSchicht(schichtnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getSchicht:");
			e.printStackTrace();			
		}
		
		
		return result;
	}
	public void addSchicht(Schicht schicht){
		
		
		try{
			this.dataSchicht.addSchicht(schicht);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addSchicht:");
			e.printStackTrace();			
		}
		
		
		
	}

	public boolean checkSchicht(int schichtnr) {
		boolean result =false;
		try{
			result = this.dataSchicht.checkSchicht(schichtnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkSchicht:");
			e.printStackTrace();			
		}
		return result;
	}
	public boolean deleteSchicht(int wpnr) {
		boolean result =false;
		try{
			result = this.dataSchicht.deleteSchicht(wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteSchicht:");
			e.printStackTrace();			
		}
		return result;
	}
	public  int getNewSchichtnr() {
		int result = 0;
		try{
			result = this.dataSchicht.getNewSchichtnr();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getNewSchichtnr:");
			e.printStackTrace();			
		}
		return result;
	}
	public Standardeinstellungen getStandardeinstellungen() {
		Standardeinstellungen result = null;;
		try{
			result = this.dataStandardeinstellungen.getStandardeinstellungen();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getSchicht:");
			e.printStackTrace();			
		}
		return result;
	}
	public LinkedList<Tauschanfrage> getTauschanfragen(){
		
		LinkedList<Tauschanfrage> result = null;;
		try{
			result = this.dataTauschanfrage.getTauschanfragen();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTauschanfragen:");
			e.printStackTrace();			
		}
		return result;
	}	
		
	public Tauschanfrage getTauschanfrage(int tauschnr) {
		Tauschanfrage result = null;;
		try{
			result = this.dataTauschanfrage.getTauschanfrage(tauschnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTauschanfrage:");
			e.printStackTrace();			
		}
		return result;
	}	
		
	
	public void addTauschanfrage(Tauschanfrage tauschanfrage){
		
		
		try{
			this.dataTauschanfrage.addTauschanfrage(tauschanfrage);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addTauschanfrage:");
			e.printStackTrace();			
		}
		
		
		
	}

	public boolean checkTauschanfrage(int tauschnr) {
		boolean result =false;
		try{
			result = this.dataTauschanfrage.checkTauschanfrage(tauschnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTauschanfrage:");
			e.printStackTrace();			
		}
		return result;
	}
	public boolean deleteTauschanfrage(int tauschnr) {
		boolean result =false;
		try{
			result = this.dataTauschanfrage.deleteTauschanfrage(tauschnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteTauschanfrage:");
			e.printStackTrace();			
		}
		return result;
	}
	public void updateTauschanfrage(Tauschanfrage tauschanfrage){
		
		
		try{
			this.dataTauschanfrage.updateTauschanfrage(tauschanfrage);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode updateTauschanfrage:");
			e.printStackTrace();			
		}
	}
	public void bestätigeTauschanfrage(int tauschnr){
		
		
		try{
			this.dataTauschanfrage.bestätigeTauschanfrage(tauschnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode bestätzigeTauschanfrage:");
			e.printStackTrace();			
		}
	}
	protected  int getNewTauschnr() {
		int result = 0;
		try{
			result = this.dataTauschanfrage.getNewTauschnr();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getNewTauschnr:");
			e.printStackTrace();			
		}
		return result;
	}
	public LinkedList<Tag> getTage() {	
		LinkedList<Tag> result = null;;
		try{
			result = this.dataTag.getTage();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTauschanfragen:");
			e.printStackTrace();			
		}
		return result;
	}
	
	public void addTag(Tag tag){
		
		
		try{
			this.dataTag.addTag(tag);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addTag:");
			e.printStackTrace();			
		}
		
		
		
	}

	public boolean checkTag(String tbez, int wpnr) {
		boolean result =false;
		try{
			result = this.dataTag.checkTag(tbez,wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTag:");
			e.printStackTrace();			
		}
		return result;
	}
	public boolean deleteTag(int wpnr) {
		boolean result =false;
		try{
			result = this.dataTag.deleteTag(wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteTag:");
			e.printStackTrace();			
		}
		return result;
	}
	public void updateTag(Tag tag){
		
		
		try{
			this.dataTag.updateTag(tag);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode updateTag:");
			e.printStackTrace();			
		}
	}
	public void setzeFeiertagtrue(String tbez, int wpnr){
		
		
		try{
			this.dataTag.setzeFeiertagtrue(tbez,wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode setzeFeiertagtrue:");
			e.printStackTrace();			
		}
	}
	public void setzeFeiertagfalse(String tbez, int wpnr){
		
		
		try{
			this.dataTag.setzeFeiertagfalse(tbez,wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode setzeFeiertagfalse:");
			e.printStackTrace();			
		}
	}
	public LinkedList<Tblock_Tag> getAlleTblock_Tag() {	
		LinkedList<Tblock_Tag> result = null;;
		try{
			result = this.dataTblock_Tag.getAlleTblock_Tag();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTauschanfragen:");
			e.printStackTrace();			
		}
		return result;
	}
	public Tblock_Tag getTblock_TagTB(int tblocknr) {	
		Tblock_Tag result = null;;
		try{
			result = this.dataTblock_Tag.getTblock_TagTB(tblocknr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTblock_TagTB:");
			e.printStackTrace();			
		}
		return result;
	}
	public Tblock_Tag getTblock_TagT(String tbez,int wpnr) {	
		Tblock_Tag result = null;;
		try{
			result = this.dataTblock_Tag.getTblock_TagT(tbez, wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTblock_TagT:");
			e.printStackTrace();			
		}
		return result;
	}
	public void addTblock_Tag(Tblock_Tag tblocktag){
		
		
		try{
			this.dataTblock_Tag.addTblock_Tag(tblocktag);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addTblock_Tag:");
			e.printStackTrace();			
		}
		
		
		
	}

	public boolean checkTblock_TagTB(int tblocknr) {
		boolean result =false;
		try{
			result = this.dataTblock_Tag.checkTblock_TagTB(tblocknr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTblock_TagTB:");
			e.printStackTrace();			
		}
		return result;
	}
	public boolean checkTblock_TagTA(String tbez, int wpnr) {
		boolean result =false;
		try{
			result = this.dataTblock_Tag.checkTblock_TagTA(tbez,wpnr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTblock_TagTA:");
			e.printStackTrace();			
		}
		return result;
	}

	public boolean deleteTblock_Tag(int tblocknr) {
		boolean result =false;
		try{
			result = this.dataTblock_Tag.deleteTblock_Tag(tblocknr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteTblock_Tag:");
			e.printStackTrace();			
		}
		return result;
	}
	public LinkedList<TerminBlockierung> getTerminBlockierungen(){
		
		LinkedList<TerminBlockierung> result = null;;
		try{
			result = this.dataTerminBlockierung.getTerminBlockierungen();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getTerminBlockierung:");
			e.printStackTrace();			
		}
		return result;
	}	
		

	public void addTerminBlockierung(TerminBlockierung terminblockierung){
		
		
		try{
			this.dataTerminBlockierung.addTerminBlockierung(terminblockierung);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addTerminBlockierung:");
			e.printStackTrace();			
		}
		
		
		
	}

	public boolean checkTerminBlockierung(int tblocknr) {
		boolean result =false;
		try{
			result = this.dataTerminBlockierung.checkTerminBlockierung(tblocknr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode checkTerminBlockierung:");
			e.printStackTrace();			
		}
		return result;
	}
	public boolean deleteTerminBlockierung(int tblocknr) {
		boolean result =false;
		try{
			result = this.dataTerminBlockierung.deleteTerminBlockierung(tblocknr);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode deleteTerminBlockierung:");
			e.printStackTrace();			
		}
		return result;
	}
	public  int getNewTblocknr() {
		int result = 0;
		try{
			result = this.dataTerminBlockierung.getNewTblocknr();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getNewTblocknr:");
			e.printStackTrace();			
		}
		return result;
	}
	public LinkedList<Warenhaus> getWarenhaus() {
		LinkedList<Warenhaus> result = null;;
		try{
			result = this.dataWarenhaus.getWarenhaus();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode getWarenhaus:");
			e.printStackTrace();			
		}
		return result;
	}	
		
	public void addWarenhaus(Warenhaus warenhaus){
		
		
		try{
			this.dataWarenhaus.addWarenhaus(warenhaus);
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode addWarenhaus:");
			e.printStackTrace();			
		}		
	}
	public void setzeBenutzerrolleAdmin() {
		try{
			this.dataUserrecht.setzeBenutzerrolleAdmin();
			
		}catch(Exception e){
			System.out.println("Fehler innerhalb des Modells:");
			System.out.println("Fehler beim Aufruf der Methode setzeBenutzerrolleAdmin:");
			e.printStackTrace();			
		}
	}
}


