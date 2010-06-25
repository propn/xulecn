package org.leixu.iap.core.util.loader;

public class DBNameMaker implements ObjectLoader{
	public Object load(Object path) {
		return (String)makeDbName((String)path);
	}

	/**
	 * 
	 * @param src
	 * @return
	 */
	public static String makeDbName(String src){
		if(src == null) return null;

		char[] chars = src.toCharArray();
		int nChars = chars.length;

		StringBuffer buf = new StringBuffer();
		boolean toUpper = false;

		int lastUpperIndex = -1;

		for (int i = 0; i < nChars; i++) {
			char currentChar = chars[i];

			if(lastUpperIndex + 1 != i 
				&& Character.isUpperCase(currentChar)){
				
				lastUpperIndex = i;
				buf.append("_" + Character.toLowerCase(currentChar));
			}else{
				buf.append(Character.toLowerCase(currentChar));
			}
		}

		return buf.toString();
	}

}
