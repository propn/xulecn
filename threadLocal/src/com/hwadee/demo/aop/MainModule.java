package com.hwadee.demo.aop;

public class MainModule {

	public static void main(String[] args) {
		DAO dao1 = (DAO) BeanFactory.getBean("dao1");
		// ����dao1��doa1��doWork�������ֵ�����dao2
		dao1.doWork();		
	}
}
