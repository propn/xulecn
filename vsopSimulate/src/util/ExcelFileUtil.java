package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelFileUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Ð´ÈëEXCELÎÄ¼þ
	 * @param fileName
	 * @param sheetName
	 * @param list
	 */
	public static void writerExcel(String fileName,String sheetName,List list){
		File file = new File(fileName);
		FileInputStream in = null;


		WritableWorkbook workbook=null;
		
		try {
			if(file.exists()){
				in=new FileInputStream(file);
				Workbook wb =Workbook.getWorkbook(in);
				workbook = Workbook.createWorkbook(file,wb);
			}else{
			
				workbook = Workbook.createWorkbook(file);
			}
			WritableSheet sheet = workbook.createSheet(sheetName, workbook.getNumberOfSheets()); 
			for(int i=0;i<list.size();i++){
				String[] results= (String[]) list.get(i);
				for(int j=0;j<results.length;j++){
					String str = results[j];
					Label lable = new Label(j, i, str); 
					sheet.addCell(lable);
				}
			}
			workbook.write();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		
			try {
				
				if(workbook!=null){
					workbook.close();
				}
				if(in!=null){
					in.close();
				}
				
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

}
