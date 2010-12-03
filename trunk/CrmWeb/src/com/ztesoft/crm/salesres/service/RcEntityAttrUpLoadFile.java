package com.ztesoft.crm.salesres.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcEntityAttrUpLoadFile extends HttpServlet {
    /**
     * Constructor of the object.
     */
    public RcEntityAttrUpLoadFile() {
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
                "/sr/rcEntityAttrInput.jsp");

        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        List fileList = new ArrayList();
        String successCount = "";
        String failCount = "";

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

                    if (!item.isFormField()) {
                        POIFSFileSystem fs = null;
                        HSSFWorkbook wb = null;
                        fs = new POIFSFileSystem(item.getInputStream());
                        wb = new HSSFWorkbook(fs);

                        int sn = wb.getNumberOfSheets();

                        for (int i = 0; i < sn; i++) {
                            HSSFSheet sheet = wb.getSheetAt(i);

                            if (sheet.getPhysicalNumberOfRows() == 0) {
                                continue;
                            }

                            for (int j = 0;
                                    j < sheet.getPhysicalNumberOfRows(); j++) {
                                HSSFRow row = sheet.getRow(j);
                                String[] str = new String[3];

                                for (int k = 0; k < 3; k++) {
                                    HSSFCell cell = row.getCell((short) k);

                                    if (cell == null) {
                                        throw new Exception("数据格式不正确");
                                    }

                                    //									if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                    //										System.out.println(cell.getStringCellValue());
                                    //									} else {
                                    //										System.out.println(cell.getBooleanCellValue());
                                    //									}
                                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                        if (!"".equals(String.valueOf(
                                                        cell.getNumericCellValue())) &&
                                                (String.valueOf(
                                                    cell.getNumericCellValue()) != null)) {
                                            str[k] = String.valueOf((int) (cell.getNumericCellValue()));
                                        }
                                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                        if (!"".equals(
                                                    cell.getStringCellValue()) &&
                                                (cell.getStringCellValue() != null)) {
                                            str[k] = cell.getStringCellValue();
                                        }
                                    }
                                }

                                fileList.add(str);
                            }
                        }
                    }
                }

                //Map rtnMap = srEntity.addMultiRcEntityAttr(fileList);
                Map map = new HashMap();
                map.put("fileList", fileList);
                Map rtnMap = (Map)ServiceManager.callJavaBeanService("RcEntityBo","addMultiRcEntityAttr" ,map);
                if (rtnMap != null) {
                    if (rtnMap.get("sCount") != null) {
                        successCount = (String) rtnMap.get("sCount");
                    }

                    if (rtnMap.get("fCount") != null) {
                        failCount = (String) rtnMap.get("fCount");
                    }
                }

                String outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../../../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";
                out.println("<script> alert('本次操作成功导入" + successCount + "条,失败" +
                    failCount + "条');window.close();</script>");
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();

            String err_msg = "";

            if (ex.getMessage() != null) {
                err_msg = ex.getMessage()
                            .substring(0,
                        ((ex.getMessage().length() <= 128)
                        ? (ex.getMessage().length() - 1) : 127));
                err_msg = err_msg.replaceAll("\\n", " ");
            }

            String outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../../../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";
            out.println("<script> alert('上传文件失败!" + err_msg +
                "');window.close();</script>");
        } catch (IOException ex) {
            ex.printStackTrace();

            String outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../../../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";
            out.println(
                "<script> alert('EXCEL文件损坏或不是EXCEL文件,解析失败,请上传正常的EXCEL文件');window.close();</script>");
        } catch (Exception ex) {
            ex.printStackTrace();

            String err_msg = "";

            if (ex.getMessage() != null) {
                err_msg = ex.getMessage()
                            .substring(0,
                        ((ex.getMessage().length() <= 128)
                        ? (ex.getMessage().length() - 1) : 127));
                err_msg = err_msg.replaceAll("\\n", " ");
            }

            String outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../../../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";
            out.println("<script> alert('上传文件失败!" + err_msg +
                "');window.close();</script>");
        } finally {
            out.flush();
            out.close();
        }
    }
}
