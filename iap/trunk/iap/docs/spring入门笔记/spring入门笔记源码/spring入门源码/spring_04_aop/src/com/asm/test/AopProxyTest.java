package com.asm.test;

import org.junit.Test;

import com.asm.dao.UserDao;
import com.asm.dao.impl.UserDaoImp;
import com.asm.dao.impl.factory.ProxyFactory;

public class AopProxyTest {
	@Test
	// �û���Ϊ�գ���ִ�з���
	public void testProxy() {
		ProxyFactory pf = new ProxyFactory();
		UserDao ud = (UserDao) pf.createUserDaoImp(new UserDaoImp());
		ud.save();
	}

	@Test
	// �û���Ϊ��Ϊ�գ���ִ��save����
	public void testProxy2() {
		ProxyFactory pf = new ProxyFactory();
		UserDao ud = (UserDao) pf.createUserDaoImp(new UserDaoImp("��ĳĳ"));
		ud.save();
		System.out.println(ud.getClass().getName() + "-----" +ud.getClass().getSuperclass().getName());
		//UserDaoImp udi = (UserDaoImp) pf.createUserDaoImp(new UserDaoImp("XXX")); //ִ�д˾�����쳣
	}
}
