package com.ztesoft.vsop.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.vsop.DateUtil;
import com.ztesoft.vsop.InfOrderRelationVo;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.web.DcSystemParamManager;
import common.Logger;

/**
 * 
 * @author qin.guoquan
 *
 */
public class InfOrderRelationDao {
	
	private Logger log = Logger.getLogger(InfOrderRelationDao.class);
	
	private final String INF_SEND_TIMES = DcSystemParamManager.getParameter(VsopConstants.INF_SEND_TIMES);
	private final String INF_ROWNUM = DcSystemParamManager.getParameter(VsopConstants.INF_ROWNUM);

	private String SQL_SELECT = "select inf_order_relation_id,user_id,user_id_type,product_id,package_id,op_type,state,state_date,create_date,send_times " +
			"from inf_order_relation " +
			"where (state = 'U' or state = 'F') and send_times < ? and rownum <= ? order by state_date";
	


	/**
	 * ������ϵ����crmͬ���ӿڱ��ѯ����״̬ʱ������,ȡǰ100��״̬ΪU����F���ҷ��ʹ���С��4�ļ�¼
	 * @return SQLException
	 */
	public List getInfOrderRelaAndDeal() throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement psmt = null;
		PreparedStatement updatePsmt = null;
		ResultSet rs = null;
//		PreparedStatement psInfMsgId = null;
		String updateSql = "UPDATE inf_order_relation SET STATE = ? WHERE inf_order_relation_id = ?";
		String sql = "SELECT inf_order_relation_id,user_id,user_id_type,product_id,package_id,op_type,state,state_date,create_date,send_times" +
					 " FROM inf_order_relation WHERE (state = 'U' or state = 'F') and send_times < ? AND ROWNUM <= ? ORDER BY inf_order_relation_id";
//		String insertSql = "INSERT INTO INF_RELA_TMP_ID(ID) SELECT inf_order_relation_id FROM inf_order_relation WHERE (state = 'U' or state = 'F') and send_times < ? AND ROWNUM <= ? ORDER BY inf_order_relation_id";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, INF_SEND_TIMES);
			psmt.setInt(2, Integer.parseInt(INF_ROWNUM));
			rs = psmt.executeQuery();
			
			List infOrderRelationList = new ArrayList();
			updatePsmt = conn.prepareStatement(updateSql );
			while(rs.next()){
				InfOrderRelationVo vo = new InfOrderRelationVo();
				vo.setInfOrderRelationId(rs.getString("inf_order_relation_id"));
				vo.setUserId(rs.getString("user_id"));
				vo.setUserIdType(rs.getString("user_id_type"));
				vo.setProductId(rs.getString("product_id"));
				vo.setPackageId(rs.getString("package_id"));
				vo.setOpType(rs.getString("op_type"));
				vo.setState(rs.getString("state"));
				vo.setStateDate(rs.getString("state_date"));
				vo.setCreateDate(rs.getString("create_date"));
				vo.setSendTimes(rs.getString("send_times"));
				infOrderRelationList.add(vo);
				
				updatePsmt.setString(1, "D");
				updatePsmt.setString(2, rs.getString("inf_order_relation_id"));
				updatePsmt.addBatch();
			}
			
			if(infOrderRelationList.size() > 0){
				
				updatePsmt.executeBatch();
				return infOrderRelationList;
			}
		} catch(SQLException e){
			log.error("#getInfOrderRelaAndDeal ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(psmt);
//			DAOUtils.closeStatement(psInfMsgId);
			DAOUtils.closeStatement(updatePsmt);
			
		}
		return null;
	}
	/**
	 * ��ѯ����״̬ʱ������,ȡǰ100��״̬ΪU����F���ҷ��ʹ���С��4�ļ�¼
	 * @return
	 */
	public List getInfOrderRelation() {
		List infOrderRelationList = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = LegacyDAOUtil.getConnection();
			ps = con.prepareStatement(SQL_SELECT);
			ps.setString(1, INF_SEND_TIMES);
			ps.setString(2, INF_ROWNUM);
			rs = ps.executeQuery();
			while (rs.next()) {
				InfOrderRelationVo vo = new InfOrderRelationVo();
				vo.setInfOrderRelationId(rs.getString("inf_order_relation_id"));
				vo.setUserId(rs.getString("user_id"));
				vo.setUserIdType(rs.getString("user_id_type"));
				vo.setProductId(rs.getString("product_id"));
				vo.setPackageId(rs.getString("package_id"));
				vo.setOpType(rs.getString("op_type"));
				vo.setState(rs.getString("state"));
				vo.setStateDate(rs.getString("state_date"));
				vo.setCreateDate(rs.getString("create_date"));
				vo.setSendTimes(rs.getString("send_times"));
				infOrderRelationList.add(vo);
			}
		} catch (Exception e) {
			log.info("getInfOrderRelation ��ȡ����ʧ��!", e);
			e.printStackTrace();
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(ps, this);

		}
		return infOrderRelationList;
	}
	
	/**
	 * ������״̬�ĳ�D������
	 * @param list
	 * @return
	 */
	public void updateInfOrderRelation(InfOrderRelationVo vo) {
		Connection con = null;
		PreparedStatement ps = null;
		String updateSql = "update inf_order_relation set state = 'D',state_date="+ DatabaseFunction.current()+ "where inf_order_relation_id = ?";	
		
		try {
			con = LegacyDAOUtil.getConnection();
			ps = con.prepareStatement(updateSql);
			ps.setString(1, vo.getInfOrderRelationId());
			ps.executeUpdate();
			//con.commit();
		} catch (Exception e) {
		/*	try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}*/
			log.info("updateInfOrderRelation ����ʧ��!", e);
			e.printStackTrace();
		} finally {
			DAOUtils.closeStatement(ps, this);

		}
	}
	
	/**
	 * ��״̬�ĳ�F,���ʹ�����1
	 * @param list
	 * @return
	 */
	public void updateInfOrderRelationWhenFail(InfOrderRelationVo vo) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "update inf_order_relation set state = 'F',send_times = ?,state_date="+DatabaseFunction.current()+" ,result_msg=? where inf_order_relation_id = ?";
		try {
			con = LegacyDAOUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getSendTimes());
			ps.setString(2, vo.getResultMsg());
			ps.setString(3, vo.getInfOrderRelationId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
		/*	try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}*/
			log.info("updateInfOrderRelation ����ʧ��!", e);
			e.printStackTrace();
		} finally {
			DAOUtils.closeStatement(ps, this);

		}
	}
	
	/**
	 * ��������ɾ�� InfOrderRelationVo
	 * @param infOrderRelationId
	 */
	public void delInfOrderRelation(String infOrderRelationId) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from inf_order_relation where inf_order_relation_id = ?";
		try {
			con = LegacyDAOUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, infOrderRelationId);
			ps.executeUpdate();
			//con.commit();
		} catch (Exception e) {
		/*	try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}*/
			log.info("delInfOrderRelation ɾ��ʧ��!", e);
			e.printStackTrace();
		} finally {
			DAOUtils.closeStatement(ps, this);

		}
	}
	
	/**
	 * ���ݲ�ƷIDȥ��ȡ��Ʒ�����
	 * @param productId
	 * @return
	 */
	public String getProductNbr(String productId) {
		String productNbr = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select product_nbr from product where product_code = ?";
		try {
			con = LegacyDAOUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, productId);
			rs = ps.executeQuery();
			while (rs.next()) productNbr = rs.getString("product_nbr");
		} catch (Exception e) {
			log.info("getProductNbr ��ȡ��Ʒ�����ʧ��!", e);
			e.printStackTrace();
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(ps, this);

		}
		return productNbr;
	}
	
	/**
	 * ����crm_ismp_code����crm��Ҫ����ͬ��������ϵ��ismp_code���ص�set
	 * @return
	 */
	public Map cacheIsmpCode(){
		//���������棬�ж��Ƿ�Ҫ��crm������ϵ����ͬ��
		String SQL_SELECT_CACHE="select cm.crm_code,cm.ismp_code from crm_ismp_code_map cm";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map ret=new HashMap();
		try{
			con = LegacyDAOUtil.getConnection();
			ps = con.prepareStatement(SQL_SELECT_CACHE);
			rs=ps.executeQuery();
			while(rs.next()){
				ret.put(rs.getString("crm_code"),rs.getString("ismp_code"));
			}
		}catch (Exception e) {
			log.info("ISMP_CODE����ʧ��!", e);
			e.printStackTrace();
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(ps, this);

		}
		
		return ret;
	}
	
	/**
	 * ��ѯ���CRM_ISMP_CODE_MAP
	 * �жϸ���ֵ��Ʒ�Ƿ��ڿ����(��ѯ��ʱ����Ҫ�ҵ���ֵ��Ʒ������ȥƥ�����ֶ�ISMP_CODE)
	 * @param product_nbr
	 * @return
	 */
	public boolean isExistCRMMap(String productNbr) {
		boolean flag = false;
		String sql = "select * from crm_ismp_code_map where ismp_code = ?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = LegacyDAOUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, productNbr);
			rs = ps.executeQuery();
			while (rs.next()) flag = true;
		} catch (Exception e) {
			log.info("isExistCRMMap �ж�ʧ��!", e);
			e.printStackTrace();
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(ps, this);

		}
		return flag;
	}
	
	/**
	 * �� InfOrderRelationVo �鵵����ʷ�� inf_order_relation_his
	 * @param vo
	 */
	public void writeInfOrderRelationHis(InfOrderRelationVo vo) {
		String sql = "insert into inf_order_relation_his(inf_order_relation_id,user_id,user_id_type,product_id,package_id,op_type,state,state_date,create_date,send_times,PARTITION_ID) values (?,?,?,?,?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = LegacyDAOUtil.getConnection();
			ps = con.prepareStatement(sql);
			int i = 1;
			ps.setLong(i++, Long.parseLong(vo.getInfOrderRelationId()));
			ps.setString(i++, vo.getUserId());
			ps.setString(i++, vo.getUserIdType());
			ps.setString(i++, vo.getProductId());
			ps.setString(i++, vo.getPackageId());
			ps.setString(i++, vo.getOpType());
			ps.setString(i++, vo.getState());
			ps.setTimestamp(i++, DateUtil.getTimestamp(vo.getStateDate()));
			ps.setTimestamp(i++, DateUtil.getTimestamp(vo.getCreateDate()));
			ps.setString(i++, vo.getSendTimes());
			ps.setInt(i++, DAOUtils.getCurrentMonth());
			ps.executeUpdate();
			//con.commit();
		} catch (Exception e) {
			log.info("��д���inf_order_relation_hisʧ��!", e);
			e.printStackTrace();
		/*	try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}*/
		} finally {
			DAOUtils.closeStatement(ps, this);

		}
	}
	
	/**
	 * ת���û����� (������ 10003 תΪ 0)
	 * @param prodSpecCode
	 * @return
	 */
	public String getUserIdType(String prodSpecCode) {
		String userIdType = "";
		String msisdnValue = DcSystemParamManager.getParameter("DC_MSISDN");
		String phsValue = DcSystemParamManager.getParameter("DC_PHS");
		String pstnValue =  DcSystemParamManager.getParameter("DC_PSTN");
		
		String[] msisdns = msisdnValue.split(",");
		for (int i = 0; i < msisdns.length; i++) {
			if (msisdns[i].equals(prodSpecCode)) userIdType = "0"; break;
		}
		
		String[] phss = phsValue.split(",");
		for (int i = 0; i < phss.length; i++) {
			if (phss[i].equals(prodSpecCode)) userIdType = "1"; break;
		}
		
		String[] pstns = pstnValue.split(",");
		for (int i = 0; i < pstns.length; i++) {
			if (pstns[i].equals(prodSpecCode)) userIdType = "2"; break;
		}
		
		return userIdType;
	}
}




