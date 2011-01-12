package com.ztesoft.common.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @time 2008-09-02
 * @author AiTanaka
 * @descript 
 */
public interface ComDAO  {
	//执行查询
	public String findByCondSingleVal(String mainSql,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName) throws Exception;
	//执行查询
	public ArrayList findByCond(String mainSql,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName) throws Exception;
	//执行批量insert,update,delete语句
	public boolean exeBatch(String mainSql,ArrayList lstAllParaMap,ArrayList lstAllParaTypeMap) throws  Exception;
	//执行批量select语句
	public HashMap findByCondBatch(String mainSql,ArrayList lstAllParaMap,ArrayList lstAllParaTypeMap,boolean aSync)throws Exception;
	//执行插入
	public int insert(String mainSql,LinkedHashMap vo,HashMap manualVoType,HashMap manualVoName) throws Exception;
	//执行更新
	public int update(String mainSql,LinkedHashMap vo,LinkedHashMap wherecond,HashMap manualVoType,HashMap manualVoName) throws Exception;
	//执行删除
	public int deleteByCond(String mainSql,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName) throws Exception;
	//执行存储过程
	public int callProcedure(String callProSql, List inList, List outList, HashMap inTypeMap,String dataSource) throws Exception;
	//设置执行sql的tablename
	public void setTableName(String pTableName);
	//获取执行sql的tablename
	public String getTableName();
	//设置执行sql的jndiName
	public void setJndiName(String pJndiName);
	//获取执行sql的jndiName
	public String getJndiName();
	//获取执行的sql语句
	public String getSql();
	
}
