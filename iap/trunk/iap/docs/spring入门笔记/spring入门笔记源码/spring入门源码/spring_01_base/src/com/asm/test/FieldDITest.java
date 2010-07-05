package com.asm.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asm.service.UserServiceBean;


public class FieldDITest  {
	@Test
	public void base(){
		ApplicationContext ctx=new ClassPathXmlApplicationContext("beans.xml");
		UserServiceBean udb= (UserServiceBean) ctx.getBean("userServiceBean");
		udb.testSetterDI();
	}
	
	@Test
	public void base2(){
		ApplicationContext ctx=new ClassPathXmlApplicationContext("beans.xml");
		UserServiceBean udb= (UserServiceBean) ctx.getBean("userServiceBean2");
		udb.testConstructorDI();
	}
}	
