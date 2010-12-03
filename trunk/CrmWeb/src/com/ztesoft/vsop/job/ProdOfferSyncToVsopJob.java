package com.ztesoft.vsop.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.quartz.JobDataMap;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.component.job.IJobTrans;
import com.ztesoft.component.job.JobTransaction;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.RefreshCacheHttpClient;

/**
 * 江西本地
 * 增值销售品同步
 * 从crm的ftp获取增值销售品文件，同步到VSOP系统
 * crm的增值销售品是ismp那边同步过来的
 * @author yi.chengfeng 2010-7-30
 *
 */
public class ProdOfferSyncToVsopJob extends JobTransaction {
	
	private static Logger logger = Logger.getLogger(ProdOfferSyncToVsopJob.class);

	public void process() {
		logger.info("ProdOfferSyncToVsopJob start.");
		//处理销售品
		handleOfferInfo();
		//处理纯增值捆绑销售品
		handlePProdOfferInfo();
		logger.info("ProdOfferSyncToVsopJob end.");
	}

	private void handlePProdOfferInfo() {
		FTPClient ftp = null;
		FTPHelper ftphelper = new FTPHelper();
		
		try {
			//登录ftp
			ftp = ftphelper.connectCrmFtp();
			String absolutePath = DcSystemParamManager.getParameter(VsopConstants.CRM_FTP_ISMP_POFFER_DIR);
			String absolutePathBackup = DcSystemParamManager.getParameter(VsopConstants.CRM_FTP_ISMP_POFFER_BACKUP_DIR);
//			String absolutePath = "/home/csss/infismp/ftp/back/PRODINFO/15";
			String[] fileNames = ftp.listNames(absolutePath);
			ftp.disconnect();
			List shouldHandleFiles = new ArrayList();
			for (int i = 0; fileNames != null && i < fileNames.length; i++) {
				String name = fileNames[i];
				logger.info("file ->" + name);
				//获取前一天的文件
				if(isValidatePProdOfferFile(name)){
					shouldHandleFiles.add(name);
				}
			}
			if(shouldHandleFiles.size() == 0){
				logger.info("no file for process,exit.");
				logStart("","no file for process", "poffer");
				return;
			}
			for (Iterator iterator = shouldHandleFiles.iterator(); iterator
					.hasNext();) {
				String fileName = (String) iterator.next();
				logger.info("process file -> " + fileName);
				//同步增值产品

				ftp = ftphelper.connectCrmFtp();
				boolean success = syncPOffer(fileName,ftp);
				ftp.disconnect();
				
//				if(success){
					ftp = ftphelper.connectCrmFtp();
					String to = absolutePathBackup + "/" + getFileNameFromFullFileName(fileName);
					ftp.rename(fileName, to );
					ftp.disconnect();
//				}
			}
			
		} catch (NumberFormatException e) {
			logger.error("", e);
		} catch (SocketException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}finally{
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void handleOfferInfo() {
		FTPClient ftp = null;
		FTPHelper ftphelper = new FTPHelper();
		try {
			//登录ftp
			ftp = ftphelper.connectCrmFtp();
			String absolutePath = DcSystemParamManager.getParameter(VsopConstants.CRM_FTP_ISMP_OFFER_DIR);
			String absolutePathBackup = DcSystemParamManager.getParameter(VsopConstants.CRM_FTP_ISMP_OFFER_BACKUP_DIR);
//			String absolutePath = "/home/csss/infismp/ftp/back/PRODINFO/15";
			String[] fileNames = ftp.listNames(absolutePath);
			ftp.disconnect();
			List shouldHandleFiles = new ArrayList();
			for (int i = 0; fileNames != null && i < fileNames.length; i++) {
				String name = fileNames[i];
				logger.info("file ->" + name);
				//获取前一天的文件
				if(isValidateOfferInfoFile(name)){
					shouldHandleFiles.add(name);
				}
			}
			if(shouldHandleFiles.size() == 0){
				logger.info("no file for process,exit.");
				logStart("","no file for process", "offer");
				return;
			}
			for (Iterator iterator = shouldHandleFiles.iterator(); iterator
					.hasNext();) {
				String fileName = (String) iterator.next();
				logger.info("process file -> " + fileName);
				//同步增值产品

				ftp = ftphelper.connectCrmFtp();
				boolean success = syncOffer(fileName,ftp);
				ftp.disconnect();
				
//				if(success){
					ftp = ftphelper.connectCrmFtp();
					String to = absolutePathBackup + "/" + getFileNameFromFullFileName(fileName);
					ftp.rename(fileName, to );
					ftp.disconnect();
//				}
			}
			
		} catch (NumberFormatException e) {
			logger.error("", e);
		} catch (SocketException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}finally{
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getFileNameFromFullFileName(String fileName) {
		int lastslash = fileName.lastIndexOf("/");
		if(lastslash != -1) return fileName.substring(lastslash+1);
		return fileName;
	}

	private void logEnd(String seq, String fileName, boolean success, String resultDesc) {
		Map param = new HashMap() ;
		Map prod = new HashMap();
		prod.put("id", seq);
		String result = success ? "success" : resultDesc;
		prod.put("deal_result", result );
		param.put("ProductOffSyncLog", prod);
		try {
			DataTranslate._String(
					ServiceManager.callJavaBeanService("PRODUCTOFFSYNCLOGBO",
							"updateProductOffSyncLog" ,param));
		} catch (Exception e) {
			
		}
	}

	private String logStart(String fileName, String result, String proc_type) {
		Map param = new HashMap() ;
		Map prod = new HashMap();
		prod.put("file_name", fileName);
		prod.put("proc_type", proc_type);
		prod.put("result", result);
		param.put("ProductOffSyncLog", prod);
		String logId = null;
		try {
			logId = DataTranslate._String(
					ServiceManager.callJavaBeanService("PRODUCTOFFSYNCLOGBO",
							"addProductOffSyncLog" ,param));
		} catch (Exception e) {
			
		}
		return logId;
	}

	/**
	 * ismp的销售品相当于VSOP的增值产品
	 * @param fileName
	 * @param ftp
	 * @return
	 */
	private boolean syncOffer(String fileName, FTPClient ftp) {
//		写日志
		String seq = logStart(fileName,"", "offer");
		InputStream in = null;
		try {
			in = retrieveFileStreamFromFtp(fileName, ftp);
			String reqXml = StreamToString(in); 
			Document doc = XMLUtils.parse(reqXml);
			Element root = doc.getRootElement();
			String requestXml = compositeRequest(root);
			
			Map xparam = new HashMap() ;
	    	xparam.put("xml", requestXml) ;
	    	xparam.put("sourceSystem", "0") ; 
			String responseXml = null;
			Map map=null;
			try {
				map = DataTranslate._Map(
						ServiceManager.callJavaBeanService(ServiceList.INTERFACE_PPMProductSysManager,
								"productInfoSysIsmp" ,xparam));
				responseXml=(String)map.get("responseXml");
			} catch (Exception e) {
				logger.error("", e);
				e.printStackTrace();
			}
			//2.2缓存处理
			String product_id=(String)map.get("product_id");
			RefreshCacheHttpClient.getInstance().refreshAllServerCaches("0", product_id);
			logger.info("responseXml:" + responseXml);
			String resultCode = XMLUtils.getSingleTagValue(responseXml, "ResultCode");
			String resultDesc = XMLUtils.getSingleTagValue(responseXml, "ResultDesc");
			logEnd(seq,fileName, "0".equals(resultCode), resultDesc);
			if("0".equals(resultCode)) return true;
			
			
		} catch (IOException e) {
			logger.error("", e);
		} catch (DocumentException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		
		return false;
	}
	private boolean syncPOffer(String fileName, FTPClient ftp) {
//		写日志
		String seq = logStart(fileName,"", "poffer");
		InputStream in = null;
		try {
			in = retrieveFileStreamFromFtp(fileName, ftp);
			String reqXml = StreamToString(in); 
			Document doc = XMLUtils.parse(reqXml);
			Element root = doc.getRootElement();
			String requestXml = compositeOfferRequest(root);
			Map xparam = new HashMap() ;
	    	xparam.put("xml", requestXml) ;
	    	xparam.put("sourceSystem", "3") ; 
			String responseXml = null;
			Map map=null;
			try {
				map = DataTranslate._Map(
						ServiceManager.callJavaBeanService("PPMOfferSynManager",
								"prodOffInfoSysnFromIsmp" ,xparam));
				responseXml=(String)map.get("responseXml");
			} catch (Exception e) {
				logger.error("",e);
				e.printStackTrace();
			}
			//2.2缓存处理
			String prod_offer_id=(String)map.get("prod_offer_id");
			logger.info("responseXml:" + responseXml);
			RefreshCacheHttpClient.getInstance().refreshAllServerCaches("1", prod_offer_id);
			String resultCode = XMLUtils.getSingleTagValue(responseXml, "ResultCode");
			String resultDesc = XMLUtils.getSingleTagValue(responseXml, "ResultDesc");
			logEnd(seq,fileName, "0".equals(resultCode), resultDesc);
			if("0".equals(resultCode)) return true;
			
		} catch (IOException e) {
			logger.error("", e);
		} catch (DocumentException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		
		return false;
	}

	private String StreamToString(InputStream in) {
		StringBuffer bf = new StringBuffer();
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(in, "UTF-8");
			BufferedReader breader = new BufferedReader(isr);
			String temp = null;
			temp = breader.readLine();
			while (temp != null) {
				bf.append(temp);
				temp = breader.readLine();
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}finally{
			try {
				if(isr != null ) isr.close();
			} catch (IOException e) {
				logger.error("",e);
			}
		}
		return bf.toString();
	}

	private String compositeOfferRequest(Element root) {
		StringBuffer bf = new StringBuffer("<Request><SessionBody><PackageSyncFromISMPReq>");
			bf.append("<StreamingNo>").append(XMLUtils.getElementStringValue(root, "streamingNo")).append("</StreamingNo>")
			.append("<TimeStamp>").append(System.currentTimeMillis()).append("</TimeStamp>")
			.append("<SystemId>").append("204").append("</SystemId>")
			.append("<OPFlag>").append(XMLUtils.getElementStringValue(root, "OPFlag")).append("</OPFlag>")
			.append("<PackageID>").append(XMLUtils.getElementStringValue(root, "PProductOfferID")).append("</PackageID>")
			.append("<PNameCN>").append(XMLUtils.getElementStringValue(root, "PNameCN")).append("</PNameCN>")
			.append("<PNameEN>").append("").append("</PNameEN>")
			.append("<PDesCN>").append(XMLUtils.getElementStringValue(root, "PNameCN")).append("</PDesCN>")
			.append("<PDesEn>").append("").append("</PDesEn>")
			.append("<Status>").append(XMLUtils.getElementStringValue(root, "status")).append("</Status>")
			.append("<ChargingPolicyCN>").append(XMLUtils.getElementStringValue(root, "chargingPolicyCN")).append("</ChargingPolicyCN>")
			.append("<ChargingPolicyID>").append(XMLUtils.getElementStringValue(root, "chargingPolicyID")).append("</ChargingPolicyID>")
			.append("<SubOption>").append(XMLUtils.getElementStringValue(root, "subOption")).append("</SubOption>")
			.append("<PresentOption>").append(XMLUtils.getElementStringValue(root, "presentOption")).append("</PresentOption>")
			.append("<CorpOnly>").append(XMLUtils.getElementStringValue(root, "corpOnly")).append("</CorpOnly>")
			.append("<Scope>").append("1").append("</Scope>");
			for (Iterator it = root.elementIterator("productOfferIDList"); it.hasNext();) {
				Element elem = (Element) it.next();
				String productNbr = XMLUtils.getElementStringValue(elem, "productOfferID");
//				String productId = DcSystemParamManager.getInstance().getProductIdByNbr(productNbr);
				bf.append("<ProductID>").append(productNbr).append("</ProductID>");
			}
		bf.append("</PackageSyncFromISMPReq></SessionBody></Request>");
		return bf.toString();
	}
	
	private String compositeRequest(Element root) {
		StringBuffer bf = new StringBuffer("<Request><SessionBody><ProductSyncFromISMPReq>");
			bf.append("<StreamingNo>").append(XMLUtils.getElementStringValue(root, "streamingNo")).append("</StreamingNo>")
				.append("<TimeStamp>").append(System.currentTimeMillis()).append("</TimeStamp>")
				.append("<SystemId>").append("204").append("</SystemId>")
				.append("<SPID>").append(XMLUtils.getElementStringValue(root, "SPID")).append("</SPID>")
				.append("<OPFlag>").append(XMLUtils.getElementStringValue(root, "OPFlag")).append("</OPFlag>")
//				.append("<ProductType>").append("1").append("</ProductType>")  //增值产品
				.append("<ProductID>").append(XMLUtils.getElementStringValue(root, "productOfferID")).append("</ProductID>")
				.append("<PnameCN>").append(getPNameCn(XMLUtils.getElementStringValue(root, "productID"))).append("</PnameCN>")
				.append("<PnameEN>").append(XMLUtils.getElementStringValue(root, "nameEN")).append("</PnameEN>")
				.append("<PdescriptionCN>").append(XMLUtils.getElementStringValue(root, "chargingPolicyCN")).append("</PdescriptionCN>")
				.append("<PdescriptionEn>").append(XMLUtils.getElementStringValue(root, "descriptionEn")).append("</PdescriptionEn>")
			;
			List ServiceCapabilityID = getServiceCapabilityIdList(XMLUtils.getElementStringValue(root, "productID"));
			if (ServiceCapabilityID != null && ServiceCapabilityID.size()>0) {
				for (int i = 0; i < ServiceCapabilityID.size(); i++) {
					String service_code = (String)ServiceCapabilityID.get(i);
					bf.append("<ServiceCapabilityID>").append(service_code).append("</ServiceCapabilityID>");
				}
			}
			bf
				.append("<Status>").append(XMLUtils.getElementStringValue(root, "status")).append("</Status>")
				.append("<ChargingPolicyCN>").append(XMLUtils.getElementStringValue(root, "chargingPolicyCN")).append("</ChargingPolicyCN>")
				.append("<ChargingPolicyID>").append(XMLUtils.getElementStringValue(root, "chargingPolicyID")).append("</ChargingPolicyID>")
				.append("<SubOption>").append(XMLUtils.getElementStringValue(root, "subOption")).append("</SubOption>")
				.append("<Present>").append(XMLUtils.getElementStringValue(root, "present")).append("</Present>")
				.append("<CorpOnly>").append(XMLUtils.getElementStringValue(root, "corpOnly")).append("</CorpOnly>")
				.append("<PackageOnly>").append(XMLUtils.getElementStringValue(root, "packageOnly")).append("</PackageOnly>")
				;
			for (Iterator it = root.elementIterator("productOPCode"); it.hasNext();) {
				bf.append("<ProductOPCode>");
				Element elem = (Element) it.next();
				String FeatureStr = XMLUtils.getElementStringValue(elem, "featureStr");
				bf.append("<FeatureStr>").append(FeatureStr).append("</FeatureStr>");
				String AccessNO = XMLUtils.getElementStringValue(elem, "accessNO");
				bf.append("<AccessNO>").append(AccessNO).append("</AccessNO>");
				String OPType = XMLUtils.getElementStringValue(elem, "opType");
				bf.append("<OPType>").append(OPType).append("</OPType>");
				String MatchMode = XMLUtils.getElementStringValue(elem, "matchMode");
				bf.append("<MatchMode>").append(MatchMode).append("</MatchMode>");
				bf.append("</ProductOPCode>");
			}
			bf
				.append("<SettlementCycle>").append(XMLUtils.getElementStringValue(root, "settlementCycle")).append("</SettlementCycle>")
				.append("<SettlementPayType>").append(XMLUtils.getElementStringValue(root, "settlementPayType")).append("</SettlementPayType>")
				.append("<SettlementPercent>").append(XMLUtils.getElementStringValue(root, "settlementPercent")).append("</SettlementPercent>")
				.append("<SettlementPercent>").append(XMLUtils.getElementStringValue(root, "settlementPercent")).append("</SettlementPercent>")
				
				.append("<Scope>").append("1").append("</Scope>")
				.append("<ProductHost>").append("204").append("</ProductHost>")
				.append("<PlatForm>").append("204").append("</PlatForm>")
			
			;
		bf.append("</ProductSyncFromISMPReq></SessionBody></Request>");
		return bf.toString();
	}
	
	private String getPNameCn(String serviceId) {
		Map param = new HashMap();
		param.put("serviceId", serviceId);
		String ret = "";
		try {
			ret = DataTranslate._String(
					ServiceManager.callJavaBeanService(ServiceList.INTERFACE_PPMProductSysManager,
							"getPNameCn" ,param ));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	private List getServiceCapabilityIdList(String serviceId) {
		
		Map param = new HashMap();
		param.put("serviceId", serviceId);
		List ret = null;
		try {
			ret = DataTranslate._List(
					ServiceManager.callJavaBeanService(ServiceList.INTERFACE_PPMProductSysManager,
							"getIsmpServiceCapabilityIdList" ,param ));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * @deprecated
	 * @param root
	 * @return
	 */
	private String compositePOfferRequest(Element root) {
		StringBuffer bf = new StringBuffer("<Request><SessionBody><PackageSyncFromISMPReq>");
		bf.append("<StreamingNo>").append(XMLUtils.getElementStringValue(root, "streamingNo")).append("</StreamingNo>")
		.append("<TimeStamp>").append(System.currentTimeMillis()).append("</TimeStamp>")
		.append("<SystemId>").append("204").append("</SystemId>")
		.append("<OPFlag>").append(XMLUtils.getElementStringValue(root, "OPFlag")).append("</OPFlag>")
		.append("<PackageID>").append(XMLUtils.getElementStringValue(root, "PProductOfferID")).append("</PackageID>")
		.append("<PNameCN>").append(XMLUtils.getElementStringValue(root, "PNameCN")).append("</PNameCN>")
		.append("<PNameEN>").append(XMLUtils.getElementStringValue(root, "PNameEN")).append("</PNameEN>")
		.append("<PDesCN>").append(XMLUtils.getElementStringValue(root, "PDesCN")).append("</PDesCN>")
		.append("<PDesEn>").append(XMLUtils.getElementStringValue(root, "PDesEn")).append("</PDesEn>")
		.append("<Status>").append(XMLUtils.getElementStringValue(root, "status")).append("</Status>")
		.append("<ChargingPolicyCN>").append(XMLUtils.getElementStringValue(root, "chargingPolicyCN")).append("</ChargingPolicyCN>")
		.append("<ChargingPolicyID>").append(XMLUtils.getElementStringValue(root, "chargingPolicyID")).append("</ChargingPolicyID>")
		.append("<SubOption>").append(XMLUtils.getElementStringValue(root, "subOption")).append("</SubOption>")
		.append("<PresentOption>").append(XMLUtils.getElementStringValue(root, "presentOption")).append("</PresentOption>")
		.append("<CorpOnly>").append(XMLUtils.getElementStringValue(root, "corpOnly")).append("</CorpOnly>")
		;
		for (Iterator it = root.elementIterator("productOfferIDList"); it.hasNext();) {
			Element elem = (Element) it.next();
			String offerId = XMLUtils.getElementStringValue(elem, "productOfferID");
			List productIdList = DcSystemParamManager.getInstance().getVproductIdsByOfferId(offerId);
			String productId = "";
			if(productIdList != null && productIdList.size() >0)
				productId = (String) productIdList.get(0);
			bf.append("<ProductID>").append(productId).append("</ProductID>");
		}
		
		bf
//		.append("<ProductID>").append(XMLUtils.getElementStringValue(root, "PNameCN")).append("</ProductID>")
		.append("<Scope>").append("1").append("</Scope>")
//		.append("<ProdOfferRelation>").append(XMLUtils.getElementStringValue(root, "ProdOfferRelation")).append("</ProdOfferRelation>")
		;
		bf.append("</PackageSyncFromISMPReq></SessionBody></Request>");
		return bf.toString();
	}

	private InputStream retrieveFileStreamFromFtp(String fileName, FTPClient ftp) throws IOException {
		InputStream in = null;
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
		
		ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
		in = ftp.retrieveFileStream(fileName);
		return in;
	}

	/**
	 * 检查是否合法的OfferInfo文件
	 * @param name
	 * @return  是合法文件则返回true
	 */
	private boolean isValidateOfferInfoFile(String name) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DAY_OF_MONTH, -1);
//		Date d = cal.getTime();
//		String yes = sdf.format(d);
//		if(name.indexOf(yes) != -1) return  true;
		if(name.indexOf("PRODOFFERINFO") != -1) return true;
		return false;
	}
	
	/**
	 * 检查是否合法的PProdOffer文件
	 * @param name
	 * @return
	 */
	private boolean isValidatePProdOfferFile(String name) {
		if(name.indexOf("PPOFFERINFO") != -1) return true;
		return false;
	}
	
	public IJobTrans getInstance(JobDataMap jobConext) {
		return new ProdOfferSyncToVsopJob();
	}

}
