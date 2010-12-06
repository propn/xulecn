package message;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import util.Config;
import util.StringUtil;
import database.SDBManager;

/**
 * <pre>
 * Title:SOAP客户端
 * Description: SOAP客户端 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class Send {

	/**
	 * @param args
	 * @param arry
	 */
	private static Logger rootLogger = Logger.getLogger(Send.class);

	public static String send(String sendStr, String sendURL, String serverCNName, String sceneName) throws Exception {
		PropertyConfigurator.configure("config/log.properties");
		SOAPClient client = new SOAPClient();
		Map data = new HashMap();
		String responseXML = "";
		try {
			//System.out.println("\n请求报文信息：" + StringUtil.formatXML(sendStr, null+ "\n"));
			byte[] temp = client.send(sendStr, sendURL);
			//String aa = CallWebService.send(sendURL, ", sendStr);
			responseXML = StringUtil.formatXML(temp, "GBK");
			
			System.out.println("返回：\n" + responseXML);
		} catch (Exception e) {
			e.printStackTrace();
			responseXML = "请求错误！\n原因： 通讯地址错误或请求报文不正确！";
			return responseXML;

		}

		return formatRetXML(responseXML, serverCNName, sceneName);
	}
	
	public static String send(String sendStr, String sendURL) throws Exception {
		PropertyConfigurator.configure("config/log.properties");
		SOAPClient client = new SOAPClient();
		Map data = new HashMap();
		String responseXML = "";
		try {
			//System.out.println("\n请求报文信息：" + StringUtil.formatXML(sendStr, null+ "\n"));
			byte[] temp = client.send(sendStr, sendURL);
			//String aa = CallWebService.send(sendURL, ", sendStr);
			responseXML = StringUtil.formatXML(temp, "GBK");
			
			System.out.println("返回：\n" + responseXML);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
		String strXML = StringUtil.unEscapeHTMLTag(responseXML);
		return strXML;
		
	}

	public static String formatRetXML(String responseXML, String serverCNName, String sceneName) throws Exception {
		//还原标签
		String strXML = StringUtil.unEscapeHTMLTag(responseXML);
		StringBuffer buffer = new StringBuffer();
		buffer.append("返回结果：");
		String resultCode = getResultFiled(strXML, "<ResultCode>", "</ResultCode>");
		if ("".equals(resultCode) || !"0".equals(resultCode)) {
			buffer.append("失败");
		}
		if ("0".equals(resultCode)) {
			buffer.append("成功");
		}
		buffer.append("\n结果描述：");
		String resultDesc = getResultFiled(strXML, "<ResultDesc>", "</ResultDesc>");
		if ("".equals(resultCode)) {
			buffer.append("无");
		} else {
			buffer.append(resultDesc);
		}

		buffer.append("\n返回报文：\n");
		String str = StringUtil.unEscapeHTMLTag(responseXML);
		buffer.append(StringUtil.formatXML(str, "GBK"));

		//updateDB(resultCode, resultDesc, serverCNName, sceneName);
		return buffer.toString();

	}

	public static String getResultFiled(String requstStr, String start, String end) {
		int Index = requstStr.indexOf(start);
		String str = "";
		//如果不存在 则直接返回
		if (Index < 0) {
			return str;
		}
		int startIndex = Index + start.length();
		int endIndex = requstStr.lastIndexOf(end);
		if (endIndex < 0) {
			return str;
		}

		return requstStr.substring(startIndex, endIndex);

	}

	public static void updateDB(String resultCode, String resultDesc, String serverCNName, String sceneName) {
		String str = resultDesc;
		String resultId = serverCNName + "_" + sceneName;
		try {
			str = new String(resultDesc.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String delsql = "delete simulate_result where testid='" + resultId + "'";
		String sql = "insert into  simulate_result(testid,resultCode,resultDesc)  values('" + resultId + "','"
				+ resultCode + "','" + resultDesc + "')";

		//为‘1’则写数据库
		if ("1".equals(Config.getConfig("WRITE_DB"))) {
			SDBManager sd = new SDBManager();
			try {
				sd.excuSQL(delsql);
				sd.excuSQL(sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
