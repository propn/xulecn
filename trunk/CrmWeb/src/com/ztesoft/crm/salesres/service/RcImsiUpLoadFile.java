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


public class RcImsiUpLoadFile extends HttpServlet {
    public RcImsiUpLoadFile() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "../sr/loadRcImsiIFrame.jsp");

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
                    System.out.println("111 ==" + item.getString());

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));

                        while ((str = read.readLine()) != null) {
                            str = str.trim();

                            if (!str.equals("") &&
                                    (str.getBytes().length <= 50)) {
                                buffer.append(str).append(",");
                            }
                        }

                        if (buffer.length() > 0) {
                            nos = buffer.substring(0, buffer.length() - 1);
                        }

                        read.close();
                    }
                }

                //ejb...SrRcImsiBO.uploadImsi
                Map map = new HashMap();
                map.put("nos", nos);
                Map rtnMap = (Map)ServiceManager.callJavaBeanService("SrRcImsiBO","uploadImsi" , map) ;
                
                //Map rtnMap = bean.uploadImsi(nos);

                String info = "IMSI码导入错误!";

                if (rtnMap != null) {
                    if (rtnMap.get("info") != null) {
                        info = (String) rtnMap.get("info");
                    }
                } else {
                    info = "IMSI码导入错误!";
                }

                out.println("<script> alert('" + info +
                    "');window.close();</script>");
                out.close();
            }
        } catch (Exception ex) {
            request.setAttribute("err", ex.getMessage());
            ex.printStackTrace();
        }
    }
}
