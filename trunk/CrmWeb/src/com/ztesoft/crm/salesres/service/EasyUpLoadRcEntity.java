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
import com.ztesoft.crm.salesres.vo.RcEntityVO;
import com.ztesoft.crm.salesres.vo.RcEntityVO2;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class EasyUpLoadRcEntity extends HttpServlet {
    /**
     * Constructor of the object.
     */
    public EasyUpLoadRcEntity() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
                         // Put your code here
    }

    /**
     * The doGet method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to get.
     *
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to post.
     *
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/sr/upLoadEasyRcEntityFrame.jsp");

        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        List fileList = new ArrayList();
        String str = "";
        String salesRescId = "";
        String lanId = "";
        String currState = "";
        String state = "";
        String storageId = "";
        String effDate = "";
        String expDate = "";
        String result = "";
        String successCount = "";
        String failCount = "";
        List failList = null;
        int formatErrCount = 0;
        List formatErrList = new ArrayList();
        Map enticodeMap = new HashMap();

        String orderId = "";
        String appId = "";
        String manufacturer = "";
        String provider = "";
        String shopkeeper = "";
        int count = 0;
        RcEntityVO2 vo = new RcEntityVO2();
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
                //upload.setHeaderEncoding("gb2312");
                upload.setSizeMax(lngMaxFileSize);

                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();

                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    //System.out.println("111 ==" + item.getFieldName() + " " +
                    //                   item.getString());
                    if (item.getFieldName().equals("salesRescId")) {
                        salesRescId = item.getString();
                    }

                    if (item.getFieldName().equals("lanId")) {
                        lanId = item.getString();
                    }

                    if (item.getFieldName().equals("currState")) {
                        currState = item.getString();
                    }

                    if (item.getFieldName().equals("state")) {
                        state = item.getString();
                    }

                    if (item.getFieldName().equals("storageId")) {
                        storageId = item.getString();
                    }

                    if (item.getFieldName().equals("effDate")) {
                        effDate = item.getString();
                    }

                    if (item.getFieldName().equals("expDate")) {
                        expDate = item.getString();
                    }

                    if (item.getFieldName().equals("manufacturer")) {
                        manufacturer = item.getString();
                        manufacturer = new String(manufacturer.getBytes(
                                    "iso-8859-1"), "gb2312");
                    }

                    if (item.getFieldName().equals("provider")) {
                        provider = item.getString();
                        provider = new String(provider.getBytes("iso-8859-1"),
                                "gb2312");
                    }

                    if (item.getFieldName().equals("shopkeeper")) {
                        shopkeeper = item.getString();
                        shopkeeper = new String(shopkeeper.getBytes(
                                    "iso-8859-1"), "gb2312");
                    }

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));
                        RcEntityVO voTemp = null;

                        while ((str = read.readLine()) != null) {
                            str = str.trim();

                            if (!str.equals("") && (str.length() <= 50)) {
                                /*if(!str.matches("[a-zA-Z0-9]{"+str.length()+"}")){
                                        count++;
                                }else{*/
                                if (str.trim().split(" ").length > 1) {
                                    formatErrCount++;
                                    formatErrList.add(str +
                                        " --- 格式错误，编码中不能包含空格");

                                    continue;
                                }

                                if (enticodeMap.get(str) != null) {
                                    formatErrCount++;
                                    str = str.replaceAll("\\\\", "\\\\\\\\");
                                    formatErrList.add(str +
                                        " --- 格式错误，上传文件中已有该编码");

                                    continue;
                                }

                                voTemp = new RcEntityVO();
                                voTemp.setRescInstanceCode(str);
                                fileList.add(voTemp);
                                enticodeMap.put(str, str);

                                //}
                            } else if (str.length() <= 50) {
                                formatErrCount++;
                                str = str.replaceAll("\\\\", "\\\\\\\\");
                                formatErrList.add(str + " --- 编码长度超长，超过了50位");
                            }
                        }

                        read.close();
                    }
                }

                //ejb...
                Map paraMap = new HashMap();
                vo.setSalesRescId(salesRescId);
                vo.setLanId(lanId);
                vo.setCurrState(currState);
                vo.setState(state);
                vo.setStorageId(storageId);
                vo.setEffDate(effDate);
                vo.setExpDate(expDate);
                vo.setManufacturer(manufacturer);
                vo.setProvider(provider);
                vo.setShopkeeper(shopkeeper);

                paraMap.put("RcEntityVO", vo);
                paraMap.put("InputList", fileList);
                paraMap.put("operId", operId);
                paraMap.put("departId", departId);


                Map rtnMap = (Map)ServiceManager.callJavaBeanService("RcEntityBo","addMultiRcEntityEasy" ,paraMap);

                if (rtnMap != null) {
                    if (rtnMap.get("result") != null) {
                        result = (String) rtnMap.get("result");
                    }

                    if (rtnMap.get("successCount") != null) {
                        successCount = (String) rtnMap.get("successCount");
                    }

                    if (rtnMap.get("failCount") != null) {
                        failCount = String.valueOf(Integer.parseInt(
                                    (String) rtnMap.get("failCount"), 10) +
                                count);
                    }

                    if (rtnMap.get("orderId") != null) {
                        orderId = (String) rtnMap.get("orderId");
                    }

                    if (rtnMap.get("appId") != null) {
                        appId = (String) rtnMap.get("appId");
                    }

                    if ((rtnMap.get("failList") != null) &&
                            (((List) rtnMap.get("failList")).size() > 0)) {
                        failList = (List) rtnMap.get("failList");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            request.setAttribute("result", result);
            request.setAttribute("orderId", orderId);
            request.setAttribute("successCount", successCount);
            request.setAttribute("failCount", failCount);
            request.setAttribute("formatErrCount",
                String.valueOf(formatErrCount));
            request.setAttribute("appId", appId);

            if ((formatErrList != null) && (formatErrList.size() > 0)) {
                request.setAttribute("formatErrList", formatErrList);
            }

            if ((failList != null) && (failList.size() > 0)) {
                request.setAttribute("failList", failList);
            }

            dispatcher.forward(request, response);
        }
    }
}
