/**
 * 
 */
package org.leixu.iwap.aop.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author lei
 * 
 */
public class AuthProxy implements MethodInterceptor {
	private String name; // ��Ա��¼��

	public AuthProxy(String name) {
		this.name = name;
	}

	/**
	 * Ȩ��У�飬�����Ա��Ϊ:maurice������Ȩ����������������ʾû��Ȩ��
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		
		//before invoke
		if (!"maurice".equals(this.name)) {
			System.out
					.println(" AuthProxy:you have no permits to do manager! ");
			return null;
		}
		
		//invoke
		 obj=proxy.invokeSuper(obj, args);
		
		 //after invoke
		 return obj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
