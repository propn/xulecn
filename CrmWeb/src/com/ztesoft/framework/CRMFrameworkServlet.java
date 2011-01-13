package com.ztesoft.framework;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.buffalo.request.RequestContextUtil;
import net.buffalo.request.RequestWorker;
import net.buffalo.view.ViewWorker;
import net.buffalo.web.servlet.ApplicationServlet;
import net.buffalo.web.upload.UploadWorker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ztesoft.common.exception.CommonError;
import com.ztesoft.common.exception.CommonException;
import com.ztesoft.common.exception.CrmException;
import com.ztesoft.common.exception.ExceptionVO;

public class CRMFrameworkServlet extends ApplicationServlet{
	private static final Log LOG = LogFactory.getLog(ApplicationServlet.class);
	private static final String OUTPUT_ENCODING =  "utf-8";
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	/**
	 * 新增扩展方法 解决HttpClient等方式 获取不到POST内容bug
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 *             add by easonwu 2009-11-03
	 * 
	 */
	protected void doRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {// 增加异常处理
			request.setCharacterEncoding("GBK");
			response.setContentType("text/html;charset=GBK");
			 //RequestContextUtil.createRequestContext(getServletContext(),
			 //request, response); //此处存在bug ，需要在每个worker里面设置
			String pathInfo = request.getPathInfo();
			
			
			LOG.debug("request path info: " + pathInfo);
			//RequestWorker worker = null;
			if (pathInfo == null || pathInfo.equals("/")) {
				RequestWorker worker = new ViewWorker();
				worker.validate(request, response);
				worker.processRequest(request, response);
			} else if (pathInfo.startsWith("/view/")) {
				RequestWorker worker = new ViewWorker();
				worker.validate(request, response);
				worker.processRequest(request, response);
			} else if (pathInfo.startsWith("/buffalo/")) {
				CRMRequestWorker worker = new CRMFrameworkBuffaloWorker();
				worker.initRequest(getServletContext(), request, response);
				worker.validate(request, response);
				worker.processRequest(getServletContext(),request, response);
			} else if (pathInfo.startsWith("/upload/")) {
				RequestWorker worker = new UploadWorker();
				worker.validate(request, response);
				worker.processRequest(request, response);
			} else {
				throw new ServletException("Cannot find the request worker!");
			}
//			RequestContextUtil.updateRequestContext(getServletContext(), request, response);
			
			/*if (pathInfo != null && !"".equals(pathInfo)&& pathInfo.startsWith("/buffalo/")) {
				((CRMFrameworkBuffaloWorker)worker).processRequest(getServletContext(),
						request, response);
			} else {
				worker.processRequest(request, response);
			}*/
		} catch (Throwable ex) {

			ex.printStackTrace();
			/*LOG.error("An exception occured when invoking a service: ", ex);
			StringWriter writer = new StringWriter();
			ex.printStackTrace(new PrintWriter(writer));
			StringBuffer faultString = new StringBuffer();
			faultString.append("An exception occured when invoking a service. \n");
			faultString.append(writer.toString());
			throw new ServiceInvocationException(faultString.toString(), ex);*/
			
			Throwable  cause = ex;
			CrmException crmEx = null;
	
			if (cause instanceof CrmException) {
				crmEx = (CrmException) cause;
			} else {
				CommonError error = new CommonError(CommonError.COMMON_ERROR) ;
				crmEx = new CommonException(error, ex);
			}
			ExceptionVO vo = new ExceptionVO(crmEx);
			StringBuffer exceptionBuffer = new StringBuffer();
			exceptionBuffer.append("<buffalo-reply><m><t>").append(vo.getClass().getName()).append("</t><s>errorCode</s><s>");
			exceptionBuffer.append(vo.getErrorCode()).append("</s><s>errorMessage</s><s><![CDATA[").append(vo.getErrorMessage());
			exceptionBuffer.append("]]></s><s>errorResolve</s><null></null><s>level</s><i>").append(vo.getLevel());
			exceptionBuffer.append("</i><s>stackInfo</s><s><![CDATA[").append(vo.getStackInfo()).append("]]></s></m></buffalo-reply>");
			response.setContentType("text/html;charset=GBK");
			OutputStreamWriter w = new OutputStreamWriter(response.getOutputStream());
			w.write(exceptionBuffer.toString());
			w.flush();
			
		}
	}
}
