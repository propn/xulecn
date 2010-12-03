package com.ztesoft.component.file.business;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.ztesoft.component.file.tool.Utils;

public class ProcessType {
	
	public static final String defaultProcessType = "commonFile" ;//默认为普通附件上传操作
	
	public static final String BusinessDataType = "businessData" ;//默认为普通附件上传操作
	
	public static BusinessDataType getBusinessDataType(HttpServletRequest request) throws ServletException {
		if(Utils.isEmpty(request.getParameter("command")))
			throw new ServletException("参数[command]不能为空 ！") ;
		if(Utils.isEmpty(request.getParameter("fileType")))
			throw new ServletException("参数[fileType]不能为空 ！") ;
		if(Utils.isEmpty(request.getParameter("moduleId")))
			throw new ServletException("参数[moduleId]不能为空 ！") ;
		
		BusinessDataType type = new BusinessDataType() ;
		type.setCommand(request.getParameter("command")) ;
		type.setFileType(request.getParameter("fileType")) ;
		type.setModuleId(request.getParameter("moduleId")) ;
		
		return type ;
	}
	
	public static CommonFileType getCommonFileType(HttpServletRequest request) throws ServletException{
		
		if(Utils.isEmpty(request.getParameter("command")))
			throw new ServletException("参数[command]不能为空 ！") ;
		if(Utils.isEmpty(request.getParameter("moduleId")))
			throw new ServletException("参数[moduleId]不能为空 ！") ;
		
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
