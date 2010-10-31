/**
 * 
 */
package aop.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author lei
 * 
 */
public class Ctx implements MethodInterceptor {

	// 实现intercept，来实现拦截
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println("CGLib Before invoke ---" + method.getName());
		obj = proxy.invokeSuper(obj, args);
		System.out.println("CGLib After invoke -----" + method.getName());
		return obj;
	}

	// 绑定过程
	public static Object getInstance(Class clz) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clz);
		enhancer.setCallback(new AuthProxy(""));
		return enhancer.create();
	}

	public static void main(String[] args) {
		InfoManager manager = (InfoManager) getInstance(InfoManager.class);
		manager.create();
	}

}