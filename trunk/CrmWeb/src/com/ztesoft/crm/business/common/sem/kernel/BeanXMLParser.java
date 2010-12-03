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

	//Ĭ��xml�����ļ�·��class�����·��
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
	/** ��ȡ���������ļ�* */
	public void parse() {
		try {

			InputStream is = null;
			ServicesDocument servicesXmlDoc = null;
			try {
				// ��ȡ�ļ�����ȡXML���ļ�
				is = BeanXMLParser.class.getClassLoader().getResourceAsStream(
						BEAN_XML_FILE_NAME);
				servicesXmlDoc = ServicesDocument.Factory.parse(is);
			} finally {
				if (is != null) {
					is.close();
				}
			}
			if (null != servicesXmlDoc) {
				// У��XML�ļ��Ƿ���Ч
				BeanParserHelper.validateXml(servicesXmlDoc);
				// ȡ��������������ж��Ƿ�Ϊ��
				Services services = servicesXmlDoc.getServices();

				Service[] servicesArray = (Service[]) services.getServiceArray();
				//����Service����
				parseServices(servicesArray);

				Components[] componentsArray = (Components[]) services.getComponentsArray();
				//����Component���
				parseComponents(componentsArray);
			}
			//���deregister
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
			// ������������
			parseComponents(components.getComponentArray());

		}
	}

	private void parseComponents(Component[] components)
			throws ClassNotFoundException {

		if (ArrayUtils.isNotEmpty(components)) {
			for (int i = 0; i < components.length; i++) {
				// ������������
				parseComponent(components[i]);
			}
		}
	}

	private void parseComponent(Component component)
			throws ClassNotFoundException {

		//����ؼ���
		String objKey = component.getName();

		//�����
		Class clazz = ClassUtils.getClassByName(component.getClass1());

		//�������������
		BeanDescription compBeanDesc = new ComponentBeanDescription(objKey,clazz);

		//��ȡ�������
		com.ztesoft.crm.sab.odm.kernel.ServicesDocument.Services.Components.Component.Property[] properyArray = null;

		properyArray = component.getPropertyArray();
		//�������Ϊ����ֱ�Ӱ������ע�ᵽbean������
		if (ArrayUtils.isEmpty(properyArray)) {

			this.beanContainer.registerClass(compBeanDesc.getKey(),
					compBeanDesc.getBeanClass(), compBeanDesc.getParameters());
			return;
		}
		//ѭ����������
		for (int i = 0; i < properyArray.length; i++) {
			//��������
			String propName = properyArray[i].getName();
			//����ֵ
			String propValue = properyArray[i].getValue();
			//���������࣬��Ҫ������handlers�ô�
			Ref[] refs = properyArray[i].getRefArray();
			//������ò�Ϊ����ȡ���õĹؼ����б�
			if (ArrayUtils.isNotEmpty(refs)) {
				List refKeys = new ArrayList();
				for (int z = 0; z < refs.length; z++) {
					refKeys.add(refs[z].getComponent());
				}
				//�Ѳ����ӵ������������
				compBeanDesc.addPropertyParameters(propName,ComponentBeanDescription.REF, refKeys);
			} else {
				//����Mapֵ����
				Props props = properyArray[i].getProps();
				if (props != null&& ArrayUtils.isNotEmpty(props.getPropArray())) {
					Map mapValue = new HashMap();
					Prop[] propArray = props.getPropArray();
					for (int z = 0; z < propArray.length; z++) {
						mapValue.put(propArray[z].getKey(), propArray[z].getValue());
					}
					//�Ѳ����ӵ������������
					compBeanDesc.addPropertyParameters(propName,ComponentBeanDescription.SIMPLE, mapValue);
				} else {
					compBeanDesc.addPropertyParameters(propName,ComponentBeanDescription.SIMPLE, propValue);
				}
			}
		}
		//�����������ע�ᵽbean������
		this.beanContainer.registerClass(compBeanDesc.getKey(), compBeanDesc.getBeanClass(), compBeanDesc.getParameters());

	}

	private void parseServices(Service[] servicesArray)
			throws ClassNotFoundException {

		if (ArrayUtils.isNotEmpty(servicesArray)) {
			for (int i = 0; i < servicesArray.length; i++) {
				// ������������
				parseService(servicesArray[i]);
			}
		}
	}

	private void parseService(Service service) throws ClassNotFoundException {
		//������ؼ���
		String objKey = service.getName();
		
		String serviceClass="com.ztesoft.crm.business.common.sem.services.BusiServiceImplemention";
		//���������Class
		//Class clazz = ClassUtils.getClassByName(service.getClass1());
		Class clazz = ClassUtils.getClassByName(serviceClass);
		//����������������Ϣ
		BeanDescription serviceBeanDesc = new ServiceBeanDescription(objKey,
				clazz);
		//��������Ϣ�����Ϊ��ע�ᣬ������������Ϣ
		Steps steps = service.getSteps();
		if (steps == null) {
			this.beanContainer.registerClass(serviceBeanDesc.getKey(), serviceBeanDesc.getBeanClass(), serviceBeanDesc.getParameters());
			return;
		}
		//��ȡ������������Ϣ
		Step[] stepArray = steps.getStepArray();
		for (int i = 0; i < stepArray.length; i++) {
			Step step = stepArray[i];
			
			String stepClass="com.ztesoft.crm.business.common.sem.steps.BusiActionImplemention";//step.getClass1()
			
			//���ӷ��������Բ���
			serviceBeanDesc.addPropertyParameters(step.getName(), stepClass);

			//���첽����������Ϣ
			BeanDescription stepBeanDesc = new StepBeanDescription(step
					.getName(), ClassUtils.getClassByName(stepClass));

			Property[] propertyArray = step.getPropertyArray();
			//������������
			if (propertyArray != null) {
				for (int k = 0; k < propertyArray.length; k++) {

					Property property = propertyArray[k];
					String refKey = null;
					if (property.getRef()!=null&&property.getRef().getComponent() != null&& !"".equals(property.getRef().getComponent())) {
						//���������
						refKey = property.getRef().getComponent();
					} else {
						//��һ����
						refKey = property.getNext().getStep();
					}
					//�������ò���
					stepBeanDesc.addPropertyParameters(property.getName(),refKey);
				}
			}
			//�Ѳ�����������Ϣע�ᵽbeanContainer������
			this.beanContainer.registerClass(stepBeanDesc.getKey(),
					stepBeanDesc.getBeanClass(), stepBeanDesc.getParameters());
		}
		//�ѷ�����������Ϣע�ᵽbeanContainer������
		this.beanContainer.registerClass(serviceBeanDesc.getKey(), serviceBeanDesc.getBeanClass(), serviceBeanDesc.getParameters());
	}

	private void cleanConfig() {
		ConvertUtils.deregister();

	}

}
