package com.ztesoft.crm.business.common.sem.kernel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 组件类描述信息
 * */
public class ComponentBeanDescription extends BeanDescription{

	
	public ComponentBeanDescription(Object key,Class beanClass){
		
		super(key,beanClass);
		
		
	}
	
	//引用类型
	public final static String REF="ref";
	
	//简单类型
	public final static String SIMPLE="simple";
	
//	符合引用型的
	public final static String HANDLERS="handlers";
	
	/**
	 * @param propName 属性名
	 * @param refKeys 引用关键字列表
	 * */
	private void addRefPropertyParameter(Object propName, Object refKeys) {
		
		
		
		Collection refs=new ArrayList();
		List refKeysList=(List)refKeys; 
		
		for(int i=0;i<refKeysList.size();i++){
			//构造RefValue型参数
			refs.add(new RefValue(refKeysList.get(i)));
		}
		
		if(!propName.equals(HANDLERS)){
			if(!refs.isEmpty()){
				this.parameters.add(new PropertyParameter(propName.toString(),refs.toArray()[0]));
			}
		}else{
			//添加到参数列表中
			this.parameters.add(new PropertyParameter(propName.toString(),refs));
		}
	}

	public void addPropertyParameters(Object propName, Object vlaue) {
		
		this.parameters.add(new PropertyParameter(propName.toString(),vlaue));
	}
	/**
	 * @param propName 属性名
	 * @param propType 属性类型
	 * @param refKeys 引用关键字列表或具体的Map ,String值
	 * */
	public  void addPropertyParameters(Object propName,Object propType,Object vlaue){
		
		if(REF.equals(propType)){
			
			addRefPropertyParameter(propName,vlaue);
		}else{
			
			addPropertyParameters(propName,vlaue);
		}
		
	}

}
