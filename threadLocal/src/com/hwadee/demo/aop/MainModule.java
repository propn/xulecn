package com.hwadee.demo.aop;

public class MainModule {

	public static void main(String[] args) {
		DAO dao1 = (DAO) BeanFactory.getBean("dao1");
		// 调用dao1，doa1的doWork方法内又调用了dao2
		dao1.doWork();		
	}
}
