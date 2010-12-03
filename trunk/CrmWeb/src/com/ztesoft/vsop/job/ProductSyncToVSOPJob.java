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
import com.ztesoft.vsop.ismp.IsmpServiceVo;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * 江西本地
 * ismp产品同步
 * 从crm的ftp获取产品文件，同步到VSOP系统，保存到库表
 * crm的增值产品是ismp那边同步过来的
 * 
 * @author yi.chengfeng 2010-7-29
 *
 */
public class ProductSyncToVSOPJob extends JobTransaction {
	
	private static Logger logger = Logger.getLogger(ProductSyncToVSOPJob.class);
	
	public void process() {
		logger.info("ProductSyncToVSOPJob start.");
		FTPClient ftp = null;
		FTPHelper ftphelper = new FTPHelper();
		
		try {
			//登录ftp
			ftp = ftphelper.connectCrmFtp();
			String absolutePath = DcSystemParamManager.getParameter(VsopConstants.CRM_FTP_ISMP_PRODUCT_DIR);
			String absolutePathBackup = DcSystemParamManager.getParameter(VsopConstants.CRM_FTP_ISMP_PRODUCT_BACKUP_DIR);
//			String absolutePath = "/home/csss/infismp/ftp/back/PRODINFO/15";
			String[] fileNames = ftp.listNames(absolutePath);
			ftp.disconnect();
			List shouldHandleFiles = new ArrayList();
			for (int i = 0; fileNames != null && i < fileNames.length; i++) {
				String name = fileNames[i];
				//获取前一天的文件
				if(isValidateProductFile(name)){
					shouldHandleFiles.add(name);
				}
			}
			if(shouldHandleFiles.size() == 0){
				logger.info("no file for process,exit.");
				logStart("","no file for process");
				return;
			}
			for (Iterator iterator = shouldHandleFiles.iterator(); iterator
					.hasNext();) {
				String fileName = (String) iterator.next();
				logger.info("process file -> " + fileName);
				//同步增值产品

				ftp = ftphelper.connectCrmFtp();
				boolean success = syncProduct(fileName,ftp);
				ftp.disconnect();
				if(success){
					ftp = ftphelper.connectCrmFtp();
					String bkfile = absolutePathBackup + "/" + getFileNameFromFullFileName(fileName);
					System.out.println("bkfile:" + bkfile);
					ftp.rename(fileName, absolutePathBackup + "/" + getFileNameFromFullFileName(fileName));
					ftp.disconnect();
				}
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
		logger.info("ProductSyncToVSOPJob end.");
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

	private String logStart(String fileName, String result) {
		Map param = new HashMap() ;
		Map prod = new HashMap();
		prod.put("file_name", fileName);
		prod.put("proc_type", "prod");
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

	private boolean syncProduct(String fileName, FTPClient ftp) {
//		写日志
		String seq = logStart(fileName,"");
		InputStream in = null;
		boolean success=false;
		try {
			in = retrieveFileStreamFromFtp(fileName, ftp);
			String reqXml = StreamToString(in); 
			Document doc = XMLUtils.parse(reqXml);
			Element root = doc.getRootElement();
//			String productSyncToVSOPReq = compositeRequest(root);
			IsmpServiceVo serviceVo = new IsmpServiceVo(root);
			Map cparam = new HashMap() ;
	    	cparam.put("serviceVo", serviceVo) ;
			try {
				success = DataTranslate._boolean(
						ServiceManager.callJavaBeanService(ServiceList.INTERFACE_PPMProductSysManager,
								"ismpServiceSync" ,cparam));
			} catch (Exception e) {
				logger.error("", e);
				e.printStackTrace();
			}
			logEnd(seq,fileName, success, "success");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return success;
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

	private String compositeRequest(Element root) {
		StringBuffer bf = new StringBuffer("<Request><SessionBody><ProductSyncToVSOPReq>");
			bf.append("<StreamingNo>").append(XMLUtils.getElementStringValue(root, "streamingNo")).append("</StreamingNo>")
				.append("<TimeStamp>").append(System.currentTimeMillis()).append("</TimeStamp>")
				.append("<SystemId>").append("204").append("</SystemId>")
				.append("<SPID>").append(XMLUtils.getElementStringValue(root, "SPID")).append("</SPID>")
				.append("<OPFlag>").append(XMLUtils.getElementStringValue(root, "OPFlag")).append("</OPFlag>")
				.append("<ProductType>").append("1").append("</ProductType>")  //增值产品
				.append("<ProductID>").append(XMLUtils.getElementStringValue(root, "productID")).append("</ProductID>")
				.append("<PnameCN>").append(XMLUtils.getElementStringValue(root, "nameCN")).append("</PnameCN>")
				.append("<PnameEN>").append(XMLUtils.getElementStringValue(root, "nameEN")).append("</PnameEN>")
				.append("<PdescriptionCN>").append(XMLUtils.getElementStringValue(root, "descriptionCN")).append("</PdescriptionCN>")
				.append("<PdescriptionEn>").append(XMLUtils.getElementStringValue(root, "descriptionEn")).append("</PdescriptionEn>")
			;
//				.append("<ServiceCapabilityID>").append("").append("</ServiceCapabilityID>")
			List ServiceCapabilityID = root.elements("ServiceCapabilityID");
			if (ServiceCapabilityID != null) {
				for (int i = 0; i < ServiceCapabilityID.size(); i++) {
					Element subElement = (Element) ServiceCapabilityID.get(i);
					String service_code = subElement.getStringValue();
					bf.append("<ServiceCapabilityID>").append(service_code).append("</ServiceCapabilityID>");
				}
			}
			bf
				.append("<Status>").append(toVsopStatus(XMLUtils.getElementStringValue(root, "status"))).append("</Status>")
				.append("<Scope>").append("1").append("</Scope>")
				.append("<ProductHost>").append("204").append("</ProductHost>")
				.append("<PlatForm>").append("204").append("</PlatForm>")
			
			;
		bf.append("</ProductSyncToVSOPReq></SessionBody></Request>");
		return bf.toString();
	}

	private String toVsopStatus(String aStatus) {
		if("2".equals(aStatus)) return "3";  //3: 预注销
		if("3".equals(aStatus)) return "4";  //4: 注销
		return aStatus;
	}

	private InputStream retrieveFileStreamFromFtp(String fileName, FTPClient ftp) throws IOException {
		InputStream in = null;
//		ftp = connectVCFTP(ftp);
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
		
		ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
		in = ftp.retrieveFileStream(fileName);
		return in;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	private boolean isValidateProductFile(String name) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DAY_OF_MONTH, -1);
//		Date d = cal.getTime();
//		String yes = sdf.format(d);
//		if(name.indexOf(yes) != -1) return  true;
		if(name.indexOf("PRODINFO") != -1) return true;
		return false;
	}

	public IJobTrans getInstance(JobDataMap jobConext) {
		return new ProductSyncToVSOPJob();
	}

}
