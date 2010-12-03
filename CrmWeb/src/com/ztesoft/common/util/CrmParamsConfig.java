package com.ztesoft.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.LogHelper;
//import com.ztesoft.oaas.common.socketservice.AuthException;
//import com.ztesoft.oaas.common.socketservice.AuthService;
//import com.ztesoft.oaas.common.socketservice.StructPackage;

public class CrmParamsConfig {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CrmParamsConfig.class);
	private Properties params = new Properties();
//	private static String FILENAME = "crm_params.properties";
	private static CrmParamsConfig paramsConfig = null;
	
	public CrmParamsConfig(){		
	}
	
	/**
	 * ��ȡʵ������
	 * @return
	 */
	public static CrmParamsConfig getInstance(){
		if(paramsConfig == null){
			paramsConfig = new CrmParamsConfig();
		}
		return paramsConfig;
	}
	
	/**
	 * ��ʼ����������ϵͳ��ʼ��ʱ���롣���߶�ʱ����ˢ�¡�
	 * @param path
	 */
	public void initParams(String path){
		
//		try {
			try {
				params = ConfigFileHelper.getCrmParamConfigFile();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//trim()
			Enumeration enu = params.keys();
			String key = "";
			String val = "";
			while(enu.hasMoreElements()){
				key = (String)enu.nextElement();
				val = params.getProperty(key);
				if(val!=null && !"".equals(val)){
					params.put(key, val.trim());
				}
			}
			
			//����ˢ���ض��ĳ�����
			if (null != params.getProperty("MAX_UPLOAD_SIZE")) {
				CrmConstants.setMaxUploadSize(params.getProperty("MAX_UPLOAD_SIZE"));
			}
			if (null != params.getProperty("NOT_FILTER_PAFGES")) {
				CrmConstants.setNotFilterPages(params.getProperty("NOT_FILTER_PAFGES"));
			}
			if (null != params.getProperty("SHOW_SQL")) {
				CrmConstants.SHOW_SQL = (String)params.getProperty("SHOW_SQL");
			}
			if (null != params.getProperty("SHOW_METHOD_TIME")) {
				CrmConstants.SHOW_METHOD_TIME = (String)params.getProperty("SHOW_METHOD_TIME");
			}
			if (null != params.getProperty("MAX_QUERY_SIZE")) {
				CrmConstants.setMaxQuerySize(params.getProperty("MAX_QUERY_SIZE"));
			}
			
			//ͨ����Ȩ����������ݿ�����
//			if (null != params.getProperty("DirectDBPwd")) {
//				
//				String directDBPwd2 = params.getProperty("DirectDBPwd");
//				StructPackage reqPack = new StructPackage("EncryptRequest");
//		        reqPack.setRepStructName("EncryptRespond");
//		        
//		        reqPack.addString("flag", "F");
//		        reqPack.addString("proclaimedBuff", "");
//		        reqPack.addString("secretBuff", params.getProperty("DirectDBPwd"));
//		        
//		        StructPackage resPack = new StructPackage("EncryptRespond");
//				try {
//					resPack = AuthService.getInstance().sendRequest(reqPack);
//					directDBPwd2 = resPack.getString("strResultBuff");
//				} catch (AuthException e) {
//					e.printStackTrace();
//				}
//				
//				params.setProperty("DirectDBPwd",directDBPwd2);
//			}
			
//		} catch (FileNotFoundException e) {
//			logger.error("file not found: "+path+ "  " + e);
//			return;
//		} catch (IOException e) {
//			logger.error("can't init config file: "+path+ "  " + e);
//			return;
//		}		
		
	}
	
	/**
	 * ��ȡ����ֵ
	 * @param name ��������
	 * @return
	 */
	public String getParamValue(String name){
		return params.getProperty(name);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CrmParamsConfig.getInstance().initParams("D:/workspaces/crmworkspace/VsopWeb/VsopWeb/WEB-INF/");
 
		
	}
	
	/*
	 * �����ڴ��е�ϵͳ����
	 */
	public void updateProperty(String paramCode) throws Exception{
			Properties tempProperty = ConfigFileHelper.getCrmParamConfigFile();
			Enumeration enu = tempProperty.keys();
			String key = "";
			String val = "";
			while(enu.hasMoreElements()){
				key = (String)enu.nextElement();
				val = tempProperty.getProperty(key);
				if(val!=null && !"".equals(val)){
					tempProperty.put(key, val.trim());
				}
			}
			String paramValue= tempProperty.getProperty(paramCode);
			//������������������ȥˢ�²�������Ϣ
			if(paramValue!=null){
				params.setProperty(paramCode,paramValue);
			}
	}

}
