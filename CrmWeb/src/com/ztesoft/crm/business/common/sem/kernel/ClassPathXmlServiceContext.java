package com.ztesoft.crm.business.common.sem.kernel;



public class ClassPathXmlServiceContext extends ServiceContext{

	public ClassPathXmlServiceContext(String xmlpath){
		
		//初始化容器
		super();

		//初始化XML解析器
		BeanXMLParser beanParser = new BeanXMLParser(beanContainer,xmlpath);
		
		//开始解析XML文件
		beanParser.parse();
		
	}

}
