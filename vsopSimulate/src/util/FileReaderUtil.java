/**
 * 
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author caozj  caozj@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FileReaderUtil {
	public static String readFileText(String fileName) throws Exception {
		//		 TODO Auto-generated method stub
		BufferedReader reader = null;

		StringBuffer buffer = new StringBuffer();
		try {
			File file = new File(Config.TEMPLATE_FILE_PATH + fileName);
			InputStream is = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(is, "GBK"));
			String line = reader.readLine();
			while (line != null) {
				//line =new String(line.getBytes("ISO8859-1"), "UTF-8");
				buffer.append(line);
				buffer.append("\n");
				line = reader.readLine();
			
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return buffer.toString();

	}

	public static String readSceneFileText(String fileName) throws Exception {
		//		 TODO Auto-generated method stub
		BufferedReader reader = null;

		StringBuffer buffer = new StringBuffer();
		try {
			File file = new File(fileName);
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				//line =new String(line.getBytes("ISO8859-1"), "UTF-8");
				buffer.append(line);
				buffer.append("\n");
				line = reader.readLine();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return buffer.toString();

	}
}
