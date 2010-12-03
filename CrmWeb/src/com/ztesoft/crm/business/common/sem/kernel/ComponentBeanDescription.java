package com.ztesoft.crm.business.common.sem.kernel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * �����������Ϣ
 * */
public class ComponentBeanDescription extends BeanDescription{

	
	public ComponentBeanDescription(Object key,Class beanClass){
		
		super(key,beanClass);
		
		
	}
	
	//��������
	public final static String REF="ref";
	
	//������
	public final static String SIMPLE="simple";
	
//	���������͵�
	public final static String HANDLERS="handlers";
	
	/**
	 * @param propName ������
	 * @param refKeys ���ùؼ����б�
	 * */
	private void addRefPropertyParameter(Object propName, Object refKeys) {
		
		
		
		Collection refs=new ArrayList();
		List refKeysList=(List)refKeys; 
		
		for(int i=0;i<refKeysList.size();i++){
			//����RefValue�Ͳ���
			refs.add(new RefValue(refKeysList.get(i)));
		}
		
		if(!propName.equals(HANDLERS)){
			if(!refs.isEmpty()){
				this.parameters.add(new PropertyParameter(propName.toString(),refs.toArray()[0]));
			}
		}else{
			//��ӵ������б���
			this.parameters.add(new PropertyParameter(propName.toString(),refs));
		}
	}

	public void addPropertyParameters(Object propName, Object vlaue) {
		
		this.parameters.add(new PropertyParameter(propName.toString(),vlaue));
	}
	/**
	 * @param propName ������
	 * @param propType ��������
	 * @param refKeys ���ùؼ����б������Map ,Stringֵ
	 * */
	public  void addPropertyParameters(Object propName,Object propType,Object vlaue){
		
		if(REF.equals(propType)){
			
			addRefPropertyParameter(propName,vlaue);
		}else{
			
			addPropertyParameters(propName,vlaue);
		}
		
	}

}
