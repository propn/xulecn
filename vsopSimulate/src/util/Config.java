package util;

import java.util.List;
import java.util.Map;

/**
 * 
 * <pre>
 * Title:����
 * Description: ������
 * </pre>
 * @author caozj  cao.zhijun3@zet.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class Config {
	//����ģ���ļ���
	public static String templateFileName = "config/template_config.properties";
	//�ӿڷ��ͷ�ʽ·�������ļ�
	public static String serverSendTypeConfigFile = "config/server_send_type_config.properties";
	//ϵͳ�����ļ�
	public static String configFileName = "config/config.properties";
	//ʡ������
	public static String provinceConfig = "config/province_config.properties";
	//ģ���ļ�·��
	public static String TEMPLATE_FILE_PATH = "template/";
	//����ģ��·��
	public static String Public_template = "public/";
	//ϵͳ�����ļ�
	public static String systemConfig = "config/system_config.properties";
	//���Ź淶��
	public static String PUBLIC_TEMPTATE="����";

	public static String SCENE_STR = "��ѡ�񳡾�";
	
	public static String TestPlanConfigFilePath = "executeConfig/";

	public static Map configMap = null;

	public static Map templateMap = null;

	public static List templateList = null;

	public static List provinceList = null;

	public static Map provinceMap = null;

	public static Map systemMap = null;

	public static List systemList = null;

	public static Map sendTypeMap = null;

	public Config() {

	}

	/**
	 * ��ʼ������
	 *
	 */
	public static void init() {

		try {
			if(configMap==null){
				configMap = ProptiesFileRead.getProperties(configFileName);
			}

			templateMap = ProptiesFileRead.getProperties(templateFileName);

			templateList = ProptiesFileRead.getPropertiesKey(templateFileName);

			provinceList = ProptiesFileRead.getPropertiesKey(provinceConfig);

			provinceMap = ProptiesFileRead.getProperties(provinceConfig);

			systemList = ProptiesFileRead.getPropertiesKey(systemConfig);

			systemMap = ProptiesFileRead.getProperties(systemConfig);

			sendTypeMap = ProptiesFileRead.getProperties(serverSendTypeConfigFile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ���������ļ�
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		if (configMap == null) {
			init();
		}
		return (String) configMap.get(key);
	}

	/**
	 * ��������ģ���ļ�
	 * @param key
	 * @return
	 */
	public static String getTemplateConfig(String key) {
		if (templateMap == null) {
			init();
		}
		return (String) templateMap.get(key);
	}

	public static List getTemplateList() {
		if (templateList == null) {
			init();
		}
		return templateList;
	}

	/**
	 * ����ʡ���б�
	 * @return
	 */
	public static List getprovinceList() {
		if (provinceList == null) {
			init();
		}
		return provinceList;
	}

	public static String getProvinceCode(String provinceName) {
		if (provinceMap == null) {
			init();
		}
		return (String) provinceMap.get(provinceName);
	}

	/**
	 * ����ϵͳ�б�
	 */
	public static List getSystemList() {
		if (systemList == null) {
			init();
		}
		return systemList;
	}

	public static String getSystemCode(String systemName) {
		if (systemMap == null) {
			init();
		}
		return (String) systemMap.get(systemName);
	}

	public static String getSendType(String systemName) {
		if (sendTypeMap == null) {
			init();
		}
		return (String) sendTypeMap.get(systemName);
	}
	
	public static void  setConfig(String key,String value){
		configMap.put(key, value);
	}
}
