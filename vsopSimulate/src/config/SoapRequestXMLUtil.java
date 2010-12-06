package config;

/**
 * <pre>
 * Title:订购报文生成
 * Description: SP/CP产品同步报文生成
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class SoapRequestXMLUtil {
	/**
	 * soap报文头
	 * @param reqFlag
	 * @return
	 */
	public static String getStart(String reqFlag) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\n");
		buffer
				.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n");
		buffer.append("<SOAP-ENV:Body>\n");
		buffer.append("<m:");
		buffer.append(reqFlag);
		buffer.append(" xmlns:m=\"http://www.mbossvsop.com.cn/vsop\">\n");
		buffer.append(" <request><![CDATA[\n");
		return buffer.toString();

	}

	/**
	 * soap报文尾
	 * @param reqFlag
	 * @return
	 */
	public static String getEnd(String reqFlag) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("]]></request>\n");
		buffer.append("</m:" + reqFlag + ">\n");
		buffer.append("</SOAP-ENV:Body>\n");
		buffer.append("</SOAP-ENV:Envelope>");
		return buffer.toString();
	}
}
