package com.ztesoft.crm.business.common.sem.kernel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;

import com.ztesoft.crm.sab.odm.kernel.ServicesDocument;
import com.ztesoft.crm.sab.odm.kernel.ServicesDocument.Services;
import com.ztesoft.crm.sab.odm.kernel.ServicesDocument.Services.Components;
import com.ztesoft.crm.sab.odm.kernel.ServicesDocument.Services.Service;
import com.ztesoft.crm.sab.odm.kernel.ServicesDocument.Services.Components.Component;
import com.ztesoft.crm.sab.odm.kernel.ServicesDocument.Services.Components.Component.Property.Props;
import com.ztesoft.crm.sab.odm.kernel.ServicesDocument.Services.Components.Component.Property.Ref;
import com.ztesoft.crm.sab.odm.kernel.ServicesDocument.Services.Components.Component.Property.Props.Prop;
import com.ztesoft.crm.sab.odm.kernel.ServicesDocument.Services.Service.Steps;
import com.ztesoft.crm.sab.odm.kernel.ServicesDocument.Services.Service.Steps.Step;
import com.ztesoft.crm.sab.odm.kernel.ServicesDocument.Services.Service.Steps.Step.Property;

public class BeanXMLParser {

	//默认xml配置文件路径class下面的路径
	private String BEAN_XML_FILE_NAME = "services.xml";

	BeanContainer beanContainer;

	public BeanXMLParser(BeanContainer beanContainer, String xmlPath) {

		this.beanContainer = beanContainer;

		this.BEAN_XML_FILE_NAME = xmlPath;
	}

	public BeanContainer getBeanContainer() {
		return beanContainer;
	}
	public void setBeanContainer(BeanContainer beanContainer) {
		this.beanContainer = beanContainer;
	}
	/** 读取解析配置文件* */
	public void parse() {
		try {

			InputStream is = null;
			ServicesDocument servicesXmlDoc = null;
			try {
				// 读取文件，获取XML流文件
				is = BeanXMLParser.class.getClassLoader().getResourceAsStream(
						BEAN_XML_FILE_NAME);
				servicesXmlDoc = ServicesDocument.Factory.parse(is);
			} finally {
				if (is != null) {
					is.close();
				}
			}
			if (null != servicesXmlDoc) {
				// 校验XML文件是否有效
				BeanParserHelper.validateXml(servicesXmlDoc);
				// 取到组件对象数组判断是否为空
				Services services = servicesXmlDoc.getServices();

				Service[] servicesArray = (Service[]) services.getServiceArray();
				//解析Service服务
				parseServices(servicesArray);

				Components[] componentsArray = (Components[]) services.getComponentsArray();
				//解析Component组件
				parseComponents(componentsArray);
			}
			//清除deregister
			cleanConfig();
		} catch (Throwable t) {
			t.printStackTrace();
			throw new BeanParseException("configure parse error message: "+ t.getMessage(), t);
		}
	}

	private void parseComponents(Components[] componentsArray)
			throws ClassNotFoundException {

		if (ArrayUtils.isNotEmpty(componentsArray)) {

			Components components = componentsArray[0];
			// 解析具体的组件
			parseComponents(components.getComponentArray());

		}
	}

	private void parseComponents(Component[] components)
			throws ClassNotFoundException {

		if (ArrayUtils.isNotEmpty(components)) {
			for (int i = 0; i < components.length; i++) {
				// 解析具体的组件
				parseComponent(components[i]);
			}
		}
	}

	private void parseComponent(Component component)
			throws ClassNotFoundException {

		//组件关键字
		String objKey = component.getName();

		//组件类
		Class clazz = ClassUtils.getClassByName(component.getClass1());

		//构造组件类描述
		BeanDescription compBeanDesc = new ComponentBeanDescription(objKey,clazz);

		//获取组件属性
		com.ztesoft.crm.sab.odm.kernel.ServicesDocument.Services.Components.Component.Property[] properyArray = null;

		properyArray = component.getPropertyArray();
		//如果属性为空则直接把组件类注册到bean容器中
		if (ArrayUtils.isEmpty(properyArray)) {

			this.beanContainer.registerClass(compBeanDesc.getKey(),
					compBeanDesc.getBeanClass(), compBeanDesc.getParameters());
			return;
		}
		//循环遍历属性
		for (int i = 0; i < properyArray.length; i++) {
			//属性名称
			String propName = properyArray[i].getName();
			//属性值
			String propValue = properyArray[i].getValue();
			//属性引用类，主要是配置handlers用处
			Ref[] refs = properyArray[i].getRefArray();
			//如果引用不为空则取引用的关键字列表
			if (ArrayUtils.isNotEmpty(refs)) {
				List refKeys = new ArrayList();
				for (int z = 0; z < refs.length; z++) {
					refKeys.add(refs[z].getComponent());
				}
				//把参数加到组件类描述中
				compBeanDesc.addPropertyParameters(propName,ComponentBeanDescription.REF, refKeys);
			} else {
				//属性Map值类型
				Props props = properyArray[i].getProps();
				if (props != null&& ArrayUtils.isNotEmpty(props.getPropArray())) {
					Map mapValue = new HashMap();
					Prop[] propArray = props.getPropArray();
					for (int z = 0; z < propArray.length; z++) {
						mapValue.put(propArray[z].getKey(), propArray[z].getValue());
					}
					//把参数加到组件类描述中
					compBeanDesc.addPropertyParameters(propName,ComponentBeanDescription.SIMPLE, mapValue);
				} else {
					compBeanDesc.addPropertyParameters(propName,ComponentBeanDescription.SIMPLE, propValue);
				}
			}
		}
		//把组件类描述注册到bean容器中
		this.beanContainer.registerClass(compBeanDesc.getKey(), compBeanDesc.getBeanClass(), compBeanDesc.getParameters());

	}

	private void parseServices(Service[] servicesArray)
			throws ClassNotFoundException {

		if (ArrayUtils.isNotEmpty(servicesArray)) {
			for (int i = 0; i < servicesArray.length; i++) {
				// 解析具体的组件
				parseService(servicesArray[i]);
			}
		}
	}

	private void parseService(Service service) throws ClassNotFoundException {
		//服务类关键字
		String objKey = service.getName();
		
		String serviceClass="com.ztesoft.crm.business.common.sem.services.BusiServiceImplemention";
		//服务类具体Class
		//Class clazz = ClassUtils.getClassByName(service.getClass1());
		Class clazz = ClassUtils.getClassByName(serviceClass);
		//构造服务类的描述信息
		BeanDescription serviceBeanDesc = new ServiceBeanDescription(objKey,
				clazz);
		//服务步骤信息，如果为空注册，服务类描述信息
		Steps steps = service.getSteps();
		if (steps == null) {
			this.beanContainer.registerClass(serviceBeanDesc.getKey(), serviceBeanDesc.getBeanClass(), serviceBeanDesc.getParameters());
			return;
		}
		//获取服务步骤数组信息
		Step[] stepArray = steps.getStepArray();
		for (int i = 0; i < stepArray.length; i++) {
			Step step = stepArray[i];
			
			String stepClass="com.ztesoft.crm.business.common.sem.steps.BusiActionImplemention";//step.getClass1()
			
			//增加服务类属性参数
			serviceBeanDesc.addPropertyParameters(step.getName(), stepClass);

			//构造步骤类描述信息
			BeanDescription stepBeanDesc = new StepBeanDescription(step
					.getName(), ClassUtils.getClassByName(stepClass));

			Property[] propertyArray = step.getPropertyArray();
			//步骤属性数组
			if (propertyArray != null) {
				for (int k = 0; k < propertyArray.length; k++) {

					Property property = propertyArray[k];
					String refKey = null;
					if (property.getRef()!=null&&property.getRef().getComponent() != null&& !"".equals(property.getRef().getComponent())) {
						//组件的引用
						refKey = property.getRef().getComponent();
					} else {
						//下一步骤
						refKey = property.getNext().getStep();
					}
					//增加引用参数
					stepBeanDesc.addPropertyParameters(property.getName(),refKey);
				}
			}
			//把步骤类描述信息注册到beanContainer容器中
			this.beanContainer.registerClass(stepBeanDesc.getKey(),
					stepBeanDesc.getBeanClass(), stepBeanDesc.getParameters());
		}
		//把服务类描述信息注册到beanContainer容器中
		this.beanContainer.registerClass(serviceBeanDesc.getKey(), serviceBeanDesc.getBeanClass(), serviceBeanDesc.getParameters());
	}

	private void cleanConfig() {
		ConvertUtils.deregister();

	}

}
