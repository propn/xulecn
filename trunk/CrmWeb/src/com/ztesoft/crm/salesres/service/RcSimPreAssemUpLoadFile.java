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
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcSimPreAssemUpLoadFile extends HttpServlet {
    public RcSimPreAssemUpLoadFile() {
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
        List list = new ArrayList();
        Map maptemp = new HashMap();
        String accept_id = "";
        String str = "";
        String message = "";
        String[] datas = null;
        int valideNum = 0;
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

                FileItem itemTemp = (FileItem) iter.next();

                if (itemTemp.getFieldName().equals("accept_id")) {
                    accept_id = itemTemp.getString();
                }

                maptemp.put("accept_id", accept_id);
                list.add(maptemp);

                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));

                        while ((str = read.readLine()) != null) {
                            Map paraMap = new HashMap(2);
                            str = str.trim();
                            datas = str.split(" ");

                            // ICCID 号码
                            if ((datas == null) || (datas.length != 2) ||
                                    !datas[0].matches("\\d{" +
                                        datas[0].length() + "}") ||
                                    (datas[0].length() > 50) ||
                                    !datas[1].matches("\\d{" +
                                        datas[1].length() + "}") ||
                                    (datas[1].length() > 50)) {
                                errCounter++;

                                continue;
                            }

                            paraMap.put("sim", datas[0]);
                            paraMap.put("no", datas[1]);
                            list.add(paraMap);
                            valideNum++;
                        }

                        read.close();
                    }
                }

                // if the upload number is more than 2000,then return
                if (valideNum > 2000) {
                    message = "通过文件上传方式预配，一次不能超过2000条记录!";
                } else {
                    //ejb...
                	Map map = new HashMap();
                	map.put("list", list);
                    String result = "";
                    Long count = (Long)ServiceManager.callJavaBeanService("RcSimBO","preAssemSimNo" , map) ;
                    
                    message = "预配成功，共成功预配"+count+"个Sim卡";
                    

                    /*if (rtnMap != null) {
                        if ((rtnMap.get("result") != null) &&
                                (rtnMap.get("result").toString().trim().length() > 0) &&
                                (rtnMap.get("message") != null) &&
                                (rtnMap.get("message").toString().trim().length() > 0)) {
                            message = (String) rtnMap.get("message");

                            if (errCounter > 0) {
                                message += (",系统过滤掉" + errCounter +
                                "条格式错误的数据!");
                            }
                        }
                    } else {
                        message = "Sim卡预配发生未知错误!";

                        if (errCounter > 0) {
                            message += (",系统过滤掉" + errCounter + "条格式错误的数据!");
                        }
                    }*/
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "Sim卡预配时发生未知错误!";
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
