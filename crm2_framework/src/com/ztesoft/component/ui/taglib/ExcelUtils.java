package com.ztesoft.component.ui.taglib;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtils {
	/**
	 * ������Ԫ��ͷ
	 * 
	 * @param titleList
	 * @param sheet
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public static void createTheadTitle(List titleList, WritableSheet sheet) throws WriteException,
			RowsExceededException {
		jxl.write.Label label;
		for (int i = 0; i < titleList.size(); i++) {
			// Label(�к�,�к� ,���� )
			label = new jxl.write.Label(i, 0, (String) titleList.get(i)); // put the title in row1
			sheet.addCell(label);
		}
	}

	/**
	 * ������Ԫ����
	 * 
	 * @param contentList
	 * @param sheet
	 * @param workbook
	 * @return
	 * @throws Exception
	 */
	public static void createContent(List contentList, WritableSheet sheet) throws Exception {

		try {
			for (int i = 0; i < contentList.size(); i++) {
				// Label(�к�,�к� ,���� )
				List mapList = (List) contentList.get(i);
				for (int j = 0; j < mapList.size(); j++) {
					String value = (String) mapList.get(j);
					jxl.write.Label label = new jxl.write.Label(j, i + 1, value); // put the title in row1

					sheet.addCell(label);
				}

			}
		} catch (Exception e) {

			throw new Exception(e);
		} finally {

		}

	}

	public static void workWrite(WritableWorkbook workbook) throws Exception {
		try {

			workbook.write();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public static void workClose(WritableWorkbook workbook) throws Exception {
		try {

			workbook.close();
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public static void fileClose(OutputStream os) throws Exception {
		try {

			
			os.close();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * ����sheet
	 * 
	 * @param worksheet
	 * @param workbook
	 * @return
	 */
	public static WritableSheet createSheet(String worksheet, WritableWorkbook workbook) {
		WritableSheet sheet = workbook.createSheet(worksheet, 0); // ��ӵ�һ��������
		return sheet;
	}

	/**
	 * ����workbook
	 * 
	 * @param os
	 * @return
	 * @throws IOException
	 */
	public static WritableWorkbook createWorkbook(OutputStream os) throws IOException {
		WritableWorkbook workbook;
		workbook = Workbook.createWorkbook(os);
		return workbook;
	}

}
