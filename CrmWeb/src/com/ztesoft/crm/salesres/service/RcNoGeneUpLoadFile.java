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
import com.ztesoft.crm.salesres.vo.RcNoVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcNoGeneUpLoadFile extends HttpServlet {
    public RcNoGeneUpLoadFile() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "../sr/loadGeneIframe.jsp");

        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        List fileList = new ArrayList();
        String str = "";
        String[] datas = new String[6];
        String storageId = "";
        String storageName = "";
        String effDate = "";
        String expDate = "";
        String selfhelpflag = "";
        RcNoVO vo = new RcNoVO();
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

                List formatErrInfoList = new ArrayList(); // 格式错误的信息提示
                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();

                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    //					System.out.println("111 ==" + item.getFieldName() + " "
                    //							+ item.getString());
                    if (item.getFieldName().equals("storageId")) {
                        storageId = item.getString();
                        vo.setStorageId(storageId);
                    } else if (item.getFieldName().equals("storageName")) {
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

                        vo.setStorageName(storageName);
                    } else if (item.getFieldName().equals("effDate")) {
                        effDate = item.getString();
                        vo.setEffDate(effDate);
                    } else if (item.getFieldName().equals("expDate")) {
                        expDate = item.getString();
                        vo.setExpDate(expDate);
                    } else if (item.getFieldName().equals("selfhelpflag")) {
                        selfhelpflag = item.getString();
                        vo.setSelfhelpflag(selfhelpflag);
                    }

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));
                        String[] lineArr = null;
                        RcNoVO voTemp = null;

                        while ((str = read.readLine()) != null) {
                            str = str.trim();

                            if (!str.equals("") &&
                                    (str.getBytes().length <= 50)) {
                                lineArr = str.split(",");

                                if ((lineArr == null) || (lineArr.length != 2)) {
                                    lineArr = str.split(" ");
                                }

                                if ((lineArr != null) && (lineArr.length == 2) &&
                                        lineArr[0].matches("\\d{" +
                                            lineArr[0].length() + "}")) {
                                    voTemp = new RcNoVO();
                                    voTemp.setRescInstanceCode(lineArr[0]);

                                    if (lineArr[1] != null) {
                                        voTemp.setNoSegName(lineArr[1]);
                                    }

                                    fileList.add(voTemp);
                                } else if (str != null) {
                                    formatErrInfoList.add(str +
                                        "    格式错误，不能导入");
                                }
                            }
                        }

                        read.close();
                    }
                }

                // ejb...
                Map paraMap = new HashMap();
                paraMap.put("No", vo);
                paraMap.put("NoList", fileList);
                paraMap.put("operId", operId);
                paraMap.put("departId", departId);

                Map rtnMap = (Map)ServiceManager.callJavaBeanService("RcNoBo","geneNosFromFile" , paraMap) ;
                
                //Map rtnMap = bean.geneNosFromFile(paraMap);
                String alertInfo = "";
                List txtIngoList = null;

                if (rtnMap != null) {
                    if (rtnMap.get("alertInfo") != null) {
                        alertInfo = (String) rtnMap.get("alertInfo");
                    }

                    if (rtnMap.get("txtIngoList") != null) {
                        txtIngoList = (List) rtnMap.get("txtIngoList");
                    }
                }

                // 处理返回信息
                if ((formatErrInfoList != null) &&
                        (formatErrInfoList.size() > 0)) {
                    alertInfo = "共有" + formatErrInfoList.size() + "条数据格式错误! " +
                        alertInfo;
                }

                String outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";

                String outFunction = "  var fso = new ActiveXObject(\"Scripting.FileSystemObject\");";
                outFunction += " var clientfile = fso.CreateTextFile(\"c:/info.txt\", true);";

                // 输入格式错误的信息
                if (formatErrInfoList.size() > 0) {
                    for (int i = 0; i < formatErrInfoList.size(); i++) {
                        outFunction += (" clientfile.WriteLine('" +
                        (String) formatErrInfoList.get(i) + "');");
                    }

                    outFunction += (" clientfile.WriteLine('共有" +
                    formatErrInfoList.size() + "条数据格式错误!');");
                    outFunction += " clientfile.WriteLine('');";
                }

                // 处理逻辑处理数据的错误
                if ((txtIngoList != null) && (txtIngoList.size() > 0)) {
                    for (int i = 0; i < txtIngoList.size(); i++) {
                        outFunction += (" clientfile.WriteLine('" +
                        (String) txtIngoList.get(i) + "');");
                    }
                }

                outFunction += " clientfile.Close(); fso=null;";

                String allStr = "<script>" + outFunction + "</script>";
                out.println(outStr + allStr);
                out.println("<script> alert('" + alertInfo +
                    ",具体请查看c:/info.txt');window.close();</script>");
                out.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            String outStr1 = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";
            String result1 = "";
            String outFunction = "<script>  var fso = new ActiveXObject(\"Scripting.FileSystemObject\");";
            outFunction += " var clientfile = fso.CreateTextFile(\"c:/info.txt\", true);";
            outFunction += " clientfile.WriteLine('生成号码出错，数据库异常!'); ";
            outFunction += " clientfile.Close(); fso=null;";

            result1 = outStr1 +
                "<script> alert('生成号码失败，出现系统异常!');window.close();</script>";

            out.println(result1);
            out.close();
        }

        //finally {
        // dispatcher.forward(request, response);
        //}
    }

    public static void main(String[] args) {
    }
}
