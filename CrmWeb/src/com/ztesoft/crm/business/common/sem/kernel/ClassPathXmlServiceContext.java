package com.ztesoft.crm.business.common.sem.kernel;



public class ClassPathXmlServiceContext extends ServiceContext{

	public ClassPathXmlServiceContext(String xmlpath){
		
		//��ʼ������
		super();

		//��ʼ��XML������
		BeanXMLParser beanParser = new BeanXMLParser(beanContainer,xmlpath);
		
		//��ʼ����XML�ļ�
		beanParser.parse();
		
	}

}
