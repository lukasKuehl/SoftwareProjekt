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
		//Wochenplan test
		LinkedList<Wochenplan>  wpnns = wps.getWochenplaene();
		
		for(Wochenplan d:wpnns){
			
			System.out.println(d.getWpnr()+" "+ d.get�ffnungszeit() + "  " + d.getMinanzinfot()
			+ "  " + d.getMehrbesetzung()+ "  " + d.getSchlie�zeit()+ "  " + d.getHauptzeitbeginn());
			LinkedList<Tag> wptage=d.getLl_tag();
			
			for(Tag twp: wptage){
				
				System.out.println( twp.getTbez() + "  " + twp.getWpnr());
			
			}
		
		}
		Wochenplan wp = wps.getWochenplan(testwp);
		System.out.println(wp.getWpnr()+" "+ wp.get�ffnungszeit() + "  " + wp.getMinanzinfot()
		+ "  " + wp.getMehrbesetzung()+ "  " + wp.getSchlie�zeit()+ "  " + wp.getHauptzeitbeginn());
		//wp.set�ffentlichstatus(false);
		int nextnr = wps.getNewWpnr();
		System.out.println( nextnr);
		//wps.oeffentlichStatusaendern(wp);
		//wps.oeffentlichStatusfalse(testwp);
		wps.oeffentlichStatustrue(testwp);
		boolean checkwp = wps.checkWochenplan(testwp);
		if(checkwp==true){
			System.out.println("success");
		}
		//boolean deletewp=wps.deleteWochenplan(testwp);
		//if(deletewp==true){
			//System.out.println("success delete Wochenplan");
		//}
		//else{
			//System.out.println("fail delete Wochenplan");
			
		//}
		//Standardeinstellungen test
		Standardeinstellungen std = wps.getStandardeinstellungen();
		System.out.println(std.get�ffnungszeit() + "  " + std.getMinanzinfot()
		+ "  " + std.getMehrbesetzung()+ "  " + std.getSchlie�zeit()+ "  " + std.getHauptzeitbeginn());
		int mhrb=10;
		//std.setMehrbesetzung(mhrb);
		//wps.updateStanadardeinstellungen(std);
		//Mitarbeiter test
			LinkedList<Mitarbeiter> ma1= wps.getAlleMitarbeiter();
			for(Mitarbeiter ma:ma1){
				
				System.out.println( ma.getEmail() + "  " + ma.getPasswort()+ "   " + ma.getBenutzername()
				+ "  " + ma.getJob()+ "  " + ma.getMaxstunden());
			
			}
			String benutzername6="Cfriese";
			Mitarbeiter ma44=wps.getMitarbeiter(benutzername6);
			System.out.println( ma44.getEmail() + "  " + ma44.getPasswort()+ "   " + ma44.getBenutzername()
			+ "  " + ma44.getJob()+ "  " + ma44.getMaxstunden());
			String wbrl="Fhimmelmann";
			wps.wechselBenutzerrolle(wbrl);
			
		//Tag test
		LinkedList<Tag>  tags = wps.getTage();
		
		for(Tag t:tags){
			
			System.out.println( t.getTbez() + "  " + t.getWpnr()+ "   " + t.isFeiertag());
			LinkedList<Schicht> schichttag=t.getLl_Schicht();
			
			for(Schicht scht:schichttag){
				
				System.out.println( scht.getTbez() + "  " + scht.getWpnr()+ "   " +scht.getSchichtnr()
				+" "+ scht.getAnfanguhrzeit());
			
			}
			LinkedList<Tblock_Tag> tbttag22=t.getLl_Tblocktag();
			
			for(Tblock_Tag tbtt:tbttag22){
				
				System.out.println(tbtt.getTbez() + "  " + tbtt.getWpnr()+ "   " +tbtt.getTblocknr());
			
			}

		}
		System.out.println("f�r wochenplan");
		int tgwp=1000;
		LinkedList<Tag> tagef�rwp=wps.getTagewp(tgwp);
		for(Tag t0:tagef�rwp){
			
			System.out.println( t0.getTbez() + "  " + t0.getWpnr()+ "   " + t0.isFeiertag());

		}
		String t1="Tag1";
		int wptag=1000;
		//wps.setzeFeiertagtrue(t1,wptag);
		wps.setzeFeiertagfalse(t1,wptag);
		boolean testtag = wps.checkTag(t1, wptag);
		if(testtag==true){
			System.out.println("successtag");
		}
		else{
			System.out.println("failtag");
		}
		//boolean deletetag=wps.deleteTag(wptag);
		//if(deletetag==true){
		//	System.out.println("success deleteTag------------");
		//}
		//else{
			//System.out.println("fail deleteTag--------------");
			
		//}
		//Schicht Test
		LinkedList<Schicht>  sch = wps.getSchichten();
		for(Schicht scht:sch){
			
			System.out.println( scht.getAnfanguhrzeit() + "  " + scht.getEndeuhrzeit()+ "   " + scht.getSchichtnr());
			LinkedList<Ma_Schicht> masch=scht.getLl_maschicht();
			
			for(Ma_Schicht sch1:masch){
				
				System.out.println( sch1.getSchichtnr()+" "+ sch1.getBenutzername());
			
			}
		}
		Schicht schicht0 =wps.getSchicht(ss);
		System.out.println( schicht0.getAnfanguhrzeit() + "  " + schicht0.getEndeuhrzeit()+ "   " + schicht0.getSchichtnr());
		LinkedList<Ma_Schicht> masch22=schicht0.getLl_maschicht();
		
		for(Ma_Schicht sch1:masch22){
			
			System.out.println( sch1.getSchichtnr()+" "+ sch1.getBenutzername());
		
		}
		boolean test22 = wps.checkSchicht(ss);
		if(test22==true){
			System.out.println("success");
		}
		else{
			System.out.println("fail");
		}
		int nextscnr = wps.getNewSchichtnr();
		System.out.println( nextscnr);
		int wpnrsch=1000;
		//boolean delete33=wps.deleteSchicht(wpnrsch);
		//if(delete33==true){
			//System.out.println("success delete schicht------------");
		//}
		//else{
			//System.out.println("fail delete schicht--------------");
			
		//}
		//Ma_Schicht
		int schichtnr=10001;
		LinkedList<Ma_Schicht> masch1 =wps.getMitarbeiterausderSchicht(schichtnr);
		for(Ma_Schicht mas1:masch1){
			
			System.out.println( mas1.getSchichtnr() + "  " + mas1.getBenutzername());
		
		}
		String b2="Gschmidt";
		LinkedList<Ma_Schicht> masch2 =wps.getSchichteneinesMitarbeiters(b2);
		for(Ma_Schicht mas2:masch2){
			
			System.out.println( mas2.getSchichtnr() + "  " + mas2.getBenutzername());
		
		}
		LinkedList<Ma_Schicht> masch3 =wps.getMa_Schicht();
		for(Ma_Schicht ma3:masch3){
			
			System.out.println( ma3.getSchichtnr() + "  " + ma3.getBenutzername());
		
		}
		boolean test = wps.checkMa_Schicht(ss, b);
		if(test==true){
			System.out.println("success");
		}
		else{
			System.out.println("fail");
		}
		//boolean delete=wps.deleteMa_Schicht(ss,b);
		//if(delete==true){
			//System.out.println("success delete maschicht");
		//}
		//else{
			//System.out.println("fail delete maschicht");
			
		//}
		//int schichtnr22=10001;
		//boolean delete2=wps.deleteMa_SchichtWochenplan(schichtnr22);
		//if(delete2==true){
		//	System.out.println("success delete maschicht");
		//}
		//else{
			//System.out.println("fail delete maschicht");
			
		//}
		//Tauschanfrage test
		int tauschnr=100000;
		boolean testtauscha = wps.checkTauschanfrage(tauschnr);
		if(testtauscha==true){
			System.out.println("successtauschanfrage");
		}
		else{
			System.out.println("failtauschanfrage");
		}
		wps.best�tigeTauschanfrage(tauschnr);
		
		LinkedList<Tauschanfrage> tauschList =wps.getTauschanfragen();
		for(Tauschanfrage tausch:tauschList){
			
			System.out.println( tausch.getTauschnr() + "  " + tausch.getSender()+ "  " + tausch.getEmpf�nger());
			
			LinkedList<Mitarbeiter> masend=tausch.getLl_Sender();
			
			for(Mitarbeiter masender:masend){
				
				System.out.println( masender.getEmail() + "  " + masender.getPasswort()+ "   " + masender.getBenutzername()
				+ "  " + masender.getJob()+ "  " + masender.getMaxstunden());
			
			}
			LinkedList<Mitarbeiter> masempf=tausch.getLl_Empf�nger();
			
			for(Mitarbeiter masender:masempf){
				
				System.out.println( masender.getEmail() + "  " + masender.getPasswort()+ "   " + masender.getBenutzername()
				+ "  " + masender.getJob()+ "  " + masender.getMaxstunden());
			
			}
			LinkedList<Schicht> schichtsend=tausch.getLl_Schichtsender();
			
			for(Schicht scht:schichtsend){
				
				System.out.println(scht.getAnfanguhrzeit() + "  " + scht.getEndeuhrzeit()+ "   " + scht.getSchichtnr());
			
			}
			LinkedList<Schicht> schichtempf=tausch.getLl_Schichtempf�nger();
			
			for(Schicht scht:schichtempf){
				
				System.out.println(scht.getAnfanguhrzeit() + "  " + scht.getEndeuhrzeit()+ "   " + scht.getSchichtnr());
			
			}
		
		}
		int tauschnr223=100000;
		Tauschanfrage ttsingle=wps.getTauschanfrage(tauschnr223);
		System.out.println( ttsingle.getTauschnr() + "  " + ttsingle.getSender()+ "  " + ttsingle.getEmpf�nger());
		
		//boolean deletetausch=wps.deleteTauschanfrage(tauschnr223);
		//if(deletetausch==true){
		//	System.out.println("success delete taauschanfrage");
		//}
		//else{
		//	System.out.println("fail delete Tauschanfrage");
			
		//}
		int nexttauschnr = wps.getNewTauschnr();
		System.out.println( nexttauschnr);
		//Test Tblock_tAg
		int tblocknr=100;
		boolean tblockt = wps.checkTblock_TagTB(tblocknr);
		if(tblockt==true){
			System.out.println("successtblocktag");
		}
		else{
			System.out.println("failtblocktag");
		}
		String tbb="Tag1";
		int tbwpnr=1000;
		boolean tblockt2 = wps.checkTblock_TagTA(tbb,tbwpnr);
		if(tblockt2==true){
			System.out.println("successtblocktag");
		}
		else{
			System.out.println("failtblocktag");
		}
		LinkedList<Tblock_Tag> tballe=wps.getAlleTblock_Tag();
		for(Tblock_Tag tb1:tballe){
			System.out.println(tb1.getTblocknr()+" "+tb1.getTbez()+" "+tb1.getWpnr());
		}
		int tbnr2=100;
		int tbnr3=1001;
		String tbstr="Samstag";
		Tblock_Tag tb2=wps.getTblock_TagTB(tbnr2);
		System.out.println(tb2.getTblocknr()+" "+tb2.getTbez()+" "+tb2.getWpnr());
		Tblock_Tag tb3=wps.getTblock_TagT(tbstr, tbnr3);
		System.out.println(tb3.getTblocknr()+" "+tb3.getTbez()+" "+tb3.getWpnr());
		//boolean deletetbt=wps.deleteTblock_Tag(tbnr3);
		//if(deletetbt==true){
		//	System.out.println("success delete tbt");
		//}
	    //else{
		//	System.out.println("fail delete Tbt");
			
		//}
		//TerminBlockierung test
		int tb0=100;
		boolean testtb0 = wps.checkTerminBlockierung(tb0);
		if(testtb0==true){
			System.out.println("success tb");
		}
		else{
			System.out.println("fail tb");
		}
		
		
		LinkedList<TerminBlockierung> tblockList =wps.getTerminBlockierungen();
		for(TerminBlockierung tb01:tblockList){
			
			System.out.println( tb01.getTblocknr() + "  " + tb01.getBenutzername()+ "  " + tb01.getAnfangzeitraum());

		
		}

		int delettb0=101;
		//boolean delettb=wps.deleteTerminBlockierung(delettb0);
		//if(delettb==true){
			//System.out.println("success delete tb");
		//}
		//else{
			//System.out.println("fail delete tb");
			
		//}
		int nexttbnr = wps.getNewTblocknr();
		System.out.println( nexttbnr);
		//Test Userrecht
		LinkedList<Userrecht> userrList=wps.getUserrecht();
		for(Userrecht ur:userrList){
			System.out.println(ur.getJob()+" "+ur.getBenutzerrolle());
		}
		//Test Warenhaus
		LinkedList<Warenhaus> whList=wps.getWarenhaus();
		for(Warenhaus wh:whList){
			System.out.println(wh.getWhname()+" "+wh.getAnzkasse()+" "+wh.getAnzinfo());
		}
	}

}
