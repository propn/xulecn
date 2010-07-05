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
		// cglib�����Ĵ��������ʵ���Ǽ̳���Ҫ�����Ŀ���࣬Ȼ���Ŀ���������з�final�������и��ǣ����ڸ��Ƿ���ʱ�����һЩ���ش��롣
		enhancer.setCallback(this); //ע��ص���
		return enhancer.create();

	}

	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy)
			throws Throwable {
		UserDaoImp2 udi = (UserDaoImp2) target;
		Object result = null;
		if (udi.getUsername() != null) {
			// ǰ��֪ͨ
			try {
				result = methodProxy.invoke(target, args);
				// ����֪ͨ
			} catch (Exception e) {
				e.printStackTrace();
				// ����֪ͨ
			} finally {
				// ����֪ͨ
			}
		}
		return result;
	}
}
