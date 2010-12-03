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
 * Title:�������������
 * Description: �����ܵ����� 
 * </pre>
 * @author xulei  xu.lei55@zet.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class QuickStartClient {

	//����axis2��HelloWorld�����add���� 
	public int add(int x, int y) {
		int sum = 0;
		//webService�ĵ��õ�ַ 
		String srvcUrl = "http://localhost:8080/axis2/services/HelloWorld";
		//�����������ռ�+������+������ 
		QName qname = new QName("http://ws.apache.org/axis2", "add");
		//���ݵĲ������� 
		Object param[] = new Object[] { x, y };
		try {
			//ʵ����Զ�̷�����ÿͻ��˶��� 
			RPCServiceClient client = new RPCServiceClient();
			//ʵ����Options���� 
			Options options = new Options();
			//����Options����������ն˵�ַ 
			options.setTo(new EndpointReference(srvcUrl));
			//����Options����Ĳ����¼����� 
			options.setAction("urn:add");
			//ΪԶ�̷�����ÿͻ��˶�������Options�Ӷ��� 
			client.setOptions(options);
			//���ݲ��������÷��񣬻�ȡ���񷵻ؽ���� 
			OMElement element = client.invokeBlocking(qname, param);
			//��ȡ���ؽ�����е�һ������ķ������� 
			String result = element.getFirstElement().getText();
			//�ַ���ת��Ϊ���� 
			sum = Integer.parseInt(result);
		}
		//��׽�쳣 
		catch (AxisFault e) {
			e.printStackTrace();
		}
		//�������� 
		return sum;
	}

	//����XFire�µ�HelloWordService�����sayHello�������������ͬadd()�� 
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

	//���� 
	public static void main(String[] args) {

		QuickStartClient client = new QuickStartClient();
		int num = client.add(12, 35);
		String hello = client.sayHello("feeler!");
		System.out.println(num);
		System.out.println(hello);
	}
}
