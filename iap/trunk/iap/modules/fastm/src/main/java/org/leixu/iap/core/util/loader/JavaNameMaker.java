package org.leixu.iap.core.util.loader;

public class JavaNameMaker implements ObjectLoader{
	public static final JavaNameMaker instance = new JavaNameMaker(); 
	public Object load(Object path) {
		return makeJavaName((String)path);
	}

	/**
	 * transfer "invoice_id" to "invoiceId"
	 * @param src
	 * @return
	 */
	public static String makeJavaName(String src) {
		if(src == null) return null;
		src = src.trim();
		if(src.indexOf("_") < 0) return src;

		char[] chars = src.toCharArray();
		int nChars = chars.length;

		StringBuffer buf = new StringBuffer();
		boolean toUpper = false;

		for (int i = 0; i < nChars; i++) {
			char currentChar = chars[i];

			if (currentChar == '_') {
				toUpper = true;
			}
			else {
				if (toUpper) {
					currentChar = Character.toUpperCase(currentChar);
					toUpper = false;
				}

				buf.append(currentChar);
			}
		}

		return buf.toString();
	}
}
