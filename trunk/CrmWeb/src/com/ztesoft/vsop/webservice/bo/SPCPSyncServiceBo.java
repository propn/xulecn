package com.ztesoft.vsop.webservice.bo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.vsop.order.SpProcess;
import com.ztesoft.vsop.order.PartnerServiceAblityProcess;
import com.ztesoft.vsop.web.RefreshCacheHttpClient;
/**
 * 
 * @author cooltan
 *
 */
public class SPCPSyncServiceBo {
	private static Logger logger = Logger.getLogger(SPCPSyncServiceBo.class);
	
	public static SPCPSyncServiceBo instance=new SPCPSyncServiceBo();
	
	public static SPCPSyncServiceBo getInstance(){
		return instance;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String CPSPInfoSync(String requestXml) throws Exception {
		long star = System.currentTimeMillis();
		logger.info("requestXml:"+requestXml);
//		2具体业务处理
    	Map cparam = new HashMap() ;
    	cparam.put("xml", requestXml) ;
		String responseXml = null ;
		Map map=null;
		try {
			 map = DataTranslate._Map(
					ServiceManager.callJavaBeanService(ServiceList.INTERFACE_CRMSPSynManager,
							"spcpInfoSyn" ,cparam));
			responseXml=(String)map.get("responseXml");
		} catch (Exception e) {
			logger.error("",e);
			e.printStackTrace();
			throw e;
		}
		//2.2缓存处理
		String partner_id=(String)map.get("partner_id");
		RefreshCacheHttpClient.getInstance().refreshAllServerCaches("2", partner_id);
		
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("CPSPInfoSync cost:"+processTime);
		logger.info("CPSPInfoSync XML:"+responseXml);
		return responseXml;
		
	}
	public String CPSPCapabilitySync(String requestXml) throws DocumentException, SQLException {
		long star = System.currentTimeMillis();
		String retXml = PartnerServiceAblityProcess.process(requestXml,"1");
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("CPSPCapabilitySync cost:"+processTime);
		
		return retXml;
	}

}
