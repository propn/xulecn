/**
 * 
 */
package org.leixu.iwap.aop.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

/**
 * @author lei
 *
 */
public class AuthProxyFilter implements  CallbackFilter {
	private   static   final   int  AUTH_NEED      =   0 ;
    private   static   final   int  AUTH_NOT_NEED  =   1 ;

    /** 
    * <pre>
    * ѡ��ʹ�õ�proxy
    * �������query��������ʹ�õڶ���proxy
    * ����ʹ�õ�һ��proxy
    * </pre>
     */ 
   @Override
    public   int  accept(Method method) {
        if  ( " query " .equals(method.getName())) {
            return  AUTH_NOT_NEED;
       }
        return  AUTH_NEED;
   }

}
