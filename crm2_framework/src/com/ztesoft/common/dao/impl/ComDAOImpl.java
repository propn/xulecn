package com.ztesoft.common.dao.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.ztesoft.common.dao.ComDAO;


/**
 * 增值产品逻辑操作
 * @time 2008-09-02
 * @author AiTanaka
 */
public class ComDAOImpl implements ComDAO {
	private String tableName="";
	private AllServiceDaoCom tableExe=null;
	public ComDAOImpl()
	{
		tableExe=new AllServiceDaoCom();
	}

	public String findByCondSingleVal(String mainSql,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName) throws Exception
	{
		if (mainSql==null)
		{mainSql="";}
		if (whereCond==null)
		{whereCond=new LinkedHashMap();}
		if (manualVoType==null)
		{manualVoType=new HashMap();}
		if (manualVoName==null)
		{manualVoName=new HashMap();}
		return this.getSingleValue(mainSql,whereCond, tableName,null,manualVoType,manualVoName,"select");
	}
	
	public ArrayList findByCond(String mainSql,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName) throws  Exception{
		if (mainSql==null)
		{mainSql="";}
		if (whereCond==null)
		{whereCond=new LinkedHashMap();}
		if (manualVoType==null)
		{manualVoType=new HashMap();}
		if (manualVoName==null)
		{manualVoName=new HashMap();}
		return (ArrayList)this.getMultiRowValue(mainSql,whereCond, tableName,null,manualVoType,manualVoName,"select");
	}

	//add by AiTanaka 091128
	public boolean exeBatch(String mainSql,ArrayList lstAllParaMap,ArrayList lstAllParaTypeMap) throws  Exception {
		if (mainSql==null)
		{mainSql="";}
		if (lstAllParaMap==null)
		{lstAllParaMap=new ArrayList();}
		if (lstAllParaTypeMap==null)
		{lstAllParaTypeMap=new ArrayList();}
		this.getNonValueBatch(mainSql,lstAllParaMap, lstAllParaTypeMap);
		return true;
	}
	
	public int insert(String mainSql,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName) throws  Exception {
		if (mainSql==null)
		{mainSql="";}
		if (whereCond==null)
		{whereCond=new LinkedHashMap();}
		if (manualVoType==null)
		{manualVoType=new HashMap();}
		if (manualVoName==null)
		{manualVoName=new HashMap();}
		return this.getNonValue(mainSql,whereCond, tableName,null,manualVoType,manualVoName,"insert");
	}
	
	public int update(String mainSql,LinkedHashMap vo,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName) throws  Exception {
		if (mainSql==null)
		{mainSql="";}
		if (whereCond==null)
		{whereCond=new LinkedHashMap();}
		if (manualVoType==null)
		{manualVoType=new HashMap();}
		if (manualVoName==null)
		{manualVoName=new HashMap();}
		if (vo==null)
		{vo=new LinkedHashMap();}
		return this.getNonValue(mainSql,vo, tableName,whereCond,manualVoType,manualVoName,"update");
	}
	
	public int deleteByCond(String mainSql,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName)  throws  Exception {
		if (mainSql==null)
		{mainSql="";}
		if (whereCond==null)
		{whereCond=new LinkedHashMap();}
		if (manualVoType==null)
		{manualVoType=new HashMap();}
		if (manualVoName==null)
		{manualVoName=new HashMap();}
		return this.getNonValue(mainSql,whereCond, tableName,null,manualVoType,manualVoName,"delete");
	}

	public int callProcedure(String callProSql, List inList, List outList, HashMap inTypeMap,String dataSource) throws Exception
	{
		if (callProSql==null)
		{callProSql="";}
		if (inList==null)
		{inList=new ArrayList();}
		if (outList==null)
		{outList=new ArrayList();}
		if (inTypeMap==null)
		{inTypeMap=new HashMap();}
		if (dataSource==null)
		{dataSource="";}
		return tableExe.callProcedure(callProSql, inList, outList, inTypeMap, dataSource);
	}
	
	public void setTableName(String pTableName)
	{
		tableName=pTableName;
	}
	
	public String getTableName()
	{
		return tableName;
	}

	public void setJndiName(String pJndiName)
	{
		tableExe.setJndiName(pJndiName);
	}
	
	public String getJndiName()
	{
		return tableExe.getJndiName();
	}
	public String getSql()
	{
		return tableExe.getSql();
	}
	public HashMap findByCondBatch(String mainSql,ArrayList lstAllParaMap,ArrayList lstAllParaTypeMap,boolean aSync)throws Exception{
		if (mainSql==null)
		{mainSql="";}
		if (lstAllParaMap==null)
		{lstAllParaMap=new ArrayList();}
		if (lstAllParaTypeMap==null)
		{lstAllParaTypeMap=new ArrayList();}
		return this.getMultiValueBatch(mainSql,lstAllParaMap, lstAllParaTypeMap,aSync);
	}
	
	//-------------------------------------------
	public String getSingleValue(String mainSql,LinkedHashMap vo,String serviceName,LinkedHashMap updateWhereCondvo,
			   HashMap manualVoType,HashMap manualVoName,String sqlType) throws Exception
		{
		   try
		   {
			java.util.ArrayList key=new ArrayList();
			java.util.ArrayList keyType=new ArrayList();
			String strSQL="";
			AllServiceDaoImpl ExeDataAccess=new AllServiceDaoImpl();
			HashMap strTmp=ExeDataAccess.getSQL(mainSql,vo,serviceName,updateWhereCondvo,manualVoType,manualVoName,sqlType);
			strSQL=(String)strTmp.get("strSQL");
			key=(ArrayList)strTmp.get("voValue");
			keyType=(ArrayList)strTmp.get("voValueType");
			return tableExe.exeSQL(strSQL, 0, key,manualVoName,keyType);
		   }
		   catch(Throwable ex)
		   {
			   ex.printStackTrace();
			   throw new Exception(ex.getMessage(),ex.getCause());
		   }
		}
		
		public int getNonValue(String mainSql,LinkedHashMap vo,String serviceName,LinkedHashMap updateWhereCondvo,
				HashMap manualVoType,HashMap manualVoName,String sqlType) throws Exception
		{
			try
			{
				java.util.ArrayList key=new ArrayList();
				java.util.ArrayList keyType=new ArrayList();
				String strSQL="";
				AllServiceDaoImpl ExeDataAccess=new AllServiceDaoImpl();
				HashMap strTmp=ExeDataAccess.getSQL(mainSql,vo,serviceName,updateWhereCondvo,manualVoType,manualVoName,sqlType);
				strSQL=(String)strTmp.get("strSQL");
				key=(ArrayList)strTmp.get("voValue");
				keyType=(ArrayList)strTmp.get("voValueType");
				return tableExe.exeSQL(strSQL,key,keyType);
			}
		   catch(Throwable ex)
		   {
			   ex.printStackTrace();
			   throw new Exception(ex.getMessage(),ex.getCause());
		   }
		}
		
		public void getNonValueBatch(String mainSql,ArrayList lstAllParaMap,ArrayList lstAllParaTypeMap) throws Exception
		{
			try
			{
				String []serviceAllName=mainSql.split(";");
				tableExe.exeBatchSQL(serviceAllName, lstAllParaMap, lstAllParaTypeMap);
			}
		   catch(Throwable ex)
		   {
			   ex.printStackTrace();
			   throw new Exception(ex.getMessage(),ex.getCause());
		   }
		}
		
		public HashMap getMultiValueBatch(String mainSql,ArrayList lstAllParaMap,ArrayList lstAllParaTypeMap,boolean aSync) throws Exception
		{
			try
			{
				String []serviceAllName=mainSql.split(";");
				return tableExe.findByCondBatch(serviceAllName, lstAllParaMap, lstAllParaTypeMap,aSync);
			}
		   catch(Throwable ex)
		   {
			   ex.printStackTrace();
			   throw new Exception(ex.getMessage(),ex.getCause());
		   }
		}
		public ArrayList getMultiRowValue(String mainSql,LinkedHashMap vo,String serviceName,LinkedHashMap updateWhereCondvo,
				HashMap manualVoType,HashMap manualVoName,String sqlType) throws Exception
		{
			try
				{
				java.util.ArrayList key=new ArrayList();
				java.util.ArrayList keyType=new ArrayList();
				String strSQL="";
				AllServiceDaoImpl ExeDataAccess=new AllServiceDaoImpl();
				HashMap strTmp=ExeDataAccess.getSQL(mainSql,vo,serviceName,updateWhereCondvo,manualVoType,manualVoName,sqlType);
				strSQL=(String)strTmp.get("strSQL");
				key=(ArrayList)strTmp.get("voValue");
				keyType=(ArrayList)strTmp.get("voValueType");
				//重新命名hashmap:renameVo为了返回给查询的界面字段对应
				return (ArrayList)tableExe.exeSQL(strSQL,serviceName,key,manualVoName,keyType);
			}
		   catch(Throwable ex)
		   {
			   ex.printStackTrace();
			   throw new Exception(ex.getMessage(),ex.getCause());
		   }
		}
}
