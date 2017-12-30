package model;

//Sollte gelöscht werden!

public class testzeitraum {
	public static void main(String[] args){
 String anfangzeitraum="KW1001, Montag";
 String endezeitraum="KW1001, Donnerstag";
 String array[] = anfangzeitraum.split(",");{
 for (int i=0; i<array.length; i++){
     array[i].trim();
     
 }
 }
 	System.out.println(array[0]+"     "+array[1]);
	}
}
