package com.ztesoft.component.file.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ztesoft.component.file.handlers.ConnectorHandler;
import com.ztesoft.component.file.handlers.PropertiesLoader;
import com.ztesoft.component.file.response.UploadResponse;
import com.ztesoft.component.file.tool.Utils;
import com.ztesoft.component.file.tool.UtilsFile;

public class ProcessBusinessData implements IProcess {
	private static long maxFileSize = 10000 * 1024;

	private ProcessBusinessData() {

	}

	public static ProcessBusinessData getInstance() {
		return SingletonHolder.instance;
	}

	static class SingletonHolder {
		static ProcessBusinessData instance = new ProcessBusinessData();
	}

	public UploadResponse processTxt(List fileItems, Map param,
			BusinessDataType type) throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		String str = null;
		List result = new ArrayList();
		Iterator it = fileItems.iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) it.next();
			BufferedReader read = new BufferedReader(new InputStreamReader(
					uplFile.getInputStream()));
			while ((str = read.readLine()) != null) {
				str = str.trim();
				if (!Utils.isEmpty(str))
					result.add(str);
			}
			read.close();
		}
		if (!result.isEmpty())
			execCommand(result, param, type);
		return null;
	}

	private void execCommand(List result, Map param, BusinessDataType type)
			throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		String className = PropertiesLoader.getProperty(type.getCommand());
		Class c = Class.forName(className);
		Object o = c.newInstance();
		TxtFileHandler fh = (TxtFileHandler) o;
		fh.processFile(result, param);
	}

	private XlsFileHandler getXlsFileHandler(String command)
			throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		String className = PropertiesLoader.getProperty(command);
		Class c = Class.forName(className);
		Object o = c.newInstance();
		XlsFileHandler fh = (XlsFileHandler) o;
		return fh;
	}

	public UploadResponse processExcel(List fileItems, Map param,
			BusinessDataType type) throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, BiffException {

		Iterator it = fileItems.iterator();

		XlsFileHandler fh = getXlsFileHandler(type.getCommand());

		while (it.hasNext()) {
			FileItem uplFile = (FileItem) it.next();
			InputStream is = uplFile.getInputStream() ;
			Workbook workBook = Workbook.getWorkbook(is);
			Sheet sheet = workBook.getSheet(0);
			//验证上传的exel文件符合要求	     
			if (!fh.validate(sheet))
				break;
			fh.processFile(sheet, param);
			
			workBook.close() ;
			is.close() ;
		}
		return null;
	}

	public UploadResponse process(HttpServletRequest request,
			HttpServlet servlet) throws ServletException, IOException,
			Exception {
		boolean isMultipart = FileUpload.isMultipartContent(request);
		if (!isMultipart)
			throw new ServletException("表单编码类型不是multipart/form-data");
		// 参数对象
		BusinessDataType type = ProcessType.getBusinessDataType(request);
		// 是文件上传
		UploadResponse ur = null;

		// 文件类型
		String modulePath = UtilsFile.constructServerSidePath(request, type
				.getModuleId());

		boolean isFullUrl = ConnectorHandler.isFullUrl();// true 绝对路径 、 false相对路径

		String moduleDirPath = isFullUrl ? modulePath : servlet
				.getServletContext().getRealPath(modulePath);

		File moduleDir = new File(moduleDirPath);
		UtilsFile.checkDirAndCreate(moduleDir);

		//		File currentDir = new File(moduleDir, "/");
		//
		//		if (!currentDir.exists())
		//			return UploadResponse.UR_INVALID_CURRENT_FOLDER;

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(moduleDir);

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);

		try {
			List items = upload.parseRequest(request);
			if (items == null || items.isEmpty())
				return null;
			Map param = new HashMap();//存储页面请求参数
			List fileItems = new ArrayList();//存储文件fieldItem

			Iterator filedItemIt = items.iterator();
			while (filedItemIt.hasNext()) {
				FileItem uplFile = (FileItem) filedItemIt.next();
				if (uplFile == null) {
					continue;
				}
				//获取请求参数
				if (uplFile.isFormField()) {
					param.put(uplFile.getName(), uplFile.getString());
					continue;
				}
				fileItems.add(uplFile);
			}
			if (fileItems.isEmpty())
				return null;

			if (BusinessDataType.TXT.equalsIgnoreCase(type.getFileType())) {
				processTxt(fileItems, param, type);
			} else if (BusinessDataType.EXCEL.equalsIgnoreCase(type
					.getFileType())) {
				processExcel(fileItems, param, type);
			}

		} catch (Exception e) {
			e.printStackTrace();
			ur = new UploadResponse(new Integer(
					UploadResponse.SC_SECURITY_ERROR));
		}
		return ur;
	}
}
