package com.asm.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asm.dao.UserDao;

public class SpringEnvTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testEnv() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
	}

	@Test
	public void base() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		UserDao userDao = (UserDao) ctx.getBean("userDaoImpl");
		userDao.save();
	}

	@Test
	public void base2() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		UserDao userDao = (UserDao) ctx.getBean("userDaoImpl2");
		userDao.save();
	}

	@Test
	public void base3() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		UserDao userDao = (UserDao) ctx.getBean("userDaoImpl3");
		userDao.save();
	}

	@Test
	public void scopeTest() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		UserDao userDao = (UserDao) ctx.getBean("userDaoImpl");
		UserDao userDao2 = (UserDao) ctx.getBean("userDaoImpl");
		System.out.println("是否为同一个对象：" + (userDao == userDao2));
	}

	@Test
	public void lifecycle() {
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("beans.xml");
		ctx.getBean("userDaoImpl");
		ctx.close();
	}
	
}
