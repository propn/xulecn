package org.leixu.iap.core.util.loader;

public class Capitalizer implements ObjectLoader{
	public static Capitalizer instance = new Capitalizer();

	public Object load(Object path) {
		return capitalize((String)path);
	}

	public static String capitalize(String str){
		if(str == null || str.length() < 1) return str;
		char firstChar = str.charAt(0);
		str = Character.toUpperCase(firstChar) + str.substring(1);
		return str;
	}
}
