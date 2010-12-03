/**
 * 
 */
package com.ztesoft.crm.business.common.query;

import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAOSystemException;


/**
 * @author lhw
 *
 */
public class SqlDAOImpl implements SqlDAO{


	private SqlMapExe sqlMapExe;

	
	private static SqlDAO sqldao=new SqlDAOImpl();
	
	private SqlDAOImpl(){
		
		sqlMapExe=SqlMapExe.getInstance();
	}
	
	public static SqlDAO getInstance(){
		
		return sqldao;
	}

	
	//ͨ��ҵ������ѯҵ������
	public String getServTypeByServCode(String servCode){
		
		return sqlMapExe.querySingleValue("getServType", servCode);
	}
//	ͨ����ѯ��ʶ�ҵ����õ�SQL���
	public String getQuerySql(String queryId) {
		
		return sqlMapExe.querySingleValue("getQuerySql", queryId);
	}

//	ͨ���ӣѣ����ƺ�������ѯȡֵ
	public String getValueBySqlNameAndCond(String sqlName, String cond) {
		
		return sqlMapExe.querySingleValue(sqlName, cond);
		
	}
//	ͨ���ӣѣ�����������ѯ����ֵ
	public String getValueBySqlAndCond(String sql, String cond) {
		
		return sqlMapExe.queryValueBySqlAndCond(sql,  cond);
		
	}
//	ͨ���ӣѣ������������ֵ��ѯ����ֵ
	public String getValueBySqlAndCond(String sql, String[] params) {
		
		return sqlMapExe.queryValueBySqlAndCond(sql, params);
	}
	

	public List getStringList(String sql,String[] sqlParams){
		
		return sqlMapExe.queryForStringList(sql, sqlParams);
		
	}
	
	//ͨ��SQL��������ֵ��ѯ���ֶζ��¼
	public List getMapList(String sql, String[] sqlParams){
		
		return sqlMapExe.queryForMapList(sql, sqlParams);
		
	}
	
	//����SQL���в�ѯ
	public List queryForMapList(String sql) {
		
		return sqlMapExe.queryForMapList(sql);
		
	}
	//��������
	public int  executeUpdate(String sql) throws DAOSystemException {
		return sqlMapExe.executeUpdate(sql);
	}

	public Map queryRowsForMap(String sql, String[] sqlParams)  throws DAOSystemException {
		

		
		return sqlMapExe.queryRowsForMap(sql,sqlParams);
	}
	
	
}
