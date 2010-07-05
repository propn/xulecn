package com.asm.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asm.service.UserServiceBean;

public class AutoScanTest {
	@Test
	public void base() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		UserServiceBean udb = (UserServiceBean) ctx.getBean("usb");
		udb.test();
	}
}
