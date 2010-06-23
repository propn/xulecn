package com.hwadee.demo.aop;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * ģ��Spring BeanFactory ��򻯵�ʵ��
 * 
 * Ĭ�ϻᴴ������Bean dao1 dao2
 * 
 * ���Ƕ��Ǿ����˴������Ķ���
 */
public class BeanFactory {

	// Bean����
	private final static Map<String, Object> beanContainer = new HashMap<String, Object>();

	// ��ʼ�����������������
	static {
		DAO dao1 = new DAOImpl1();
		Object dao1Proxy = createTransactionProxy(dao1);
		beanContainer.put("dao1", dao1Proxy);

		DAO dao2 = new DAOImpl2();
		Object dao2Proxy = createTransactionProxy(dao2);
		beanContainer.put("dao2", dao2Proxy);
	}

	// �����������
	private static Object createTransactionProxy(Object target) {
		// ʹ�� Proxy.newProxyInstance ��������һ���������
		Object proxy = Proxy.newProxyInstance(target.getClass()
				.getClassLoader(), target.getClass().getInterfaces(),
				new RequiredTransactionInterceptor(target));
		return proxy;
	}

	// ��ȡBean
	public static Object getBean(String id) {
		return beanContainer.get(id);
	}
}
