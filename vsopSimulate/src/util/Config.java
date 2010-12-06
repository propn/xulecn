package util;

import java.util.List;
import java.util.Map;

/**
 * 
 * <pre>
 * Title:配置
 * Description: 配置类
 * </pre>
 * @author caozj  cao.zhijun3@zet.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class Config {
	//报文模板文件名
	public static String templateFileName = "config/template_config.properties";
	//接口发送方式路径配置文件
	public static String serverSendTypeConfigFile = "config/server_send_type_config.properties";
	//系统配置文件
	public static String configFileName = "config/config.properties";
	//省份配置
	public static String provinceConfig = "config/province_config.properties";
	//模板文件路径
	public static String TEMPLATE_FILE_PATH = "template/";
	//公共模板路径
	public static String Public_template = "public/";
	//系统配置文件
	public static String systemConfig = "config/system_config.properties";
	//集团规范名
	public static String PUBLIC_TEMPTATE="集团";

	public static String SCENE_STR = "请选择场景";
	
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
	 * 初始化配置
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
	 * 返回配置文件
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
	 * 返回配置模板文件
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
	 * 返回省份列表
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
	 * 返回系统列表
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
