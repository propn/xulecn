/**
 * 
 */
package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * <pre>
 * Title:写properties文件
 * Description: 程序功能的描述 
 * </pre>
 * @author caozj  cao.zhijun3@zet.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ProptiesFileWriter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProptiesFileWriter.writeProperties("G:\\CRMworkspace\\test.paroperties", "你好", "ddddddddddd");
	}

	public static void writeProperties(String filePath, String parameterName, String parameterValue) {
		Properties prop = new Properties();
		InputStream fis = null;
		OutputStream fos = null;
		try {
			fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			String pn = new String(parameterName.getBytes("GBK"), "ISO-8859-1");
			String pv = new String(parameterValue.getBytes("GBK"), "ISO-8859-1");
			fos = new FileOutputStream(filePath);
			prop.setProperty(pn, pv);

			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (Exception e) {

		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void deleteProperties(String filePath, String parameterName) {
		Properties prop = new Properties();
		InputStream fis = null;
		OutputStream fos = null;
		try {
			fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			String pn = new String(parameterName.getBytes("GBK"), "ISO-8859-1");

			fos = new FileOutputStream(filePath);
			prop.remove(pn);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (Exception e) {

		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
