package com.asm.test;

import org.junit.Test;

import com.asm.dao.impl.UserDaoImp2;
import com.asm.dao.impl.factory.CglibFactory;


public class AopProxyTestCglib {
	@Test	//用户名为空，不执行方法
	public void testProxy(){
		CglibFactory cf=new CglibFactory();
		UserDaoImp2 ud=(UserDaoImp2) cf.createUserDaoImp2(new UserDaoImp2());
		ud.save();
	} 
	@Test	//用户名为不为空，不执行方法
	public void testProxy2(){
		CglibFactory cf=new CglibFactory();
		UserDaoImp2 ud=(UserDaoImp2) cf.createUserDaoImp2(new UserDaoImp2("张某某"));
		ud.save();
	}
}
