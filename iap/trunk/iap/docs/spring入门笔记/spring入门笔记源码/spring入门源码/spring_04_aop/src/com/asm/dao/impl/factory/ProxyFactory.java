package com.asm.dao.impl.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.asm.dao.impl.UserDaoImp;

public class ProxyFactory implements InvocationHandler {
	private Object target;

	public Object createUserDaoImp(Object target) {
		this.target = target;
		return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target
				.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		UserDaoImp udi = (UserDaoImp) target;
		Object result = null;
		if (udi.getUsername() != null) {
			result = method.invoke(target, args);
		}
		return result;
	}
}
