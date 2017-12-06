package view;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

public class Einsatzplanview {

	private EinsatzplanController myController = null;
	private Einsatzplanmodel myModel = null;
	
	
	public Einsatzplanview(EinsatzplanController einsatzplanController, Einsatzplanmodel model) {
		this.myController = einsatzplanController;
		this.myModel = model;
	}



}
