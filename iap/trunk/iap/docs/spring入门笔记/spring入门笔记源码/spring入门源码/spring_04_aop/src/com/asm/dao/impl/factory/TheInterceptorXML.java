package com.asm.dao.impl.factory;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class TheInterceptorXML {
	public void before() {
		System.out.println("前置通知");
	}

	public void afterReturning() {
		System.out.println("后置通知");
	}

	public void beforeAdd(JoinPoint jp) {
		System.out.println("前置通知，获取类名：" + jp.getTarget().getClass().getName());
		System.out.println("前置通知，获取方法名：" + jp.getSignature().getName());
		System.out.print("前置通知，获取参数：");
		for (Object obj : jp.getArgs()) {
			System.out.print(obj + "\t");
		}
	}

	public void afterReturningRes(String result) {
		System.out.println("后置通知,返回结果:" + result);
	}

	public void after() {
		System.out.println("最终通知");
	}

	public void AfterThrowing() {
		System.out.println("例外通知");
	}

	public void catchException(Exception e) {
		System.out.println("获取抛出的异常：" + e);
	}

	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("进入环绕");
		// if(){ // 进行一些判断，再执行环绕
		Object result = pjp.proceed();
		// }
		System.out.println("退出环绕");
		return result;
	}
}
