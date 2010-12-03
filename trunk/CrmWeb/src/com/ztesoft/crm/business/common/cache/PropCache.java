package com.ztesoft.crm.business.common.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * ����Ʒ����ȡֵ������
 **/
public final class PropCache {
 
	
	public  static List dropItemTypes=new ArrayList();
	
	public 	static List passwordItemTypes=new ArrayList();
	
	public 	static List dateItemTypes=new ArrayList();
	
	public 	static List popItemTypes=new ArrayList();
	
	
	private static Map attrCache = Collections.synchronizedMap(new HashMap());
	
	private final static String split="/";
	
	public final static String valueMapKey="_value_";
	
	public final static String descMapKey="_desc_";
	
	static{
		
		//���������ͽ��л���
		
		dropItemTypes=Arrays.asList(new String[]{"1","3","U","O","o","S","s"});
		
		//����
		passwordItemTypes=Arrays.asList(new String[]{"2"});
		
		//������
		
		dateItemTypes=Arrays.asList(new String[]{"5"});
		
		//ѡ���͵�
		
		popItemTypes=Arrays.asList(new String[]{"6","7","9","P","p"});
		
		
		//��ʼ����ʱ��ͼ������ݵ�������
	//	loadData();	
	}
	/**
	Value	Label
	98A	��ѡ������
	98B	��ѡ������
	98C	�ı�����
	98D	���ڿؼ�
	98E	��������
	98F	�ʼ���ַ����
	98G	IP����
	98H	���֤����
	98I	��������
	98J	����������
	98K	�����ı�����
	98L	������ѡ��ؼ�
	98M	����ҳ��ѡ��ؼ�
	**/
	
	public final static String dropDown="98A";//������
	
	public final static String password="98E";//����
	
	public final static String date="98D";//����
	
	public final static String pop="98M";//����ѡ��
	
	public final static String text="98C";//��ͨ������
	
	
	

	public static String switchType(String type){
		//�����͵�
		if(dropItemTypes.contains(type))
			return dropDown;
		//������
		if(passwordItemTypes.contains(type))
			return password;
		//�����͵�
		if(dateItemTypes.contains(type))
			return date;
		//��������
		if(popItemTypes.contains(type))
			return pop;
		
		return text;
		
	}
	/**
	 * ͨ�����Ա����ȡ������ȡֵ�б� 
	 **/
	public static ArrayList getPropByCode(String propCode){
		
		return (ArrayList) attrCache.get(propCode);
		
	}
	
/*	*//**
	 * ͨ�����Ա����ȡ������ȡֵ,ƴ���б�
	 **//*
	public static String getSplitString(String propCode,String splitKey){
		
		String valueKey=propCode+valueMapKey;//����ֵ�Ĺؼ���
		String descKey=propCode+descMapKey;//����ֵ������Ϣ�Ĺؼ���
		String valueSplitString="";//ֵ��
		String descSplitString="";//������
		if(attrCache.keySet().contains(splitKey)){
			//�ӻ�����ȡ����
			return String.valueOf(attrCache.get(splitKey));
		}
		List list=(ArrayList) attrCache.get(propCode);
		
		//�������������Ϊ�գ���ѿմ������ڻ�����
		//if(list==null||list.isEmpty()){
			attrCache.put(valueKey, "");
			attrCache.put(descKey, "");
			return "";
	//	}
		//ƴ��SPLIT��
		Iterator listIt=list.iterator();
		while(listIt.hasNext()){
			DcPublicDupVO vo=(DcPublicDupVO)listIt.next();
			valueSplitString+=vo.getPkeyb();
			descSplitString+=vo.getPname();
			if(listIt.hasNext()){
				valueSplitString+=split;
				descSplitString+=split;
			}
			
		}
		attrCache.put(valueKey, valueSplitString);
		attrCache.put(descKey, descSplitString);
		
		return String.valueOf(attrCache.get(splitKey));
	
	}*/
	
//�����ݿ��в�ѯ������Ʒ����ȡֵ���ݣ������浽������
/*	public static void loadData(){

		DcPublicDupDAO dao=new DcPublicDupDAOImpl();	
		List list=dao.findByCond(" stype=? ", new String[]{"14"});//ֻ���渽����Ʒ������	
		List mapKeys=new ArrayList();
		//һ��ѭ�����з������
		Iterator it=list.iterator();
		while(it.hasNext()){
			DcPublicDupVO vo=(DcPublicDupVO)it.next();
			String key=vo.getPkeya();
			if(!mapKeys.contains(key)){
				mapKeys.add(key);
				attrCache.put(key, new ArrayList());
			}
			((ArrayList)attrCache.get(key)).add(vo);
		}
		
		
		
	}*/
	
	
	
	
	
	
	
	
}
