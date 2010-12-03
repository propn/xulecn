package com.ztesoft.crm.salesres.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import com.ztesoft.common.util.PageModel;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class StockOutUpLoadFile extends HttpServlet {
    public StockOutUpLoadFile() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = null;

        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        Collection fileList = new HashSet();
        String str = "";
        String[] datas = new String[6];
        String orderId = "";
        String salesRescId = "";
        String projectFlag = "";
        String inStorage = "";
        String outStorage = "";
        String appType = "";
        long lngMaxFileSize = 10000 * 1024;
        boolean isMultipart = org.apache.commons.fileupload.FileUpload.isMultipartContent(request);

        try {
            GlobalVariableHelper helper = new GlobalVariableHelper(request);
            String operId = helper.getVariable(GlobalVariableHelper.OPER_ID);
            String landId = helper.getVariable(GlobalVariableHelper.LAN_ID);

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
                    System.out.println(" ==" + item.getFieldName() + " " +
                        item.getString());

                    if (item.getFieldName().equals("orderId")) {
                        orderId = item.getString();
                    }

                    if (item.getFieldName().equals("salesRescId")) {
                        salesRescId = item.getString();
                    }

                    if (item.getFieldName().equals("projectFlag")) {
                        projectFlag = item.getString();

                        if (projectFlag.equalsIgnoreCase("henan")) {
                            dispatcher = request.getRequestDispatcher(
                                    "../sr/upLoadStockOut_henan.jsp");
                        }

                        if (projectFlag.equalsIgnoreCase("jiangxi")) {
                            dispatcher = request.getRequestDispatcher(
                                    "../sr/upLoadStockOut.jsp");
                        }
                    }

                    if (item.getFieldName().equals("inStorage")) {
                        inStorage = item.getString();
                    }

                    if (item.getFieldName().equals("outStorage")) {
                        outStorage = item.getString();
                    }

                    if (item.getFieldName().equals("appType")) {
                        appType = item.getString();
                    }

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));

                        while ((str = read.readLine()) != null) {
                            str = str.trim();
                            fileList.add(str);
                        }

                        read.close();
                    }
                }

                iter = fileList.iterator();

                while (iter.hasNext()) {
                    str = (String) iter.next();

                    if (!str.equals("") && (str.getBytes().length <= 50)) {
                        if (projectFlag.equalsIgnoreCase("jiangxi")) {
                        } else if (projectFlag.equalsIgnoreCase("henan") &&
                                str.matches("\\d{" + str.length() + "}")) {
                        } else {
                            iter.remove();
                        }
                    } else {
                        iter.remove();
                    }
                }

                //ejb...RcOrderListBO
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-mm-dd hh:mm:ss");
                datas[0] = format.format(new Date(System.currentTimeMillis())); //开始时间
                datas[1] = "上传的有效数据数量为：" + fileList.size() + " 条。"; //有效数据

                Map map = new HashMap();
                map.put("salesRescId", salesRescId);
                map.put("inStorageId", inStorage);
                map.put("outStorageId", outStorage);
                map.put("appType", appType);
                String[] ss = (String[])ServiceManager.callJavaBeanService("RcOrderListBO","upLoadStockOut" ,map);

                //String[] ss = {  };
                List list = new ArrayList();

                /*if ((fileList != null) && (fileList.size() > 0)) {
                    if (projectFlag.equalsIgnoreCase("jiangxi")) {
                        ss = bean.upLoadStockOut(orderId, salesRescId, appType,
                                inStorage, landId, fileList);
                        datas[2] = format.format(new Date(
                                    System.currentTimeMillis())); //结束时间
                        datas[3] = ss[0]; //成功上传的数据
                        datas[4] = ss[1]; //发生错误的信息
                        datas[5] = ss[2];
                    }

                    if (projectFlag.equalsIgnoreCase("henan")) {
                        list = bean.upLoadStockOut(map, fileList);
                        datas[1] = (String) list.get(0); //返回的ORDERID
                        datas[2] = String.valueOf(list.get(1)); //成功上传的记录数量
                        datas[3] = (String) list.get(3); //失败的记录的资源实例编码

                        String flag = "";

                        if ((datas[1] == null) || (datas[1].length() <= 0)) {
                            flag = "-1";
                        }

                        request.setAttribute("flag", flag);
                    }
                } else {
                    if (projectFlag.equalsIgnoreCase("henan")) {
                        throw new Exception(datas[1]);
                    }
                }*/

                request.setAttribute("result", datas);
            }
        } catch (Exception ex) {
            String errstring = ex.getMessage();

            if (errstring == null) {
                errstring = ex.getCause().toString();
            }

            errstring = errstring.split("---")[errstring.split("--").length -
                1];
            errstring = errstring.replaceAll("\\n", " ");
            request.setAttribute("err", errstring);
            ex.printStackTrace();
        } finally {
            dispatcher.forward(request, response);
        }
    }
}
