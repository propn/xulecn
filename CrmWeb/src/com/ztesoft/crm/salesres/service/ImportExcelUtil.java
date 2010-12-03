/**
 *
 */
package com.ztesoft.crm.salesres.service;

import org.apache.commons.beanutils.*;

import org.apache.log4j.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.lang.reflect.InvocationTargetException;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ImportExcelUtil {
    private Logger logger = Logger.getLogger(ImportExcelUtil.class);
    private HSSFWorkbook wb = null;
    private HSSFSheet sheet = null;

    //	private IDoExcelRow idoRow = null;
    private StringBuffer message = new StringBuffer();

    public ImportExcelUtil() {
    }

    public ImportExcelUtil(InputStream input) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(input);
            wb = new HSSFWorkbook(fs);
            sheet = wb.getSheetAt(0);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            message.append("");
            logger.error(ioe);
        }
    }

    /**
     * ��̬��ȡexcel�е�һ�����ݣ�������һ��Object��
     * ���objName�������࣬���û�����idoRow���ԣ��ڸýӿ��ж�����δ�����Щ���Լ��ɣ�
     * ���û��������ֱ��ʹ�ø�����С�
     * @param objName:����Object�������ַ���
     * @param rowNO:�кţ���ȡexcel�ļ��еĵڼ�������
     * @param columns:����Ҫ�������е������������飬Ӧ�ú�excel��������Ӧ��
     * �����$��ͷ���У���ʾ������objName��������е���
     * @param map:�����objName�л���������excel�ļ���û�б���ֵ��������map�����ã�
     * ϵͳ����map�е�ֵ���а�objName�е���Ӧ���Ը�ֵ
     * @return Ҫ���ص����ɶ���
     */
    public Object readRow(String objName, int rowNO, String[] columns, Map map) {
        if ((rowNO < 0) || (objName == null) || (columns == null) ||
                (columns.length <= 0)) {
            return null;
        }

        Object cellValue = null;
        Object obj = null;
        BeanUtils beanUtil = new BeanUtils();
        HSSFRow row = sheet.getRow(rowNO);
        HSSFCell cell = null;
        DecimalFormat df = new DecimalFormat("#.000000");
        int cellType = HSSFCell.CELL_TYPE_STRING;
        List items = new ArrayList();

        try {
            obj = Class.forName(objName).newInstance();

            for (int i = 0; i < columns.length; i++) {
                cell = row.getCell((short) i);

                if (cell != null) {
                    cellType = cell.getCellType();

                    if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                        cellValue = df.format(cell.getNumericCellValue());
                    } else {
                        cellValue = cell.getStringCellValue();
                    }

                    //logger.info("cellValue:"+cellValue);
                    if (cellValue == null) {
                        continue;
                    }

                    //if(columns[i].startsWith("$")){
                    //	if(idoRow!=null)
                    //	   idoRow.doChildColumn(items,columns[i].substring(1),cellValue);
                    //}else{
                    beanUtil.copyProperty(obj, columns[i], cellValue);

                    //}
                } else {
                    continue;
                }
            }

            //			if(idoRow!=null)
            //				idoRow.setChildProperty(obj,items);
            if (map != null) {
                beanUtil.populate(obj, map);
            }

            //			if(idoRow!=null)
            //				idoRow.transRow(obj);
        } catch (IllegalAccessException e) {
            logger.error("ImportExcelUtil��readRowʱ������!", e);
        } catch (InvocationTargetException e) {
            logger.error("ImportExcelUtil��readRowʱ������!", e);
        } catch (Exception e) {
            logger.error("ImportExcelUtil��readRowʱ������!", e);
        }

        return obj;
    }

    /**
     * @param objName:Ҫ��ʼ���Ķ�����
     * @param initRowNO:�ӵڼ������ݿ�ʼ��ȡ
     * @param columns:��excel�ļ��е��ж�Ӧ�������$��ͷ���У���ʾ������objName��������е���
     * @param map:�����objName�л���������excel�ļ���û�б���ֵ��������map�����ã�
     * ϵͳ����map�е�ֵ���а�objName�е���Ӧ���Ը�ֵ
     * @return
     */
    public List readExcel(String objName, int initRowNO, String[] columns,
        Map map) {
        if ((initRowNO < 0) || (objName == null) || (columns == null) ||
                (columns.length <= 0)) {
            return null;
        }

        if (!isRightFormat(columns.length)) {
            message.append("Columns in Excel file is incorrect,please check!");

            return null;
        }

        ArrayList list = new ArrayList();

        for (int i = initRowNO; i <= sheet.getLastRowNum(); i++) {
            Object obj = readRow(objName, i, columns, map);
            list.add(obj);
        }

        return list;
    }

    /**
     * �ж�excel�ļ��е������Ƿ���ȷ
     * @param length���涨��excel�ļ���Ӧ���е��������ڳ��������ж���
     * @return
     */
    public boolean isRightFormat(int length) {
        HSSFRow row = sheet.getRow(0);
        int lengthRow = row.getLastCellNum();

        if (length == lengthRow) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return Returns the sheet.
     */
    public HSSFSheet getSheet() {
        return sheet;
    }

    /**
     * @param sheet The sheet to set.
     */
    public void setSheet(HSSFSheet sheet) {
        this.sheet = sheet;
    }

    /**
     * @return Returns the wb.
     */
    public HSSFWorkbook getWb() {
        return wb;
    }

    /**
     * @param wb The wb to set.
     */
    public void setWb(HSSFWorkbook wb) {
        this.wb = wb;
    }

    /**  �õ���������в�������Ϣ **/
    public String getMessage() {
        if (message.length() > 0) {
            return message.toString();
        } else {
            return null;
        }
    }

    /////////// ����Ϊдexcel ///////////////////////
    public void exportExcel(String sheetName, String title, String[] headers,
        String[] columns, List datalist, OutputStream out) {
        int startNo = 0;

        if ((title != null) && (title.trim().length() > 0)) {
            startNo = 1;
        }

        // ����һ��������
        HSSFWorkbook workbook = new HSSFWorkbook();

        // ����һ�����
        HSSFSheet sheet = workbook.createSheet(sheetName);
        HSSFRow row = null;
        HSSFCell cell = null;
        // ���ñ��Ĭ���п��Ϊ15���ֽ�
        sheet.setDefaultColumnWidth((short) 20);

        // ����һ����ʽ
        HSSFCellStyle style = workbook.createCellStyle();
        // ������Щ��ʽ
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // ����һ������
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // ������Ӧ�õ���ǰ����ʽ
        style.setFont(font);

        // ���ɲ�������һ����ʽ
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // ������һ������
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // ������Ӧ�õ���ǰ����ʽ
        style2.setFont(font2);

        // �������ı�����
        if ((title != null) && (title.trim().length() > 0)) {
            row = sheet.createRow(0);
            sheet.addMergedRegion(new Region(0, (short) 0, 0,
                    (short) (headers.length - 1)));
            cell = row.createCell((short) 0);
            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell.setCellStyle(style);
            cell.setCellValue(title);
        }

        //������������
        row = sheet.createRow(startNo);

        for (short i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell.setCellStyle(style);
            cell.setCellValue(headers[i]);
        }

        //�����������ݣ�����������
        Object obj = null;
        String colValue = "";
        BeanUtils beanUtil = new BeanUtils();
        Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
        Matcher matcher = null;

        for (int index = 0; index < datalist.size(); index++) {
            row = sheet.createRow(index + startNo + 1);
            obj = datalist.get(index);

            //���÷��䣬����javabean���Ե��Ⱥ�˳�򣬶�̬����getXxx()�����õ�����ֵ
            for (short i = 0; i < columns.length; i++) {
                cell = row.createCell(i);
                cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellStyle(style2);

                try {
                    colValue = beanUtil.getProperty(obj, columns[i]);

                    //�������ͼƬ���ݣ�������������ʽ�ж�textValue�Ƿ�ȫ�����������
                    if (colValue != null) {
                        cell.setCellValue(colValue);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }

        try {
            workbook.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //    	FileInputStream file;
        //		try {
        //			file = new FileInputStream("e:\\input.xls");
        //			ImportExcelUtil helper = new ImportExcelUtil(file);
        //			String[] columns = new String[]{"custCode","memClass"};
        //			List list = helper.readExcel("com.ztesoft.crm.cs.mb.vo.MbAssessInfoVO",1,columns,null);
        //			MbAssessInfoVO voTemp = null;
        //			if(list!=null&&list.size()>0)
        //			for(int i=0;i<list.size();i++){
        //				voTemp = (MbAssessInfoVO)list.get(i);
        //				System.out.println("voTemp.custCode:"+voTemp.getCustCode()
        //						+"||voTemp.memClass:"+voTemp.getMemClass());
        //		    }
        //		} catch (FileNotFoundException e) {
        //			e.printStackTrace();
        //		}
        String fileName = "D:\\testtestExcel.xls";

        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ImportExcelUtil helper = new ImportExcelUtil();

            //			com.ztesoft.crm.cs.mb.vo.MbAssessInfoVO vo1 = new com.ztesoft.crm.cs.mb.vo.MbAssessInfoVO();
            //			List list1 = new ArrayList();
            //			vo1.setAppDate("1990-09-09");
            //			vo1.setCertNo("123456789012345678");
            //			vo1.setCustName("������");
            //			list1.add(vo1);
            //			String[] headers = new String[]{"����","֤������","��������"};
            //			String[] columns = new String[]{"custName","certNo","appDate"};
            //			helper.exportExcel("excel","���Ա��� 2009-07-10", headers, columns, list1, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return ���� idoRow��
     */

    //	public IDoExcelRow getIdoRow() {
    //		return idoRow;
    //	}
    //	/**
    //	 * @param idoRow Ҫ���õ� idoRow��
    //	 */
    //	public void setIdoRow(IDoExcelRow idoRow) {
    //		this.idoRow = idoRow;
    //	}
}
