package com.ztesoft.component.file.business;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.ztesoft.component.file.handlers.CommandHandler;
import com.ztesoft.component.file.handlers.ConnectorHandler;
import com.ztesoft.component.file.handlers.RequestCycleHandler;
import com.ztesoft.component.file.handlers.ResourceTypeHandler;
import com.ztesoft.component.file.response.UploadResponse;
import com.ztesoft.component.file.servlet.Messages;
import com.ztesoft.component.file.tool.Utils;
import com.ztesoft.component.file.tool.UtilsFile;
import com.ztesoft.component.file.tool.UtilsResponse;

/**
 * 
 * @author easonwu
 *
 */
public class ProcessCommonFile implements IProcess {
	private static long maxFileSize = 10000 * 1024;
	
	private ProcessCommonFile() {

	}

	public static ProcessCommonFile getInstance() {
		return SingletonHolder.instance;
	}

	static class SingletonHolder {
		static ProcessCommonFile instance = new ProcessCommonFile();
	}

	public UploadResponse process(HttpServletRequest request,
			HttpServlet servlet) throws ServletException, IOException,
			Exception {

		// 参数对象
		CommonFileType type = ProcessType.getCommonFileType(request);
		
		UploadResponse ur = null;
		//UploadResponse ur = validate(request, param);// 验证

		// 文件类型
		String modulePath = UtilsFile.constructServerSidePath(request, type
				.getModuleId());

		boolean isFullUrl = ConnectorHandler.isFullUrl();// true 绝对路径 、 false相对路径

		String moduleDirPath = isFullUrl ? modulePath : servlet.getServletContext()
				.getRealPath(modulePath);

		File moduleDir = new File(moduleDirPath);
		UtilsFile.checkDirAndCreate(moduleDir);

		File currentDir = new File(moduleDir, type.getTempDir());

		if (!currentDir.exists())
			return UploadResponse.UR_INVALID_CURRENT_FOLDER;

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);
		
		try {
			List items = upload.parseRequest(request);
			if (items == null || items.isEmpty())
				return null;
			Iterator filedItemIt = items.iterator();
			while (filedItemIt.hasNext()) {
				FileItem uplFile = (FileItem) filedItemIt.next();
				if (uplFile == null || uplFile.isFormField())
					continue;
				processACommonFile(uplFile, request, type, ur ,  currentDir , moduleDirPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ur = new UploadResponse(new Integer(
					UploadResponse.SC_SECURITY_ERROR));
		}
		return ur;

	}

	/**
	 * 信息验证
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	private UploadResponse validate(HttpServletRequest request, Map param) {
		String command = (String) param.get("command");
		String type = (String) param.get("type");
		String currentFolder = (String) param.get("currentFolder");

		UploadResponse ur = null;
		if (!RequestCycleHandler.isEnabledForFileUpload(request))
			ur = new UploadResponse(new Integer(
					UploadResponse.SC_SECURITY_ERROR), null, null,
					Messages.NOT_AUTHORIZED_FOR_UPLOAD);
		else if (!CommandHandler.isValidForPost(command))
			ur = new UploadResponse(new Integer(UploadResponse.SC_ERROR), null,
					null, Messages.INVALID_COMMAND);
		else if (type != null && !ResourceTypeHandler.isValid(type))
			ur = new UploadResponse(new Integer(UploadResponse.SC_ERROR), null,
					null, Messages.INVALID_TYPE);
		else if (!UtilsFile.isValidPath(currentFolder))
			ur = UploadResponse.UR_INVALID_CURRENT_FOLDER;
		return ur;
	}

	/**
	 * 
	 * 
	 * 普通文件上传，处理动作有： 1. 存储到指定目录 2. 上传到FTP 3. 把文件放置到数据库 4. 存储文件相关信息
	 * 
	 * 需要用到的参数:
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
//	public UploadResponse processCommonFile(HttpServletRequest request,
//			CommonFileType type) {
//		UploadResponse ur = null;
//
//		FileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//
//		try {
//			List items = upload.parseRequest(request);
//			if (items == null || items.isEmpty())
//				return null;
//			Iterator filedItemIt = items.iterator();
//			while (filedItemIt.hasNext()) {
//				FileItem uplFile = (FileItem) filedItemIt.next();
//				if (uplFile == null || uplFile.isFormField())
//					continue;
//				processACommonFile(uplFile, request, type, ur);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ur = new UploadResponse(new Integer(
//					UploadResponse.SC_SECURITY_ERROR));
//		}
//		return ur;
//	}

	public UploadResponse processACommonFile(FileItem uplFile,
			HttpServletRequest request, CommonFileType type, UploadResponse ur ,
			File currentDir , String currentFolder)
			throws IllegalArgumentException, IOException, Exception {
		// We upload only one file at the same time

		long test = uplFile.getSize();
		String newFilename = null;

		String rawName = UtilsFile.sanitizeFileName(uplFile.getName());
		String filename = FilenameUtils.getName(rawName);
		String baseName = FilenameUtils.removeExtension(filename);
		String extension = FilenameUtils.getExtension(filename);

		//		ResourceTypeHandler resourceType = (ResourceTypeHandler) param
		//				.get("resourceType");
//		String currentFolder = (String) param.get("currentFolder");
//		File currentDir = (File) param.get("currentDir");

		//		if (!ExtensionsHandler.isAllowed(resourceType, extension))
		//			return new UploadResponse(new Integer(
		//					UploadResponse.SC_INVALID_EXTENSION));

		// construct an unique file name
		File pathToSave = new File(currentDir, filename);
		int counter = 1;
		while (pathToSave.exists()) {
			newFilename = baseName.concat("(").concat(String.valueOf(counter))
					.concat(")").concat(".").concat(extension);
			pathToSave = new File(currentDir, newFilename);
			counter++;
		}
		if (Utils.isEmpty(newFilename))
			ur = new UploadResponse(new Integer(UploadResponse.SC_OK),
					UtilsResponse.constructResponseUrl(request, type.getModuleId(),
							currentFolder, true, ConnectorHandler.isFullUrl())
							.concat(filename));
		else
			ur = new UploadResponse(new Integer(UploadResponse.SC_RENAMED),
					UtilsResponse.constructResponseUrl(request,  type.getModuleId(),
							currentFolder, true, ConnectorHandler.isFullUrl())
							.concat(newFilename), newFilename);

		// secure image check
		//		if (resourceType.equals(ResourceTypeHandler.IMAGE)
		//				&& ConnectorHandler.isSecureImageUploads()) {
		//			if (UtilsFile.isImage(uplFile.getInputStream()))
		//				uplFile.write(pathToSave);
		//			else {
		//				uplFile.delete();
		//				ur = new UploadResponse(new Integer(
		//						UploadResponse.SC_INVALID_EXTENSION));
		//			}
		//		} else{
		uplFile.write(pathToSave);
		//		}

		return ur;
	}

}
