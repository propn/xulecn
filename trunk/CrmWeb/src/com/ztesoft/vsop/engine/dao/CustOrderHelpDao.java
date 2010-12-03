package com.ztesoft.vsop.engine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

public class CustOrderHelpDao {
	protected static Logger logger = Logger.getLogger(CustOrderHelpDao.class);
	private String SQL_INSERT = "insert into CUSTOMER_ORDER (CUST_ORDER_ID, OTHER_SYS_ORDER_ID, CUST_SO_NUMBER,CUST_ORDER_TYPE,TIME_NAME_ID,CUST_ID,STAFF_ID,CHANNEL_ID,STATUS,STATUS_DATE,PRE_HANDLE_FLAG,HANDLE_PEOPLE_NAME,CONTACT_PHONE," +
	"CONTACT_PEOPLE,PRIORITY,REASON,ORDER_CHANNEL,ORDER_SYSTEM,RECEIVE_DATE,DISPOSAL_RESULT,DISPOSAL_RESULT_DESC,ACC_NBR,PRODUCT_ID,LAN_ID,PROD_INST_ID,PARTITION_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private String SQL_INSERT_HIS = "insert into CUSTOMER_ORDER_HIS (CUST_ORDER_ID, OTHER_SYS_ORDER_ID, CUST_SO_NUMBER,CUST_ORDER_TYPE,TIME_NAME_ID,CUST_ID,STAFF_ID,CHANNEL_ID,STATUS,STATUS_DATE,PRE_HANDLE_FLAG,HANDLE_PEOPLE_NAME,CONTACT_PHONE," +
	"CONTACT_PEOPLE,PRIORITY,REASON,ORDER_CHANNEL,ORDER_SYSTEM,RECEIVE_DATE,DISPOSAL_RESULT,DISPOSAL_RESULT_DESC,ACC_NBR,PRODUCT_ID,LAN_ID,PROD_INST_ID,PARTITION_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	/**
	 * 新增订单
	 * @param order
	 * @throws Exception
	 */
	public void insertOrder(CustomerOrder order) throws Exception{
		String insertSql = DAOSQLUtils.getFilterSQL(SQL_INSERT) ;
		PreparedStatement orderPs = null;
		try{
			Connection conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			SequenceManageDAOImpl sequenceManage = new SequenceManageDAOImpl();
			String custOrderId = sequenceManage.getNextSequence("SEQ_ORDER_INFO_ID");
			order.setCustOrderId(custOrderId);
			//保存订单
			orderPs = conn.prepareStatement(insertSql);
			int index = 1;
			orderPs.setLong(index++, Long.valueOf(custOrderId).longValue());//CUST_ORDER_ID
			orderPs.setString(index++, order.getOtherSysOrderId());//OTHER_SYS_ORDER_ID, 
			orderPs.setString(index++, order.getCustSoNumber());//CUST_SO_NUMBER,
			orderPs.setString(index++, order.getCustOrderType());//CUST_ORDER_TYPE,
			orderPs.setString(index++, order.getTimeNameId());//TIME_NAME_ID,
			orderPs.setString(index++, order.getCustId());//CUST_ID,
			orderPs.setString(index++, order.getStaffId());//STAFF_ID,
			orderPs.setString(index++, order.getChnId());//CHANNEL_ID,
			orderPs.setString(index++, order.getStatus());//STATUS,
			orderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATUS_DATE,
			orderPs.setString(index++, order.getPreHandleFlag());//PRE_HANDLE_FLAG,
			orderPs.setString(index++, order.getHandlePeopleName());//HANDLE_PEOPLE_NAME,
			orderPs.setString(index++, order.getContactPhone());//CONTACT_PHONE,
			orderPs.setString(index++, order.getContactPeople());//CONTACT_PEOPLE,
			orderPs.setString(index++, order.getPriority());//PRIORITY,
			orderPs.setString(index++, order.getReason());//REASON,
			orderPs.setString(index++, order.getOrderChn());//ORDER_CHANNEL,
			orderPs.setString(index++, order.getOrderSystem());//ORDER_SYSTEM,
			orderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//RECEIVE_DATE,
			orderPs.setString(index++, order.getDisposalResult());//DISPOSAL_RESULT,
			orderPs.setString(index++, order.getDisposalResultDesc());//DISPOSAL_RESULT_DESC,
			orderPs.setString(index++, order.getAccNbr());//ACC_NBR,
			orderPs.setString(index++, order.getProdId());//PRODUCT_ID,
			orderPs.setString(index++, order.getLanId());//LAN_ID
			orderPs.setString(index++, order.getProdInstId());
			orderPs.setLong(index++,DAOUtils.getCurrentMonth());
			orderPs.executeUpdate();
		}catch(SQLException ex){
			logger.error("#saveOrderAndOrderItems ex.", ex);
			throw ex;
		}finally{
			DAOUtils.closeStatement(orderPs);
		}
	}
	
	
	/**
	 * 新增订单
	 * @param order
	 * @throws Exception
	 */
	public void insertOrderHis(CustomerOrder order) throws Exception{
		String insertSql = DAOSQLUtils.getFilterSQL(SQL_INSERT_HIS) ;
		PreparedStatement orderPs = null;
		try{
			Connection conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			SequenceManageDAOImpl sequenceManage = new SequenceManageDAOImpl();
			String custOrderId = sequenceManage.getNextSequence("SEQ_ORDER_INFO_ID");
			order.setCustOrderId(custOrderId);
			//保存订单
			orderPs = conn.prepareStatement(insertSql);
			int index = 1;
			orderPs.setLong(index++, Long.valueOf(custOrderId).longValue());//CUST_ORDER_ID
			orderPs.setString(index++, order.getOtherSysOrderId());//OTHER_SYS_ORDER_ID, 
			orderPs.setString(index++, order.getCustSoNumber());//CUST_SO_NUMBER,
			orderPs.setString(index++, order.getCustOrderType());//CUST_ORDER_TYPE,
			orderPs.setString(index++, order.getTimeNameId());//TIME_NAME_ID,
			orderPs.setString(index++, order.getCustId());//CUST_ID,
			orderPs.setString(index++, order.getStaffId());//STAFF_ID,
			orderPs.setString(index++, order.getChnId());//CHANNEL_ID,
			orderPs.setString(index++, order.getStatus());//STATUS,
			orderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATUS_DATE,
			orderPs.setString(index++, order.getPreHandleFlag());//PRE_HANDLE_FLAG,
			orderPs.setString(index++, order.getHandlePeopleName());//HANDLE_PEOPLE_NAME,
			orderPs.setString(index++, order.getContactPhone());//CONTACT_PHONE,
			orderPs.setString(index++, order.getContactPeople());//CONTACT_PEOPLE,
			orderPs.setString(index++, order.getPriority());//PRIORITY,
			orderPs.setString(index++, order.getReason());//REASON,
			orderPs.setString(index++, order.getOrderChn());//ORDER_CHANNEL,
			orderPs.setString(index++, order.getOrderSystem());//ORDER_SYSTEM,
			orderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//RECEIVE_DATE,
			orderPs.setString(index++, order.getDisposalResult());//DISPOSAL_RESULT,
			orderPs.setString(index++, order.getDisposalResultDesc());//DISPOSAL_RESULT_DESC,
			orderPs.setString(index++, order.getAccNbr());//ACC_NBR,
			orderPs.setString(index++, order.getProdId());//PRODUCT_ID,
			orderPs.setString(index++, order.getLanId());//LAN_ID
			orderPs.setString(index++, order.getProdInstId());
			orderPs.setLong(index++,DAOUtils.getCurrentMonth());
			orderPs.executeUpdate();
		}catch(SQLException ex){
			logger.error("#saveOrderAndOrderItems ex.", ex);
			throw ex;
		}finally{
			DAOUtils.closeStatement(orderPs);
		}
	}

}
