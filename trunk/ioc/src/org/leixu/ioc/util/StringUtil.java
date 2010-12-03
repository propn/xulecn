/**
 * @author cicl
 * @date
 * 
 */
package org.leixu.ioc.util;

/**
 * @author cicl
 *
 */
public class StringUtil {
	public static boolean isEmpty(String value) {
		return (null == value) || ("".equals(value));
	}

	/**
	 * 判断字符串的编码
	 *
	 * @param str
	 *            字符串编
	 * @return
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				// String s = encode;
				return encode;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				// String s1 = encode;
				return encode;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				// String s2 = encode;
				return encode;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				// String s3 = encode;
				return encode;
			}
		} catch (Exception exception3) {
		}
		return "";
	}
}
