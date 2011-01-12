package com.ztesoft.common.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;


/**
 * @time 2008-09-02
 * @author AiTanaka
 * @descript dao通用实现类,执行sql语句用
 */
public class AllServiceDaoCom {

	private Connection dbConnection = null;
    private PreparedStatement stmt = null;

    private String finalTimeFormat="yyyy-MM-dd HH:mm:ss";
    private String finalDateFormat="yyyy-MM-dd";
    private SimpleDateFormat ft = new SimpleDateFormat(finalTimeFormat);
    private SimpleDateFormat ft2 = new SimpleDateFormat(finalDateFormat);
    private String jndiName=JNDINames.CRM_DATASOURCE;
    private String strSql="";
    private int v = 0;
    
   public  AllServiceDaoCom()
   {
	   //
   }
   public void setJndiName(String pJndiName)
   {
	   jndiName=pJndiName;
   }
   public String getJndiName()
   {
	   return jndiName;
   }
   public String getSql()
   {
	   return strSql;
   }
   //执行单条sql语句,无返回值
   public int exeSQL(String serviceName,ArrayList lstParaMap,ArrayList lstParaTypeMap)throws Exception
   {
	   try
	   {
		   this.getConnection();
		   replaceSQLByCond(serviceName,lstParaMap,lstParaTypeMap);
		   return stmt.executeUpdate(); 
	   }
	   catch (Throwable ex) 
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage(), ex.getCause());
		}
	   finally
	   {
		   DAOUtils.closeStatement(stmt, this);
	   }
   }
   
   //执行多条sql语句,有返回值
   public HashMap findByCondBatch(String []serviceAllName,ArrayList lstAllParaMap,ArrayList lstAllParaTypeMap)throws Exception
   {
	   HashMap hsRet=new HashMap();
	   try
	   {
		   String serviceSql;
		   ArrayList lstParaMap=null;
		   ArrayList lstParaTypeMap=null;
		   this.getConnection();

		   for(int k=0;k<serviceAllName.length;k++){
			   int mCount=0;
			   serviceSql=serviceAllName[k];
			   if(lstAllParaMap!=null && !lstAllParaMap.isEmpty()){
				   lstParaMap=(ArrayList)lstAllParaMap.get(k);
				   mCount=lstParaMap.size();
			   }
			   
			   if(lstAllParaTypeMap!=null && !lstAllParaTypeMap.isEmpty())
			   lstParaTypeMap=(ArrayList)lstAllParaTypeMap.get(k);

			   int i=1;
			   strSql=getFilterSQL(serviceSql);
			   if(mCount>0){
				   stmt = dbConnection.prepareStatement(strSql);
				   for (i=0;i<mCount;i++)
				   {
					   String _columnType="string";
					   if(lstParaTypeMap!=null && !lstParaTypeMap.isEmpty()){
						   _columnType=(String)lstParaTypeMap.get(i);
						   if(_columnType==null)_columnType="string";
					   }
					   
					   if (_columnType.toLowerCase().equals("date"))
					   {
						   String paramVal = (String)lstParaMap.get(i);
						   Timestamp   sqlTime   =   null;
						   if (paramVal!=null && paramVal.length()>10 && paramVal.length()>0){
						   sqlTime = new Timestamp(ft.parse(paramVal, new ParsePosition(0)).getTime());
						   stmt.setTimestamp(i + 1, sqlTime);
						   }
						   else if (paramVal!=null && paramVal.length()<=10 && paramVal.length()>0){
						   sqlTime = new Timestamp(ft2.parse(paramVal, new ParsePosition(0)).getTime());
						   stmt.setTimestamp(i + 1, sqlTime);
						   }
						   else {
						   stmt.setString(i+1, (String)lstParaMap.get(i));
						   }
					   }
					   else
					   {stmt.setString(i+1, (String)lstParaMap.get(i));}
				   }
			   }else{//如果没有传输入的参数,只是传入一个sql,那么需要分析这个sql,以便使用预编译的方式去执行
				   stmt = dbConnection.prepareStatement(strSql);
			   }
			   
			   ResultSet rs=stmt.executeQuery();
			   ArrayList listResultDataSet = fetchMultiResults(rs,null);
			   hsRet.put(serviceSql, listResultDataSet);
			   DAOUtils.closeStatement(stmt, this);
		   }
	   }
	   catch (Throwable ex) 
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage(), ex.getCause());
		}
	   finally
	   {
		   DAOUtils.closeStatement(stmt, this);
	   }
	   return hsRet;
   }
   
   //执行多条sql语句,有返回值
   public HashMap findByCondBatch(String []serviceAllName,ArrayList lstAllParaMap,ArrayList lstAllParaTypeMap,boolean async)throws Exception
   {
	   return findByCondBatch(serviceAllName,lstAllParaMap,lstAllParaTypeMap);
   }
   
 
   //执行多条sql语句,无返回值
   public void exeBatchSQL(String []serviceAllName,ArrayList lstAllParaMap,ArrayList lstAllParaTypeMap)throws Exception
   {
	   try
	   {
		   String serviceSql;
		   ArrayList lstParaMap=null;
		   ArrayList lstParaTypeMap=null;
		   this.getConnection();

		   for(int k=0;k<serviceAllName.length;k++){
			   int mCount=0;
			   serviceSql=serviceAllName[k];
			   if(lstAllParaMap!=null && !lstAllParaMap.isEmpty()){
				   lstParaMap=(ArrayList)lstAllParaMap.get(k);
				   mCount=lstParaMap.size();
			   }
			   if(lstAllParaTypeMap!=null && !lstAllParaTypeMap.isEmpty())
			   lstParaTypeMap=(ArrayList)lstAllParaTypeMap.get(k);

			   int i=1;
			   strSql=getFilterSQL(serviceSql);
			   if(mCount>0){
				   stmt = dbConnection.prepareStatement(strSql);
				   for (i=0;i<mCount;i++)
				   {
					   String _columnType="string";
					   if(lstParaTypeMap!=null && !lstParaTypeMap.isEmpty()){
						   _columnType=(String)lstParaTypeMap.get(i);
						   if(_columnType==null)_columnType="string";
					   }
					   
					   if (_columnType.toLowerCase().equals("date"))
					   {
						   String paramVal = (String)lstParaMap.get(i);
						   Timestamp   sqlTime   =   null;
						   if (paramVal!=null && paramVal.length()>10 && paramVal.length()>0){
						   sqlTime = new Timestamp(ft.parse(paramVal, new ParsePosition(0)).getTime());
						   stmt.setTimestamp(i + 1, sqlTime);
						   }
						   else if (paramVal!=null && paramVal.length()<=10 && paramVal.length()>0){
						   sqlTime = new Timestamp(ft2.parse(paramVal, new ParsePosition(0)).getTime());
						   stmt.setTimestamp(i + 1, sqlTime);
						   }
						   else {
						   stmt.setString(i+1, (String)lstParaMap.get(i));
						   }
					   }
					   else
					   {stmt.setString(i+1, (String)lstParaMap.get(i));}
				   }
			   }else{//如果没有传输入的参数,只是传入一个sql,那么需要分析这个sql,以便使用预编译的方式去执行
				   stmt = dbConnection.prepareStatement(strSql);
			   }
			   stmt.execute();
			   DAOUtils.closeStatement(stmt, this);
		   }
	   }
	   catch (Throwable ex) 
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage(), ex.getCause());
		}
	   finally
	   {
		   DAOUtils.closeStatement(stmt, this);
	   }
   }
   //重命名hashmap
   private String renameHashMap(String oldName,HashMap replaceName)
   {
	   if (replaceName==null)
	   {return oldName;}
	   
	   if (replaceName.containsKey(oldName))
	   {
	   return (String)replaceName.get(oldName);
	   }
	   else
	   {
	   return oldName;
	   }
   }   
   
   private void replaceSQLByCond(String serviceSql,ArrayList lstParaMap,ArrayList lstParaDataType) throws Exception
   {
	   try
	   {
		   int mCount=lstParaMap.size();
		   int i=1;
		   strSql=getFilterSQL(serviceSql);
		   if(mCount>0){
			   stmt = dbConnection.prepareStatement(strSql);
			   for (i=0;i<mCount;i++)
			   {
				   if (((String)lstParaDataType.get(i)).toLowerCase().equals("date"))
				   {
					   String paramVal = (String)lstParaMap.get(i);
					   Timestamp   sqlTime   =   null;
					   if (paramVal!=null && paramVal.length()>10 && paramVal.length()>0){
					   sqlTime = new Timestamp(ft.parse(paramVal, new ParsePosition(0)).getTime());
					   stmt.setTimestamp(i + 1, sqlTime);
					   }
					   else if (paramVal!=null && paramVal.length()<=10 && paramVal.length()>0){
					   sqlTime = new Timestamp(ft2.parse(paramVal, new ParsePosition(0)).getTime());
					   stmt.setTimestamp(i + 1, sqlTime);
					   }
					   else {
					   stmt.setTimestamp(i+1, null);
					   }
				   }
				   else
				   {stmt.setString(i+1, (String)lstParaMap.get(i));}
			   }
		   }else{//如果没有传输入的参数,只是传入一个sql,那么需要分析这个sql,以便使用预编译的方式去执行
			   stmt = dbConnection.prepareStatement(strSql);
		   }
	   }
	   catch(Throwable Ex)
	   {
		   Ex.printStackTrace();
		   throw new Exception(serviceSql+Ex.getMessage(),Ex);
		   }
   }
   
   //尝试去添加用户名。如果添加失败也不报错 
   private String getFilterSQL(String serviceSql)
   {
	   try
	   {
	   serviceSql=DAOSQLUtils.getFilterSQL(serviceSql);
	   }
	   catch(Throwable ex)
	   {
		   ex.printStackTrace();
	   }
	   return serviceSql;
   }
   //执行单条sql语句,返回数据集
   public List exeSQL(String serviceSql,String Service_type,ArrayList ParaMap,HashMap voName,ArrayList ParaTypeMap)throws Exception
   {
	   ResultSet rs;
	   List listResultDataSet;
	   try
	   {
		   this.getConnection();
		   replaceSQLByCond(serviceSql,ParaMap,ParaTypeMap);
		   rs=stmt.executeQuery();
		   listResultDataSet = fetchMultiResults(rs,voName);
		   return listResultDataSet;
	   }
	   catch (Throwable ex) 
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage(), ex);
		}
	   finally
	   {
		   DAOUtils.closeStatement(stmt, this);
	   }
   }
   
   //执行单条sql语句,返回第一条记录的某个字段的值
   public String exeSQL(String serviceSql,int columnNumber,ArrayList lstParaMap,HashMap voName,ArrayList lstParaTypeMap)throws Exception
   {
	   ResultSet rs;
	   List listResultDataSet;
	   String tmp="";
	   try
	   {
		   this.getConnection();
		   replaceSQLByCond(serviceSql,lstParaMap,lstParaTypeMap);
		   rs=stmt.executeQuery();
		   listResultDataSet = fetchMultiResults(rs,voName);
		   if (listResultDataSet.size()>0)
		   {
		   String ColumnName=rs.getMetaData().getColumnName(columnNumber+1).toLowerCase();
		   tmp= (String)((HashMap)(listResultDataSet.get(0))).get(ColumnName);
		   }
		   else
		   {tmp="";} 
		   return tmp;
	   }
	   catch (Throwable ex) 
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage(), ex);
		}
	   finally
	   {
		   DAOUtils.closeStatement(stmt, this);
	   }
   }
   
   //执行单条sql语句,返回所有记录的某个字段的值
   public String exeSQL(String serviceSql,int columnNumber,ArrayList lstParaMap,String spa,HashMap voName,ArrayList lstParaTypeMap)throws Exception
   {
	   ResultSet rs;
	   List listResultDataSet;
	   String tmp="";
	   try
	   {
		   this.getConnection();
		   replaceSQLByCond(serviceSql,lstParaMap,lstParaTypeMap);
		   rs=stmt.executeQuery();
		   listResultDataSet = fetchMultiResults(rs,voName);
		   if (listResultDataSet.size()>0)
		   {
			   String ColumnName=rs.getMetaData().getColumnName(columnNumber+1).toLowerCase();
			   for(int i=0;i<listResultDataSet.size();i++)
			   {
				   tmp=tmp+ spa+(String)((HashMap)(listResultDataSet.get(i))).get(ColumnName);
			   }
		   }
		   else
		   {tmp="";} 
		   return tmp;
	   }
	   catch (Throwable ex) 
	   {
			//conn.rollback();
			ex.printStackTrace();
			throw new Exception(ex.getMessage(), ex.getCause());
	   }
	   finally
	   {
		   DAOUtils.closeStatement(stmt, this);
	   }
   }
   
   private ArrayList fetchMultiResults(ResultSet rs,HashMap voName) throws Exception 
	{
		ArrayList resultList = null;
		int count = 0;
		try
		{
			if (rs != null)
			{
				resultList = new ArrayList();
				while (rs.next()) 
				{
					count ++;
					if(count>20000)
					{
						throw new Exception("出现超量数据查询，请与项目组联系 !!!!!!!!!!!!!!\n");
					}
					
					HashMap vo = new HashMap();
					populateDto( vo, rs,voName);
					resultList.add( vo );
				}
			}
			return resultList;
		}
		catch (Throwable ex) 
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage(),ex.getCause());
		}
	}

   
   
	private void populateDto(HashMap vo, ResultSet rs,HashMap voName) throws SQLException 
	{
		int fieldcount = rs.getMetaData().getColumnCount();
		String ColumnName = "";
		String newColumnName = "";
		String ColumnTypeName = "";
		int voCount=0;
		if (voName!=null)
		{voCount=voName.size();}
		for (int i = 0; i < fieldcount; i++)
		{
			ColumnTypeName = rs.getMetaData().getColumnTypeName(i + 1);
			ColumnName = rs.getMetaData().getColumnName(i + 1).toLowerCase();
			if (voCount>0)
			{newColumnName=renameHashMap(ColumnName,voName);}
			else
			{newColumnName=ColumnName;}
			if (ColumnTypeName.toLowerCase().equals("date") || ColumnTypeName.toLowerCase().equals("datetime year to second")){
				String _dateVal=DAOUtils.getFormatedDateTime( rs.getTimestamp(ColumnName));
				if(_dateVal!=null && !_dateVal.equals("")){
					if(_dateVal.indexOf(".")>=0){
						String[]_lstDateVal=_dateVal.split(".");//如果是2009-12-11 00:00:00.0改成2009-12-11 00:00:00
						vo.put(newColumnName, _lstDateVal[0]);		
					}else{
						vo.put(newColumnName, _dateVal);	
					}
				}
				else{
					vo.put(newColumnName, "");	
				}
			}
			else
			{
				vo.put(newColumnName, rs.getString(ColumnName));
			}
		}
	}


    //执行存储过程的调用函数
	public int callProcedure(String callProSql, List inList, List outList, HashMap inTypeMap,String dataSource) throws Exception
	{
		CallableStatement proc = null; 
		String finalTimeFormat="yyyy-MM-dd HH:mm:ss";
	    String finalDateFormat="yyyy-MM-dd";
	    SimpleDateFormat ft = new SimpleDateFormat(finalTimeFormat);
	    SimpleDateFormat ft2 = new SimpleDateFormat(finalDateFormat);
		String paramVal = "";
		int i = 0;
		int j = 0;
		int v=-1;
		try 
		{
			this.getConnection();
			proc = dbConnection.prepareCall(callProSql); 
			
			//设置入参
				for(i = 0; inList != null && i < inList.size(); i ++)
				{
					paramVal = (String)inList.get(i);
					
					if(inTypeMap != null && !inTypeMap.isEmpty() && "date".equals(((String)inTypeMap.get(String.valueOf(i + 1))).toLowerCase()))
					{					
						Timestamp   sqlTime   =   null;
						try 
						{
							 if (paramVal!=null && paramVal.length()>10 && paramVal.length()>0){
							   sqlTime = new Timestamp(ft.parse(paramVal, new ParsePosition(0)).getTime());
							   }
							   else if (paramVal!=null && paramVal.length()<=10 && paramVal.length()>0){
							   sqlTime = new Timestamp(ft2.parse(paramVal, new ParsePosition(0)).getTime());
							   }
						} 
						catch (Exception e) 
						{
						}
						if(sqlTime!=null)
						{
							proc.setTimestamp(i + 1, sqlTime);
						}
						else
						{
							proc.setTimestamp(i + 1, null);
						}
					}
					else
					{
						proc.setString(i + 1, paramVal);
					}
				}
		
			
			//设置出参
			if(outList!=null){
				int size=outList.size();
				for(j = 0; outList != null && j < outList.size(); j ++)
				{
					proc.registerOutParameter(i + j + 1, ((Integer)outList.get(j)).intValue());
				}
			}
			  
			proc.execute(); 

			//清空出参
			if(outList != null)
			{
				int size=outList.size();
				outList.clear();
				//返回出参
				for(int k = i; k < i+size; k ++)
				{
					outList.add(proc.getObject(k+1));
				}
			}

			v=0;
			return v;
		}
		catch (Throwable ex) 
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage(), ex);
		}
		finally 
		{
			DAOUtils.closeStatement(proc, this);
		}
	}
   
   private void getConnection(){
	   dbConnection = ConnectionContext.getContext().getConnection(jndiName);
   }
	
}
