package com.ztesoft.vsop.order.manager.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.buffalo.request.RequestContext;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.powerise.ibss.framework.Const;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.order.manager.dao.BatchImportRelationDAO;

public class BatchUpload extends HttpServlet {
	private static final Logger log = Logger.getLogger(BatchUpload.class);

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// ʵ����һ��Ӳ���ļ�����,���������ϴ����ServletFileUpload
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		dfif.setSizeThreshold(4096); // �����ϴ��ļ�ʱ������ʱ����ļ����ڴ��С,������4K.���ڵĲ��ֽ���ʱ����Ӳ��
		ServletFileUpload upload = new ServletFileUpload(dfif);
		//final long MAX_SIZE = 3 * 1024 * 1024;// �����ϴ��ļ����Ϊ 3M
		//upload.setSizeMax(MAX_SIZE);

		BatchImportRelationDAO dao = new BatchImportRelationDAO();
		List items = null;
		List ls = null;
		String errorInfo = "";
		boolean flag = true;
		BufferedReader reader = null;
		try {
			items = upload.parseRequest(request);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			errorInfo = "�ļ��ϴ��쳣 :" + e.getMessage();
		}
		if (flag) {
			try {
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (!item.isFormField()) {
						InputStream is = item.getInputStream();
						InputStreamReader ir = new InputStreamReader(is);
						reader = new BufferedReader(ir);
						ls = extractListFromReader(reader);
						is.close();
						ir.close();
						reader.close();
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				flag = false;
				errorInfo = "�������ݵ�������:" + e.getMessage();
			}
		}
		String batch = "";
		String userId = "";
		if (flag) {
			try {
				Map userinfo = (Map) request.getSession().getAttribute(
						"LoginRtnParamsHashMap");
				userId = Const.getStrValue(userinfo, "vg_oper_id");
			} catch (Exception e) {
				flag = false;
				errorInfo = "�û���½״̬�쳣" + e.getMessage();
				e.printStackTrace();
			}
		}
		if (flag) {
			try {
				batch = dao.importData(ls, userId);// ��vsopĳ������ݵ��м��
			} catch (Exception e) {
				log.error("�����������ݵ��м�����:", e);
				flag = false;
				errorInfo = "�����������ݵ��м�����:";
				LegacyDAOUtil.rollbackOnException();
			} finally {
				LegacyDAOUtil.commitAndCloseConnection();
			}
		}
		if (flag) {
			try {
				dao.batchOrder(batch);
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
				errorInfo = "���ɶ�����ϵʧ��"+e.getMessage();
				LegacyDAOUtil.rollbackOnException();
			} finally {
				LegacyDAOUtil.commitAndCloseConnection();
			}
		}
		returnResult(response, errorInfo,flag);
	}

	private List extractListFromReader(BufferedReader reader)
			throws IOException {
		List ret = new ArrayList();
		String line = reader.readLine();
		while (line != null) {
			ret.add(line);
			line = reader.readLine();
		}
		return ret;
	}

	private void returnResult(HttpServletResponse response, String errorInfo,boolean flag)
			throws IOException {
		String returnInfo = "<script type=\"text/javascript\">parent.document.getElementById('tip').style.display='none';parent.page_onLoad();</script>";
		if(!flag){
			returnInfo = "<script type=\"text/javascript\">parent.document.getElementById('tip').innerText='"+errorInfo+"';</script>";
		}
		response.setCharacterEncoding("GBK");
		response.getWriter().write(returnInfo);
		response.getWriter().flush();
	}
}
