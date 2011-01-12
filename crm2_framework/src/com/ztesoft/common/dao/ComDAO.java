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
	//ִ�в�ѯ
	public String findByCondSingleVal(String mainSql,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName) throws Exception;
	//ִ�в�ѯ
	public ArrayList findByCond(String mainSql,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName) throws Exception;
	//ִ������insert,update,delete���
	public boolean exeBatch(String mainSql,ArrayList lstAllParaMap,ArrayList lstAllParaTypeMap) throws  Exception;
	//ִ������select���
	public HashMap findByCondBatch(String mainSql,ArrayList lstAllParaMap,ArrayList lstAllParaTypeMap,boolean aSync)throws Exception;
	//ִ�в���
	public int insert(String mainSql,LinkedHashMap vo,HashMap manualVoType,HashMap manualVoName) throws Exception;
	//ִ�и���
	public int update(String mainSql,LinkedHashMap vo,LinkedHashMap wherecond,HashMap manualVoType,HashMap manualVoName) throws Exception;
	//ִ��ɾ��
	public int deleteByCond(String mainSql,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName) throws Exception;
	//ִ�д洢����
	public int callProcedure(String callProSql, List inList, List outList, HashMap inTypeMap,String dataSource) throws Exception;
	//����ִ��sql��tablename
	public void setTableName(String pTableName);
	//��ȡִ��sql��tablename
	public String getTableName();
	//����ִ��sql��jndiName
	public void setJndiName(String pJndiName);
	//��ȡִ��sql��jndiName
	public String getJndiName();
	//��ȡִ�е�sql���
	public String getSql();
	
}
