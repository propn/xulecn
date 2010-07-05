package com.asm.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asm.dao.UserDao;

public class SpringEnv {
	public static void main(String[] args) {
		// testEnv();//测试环境是否搭建成功
		// base();
		// base2();
		// base3();
		lifecycle();
	}

	public static void testEnv() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
	}

	public static void base() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		UserDao userDao = (UserDao) ctx.getBean("userDaoImpl");
		userDao.save();
	}

	public static void base2() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		UserDao userDao = (UserDao) ctx.getBean("userDaoImpl2");
		userDao.save();
	}

	public static void base3() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		UserDao userDao = (UserDao) ctx.getBean("userDaoImpl3");
		userDao.save();
	}

	public static void lifecycle() {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
//		UserDao userDao = (UserDao) ctx.getBean("userDaoImpl");
//		userDao.save();
		ctx.close();
	}
}
