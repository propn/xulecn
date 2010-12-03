package com.ztesoft.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * Ftp����������
 * 
 * @author easonwu 
 *
 */
public class FtpUtil {

	private  String userName = "bi";
	private  String passWord = "bi";
	private  String hostName = "10.40.197.41" ;
	private  String port = "" ;
	private  String workDir = "test/download" ;
//	private  String workDir = "test/upload" ;
//	private  String workDir = "" ;
	private  FTPClient ftpClient = new FTPClient();
	

	public FtpUtil() throws IOException {
		initailCheck() ;
	}
	
	public FtpUtil(String hostName , String port , String userName 
			, String passWord , String workDir) throws IOException {
		this.hostName = hostName ;
		this.port = port ;
		this.userName = userName ;
		this.passWord = passWord ;
		this.workDir = workDir ;
		initailCheck() ;
	}
	
	private void initailCheck() throws IOException {
		connectToServer();
		if( this.workDir != null && !"".endsWith(this.workDir )){
			checkPathExist(this.workDir);
		}
		closeConnect();
	}

	/** 
	 * ����ָ��Ŀ¼�Ƿ���� 
	 * @param String filePath Ҫ���ҵ�Ŀ¼ 
	 * @return boolean:����:true��������:false 
	 * @throws IOException 
	 */
	private  boolean checkPathExist(String filePath) throws IOException {
		boolean existFlag = false;
		try {
			if (!ftpClient.changeWorkingDirectory(filePath)) {
				ftpClient.makeDirectory(filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existFlag;
	}

	/** 
	 * ���ӵ�ftp������ 
	 */
	private  void connectToServer() {
		if (!ftpClient.isConnected()) {
			int reply;
			try {
				ftpClient = new FTPClient();
			
				if(this.port == null || "".equals(this.port)){
					ftpClient.connect(this.hostName);
				}else{
					ftpClient.connect(this.hostName , Integer.parseInt(this.port)) ;
				}

				ftpClient.login(userName, passWord);
				reply = ftpClient.getReplyCode();

				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
					System.err.println("FTP server refused connection.");
				}
			} catch (Exception e) {
				System.err.println("��¼ftp��������" + this.hostName + "��ʧ��");
				e.printStackTrace();
			}
		}
	}

	/** 
	 * �ر����� 
	 */
	private  void closeConnect() {
		try {
			if (ftpClient != null) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 
	 * ת��[GBK ->  ISO-8859-1] 
	 * ��ͬ��ƽ̨��Ҫ��ͬ��ת�� 
	 * @param obj 
	 * @return 
	 */
	private static String gbkToIso8859(Object obj) {
		try {
			if (obj == null)
				return "";
			else
				return new String(obj.toString().getBytes("GBK"), "iso-8859-1");
		} catch (Exception e) {
			return "";
		}
	}

	/** 
	 * ת��[ISO-8859-1 ->  GBK] 
	 * ��ͬ��ƽ̨��Ҫ��ͬ��ת�� 
	 * @param obj 
	 * @return 
	 */
	private static String iso8859ToGbk(Object obj) {
		try {
			if (obj == null)
				return "";
			else
				return new String(obj.toString().getBytes("iso-8859-1"), "GBK");
		} catch (Exception e) {
			return "";
		}
	}

	/** 
	 * ���ô����ļ�������[�ı��ļ����߶������ļ�] 
	 * @param fileType--BINARY_FILE_TYPE��ASCII_FILE_TYPE 
	 */
	private  void setFileType(int fileType) {
		try {
			ftpClient.setFileType(fileType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void changeDir(String filePath) throws IOException {
//		��ת��ָ�����ļ�Ŀ¼ 
		if (filePath != null && !filePath.equals("")) {
			if (filePath.indexOf("/") != -1) {
				int index = 0;
				while ((index = filePath.indexOf("/")) != -1) {
					System.out.println("P:"+ filePath.substring(0,
							index)) ;
					ftpClient.changeWorkingDirectory(filePath.substring(0,
							index));

					filePath = filePath.substring(index + 1, filePath.length());
				}
				if (!filePath.equals("") && !"/".equals(filePath)) {
					ftpClient.changeWorkingDirectory(filePath);
				}
			} else {
				ftpClient.changeWorkingDirectory(filePath);
			}
		}
	}
	/**
	 * check file 
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private  boolean checkFileExist(String filePath, String fileName)
			throws IOException {
		boolean existFlag = false;
		changeDir( filePath )  ;
		String[] fileNames = ftpClient.listNames();
		if (fileNames != null && fileNames.length > 0) {
			for (int i = 0; i < fileNames.length; i++) {
				System.out.println("File:" + iso8859ToGbk(fileNames[i])) ;
				if (fileNames[i] != null
						&& iso8859ToGbk(fileNames[i]).equals(fileName)) {
					existFlag = true;
					break;
				}
			}
		}
		ftpClient.changeToParentDirectory();
		return existFlag;
	}

	/**
	 * download ftp file as inputstream 
	 * @param remoteFilePath 
	 * @param remoteFileName
	 * @return
	 * @throws IOException
	 */
	public  InputStream downloadFile(
			String remoteFileName) throws IOException {
		InputStream returnValue = null;
		//�����ļ� 
		BufferedOutputStream buffOut = null;
		try {
			//����ftp������ 
			connectToServer();
			if (!checkFileExist(this.workDir, remoteFileName)) {
				System.out.println("<----------- ERR : file  " + this.workDir	+ "/" + remoteFileName+ " does not exist, download failed!----------->");
				return null;
			} else {
				changeDir( this.workDir )  ;
				String[] fileNames = ftpClient.listNames();
				
				//���ô���������ļ� 
				setFileType(FTP.BINARY_FILE_TYPE);
				//��÷������ļ� 
				returnValue = ftpClient.retrieveFileStream(remoteFileName);
				//������������Ϣ 
				if (returnValue !=null ) {
					System.out.println("<----------- INFO: download "+ this.workDir + "/" + remoteFileName+ " from ftp �� succeed! ----------->");
				} else {
					System.out.println("<----------- ERR : download "+ this.workDir + "/" + remoteFileName+ " from ftp : failed! ----------->");
				}
			}
			//�ر����� 
			closeConnect();
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = null;
			//������������Ϣ 
			System.out.println("<----------- ERR : download " +  this.workDir + "/" + remoteFileName+ " from ftp : failed! ----------->");
		} finally {
			try {
				if (ftpClient.isConnected()) {
					closeConnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}
	
	/**
	 * 
	 * @param remoteFilePath
	 * @param remoteFileName
	 * @param localFileName
	 * @return
	 * @throws IOException
	 */
	public  boolean downloadFile(String remoteFilePath,
			String remoteFileName, String localFileName) throws IOException {
		boolean returnValue = false;
		//�����ļ� 
		BufferedOutputStream buffOut = null;
		try {
			//����ftp������ 
			connectToServer();
			File localFile = new File(localFileName.substring(0, localFileName
					.lastIndexOf("/")));
			if (!localFile.exists()) {
				localFile.mkdirs();
			}
			if (!checkFileExist(remoteFilePath, remoteFileName)) {
				System.out.println("<----------- ERR : file  " + remoteFilePath	+ "/" + remoteFileName+ " does not exist, download failed!----------->");
				return false;
			} else {
				changeDir( remoteFilePath )  ;
				String[] fileNames = ftpClient.listNames();
				
				//���ô���������ļ� 
				setFileType(FTP.BINARY_FILE_TYPE);
				//��÷������ļ� 
				buffOut = new BufferedOutputStream(new FileOutputStream(
						localFileName));
				returnValue = ftpClient.retrieveFile(
						remoteFileName, buffOut);
				//������������Ϣ 
				if (returnValue) {
					System.out.println("<----------- INFO: download "+ remoteFilePath + "/" + remoteFileName+ " from ftp �� succeed! ----------->");
				} else {
					System.out.println("<----------- ERR : download "+ remoteFilePath + "/" + remoteFileName+ " from ftp : failed! ----------->");
				}
			}
			//�ر����� 
			closeConnect();
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = false;
			//������������Ϣ 
			System.out.println("<----------- ERR : download " + remoteFilePath+ "/" + remoteFileName+ " from ftp : failed! ----------->");
		} finally {
			try {
				if (buffOut != null) {
					buffOut.close();
				}
				if (ftpClient.isConnected()) {
					closeConnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}
	
	/**
	 * 
	 * @param remoteFileNameList
	 * @return
	 * @throws IOException
	 */
	public  List batchDownloadFile(
			List remoteFileNameList) throws IOException {
		InputStream resultIs = null ;
		List inputStreamList = null ;
		String remoteFileName = null ;
		
		if( remoteFileNameList == null ||remoteFileNameList.isEmpty() ) return null ;
			
		inputStreamList = new ArrayList() ;
		for( Iterator it = remoteFileNameList.iterator() ; it.hasNext() ; ) {
			remoteFileName = (String)it.next() ;
			resultIs = this.downloadFile(remoteFileName);
			if (resultIs != null ) {
				inputStreamList.add(resultIs) ;
			} 
		}
		return inputStreamList;
	}
	
	
	/**
	 * ɾ�����������ļ�
	 * 
	 * @param fileDir
	 *            �ļ�·��
	 * @param fileName
	 *            �ļ�����
	 * @throws IOException
	 */
	private  boolean delFile(String fileDir, String fileName)
			throws IOException {
		boolean returnValue = false;
		try {
			//����ftp������ 
			connectToServer();
			//��ת��ָ�����ļ�Ŀ¼ 
			if (fileDir != null) {
				if (fileDir.indexOf("/") != -1) {
					int index = 0;
					while ((index = fileDir.indexOf("/")) != -1) {
						ftpClient.changeWorkingDirectory(fileDir.substring(0,
								index));
						fileDir = fileDir
								.substring(index + 1, fileDir.length());
					}
					if (!fileDir.equals("")) {
						ftpClient.changeWorkingDirectory(fileDir);
					}
				} else {
					ftpClient.changeWorkingDirectory(fileDir);
				}
			}
			//���ô���������ļ� 
			setFileType(FTP.BINARY_FILE_TYPE);
			//��÷������ļ� 
			returnValue = ftpClient.deleteFile(fileName);
			//�ر����� 
			closeConnect();
			//������������Ϣ 
			if (returnValue) {
				System.out.println("<----------- INFO: delete " + fileDir + "/"
						+ fileName + " at ftp:succeed! ----------->");
			} else {
				System.out.println("<----------- ERR : delete " + fileDir + "/"
						+ fileName + " at ftp:failed! ----------->");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = false;
			//������������Ϣ 
			System.out.println("<----------- ERR : delete " + fileDir + "/"
					+ fileName + " at ftp:failed! ----------->");
		} finally {
			try {
				if (ftpClient.isConnected()) {
					closeConnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	/**
	 * upload file to ftp 
	 * @param uploadFile
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(String uploadFile , String fileName ) throws IOException{
		if(uploadFile == null || "".equals(uploadFile.trim())){
			System.out.println("<----------- ERR :  uploadFile:" + uploadFile
					+ " is null , upload failed! ----------->");
			return false ;
		}
		return this.uploadFile(new File(uploadFile) , fileName ) ;
	}
	
	
	/**
	 * batch upload files 
	 * @param uploadFiles
	 * @return
	 * @throws IOException
	 */
	public boolean batchUploadFile(List uploadFileList ) throws IOException{
		if(uploadFileList == null || uploadFileList.isEmpty()){
			System.out.println("<----------- ERR :  batchUploadFile failed! because the file list is empty ! ----------->");
			return false ;
		}
		
		for( Iterator it = uploadFileList.iterator() ; it.hasNext() ;){
			File uploadFile = (File)it.next() ;
			if( !uploadFile(uploadFile , uploadFile.getName() ) ){
				System.out.println("<----------- ERR :  upload file��"+uploadFile.getName()+"��  failed! ----------->") ;
				return false ;
			}
		}
		return true ;
	}
	
	/**
	 * upload file to ftp 
	 * @param uploadFile
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public  boolean uploadFile(File uploadFile, String fileName)
			throws IOException {
		if (!uploadFile.exists()) {
			System.out.println("<----------- ERR : an named " + fileName
					+ " not exist, upload failed! ----------->");
			return false;
		} 
		return this.uploadFile(new FileInputStream(uploadFile) , fileName ) ;
	}

	/**
	 * upload file to ftp 
	 * @param is
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(InputStream is , String fileName ) throws IOException {
		boolean returnValue = false;
		// �ϴ��ļ�
		BufferedInputStream buffIn = null;
		try {
		
				// ��������
				connectToServer();
				// ���ô���������ļ�
				setFileType(FTP.BINARY_FILE_TYPE);
				// ����ļ�
				buffIn = new BufferedInputStream(is);
				// �ϴ��ļ���ftp
				returnValue = ftpClient.storeFile(gbkToIso8859(this.workDir + "/"
						+ fileName), buffIn);
				// ������������Ϣ
				if (returnValue) {
					System.out.println("<----------- INFO: upload file  to ftp : succeed! ----------->");
				} else {
					System.out.println("<----------- ERR : upload file  to ftp : failed! ----------->");
				}
				// �ر�����
				closeConnect();
			
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = false;
			System.out.println("<----------- ERR : upload file  to ftp : failed! ----------->");
		} finally {
			try {
				if (buffIn != null) {
					buffIn.close();
				}
				if (ftpClient.isConnected()) {
					closeConnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue ;
	}
	
	
	  public boolean changeDirectory(String path) throws IOException {   
		  return ftpClient.changeWorkingDirectory(path);   
	    }   
	    public boolean createDirectory(String pathName) throws IOException {   
	        return ftpClient.makeDirectory(pathName);   
	    }   
	    public boolean removeDirectory(String path) throws IOException {   
	        return ftpClient.removeDirectory(path);   
	    } 
	    // delete all subDirectory and files.   
	    public boolean removeDirectory(String path, boolean isAll)   
	            throws IOException {   
	           
	        if (!isAll) {   
	            return removeDirectory(path);   
	        }   
	  
	        FTPFile[] ftpFileArr = ftpClient.listFiles(path);   
	        if (ftpFileArr == null || ftpFileArr.length == 0) {   
	            return removeDirectory(path);   
	        }   
	        //    
	        for (int i=ftpFileArr.length ; i>0 ; i-- ) {   
	        	FTPFile ftpFile = ftpFileArr[i-1] ; 
	            String name = ftpFile.getName();   
	            if (ftpFile.isDirectory()) {   
	System.out.println("* [sD]Delete subPath ["+path + "/" + name+"]");                
	                removeDirectory(path + "/" + name, true);   
	            } else if (ftpFile.isFile()) {   
	System.out.println("* [sF]Delete file ["+path + "/" + name+"]");                           
	                deleteFile(path + "/" + name);   
	            } else if (ftpFile.isSymbolicLink()) {   
	  
	            } else if (ftpFile.isUnknown()) {   
	  
	            }   
	        }   
	        return ftpClient.removeDirectory(path);   
	    }   

	    public boolean deleteFile(String pathName) throws IOException {   
	        return ftpClient.deleteFile(pathName);   
	    }   
	    
	    public List getFileList(String path) throws IOException {   
	        FTPFile[] ftpFiles= ftpClient.listFiles(path);   
	           
	        List retList = new ArrayList();   
	        if (ftpFiles == null || ftpFiles.length == 0) {   
	            return retList;   
	        }   
	        for (int i=ftpFiles.length ; i>0 ; i-- ) {   
	        	FTPFile ftpFile = ftpFiles[i-1] ;
	        	  if (ftpFile.isFile()) {   
		                retList.add(ftpFile.getName());   
		            }   
	        }
	           
	        return retList;   
	    } 
	    
	public static void main(String[] args ) {

		try{
			FtpUtil ftp = new FtpUtil() ;

//			boolean isOK = ftp.checkFileExist("/test/download/" , "900120090416.dbf") ;
//			System.out.println("hi:"+isOK) ;
//			ftp.connectToServer();
//			File uploadFile = new File("C:\\uploadFile\\c.sql") ;
//			ftp.uploadFile(uploadFile, "c3.sql") ;
//			ftp.downloadFile("900520090420.dbf") ;
			List t = new ArrayList() ;
			t.add("900520090420.dbf") ;
			t.add("900220090420.dbf") ;
			ftp.batchDownloadFile(t)  ;
//			ftp.downloadFile(ftp.workDir , "900520090420.dbf" , "C:/900520090420.dbf") ;
//			ftp.downloadFile(ftp.workDir , "900220090420.dbf" , "C:/900220090420.dbf") ;
//			ftp.uploadFile( "C:/900120090416.dbf" , "900120090416.dbf") ;
//			ftp.closeConnect();
		}catch(Exception e ){
			e.printStackTrace() ;
		}
	}
}
