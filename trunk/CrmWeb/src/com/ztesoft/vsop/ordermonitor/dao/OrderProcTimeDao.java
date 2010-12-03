package com.ztesoft.vsop.ordermonitor.dao;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.util.JNDINames;

public class OrderProcTimeDao extends AbstractDAO {
	
	//查询的工单处理时间的sql
	private String SQL_ORDER_PROC_TIME="select acc_nbr,prod_inst_id,product_name,product_id,service_offer_id,handle_time,execute_time,vsop_proc_time,system_code,finish_time,platform_proc_time from order_proc_time where 1=1 ";
	
	//查询条数
	private String SQL_ORDER_PROC_COUNT="select count(*) as col_counts from order_proc_time where 1=1 ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;
	

	public String getDbName() {
		return this.dbName;
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
		return null;
	}

	public String getSelectCountSQL() {
		return this.SQL_ORDER_PROC_COUNT;
	}

	public String getSelectSQL() {
		return this.SQL_ORDER_PROC_TIME;
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
