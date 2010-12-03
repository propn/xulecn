package com.ztesoft.vsop.order.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.ConstantsState;
import com.ztesoft.vsop.DateUtil;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.order.OrderConstant;
import com.ztesoft.vsop.order.exception.ProductInstanceIdNotExists;
import com.ztesoft.vsop.order.vo.AProductInfo;
import com.ztesoft.vsop.order.vo.InfMsg;
import com.ztesoft.vsop.order.vo.MsgSynHB;
import com.ztesoft.vsop.order.vo.ProductOfferInfo;
import com.ztesoft.vsop.order.vo.SecondConfirmMsg;
import com.ztesoft.vsop.order.vo.SpOutMsgFeedback;
import com.ztesoft.vsop.order.vo.UserInfoSyncMsg;
import com.ztesoft.vsop.order.vo.VProductInfo;
import com.ztesoft.vsop.order.vo.VSubProdInfo;
import com.ztesoft.vsop.order.vo.VproducInfo;
import com.ztesoft.vsop.order.vo.returnProdOfferVO;
import com.ztesoft.vsop.order.vo.returnSubVO;
import com.ztesoft.vsop.order.vo.request.SubInstHisQueryRequest;
import com.ztesoft.vsop.order.vo.request.SubInstQueryRequest;
import com.ztesoft.vsop.order.vo.request.SubscribeToVSOPRequest;
import com.ztesoft.vsop.order.vo.request.WorkListFKToVSOPRequest;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class OrderDao {
	private static Logger logger = Logger.getLogger(OrderDao.class);
//	private String dbName = JNDINames.DEFAULT_DATASOURCE ;
	private String SEQ_ORDER_INFO = "SEQ_ORDER_INFO_ID";
//	private String SEQ_BIZ_CAPABILITY_INST = "SEQ_BCI_SERV_PRODUCT_ID";
	private String SQL_INSERT = "insert into CUSTOMER_ORDER (CUST_ORDER_ID, OTHER_SYS_ORDER_ID, CUST_SO_NUMBER,CUST_ORDER_TYPE,TIME_NAME_ID,CUST_ID,STAFF_ID,CHANNEL_ID,STATUS,STATUS_DATE,PRE_HANDLE_FLAG,HANDLE_PEOPLE_NAME,CONTACT_PHONE," +
			"CONTACT_PEOPLE,PRIORITY,REASON,ORDER_CHANNEL,ORDER_SYSTEM,RECEIVE_DATE,DISPOSAL_RESULT,DISPOSAL_RESULT_DESC,ACC_NBR,PRODUCT_ID,LAN_ID,PROD_INST_ID,PARTITION_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private String INSERT_ORDER_RELATION_SQL = "INSERT INTO ORDER_RELATION(ORDER_RELATION_ID,PROD_INST_ID,PRODUCT_ID,PPROD_OFFER_ID,PROD_OFFER_INST_ID,PROD_OFFER_ID,ORDER_CHANNEL,STATE,EFF_DATE,EXP_DATE,PACKAGE_ID,LAN_ID,STATE_DATE,ACTIVE_STATE,ACC_NBR,PROD_TYPE_CD,CREATE_DATE)" +
	" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	//数据库类型是否是informix
	private static boolean isInformix=false;	
	static{
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			isInformix=true;
		}
	}
	
	/**
	 * 保存订单、订单项
	 * @param order
	 * @return
	 * @throws FrameException
	 * @throws SQLException 
	 */
	public boolean getInstByNBR(String where,List param) throws SQLException{
		ResultSet rs = null;
		PreparedStatement psProdId = null;
		Connection conn = LegacyDAOUtil.getConnection();
		StringBuffer getProdInstId =new StringBuffer("SELECT PROD_INST_ID,LAN_ID,PRODUCT_ID from PROD_INST where 1=1 ");
		boolean inst = false;
		try {
			psProdId = conn.prepareStatement(getProdInstId.toString()+where);
			for (int j = 0; j < param.size(); j++) {
				psProdId.setString(j+1, (String) param.get(j));
			}
			rs = psProdId.executeQuery();
			if(rs.next())
				inst = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("getInstByNBR ex.",e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(psProdId);
		}
		return inst;
	}
	public void getOrderByNbr(SubscribeToVSOPRequest order)throws SQLException{
		PreparedStatement psProdId = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = LegacyDAOUtil.getConnection();
			String getProdInstId = "SELECT PROD_INST_ID,LAN_ID,PRODUCT_ID from PROD_INST where ACC_NBR = ? and STATE_CD<>?";
			psProdId = conn.prepareStatement(getProdInstId);
			psProdId.setString(1, order.getProductNo());
			psProdId.setString(2, OrderConstant.orderStateOfDelete);
			rs = psProdId.executeQuery();
			if(rs.next()){
				order.setProdInstId(rs.getString(1));
				order.setLanCode(rs.getString(2));
				order.setProdSpecCode(rs.getString(3));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#getOrderByNbr",e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(psProdId);
		}
	}
	public boolean saveOrderAndOrderItems(SubscribeToVSOPRequest order) throws SQLException {
		String insertSql = DAOSQLUtils.getFilterSQL(SQL_INSERT) ;
		PreparedStatement orderPs = null;
		PreparedStatement subOrderPs = null;
		try{
			Connection conn = LegacyDAOUtil.getConnection();
			order.setSequence(getSequence(SEQ_ORDER_INFO, conn ));
			//保存订单
			orderPs = conn.prepareStatement(insertSql);
			int index = 1;
			orderPs.setLong(index++, order.getSequence());//CUST_ORDER_ID
			String orderId = order.getOrderId();
			if(null == orderId || "".equals(orderId) || "null" .equals(orderId))
				orderId = null;
			orderPs.setString(index++, orderId);//OTHER_SYS_ORDER_ID, 
			orderPs.setString(index++, order.getStreamingNo());//CUST_SO_NUMBER,
			orderPs.setString(index++, order.getActionType());//CUST_ORDER_TYPE,
			orderPs.setString(index++, "");//TIME_NAME_ID,
			orderPs.setString(index++, "");//CUST_ID,
			orderPs.setString(index++, "");//STAFF_ID,
			orderPs.setString(index++, "");//CHANNEL_ID,
			orderPs.setString(index++, order.getState());//STATUS,
			orderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATUS_DATE,
			orderPs.setString(index++, "");//PRE_HANDLE_FLAG,
			orderPs.setString(index++, "");//HANDLE_PEOPLE_NAME,
			orderPs.setString(index++, "");//CONTACT_PHONE,
			orderPs.setString(index++, "");//CONTACT_PEOPLE,
			orderPs.setString(index++, "");//PRIORITY,
			orderPs.setString(index++, "");//REASON,
			orderPs.setString(index++, order.getSystemId());//ORDER_CHANNEL,
			orderPs.setString(index++, order.getSystemId());//ORDER_SYSTEM,
			orderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//RECEIVE_DATE,
			orderPs.setString(index++, "");//DISPOSAL_RESULT,
			orderPs.setString(index++, "");//DISPOSAL_RESULT_DESC,
			orderPs.setString(index++, order.getProductNo());//ACC_NBR,
			orderPs.setString(index++, order.getProdSpecCode());//PRODUCT_ID,
			orderPs.setString(index++, order.getLanCode());//LAN_ID
			orderPs.setString(index++, order.getProdInstId());
			orderPs.setLong(index++,DAOUtils.getCurrentMonth());
			orderPs.executeUpdate();
			//保存子订单
			insertSql = "insert into ORDER_ITEM (ORDER_ITEM_ID,CUST_ORDER_ID,ORDER_ITEM_CD,ORDER_ITEM_OBJ_ID,CUST_WORKSHEET_ID,STATUS,STATUS_DATE,STATE_CHANGE_REASON,PRIORITY,PRE_HANDLE_FLAG" +
					",HANDLE_TIME,ARCHIVE_DATE,FINISH_TIME,PRODUCT_ID,ACC_NBR,PACKAGE_ID,PRODUCT_OFFER_ID,EFF_TIME,EXP_TIME,RESULT_TYPE,RESULT_INFO,LAN_ID,PPROD_OFFER_ID,PROD_INST_ID,SERVICE_OFFER_ID,PARTITION_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+DatabaseFunction.current()+", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
			subOrderPs = conn.prepareStatement(insertSql);
			for (Iterator productOfferItr = order.getProductOfferInfo().iterator(); productOfferItr.hasNext();) {
				ProductOfferInfo productOffer = (ProductOfferInfo) productOfferItr.next();
				List vSubProdInfoList = productOffer.getVSubProdInfoList();
				for (Iterator iterator = vSubProdInfoList.iterator(); iterator
						.hasNext();) {
					VSubProdInfo vSubProdInfo = (VSubProdInfo) iterator.next();
					vSubProdInfo.setSequence(getSequence(VsopSubOrderInfoDAO.SEQUENCE, conn));
					index = 1;
					subOrderPs.setLong(index++, vSubProdInfo.getSequence());//ORDER_ITEM_ID
					subOrderPs.setLong(index++,order.getSequence());//CUST_ORDER_ID,
					subOrderPs.setString(index++, OrderConstant.ORDER_ITEM_CD_PRODOFFER);//ORDER_ITEM_CD,
					subOrderPs.setString(index++, vSubProdInfo.getVproductInstId());//ORDER_ITEM_OBJ_ID,
					subOrderPs.setString(index++, "");//CUST_WORKSHEET_ID,
					subOrderPs.setString(index++, order.getState());//STATUS,
					subOrderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATUS_DATE,
					subOrderPs.setString(index++, "");//STATE_CHANGE_REASON,
					subOrderPs.setString(index++, "");//PRIORITY,
					subOrderPs.setString(index++, "");//PRE_HANDLE_FLAG,
//					subOrderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//HANDLE_TIME,
					subOrderPs.setString(index++, "");//ARCHIVE_DATE,
					subOrderPs.setString(index++, "");//FINISH_TIME,
					subOrderPs.setString(index++, vSubProdInfo.getVProductID());//PRODUCT_ID,
					subOrderPs.setString(index++, order.getProductNo());//ACC_NBR,
					subOrderPs.setString(index++, vSubProdInfo.getPackageId());//PACKAGE_ID,
					subOrderPs.setString(index++, vSubProdInfo.getProductOfferId());//PRODUCT_OFFER_ID,
					subOrderPs.setTimestamp(index++, DAOUtils.parseTimestamp(vSubProdInfo.getEffDate()));//EFF_TIME,
					subOrderPs.setTimestamp(index++, DAOUtils.parseTimestamp(vSubProdInfo.getExpDate()));//EXP_TIME,
					subOrderPs.setString(index++, "");//RESULT_TYPE,
					subOrderPs.setString(index++, "");//RESULT_INFO,
					subOrderPs.setString(index++, order.getLanCode());//LAN_ID
					subOrderPs.setString(index++, vSubProdInfo.getPprodOfferId());
					subOrderPs.setString(index++, order.getProdInstId());
					subOrderPs.setString(index++, order.getActionType());
					subOrderPs.setLong(index++,DAOUtils.getCurrentMonth());
					//subOrderPs.executeUpdate();
					subOrderPs.addBatch();
				}
				if(vSubProdInfoList.size() > 0)
					subOrderPs.executeBatch();
			}
		}catch(SQLException ex){
			logger.error("#saveOrderAndOrderItems ex.", ex);
			throw ex;
		}finally{
			DAOUtils.closeStatement(subOrderPs);
			DAOUtils.closeStatement(orderPs);
		}
		return true;
	}

	public void saveXML(String spiXML, String systemId, long inOrderId, String productNo, String lanCode) throws SQLException {
		String insertSql = "INSERT INTO SP_OUT_MSG(ID,IN_TIME,MSG_ID,SYS,ORDER_ID,STATE,MSG,PROD_NO,LAN_CODE,PARTITION_ID) " +
				" VALUES(SP_OUT_MSG_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?)";
		long s=System.currentTimeMillis();
		PreparedStatement ps = null;
		Connection conn = null;
		InputStreamReader ir = null;
		try {
			conn = LegacyDAOUtil.getConnection();
			ps = conn.prepareStatement(insertSql);
			int index = 1;
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setString(index++, "0");//msg_id
			ps.setString(index++, systemId);//SYS,
			ps.setLong(index++, inOrderId);//order_id,
			ps.setString(index++, "0");//STATE
			if (spiXML != null) {
				ir = new InputStreamReader(new ByteArrayInputStream(spiXML.getBytes()));
				ps.setCharacterStream(index++, ir, spiXML.length());
			} else {
				ps.setNull(index++, java.sql.Types.VARCHAR);
			}
			ps.setString(index++, productNo);
			ps.setString(index++, genLanCode(productNo));
			ps.setLong(index++, DAOUtils.getCurrentMonth());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#saveXML ex.",e);
			throw e;
		}finally{
			if(ir != null){
				try {
					ir.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		logger.info("save spi XML cost:"+(System.currentTimeMillis()-s));
	}
	/**
	 * 激活模块优化性能用
	 * 分给激活模块的不同进程
	 * @param lanCode
	 * @return
	 */
	private String genLanCode(String productNo) {
		String spiProcNum = DcSystemParamManager.getParameter(VsopConstants.SPI_PROC_NUM);
		return generateThreadId(productNo, spiProcNum);
	}
	private int getRandomNum(int range) {
		java.util.Random r = new java.util.Random();
		int ri = r.nextInt(range);
		return ri;
	}
	/**
	 * 
	 * 根据orderId获取order
	 * @param order
	 * @throws SQLException 
	 */
	public void findOrder(SubscribeToVSOPRequest order) throws SQLException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		
		PreparedStatement pstm2 = null;
		ResultSet rs2 = null;
		String orderSql = "select CUST_ORDER_ID, OTHER_SYS_ORDER_ID, CUST_SO_NUMBER,CUST_ORDER_TYPE,TIME_NAME_ID,CUST_ID,STAFF_ID,CHANNEL_ID,STATUS,STATUS_DATE,PRE_HANDLE_FLAG,HANDLE_PEOPLE_NAME,CONTACT_PHONE," +
			"CONTACT_PEOPLE,PRIORITY,REASON,ORDER_CHANNEL,ORDER_SYSTEM,RECEIVE_DATE,DISPOSAL_RESULT,DISPOSAL_RESULT_DESC,ACC_NBR,PRODUCT_ID,LAN_ID from CUSTOMER_ORDER where 1=1 AND OTHER_SYS_ORDER_ID=?";
		
		try {
			conn = LegacyDAOUtil.getConnection();
			pstm = conn.prepareStatement(orderSql);
			pstm.setString(1, order.getOrderId());
			rs = pstm.executeQuery();
			if (rs.next()) {
				//				order.setActionType(rs.getString("order_type"));
				//				order.setPackageId(rs.getString("PackageId"));
				//				order.setProdSpecCode(rs.getString(""));
				order.setProductId(rs.getString("PRODUCT_ID"));
				order.setProductNo(rs.getString("ACC_NBR"));
				//				order.setProductOfferId(rs.getString("order_type"));
				order.setSequence(rs.getLong("CUST_ORDER_ID"));
				order.setSystemId(rs.getString("ORDER_SYSTEM"));
				//				order.setUserState(rs.getString("order_type"));
			}
			
			
			String subOrderSql = "select ORDER_ITEM_ID,CUST_ORDER_ID,ORDER_ITEM_CD,ORDER_ITEM_OBJ_ID,CUST_WORKSHEET_ID,STATUS,STATUS_DATE"
					+ ",STATE_CHANGE_REASON,PRIORITY,PRE_HANDLE_FLAG,HANDLE_TIME,ARCHIVE_DATE,FINISH_TIME,PRODUCT_ID,ACC_NBR,PACKAGE_ID"
					+ ",PRODUCT_OFFER_ID,EFF_TIME,EXP_TIME,RESULT_TYPE,RESULT_INFO,LAN_ID from ORDER_ITEM where 1=1 AND CUST_ORDER_ID=?";
			pstm2 = conn.prepareStatement(subOrderSql);
			pstm2.setLong(1, order.getSequence());
			rs2 = pstm.executeQuery();
			List subProductList = new ArrayList();
			while (rs2.next()) {
				VSubProdInfo subProduct = new VSubProdInfo();
				subProduct.setActionType(rs.getString("ORDER_ITEM_CD"));
				subProduct.setEffDate(rs.getString("EFF_TIME"));
				subProduct.setExpDate(rs.getString("EFF_TIME"));
				subProduct.setSequence(rs.getLong("ORDER_ITEM_ID"));
				subProductList.add(subProduct);
			}
			List pOfferList = new ArrayList();
			ProductOfferInfo poffer = new ProductOfferInfo();
			poffer.setVSubProdInfoList(subProductList);
			order.setProductOfferInfo(pOfferList);
		} catch (SQLException e) {
			logger.error("#findOrder ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(pstm);
			DAOUtils.closeResultSet(rs2);
			DAOUtils.closeStatement(pstm2);
		}
		
	}

	private String undealState = "U"; //未处理
	private String dealingState = "D"; // 处理中
	private String failState = "F"; // 处理失败
	private String successState = "S"; // 处理成功
	
	public List getUnDealOrders(int limit, String threadid) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement psmt = null;
		PreparedStatement updatePsmt = null;
		ResultSet rs = null;
		//PreparedStatement psInfMsgId = null;
		String updateSql = "UPDATE INF_MSG SET STATE = ? WHERE INF_MSG_ID =? ";
		
		//oracle
		String sql = "SELECT INF_MSG_ID,INF_MSG,INF_SYSTEM,CREATE_DATE,STATE,STATE_DATE FROM INF_MSG WHERE STATE = ? AND ROWNUM <= ?"+("".equals(threadid)? "" : " and THREAD_ID=?")+" ORDER BY INF_MSG_ID";
		//informix
		if (isInformix) {
			sql = "SELECT first ? INF_MSG_ID,INF_MSG,INF_SYSTEM,CREATE_DATE,STATE,STATE_DATE FROM INF_MSG WHERE STATE = ? "+("".equals(threadid)? "" : " and THREAD_ID=?")+" ORDER BY INF_MSG_ID";
		}
		
		//String insertSql = "INSERT INTO TMP_ID(ID) SELECT INF_MSG_ID FROM INF_MSG WHERE STATE = ? AND ROWNUM <= ?"+("".equals(threadid)? "" : " and THREAD_ID=?")+" ORDER BY INF_MSG_ID";
		try {
			/*psInfMsgId = conn.prepareStatement(insertSql);
			psInfMsgId.setString(1, undealState);
			psInfMsgId.setInt(2, limit);
			if(!"".equals(threadid)){
				psInfMsgId.setString(3, threadid);
			}
			psInfMsgId.executeUpdate();*/
			
			psmt = conn.prepareStatement(sql);
			
			
			
			//informix 
			if (isInformix) {
				psmt.setString(2, undealState);
				psmt.setInt(1, limit);
			}else{//oracle
				psmt.setString(1, undealState);
				psmt.setInt(2, limit);
			}
			
			if(!"".equals(threadid)){
				psmt.setString(3, threadid);
			}
			rs = psmt.executeQuery();
			
			List unDealOrderList = new ArrayList();
//			StringBuffer bf = new StringBuffer();
			updatePsmt = conn.prepareStatement(updateSql);
			while(rs.next()){
				String infMsgId = rs.getString("INF_MSG_ID");
				InfMsg infMsg = new InfMsg();
				infMsg.setInfMagId(infMsgId);
				/*oracle.sql.CLOB clob = (oracle.sql.CLOB)rs.getClob("INF_MSG"); 
				Reader is = clob.getCharacterStream(); 
				BufferedReader br = new BufferedReader(is); 
				StringBuffer bf = new StringBuffer();
				try {
					String s = br.readLine();
					while (s != null){ 
						bf.append(s); 
						s = br.readLine(); 
					} 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				infMsg.setInfMsg(bf.toString());*/
				
				infMsg.setInfMsg(rs.getString("INF_MSG"));
				infMsg.setInfSystem(rs.getString("INF_SYSTEM"));
				infMsg.setState(rs.getString("STATE"));
				infMsg.setCreateDate(DAOUtils.getFormatedDateTime(rs.getTimestamp("CREATE_DATE")));
				infMsg.setStateDate(DAOUtils.getFormatedDateTime(rs.getTimestamp("STATE_DATE")));
				unDealOrderList.add(infMsg);
				
				updatePsmt.setString(1, dealingState);
				updatePsmt.setString(2, infMsgId);
				updatePsmt.addBatch();
			}
			
			if(unDealOrderList.size() > 0){
				updatePsmt.executeBatch();
				return unDealOrderList;
			}

		} catch(SQLException e){
			logger.error("#getUnDealOrders ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(psmt);
			//DAOUtils.closeStatement(psInfMsgId);
			DAOUtils.closeStatement(updatePsmt);
		}
		return null;
	}

	public void updateInfMsgState(String infMagId, boolean success, String resultMsg) throws Exception {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement updatePsmt = null;
		PreparedStatement insertPsmt = null;
		PreparedStatement deletePsmt = null;
		String updateSql = "UPDATE INF_MSG SET STATE_DATE=?,STATE = ?,RESULT_MSG=? WHERE INF_MSG_ID =?";
		String insertSql = "INSERT INTO INF_MSG_L SELECT * FROM INF_MSG WHERE INF_MSG_ID=? ";
		String state = failState;
		if(success) state = successState;
		try{
			updatePsmt = conn.prepareStatement(updateSql);
			updatePsmt.setTimestamp(1, DAOUtils.getCurrentTimestamp());
			updatePsmt.setString(2, state);
			if(resultMsg!= null && resultMsg.length()>500){
				resultMsg = resultMsg.substring(0, 499);
			}
			updatePsmt.setString(3, resultMsg);
			updatePsmt.setString(4, infMagId);
			updatePsmt.executeUpdate();
			if(success){
				insertPsmt = conn.prepareStatement(insertSql);
				insertPsmt.setString(1, infMagId);
				insertPsmt.executeUpdate();
				
				deletePsmt = conn.prepareStatement("DELETE FROM INF_MSG WHERE INF_MSG_ID=?");
				deletePsmt.setString(1, infMagId);
				deletePsmt.executeUpdate();
			}
			
		}catch(Exception e){
			logger.error("#updateInfMsgState ex.",e);
			throw e;
		}finally{
			DAOUtils.closeStatement(updatePsmt);
			DAOUtils.closeStatement(insertPsmt);
			DAOUtils.closeStatement(deletePsmt);
		}
	}

	/**
	 * 先查询产品实例id，然后保存订购关系
	 * @throws ProductInstanceIdNotExists 
	 */
	public void saveOrderRelation(SubscribeToVSOPRequest order, Connection conn) throws SQLException {
		String prodInstId = order.getProdInstId();
		for (Iterator itr = order.getProductOfferInfo().iterator(); itr.hasNext();) {
			ProductOfferInfo productOffer = (ProductOfferInfo) itr.next();
			saveOrderRelation(prodInstId,productOffer,order.getSystemId(), order.getState(), conn, order.getProdSpecCode(),order.getLanCode(), order.getProductNo());
		}
	}

	public boolean saveOrderRelation(String prodInstId,ProductOfferInfo productOffer, String orderChannel, String state, Connection conn, String productId,String lan_id, String accNbr) throws SQLException{
		String prodOfferId = "";
		String pprodOfferId = "";
		String packageId = "";
		PreparedStatement ps = null;
		PreparedStatement deletePs = null;
		int result = 0;
		try {
			ps = conn.prepareStatement(INSERT_ORDER_RELATION_SQL);
			deletePs = conn.prepareStatement("delete from ORDER_RELATION where PROD_INST_ID = ? and PRODUCT_ID=?");
			List vprod = productOffer.getVSubProdInfoList();
			for (Iterator iterator = vprod.iterator(); iterator.hasNext();) {
				VSubProdInfo vproduct = (VSubProdInfo) iterator.next();
				//先删除再新增
				deletePs.setString(1, prodInstId);
				deletePs.setString(2, vproduct.getVProductID());
				deletePs.addBatch();
				
				if (OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID
						.equals(productOffer.getProductOfferType())) {
					pprodOfferId = productOffer.getProductOfferID();
					//prodOfferId = findOfferByProductDb(vproduct.getVProductID(),conn);
					prodOfferId = DcSystemParamManager.getInstance().
						getofferIdByProductId(vproduct.getVProductID());
				} else if (OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID
						.equals(productOffer.getProductOfferType())) {
					packageId = productOffer.getProductOfferID();
					//prodOfferId = findOfferByProductDb(vproduct.getVProductID(),conn);
					prodOfferId = DcSystemParamManager.getInstance().
					getofferIdByProductId(vproduct.getVProductID());
				} else if (OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID
						.equals(productOffer.getProductOfferType())) {
					prodOfferId = vproduct.getProductOfferId();
				}
				int index = 1;
				long seq = getSequence("SEQ_ORDER_RELATION_ID", conn);
				vproduct.setVproductInstId(String.valueOf(seq));
				ps.setLong(index++, seq); //ORDER_RELATION_ID
				ps.setString(index++, prodInstId); //PROD_INST_ID
				ps.setString(index++, vproduct.getVProductID());//PRODUCT_ID
				ps.setString(index++, pprodOfferId);//PPROD_OFFER_ID
				ps.setString(index++, "");//PROD_OFFER_INST_ID
				ps.setString(index++, prodOfferId);//PROD_OFFER_ID,直接写产品标识,这个标识与虚拟销售品标识一致
				ps.setString(index++, orderChannel);//ORDER_CHANNEL
				ps.setString(index++, state);//STATE
				String dateType1 = CrmConstants.DATE_TIME_FORMAT;
				String effdate =productOffer.getEffDate(); //"2010-06-28";  //得到销售品的生失效时间
				if(null!=effdate && !"".equals(effdate)){
					 if( effdate.length()==14)
						dateType1 = CrmConstants.DATE_TIME_FORMAT_14;
				}
				ps.setTimestamp(index++, DAOUtils.parseTimestamp(effdate,dateType1));//EFF_TIME,
				
				String expdate =productOffer.getExpDate(); //"20100629151617";
				String dateType2 = CrmConstants.DATE_TIME_FORMAT;
				if(null!=expdate && !"".equals(expdate)){
					if(expdate.length()==14)
						dateType2 = CrmConstants.DATE_TIME_FORMAT_14;
				}
				ps.setTimestamp(index++, DAOUtils.parseTimestamp(expdate, dateType2));//EXP_TIME,
				ps.setString(index++, packageId);//PACKAGE_ID
				ps.setString(index++, lan_id);//LAN_ID
				ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATE_DATE
				ps
						.setString(index++,
								OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);//待激活
				ps.setString(index++, accNbr);//ACC_NBR
				ps.setString(index++, productId);//PROD_TYPE_CD
				ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//CREATE_DATE
				ps.addBatch();
				//result = ps.executeUpdate();
			}
			if(vprod.size()>0){
				deletePs.executeBatch();
				ps.executeBatch();
			}
		} catch (SQLException e) {
			logger.error("#saveOrderRelation ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
			DAOUtils.closeStatement(deletePs);
		}
		return result > 0;
	}
	public long getSequence(String sequenceName, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		long seq = 0;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT " + sequenceName
					+ ".NEXTVAL FROM DUAL");
			rs = ps.executeQuery();
			if (rs.next()) {
				seq = rs.getLong(1);
			}
		} catch (SQLException e) {
			logger.error("#SQLException ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
			DAOUtils.closeResultSet(rs);
		}
		return seq;
	}

	/**
	 * 根据订单查询增值产品
	 * @param order
	 * @param conn
	 * @throws SQLException 
	 */
	public void findSubProducts(SubscribeToVSOPRequest order, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select r.ORDER_RELATION_ID,r.PRODUCT_ID,r.EFF_DATE,r.EXP_DATE from ORDER_RELATION r " +
				"where r.PACKAGE_ID IS NULL  AND r.PROD_INST_ID in " +
				"(select PROD_INST_ID from PROD_INST pi where pi.ACC_NBR=?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, order.getProductNo());
			rs = ps.executeQuery();
			List subProducts = new ArrayList();
			while (rs.next()) {
				VSubProdInfo subProduct = new VSubProdInfo();
				subProduct.setActionType(order.getActionType());
				subProduct.setEffDate(rs.getString("EFF_DATE"));
				subProduct.setExpDate(rs.getString("EXP_DATE"));
				subProduct.setSequence(rs.getLong("ORDER_RELATION_ID"));
				subProduct.setVProductID(rs.getString("PRODUCT_ID"));
				subProducts.add(subProduct);
			}
			ProductOfferInfo poffer = new ProductOfferInfo();
			poffer.setVSubProdInfoList(subProducts);
			List pofferList = new ArrayList();
			pofferList.add(poffer);
			order.setProductOfferInfo(pofferList);
		} catch (SQLException e) {
			logger.error("#findSubProducts ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
	}
	public void updateIncProductState(SubscribeToVSOPRequest order, Connection conn) throws SQLException {
		StringBuffer bf = new StringBuffer("");
		List prodOfferList = order.getProductOfferInfo();
		for (Iterator iterator = prodOfferList.iterator(); iterator.hasNext();) {
			ProductOfferInfo offer = (ProductOfferInfo) iterator.next();
			List vsubList = offer.getVSubProdInfoList();
			for (Iterator iterator2 = vsubList.iterator(); iterator2.hasNext();) {
				VSubProdInfo subVprod = (VSubProdInfo) iterator2.next();
				bf.append(",").append(subVprod.getVProductID());
			}
		}
		String vprodIds = bf.toString();
		if(null != vprodIds &&!"".equals(vprodIds))
			vprodIds=vprodIds.substring(1);
		logger.info("vprodIds->" + vprodIds);
		if("".equals(vprodIds.trim())) return;
		PreparedStatement ps = null;
		String prodInstId = order.getProdInstId();
		String sql = "UPDATE ORDER_RELATION r SET STATE=?  where r.PROD_INST_ID = ? " +
				" and r.product_id in (" + vprodIds + ")";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, order.getState());
			ps.setString(2, prodInstId);
			//ps.setString(3, order.getProductNo());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#updateIncProductState ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	
	public void addProdInstance(WorkListFKToVSOPRequest order, Connection conn) throws SQLException {
		PreparedStatement ps = null;
//		PreparedStatement lanCodeps = null;
//		ResultSet rs = null;
		String sql = "INSERT INTO PROD_INST(PROD_INST_ID,PRODUCT_ID,COMMON_REGION_ID,PROD_TYPE_CD,PAYMENT_MODE_CD,CREATE_DATE,BEGIN_RENT_TIME,STOP_RENT_TIME,FINISH_TIME,PRODUCT_PASSWORD,STATE_CD,LAN_ID,ACC_NBR,STATE_DATE)" +
				" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			//广西本地化：除宽带，手机其他产品类型的用户号码前面都需要加区号再填入产品实例表的acc_nbr中
//			String proviceCode = CrmParamsConfig.getInstance().getParamValue("proviceCode");
			String accNbr = order.getProductNo();
			//不需要加区号以区分唯一性的产品类型
			/*String noAppendLan = CrmParamsConfig.getInstance().getParamValue("NoAppendLanCode");
			if(proviceCode.equals(CrmConstants.GX_PROV_CODE)){
				if(noAppendLan != null && noAppendLan.indexOf(order.getProdSpecCode()) == -1){
					String lanIdSql = "select lan_code from lan_id=?";
					lanCodeps = conn.prepareStatement(lanIdSql);
					int index = 1;
					lanCodeps.setString(index++, order.getReginID());//广西服开传过来的是本地网ID，需转换成区号
					rs = lanCodeps.executeQuery();
					String lanCode = "";
					if(rs.next()){
						lanCode = rs.getString("lan_code");
					}
					accNbr = lanCode+accNbr;
				}
			}*/
			ps = conn.prepareStatement(sql);
			int index = 1;
			String prodInstId = order.getPorductInstID();
			long pid = 0L;
			if (prodInstId == null || "".equals(prodInstId)) {
				pid = getSequence("SEQ_PROD_INST_ID", conn);
			} else {
				pid = new Long(prodInstId).longValue();
			}
			order.setPorductInstID(String.valueOf(pid));
			ps.setLong(index++, pid);
			ps.setString(index++, order.getProdSpecCode());//PRODUCT_ID,
			ps.setString(index++, order.getReginID());//COMMON_REGION_ID,
			ps.setString(index++, "01");//PROD_TYPE_CD,  
			ps.setString(index++, order.getUserPayType());//PAYMENT_MODE_CD,
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//CREATE_DATE,
			ps.setString(index++, "");//BEGIN_RENT_TIME,
			ps.setString(index++, "");//STOP_RENT_TIME,
			ps.setString(index++, "");//FINISH_TIME,
			ps.setString(index++, "");//PRODUCT_PASSWORD,
			ps.setString(index++, order.getState());//STATE_CD,
			ps.setString(index++, order.getReginID());//LAN_ID,
			ps.setString(index++, accNbr);//ACC_NBR,
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATE_DATE
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#addProdInstance ex.", e);
			throw e;
		}finally{
//			DAOUtils.closeResultSet(rs);
//			DAOUtils.closeStatement(lanCodeps);
			DAOUtils.closeStatement(ps);
		}
	}

	public void addProductOfferInstance(SubscribeToVSOPRequest order, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		String sql = "INSERT INTO PROD_OFFER_INST(PROD_OFFER_INST_ID,PROD_OFFER_ID,OFFER_AGRMT_INST_ID,CREATE_DATE,STATE_CD,EFF_DATE,EXP_DATE,HANDLE_STAFF_ID,CHANNEL_ID,DEVELOP_STAFF_ID,CUST_ID)" +
				" VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			int index = 1;
			long prodOfferId = getSequence("", conn);
			ps.setLong(index++, prodOfferId);
			ps.setString(index++, "");//PROD_OFFER_ID,
			ps.setString(index++, "");//OFFER_AGRMT_INST_ID,
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//CREATE_DATE,
			ps.setString(index++, "");//STATE_CD,
			ps.setString(index++, "");//EFF_DATE,
			ps.setString(index++, "");//EXP_DATE,
			ps.setString(index++, "");//HANDLE_STAFF_ID,
			ps.setString(index++, "");//CHANNEL_ID,
			ps.setString(index++, "");//DEVELOP_STAFF_ID,
			ps.setString(index++, "");//CUST_ID
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#addProductOfferInstance ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}

	public void updateProdInstanceState(String newState, String productNo, String productId,String prodInstId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "UPDATE PROD_INST SET STATE_CD = ?,STATE_DATE=? WHERE prod_inst_id = ? ";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, newState);
			ps.setTimestamp(2, DAOUtils.getCurrentTimestamp());
			ps.setString(3, prodInstId);
//			ps.setString(4, productId);
			ps.executeUpdate();
		} catch (SQLException e) {
			DAOUtils.closeStatement(ps);
			logger.error("#updateProdInstanceState ex.",e);
			throw e;
		}
		finally{
			DAOUtils.closeStatement(ps);
		}
	}

	public void updateProdInstanceNo(String newProductNo, String productNo, String productId,String prodInstId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		PreparedStatement updateRelationPs = null;
		String sql = "UPDATE PROD_INST SET ACC_NBR = ?,product_id=? WHERE prod_inst_id = ? ";
		String updateRelation = "update ORDER_RELATION set ACC_NBR=?,prod_type_cd=? WHERE PROD_INST_ID = ? ";
		try {
			updateRelationPs = conn.prepareStatement(updateRelation);
			updateRelationPs.setString(1, newProductNo);
			updateRelationPs.setString(2, productId);
			updateRelationPs.setString(3, prodInstId);
//			
			updateRelationPs.executeUpdate();
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, newProductNo);
			ps.setString(2, productId);
			ps.setString(3, prodInstId);

			ps.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#updateProdInstanceNo ex.", e);
			throw e;
		}
		finally{
			DAOUtils.closeStatement(ps);
			DAOUtils.closeStatement(updateRelationPs);
		}		
	}

	/**
	 * 附属产品退订
	 * @param aproduct
	 * @param prodInstId 
	 * @param conn 
	 * @throws SQLException 
	 */
	public void deleteAproduct(AProductInfo aproduct, String prodInstId, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE BIZ_CAPABILITY_INST bci SET bci.STATE = ?  where PRODUCT_ID=? and PROD_INST_ID=? and state=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "00X");
			ps.setString(2, aproduct.getAProductID());
			ps.setString(3, prodInstId);
			ps.setString(4, "00A");
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#deleteAproduct ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		
	}
	/**
	 * 附属产品实例
	 * @param aproduct
	 * @param conn 
	 * @param prodInstId 
	 * @throws SQLException 
	 */
	public void addAproduct(AProductInfo aproduct, Connection conn, String prodInstId) throws SQLException  {
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try {
			if (null == prodInstId || "".equals(prodInstId)) {
				String sql1 = "select PROD_INST_ID from PROD_INST where ACC_NBR=?";
				ps1 = conn.prepareStatement(sql1);
				ps1.setString(1, aproduct.getProductNo());
				rs = ps1.executeQuery();
				if (rs.next()) {
					prodInstId = rs.getString(1);
				}
				DAOUtils.closeResultSet(rs);
			}
			if("".equals(aproduct.getAProductInstID())){
				long aproductId = getSequence("SEQ_BIZ_CAPABILITY_INST_ID", conn);
				aproduct.setAProductInstID(String.valueOf(aproductId));
			}
			String sql2 = "insert into BIZ_CAPABILITY_INST(SERV_PRODUCT_ID,PRODUCT_ID,PROD_INST_ID,LAN_ID,STATE) values (?,?,?,?,?)";
			ps2 = conn.prepareStatement(sql2);
			ps2.setString(1, aproduct.getAProductInstID());//SERV_PRODUCT_ID
			ps2.setString(2, aproduct.getAProductID());//PRODUCT_ID,
			ps2.setString(3, prodInstId);//PROD_INST_ID,
			ps2.setString(4, aproduct.getLanCode());//LAN_ID,
			ps2.setString(5, "00A");//STATE
			ps2.executeUpdate();
		} catch (SQLException e) {
			logger.error("#addAproduct ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps1);
			DAOUtils.closeStatement(ps2);
		}
	}

	public String findProdInstIdByProductNo(String productNo, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		String sql = "SELECT PROD_INST_ID FROM PROD_INST WHERE ACC_NBR = ?";
		ResultSet rs = null;
		String prodInstId = null;
		try {
			prodInstId = "";
			ps = conn.prepareStatement(sql);
			ps.setString(1, productNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				prodInstId = rs.getString("PROD_INST_ID");
			}
		} catch (SQLException e) {
			logger.error("#findProdInstIdByProductNo ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return prodInstId;
	}

	public boolean delOrder(SubscribeToVSOPRequest order, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		List ls = order.getProductOfferInfo();
		StringBuffer prodIdStr = new StringBuffer("");
		int idx = 0;
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			ProductOfferInfo poffer = (ProductOfferInfo)iterator.next();
			List vproductList = poffer.getVSubProdInfoList();
			for (Iterator vprodItr = vproductList.iterator(); vprodItr.hasNext();) {
				VSubProdInfo vproduct = (VSubProdInfo) vprodItr.next();
				if(idx == 0 ) {
					prodIdStr.append(vproduct.getVProductID());
				}else{
					prodIdStr.append("," + vproduct.getVProductID());
				}
				idx++;
			}
		}
		String updateSql = "update ORDER_RELATION r set STATE = ?, ACTIVE_STATE=? " +
				" where  PRODUCT_ID in (#toreplace#) " +
				" and PROD_INST_ID =? " +
				" and r.STATE=?";
		String [] sProdIds=(prodIdStr.toString()).split(",");
		String whereCond="";
		if(sProdIds!=null&&sProdIds.length>0){
			for(int j=0;j<sProdIds.length;j++){
				if(j==0){
					whereCond+="?";
				}else{
					whereCond+=",?";
				}
			}
			updateSql=updateSql.replaceAll("#toreplace#", whereCond);
		}else{
			return false;//如果没有退订的增值产品返回失败
		}
		int result;
		try {
			ps = conn.prepareStatement(updateSql);
			int i=1;
			ps.setString(i++, OrderConstant.orderStateOfDelete);
			ps.setString(i++, OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
			for(int j=0;j<sProdIds.length;j++){
				ps.setString(i++,sProdIds[j]);
			}
			ps.setString(i++, order.getProdInstId());
			ps.setString(i++, OrderConstant.orderStateOfCreated);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#delOrder ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	
	public boolean delOrder_fk(WorkListFKToVSOPRequest order, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		List ls = order.getVProductInfoList();
		StringBuffer prodIdStr = new StringBuffer();
		int idx = 0;
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			VProductInfo subProd = (VProductInfo) iterator.next();
			if(idx == 0 ) {
				prodIdStr.append(subProd.getVProductID());
			}else{
				prodIdStr.append("," + subProd.getVProductID());
			}
			idx++;
		}
		String updateSql = "update ORDER_RELATION r set STATE = ?,ACTIVE_STATE=? where  PRODUCT_ID in ("+prodIdStr.toString()+") and PROD_INST_ID in (select PROD_INST_ID from PROD_INST where ACC_NBR = ? ) and r.STATE=?";
		int result = -1;
		try {
			ps = conn.prepareStatement(updateSql);
			ps.setString(1, order.getState());
			ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
			ps.setString(3, order.getProductNo());
			ps.setString(4, OrderConstant.orderStateOfCreated);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#delOrder_fk ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	
		return result > 0;
	}

	public List getReletionsByIds(String relationIds, Connection conn) throws SQLException {
		String sql = "select r.ORDER_RELATION_ID,r.PROD_INST_ID,r.PRODUCT_ID sub_product_id,pi.PRODUCT_ID,pi.ACC_NBR,r.EFF_DATE,r.EXP_DATE" +
				",PPROD_OFFER_ID,PROD_OFFER_ID,PACKAGE_ID" +
				",(select product_nbr from product where product_id=r.product_id) product_nbr" +
				",(select offer_nbr from prod_offer where PROD_OFFER_ID=r.PROD_OFFER_ID) offer_nbr " +
				",(select offer_nbr from prod_offer where PROD_OFFER_ID=r.PACKAGE_ID) package_nbr " +
				",(select offer_nbr from prod_offer where PROD_OFFER_ID=r.PPROD_OFFER_ID) pprod_offer_nbr " +
				"from ORDER_RELATION r left join PROD_INST pi on r.PROD_INST_ID=pi.PROD_INST_ID " +
				"where r.ORDER_RELATION_ID in (" + relationIds + ")";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List result = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			result = new ArrayList();
			while (rs.next()) {
				Map m = new HashMap();
				m.put("ORDER_RELATION_ID", rs.getString("ORDER_RELATION_ID"));
				m.put("PROD_INST_ID", rs.getString("PROD_INST_ID"));
				m.put("PRODUCT_ID", rs.getString("PRODUCT_ID"));
				m.put("product_nbr", rs.getString("product_nbr"));
				m.put("SUB_PRODUCT_ID", rs.getString("sub_product_id"));
				m.put("ACC_NBR", rs.getString("ACC_NBR"));
				m.put("EFF_DATE", rs.getString("EFF_DATE"));
				m.put("EXP_DATE", rs.getString("EXP_DATE"));
				String prodOfferNbr = rs.getString("offer_nbr");
				String packageNbr = rs.getString("package_nbr");
				String pprodOfferNbr = rs.getString("pprod_offer_nbr");
				m.put("PROD_OFFER_NBR", prodOfferNbr);
				m.put("PROD_OFFER_TYPE", OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID);
				if(packageNbr!=null && !"".equals(packageNbr)){
					m.put("PROD_OFFER_NBR", packageNbr);
					m.put("PROD_OFFER_TYPE", OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID);
				}
				if(pprodOfferNbr!=null && !"".equals(pprodOfferNbr)){
					m.put("PROD_OFFER_NBR", pprodOfferNbr);
					m.put("PROD_OFFER_TYPE", OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID);
				}
				m.put("PROD_OFFER_ID", rs.getString("PROD_OFFER_ID"));
				m.put("PACKAGE_ID", rs.getString("PACKAGE_ID"));
				m.put("PPROD_OFFER_ID", rs.getString("PPROD_OFFER_ID"));
				result.add(m);
			}
		} catch (SQLException e) {
			logger.error("#getReletionsByIds ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return result;
	}
	
	/**
	 * 根据销售品id查询该销售品所有的增值产品
	 * @param prodOfferId
	 * @param actionType
	 * @param effDate
	 * @param expDate
	 * @return
	 * @throws SQLException 
	 */
	public List getVProductsByProdOfferId(String prodOfferId, String actionType, String effDate, String expDate) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select a.prod_offer_id, b.product_id,po.offer_nbr,p.product_nbr,po.prod_offer_sub_type from PROD_OFFER_DETAIL_ROLE a, ROLE_PROD_RELA b,prod_offer po,product p  where a.prod_offer_role_cd = b.prod_offer_role_cd and po.prod_offer_id=a.prod_offer_id and b.product_id=p.product_id and a.prod_offer_id = ? ";
		List ret = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, prodOfferId);
			rs = ps.executeQuery();
			ret = new ArrayList();
			while(rs.next()){
				VSubProdInfo subProduct = new VSubProdInfo();
				subProduct.setProductOfferId(prodOfferId);
				subProduct.setProductOfferNbr(rs.getString("offer_nbr"));
				subProduct.setVproductNbr(rs.getString("product_nbr"));
				subProduct.setVProductID(rs.getString("PRODUCT_ID"));
				subProduct.setProductOfferType(rs.getString("prod_offer_sub_type"));
				subProduct.setActionType(actionType);
				subProduct.setEffDate(effDate);
				subProduct.setExpDate(expDate);
				ret.add(subProduct);
			}
		}catch(SQLException e){
			logger.error("#getSubProductsByProdOfferId ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return ret;
	}

	public List subInstQuery(SubInstQueryRequest subInstQueryVo, String state) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		List ret = null;
//		String querySql="select r.product_id, r.state, r.create_date, r.eff_date, r.exp_date, r.order_channel,r.package_id,r.pprod_offer_id," +
//				" p.product_name p_name," +
//				" (select t.partner_id from partner t where t.partner_id=p.product_provider_id ) sp_id," +
//				" (select t.partner_name from partner t where t.partner_id=p.product_provider_id ) sp_name," +
//				" (select pr.prod_offer_id from prod_offer pr where pr.prod_offer_id=r.prod_offer_id) pr_id," +
//				" (select pr.prod_offer_name from prod_offer pr where pr.prod_offer_id=r.prod_offer_id) pr_name," +
//				" (select pr.prod_offer_sub_type from prod_offer pr where pr.prod_offer_id=r.prod_offer_id) pr_type," +
//				" (select pr.chargingpolicy_cn from prod_offer pr where pr.prod_offer_id=r.prod_offer_id) pr_chargeCN " +
//				" from order_relation r inner join product p on p.product_id=r.product_id" +
//				" where r.prod_type_cd =? and r.acc_nbr =? and r.state =? ";
		String querySql="select r.product_id,r.prod_offer_id pr_id, r.state, to_char(r.create_date,'yyyymmddhh24miss') create_date, to_char(r.eff_date,'yyyymmddhh24miss') eff_date, to_char(r.exp_date,'yyyymmddhh24miss') exp_date," +
				" r.order_channel,r.package_id,r.pprod_offer_id, p.product_name p_name," +
		" (select pr.prod_offer_name from prod_offer pr where pr.prod_offer_id = r.prod_offer_id) pr_name_a," +		
		" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.package_id) pr_name_b," + 
		" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.pprod_offer_id) pr_name_c," + 		
		" (select t.partner_id from partner t where t.partner_id = p.product_provider_id ) sp_id," +
		" (select t.partner_name from partner t where t.partner_id = p.product_provider_id ) sp_name," +
		" (select pr.prod_offer_sub_type from prod_offer pr where pr.prod_offer_id = r.prod_offer_id) pr_type," +
		" (select pr.chargingpolicy_cn from prod_offer pr where pr.prod_offer_id = r.prod_offer_id) pr_chargeCN " +
		" from order_relation r inner join product p on p.product_id = r.product_id" +
		" where r.PROD_INST_ID =? and r.state =? ";		
		String systemId = subInstQueryVo.getSystemId();
		if (systemId != null && !"".equals(systemId)) {
//			querySql += " and r.ORDER_CHANNEL = ? ";
		}

		try {
			int index = 1;
			ps = conn.prepareStatement(querySql);
			ps.setString(index++, subInstQueryVo.getProdInstId());
//			ps.setString(index++, subInstQueryVo.getProductNo());
			ps.setString(index++, state);//只是查询00A状态
			if (systemId != null && !"".equals(systemId)) {
//				ps.setString(index++, systemId);// 接口参数修改，增加交易发起系统标识检索条件
			}
			rs = ps.executeQuery();
			ret = new ArrayList();
			while (rs.next()) {
				VproducInfo vProduct = new VproducInfo();
				vProduct.setVproductID(rs.getString("product_id"));
				vProduct.setVproductName(parseOutputString(rs.getString("p_name")));
				vProduct.setSPID(rs.getString("sp_id"));
				vProduct.setSPName(parseOutputString(rs.getString("sp_name")));
				vProduct.setChargingPolicyCN(parseOutputString(rs.getString("pr_chargeCN")));
				vProduct.setStatus("0");//把00A状态下的以0形式返回
				vProduct.setSubscribeTime(parseOutputString(rs.getString("create_date")));
				vProduct.setEffDate(parseOutputString(rs.getString("eff_date")));
				vProduct.setExpDate(parseOutputString(rs.getString("exp_date")));
				vProduct.setChannelPlayerID(parseOutputString(rs.getString("order_channel")));
				//逻辑处理
				//PROD_OFFER_ID不为空
				if(rs.getString("pr_id") !=null && !"".equals(rs.getString("pr_id").trim())){
					vProduct.setProductOfferType("0");
					vProduct.setProductOfferID(rs.getString("pr_id"));
					vProduct.setProductOfferName(parseOutputString(rs.getString("pr_name_a")));					
				}
				//PACKAGE_ID不为空
				if(rs.getString("package_id") !=null && !"".equals(rs.getString("package_id").trim()) ){
					vProduct.setProductOfferType("1");
					vProduct.setProductOfferID(rs.getString("package_id"));
					vProduct.setProductOfferName(parseOutputString(rs.getString("pr_name_b")));		
				}
				//PPROD_OFFER_ID不为空
				if(rs.getString("pprod_offer_id") !=null && !"".equals(rs.getString("pprod_offer_id").trim())){
					vProduct.setProductOfferType("2");
					vProduct.setProductOfferID(rs.getString("pprod_offer_id"));
					vProduct.setProductOfferName(parseOutputString(rs.getString("pr_name_c")));
				}
				ret.add(vProduct);
				
			}
		} catch (SQLException e) {
			logger.error("#subInstQuery ex.", e);
			throw e;
		} finally {
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return ret;
	}

	public Map[] findPlatform(String productids) throws SQLException {
		Map prodMapPlatform = new HashMap();
		Map platformMapProd = new HashMap();
		//cooltan 修改成缓存读取数据
		String [] products=productids.split(",");
		if(products!=null&&products.length>0){
			for(int i=0;i<products.length;i++){
				String productId=products[i];
				List prodPlatforms=DcSystemParamManager.getInstance().getProductPlatformsById(productId);
				prodMapPlatform.put(productId, prodPlatforms);
				if(prodPlatforms!=null){
					for(int j=0;j<prodPlatforms.size();j++){
						String platformId=(String)prodPlatforms.get(j);
						List tmp = (List)platformMapProd.get(platformId);
						if(tmp == null){
							tmp = new ArrayList();
							tmp.add(productId);
							platformMapProd.put(platformId, tmp);
						}else{
							tmp.add(productId);
						}
					}
				}
			}
		}
		
		return new Map[]{prodMapPlatform,platformMapProd};
		/*Connection conn = LegacyDAOUtil.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		Map prodMapPlatform = new HashMap();
		Map platformMapProd = new HashMap();
		String sql = "select PRODUCT_ID,SYSTEM_CODE from PRODUCT_SYSTEM_INFO where PRODUCT_ID in (" + productids + ")";
		try {
			ps = conn.prepareStatement(sql);
		
			rs = ps.executeQuery();
			while(rs.next()){
				String productId = rs.getString("PRODUCT_ID");
				String platformId = rs.getString("SYSTEM_CODE");
				List prodPlatform = (List)prodMapPlatform.get(productId);
				if(prodPlatform == null){
					prodPlatform = new ArrayList();
					prodPlatform.add(platformId);
					prodMapPlatform.put(productId, prodPlatform);
				}else{
					prodPlatform.add(platformId);
				}
				
				List platformProd = (List)platformMapProd.get(platformId);
				if (platformProd == null) {
					platformProd = new ArrayList();
					platformProd.add(productId);
					platformMapProd.put(platformId, platformProd);
				} else {
					platformProd.add(productId);
				}
			}
		} catch (SQLException e) {
			logger.error("#findPlatform ex.",e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return new Map[]{prodMapPlatform,platformMapProd};*/
	}

	public String[] checkRelationType(String productNo, String productId,
			Connection conn) throws SQLException {
		String sql = "Select a.product_id,a.relation_type_cd from PRODUCT_RELATION a, ORDER_RELATION b where a.product_id = b.product_id" +
				" and a.relation_type_cd in ('0', '2') and b.prod_inst_id =(select prod_inst_id from prod_inst where acc_nbr=? ) and a.pro_product_id = ? and b.state='00A'" +
				" union " +
				"Select a.pro_product_id,a.relation_type_cd from PRODUCT_RELATION a, ORDER_RELATION b where a.pro_product_id = b.product_id" +
				" and a.relation_type_cd in ('0', '2') and b.prod_inst_id = (select prod_inst_id from prod_inst where acc_nbr=? ) and a.product_id = ? and b.state='00A'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String relationType;
		String anotherProductId;
		anotherProductId = "";
		relationType = "-1";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, productNo);
			ps.setString(2, productId);
			ps.setString(3, productNo);
			ps.setString(4, productId);
			rs = ps.executeQuery();
			if (rs.next()) {
				anotherProductId = rs.getString("product_id");
				relationType = rs.getString("relation_type_cd");
			}
		} catch (SQLException e) {
			logger.error("#checkRelationType ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return new String[]{anotherProductId,relationType};
	}

	public boolean existOrderRelation(String productNo, String productID,
			Connection conn) throws SQLException {
		String sql = "Select 1 from ORDER_RELATION where acc_nbr=? and PRODUCT_ID=? and STATE in (?,?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, productNo);
			ps.setString(2, productID);
			ps.setString(3, OrderConstant.orderStateOfCreated);
			ps.setString(4, OrderConstant.orderStateOfUnconfirm);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			logger.error("#existOrderRelation ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return false;
	}
	
	public String getProductCodeByProductId(String productId) throws SQLException{
		String sql = "SELECT PRODUCT_NBR FROM PRODUCT where PRODUCT_ID=?";
		String productCode = "";
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, productId);
			rs = ps.executeQuery();
			if(rs.next()){
				productCode = rs.getString("PRODUCT_NBR");
			}
		} catch (SQLException e) {
			logger.error("#getProductCodeByProductId ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return productCode;
	}
	public String getProductIdByProductNo(String productNo) throws SQLException{
		String sql = "SELECT PRODUCT_ID FROM PROD_INST where ACC_NBR=?";
		String productCode = "";
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, productNo);
			rs = ps.executeQuery();
			if(rs.next()){
				productCode = rs.getString("PRODUCT_ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#getProductIdByProductNo ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return productCode;
	}

	public void replaceOrderRelation(String prodInstId, VProductInfo vproduct) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		String sql = "update ORDER_RELATION set PRODUCT_ID = ?,ACTIVE_STATE=? where PROD_INST_ID=? and PRODUCT_ID=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, vproduct.getVProductID());
			ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
			ps.setString(3, prodInstId);
			ps.setString(4, vproduct.getOldVproductId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#replaceOrderRelation ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}

	public void penndingPackage(String prodInstId, VProductInfo vproduct,String pproductOfferId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		String sql = "update ORDER_RELATION set PPROD_OFFER_ID = ?,ACTIVE_STATE=? where PROD_INST_ID=? and PRODUCT_ID=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, pproductOfferId);
			ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
			ps.setString(3, prodInstId);
			ps.setString(4, vproduct.getVProductID());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#penndingPackage ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}

	public void deleleOrderRelation(String prodInstId, VProductInfo vproduct) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		String sql = "update ORDER_RELATION set STATE = ?,ACTIVE_STATE=? where PROD_INST_ID=? and PRODUCT_ID=?";
		PreparedStatement ps = null;
		ps = conn.prepareStatement(sql);
		try {
			ps.setString(1, vproduct.getState());
			ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
			ps.setString(3, prodInstId);
			ps.setString(4, vproduct.getVProductID());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#deleleOrderRelation ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	
	public boolean deleleOrderRelation(String accNbr, String prodSpecCode, String state,String prodInstId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "update ORDER_RELATION set STATE = ? where PROD_INST_ID = ? ";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, state);
			ps.setString(2, prodInstId);
			//ps.setString(3, prodSpecCode);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#deleleOrderRelation",e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return true;
	}

	public void cancelOrderRelationFromPackage(String prodInstId,
			VProductInfo vproduct, String productOfferId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		String sql = "update ORDER_RELATION set PACKAGE_ID = ?,ACTIVE_STATE=? where PROD_INST_ID=? and PRODUCT_ID=? and PACKAGE_ID=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "");
			ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
			ps.setString(3, prodInstId);
			ps.setString(4, vproduct.getVProductID());
			ps.setString(5, productOfferId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#cancelOrderRelationFromPackage ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}

	/**
	 * 
	 * @param infSystem
	 * @param infMsg
	 * @param productNo
	 * @param custOrderId   内部订单标识
	 * @param otherSysOrderId  外部订单标识
	 * @return
	 * @throws SQLException
	 */
	public boolean saveWorkListToInfMsg(String infSystem, String infMsg, String productNo, String custOrderId, String otherSysOrderId) throws SQLException {
		long s=System.currentTimeMillis();
		Connection conn = LegacyDAOUtil.getConnection();
		String sql = "insert into inf_msg(INF_MSG_ID,INF_MSG,INF_SYSTEM,CREATE_DATE,STATE,STATE_DATE,THREAD_ID,CUST_ORDER_ID,OTHER_SYS_ORDER_ID,PARTITION_ID) values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		InputStreamReader ir = null;
		try {
			ps = conn.prepareStatement(sql);
			int index = 1;
			long seq = getSequence("SEQ_INF_MSG_ID", conn);
			ps.setLong(index++, seq);
			if (infMsg != null) {
				ir = new InputStreamReader(new ByteArrayInputStream(infMsg.getBytes()));
				ps.setCharacterStream(index++, ir, infMsg.length());
			} else {
				ps.setNull(index++, java.sql.Types.VARCHAR);
			}
			ps.setString(index++, infSystem);
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setString(index++, undealState);
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setInt(index++, createThreadId(productNo));
			ps.setString(index++, custOrderId);
			ps.setString(index++, otherSysOrderId);
			ps.setInt(index++, DAOUtils.getCurrentMonth());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#saveWorkListToInfMsg",e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
			if(ir != null){
				try {
						ir.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("saveWorkListToInfMsg cost:"+(System.currentTimeMillis()-s));
		return true;
	}

	private int createThreadId(String productNo) {
		String threadIdStringValue = DcSystemParamManager.getParameter(VsopConstants.FK_THREAD_NUM);
		if(null == threadIdStringValue || "".equals(threadIdStringValue)) return 0;
		int threadNum = Integer.parseInt(threadIdStringValue);
		int prodNo = Integer.parseInt(productNo.substring(productNo.length()-1));
		int ret = prodNo % threadNum;
		return ret;
	}
	
	public boolean saveOrderAndOrderItems_fk(WorkListFKToVSOPRequest order) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		String insertSql = DAOSQLUtils.getFilterSQL(SQL_INSERT) ;
		PreparedStatement orderPs = null;
		PreparedStatement subOrderPs = null;
		PreparedStatement vProdOrderPs=null;
		PreparedStatement aProdOrderPs=null;
		try {
			//保存订单
			orderPs = conn.prepareStatement(insertSql);
			int index = 1;
			long custOrderId = getSequence(SEQ_ORDER_INFO, conn);
			order.setSequence(custOrderId);
			orderPs.setLong(index++, custOrderId);//CUST_ORDER_ID
			orderPs.setString(index++, order.getOrderId());//OTHER_SYS_ORDER_ID, 
			orderPs.setString(index++, order.getStreamingNo());//CUST_SO_NUMBER,
			orderPs.setString(index++, order.getActionType());//CUST_ORDER_TYPE,
			orderPs.setString(index++, "");//TIME_NAME_ID,
			orderPs.setString(index++, "");//CUST_ID,
			orderPs.setString(index++, "");//STAFF_ID,
			orderPs.setString(index++, "");//CHANNEL_ID,
			orderPs.setString(index++, order.getState());//STATUS,
			orderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATUS_DATE,
			orderPs.setString(index++, "");//PRE_HANDLE_FLAG,
			orderPs.setString(index++, "");//HANDLE_PEOPLE_NAME,
			orderPs.setString(index++, "");//CONTACT_PHONE,
			orderPs.setString(index++, "");//CONTACT_PEOPLE,
			orderPs.setString(index++, "");//PRIORITY,
			orderPs.setString(index++, "");//REASON,
			orderPs.setString(index++, order.getSystemId());//ORDER_CHANNEL,
			orderPs.setString(index++, order.getSystemId());//ORDER_SYSTEM,
			orderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//RECEIVE_DATE,
			orderPs.setString(index++, "");//DISPOSAL_RESULT,
			orderPs.setString(index++, "");//DISPOSAL_RESULT_DESC,
			orderPs.setString(index++, order.getProductNo());//ACC_NBR,
			orderPs.setString(index++, order.getProdSpecCode());//PRODUCT_ID,
			orderPs.setString(index++, order.getReginID());//LAN_ID
			orderPs.setString(index++, order.getPorductInstID());
			orderPs.setLong(index++, DAOUtils.getCurrentMonth());
			orderPs.executeUpdate();
			insertSql = "insert into ORDER_ITEM (ORDER_ITEM_ID,CUST_ORDER_ID,ORDER_ITEM_CD,ORDER_ITEM_OBJ_ID,CUST_WORKSHEET_ID,STATUS,STATUS_DATE,STATE_CHANGE_REASON,PRIORITY,PRE_HANDLE_FLAG"
					+ ",HANDLE_TIME,ARCHIVE_DATE,FINISH_TIME,PRODUCT_ID,ACC_NBR,PACKAGE_ID,PRODUCT_OFFER_ID,EFF_TIME,EXP_TIME,RESULT_TYPE,RESULT_INFO,LAN_ID,PPROD_OFFER_ID,PROD_INST_ID,SERVICE_OFFER_ID,PARTITION_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+DatabaseFunction.current()+", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
			//保存主产品子订单
			subOrderPs = conn.prepareStatement(insertSql);
			long orderItemId = getSequence(
					VsopSubOrderInfoDAO.SEQUENCE, conn);
			index = 1;
			subOrderPs.setLong(index++, orderItemId);//ORDER_ITEM_ID
			subOrderPs.setLong(index++, custOrderId);//CUST_ORDER_ID,
			subOrderPs.setString(index++,
					OrderConstant.ORDER_ITEM_CD_PRODUCT);//ORDER_ITEM_CD,
			subOrderPs.setString(index++, order.getPorductInstID());//ORDER_ITEM_OBJ_ID,
			subOrderPs.setString(index++, "");//CUST_WORKSHEET_ID,
			subOrderPs.setString(index++, order.getState());//STATUS,
			subOrderPs.setTimestamp(index++, DAOUtils
					.getCurrentTimestamp());//STATUS_DATE,
			subOrderPs.setString(index++, "");//STATE_CHANGE_REASON,
			subOrderPs.setString(index++, "");//PRIORITY,
			subOrderPs.setString(index++, "");//PRE_HANDLE_FLAG,
//			subOrderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//HANDLE_TIME,
			subOrderPs.setString(index++, "");//ARCHIVE_DATE,
			subOrderPs.setString(index++, "");//FINISH_TIME,
			subOrderPs.setString(index++, order.getProdSpecCode());//PRODUCT_ID,
			subOrderPs.setString(index++, order.getProductNo());//ACC_NBR,
			subOrderPs.setString(index++, "");//PACKAGE_ID,
			subOrderPs.setString(index++, "");//PRODUCT_OFFER_ID,
			subOrderPs.setString(index++, "");//EFF_TIME,
			subOrderPs.setString(index++, "");//EXP_TIME,
			subOrderPs.setString(index++, "");//RESULT_TYPE,
			subOrderPs.setString(index++, "");//RESULT_INFO,
			subOrderPs.setString(index++, order.getReginID());//LAN_ID
			subOrderPs.setString(index++, "");//PPROD_OFFER_ID
			subOrderPs.setString(index++, order.getPorductInstID());
			subOrderPs.setString(index++, order.getActionType());
			subOrderPs.setLong(index++, DAOUtils.getCurrentMonth());
			subOrderPs.executeUpdate();
			//保存增值产品子订单
			List vprodList = order.getVProductInfoList();
			if(vprodList != null){
				vProdOrderPs = conn.prepareStatement(insertSql);
				for (Iterator vproductItr = vprodList
						.iterator(); vproductItr.hasNext();) {
					VProductInfo vproduct = (VProductInfo) vproductItr.next();
					vproduct.setOrderItemId(getSequence(
							VsopSubOrderInfoDAO.SEQUENCE, conn));
					index = 1;
					vProdOrderPs.setLong(index++, vproduct.getOrderItemId());//ORDER_ITEM_ID
					vProdOrderPs.setLong(index++, custOrderId);//CUST_ORDER_ID,
					vProdOrderPs.setString(index++,
							OrderConstant.ORDER_ITEM_CD_PRODOFFER);//ORDER_ITEM_CD,
					vProdOrderPs.setString(index++, vproduct.getVProductInstID());//ORDER_ITEM_OBJ_ID,
					vProdOrderPs.setString(index++, "");//CUST_WORKSHEET_ID,
					vProdOrderPs.setString(index++, order.getState());//STATUS,
					vProdOrderPs.setTimestamp(index++, DAOUtils
							.getCurrentTimestamp());//STATUS_DATE,
					vProdOrderPs.setString(index++, "");//STATE_CHANGE_REASON,
					vProdOrderPs.setString(index++, "");//PRIORITY,
					vProdOrderPs.setString(index++, "");//PRE_HANDLE_FLAG,
//					vProdOrderPs.setTimestamp(index++, DAOUtils
//							.getCurrentTimestamp());//HANDLE_TIME,
					vProdOrderPs.setString(index++, "");//ARCHIVE_DATE,
					vProdOrderPs.setString(index++, "");//FINISH_TIME,
					vProdOrderPs.setString(index++, vproduct.getVProductID());//PRODUCT_ID,
					vProdOrderPs.setString(index++, order.getProductNo());//ACC_NBR,
					vProdOrderPs.setString(index++, "");//PACKAGE_ID,
					vProdOrderPs.setString(index++, order.getProductOfferId());//PRODUCT_OFFER_ID,
					vProdOrderPs.setTimestamp(index++, DAOUtils
							.parseTimestamp(vproduct.getEffDate()));//EFF_TIME,
					vProdOrderPs.setTimestamp(index++, DAOUtils
							.parseTimestamp(vproduct.getExpDate()));//EXP_TIME,
					vProdOrderPs.setString(index++, "");//RESULT_TYPE,
					vProdOrderPs.setString(index++, "");//RESULT_INFO,
					vProdOrderPs.setString(index++, order.getReginID());//LAN_ID
					vProdOrderPs.setString(index++, vproduct.getProductOfferId());//PPROD_OFFER_ID
					vProdOrderPs.setString(index++, order.getPorductInstID());
					vProdOrderPs.setString(index++, vproduct.getActionType());
					//vProdOrderPs.executeUpdate();
					vProdOrderPs.addBatch();
				}
				if(vprodList.size() > 0)
					vProdOrderPs.executeBatch();
			}
			//附属产品子订单
			List aprodList = order.getAProductInfoList();
			if(aprodList != null){
				aProdOrderPs = conn.prepareStatement(insertSql);
				for (Iterator aproductItr = aprodList
						.iterator(); aproductItr.hasNext();) {
					AProductInfo aproduct = (AProductInfo) aproductItr.next();
					aproduct.setOrderItemId(getSequence(
							VsopSubOrderInfoDAO.SEQUENCE, conn));
					index = 1;
					aProdOrderPs.setLong(index++, aproduct.getOrderItemId());//ORDER_ITEM_ID
					aProdOrderPs.setLong(index++, custOrderId);//CUST_ORDER_ID,
					aProdOrderPs.setString(index++,OrderConstant.ORDER_ITEM_CD_PRODUCT);//ORDER_ITEM_CD,
					aProdOrderPs.setString(index++, aproduct.getAProductInstID());//ORDER_ITEM_OBJ_ID,
					aProdOrderPs.setString(index++, "");//CUST_WORKSHEET_ID,
					aProdOrderPs.setString(index++, order.getState());//STATUS,
					aProdOrderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATUS_DATE,
					aProdOrderPs.setString(index++, "");//STATE_CHANGE_REASON,
					aProdOrderPs.setString(index++, "");//PRIORITY,
					aProdOrderPs.setString(index++, "");//PRE_HANDLE_FLAG,
//					aProdOrderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//HANDLE_TIME,
					aProdOrderPs.setString(index++, "");//ARCHIVE_DATE,
					aProdOrderPs.setString(index++, "");//FINISH_TIME,
					aProdOrderPs.setString(index++, aproduct.getAProductID());//PRODUCT_ID,
					aProdOrderPs.setString(index++, order.getProductNo());//ACC_NBR,
					aProdOrderPs.setString(index++, "");//PACKAGE_ID,
					aProdOrderPs.setString(index++, "");//PRODUCT_OFFER_ID,
					aProdOrderPs.setString(index++, "");//EFF_TIME,
					aProdOrderPs.setString(index++, "");//EXP_TIME,
					aProdOrderPs.setString(index++, "");//RESULT_TYPE,
					aProdOrderPs.setString(index++, "");//RESULT_INFO,
					aProdOrderPs.setString(index++, order.getReginID());//LAN_ID
					aProdOrderPs.setString(index++, "");//PPROD_OFFER_ID
					aProdOrderPs.setString(index++, order.getPorductInstID());
					aProdOrderPs.setString(index++, aproduct.getActionType());
					//aProdOrderPs.executeUpdate();
					aProdOrderPs.addBatch();
				}
				if(aprodList.size() > 0)
					aProdOrderPs.executeBatch();
			}
		} catch (SQLException e) {
			logger.error("#saveOrderAndOrderItems_fk ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(subOrderPs);
			DAOUtils.closeStatement(orderPs);
			DAOUtils.closeStatement(vProdOrderPs);
			DAOUtils.closeStatement(aProdOrderPs);
		}
		return true;		
	}

	public void updateProdInstPayType(String newPayType, String prodSpecCode,
			String productNo,String prodInstId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("update PROD_INST set PAYMENT_MODE_CD=? where prod_inst_id=?");
			ps.setString(1, newPayType);
			ps.setString(2, prodInstId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#updateProdInstPayType ex.",e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}

	public boolean saveOrderRelation_fk(String systemId, List vprod, String state, String productNo, String productId, String productInstId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		PreparedStatement deletePs = null;
		int index = 1;
		int result = 0;
		try {
			ps = conn.prepareStatement(INSERT_ORDER_RELATION_SQL);
			deletePs = conn.prepareStatement("delete from ORDER_RELATION where PROD_INST_ID = ? and PRODUCT_ID=?");
			for (Iterator iterator = vprod.iterator(); iterator.hasNext();) {
				VProductInfo vproduct = (VProductInfo) iterator.next();
				//先删除再新增
				deletePs.setString(1, productInstId);
				deletePs.setString(2, vproduct.getVProductID());
				
				if("".equals(vproduct.getVProductInstID())){
					long seq = getSequence("SEQ_ORDER_RELATION_ID", conn);
					vproduct.setVProductInstID(String.valueOf(seq));
				}
				Map product_prodOfferMap=DcSystemParamManager.getInstance().getProductId2prodOfferId();
				String prodOfferId = Const.getStrValue(product_prodOfferMap, vproduct.getVProductID());//findOfferByProductDb(vproduct.getVProductID(),conn);
				ps.setString(index++, vproduct.getVProductInstID()); //ORDER_RELATION_ID
				ps.setString(index++, productInstId); //PROD_INST_ID
				ps.setString(index++, vproduct.getVProductID());//PRODUCT_ID
				ps.setString(index++, vproduct.getProductOfferId());//PPROD_OFFER_ID
				ps.setString(index++, "");//PROD_OFFER_INST_ID
				ps.setString(index++, prodOfferId);//PROD_OFFER_ID
				ps.setString(index++, systemId);//ORDER_CHANNEL
				ps.setString(index++,
						(state == null || "".equals(state)) ? vproduct
								.getState() : state);//STATE
				ps.setTimestamp(index++, DAOUtils.parseTimestamp(vproduct
						.getEffDate()));//EFF_TIME,
				ps.setTimestamp(index++, DAOUtils.parseTimestamp(vproduct
						.getExpDate()));//EXP_TIME,
				ps.setString(index++, "");//PACKAGE_ID
				ps.setString(index++, vproduct.getLanCode());//LAN_ID
				ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATE_DATE
				ps
						.setString(index++,
								OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
				ps.setString(index++, productNo); //acc_nbr
				ps.setString(index++, productId); //PROD_TYPE_CD  主产品id
				ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//CREATE_DATE
				deletePs.executeUpdate();
				result = ps.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error("#saveOrderRelation_fk ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
			DAOUtils.closeStatement(deletePs);
		}
		return result > 0;
	}

	public void saveToSecondConfirmMsg(String rqcode, String custOrderId,
			String requestXML, String replayXml, String accNbr) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		InputStreamReader ir = null;
		try {
			ps = conn.prepareStatement("insert into secondconfirm_msg(rqcode,cust_order_id,request_xml,create_date,state,THREAD_ID,SMS_MSG,DEAL_TIME,RESULT_DESC,ACC_NBR,RQResult,reply_count,PARTITION_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			int index = 1;
			ps.setString(index ++, rqcode);
			ps.setString(index++, custOrderId);
			if (requestXML != null) {
				ir = new InputStreamReader(new ByteArrayInputStream(requestXML.getBytes()));
				ps.setCharacterStream(index++, ir, requestXML.length());
			} else {
				ps.setNull(index++, java.sql.Types.VARCHAR);
			}
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setString(index++, ConstantsState.SecondConfirmMsg_UnDeal);
			ps.setString(index++, generateReconfirmThreadId(accNbr));
			ps.setString(index++, replayXml);
			ps.setTimestamp(index++, null);  //DEAL_TIME
			ps.setString(index++, "");  //RESULT_DESC
			ps.setString(index++, accNbr); //ACC_NBR
			ps.setString(index++, ConstantsState.SecondConfirmMsg_Unreply); //RQResult
			ps.setInt(index++, 0);  //reply_count
			ps.setInt(index++, DAOUtils.getCurrentMonth());  
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#saveToSecondConfirmMsg",e);
			throw e;
		}finally{
			if(ir != null){
				try {
					ir.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			DAOUtils.closeStatement(ps);
		}
		
	}

	private String generateUserInfoSyncThreadId(String accNbr) {
		String threadCount = DcSystemParamManager.getParameter(VsopConstants.USERINFO_SYNC_THREAD_NUM);
		if(null == threadCount || "".equals(threadCount)) threadCount = "1";
		return generateThreadId(accNbr,threadCount);
	}
	private String generateReconfirmThreadId(String accNbr) {
		String threadCount = DcSystemParamManager.getParameter(VsopConstants.RECONFIRM_THREAD_NUM);
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
	public String[] getRequestXmlFromSecondConfirmMsg(String code) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select request_xml,CUST_ORDER_ID from secondconfirm_msg where rqcode=? ");
			ps.setString(1, code);
			rs = ps.executeQuery();
			if(rs.next()){
				String req = rs.getString(1);
				String custOrderId = rs.getString(2);
				return new String[]{req,custOrderId};
			}
		} catch (SQLException e) {
			logger.error("#getRequestXmlFromSecondConfirmMsg ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return null;
	}
	/**
	 * 获取销售品下的所有已经订购的增值产品
	 * @param productId 主产品编码
	 * @param productNo 主产品号码
	 * @param productOfferID  销售品编码
	 * @param actionType
	 * @param effDate
	 * @param expDate
	 * @return
	 * @throws SQLException 
	 */
	public List getOrderedVProductsByProdOfferId(String productId,
			String productNo, String productOfferID, String actionType, String effDate, String expDate) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select r.order_relation_id,r.PRODUCT_ID" +
				",(select p.product_nbr from product p where p.product_id=r.product_id) product_nbr" +
				//",(select po.offer_nbr from prod_offer po where po.prod_offer_id=r.PROD_OFFER_ID) offer_nbr" +
				",(select po.prod_offer_sub_type from prod_offer po where po.prod_offer_id=r.PROD_OFFER_ID) prod_offer_sub_type " +
				" from ORDER_RELATION r where r.acc_nbr= ? and r.prod_type_cd = ? and r.PROD_OFFER_ID=? and STATE=?";
		List ret = new ArrayList();
		try {
			ps = conn.prepareStatement(sql);
			int index = 1;
			ps.setString(index++, productNo);
			ps.setString(index++, productId);
			ps.setString(index++, productOfferID);
			ps.setString(index++, OrderConstant.orderStateOfCreated);
			rs = ps.executeQuery();
			while(rs.next()){
				VSubProdInfo subProduct = new VSubProdInfo();
				subProduct.setProductOfferId(productOfferID);
				subProduct.setVproductInstId(rs.getString("order_relation_id"));
				subProduct.setVProductID(rs.getString("PRODUCT_ID"));
				subProduct.setVproductNbr(rs.getString("product_nbr"));
				subProduct.setProductOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(productOfferID));
				subProduct.setProductOfferType(rs.getString("prod_offer_sub_type"));
				subProduct.setActionType(actionType);
				subProduct.setEffDate(effDate);
				subProduct.setExpDate(expDate);
				ret.add(subProduct);
			}
			
		} catch (SQLException e) {
			logger.error("#getOrderedVProductsByProdOfferId ex.",e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return ret;
	}
	public Map getOrderedVproducts(String prodSpecCode, String productNo,
			String actionType) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List pprodofferList = new ArrayList();
		List vproductList = new ArrayList();
		String sql = "select r.ORDER_RELATION_ID,r.PRODUCT_ID,r.PROD_OFFER_ID,r.PACKAGE_ID,r.PPROD_OFFER_ID" +
				",(select po.PROD_OFFER_NAME from PROD_OFFER po where po.PROD_OFFER_ID=r.PPROD_OFFER_ID) PPROD_OFFER_NAME" +
				//",(select p.PRODUCT_NAME from PRODUCT p where p.PRODUCT_ID=r.PRODUCT_ID) PRODUCT_NAME" +
				//",(select po.OFFER_NBR from PROD_OFFER po where po.PROD_OFFER_ID=r.PROD_OFFER_ID) offer_nbr" +
				//",(select po.OFFER_NBR from PROD_OFFER po where po.PROD_OFFER_ID=r.PACKAGE_ID) package_nbr" +
				",(select p.PRODUCT_Nbr from PRODUCT p where p.PRODUCT_ID=r.PRODUCT_ID) product_nbr " +
				"from ORDER_RELATION r where r.acc_nbr= ? and r.prod_type_cd = ?  and r.STATE=?";
		try {
			ps = conn.prepareStatement(sql);
			int index = 1;
			ps.setString(index++, productNo);
			ps.setString(index++, prodSpecCode);
			ps.setString(index++, OrderConstant.orderStateOfCreated);
			rs = ps.executeQuery();
			while(rs.next()){
				
				String productId = rs .getString("PRODUCT_ID");
				String productName =DcSystemParamManager.getInstance().getProductnameById(productId);
				String pprodOfferId = rs.getString("PPROD_OFFER_ID");
				String prodOfferId = rs.getString("PROD_OFFER_ID");
				String packageId = rs.getString("PACKAGE_ID");
				String pprodOfferName =rs.getString("PPROD_OFFER_NAME");
				String offerNbr = DcSystemParamManager.getInstance().getProdOfferNbrById(prodOfferId);//rs.getString("offer_nbr");
				String packageNbr =DcSystemParamManager.getInstance().getProdOfferNbrById(packageId); //rs.getString("package_nbr");
				String productNbr = rs.getString("product_nbr");
				String ORDER_RELATION_ID = rs.getString("ORDER_RELATION_ID");
				Map tmp = new HashMap();
				tmp.put("ORDER_RELATION_ID", ORDER_RELATION_ID );
				tmp.put("PRODUCT_ID", productId);
				tmp.put("PRODUCT_NAME", productName);
				tmp.put("PPROD_OFFER_ID", pprodOfferId);
				tmp.put("PPROD_OFFER_NAME", pprodOfferName);
				tmp.put("SPProdSpecID", productId);
				tmp.put("ActionType", actionType);
				tmp.put("ProductOfferId", prodOfferId);
				tmp.put("OfferNbr", offerNbr);
				tmp.put("ProductNbr", productNbr);
				tmp.put("ProductOfferType", OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID);
				if(packageId != null && !"".equals(packageId)){
					tmp.put("ProductOfferId", packageId);
					tmp.put("OfferNbr", packageNbr);
					tmp.put("ProductOfferType", OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID);
				}
				if(pprodOfferId != null && !"".equals(pprodOfferId)){
					pprodofferList.add(tmp);
				}else{
					vproductList.add(tmp);
				}
			}
		} catch (SQLException e) {
			logger.error("#getOrderedVproducts ex.",e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		Map retMap = new HashMap();
		retMap.put("pprodofferList", pprodofferList);
		retMap.put("vproductList", vproductList);
		return retMap;
	}
	/**
	 * 取x平台web service 地址
	 * @param prodSpecCode  产品编码
	 * @param productNo  产品号码
	 * @return
	 * @throws SQLException 
	 */
	public List getXplatformServiceUrl(String prodSpecCode, String productNo,String prodInstId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List serviceUrl = new ArrayList();
		String sql = "select distinct(c.service_url) from order_relation a,PRODUCT_SYSTEM_INFO b,SYSTEM_INFO c " +
				"where a.product_id=b.product_id and b.system_code=c.system_code and c.is_user_state=1 and a.prod_inst_id=? and a.STATE =?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, prodInstId);
			ps.setString(2, OrderConstant.orderStateOfCreated);
			rs = ps.executeQuery();
			while(rs.next()){
				String url = rs.getString("service_url");
				serviceUrl.add(url);
			}
		} catch (SQLException e) {
			logger.error("#getXplatformServiceUrl ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return serviceUrl;
	}
	public void initLanCodeAndProdInstId(SubscribeToVSOPRequest order) throws SQLException {
		String getProdInstId = "SELECT PROD_INST_ID,LAN_ID,PRODUCT_ID from PROD_INST where ACC_NBR = ? and STATE_CD in(?,?)";
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(getProdInstId);
			ps.setString(1, order.getProductNo());
			ps.setString(2, OrderConstant.orderStateOfCreated);
			ps.setString(3, OrderConstant.orderStateOfUnconfirm);
			rs = ps.executeQuery();
			if(rs.next()){
				order.setProdInstId(rs.getString(1));
				order.setLanCode(rs.getString(2));
				order.setProdSpecCode(rs.getString(3));
			}
		} catch (SQLException e) {
			logger.error("#initLanCodeAndProdInstId ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
	}
	public String[] getProductInstId(String productNo, String prodSpecCode) throws SQLException {
		PreparedStatement psProdId = null;
		ResultSet rs = null;
		Connection conn = LegacyDAOUtil.getConnection();
		String prodInstId = "";
		String lanCode = "";
		String userState = "";
		String payMode="";
		try{
			String getProdInstId = "SELECT PROD_INST_ID,LAN_ID,STATE_CD,PAYMENT_MODE_CD,PRODUCT_ID from PROD_INST where ACC_NBR = ? and PRODUCT_ID=? and STATE_CD in(?,?)";
			psProdId = conn.prepareStatement(getProdInstId);
			psProdId.setString(1, productNo);
			psProdId.setString(2, prodSpecCode);
			psProdId.setString(3, OrderConstant.orderStateOfCreated);
			psProdId.setString(4, OrderConstant.orderStateOfUnconfirm);
			rs = psProdId.executeQuery();
			if(rs.next()){
				prodInstId = rs.getString(1);
				lanCode = rs.getString(2);
				userState=rs.getString(3);
				payMode=rs.getString(4);
			}
			
		}catch(SQLException e){
			logger.error("#saveOrderRelation ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(psProdId);
		}
		return new String[]{prodInstId,lanCode,userState,payMode};
	}
	public String getProductId(String vproductNbr) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String productId = "";
		String sql = "SELECT PRODUCT_ID FROM PRODUCT WHERE PRODUCT_NBR=?";
		try {
			ps = conn.prepareStatement(sql );
			ps.setString(1, vproductNbr);
			rs = ps.executeQuery();
			if(rs.next()){
				productId = rs.getString("PRODUCT_ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#getProductId ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return productId;
	}
	public String getProductOfferId(String offNbr) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT PROD_OFFER_ID FROM PROD_OFFER WHERE OFFER_NBR=?";
		String productOfferId = "";
		try {
			ps = conn.prepareStatement(sql );
			ps.setString(1, offNbr);
			rs = ps.executeQuery();
			if(rs.next()){
				productOfferId = rs.getString("PROD_OFFER_ID");
			}
		} catch (SQLException e) {
			logger.error("#getProductOfferId ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return productOfferId;
	}
	public void saveSpOutMsgFeedback(String outOrderId, long orderId, String systemId, String streamingNo, String msg, String productNo) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		InputStreamReader ir = null;
		String sql = "insert into sp_out_msg_feedback (ID, IN_TIME, ORDER_ID, OIUT_ORDER_ID, OUT_STREAM_NO, ORDER_RESULT, MSG, FEEBACK_RESULT, FEEBACK_MSG, STATE, FEEBACK_DESC, FEEBACK_TIME, INT_SYS_ID,THREAD_ID,writer,prod_no)" +
				" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			
			ps = conn.prepareStatement(sql );
			long id = getSequence("sp_out_msg_feedback_seq", conn);
			int index = 1;
			ps.setLong(index ++, id);//ID, 
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//IN_TIME, 
			ps.setLong(index++, orderId);//ORDER_ID, 
			ps.setString(index++, outOrderId);//OIUT_ORDER_ID, 
			ps.setString(index++, streamingNo);//OUT_STREAM_NO, 
			ps.setString(index++, "");//ORDER_RESULT, 
			ps.setString(index++, msg);
			ps.setString(index++, "");//FEEBACK_RESULT, 
			ps.setString(index++, "");//FEEBACK_MSG, 
			ps.setString(index++, "0");//STATE, 
			ps.setString(index++, "");//FEEBACK_DESC, 
			ps.setString(index++, "");//FEEBACK_TIME, 
			ps.setString(index++, systemId);//INT_SYS_ID
			ps.setInt(index++, createThreadId(productNo));
			ps.setInt(index++, 1);
			ps.setString(index++, productNo);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#saveSpOutMsgFeedback ex.", e);
			throw e;
		}finally{
			if(ir != null){
				try {
					ir.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			DAOUtils.closeStatement(ps);
		}
	}

	/**
	 * 根据产品找基础销售品
	 * @param order
	 * @param conn
	 * @throws SQLException 
	 */
	public String findOfferByProductDb(String productId, Connection conn) throws SQLException {
		String prodOfferId = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select a.prod_offer_id "+
					  "from PROD_OFFER_DETAIL_ROLE a, ROLE_PROD_RELA b,PROD_OFFER c "+
					 "where a.prod_offer_role_cd = b.prod_offer_role_cd and a.prod_offer_id=c.prod_offer_id "+
					   "and b.product_id =? and c.prod_offer_sub_type='0'";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, productId);
			rs = ps.executeQuery();
			if (rs.next()) {
				prodOfferId = rs.getString("prod_offer_id");
			}
		} catch (SQLException e) {
			logger.error("#findSubProducts ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return prodOfferId;
	}
	public List getUnDealFeedbacks(int limit, String threadId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement selectPs = null;
		//PreparedStatement insertPs = null;
		PreparedStatement updatePs = null;
		ResultSet rs = null;
		List ret = new ArrayList();
		
		
		
		String selectSql = "select ID, IN_TIME, ORDER_ID, OIUT_ORDER_ID, OUT_STREAM_NO, ORDER_RESULT, MSG, FEEBACK_RESULT, FEEBACK_MSG, STATE, FEEBACK_DESC, FEEBACK_TIME, INT_SYS_ID,writer from sp_out_msg_feedback " +
				"where state = ?  and rownum < ? "+("".equals(threadId)? "" : " and thread_id = ?")+" order by id";
		
		if(isInformix){
			 selectSql = "select first ? ID, IN_TIME, ORDER_ID, OIUT_ORDER_ID, OUT_STREAM_NO, ORDER_RESULT, MSG, FEEBACK_RESULT, FEEBACK_MSG, STATE, FEEBACK_DESC, FEEBACK_TIME, INT_SYS_ID,writer from sp_out_msg_feedback " +
			"where state = ? "+("".equals(threadId)? "" : " and thread_id = ?")+" order by id";
		}
		//String insertSql = "insert into TMP_ID(id) select id from sp_out_msg_feedback where state = ?  and rownum < ? "+("".equals(threadId)? "" : " and thread_id = ?")+" order by id";
		String updateSql = "update sp_out_msg_feedback set state=? where id =? ";
		try {
			/*insertPs = conn.prepareStatement(insertSql);
			insertPs.setString(1, "0");
			insertPs.setInt(2, limit);
			if(!"".equals(threadId)){
				insertPs.setString(3, threadId);
			}
			insertPs.executeUpdate();*/
			updatePs = conn.prepareStatement(updateSql);
			
			selectPs = conn.prepareStatement(selectSql );
			
			if(isInformix){
				selectPs.setInt(1, limit);
				selectPs.setString(2, "0");
			}else{
				selectPs.setString(1, "0");
				selectPs.setInt(2, limit);
			}
			
			
			if(!"".equals(threadId)){
				selectPs.setString(3, threadId);
			}
			rs = selectPs.executeQuery();
			while(rs.next()){
				SpOutMsgFeedback feeback = new SpOutMsgFeedback();
				String id = rs.getString("ID");
				feeback.setId(id);
				feeback.setInTime(rs.getString("IN_TIME"));
				feeback.setOrderId(rs.getString("ORDER_ID"));
				feeback.setStreamNo(rs.getString("OUT_STREAM_NO"));
				feeback.setMsg(rs.getString("MSG"));
				feeback.setWriter(rs.getString("writer"));
				ret.add(feeback);
				updatePs.setString(1, "-1");
				updatePs.setString(2, id);
				updatePs.addBatch();
			}
			
			if(null!=ret && ret.size()>0){
				updatePs.executeBatch();
			}
		} catch (SQLException e) {
			logger.error("#getUnDealFeedbacks ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			//DAOUtils.closeStatement(insertPs);
			DAOUtils.closeStatement(selectPs);
			DAOUtils.closeStatement(updatePs);
		}
		return ret;
	}
	public void updateSecondConFirmMsg(String code, String state) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "update secondconfirm_msg set state = ? where RQCODE = ?";
		try {
			ps = conn.prepareStatement(sql );
			ps.setString(1, state);
			ps.setString(2, code);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#updateSecondConFirmMsg ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	public String createRQCode() throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		long seq = getSequence("seq_secondconfirm_msg_id", conn );
		return String.valueOf(seq);
	}
	public void updateOrderAndOrderRelationState(SubscribeToVSOPRequest order, String state) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
//		PreparedStatement orderPs = null;
		PreparedStatement itemPs = null;
		PreparedStatement relationPs = null;
//		String updateOrderSql = "update customer_order set STATUS = ? where CUST_ORDER_ID = ?";
		String updateItemSql = "update order_item set STATUS=? where CUST_ORDER_ID=? and ORDER_ITEM_OBJ_ID=?";
		String relationSql = "update ORDER_RELATION set STATE=? where ORDER_RELATION_ID = ?";
		try {
//			orderPs = conn.prepareStatement(updateOrderSql );
//			orderPs.setString(1, state);
//			orderPs.setLong(2, order.getSequence());
//			orderPs.executeUpdate();
			
			itemPs = conn.prepareStatement(updateItemSql);
			
			relationPs = conn.prepareStatement(relationSql);
			List offerList = order.getProductOfferInfo();
			for (Iterator iterator = offerList.iterator(); iterator.hasNext();) {
				ProductOfferInfo prodOffer = (ProductOfferInfo) iterator.next();
				List vprodList = prodOffer.getVSubProdInfoList();
				for (Iterator iterator2 = vprodList.iterator(); iterator2
						.hasNext();) {
					VSubProdInfo vprod = (VSubProdInfo) iterator2.next();
					
					itemPs.setString(1, state);
					itemPs.setLong(2, order.getSequence());
					itemPs.setString(3, vprod.getVproductInstId());
					itemPs.executeUpdate();
					
					int index = 1;
					relationPs.setString(index++, state);
					relationPs.setString(index++, vprod.getVproductInstId());
					relationPs.executeUpdate();
					
				}
			}
			
		} catch (SQLException e) {
			logger.error("#updateOrderAndOrderRelationState ex.", e);
			throw e;
		}finally{
//			DAOUtils.closeStatement(orderPs);
			DAOUtils.closeStatement(itemPs);
			DAOUtils.closeStatement(relationPs);
		}
	}
	public void updateFeedbackAndArchive(SpOutMsgFeedback feedback) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		PreparedStatement insertPsmt = null;
		PreparedStatement deletePsmt = null;
		InputStreamReader ir = null;
		String sql = "update sp_out_msg_feedback set FEEBACK_RESULT = ?, FEEBACK_MSG = ?,STATE=?,FEEBACK_DESC=?,FEEBACK_TIME=? " +
				"where id=?";
		String insertSql = "INSERT INTO sp_out_msg_feedback_HIS SELECT * FROM sp_out_msg_feedback WHERE ID=? ";
		String feebackid = feedback.getId();
		try {
			int index = 1;
			ps = conn.prepareStatement(sql );
			ps.setString(index++, feedback.getFeedbackResult());
			String feedbackMsg = feedback.getFeedbackMsg();
			if (feedbackMsg != null) { //MSG, 
				ir = new InputStreamReader(new ByteArrayInputStream(feedbackMsg.getBytes()));
				ps.setCharacterStream(index++, ir, feedbackMsg.length());
			} else {
				ps.setNull(index++, java.sql.Types.VARCHAR);
			}
			ps.setString(index++, (feedback.getFeedbackResult().equals("0")? "1" : "2"));
			ps.setString(index++, feedback.getFeedbackDesc());
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setString(index++, feebackid);
			ps.executeUpdate();
			
			if(feedback.getFeedbackResult().equals("0")){
				insertPsmt = conn.prepareStatement(insertSql);
				insertPsmt.setString(1, feebackid);
				insertPsmt.executeUpdate();
				
				deletePsmt = conn.prepareStatement("DELETE FROM sp_out_msg_feedback WHERE ID=?");
				deletePsmt.setString(1, feebackid);
				deletePsmt.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error("#updateFeedback ex.", e);
			throw e;
		}finally{
			if(ir != null){
				try {
					ir.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			DAOUtils.closeStatement(ps);
			DAOUtils.closeStatement(insertPsmt);
			DAOUtils.closeStatement(deletePsmt);
		}
		
	}
	public void archiveOrder(String orderId) throws SQLException {
		
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement insertPs = null;
		PreparedStatement insertItemPs = null;
		PreparedStatement deletePs = null;
		PreparedStatement deleteItemPs = null;
		String insertSql = "insert into customer_order_his(CUST_ORDER_ID, OTHER_SYS_ORDER_ID, CUST_SO_NUMBER, CUST_ORDER_TYPE, TIME_NAME_ID, CUST_ID, STAFF_ID, CHANNEL_ID, STATUS, STATUS_DATE, PRE_HANDLE_FLAG, HANDLE_PEOPLE_NAME, CONTACT_PHONE, CONTACT_PEOPLE, PRIORITY, REASON, ORDER_CHANNEL, ORDER_SYSTEM, RECEIVE_DATE, DISPOSAL_RESULT, DISPOSAL_RESULT_DESC, ACC_NBR, PRODUCT_ID, LAN_ID, PROD_INST_ID) select CUST_ORDER_ID, OTHER_SYS_ORDER_ID, CUST_SO_NUMBER, CUST_ORDER_TYPE, TIME_NAME_ID, CUST_ID, STAFF_ID, CHANNEL_ID, STATUS, STATUS_DATE, PRE_HANDLE_FLAG, HANDLE_PEOPLE_NAME, CONTACT_PHONE, CONTACT_PEOPLE, PRIORITY, REASON, ORDER_CHANNEL, ORDER_SYSTEM, RECEIVE_DATE, '成功', DISPOSAL_RESULT_DESC, ACC_NBR, PRODUCT_ID, LAN_ID, PROD_INST_ID from customer_order c where c.cust_order_id=? ";
		String deleleOrderSql = "delete from customer_order c where c.cust_order_id=?";
		String insertItemSql = "insert into order_item_his(ORDER_ITEM_ID, CUST_ORDER_ID, ORDER_ITEM_CD, ORDER_ITEM_OBJ_ID, CUST_WORKSHEET_ID, STATUS, STATUS_DATE, STATE_CHANGE_REASON, PRIORITY, PRE_HANDLE_FLAG, HANDLE_TIME, ARCHIVE_DATE, FINISH_TIME, PRODUCT_ID, ACC_NBR, PACKAGE_ID, PRODUCT_OFFER_ID, EFF_TIME, EXP_TIME, RESULT_TYPE, RESULT_INFO, LAN_ID, PPROD_OFFER_ID, PROD_INST_ID, SERVICE_OFFER_ID) select ORDER_ITEM_ID, CUST_ORDER_ID, ORDER_ITEM_CD, ORDER_ITEM_OBJ_ID, CUST_WORKSHEET_ID, STATUS, STATUS_DATE, STATE_CHANGE_REASON, PRIORITY, PRE_HANDLE_FLAG, HANDLE_TIME, "+DatabaseFunction.current()+", "+DatabaseFunction.current()+", PRODUCT_ID, ACC_NBR, PACKAGE_ID, PRODUCT_OFFER_ID, EFF_TIME, EXP_TIME, RESULT_TYPE, RESULT_INFO, LAN_ID, PPROD_OFFER_ID, PROD_INST_ID, SERVICE_OFFER_ID from order_item a where a.CUST_ORDER_ID=?";
		String deleteItemSql = "delete from order_item a where a.CUST_ORDER_ID=?";
		try {
			insertPs = conn.prepareStatement(insertSql );
			insertPs.setString(1, orderId);
			insertPs.executeUpdate();
			
			deletePs = conn.prepareStatement(deleleOrderSql);
			deletePs.setString(1, orderId);
			deletePs.executeUpdate();
			
			insertItemPs = conn.prepareStatement(insertItemSql);
			insertItemPs.setString(1, orderId);
			insertItemPs.executeUpdate();
			
			deleteItemPs = conn.prepareStatement(deleteItemSql);
			deleteItemPs.setString(1, orderId);
			deleteItemPs.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("#archivingOrder ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(insertPs);
			DAOUtils.closeStatement(deletePs);
			DAOUtils.closeStatement(insertItemPs);
			DAOUtils.closeStatement(deleteItemPs);
		}
	}
	public String getVprodInstId(Connection conn, String vproductID,
			String productNo, String productId2) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select ORDER_RELATION_ID from ORDER_RELATION where PRODUCT_ID=? and PROD_TYPE_CD=? and ACC_NBR=?";
		String vprodInstId = "";
		try {
			ps = conn.prepareStatement(sql );
			ps.setString(1, vproductID);
			ps.setString(2, productId2);
			ps.setString(3, productNo);
			rs = ps.executeQuery();
			if(rs.next()){
				vprodInstId = rs.getString("ORDER_RELATION_ID");
			}
		} catch (SQLException e) {
			logger.error("#getVprodInstId ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return vprodInstId;
	}
	public List getUnDealHBRecords(int limit, String threadId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement psmt = null;
		PreparedStatement updatePsmt = null;
		ResultSet rs = null;
		//PreparedStatement psId = null;
		//String insertSql = "insert into TMP_ID(id) select ID from SP_OUT_MSG_SYNHB where STATE=? and rownum<=?" + ("".equals(threadId) ? "" : " and THREAD_ID=?") + " order by id";
		List ret = new ArrayList();
		try {
			/*psId = conn.prepareStatement(insertSql );
			psId.setString(1, "0");
			psId.setInt(2, limit);
			if(!"".equals(threadId)){
				psId.setString(3, threadId);
			}
			psId.executeUpdate();*/
			
			String updateSql = "update SP_OUT_MSG_SYNHB set STATE=? where id =? ";
			updatePsmt = conn.prepareStatement(updateSql );
			
			
			
			String selectSql = "select * from (select ID, IN_TIME, ORDER_ID, OIUT_ORDER_ID, OUT_STREAM_NO, ORDER_RESULT, MSG, FEEBACK_RESULT, FEEBACK_MSG, STATE, FEEBACK_DESC, FEEBACK_TIME, INT_SYS_ID, THREAD_ID " +
					" from SP_OUT_MSG_SYNHB where STATE=? " + ("".equals(threadId) ? "" : " and THREAD_ID=?") + " order by id ) where rownum<=?" ;
			
			if (isInformix) {
				selectSql = "select first ? * from (select ID, IN_TIME, ORDER_ID, OIUT_ORDER_ID, OUT_STREAM_NO, ORDER_RESULT, MSG, FEEBACK_RESULT, FEEBACK_MSG, STATE, FEEBACK_DESC, FEEBACK_TIME, INT_SYS_ID, THREAD_ID " +
				" from SP_OUT_MSG_SYNHB where STATE=? " + ("".equals(threadId) ? "" : " and THREAD_ID=?") + " order by id ) " ;
			}
			
			psmt = conn.prepareStatement(selectSql );
			int index =1;
			
			if (isInformix) {
				psmt.setInt(index++, limit);
				psmt.setString(index++, "0");
				if(!"".equals(threadId)){
					psmt.setString(index++, threadId);
				}
			}else{
				psmt.setString(index++, "0");
				if(!"".equals(threadId)){
					psmt.setString(index++, threadId);
				}
				psmt.setInt(index++, limit);
			}
			
			System.out.println(" aaaaa                        "+selectSql);
			rs = psmt.executeQuery();
			while(rs.next()){
				MsgSynHB synHb = new MsgSynHB();
				String id =rs.getString("ID");
				synHb.setId(id);
				synHb.setOrderId(rs.getString("ORDER_ID"));
				synHb.setOutOrderId(rs.getString("OIUT_ORDER_ID"));
				synHb.setOutStreamNo(rs.getString("OUT_STREAM_NO"));
				synHb.setOrderResult(rs.getString("ORDER_RESULT"));
				synHb.setMsg(rs.getString("MSG"));
				synHb.setFeebackResult(rs.getString("FEEBACK_RESULT"));
				synHb.setFeebackMsg(rs.getString("FEEBACK_MSG"));
				synHb.setFeebackDesc(rs.getString("FEEBACK_DESC"));
				synHb.setIntSysId(rs.getString("INT_SYS_ID"));
				synHb.setThreadId(rs.getString("THREAD_ID"));
				ret.add(synHb);
				updatePsmt.setString(1, "-1");
				updatePsmt.setString(2, id);
				updatePsmt.addBatch();
			}
			if(null !=ret && ret.size()>0)
				updatePsmt.executeBatch();
		} catch (SQLException e) {
			logger.error("#getUnDealHBRecords ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			//DAOUtils.closeStatement(psId);
			DAOUtils.closeStatement(updatePsmt);
			DAOUtils.closeStatement(psmt);
		}
		return ret;
	}
	public void updateSynHbMsgArchive(MsgSynHB synHb) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		PreparedStatement insertPsmt = null;
		PreparedStatement deletePsmt = null;
		InputStreamReader ir = null;
		String sql = "update SP_OUT_MSG_SYNHB set FEEBACK_RESULT=?,FEEBACK_MSG=?,STATE=?,FEEBACK_DESC=?,FEEBACK_TIME=? where ID=?";
		String insertSql = "INSERT INTO SP_OUT_MSG_SYNHB_HIS SELECT * FROM SP_OUT_MSG_SYNHB WHERE ID=? ";
		try {
			String synid = synHb.getId();
			ps = conn.prepareStatement(sql );
			int index = 1;
			ps.setString(index ++, synHb.getFeebackResult());
			ps.setString(index++, synHb.getFeebackMsg());
			ps.setString(index++, (synHb.getFeebackResult().equals("0") ? "1" : "-1"));
			ps.setString(index++, synHb.getFeebackDesc());
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setString(index++, synid);
			ps.executeUpdate();
			if(synHb.getFeebackResult().equals("0")){
				insertPsmt = conn.prepareStatement(insertSql);
				insertPsmt.setString(1, synid);
				insertPsmt.executeUpdate();
				
				deletePsmt = conn.prepareStatement("DELETE FROM SP_OUT_MSG_SYNHB WHERE ID=?");
				deletePsmt.setString(1, synid);
				deletePsmt.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error("updateSynHbMsg ex.", e);
			throw e;
		}finally{
			if(ir != null){
				try {
					ir.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			DAOUtils.closeStatement(ps);
			DAOUtils.closeStatement(insertPsmt);
			DAOUtils.closeStatement(deletePsmt);
		}
		
	}
	/**
	 * 计费调用接口失败或者保存失败
	 * @param synHb
	 * @throws SQLException
	 */
	public void failSynHbMsg(MsgSynHB synHb) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "update SP_OUT_MSG_SYNHB set FEEBACK_RESULT=?,FEEBACK_MSG=?,STATE=?,FEEBACK_DESC=?,FEEBACK_TIME=? where ID=?";
		try {
			String synid = synHb.getId();
			ps = conn.prepareStatement(sql );
			int index = 1;
			ps.setString(index ++, synHb.getFeebackResult());
			ps.setString(index++, synHb.getFeebackMsg());
			ps.setString(index++, "-1"); //-1为处理失败
			ps.setString(index++, synHb.getFeebackDesc());
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setString(index++, synid);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("updateSynHbMsg ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		
	}		

	private String parseOutputString(String filed){
		if("".equals(filed) || filed == null)
			return "";
		else return filed;
	}
	public String updateSecondMsgAndReturnOrderId(String code, String result) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String custOrderId = "";
		PreparedStatement updPs = null;
		try {
			ps = conn.prepareStatement("SELECT CUST_ORDER_ID FROM SECONDCONFIRM_MSG WHERE RQCODE=?");
			ps.setString(1, code);
			rs = ps.executeQuery();
			if(rs.next()){
				custOrderId = rs.getString("CUST_ORDER_ID");
			}
			
			updPs = conn.prepareStatement("UPDATE SECONDCONFIRM_MSG SET RQResult = ?,reply_time=? WHERE RQCODE=?");
			updPs.setString(1, result);
			updPs.setTimestamp(2, DAOUtils.getCurrentTimestamp());
			updPs.setString(3, code);
			updPs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("updateSecondMsgAndReturnOrderId ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
			DAOUtils.closeStatement(updPs);
		}
		
		return custOrderId;
	}
	public boolean isLastMsg(String custOrderId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long count = 0;
		try {
			ps = conn.prepareStatement("select count(*) as count from SECONDCONFIRM_MSG where CUST_ORDER_ID = ? and RQResult=?");
			ps.setString(1, custOrderId);
			ps.setString(2, ConstantsState.SecondConfirmMsg_Unreply);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getLong("count");
			}
			if(count>0) return false;
		} catch (SQLException e) {
			logger.error("checkLastMsg ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		
		return true;
	}
	public List findSecondOrders(String custOrderId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List ret = new ArrayList();
		try {
			ps = conn.prepareStatement("select request_xml,CUST_ORDER_ID,RQResult from SECONDCONFIRM_MSG where CUST_ORDER_ID = ? and STATE in ('00B','00X')");
			ps.setString(1, custOrderId);
			rs = ps.executeQuery();
			while(rs.next()){
				Map m = new HashMap();
				m.put("RQResult", rs.getString("RQResult"));
				m.put("request_xml", rs.getString("request_xml"));
				ret.add(m);
			}
		} catch (SQLException e) {
			logger.error("findSecondOrders ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return ret;
	}
	public void saveXMLWithoutClob(String spiXML, String systemId,
			long inOrderId, String productNo, String reginID) throws SQLException {
		String insertSql = "INSERT INTO SP_OUT_MSG(ID,IN_TIME,MSG_ID,SYS,ORDER_ID,STATE,MSG,PROD_NO,LAN_CODE,PARTITION_ID) " +
		" VALUES(SP_OUT_MSG_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?)";
		//String seqSql = "SELECT SP_OUT_MSG_SEQ.NEXTVAL FROM DUAL";
		long s=System.currentTimeMillis();
		PreparedStatement ps = null;
		//ResultSet rs = null;
		Connection conn = null;
//		InputStreamReader ir = null;
		try {
			conn = LegacyDAOUtil.getConnection();
			//long msgSeq = -1;
			//ps = conn.prepareStatement(seqSql);
			//rs = ps.executeQuery();
			//while (rs.next()) {
				//msgSeq = rs.getLong(1);
			//}
			ps = conn.prepareStatement(insertSql);
			int index = 1;
			//ps.setLong(index++, msgSeq);
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setString(index++, "0");//msg_id
			ps.setString(index++, systemId);//SYS,
			ps.setLong(index++, inOrderId);//order_id,
			ps.setString(index++, "0");//STATE
			ps.setString(index++, spiXML);//
			ps.setString(index++, productNo);
			ps.setString(index++, genLanCode(productNo));
			ps.setInt(index++, DAOUtils.getCurrentMonth());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#saveXML ex.",e);
			throw e;
		}finally{
			//DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
	}
	/**
	 * 
	 * @param infSystem
	 * @param infMsg
	 * @param productNo
	 * @param custOrderId  内部订单标识
	 * @param otherSysOrderId	外部订单标识
	 * @return
	 * @throws SQLException
	 */
	public boolean saveWorkListToInfMsgWithoutClob(String infSystem, String infMsg,
			String productNo, String custOrderId, String otherSysOrderId) throws SQLException {
		long s=System.currentTimeMillis();
		Connection conn = LegacyDAOUtil.getConnection();
		String sql = "insert into inf_msg(INF_MSG_ID,INF_MSG,INF_SYSTEM,CREATE_DATE,STATE,STATE_DATE,THREAD_ID,PARTITION_ID,CUST_ORDER_ID,OTHER_SYS_ORDER_ID) values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			int index = 1;
			long seq = getSequence("SEQ_INF_MSG_ID", conn);
			ps.setLong(index++, seq);
			ps.setString(index++, infMsg);
			ps.setString(index++, infSystem);
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setString(index++, undealState);
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setInt(index++, createThreadId(productNo));
			ps.setInt(index++, DAOUtils.getCurrentMonth());
			ps.setString(index++, custOrderId);
			ps.setString(index, otherSysOrderId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#saveWorkListToInfMsgWithoutClob",e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		logger.info("saveWorkListToInfMsg cost:"+(System.currentTimeMillis()-s));
		return true;
	}
	public List getUndealReplay(String recordsPerOnce, String threadId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		PreparedStatement psBatch = null;
		ResultSet rs = null;
		String sql = "select RQCODE,CUST_ORDER_ID,REQUEST_XML,CREATE_DATE,THREAD_ID,STATE,SMS_MSG,DEAL_TIME,RESULT_DESC,ACC_NBR,RQRESULT,REPLY_COUNT from SECONDCONFIRM_MSG" +
				" where STATE=? and THREAD_ID=? and rownum<=?";
		
		String sql_informix = "select first ? RQCODE,CUST_ORDER_ID,REQUEST_XML,CREATE_DATE,THREAD_ID,STATE,SMS_MSG,DEAL_TIME,RESULT_DESC,ACC_NBR,RQRESULT,REPLY_COUNT from SECONDCONFIRM_MSG" +
		" where STATE=? and THREAD_ID=? ";
		
		if(isInformix){
			sql=sql_informix;
		}
		
		try {
			psBatch = conn.prepareStatement("update SECONDCONFIRM_MSG set STATE=? where RQCODE=?");
			ps = conn.prepareStatement(sql );
			
			if(isInformix){
				ps.setString(1, recordsPerOnce);
				ps.setString(2, ConstantsState.SecondConfirmMsg_UnDeal);
				ps.setString(3, threadId);
			}else{
				ps.setString(1, ConstantsState.SecondConfirmMsg_UnDeal);
				ps.setString(2, threadId);
				ps.setString(3, recordsPerOnce);
			}
			
			rs = ps.executeQuery();
			List ret = new ArrayList();
			while(rs.next()){
				SecondConfirmMsg msg = new SecondConfirmMsg();
				msg.setRqCode(rs.getString("RQCODE"));
				msg.setCustOrderId(rs.getString("CUST_ORDER_ID"));
				msg.setRequestXml(rs.getString("REQUEST_XML"));
				msg.setCreateDate(DAOUtils.getFormatedDateTime(rs.getTimestamp("CREATE_DATE")));//
				msg.setThreadId(rs.getString("THREAD_ID"));
				msg.setState(rs.getString("STATE"));
				msg.setSmsMsg(rs.getString("SMS_MSG"));
				msg.setDealTime(DAOUtils.getFormatedDateTime(rs.getTimestamp("DEAL_TIME")));//
				msg.setResultDesc(rs.getString("RESULT_DESC"));
				msg.setAccNbr(rs.getString("ACC_NBR"));
				msg.setRqResult(rs.getString("RQRESULT"));
				msg.setReplayCount(rs.getString("REPLY_COUNT").equals("") ? "0" : rs.getString("REPLY_COUNT"));
				ret.add(msg);
				
				psBatch.setString(1, ConstantsState.SecondConfirmMsg_Dealing);
				psBatch.setString(2, msg.getRqCode());
				psBatch.addBatch();
			}
			if(ret.size()>0) psBatch.executeBatch();
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#getUndealReplay",e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
			DAOUtils.closeStatement(psBatch);
			DAOUtils.closeResultSet(rs);
		}
	}
	public void updateReconfirmMsg(String state, SecondConfirmMsg replyMsg) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "update SECONDCONFIRM_MSG set STATE=?,DEAL_TIME=?,RESULT_DESC=? where RQCODE=?";
		try {
			ps = conn.prepareStatement(sql );
			ps.setString(1, state);
			ps.setTimestamp(2, DAOUtils.getCurrentTimestamp());
			ps.setString(3, replyMsg.getResultDesc());
			ps.setString(4, replyMsg.getRqCode());
			ps.executeUpdate();
		} catch (SQLException e) {
			LegacyDAOUtil.rollbackOnException();
			logger.error("", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	public SecondConfirmMsg getSecondConfirmMsg(String rqCode) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select rqcode,cust_order_id,request_xml,create_date,state,THREAD_ID,SMS_MSG,DEAL_TIME,RESULT_DESC,ACC_NBR,RQResult,reply_count from PK_SECONDCONFIRM_MSG where RQCODE=?";
		try {
			ps = conn.prepareStatement(sql );
			ps.setString(1, rqCode);
			
			rs = ps.executeQuery();
			if(rs.next()){
				SecondConfirmMsg msg = new SecondConfirmMsg();
				msg.setRqCode(rs.getString("rqcode"));
				msg.setCustOrderId(rs.getString("cust_order_id"));
				msg.setRequestXml(rs.getString("request_xml"));
				msg.setCreateDate(DAOUtils.getFormatedDateTime(rs.getTimestamp("create_date")));
				msg.setState(rs.getString("state"));
				msg.setThreadId(rs.getString("THREAD_ID"));
				msg.setRqResult(rs.getString("RQResult"));
				msg.setAccNbr(rs.getString("ACC_NBR"));
				return msg;
			}
			return null;
		} catch (SQLException e) {
			logger.error("", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
			DAOUtils.closeResultSet(rs);
		}
	}
	public void archivingSecondMsg(String orderId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement insertPs = null;
		PreparedStatement deletePs = null;
		String sql = "insert into SECONDCONFIRM_MSG_HIS select * from SECONDCONFIRM_MSG where CUST_ORDER_ID=?";
		try {
			insertPs = conn.prepareStatement(sql );
			insertPs.setString(1, orderId);
			insertPs.executeUpdate();
			
			deletePs = conn.prepareStatement("delete from SECONDCONFIRM_MSG where CUST_ORDER_ID=?");
			deletePs.setString(1, orderId);
			deletePs.executeUpdate();
		
		} catch (SQLException e) {
			logger.error("", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(insertPs);
			DAOUtils.closeStatement(deletePs);
		}
		
	}
	public void saveUserInfoSyncMsg(String systemId, String url,
			long inOrderId, String productNo, String requestXml) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement insertPs = null;
		String sql = "insert into UserInfo_Sync_msg(MSG_ID,SYSTEM_ID,SERVICE_URL,CREATE_DATE,CUST_ORDER_ID,ACC_NBR,REQUEST_XML,RESPONSE_XML,STATE,DEAL_TIME,RESULT_DESC,THREAD_ID)" +
				" values(SEQ_USERINFO_SYNC_MSG_ID.nextval,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			insertPs = conn.prepareStatement(sql);
			int index = 1;
			insertPs.setString(index++, systemId);
			insertPs.setString(index++, url);
			insertPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			insertPs.setLong(index++, inOrderId);
			insertPs.setString(index++, productNo);
			insertPs.setString(index++, requestXml);//REQUEST_XML
			insertPs.setString(index++, "");//RESPONSE_XML
			insertPs.setString(index++, ConstantsState.UserInfoSyncMsg_UnDeal);//STATE
			insertPs.setString(index++, "");//DEAL_TIME
			insertPs.setString(index++, "");//RESULT_DESC
			insertPs.setString(index++, generateUserInfoSyncThreadId(productNo));//THREAD_ID
			insertPs.executeUpdate();
		} catch (SQLException e) {
			logger.error("", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(insertPs);
		}
	}
	public List getUndealUserInfoSyncMsg(String recordsPerOnce, String threadId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		PreparedStatement psBatch = null;
		ResultSet rs = null;
		String sql = "select MSG_ID,SYSTEM_ID,SERVICE_URL,CREATE_DATE,CUST_ORDER_ID,ACC_NBR,REQUEST_XML,RESPONSE_XML,STATE,DEAL_TIME,RESULT_DESC,THREAD_ID from USERINFO_SYNC_MSG" +
				" where STATE=? and THREAD_ID=? and rownum<=?";
		
		String sql_informix = "select first ?  MSG_ID,SYSTEM_ID,SERVICE_URL,CREATE_DATE,CUST_ORDER_ID,ACC_NBR,REQUEST_XML,RESPONSE_XML,STATE,DEAL_TIME,RESULT_DESC,THREAD_ID from USERINFO_SYNC_MSG" +
		" where STATE=? and THREAD_ID=?";
		
		if(isInformix){
			sql=sql_informix;
		}
		
		try {
			logger.info("getUndealUserInfoSyncMsg sql:" + sql);
			logger.info("recordsPerOnce:" + recordsPerOnce+",threadId:" + threadId);
			psBatch = conn.prepareStatement("update USERINFO_SYNC_MSG set STATE=? where MSG_ID=?");
			ps = conn.prepareStatement(sql);
			if(isInformix){
				ps.setString(1, recordsPerOnce);
				ps.setString(2, ConstantsState.UserInfoSyncMsg_UnDeal);
				ps.setString(3, threadId);
				
			}else{
				ps.setString(1, ConstantsState.UserInfoSyncMsg_UnDeal);
				ps.setString(2, threadId);
				ps.setString(3, recordsPerOnce);
			}
			
			rs = ps.executeQuery();
			List ret = new ArrayList();
			while(rs.next()){
				UserInfoSyncMsg msg = new UserInfoSyncMsg();
				msg.setMsgId(rs.getString("MSG_ID"));
				msg.setSystemId(rs.getString("SYSTEM_ID"));
				msg.setServiceUrl(rs.getString("SERVICE_URL"));
				msg.setCreateDate(DAOUtils.getFormatedDateTime(rs.getTimestamp("CREATE_DATE")));
				msg.setCustOrderId(rs.getString("CUST_ORDER_ID"));
				msg.setAccNbr(rs.getString("ACC_NBR"));
				msg.setRequestXml(rs.getString("REQUEST_XML"));
				msg.setResponseXml(rs.getString("RESPONSE_XML"));
				msg.setState(rs.getString("STATE"));
				msg.setDealTime(DAOUtils.getFormatedDateTime(rs.getTimestamp("DEAL_TIME")));
				msg.setResultDesc(rs.getString("RESULT_DESC"));
				msg.setThreadId(rs.getString("THREAD_ID"));
				ret.add(msg);
				
				psBatch.setString(1, ConstantsState.UserInfoSyncMsg_Dealing);
				psBatch.setString(2, msg.getMsgId());
				psBatch.addBatch();
			}
			if(ret.size()>0) psBatch.executeBatch();
			return ret;
		} catch (SQLException e) {
			logger.error("", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
			DAOUtils.closeStatement(psBatch);
			DAOUtils.closeResultSet(rs);
		}
	}
	public void updateUserInfoSyncMsg(String state,
			UserInfoSyncMsg userInfoSyncMsg) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "update USERINFO_SYNC_MSG set STATE=?,DEAL_TIME=?,RESULT_DESC=?,RESPONSE_XML=? where MSG_ID=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, state);
			ps.setTimestamp(2, DAOUtils.getCurrentTimestamp());
			String resultDesc = userInfoSyncMsg.getResultDesc();
			if(resultDesc != null && resultDesc.length()>4000) resultDesc = resultDesc.substring(0, 4000);
			ps.setString(3, resultDesc);
			ps.setString(4, userInfoSyncMsg.getResponseXml());
			ps.setString(5, userInfoSyncMsg.getMsgId());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	public void archiveUserInfoSyncMsg(UserInfoSyncMsg userInfoSyncMsg) throws SQLException {
		logger.info("archiveUserInfoSyncMsg");
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		PreparedStatement deletePs = null;
		String insertSql = "insert into USERINFO_SYNC_MSG_HIS select * from  USERINFO_SYNC_MSG a where a.MSG_ID=?";
		String deleteSql = "delete from USERINFO_SYNC_MSG  where MSG_ID=?";
		
		try {
			ps = conn.prepareStatement(insertSql);
			ps.setString(1, userInfoSyncMsg.getMsgId());
			ps.executeUpdate();
			
			deletePs = conn.prepareStatement(deleteSql);
			deletePs.setString(1, userInfoSyncMsg.getMsgId());
			deletePs.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
			DAOUtils.closeStatement(deletePs);
		}
	}
	public String[] getProdInstInfos(String lanCodeTmp, String productNoTmp) throws SQLException {
		PreparedStatement psProdId = null;
		ResultSet rs = null;
		Connection conn = LegacyDAOUtil.getConnection();
		String prodInstId = "";
		String lanCode = "";
		String userState = "";
		String payMode="";
		String prodSpecCode = "";
		try{
			String getProdInstId = "SELECT PROD_INST_ID,LAN_ID,STATE_CD,PAYMENT_MODE_CD,PRODUCT_ID,PROD_TYPE_CD from PROD_INST where ACC_NBR = ? and STATE_CD in(?,?) " + ( (null != lanCodeTmp) ? " and LAN_ID = ?" : "" );
			psProdId = conn.prepareStatement(getProdInstId);
			psProdId.setString(1, productNoTmp);
			psProdId.setString(2, OrderConstant.orderStateOfCreated);
			psProdId.setString(3, OrderConstant.orderStateOfUnconfirm);
			if(null != lanCodeTmp) psProdId.setString(4, lanCodeTmp);
			rs = psProdId.executeQuery();
			if(rs.next()){
				prodInstId = rs.getString(1);
				lanCode = rs.getString(2);
				userState=rs.getString(3);
				payMode=rs.getString(4);
				prodSpecCode=rs.getString("PRODUCT_ID");
			}
			
		}catch(SQLException e){
			logger.error("#saveOrderRelation ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(psProdId);
		}
		return new String[]{prodInstId,lanCode,userState,payMode,prodSpecCode};
	}
	/**
	 * 订购历史查询
	 * @param subInstQueryVo
	 * @param orderstateofcreated
	 * @return
	 * @throws SQLException 
	 */
	public List subscribeHisQry(SubInstHisQueryRequest subInstQueryVo,
			String state) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		List ret = null;

		String querySql1_oracle = "select r.product_id,r.product_offer_id pr_id, r.status state,r.service_offer_id, to_char(r.status_date,'yyyymmddhh24miss') create_date, to_char(r.eff_time,'yyyymmddhh24miss') eff_date, to_char(r.exp_time,'yyyymmddhh24miss') exp_date," +
		"p.product_nbr,"+		
		" (select o.order_channel from customer_order o where o.cust_order_id=r.cust_order_id and rownum=1) order_channel,r.package_id,r.pprod_offer_id, p.product_name p_name," +
		" (select pr.prod_offer_name from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_name_a," +		
		" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.package_id) pr_name_b," + 
		" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.pprod_offer_id) pr_name_c," + 		
		" (select t.partner_id from partner t where t.partner_id = p.product_provider_id ) sp_id," +
		" (select t.partner_name from partner t where t.partner_id = p.product_provider_id ) sp_name," +
		" (select pr.prod_offer_sub_type from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_type," +
		" (select pr.chargingpolicy_cn from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_chargeCN, " +
		" (select to_char(eff_date,'yyyymmddhh24miss')||'#'||to_char(exp_date,'yyyymmddhh24miss') from order_relation orl where orl.product_id=r.product_id and orl.PROD_INST_ID=r.PROD_INST_ID and rownum=1) as efxp_date " +
		" from order_item r inner join product p on p.product_id = r.product_id" +
		" where r.PROD_INST_ID =? and r.status =? and r.order_item_cd!='04' and r.status_date between to_date(?,'yyyyMMdd') and to_date(?,'yyyyMMdd')+1 and r.service_offer_id in (0, 1) ";	
		String querySql1_informix = "select r.product_id,r.product_offer_id pr_id, r.status state,r.service_offer_id, to_char(r.status_date,'yyyymmddhh24miss') create_date, to_char(r.eff_time,'yyyymmddhh24miss') eff_date, to_char(r.exp_time,'yyyymmddhh24miss') exp_date," +
		"p.product_nbr,"+		
		" (select min(o.order_channel) from customer_order o where o.cust_order_id=r.cust_order_id ) order_channel,r.package_id,r.pprod_offer_id, p.product_name p_name," +
		" (select pr.prod_offer_name from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_name_a," +		
		" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.package_id) pr_name_b," + 
		" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.pprod_offer_id) pr_name_c," + 		
		" (select t.partner_id from partner t where t.partner_id = p.product_provider_id ) sp_id," +
		" (select t.partner_name from partner t where t.partner_id = p.product_provider_id ) sp_name," +
		" (select pr.prod_offer_sub_type from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_type," +
		" (select pr.chargingpolicy_cn from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_chargeCN, " +
		" (select min(to_char(eff_date,'%Y%m%d%H%M%S')||'#'||to_char(exp_date,'%Y%m%d%H%M%S')) from order_relation orl where orl.product_id=r.product_id and orl.PROD_INST_ID=r.PROD_INST_ID ) as efxp_date " +
		" from order_item r inner join product p on p.product_id = r.product_id" +
		" where r.PROD_INST_ID =? and r.status =? and r.order_item_cd!='04' and r.status_date between to_date(?,'%Y%m%d') and to_date(?,'%Y%m%d')+1 and r.service_offer_id in (0, 1) ";
		
		String querySql2_oracle = "select r.product_id,r.product_offer_id pr_id, r.status state,r.service_offer_id, to_char(r.status_date,'yyyymmddhh24miss') create_date, to_char(r.eff_time,'yyyymmddhh24miss') eff_date, to_char(r.exp_time,'yyyymmddhh24miss') exp_date," +
		"p.product_nbr,"+
		" (select o.order_channel from customer_order_his o where o.cust_order_id=r.cust_order_id and rownum=1) order_channel,r.package_id,r.pprod_offer_id, p.product_name p_name," +
		" (select pr.prod_offer_name from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_name_a," +		
		" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.package_id) pr_name_b," + 
		" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.pprod_offer_id) pr_name_c," + 		
		" (select t.partner_id from partner t where t.partner_id = p.product_provider_id ) sp_id," +
		" (select t.partner_name from partner t where t.partner_id = p.product_provider_id ) sp_name," +
		" (select pr.prod_offer_sub_type from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_type," +
		" (select pr.chargingpolicy_cn from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_chargeCN," +
		" (select to_char(eff_date,'yyyymmddhh24miss')||'#'||to_char(exp_date,'yyyymmddhh24miss') from order_relation orl where orl.product_id=r.product_id and orl.PROD_INST_ID=r.PROD_INST_ID and rownum=1) as efxp_date " +
		" from order_item_his r inner join product p on p.product_id = r.product_id" +
		" where r.PROD_INST_ID =? and r.status =? and r.order_item_cd!='04' and r.status_date between to_date(?,'yyyyMMdd') and to_date(?,'yyyyMMdd')+1 and r.service_offer_id in (0, 1) "
		+ createPartitionCondistion(subInstQueryVo.getBeginTime(),subInstQueryVo.getEndTime())
		;		
		String querySql2_informix = "select r.product_id,r.product_offer_id pr_id, r.status state,r.service_offer_id, to_char(r.status_date,'yyyymmddhh24miss') create_date, to_char(r.eff_time,'yyyymmddhh24miss') eff_date, to_char(r.exp_time,'yyyymmddhh24miss') exp_date," +
		"p.product_nbr,"+
		" (select min(o.order_channel) from customer_order_his o where o.cust_order_id=r.cust_order_id ) order_channel,r.package_id,r.pprod_offer_id, p.product_name p_name," +
		" (select pr.prod_offer_name from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_name_a," +		
		" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.package_id) pr_name_b," + 
		" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.pprod_offer_id) pr_name_c," + 		
		" (select t.partner_id from partner t where t.partner_id = p.product_provider_id ) sp_id," +
		" (select t.partner_name from partner t where t.partner_id = p.product_provider_id ) sp_name," +
		" (select pr.prod_offer_sub_type from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_type," +
		" (select pr.chargingpolicy_cn from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_chargeCN," +
		" (select min(to_char(eff_date,'%Y%m%d%H%M%S')||'#'||to_char(exp_date,'%Y%m%d%H%M%S')) from order_relation orl where orl.product_id=r.product_id and orl.PROD_INST_ID=r.PROD_INST_ID ) as efxp_date " +
		" from order_item_his r inner join product p on p.product_id = r.product_id" +
		" where r.PROD_INST_ID =? and r.status =? and r.order_item_cd!='04' and r.status_date between to_date(?,'%Y%m%d') and to_date(?,'%Y%m%d')+1 and r.service_offer_id in (0, 1) "
		+ createPartitionCondistion(subInstQueryVo.getBeginTime(),subInstQueryVo.getEndTime())
		;	
		String querySql="";
		if(isInformix){
			 querySql = querySql1_informix + " union all " + querySql2_informix;
		}else{
			 querySql = querySql1_oracle + " union all " + querySql2_oracle;
		}
		try {
			int index = 1;
			ps = conn.prepareStatement(querySql);
			ps.setString(index++, subInstQueryVo.getProdInstId());
			ps.setString(index++, state);//只是查询00A状态
			ps.setString(index++, subInstQueryVo.getBeginTime());
			ps.setString(index++, subInstQueryVo.getEndTime());
			// order_item_his
			ps.setString(index++, subInstQueryVo.getProdInstId());
			ps.setString(index++, state);//只是查询00A状态
			ps.setString(index++, subInstQueryVo.getBeginTime());
			ps.setString(index++, subInstQueryVo.getEndTime());
			rs = ps.executeQuery();
			ret = new ArrayList();
			while (rs.next()) {
				VproducInfo vProduct = new VproducInfo();
				vProduct.setVproductID(rs.getString("product_id"));
				vProduct.setVproductName(parseOutputString(rs.getString("p_name")));
				vProduct.setSPID(rs.getString("sp_id"));
				vProduct.setSPName(parseOutputString(rs.getString("sp_name")));
				vProduct.setChargingPolicyCN(parseOutputString(rs.getString("pr_chargeCN")));
				vProduct.setStatus("0");//把00A状态下的以0形式返回
				vProduct.setActionType(rs.getString("service_offer_id"));//订购动作
				vProduct.setSubscribeTime(parseOutputString(rs.getString("create_date")));
				String efxpDate = parseOutputString(rs.getString("efxp_date"));
				vProduct.setProductNbr(parseOutputString(rs.getString("product_nbr")));
				//生失效时间通过关联order_relation查出返回
				if(efxpDate != null && !"".equals(efxpDate)){
					String[] _values = efxpDate.split("#");
					if(_values.length == 0){
						vProduct.setEffDate("");
						vProduct.setExpDate("");
					}
					else if(_values.length == 1){
						vProduct.setEffDate(_values[0]);
						vProduct.setExpDate("");
					}else{
						vProduct.setEffDate(_values[0]);
						vProduct.setExpDate(_values[1]);
					}
				}else{
					vProduct.setEffDate(parseOutputString(rs.getString("eff_date")));
					vProduct.setExpDate(parseOutputString(rs.getString("exp_date")));
				}
				vProduct.setChannelPlayerID(parseOutputString(rs.getString("order_channel")));
				
				//逻辑处理
				//PROD_OFFER_ID不为空
				if(rs.getString("pr_id") !=null && !"".equals(rs.getString("pr_id").trim())){
					vProduct.setProductOfferType("0");
					vProduct.setProductOfferID(rs.getString("pr_id"));
					vProduct.setProdOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(rs.getString("pr_id")));
					vProduct.setProductOfferName(parseOutputString(rs.getString("pr_name_a")));					
				}
				//PACKAGE_ID不为空
				if(rs.getString("package_id") !=null && !"".equals(rs.getString("package_id").trim()) ){
					vProduct.setProductOfferType("1");
					vProduct.setProductOfferID(rs.getString("package_id"));
					vProduct.setProductOfferName(parseOutputString(rs.getString("pr_name_b")));
					vProduct.setProdOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(rs.getString("package_id")));
				}
				//PPROD_OFFER_ID不为空
				if(rs.getString("pprod_offer_id") !=null && !"".equals(rs.getString("pprod_offer_id").trim())){
					vProduct.setProductOfferType("2");
					vProduct.setProductOfferID(rs.getString("pprod_offer_id"));
					vProduct.setProductOfferName(parseOutputString(rs.getString("pr_name_c")));
					vProduct.setProdOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(rs.getString("pprod_offer_id")));
				}
				ret.add(vProduct);
				
			}
		}
		catch (SQLException e) {
			logger.error("#subscribeHisQry ex.", e);
			logger.error(querySql);
			throw e;
		} finally {
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return ret;
	}
	
	private String createPartitionCondistion(
			String beginStr, String endStr) {
		String sql = "and (";
		String cond = "";
		String pattern = "yyyyMMdd";
		Calendar beginCal = DateUtil.parseStrToCalendar(beginStr, pattern );
		Calendar endCal = DateUtil.parseStrToCalendar(endStr, pattern );
		int beginMonth = beginCal.get(Calendar.MONTH)+1;
		int endMonth = endCal.get(Calendar.MONTH)+1;
		for(int i = beginMonth;i<=endMonth;i++){
			cond += " partition_id = "+String.valueOf(i)+" OR";
		}
		if(cond.length()>2) cond = cond.substring(0, cond.length()-2);
		sql = sql + cond + ")";
		return sql;
	}
	
	
	
	//select a.prod_offer_id, b.product_id,po.offer_nbr,p.product_nbr,po.prod_offer_sub_type from PROD_OFFER_DETAIL_ROLE a, ROLE_PROD_RELA b,prod_offer po,product p  where a.prod_offer_role_cd = b.prod_offer_role_cd and po.prod_offer_id=a.prod_offer_id and b.product_id=p.product_id and a.prod_offer_id = '';
	public Map queryAndSetProductOfferIdByVProductId(String vProductID) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		Map ret = new HashMap();

		String querySql = "select a.prod_offer_id, b.product_id,po.offer_nbr,p.product_nbr,po.prod_offer_sub_type from PROD_OFFER_DETAIL_ROLE a, ROLE_PROD_RELA b,prod_offer po,product p  where a.prod_offer_role_cd = b.prod_offer_role_cd and po.prod_offer_id=a.prod_offer_id and b.product_id=p.product_id and b.product_id = ? and po.prod_offer_sub_type=?";
		try {
			int index = 1;
			ps = conn.prepareStatement(querySql);
			ps.setString(index++, vProductID);
			ps.setString(index++, "0");
			rs = ps.executeQuery();
			if (rs.next()) {
				ret.put("prod_offer_id", rs.getString("prod_offer_id"));
				ret.put("offer_nbr", rs.getString("offer_nbr"));
			}
			
		} catch (SQLException e) {
			logger.error("#subInstQuery ex.", e);
			throw e;
		} finally {
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return ret;
	}
	/**
	 * 订购申请回单取数逻辑
	 * @param limit
	 * @param threadId
	 * @return
	 * @throws SQLException
	 */
	public List getUnDealOrderFeedback(int limit, String threadId) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement selectPs = null;
		//PreparedStatement insertPs = null;
		PreparedStatement updatePs = null;
		PreparedStatement selectItemPs = null;
		ResultSet rs = null;
		ResultSet rsItem = null;
		List ret = new ArrayList();
//		String selectSql = "select ORDER_FEED_ID, IN_TIME, ORDER_ID, OUT_ORDER_ID, STREAM_NO, PROD_SPEC_CODE,PRODUCT_NO,SYSTEM_ID,THREAD_ID,STATE from ORDER_FEED_BACK " +
//				"where state = ?  and rownum < ? "+("".equals(threadId)? "" : " and thread_id = ?")+" order by id";
		String selectSql = "select ORDER_FEED_ID, IN_TIME, ORDER_ID, OUT_ORDER_ID, STREAM_NO, PROD_SPEC_CODE,PRODUCT_NO,SYSTEM_ID,THREAD_ID,STATE from ORDER_FEED_BACK " +
		"where state = ?  and rownum < ? "+("".equals(threadId)? "" : " and thread_id = ?")+" order by ORDER_FEED_ID";		
		//String insertSql = "insert into TMP_ID(id) select id from sp_out_msg_feedback where state = ?  and rownum < ? "+("".equals(threadId)? "" : " and thread_id = ?")+" order by id";
		String updateSql = "update ORDER_FEED_BACK set state=?,state_date="+DatabaseFunction.current()+" where ORDER_FEED_ID =? ";
		String selectItem = "select ORDER_FEED_ID,PROD_OFFER_ID,OP_RESULT,OP_DESC from order_feed_back_item where ORDER_FEED_ID=?";
		try {
			/*insertPs = conn.prepareStatement(insertSql);
			insertPs.setString(1, "0");
			insertPs.setInt(2, limit);
			if(!"".equals(threadId)){
				insertPs.setString(3, threadId);
			}
			insertPs.executeUpdate();*/
			updatePs = conn.prepareStatement(updateSql);
			selectItemPs = conn.prepareStatement(selectItem);
			
			selectPs = conn.prepareStatement(selectSql );
			selectPs.setString(1, "0");
			selectPs.setInt(2, limit);
			if(!"".equals(threadId)){
				selectPs.setString(3, threadId);
			}
			rs = selectPs.executeQuery();
			while(rs.next()){
				returnSubVO feeback = new returnSubVO();
				String id = rs.getString("ORDER_FEED_ID");
				feeback.setOrderFeedId(id);
				feeback.setInTime(rs.getString("IN_TIME"));
				feeback.setOrderId(rs.getString("ORDER_ID"));
				feeback.setStreamNo(rs.getString("STREAM_NO"));
				feeback.setOutOrderId(rs.getString("OUT_ORDER_ID"));
				feeback.setProdSpecCode(rs.getString("PROD_SPEC_CODE"));
				feeback.setProductNo(rs.getString("PRODUCT_NO"));
				feeback.setSystemId(rs.getString("SYSTEM_ID"));
				//获取回单子工单数据
				selectItemPs.setString(1, id);
				rsItem = selectItemPs.executeQuery();
				//存放回单子工单数据 list
				List subList = new ArrayList();
				while(rsItem.next()){
					returnProdOfferVO itemVo = new returnProdOfferVO();
					itemVo.setProductOfferID(rsItem.getString("PROD_OFFER_ID"));
					itemVo.setOPResult(rsItem.getString("OP_RESULT"));
					itemVo.setOPDesc(rsItem.getString("OP_DESC"));
					subList.add(itemVo);
				}
				feeback.setReturnList(subList);
				ret.add(feeback);
				updatePs.setString(1, "-1");
				updatePs.setString(2, id);
				updatePs.addBatch();
			}
			
			if(null!=ret && ret.size()>0){
				updatePs.executeBatch();
			}
		} catch (SQLException e) {
			logger.error("#getUnDealFeedbacks ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeResultSet(rsItem);
			DAOUtils.closeStatement(selectItemPs);
			DAOUtils.closeStatement(selectPs);
			DAOUtils.closeStatement(updatePs);
		}
		return ret;
	}
	/**
	 * 成功回单后的回单数据归档
	 * @param orderId
	 * @throws SQLException
	 */
	public void backUpOrder(String orderId) throws SQLException {
		
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement insertPs = null;
		PreparedStatement insertItemPs = null;
		PreparedStatement deletePs = null;
		PreparedStatement deleteItemPs = null;
		String insertSql = "insert into ORDER_FEED_BACK_his select * from ORDER_FEED_BACK c where c.ORDER_FEED_ID=? ";
		String deleleOrderSql = "delete from ORDER_FEED_BACK c where c.ORDER_FEED_ID=?";
		String insertItemSql = "insert into order_feed_back_item_his select * from order_feed_back_item a where a.ORDER_FEED_ID=?";
		String deleteItemSql = "delete from order_feed_back_item a where a.ORDER_FEED_ID=?";
		try {
			insertPs = conn.prepareStatement(insertSql );
			insertPs.setString(1, orderId);
			insertPs.executeUpdate();
			
			deletePs = conn.prepareStatement(deleleOrderSql);
			deletePs.setString(1, orderId);
			deletePs.executeUpdate();
			
			insertItemPs = conn.prepareStatement(insertItemSql);
			insertItemPs.setString(1, orderId);
			insertItemPs.executeUpdate();
			
			deleteItemPs = conn.prepareStatement(deleteItemSql);
			deleteItemPs.setString(1, orderId);
			deleteItemPs.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("#archivingOrder ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(insertPs);
			DAOUtils.closeStatement(deletePs);
			DAOUtils.closeStatement(insertItemPs);
			DAOUtils.closeStatement(deleteItemPs);
		}
	}
}
