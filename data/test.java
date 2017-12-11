package data;
import data.Ma_Schicht;
public class test {

	public static void main(String[] args) {
		Ma_Schicht ma= new Ma_Schicht("hans",23131);
		String wp=Integer.toString(ma.getSchichtnr());
		if(wp.length() ==4){
			System.out.println("NICEEE");
		}
		else{
			System.out.println("fker");
	}}
	
}
