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
import com.ztesoft.crm.salesres.vo.RcNosegWanVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class NoSegWanUpLoad extends HttpServlet {
    /**
     * Constructor of the object.
     */
    public NoSegWanUpLoad() {
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
                "/sr/nosim/uploadNoSegWanFrame.jsp");

        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        List fileList = new ArrayList();
        String str = "";
        String lanId = "";
        String operCode = "";
        String departId = "";
        String noGrpId = "";
        String segName = "";
        String result = "";
        String successCount = "";
        String failCount = "";
        List failList = null;
        int formatErrCount = 0;
        List formatErrList = new ArrayList();
        Map enticodeMap = new HashMap();

        int count = 0;
        RcNosegWanVO vo = new RcNosegWanVO();
        long lngMaxFileSize = 10000 * 1024;
        boolean isMultipart = org.apache.commons.fileupload.FileUpload.isMultipartContent(request);

        try {
            GlobalVariableHelper helper = new GlobalVariableHelper(request);
            operCode = helper.getVariable(GlobalVariableHelper.OPER_CODE);
            departId = helper.getVariable(GlobalVariableHelper.DEPART_ID);
            lanId = helper.getVariable(GlobalVariableHelper.LAN_ID);

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

                    if (item.getFieldName().equals("noGrpId")) {
                        noGrpId = item.getString();
                        vo.setNoGrpId(noGrpId);
                    }

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));
                        RcNosegWanVO voTemp = null;
                        String[] tempArr = new String[2];

                        while ((str = read.readLine()) != null) {
                            str = str.trim();
                            str = str.replaceAll("\\\\", "\\\\\\\\");

                            if (str.trim().split(" ").length != 2) {
                                formatErrCount++;
                                formatErrList.add(str +
                                    " --- 格式错误，一行数据应该仅包含起始号码和终止号码，且用空格隔开");

                                continue;
                            }

                            tempArr = str.trim().split(" ");

                            // 检验是否是数字
                            if (!tempArr[0].matches("\\d{1,30}") ||
                                    !tempArr[1].matches("\\d{1,30}") ||
                                    ((Long.parseLong(tempArr[0]) < 0) ||
                                    (Long.parseLong(tempArr[1]) < 0))) {
                                formatErrCount++;
                                formatErrList.add(str +
                                    " --- 格式错误，万号段的起始号码和终止号码必须是数字并且长度小于30位");

                                continue;
                            }

                            if ((Long.parseLong(tempArr[1]) -
                                    Long.parseLong(tempArr[0])) != 9999) {
                                formatErrCount++;
                                formatErrList.add(str +
                                    " --- 格式错误，万号段的起始号码和终止号码之间必须间隔10000个号码");

                                continue;
                            }

                            if (enticodeMap.get(str) != null) {
                                formatErrCount++;
                                formatErrList.add(str +
                                    " --- 格式错误，上传文件中已有该万号段");

                                continue;
                            }

                            segName = this.getSetName(tempArr[0], tempArr[1]);

                            if ((segName == null) ||
                                    (segName.trim().length() < 1)) {
                                formatErrCount++;
                                formatErrList.add(str +
                                    " --- 格式错误，万号段的起始号码和终止号码开头的第1位数字必须相同");

                                continue;
                            }

                            voTemp = new RcNosegWanVO();
                            voTemp.setBeginNo(tempArr[0]);
                            voTemp.setEndNo(tempArr[1]);
                            voTemp.setDepartId(departId);
                            voTemp.setOperCode(operCode);
                            voTemp.setLanId(lanId);
                            voTemp.setSegName(segName);
                            fileList.add(voTemp);
                            enticodeMap.put(str, str);
                            segName = null;
                        }

                        read.close();
                    }
                }

                //ejb...
                Map paraMap = new HashMap();
                vo.setLanId(lanId);

                paraMap.put("paramVO", vo);
                paraMap.put("InputList", fileList);
                paraMap.put("operCode", operCode);
                paraMap.put("departId", departId);

                Map rtnMap = (Map)ServiceManager.callJavaBeanService("SrRcNoSegWanBO","noSegWanUpLoad" ,paraMap);

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
            request.setAttribute("successCount", successCount);
            request.setAttribute("failCount", failCount);
            request.setAttribute("formatErrCount",
                String.valueOf(formatErrCount));

            if ((formatErrList != null) && (formatErrList.size() > 0)) {
                request.setAttribute("formatErrList", formatErrList);
            }

            if ((failList != null) && (failList.size() > 0)) {
                request.setAttribute("failList", failList);
            }

            dispatcher.forward(request, response);
        }
    }

    private String getSetName(String beginNo, String endNo) {
        String segName = "";

        if ((beginNo != null) && (beginNo.trim().length() > 0) &&
                (endNo != null) && (endNo.trim().length() > 0)) {
            StringBuffer buffer = new StringBuffer();
            char[] beginArr = beginNo.toCharArray();
            char[] endArr = endNo.toCharArray();
            int length = (beginArr.length > endArr.length) ? endArr.length
                                                           : beginArr.length;

            for (int i = 0; i < length; i++) {
                if (beginArr[i] == endArr[i]) {
                    buffer.append(beginArr[i]);
                } else {
                    break;
                }
            }

            segName = buffer.toString();
        } else if ((beginNo != null) && (beginNo.trim().length() > 0)) {
            segName = beginNo;
        } else if ((endNo != null) && (endNo.trim().length() > 0)) {
            segName = endNo;
        }

        return segName;
    }
}
