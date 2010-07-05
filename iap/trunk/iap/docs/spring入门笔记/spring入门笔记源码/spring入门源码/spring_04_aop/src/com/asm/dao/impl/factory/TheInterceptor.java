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
	// 声明一个切入点(第一个*后要留一个空格)
	private void anyMethod() {
	}

	//@Before("anyMethod()")
	// 前置通知
	public void before() {
		System.out.println("前置通知");
	}

	//@AfterReturning("anyMethod()")
	// 后置通知
	public void afterReturning() {
		System.out.println("后置通知");
	}

	@Before("anyMethod() && args(name)")
	// 前置通知,只针对UseDaoImp的add方法
	public void beforeAdd(String name) {
		System.out.println("前置通知:" + name);
	}

	@AfterReturning(pointcut = "anyMethod()", returning = "result")
	// 后置通知,监听返回结果，针对UserDaoImp的getUsername()方法
	public void afterReturningRes(String result) {
		System.out.println("后置通知,返回结果:" + result);
	}
	
	
	//@After("anyMethod()")
	// 最终通知
	public void after() {
		System.out.println("最终通知");
	}

	//@AfterThrowing("anyMethod()")
	// 例外通知
	public void AfterThrowing() {
		System.out.println("例外通知");
	}

	@AfterThrowing(pointcut="anyMethod()",throwing="e")
	public void catchException(Exception e){
		System.out.println("获取抛出的异常："+e);  
	}
	
	@Around("anyMethod()") //环绕通知
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("进入环绕");

		//if(){	// 进行一些判断，再执行环绕
		Object result = pjp.proceed();
		//}
		System.out.println("退出环绕");
		return result;
	}
}
