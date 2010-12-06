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
 * Title:SOAP�ͻ���
 * Description: SOAP�ͻ��� 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
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
			//System.out.println("\n��������Ϣ��" + StringUtil.formatXML(sendStr, null+ "\n"));
			byte[] temp = client.send(sendStr, sendURL);
			//String aa = CallWebService.send(sendURL, ", sendStr);
			responseXML = StringUtil.formatXML(temp, "GBK");
			
			System.out.println("���أ�\n" + responseXML);
		} catch (Exception e) {
			e.printStackTrace();
			responseXML = "�������\nԭ�� ͨѶ��ַ����������Ĳ���ȷ��";
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
			//System.out.println("\n��������Ϣ��" + StringUtil.formatXML(sendStr, null+ "\n"));
			byte[] temp = client.send(sendStr, sendURL);
			//String aa = CallWebService.send(sendURL, ", sendStr);
			responseXML = StringUtil.formatXML(temp, "GBK");
			
			System.out.println("���أ�\n" + responseXML);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
		String strXML = StringUtil.unEscapeHTMLTag(responseXML);
		return strXML;
		
	}

	public static String formatRetXML(String responseXML, String serverCNName, String sceneName) throws Exception {
		//��ԭ��ǩ
		String strXML = StringUtil.unEscapeHTMLTag(responseXML);
		StringBuffer buffer = new StringBuffer();
		buffer.append("���ؽ����");
		String resultCode = getResultFiled(strXML, "<ResultCode>", "</ResultCode>");
		if ("".equals(resultCode) || !"0".equals(resultCode)) {
			buffer.append("ʧ��");
		}
		if ("0".equals(resultCode)) {
			buffer.append("�ɹ�");
		}
		buffer.append("\n���������");
		String resultDesc = getResultFiled(strXML, "<ResultDesc>", "</ResultDesc>");
		if ("".equals(resultCode)) {
			buffer.append("��");
		} else {
			buffer.append(resultDesc);
		}

		buffer.append("\n���ر��ģ�\n");
		String str = StringUtil.unEscapeHTMLTag(responseXML);
		buffer.append(StringUtil.formatXML(str, "GBK"));

		//updateDB(resultCode, resultDesc, serverCNName, sceneName);
		return buffer.toString();

	}

	public static String getResultFiled(String requstStr, String start, String end) {
		int Index = requstStr.indexOf(start);
		String str = "";
		//��������� ��ֱ�ӷ���
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

		//Ϊ��1����д���ݿ�
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
