package com.ztesoft.component.ui.taglib;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExportToExcelFile implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WritableWorkbook wwb = null;

	private WritableSheet writeSheet = null;

	public ExportToExcelFile(OutputStream os) {
		createWritableWorkBook(os);
	}

	public ExportToExcelFile(String exportPath) {
		createWritableWorkBook(exportPath);
	}

	public ExportToExcelFile(OutputStream os, String title) {
		createWritableWorkBook(os);
		createSheet(title);
	}

	/**
	 * createWorkBook
	 * 
	 * @param exportPath
	 *            String
	 */
	private void createWritableWorkBook(String exportPath) {
		try {
			// ����Workbook����, ֻ��Workbook����
			wwb = Workbook.createWorkbook(new File(exportPath));

		} catch (Exception e) {
			e.printStackTrace();
			// log.error("" + e.getMessage());
		}

	}

	/**
	 * createWorkBook
	 * 
	 * @param exportPath
	 *            String
	 */
	private void createWritableWorkBook(OutputStream os) {
		try {
			// Method 2����WritableWorkbookֱ��д�뵽�����
			wwb = Workbook.createWorkbook(os);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void createSheet(String title) {
		// ����Excel������
		writeSheet = wwb.createSheet(title, 0);
	}

	public void createExcelTitle(String titleName, int col) {
		try {
			if (titleName == null || titleName.equalsIgnoreCase("")) {
				return;
			}

			Label labelCFC2 = new Label(col, 0, titleName);
			// ws.setColumnView(i, 13);
			writeSheet.addCell(labelCFC2);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void createExcelContent(String content, int col, int row) {
		try {
			if (content == null || content.equals("")
					|| content.equalsIgnoreCase("null"))
				content = "";
			Label labelCFC2 = new Label(col, row, content);
			// ws.setColumnView(i, 13);
			writeSheet.addCell(labelCFC2);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * createXls �������Գɼ�ģ��
	 * 
	 * @param titles
	 *            String[]
	 * @param contents
	 *            List
	 */
	public void createXls(String title, List titleList, List titleFieldList,
			List contents) throws WriteException, IOException {

		// ����Excel������
		WritableSheet ws = wwb.createSheet(title, 0);

		/**
		 * д���ֶ�����
		 */
		for (int i = 0; i < titleList.size(); i++) {
			String titleName = (String) titleList.get(i);
			Label labelCFC2 = new Label(i, 0, titleName);
			// ws.setColumnView(i, 13);
			ws.addCell(labelCFC2);
		}

		for (int i = 0; i < contents.size(); i++) {
			Map map = (Map) contents.get(i);
			for (int j = 0; j < titleFieldList.size(); j++) {
				String titleName = (String) titleFieldList.get(j);
				if (titleName == null || titleName.equalsIgnoreCase("")) {
					continue;
				}
				String content = (String) map.get(titleName);
				if (content == null || content.equals("")
						|| content.equalsIgnoreCase("null"))
					content = "";
				Label labelCFC2 = new Label(j, i + 1, content);
				ws.addCell(labelCFC2);
			}
		}

		// д��Exel������
		wwb.write();
		// �ر�Excel����������
		wwb.close();

	}

	public void writeExcel() {
		// д��Exel������
		try {
			wwb.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * �رղ���
	 * 
	 */
	public void closeExcel() {
		if (wwb != null) {
			try {
				wwb.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		wwb = null;
	}
}
