package model;

import data.Wochenplan;

//Klasse gehört in das Package data --> Allgemeine Klasse

public interface Observer {
	public void update(Wochenplan wochenplan);
}
