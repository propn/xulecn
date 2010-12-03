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
public class NumberUtil {
	/**
	 * 将int转换为long
	 * @param value
	 * @return
	 */
	public static long transIntToLong(int value) {
		return new Integer(value).longValue();
	}

	/**
	 * 将long转换为int
	 * @param value
	 * @return
	 */
	public static int transLongToInt(long value) {
		return new Long(value).intValue();
	}
}
