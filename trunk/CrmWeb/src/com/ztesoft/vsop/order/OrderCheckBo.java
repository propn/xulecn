package com.ztesoft.vsop.order;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.crm.business.common.query.SqlDAO;
import com.ztesoft.crm.business.common.query.SqlDAOImpl;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.order.dao.OrderCheckDao;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * 订购关系比对
 * @author liu.yuming  2010-04-12
 *
 */
public class OrderCheckBo {
	private static final Logger log = Logger.getLogger(OrderCheckBo.class);
	private static final String CHECK_FILE_DIR = "CHECK_FILE_DIR";
	private static final String X_PLATFORM_FILE_DIR = "X_PLATFORM_FILE_DIR";//X平台上传比对文件的存放路径
	private static final String VSOP_CODE = "200";//省级VSOP
	private static final String[] GET_FILE_SYS_CODE = DcSystemParamManager.getParameter("GET_FILE_SYS_CODE").split(",");//{"203","204","205"};//ISMP

	public int checkOrder(String cycleType) throws Exception{
		log.info("checkOrder   start");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String datef = "";
		String dateType = "A";
		if("D".equals(cycleType)){
			datef = (new SimpleDateFormat("yyyyMMdd")).format(calendar.getTime());
		}else{
			datef = (new SimpleDateFormat("yyyyMM")).format(calendar.getTime());
			dateType ="I";
		}
		String fileDir = null;//CrmParamsConfig.getInstance().getParamValue(CHECK_FILE_DIR);
		if(fileDir == null || fileDir.equals(""))fileDir = "E:\\vsop_file";
		//文件是否都不存在计数器
		int num = 0;
		//业务平台各提供比对文件
		for(int i=0;i<GET_FILE_SYS_CODE.length;i++){
			String fileName = dateType+"-"+datef+"-VSOP R8.2.1-"+GET_FILE_SYS_CODE[i]+"-00-01.AVL";
			String filePathAndName = fileDir + File.separator + GET_FILE_SYS_CODE[i] + File.separator + fileName;
			log.info("check file from ISMP filePathAndName："+filePathAndName);
			File file = new File(filePathAndName);
			if(!file.exists() || file.isDirectory()){
				continue;
			}else{
				num++;
				OrderCheckDao dao = new OrderCheckDao();
				//导入外系统的数据到中间表
				BufferedReader reader = null;
				FileInputStream fi = null;
				InputStreamReader ir = null;
				List ls = null;
				try {
					fi = new FileInputStream(filePathAndName);
					ir = new InputStreamReader(fi);
					reader = new BufferedReader(ir);
					ls = extractListFromReader(reader);
					ir.close();
					fi.close();
					reader.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					if(ir != null)ir.close();
					if(fi != null)fi.close();
					if(reader != null)reader.close();
					e.printStackTrace();
					throw e;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					if(ir != null)ir.close();
					if(fi != null)fi.close();
					if(reader != null)reader.close();
					e.printStackTrace();
					throw e;
				}finally{
					if(ir != null)ir.close();
					if(fi != null)fi.close();
					if(reader != null)reader.close();
				}
				try{
					//delete
					//导vsop某天的数据到中间表
					dao.importIsmpDataToMid(ls,fileName);
				}catch(Exception e){
					log.info("导vsop某天的数据到中间表错误:"+e);
					throw e;
				}finally{
					LegacyDAOUtil.commitAndCloseConnection();
				}
				//将已经读取的文件移除到另外一个文件夹下备份
				excelFileBak(fileDir + File.separator + GET_FILE_SYS_CODE[i], filePathAndName);
			}
		}
		//广西本地化需求，按X平台提供的订购关系比对文件调整vsop订购关系 liuyuming start
//		String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");
//		if(provinceCode.equals(CrmConstants.GX_PROV_CODE)){
//			orderRelaCheckXPlatformGX(cycleType);
//		}
		//广西本地化需求，按X平台提供的订购关系比对文件调整vsop订购关系 liuyuming start
		//下面的逻辑启动另外一个connection
		//比对
		
		if(num > 0) 
			new compareToBO().invoProcedure(cycleType);
		else return 0;
		
		//属主是VSOP且业务使用范围是本省业务的订购关系数据取出生成文件发送给按照对接平台表PRODUCT_SYSTEM_INFO 将文件发送给增值产品对应的各自平台 
//		singleExportXFile();
		//属主是VSOP且业务使用范围是集团业务的订购关系数据取出生成文件发送给集团VSOP平台
//		singleExportGroupFile();
		//属主是ISMP且业务使用范围是集团业务的订购关系数据取出生成文件发送给集团VSOP平台
//		singleExportISMPFile();
//		//将当天发生变更，属主是VSOP且业务使用范围是本省业务的订购关系取出，每行一条记录 
//		String host = "'101','102','200','201','202','206','207','208','209','210','211','213','214','215'";//属主为vsop的系统编码
//		List dataProv = dao.exportVsopDataToFile(host ,"1");
//		//生成送给X平台的文件
//		exportDataToFile(fileDir, "TVSOP-R7.5.2-"+datef+SYS_CODE+"_X", dataProv);
//		//将当天发生变更，属主是VSOP且业务使用范围是集团业务的订购关系取出，每行一条记录 
//		List dataGroup = dao.exportVsopDataToFile(host ,"0");
//		//生成送给集团VSOP平台的文件
//		exportDataToFile(fileDir, "TVSOP-R7.5.2-"+datef+SYS_CODE+"_VSOP", dataGroup);
		log.info("checkOrder   end");
		return 1;
	}
	/**
	 * 广西本地化需求，订购关系比对以平台为准，X平台提供订购关系比对文件给vsop，由vsop负责比对并调整
	 */
	private void orderRelaCheckXPlatformGX(String cycleType)throws Exception{
		
		String fileDir = CrmParamsConfig.getInstance().getParamValue(X_PLATFORM_FILE_DIR);
		File fileDi = new File(fileDir);
		if(fileDi.isDirectory()){
			File[] files = fileDi.listFiles();
			for(int i=0;i<files.length;i++){
				File file = files[i];
				if(!file.exists() || file.isDirectory() || !checkFileName(file.getName(),cycleType)){
					continue;
				}else{
					OrderCheckDao dao = new OrderCheckDao();
					//导入外系统的数据到中间表
					BufferedReader reader = null;
					FileInputStream fi = null;
					InputStreamReader ir = null;
					List ls = null;
					try {
						fi = new FileInputStream(file);
						ir = new InputStreamReader(fi);
						reader = new BufferedReader(ir);
						ls = extractListFromReader(reader);
						ir.close();
						fi.close();
						reader.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						if(ir != null)ir.close();
						if(fi != null)fi.close();
						if(reader != null)reader.close();
						e.printStackTrace();
						throw e;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						if(ir != null)ir.close();
						if(fi != null)fi.close();
						if(reader != null)reader.close();
						e.printStackTrace();
						throw e;
					}finally{
						if(ir != null)ir.close();
						if(fi != null)fi.close();
						if(reader != null)reader.close();
					}
					try{
						//delete
						//导vsop某天的数据到中间表
						dao.importXDataToMidGX(ls,file.getName());
					}catch(Exception e){
						log.info("导vsop某天的数据到中间表错误:"+e);
					}finally{
						LegacyDAOUtil.commitAndCloseConnection();
					}
					//将已经读取的文件移除到另外一个文件夹下备份
					excelFileBak(fileDir, file.getAbsolutePath());
				}
			}
		}
//		String fileName = dateType+"-"+datef+"-VSOP R8.2.1-"+ISMP_CODE[i]+"-00-01.AVL";
//		String filePathAndName = fileDir + File.separator + fileName;
//		log.info("check file from ISMP filePathAndName："+filePathAndName);
//		File file = new File(filePathAndName);
		
	}
	/**
	 * 检查文件名是否符合集团规范的命名规则
	 * @param fileName
	 * @param cycleType 按月或按日增量比对
	 * @return
	 */
	private boolean checkFileName(String fileName, String cycleType){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String datef = "";
		String dateType = "A";
		if("D".equals(cycleType)){
			datef = (new SimpleDateFormat("yyyyMMdd")).format(calendar.getTime());
		}else{
			datef = (new SimpleDateFormat("yyyyMM")).format(calendar.getTime());
			dateType ="I";
		}
		//截取文件名的各部分命名字段
		String[] strs = fileName.split("-");
		if(!strs[0].equals(dateType))return false;
		if(!strs[1].equals(datef))return false;
		return true;
	}
	/**
	 * 可单独配置 属主是VSOP且业务使用范围是本省业务的订购关系数据取出生成文件发送给X平台
	 * 20100427 liuyuming 改为按照对接平台表PRODUCT_SYSTEM_INFO 将文件发送给增值产品对应的各自平台 
	 * @throws Exception 
	 */
	public void singleExportXFile(String timeType) throws Exception{
		//先生成查询表数据
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String datef = "";
		String fileType = "A";
		if("M".equals(timeType)){
			datef = (new SimpleDateFormat("yyyyMM")).format(calendar.getTime());
			fileType = "I";
		}else if("D".equals(timeType)){
			datef = (new SimpleDateFormat("yyyyMMdd")).format(calendar.getTime());
			fileType = "A";
		}
		
		new compareToBO().invoSendFileDataProcedure(timeType);
		OrderCheckDao dao = new OrderCheckDao();
		//将当天发生变更，属主是VSOP且业务使用范围是本省业务的订购关系取出，每行一条记录 
		String host = "'200'";//属主为vsop的系统编码
		List systemCode = dao.getSystemCode();
		String fileDir = CrmParamsConfig.getInstance().getParamValue(CHECK_FILE_DIR);
		String fileName = fileType+"-"+datef+"-VSOP R7.2-"+this.VSOP_CODE+"-00-01";
		for(int i=0;i<systemCode.size();i++){
			String sysCode = (String)systemCode.get(i);
			List dataProv  = new ArrayList();
			//如果平台编码是ISMP的就不生成文件
			if(sysCode.indexOf(DcSystemParamManager.getParameter("GET_FILE_SYS_CODE")) > -1){
				fileName = fileType+"-"+datef+"-VSOP R8.2.2-"+this.VSOP_CODE+"-00-01";
				dataProv = dao.exportVsopDataToIsmpFile(host,"1",sysCode);
			}else if("100".equals(sysCode)){
				continue;
			}else {
				dataProv = dao.exportVsopDataToFile(host,"1",sysCode);
				fileName = fileType+"-"+datef+"-VSOP R7.2-"+this.VSOP_CODE+"-00-01";
			}
			//生成送给X平台的文件
			exportPlatFormDataToFile(fileDir, sysCode, fileName, dataProv);
		}
		LegacyDAOUtil.releaseConnection();
//		putFileByFtp(datef+"X.AVL",fileName,"DC_XPLATFORM_FTP");
	}
	/**
	 * 可单独配置 属主是VSOP且业务使用范围是集团业务的订购关系数据取出生成文件发送给集团VSOP平台
	 * timeType 为 "D"按天   "M" 按月全量
	 * @throws Exception 
	 */
	public void singleExportGroupFile(String timeType) throws Exception{
		//先生成查询表数据
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String datef = "";
		String fileType = "A";
		if("M".equals(timeType)){
			datef = (new SimpleDateFormat("yyyyMM")).format(calendar.getTime());
			fileType = "I";
		}else if("D".equals(timeType)){
			datef = (new SimpleDateFormat("yyyyMMdd")).format(calendar.getTime());
			fileType = "A";
		}
		new compareToBO().invoSendFileDataProcedure(timeType);
		OrderCheckDao dao = new OrderCheckDao();
		//将当天发生变更，属主是VSOP且业务使用范围是本省业务的订购关系取出，每行一条记录 
		String host = "'200'";//属主为vsop的系统编码
//		List systemCode = dao.getSystemCode();
		String fileDir = CrmParamsConfig.getInstance().getParamValue(CHECK_FILE_DIR);

		String fileName = fileType+"-"+datef+"-VSOP R9.3-"+this.VSOP_CODE+"-00-01";
		String sysCode = "100";//集团VSOP
		//生成送给集团VSOP平台的文件
		List dataProv1 = dao.getIsmpData(host, "0");
		
		//生成送给集团VSOP平台ISMP属主的文件
		/*host = "'203','204','205'";
		List dataProv2 = dao.exportVsopDataToIsmpFile(host, "0",host);
		for (int i = 0; i < dataProv2.size(); i++) {
			dataProv1.add(dataProv2.get(i));
		}*/
		exportPlatFormDataToFile(fileDir, "100", fileName, dataProv1);
		LegacyDAOUtil.releaseConnection();	
		//属主是ISMP且业务使用范围是集团业务的订购关系数据取出生成文件发送给集团VSOP平台
		singleExportISMPFile(timeType);  
//		putFileByFtp("TVSOP-R7.5.2-"+datef+SYS_CODE+"_GVSOP.AVL",fileName,"DC_GPLATFORM_FTP");
	}
	/**
	 * 可单独配置 属主是ISMP且业务使用范围是集团业务的订购关系数据取出生成文件发送给集团VSOP平台
	 * timeType 为 "D"按天   "M" 按月全量
	 * @throws Exception 
	 */
	public void singleExportISMPFile(String timeType) throws Exception{
		new compareToBO().invoOdsProcedure(timeType);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String datef = "";
		String fileType = "A";
		if("M".equals(timeType)){
			datef = (new SimpleDateFormat("yyyyMM")).format(calendar.getTime());
			fileType = "I";
		}else if("D".equals(timeType)){
			datef = (new SimpleDateFormat("yyyyMMdd")).format(calendar.getTime());
			fileType = "A";
		}
		OrderCheckDao dao = new OrderCheckDao();
		// 属主是ISMP且业务使用范围是集团业务的订购关系数据取出生成文件发送给集团VSOP平台 
//		List systemCode = dao.getSystemCode();
		String fileDir = CrmParamsConfig.getInstance().getParamValue(CHECK_FILE_DIR);

		String fileName = fileType+"-"+datef+"-VSOP R8.2.2-"+this.VSOP_CODE+"-00-01";
		String host = "'203','204','205'";
		List dataProv = dao.exportVsopDataToIsmpFile(host, "0",host);
		exportPlatFormDataToFile(fileDir, "100", fileName, dataProv);
		LegacyDAOUtil.releaseConnection();
//		putFileByFtp("TVSOP-R7.5.2-"+datef+SYS_CODE+"_GVSOP.AVL",fileName,"DC_GPLATFORM_FTP");
	}
	/**
	 * 发送在VSOP当天发生订购关系改变的所有数据到ODS
	 * @throws Exception 
	 */
	public void exportToOdsFile() throws Exception{
		try{
			new compareToBO().invoOdsProcedure("D");
			OrderCheckDao dao = new OrderCheckDao();
			//将当天VSOP订购关系发生变更的所有订购关系取出，每行一条记录 
			List dataProv = dao.exportDataToODS();//所有平台数据取出
			String fileDir = CrmParamsConfig.getInstance().getParamValue(CHECK_FILE_DIR);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			String datef = (new SimpleDateFormat("yyyyMMdd")).format(calendar.getTime());
			String fileName = "A-"+datef+"-VSOP R4.1-"+this.VSOP_CODE+"-00-01";
			//生成送给ODS平台的文件
			exportPlatFormDataToFile(fileDir, "210", fileName, dataProv);
//			putFileByFtp("TVSOP-R7.5.2-"+datef+SYS_CODE+"_GVSOP.AVL",fileName,"DC_ODSPLATFORM_FTP");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			LegacyDAOUtil.releaseConnection();
		}
	}
	private  List extractListFromReader(BufferedReader reader) throws IOException {
		List ret = new ArrayList();
		String line = reader.readLine();
		while(line != null){
			ret.add(line);
			line = reader.readLine();
		}
		return ret;
	}
	private String exportDataToFile(String filePath, String exportTime, List dataList){
		FileWriter fw = null;
		String separator = System.getProperty("file.separator");
		String fileName = filePath+separator+exportTime+".AVL";
		try{
			fw = new FileWriter(fileName);
			for(int i=0; i < dataList.size(); i++){
				fw.write((String)dataList.get(i));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fileName;
	}
	
	private void putFileByFtp(String remoteFileName,String filePath, String ftpUrl) throws NumberFormatException, SocketException, IOException{
		FTPClient ftp = new FTPClient();
		SqlDAO dao = SqlDAOImpl.getInstance();
		String sql = "select param_val from dc_system_param where param_code = ?";
		String result = dao.getValueBySqlAndCond(sql, ftpUrl);
		String[] splitStr = result.split(",");
		if(splitStr.length < 4){
			log.error("FTP参数配置错误!");
			return;
		}
		String ftpURL = splitStr[0];
		String ftpDir = splitStr[1];
		String userName = splitStr[2];
		String pwd = splitStr[3];
		ftp = connectFTP(ftp, ftpURL, userName, pwd);
		if (!(ftp.changeWorkingDirectory(ftpDir))) {
		      ftp.makeDirectory(ftpDir);
		      ftp.changeWorkingDirectory(ftpDir);
		}
		ftp.setFileType(ftp.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
		ftp.setFileTransferMode(ftp.STREAM_TRANSFER_MODE);
		InputStream in = new FileInputStream(filePath);
		if(in == null){
			log.error("本地文件不存在!");
			return;
		}
		try{
			ftp.storeFile(remoteFileName, in);
//			in.close();
//			ftp.logout();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			in.close();
			ftp.logout();
		}
	}
	/**
	 *  登录FTP，并返回FTPClient的引用
	 * @param ftp
	 * @param ftpName
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws NumberFormatException
	 * @throws SocketException
	 * @throws IOException
	 */
	private  FTPClient connectFTP(FTPClient ftp,
								  String ftpName,
								  String userName,
								  String passWord) throws NumberFormatException, SocketException, IOException{
		log.info("connectFTP start");
		if(ftp.isConnected()){
			return ftp;
		}
		
		String hostName = ftpName.split(":")[0];
		String port = "";
		if(ftpName.indexOf(":")>0){
			port = ftpName.split(":")[1];
		}
		if (!"".equalsIgnoreCase(port)) {
			ftp.connect(hostName, Integer.parseInt(port));
		} else {
			ftp.connect(hostName);
		}
		int reply;
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			log.error("HOSTNAME:" + hostName + "FTP server can not connect");
			return null;
		}
		if (!ftp.login(userName, passWord)) {
			ftp.logout();
			log.error("userName or password incorrect");
			return null;
		}
		log.info("connectFTP end");
		return ftp;
	}
//	public static void main(String[] arg){
//		try {
//			new OrderCheckBo().singleExportXFile();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	/**
	 * 根据参数生成送不同平台的数据文件
	 * @param filePath
	 * @param exportTime
	 * @param dataList
	 * @return
	 */
	private String exportPlatFormDataToFile(String filePath, String sysCode, String _fileName, List dataList){
		FileWriter fw = null;
		String separator = System.getProperty("file.separator");
		String fileDir = filePath+separator+sysCode;
		File fDir = new File(fileDir);
		if(!fDir.exists()){
			fDir.mkdir();
		}
		String fileName = filePath+separator+sysCode+separator+_fileName;
		try{
			fw = new FileWriter(fileName+".TMP");
			for(int i=0; i < dataList.size(); i++){
				fw.write((String)dataList.get(i));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		renameAndCheckFile(fileName, _fileName, dataList.size());
		return fileName;
	}
	private void renameAndCheckFile(String fileName, String _fileName, int size){
		FileWriter fw = null;
		try{
			File newFile = new File(fileName+".AVL");
			if(newFile.exists())
				newFile.delete();
			//文件生成完后修改后缀名
			new File(fileName+".TMP").renameTo(newFile);
			//生成校验文件
			File hzFile = new File(fileName+".HZ");
			if(hzFile.exists())
				hzFile.delete();
			fw = new FileWriter(fileName+".HZ");
			fw.write("File Name:"+_fileName+"\r\n");//文件名
			fw.write("File Size:"+newFile.length()+"\r\n");
			fw.write("The number of record:"+size);
		}catch(Exception e){
			
		}finally{
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	  * 把读取完的文件移动到备份文件夹
	  *
	  */
	 public void excelFileBak(String fileD, String filePath) {

		String sep = System.getProperty("file.separator");
		// String fileDir = fileD+sep+"ISMP_BAK";
		String bakDir = fileD + sep + "FILE_BAK";
		File oldFile = new File(filePath);
		if (oldFile.isFile()) {
			if (!(new File(bakDir).isDirectory())) {
				new File(bakDir).mkdirs();
			}
		}
		String newName = oldFile.getName();
		File newFile=new File(bakDir + sep + newName);
		if(newFile.exists())
			newFile.delete();
		oldFile.renameTo(newFile);
//		File[] fs = f.listFiles();
		
//		for (int i = 0; i < fs.length; ++i) {
//			File oldFile = fs[i];
//			if (oldFile.isFile()) {
//				if (!(new File(bakDir).isDirectory())) {
//					new File(bakDir).mkdirs();
//				}
//				String newName = oldFile.getName();
//				oldFile.renameTo(new File(bakDir + sep + newName));
//			}
//		}
	}
}
