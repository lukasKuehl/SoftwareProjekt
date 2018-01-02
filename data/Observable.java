package data;

//Klasse gehört in das Package data --> Allgemeine Klasse

public interface Observable {
	public void register(Observer view, String benutzername, String passwort);
	public void removeObserver(Observer view);
	public void notifyObservers();
	
}
