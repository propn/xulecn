package com.ztesoft.crm.salesres.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcNoDeFreezeUpLoadFile extends HttpServlet {
    public RcNoDeFreezeUpLoadFile() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "../sr/loadDeFreezeIframe.jsp");

        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        Collection fileList = new HashSet();
        String str = "";
        StringBuffer buffer = new StringBuffer();
        String storageId = "";
        String nos = "";
        String deliverType = "2";
        long lngMaxFileSize = 10000 * 1024;
        boolean isMultipart = org.apache.commons.fileupload.FileUpload.isMultipartContent(request);
        String info = "";

        try {
            GlobalVariableHelper helper = new GlobalVariableHelper(request);
            String operId = helper.getVariable(GlobalVariableHelper.OPER_ID);

            if (isMultipart) { // 是文件上传

                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(100000);
                factory.setRepository(new java.io.File("./upload/"));

                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(lngMaxFileSize);

                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();

                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));

                        while ((str = read.readLine()) != null) {
                            str = str.trim();

                            if (!str.equals("") &&
                                    (str.getBytes().length <= 50)) {
                                buffer.append("'").append(str).append("',");
                            }
                        }

                        if (buffer.length() > 0) {
                            nos = buffer.substring(0, buffer.length() - 1);
                        }

                        read.close();
                    }
                }

                //ejb...
                Map map = new HashMap();
                map.put("nos", nos);
                map.put("deliverType", deliverType);

                Map rtnMap = (Map)ServiceManager.callJavaBeanService("RcNoBo","deFreezeNos" , map) ;
                //Map rtnMap = bean.deFreezeNos(map);
                String message = "";
                info = "号码解冻成功!";

                if (rtnMap != null) {
                    if (rtnMap.get("info") != null) {
                        info = (String) rtnMap.get("info");
                    }
                } else {
                    message = "号码解冻错误!";
                }

                System.out.println("info:  " + info);
            }
        } catch (Exception ex) {
            info = "系统内部异常错误，操作失败!";
            request.setAttribute("err", ex.getMessage());
            ex.printStackTrace();
        } finally {
            out.println("<script> alert('" + info +
                "');window.close();</script>");
            out.close();
        }
    }
}
