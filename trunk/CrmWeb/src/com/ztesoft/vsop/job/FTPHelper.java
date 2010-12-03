package com.ztesoft.vsop.job;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class FTPHelper {
	
	private static Logger log = Logger.getLogger(FTPHelper.class);
	
	public FTPClient connectCrmFtp() throws NumberFormatException, SocketException, IOException {
		FTPClient ftp = new FTPClient();
		String crmFtp = DcSystemParamManager.getParameter(VsopConstants.CRM_FTP_INFO);
//		String crmFtp = "csss:csss@134.224.4.69:21";//user:password@ip:port
		
		String userAndPassword = crmFtp.split("@")[0];
		String hostNameAndPort = crmFtp.split("@")[1];
		if(hostNameAndPort == null || hostNameAndPort.length()==0){
			log.error("没有ftp的主机ip地址信息!");
			return null;
		}
		
		String hostName = hostNameAndPort.split(":")[0];
		String port = "";
		if(hostNameAndPort.indexOf(":")>0){
			port = hostNameAndPort.split(":")[1];
		}
		String userName = userAndPassword.split(":")[0];
		if(userName == null || userName.length()==0){
			log.error("没有ftp的登录用户名信息!");
			return null;
		}
		String password = userAndPassword.split(":")[1];
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
		if (!ftp.login(userName, password)) {
			ftp.logout();
			log.error("userName or password incorrect");
			return null;
		}
		return ftp;
	}

	public static void main(String[] args){
		try {
			FTPClient ftp = new FTPHelper().connectCrmFtp();
			String[] fileNames = ftp.listNames("/home/csss/infismp/ftp/back/PRODINFO/15");
			for (int i = 0; i < fileNames.length; i++) {
				String name = fileNames[i];
				System.out.println(name);
			}
			System.out.println(System.currentTimeMillis());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
