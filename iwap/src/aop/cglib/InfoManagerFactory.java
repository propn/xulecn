/**
 * 
 */
package aop.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * @author lei
 * 
 */
public class InfoManagerFactory {
	private static InfoManager manger = new InfoManager();

	/**
	 * ����ԭʼ��InfoManager
	 * 
	 * @return
	 */
	public static InfoManager getInstance() {
		return manger;
	}

	/**
	 * ��������Ȩ�޼����InfoManager
	 * 
	 * @param auth
	 * @return
	 */
	public static InfoManager getAuthInstance(AuthProxy auth) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(InfoManager.class);
		enhancer.setCallback(auth);
		return (InfoManager) enhancer.create();
	}

	/**
	 * ������ͬȨ��Ҫ���InfoManager
	 * 
	 * @param auth
	 * @return
	 */
	public static InfoManager getSelectivityAuthInstance(AuthProxy auth) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(InfoManager.class);
		enhancer.setCallbacks(new Callback[] { auth, NoOp.INSTANCE });
		enhancer.setCallbackFilter(new AuthProxyFilter());
		return (InfoManager) enhancer.create();
	}

}
