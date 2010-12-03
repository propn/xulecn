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
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.vo.RcNoVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcNoDeliverUpLoadFile extends HttpServlet {
    public RcNoDeliverUpLoadFile() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/sr/nosim/loadDeliverIframe.jsp");

        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        List noList = new ArrayList();

        //List segList = new ArrayList();
        String str = "";
        StringBuffer buffer = new StringBuffer();
        String storageId = "";
        String storageName = "";
        String storageId_old = "";
        String rescLevel = "";
        String nos = "";
        String deliverType = "2";
        String orderId = "";
        String result = "0";
        String info = "";
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

                    if (item.getFieldName().equals("storageId")) {
                        storageId = item.getString();
                    }

                    if (item.getFieldName().equals("storageName")) {
                        storageName = item.getString();

                        if ((storageName == null) || "".equals(storageName)) {
                            continue;
                        }

                        String[] sts = storageName.split("%u");

                        if (sts.length > 1) {
                            storageName = sts[0];

                            for (int i = 1; i < sts.length; i++) {
                                storageName += String.valueOf(((char) Integer.parseInt(
                                        sts[i].substring(0, 4), 16)));

                                if (sts[i].length() > 4) {
                                    storageName += sts[i].substring(4,
                                        sts[i].length());
                                }
                            }
                        }
                    }

                    if (item.getFieldName().equals("storageId_old")) {
                        storageId_old = item.getString();
                    }

                    if (item.getFieldName().equals("rescLevel")) {
                        rescLevel = item.getString();
                    }

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));

                        //int num = 1;
                        while ((str = read.readLine()) != null) {
                            str = str.trim();

                            if (!str.equals("") &&
                                    (str.getBytes().length <= 50)) {
                                //buffer.append("'").append(str).append("',");
                                noList.add(str);

                                /*if (num%400==0) {
                                                       segList.add(buffer.toString());
                                                       buffer= new StringBuffer();
                                                }
                                                num++;*/
                            }
                        }

                        /*if(buffer.length()>0){
                          nos = buffer.substring(0,buffer.length()-1);
                          segList.add(nos);
                        }*/
                        read.close();
                    }
                }

                //ejb...
                Map map = new HashMap();
                RcNoVO vo = new RcNoVO();
                vo.setStorageId(storageId);
                vo.setStorageName(storageName);
                vo.setStorageId_old(storageId_old);
                vo.setRescLevel(rescLevel);
                map.put("No", vo);
                //map.put("nos",nos);
                map.put("noList", noList);
                //map.put("segList",segList);
                map.put("deliverType", deliverType);
                map.put("operId", operId);
                map.put("departId", departId);



                Map rtnMap = (Map)ServiceManager.callJavaBeanService("RcNoBo","deliverNos" , map) ;
                String message = "";
                info = "号码分配成功!";

                if (rtnMap != null) {
                    if (rtnMap.get("result") != null) {
                        if ("1".equals((String) rtnMap.get("result"))) {
                            orderId = (String) rtnMap.get("orderId");
                            result = (String) rtnMap.get("result");
                            info = (String) rtnMap.get("info");
                        } else if ("0".equals((String) rtnMap.get("result"))) {
                            result = (String) rtnMap.get("result");
                            info = (String) rtnMap.get("info");
                        }
                    }

                    if (rtnMap.get("info") != null) {
                        info = (String) rtnMap.get("info");
                    }
                } else {
                    result = "0";
                    info = "发生未知异常错误，操作失败!";
                }

                //String outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../../../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";

                //String outFunction ="  var fso = new ActiveXObject(\"Scripting.FileSystemObject\");";
                //outFunction += " var clientfile = fso.CreateTextFile(\"c:/info.txt\", true);";
                //outFunction += " clientfile.WriteLine('" +info+ "');";
                //outFunction += " clientfile.Close(); fso=null;";
                //String allStr = "<script>" + outFunction + "</script>";
                //out.println(outStr + allStr);
                //out.println("<script> alert('" + info +"');parent.refreashResult("+orderId+");</script>");
                //out.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //String outStr1 = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";
            //String result1 = "";
            //String outFunction ="<script>  var fso = new ActiveXObject(\"Scripting.FileSystemObject\");";
            //outFunction += " var clientfile = fso.CreateTextFile(\"c:/info.txt\", true);";
            //outFunction += " clientfile.WriteLine('生成分配出错，出现系统异常，具体请联系管理员!'); ";
            //outFunction += " clientfile.Close(); fso=null;";

            //result1 = outStr1 + "<script> alert('生成分配失败，出现系统异常，具体请联系管理员!');window.close();</script>";

            //out.println(result1);
            //out.close();
            result = "0";
            info = "发生未知异常错误，操作失败!";
        } finally {
            request.setAttribute("result", result);
            request.setAttribute("orderId", orderId);
            request.setAttribute("info", info);

            dispatcher.forward(request, response);
        }
    }
}
