/**
 * 
 */
package com.ztesoft.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;



/**
 * bean�Ŀ�������
soft.com
 * @date 2008-3-5
 */
public class DTOUtil{
    
	// for test
	public static void main(String[]  args) throws Exception
	{
		Src src = new Src();
		Dest dest = new Dest();
		src.setId(1);
		src.setName("srcName");
		src.setExt("srcExt");
		long beg = System.currentTimeMillis();
		for (int i = 0; i < 100000; i ++)
			MyBeanUtils.copyProperties(dest, src);
		long end = System.currentTimeMillis();
 
		
		beg = System.currentTimeMillis();
		for (int i = 0; i < 100000; i ++)
			BeanUtils.copyProperties(dest, src);
		end = System.currentTimeMillis();
 
		
	}
	
	public static class  base
	{
		private int id;
		private String name;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public static class Dest extends base
	{
		
		private String ext;

		public String getExt() {
			return ext;
		}

		public void setExt(String ext) {
			this.ext = ext;
		}
	}
	
	public static class Src extends base
	{
		public String getExt() {
			return ext;
		}

		public void setExt(String ext) {
			this.ext = ext;
		}

		private String ext;		
	}
	
    public static void copyProperties(Object dest,Object ori) throws IllegalAccessException, InvocationTargetException{
        // BeanUtils.copyProperties(dest,ori);
    	MyBeanUtils.copyProperties(dest, ori);
    }
    
    public static void populate(Object dest,HashMap map) throws IllegalAccessException, InvocationTargetException{
       //BeanUtils.populate(dest,map);
    	MyBeanUtils.populate(dest, map);
    }
    
    /**
     * ����mapping�ַ�������2���������ԣ�mapping��ʽ���£�
     * 'desPropertyName:orgPropertyName'����'*'��������ͬ�����Կ���
     * 
     * @param org
     * @param des
     * @param mapping
     * @throws Exception
     */
    public static void copyProperties(Object des,Object org,String[] mapping) throws IllegalAccessException, InvocationTargetException, Exception {
        int x = 0;
        if ((mapping == null && mapping.length == 0) || (org == null || des == null)) {
            StringBuffer sb = new StringBuffer();
            sb.append("There is a system exception when set the property value of the des object  from the org object.\n");
            sb.append("May be someone object is null!");
            throw new Exception(sb.toString());
        }
        while (x < mapping.length) {
            String str = mapping[x];
            
            if("*".equals(str)){
                copyProperties(des,org);
            }else{
                if(str.indexOf(":")==-1){
                    throw new Exception("The "+str+"'s change pattern is not match.");
                }
                if(str.indexOf(":") == 0) return;
                
                String desPropertyName = str.substring(0, str.indexOf(":"));
                
                String orgPropertyName = str.substring(str.indexOf(":")+1);
                if (desPropertyName!=null && orgPropertyName!=null) {
                    //Object value = PropertyUtils.getProperty(org, orgPropertyName.trim());
                	Object value = MyBeanUtils.getProperty(org, orgPropertyName.trim()); 
                	if (value != null ) {
                        //PropertyUtils.setProperty(des, desPropertyName, value);
                		MyBeanUtils.setProperty(des, desPropertyName, value);
                    }
                }
            }
            x++;
        }
    }
}

class MyBeanUtils 
{
	// ��HashMap�������ݵ�destObj�����С�
	public static Object populate(Object destObj, HashMap srcMap) throws IllegalAccessException, InvocationTargetException {
		try{
			PropertyDescriptor[] destFields  = getPropertyDescriptors(destObj); 
			for (int i = 0; i < destFields.length; i++){
				String name = destFields[i].getName();
				Object value = srcMap.get(name);
				if (value != null){
					Method writeMethod = destFields[i].getWriteMethod();
					writeMethod.invoke(destObj, new Object[]{value});
				}
			}
			return destObj;
		}catch(IllegalAccessException ille){
			throw ille;
		}catch(InvocationTargetException inve){
			throw inve;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new IllegalAccessException();
		}
	}
	
	// ��ȡһ�����Ե�ȡֵ	
	public static Object getProperty(Object object, String propertyName) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		try{
			PropertyDescriptor[] fields  = getPropertyDescriptors(object); 
			for (int i = 0; i < fields.length; i++){
				if (fields[i].getName().equals(propertyName)){
					Method readMethod = fields[i].getReadMethod();
					return readMethod.invoke(object, new Object[0]);
				}
			}
			return null;
		}catch(IllegalArgumentException ille){
			throw ille;
		}catch(IllegalAccessException illae){
			throw illae;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	// ����һ�����Ե�ȡֵ
	public static Object setProperty(Object object, String propertyName, Object value) throws IllegalArgumentException,
		IllegalAccessException, InvocationTargetException {
		try{
			PropertyDescriptor[] fields  = getPropertyDescriptors(object); 
			for (int i = 0; i < fields.length; i++){
				if (fields[i].getName().equals(propertyName)){
					Method writeMothod = fields[i].getWriteMethod();
					return writeMothod.invoke(object, new Object[]{value});
				}
			}
			return null;
		}catch(IllegalArgumentException ille){
			throw ille;
		}catch(IllegalAccessException illae){
			throw illae;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	/**
	 * ��ȡbean������Ϣ
	 */
	private static PropertyDescriptor[] getPropertyDescriptors(Object obj) throws Exception
	{
		try{
			BeanInfo beanInfo  = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			return propertyDescriptors;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	// ��һ�����󿽱����ݵ�����һ�����
	public static Object copyProperties(Object dest, Object src) throws IllegalAccessException, InvocationTargetException{
		try{
			PropertyDescriptor[] srcFields  = getPropertyDescriptors(src); 
			PropertyDescriptor[] destFields  = getPropertyDescriptors(dest); 
			
			for (int i = 0; i < srcFields.length; i++){
				String srcName = srcFields[i].getName();
				for (int j = 0; j < destFields.length; j++){
					String destName = destFields[j].getName();
					if (srcName.equals(destName) && !"class".equals(srcName)){
						Method readMethod = srcFields[i].getReadMethod();
						if (readMethod == null ) continue;
						Method writeMethod = destFields[j].getWriteMethod();
						if (writeMethod == null) continue;
						Object value = readMethod.invoke(src, new Object[0]);
						writeMethod.invoke(dest, new Object[]{value});
					}
				}
			}
			return dest;
		}catch(IllegalAccessException ex1){
			throw ex1;
		}catch(InvocationTargetException ex2){
			throw ex2;
		}catch(Exception ex3){
			ex3.printStackTrace();
			throw new IllegalAccessException();
		}
	}
}
