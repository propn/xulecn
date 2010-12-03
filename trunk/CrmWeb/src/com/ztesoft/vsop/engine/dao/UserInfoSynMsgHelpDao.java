package com.ztesoft.vsop.engine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.vo.UserinfoSyncMsgVO;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * 用户状态同步DAO类
 * @author cooltan
 *
 */
public class UserInfoSynMsgHelpDao {
	private String SEQUENCECODE = "SEQ_USERINFO_SYNC_MSG_ID";
	private int maxRows;
	
	private String SQL_INSERT = "INSERT INTO USERINFO_SYNC_MSG ( MSG_ID,SYSTEM_ID,SERVICE_URL,CREATE_DATE,CUST_ORDER_ID,ACC_NBR,REQUEST_XML,RESPONSE_XML,STATE,DEAL_TIME,RESULT_DESC,THREAD_ID ) VALUES ( ?,?,?,"+DatabaseFunction.current()+",?,?,?,?,?,?,?,? )";

	/**
	 * 新增用户状态同步队列；
	 * @param vo
	 */
	public void insertAUserInfoSynMsg(UserinfoSyncMsgVO vo){
		this.insert(vo);
	}
	/**
	 * 根据平台列表新增用户状态同步队列；
	 * @param systemInfo
	 * @param requestXml
	 * @param custOrderId
	 * @param accNbr
	 */
	public void batchinsertUserInfoSynMsg(List systemInfo,String requestXml,String custOrderId,String accNbr){
		for (Iterator iterator = systemInfo.iterator(); iterator.hasNext();) {
			String url = (String) iterator.next();
			UserinfoSyncMsgVO vo=new UserinfoSyncMsgVO();
			vo.setAccNbr(accNbr);
			vo.setCustOrderId(custOrderId);
			vo.setRequestXml(requestXml);
			vo.setServUrl(url);
			vo.setState("0");
			this.insertAUserInfoSynMsg(vo);
		}
	}
	/**
	 * 根据产品实例查找该用户需要用户同步的X平台列表
	 * @param prodInstId
	 * @return
	 * @throws SQLException
	 */
	public List getXplatformServiceUrl(String prodInstId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List serviceUrl = new ArrayList();
		String sql = "select distinct(c.service_url) from order_relation a," +
				" PRODUCT_SYSTEM_INFO b,SYSTEM_INFO c " +
				" where a.product_id=b.product_id and b.system_code=c.system_code " +
				" and c.is_user_state=1 and a.prod_inst_id=? " ;
				//"and a.STATE =?";
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, prodInstId);
			//ps.setString(2, OrderConstant.orderStateOfCreated);
			rs = ps.executeQuery();
			while(rs.next()){
				String url = rs.getString("service_url");
				serviceUrl.add(url);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return serviceUrl;
	}
	
	public void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(
					JNDINames.DEFAULT_DATASOURCE);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
			String seq = aSequenceManageDAOImpl
					.getNextSequence(SEQUENCECODE);
			((UserinfoSyncMsgVO)vo).setMsgId(seq);
			if ("".equals(((UserinfoSyncMsgVO)vo).getMsgId())) {
				((UserinfoSyncMsgVO)vo).setMsgId(null);
			}
			stmt.setString( index++, ((UserinfoSyncMsgVO)vo).getMsgId() );
			stmt.setString( index++, ((UserinfoSyncMsgVO)vo).getSystemId() );
			stmt.setString( index++, ((UserinfoSyncMsgVO)vo).getServUrl() );
			stmt.setString( index++, ((UserinfoSyncMsgVO)vo).getCustOrderId() );
			stmt.setString( index++, ((UserinfoSyncMsgVO)vo).getAccNbr() );
			stmt.setString( index++, ((UserinfoSyncMsgVO)vo).getRequestXml() );
			stmt.setString( index++, ((UserinfoSyncMsgVO)vo).getResponseXml() );
			stmt.setString( index++, ((UserinfoSyncMsgVO)vo).getState() );
			stmt.setDate( index++, DAOUtils.parseDateTime(((UserinfoSyncMsgVO)vo).getDealTime()) );
			stmt.setString( index++, ((UserinfoSyncMsgVO)vo).getResultDesc() );
			stmt.setString( index++, this.generateUserInfoSyncThreadId(((UserinfoSyncMsgVO)vo).getAccNbr() ) );
			int rows = stmt.executeUpdate();
		}
		catch (SQLException se) {
			Debug.print(SQL_INSERT,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
		}
	}
	
	private String generateUserInfoSyncThreadId(String accNbr) {
		String threadCount = DcSystemParamManager.getParameter(VsopConstants.USERINFO_SYNC_THREAD_NUM);
		if(null == threadCount || "".equals(threadCount)) threadCount = "1";
		return generateThreadId(accNbr,threadCount);
	}
	/**
	 * 根据业务号码和线程数生成线程id
	 * @param accNbr  产品号码
	 * @param threadNum  线程总数
	 * @return
	 */
	private String generateThreadId(String accNbr,String threadNum){
		int r = 2;
		if(threadNum != null && !"".equals(threadNum)) r = Integer.parseInt(threadNum);
		int prodNo = Integer.parseInt(accNbr.substring(accNbr.length()-1));
		int ramdom = prodNo % r;
		return String.valueOf(ramdom);
	}
		

}
