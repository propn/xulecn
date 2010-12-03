/**
 * 
 */
package com.ztesoft.crm.business.common.query;

import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAOSystemException;




public interface SqlDAO {

	//ͨ��ҵ�����ҵ�ҵ������
	public abstract String getServTypeByServCode(String servCode);
	
	//	ͨ����ѯ��ʶ�ҵ����õ�SQL���
	public abstract String getQuerySql(String queryId);
	
	
	
	//ͨ���ӣѣ����ƺ�������ѯȡֵ
	public abstract String getValueBySqlNameAndCond(String sqlName,String cond);
	
	//ͨ���ӣѣ�����������ѯ����ֵ
	public String getValueBySqlAndCond(String sql, String cond) ;
	
	
	
	//ͨ���ӣѣ������������ֵ��ѯ����ֵ
	public String getValueBySqlAndCond(String sql, String []params) ;
	
	//ͨ���ӣѣ������������ֵ��ѯ����ֵ
	public Map queryRowsForMap(String sql,String[] sqlParams);
	
	
	//ͨ��SQL���Ͳ�����ѯstring list	
	public List getStringList(String sql,String[]sqlParams);
	
	
	//ͨ��SQL���Ͳ�����ѯMAP list	
	public List getMapList(String sql,String[]sqlParams);
	
	//ͨ��SQL����ѯMAP list	
	public List queryForMapList(String sql);
	
//	��������
	public int  executeUpdate(String sql) throws DAOSystemException ;
}
