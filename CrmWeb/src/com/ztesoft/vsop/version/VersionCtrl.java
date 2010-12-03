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
 * Title:�������������
 * Description: �����ܵ����� 
 * </pre>
 * @author xulei  xu.lei55@zet.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
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
			log.info("��ʼ���汾��Ϣ��");
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
	 * ����VSOP�汾��Ϣ
	 * @return
	 */
	public static String getVersion(String apath) {
		path = apath;
		if (null == property) {
			init();
		}
		log.info("ϵͳ�汾����:" + property.getProperty("Version"));
		return property.getProperty("Version");
	}

	/**
	 * ����VSOP����ʱ��
	 * @return
	 */
	public static String getBuildtime(String apath) {
		path = apath;
		if (null == property) {
			init();
		}
		log.info("ϵͳ�汾����ʱ����:" + property.getProperty("Build-Date"));
		return property.getProperty("Build-Date");
	}
}
