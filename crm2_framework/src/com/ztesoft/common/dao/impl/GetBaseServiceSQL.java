package com.ztesoft.common.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.CrmConstants;


/**
 * @time 2008-09-02
 * @author AiTanaka
 * @descript dao通用实现类,拼凑sql语句用
 */
public class GetBaseServiceSQL {
	
	private static String strSQL="";
	private static Properties properties = null;
	public static String getMainSql(String mainSql,String Rec_type,String sqlType)
	{
		if (mainSql!=null && !mainSql.equals(""))
		{
			strSQL= mainSql;
		}
		else
		{
			if (sqlType.equals("select"))
			{
				strSQL= getSelect(Rec_type);
			}else if (sqlType.equals("insert"))
			{
				strSQL= getInsert(Rec_type);
			}
			else if (sqlType.equals("update"))
			{
				strSQL= getUpdate(Rec_type);
			}
			else if (sqlType.equals("delete"))
			{
				strSQL= getDelete(Rec_type);
			}
			else
			{strSQL= mainSql;}
		}
		return strSQL;
	}
	
	public static void initResourceOra()
	{
		FileInputStream fis = null;
		String fileName = "";
		File cfgFile = null;
		cfgFile = new File("./cfg/"+ "Resources.properties");
		if(!cfgFile.exists()){
			fileName = CrmConstants.WEB_INF_PATH + "Resources.properties";
			cfgFile = new File(fileName);
		}
		
		if(cfgFile==null || !cfgFile.exists()){
			return;
		}
		try {
			fis = new FileInputStream(cfgFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		try {
			properties.load(fis);
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static HashMap cacheTableContruct=new HashMap();
	/**
	 * @time 2008-09-03
	 * @author AiTanaka
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws OaException 
	 * @throws FileNotFoundException 
	 * @descript 设置sql的参数类型,string默认,只有date类型需要设置
	 */
	public static HashMap queryDataTypeMapToList(String tableName) throws Exception
	{
		HashMap vo=new HashMap();
		if (!tableName.equals("") && !tableName.equals("null") && tableName!=null)
		{
			String newTableName=tableName;
			String strSQL="";
			
			String strTable=newTableName;
			String strOwner="";
			if (properties==null)
			{
				properties = new Properties();
				initResourceOra();
			}
			String value = properties.getProperty(strTable.toLowerCase());
			if(value==null)value="";
			
			String[] values = value.split(",");
			String _key="";
			if (values.length==3)
			{
				strOwner=values[0];
				strSQL=
					"select distinct column_name From sys.dba_tab_cols where table_name='"+strTable.toUpperCase()+
					"' and data_type='DATE' and owner='"+strOwner.toUpperCase()+"'";
				_key=strTable.toUpperCase()+strOwner.toUpperCase();
			}
			else
			{
				strSQL=
					"select distinct column_name From sys.dba_tab_cols where table_name='"+strTable.toUpperCase()+
					"' and data_type='DATE' ";
				_key=strTable.toUpperCase();
			}
			if(cacheTableContruct.containsKey(_key))return (HashMap)cacheTableContruct.get(_key);
			ComDAOImpl tableColType=new ComDAOImpl();
			HashMap testVo=new HashMap();
			LinkedHashMap testVo2=new LinkedHashMap();
			ArrayList tempRs=tableColType.getMultiRowValue(strSQL, testVo2, "",testVo2,testVo,testVo,"select");
			for (int i=0;i<tempRs.size();i++)
			{
				HashMap colRs=new HashMap();
				colRs=(HashMap)tempRs.get(i);
				String columnName=(String)colRs.get("column_name");
				vo.put(columnName.toLowerCase(), "date");
			}
			//缓存表结构
			cacheTableContruct.put(_key, vo);
			
		}
		return vo;
	}
	
	public static String getDbName(String tableName ){
		String strOwner="";
		if (properties==null)
		{
			properties = new Properties();
			initResourceOra();
		}
		String value = properties.getProperty(tableName.toLowerCase());
		if (value!=null)
		{
			String[] values = value.split(",");
//			String _key="";
			if (values.length==3)
			{
				strOwner=values[0];
			}
		}
		return strOwner; 
	}
	public static String getSelect(String tableName)
	{
		return " select * from "+tableName+" where 1=1 ";
	}
	public static String getInsert(String tableName)
	{
		return "insert into "+tableName+" ";
	}
	public static String getDelete(String tableName)
	{
		return "delete from "+tableName+" where 1=1 ";
	}
	public static String getUpdate(String tableName)
	{
		return "update "+tableName+" ";
	}
}
