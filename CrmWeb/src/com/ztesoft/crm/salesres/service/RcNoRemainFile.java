package com.ztesoft.crm.salesres.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

import com.ztesoft.crm.salesres.vo.RcNoRemainInfoVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcNoRemainFile extends HttpServlet {
    public RcNoRemainFile() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/sr/nosim/uploadNoremain.jsp");
        request.setCharacterEncoding("gb2312");

        //response.setContentType("text/html;charset=gb2312");
        String str = "";
        StringBuffer noBuff = new StringBuffer();
        String rescState = "";
        String state = "";
        String states = "";
        String statesInfo = "";
        String result = "0";
        String info = "";
        String failNos = "";
        List douList = new ArrayList(); //记录重复的行
        List noList = new ArrayList();

        RcNoRemainInfoVO voo = new RcNoRemainInfoVO();

        long lngMaxFileSize = 10000 * 1024;
        boolean isMultipart = org.apache.commons.fileupload.FileUpload.isMultipartContent(request);

        try {
            GlobalVariableHelper helper = new GlobalVariableHelper(request);
            String operId = helper.getVariable(GlobalVariableHelper.OPER_ID);
            String departId = helper.getVariable(GlobalVariableHelper.DEPART_ID);
            String provId = helper.getVariable(GlobalVariableHelper.PROV_ID);

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
                        voo.setStorageId(item.getString());
                    }

                    //          if (item.getFieldName().equals("storageName")) {
                    //        	  voo.setStorageName( item.getString());
                    //		  }
                    if (item.getFieldName().equals("remainNum")) {
                        voo.setRemainNum(item.getString());
                    }

                    if (item.getFieldName().equals("remainFlag")) {
                        voo.setRemainFlag(item.getString());
                    }

                    if (item.getFieldName().equals("endRemainTime")) {
                        voo.setEndRemainTime(item.getString());
                    }

                    //          if (item.getFieldName().equals("comments")) {
                    //        	  voo.setComments( item.getString());
                    //		  }
                    if (item.getFieldName().equals("rescState")) {
                        voo.setRescState(item.getString());
                    }

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));

                        //int num = 1;
                        while ((str = read.readLine()) != null) {
                            str = str.trim();

                            if (noList.contains(str)) { //是否重复
                                douList.add(str);

                                continue;
                            }

                            if (!str.equals("") &&
                                    (str.getBytes().length <= 50)) {
                                noBuff.append(str).append(",");
                            }

                            noList.add(str);
                        }

                        read.close();
                    }
                }

                SrRcNoService service = new SrRcNoService();

                if (voo != null) {
                    voo.setOperId(operId + "@" + provId); //传入，表示广西
                    voo.setDepartId(departId);
                    voo.setRemainFlag("0");
                    voo.setStorageName(request.getParameter("storageName"));
                    voo.setComments(request.getParameter("comments"));
                }

                Map retMap = service.geneNoRemain2(voo, noBuff.toString(), "1");
                String[] ret = new String[3];

                info = "操作成功!";

                if (ret != null) {
                    //          result = ret[0];
                    //          info = ret[1];
                    //          failNos += ret[2];
                    result = (String) retMap.get("result");
                    info = (String) retMap.get("info");
                    failNos += (String) retMap.get("failNos");
                } else {
                    result = "-1";
                    info = "发生未知异常错误，操作失败!";
                }

                if ((douList != null) && (douList.size() > 0)) {
                    result = "0";

                    for (int k = 0; k < douList.size(); k++) {
                        failNos += (douList.get(k) + " -- 号码在文本里重复出现，只处理一次；\n");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result = "-1";
            info = "发生未知异常错误，操作失败!";
        } finally {
            request.setAttribute("result", result);
            failNos = failNos.replaceAll("\n", "               ");
            request.setAttribute("failNos", failNos);
            request.setAttribute("info", info);
        }

        dispatcher.forward(request, response);
    }
}
