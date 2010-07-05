package com.asm.dao.impl.factory;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class TheInterceptorXML {
	public void before() {
		System.out.println("ǰ��֪ͨ");
	}

	public void afterReturning() {
		System.out.println("����֪ͨ");
	}

	public void beforeAdd(JoinPoint jp) {
		System.out.println("ǰ��֪ͨ����ȡ������" + jp.getTarget().getClass().getName());
		System.out.println("ǰ��֪ͨ����ȡ��������" + jp.getSignature().getName());
		System.out.print("ǰ��֪ͨ����ȡ������");
		for (Object obj : jp.getArgs()) {
			System.out.print(obj + "\t");
		}
	}

	public void afterReturningRes(String result) {
		System.out.println("����֪ͨ,���ؽ��:" + result);
	}

	public void after() {
		System.out.println("����֪ͨ");
	}

	public void AfterThrowing() {
		System.out.println("����֪ͨ");
	}

	public void catchException(Exception e) {
		System.out.println("��ȡ�׳����쳣��" + e);
	}

	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("���뻷��");
		// if(){ // ����һЩ�жϣ���ִ�л���
		Object result = pjp.proceed();
		// }
		System.out.println("�˳�����");
		return result;
	}
}
