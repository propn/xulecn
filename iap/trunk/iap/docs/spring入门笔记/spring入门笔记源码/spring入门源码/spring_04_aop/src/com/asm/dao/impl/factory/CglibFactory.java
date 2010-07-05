package com.asm.dao.impl.factory;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.asm.dao.impl.UserDaoImp2;

public class CglibFactory implements MethodInterceptor {
	private Object target;

	public Object createUserDaoImp2(Object target) {
		this.target = target;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		// cglib创建的代理对象，其实就是继承了要代理的目标类，然后对目标类中所有非final方法进行覆盖，但在覆盖方法时会添加一些拦截代码。
		enhancer.setCallback(this); //注册回调器
		return enhancer.create();

	}

	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy)
			throws Throwable {
		UserDaoImp2 udi = (UserDaoImp2) target;
		Object result = null;
		if (udi.getUsername() != null) {
			// 前置通知
			try {
				result = methodProxy.invoke(target, args);
				// 后置通知
			} catch (Exception e) {
				e.printStackTrace();
				// 例外通知
			} finally {
				// 最终通知
			}
		}
		return result;
	}
}
