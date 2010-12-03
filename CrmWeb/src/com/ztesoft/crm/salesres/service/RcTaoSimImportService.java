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
import com.ztesoft.crm.salesres.vo.RcSimEvdoVO;
import com.ztesoft.crm.salesres.vo.RcSimVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcTaoSimImportService extends HttpServlet {
    public RcTaoSimImportService() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "sr/RcTaoSimImportFrame.jsp");

        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        List fileList = new ArrayList();
        String str = "";
        int importType = 1;
        String storageId = "";
        String simType = "";
        String effDate = "";
        String expDate = "";
        String salesRescId = "";
        RcSimVO vo = new RcSimVO();
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

                    if (item.getFieldName().equals("importType")) {
                        importType = Integer.parseInt(item.getString());
                    }

                    if (item.getFieldName().equals("storageId")) {
                        storageId = item.getString();
                        vo.setStorageId(storageId);
                    } else if (item.getFieldName().equals("simType")) {
                        simType = item.getString();
                        vo.setSimType(simType);
                    } else if (item.getFieldName().equals("effDate")) {
                        effDate = item.getString();
                        vo.setEffDate(effDate);
                    } else if (item.getFieldName().equals("expDate")) {
                        expDate = item.getString();
                        vo.setExpDate(expDate);
                    } else if (item.getFieldName().equals("salesRescId")) {
                        salesRescId = item.getString();
                        vo.setSalesRescId(salesRescId);
                    }

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));
                        String[] lineArr = null;
                        RcSimEvdoVO voTemp = null;

                        while ((str = read.readLine()) != null) {
                            str = str.trim();

                            if (str.startsWith("//")) {
                                continue;
                            }

                            if ((simType != null) &&
                                    !RcSimVO.SimType_evdo.equals(simType) &&
                                    (str.split(" ").length < 14)) {
                                str = str.trim() + " # #"; // 添加2个空格凑够14位
                            }

                            if (!str.equals("")) {
                                lineArr = str.split(" ");

                                if ((lineArr == null) || (lineArr.length < 14)) {
                                    formatErrInfoList.add(str +
                                        "    格式错误，不能导入");

                                    continue;
                                }

                                for (int i = 0; i < lineArr.length; i++) { // 如果上传的字符有"#"，代表该字段留空，在此设为空

                                    if ((lineArr[i] != null) &&
                                            "#".equals(lineArr[i])) {
                                        lineArr[i] = "";
                                    }
                                }

                                //号码 IMSI ESN ICCID PIN1 PIN2 PUK1 PUK2 HRPD_SS HRPDUPP
                                if (!lineArr[0].matches("\\d{" +
                                            lineArr[0].length() + "}") ||
                                        (lineArr[0].length() > 50) ||
                                        !lineArr[1].matches("\\d{" +
                                            lineArr[1].length() + "}") ||
                                        (lineArr[2].length() > 30) ||
                                        !lineArr[3].matches("\\d{" +
                                            lineArr[3].length() + "}") ||
                                        (lineArr[3].length() > 50) ||
                                        (lineArr[4].length() > 30) ||
                                        (lineArr[5].length() > 30) ||
                                        (lineArr[6].length() > 30) ||
                                        (lineArr[7].length() > 30) ||
                                        !lineArr[8].matches("\\d{" +
                                            lineArr[8].length() + "}") ||
                                        (lineArr[9].length() > 30) ||
                                        (lineArr[10].length() > 2) ||
                                        (lineArr[11].length() > 100) ||
                                        (lineArr[12].length() > 64) ||
                                        (lineArr[13].length() > 64)) {
                                    formatErrInfoList.add(str +
                                        "    格式错误，不能导入");

                                    continue;
                                }

                                voTemp = new RcSimEvdoVO();
                                voTemp.setDnNo(lineArr[0]);
                                voTemp.setImsiId(lineArr[1]);
                                voTemp.setEsn(lineArr[2]);
                                voTemp.setRescInstanceCode(lineArr[3]);
                                voTemp.setPin1(lineArr[4]);
                                voTemp.setPin2(lineArr[5]);
                                voTemp.setPukNo(lineArr[6]);
                                voTemp.setPuk2No(lineArr[7]);
                                voTemp.setImsiId2(lineArr[8]);
                                voTemp.setKi(lineArr[9]);
                                voTemp.setAcc(lineArr[10]);
                                voTemp.setSmsp(lineArr[11]);
                                voTemp.setHrpdSs(lineArr[12]);
                                voTemp.setHrpdUpp(lineArr[13]);
                                fileList.add(voTemp);
                            }
                        }

                        read.close();
                    }
                }

                //ejb...
                Map map = new HashMap();
                map.put("vo", vo);
                map.put("list", fileList);
                map.put("operId", operId);
                map.put("departId", departId);

//                RcSIM bean = (RcSIM) EJBHelper.creatEJB("ejb/RcSIM",
//                        RcSIMHome.class);
//                Map rtnMap = bean.importRcTaoSimInfo(map, importType);
                map.put("importType", new Integer (importType));
                Map rtnMap = (Map )ServiceManager.callJavaBeanService("RcSimBO","importRcTaoSimInfo" ,map);
                
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
            request.setAttribute("err", ex.getMessage());
            ex.printStackTrace();

            String outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";
            out.println(outStr);
            out.println("<script> alert('发生未知异常错误!');window.close();</script>");
            out.close();
        }

        //finally {
        // dispatcher.forward(request, response);
        //}
    }
}
