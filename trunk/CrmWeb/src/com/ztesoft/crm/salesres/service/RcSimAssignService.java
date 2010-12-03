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


public class RcSimAssignService extends HttpServlet {
    public RcSimAssignService() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        Collection fileList = new HashSet();
        String str = "";
        String result = "";
        int errCounter = 0;
        //RcSIM bean = null;
        Map pmp = new HashMap();
        boolean uploadFileFlag = true;
        long lngMaxFileSize = 10000 * 1024;
        boolean isMultipart = FileUpload.isMultipartContent(request);

        try {
            GlobalVariableHelper helper = new GlobalVariableHelper(request);
            String operId = helper.getVariable(GlobalVariableHelper.OPER_ID);
            String departId = helper.getVariable(GlobalVariableHelper.DEPART_ID);

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

                    if (item.getFieldName().equals("selection")) {
                        String flag = item.getString().trim();

                        //0-fileloadup;1-cardno;
                        if (flag.equals("0")) { //�ļ��ϴ�
                            uploadFileFlag = true;
                        } else if (flag.equals("1")) { //��������
                            uploadFileFlag = false;
                        }

                        pmp.put("fileFlag", new Boolean(uploadFileFlag));
                    }

                    if (item.getFieldName().equals("cardTypeVal")) {
                        String cardTypeVal = item.getString().trim();
                        pmp.put("cardType", cardTypeVal);
                    }

                    if (item.getFieldName().equals("inStorageId")) {
                        pmp.put("inStorageId", item.getString().trim());
                    }

                    if (item.getFieldName().equals("outStorageId")) {
                        pmp.put("outStorageId", item.getString().trim());
                    }

                    if (item.getFieldName().equals("startCardNo")) {
                        pmp.put("startCardNo", item.getString().trim());
                    }

                    if (item.getFieldName().equals("endCardNo")) {
                        pmp.put("endCardNo", item.getString().trim());
                    }

                    if (!item.isFormField() && uploadFileFlag) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));

                        while ((str = read.readLine()) != null) {
                            str = str.trim();

                            if ((str == "") || (str.length() > 50)) {
                                errCounter++;

                                continue;
                            }

                            fileList.add(str);
                        }

                        read.close();
                        System.out.println("�ϴ�����Ч��������Ϊ��" + fileList.size() +
                            " ����"); //��Ч����
                    }
                }

                // �������Ա�Ͳ�����Ϣ
                pmp.put("operId", operId);
                pmp.put("departId", departId);

//                bean = (RcSIM) EJBHelper.creatEJB("ejb/RcSIM", RcSIMHome.class);
//
//                List list = (List) bean.assignSim(pmp, fileList);
                
                pmp.put("fileList", fileList);
                List list = (List)ServiceManager.callJavaBeanService("RcSimBO","assignSim" ,pmp);

                if ((list == null) || (list.size() <= 0)) {
                    result = "0";
                } else {
                    result = (String) list.get(0);
                }

                if (uploadFileFlag) {
                    result = "<script>alert('" + result + "�����ݵ���ɹ�,Ϊ����˵�" +
                        errCounter + "����ʽ��Ч������');window.close();</script>";
                } else {
                    result = "<script>alert('" + result +
                        "������ִ�гɹ���');window.close();</script>";
                }
            }
        } catch (Exception ex) {
            //bean = null;
            ex.printStackTrace();
            result = ex.getCause().toString().split(":")[1];

            String outFunction = "<script>  var fso = new ActiveXObject(\"Scripting.FileSystemObject\");";
            outFunction += " var clientfile = fso.CreateTextFile(\"c:/err_info.txt\", true);";

            if (uploadFileFlag) {
                outFunction += (" clientfile.WriteLine('Ϊ����˵�" + errCounter +
                "����ʽ��Ч������');");
            } else {
            }

            outFunction += (" clientfile.WriteLine('���������������Ϣ��" + result +
            "');");

            outFunction += " clientfile.Close(); fso=null;";

            if (uploadFileFlag) {
                result = outFunction +
                    "alert('���ݵ���ʧ�ܣ�����������ݵĸ�ʽ����Ч�� ����ϸ��Ϣ��c:/err_info.txt');window.close();</script>";
            } else {
                result = outFunction +
                    "alert('����ʧ�ܣ���ϸ��Ϣ��c:/err_info.txt');window.close();</script>";
            }
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
