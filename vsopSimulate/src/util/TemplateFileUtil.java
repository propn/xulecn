package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TemplateFileUtil {

	public static String readTempFile(String name) {
		String rteStr = "";
		String fileName = name + ".xml";
		try {
			rteStr = FileReaderUtil.readFileText(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			rteStr = "�ļ������ڻ��ȡ�ļ�����";
		}
		return rteStr;

	}

	public static String readDescFile(String name) {
		String rteStr = "";
		String fileName = name + ".txt";
		try {
			rteStr = FileReaderUtil.readFileText(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			rteStr = "�ļ������ڻ��ȡ�ļ�����";
		}
		return rteStr;

	}

	/**
	 * д�ļ�
	 * @param fileName
	 * @param str
	 * @throws Exception
	 */
	public static void writerFile(String fileName, String str) throws Exception {
		BufferedWriter writer = null;
		System.out.println(fileName);
		File file = new File(fileName);
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			writer.close();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}