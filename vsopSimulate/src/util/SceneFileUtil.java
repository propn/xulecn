package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SceneFileUtil {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*	File file = new File("D:\\IDE\\eclipse\\test2.log");
			if(!file.exists()){
				System.out.println("yes");
			}
			File d = new File("D:\\IDE\\eclipse\\test2");
			String fileName = file.getName();
			d.mkdir();
			String sceneName = fileName.substring(0, fileName.lastIndexOf('.'));
			System.out.println(sceneName);
			*/

		//deleteFileDir("G:\\CRMworkspace\\test");
		File file = new File("G:\\CRMworkspace\\test");

		file.renameTo(new File("G:\\CRMworkspace\\test1"));
	}

	/**
	 * 得到文件列表
	 * @param serverName
	 * @return
	 */
	public static List getSceneList(String serverName) {
		List list = new ArrayList();
		String bendiName = Config.getConfig("province");
		//场景模板在基本模板文件路径下
		File file = new File(Config.TEMPLATE_FILE_PATH + bendiName+"/"+serverName);
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

	
	/**
	 * 得到文件列表
	 * @param serverName
	 * @return
	 */
	public static List getBendSceneList(String serverName,String bendiName) {
		List list = new ArrayList();
		
		//场景模板在基本模板文件路径下
		File file = new File(Config.TEMPLATE_FILE_PATH + bendiName+"/"+serverName);
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
	public static String getSceneFileStr(String serverName, String sceneName) {
		String bendiName = Config.getConfig("province");
		String retStr = "";
		String FileName = Config.TEMPLATE_FILE_PATH + bendiName+"/"+serverName + "/" + sceneName + ".xml";
		try {
			retStr = FileReaderUtil.readSceneFileText(FileName);
		} catch (Exception e) {
			retStr = "场景配置报文不存在或读取文件错误！";
		}

		return retStr;
	}

	/**
	 * 判断文件是否存在
	 * @param serverName
	 * @param sceneName
	 * @return
	 */
	public static boolean isSceneExits(String serverName, String sceneName) {
		boolean flag = true;
		String bendiName = Config.getConfig("province");
		//判断文件夹是否存在
		String dirFileName = Config.TEMPLATE_FILE_PATH +bendiName+"/"+ serverName;
		File dirFile = new File(dirFileName);
		if (!dirFile.exists()) {
			dirFile.mkdir();
			return false;
		}
		//判断文件是否存在
		File sceneFile = new File(dirFileName + "/" + sceneName + ".xml");
		if (!sceneFile.exists()) {
			return false;
		}
		return flag;
	}

	public static void writerSceneFile(String serverName, String sceneName, String xmlStr) throws Exception {
		String bendiName = Config.getConfig("province");
		String sceneFileName = Config.TEMPLATE_FILE_PATH + bendiName+"/"+serverName + "/" + sceneName + ".xml";
		BufferedWriter writer = null;
		File file = new File(sceneFileName);
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(xmlStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			writer.close();
		}

	}

	//删除文件夹
	public static void deleteFileDir(String filepath) {
		File file = new File(filepath);
		deleteFileDir(file);
	}

	public static void deleteFileDir(File file) {

		if (file.isDirectory()) {
			File[] child = file.listFiles();
			if (child != null && child.length != 0) {
				for (int i = 0; i < child.length; i++) {
					deleteFileDir(child[i]);
					child[i].delete();
				}
			}
		}
		file.delete();
	}

	public static void renameFile(String oldFilePath, String newFilePath) {
		File oldfile = new File(oldFilePath);
		File newFile = new File(newFilePath);
		oldfile.renameTo(newFile);
	}
	
	public static List getBendiList(){
		List list = new ArrayList();
		File file = new File(Config.TEMPLATE_FILE_PATH);
		File[] Files = file.listFiles();
		for (int i = 0; i < Files.length; i++) {
			if(Files[i].isDirectory()){
				String dName = Files[i].getName();
				if(dName.indexOf(".")==-1){
					list.add(dName);
				}
			}
		}
		return list;
	}
	
	public static String getSceneFileStr(String bendiName,String serverName, String sceneName) {
		
		String retStr = "";
		String FileName = Config.TEMPLATE_FILE_PATH + bendiName+"/"+serverName + "/" + sceneName + ".xml";
		try {
			retStr = FileReaderUtil.readSceneFileText(FileName);
		} catch (Exception e) {
			retStr = "场景配置报文不存在或读取文件错误！";
		}

		return retStr;
	}
}
