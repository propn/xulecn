package com.powerise.ibss.util;


/**
����̳�java.util.HashMap�࣬������get������
 */
public class HashArray extends java.util.HashMap {
   java.util.HashMap ha;
   
   /**
   ͨ��HashMap�����ʵ������
   @param ha
    */
   public HashArray(java.util.HashMap ha) {
      this.ha =ha;    
   }
   
   /**
   ����java.util.HashMap �� 
   get(Object)��������get(Object)���صĽ����ΪNULL���򷵻�gett(Object)��ֵ����ΪNU
   LL�򷵻ؿ��ַ���
   @param o - HashMap�ļ�ֵ
   @return Object
    */
   public Object get(Object o) {
      return get(o, "");    
   }
   
   /**
   ����java.util.HashMap �� 
   get(Object)��������get(Object)���صĽ����ΪNULL���򷵻�get(Object)��ֵ����get(O
   bject)���ص����ݼ�Ϊ���ݿ���е������ֶΣ���ת����ͳһ��ʽ��������ΪNULL�򷵻�ָ
   ����ȱʡ�滻����
   @param o - HashMap��ֵ
   @param d - ȱʡ�滻ֵ
   @return Object
    */
   public Object get(Object o, Object d) {
      if(ha.get(o) ==null)
         return d;
      else{
         //if(DateUtil.isDate(ha.get(o).toString(),"yyyy-MM-dd hh:mm:ss")){
            //�ж��Ƿ���ѡȡ���ݿ��������ֶ�
         //   return ha.get(o).toString().substring(0, ha.get(o).toString().length()-2);
         //}else{
            return ha.get(o);
         //}
      
      }    
   }
}
