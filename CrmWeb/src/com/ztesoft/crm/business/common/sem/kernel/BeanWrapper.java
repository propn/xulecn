package com.ztesoft.crm.business.common.sem.kernel;

public interface BeanWrapper {

	
	   /**
	    * ������ʵ���Ĺؼ���
	    * */
	   public Object getKey();

	   /**
	    * ������ʵ��������Ӧ��Class
	    * */
	   public Class getBeanImplimetationClass();
	   /**
	    * ������ʵ��
	    * */
	   public Object getBeanInstance();

	   /**
	    * ����������
	    * */
	   public BeanContainer getContainer();
	   /**
	    * ������������
	    * */
	   public void setContainer(BeanContainer container);
	
}
