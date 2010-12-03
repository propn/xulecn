/**
 * 
 */
package org.leixu.axis2;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author xulei  xu.lei55@zet.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class QuickStartClient {

	//调用axis2下HelloWorld服务的add操作 
	public int add(int x, int y) {
		int sum = 0;
		//webService的调用地址 
		String srvcUrl = "http://localhost:8080/axis2/services/HelloWorld";
		//操作的命名空间+“：”+操作名 
		QName qname = new QName("http://ws.apache.org/axis2", "add");
		//传递的参数对象集 
		Object param[] = new Object[] { x, y };
		try {
			//实例化远程服务调用客户端对象 
			RPCServiceClient client = new RPCServiceClient();
			//实例化Options对象 
			Options options = new Options();
			//设置Options对象的连接终端地址 
			options.setTo(new EndpointReference(srvcUrl));
			//设置Options对象的操作事件对象 
			options.setAction("urn:add");
			//为远程服务调用客户端对象设置Options子对象 
			client.setOptions(options);
			//传递参数，调用服务，获取服务返回结果集 
			OMElement element = client.invokeBlocking(qname, param);
			//获取返回结果集中第一条结果的返回内容 
			String result = element.getFirstElement().getText();
			//字符串转换为整型 
			sum = Integer.parseInt(result);
		}
		//捕捉异常 
		catch (AxisFault e) {
			e.printStackTrace();
		}
		//返回内容 
		return sum;
	}

	//调用XFire下的HelloWordService服务的sayHello操作（具体操作同add()） 
	public String sayHello(String name) {
		String result = null;
		String srvcUrl = "http://localhost:8080/simpleWebService/services/HelloWordService";
		QName qname = new QName("http://ws.apache.org/axis2", "sayHello");
		Object param[] = new Object[] { name };
		try {
			RPCServiceClient client = new RPCServiceClient();
			Options options = new Options();
			options.setTo(new EndpointReference(srvcUrl));
			options.setAction("urn:sayHello");
			client.setOptions(options);
			OMElement element = client.invokeBlocking(qname, param);

			result = element.getFirstElement().getText();

		} catch (AxisFault e) {
			e.printStackTrace();
		}
		return result;
	}

	//测试 
	public static void main(String[] args) {

		QuickStartClient client = new QuickStartClient();
		int num = client.add(12, 35);
		String hello = client.sayHello("feeler!");
		System.out.println(num);
		System.out.println(hello);
	}
}
