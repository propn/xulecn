package com.ztesoft.vsop.webservice.bo;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.ztesoft.vsop.order.ProductProcess;
import com.ztesoft.vsop.order.ProductOfferProcess;
import com.ztesoft.vsop.order.ProductOfferCRMProcess;
import com.ztesoft.vsop.order.SpProcess;

/**
 * 
 * @author cooltan
 * 
 */

public class ProdOfferServiceSyncBo {
	private static Logger logger = Logger
			.getLogger(ProdOfferServiceSyncBo.class);

	public static ProdOfferServiceSyncBo instance = new ProdOfferServiceSyncBo();

	public static ProdOfferServiceSyncBo getInstance() {
		return instance;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String productInfoSync(String requestXml) {
		String responseXml = null;
		long star = System.currentTimeMillis();
		logger.info("productInfoSync requestXml:" + requestXml);
		// 具体业务处理.......
		try {
			responseXml = ProductProcess.process(requestXml, "1");
		} catch (Exception ex) {
			logger.error("productInfoSync business error:", ex);
		}

		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("productInfoSync responseXml:" + responseXml);
		logger.info("productInfoSync cost:" + processTime);
		return responseXml;

	}

	public String packageInfoSync(String requestXml) throws DocumentException,
			SQLException {
		long star = System.currentTimeMillis();

		String retXml = ProductOfferProcess.process(requestXml, "1");

		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("packageInfoSync cost:" + processTime);
		logger.info("packageInfoSync XML:" + retXml);
		return retXml;

	}

	public String PPOfferInfoSync(String requestXml) throws DocumentException,
			SQLException {
		long star = System.currentTimeMillis();

		String retXml = ProductOfferCRMProcess.process(requestXml, "1");

		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("PPOfferInfoSync cost:" + processTime);
		logger.info("PPOfferInfoSync XML:" + retXml);
		return retXml;

	}
	

}
