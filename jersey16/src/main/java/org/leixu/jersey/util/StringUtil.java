package org.leixu.jersey.util;

public class StringUtil {
	
	public static boolean isBlank(String str) {
		if (null == str || str.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
