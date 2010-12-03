package com.ztesoft.crm.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.order.vo.ProductOfferInfo;

public class ProdOffDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select prod_offer_id,fee_set_flag,prod_offer_sub_type,prod_offer_name,state_date,prod_offer_publisher,state,eff_date,exp_date,manage_grade,offer_nbr,offer_provider_id,brand_id,serv_brand_id,templet_id,default_time_period,offer_desc,pricing_desc,pname_cn,pname_en,pdes_cn,pdes_en,chargingpolicy_cn,chargingpolicy_id,sub_option,present_option,corp_only,scope,package_host,offer_code from PROD_OFFER where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from prod_offer where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PROD_OFFER (prod_offer_id, fee_set_flag, prod_offer_sub_type, prod_offer_name, state_date, prod_offer_publisher, state, eff_date, exp_date, manage_grade, offer_nbr, offer_provider_id, brand_id, serv_brand_id, templet_id, default_time_period, offer_desc, pricing_desc, pname_cn, pname_en, pdes_cn, pdes_en, chargingpolicy_cn, chargingpolicy_id, sub_option, present_option, corp_only, scope, package_host,offer_code) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PROD_OFFER set prod_offer_id=?, fee_set_flag=?, prod_offer_sub_type=?, prod_offer_name=?, state_date=?, prod_offer_publisher=?, state=?, eff_date=?, exp_date=?, manage_grade=?, offer_nbr=?, offer_provider_id=?, brand_id=?, serv_brand_id=?, templet_id=?, default_time_period=?, offer_desc=?, pricing_desc=?, pname_cn=?, pname_en=?, pdes_cn=?, pdes_en=?, chargingpolicy_cn=?, chargingpolicy_id=?, sub_option=?, present_option=?, corp_only=?, scope=?, package_host=?,offer_code=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PROD_OFFER where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PROD_OFFER where prod_offer_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PROD_OFFER set prod_offer_id=?, fee_set_flag=?, prod_offer_sub_type=?, prod_offer_name=?, state_date=?, prod_offer_publisher=?, state=?, eff_date=?, exp_date=?, manage_grade=?, offer_nbr=?, offer_provider_id=?, brand_id=?, serv_brand_id=?, templet_id=?, default_time_period=?, offer_desc=?, pricing_desc=?, pname_cn=?, pname_en=?, pdes_cn=?, pdes_en=?, chargingpolicy_cn=?, chargingpolicy_id=?, sub_option=?, present_option=?, corp_only=?, scope=?, package_host=?,offer_code=? where prod_offer_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select prod_offer_id,fee_set_flag,prod_offer_sub_type,prod_offer_name,state_date,prod_offer_publisher,state,eff_date,exp_date,manage_grade,offer_nbr,offer_provider_id,brand_id,serv_brand_id,templet_id,default_time_period,offer_desc,pricing_desc,pname_cn,pname_en,pdes_cn,pdes_en,chargingpolicy_cn,chargingpolicy_id,sub_option,present_option,corp_only,scope,package_host,offer_code from PROD_OFFER where prod_offer_id=? ";
	
	
	private String SQL_SELECT_PROD_ACCNBR = " select o.prod_offer_name,o.prod_offer_id ,1 product_names,2 product_ids from PRODUCT_CATALOG_ITEM_ELEMENT a right join prod_offer o on a.element_id = o.prod_offer_id where 1=1 ";
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;

	private String GET_KEY = "select o.prod_offer_id from prod_offer o where  o.offer_nbr=?" ;
	
	//ISMP往VSOP平台同步增值产品时同时往表INF_PROD_TO_OCS新增相关记录 2010-10-11 qin.guoquan
	private String SQL_INSERT_INF_PROD_TO_OCS = "insert into inf_prod_to_ocs (inf_prod_to_ocs_id, prod_msg, prod_sub_type, prod_code, op_flag, prod_system, create_date, state, state_date, send_times, result_msg) values (?,?,?,?,?,?,?,?,?,?,?)";

	public String getProdAccnbr(){
		return SQL_SELECT_PROD_ACCNBR;
	}
	public String getKey(String systemCode , String offerCode) throws Exception{
		List whereCondParams = new ArrayList();
		whereCondParams.add(offerCode) ;
		
		List arrayList = findBySql( GET_KEY, whereCondParams , Const.UN_JUDGE_ERROR, Const.UN_JUDGE_ERROR, "");
		if (arrayList != null && arrayList.size()>0){
			Map m = (Map)arrayList.get(0);
			return Const.getStrValue(m, "prod_offer_id") ;
		}else
			return "";
	}
	public ProdOffDAO() {

	}
	

	/**
	 * Insert参数设置
	 * @param map
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateInsertParams(Map map) throws FrameException{
		if(map == null || map.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
				
		
		
		params.add(map.get("prod_offer_id")) ;
									
		params.add(map.get("fee_set_flag")) ;
									
		params.add(map.get("prod_offer_sub_type")) ;
									
		params.add(map.get("prod_offer_name")) ;
						
		//params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
		params.add(DAOUtils.getCurrentTimestamp());		
		
		params.add(map.get("prod_offer_publisher")) ;
									
		params.add(map.get("state")) ;
						
		//暂把生效时间和实效时间都设置为系统当前时间，尚不知逻辑如何	
		//params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
		params.add(DAOUtils.parseDateTime(map.get("eff_date" )));				
		
		//params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
		params.add(DAOUtils.parseDateTime(map.get("exp_date" )));	
									
		params.add(map.get("manage_grade")) ;
									
		params.add(map.get("offer_nbr")) ;
									
		params.add(map.get("offer_provider_id")) ;
									
		params.add(map.get("brand_id")) ;
									
		params.add(map.get("serv_brand_id")) ;
									
		params.add(map.get("templet_id")) ;
									
		params.add(map.get("default_time_period")) ;
									
		params.add(map.get("offer_desc")) ;
									
		params.add(map.get("pricing_desc")) ;
									
		params.add(map.get("pname_cn")) ;
									
		params.add(map.get("pname_en")) ;
									
		params.add(map.get("pdes_cn")) ;
									
		params.add(map.get("pdes_en")) ;
									
		params.add(map.get("chargingpolicy_cn")) ;
									
		params.add(map.get("chargingpolicy_id")) ;
									
		params.add(map.get("sub_option")) ;
									
		params.add(map.get("present_option")) ;
									
		params.add(map.get("corp_only")) ;
									
		params.add(map.get("scope")) ;
									
		params.add(map.get("package_host")) ;
		
		//销售品本系统内部编码和外部编码一样
		params.add(map.get("offer_nbr"));
						
		return params ;
	}
	

	/**
	 * update 参数设置
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParams(Map vo , List condParas) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		
		List params = new ArrayList() ;
				
					
		params.add(vo.get("prod_offer_id")) ;
						
					
		params.add(vo.get("fee_set_flag")) ;
						
					
		params.add(vo.get("prod_offer_sub_type")) ;
						
					
		params.add(vo.get("prod_offer_name")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("prod_offer_publisher")) ;
						
					
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
					
		params.add(vo.get("manage_grade")) ;
						
					
		params.add(vo.get("offer_nbr")) ;
						
					
		params.add(vo.get("offer_provider_id")) ;
						
					
		params.add(vo.get("brand_id")) ;
						
					
		params.add(vo.get("serv_brand_id")) ;
						
					
		params.add(vo.get("templet_id")) ;
						
					
		params.add(vo.get("default_time_period")) ;
						
					
		params.add(vo.get("offer_desc")) ;
						
					
		params.add(vo.get("pricing_desc")) ;
						
					
		params.add(vo.get("pname_cn")) ;
						
					
		params.add(vo.get("pname_en")) ;
						
					
		params.add(vo.get("pdes_cn")) ;
						
					
		params.add(vo.get("pdes_en")) ;
						
					
		params.add(vo.get("chargingpolicy_cn")) ;
						
					
		params.add(vo.get("chargingpolicy_id")) ;
						
					
		params.add(vo.get("sub_option")) ;
						
					
		params.add(vo.get("present_option")) ;
						
					
		params.add(vo.get("corp_only")) ;
						
					
		params.add(vo.get("scope")) ;
						
					
		params.add(vo.get("package_host")) ;
		
		//内部编码目前和序列主键一样
		params.add(vo.get("prod_offer_id"));
		
						
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add(condParas.get(i));
			}
		}
		return params ;
		
	}
	
		/**	
	 * 根据主键更新参数设置
	 * @param vo
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParamsByKey(Map vo , Map keyCondMap) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(vo.get("prod_offer_id")) ;
									
		params.add(vo.get("fee_set_flag")) ;
									
		params.add(vo.get("prod_offer_sub_type")) ;
									
		params.add(vo.get("prod_offer_name")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("prod_offer_publisher")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
									
		params.add(vo.get("manage_grade")) ;
									
		params.add(vo.get("offer_nbr")) ;
									
		params.add(vo.get("offer_provider_id")) ;
									
		params.add(vo.get("brand_id")) ;
									
		params.add(vo.get("serv_brand_id")) ;
									
		params.add(vo.get("templet_id")) ;
									
		params.add(vo.get("default_time_period")) ;
									
		params.add(vo.get("offer_desc")) ;
									
		params.add(vo.get("pricing_desc")) ;
									
		params.add(vo.get("pname_cn")) ;
									
		params.add(vo.get("pname_en")) ;
									
		params.add(vo.get("pdes_cn")) ;
									
		params.add(vo.get("pdes_en")) ;
									
		params.add(vo.get("chargingpolicy_cn")) ;
									
		params.add(vo.get("chargingpolicy_id")) ;
									
		params.add(vo.get("sub_option")) ;
									
		params.add(vo.get("present_option")) ;
									
		params.add(vo.get("corp_only")) ;
									
		params.add(vo.get("scope")) ;
									
		params.add(vo.get("package_host")) ;
						
		//内部编码目前和序列主键一样		
		//params.add(vo.get("prod_offer_id")) ;
		params.add(vo.get("offer_nbr"));
		
		params.add(keyCondMap.get("prod_offer_id"));
		return params ;
	}
		
		/**
	 * 主键条件参数设置
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */	
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(keyCondMap.get("prod_offer_id")) ;
						
		return params  ;
	}
	
	
	public String getDbName(){
		return this.dbName ;
	}
	
	public String getDeleteSQLByKey() throws FrameException {
					
		return this.SQL_DELETE_KEY ;
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
					
		return this.SQL_UPDATE_KEY ;
				
	}
	
	public String getSelectSQL(){
		return this.SQL_SELECT ;
	}
	
	public String getSelectCountSQL(){
		return this.SQL_SELECT_COUNT ;
	}
	
	public String getInsertSQL(){
		return this.SQL_INSERT ;
	}
	
	public String getUpdateSQL(){
		return this.SQL_UPDATE ;
	}
	
	public String getDeleteSQL(){
		return this.SQL_DELETE ;
	}
	
	public String getSQLSQLByKey() throws FrameException {
		return this.SQL_SELECT_KEY ;
	}
	
	public String getSqlForProdOff(String prodOffId) {		
		String sql = "select p.prod_offer_id,p.offer_nbr,p.prod_offer_name,p.fee_set_flag,p.prod_offer_sub_type, p.offer_desc " +
			"from prod_offer p where p.prod_offer_id <> " + prodOffId + " and p.prod_offer_id not in " +
			"(select t.offer_z_id from prod_offer_rel t where t.offer_a_id = "+ prodOffId +")";
		return sql;
	}
	
	public String getSqlForProdOffCount(String prodOffId) {
		String sql_count = "select count(*) as col_counts from prod_offer p where p.prod_offer_id <> " 
			+ prodOffId + " and p.prod_offer_id not in " +
			"(select t.offer_z_id from prod_offer_rel t where t.offer_a_id = "+ prodOffId +")";
		return sql_count;
	}	
	
	/**
	 * 返回分页查询结果
	 * @param whereCond 查询条件
	 * @param para 条件参数集合
	 * @param pageIndex 开始数据索引
	 * @param pageSize 每页数据
	 * @return
	 * @throws FrameException
	 * 
	 */
	public PageModel searchByCond(String prodOffId, String whereCond,List para,int pageIndex, int pageSize) throws FrameException {
		String querySQL= getSqlForProdOff(prodOffId) + whereCond;
		String countSQL= getSqlForProdOffCount(prodOffId) + whereCond;

		DynamicDict dto = Base.queryPage(this.getDbName(), querySQL, countSQL, para, pageIndex, pageSize, 
				null,Const.ACTION_RESULT, 1, Const.UN_JUDGE_ERROR, "") ;
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}
	/**
	 * 根据prodOfferIds得到所有的销售品
	 * @param conn
	 * @param prodOfferId
	 * @return
	 * @throws SQLException
	 */
	public List getProdOffersByProdOfferId(String prodOfferIds) throws SQLException {
		String sql = "select prod_offer_name, PROD_OFFER_SUB_TYPE,prod_offer_id,eff_date,exp_date,offer_nbr from prod_offer where offer_nbr in("+prodOfferIds+") ";
		Connection conn =null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		List ret = new ArrayList();
		try {
			conn = LegacyDAOUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				ProductOfferInfo prodOffer = new ProductOfferInfo();
				prodOffer.setProductOfferType(rs.getString("PROD_OFFER_SUB_TYPE"));
				prodOffer.setEffDate(rs.getString("EFF_DATE"));
				prodOffer.setExpDate(rs.getString("EXP_DATE"));
				prodOffer.setProductOfferID(rs.getString("offer_nbr"));
				ret.add(prodOffer);
			}
			
		}catch (SQLException se) {
			Debug.print("",this);
			throw new DAOSystemException("SQLException while insert sql:\n"+sql, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(ps, this);
			LegacyDAOUtil.releaseConnection(conn);
		}
		return ret;
	}

	public PageModel getProdOfferByNbr(String whereCond, List para, Map params, int pageIndex, int pageSize)throws FrameException {
		StringBuffer querySQL = new StringBuffer("select ");
		querySQL.append("o.prod_offer_name, o.prod_offer_id, o.offer_nbr, 1 product_names, 2 product_ids ");
		querySQL.append("from prod_offer o ");
		
		StringBuffer countSQL = new StringBuffer("select count(*) ");
		countSQL.append("from prod_offer o ");
		
		boolean byAblitity = false;
		StringBuffer sbf = new StringBuffer();
		
		String orderId = "";
		if(Const.containStrValue(params, "orderId")) {
			orderId = Const.getStrValue(params, "orderId");
			if (orderId != null && !orderId.equals("")) {
				int intOrderId = Integer.parseInt(orderId);
				if (intOrderId < 0) {	// 销售品类型节点
					if (intOrderId == -1) {
						intOrderId = 0;
						sbf.append(" and o.PROD_OFFER_SUB_TYPE = ?");
						para.add(new Integer(intOrderId));
					} else if (intOrderId == -2) {
						intOrderId = 1;
						sbf.append(" and o.PROD_OFFER_SUB_TYPE = ?");
						para.add(new Integer(intOrderId));
					} else if (intOrderId == -3) {
						intOrderId = 2;
						sbf.append(" and o.PROD_OFFER_SUB_TYPE = ?");
						para.add(new Integer(intOrderId));
					} else if (intOrderId == -100) {	// 业务能力
						sbf.append(" and o.PROD_OFFER_SUB_TYPE = 0");	// 只获取单增值销售品
					}
				} else {	// 单销售品业务能力节点
					byAblitity = true;
					querySQL.append(", PRODUCT_SERVICE_ABILITY_REL s ");
					countSQL.append(", PRODUCT_SERVICE_ABILITY_REL s ");
				}
			}
		}
		
		querySQL.append("where 1=1").append(whereCond).append(sbf);
		countSQL.append("where 1=1").append(whereCond).append(sbf);
		
		if (byAblitity) {
			querySQL.append(" and o.prod_offer_id = s.product_id and s.service_code = ? ");
			countSQL.append(" and o.prod_offer_id = s.product_id and s.service_code = ? ");
			para.add(orderId);
		}
		
		DynamicDict dto = Base.queryPage(getDbName(), querySQL.toString(),
				countSQL.toString(), para, pageIndex, pageSize, null, null, 1,
				Const.UN_JUDGE_ERROR, "");

		PageModel pages = DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT));
		
		// 获取销售品成员
		Connection conn =null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		List list = pages.getList();
		String sql = "select p.product_id,p.product_name,p.product_nbr from PROD_OFFER_DETAIL_ROLE po,product p ,ROLE_PROD_RELA b where b.product_id = p.product_id and po.prod_offer_role_cd = b.prod_offer_role_cd and po.prod_offer_id = ? ";
		try {
			conn = LegacyDAOUtil.getConnection();
			for (int i=0; i<list.size(); i++) {
				Map map = (Map) list.get(i);
				String prodOfferId = Const.getStrValue(map , "prod_offer_id");
				if(null != prodOfferId && !"".equals(prodOfferId)){
					ps = conn.prepareStatement(sql);
					ps.setString(1, prodOfferId);
					rs = ps.executeQuery();
					StringBuffer names = new StringBuffer("");
					StringBuffer ids = new StringBuffer("");
					while(rs.next()){
						names.append(rs.getString("PRODUCT_NAME")).append(",");
						ids.append(rs.getString("PRODUCT_ID")).append(",");
					}
					if (names.length() != 0) {
						names.setLength(names.length() - 1);
					}
					if (ids.length() != 0) {
						ids.setLength(ids.length() - 1);
					}
					map.put("product_names",names.toString());
					map.put("product_ids",ids.toString());
				}
			}
		}
		catch (SQLException se) {
			Debug.print("",this);
			throw new DAOSystemException("SQLException while select sql:\n"+SQL_SELECT_PROD_ACCNBR, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(ps, this);
			LegacyDAOUtil.releaseConnection(conn);
		}
		
		return pages;
	}
	
	public void cancelProdOfferById(String prodOfferId) {
		String updateStr = "update PROD_OFFER set STATE=4, STATE_DATE=sysdate where PROD_OFFER_ID=" + prodOfferId;
		try {
			updateBySQL(updateStr, new HashMap());
		} catch (FrameException e) {
			e.printStackTrace();
		}
	}
	
	/**，
	 * 
	 * @param SQL update语言
	 * @param vo参数表，必须用LinkedHashMap实现，这样才能保持里面的参数顺序不变
	 * @return
	 * @throws FrameException
	 */
	public boolean updateBySQL(String SQL ,Map vo) throws FrameException {
  		List updateParams = new ArrayList();
  		Iterator it = vo.entrySet().iterator();
  		while (it.hasNext())
  			{
  				Map.Entry pairs = (Map.Entry)it.next();
  				updateParams.add(pairs.getValue());
  			}
  		return Base.update(this.getDbName(), SQL, updateParams, 1, Const.UN_JUDGE_ERROR, "") > 0 ;
	}
	
	/**
	 * 处理插入 inf_prod_to_ocs 的sql语句  2010-10-11 qin.guoquan
	 * @param vo
	 * @return
	 * @throws FrameException
	 */
	public boolean insertInfProdToOCS(Map vo) throws FrameException {
		Map t  = vo ;
		List insertParams = translateInsertParamsForOCS(vo) ;
		String SQL = getSQLForInsertInfProdToOCS() ;
		return Base.update(this.getDbName(), SQL, insertParams,  1, Const.UN_JUDGE_ERROR, "") > 0 ;
	}	
	
	/**
	 * 处理插入 inf_prod_to_ocs 的sql语句 2010-10-11 qin.guoquan
	 * @param map
	 * @return
	 * @throws FrameException
	 */
	public List translateInsertParamsForOCS(Map map) throws FrameException {
		
		if (map == null || map.isEmpty()) return null;
		
		List params = new ArrayList();
		params.add(map.get("inf_prod_to_ocs_id"));
		params.add(map.get("prod_msg"));
		params.add(map.get("prod_sub_type"));
		params.add(map.get("prod_code"));
		params.add(map.get("op_flag"));
		params.add(map.get("prod_system"));
		params.add(DAOUtils.parseDateTime(map.get("create_date")));
		params.add(map.get("state"));
		params.add(DAOUtils.parseDateTime(map.get("state_date")));
		params.add(map.get("send_times"));
		params.add(map.get("result_msg"));

		return params;
	}	
	
	/**
	 * 2010-10-11 qin.guoquan
	 * @return
	 */
	public String getSQLForInsertInfProdToOCS() {
		return SQL_INSERT_INF_PROD_TO_OCS;
	}
}
