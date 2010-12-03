package com.ztesoft.crm.business.common.sem.kernel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DefaultBeanContainer implements BeanContainer {

		//类包装器MAP缓存
	   private Map wraprerCache = new Hashtable();

	   //类包装器列表
	   private List wraprerList = new ArrayList();
	   
	   //实例列表
	   private List instantiatedWrapperList = new ArrayList();

	   //从容器中获取类对象
	   public Object getBean(Object key) {
	      BeanWrapper wrapper = getBeanWrapper(key);
	      if (wrapper != null) {
	         return wrapper.getBeanInstance();
	      } else {
	         return null;
	      }
	   }
	   //通过class查找类对象
	   public Object getBeanByClass(Class cls) {
		   BeanWrapper wrapper = (BeanWrapper) getBeanWrapper(cls);
	      return wrapper == null ? null : wrapper.getBeanInstance();
	   }
	   //获取所有类对象列表
	   public List getBeans() {
	      return getBeanInstancesByclass(null);
	   }

	   //判断包装器中是否存在某个具体的关键字
	   public boolean isExist(Object key){

	      if(key != null)
	         return wraprerCache.containsKey(key);
	      else
	         return false;
	   }
	   //通过class查询所有类型相同的类对象列表
	   public List getBeanInstancesByclass(Class type){
	      Map wrapperToInstanceMap = new Hashtable();

	      for (Iterator iterator = wraprerList.iterator(); iterator.hasNext();) {
	         BeanWrapper wraper = (BeanWrapper) iterator.next();
	         if (type == null
	             || type
	             .isAssignableFrom(wraper
	             .getBeanImplimetationClass())) {
	            Object beanInstance = wraper.getBeanInstance();
	            wrapperToInstanceMap.put(wraper, beanInstance);
	            instantiatedWrapperList.add(wraper);
	         }
	      }

	      List result = new ArrayList();
	      for (Iterator iterator = instantiatedWrapperList.iterator(); iterator
	           .hasNext();) {
	         Object wrapper = iterator.next();
	         final Object instance = wrapperToInstanceMap.get(wrapper);
	         if (instance != null) {
	            result.add(instance);
	         }
	      }
	      return Collections.unmodifiableList(result);
	   }

	   //把Class注册到容器中
	   public BeanWrapper registerClass(Class cls) {
	      return registerClass(cls, cls);
	   }
	   //把Class注册到容器中
	   public BeanWrapper registerClass(Object key, Class cls) {
			
		   return registerClass(key, cls,null);
	   }

	   //把Class注册到容器中，封装出了类包装器,传入了属性参数列表
	   public BeanWrapper registerClass(Object key, Class cls,Parameter[]parameters) {

	      return registerBeanWrapper(new ClassWrapper(key, cls, parameters));
	   }
	   //取消类注册到容器中
	   public BeanWrapper unregisterClass(Object key) {
	      if (!wraprerCache.containsKey(key)) {
	         throw new RuntimeException("缓存中不存在此关键字"+key+"的注册,不能取消注册!");
	      } else {
	    	  BeanWrapper wrapper = (BeanWrapper) wraprerCache.remove(key);
	         wraprerList.remove(wrapper);
	         return wrapper;
	      }
	   }
	   //通过类型获取对象类包装器的列表
	   private List getBeanWrappers(Class type) {
	      List found = new ArrayList();
	      for (Iterator iterator = wraprerList.iterator(); iterator.hasNext();) {
	    	  BeanWrapper wrapper = (BeanWrapper) iterator.next();
	         if (type.isAssignableFrom(wrapper.getBeanImplimetationClass())) {
	            found.add(wrapper);
	         }
	      }
	      return Collections.unmodifiableList(found);
	   }
	   //把具体的类包装器注册到容器中
	   private BeanWrapper registerBeanWrapper(BeanWrapper wrapper) {
	      Object key = wrapper.getKey();
	      if (wraprerCache.containsKey(key)) {
	    	  throw new RuntimeException("重复注册类包装器");
	      }
	      wrapper.setContainer(this);
	      wraprerList.add(wrapper);
	      wraprerCache.put(key, wrapper);
	      return wrapper;
	   }

	   private BeanWrapper getBeanWrapper(Object key) {
	      return (BeanWrapper) wraprerCache.get(key);
	   }
	



}
