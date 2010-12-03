package com.ztesoft.vsop.order;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.web.DcSystemParamManager;

public class XFileSqlldrBatch {
	private static final Logger log = Logger.getLogger(XFileSqlldrBatch.class);
	private static final String[] platForm = DcSystemParamManager.getParameter("GET_FILE_SYS_CODE").split(",");//{"204","301"};
	
	public void execCompare(String cycleType){
		try{
			log.info("XFileSqlldrBatch.execCompare start");
			//首先将文件中数据导入中间表
			execSqlldrXtxt(cycleType);
	    	new compareToBO().invoProcedure(cycleType);
			log.info("XFileSqlldrBatch.execCompare end");
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}
	private void execSqlldrXtxt(String cycleType) throws Exception{
		 
		 try{
			Calendar calendar = Calendar.getInstance();
			String datef = "";
			if("D".equals(cycleType)){
				datef = (new SimpleDateFormat("yyyyMMdd")).format(calendar.getTime());
			}else{
				datef = (new SimpleDateFormat("yyyyMM")).format(calendar.getTime());
			}
	    	String rootDir = DcSystemParamManager.getParameter("FTP_COMPARE_FILE_PATH");//"E:\\vsop_file";
			log.info("XFileSqlldrBatch.execSqlldrXtxt rootDir:"+rootDir);
	    	for(int i = 0;i < platForm.length; i++){
	    		String dir = platForm[i];
	    		File f = new File(rootDir+File.separator+platForm[i]);
				try {
					File[] fs = f.listFiles();
					log.info("XFileSqlldrBatch.execSqlldrXtxt platForm[i]:"+dir);
					for (int j = 0; j < fs.length; ++j) {
						File file = fs[j];
						String fileName = file.getName();
						//解压缩
						if(file.isFile() && fileName.indexOf(".tar.gz") > -1){
							log.info("XFileSqlldrBatch.execSqlldrXtxt unzip start");
							String upzipName = file.getPath();
							executeShell(new String[]{upzipName, upzipName.replaceAll(".gz", "")});
							log.info("XFileSqlldrBatch.execSqlldrXtxt unzip end");
						}
						if(file.isFile() && fileName.indexOf(datef) > -1 && fileName.indexOf(".txt") > -1){
							File newFile = new File(rootDir+File.separator+dir+File.separator+dir+".txt");//文件改成固定名称用于sqlldr脚本执行
							if(newFile.exists())newFile.delete();
							file.renameTo(newFile);
//							String command = "sqlldr vsop/vsop@GX_VSOP control=E:\\vsop_file\\204\\204.ctl  log=E:\\vsop_file\\204\\204.log";
							String command =DcSystemParamManager.getParameter("SQLLDR_COMM_"+platForm[i]);//业务平台文件数据批量读取命令
							if(null==command ||"".equals(command)){
								 log.info("-----------SQLLDR is not in dc_system_param");
							}else{
								 log.info("------------SQLLDR is "+command);
							     if (saveTxtIntoDataBase(command)) {
//							    	String fileDir = DcSystemParamManager.getParameter("FTP_COMPARE_FILE_PATH");
//							        excelFileBak(platForm[0]);
							        //将已经读取的文件移除到另外一个文件夹下备份
									excelFileBak(rootDir + File.separator + platForm[0], platForm[0], fileName);
//							        excelFileBak(platForm[1]);
							     }else{
									log.info("UT_SQLLDR is fail");
									File oldFile = new File(rootDir+File.separator+dir+File.separator+fileName);
									if(oldFile.exists())oldFile.delete();
									newFile.renameTo(oldFile);//导入失败，改回原文件名
								}
								
							}
						}
					}
				}catch(Exception e){
					log.error(e.getMessage());
					throw e;
				}
	    	}
		 }catch(Exception e){
			 e.printStackTrace(); 
			 throw e;
		 }
		
	 }
	/**
	 * 执行把txt导入到数据库的
	 * @param command
	 * @return
	 * @throws Exception
	 */
	 private synchronized boolean saveTxtIntoDataBase(String command) throws Exception {
			
		 	Runtime rt = Runtime.getRuntime();
			Process pcs = null;
			 BufferedReader br = null;
			 BufferedReader ebr = null;
			 int ret = -1;
			try {
				pcs = rt.exec(command);
				  br = new BufferedReader(new InputStreamReader(pcs.getInputStream()));
			      ebr = new BufferedReader(new InputStreamReader(pcs.getErrorStream()));
			      String line = new String();
			      while ( (line = br.readLine()) != null) {
			        System.out.println(line);
			      }
			      while ( (line = ebr.readLine()) != null) {
			        System.out.println(line);
			      }
			      pcs.waitFor();
			      ret = pcs.exitValue();
			      

			}
			 catch (InterruptedException ire) {
			      ire.printStackTrace();
			      throw new Exception();
			 }
			 catch (Exception e) {
			      e.printStackTrace();
			      throw new Exception();
			}
			 finally {
		      if (br != null) {
		        br.close();
		      }
		      if (ebr != null) {
		    	  ebr.close();
			      }
			 }
			 
			 if(ret==0){
				 System.out.println(command +"run success");
				 return true;
			 }else{
				 System.out.println(command +"run fail");
				 return false;
			 }
	 }
	 /**
		 * 执行shell命令
		 * @param command
		 * @return
		 * @throws Exception
		 */
		 private synchronized boolean executeShell(String[] command) throws Exception {
				
			 	Runtime rt = Runtime.getRuntime();
				Process pcs = null;
				 BufferedReader br = null;
				 BufferedReader ebr = null;
				 int ret = -1;
				try {
					pcs = rt.exec(command);
					  br = new BufferedReader(new InputStreamReader(pcs.getInputStream()));
				      ebr = new BufferedReader(new InputStreamReader(pcs.getErrorStream()));
				      String line = new String();
				      while ( (line = br.readLine()) != null) {
				        System.out.println(line);
				      }
				      while ( (line = ebr.readLine()) != null) {
				        System.out.println(line);
				      }
				      pcs.waitFor();
				      ret = pcs.exitValue();
				      

				}
				 catch (InterruptedException ire) {
				      ire.printStackTrace();
				      throw new Exception();
				 }
				 catch (Exception e) {
				      e.printStackTrace();
				      throw new Exception();
				}
				 finally {
			      if (br != null) {
			        br.close();
			      }
			      if (ebr != null) {
			    	  ebr.close();
				      }
				 }
				 
				 if(ret==0){
					 System.out.println(command +"run success");
					 return true;
				 }else{
					 System.out.println(command +"run fail");
					 return false;
				 }
		 }
	 /**
	  * 把读取完的文件移动到备份文件夹
	  *
	  */
	 public void excelFileBak(String fileD,String platForm, String newFileName) {

		String sep = System.getProperty("file.separator");
		// String fileDir = fileD+sep+"ISMP_BAK";
		String bakDir = fileD + sep + "FILE_BAK";
		File oldFile = new File(fileD+File.separator+platForm+".txt");
		if (oldFile.isFile()) {
			if (!(new File(bakDir).isDirectory())) {
				new File(bakDir).mkdirs();
			}
		}
		File newFile=new File(bakDir + sep + newFileName);
		if(newFile.exists())
			newFile.delete();
		oldFile.renameTo(newFile);
	 }
	 /**
	  * 把导入到数据库的文件移动到备份文件夹
	  *
	  */
	 /*public void excelFileBak(String res) {

		String sep = System.getProperty("file.separator");
		String fileDir = DcSystemParamManager.getParameter("FTP_COMPARE_FILE_PATH") + sep + res;

		String bakDir = fileDir + sep + "FILE_BAK";

		File f = new File(fileDir);
		try {
			File[] fs = f.listFiles();

			for (int i = 0; i < fs.length; ++i) {
				File oldFile = fs[i];
				if (oldFile.isFile()) {
					int index = oldFile.getName().lastIndexOf(".");

					String oldName = oldFile.getName().substring(0, index);
					if ("txt".equals(oldFile.getName().substring(index + 1,
							oldFile.getName().length()))) {
						System.out.println("oldFile:" + oldFile.getName());
						System.out.println("bak file:" + bakDir);
						if (!(new File(bakDir).isDirectory())) {
							new File(bakDir).mkdirs();
						}
						//		            FileOutputStream fos = null;
						//		            FileInputStream fis = null;
						//		            DataInputStream dataIn = null;
						try {
							System.out.println(bakDir + sep + oldName + ".txt");
							//		              String toDay = getToday();
							File newFile = new File(bakDir + sep + oldName
									+ ".txt");
							if (newFile.exists()) {
								newFile.delete();
							}
							oldFile.renameTo(newFile);

							//		              fos = new FileOutputStream(newFile);
							//
							//		              fis = new FileInputStream(oldFile);
							//		              dataIn = new DataInputStream(fis);
							//
							//		              byte[] buffer = new byte[1024];
							//		              int length = 0;
							//		              while ((length = dataIn.read(buffer)) != -1) {
							//		                fos.write(buffer, 0, length);
							//		              }
							//
							//		              if (dataIn != null)
							//		                dataIn.close();
							//
							//		              if (fis != null)
							//		                fis.close();
							//
							//		              if (fos != null) {
							//		                fos.close();
							//		              }
							//
							//		              OutputStream a = new FileOutputStream(oldFile, false);
							//		              a.write(new String("").getBytes());
							//		              a.close();
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	 
	 public static void main(String[] args){
		 try {
			new XFileSqlldrBatch().execSqlldrXtxt("D");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
