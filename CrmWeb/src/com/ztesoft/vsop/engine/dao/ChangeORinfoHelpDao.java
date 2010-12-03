package com.ztesoft.vsop.engine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.engine.vo.InfOrderRelationVO;

public class ChangeORinfoHelpDao {
	protected static Logger logger = Logger.getLogger(ProdInstHelpDao.class);
	private String SQL_INSERT = "insert into inf_order_relation(inf_order_relation_id,user_id,user_id_type,product_id,package_id,op_type,state,state_date,create_date,send_times,PARTITION_ID) values (?,?,?,?,?,?,?,"+DatabaseFunction.current()+","+DatabaseFunction.current()+",?,?)";
	/**
	 * 新增订购关系实例增量同步队列
	 * @param infOrderRelationVo
	 */
	public void writeInfOrderRelation(InfOrderRelationVO infOrderRelationVo) {
		PreparedStatement ps = null;
		int i = 1;
		try {
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			SequenceManageDAOImpl sequenceManage = new SequenceManageDAOImpl();
			String id =sequenceManage.getNextSequence("SEQ_INF_ORDER_RELATION_ID");
			infOrderRelationVo.setInfOrderRelationId(id);
			ps = conn.prepareStatement(SQL_INSERT);
			ps.setLong(i++, Long.parseLong(id));
			ps.setString(i++, infOrderRelationVo.getUserId());
			ps.setString(i++, infOrderRelationVo.getUserIdType());
			ps.setString(i++, infOrderRelationVo.getProductId());
			ps.setString(i++, infOrderRelationVo.getPackageId());
			ps.setString(i++, infOrderRelationVo.getOpType());
			ps.setString(i++, infOrderRelationVo.getState());
			ps.setString(i++, infOrderRelationVo.getSendTimes());
			ps.setInt(i++, DAOUtils.getCurrentMonth());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("填写库表INF_ORDER_RELATION失败!", e);
			e.printStackTrace();
		} finally {
			DAOUtils.closeStatement(ps, this);
		}
	}

}
