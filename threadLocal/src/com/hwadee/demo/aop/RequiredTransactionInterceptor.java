package com.hwadee.demo.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hwadee.demo.TransactionHelper;

/**
 * 事务拦截器 代理对象执行接口的任意方法都会被拦截 对方法的调用交由本类的 invoke 方法处理
 */
public class RequiredTransactionInterceptor implements InvocationHandler {

	// 目标对象
	private Object target;

	// 在构造方法中传入目标对象
	public RequiredTransactionInterceptor(Object target) {
		this.target = target;
	}

	/**
	 * 在代理对象调用接口方法时的请求会被此方法拦截
	 * 
	 * @param proxy
	 *            代理对象
	 * @param method
	 *            目标对象当前调用的方法
	 * @param args
	 *            调用此方法时传递的参数
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		// 在目标方法被调用前织入的逻辑,此处以Required传播属性为例
		// 判断当前的事务环境，是开始一个新事务还是加入已有的事务

		boolean existsTransaction = TransactionHelper.existsTransaction();

		if (existsTransaction == false) {
			TransactionHelper.beginTransaction();
			System.out.println("当前事务环境还没有事务，开启一个新事务");
		} else {
			System.out.println("当前事务环境已存在事务，加入事务");
		}

		// 目标方法的返回值
		Object result = null;

		// 此处才真正调用目标对象的方法
		try {
			result = method.invoke(target, args);
		} catch (InvocationTargetException e) {
			// 捕获调用目标异常，如果目标异常是运行时异常则设置回滚标志
			Throwable cause = e.getCause();
			if (cause instanceof RuntimeException) {
				TransactionHelper.setRollbackOnly(true);
				System.out.println("出现运行时异常，事务环境被设置为必须回滚");
			} else {
				System.out.println("出现非运行时异常，忽略");
			}
		}

		// 在目标方法被调用后织入的逻辑
		System.out.println("判断当前的事务环境，是应该提交事务还是回滚事务");
		if (existsTransaction == false
				&& TransactionHelper.isRollbackOnly() == false) {
			TransactionHelper.commit();
			System.out.println("事务已提交");
		} else if (existsTransaction == false
				&& TransactionHelper.isRollbackOnly() == true) {
			TransactionHelper.rollback();
			System.out.println("事务已回滚");
		} else if (existsTransaction == true) {
			System.out.println("子事务忽略事务提交或回滚");
		}

		System.out.println("=============================");

		return result;
	}
}
