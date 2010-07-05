package com.asm.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asm.dao.UserDao;
import com.asm.dao.impl.UserDaoImp;
import com.asm.dao.impl.UserDaoImp2;

public class AopSpringTest {
	@Test
	public void base() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		// UserDaoImp udi=new UserDaoImp();
		// udi.save();
		System.out.println("--------------------------------------");
		// UserDaoImp udii=(UserDaoImp) ctx.getBean("userDaoImp");
		UserDao ud = (UserDao) ctx.getBean("userDaoImp");
		System.out.println(ud.getClass().getName());
	}

	@Test
	public void base2() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		UserDaoImp2 udi2 = (UserDaoImp2) ctx.getBean("userDaoImp2");
		System.out.println(udi2.getClass().getName());
		System.out.println(udi2.getClass().getSuperclass().getName());
		udi2.save();
	}

	@Test
	public void advieeTest() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		UserDao ud=(UserDao) ctx.getBean("userDaoImp");
		ud.save();
		//ud.add("xxx");
	}
}
