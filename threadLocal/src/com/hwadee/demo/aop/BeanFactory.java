package com.hwadee.demo.aop;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 模拟Spring BeanFactory 最简化的实现
 * 
 * 默认会创建两个Bean dao1 dao2
 * 
 * 它们都是经过了代理过后的对象
 */
public class BeanFactory {

	// Bean容器
	private final static Map<String, Object> beanContainer = new HashMap<String, Object>();

	// 初始化创建两个代理对象
	static {
		DAO dao1 = new DAOImpl1();
		Object dao1Proxy = createTransactionProxy(dao1);
		beanContainer.put("dao1", dao1Proxy);

		DAO dao2 = new DAOImpl2();
		Object dao2Proxy = createTransactionProxy(dao2);
		beanContainer.put("dao2", dao2Proxy);
	}

	// 创建代理对象
	private static Object createTransactionProxy(Object target) {
		// 使用 Proxy.newProxyInstance 方法创建一个代理对象
		Object proxy = Proxy.newProxyInstance(target.getClass()
				.getClassLoader(), target.getClass().getInterfaces(),
				new RequiredTransactionInterceptor(target));
		return proxy;
	}

	// 获取Bean
	public static Object getBean(String id) {
		return beanContainer.get(id);
	}
}
