package com.ztesoft.crm.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.LegacyDAOUtil;

public class ProdOffDetaRoleDAO extends AbstractDAO {

	// 查询SQL
	private String SQL_SELECT = "select prod_offer_role_cd,role_name,up_prod_offer_role_cd,prod_offer_id,role_down_num,role_up_num,state,create_date,state_date,rule_type,min_count,max_count,product_id,(select PRODUCT_NAME from PRODUCT p where p.PRODUCT_ID = a.product_id) product_name from PROD_OFFER_DETAIL_ROLE a where 1=1 ";

	// 统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from prod_offer_detail_role where 1=1 ";

	// insert SQl
	private String SQL_INSERT = "insert into PROD_OFFER_DETAIL_ROLE (prod_offer_role_cd, role_name, up_prod_offer_role_cd, prod_offer_id, role_down_num, role_up_num, state, create_date, state_date, rule_type, min_count, max_count, product_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// 普通update SQL
	private String SQL_UPDATE = "update PROD_OFFER_DETAIL_ROLE set prod_offer_role_cd=?, role_name=?, up_prod_offer_role_cd=?, prod_offer_id=?, role_down_num=?, role_up_num=?, state=?, create_date=?, state_date=?, rule_type=?, min_count=?, max_count=?, product_id=? where 1=1 ";

	// 普通delete SQL
	private String SQL_DELETE = "delete from PROD_OFFER_DETAIL_ROLE where 1=1 ";

	// 根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PROD_OFFER_DETAIL_ROLE where prod_offer_role_cd=?";

	// 根据主键update SQL
	private String SQL_UPDATE_KEY = "update PROD_OFFER_DETAIL_ROLE set prod_offer_role_cd=?, role_name=?, up_prod_offer_role_cd=?, prod_offer_id=?, role_down_num=?, role_up_num=?, state=?, create_date=?, state_date=?, rule_type=?, min_count=?, max_count=?, product_id=? where prod_offer_role_cd=?";

	// 根据主键查询SQL
	private String SQL_SELECT_KEY = "select prod_offer_role_cd,role_name,up_prod_offer_role_cd,prod_offer_id,role_down_num,role_up_num,state,create_date,state_date,rule_type,min_count,max_count,product_id, (select PRODUCT_NAME from PRODUCT p where p.PRODUCT_ID = a.product_id) product_name from PROD_OFFER_DETAIL_ROLE a where prod_offer_role_cd=? ";


	// 根据产品表里的product_id查询此产品对应的提供商信息:
	private String SQL_PARTNER_PRODUCT="select podr.prod_offer_role_cd,podr.product_id,p.product_code,p.product_name,pa.partner_code,pa.partner_name FROM partner pa inner join product p on p.product_provider_id = pa.partner_id inner join ((SELECT pr.product_id,pr.prod_offer_role_cd FROM prod_offer_detail_role pr WHERE pr.prod_offer_id = ? ) podr ) on podr.product_id = p.product_id ";
	// 当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE;

	private String BATCH_DELETE = "delete from PROD_OFFER_DETAIL_ROLE a where a.prod_offer_id=?";

	public String getBatchDeleteSQL() {
		return this.BATCH_DELETE;
	}

	/**
	 * 批量删除
	 * 
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public boolean batchDelete(String prod_offer_id) throws Exception {
		List para = new ArrayList();
		para.add(prod_offer_id);
		return Base.update(getDbName(), getBatchDeleteSQL(), para,
				Const.UN_JUDGE_ERROR, Const.UN_JUDGE_ERROR, null) > 1;
	}

	/**
	 * 批量新增
	 * 
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public int[] batchInsert(List para) throws Exception {
		return Base.batchUpdate(getDbName(), this.getInsertSQL(), para,
				Const.UN_JUDGE_ERROR, Const.UN_JUDGE_ERROR, null);
	}

	public ProdOffDetaRoleDAO() {

	}

	/**
	 * Insert参数设置
	 * 
	 * @param map
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateInsertParams(Map map) throws FrameException {
		if (map == null || map.isEmpty())
			return null;
		List params = new ArrayList();

		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		String prodOfferRoleId = seqDao
				.getNextSequence("SEQ_PROD_OFFER_ROLE_CD");
		params.add(prodOfferRoleId);
		// params.add(prodOfferRoleId);

		params.add(map.get("role_name"));

		params.add(map.get("up_prod_offer_role_cd"));

		params.add(map.get("prod_offer_id"));

		params.add(map.get("role_down_num"));

		params.add(map.get("role_up_num"));

		params.add(map.get("state"));

		// 暂把生效时间和实效时间都设置为系统当前时间，尚不知逻辑如何
		// params.add(DAOUtils.parseDateTime(map.get("create_date" ))) ;
		params.add(DAOUtils.getCurrentTimestamp());
		// params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
		params.add(DAOUtils.getCurrentTimestamp());

		params.add(map.get("rule_type"));

		params.add(map.get("min_count"));

		params.add(map.get("max_count"));

		params.add(map.get("product_id"));

		return params;
	}

	public String getSQL_PARTNER_PRODUCT() {
		
		return this.SQL_PARTNER_PRODUCT;
	}

	public PageModel searchCompileInfoByPrimaryKey(String whereCond, List para,
			int pageIndex, int pageSize) throws FrameException {
		StringBuffer querySQL = new StringBuffer("select ");
		querySQL.append("b.prod_offer_role_cd,b.product_id,a.prod_offer_id,c.product_name,c.product_nbr,d.partner_name,d.partner_code ");
		querySQL.append("from prod_offer_detail_role a,role_prod_rela b,product c,partner d ");
		querySQL.append("where 1=1 ");
		querySQL.append("and a.prod_offer_id = ? ");
		querySQL.append("and a.prod_offer_role_cd = b.prod_offer_role_cd ");
		querySQL.append("and b.product_id = c.product_id ");
		querySQL.append("and c.product_provider_id = d.partner_id ");
		
		StringBuffer countSQL = new StringBuffer("select count(*) ");
		countSQL.append("from prod_offer_detail_role a ");
		countSQL.append("where a.prod_offer_id = ? ");
		
		DynamicDict dto = Base.queryPage(this.getDbName(), querySQL.toString(), countSQL.toString(),
				para, pageIndex, pageSize, null, Const.ACTION_RESULT, 1,
				Const.UN_JUDGE_ERROR, "");	
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT));

	}
	
	/**
	 * 插入销售品产品关系表.
	 * 
	 * @param ProdOffDetaRole
	 *  如: {prod_offer_id=200000642, product_id=100001043, product_name=a)a4, prod_offer_role_cd=}
	 * @return
	 * @throws SQLException 
	 */
	public boolean insertProdOffRel(Map ProdOffDetaRole) throws SQLException {
		String seqSql = "select SEQ_PROD_OFFER_ROLE_CD.NEXTVAL from dual";
		String insertSql_roleProdRela = "insert into ROLE_PROD_RELA(PROD_OFFER_ROLE_CD, PRODUCT_ID) values(?,?)";
		String insertSql_prodOfferDetailRole = "insert into PROD_OFFER_DETAIL_ROLE(PROD_OFFER_ROLE_CD, PROD_OFFER_ID) values(?,?)";
		String selectSql_prodOfferDetailRole = "select PROD_OFFER_ROLE_CD from PROD_OFFER_DETAIL_ROLE where PROD_OFFER_ID=?";
		
		String prodOfferId = (String) ProdOffDetaRole.get("prod_offer_id");
		String productId = (String) ProdOffDetaRole.get("product_id");
		long seq = 0;
		
		ResultSet rsseq = null;
		ResultSet rsc = null;
		PreparedStatement pstmc = null;
		PreparedStatement pstmseq = null;
		PreparedStatement pstm1 = null;
		PreparedStatement pstm2 = null;
		Connection conn = null;
		
		try {
			conn = LegacyDAOUtil.getConnection();
			
			pstmc = conn.prepareStatement(selectSql_prodOfferDetailRole);
			pstmc.setString(1, prodOfferId);
			rsc = pstmc.executeQuery();
			if (rsc.next()) {
				seq = rsc.getLong(1);
			}
			
			if (seq == 0) {
				// 如果不存在该销售品则生成序列号
				pstmseq = conn.prepareStatement(seqSql);
				pstmseq.executeQuery();
				rsseq = pstmseq.executeQuery();
				if (rsseq.next()) {
					seq = rsseq.getLong(1);
				}
				
				// 插入记录到PROD_OFFER_DETAIL_ROLE
				pstm2 = conn.prepareStatement(insertSql_prodOfferDetailRole);
				pstm2.setLong(1, seq);
				pstm2.setString(2, prodOfferId);
				pstm2.executeUpdate();
			}
			
			// 再插入记录到ROLE_PROD_RELA;
			if (productId != null && !productId.equals("")) {
				pstm1 = conn.prepareStatement(insertSql_roleProdRela);
				pstm1.setLong(1, seq);
				pstm1.setString(2, productId);
				pstm1.executeUpdate();
			}
		} catch (SQLException e) {
			throw e;
		} finally{
			DAOUtils.closeResultSet(rsc);
			DAOUtils.closeResultSet(rsseq);
			DAOUtils.closeStatement(pstmc);
			DAOUtils.closeStatement(pstmseq);
			DAOUtils.closeStatement(pstm1);
			DAOUtils.closeStatement(pstm2);
			LegacyDAOUtil.commitAndCloseConnection(conn);
		}
		
		return true;
	}
	
	/**
	 * 根据销售品ID删除销售品产品关系数据.
	 * 
	 * @param prodOffDetaRole
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteProdOffDetaRoleById(Map prodOffDetaRole) throws SQLException {
		String selectSql_roleProdRela = "select a.PROD_OFFER_ROLE_CD from prod_offer_detail_role a where a.prod_offer_id=?";
		String deleteSql_roleProdRela = "delete from ROLE_PROD_RELA where PROD_OFFER_ROLE_CD=?";
		
		String prodOfferId = (String) prodOffDetaRole.get("prod_offer_id");
		
		ResultSet rs = null;
		PreparedStatement pstm0 = null;
		PreparedStatement pstm1 = null;
		Connection conn = null;
		
		try {
			conn = LegacyDAOUtil.getConnection();
			
			long cd = 0;
			pstm0 = conn.prepareStatement(selectSql_roleProdRela);
			pstm0.setString(1, prodOfferId);
			rs = pstm0.executeQuery();
			if (rs.next()) {
				cd = rs.getLong(1);
			}
			
			if (cd != 0) {
				pstm1 = conn.prepareStatement(deleteSql_roleProdRela);
				pstm1.setLong(1, cd);
				pstm1.executeUpdate();
			}
		} catch (SQLException e) {
			throw e;
		} finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(pstm0);
			DAOUtils.closeStatement(pstm1);
			LegacyDAOUtil.commitAndCloseConnection(conn);
		}
		
		return true;
	}
	
	/**
	 * 删除销售品产品关系数据.
	 * 
	 * @param keyCondMap
	 * @return
	 */
	public boolean deleteProdOffDetaRole(Map keyCondMap) throws SQLException {
		String prodOfferRoleCd = (String) keyCondMap.get("prod_offer_role_cd");
		String productId = (String) keyCondMap.get("product_id");
		
		String deleteSql_roleProdRela = "delete from ROLE_PROD_RELA where PROD_OFFER_ROLE_CD=? and PRODUCT_ID=? ";
		
		PreparedStatement pstm1 = null;
		Connection conn = null;
		
		try {
			conn = LegacyDAOUtil.getConnection();
			pstm1 = conn.prepareStatement(deleteSql_roleProdRela);
			pstm1.setString(1, prodOfferRoleCd);
			pstm1.setString(2, productId);
			pstm1.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally{
			DAOUtils.closeStatement(pstm1);
			LegacyDAOUtil.commitAndCloseConnection(conn);
		}
		
		return true;
	}

	/**
	 * update 参数设置
	 * 
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParams(Map vo, List condParas)
			throws FrameException {
		if (vo == null || vo.isEmpty())
			return null;

		List params = new ArrayList();

		params.add(vo.get("prod_offer_role_cd"));

		params.add(vo.get("role_name"));

		params.add(vo.get("up_prod_offer_role_cd"));

		params.add(vo.get("prod_offer_id"));

		params.add(vo.get("role_down_num"));

		params.add(vo.get("role_up_num"));

		params.add(vo.get("state"));

		params.add(DAOUtils.parseDateTime(vo.get("create_date")));

		params.add(DAOUtils.parseDateTime(vo.get("state_date")));

		params.add(vo.get("rule_type"));

		params.add(vo.get("min_count"));

		params.add(vo.get("max_count"));

		params.add(vo.get("product_id"));

		if (condParas != null && !condParas.isEmpty()) {
			for (int i = 0, j = condParas.size(); i < j; i++) {
				params.add(condParas.get(i));
			}
		}
		return params;

	}

	/**
	 * 根据主键更新参数设置
	 * 
	 * @param vo
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParamsByKey(Map vo, Map keyCondMap)
			throws FrameException {
		if (vo == null || vo.isEmpty())
			return null;
		if (keyCondMap == null || keyCondMap.isEmpty())
			return null;

		List params = new ArrayList();

		params.add(vo.get("prod_offer_role_cd"));

		params.add(vo.get("role_name"));

		params.add(vo.get("up_prod_offer_role_cd"));

		params.add(vo.get("prod_offer_id"));

		params.add(vo.get("role_down_num"));

		params.add(vo.get("role_up_num"));

		params.add(vo.get("state"));

		params.add(DAOUtils.parseDateTime(vo.get("create_date")));

		params.add(DAOUtils.parseDateTime(vo.get("state_date")));

		params.add(vo.get("rule_type"));

		params.add(vo.get("min_count"));

		params.add(vo.get("max_count"));

		params.add(vo.get("product_id"));

		params.add(keyCondMap.get("prod_offer_role_cd"));

		return params;
	}

	/**
	 * 主键条件参数设置
	 * 
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateKeyCondMap(Map keyCondMap) throws FrameException {
		if (keyCondMap == null || keyCondMap.isEmpty())
			return null;

		List params = new ArrayList();

		params.add(keyCondMap.get("prod_offer_role_cd"));

		return params;
	}

	public String getDbName() {
		return this.dbName;
	}

	public String getDeleteSQLByKey() throws FrameException {

		return this.SQL_DELETE_KEY;

	}

	public String getUpdateSQLByKey() throws FrameException {

		return this.SQL_UPDATE_KEY;

	}

	public String getSelectSQL() {
		return this.SQL_SELECT;
	}

	public String getSelectCountSQL() {
		return this.SQL_SELECT_COUNT;
	}

	public String getInsertSQL() {
		return this.SQL_INSERT;
	}

	public String getUpdateSQL() {
		return this.SQL_UPDATE;
	}

	public String getDeleteSQL() {
		return this.SQL_DELETE;
	}

	public String getSQLSQLByKey() throws FrameException {

		return this.SQL_SELECT_KEY;

	}

	/**
	 * ISMP同步给VSOP时专用
	 */
	public List translateInsertParamsForIsmp(Map map) throws FrameException {
		if (map == null || map.isEmpty())
			return null;
		List params = new ArrayList();
		params.add(map.get("prod_offer_role_cd"));
		params.add(map.get("role_name"));
		params.add(map.get("up_prod_offer_role_cd"));
		params.add(map.get("prod_offer_id"));
		params.add(map.get("role_down_num"));
		params.add(map.get("role_up_num"));
		params.add(map.get("state"));
		// 暂把生效时间和实效时间都设置为系统当前时间，尚不知逻辑如何
		// params.add(DAOUtils.parseDateTime(map.get("create_date" ))) ;
		params.add(DAOUtils.getCurrentTimestamp());
		// params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
		params.add(DAOUtils.getCurrentTimestamp());
		params.add(map.get("rule_type"));
		params.add(map.get("min_count"));
		params.add(map.get("max_count"));
		params.add(map.get("product_id"));
		return params;
	}
	
	/**
	 * ISMP同步给VSOP时专用，因为序列问题，详见PPMProductSysManager的insertProduct()方法。
	 * @param vo
	 * @return
	 * @throws FrameException
	 */
	public boolean insertForIsmp(Map vo) throws FrameException {
		List insertParams = translateInsertParamsForIsmp(vo) ;
		String SQL = getInsertSQL() ;
		return Base.update(this.getDbName(), SQL, insertParams,  1, Const.UN_JUDGE_ERROR, "") > 0 ;
	}
}
