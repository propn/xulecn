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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class BatchAddMpStorageService extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        //System.out.println(request.getSession());
        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        String str = "";
        String[] datas = null;
        List pList = new ArrayList();
        List pList1 = new ArrayList();
        List pList2 = new ArrayList();
        Map retMap = null;
        List formatErrList = new ArrayList();
        List doulist = new ArrayList();
        List failList = null;
        List doneList = new ArrayList();
        String alertInfo = "";
        String successNum = "0";
        //SrStorage bean = null;

        List isdoulist = new ArrayList();

        int errCounter = 0;
        long lngMaxFileSize = 10000 * 1024;
        boolean isMultipart = FileUpload.isMultipartContent(request);

        String NUMBER = "[0-9]*";

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
                            datas = str.split(" ");

                            if (isdoulist.contains(str)) {
                                doulist.add(str);

                                continue;
                            }

                            isdoulist.add(str);

                            if ((datas == null) || (datas.length != 2)) {
                                errCounter++;
                                str = str.replaceAll("\\\\", "\\\\\\\\");
                                formatErrList.add(str);

                                continue;
                            }

                            if ((datas[0].length() == 0) ||
                                    (datas[0].length() > 50) ||
                                    (datas[0].indexOf("\\") != -1) ||
                                    (datas[0].indexOf("'") != -1) ||
                                    (datas[1].length() == 0) ||
                                    (datas[1].length() > 50) ||
                                    (datas[1].indexOf("\\") != -1) ||
                                    (datas[1].indexOf("'") != -1)) {
                                errCounter++;
                                str = str.replaceAll("\\\\", "\\\\\\\\");
                                formatErrList.add(str);

                                continue;
                            }

                            if (!datas[0].matches(NUMBER) ||
                                    !datas[1].matches(NUMBER)) { //校验是否全是数字
                                errCounter++;
                                str = str.replaceAll("\\\\", "\\\\\\\\");
                                formatErrList.add(str);

                                continue;
                            }

                            pList1.add(datas[0]);
                            pList2.add(datas[1]);
                            pList.add(datas);
                        }

                        read.close();
                    }
                }

                //bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
                //        SrStorageHome.class);
                
                

                if ((pList != null) && (pList.size() > 0)) {
                    //插入RC_SIM_RELA表
                    Map map = new HashMap();
                    map.put("dataList", pList);
                    map.put("operId", operId);
                    map.put("departId", departId);
                    //retMap = (Map) bean.batchAddMpStorage(map);
                    retMap = (Map)ServiceManager.callJavaBeanService("SrStorageBO","batchAddMpStorage" ,map);
                }

                if (retMap != null) {
                    failList = (List) retMap.get("failList");
                    doneList = (List) retMap.get("doneList");
                    successNum = (String) retMap.get("successNum");
                    alertInfo = successNum + "条数据成功设置,过滤掉" + errCounter +
                        "条格式错误的数据,处理失败" + failList.size() +
                        "条记录,具体请查看c:/info.txt";
                } else {
                    alertInfo = successNum + "条数据成功设置,过滤掉" + errCounter +
                        "条格式错误的数据,具体请查看c:/info.txt";
                }

                // 设置显示信息
                String outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";

                String outFunction = "  var fso = new ActiveXObject(\"Scripting.FileSystemObject\");";
                outFunction += " var clientfile = fso.CreateTextFile(\"c:/info.txt\", true);";
                outFunction += (" clientfile.WriteLine('" + alertInfo + "');");
                outFunction += " clientfile.WriteLine('');";

                // 重复信息
                if ((doulist != null) && (doulist.size() > 0)) {
                    for (int i = 0; i < doulist.size(); i++) {
                        outFunction += (" clientfile.WriteLine('" +
                        (String) doulist.get(i) + "');");
                    }

                    outFunction += (" clientfile.WriteLine('共有" +
                    doulist.size() + "条数据重复!');");
                    outFunction += " clientfile.WriteLine('');";
                }

                // 输入格式错误的信息
                if (formatErrList.size() > 0) {
                    for (int i = 0; i < formatErrList.size(); i++) {
                        outFunction += (" clientfile.WriteLine('" +
                        (String) formatErrList.get(i) + "');");
                    }

                    outFunction += (" clientfile.WriteLine('共有" +
                    formatErrList.size() + "条数据格式错误!');");
                    outFunction += " clientfile.WriteLine('');";
                }

                // 处理逻辑处理数据的错误
                if ((failList != null) && (failList.size() > 0)) {
                    for (int i = 0; i < failList.size(); i++) {
                        outFunction += (" clientfile.WriteLine('" +
                        (String) failList.get(i) + "');");
                    }

                    outFunction += (" clientfile.WriteLine('共有" +
                    failList.size() + "条处理失败的记录');");
                }

                outFunction += " clientfile.Close(); fso=null;";

                String allStr = "<script>" + outFunction + "</script>";
                //System.out.println(outStr + allStr);  
                out.println(outStr + allStr);
                //System.out.println(alertInfo);
                out.println("<script>alert('" + alertInfo +
                    "');window.close();</script>");
                out.close();
            }
        } catch (Exception ex) {
            //bean = null;
            request.setAttribute("err", ex.getMessage());
            ex.printStackTrace();

            String outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";
            out.println(outStr);
            out.println(
                "<script> alert('发生未知异常错误,请确认上传的文件没有包含非数字的字符!');window.close();</script>");
            out.close();
        } finally {
            out.close();
        }
    }
}
