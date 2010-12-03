package com.ztesoft.crm.salesres.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.ztesoft.crm.salesres.bo.RcSimBO;
import com.ztesoft.crm.salesres.exception.BizLogicException;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcSimCancelPreAssemUpLoadFile extends HttpServlet {
    public RcSimCancelPreAssemUpLoadFile() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "../sr/loadDeliverIframe.jsp");

        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        String str = "";
        String message = "";
        List codes = new ArrayList();
        int errCounter = 0;
        long lngMaxFileSize = 10000 * 1024;
        boolean isMultipart = org.apache.commons.fileupload.FileUpload.isMultipartContent(request);

        try {
            GlobalVariableHelper helper = new GlobalVariableHelper(request);
            String operId = helper.getVariable(GlobalVariableHelper.OPER_ID);
            String departId = helper.getVariable(GlobalVariableHelper.DEPART_ID);

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

                            // sim卡号
                            if (!str.matches("\\d{" + str.length() + "}") ||
                                    (str.length() > 50)) {
                                errCounter++;

                                continue;
                            }

                            codes.add(str);
                        }

                        read.close();
                    }
                }

                Map map = new HashMap();
                map.put("codes", codes);
                Long count = (Long)ServiceManager.callJavaBeanService("RcNoBo","cancelPreAssemSimNo" , map) ;
                
                String result = "";
                message = "取消预配成功，共成功取消"+count+"个预配";
                
                
                
                //Map rtnMap = bean.cancelPreAssemSimNo(codes);

                /*if (rtnMap != null) {
                    if ((rtnMap.get("result") != null) &&
                            (rtnMap.get("result").toString().trim().length() > 0) &&
                            (rtnMap.get("message") != null) &&
                            (rtnMap.get("message").toString().trim().length() > 0)) {
                        message = (String) rtnMap.get("message");

                        if (errCounter > 0) {
                            message += (",系统过滤掉" + errCounter + "条格式错误的数据!");
                        }
                    }
                } else {
                    message = "Sim卡取消预配发生未知错误!";

                    if (errCounter > 0) {
                        message += (",系统过滤掉" + errCounter + "条格式错误的数据!");
                    }
                }*/
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "Sim卡取消预配时发生未知错误!";
        } finally {
            String outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";
            message = "<script>alert('" + message +
                "');window.close();</script>";
            message = outStr + message;
            out.println(message);
            System.out.println(message);
            out.println();
            out.close();
        }
    }
}
