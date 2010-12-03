package com.ztesoft.vsop.product.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.product.dao.ProductCatgItemDAO;

public class ProductDAO extends AbstractDAO {

	// 查询SQL
	private String SQL_SELECT = "select product_id,product_nbr,product_code,product_name,product_desc,manage_grade,prod_bundle_type,product_provider_id,(select t.partner_name from partner t where t.partner_id=product_provider_id) product_provider_name,product_state_cd,state_date,eff_date,exp_date,create_date,prod_func_type,order_host,charging_policy_cn,charging_policy_id,sub_option,present,corp_only,package_only,settlement_cycle,settlement_paytype,settlement_percent,scope,system_code from PRODUCT p where 1=1 ";
	
	//查询传统产品SQL
	private String SQL_SELECT_TRA="select product_id,product_name,prod_func_type,product_desc,product_code,product_state_cd from PRODUCT where 1=1 ";
	
	// 统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from product p where 1=1 ";

	// insert SQl
	private String SQL_INSERT = "insert into PRODUCT (product_id, product_nbr, product_name, product_desc, manage_grade, prod_bundle_type, product_provider_id, product_state_cd, state_date, eff_date, exp_date, create_date, prod_func_type, order_host, charging_policy_cn, charging_policy_id, sub_option, present, corp_only, package_only, settlement_cycle, settlement_paytype, settlement_percent, scope, system_code, product_code) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	//新增传统产品SQL
	private String SQL_INSERT_TRA="insert into PRODUCT(product_id,product_name,prod_func_type,product_desc,product_state_cd,product_provider_id,create_date,state_date,product_code) values (?,?,?,?,?,?,?,?,?) ";
	// 普通update SQL
	private String SQL_UPDATE = "update PRODUCT set product_id=?, product_nbr=?, product_name=?, product_desc=?, manage_grade=?, prod_bundle_type=?, product_provider_id=?, product_state_cd=?, state_date=?, eff_date=?, exp_date=?, create_date=?, prod_func_type=?, order_host=?, charging_policy_cn=?, charging_policy_id=?, sub_option=?, present=?, corp_only=?, package_only=?, settlement_cycle=?, settlement_paytype=?, settlement_percent=?, scope=?, system_code=?,product_code=? where 1=1 ";

	// 普通delete SQL
	private String SQL_DELETE = "delete from PRODUCT where 1=1 ";

	// 根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PRODUCT where product_id=?";

	// 根据主键update SQL
	private String SQL_UPDATE_KEY = "update PRODUCT set product_id=?, product_nbr=?, product_name=?, product_desc=?, manage_grade=?, prod_bundle_type=?, product_provider_id=?, product_state_cd=?, state_date=?, eff_date=?, exp_date=?, create_date=?, prod_func_type=?, order_host=?, charging_policy_cn=?, charging_policy_id=?, sub_option=?, present=?, corp_only=?, package_only=?, settlement_cycle=?, settlement_paytype=?, settlement_percent=?, scope=?, system_code=?, product_code=? where product_id=?";

	//
	private String SQL_UPDATE_KEY_TRA="update PRODUCT set product_id=?, product_name=?, prod_func_type=?, product_desc=?, product_state_cd=?, product_provider_id=?, state_date=?, product_code=? where product_id=? ";
	// 根据主键查询SQL
	private String SQL_SELECT_KEY = "select product_id,product_nbr,product_code,product_name,product_desc,manage_grade,prod_bundle_type,product_provider_id,(select t.partner_name from partner t where t.partner_id=product_provider_id) product_provider_name,product_state_cd,state_date,eff_date,exp_date,create_date,prod_func_type,order_host,charging_policy_cn,charging_policy_id,sub_option,present,corp_only,package_only,settlement_cycle,settlement_paytype,settlement_percent,scope,system_code,product_code from PRODUCT where product_id=? ";

	// 根据主键查询传统产品SQL
	private String SQL_SELECT_KEY_TRA="select product_id,product_name,prod_func_type,product_desc,product_state_cd,product_code from PRODUCT where product_id=? ";
	// 找用户对应的业务能力
	//private String SQL_SELECT_KEY_SERVICE_NAME = "select service_name from SERVICE_ABILITY a,PRODUCT_SERVICE_ABILITY_REL b where a.SERVICE_CODE=b.SERVICE_CODE and b.PRODUCT_ID=? and REL_TYPE='1' ";

	private String SQL_SELECT_KEY_SERVICE_NAME = "select distinct(b.service_code) as service_code from BIZ_CAPABILITY_INST a,PRODUCT_SERVICE_ABILITY_REL b where a.product_id=b.product_id and a.prod_inst_id=? and b.rel_type=0 ";
	
	private String SQL_SELECT_KEY_PROINFO = "SELECT p.product_nbr,p.product_code,p.product_name,p.state_date,p.create_date FROM product p WHERE p.product_id = ? ";
	// 当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE;

	private String productTypeSQL = "select t.product_type_id,t.product_type_name,t.service_code from product_type t";

	private String existsRow = "select count(*) existRow from product t where t.product_nbr=? and t.system_code=? ";

	private String GET_KEY = "select p.product_id from product p where p.product_nbr=?";
	
	//模拟器删除产品时把产品状态改为注销状态
	private String SQL_CALCEL = "update product set PRODUCT_STATE_CD = ? where product_nbr = ?";
	
	//根据产品编码查找产品id
	private String SQL_GET_PRODUCT_ID = "select product_id from product where product_nbr = ?";
	
	//根据产品提供商Id查找产品
	private String SQL_SELECT_PROVIDER_ID="select product_id,product_code,product_name,prod_func_type,product_desc,product_state_cd from product where 1=1 ";

	//ISMP往VSOP平台同步增值产品时同时往表INF_PROD_TO_OCS新增相关记录 2010-10-11 qin.guoquan
	private String SQL_INSERT_INF_PROD_TO_OCS = "insert into inf_prod_to_ocs (inf_prod_to_ocs_id, prod_msg, prod_sub_type, prod_code, op_flag, prod_system, create_date, state, state_date, send_times, result_msg) values (?,?,?,?,?,?,?,?,?,?,?)";
	
	//根据产品提供商查找相关产品用于查询统计
	public PageModel searchProductByProviderId(String whereCond ,List para,int pageIndex, int pageSize) throws FrameException {
		String querySQL= this.SQL_SELECT_PROVIDER_ID +  whereCond;
		String countSQL= getSelectCountSQL() +  whereCond  ;
		DynamicDict dto = Base.queryPage(this.getDbName(), querySQL, countSQL, para, pageIndex, pageSize, 
				null,Const.ACTION_RESULT, 1, Const.UN_JUDGE_ERROR, "") ;
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}
	
	public String getKey(String systemCode, String productCode)
			throws Exception {
		List whereCondParams = new ArrayList();
		whereCondParams.add(productCode);

		List arrayList = findBySql(GET_KEY, whereCondParams,
				Const.UN_JUDGE_ERROR, Const.UN_JUDGE_ERROR, "");
		if (arrayList != null && arrayList.size() > 0) {
			Map m = (Map) arrayList.get(0);
			return Const.getStrValue(m, "product_id");
		} else
			return "";
	}

	/**
	 * 检测是否存在一样的行
	 * 
	 * @param product_nbr
	 * @param system_code
	 * @param product_id
	 * @param product_code
	 * @return
	 * @throws Exception
	 */
	public boolean checkExistsRow(String product_nbr, String system_code, String product_id) throws Exception {
		if (product_id != null && !"".equals(product_id)) {
			existsRow += " and product_id<>?";
			String[] para = { product_nbr, system_code, product_id };
			return Base
					.query_int(dbName, Const.UN_JUDGE_ERROR, existsRow, para) > 0;
		} else {
			String[] para = { product_nbr, system_code };
			return Base
					.query_int(dbName, Const.UN_JUDGE_ERROR, existsRow, para) > 0;
		}
	}

	public ProductDAO() {

	}

	/**
	 * 检测是否是更新自己id列的内容
	 * 
	 * @param product_nbr
	 * @param system_code
	 * @param product_id
	 * @param product_code
	 * @return
	 * @throws Exception
	 */
	public boolean checkUpdateSelf(String product_nbr, String system_code,
			String product_id, String product_code) throws Exception {
		if (product_id != null && !"".equals(product_id)) {
			Map keyParam = new HashMap();
			keyParam.put("product_id", product_id);
			Map resultMap = this.findByPrimaryKey(keyParam);
			if ((product_nbr != null) && (system_code != null)
					&& product_code != null) {
				return product_nbr.equals(resultMap.get("product_nbr"))
						&& system_code.equals(resultMap.get("system_code"))
						&& product_code.equals(resultMap.get("product_code"));
			}
			return false;

		}
		return false;
	}

	/**
	 * 加载产品类型列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList loadProductType(String typeId, String orderId) throws Exception {
		List param = new ArrayList();
		StringBuffer sbf = new StringBuffer("and catalog_id=100001 ");
		if (typeId != null && !typeId.equals("")) {
			sbf.append("and p.parent_catalog_item_id=? ");
			param.add(typeId);
		}
		if (orderId != null && !orderId.equals("") && orderId.equals("-100")) {	// 获取在用的业务能力
			sbf.append("and p.order_id=s.service_code ");
			sbf.append("and s.is_using=0 ");
		}
		
		StringBuffer querySQL = new StringBuffer("select ");
		querySQL.append("p.catalog_item_id,p.parent_catalog_item_id,p.catalog_id,p.catalog_item_name,p.catalog_item_desc,p.catalog_item_type,");
		querySQL.append("p.ord_action_type,p.ord_no,p.cancel_ord_no,p.seq,p.state,p.party_id,p.party_role_id,p.oper_region_id,p.oper_date,p.order_id ");
		querySQL.append("from PRODUCT_CATALOG_ITEM p ");
		if (orderId != null && !orderId.equals("") && orderId.equals("-100")) {	// 获取在用的业务能力
			querySQL.append(", SERVICE_ABILITY s ");
		}
		querySQL.append("where 1=1 ").append(sbf);
		
		ProductCatgItemDAO dao = new ProductCatgItemDAO();
		List result = dao.findBySql(querySQL.toString(), param);
		return (ArrayList) result;
	}

	public List getAbilityById(String productId) throws Exception {
		Map resultMap = new HashMap();
		List whereParams = new ArrayList();
		whereParams.add(productId);
/*		List arrayList1 = findBySql(this.getSQLProdInfoKey(), whereParams,
				Const.UN_JUDGE_ERROR, Const.UN_JUDGE_ERROR, "");
		if (arrayList1 != null) {
			resultMap = (Map) arrayList1.get(0);
		}*/
		List arrayList2 = findBySql(this.SQL_SELECT_KEY_SERVICE_NAME,
				whereParams, Const.UN_JUDGE_ERROR, Const.UN_JUDGE_ERROR, "");

		return arrayList2;

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

		params.add(map.get("product_id"));

		params.add(map.get("product_nbr"));

		params.add(map.get("product_name"));

		params.add(map.get("product_desc"));

		params.add(map.get("manage_grade"));

		params.add(map.get("prod_bundle_type"));

		params.add(map.get("product_provider_id"));

		params.add(map.get("product_state_cd"));

		params.add(DAOUtils.parseDateTime(map.get("state_date")));

		params.add(DAOUtils.parseDateTime(map.get("eff_date")));

		params.add(DAOUtils.parseDateTime(map.get("exp_date")));

		params.add(DAOUtils.parseDateTime(map.get("create_date")));

		params.add(map.get("prod_func_type"));

		params.add(map.get("order_host"));

		params.add(map.get("charging_policy_cn"));

		params.add(map.get("charging_policy_id"));

		params.add(map.get("sub_option"));

		params.add(map.get("present"));

		params.add(map.get("corp_only"));

		params.add(map.get("package_only"));

		params.add(map.get("settlement_cycle"));

		params.add(map.get("settlement_paytype"));

		params.add(map.get("settlement_percent"));

		params.add(map.get("scope"));

		params.add(map.get("system_code"));
		
		// 目前内部编码跟产品编码一致
		params.add(map.get("product_code"));

		return params;
	}
	
	/**
	 * 处理插入 inf_prod_to_ocs 的sql语句  2010-10-11 qin.guoquan
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

		params.add(vo.get("product_id"));

		params.add(vo.get("product_nbr"));

		params.add(vo.get("product_name"));

		params.add(vo.get("product_desc"));

		params.add(vo.get("manage_grade"));

		params.add(vo.get("prod_bundle_type"));

		params.add(vo.get("product_provider_id"));

		params.add(vo.get("product_state_cd"));

		params.add(DAOUtils.parseDateTime(vo.get("state_date")));

		params.add(DAOUtils.parseDateTime(vo.get("eff_date")));

		params.add(DAOUtils.parseDateTime(vo.get("exp_date")));

		params.add(DAOUtils.parseDateTime(vo.get("create_date")));

		params.add(vo.get("prod_func_type"));

		params.add(vo.get("order_host"));

		params.add(vo.get("charging_policy_cn"));

		params.add(vo.get("charging_policy_id"));

		params.add(vo.get("sub_option"));

		params.add(vo.get("present"));

		params.add(vo.get("corp_only"));

		params.add(vo.get("package_only"));

		params.add(vo.get("settlement_cycle"));

		params.add(vo.get("settlement_paytype"));

		params.add(vo.get("settlement_percent"));

		params.add(vo.get("scope"));

		params.add(vo.get("system_code"));
		// 目前内部编码跟产品编码一致
		params.add(vo.get("product_code"));

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

		params.add(vo.get("product_id"));
		params.add(vo.get("product_nbr"));
		params.add(vo.get("product_name"));
		params.add(vo.get("product_desc"));
		params.add(vo.get("manage_grade"));
		params.add(vo.get("prod_bundle_type"));
		params.add(vo.get("product_provider_id"));
		params.add(vo.get("product_state_cd"));
		params.add(DAOUtils.parseDateTime(vo.get("state_date")));
		params.add(DAOUtils.parseDateTime(vo.get("eff_date")));
		params.add(DAOUtils.parseDateTime(vo.get("exp_date")));
		params.add(DAOUtils.parseDateTime(vo.get("create_date")));
		params.add(vo.get("prod_func_type"));
		params.add(vo.get("order_host"));
		params.add(vo.get("charging_policy_cn"));
		params.add(vo.get("charging_policy_id"));
		params.add(vo.get("sub_option"));
		params.add(vo.get("present"));
		params.add(vo.get("corp_only"));
		params.add(vo.get("package_only"));
		params.add(vo.get("settlement_cycle"));
		params.add(vo.get("settlement_paytype"));
		params.add(vo.get("settlement_percent"));
		params.add(vo.get("scope"));
		params.add(vo.get("system_code"));
		params.add(vo.get("product_code"));
		params.add(keyCondMap.get("product_id"));//对应where条件的参数位置

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

		params.add(keyCondMap.get("product_id"));

		return params;
	}

	public PageModel searchProductInsts(String where, List pv, int pageIndex,
			int pageSize) throws Exception {
		StringBuffer querySQL = new StringBuffer();
		querySQL.append("select * from (");
		querySQL
				.append("select a.ACC_NBR as ACC_NBR,a.USER_NAME as USER_NAME,a.UIM_NO as UIM_no,a.OLD_UIM_NO as OLD_UIM_NO, a.PROD_INST_ID as PROD_INST_ID, a.LAN_ID as LAN_ID, a.PAYMENT_MODE_CD, a.PRODUCT_ID as PRODUCT_ID, b.PRODUCT_NAME M_PRODUCT_NAME, a.STATE_CD, a.STATE_DATE, a.CREATE_DATE ");
		querySQL.append("from PROD_INST a, PRODUCT b ");
		querySQL.append("where a.PRODUCT_ID=b.PRODUCT_ID) c where 1=1 ");
		querySQL.append(where);

		StringBuffer countSQL = new StringBuffer();
		countSQL.append("select count(*) from (");
		countSQL
				.append("select a.ACC_NBR as ACC_NBR,a.USER_NAME as USER_NAME,a.UIM_NO as UIM_no,a.OLD_UIM_NO as OLD_UIM_NO, a.PROD_INST_ID as PROD_INST_ID, a.PRODUCT_ID as PRODUCT_ID, a.LAN_ID as LAN_ID, b.PRODUCT_NAME M_PRODUCT_NAME ");
		countSQL.append("from PROD_INST a, PRODUCT b ");
		countSQL.append("where a.PRODUCT_ID=b.PRODUCT_ID) c where 1=1 ");
		countSQL.append(where);

		DynamicDict dto = Base.queryPage(getDbName(), querySQL.toString(),
				countSQL.toString(), pv, pageIndex, pageSize, null, null, 1,
				Const.UN_JUDGE_ERROR, "");

		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT));
	}

	public PageModel searchProducts(String where, List pv, int pageIndex,
			int pageSize) throws Exception {
		StringBuffer querySQL = new StringBuffer();
		querySQL
				.append("select PRODUCT_ID,PRODUCT_NBR,PRODUCT_NAME,PRODUCT_DESC from PRODUCT where 1=1 ");
		querySQL.append(where);

		StringBuffer countSQL = new StringBuffer();
		countSQL.append("select count(*) from PRODUCT where 1=1 ");
		countSQL.append(where);

		DynamicDict dto = Base.queryPage(getDbName(), querySQL.toString(),
				countSQL.toString(), pv, pageIndex, pageSize, null, null, 1,
				Const.UN_JUDGE_ERROR, "");

		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT));
	}
	
	public PageModel searchProductsByAblitity(String where, List pv, Map params, int pageIndex,
			int pageSize) throws Exception {
		String orderId = Const.getStrValue(params, "service_ability_id");
		
		StringBuffer querySQL = new StringBuffer("select ");
		querySQL.append("p.product_id,p.product_nbr,p.product_code,p.product_name,p.product_desc,p.manage_grade,p.prod_bundle_type,p.product_provider_id,");
		querySQL.append("(select t.partner_name from partner t where t.partner_id = p.product_provider_id) product_provider_name,");
		querySQL.append("p.product_state_cd,p.state_date,p.eff_date,p.exp_date,p.create_date,p.prod_func_type,p.order_host,");
		querySQL.append("p.charging_policy_cn,p.charging_policy_id,p.sub_option,p.present,p.corp_only,p.package_only,p.settlement_cycle,");
		querySQL.append("p.settlement_paytype,p.settlement_percent,p.scope,p.system_code ");
		querySQL.append("from PRODUCT p ");
		
		StringBuffer countSQL = new StringBuffer("select count(*) from PRODUCT p ");
		
		if (!orderId.equals("")) {
			querySQL.append(",PRODUCT_SERVICE_ABILITY_REL s ");
			countSQL.append(",PRODUCT_SERVICE_ABILITY_REL s ");
		}
		
		querySQL.append("where 1=1 ").append(where);
		countSQL.append("where 1=1 ").append(where);
		
		if (!orderId.equals("")) {
			querySQL.append(" and p.product_id = s.product_id ");
			countSQL.append(" and p.product_id = s.product_id ");
		}
		
		if (!orderId.equals("") && !orderId.equals("-100")) {
			querySQL.append(" and s.service_code = ?");
			countSQL.append(" and s.service_code = ?");
			pv.add(orderId);
		}
		
		DynamicDict dto = Base.queryPage(getDbName(), querySQL.toString(),
				countSQL.toString(), pv, pageIndex, pageSize, null, null, 1,
				Const.UN_JUDGE_ERROR, "");
		
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}
	
	//对传统产品的管理Dao
	public PageModel searchProductTraByCond(String whereCond ,List para,int pageIndex, int pageSize) throws FrameException {
		String querySQL= getSQL_SELECT_TRA() +  whereCond;
		String countSQL= getSelectCountSQL() +  whereCond  ;

		DynamicDict dto = Base.queryPage(this.getDbName(), querySQL, countSQL, para, pageIndex, pageSize, 
				null,Const.ACTION_RESULT, 1, Const.UN_JUDGE_ERROR, "") ;
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}
	
  	public boolean updateProductTraByKey( Map vo , Map keyCondMap ) throws FrameException {
  		List updateParams = translateUpdateProductTraParamsByKey( vo ,  keyCondMap ) ;
  		String SQL = this.SQL_UPDATE_KEY_TRA ;
  		return Base.update(this.getDbName(), SQL, updateParams, 1, Const.UN_JUDGE_ERROR, "") > 0 ;
	}
	public boolean insertProductTra(Map vo) throws FrameException {
		Map t  = vo ;
		List insertParams = translateInsertProductTraParams(vo) ;
		String SQL = this.SQL_INSERT_TRA ;
		return Base.update(this.getDbName(), SQL, insertParams,  1, Const.UN_JUDGE_ERROR, "") > 0 ;
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
	
  	public HashMap findProductTraByPrimaryKey(Map keyCondMap  ) throws FrameException {
  		List whereCondParams = this.translateProductTraKeyCondMap(keyCondMap) ;
		List arrayList = findBySql( this.SQL_SELECT_KEY_TRA, whereCondParams , 1, Const.UN_JUDGE_ERROR, "");
		if (arrayList != null && arrayList.size()>0)
			return (HashMap)arrayList.get(0);
		else
			return getEmptyMap();
	}
  	
	public List translateInsertProductTraParams(Map map) throws FrameException {
		if (map == null || map.isEmpty())
			return null;
		List params = new ArrayList();
		params.add(map.get("product_id"));
		params.add(map.get("product_name"));
		params.add(map.get("prod_func_type"));
		params.add(map.get("product_desc"));
		params.add(map.get("product_state_cd"));
		params.add(map.get("product_provider_id"));
		params.add(DAOUtils.parseDateTime(map.get("create_date")));
		params.add(DAOUtils.parseDateTime(map.get("state_date")));
		//系统编码目前和产品编码一样
		params.add(map.get("product_nbr"));		
		return params;
	}
	
  	public List translateUpdateProductTraParamsByKey( Map vo , Map keyCondMap ) throws FrameException {
		if (vo == null || vo.isEmpty())
			return null;
		if (keyCondMap == null || keyCondMap.isEmpty())
			return null;

		List params = new ArrayList();
		
		params.add(vo.get("product_id"));
		params.add(vo.get("product_name"));
		params.add(vo.get("prod_func_type"));
		params.add(vo.get("product_desc"));
		params.add(vo.get("product_state_cd"));
		params.add(vo.get("product_provider_id"));
		params.add(DAOUtils.parseDateTime(vo.get("state_date")));
		
		//内部编码和产品编码一样
		params.add(keyCondMap.get("product_nbr"));
		params.add(keyCondMap.get("product_nbr"));			
  		return params;
  	}
  	
	public List translateProductTraKeyCondMap(Map keyCondMap) throws FrameException {
		if (keyCondMap == null || keyCondMap.isEmpty())
			return null;

		List params = new ArrayList();

		params.add(keyCondMap.get("product_id"));

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
	

	public String getSQL_SELECT_TRA() {
		return SQL_SELECT_TRA;
	}

	public String getSQLSQLByKey() throws FrameException {

		return this.SQL_SELECT_KEY;

	}

	public String getSQLProdInfoKey() {
		return this.SQL_SELECT_KEY_PROINFO;
	}

	public String getCalcelSql() {
		return SQL_CALCEL;
	}
	
	public String getProductIdByProductNbr() {
		return SQL_GET_PRODUCT_ID;
	}
	
	/**
	 * 2010-10-11 qin.guoquan
	 * @return
	 */
	public String getSQLForInsertInfProdToOCS() {
		return SQL_INSERT_INF_PROD_TO_OCS;
	}
}
