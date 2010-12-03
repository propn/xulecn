package com.ztesoft.component.file.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ztesoft.component.file.business.ProcessBusinessData;
import com.ztesoft.component.file.business.ProcessCommonFile;
import com.ztesoft.component.file.business.ProcessType;
import com.ztesoft.component.file.handlers.ConnectorHandler;
import com.ztesoft.component.file.response.UploadResponse;
import com.ztesoft.component.file.tool.UtilsFile;

public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = -5742008970929377161L;

	private static final Logger logger = LoggerFactory
			.getLogger(FileUploadServlet.class);

	public static final String getStrDate(int int_dateFormat) {
		String[] dateFormat = { "yyyyMMddHHmmssSSS", "yyyyMMddHHmmss",
				"yyMMddHHmmss", "yyyyMMdd", "yyyy-MM-dd", "HHmmssSSS", "HHmmss" };
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat(dateFormat[int_dateFormat]);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sdf.format(new Date());
	}


	/**
	 * Initialize the servlet: <code>mkdir</code> &lt;DefaultUserFilesPath&gt;
	 */
	public void init() throws ServletException, IllegalArgumentException {
		String realDefaultUserFilesPath = getServletContext().getRealPath(
				ConnectorHandler.getDefaultUserFilesPath());

		File defaultUserFilesDir = new File(realDefaultUserFilesPath);
		UtilsFile.checkDirAndCreate(defaultUserFilesDir);

		logger.info("ConnectorServlet successfully initialized!");
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost( request,  response) ;
	}

	

	

	/**
	 * Manage the <code>POST</code> requests (<code>FileUpload</code>).<br />
	 * 
	 * The servlet accepts commands sent in the following format:<br />
	 * <code>connector?Command=&lt;FileUpload&gt;&Type=&lt;ResourceType&gt;&CurrentFolder=&lt;FolderPath&gt;</code>
	 * with the file in the <code>POST</code> body.<br />
	 * <br>
	 * It stores an uploaded file (renames a file if another exists with the
	 * same name) and then returns the JavaScript callback.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("Entering Connector#doPost");

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart)
			throw new ServletException("表单缺失multipart/form-data编码类型声明");

		request.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		UploadResponse ur = null ;
		String processType = request.getParameter("processType");
		
		if (ProcessType.defaultProcessType.equalsIgnoreCase(processType)) {
			try {
				ur = ProcessCommonFile.getInstance().process(request , this) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ProcessType.BusinessDataType.equalsIgnoreCase(processType)) {
			try {
				ur = ProcessBusinessData.getInstance().process(request , this) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new ServletException("参数[processType]不能为空 !");
		}
		out.print(ur);
		out.flush();
		out.close();

		logger.debug("Exiting Connector#doPost");
	}

}
