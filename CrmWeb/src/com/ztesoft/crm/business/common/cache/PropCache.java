package com.ztesoft.crm.business.common.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 销售品属性取值缓存器
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
		
		//对下拉类型进行缓存
		
		dropItemTypes=Arrays.asList(new String[]{"1","3","U","O","o","S","s"});
		
		//密码
		passwordItemTypes=Arrays.asList(new String[]{"2"});
		
		//日期型
		
		dateItemTypes=Arrays.asList(new String[]{"5"});
		
		//选择型的
		
		popItemTypes=Arrays.asList(new String[]{"6","7","9","P","p"});
		
		
		//初始化的时候就加载数据到缓存中
	//	loadData();	
	}
	/**
	Value	Label
	98A	单选下拉框
	98B	多选下拉框
	98C	文本输入
	98D	日期控件
	98E	密码输入
	98F	邮件地址输入
	98G	IP输入
	98H	身份证输入
	98I	整数输入
	98J	浮点数输入
	98K	多行文本输入
	98L	本地网选择控件
	98M	弹出页面选择控件
	**/
	
	public final static String dropDown="98A";//下拉框
	
	public final static String password="98E";//密码
	
	public final static String date="98D";//日期
	
	public final static String pop="98M";//弹出选择
	
	public final static String text="98C";//普通输入型
	
	
	

	public static String switchType(String type){
		//下拉型的
		if(dropItemTypes.contains(type))
			return dropDown;
		//密码型
		if(passwordItemTypes.contains(type))
			return password;
		//日期型的
		if(dateItemTypes.contains(type))
			return date;
		//弹出界面
		if(popItemTypes.contains(type))
			return pop;
		
		return text;
		
	}
	/**
	 * 通过属性编码获取，属性取值列表 
	 **/
	public static ArrayList getPropByCode(String propCode){
		
		return (ArrayList) attrCache.get(propCode);
		
	}
	
/*	*//**
	 * 通过属性编码获取，属性取值,拼串列表
	 **//*
	public static String getSplitString(String propCode,String splitKey){
		
		String valueKey=propCode+valueMapKey;//缓存值的关键字
		String descKey=propCode+descMapKey;//缓存值描述信息的关键字
		String valueSplitString="";//值串
		String descSplitString="";//描述串
		if(attrCache.keySet().contains(splitKey)){
			//从缓存中取数据
			return String.valueOf(attrCache.get(splitKey));
		}
		List list=(ArrayList) attrCache.get(propCode);
		
		//如果缓存中数据为空，则把空串放置在缓存中
		//if(list==null||list.isEmpty()){
			attrCache.put(valueKey, "");
			attrCache.put(descKey, "");
			return "";
	//	}
		//拼凑SPLIT串
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
	
//从数据库中查询附属产品属性取值数据，并缓存到界面中
/*	public static void loadData(){

		DcPublicDupDAO dao=new DcPublicDupDAOImpl();	
		List list=dao.findByCond(" stype=? ", new String[]{"14"});//只缓存附属产品的数据	
		List mapKeys=new ArrayList();
		//一层循环进行分组操作
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
