/**
 * 
 */
package com.ztesoft.vsop.version;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author xulei  xu.lei55@zet.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class VersionCtrl {
	public static Properties property = null;
	private static final Logger log = Logger.getLogger(VersionCtrl.class);
	public static String path = "";

	public static void init() {
		try {
			property = new Properties();
			JarFile jarFile;
			String jarPath = path + "WEB-INF/lib/vsop.jar";
			log.info("############################################");
			log.info("初始化版本信息：");
			log.info(jarPath);

			jarFile = new JarFile(jarPath);
			Enumeration entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry entry = (JarEntry) entrys.nextElement();
				if ("META-INF/MANIFEST.MF".equals(entry.getName())) {
					InputStream is = jarFile.getInputStream(entry);
					property.load(is);
					is.close();
				}
			}
			jarFile.close();
			log.info("Version:" + property.getProperty("Version"));
			log.info("Build-Date:" + property.getProperty("Build-Date"));
			log.info("############################################");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回VSOP版本信息
	 * @return
	 */
	public static String getVersion(String apath) {
		path = apath;
		if (null == property) {
			init();
		}
		log.info("系统版本号是:" + property.getProperty("Version"));
		return property.getProperty("Version");
	}

	/**
	 * 返回VSOP构建时间
	 * @return
	 */
	public static String getBuildtime(String apath) {
		path = apath;
		if (null == property) {
			init();
		}
		log.info("系统版本构建时间是:" + property.getProperty("Build-Date"));
		return property.getProperty("Build-Date");
	}
}
