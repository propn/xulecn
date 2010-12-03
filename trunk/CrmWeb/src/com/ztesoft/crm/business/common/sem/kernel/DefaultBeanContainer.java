package com.ztesoft.crm.business.common.sem.kernel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DefaultBeanContainer implements BeanContainer {

		//���װ��MAP����
	   private Map wraprerCache = new Hashtable();

	   //���װ���б�
	   private List wraprerList = new ArrayList();
	   
	   //ʵ���б�
	   private List instantiatedWrapperList = new ArrayList();

	   //�������л�ȡ�����
	   public Object getBean(Object key) {
	      BeanWrapper wrapper = getBeanWrapper(key);
	      if (wrapper != null) {
	         return wrapper.getBeanInstance();
	      } else {
	         return null;
	      }
	   }
	   //ͨ��class���������
	   public Object getBeanByClass(Class cls) {
		   BeanWrapper wrapper = (BeanWrapper) getBeanWrapper(cls);
	      return wrapper == null ? null : wrapper.getBeanInstance();
	   }
	   //��ȡ����������б�
	   public List getBeans() {
	      return getBeanInstancesByclass(null);
	   }

	   //�жϰ�װ�����Ƿ����ĳ������Ĺؼ���
	   public boolean isExist(Object key){

	      if(key != null)
	         return wraprerCache.containsKey(key);
	      else
	         return false;
	   }
	   //ͨ��class��ѯ����������ͬ��������б�
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

	   //��Classע�ᵽ������
	   public BeanWrapper registerClass(Class cls) {
	      return registerClass(cls, cls);
	   }
	   //��Classע�ᵽ������
	   public BeanWrapper registerClass(Object key, Class cls) {
			
		   return registerClass(key, cls,null);
	   }

	   //��Classע�ᵽ�����У���װ�������װ��,���������Բ����б�
	   public BeanWrapper registerClass(Object key, Class cls,Parameter[]parameters) {

	      return registerBeanWrapper(new ClassWrapper(key, cls, parameters));
	   }
	   //ȡ����ע�ᵽ������
	   public BeanWrapper unregisterClass(Object key) {
	      if (!wraprerCache.containsKey(key)) {
	         throw new RuntimeException("�����в����ڴ˹ؼ���"+key+"��ע��,����ȡ��ע��!");
	      } else {
	    	  BeanWrapper wrapper = (BeanWrapper) wraprerCache.remove(key);
	         wraprerList.remove(wrapper);
	         return wrapper;
	      }
	   }
	   //ͨ�����ͻ�ȡ�������װ�����б�
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
	   //�Ѿ�������װ��ע�ᵽ������
	   private BeanWrapper registerBeanWrapper(BeanWrapper wrapper) {
	      Object key = wrapper.getKey();
	      if (wraprerCache.containsKey(key)) {
	    	  throw new RuntimeException("�ظ�ע�����װ��");
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
