package com.asm.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asm.dao.UserDao;
import com.asm.dao.impl.UserDaoImp;
import com.asm.dao.impl.UserDaoImp2;

public class AopSpringXMLTest {
	
	@Test
	public void advieeTest() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beansXML.xml");
		UserDao ud=(UserDao) ctx.getBean("userDaoImp");
		ud.add("xxx");
		//ud.save();
	}
}
