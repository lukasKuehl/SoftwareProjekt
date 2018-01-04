package data;

/** 
 * @author Anes Preljevic
 * @info Das Interface Observable stellt  Funktionen zur Verfügung, um den Oberserver(Beobachter--> View) zu benachrichtigen,
 * registrieren und zu entfernen vom Beobachtbaren Objekt(Model).
 */

public interface Observable {
	public void register(Observer view, String benutzername, String passwort);
	public void removeObserver(Observer view);
	public void notifyObservers();
	
}
