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
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ztesoft.crm.salesres.vo.RcNoVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class BatchModifyNoLevelService extends HttpServlet {
    public BatchModifyNoLevelService() {
    }

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
        List formatErrList = new ArrayList(); //��ʽ����
        List douList = new ArrayList(); //�ظ�����
        List failList = null;
        String alertInfo = "";
        String successNum = "";
        RcNoVO rcNoVO = new RcNoVO();
        String flag = request.getParameter("flag");

        Map rtnMap = null;
        String result = "0";
        String info = "";
        StringBuffer failNos = new StringBuffer("");
        String failNosStr = null;

        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/sr/nosim/batchModifyNoLevel.jsp");

        //RcNoVO�ֶ�
        int errCounter = 0;
        long lngMaxFileSize = 10000 * 1024;
        boolean isMultipart = FileUpload.isMultipartContent(request);

        try {
            GlobalVariableHelper helper = new GlobalVariableHelper(request);
            String operId = helper.getVariable(GlobalVariableHelper.OPER_ID);
            String operCode = helper.getVariable(GlobalVariableHelper.OPER_CODE);
            String departId = helper.getVariable(GlobalVariableHelper.DEPART_ID);

            if (isMultipart) { // ���ļ��ϴ�

                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(100000);
                factory.setRepository(new java.io.File("./upload/"));

                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(lngMaxFileSize);

                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();
                List noList = new ArrayList();

                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (item.getFieldName().equals("storageId")) {
                        rcNoVO.setStorageId(item.getString());
                    }

                    if (item.getFieldName().equals("storageName")) {
                        rcNoVO.setStorageName(item.getString());
                    }

                    if (item.getFieldName().equals("effDate")) {
                        rcNoVO.setEffDate(item.getString());
                    }

                    if (item.getFieldName().equals("expDate")) {
                        rcNoVO.setExpDate(item.getString());
                    }

                    if (item.getFieldName().equals("rescLevel")) {
                        rcNoVO.setRescLevel(item.getString());
                    }

                    if (item.getFieldName().equals("balaMode")) {
                        rcNoVO.setBalaMode(item.getString());
                    }

                    if (item.getFieldName().equals("selfhelpflag")) {
                        rcNoVO.setSelfhelpflag(item.getString());
                    }

                    if (item.getFieldName().equals("lanId")) {
                        rcNoVO.setLanId(item.getString());
                    }

                    if (item.getFieldName().equals("regionId")) {
                        rcNoVO.setRegionId(item.getString());
                    }

                    if (item.getFieldName().equals("exchId")) {
                        rcNoVO.setExchId(item.getString());
                    }

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));

                        while ((str = read.readLine()) != null) {
                            str = str.trim();
                            datas = str.split(" ");

                            if ((datas == null) || (datas.length != 1)) { //�Ƿ��ʽ����
                                errCounter++;
                                str = str.replaceAll("\\\\", "\\\\\\\\");
                                formatErrList.add(str);

                                continue;
                            }

                            if (noList.contains(datas[0])) { //�Ƿ��ظ�
                                douList.add(datas[0]);

                                continue;
                            } else {
                                pList.add(datas[0]); //��ִ�е���
                            }

                            noList.add(datas[0]);
                        }

                        read.close();
                    }
                }

                if ((douList != null) && (douList.size() > 0)) {
                    for (int k = 0; k < douList.size(); k++) {
                        failNos.append((String) (douList.get(k)) +
                            " -- �������ı����ظ����֣�ֻ����һ��;");
                    }
                }

                if ((pList != null) && (pList.size() > 0)) {
                    Map map = new HashMap();
                    map.put("vo", rcNoVO);

                    String reworkIp = "";
                    map.put("reworkIp", reworkIp);
                    map.put("operId", operId);
                    map.put("operCode", operCode);

                    String falg = "1";
                    map.put("list", pList);
                    map.put("flag", falg);

                    SrRcNoService srRcNoService = new SrRcNoService();
                    rtnMap = srRcNoService.updateTxtBatch(map);

                    if (rtnMap != null) {
                        if (rtnMap.get("result") != null) {
                            result = (String) rtnMap.get("result");
                        }

                        if (rtnMap.get("info") != null) {
                            info = (String) rtnMap.get("info");
                        }

                        if (rtnMap.get("failNos") != null) {
                            failNos.append("-- �߼�����ļ�¼��--;");
                            failNos.append((String) rtnMap.get("failNos"));
                        }
                    } else {
                        result = "-1";
                        info = "����δ֪�쳣���󣬲���ʧ��!";
                    }
                } else {
                    result = "0";
                    info = "�ļ��ϴ�����Ч��¼��Ϊ0������֤";
                }
            }
        } catch (Exception ex) {
            request.setAttribute("err", ex.getMessage());
            ex.printStackTrace();
        } finally {
            request.setAttribute("result", result.trim());

            if ((formatErrList != null) && (formatErrList.size() > 0)) {
                failNos.append("-- ��ʽ����ļ�¼��--;");

                for (int i = 0; i < formatErrList.size(); i++) {
                    failNos.append((String) formatErrList.get(i) +
                        " -- ��ʽ���� ;");
                }
            }

            failNosStr = failNos.toString();
            //failNosStr = failNosStr.replaceAll(";", "$");
            request.setAttribute("failNos", failNosStr);
            request.setAttribute("info", info.trim());
            dispatcher.forward(request, response);
        }
    }
}
