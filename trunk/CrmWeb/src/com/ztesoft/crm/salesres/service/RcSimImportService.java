package com.ztesoft.crm.salesres.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
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
import com.ztesoft.crm.salesres.vo.RcSimVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcSimImportService extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        //System.out.println(request.getSession());
        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        Collection fileList = new ArrayList();
        String str = "";
        String[] datas = null;
        List pList = new ArrayList();
        String result = "0";
        int errCounter = 0;
        long lngMaxFileSize = 10000 * 1024;
        boolean isMultipart = FileUpload.isMultipartContent(request);
        //RcSIM bean = null;

        try {
            GlobalVariableHelper helper = new GlobalVariableHelper(request);
            String operId = helper.getVariable(GlobalVariableHelper.OPER_ID);
            String departId = helper.getVariable(GlobalVariableHelper.DEPART_ID);
            String simType = null;

            if (isMultipart) { // ���ļ��ϴ�

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
                        pList.add(item.getString().trim());
                    } else if (item.getFieldName().equals("simType")) {
                        pList.add(item.getString().trim());
                        simType = item.getString().trim();
                    } else if (item.getFieldName().equals("effDate")) {
                        pList.add(item.getString().trim());
                    } else if (item.getFieldName().equals("expDate")) {
                        pList.add(item.getString().trim());
                    } else if (item.getFieldName().equals("salesRescId")) {
                        pList.add(item.getString().trim());
                    }

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));

                        while ((str = read.readLine()) != null) {
                            str = str.trim();

                            if (str.startsWith("//")) {
                                continue;
                            }

                            if ((simType != null) &&
                                    !RcSimVO.SimType_evdo.equals(simType) &&
                                    (str.split(" ").length < 13)) {
                                str = str.trim() + " # #"; // ���2���ո�չ�13λ
                            }

                            datas = str.split(" ");

                            if ((datas == null) || (datas.length < 13)) {
                                errCounter++;

                                continue;
                            }

                            for (int i = 0; i < datas.length; i++) { // ����ϴ����ַ���"#"��������ֶ����գ��ڴ���Ϊ��

                                if ((datas[i] != null) && "#".equals(datas[i])) {
                                    datas[i] = "";
                                }
                            }

                            //IMSI ESN ICCID PIN1 PIN2 PUK1 PUK2 IMSI2 KI ACC SMSP HRPD_SS HRPDUPP
                            if (!datas[0].matches("\\d{" + datas[0].length() +
                                        "}") || (datas[1].length() > 30) ||
                                    !datas[2].matches("\\d{" +
                                        datas[2].length() + "}") ||
                                    (datas[2].length() > 50) ||
                                    (datas[3].length() > 30) ||
                                    (datas[4].length() > 30) ||
                                    (datas[5].length() > 30) ||
                                    (datas[6].length() > 30) ||
                                    !datas[7].matches("\\d{" +
                                        datas[7].length() + "}") ||
                                    (datas[8].length() > 30) ||
                                    (datas[9].length() > 2) ||
                                    (datas[10].length() > 100) ||
                                    (datas[11].length() > 64) ||
                                    (datas[12].length() > 64)) {
                                errCounter++;

                                continue;
                            }

                            fileList.add(datas);
                        }

                        read.close();
                    }
                }

                //ejb...
                Map map = new HashMap();
                map.put("pList", pList);
                map.put("fileList", fileList);
                map.put("operId", operId);
                map.put("departId", departId);

                System.out.println("�ϴ�����Ч��������Ϊ��" + fileList.size() + " ����"); //��Ч����

                if (fileList.size() > 0) {
//                    bean = (RcSIM) EJBHelper.creatEJB("ejb/RcSIM",
//                            RcSIMHome.class);
//                    result = (String) bean.importRcSimInfo(map).get(0);
                    List retList = (List)ServiceManager.callJavaBeanService("RcSimBO","importRcSimInfo" ,map);
                    result = (String) retList.get(0);
                }

                result = "<script>alert('" + result + "�����ݵ���ɹ�,Ϊ����˵�" +
                    errCounter + "����ʽ��Ч������');window.close();</script>";
            }
        } catch (Exception ex) {
            //bean = null;
            ex.printStackTrace();

            String expInfo = "";

            if (ex.getCause() != null) {
                expInfo = ex.getCause().toString();
                result = expInfo.substring(expInfo.lastIndexOf(":"));
            } else if (ex.getMessage() != null) {
                expInfo = ex.getMessage().toString();
                result = expInfo.substring(expInfo.lastIndexOf(":"));
            }

            String outFunction = "<script>  var fso = new ActiveXObject(\"Scripting.FileSystemObject\");";
            outFunction += " var clientfile = fso.CreateTextFile(\"c:/err_info.txt\", true);";

            outFunction += (" clientfile.WriteLine('Ϊ����˵�" + errCounter +
            "����ʽ��Ч������'); clientfile.WriteLine('���������������Ϣ��" + result + "');");
            outFunction += " clientfile.WriteLine('��������¼��IMSI���ICCID����IMSI2���Ƿ��Ѿ����ڣ�������IMSI��ʹ�IMSI2�붼�����е�imsi�����!');";
            outFunction += " clientfile.Close(); fso=null;";

            result = outFunction +
                "alert('���ݵ���ʧ�ܣ�����������ݵĸ�ʽ����Ч�ͣ���ϸ��Ϣ��c:/err_info.txt');window.close();</script>";
        } finally {
            String outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";
            result = outStr + result;
            out.println(result);
            System.out.println(result);
            out.println();
            out.close();
        }
    }
}
