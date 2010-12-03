package org.leixu.aop;

import org.junit.BeforeClass;
import org.junit.Test;

public class AOPTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void proxyTest() {
		JDKProxyFactory factory = new JDKProxyFactory();
		PersonService service = (PersonService) factory.createProxyIntance(new PersonServiceBean("xxx"));
		PersonService service2 = (PersonService) factory.createProxyIntance(new PersonServiceBean(null));
		/*ע�⣺��������������Ӧ�ò��ýӿ���������,����return Proxy.newProxyInstance(this.targetObject
		 .getClass().getClassLoader(),this.targetObject.getClass().getInterfaces(), this);
		 ��������ֵĽӿ�����������������Ϊ���������ʵ��������ӿڵ�
		*/
		service.save("888");
		service2.save("888");
		//�������������invoke�ͻᱻ����
	}

}
