package com.ztesoft.crm.salesres.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.component.common.staticdata.StaticAttrCache;
import com.ztesoft.component.common.staticdata.vo.StaticAttrVO;
import com.ztesoft.crm.salesres.vo.RcEntityReportVO;


public class TempFileDownLoad extends HttpServlet {
    /**
     * Constructor of the object.
     */
    public TempFileDownLoad() {
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
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        //		 设置字符集(显示中文)
        request.setCharacterEncoding("GBK");

        String lanId = request.getParameter("lanId");
        String countType = request.getParameter("countType");
        String sheetType = request.getParameter("sheetType");
        String date = request.getParameter("date");
        int pageIndex = 1;
        int pageSize = 10000;

        String[] headers = new String[] {
                "本地网", "期末到达数", "当期新增数", "当期销售数", "当期库存", "差异"
            };
        String[] columns = new String[] {
                "lanId", "stockNumEnd", "addNum", "saleNum", "stockNumCurr",
                "differentnum"
            };

        try {
            // 获取文件目录分隔符
            String separator = System.getProperty("file.separator");

            // 文件绝对路径
            String file_name = request.getParameter("file_name");

            // 文件原名称
            SrStockService service = new SrStockService();
            List pm =null;// service.qryRcEntityCount(lanId, countType, sheetType, date);

            if (pm != null) {
                response.reset();
                response.setContentType("application/x-msdownload");
                response.addHeader("Content-Disposition",
                    "attachment; filename=\"" + "cdmaTerminalReport.xls\"");
                transStaticData(pm);

                ImportExcelUtil util = new ImportExcelUtil();
                String time = "";

                if ("1".equals(sheetType)) {
                    util.exportExcel("sheet1", "CDM终端统计日报（" + date + "）",
                        headers, columns, pm, response.getOutputStream());
                } else if ("2".equals(sheetType)) {
                    util.exportExcel("sheet1",
                        "CDM终端统计月报（" + date.substring(0, 7) + "）", headers,
                        columns, pm, response.getOutputStream());
                } else {
                    util.exportExcel("sheet1",
                        "CDM终端统计年报（" + date.substring(0, 4) + "）", headers,
                        columns, pm, response.getOutputStream());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            try {
                response.setContentType("text/html;charset=gb2312");

                PrintWriter out;
                out = response.getWriter();
                out.println("<script>alert('系统异常错误');</script>");
                out.flush();
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
        }
    }

    // 取得附件的名称
    public static String getAttachName(String file_name) {
        if (file_name == null) {
            return "";
        }

        file_name = file_name.trim();

        int iPos = 0;
        iPos = file_name.lastIndexOf("\\");

        if (iPos > -1) {
            file_name = file_name.substring(iPos + 1);
        }

        iPos = file_name.lastIndexOf("/");

        if (iPos > -1) {
            file_name = file_name.substring(iPos + 1);
        }

        iPos = file_name.lastIndexOf(File.separator);

        if (iPos > -1) {
            file_name = file_name.substring(iPos + 1);
        }

        return file_name;
    }

    // UTF8转码
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if ((c >= 0) && (c <= 255)) {
                sb.append(c);
            } else {
                byte[] b;

                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }

                for (int j = 0; j < b.length; j++) {
                    int k = b[j];

                    if (k < 0) {
                        k += 256;
                    }

                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }

        String s_utf8 = sb.toString();
        sb.delete(0, sb.length());
        sb.setLength(0);
        sb = null;

        return s_utf8;
    }

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to
     * post.
     *
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println(
            "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
        out.println("  <BODY>");
        out.print("    This is ");
        out.print(this.getClass());
        out.println(", using the POST method");
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

    private void transStaticData(List list) {
        if ((list != null) && (list.size() > 0)) {
            StaticAttrCache cache = StaticAttrCache.getInstance();
            RcEntityReportVO voTemp = null;
            List lanList = cache.getAttrData("DC_LAN_CODE");
            StaticAttrVO dataVO = null;
            Map classMap = new HashMap();

            if ((lanList != null) && (lanList.size() > 0)) {
                for (int j = 0; j < lanList.size(); j++) {
                    dataVO = (StaticAttrVO) lanList.get(j);

                    if (dataVO != null) {
                        classMap.put(dataVO.getAttrValue(),
                            dataVO.getAttrValueDesc());
                    }
                }
            }

            for (int i = 0; i < list.size(); i++) {
                voTemp = (RcEntityReportVO) list.get(i);

                if ((voTemp.getLanId() != null) &&
                        (voTemp.getLanId().trim().length() > 0)) {
                    if (classMap.get(voTemp.getLanId().trim()) != null) {
                        voTemp.setLanId((String) classMap.get(
                                voTemp.getLanId().trim()));
                    }
                }
            }
        }
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException
     *             if an error occure
     */
    public void init() throws ServletException {
        // Put your code here
    }
}
