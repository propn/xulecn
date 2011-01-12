/**
 * 
 */
package com.ztesoft.common.dao.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator; 

/**
 * @time 2008-09-02
 * @author AiTanaka
 * @descript dao通用实现类,拼凑sql语句用
 */
public class AllServiceDaoImpl {
	private String strSQL="";//执行sql的临时变量
	private HashMap mapExeSql;
	/**
	 * @time 2008-09-02
	 * @author AiTanaka
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws OaException 
	 * @throws FileNotFoundException 
	 * @descript dao通用实现类,拼凑sql语句用
	 * @descript 传入参数说明:
	 * mainSql:如果这个参数不为空,那么就以这个参数作为sql的常量,代替原来定义的字符常量,例如'CRM_ISMP_SELECT'等的常量
	 * vo:这个参数是sql语句的主体,对应select操作的where字段,insert操作的values字段,delete操作的where字段,update操作的set字段
	 * voValue:这个参数是sql语句的主体字段对应的具体的值,对应select操作的where字段值,insert操作的values字段值,delete操作的where字段值,update操作的set字段值
	 * voValueType:这个参数是sql语句的主体字段的数据类型(所有?的类型)
	 * updateWhereCond:这个参数只有做update操作的时候才有意义,对应sql语句的update操作的where字段
	 */
	public HashMap getSQL(String mainSql,LinkedHashMap inputVo,String recType,LinkedHashMap inputUpdateWhereCond,HashMap manualVoType,HashMap manualVoName,String sqlType) throws Exception
	   {
		   ArrayList voValue=new ArrayList();
		   ArrayList voValueType=new ArrayList();
		   //重命名vo,为了和数据库字段保持一致,所以重命名
		   LinkedHashMap vo=new LinkedHashMap();
		   vo=renameVo(recType,inputVo,manualVoName);//主字段重命名
		   LinkedHashMap updateWhereCond=new LinkedHashMap();//update的where条件也需要重命名
		   updateWhereCond=renameVo(recType,inputUpdateWhereCond,manualVoName);
		   
		   manualVoType=renameVo(recType,manualVoType,manualVoName);
		   HashMap allVo=new HashMap();
		   String strInsertParam="";

		   //获取所有的返回值
		   strSQL=GetBaseServiceSQL.getMainSql(mainSql, recType,sqlType);
		   allVo=getSqlObject(mainSql,vo,recType,updateWhereCond,manualVoType,sqlType);
		   strInsertParam=(String)allVo.get("strInsertParam");
		   voValue=(ArrayList)allVo.get("voValue");
		   voValueType=(ArrayList)allVo.get("voValueType");
			 
		   if (strInsertParam!=null && !strInsertParam.equals("") && (mainSql!=null && mainSql.equals("")))
		   {
			   strSQL=strSQL+strInsertParam;
		   }
		   mapExeSql=new HashMap();
		   mapExeSql.put("strSQL", strSQL);
		   mapExeSql.put("voValue", voValue);
		   mapExeSql.put("voValueType", voValueType);
	   return mapExeSql;
	}
	
	private HashMap getSqlObject(String mainSql,LinkedHashMap vo,String recType,LinkedHashMap updateWhereCond,HashMap manualVoType,String sqlType) throws Exception
	{
		HashMap allVo=new HashMap();
		if(manualVoType==null || manualVoType.isEmpty())
			manualVoType=GetBaseServiceSQL.queryDataTypeMapToList(recType);
		
		if(mainSql!=null && !mainSql.equals("")){//传入sql和对应的参数值时
			allVo=joinMainSqlParam(vo,manualVoType);
		}else if (sqlType.equals("select")){//单表select
			allVo=joinSelectParam(vo,manualVoType);
		}else if(sqlType.equals("insert")){//单表insert
			allVo=joinInsertParam(vo,manualVoType);
		}
		else if(sqlType.equals("update")){//单表update
			allVo=joinUpdateParam(vo,manualVoType,updateWhereCond);
			//特殊判断，如果是update语句，但是一个条件都没有，那么报错
			ArrayList voValue=(ArrayList)allVo.get("voValue");
			if(voValue==null || voValue.isEmpty()){
				throw new Exception("update语句没有条件");
			}
		}
		else if(sqlType.equals("delete")){//单表delete
			allVo=joinDeleteParam(vo,manualVoType);
			//特殊判断，如果是delete语句，但是一个条件都没有，那么报错
			ArrayList voValue=(ArrayList)allVo.get("voValue");
			if(voValue==null || voValue.isEmpty()){
				throw new Exception("delete语句没有条件");
			}
		}
		return allVo;
	}
	private HashMap joinMainSqlParam(LinkedHashMap map,HashMap voType)
	{
		HashMap allVo=new HashMap();
		ArrayList _voValue=new ArrayList();
		ArrayList _voValueType=new ArrayList();
		for (Iterator iter = (Iterator) map.entrySet().iterator(); iter.hasNext();) {
			 Map.Entry entry=(Map.Entry)iter.next();
			 String key = (String)entry.getKey();
		     String val = (String)entry.getValue();
		     if (val==null)
			 {
		    	 val="";
			 }
			 //设置对应参数的值
		     _voValue.add(val);
			 //设置对应参数的值类型
			 String columnDataType="string"; 
			 if (voType.containsKey(key)){
				 columnDataType=(String)voType.get(key);
				 if(columnDataType==null || columnDataType.equals(""))
					 columnDataType="string"; 
			 }
			 _voValueType.add(columnDataType);
		 }
		allVo.put("strInsertParam", " ");
		allVo.put("voValue",_voValue);
		allVo.put("voValueType",_voValueType);
		return allVo;
	}
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript 把得到的用,号或者and分割符的语句组成正确的sql,这个是对insert操作
	 * 参数说明:
	 * map:sql主体的字段,对应select操作的where字段,insert操作的values字段,delete操作的where字段,update操作的set字段
	 * * strValue:返回字段,用于把map字段的值拼装成and col1=?这样的形式(insert语句的values前的字段拼写)
	 * strValues:返回字段,用于把map字段的值拼装成and col1=?这样的形式(insert语句的values后的字段拼写)
	 * mapValue:返回字段,用于把map字段的值按拼装的顺序拼装成一个ArrayList
	 * mapType:返回字段,用于把map字段的值根据voType这个hashmap找到对应字段的数据类型按拼装的顺序拼装成一个ArrayList
	 * voType:对应map字段的数据类型,通过字段名查找,其实voType里面放的都是date类型的,后面的程序会判断如果找不到就默认用string类型
	 */
	private HashMap joinInsertParam(LinkedHashMap map,HashMap voType)
	{
		String strValues="";
		String strValue="";
		HashMap tmpHashMap=loadHashMapToInsertString(map,voType);
		
		strValue=(String)tmpHashMap.get("strValue");
		strValues=(String)tmpHashMap.get("strValues");
		String strInsertParam="";
		if (!strValues.equals("") && !strValue.equals(""))
		{
			strValue="("+((String)strValue).substring(0, ((String)strValue).length()-2) +") values";
			strValues="("+((String)strValues).substring(0, ((String)strValues).length()-2) +")";
			strInsertParam=strValue+strValues;
		}
		
		HashMap AllVo=new HashMap();
		AllVo.put("strInsertParam", strInsertParam);//执行的sql语句
		AllVo.put("voValue", tmpHashMap.get("voValue"));//sql语句里?的值
		AllVo.put("voValueType", tmpHashMap.get("voValueType"));//sql语句里?的值类型
		return AllVo;
	}
	
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript 把得到的用,号或者and分割符的语句组成正确的sql,这个是对update操作
	 * 参数说明:
	 * map:sql主体的字段,对应select操作的where字段,insert操作的values字段,delete操作的where字段,update操作的set字段
	 * strValues:返回字段,用于把map字段的值拼装成and col1=?这样的形式
	 * mapValue:返回字段,用于把map字段的值按拼装的顺序拼装成一个ArrayList
	 * mapType:返回字段,用于把map字段的值根据voType这个hashmap找到对应字段的数据类型按拼装的顺序拼装成一个ArrayList
	 * voType:对应map字段的数据类型,通过字段名查找,其实voType里面放的都是date类型的,后面的程序会判断如果找不到就默认用string类型
	 * whereCond:对应update语句的where字段的值
	 */
	private HashMap joinUpdateParam(LinkedHashMap map,HashMap voType,LinkedHashMap whereCond)
	{
		String strValues="";
		String strValue="";
		HashMap tmpHashMap=loadHashMapToUpdateString(map,voType,whereCond);
		strValue=(String)tmpHashMap.get("strValue");
		strValues=(String)tmpHashMap.get("strValues");
		String strInsertParam="";
		if (!strValues.equals(""))
		{
			strValue=" set "+strValue.substring(0, strValue.length()-2) +" where 1=1 ";
			strValues=strValues.substring(0, strValues.length()-1);
			strInsertParam=strValue+strValues;
		}
		HashMap AllVo=new HashMap();
		AllVo.put("strInsertParam", strInsertParam);//执行的sql语句
		AllVo.put("voValue", tmpHashMap.get("voValue"));//sql语句里?的值
		AllVo.put("voValueType", tmpHashMap.get("voValueType"));//sql语句里?的值类型
		return AllVo;
	}
	
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript 把得到的用,号或者and分割符的语句组成正确的sql,这个是对select操作
	 * 参数说明:
	 * map:sql主体的字段,对应select操作的where字段,insert操作的values字段,delete操作的where字段,update操作的set字段
	 * strValues:返回字段,用于把map字段的值拼装成and col1=?这样的形式
	 * mapValue:返回字段,用于把map字段的值按拼装的顺序拼装成一个ArrayList
	 * mapType:返回字段,用于把map字段的值根据voType这个hashmap找到对应字段的数据类型按拼装的顺序拼装成一个ArrayList
	 * voType:对应map字段的数据类型,通过字段名查找,其实voType里面放的都是date类型的,后面的程序会判断如果找不到就默认用string类型
	 */
	private HashMap joinSelectParam(LinkedHashMap map,HashMap voType)
	{
		return loadHashMapToSelectString(map,voType,"=");
	}
	
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript 把得到的用,号或者and分割符的语句组成正确的sql,这个是对delete操作
	 * 参数说明:
	 * map:sql主体的字段,对应select操作的where字段,insert操作的values字段,delete操作的where字段,update操作的set字段
	 * strValues:返回字段,用于把map字段的值拼装成and col1=?这样的形式
	 * mapValue:返回字段,用于把map字段的值按拼装的顺序拼装成一个ArrayList
	 * mapType:返回字段,用于把map字段的值根据voType这个hashmap找到对应字段的数据类型按拼装的顺序拼装成一个ArrayList
	 * voType:对应map字段的数据类型,通过字段名查找,其实voType里面放的都是date类型的,后面的程序会判断如果找不到就默认用string类型
	 */
	private HashMap joinDeleteParam(LinkedHashMap map,HashMap voType)
	{
		return loadHashMapToDeleteString(map,voType);
	}
	
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript 把传入的hashmap转化成用,号或者and分割符的语句,这个是对insert操作
	 * 参数说明:
	 * map:sql主体的字段,对应select操作的where字段,insert操作的values字段,delete操作的where字段,update操作的set字段
	 * strValues:返回字段,用于把map字段的值拼装成and col1=?这样的形式(insert语句的values后的字段拼写)
	 * strValue:返回字段,用于把map字段的值拼装成and col1=?这样的形式(insert语句的values前的字段拼写)
	 * mapValue:返回字段,用于把map字段的值按拼装的顺序拼装成一个ArrayList
	 * mapType:返回字段,用于把map字段的值根据voType这个hashmap找到对应字段的数据类型按拼装的顺序拼装成一个ArrayList
	 * voType:对应map字段的数据类型,通过字段名查找,其实voType里面放的都是date类型的,后面的程序会判断如果找不到就默认用string类型
	 */
	private HashMap loadHashMapToInsertString(LinkedHashMap map,HashMap voType)
	{
		ArrayList mapValue=new ArrayList();
		ArrayList mapType=new ArrayList();
		HashMap lstReturn=new HashMap();
		String strValue="";
		String strValues="";
		for (Iterator iter = (Iterator) map.entrySet().iterator(); iter.hasNext();) {
			 Map.Entry entry=(Map.Entry)iter.next();
		     String key = (String)entry.getKey();
		     String val = (String)entry.getValue();
		     if (val==null)
			 {
		    	 val="";
			 }
	    	 //设置sql语句的参数 
		     strValue=strValue+" "+key.toString()+", ";
		     strValues=strValues+" ?, ";
			 //设置对应参数的值
			 mapValue.add(val);
			 //设置对应参数的值类型
			 String columnDataType="string"; 
			 if (voType.containsKey(key)){
				 columnDataType=(String)voType.get(key);
				 if(columnDataType==null || columnDataType.equals("")){
					 columnDataType="string"; 
				 }
			 }
			 mapType.add(columnDataType);
			 
		 }
		lstReturn.put("strValue", strValue);
		lstReturn.put("strValues", strValues);
		lstReturn.put("voValue", mapValue);
		lstReturn.put("voValueType", mapType);
		return lstReturn;
	}
	
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript 把传入的hashmap转化成用,号或者and分割符的语句,这个是对update操作
	 * 参数说明:
	 * map:sql主体的字段,对应select操作的where字段,insert操作的values字段,delete操作的where字段,update操作的set字段
	 * strValues:返回字段,用于把map字段的值拼装成 col1=?这样的形式(update语句的where后的字段拼写)
	 * strValue:返回字段,用于把map字段的值拼装成and col1=?这样的形式(insert语句的where前的字段拼写)
	 * mapValue:返回字段,用于把map字段的值按拼装的顺序拼装成一个ArrayList
	 * mapType:返回字段,用于把map字段的值根据voType这个hashmap找到对应字段的数据类型按拼装的顺序拼装成一个ArrayList
	 * voType:对应map字段的数据类型,通过字段名查找,其实voType里面放的都是date类型的,后面的程序会判断如果找不到就默认用string类型
	 */
	private HashMap loadHashMapToUpdateString(LinkedHashMap map,HashMap voType,LinkedHashMap whereCond)
	{
		ArrayList mapValue=new ArrayList();
		ArrayList mapType=new ArrayList();
		HashMap lstReturn=new HashMap();
		String strValue="";
		String strValues="";
		//设置set后面的字段
		for (Iterator iter = (Iterator) map.entrySet().iterator(); iter.hasNext();) {
			 Map.Entry entry=(Map.Entry)iter.next();
			 String key = (String)entry.getKey();
		     String val = (String)entry.getValue();
		     if (val==null)
			 {
		    	 val="";
			 }
	    	 //设置sql语句的参数 
		     strValue=strValue+" "+key.toString()+"=?, ";
			 //设置对应参数的值
			 mapValue.add(val);
			 //设置对应参数的值类型
			 String columnDataType="string"; 
			 if (voType.containsKey(key)){
				 columnDataType=(String)voType.get(key);
				 if(columnDataType==null || columnDataType.equals("")){
					 columnDataType="string"; 
				 }
			 }
			 mapType.add(columnDataType);
		 }
		
		//设置where 后面的字段
		for (Iterator iter = (Iterator) whereCond.entrySet().iterator(); iter.hasNext();) {
			 Map.Entry entry=(Map.Entry)iter.next();
			 String key = (String)entry.getKey();
		     String val = (String)entry.getValue();
		     if (val==null)
			 {
		    	 val="";
			 }
	    	 //设置sql语句的参数 
		     strValues=strValues+" and "+key.toString()+"=? ";
			 //设置对应参数的值
			 mapValue.add(val);
			 //设置对应参数的值类型
			 String columnDataType="string"; 
			 if (voType.containsKey(key)){
				 columnDataType=(String)voType.get(key);
				 if(columnDataType==null || columnDataType.equals("")){
					 columnDataType="string"; 
				 }
			 }
			 mapType.add(columnDataType);
		 }
		lstReturn.put("strValue", strValue);
		lstReturn.put("strValues", strValues);
		lstReturn.put("voValue", mapValue);
		lstReturn.put("voValueType", mapType);
		return lstReturn;
	}
	
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript 把传入的hashmap转化成用,号或者and分割符的语句,这个是对delete操作
	 * 参数说明:
	 * map:sql主体的字段,对应select操作的where字段,insert操作的values字段,delete操作的where字段,update操作的set字段
	 * strValues:返回字段,用于把map字段的值拼装成 col1=?这样的形式(delete语句的where后的字段拼写)
	 * mapValue:返回字段,用于把map字段的值按拼装的顺序拼装成一个ArrayList
	 * mapType:返回字段,用于把map字段的值根据voType这个hashmap找到对应字段的数据类型按拼装的顺序拼装成一个ArrayList
	 * voType:对应map字段的数据类型,通过字段名查找,其实voType里面放的都是date类型的,后面的程序会判断如果找不到就默认用string类型
	 */
	private HashMap loadHashMapToDeleteString(LinkedHashMap map,HashMap voType)
	{
		ArrayList mapValue=new ArrayList();
		ArrayList mapType=new ArrayList();
		HashMap lstReturn=new HashMap();
		String strValues="";
		for (Iterator iter = (Iterator) map.entrySet().iterator(); iter.hasNext();) {
			 Map.Entry entry=(Map.Entry)iter.next();
			 String key = (String)entry.getKey();
		     String val = (String)entry.getValue();
		     if (val==null)
			 {
		    	 val="";
			 }
	    	 //设置sql语句的参数 
		     strValues=strValues+" and "+key.toString()+"=? ";
			 //设置对应参数的值
			 mapValue.add(val);
			 //设置对应参数的值类型
			 String columnDataType="string"; 
			 if (voType.containsKey(key)){
				 columnDataType=(String)voType.get(key);
				 if(columnDataType==null || columnDataType.equals("")){
					 columnDataType="string"; 
				 }
			 }
			 mapType.add(columnDataType);
		 }

		lstReturn.put("strInsertParam", strValues);
		lstReturn.put("voValue", mapValue);
		lstReturn.put("voValueType", mapType);
		return lstReturn;
	}
	
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript 把传入的hashmap转化成用,号或者and分割符的语句,这个是对select操作
	 * 参数说明:
	 * map:sql主体的字段,对应select操作的where字段,insert操作的values字段,delete操作的where字段,update操作的set字段
	 * strValues:返回字段,用于把map字段的值拼装成 col1=?这样的形式(delete语句的where后的字段拼写)
	 * mapValue:返回字段,用于把map字段的值按拼装的顺序拼装成一个ArrayList
	 * mapType:返回字段,用于把map字段的值根据voType这个hashmap找到对应字段的数据类型按拼装的顺序拼装成一个ArrayList
	 * voType:对应map字段的数据类型,通过字段名查找,其实voType里面放的都是date类型的,后面的程序会判断如果找不到就默认用string类型
	 */
	private HashMap loadHashMapToSelectString(LinkedHashMap map,HashMap voType,String WhereType)
	{
		HashMap lstReturn=new HashMap();
		ArrayList mapValue =new ArrayList();
		ArrayList mapType=new ArrayList();
		String strValues="";
		for (Iterator iter = (Iterator) map.entrySet().iterator(); iter.hasNext();) {
			 Map.Entry entry=(Map.Entry)iter.next();
			 String key = (String)entry.getKey();
		     String val = (String)entry.getValue();
		     if (val==null)
			 {
		    	 val="";
			 }
		     if (WhereType.equals("="))
		     {
			 strValues=strValues+" and "+key.toString()+"=? ";
		     }else if (WhereType.equals("like"))
		     {
				 strValues=strValues+" and "+key.toString()+" like '%' || ?  || '%' ";
			 }else if (WhereType.equals("=null"))
		     {
				 strValues=strValues+" and ("+key.toString()+"=? or "+key.toString()+" is null) ";
			 }
		     
			 //设置对应参数的值
			 mapValue.add(val);
			 //设置对应参数的值类型
			 String columnDataType="string"; 
			 if (voType.containsKey(key)){
				 columnDataType=(String)voType.get(key);
				 if(columnDataType==null || columnDataType.equals("")){
					 columnDataType="string"; 
				 }
			 }
			 mapType.add(columnDataType);
		 }
		lstReturn.put("strInsertParam", strValues);
		lstReturn.put("voValue", mapValue);
		lstReturn.put("voValueType", mapType);

		return lstReturn;
	}

	
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript 数据库字段映射界面dataset字段,主要用于查询的时候如果界面需要的字段是ismp_code_type,
	 * 但数据库字段是大写的ISMP_CODE_TYPE,那么就需要转换,数据库字段在dao那边默认转换成大写的
	 */
	public static LinkedHashMap renameVo(String recType,LinkedHashMap vo,HashMap manualVoName)
	{
		HashMap tmpMap=new HashMap();
		LinkedHashMap newReturnVo=new LinkedHashMap();
		if(vo==null || vo.isEmpty())return newReturnVo;
		tmpMap=null;//获取手工编写的字段映射,如果有,就用，如果没有,就按照自动命名规则重命名
		for (Iterator iter = (Iterator) vo.entrySet().iterator(); iter.hasNext();) {
			 Map.Entry entry=(Map.Entry)iter.next();
		     Object key = entry.getKey();
		     Object val = entry.getValue();
		     String newKey="";
		     if (tmpMap==null)
		     {
		    	 newKey=webFieldToDataField((String)key);
		     }
		     else
		     {
			     if (tmpMap.containsKey((String)key)) 
			     {newKey=(String)tmpMap.get((String)key);}
			     else
			     {newKey=webFieldToDataField((String)key);}
		     }
		     newReturnVo.put(newKey, val);
		 }
		return newReturnVo;
	}
	
	public static HashMap renameVo(String recType,HashMap vo,HashMap manualVoName)
	{
		HashMap tmpMap=new HashMap();
		HashMap newReturnVo=new LinkedHashMap();
		tmpMap=manualVoName;//获取手工编写的字段映射,如果有,就用，如果没有,就按照自动命名规则重命名
		for (Iterator iter = (Iterator) vo.entrySet().iterator(); iter.hasNext();) {
			 Map.Entry entry=(Map.Entry)iter.next();
		     Object key = entry.getKey();
		     Object val = entry.getValue();
		     String newKey="";
		     if (tmpMap==null)
		     {
		    	 newKey=webFieldToDataField((String)key);
		     }
		     else
		     {
			     if (tmpMap.containsKey((String)key)) 
			     {newKey=(String)tmpMap.get((String)key);}
			     else
			     {newKey=webFieldToDataField((String)key);}
		     }
		     newReturnVo.put(newKey, val);
		 }
		return newReturnVo;
	}
	
	public static LinkedHashMap dataFieldRenameVo(String recType,LinkedHashMap vo,HashMap manualVoName)
	{
		HashMap tmpMap=new HashMap();
		LinkedHashMap newReturnVo=new LinkedHashMap();
		tmpMap=manualVoName;//获取手工编写的字段映射,如果有,就用，如果没有,就按照自动命名规则重命名
		for (Iterator iter = (Iterator) vo.entrySet().iterator(); iter.hasNext();) {
			 Map.Entry entry=(Map.Entry)iter.next();
		     Object key = entry.getKey();
		     Object val = entry.getValue();
		     String newKey="";
		     if (manualVoName==null)
		     {
		    	 newKey=dataFieldToWebField((String)key);
		     }
		     else
		     {
			     if (tmpMap.containsKey((String)key)) 
			     {newKey=(String)tmpMap.get((String)key);}
			     else
			     {newKey=dataFieldToWebField((String)key);}
		     }
		     newReturnVo.put(newKey, val);
		 }
		return newReturnVo;
	}

	//把productOfferId转成这样的格式:product_offer_id
	public static String webFieldToDataField(String field)
	{
		String strTmpReturnField="";
		String strTmpField=field;
		String strTmp="";
		char []strArrayList=field.toCharArray();
		for (int i=0;i<strArrayList.length;i++)
		{
			strTmp=String.valueOf(strArrayList[i]);
			int kk="ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(strTmp);
			if (kk>=0)//如果是大写字母
			{
				if (i==0)
				{strTmpReturnField=strTmpReturnField+strTmp.toLowerCase();}
				else
				{strTmpReturnField=strTmpReturnField+"_"+strTmp.toLowerCase();}
			}
			else
			{strTmpReturnField=strTmpReturnField+strTmp.toLowerCase();}
		}
		return strTmpReturnField;
	}
		
	//把product_offer_id转成这样的格式:productOfferId
	public static String dataFieldToWebField(String field)
	{
		String strTmpField=field.toLowerCase();
		String strTmpReturnField="";
		String strTmp1="";
		String strTmp2="";
		for (int i=0;i<strTmpField.length();i++)
		{
			
			strTmp1=strTmpField.substring(i, i+1);
			if (i>=(strTmpField.length()-1))
			{
				strTmp2="";
			}
			else
			{
				strTmp2=strTmpField.substring(i+1, i+2);
			}
			
			if (strTmp1.equals("_"))
			{
				strTmp2=strTmp2.toUpperCase();
				i=i+1;//跳过"_"这个符号
				strTmpReturnField=strTmpReturnField+strTmp2;
			}
			else
			{
				strTmpReturnField=strTmpReturnField+strTmp1;
			}
		}
		return strTmpReturnField;
	}
}
