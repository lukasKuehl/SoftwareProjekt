package model;
import java.sql.*;
import java.util.LinkedList;
import data.*;

/**
 * @author Anes Preljevic
 * @info Testen der Model Methoden
 */
public class TestModel {

	public static void main(String[] args) {
		Einsatzplanmodel wps = new Einsatzplanmodel();
		LinkedList<Wochenplan>  wpnns = wps.getWochenplaene();
		
		for(Wochenplan d:wpnns){
			
			System.out.println( d.get÷ffnungszeit() + "  " + d.getMinanzinfot()
			+ "  " + d.getMehrbesetzung()+ "  " + d.getSchlieﬂzeit()+ "  " + d.getHauptzeitbeginn());
		
		}

	}

}
