package org.leixu.iap.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringUtils {

	public static String trace(Throwable t){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		pw.flush();
		String result = sw.toString();
		return result;
	}

	public static boolean empty(Object obj){
		return obj == null ||
			obj.toString().trim().length() == 0;
	}
}
