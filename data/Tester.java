package data;

import controller.EinsatzplanController;
import model.Einsatzplanmodel;

public class Tester {

	public static void main(String[] args){
		
		new EinsatzplanController(new Einsatzplanmodel());
		System.out.println("Test erfolgreich!");
		
	}
	
	
	
}
