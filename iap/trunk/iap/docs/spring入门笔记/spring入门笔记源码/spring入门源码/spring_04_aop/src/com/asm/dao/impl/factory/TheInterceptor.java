package com.asm.dao.impl.factory;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TheInterceptor {
	// @Pointcut("execution (* com.asm.dao.impl.UserDaoImp.*(..))")
	@Pointcut("execution (* com.asm.dao.impl.*.*(..))")
	// ����һ�������(��һ��*��Ҫ��һ���ո�)
	private void anyMethod() {
	}

	//@Before("anyMethod()")
	// ǰ��֪ͨ
	public void before() {
		System.out.println("ǰ��֪ͨ");
	}

	//@AfterReturning("anyMethod()")
	// ����֪ͨ
	public void afterReturning() {
		System.out.println("����֪ͨ");
	}

	@Before("anyMethod() && args(name)")
	// ǰ��֪ͨ,ֻ���UseDaoImp��add����
	public void beforeAdd(String name) {
		System.out.println("ǰ��֪ͨ:" + name);
	}

	@AfterReturning(pointcut = "anyMethod()", returning = "result")
	// ����֪ͨ,�������ؽ�������UserDaoImp��getUsername()����
	public void afterReturningRes(String result) {
		System.out.println("����֪ͨ,���ؽ��:" + result);
	}
	
	
	//@After("anyMethod()")
	// ����֪ͨ
	public void after() {
		System.out.println("����֪ͨ");
	}

	//@AfterThrowing("anyMethod()")
	// ����֪ͨ
	public void AfterThrowing() {
		System.out.println("����֪ͨ");
	}

	@AfterThrowing(pointcut="anyMethod()",throwing="e")
	public void catchException(Exception e){
		System.out.println("��ȡ�׳����쳣��"+e);  
	}
	
	@Around("anyMethod()") //����֪ͨ
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("���뻷��");

		//if(){	// ����һЩ�жϣ���ִ�л���
		Object result = pjp.proceed();
		//}
		System.out.println("�˳�����");
		return result;
	}
}
