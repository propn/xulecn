package com.ztesoft.component.file.business;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.ztesoft.component.file.tool.Utils;

public class ProcessType {
	
	public static final String defaultProcessType = "commonFile" ;//Ĭ��Ϊ��ͨ�����ϴ�����
	
	public static final String BusinessDataType = "businessData" ;//Ĭ��Ϊ��ͨ�����ϴ�����
	
	public static BusinessDataType getBusinessDataType(HttpServletRequest request) throws ServletException {
		if(Utils.isEmpty(request.getParameter("command")))
			throw new ServletException("����[command]����Ϊ�� ��") ;
		if(Utils.isEmpty(request.getParameter("fileType")))
			throw new ServletException("����[fileType]����Ϊ�� ��") ;
		if(Utils.isEmpty(request.getParameter("moduleId")))
			throw new ServletException("����[moduleId]����Ϊ�� ��") ;
		
		BusinessDataType type = new BusinessDataType() ;
		type.setCommand(request.getParameter("command")) ;
		type.setFileType(request.getParameter("fileType")) ;
		type.setModuleId(request.getParameter("moduleId")) ;
		
		return type ;
	}
	
	public static CommonFileType getCommonFileType(HttpServletRequest request) throws ServletException{
		
		if(Utils.isEmpty(request.getParameter("command")))
			throw new ServletException("����[command]����Ϊ�� ��") ;
		if(Utils.isEmpty(request.getParameter("moduleId")))
			throw new ServletException("����[moduleId]����Ϊ�� ��") ;
		
		CommonFileType type = new CommonFileType() ;
		type.setCommand(request.getParameter("command")) ;
		type.setModuleId(request.getParameter("moduleId")) ;
		
		type.setFtp(request.getParameter("ftp")) ;
		type.setStore(request.getParameter("store")) ;
		type.setTempDir(request.getParameter("tempDir")) ;
		return type ;
	}
	
	private static String getProcessType(HttpServletRequest request){
		String processType = request.getParameter("processType") ;
		return processType == null || "".equals(processType.trim()) ? 
				defaultProcessType : processType.trim() ;
	}
}
