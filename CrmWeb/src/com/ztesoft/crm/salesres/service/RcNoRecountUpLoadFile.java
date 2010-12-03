package com.ztesoft.crm.salesres.service;

import java.io.IOException;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcNoRecountUpLoadFile extends HttpServlet {
    public RcNoRecountUpLoadFile() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/sr/nosim/loadRecountIframe.jsp");

        response.setContentType("text/html;charset=gb2312");

        PrintWriter out = response.getWriter();
        List noList = new ArrayList();
        String storageId = "";
        String outStr = "";
        String rtn = "";
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

                POIFSFileSystem fs = null;
                HSSFWorkbook wb = null;
                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();

                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (item.getFieldName().equals("storageId")) {
                        storageId = item.getString();
                    }

                    if (!item.isFormField()) {
                        fs = new POIFSFileSystem(item.getInputStream());
                        wb = new HSSFWorkbook(fs);

                        int sn = wb.getNumberOfSheets();

                        for (int i = 0; i < sn; i++) { //行数

                            HSSFSheet sheet = wb.getSheetAt(i);

                            if (sheet.getPhysicalNumberOfRows() == 0) {
                                continue;
                            }

                            for (int j = 0;
                                    j < sheet.getPhysicalNumberOfRows(); j++) {
                                HSSFRow row = sheet.getRow(j);
                                String str = "";
                                HSSFCell cell = row.getCell((short) 0);

                                if (cell == null) {
                                    throw new Exception("数据格式不正确");
                                }

                                if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                    if (!"".equals(String.valueOf(
                                                    cell.getNumericCellValue())) &&
                                            (String.valueOf(
                                                cell.getNumericCellValue()) != null)) {
                                        str = String.valueOf((long) (cell.getNumericCellValue()));
                                    }
                                }

                                noList.add(str);
                            }
                        }
                    }
                }
            }

            Map map = new HashMap();
            map.put("storageId", storageId);
            map.put("noList", noList);
            map.put("recountType", "1");
            map.put("operId", operId);
            map.put("departId", departId);

            rtn = (String)ServiceManager.callJavaBeanService("RcNoBo","reCountLevel" , map) ;
                
            //rtn = bean.reCountLevel(map);
            outStr = "<head><META HTTP-EQUIV=\"library\" CONTENT=\"kernel,customerpilot,calendar,validator\"><script type=\"text/javascript\" src=\"../public/components/common2.js\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><ui:import library=\"\"></ui:import></head>";
            out.println(outStr + "<script> alert('重新计算成功');</script>");
            //dispatcher.forward(request, response);
            response.sendRedirect("/SrWeb/sr/nosim/loadRecountIframe.jsp?msg=" +
                rtn);
        } catch (Exception ex) {
            ex.printStackTrace();
            out.println(outStr + "<script> alert('重新计算失败');</script>");
            response.sendRedirect("/SrWeb/sr/nosim/loadRecountIframe.jsp?msg=" +
                rtn);
        } finally {
            out.flush();
            out.close();
        }
    }
}
