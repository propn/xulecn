package aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxyFactory implements InvocationHandler {
	private Object targetObject;

	// ����ΪĿ����󴴽��õĴ������
	public Object createProxyIntance(Object targetObject) {
		this.targetObject = targetObject;
		// �ڶ����������������Ҫʵ�ֵĽӿ�����Щ��
		// ������������InvocationHandler��handler��C++�Ǿ������˼�����⣬һ�㿴�������׺ʵ��������һ���ص�
		// InvocationHandler�������һ���ӿڣ�������Ļص���������˵���ص�������ʱ���أ����Դ����ĸ�����������ط���
		// ����дthis���ʹ���JDKProxyFactory�����ʵ�������������Ҫ����������ʵ��InvocationHandler�ӿ�
		// �ӿ������и�invoke����
		return Proxy.newProxyInstance(this.targetObject.getClass().getClassLoader(), this.targetObject.getClass()
				.getInterfaces(), this);
	}

	// ����1���������
	// ����2�������ص��ķ���
	// ����3���������������
	// �����ǶԴ�������ҵ�񷽷����е��õ�ʱ��������ò����ᱻ�����this���ص������ص����ִ��invoke����
	// Ҳ����˵���ǿͻ��˵��ô������ķ�����ʱ�򣬻����this����InvocationHandler����ӿھ�����invoke����
	// ��invoke�������棬�������Ҫ����Ŀ�����Ļ������Ǿͱ���Ѵ�����󷽷��ĵ���ί�ɸ�Ŀ�����
	// �����Ҫ�ѷ����ĵ���ί�ɸ�method
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		PersonServiceBean bean = (PersonServiceBean) this.targetObject;
		Object result = null;
		if (bean.getUser() != null) {
			result = method.invoke(targetObject, args);
		}
		return result;
	}
	/*
	 �������ʵ����ΪĿ���ഴ���������Ĵ��룬����ִ�й����������ģ����ͻ��˵��ô�������ҵ�񷽷���ʱ����ô
	 �������ͻ�ִ��invoke���������ⷽ�����棬�ٰѷ����ĵ���ί�ɸ�Ŀ�����Ҳ���൱�ڵ���Ŀ������method������
	 ��ȻҲҪ�ѷ������������args�����ȥ
	 */

}
