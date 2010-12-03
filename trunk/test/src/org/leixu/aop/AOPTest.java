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
		/*注意：这个代理对象，我们应该采用接口来引用它,采用return Proxy.newProxyInstance(this.targetObject
		 .getClass().getClassLoader(),this.targetObject.getClass().getInterfaces(), this);
		 这里面出现的接口引用这个代理对象，因为代理对象是实现了这个接口的
		*/
		service.save("888");
		service2.save("888");
		//调用这个方法，invoke就会被调用
	}

}
