package com.ztesoft.crm.business.common.sem.kernel;

import java.util.List;

/**
 * Bean ���� ��װ Service Step Component��
 */
public interface BeanContainer {

	/**
	 * @param key xml�����е�����name�ؼ���
	 * @return ���ؾ���class ʵ��
	 */
	public Object getBean(Object key);

	/**
	 * @param class service step component������
	 * @return ���ؾ���class ʵ��
	 */
	public Object getBeanByClass(Class cls);

	/**
	 * @return ��������������classʵ��
	 */
	public List getBeans();

	/**
	 * @param cls service step component������
	 * @return ���ؾ���class��װ�� ClassWrapper
	 */
	
	public BeanWrapper registerClass(Class cls);

	/**
	 * @param key xml�����е�����name�ؼ���
	 * @param cls service step component������
	 * @return ���ؾ���class��װ�� ClassWrapper
	 */
	public BeanWrapper registerClass(Object key, Class cls);

	
	/**
	 * @param key xml�����е�����name�ؼ���
	 * @param cls service step component������
	 * @param parameters ���Ե����͵ķ�װ����ʵ�����������Ե�ʱ��
	 * @return ���ؾ���class��װ�� ClassWrapper
	 */
	
	public BeanWrapper registerClass(Object key, Class cls,
			Parameter[] parameters);

	/**
	 * ��������ɾ���������װ
	 * @param key xml�����е�����name�ؼ���
	 * @return ���ؾ���class��װ�� ClassWrapper
	 */
	
	public BeanWrapper unregisterClass(Object key);

	/**
	 *  �ж��������Ƿ����ĳ���������װ��
	 *  @param key xml�����е�����name�ؼ���
	 *  
	 * */
	public boolean isExist(Object key);

}
