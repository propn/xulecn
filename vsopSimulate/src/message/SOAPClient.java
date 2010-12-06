package message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.StringUtil;

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
public class SOAPClient {

	public static void main(String[] args) {
		try {
			new SOAPClient().send("", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Logger rootLogger = Logger.getLogger(SOAPClient.class);

	/**
	 * 
	 * 注释: 发送消息并返回数据 返回值: byte[]
	 * 
	 * @param
	 * @throws SOAPException
	 * @throws
	 */
	public byte[] send(String sendMsg, String sendURL) throws Exception {
		SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
		SOAPMessage message = MessageFactory.newInstance().createMessage();
		SOAPMessage reply = null;
		try {

			Object http_url = sendURL;//"http://127.0.0.1:8181/ccgwboard/kernelBusinessWS";
			rootLogger.debug("\n 发送长度：" + sendMsg.length() + "\n");
			rootLogger.debug("\n 请求报文信息:\n" + sendMsg + "\n");
			//SoapAction,""

			// 发送报文信息
			message.getSOAPPart().setContent(
					new StreamSource(new ByteArrayInputStream(StringUtil.formatXML(sendMsg, "GBK").getBytes())));
			message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "GBK");
			System.out.println("1:\n");
			message.writeTo(System.out);
			message.saveChanges();

			message.writeTo(System.out);

			reply = connection.call(message, http_url);

			reply.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "GBK");

			reply.writeTo(System.out);
			//			procReply(reply);
			rootLogger.debug("\n接收SOAP协议的地址是：" + http_url + "\n");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			TransformerFactory.newInstance().newTransformer().transform(reply.getSOAPPart().getContent(),
					new StreamResult(baos));
			// 获取交易返回报文
			return baos.toByteArray();
		} catch (Exception ex) {
			rootLogger.error("使用SOAP协议发送报文出错！", ex);
			throw new Exception("使用SOAP协议发送报文出错！", ex);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	private static void procReply(SOAPMessage reply) {
		SOAPEnvelope se;
		try {
			SOAPPart sp = reply.getSOAPPart();
			se = sp.getEnvelope();
			SOAPBody body = se.getBody();
			SOAPHeader head = se.getHeader();
			SOAPElement element = (SOAPElement) body.getChildElements().next();
			NodeList bodyList = element.getChildNodes();

			for (int i = 0; i < bodyList.getLength(); i++) {
				Node child = bodyList.item(i);
				//				String nodeName = child.getTextContent();
				//				System.out.println(nodeName);
				Node node = child.getAttributes().getNamedItem("name");
				if (node == null) {
					continue;
				}
				String name = node.getNodeValue();
				if (name == null || "".equals(name)) {
					continue;
				}
				String value = "";
				if (child.getChildNodes() != null && child.getChildNodes().item(0) != null
						&& child.getChildNodes().item(0).getNodeValue() != null) {
					value = child.getChildNodes().item(0).getNodeValue();
				}
				System.out.print(name + "=" + value);
				//				System.out.print(child.getChildNodes().item(0) + "|" + child.getNodeValue());
				System.out.println();
			}

		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private synchronized boolean isConnect(String url) {
		HttpURLConnection connection = null;
		URL urlStr = null;
		int state = -1;

		int counts = 0;
		boolean succ = false;
		if (url == null || url.length() <= 0) {
			return succ;
		}

		try {
			urlStr = new URL(url);
			connection = (HttpURLConnection) urlStr.openConnection();
			state = connection.getResponseCode();
			if (state == 200) {
				succ = true;
			}
		} catch (Exception ex) {

		}

		return succ;
	}

}
