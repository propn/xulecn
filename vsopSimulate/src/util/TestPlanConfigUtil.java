package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestPlanConfigUtil {
	/**
	 * �ж��ļ��Ƿ����
	 * @param serverName
	 * @param sceneName
	 * @return
	 */
	public static boolean isTestPlanExits(String bendiStr, String testPlan) {
		boolean flag = true;
		//�ж��ļ����Ƿ����
		String dirFileName = Config.TestPlanConfigFilePath + bendiStr;
		File dirFile = new File(dirFileName);
		if (!dirFile.exists()) {
			dirFile.mkdir();
			return false;
		}
		//�ж��ļ��Ƿ����
		File sceneFile = new File(dirFileName + "/" + testPlan + ".txt");
		if (!sceneFile.exists()) {
			return false;
		}
		return flag;
	}
	
	/**
	 * дִ�мƻ������ļ�
	 * @param serverName
	 * @param sceneName
	 * @return
	 */
	public static void writerTestPlanConfigFile(String bendiStr, String testPlanName,String str) throws Exception{
		boolean flag = true;
		//�ж��ļ����Ƿ����
		String dirFileName = Config.TestPlanConfigFilePath + bendiStr;
		File dirFile = new File(dirFileName);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		String testPlanFileName = Config.TestPlanConfigFilePath + bendiStr + "/" + testPlanName + ".txt";
		BufferedWriter writer = null;
		File file = new File(testPlanFileName);
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
	 * �õ��ļ��б�
	 * @param serverName
	 * @return
	 */
	public static List getTestPlanList(String testPlanName) {
		List list = new ArrayList();
		//����ģ���ڻ���ģ���ļ�·����
		File file = new File(Config.TestPlanConfigFilePath + testPlanName);
		if (!file.exists()) {
			file.mkdir();
			return list;
		}
		File[] sceneFiles = file.listFiles();
		for (int i = 0; i < sceneFiles.length; i++) {
			if (!sceneFiles[i].isDirectory()) {
				String fileName = sceneFiles[i].getName();
				String sceneName = fileName.substring(0, fileName.lastIndexOf('.'));
				list.add(sceneName);
			}
		}
		return list;
	}
	
	public static List getSendList(String testPlanName)throws Exception{
		List list = new ArrayList();
		String bendiName = Config.getConfig("province");
		String fileName = Config.TestPlanConfigFilePath+bendiName+"/"+testPlanName+".txt";
		BufferedReader reader = null;
		
		try {
			File file = new File(fileName);
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				//line =new String(line.getBytes("ISO8859-1"), "UTF-8");
				if(line!=null&&!line.equals("")){
					String[] sends = line.split("\\|");
					list.add(sends);
				}
				line = reader.readLine();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return list;
		
		
	}
	
	public static void deleteTestPlan(String testPlanName){
		String bendiName = Config.getConfig("province");
		String fileName = Config.TestPlanConfigFilePath+bendiName+"/"+testPlanName+".txt";
		File file = new File(fileName);
		file.delete();
		
	}
}
