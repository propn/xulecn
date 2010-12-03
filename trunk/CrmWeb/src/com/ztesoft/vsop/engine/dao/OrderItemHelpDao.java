package com.ztesoft.vsop.engine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.vo.AproductInfo;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.OrderItemVO;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class OrderItemHelpDao {
	protected static Logger logger = Logger.getLogger(OrderItemHelpDao.class);
	private static SequenceManageDAOImpl sequenceManage = new SequenceManageDAOImpl();
	private String insertSql = "insert into ORDER_ITEM (ORDER_ITEM_ID,CUST_ORDER_ID,ORDER_ITEM_CD,ORDER_ITEM_OBJ_ID,CUST_WORKSHEET_ID,STATUS,STATUS_DATE,STATE_CHANGE_REASON,PRIORITY,PRE_HANDLE_FLAG"
			+ ",HANDLE_TIME,ARCHIVE_DATE,FINISH_TIME,PRODUCT_ID,ACC_NBR,PACKAGE_ID,PRODUCT_OFFER_ID,EFF_TIME,EXP_TIME,RESULT_TYPE,RESULT_INFO,LAN_ID,PPROD_OFFER_ID,PROD_INST_ID,SERVICE_OFFER_ID,PARTITION_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+DatabaseFunction.current()+", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
	private String insertSqlHis = "insert into ORDER_ITEM_HIS (ORDER_ITEM_ID,CUST_ORDER_ID,ORDER_ITEM_CD,ORDER_ITEM_OBJ_ID,CUST_WORKSHEET_ID,STATUS,STATUS_DATE,STATE_CHANGE_REASON,PRIORITY,PRE_HANDLE_FLAG"
		+ ",HANDLE_TIME,ARCHIVE_DATE,FINISH_TIME,PRODUCT_ID,ACC_NBR,PACKAGE_ID,PRODUCT_OFFER_ID,EFF_TIME,EXP_TIME,RESULT_TYPE,RESULT_INFO,LAN_ID,PPROD_OFFER_ID,PROD_INST_ID,SERVICE_OFFER_ID,PARTITION_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+DatabaseFunction.current()+", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
	/**
	 * 根据所有增值产品新增订单项
	 * @param order
	 * @throws Exception
	 */
	
	public void insertOrderItemsByOrder(CustomerOrder order) throws Exception{
		// 保存增值产品子订单
		PreparedStatement subOrderPs = null;
		SequenceManageDAOImpl sequenceManage = new SequenceManageDAOImpl();
		try {
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			subOrderPs = conn.prepareStatement(insertSql);
			
			for (Iterator productOfferItr = order.getProductOfferInfoList().iterator(); productOfferItr.hasNext();) {
				ProductOfferInfo productOffer = (ProductOfferInfo) productOfferItr.next();
				List vSubProdInfoList = productOffer.getVproductInfoList();
				String offerType = productOffer.getOfferType();
				for (Iterator iterator = vSubProdInfoList.iterator(); iterator
						.hasNext();) {
					VproductInfo vSubProdInfo = (VproductInfo) iterator.next();
					String prodInfoId = sequenceManage.getNextSequence("SEQ_SUB_ORDER_INFO_ID");
					long id = Long.valueOf(prodInfoId).longValue();
					int index = 1;
					String prodOfferId = vSubProdInfo.getOfferId();
					String packageId = "";
					String pProdOfferId= "";
					if("1".equals(offerType)){
						packageId = prodOfferId;
						prodOfferId=DcSystemParamManager.getInstance().getofferIdByProductId(vSubProdInfo.getVProductId());
					}else if("2".equals(offerType)){
						pProdOfferId = prodOfferId;
						prodOfferId=DcSystemParamManager.getInstance().getofferIdByProductId(vSubProdInfo.getVProductId());
					}
					subOrderPs.setLong(index++, id);//ORDER_ITEM_ID
					subOrderPs.setString(index++,order.getCustOrderId());//CUST_ORDER_ID,
					subOrderPs.setString(index++, OrderConstant.ORDER_ITEM_CD_PRODOFFER);//ORDER_ITEM_CD,
					subOrderPs.setString(index++, vSubProdInfo.getVProductInstID());//ORDER_ITEM_OBJ_ID,
					subOrderPs.setString(index++, "");//CUST_WORKSHEET_ID,
					subOrderPs.setString(index++, order.getStatus());//STATUS,
					subOrderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATUS_DATE,
					subOrderPs.setString(index++, "");//STATE_CHANGE_REASON,
					subOrderPs.setString(index++, "");//PRIORITY,
					subOrderPs.setString(index++, "");//PRE_HANDLE_FLAG,
//					subOrderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//HANDLE_TIME,
					subOrderPs.setString(index++, "");//ARCHIVE_DATE,
					subOrderPs.setString(index++, "");//FINISH_TIME,
					subOrderPs.setString(index++, vSubProdInfo.getVProductId());//PRODUCT_ID,
					subOrderPs.setString(index++, order.getAccNbr());//ACC_NBR,
					subOrderPs.setString(index++, packageId);//PACKAGE_ID,
					subOrderPs.setString(index++, prodOfferId);//PRODUCT_OFFER_ID,
					subOrderPs.setTimestamp(index++, DAOUtils.parseTimestamp(vSubProdInfo.getEffDate()));//EFF_TIME,
					subOrderPs.setTimestamp(index++, DAOUtils.parseTimestamp(vSubProdInfo.getExpDate()));//EXP_TIME,
					subOrderPs.setString(index++, "");//RESULT_TYPE,
					subOrderPs.setString(index++, "");//RESULT_INFO,
					subOrderPs.setString(index++, order.getLanId());//LAN_ID
					subOrderPs.setString(index++, pProdOfferId);
					subOrderPs.setString(index++, order.getProdInstId());
//					subOrderPs.setString(index++, order.getCustOrderType());
					subOrderPs.setString(index++, vSubProdInfo.getActionType());//改写增值产品对应的动作，不是订单对应的动作
					subOrderPs.setLong(index++,DAOUtils.getCurrentMonth());
					//subOrderPs.executeUpdate();
					subOrderPs.addBatch();
				}
				if(vSubProdInfoList.size() > 0)
					subOrderPs.executeBatch();
				}
		} catch (SQLException e) {
			logger.error("insertOrderItemsByOrder #ex.", e);
			throw e;
		} finally {
			DAOUtils.closeStatement(subOrderPs);
		}
	}
	/**
	 * 根据所有附属产品新增订单项
	 * @param custOrderId
	 * @param orderSatus
	 * @param accNbr
	 * @param lanId
	 * @param productInstId
	 * @param aprodList
	 * @throws SQLException
	 */
	public void insertOrderItemsByAprodusts(String custOrderId,
			String orderSatus, String accNbr, String lanId,
			String productInstId, List aprodList,String custOrderType) throws SQLException {
		// 附属产品子订单
		PreparedStatement aProdOrderPs = null;
		try {
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			if (aprodList != null) {
				aProdOrderPs = conn.prepareStatement(insertSql);
				for (Iterator aproductItr = aprodList.iterator(); aproductItr
						.hasNext();) {
					AproductInfo aproduct = (AproductInfo) aproductItr.next();
					String orderItemId = sequenceManage
							.getNextSequence("SEQ_SUB_ORDER_INFO_ID");
					int index = 1;
					aProdOrderPs.setLong(index++, Long.valueOf(orderItemId).longValue());// ORDER_ITEM_ID
					aProdOrderPs.setLong(index++, Long.valueOf(custOrderId).longValue());// CUST_ORDER_ID,
					aProdOrderPs.setString(index++,
							OrderConstant.ORDER_ITEM_CD_PRODUCT);// ORDER_ITEM_CD,
					aProdOrderPs.setString(index++, aproduct
							.getAProductInstID());// ORDER_ITEM_OBJ_ID,
					aProdOrderPs.setString(index++, "");// CUST_WORKSHEET_ID,
					aProdOrderPs.setString(index++, orderSatus);// STATUS,
					aProdOrderPs.setTimestamp(index++, DAOUtils
							.getCurrentTimestamp());// STATUS_DATE,
					aProdOrderPs.setString(index++, "");// STATE_CHANGE_REASON,
					aProdOrderPs.setString(index++, "");// PRIORITY,
					aProdOrderPs.setString(index++, "");// PRE_HANDLE_FLAG,
					// aProdOrderPs.setTimestamp(index++,
					// DAOUtils.getCurrentTimestamp());//HANDLE_TIME,
					aProdOrderPs.setString(index++, "");// ARCHIVE_DATE,
					aProdOrderPs.setString(index++, "");// FINISH_TIME,
					aProdOrderPs.setString(index++, aproduct.getAProductID());// PRODUCT_ID,
					aProdOrderPs.setString(index++, accNbr);// ACC_NBR,
					aProdOrderPs.setString(index++, "");// PACKAGE_ID,
					aProdOrderPs.setString(index++, "");// PRODUCT_OFFER_ID,
					aProdOrderPs.setString(index++, "");// EFF_TIME,
					aProdOrderPs.setString(index++, "");// EXP_TIME,
					aProdOrderPs.setString(index++, "");// RESULT_TYPE,
					aProdOrderPs.setString(index++, "");// RESULT_INFO,
					aProdOrderPs.setString(index++, lanId);// LAN_ID
					aProdOrderPs.setString(index++, "");// PPROD_OFFER_ID
					aProdOrderPs.setString(index++, productInstId);
					//aProdOrderPs.setString(index++, custOrderType);
					aProdOrderPs.setString(index++, aproduct.getActionType());//改写附属产品对应的动作，不是订单对应的动作
					aProdOrderPs.setLong(index++, DAOUtils.getCurrentMonth());
					aProdOrderPs.addBatch();
				}
				if (aprodList.size() > 0)
					aProdOrderPs.executeBatch();
			}
		} catch (SQLException e) {
			logger.error("#insertOrderItemsByAprodusts ex.", e);
			throw e;
		} finally {
			DAOUtils.closeStatement(aProdOrderPs);
		}
	}

	/**
	 * 新增订单项
	 * 
	 * @param orderItem
	 */
	public void insertAOrderItem(OrderItemVO orderItem) throws Exception{
		PreparedStatement subOrderPs = null;
		try {
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			subOrderPs = conn.prepareStatement(insertSql);
			String orderItemId = orderItem.getOrderItemId();
			if (null == orderItemId && "".equals(orderItemId)) {
				orderItemId = sequenceManage
						.getNextSequence("SEQ_SUB_ORDER_INFO_ID");
			}
			int index = 1;
			subOrderPs.setLong(index++, Long.valueOf(orderItemId).longValue());// ORDER_ITEM_ID
			subOrderPs.setLong(index++, Long
					.valueOf(orderItem.getCustOrderId()).longValue());// CUST_ORDER_ID,
			subOrderPs
					.setString(index++, OrderConstant.ORDER_ITEM_CD_PRODOFFER);// ORDER_ITEM_CD,
			subOrderPs.setString(index++, orderItem.getOrderItemObjId());// ORDER_ITEM_OBJ_ID,
			subOrderPs.setString(index++, orderItem.getCustWorksheetId());// CUST_WORKSHEET_ID,
			subOrderPs.setString(index++, orderItem.getStatus());// STATUS,
			subOrderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());// STATUS_DATE,
			subOrderPs.setString(index++, orderItem.getStateChangeReason());// STATE_CHANGE_REASON,
			subOrderPs.setString(index++, orderItem.getPriority());// PRIORITY,
			subOrderPs.setString(index++, orderItem.getPreHandleFlag());// PRE_HANDLE_FLAG,
			// subOrderPs.setTimestamp(index++,
			// DAOUtils.getCurrentTimestamp());//HANDLE_TIME,
			subOrderPs.setString(index++, orderItem.getArchiveDate());// ARCHIVE_DATE,
			subOrderPs.setString(index++, orderItem.getFinishTime());// FINISH_TIME,
			subOrderPs.setString(index++, orderItem.getProdId());// PRODUCT_ID,
			subOrderPs.setString(index++, orderItem.getAccNbr());// ACC_NBR,
			subOrderPs.setString(index++, orderItem.getPackageId());// PACKAGE_ID,
			subOrderPs.setString(index++, orderItem.getProdOffId());// PRODUCT_OFFER_ID,
			subOrderPs.setString(index++, orderItem.getEffTime());// EFF_TIME,
			subOrderPs.setString(index++, orderItem.getExpTime());// EXP_TIME,
			subOrderPs.setString(index++, orderItem.getResultType());// RESULT_TYPE,
			subOrderPs.setString(index++, orderItem.getResultInfo());// RESULT_INFO,
			subOrderPs.setString(index++, orderItem.getLanId());// LAN_ID
			subOrderPs.setString(index++, orderItem.getPprodOffId());
			subOrderPs.setString(index++, orderItem.getProdInstId());
			subOrderPs.setString(index++, orderItem.getServOffId());
			subOrderPs.setLong(index++, DAOUtils.getCurrentMonth());
			subOrderPs.executeUpdate();
		} catch (SQLException ex) {
			logger.error("#saveOrderAndOrderItems ex.", ex);
			throw ex;
		} finally {
			DAOUtils.closeStatement(subOrderPs);
		}
	}
	/**
	 * 通过订单对象写主产品订单项
	 * @param order
	 * @throws Exception
	 */
	public void insertMainProductOrderItemByOrder(CustomerOrder order)
			throws Exception {
		PreparedStatement subOrderPs = null;
		try {
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			String ainsertSql = "insert into ORDER_ITEM (ORDER_ITEM_ID,CUST_ORDER_ID,ORDER_ITEM_CD,ORDER_ITEM_OBJ_ID,CUST_WORKSHEET_ID,STATUS,STATUS_DATE,STATE_CHANGE_REASON,PRIORITY,PRE_HANDLE_FLAG"
					+ ",HANDLE_TIME,ARCHIVE_DATE,FINISH_TIME,PRODUCT_ID,ACC_NBR,PACKAGE_ID,PRODUCT_OFFER_ID,EFF_TIME,EXP_TIME,RESULT_TYPE,RESULT_INFO,LAN_ID,PPROD_OFFER_ID,PROD_INST_ID,SERVICE_OFFER_ID,PARTITION_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+DatabaseFunction.current()+", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";

			subOrderPs = conn.prepareStatement(ainsertSql);
			String orderItemId = null;
			if (null == orderItemId || "".equals(orderItemId)) {
				orderItemId = sequenceManage
						.getNextSequence("SEQ_SUB_ORDER_INFO_ID");
			}
			int index = 1;
			// 保存主产品子订单
			subOrderPs.setString(index++, orderItemId);// ORDER_ITEM_ID
			subOrderPs.setString(index++, order.getCustOrderId());// CUST_ORDER_ID,
			subOrderPs.setString(index++, OrderConstant.ORDER_ITEM_CD_PRODUCT);// ORDER_ITEM_CD,
			subOrderPs.setString(index++, order.getProdInstId());// ORDER_ITEM_OBJ_ID,
			subOrderPs.setString(index++, "");// CUST_WORKSHEET_ID,
			subOrderPs.setString(index++, order.getStatus());// STATUS,
			subOrderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());// STATUS_DATE,
			subOrderPs.setString(index++, "");// STATE_CHANGE_REASON,
			subOrderPs.setString(index++, "");// PRIORITY,
			subOrderPs.setString(index++, "");// PRE_HANDLE_FLAG,
			// subOrderPs.setTimestamp(index++,
			// DAOUtils.getCurrentTimestamp());//HANDLE_TIME,
			subOrderPs.setString(index++, "");// ARCHIVE_DATE,
			subOrderPs.setString(index++, "");// FINISH_TIME,
			subOrderPs.setString(index++, order.getProdId());// PRODUCT_ID,
			subOrderPs.setString(index++, order.getAccNbr());// ACC_NBR,
			subOrderPs.setString(index++, "");// PACKAGE_ID,
			subOrderPs.setString(index++, "");// PRODUCT_OFFER_ID,
			subOrderPs.setString(index++, "");// EFF_TIME,
			subOrderPs.setString(index++, "");// EXP_TIME,
			subOrderPs.setString(index++, "");// RESULT_TYPE,
			subOrderPs.setString(index++, "");// RESULT_INFO,
			subOrderPs.setString(index++, order.getLanId());// LAN_ID
			subOrderPs.setString(index++, "");// PPROD_OFFER_ID
			subOrderPs.setString(index++, order.getProdInstId());
			subOrderPs.setString(index++, order.getCustOrderType());
			subOrderPs.setLong(index++, DAOUtils.getCurrentMonth());
			subOrderPs.executeUpdate();
		} catch (SQLException ex) {
			logger.error("#saveOrderAndOrderItems ex.", ex);
			throw ex;
		} finally {
			DAOUtils.closeStatement(subOrderPs);
		}
	}
	

	/**
	 * 根据所有增值产品新增订单项
	 * @param order
	 * @throws Exception
	 */
	
	public void insertOrderItemsHisByOrder(CustomerOrder order) throws Exception{
		// 保存增值产品子订单
		PreparedStatement subOrderPs = null;
		SequenceManageDAOImpl sequenceManage = new SequenceManageDAOImpl();
		try {
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			subOrderPs = conn.prepareStatement(insertSqlHis);
			
			for (Iterator productOfferItr = order.getProductOfferInfoList().iterator(); productOfferItr.hasNext();) {
				ProductOfferInfo productOffer = (ProductOfferInfo) productOfferItr.next();
				List vSubProdInfoList = productOffer.getVproductInfoList();
				String offerType = productOffer.getOfferType();
				for (Iterator iterator = vSubProdInfoList.iterator(); iterator
						.hasNext();) {
					VproductInfo vSubProdInfo = (VproductInfo) iterator.next();
					String prodInfoId = sequenceManage.getNextSequence("SEQ_SUB_ORDER_INFO_ID");
					long id = Long.valueOf(prodInfoId).longValue();
					int index = 1;
					String prodOfferId = vSubProdInfo.getOfferId();
					String packageId = "";
					String pProdOfferId= "";
					if("1".equals(offerType)){
						packageId = prodOfferId;
						prodOfferId=DcSystemParamManager.getInstance().getofferIdByProductId(vSubProdInfo.getVProductId());
					}else if("2".equals(offerType)){
						pProdOfferId = prodOfferId;
						prodOfferId=DcSystemParamManager.getInstance().getofferIdByProductId(vSubProdInfo.getVProductId());
					}
					subOrderPs.setLong(index++, id);//ORDER_ITEM_ID
					subOrderPs.setString(index++,order.getCustOrderId());//CUST_ORDER_ID,
					subOrderPs.setString(index++, OrderConstant.ORDER_ITEM_CD_PRODOFFER);//ORDER_ITEM_CD,
					subOrderPs.setString(index++, vSubProdInfo.getVProductInstID());//ORDER_ITEM_OBJ_ID,
					subOrderPs.setString(index++, "");//CUST_WORKSHEET_ID,
					subOrderPs.setString(index++, order.getStatus());//STATUS,
					subOrderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATUS_DATE,
					subOrderPs.setString(index++, "");//STATE_CHANGE_REASON,
					subOrderPs.setString(index++, "");//PRIORITY,
					subOrderPs.setString(index++, "");//PRE_HANDLE_FLAG,
//					subOrderPs.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//HANDLE_TIME,
					subOrderPs.setString(index++, "");//ARCHIVE_DATE,
					subOrderPs.setString(index++, "");//FINISH_TIME,
					subOrderPs.setString(index++, vSubProdInfo.getVProductId());//PRODUCT_ID,
					subOrderPs.setString(index++, order.getAccNbr());//ACC_NBR,
					subOrderPs.setString(index++, packageId);//PACKAGE_ID,
					subOrderPs.setString(index++, prodOfferId);//PRODUCT_OFFER_ID,
					subOrderPs.setTimestamp(index++, DAOUtils.parseTimestamp(vSubProdInfo.getEffDate()));//EFF_TIME,
					subOrderPs.setTimestamp(index++, DAOUtils.parseTimestamp(vSubProdInfo.getExpDate()));//EXP_TIME,
					subOrderPs.setString(index++, "");//RESULT_TYPE,
					subOrderPs.setString(index++, "");//RESULT_INFO,
					subOrderPs.setString(index++, order.getLanId());//LAN_ID
					subOrderPs.setString(index++, pProdOfferId);
					subOrderPs.setString(index++, order.getProdInstId());
//					subOrderPs.setString(index++, order.getCustOrderType());
					subOrderPs.setString(index++, vSubProdInfo.getActionType());//改写增值产品对应的动作，不是订单对应的动作
					subOrderPs.setLong(index++,DAOUtils.getCurrentMonth());
					//subOrderPs.executeUpdate();
					subOrderPs.addBatch();
				}
				if(vSubProdInfoList.size() > 0)
					subOrderPs.executeBatch();
				}
		} catch (SQLException e) {
			logger.error("insertOrderItemsByOrder #ex.", e);
			throw e;
		} finally {
			DAOUtils.closeStatement(subOrderPs);
		}
	}

}
