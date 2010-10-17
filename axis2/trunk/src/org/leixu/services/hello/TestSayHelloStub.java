package org.leixu.services.hello;

import junit.framework.TestCase;

import org.leixu.services.hello.client.SayHelloStub;
import org.leixu.services.hello.service.GetPerson;
import org.leixu.services.hello.service.Say;

public class TestSayHelloStub extends TestCase {
	public void testSay() throws Exception {
		// 初始化桩文件
		SayHelloStub stub = new SayHelloStub("http://localhost:8080/axis2/services/SayHello");
		// 初始化SimpleMethod方法。
		Say say = new Say();
		// 调用simpleMethod的setName方法。
		say.setName("zt");
		System.out.println(stub.say(say).get_return());
	}

	public void testGetPerson() throws Exception {
		// 初始化桩文件
		SayHelloStub stub = new SayHelloStub("http://localhost:8080/axis2/services/SayHello");
		// 初始化SimpleMethod方法。
		GetPerson getPerson = new GetPerson();
		// 调用simpleMethod的setName方法。
		getPerson.setName("zt");
		getPerson.setAge(15);
		System.out.println(stub.getPerson(getPerson).get_return().getName());
		System.out.println(stub.getPerson(getPerson).get_return().getAge());
	}

}
