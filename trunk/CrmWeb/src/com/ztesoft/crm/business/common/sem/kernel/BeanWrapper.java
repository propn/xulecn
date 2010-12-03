package com.ztesoft.crm.business.common.sem.kernel;

public interface BeanWrapper {

	
	   /**
	    * 返回类实例的关键字
	    * */
	   public Object getKey();

	   /**
	    * 返回类实例的所对应的Class
	    * */
	   public Class getBeanImplimetationClass();
	   /**
	    * 返回类实例
	    * */
	   public Object getBeanInstance();

	   /**
	    * 返回类容器
	    * */
	   public BeanContainer getContainer();
	   /**
	    * 设置容器引用
	    * */
	   public void setContainer(BeanContainer container);
	
}
