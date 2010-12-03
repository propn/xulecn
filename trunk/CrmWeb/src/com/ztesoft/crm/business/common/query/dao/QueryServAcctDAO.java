package com.ztesoft.crm.business.common.query.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;

public class QueryServAcctDAO extends AbstractDAO {

	public static final String QUERY_SERV_ACCT_BY_ACCT_ID = 
			" SELECT a.acct_item_group_id, a.serv_id, a.acct_id, b.cust_id, b.product_id, b.area_code, " +
			"     b.acc_nbr, b.lan_id 																   " +
			"	from serv_acct a, serv b where a.serv_id = b.serv_id and a.acct_id = ? and b.state=?   ";
	
	public static final String COUNT_SERV_ACCT = 
			" SELECT count(*) counts														   		   " +
			"	from serv_acct a, serv b where a.serv_id = b.serv_id and a.acct_id = ? and b.state=?   ";
	
	//当前DAO 所属数据库name
	private String dbName = JNDINames.CRM_DATASOURCE;
	
	public QueryServAcctDAO(){
		
	}
	
	/**
	 * 根据帐号ID查询帐务定制关系，查询在用的帐务定制关系。
	 * @param acctId
	 * @param state
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws FrameException
	 */
	public PageModel queryServAcctByAcctIdAndState(String acctId,String state,Integer pageIndex,Integer pageSize)throws FrameException{
		List para = new ArrayList();
		para.add(acctId);
		para.add(state);
		return this.searchBySql(QUERY_SERV_ACCT_BY_ACCT_ID, para, pageIndex.intValue(), pageSize.intValue());
	}
	
	/**
	 * 查询帐户是否与实例相关。
	 * @param acctId
	 * @param state
	 * @return
	 * @throws FrameException
	 */
	public String queryServAcctState(String acctId,String state)throws FrameException{
		List para = new ArrayList();
		para.add(acctId);
		para.add(state);
		ArrayList countList = (ArrayList) this.findBySql(COUNT_SERV_ACCT, para);
		Iterator countIter = countList.iterator();
		String counts = "0";
		if(countIter.hasNext()){
			Map voMap = (Map) countIter.next();
			counts = (String) voMap.get("counts");
		}
		return counts;
	}
	
	public String getDbName() {
		// TODO Auto-generated method stub
		return dbName;
	}

	public String getDeleteSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDeleteSQLByKey() throws FrameException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInsertSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSQLSQLByKey() throws FrameException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSelectCountSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSelectSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUpdateSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUpdateSQLByKey() throws FrameException {
		// TODO Auto-generated method stub
		return null;
	}

}
