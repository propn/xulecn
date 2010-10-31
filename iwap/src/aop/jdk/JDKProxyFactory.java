package aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxyFactory implements InvocationHandler {
	private Object targetObject;

	// 返回为目标对象创建好的代理对象
	public Object createProxyIntance(Object targetObject) {
		this.targetObject = targetObject;
		// 第二个参数：代理对象要实现的接口是哪些？
		// 第三个参数：InvocationHandler，handler在C++是句柄的意思，在这，一般看到这个后缀实际上它是一个回调
		// InvocationHandler这个类是一个接口，这里面的回调。。就是说拦截到方法的时候呢，可以触发哪个类里面的拦截方法
		// 这里写this，就代表JDKProxyFactory这个类实例本身。。。这就要求这个类必须实现InvocationHandler接口
		// 接口里面有个invoke方法
		return Proxy.newProxyInstance(this.targetObject.getClass().getClassLoader(), this.targetObject.getClass()
				.getInterfaces(), this);
	}

	// 参数1：代理对象
	// 参数2：被拦截到的方法
	// 参数3：方法的输入参数
	// 当我们对代理对象的业务方法进行调用的时候，这个调用操作会被上面的this拦截到，拦截到后会执行invoke方法
	// 也就是说我们客户端调用代理对象的方法的时候，会调用this里面InvocationHandler这个接口决定的invoke方法
	// 在invoke方法里面，如果我们要访问目标对象的话，我们就必须把代理对象方法的调用委派给目标对象，
	// 这里就要把方法的调用委派给method
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		PersonServiceBean bean = (PersonServiceBean) this.targetObject;
		Object result = null;
		if (bean.getUser() != null) {
			result = method.invoke(targetObject, args);
		}
		return result;
	}
	/*
	 在这里就实现了为目标类创建代理对象的代码，它的执行过程是这样的：当客户端调用代理对象的业务方法的时候，那么
	 代理对象就会执行invoke方法，在这方面里面，再把方法的调用委派给目标对象，也就相当于调用目标对象的method方法，
	 当然也要把方法的输入参数args输入进去
	 */

}
