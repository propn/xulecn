package com.ztesoft.vsop.engine.help;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.engine.vo.VproductInfo;

public class VproductHelp {
	protected static Logger logger = Logger.getLogger(VproductHelp.class);
	/**
	 * 根据销售品id查询该销售品所有的增值产品
	 * @param prodOfferId
	 * @param actionType
	 * @param effDate
	 * @param expDate
	 * @return
	 * @throws SQLException 
	 */
	public List getVProductsByProdOfferId(String prodOfferId, String prodInstId) throws SQLException {
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select a.prod_offer_id, b.product_id,po.offer_nbr,p.product_nbr,po.prod_offer_sub_type " +
				" from PROD_OFFER_DETAIL_ROLE a, ROLE_PROD_RELA b,prod_offer po,product p  ,order_relation r " +
				" where a.prod_offer_role_cd = b.prod_offer_role_cd and po.prod_offer_id=a.prod_offer_id " +
				" and b.product_id=p.product_id and po.prod_offer_id = r.prod_offer_id and r.prod_offer_id = ? and r.prod_inst_id = ? and r.state='00A' ";
		List ret = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, prodOfferId);
			ps.setString(2, prodInstId);
			rs = ps.executeQuery();
			ret = new ArrayList();
			while(rs.next()){
				VproductInfo subProduct = new VproductInfo();
				subProduct.setOfferId(prodOfferId);
				subProduct.setOfferNbr(rs.getString("offer_nbr"));
				subProduct.setVProductNbr(rs.getString("product_nbr"));
				subProduct.setVProductId(rs.getString("PRODUCT_ID"));
				subProduct.setOfferType(rs.getString("prod_offer_sub_type"));
				subProduct.setVProductInstID(prodInstId);
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
	
	/**
	 * 根据捆绑销售品id查询该捆绑销售品所有的增值产品
	 * @param prodOfferId
	 * @param actionType
	 * @param effDate
	 * @param expDate
	 * @return
	 * @throws SQLException 
	 */
	public List getVProductsByPackageId(String packageId, String prodInstId) throws SQLException {
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select a.prod_offer_id, b.product_id,po.offer_nbr,p.product_nbr,po.prod_offer_sub_type " +
				" from PROD_OFFER_DETAIL_ROLE a, ROLE_PROD_RELA b,prod_offer po,product p  ,order_relation r " +
				" where a.prod_offer_role_cd = b.prod_offer_role_cd and po.prod_offer_id=a.prod_offer_id " +
				" and b.product_id=p.product_id and po.prod_offer_id = r.prod_offer_id and r.package_id = ? and r.prod_inst_id = ? ";
		List ret = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, packageId);
			ps.setString(2, prodInstId);
			rs = ps.executeQuery();
			ret = new ArrayList();
			while(rs.next()){
				VproductInfo subProduct = new VproductInfo();
				subProduct.setOfferId(packageId);
				subProduct.setOfferNbr(rs.getString("offer_nbr"));
				subProduct.setVProductNbr(rs.getString("product_nbr"));
				subProduct.setVProductId(rs.getString("PRODUCT_ID"));
				subProduct.setOfferType(rs.getString("prod_offer_sub_type"));
				subProduct.setVProductInstID(prodInstId);
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
	
	/**
	 * 根据传统+捆绑销售品id查询该传统+捆绑销售品所有的增值产品
	 * @param prodOfferId
	 * @param actionType
	 * @param effDate
	 * @param expDate
	 * @return
	 * @throws SQLException 
	 */
	public List getVProductsByPProdOfferId(String PProdOfferId, String prodInstId) throws SQLException {
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select a.prod_offer_id, b.product_id,po.offer_nbr,p.product_nbr,po.prod_offer_sub_type " +
				" from PROD_OFFER_DETAIL_ROLE a, ROLE_PROD_RELA b,prod_offer po,product p  ,order_relation r " +
				" where a.prod_offer_role_cd = b.prod_offer_role_cd and po.prod_offer_id=a.prod_offer_id " +
				" and b.product_id=p.product_id and po.prod_offer_id = r.prod_offer_id and r.pprod_offer_id = ? and r.prod_inst_id = ? ";
		List ret = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, PProdOfferId);
			ps.setString(2, prodInstId);
			rs = ps.executeQuery();
			ret = new ArrayList();
			while(rs.next()){
				VproductInfo subProduct = new VproductInfo();
				subProduct.setOfferId(PProdOfferId);
				subProduct.setOfferNbr(rs.getString("offer_nbr"));
				subProduct.setVProductNbr(rs.getString("product_nbr"));
				subProduct.setVProductId(rs.getString("PRODUCT_ID"));
				subProduct.setOfferType(rs.getString("prod_offer_sub_type"));
				subProduct.setVProductInstID(prodInstId);
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
}
