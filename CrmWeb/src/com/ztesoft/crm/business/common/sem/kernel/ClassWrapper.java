
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
 * class 包装器
 * */
public class ClassWrapper implements BeanWrapper {

	//关键字
   private Object key;
   	//类原型
   private Class beanClass;
    //构造函数参数列表
   private List constructorParameters = null;
    //属性参数列表
   private List propertyParameters = null;
   //bean容器
   private BeanContainer container;

   //初始化方法签名，暂不使用
   private Method initMethod;
   //初始化方法名
   private String initMethodName;
   //参数列表
   private Parameter[]parameters;


   public ClassWrapper(Object key, Class beanClass,Parameter[]parameters) {
      this.key = key;
      this.beanClass = beanClass;
      this.parameters = parameters;
      this.constructorParameters = new ArrayList();
      this.propertyParameters = new ArrayList();
      //分解参数类型
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
   //分解参数类型，加到相应的列表中
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
   //实例化bean
   private Object instantiateBean() {

	 //初始化构造函数的参数
	 this.initParameters(constructorParameters);
     //初始化属性的参数
     this.initParameters(propertyParameters);
     
     if(initMethodName != null && !initMethodName.trim().equals("")){
       try{
    	   this.initMethod = this.getBeanImplimetationClass().getMethod(initMethodName,new Class[]{});

       }catch(Exception e){
    	   throw new RuntimeException("未查询到初始化函数["+initMethodName+"][" + key +"]");
       }
     }
     //通过构造函数实例化具体类
     Object instance = this.createInstanceWithConstructor();
     
     //设置bean属性
     this.setBeanPropertyValue(instance);

      if( this.initMethod != null && instance != null){
        try{
          this.initMethod.invoke(instance,new Object[]{});
        }catch(Exception e){
          throw new RuntimeException("执行初始化函数失败:" + key);
        }
      }

      return instance;
   }

   //初始化参数
   private void initParameters(List parameters) {
      for (int i = 0; i < parameters.size(); i++) {
         Parameter parameter = (Parameter)parameters.get(i);
         
         if (parameter.getValue() instanceof RefValue) {//引用类型的会出现递归的情况
            parameter.setValue(parameterToValue(parameter.getValue()));
         }else if(parameter.getValue() instanceof ClassValue){//通过class类加载的
            parameter.setValue(parameterToValue(parameter.getValue()));
         }else if (parameter.getValue() instanceof Collection) {//集合型的
            Collection col = (Collection)parameter.getValue();
            List list = new ArrayList();
            list.addAll(col);
            col.clear();
            //循环取出具体的集合元素
            for (int j = 0; j < list.size(); j++) {
               Object item = list.get(j);
               col.add(parameterToValue(item));
            }
         }else if (parameter.getValue() instanceof Map) {//封装成map集合的
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
 
         //出现循环递归查找bean
         Object refBean = this.getContainer().getBean(refKey);
     
         //检查引用的类是否存在
         checkRef(refKey,refBean);

         return refBean;

      }else if (value instanceof ClassValue){
    	  
    	  //从容器中获取
    	  ClassValue classValue=((ClassValue)value);
    	  if(!getContainer().isExist(classValue.getRefKey())){//容器中不含的类则直接实例化
    		  String className = ((ClassValue)value).getClazz();
    		  return newInstanceByClsName(className);
          }else{
        	  //出现滴灌查找
        	  return this.getContainer().getBean(classValue.getRefKey());
          }
    
      }
      else if (value instanceof Collection){//集合类型转成值
         Collection col = (Collection)value;
         List list = new ArrayList();
         list.addAll(col);
         col.clear();
         for (int j = 0; j < list.size(); j++) {
            Object item = list.get(j);
            col.add(parameterToValue(item));//会出现递归的情况
         }
         return col;
      }else if (value instanceof Map){//简单Map类型
         Map map = (Map)value;
         Iterator itor = map.keySet().iterator();
         while (itor.hasNext()) {
            Object key = itor.next();
            Object entry = map.get(key);
            map.put(key,parameterToValue(entry));
         }
         return map;
      }else{
         return value;//直接返回值
      }
   }
   
   private Object newInstanceByClsName(String name){
	      try{
	         return Class.forName(name,true,beanClass.getClassLoader()).newInstance();
	      }catch(Exception e){
	         throw new RuntimeException(
	               "通过类名实例化失败:"
	               + name,e);
	      }
	   }
   //通过构造函数创建类实例，返回类实例对性
   private Object createInstanceWithConstructor() {

	  //封装构造函数所需要的参数
      Object[] constructorParamValue = null;
      constructorParamValue = new Object[constructorParameters.size()];
      for (int i = 0; i < constructorParameters.size(); i++) {
         constructorParamValue[i] = ((ConstructorParameter)constructorParameters.get(i)).getValue();
      }

      try{
    	  //获取相匹配的构造函数
         Constructor constructor = this.getMatchedConstructor(constructorParamValue);
         if(constructor != null){
        	 //实例化
            return constructor.newInstance(constructorParamValue);
         }

      }catch(Exception e){
         throw new RuntimeException(
               "构造类失败:"
               + getBeanImplimetationClass(),e);
      }
      return constructorParamValue;
   }

   //通过构造函数参数数组查询相匹配的构造函数
   private Constructor getMatchedConstructor(Object[] constructorParamValue){
      Constructor targetConstructor = null;
      int len = constructorParameters.size();
      //找到所有构造函数
      List constructors = getAllMachedConstructors(len);
      boolean isFound =true;
      //循环查找匹配的构造函数
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
   //找到当前类所有的构造函数
   private List getAllMachedConstructors(int len) {

      List list = new ArrayList();
      //当前Class类
      Class cls = this.getBeanImplimetationClass();
      Constructor[] declareConstuctors = cls.getDeclaredConstructors();
      for (int i = 0; i < declareConstuctors.length; i++) {
         if (declareConstuctors[i].getParameterTypes().length == len){
            list.add(declareConstuctors[i]);
         }
      }
      return list;
   }
   //给属性赋值类实例instance对象
   private void setBeanPropertyValue(Object instance) {
      if(instance instanceof Collection){//列表型
         Collection col =(Collection)instance;
         for (int i = 0; i < propertyParameters.size(); i++) {
            PropertyParameter parameter = (PropertyParameter)propertyParameters.get(i);
            if (parameter.getValue() instanceof Collection){
               col.addAll((Collection)parameter.getValue());
            }
         }

      }else if(instance instanceof Map){//MAP型
         Map map =(Map)instance;
         for (int i = 0; i < propertyParameters.size(); i++) {
            PropertyParameter parameter = (PropertyParameter)propertyParameters.get(i);
            if (parameter.getValue() instanceof Map){
               map.putAll((Map)parameter.getValue());
            }
         }
      }else {
    	  //通过setter方法赋值
         Map methodMap = new Hashtable();
         if (propertyParameters != null && !propertyParameters.isEmpty()) {
            try {
               BeanInfo beanInfo = Introspector.getBeanInfo(getBeanImplimetationClass());
               PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

               //把属性的setter方法签名放置在methodMap中
               for (int i = 0; i < propertyDescriptors.length; i++) {
                  PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
                  Method setter = propertyDescriptor.getWriteMethod();
                  if (setter != null)
                     methodMap.put(propertyDescriptor.getName(), setter);
               }
            } catch (IntrospectionException e) {
               throw new RuntimeException("加载类失败,类名"
                     + getBeanImplimetationClass(),e);
            }

            for (int i = 0; i < propertyParameters.size(); i++) {
               PropertyParameter parameter = (PropertyParameter)propertyParameters.get(i);
               //方法名
               Method setter = (Method) methodMap.get(parameter.getName());
               if (setter == null) {
                  throw new RuntimeException(
                        "加载类失败,未查询到公共 setter 方法 for property: ["
                        + parameter.getName() + "] in "
                        + instance.getClass().getName());

               } else {
                  try {
                	  //参数类型
                     Class type = setter.getParameterTypes()[0];
                     //类型转换
                     Object value = convertType(type,parameter.getValue());
                     //方法执行
                     setter.invoke(instance, new Object[] {value});
                  } catch (Exception e) {
                     throw new RuntimeException(
                           "给属性赋值失败 property: ["
                           + parameter.getName() + "] in "
                           + instance.getClass().getName(),e);
                  }
               }
            }
         }
      }
   }

   //如果容器中不存在某个类的引用，则抛出异常信息
   private void checkRef(Object refKey,Object refValue){
      if( refValue == null){
         throw new RuntimeException(refKey+" 类未查询到!");
      }
   }

   //简单类型的转换
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