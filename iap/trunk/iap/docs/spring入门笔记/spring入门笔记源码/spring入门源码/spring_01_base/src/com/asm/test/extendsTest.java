package com.asm.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asm.vo.User;

public class extendsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		User user = (User) new ClassPathXmlApplicationContext("beans.xml").getBean("XXX");
		System.out.println(user.getUsername() + "----------" + user.getPassword());
	}
}
