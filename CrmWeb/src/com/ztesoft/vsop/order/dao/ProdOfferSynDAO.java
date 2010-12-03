package com.ztesoft.vsop.order.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.webservice.vo.InfProdOfferInst;
import com.ztesoft.vsop.webservice.vo.OfferDetailInfo;

public class ProdOfferSynDAO {
	private static Logger logger = Logger.getLogger(ProdOfferSynDAO.class);
	private String insertSql = "insert into INF_PRODUCT_OFFER_INSTANCE(INF_OFFER_INSTANCE_ID,OFFER_INST_ID,OFFER_ID,COMP_OFFER_ID,EFF_DATE,EXP_DATE,OPER_TYPE,LAN_ID,STATE,STATE_DATE,CREATE_DATE,SEND_TIMES,PARTITION_ID)" +
							   " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private String insertDetailSql = "insert into INF_OFFER_INST_DET(INF_OFFER_INST_DET_ID,INF_OFFER_INSTANCE_ID,OFFER_INST_ID,OFFER_ID,COMP_OFFER_ID," +
									 "EFF_DATE,EXP_DATE,OPER_TYPE,LAN_ID,INSTANCE_RELATION_ID,PRODUCT_ID,OFFER_DETAIL_ID,STATE_DATE,CREATE_DATE,PARTITION_ID)" +
									 " values(SEQ_INF_OFFER_INST_DET_ID.Nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public boolean insertInfProdOffInst(InfProdOfferInst vo) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement psCount = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int index = 1;
		int result = 0;
		try {
			if(CrmConstants.DB_TYPE_INFORMIX.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
				psCount = conn.prepareStatement("select first 1 count(*) colNum from INF_PRODUCT_OFFER_INSTANCE where OFFER_INST_ID="+vo.getOfferInstId());
			}else{
				psCount = conn.prepareStatement("select count(*) colNum from INF_PRODUCT_OFFER_INSTANCE where OFFER_INST_ID="+vo.getOfferInstId() + " and rownum=1");
				
			}
			rs = psCount.executeQuery();
			int count = -1;
			if(rs.next()){
				count = rs.getInt("colNum");
			}
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(psCount);
			if(count >= 1)return false;
			ps = conn.prepareStatement(insertSql);
			
			ps.setString(index++, vo.getInfOfferInstanceId()); //
			ps.setString(index++, vo.getOfferInstId()); //
			ps.setString(index++, vo.getOfferId());//
			ps.setString(index++, vo.getCompOfferId());//
			ps.setDate(index++, DAOUtils.parseDateTime(vo.getEffDate()));
			ps.setDate(index++,DAOUtils.parseDateTime( vo.getExpDate()));
			ps.setString(index++, vo.getOperType());//
			ps.setString(index++, vo.getLanId());//
			ps.setString(index++, "U");//STATE
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//state_date,
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//create_date,
			ps.setString(index++, "0");//send_times
			ps.setLong(index++, DAOUtils.getCurrentMonth());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#insertInfProdOffInst ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public boolean insertInfProdOffDetail(InfProdOfferInst vo,OfferDetailInfo detailVo) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int index = 1;
		int result = 0;
		try {
			ps = conn.prepareStatement(insertDetailSql);
			
			ps.setString(index++, vo.getInfOfferInstanceId()); //
			ps.setString(index++, vo.getOfferInstId()); //
			ps.setString(index++, vo.getOfferId());//
			ps.setString(index++, vo.getCompOfferId());//
			ps.setDate(index++, DAOUtils.parseDateTime(vo.getEffDate()));
			ps.setDate(index++,DAOUtils.parseDateTime( vo.getExpDate()));
			ps.setString(index++, vo.getOperType());//
			ps.setString(index++, vo.getLanId());//
			ps.setString(index++, detailVo.getInstanceRelationId());
			ps.setString(index++, detailVo.getProductId());
			ps.setString(index++, detailVo.getOfferDetailId());
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//state_date,
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//create_date,
			ps.setLong(index++, DAOUtils.getCurrentMonth());
//			ps.setString(index++, "0");//send_times
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#insertInfProdOffInst ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	/**
	 * 按分页读取接口表记录
	 * @param limit
	 * @return
	 * @throws SQLException
	 */
	public List getUnDealOrders(int limit) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement psmt = null;
		PreparedStatement updatePsmt = null;
		ResultSet rs = null;
		//PreparedStatement psInfMsgId = null;
		//String updateSql = "UPDATE INF_PRODUCT_OFFER_INSTANCE SET STATE = ? WHERE INF_OFFER_INSTANCE_ID in (SELECT ID FROM TMP_ID)";
		String updateSql =  "UPDATE INF_PRODUCT_OFFER_INSTANCE SET STATE = ? WHERE INF_OFFER_INSTANCE_ID=?";
		//String sql = "SELECT * FROM INF_PRODUCT_OFFER_INSTANCE WHERE INF_OFFER_INSTANCE_ID in (SELECT ID FROM TMP_ID)";
		String sql_oracle =  "SELECT * FROM INF_PRODUCT_OFFER_INSTANCE WHERE INF_OFFER_INSTANCE_ID in (SELECT ID FROM TMP_ID) STATE in ('U','F') and SEND_TIMES <= 3 AND ROWNUM <= ? ORDER BY INF_OFFER_INSTANCE_ID";
		String sql_informix = "SELECT first ? * FROM INF_PRODUCT_OFFER_INSTANCE WHERE INF_OFFER_INSTANCE_ID in (SELECT ID FROM TMP_ID) STATE in ('U','F') and SEND_TIMES <= 3 ORDER BY INF_OFFER_INSTANCE_ID";
		
		//String insertSql = "INSERT INTO TMP_ID(ID) SELECT INF_OFFER_INSTANCE_ID FROM INF_PRODUCT_OFFER_INSTANCE WHERE STATE in ('U','F') and SEND_TIMES <= 3 AND ROWNUM <= ? ORDER BY INF_OFFER_INSTANCE_ID";
		
		try {
			//增加informix支持  修改为直接在原表上更新状态的方式
			String selectSql = sql_oracle;
			if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
				selectSql = sql_informix;
			}
			psmt = conn.prepareStatement(selectSql);
			psmt.setInt(1, limit);
			rs = psmt.executeQuery();
			List unDealOrderList = new ArrayList();
			
			updatePsmt = conn.prepareStatement(updateSql);
			while(rs.next()){
				String infMsgId = rs.getString("INF_OFFER_INSTANCE_ID");
				InfProdOfferInst infMsg = new InfProdOfferInst();
				infMsg.setInfOfferInstanceId(infMsgId);
				infMsg.setOfferInstId(rs.getString("OFFER_INST_ID"));
				infMsg.setOfferId(rs.getString("OFFER_ID"));
				infMsg.setCompOfferId(rs.getString("COMP_OFFER_ID"));
				infMsg.setEffDate(DAOUtils.getFormatedDateTime(rs.getTimestamp("EFF_DATE")));
				infMsg.setExpDate(DAOUtils.getFormatedDateTime(rs.getTimestamp("EXP_DATE")));
				infMsg.setOperType(rs.getString("OPER_TYPE"));
				infMsg.setLanId(rs.getString("LAN_ID"));
				infMsg.setState(rs.getString("STATE"));
				infMsg.setStateDate(rs.getString("STATE_DATE"));
				infMsg.setCreateDate(rs.getString("CREATE_DATE"));
				infMsg.setSendTimes(rs.getString("SEND_TIMES"));
				unDealOrderList.add(infMsg);
				
				updatePsmt.setString(1, infMsgId);
				updatePsmt.setString(2, "D");
			}
			
			if(unDealOrderList.size() > 0){
				updatePsmt.executeUpdate();
				return unDealOrderList;
			}
		} catch(SQLException e){
			logger.error("#getUnDealOrders ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(psmt);
			DAOUtils.closeStatement(updatePsmt);
		}
		return null;
	}
	/**
	 * 更新状态，成功后归档
	 * @param infMagId
	 * @param success
	 * @param resultMsg
	 * @throws Exception
	 */
	public void updateInfOfferState(String infMagId, boolean success, String resultMsg) throws Exception {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement updatePsmt = null;
		PreparedStatement insertPsmt = null;
		PreparedStatement deletePsmt = null;
		String updateSql = "UPDATE INF_MSG SET STATE_DATE=?,STATE = ?,RESULT_MSG=? WHERE INF_MSG_ID =?";
		String insertSql = "INSERT INTO INF_MSG_L SELECT * FROM INF_MSG WHERE INF_MSG_ID=? ";
		String state = "failState";
		if(success) state = "S";
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
			logger.error("#updateInfOfferState ex.",e);
			throw e;
		}finally{
			DAOUtils.closeStatement(updatePsmt);
			DAOUtils.closeStatement(insertPsmt);
			DAOUtils.closeStatement(deletePsmt);
		}
	}
	public List getOfferDetail(String id) throws Exception{
		String querySql = "select a.INSTANCE_RELATION_ID,a.OFFER_DETAIL_ID,a.PRODUCT_ID,b.acc_nbr,b.product_id product_type,b.lan_id from INF_OFFER_INST_DET a,prod_inst b " +
						  "where a.product_id=b.prod_inst_id and a.INF_OFFER_INSTANCE_ID="+id;
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List resultList = new ArrayList();
		try{
			psmt = conn.prepareStatement(querySql);
			rs = psmt.executeQuery();
			while(rs.next()){
				OfferDetailInfo vo = new OfferDetailInfo();
				vo.setInstanceRelationId(rs.getString("INSTANCE_RELATION_ID"));
				vo.setOfferDetailId(rs.getString("OFFER_DETAIL_ID"));
				vo.setProductId(rs.getString("PRODUCT_ID"));
				vo.setProdType(rs.getString("product_type"));
				vo.setLanId(rs.getString("lan_id"));
				String accNbr = rs.getString("acc_nbr");
				String regionCode = accNbr.substring(0, 4);
				vo.setAccNbr(accNbr);
				vo.setRegionCode(regionCode);
				resultList.add(vo);
			}
		}catch(Exception e){
			logger.error("#getOfferDetail ex.",e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(psmt);
		}
		return resultList;
	}
	public String isContinue(OfferDetailInfo detailVo) throws Exception{
		String offerDetailId = detailVo.getOfferDetailId();
		String productInstId = detailVo.getProductId();
		if(offerDetailId == null || "".equals(offerDetailId) ||
				productInstId == null || "".equals(productInstId))return "0";
		//判断是否可继续执行操作逻辑：vsop是否已经有优惠下对应的增值产品订购，有则更新并加入优惠，无则转入等待。
		/*String querySql = "select count(*) numcol from INCR_PRODUCT_RESTRICT where offer_detail_id="+offerDetailId+
						  " and incr_product_str like '%'||"+
						  "(select c.CRM_CODE from order_relation a,product b,crm_ismp_code_map c "+
						  " where a.product_id=b.product_id and b.product_nbr=c.ISMP_CODE and a.prod_inst_id = "+productInstId+")"//编码映射时暂时不增加系统编码限制条件
						  +"||'%' and rownum=1";*/
		Connection conn = LegacyDAOUtil.getConnection();
		CallableStatement cs = null;
		int num = 0;
		String vStr = "";
		try{
			cs = conn.prepareCall("{? = call FUN_ISEXITS_OFFER_INST(?,?,?)}");
			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.setString(2, offerDetailId);
			cs.setString(3, productInstId);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.execute();
			num = cs.getInt(1);
			vStr = cs.getString(4);
			vStr = num+"#"+vStr;
//			if(rs.next()){
//				num = rs.getInt("numcol");
//			}
		}catch(Exception e){
			logger.error("#getOfferDetail ex.",e);
			throw e;
		}finally{
			if(cs != null)cs.close();
//			DAOUtils.closeResultSet(rs);
//			DAOUtils.closeStatement(psmt);
		}
		return vStr;
	}
	//取出增值产品ID
	//str:crm侧获取的优惠下面必选的增值产品串
	public List getProductId(String str,String productInstId) throws Exception{
		String sql = "select distinct a.product_id,c.ismp_code from order_relation a,product b,crm_ismp_code_map c "+
					 " where a.product_id=b.product_id and b.product_nbr=c.ISMP_CODE and a.prod_inst_id = "+productInstId+
					 " and c.CRM_CODE in ("+str+")";
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try{
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()){
				String prodId = rs.getString("product_id");
				String ismpCode = rs.getString("ismp_code");
				list.add(prodId+","+ismpCode);
			}
		}catch(Exception e){
			logger.error("#getProductId ex.",e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(psmt);
		}
		return list;
	}
	public boolean insertOfferInst(InfProdOfferInst vo) throws SQLException{
		String addSql = "insert into PRODUCT_OFFER_INSTANCE(PRODUCT_OFFER_INSTANCE_ID,CUST_ID,PRODUCT_OFFER_ID,EFF_DATE,EXP_DATE,STATE,STATE_DATE," +
						"CREATED_DATE,LAN_ID,OFFER_COMP_ID)values(?,-1,?,?,?,?,?,?,?,?)";
		logger.info("insertOfferInst addSql:"+addSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int index = 1;
		int result = 0;
		try {
			ps = conn.prepareStatement(addSql);
			
			ps.setString(index++, vo.getOfferInstId()); //
			ps.setString(index++, vo.getOfferId()); //
			ps.setDate(index++, DAOUtils.parseDateTime(vo.getEffDate()));
			ps.setDate(index++,DAOUtils.parseDateTime( vo.getExpDate()));
			ps.setString(index++, "00A");//
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//
			ps.setString(index++, vo.getLanId());//
			ps.setString(index++, vo.getCompOfferId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#insertOfferInst ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public boolean insertOfferDetailInst(InfProdOfferInst vo, OfferDetailInfo detailVo) throws SQLException{
		String addSql = "insert into PRODUCT_OFFER_INSTANCE_DETAIL(INSTANCE_RELATION_ID,PRODUCT_OFFER_INSTANCE_ID,PRO_OFFER_DETAIL_ID,INSTANCE_ID," +
						"OFFER_DETAIL_ID,EFF_DATE,EXP_DATE,STATE,STATE_DATE,CREATED_DATE)values(?,?,?,?,?,?,?,?,?,?)";
		logger.info("insertOfferDetailInst addSql:"+addSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int index = 1;
		int result = 0;
		try {
			ps = conn.prepareStatement(addSql);
			
			ps.setString(index++, detailVo.getInstanceRelationId()); //
			ps.setString(index++, vo.getOfferInstId()); //
			ps.setString(index++, detailVo.getOfferDetailId());//
			ps.setString(index++, detailVo.getProductId());//
			ps.setString(index++, detailVo.getOfferDetailId());
			ps.setDate(index++, DAOUtils.parseDateTime(vo.getEffDate()));
			ps.setDate(index++,DAOUtils.parseDateTime( vo.getExpDate()));
			ps.setString(index++, "00A");
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#insertOfferDetailInst ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public boolean updateOfferInst(InfProdOfferInst vo) throws SQLException{
		String updSql = "update PRODUCT_OFFER_INSTANCE set EXP_DATE = ?,STATE_DATE = ? " +
						" where PRODUCT_OFFER_INSTANCE_ID = "+vo.getOfferInstId();
		logger.info("updateOfferInst updSql:"+updSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int index = 1;
		int result = 0;
		try {
			ps = conn.prepareStatement(updSql);
			ps.setDate(index++,DAOUtils.parseDateTime( vo.getExpDate()));
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#updateOfferInst ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public boolean udpateOfferDetailInst(InfProdOfferInst vo,OfferDetailInfo detailVo) throws SQLException{
		String updSql = "update PRODUCT_OFFER_INSTANCE_DETAIL set EXP_DATE = ?,STATE_DATE = ? " +
						" where product_offer_instance_id = "+vo.getOfferInstId();
		logger.info("udpateOfferDetailInst updSql:"+updSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int index = 1;
		int result = 0;
		try {
			ps = conn.prepareStatement(updSql);
			ps.setDate(index++,DAOUtils.parseDateTime( vo.getExpDate()));
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#udpateOfferDetailInst ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public boolean delOfferInst(String id) throws SQLException{
		String updSql = "delete from PRODUCT_OFFER_INSTANCE " +
						" where PRODUCT_OFFER_INSTANCE_ID = "+id;
		logger.info("delOfferInst updSql:"+updSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = conn.prepareStatement(updSql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#delOfferInst ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public boolean delOfferDetailInst(String id) throws SQLException{
		String updSql = "delete from PRODUCT_OFFER_INSTANCE_DETAIL " +
						" where PRODUCT_OFFER_INSTANCE_ID = "+id;
		logger.info("delOfferDetailInst updSql:"+updSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = conn.prepareStatement(updSql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#delOfferDetailInst ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public String getSequence(String sequenceName) throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		long seq = 0;
		ResultSet rs = null;
		ps = conn.prepareStatement("SELECT " + sequenceName + ".NEXTVAL FROM DUAL");
		rs = ps.executeQuery();
		if(rs.next()){
			seq = rs.getLong(1);
		}
		if(rs!=null) rs.close();
		if(ps!=null) ps.close();
		return String.valueOf(seq);
	}
	public boolean updateOrderRela(String prodInstId, String offerId, String productId) throws SQLException{
		String updSql = "update order_relation set pprod_offer_id = '" + offerId+"'"+
						" where prod_inst_id = "+ prodInstId +" and product_id="+productId;
		logger.info("updateOrderRela updSql:"+updSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = conn.prepareStatement(updSql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#updateOrderRela ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public boolean insertOfferDetail(String id)throws SQLException{
		String addSql = "insert into INF_OFFER_INST_DET_HIS "+
		" select * from INF_OFFER_INST_DET where INF_OFFER_INSTANCE_ID="+id;
		logger.info("insertOfferDetail updSql:"+addSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = conn.prepareStatement(addSql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#insertOfferDetail ex.", e);
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public boolean delOfferDetail(String id)throws SQLException{
		String delSql = "delete from INF_OFFER_INST_DET "+
						   " where INF_OFFER_INSTANCE_ID="+id;
		logger.info("delOfferDetail updSql:"+delSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = conn.prepareStatement(delSql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#delOfferDetail ex.", e);
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public boolean insertOffer(String id)throws SQLException{
		String addSql = "insert into INF_PRODUCT_OFFER_INSTANCE_HIS "+
		" select * from INF_PRODUCT_OFFER_INSTANCE where INF_OFFER_INSTANCE_ID="+id;
		logger.info("insertOfferDetail updSql:"+addSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = conn.prepareStatement(addSql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#insertOfferDetail ex.", e);
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public boolean delOffer(String id)throws SQLException{
		String delSql = "delete from INF_PRODUCT_OFFER_INSTANCE "+
						   " where INF_OFFER_INSTANCE_ID="+id;
		logger.info("delOfferDetail updSql:"+delSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = conn.prepareStatement(delSql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#delOfferDetail ex.", e);
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public void dealOperException(InfProdOfferInst vo)throws SQLException{
		String delSql = "update INF_PRODUCT_OFFER_INSTANCE set send_times=send_times+1,state='F'"
				+ " where INF_OFFER_INSTANCE_ID=" + vo.getInfOfferInstanceId();
		logger.info("dealOperException delSql:" + delSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(delSql);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#dealOperException ex.", e);
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
		}
	}
	public void dealOperWait(InfProdOfferInst vo)throws SQLException{
		String delSql = "update INF_PRODUCT_OFFER_INSTANCE set state='F'"
				+ " where INF_OFFER_INSTANCE_ID=" + vo.getInfOfferInstanceId();
		logger.info("dealOperException delSql:" + delSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(delSql);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#dealOperException ex.", e);
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
		}
	}
	public boolean delOfferInstBefore(String id)throws SQLException{
		String delSql = "delete from PRODUCT_OFFER_INSTANCE "+
						   " where INF_OFFER_INSTANCE_ID="+id;
		logger.info("delOfferDetail updSql:"+delSql);
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = conn.prepareStatement(delSql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#delOfferDetail ex.", e);
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}
	public boolean isSyn(OfferDetailInfo detailVo) throws Exception{
		String offerDetailId = detailVo.getOfferDetailId();
		String productInstId = detailVo.getProductId();
		if(offerDetailId == null || "".equals(offerDetailId) ||
				productInstId == null || "".equals(productInstId))return false;
		//当前查询是在一个优惠下面只有一个ismp的增值产品(可以有多个附属产品)的情况下才需要同步优惠给vsop。
		/*String querySql = "select count(*) numcol from INCR_PRODUCT_RESTRICT where offer_detail_id="+offerDetailId+
						  " and incr_product_str like '%'||"+
						  "(select c.CRM_CODE from product b,crm_ismp_code_map c "+
						  " where b.product_nbr=c.ISMP_CODE)"//编码映射时暂时不增加系统编码限制条件
						  +"||'%' and rownum=1";*/
		Connection conn = LegacyDAOUtil.getConnection();
		CallableStatement cs = null;
		int num = 0;
		try{
			cs = conn.prepareCall("{? = call FUN_GET_ISSYN_OFFER(?)}");
			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.setString(2, offerDetailId);
			cs.execute();
			num = cs.getInt(1);
//			rs = cs.
//			if(rs.next()){
//				num = rs.getInt("numcol");
//			}
		}catch(Exception e){
			logger.error("#getOfferDetail ex.",e);
			throw e;
		}finally{
			if(cs != null)cs.close();
//			DAOUtils.closeResultSet(rs);
//			DAOUtils.closeStatement(cs);
		}
		return num > 0;
	}
}
