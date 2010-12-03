/**
 * @author cicl
 * @date
 * 
 */
package org.leixu.ioc.util;

import java.io.File;

/**
 * @author cicl
 *
 */
public class FileUtil {
	/**
	 * 获取file
	 * @param path
	 * @return
	 */
	public static File createFile(String path) {
		if (StringUtil.isEmpty(path)) {
			return null;
		}

		File file = null;
		file = new File(path);

		return file;
	}

	/**
	 * 判断文件是否存在
	 * @param path
	 * @return
	 */
	public static boolean fileExisted(String path) {
		if (StringUtil.isEmpty(path)) {
			return false;
		}
		return createFile(path).isFile();
	}

	/**
	 * 判断目录是否存在
	 * 
	 * @param path
	 * @return
	 * @creator chuchanglin @ 2009-10-21
	 */
	public static boolean dirExists(String path) {
		if (StringUtil.isEmpty(path)) {
			return false;
		}
		return new File(path).exists();
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @creator fangxiang @ May 8, 2009
	 */
	public static long fileLength(String fileName) {
		if (StringUtil.isEmpty(fileName)) {
			return 0;
		}

		File file = new File(fileName);
		return file.length();
	}

	/**
	 * 获取文件大小数组
	 */
	public static byte[] getSizeByByteArr(String path) {
		return new byte[NumberUtil.transLongToInt(createFile(path).length())];
	}

	/**
	 * 获取编辑路径
	 * @param sorPath
	 * @return
	 */
	public static String getSrcPath(String sorPath) {
		if (StringUtil.isEmpty(sorPath)) {
			return sorPath;
		} else {
			return File.separator + sorPath;
		}
	}
}
