package fr.univ_lyon1.etu.ewide.util;

public class ExpressionValidator {

	public static boolean isInt(String value){
		
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return false;
		}
		
	}
}
