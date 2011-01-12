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
 * @descript daoͨ��ʵ����,ƴ��sql�����
 */
public class AllServiceDaoImpl {
	private String strSQL="";//ִ��sql����ʱ����
	private HashMap mapExeSql;
	/**
	 * @time 2008-09-02
	 * @author AiTanaka
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws OaException 
	 * @throws FileNotFoundException 
	 * @descript daoͨ��ʵ����,ƴ��sql�����
	 * @descript �������˵��:
	 * mainSql:������������Ϊ��,��ô�������������Ϊsql�ĳ���,����ԭ��������ַ�����,����'CRM_ISMP_SELECT'�ȵĳ���
	 * vo:���������sql��������,��Ӧselect������where�ֶ�,insert������values�ֶ�,delete������where�ֶ�,update������set�ֶ�
	 * voValue:���������sql���������ֶζ�Ӧ�ľ����ֵ,��Ӧselect������where�ֶ�ֵ,insert������values�ֶ�ֵ,delete������where�ֶ�ֵ,update������set�ֶ�ֵ
	 * voValueType:���������sql���������ֶε���������(����?������)
	 * updateWhereCond:�������ֻ����update������ʱ���������,��Ӧsql����update������where�ֶ�
	 */
	public HashMap getSQL(String mainSql,LinkedHashMap inputVo,String recType,LinkedHashMap inputUpdateWhereCond,HashMap manualVoType,HashMap manualVoName,String sqlType) throws Exception
	   {
		   ArrayList voValue=new ArrayList();
		   ArrayList voValueType=new ArrayList();
		   //������vo,Ϊ�˺����ݿ��ֶα���һ��,����������
		   LinkedHashMap vo=new LinkedHashMap();
		   vo=renameVo(recType,inputVo,manualVoName);//���ֶ�������
		   LinkedHashMap updateWhereCond=new LinkedHashMap();//update��where����Ҳ��Ҫ������
		   updateWhereCond=renameVo(recType,inputUpdateWhereCond,manualVoName);
		   
		   manualVoType=renameVo(recType,manualVoType,manualVoName);
		   HashMap allVo=new HashMap();
		   String strInsertParam="";

		   //��ȡ���еķ���ֵ
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
		
		if(mainSql!=null && !mainSql.equals("")){//����sql�Ͷ�Ӧ�Ĳ���ֵʱ
			allVo=joinMainSqlParam(vo,manualVoType);
		}else if (sqlType.equals("select")){//����select
			allVo=joinSelectParam(vo,manualVoType);
		}else if(sqlType.equals("insert")){//����insert
			allVo=joinInsertParam(vo,manualVoType);
		}
		else if(sqlType.equals("update")){//����update
			allVo=joinUpdateParam(vo,manualVoType,updateWhereCond);
			//�����жϣ������update��䣬����һ��������û�У���ô����
			ArrayList voValue=(ArrayList)allVo.get("voValue");
			if(voValue==null || voValue.isEmpty()){
				throw new Exception("update���û������");
			}
		}
		else if(sqlType.equals("delete")){//����delete
			allVo=joinDeleteParam(vo,manualVoType);
			//�����жϣ������delete��䣬����һ��������û�У���ô����
			ArrayList voValue=(ArrayList)allVo.get("voValue");
			if(voValue==null || voValue.isEmpty()){
				throw new Exception("delete���û������");
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
			 //���ö�Ӧ������ֵ
		     _voValue.add(val);
			 //���ö�Ӧ������ֵ����
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
	 * @descript �ѵõ�����,�Ż���and�ָ������������ȷ��sql,����Ƕ�insert����
	 * ����˵��:
	 * map:sql������ֶ�,��Ӧselect������where�ֶ�,insert������values�ֶ�,delete������where�ֶ�,update������set�ֶ�
	 * * strValue:�����ֶ�,���ڰ�map�ֶε�ֵƴװ��and col1=?��������ʽ(insert����valuesǰ���ֶ�ƴд)
	 * strValues:�����ֶ�,���ڰ�map�ֶε�ֵƴװ��and col1=?��������ʽ(insert����values����ֶ�ƴд)
	 * mapValue:�����ֶ�,���ڰ�map�ֶε�ֵ��ƴװ��˳��ƴװ��һ��ArrayList
	 * mapType:�����ֶ�,���ڰ�map�ֶε�ֵ����voType���hashmap�ҵ���Ӧ�ֶε��������Ͱ�ƴװ��˳��ƴװ��һ��ArrayList
	 * voType:��Ӧmap�ֶε���������,ͨ���ֶ�������,��ʵvoType����ŵĶ���date���͵�,����ĳ�����ж�����Ҳ�����Ĭ����string����
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
		AllVo.put("strInsertParam", strInsertParam);//ִ�е�sql���
		AllVo.put("voValue", tmpHashMap.get("voValue"));//sql�����?��ֵ
		AllVo.put("voValueType", tmpHashMap.get("voValueType"));//sql�����?��ֵ����
		return AllVo;
	}
	
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript �ѵõ�����,�Ż���and�ָ������������ȷ��sql,����Ƕ�update����
	 * ����˵��:
	 * map:sql������ֶ�,��Ӧselect������where�ֶ�,insert������values�ֶ�,delete������where�ֶ�,update������set�ֶ�
	 * strValues:�����ֶ�,���ڰ�map�ֶε�ֵƴװ��and col1=?��������ʽ
	 * mapValue:�����ֶ�,���ڰ�map�ֶε�ֵ��ƴװ��˳��ƴװ��һ��ArrayList
	 * mapType:�����ֶ�,���ڰ�map�ֶε�ֵ����voType���hashmap�ҵ���Ӧ�ֶε��������Ͱ�ƴװ��˳��ƴװ��һ��ArrayList
	 * voType:��Ӧmap�ֶε���������,ͨ���ֶ�������,��ʵvoType����ŵĶ���date���͵�,����ĳ�����ж�����Ҳ�����Ĭ����string����
	 * whereCond:��Ӧupdate����where�ֶε�ֵ
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
		AllVo.put("strInsertParam", strInsertParam);//ִ�е�sql���
		AllVo.put("voValue", tmpHashMap.get("voValue"));//sql�����?��ֵ
		AllVo.put("voValueType", tmpHashMap.get("voValueType"));//sql�����?��ֵ����
		return AllVo;
	}
	
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript �ѵõ�����,�Ż���and�ָ������������ȷ��sql,����Ƕ�select����
	 * ����˵��:
	 * map:sql������ֶ�,��Ӧselect������where�ֶ�,insert������values�ֶ�,delete������where�ֶ�,update������set�ֶ�
	 * strValues:�����ֶ�,���ڰ�map�ֶε�ֵƴװ��and col1=?��������ʽ
	 * mapValue:�����ֶ�,���ڰ�map�ֶε�ֵ��ƴװ��˳��ƴװ��һ��ArrayList
	 * mapType:�����ֶ�,���ڰ�map�ֶε�ֵ����voType���hashmap�ҵ���Ӧ�ֶε��������Ͱ�ƴװ��˳��ƴװ��һ��ArrayList
	 * voType:��Ӧmap�ֶε���������,ͨ���ֶ�������,��ʵvoType����ŵĶ���date���͵�,����ĳ�����ж�����Ҳ�����Ĭ����string����
	 */
	private HashMap joinSelectParam(LinkedHashMap map,HashMap voType)
	{
		return loadHashMapToSelectString(map,voType,"=");
	}
	
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript �ѵõ�����,�Ż���and�ָ������������ȷ��sql,����Ƕ�delete����
	 * ����˵��:
	 * map:sql������ֶ�,��Ӧselect������where�ֶ�,insert������values�ֶ�,delete������where�ֶ�,update������set�ֶ�
	 * strValues:�����ֶ�,���ڰ�map�ֶε�ֵƴװ��and col1=?��������ʽ
	 * mapValue:�����ֶ�,���ڰ�map�ֶε�ֵ��ƴװ��˳��ƴװ��һ��ArrayList
	 * mapType:�����ֶ�,���ڰ�map�ֶε�ֵ����voType���hashmap�ҵ���Ӧ�ֶε��������Ͱ�ƴװ��˳��ƴװ��һ��ArrayList
	 * voType:��Ӧmap�ֶε���������,ͨ���ֶ�������,��ʵvoType����ŵĶ���date���͵�,����ĳ�����ж�����Ҳ�����Ĭ����string����
	 */
	private HashMap joinDeleteParam(LinkedHashMap map,HashMap voType)
	{
		return loadHashMapToDeleteString(map,voType);
	}
	
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @descript �Ѵ����hashmapת������,�Ż���and�ָ�������,����Ƕ�insert����
	 * ����˵��:
	 * map:sql������ֶ�,��Ӧselect������where�ֶ�,insert������values�ֶ�,delete������where�ֶ�,update������set�ֶ�
	 * strValues:�����ֶ�,���ڰ�map�ֶε�ֵƴװ��and col1=?��������ʽ(insert����values����ֶ�ƴд)
	 * strValue:�����ֶ�,���ڰ�map�ֶε�ֵƴװ��and col1=?��������ʽ(insert����valuesǰ���ֶ�ƴд)
	 * mapValue:�����ֶ�,���ڰ�map�ֶε�ֵ��ƴװ��˳��ƴװ��һ��ArrayList
	 * mapType:�����ֶ�,���ڰ�map�ֶε�ֵ����voType���hashmap�ҵ���Ӧ�ֶε��������Ͱ�ƴװ��˳��ƴװ��һ��ArrayList
	 * voType:��Ӧmap�ֶε���������,ͨ���ֶ�������,��ʵvoType����ŵĶ���date���͵�,����ĳ�����ж�����Ҳ�����Ĭ����string����
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
	    	 //����sql���Ĳ��� 
		     strValue=strValue+" "+key.toString()+", ";
		     strValues=strValues+" ?, ";
			 //���ö�Ӧ������ֵ
			 mapValue.add(val);
			 //���ö�Ӧ������ֵ����
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
	 * @descript �Ѵ����hashmapת������,�Ż���and�ָ�������,����Ƕ�update����
	 * ����˵��:
	 * map:sql������ֶ�,��Ӧselect������where�ֶ�,insert������values�ֶ�,delete������where�ֶ�,update������set�ֶ�
	 * strValues:�����ֶ�,���ڰ�map�ֶε�ֵƴװ�� col1=?��������ʽ(update����where����ֶ�ƴд)
	 * strValue:�����ֶ�,���ڰ�map�ֶε�ֵƴװ��and col1=?��������ʽ(insert����whereǰ���ֶ�ƴд)
	 * mapValue:�����ֶ�,���ڰ�map�ֶε�ֵ��ƴװ��˳��ƴװ��һ��ArrayList
	 * mapType:�����ֶ�,���ڰ�map�ֶε�ֵ����voType���hashmap�ҵ���Ӧ�ֶε��������Ͱ�ƴװ��˳��ƴװ��һ��ArrayList
	 * voType:��Ӧmap�ֶε���������,ͨ���ֶ�������,��ʵvoType����ŵĶ���date���͵�,����ĳ�����ж�����Ҳ�����Ĭ����string����
	 */
	private HashMap loadHashMapToUpdateString(LinkedHashMap map,HashMap voType,LinkedHashMap whereCond)
	{
		ArrayList mapValue=new ArrayList();
		ArrayList mapType=new ArrayList();
		HashMap lstReturn=new HashMap();
		String strValue="";
		String strValues="";
		//����set������ֶ�
		for (Iterator iter = (Iterator) map.entrySet().iterator(); iter.hasNext();) {
			 Map.Entry entry=(Map.Entry)iter.next();
			 String key = (String)entry.getKey();
		     String val = (String)entry.getValue();
		     if (val==null)
			 {
		    	 val="";
			 }
	    	 //����sql���Ĳ��� 
		     strValue=strValue+" "+key.toString()+"=?, ";
			 //���ö�Ӧ������ֵ
			 mapValue.add(val);
			 //���ö�Ӧ������ֵ����
			 String columnDataType="string"; 
			 if (voType.containsKey(key)){
				 columnDataType=(String)voType.get(key);
				 if(columnDataType==null || columnDataType.equals("")){
					 columnDataType="string"; 
				 }
			 }
			 mapType.add(columnDataType);
		 }
		
		//����where ������ֶ�
		for (Iterator iter = (Iterator) whereCond.entrySet().iterator(); iter.hasNext();) {
			 Map.Entry entry=(Map.Entry)iter.next();
			 String key = (String)entry.getKey();
		     String val = (String)entry.getValue();
		     if (val==null)
			 {
		    	 val="";
			 }
	    	 //����sql���Ĳ��� 
		     strValues=strValues+" and "+key.toString()+"=? ";
			 //���ö�Ӧ������ֵ
			 mapValue.add(val);
			 //���ö�Ӧ������ֵ����
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
	 * @descript �Ѵ����hashmapת������,�Ż���and�ָ�������,����Ƕ�delete����
	 * ����˵��:
	 * map:sql������ֶ�,��Ӧselect������where�ֶ�,insert������values�ֶ�,delete������where�ֶ�,update������set�ֶ�
	 * strValues:�����ֶ�,���ڰ�map�ֶε�ֵƴװ�� col1=?��������ʽ(delete����where����ֶ�ƴд)
	 * mapValue:�����ֶ�,���ڰ�map�ֶε�ֵ��ƴװ��˳��ƴװ��һ��ArrayList
	 * mapType:�����ֶ�,���ڰ�map�ֶε�ֵ����voType���hashmap�ҵ���Ӧ�ֶε��������Ͱ�ƴװ��˳��ƴװ��һ��ArrayList
	 * voType:��Ӧmap�ֶε���������,ͨ���ֶ�������,��ʵvoType����ŵĶ���date���͵�,����ĳ�����ж�����Ҳ�����Ĭ����string����
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
	    	 //����sql���Ĳ��� 
		     strValues=strValues+" and "+key.toString()+"=? ";
			 //���ö�Ӧ������ֵ
			 mapValue.add(val);
			 //���ö�Ӧ������ֵ����
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
	 * @descript �Ѵ����hashmapת������,�Ż���and�ָ�������,����Ƕ�select����
	 * ����˵��:
	 * map:sql������ֶ�,��Ӧselect������where�ֶ�,insert������values�ֶ�,delete������where�ֶ�,update������set�ֶ�
	 * strValues:�����ֶ�,���ڰ�map�ֶε�ֵƴװ�� col1=?��������ʽ(delete����where����ֶ�ƴд)
	 * mapValue:�����ֶ�,���ڰ�map�ֶε�ֵ��ƴװ��˳��ƴװ��һ��ArrayList
	 * mapType:�����ֶ�,���ڰ�map�ֶε�ֵ����voType���hashmap�ҵ���Ӧ�ֶε��������Ͱ�ƴװ��˳��ƴװ��һ��ArrayList
	 * voType:��Ӧmap�ֶε���������,ͨ���ֶ�������,��ʵvoType����ŵĶ���date���͵�,����ĳ�����ж�����Ҳ�����Ĭ����string����
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
		     
			 //���ö�Ӧ������ֵ
			 mapValue.add(val);
			 //���ö�Ӧ������ֵ����
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
	 * @descript ���ݿ��ֶ�ӳ�����dataset�ֶ�,��Ҫ���ڲ�ѯ��ʱ�����������Ҫ���ֶ���ismp_code_type,
	 * �����ݿ��ֶ��Ǵ�д��ISMP_CODE_TYPE,��ô����Ҫת��,���ݿ��ֶ���dao�Ǳ�Ĭ��ת���ɴ�д��
	 */
	public static LinkedHashMap renameVo(String recType,LinkedHashMap vo,HashMap manualVoName)
	{
		HashMap tmpMap=new HashMap();
		LinkedHashMap newReturnVo=new LinkedHashMap();
		if(vo==null || vo.isEmpty())return newReturnVo;
		tmpMap=null;//��ȡ�ֹ���д���ֶ�ӳ��,�����,���ã����û��,�Ͱ����Զ���������������
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
		tmpMap=manualVoName;//��ȡ�ֹ���д���ֶ�ӳ��,�����,���ã����û��,�Ͱ����Զ���������������
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
		tmpMap=manualVoName;//��ȡ�ֹ���д���ֶ�ӳ��,�����,���ã����û��,�Ͱ����Զ���������������
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

	//��productOfferIdת�������ĸ�ʽ:product_offer_id
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
			if (kk>=0)//����Ǵ�д��ĸ
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
		
	//��product_offer_idת�������ĸ�ʽ:productOfferId
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
				i=i+1;//����"_"�������
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
