package com.ztesoft.crm.business.common.sem.kernel;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Step Component ���������
 **/
public abstract class BeanDescription implements java.io.Serializable{
	
	protected Object key;//��ؼ���
	protected Class beanClass; //����
	public BeanDescription(Object key,Class beanClass){
		this.key=key;
		this.beanClass=beanClass;
	}
	protected List parameters=new ArrayList(); 
	
	public Class getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}
	/**
	 * �������Բ�������
	 * @return ����������ʵ������ֵ����Ҫ�Ĳ���
	 * */
	public Parameter[] getParameters() {
		
		Parameter[] propertyParameters=new PropertyParameter[parameters.size()]; 
		
		for(int i=0;i<propertyParameters.length;i++){
			
			propertyParameters[i]=(PropertyParameter)parameters.get(i);
		}
		return propertyParameters;
	}
	
	/**
	 * ������Բ���
	 * @param propName ������
	 * @param value ����ֵ value �����Ǿ������ʵ����Map ��Collection,String ��
	 * */
	public abstract void addPropertyParameters(Object propName,Object vlaue);
	
	/**
	 * �˷���������ComponentDescrption���õ�
	 * @param propName ������
	 * @param propType ��������
	 * @param value ����ֵ value �����Ǿ������ʵ����Map ��Collection,String ��
	 * */
	public  void addPropertyParameters(Object propName,Object propType,Object vlaue){
		
		
	}
}
