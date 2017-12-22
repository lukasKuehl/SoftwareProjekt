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
		int testwp=1000;
		int ss=10000;
		String b="Kmuster";
		Einsatzplanmodel wps = new Einsatzplanmodel();
		LinkedList<Wochenplan>  wpnns = wps.getWochenplaene();
		
		for(Wochenplan d:wpnns){
			
			System.out.println(d.getWpnr()+" "+ d.get÷ffnungszeit() + "  " + d.getMinanzinfot()
			+ "  " + d.getMehrbesetzung()+ "  " + d.getSchlieﬂzeit()+ "  " + d.getHauptzeitbeginn());
		
		}
		Wochenplan wp = wps.getWochenplan(testwp);
		System.out.println(wp.getWpnr()+" "+ wp.get÷ffnungszeit() + "  " + wp.getMinanzinfot()
		+ "  " + wp.getMehrbesetzung()+ "  " + wp.getSchlieﬂzeit()+ "  " + wp.getHauptzeitbeginn());
	
	
		LinkedList<Tag>  tags = wps.getTage();
		
		for(Tag t:tags){
			
			System.out.println( t.getTbez() + "  " + t.getWpnr()+ "   " + t.isFeiertag());
		
		}
		LinkedList<Schicht>  sch = wps.getSchichten();
		
		for(Schicht scht:sch){
			
			System.out.println( scht.getAnfanguhrzeit() + "  " + scht.getEndeuhrzeit()+ "   " + scht.getSchichtnr());
		
		}
		boolean test = wps.checkMa_Schicht(ss, b);
		if(test==true){
			System.out.println("success");
		}
		else{
			System.out.println("fail");
		}
		boolean delete=wps.deleteMa_Schicht(ss,b);
		if(delete==true){
			System.out.println("success delete maschicht");
		}
		else{
			System.out.println("fail delete maschicht");
			
		}
		int nextnr = wps.getNewWpnr();
		
		
		System.out.println( nextnr);

	}

}
