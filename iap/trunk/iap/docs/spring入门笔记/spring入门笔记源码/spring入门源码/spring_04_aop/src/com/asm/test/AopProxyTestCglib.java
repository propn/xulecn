package com.asm.test;

import org.junit.Test;

import com.asm.dao.impl.UserDaoImp2;
import com.asm.dao.impl.factory.CglibFactory;


public class AopProxyTestCglib {
	@Test	//�û���Ϊ�գ���ִ�з���
	public void testProxy(){
		CglibFactory cf=new CglibFactory();
		UserDaoImp2 ud=(UserDaoImp2) cf.createUserDaoImp2(new UserDaoImp2());
		ud.save();
	} 
	@Test	//�û���Ϊ��Ϊ�գ���ִ�з���
	public void testProxy2(){
		CglibFactory cf=new CglibFactory();
		UserDaoImp2 ud=(UserDaoImp2) cf.createUserDaoImp2(new UserDaoImp2("��ĳĳ"));
		ud.save();
	}
}
