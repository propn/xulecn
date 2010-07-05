package com.asm.test;

import org.junit.Test;

import com.asm.dao.UserDao;
import com.asm.dao.impl.UserDaoImp;
import com.asm.dao.impl.factory.ProxyFactory;

public class AopProxyTest {
	@Test
	// 用户名为空，不执行方法
	public void testProxy() {
		ProxyFactory pf = new ProxyFactory();
		UserDao ud = (UserDao) pf.createUserDaoImp(new UserDaoImp());
		ud.save();
	}

	@Test
	// 用户名为不为空，才执行save方法
	public void testProxy2() {
		ProxyFactory pf = new ProxyFactory();
		UserDao ud = (UserDao) pf.createUserDaoImp(new UserDaoImp("张某某"));
		ud.save();
		System.out.println(ud.getClass().getName() + "-----" +ud.getClass().getSuperclass().getName());
		//UserDaoImp udi = (UserDaoImp) pf.createUserDaoImp(new UserDaoImp("XXX")); //执行此句会有异常
	}
}
