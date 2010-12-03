
package com.ztesoft.crm.business.common.sem.kernel;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * class ��װ��
 * */
public class ClassWrapper implements BeanWrapper {

	//�ؼ���
   private Object key;
   	//��ԭ��
   private Class beanClass;
    //���캯�������б�
   private List constructorParameters = null;
    //���Բ����б�
   private List propertyParameters = null;
   //bean����
   private BeanContainer container;

   //��ʼ������ǩ�����ݲ�ʹ��
   private Method initMethod;
   //��ʼ��������
   private String initMethodName;
   //�����б�
   private Parameter[]parameters;


   public ClassWrapper(Object key, Class beanClass,Parameter[]parameters) {
      this.key = key;
      this.beanClass = beanClass;
      this.parameters = parameters;
      this.constructorParameters = new ArrayList();
      this.propertyParameters = new ArrayList();
      //�ֽ��������
      this.seperateParameter();
   }
   public Object getKey() {
      return this.key;
   }
   public Class getBeanImplimetationClass() {
      return this.beanClass;
   }


   public BeanContainer getContainer() {
      return this.container;
   }
   public void setContainer(BeanContainer container) {
      this.container = container;
   }
   public Object getBeanInstance(){
	   	 
         return this.instantiateBean();
   }
   //�ֽ�������ͣ��ӵ���Ӧ���б���
   private void seperateParameter(){
      if(parameters != null && parameters.length >0){
         for(int i = 0;i< parameters.length;i++){
            if(parameters[i] instanceof ConstructorParameter){
               constructorParameters.add(parameters[i]);
            }else if (parameters[i] instanceof PropertyParameter){
               propertyParameters.add(parameters[i]);
            }
         }
      }
   }
   //ʵ����bean
   private Object instantiateBean() {

	 //��ʼ�����캯���Ĳ���
	 this.initParameters(constructorParameters);
     //��ʼ�����ԵĲ���
     this.initParameters(propertyParameters);
     
     if(initMethodName != null && !initMethodName.trim().equals("")){
       try{
    	   this.initMethod = this.getBeanImplimetationClass().getMethod(initMethodName,new Class[]{});

       }catch(Exception e){
    	   throw new RuntimeException("δ��ѯ����ʼ������["+initMethodName+"][" + key +"]");
       }
     }
     //ͨ�����캯��ʵ����������
     Object instance = this.createInstanceWithConstructor();
     
     //����bean����
     this.setBeanPropertyValue(instance);

      if( this.initMethod != null && instance != null){
        try{
          this.initMethod.invoke(instance,new Object[]{});
        }catch(Exception e){
          throw new RuntimeException("ִ�г�ʼ������ʧ��:" + key);
        }
      }

      return instance;
   }

   //��ʼ������
   private void initParameters(List parameters) {
      for (int i = 0; i < parameters.size(); i++) {
         Parameter parameter = (Parameter)parameters.get(i);
         
         if (parameter.getValue() instanceof RefValue) {//�������͵Ļ���ֵݹ�����
            parameter.setValue(parameterToValue(parameter.getValue()));
         }else if(parameter.getValue() instanceof ClassValue){//ͨ��class����ص�
            parameter.setValue(parameterToValue(parameter.getValue()));
         }else if (parameter.getValue() instanceof Collection) {//�����͵�
            Collection col = (Collection)parameter.getValue();
            List list = new ArrayList();
            list.addAll(col);
            col.clear();
            //ѭ��ȡ������ļ���Ԫ��
            for (int j = 0; j < list.size(); j++) {
               Object item = list.get(j);
               col.add(parameterToValue(item));
            }
         }else if (parameter.getValue() instanceof Map) {//��װ��map���ϵ�
            Map map = (Map) parameter.getValue();
            Iterator itor = map.keySet().iterator();
            while (itor.hasNext()) {
               Object key = itor.next();
               Object entry = map.get(key);
               Object value = parameterToValue(entry);
               map.put(key,value);
            }
         }
      }
   }

 
   private Object parameterToValue(Object value){
      if(value instanceof RefValue){
    	 
    	 RefValue refValue=((RefValue)value);
    	  
         Object refKey = refValue.getRefKey();
 
         //����ѭ���ݹ����bean
         Object refBean = this.getContainer().getBean(refKey);
     
         //������õ����Ƿ����
         checkRef(refKey,refBean);

         return refBean;

      }else if (value instanceof ClassValue){
    	  
    	  //�������л�ȡ
    	  ClassValue classValue=((ClassValue)value);
    	  if(!getContainer().isExist(classValue.getRefKey())){//�����в���������ֱ��ʵ����
    		  String className = ((ClassValue)value).getClazz();
    		  return newInstanceByClsName(className);
          }else{
        	  //���ֵι����
        	  return this.getContainer().getBean(classValue.getRefKey());
          }
    
      }
      else if (value instanceof Collection){//��������ת��ֵ
         Collection col = (Collection)value;
         List list = new ArrayList();
         list.addAll(col);
         col.clear();
         for (int j = 0; j < list.size(); j++) {
            Object item = list.get(j);
            col.add(parameterToValue(item));//����ֵݹ�����
         }
         return col;
      }else if (value instanceof Map){//��Map����
         Map map = (Map)value;
         Iterator itor = map.keySet().iterator();
         while (itor.hasNext()) {
            Object key = itor.next();
            Object entry = map.get(key);
            map.put(key,parameterToValue(entry));
         }
         return map;
      }else{
         return value;//ֱ�ӷ���ֵ
      }
   }
   
   private Object newInstanceByClsName(String name){
	      try{
	         return Class.forName(name,true,beanClass.getClassLoader()).newInstance();
	      }catch(Exception e){
	         throw new RuntimeException(
	               "ͨ������ʵ����ʧ��:"
	               + name,e);
	      }
	   }
   //ͨ�����캯��������ʵ����������ʵ������
   private Object createInstanceWithConstructor() {

	  //��װ���캯������Ҫ�Ĳ���
      Object[] constructorParamValue = null;
      constructorParamValue = new Object[constructorParameters.size()];
      for (int i = 0; i < constructorParameters.size(); i++) {
         constructorParamValue[i] = ((ConstructorParameter)constructorParameters.get(i)).getValue();
      }

      try{
    	  //��ȡ��ƥ��Ĺ��캯��
         Constructor constructor = this.getMatchedConstructor(constructorParamValue);
         if(constructor != null){
        	 //ʵ����
            return constructor.newInstance(constructorParamValue);
         }

      }catch(Exception e){
         throw new RuntimeException(
               "������ʧ��:"
               + getBeanImplimetationClass(),e);
      }
      return constructorParamValue;
   }

   //ͨ�����캯�����������ѯ��ƥ��Ĺ��캯��
   private Constructor getMatchedConstructor(Object[] constructorParamValue){
      Constructor targetConstructor = null;
      int len = constructorParameters.size();
      //�ҵ����й��캯��
      List constructors = getAllMachedConstructors(len);
      boolean isFound =true;
      //ѭ������ƥ��Ĺ��캯��
      for(int i= 0;i < constructors.size();i++){
         Constructor construcor = (Constructor)constructors.get(i);
         Class[] paramType = construcor.getParameterTypes();
         
         isFound =true;
         for(int j =0;j <constructorParamValue.length;j++){
            if(!(paramType[j].isInstance(constructorParamValue[j]))){
               isFound = false;
               break;
            }
         }
         if(isFound){
            targetConstructor = (Constructor)constructors.get(i);
            break;
         }
      }

      return targetConstructor;
   }
   //�ҵ���ǰ�����еĹ��캯��
   private List getAllMachedConstructors(int len) {

      List list = new ArrayList();
      //��ǰClass��
      Class cls = this.getBeanImplimetationClass();
      Constructor[] declareConstuctors = cls.getDeclaredConstructors();
      for (int i = 0; i < declareConstuctors.length; i++) {
         if (declareConstuctors[i].getParameterTypes().length == len){
            list.add(declareConstuctors[i]);
         }
      }
      return list;
   }
   //�����Ը�ֵ��ʵ��instance����
   private void setBeanPropertyValue(Object instance) {
      if(instance instanceof Collection){//�б���
         Collection col =(Collection)instance;
         for (int i = 0; i < propertyParameters.size(); i++) {
            PropertyParameter parameter = (PropertyParameter)propertyParameters.get(i);
            if (parameter.getValue() instanceof Collection){
               col.addAll((Collection)parameter.getValue());
            }
         }

      }else if(instance instanceof Map){//MAP��
         Map map =(Map)instance;
         for (int i = 0; i < propertyParameters.size(); i++) {
            PropertyParameter parameter = (PropertyParameter)propertyParameters.get(i);
            if (parameter.getValue() instanceof Map){
               map.putAll((Map)parameter.getValue());
            }
         }
      }else {
    	  //ͨ��setter������ֵ
         Map methodMap = new Hashtable();
         if (propertyParameters != null && !propertyParameters.isEmpty()) {
            try {
               BeanInfo beanInfo = Introspector.getBeanInfo(getBeanImplimetationClass());
               PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

               //�����Ե�setter����ǩ��������methodMap��
               for (int i = 0; i < propertyDescriptors.length; i++) {
                  PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
                  Method setter = propertyDescriptor.getWriteMethod();
                  if (setter != null)
                     methodMap.put(propertyDescriptor.getName(), setter);
               }
            } catch (IntrospectionException e) {
               throw new RuntimeException("������ʧ��,����"
                     + getBeanImplimetationClass(),e);
            }

            for (int i = 0; i < propertyParameters.size(); i++) {
               PropertyParameter parameter = (PropertyParameter)propertyParameters.get(i);
               //������
               Method setter = (Method) methodMap.get(parameter.getName());
               if (setter == null) {
                  throw new RuntimeException(
                        "������ʧ��,δ��ѯ������ setter ���� for property: ["
                        + parameter.getName() + "] in "
                        + instance.getClass().getName());

               } else {
                  try {
                	  //��������
                     Class type = setter.getParameterTypes()[0];
                     //����ת��
                     Object value = convertType(type,parameter.getValue());
                     //����ִ��
                     setter.invoke(instance, new Object[] {value});
                  } catch (Exception e) {
                     throw new RuntimeException(
                           "�����Ը�ֵʧ�� property: ["
                           + parameter.getName() + "] in "
                           + instance.getClass().getName(),e);
                  }
               }
            }
         }
      }
   }

   //��������в�����ĳ��������ã����׳��쳣��Ϣ
   private void checkRef(Object refKey,Object refValue){
      if( refValue == null){
         throw new RuntimeException(refKey+" ��δ��ѯ��!");
      }
   }

   //�����͵�ת��
   private Object convertType(Class type, Object parameterValue) {
      if (type.equals(Boolean.class) || type.equals(boolean.class)) {
         return Boolean.valueOf(parameterValue.toString());
      } else if (type.equals(Byte.class) || type.equals(byte.class)) {
         return Byte.valueOf(parameterValue.toString());
      } else if (type.equals(Short.class) || type.equals(short.class)) {
         return Short.valueOf(parameterValue.toString());
      } else if (type.equals(Integer.class) || type.equals(int.class)) {
         return Integer.valueOf(parameterValue.toString());
      } else if (type.equals(Long.class) || type.equals(long.class)) {
         return Long.valueOf(parameterValue.toString());
      } else if (type.equals(Float.class) || type.equals(float.class)) {
         return Float.valueOf(parameterValue.toString());
      } else if (type.equals(Double.class) || type.equals(double.class)) {
         return Double.valueOf(parameterValue.toString());
      } else if (type.equals(Character.class) || type.equals(char.class)) {
         return new Character(parameterValue.toString().toCharArray()[0]);
      } else {
         return parameterValue;
      }
   }


}