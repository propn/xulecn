package thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import message.Send;
import util.Config;
import util.ExcelFileUtil;
import util.SceneFileUtil;
import util.TestPlanConfigUtil;
import util.XmlUtil;
import view.excuListView;

//循环发送交易线程
public class TestPlanThread extends Thread {
	private excuListView  excuView = null;
	private String bendiName =null;
	private String testPlanName = null;
	public TestPlanThread(){
	}
	public TestPlanThread(excuListView excuView,String bendiName,String testPlanName){
		super();
		this.excuView = excuView;
		this.bendiName = bendiName;
		this.testPlanName = testPlanName;
		
	}
	public void run() { 
		List writerList = new ArrayList();
		List list = null;
		try {
			list = TestPlanConfigUtil.getSendList(testPlanName);
		} catch (Exception e) {
			excuView.excuFail();
			e.printStackTrace();
			return;
		}
		List sendList = new ArrayList();
		List responseList = new ArrayList();
			for(int i=0;i<list.size();i++){
				excuView.setState(i, 4, "正在执行");
				String[] s = new String[8];
				String[] sends = (String[]) list.get(i);
				String bdName = sends[0];
				String serverCNName = sends[1];
				String serverName = Config.getTemplateConfig(serverCNName);
				String sceneName = sends[2];
				s[0]=String.valueOf(i);
				s[1]=sends[0];
				s[2]=sends[1];
				s[3]=sends[2];
				
				
				String requestXml = SceneFileUtil.getSceneFileStr(bdName,serverName, sceneName);
				String str = "";
				String sendURL = "";
				String sendType = Config.getSendType(serverCNName) == null ? "services" : Config.getSendType(serverCNName);

				str = Config.getConfig("URL") + sendType + "/";
				sendURL = str+serverName;
				
				String responseXML ="";
				try {
					responseXML = Send.send(requestXml, sendURL);
				 
					
					String resultCode = Send.getResultFiled(responseXML, "<ResultCode>", "</ResultCode>");
					String resultDesc = Send.getResultFiled(responseXML, "<ResultDesc>", "</ResultDesc>");
					if(responseXML.equals(resultCode)||"".equals(resultCode)){
						resultCode = "失败";
					}
					if(responseXML.equals(resultDesc)||"".equals(resultDesc)){
						resultDesc = "发送失败";
					}
					s[4]=resultCode;
					s[5]=resultDesc;
					s[6]=requestXml;
					s[7]=responseXML;
					writerList.add(s);
					sendList.add(requestXml);
					responseList.add(XmlUtil.formatXML(responseXML.getBytes(),"GBK"));
					excuView.setState(i, 4, "完毕");
					
					excuView.setState(i, 5, resultCode);
					excuView.setState(i, 6, resultDesc);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					s[4]="失败";
					s[5]="发送异常";
					s[6]=requestXml;
					s[7]="";
					writerList.add(s);
					sendList.add(requestXml);
					responseList.add(responseXML);
					excuView.setState(i, 4, "完毕");
					excuView.setState(i, 5, "失败");
					excuView.setState(i, 6, "发送异常");
					e.printStackTrace();
				}
				
				
			}
			
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateStr = format.format(date);
			String sheetName = bendiName+testPlanName+"_"+dateStr;
			String filePath = Config.getConfig("ResultFilePath");
			ExcelFileUtil.writerExcel(filePath, sheetName, writerList);
			excuView.exceEnd(sendList,responseList);
	}
}
