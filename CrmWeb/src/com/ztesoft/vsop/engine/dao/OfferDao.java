package com.ztesoft.vsop.engine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.crm.product.dao.ProdOffDetaRoleDAO;
import com.ztesoft.vsop.DateUtil;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.RefreshCacheHttpClient;

public class OfferDao {
	public String createProdOff(ProductOfferInfo offer) throws Exception {
		List productList =offer.getVproductInfoList();
		String prodOfferId = this.insertProdOff(offer);

		// 插入PROD_OFFER_DETAIL_ROLE关系
		ProdOffDetaRoleDAO prodOfferDao = new ProdOffDetaRoleDAO();
		for (Iterator ite = productList.iterator(); ite.hasNext();) {
			VproductInfo product = (VproductInfo) ite.next();
			Map prodOffDetaRole = new HashMap();
			prodOffDetaRole.put("prod_offer_id", prodOfferId);
			prodOffDetaRole.put("product_id", product.getVProductId());
			prodOfferDao.insertProdOffRel(prodOffDetaRole);
		}
		return prodOfferId;
	}
	public String insertProdOff(ProductOfferInfo offer) throws Exception {
		PreparedStatement ps = null;
		String offerId="";
		SequenceManageDAOImpl sequenceManage = new SequenceManageDAOImpl();
		try {
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			String insertSql = "insert into prod_offer (prod_offer_id,prod_offer_sub_type,state_date,eff_date,exp_date,offer_nbr,state,prod_offer_name,PACKAGE_HOST) "
					+ " values (?,2, "+DatabaseFunction.current()+", ?, ?, ?, ?, ?,?)";

			ps = conn.prepareStatement(insertSql);
			offerId = sequenceManage
						.getNextSequence("SEQ_SUB_ORDER_INFO_ID");
			int index = 1;
			// 保存主产品子订单
			ps.setString(index++, offerId);// offerid
			ps.setTimestamp(index++, DAOUtils.parseTimestamp(offer.getEffDate(),DateUtil.DATE_TIME_FORMAT_14));
			ps.setTimestamp(index++, DAOUtils.parseTimestamp(offer.getExpDate(),DateUtil.DATE_TIME_FORMAT_14));
			ps.setString(index++, offer.getOfferNbr());// offernbr
			ps.setString(index++, "0");// status
			ps.setString(index++, offer.getOfferName());//offername
			ps.setString(index++, "204");//PACKAGE_HOST
			ps.executeUpdate();
			//增量刷新销售品相关信息
			//RefreshCacheHttpClient.getInstance().refreshAllServerCaches(DcSystemParamManager.CACHE_OBJECTTYPE_PRODOFFER1,offerId);
			//DcSystemParamManager.getInstance().getIncrementData(DcSystemParamManager.CACHE_OBJECTTYPE_PRODOFFER1,offerId);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DAOUtils.closeStatement(ps);
		}
		return offerId;
	}

}
