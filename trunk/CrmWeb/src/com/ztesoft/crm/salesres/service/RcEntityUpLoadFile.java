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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcEntityUpLoadFile extends HttpServlet {
    /**
     * Constructor of the object.
     */
    public RcEntityUpLoadFile() {
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
        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        List fileList = new ArrayList();
        String str = "";
        int result = 0;
        int maxCount = 0;
        String[] retMsgArr = null;
        long lngMaxFileSize = CrmConstants.MAX_UPLOAD_SIZE;
        boolean isMultipart = org.apache.commons.fileupload.FileUpload.isMultipartContent(request);

        try {
            GlobalVariableHelper helper = new GlobalVariableHelper(request);
            String operId = helper.getVariable(GlobalVariableHelper.OPER_ID);

            if (isMultipart) { // 是文件上传

                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(100000);
                factory.setRepository(new java.io.File("./upload/"));

                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(lngMaxFileSize);

                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();

                if (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (!item.isFormField()) {
                        BufferedReader read = new BufferedReader(new InputStreamReader(
                                    item.getInputStream()));

                        while ((str = read.readLine()) != null) {
                            str = str.trim();
                            fileList.add(str);
                            maxCount++;
                        }

                        read.close();
                        Map m = new HashMap();
                        m.put("fileList", fileList);
                        retMsgArr = (String[])ServiceManager.callJavaBeanService("RcEntityBo","addMultiRcEntityEasy" ,m);

                        //addMultiRcEntityEasy
                        //retMsgArr = srEntity.addMultiRcEntity(fileList);
                    }
                }

                String outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../../../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";

                String outFunction = "  var fso = new ActiveXObject(\"Scripting.FileSystemObject\");";
                outFunction += " var clientfile = fso.CreateTextFile(\"c:/rcentity_info.crm\", true);";

                for (int i = 0; i < retMsgArr.length; i++) {
                    outFunction += (" clientfile.WriteLine('" + retMsgArr[i] +
                    "');");
                }

                outFunction += " clientfile.Close(); fso=null;";

                String alertStr = retMsgArr[retMsgArr.length - 1] +
                    ",详细数据请查看 c:/rcentity_info.crm 文件.";

                //out.println(outStr+"<script>"+outFunction+" alert("+alertStr+");window.close();</script>");
                String allStr = "<script>" + outFunction + "</script>";

                out.println(outStr + allStr);
                out.println("<script> alert('" + alertStr +
                    "');window.close();</script>");
                out.close();
            }
        } //end try;
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occure
     */
    public void init() throws ServletException {
        // Put your code here
    }
}
