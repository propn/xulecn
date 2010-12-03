package com.ztesoft.vsop.engine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.vsop.engine.vo.AproductInfo;
import com.ztesoft.vsop.engine.vo.BizCapabilityInstVO;


public class BizCapInstHelpDao {
	protected static Logger logger = Logger.getLogger(BizCapInstHelpDao.class);
	private String SQL_SELECT = "SELECT SERV_PRODUCT_ID,PRODUCT_ID,PROD_INST_ID,LAN_ID,STATE FROM BIZ_CAPABILITY_INST";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM BIZ_CAPABILITY_INST";

	private int maxRows;
	/**
	 * 批量新增业务能力实例
	 * @param prodInstId 产品实例id
	 * @param aProducts 业务能力
	 * @throws Exception
	 */
	public void insertBizCapInstByAprodusts(String prodInstId,List aProducts) throws Exception{
		if(aProducts != null && aProducts.size()>0){
			Connection conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			for (Iterator iterator = aProducts.iterator(); iterator.hasNext();) {
				AproductInfo aproduct = (AproductInfo) iterator.next();
					addAproduct(aproduct, prodInstId,conn);
				}
			}
	}
	/**
	 * 批量删除业务能力实例
	 * @param prodInstId 产品实例id
	 * @param aProducts 业务能力
	 * @throws SQLException 
	 * @throws Exception
	 */
	public void deleteBizCapInstByAprodusts(String prodInstId,List aProducts) throws SQLException{
		if(aProducts != null && aProducts.size()>0){
			Connection conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			for (Iterator iterator = aProducts.iterator(); iterator.hasNext();) {
				AproductInfo aproduct = (AproductInfo) iterator.next();
				deleteAproduct(aproduct, prodInstId,conn);
				}
			}
	}
	/**
	 * 批量修改业务能力实例
	 * @param prodInstId
	 * @param aProducts
	 * @throws Exception
	 */
	public void modifyBizCapInstByAprodusts(String prodInstId,List aProducts) throws Exception{
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
		for (Iterator iterator = aProducts.iterator(); iterator.hasNext();) {
			AproductInfo aproduct = (AproductInfo) iterator.next();
			if(AproductInfo.actionTypeOfAdd.equals(aproduct.getActionType())){
				addAproduct(aproduct, prodInstId, conn);
			}else if(AproductInfo.actionTypeOfCancel.equals(aproduct.getActionType())){
				deleteAproduct(aproduct, prodInstId, conn);
			}
		}
	}
	/**
	 * 新增附属产品实例
	 * @param aproduct
	 * @param conn 
	 * @param prodInstId 
	 * @throws SQLException 
	 */
	public void addAproduct(AproductInfo aproduct, String prodInstId, Connection conn) throws SQLException  {
		PreparedStatement ps2 = null;
		try {
			if("".equals(aproduct.getAProductInstID())){
				SequenceManageDAOImpl sequenceManage = new SequenceManageDAOImpl();
				String aproductId = sequenceManage.getNextSequence("SEQ_BIZ_CAPABILITY_INST_ID");
				aproduct.setAProductInstID(aproductId);
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
			DAOUtils.closeStatement(ps2);
		}
	}
	/**
	 * 附属产品退订
	 * @param aproduct
	 * @param prodInstId 
	 * @param conn 
	 * @throws SQLException 
	 */
	public void deleteAproduct(AproductInfo aproduct, String prodInstId, Connection conn) throws SQLException {
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
	 * 查询用户的业务能力附属产品
	 * @param prodInstId
	 * @return
	 */
	public List qryBizCapInstsByProdInstId(String prodInstId){
		return this.findByCond(" prod_inst_id=? ", new String[]{prodInstId});
	}
	private List findByCond(String whereCond, String[] para) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = "";
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			SQL = SQL_SELECT + " WHERE " + whereCond;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
			if (para != null) {
				for (int i = 0; i < para.length; i++) {
					stmt.setString(i + 1, para[i]);
				}
			}
			stmt.setMaxRows( maxRows );
			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
		}
		catch (SQLException se) {
			Debug.print(SQL,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+SQL, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
		}
	}
	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			BizCapabilityInstVO vo = new BizCapabilityInstVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(BizCapabilityInstVO vo, ResultSet rs) throws SQLException {
		vo.setServProdId( DAOUtils.trimStr( rs.getString( "SERV_PRODUCT_ID" ) ) );
		vo.setProdId( DAOUtils.trimStr( rs.getString( "PRODUCT_ID" ) ) );
		vo.setProdInstId( DAOUtils.trimStr( rs.getString( "PROD_INST_ID" ) ) );
		vo.setLanId( DAOUtils.trimStr( rs.getString( "LAN_ID" ) ) );
		vo.setState( DAOUtils.trimStr( rs.getString( "STATE" ) ) );
	}
}
