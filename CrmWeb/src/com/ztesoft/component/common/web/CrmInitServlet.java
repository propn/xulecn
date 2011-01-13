package com.ztesoft.component.common.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.powerise.ibss.util.SysSet;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.component.common.staticdata.StaticAttrCache;

public class CrmInitServlet extends HttpServlet {

	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CrmInitServlet.class);

	public void init() throws ServletException {
//		initLogger();
		String file_sepa = System.getProperty("file.separator");
		String web_inf_path = getServletContext().getRealPath("/") + file_sepa
				+ "WEB-INF" + file_sepa;
		System.setProperty("WEB_INF_PATH", web_inf_path);
		CrmConstants.WEB_INF_PATH = web_inf_path;
		logger.debug("-----------------1111111111111111112222222222222222222222222");
		// init log4j
		// org.apache.log4j.xml.DOMConfigurator.configure(CrmConstants.WEB_INF_PATH+"classes"+file_sepa+"log4j.xml");
		// org.apache.log4j.PropertyConfigurator.configure(CrmConstants.WEB_INF_PATH+"classes"+file_sepa+"log4j.properties");
	/*	BrandCache caches = new BrandCache();
		caches.loadData();*/
		// ��ʼ��
		try {

			// ���ò�������
			CrmParamsConfig.getInstance().initParams(CrmConstants.WEB_INF_PATH);
			SysSet.initSystem(3);//web + app 
		} catch (Exception e) {
			logger.error("���ò�������ʧ��: ", e);
		}
		try {
			// ��̬��������
			StaticAttrCache.getInstance().initStaticAttr();

		} catch (Exception e) {
			logger.error("��̬��������ʧ��: ", e);
		}
		
		
//		try {
//
//			// ��ʼ��Service����������
//			ServiceLibrary.getInst().initlize();
//
//		} catch (Exception e) {
//			logger.error("��ʼ��Service����������ʧ��", e);
//		}
		try {

			// ��ʼ��ҳ��Ԫ����������
//			PageTagUtils.getInstance().initlize();

		} catch (Exception e) {
			logger.error("��ʼ��Service����������ʧ��", e);
		}


	}
	
	/**
	 * ���񷽷�
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	public void initLogger() {
		InputStream is = CrmInitServlet.class.getResourceAsStream("/log4j.properties");
		Properties prop = new Properties();
		try {
			prop.load(is);
			PropertyConfigurator.configure(prop);
		} catch (IOException e) {
			try {
				is.close();
				is = null;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				is.close();
				is = null;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
