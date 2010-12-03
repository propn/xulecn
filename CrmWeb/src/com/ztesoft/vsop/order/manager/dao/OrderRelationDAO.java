package com.ztesoft.vsop.order.manager.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.DateUtil;

public class OrderRelationDAO extends AbstractDAO {
	private static Logger logger = Logger.getLogger(OrderRelationDAO.class);

	// 查询SQL
	private String SQL_SELECT = "select order_relation_id,prod_inst_id,product_id,prod_offer_inst_id,prod_offer_id,order_channel,state,eff_date,exp_date,package_id,lan_id from ORDER_RELATION where 1=1 ";

	// 统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from order_relation where 1=1 ";

	// insert SQl
	private String SQL_INSERT = "insert into ORDER_RELATION (order_relation_id, prod_inst_id, product_id, prod_offer_inst_id, prod_offer_id, order_channel, state, eff_date, exp_date, package_id, lan_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// 普通update SQL
	private String SQL_UPDATE = "update ORDER_RELATION set order_relation_id=?, prod_inst_id=?, product_id=?, prod_offer_inst_id=?, prod_offer_id=?, order_channel=?, state=?, eff_date=?, exp_date=?, package_id=?, lan_id=? where 1=1 ";

	// 普通delete SQL
	private String SQL_DELETE = "delete from ORDER_RELATION where 1=1 ";

	// 根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ORDER_RELATION where order_relation_id=?";

	// 根据主键update SQL
	private String SQL_UPDATE_KEY = "update ORDER_RELATION set order_relation_id=?, prod_inst_id=?, product_id=?, prod_offer_inst_id=?, prod_offer_id=?, order_channel=?, state=?, eff_date=?, exp_date=?, package_id=?, lan_id=? where order_relation_id=?";

	// 根据主键查询SQL
	private String SQL_SELECT_KEY = "select order_relation_id,prod_inst_id,product_id,prod_offer_inst_id,prod_offer_id,order_channel,state,eff_date,exp_date,package_id,lan_id from ORDER_RELATION where order_relation_id=? ";

	// 按sp/cp订购关系查询统计
	private String SQL_SELECT_STA = " select orr.acc_nbr,orr.eff_date,orr.exp_date,orr.state,(select prod_offer_name from prod_offer where prod_offer_id= decode(nvl(orr.PPROD_OFFER_ID,0), 0, decode(nvl(orr.PACKAGE_ID,0), 0, orr.PROD_OFFER_ID, orr.PACKAGE_ID), orr.PPROD_OFFER_ID)) prod_offer_name,(select p.product_name  from product p where p.product_id=orr.product_id) product_name,(select p.product_code from product p where p.product_id=orr.product_id)product_code,(select p.product_name from product p where p.product_id=orr.prod_type_cd) m_product_name from order_relation orr where 1=1 ";
	
	//按sp/cp全部产品订购关系查询统计
	private String SQL_SELECT_ALL_STA="select orr.acc_nbr,orr.eff_date,orr.exp_date,orr.state,(select prod_offer_name from prod_offer where prod_offer_id= decode(nvl(orr.PPROD_OFFER_ID,0), 0, decode(nvl(orr.PACKAGE_ID,0), 0, orr.PROD_OFFER_ID, orr.PACKAGE_ID), orr.PPROD_OFFER_ID)) prod_offer_name, p.product_name product_name,p.product_code,(select p.product_name from product p where p.product_id=orr.prod_type_cd) m_product_name from order_relation orr inner join product p on orr.product_id=p.product_id where 1=1 ";
	
	//按sp/cp全部产品订购关系查询统计总数
	private String SQL_SELECT_ALL_STA_COUNT="select count(*) as col_counts from order_relation orr inner join product p on orr.product_id=p.product_id where 1=1 ";
	//查询指定业务号码在当日是否有订购关系改变
	private static String SQL_CHECK_CHA = "select  trunc(sysdate)-state_date from order_relation where 1=1 ";
	//informix版本
	private static String SQL_CHECK_CHA_informix = "select today-state_date from order_relation where 1=1 ";
	// 当前DAO 所属数据库name
	
	private String dbName = JNDINames.DEFAULT_DATASOURCE;
	
	/**
	 * 对于oracle和informix有差别的sql，使用这种方式替换
	 * xmh 
	 */
	static{
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			SQL_CHECK_CHA=SQL_CHECK_CHA_informix;
		} else if (CrmConstants.DB_TYPE_ORACLE.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			
		}
	}
	
	public OrderRelationDAO() {

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

		params.add(map.get("order_relation_id"));

		params.add(map.get("prod_inst_id"));

		params.add(map.get("product_id"));

		params.add(map.get("prod_offer_inst_id"));

		params.add(map.get("prod_offer_id"));

		params.add(map.get("order_channel"));

		params.add(map.get("state"));

		params.add(DAOUtils.parseDateTime(map.get("eff_date")));

		params.add(DAOUtils.parseDateTime(map.get("exp_date")));

		params.add(map.get("package_id"));

		params.add(map.get("lan_id"));

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

		params.add(vo.get("order_relation_id"));

		params.add(vo.get("prod_inst_id"));

		params.add(vo.get("product_id"));

		params.add(vo.get("prod_offer_inst_id"));

		params.add(vo.get("prod_offer_id"));

		params.add(vo.get("order_channel"));

		params.add(vo.get("state"));

		params.add(DAOUtils.parseDateTime(vo.get("eff_date")));

		params.add(DAOUtils.parseDateTime(vo.get("exp_date")));

		params.add(vo.get("package_id"));

		params.add(vo.get("lan_id"));

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

		params.add(vo.get("order_relation_id"));

		params.add(vo.get("prod_inst_id"));

		params.add(vo.get("product_id"));

		params.add(vo.get("prod_offer_inst_id"));

		params.add(vo.get("prod_offer_id"));

		params.add(vo.get("order_channel"));

		params.add(vo.get("state"));

		params.add(DAOUtils.parseDateTime(vo.get("eff_date")));

		params.add(DAOUtils.parseDateTime(vo.get("exp_date")));

		params.add(vo.get("package_id"));

		params.add(vo.get("lan_id"));

		params.add(keyCondMap.get("order_relation_id"));

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

		params.add(keyCondMap.get("order_relation_id"));

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

	public PageModel getOrderProductByCond(String whereCond, List para,
			int pageIndex, int pageSize) throws FrameException {
		// 获取销售品名称的SQL片段
		// 先判断PPROD_OFFER_ID是否为空,为空则取PACKAGE_ID,如果PACKAGE_ID也为空则取PROD_OFFER_ID
		String prod_set_sql = "decode(nvl(ore.PPROD_OFFER_ID,0), 0, decode(nvl(ore.PACKAGE_ID,0), 0, ore.PROD_OFFER_ID, ore.PACKAGE_ID), ore.PPROD_OFFER_ID)";

		StringBuffer mainSql = new StringBuffer("select ");
		mainSql.append("ore.ORDER_RELATION_ID,");
		mainSql.append("ore.STATE,");
		mainSql.append("to_char(ore.EFF_DATE,"+DatabaseFunction.getDataFormat(2)+") EFF_DATE ,");
		mainSql.append("to_char(ore.EXP_DATE,"+DatabaseFunction.getDataFormat(2)+") EXP_DATE ,");
		mainSql.append("ore.LAN_ID,");
		mainSql.append("ore.PROD_INST_ID,");
		mainSql.append("ore.PROD_OFFER_ID,");
		mainSql.append("ore.PACKAGE_ID,");
		mainSql.append("ore.PPROD_OFFER_ID,");
		mainSql.append("ore.CREATE_DATE,");
		mainSql.append("po.PROD_OFFER_NAME,");
		mainSql.append("p.PRODUCT_NBR,");
		mainSql.append("(select pname from dc_public where stype='9020' and pkey= order_channel) ORDER_CHANNEL,");
		mainSql.append("p.PRODUCT_ID,");
		mainSql.append("p.PRODUCT_NAME,");
		mainSql.append("p.PRODUCT_PROVIDER_ID,");
		mainSql.append("(select PARTNER_NAME from PARTNER where PARTNER_ID = p.PRODUCT_PROVIDER_ID) PARTNER_NAME,");
		mainSql.append("(select PROD_OFFER_NAME from PROD_OFFER where PROD_OFFER_ID = ");
		mainSql.append(prod_set_sql);
		mainSql.append(") prod_set,");
		mainSql.append(prod_set_sql).append(" M_PROD_OFFER_ID,");
		mainSql.append("ore.ACC_NBR PRODUCT_NO,");
		mainSql.append("(select PRODUCT_NAME from PRODUCT where PRODUCT_ID = ore.prod_type_cd) M_PRODUCT_NAME, ");
		mainSql.append("ore.prod_type_cd M_PRODUCT_ID ");

		mainSql.append("from ORDER_RELATION ore ");
//		mainSql.append("left join PROD_INST pi on pi.PROD_INST_ID = ore.PROD_INST_ID ");
		mainSql.append("left join PRODUCT p on p.PRODUCT_ID = ore.PRODUCT_ID ");
		mainSql.append("left join PROD_OFFER po on po.PROD_OFFER_ID = ore.PROD_OFFER_ID");

		String querySQL = "select * from (" + mainSql + ") tt where 1=1 "
				+ whereCond + " order by PROD_OFFER_NAME";
		String countSQL = "select count(*) as col_counts from (" + mainSql
				+ ") tt where 1=1 " + whereCond;
		DynamicDict dto = Base.queryPage(this.getDbName(), querySQL, countSQL,
				para, pageIndex, pageSize, null, null, 1, Const.UN_JUDGE_ERROR,
				"");
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT));
	}

	public PageModel getOrderHistory(String whereCond, List para,
			int pageIndex, int pageSize) throws FrameException {
		// 获取销售品名称的SQL片段
		// 先判断PPROD_OFFER_ID是否为空,为空则取PACKAGE_ID,如果PACKAGE_ID也为空则取PRODUCT_OFFER_ID
		String prod_set_sql = "decode(nvl(h.PPROD_OFFER_ID,0), 0, decode(nvl(h.PACKAGE_ID,0), 0, h.PRODUCT_OFFER_ID, h.PACKAGE_ID), h.PPROD_OFFER_ID)";

		StringBuffer mainSql = new StringBuffer("select ");
		mainSql.append("h.ORDER_ITEM_ID,");
		mainSql.append("h.CUST_ORDER_ID,");
		mainSql.append("h.ORDER_ITEM_CD order_type,");
		mainSql.append("(SELECT ORDER_CHANNEL FROM CUSTOMER_ORDER_HIS ch WHERE ch.CUST_ORDER_ID = h.CUST_ORDER_ID) ORDER_CHANNEL,");
		mainSql.append("h.HANDLE_TIME,");
		mainSql.append("h.ACC_NBR,");
		mainSql.append("h.lan_id,");
		mainSql.append("h.STATUS state,");
		mainSql.append("p.PRODUCT_NAME M_PRODUCT_NAME,");
		mainSql.append("(select PROD_OFFER_NAME from PROD_OFFER where PROD_OFFER_ID = ");
		mainSql.append(prod_set_sql);
		mainSql.append(") prod_set ");
		mainSql.append("from ORDER_ITEM_HIS h ");
		mainSql.append("left join PRODUCT p on h.PRODUCT_ID = p.PRODUCT_ID ");

		String querySQL = "select * from (" + mainSql + ") tt where 1=1 "
				+ whereCond;
		String countSQL = "select count(*) as col_counts from (" + mainSql
				+ ") tt where 1=1 " + whereCond;
		DynamicDict dto = Base.queryPage(this.getDbName(), querySQL, countSQL,
				para, pageIndex, pageSize, null, null, 1, Const.UN_JUDGE_ERROR,
				"");
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT));
	}

	public PageModel getOrderHistoryByProduct(String whereCond, List para,
			int pageIndex, int pageSize) throws FrameException {
		// StringBuffer querySql = new StringBuffer("select ");
		// querySql.append("SERVICE_OFFER_ID,HANDLE_TIME,ARCHIVE_DATE");
		// querySql.append("from CUSTOMER_ORDER_HIS where 1=1 ");
		StringBuffer querySql = new StringBuffer();
		querySql
				.append("select service_offer_id, handle_time, archive_date from ORDER_ITEM_HIS where service_offer_id>=10 and service_offer_id<=16");
		querySql.append(whereCond);

		//为支持INFORMIX做修改
		StringBuffer countSql = new StringBuffer(
				"select count(*) from ORDER_ITEM_HIS where 1=1 ");
		countSql.append(whereCond);

		DynamicDict dto = Base.queryPage(this.getDbName(), querySql.toString(),
				countSql.toString(), para, pageIndex, pageSize, null, null, 1,
				Const.UN_JUDGE_ERROR, "");

		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT));
	}

	public ArrayList getOffersByCond(String whereCond, List para)
			throws FrameException {
		StringBuffer mainSql = new StringBuffer("select ");
		mainSql.append("a.order_relation_id,");
		mainSql.append("a.state,");
		mainSql.append("a.acc_nbr,");
		mainSql.append("a.lan_id,");
		mainSql.append("a.product_id,");
		mainSql
				.append("(select p.product_name from product p where p.product_id = a.prod_type_cd) m_product_name,");
		mainSql.append("a.prod_offer_id,");
		mainSql
				.append("(select po.prod_offer_name from prod_offer po where po.prod_offer_id = a.prod_offer_id) prod_offer_name,");
		mainSql.append("a.pprod_offer_id,");
		mainSql
				.append("(select po.prod_offer_name from prod_offer po where po.offer_nbr = to_char(a.pprod_offer_id)) pprod_offer_name,");
		mainSql.append("a.package_id,");
		mainSql
				.append("(select po.prod_offer_name from prod_offer po where po.prod_offer_id = a.package_id) as package_name ");
		mainSql.append("from ORDER_RELATION a ");
		String querySQL = mainSql.toString() + "where 1=1 " + whereCond;
		DynamicDict dto = Base.query(getDbName(), querySQL, para, "A", 1,
				Const.UN_JUDGE_ERROR, "");

		ArrayList list = (ArrayList) dto.m_Values.get("A");
		return list;
	}

	/**
	 * 更新指定号码的全部订购关系状态.
	 * 
	 * @param accNbr
	 *            用户号码.
	 * @param productId
	 *            主产品ID.
	 * @param state
	 *            状态.
	 * @throws FrameException
	 */
	public void updateOrderRelationState(String accNbr, String productId,
			String state) throws FrameException {
		StringBuffer sql = new StringBuffer();
		sql.append("update ORDER_RELATION set state='").append(state).append(
				"' ");
		sql.append("where PROD_INST_ID = (");
		sql.append("select PROD_INST_ID from PROD_INST where PRODUCT_ID=")
				.append(accNbr).append(" and ACC_NBR='").append(accNbr).append(
						"'");
		sql.append(")");

		Base.update(getDbName(), sql.toString(), new String[] {}, 1);
	}

	/**
	 * 订购关系统计查询
	 * 
	 * @param whereCond
	 * @param para
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws FrameException
	 */
	public PageModel orderRelationSta(String whereCond, List para,
			int pageIndex, int pageSize) throws FrameException {
		String querySQL = this.SQL_SELECT_STA + whereCond;
		String countSQL = getSelectCountSQL() + whereCond;
		DynamicDict dto = Base.queryPage(this.getDbName(), querySQL, countSQL,
				para, pageIndex, pageSize, null, Const.ACTION_RESULT, 1,
				Const.UN_JUDGE_ERROR, "");
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT));
	}
	public PageModel orderRelationAllSta(String whereCond, List para,
			int pageIndex, int pageSize) throws FrameException {
		String querySQL = this.SQL_SELECT_ALL_STA + whereCond;
		String countSQL = this.SQL_SELECT_ALL_STA_COUNT + whereCond;
		DynamicDict dto = Base.queryPage(this.getDbName(), querySQL, countSQL,
				para, pageIndex, pageSize, null, Const.ACTION_RESULT, 1,
				Const.UN_JUDGE_ERROR, "");
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT));
	}
	/**
	 * 判断指定业务号码当日订购关系是否发生变化
	 * @param prodType
	 * @param accNbr
	 * @return
	 * @throws FrameException
	 */
	public String isOrderRelaChange(String prodType, String accNbr, String productId) throws FrameException {
		String querySQL = this.SQL_CHECK_CHA + " and product_id="+productId+" and acc_nbr='"+accNbr+"'";
		return Base.query_string(this.getDbName(), querySQL, 1);
	}
	/**
	 * 调整订购关系
	 * @param prodType
	 * @param accNbr
	 * @param packageId
	 * @param pprodOfferId
	 * @param status
	 * @return
	 * @throws FrameException
	 */
	public boolean adjustOrderRelation(String prodType,
									   String accNbr,
									   String packageId, 
									   String pprodOfferId,
									   String status,
									   String productId) throws FrameException{
		String[] prodInstIdStr = getIdTypeStr(accNbr,prodType).split(",");
		String querySQL = "update order_relation set state='"+status+"',PACKAGE_ID='"+packageId+"',PPROD_OFFER_ID='"+pprodOfferId+"'"+
						  " where acc_nbr='"+accNbr+"' and product_id="+productId+" and PROD_TYPE_CD='"+prodInstIdStr[1]+"'";
		logger.info("OrderRelationDAO.adjustOrderRelation SQL:"+querySQL);
		int num = Base.update(this.getDbName(), querySQL, new String[]{}, 1);
		if(num > 0)return true;
		else return false;
	}
	/**
	 * 删除订购关系
	 * @param prodType
	 * @param accNbr
	 * @param packageId
	 * @param pprodOfferId
	 * @param status
	 * @return
	 * @throws FrameException
	 */
	public boolean delOrderRelation(String prodType,
									String accNbr,
									String productId) throws FrameException{
		String querySQL = "delete from order_relation where product_id="+productId+" and acc_nbr='"+accNbr+"'";
		int num = Base.update(this.getDbName(), querySQL, new String[]{}, 1);
		if(num > 0)return true;
		else return false;
	}
	/**
	 * 增补订购关系
	 * @param prodType
	 * @param accNbr
	 * @param packageId
	 * @param pprodOfferId
	 * @param status
	 * @return
	 * @throws FrameException
	 */
	public boolean addOrderRelation(String prodType,
									String accNbr,
									String productId) throws FrameException{
		
		String[] prodInstIdStr = getIdTypeStr(accNbr,prodType).split(",");
		
		String querySQL = "insert into ORDER_RELATION (ORDER_RELATION_ID,PROD_INST_ID,PRODUCT_ID,"+
						  "PROD_OFFER_INST_ID,PROD_OFFER_ID,ORDER_CHANNEL,STATE,EFF_DATE,EXP_DATE,"+
						  "PACKAGE_ID,LAN_ID,STATE_DATE,PPROD_OFFER_ID,ACTIVE_STATE,ACC_NBR,CREATE_DATE,PROD_TYPE_CD)"+
						  "select SEQ_ORDER_RELATION_ID.Nextval,"+prodInstIdStr[0]+",product_id,null,null,CHANNEL_PLAYER_ID,"+
						  "status,EFFECTIVE_TIME,EXPIRE_TIME,PACKAGE_ID,null,sysdate-1,PPRODUCT_OFFER_ID,'1',"+
						  "oa,sysdate-1,"+prodInstIdStr[1]+" from VSOP_ADJUST_COMPARE where oa='"+accNbr+
						  "' and oa_type='"+prodType+"' and product_id="+productId;
		
		//为支持INFORMIX增加
		String querySQL_informix = "insert into ORDER_RELATION (ORDER_RELATION_ID,PROD_INST_ID,PRODUCT_ID,"+
		  "PROD_OFFER_INST_ID,PROD_OFFER_ID,ORDER_CHANNEL,STATE,EFF_DATE,EXP_DATE,"+
		  "PACKAGE_ID,LAN_ID,STATE_DATE,PPROD_OFFER_ID,ACTIVE_STATE,ACC_NBR,CREATE_DATE,PROD_TYPE_CD)"+
		  "select SEQ_ORDER_RELATION_ID.Nextval,"+prodInstIdStr[0]+",product_id,null,null,CHANNEL_PLAYER_ID,"+
		  "status,EFFECTIVE_TIME,EXPIRE_TIME,PACKAGE_ID,null,current-1 units day,PPRODUCT_OFFER_ID,'1',"+
		  "oa,current-1 units day,"+prodInstIdStr[1]+" from VSOP_ADJUST_COMPARE where oa='"+accNbr+
		  "' and oa_type='"+prodType+"' and product_id="+productId;

 
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)){ 
			querySQL=querySQL_informix;		
		}

		logger.info("OrderRelationDAO.addOrderRelation SQL:"+querySQL);
		int num = Base.update(this.getDbName(), querySQL, new String[]{}, 1);
		if(num > 0)return true;
		else return false;
	}
	public String getIdTypeStr(String accNbr, String prodType)throws FrameException{
		String Sql = "";
		if("0".equals(prodType)){
			//0:手机
			Sql = "select param_val from dc_system_param where param_code='DC_MSISDN'";
		}else if("1".equals(prodType)){
			//1:小灵通
			Sql = "select param_val from dc_system_param where param_code='DC_MSISDN'";
		}else{
			//2:固话
			Sql = "select param_val from dc_system_param where param_code='DC_MSISDN'";
		}
		String prodTypeStr = Base.query_string(this.getDbName(), Sql, 1);
		if("".equals(prodTypeStr))return null;
		String querySQL = "select prod_inst_id || ',' || product_id ||',' || lan_id from prod_inst where acc_nbr='"+
						  accNbr+"' and product_id in ("+prodTypeStr+")";
		logger.info("OrderRelationDAO.getIdTypeStr SQL:"+querySQL);
		String typeStr = Base.query_string(this.getDbName(), querySQL, 1);
		if(typeStr.equals(""))return null;
		return typeStr;
	}
	/**
	 * 订单归档
	 * @param prodType
	 * @param accNbr
	 * @param packageId
	 * @param pprodOfferId
	 * @param status
	 * @return
	 * @throws FrameException
	 */
	public boolean addCustomerHis(Map param,String custOrderId, String reason, String disposalResult) throws FrameException{
		String prodType = Const.getStrValue(param,"oaType");
		String accNbr = Const.getStrValue(param,"oa");
		String productId = Const.getStrValue(param,"productId");
		String flag = Const.getStrValue(param,"flag");
		String querySQL = "";
		String querySQL_informix="";
		String prod_inst_id = Const.getStrValue(param, "prod_inst_id");
		String lan_id = Const.getStrValue(param, "lan_id");
		if("0".equals(flag)){
			querySQL = "insert into CUSTOMER_ORDER_HIS (CUST_ORDER_ID,OTHER_SYS_ORDER_ID,CUST_SO_NUMBER,CUST_ORDER_TYPE,Status,STATUS_DATE,"+
 			  "reason,Order_Channel,order_system,Disposal_Result,Acc_Nbr,Product_Id,Lan_Id,prod_inst_id)"+
			  "select "+custOrderId+",STREAMING_NO,STREAMING_NO,'3',"+
			  "'00X',sysdate,'"+reason+"',CHANNEL_PLAYER_ID,CHANNEL_PLAYER_ID,'"+disposalResult+"',OA,product_id,'"+lan_id+"',"+prod_inst_id+
			  " from VSOP_ADJUST_COMPARE where oa='"+accNbr+
			  "' and oa_type='"+prodType+"' and product_id="+productId;
			
			//支持INFORMIX
			
			querySQL_informix = "insert into CUSTOMER_ORDER_HIS (CUST_ORDER_ID,OTHER_SYS_ORDER_ID,CUST_SO_NUMBER,CUST_ORDER_TYPE,Status,STATUS_DATE,"+
			  "reason,Order_Channel,order_system,Disposal_Result,Acc_Nbr,Product_Id,Lan_Id,prod_inst_id)"+
			  "select "+custOrderId+",STREAMING_NO,STREAMING_NO,'3',"+
			  "'00X',current,'"+reason+"',CHANNEL_PLAYER_ID,CHANNEL_PLAYER_ID,'"+disposalResult+"',OA,product_id,'"+lan_id+"',"+prod_inst_id+
			  " from VSOP_ADJUST_COMPARE where oa='"+accNbr+
			  "' and oa_type='"+prodType+"' and product_id="+productId;
			
			
			if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
				querySQL=querySQL_informix;
			}
			
					
		}else{
			querySQL = "insert into CUSTOMER_ORDER_HIS (CUST_ORDER_ID,OTHER_SYS_ORDER_ID,CUST_SO_NUMBER,CUST_ORDER_TYPE,Status,STATUS_DATE,"+
			  "reason,Order_Channel,order_system,Disposal_Result,Acc_Nbr,Product_Id,Lan_Id,prod_inst_id)"+
			  "select "+custOrderId+",STREAMING_NO,STREAMING_NO,'3',"+
			  "status,sysdate,'"+reason+"',CHANNEL_PLAYER_ID,CHANNEL_PLAYER_ID,'"+disposalResult+"',OA,product_id,'"+lan_id+"',"+prod_inst_id+
			  " from VSOP_ADJUST_COMPARE where oa='"+accNbr+
			  "' and oa_type='"+prodType+"' and product_id="+productId;
			
			//支持informix
			querySQL_informix = "insert into CUSTOMER_ORDER_HIS (CUST_ORDER_ID,OTHER_SYS_ORDER_ID,CUST_SO_NUMBER,CUST_ORDER_TYPE,Status,STATUS_DATE,"+
			  "reason,Order_Channel,order_system,Disposal_Result,Acc_Nbr,Product_Id,Lan_Id,prod_inst_id)"+
			  "select "+custOrderId+",STREAMING_NO,STREAMING_NO,'3',"+
			  "status,current,'"+reason+"',CHANNEL_PLAYER_ID,CHANNEL_PLAYER_ID,'"+disposalResult+"',OA,product_id,'"+lan_id+"',"+prod_inst_id+
			  " from VSOP_ADJUST_COMPARE where oa='"+accNbr+
			  "' and oa_type='"+prodType+"' and product_id="+productId;
			
			if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
				querySQL=querySQL_informix;
			}
				
		}
		logger.info("OrderRelationDAO.addCustomerHis SQL:"+querySQL);
		int num = Base.update(this.getDbName(), querySQL, new String[]{}, 1);
		if(num > 0)return true;
		else return false;
	}
	/**
	 * 订单项归档
	 * @param prodType
	 * @param accNbr
	 * @param packageId
	 * @param pprodOfferId
	 * @param status
	 * @return
	 * @throws FrameException
	 */
	public boolean addOrderItemHis(Map param,String custOrderId, String reason, String disposalResult) throws FrameException{
		String prodType = Const.getStrValue(param,"oaType");
		String accNbr = Const.getStrValue(param,"oa");
		String productId = Const.getStrValue(param,"productId");
		String flag = Const.getStrValue(param,"flag");
		String querySQL = "";
		String prod_inst_id = Const.getStrValue(param, "prod_inst_id");
		String lan_id = Const.getStrValue(param, "lan_id");
		if("0".equals(flag)){
			querySQL = "insert into ORDER_ITEM_HIS (ORDER_ITEM_ID,CUST_ORDER_ID,STATUS,STATUS_DATE,STATE_CHANGE_REASON,"+
               		   "ARCHIVE_DATE,PRODUCT_ID,ACC_NBR,PACKAGE_ID,PPROD_OFFER_ID,EFF_TIME,EXP_TIME,RESULT_TYPE,LAN_ID,PROD_INST_ID)"+
			  "select SEQ_ORDER_ITEM_ID.Nextval,STREAMING_NO,'00X',"+DatabaseFunction.currentDay()+",'"+reason+"',"+
			  DatabaseFunction.currentDay()+",product_id,oa,PACKAGE_ID,PPRODUCT_OFFER_ID,EFFECTIVE_TIME,EXPIRE_TIME,'1','"+lan_id+"',"+prod_inst_id+
			  " from VSOP_ADJUST_COMPARE where oa='"+accNbr+
			  "' and oa_type='"+prodType+"' and product_id="+productId;
		}else{
			querySQL = "insert into ORDER_ITEM_HIS (ORDER_ITEM_ID,CUST_ORDER_ID,STATUS,STATUS_DATE,STATE_CHANGE_REASON,"+
    		   "ARCHIVE_DATE,PRODUCT_ID,ACC_NBR,PACKAGE_ID,PPROD_OFFER_ID,EFF_TIME,EXP_TIME,RESULT_TYPE,LAN_ID,PROD_INST_ID)"+
			   "select SEQ_ORDER_ITEM_ID.Nextval,STREAMING_NO,status,"+DatabaseFunction.currentDay()+",'"+reason+"',"+
			   DatabaseFunction.currentDay()+",product_id,oa,PACKAGE_ID,PPRODUCT_OFFER_ID,EFFECTIVE_TIME,EXPIRE_TIME,'1','"+lan_id+"',"+prod_inst_id+
				  " from VSOP_ADJUST_COMPARE where oa='"+accNbr+
				  "' and oa_type='"+prodType+"' and product_id="+productId;
		}
		logger.info("OrderRelationDAO.addOrderItemHis SQL:"+querySQL);
		int num = Base.update(this.getDbName(), querySQL, new String[]{}, 1);
		if(num > 0)return true;
		else return false;
	}
	public boolean insertOrderLog(Map param) throws FrameException{
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String productId = (String)param.get("productId");
		String querySQL = "insert into ORDER_RELATION_COMPARE_LOG "+
              "select SEQ_order_compare_id.Nextval,STREAMING_NO,SUBSCRIPTION_ID,OA,OA_TYPE,DA,DA_TYPE,"+
              "FA,FA_TYPE,WITHDRAWER,WITHDRAW_REASON,SUSPEND_REASON,SPID,PRODUCT_ID,PACKAGE_ID,PPRODUCT_OFFER_ID,"+
              "STATUS,SUBSCRIBE_TIME,EFFECTIVE_TIME,EXPIRE_TIME,SUSPEND_TIME,RESUME_TIME,LAST_USE_TIME,SILENCE,"+
              "CHANNEL_PLAYER_ID,FLAG,OLD_STATUS,OLD_PACKAGE_ID,OLD_PPRODUCT_OFFER_ID,FILE_NAME,SYSDATE,'1'"+//1:代表手工调整
              " from VSOP_ADJUST_COMPARE where oa='"+accNbr+
			  "' and oa_type='"+prodType+"' and product_id="+productId;
		logger.info("OrderRelationDAO.insertOrderLog SQL:"+querySQL);
		int num = Base.update(this.getDbName(), querySQL, new String[]{}, 1);
		if(num > 0)return true;
		else return false;
	}
	
	/**
	 * 订购关系历史查询
	 * @param prodInstId
	 * @param startTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws FrameException
	 */
	public PageModel getOrderProductHisByCond(String  prodInstId,String startTime,String endTime,String activeType,
			int pageIndex,int pageSize) throws FrameException {
		StringBuffer querySql_oracle = new StringBuffer();
		StringBuffer querySql_informix = new StringBuffer();
		querySql_oracle.append("select r.product_id,r.product_offer_id pr_id, r.status state,r.service_offer_id, to_char(r.status_date,'yyyy-mm-dd hh24:mi:ss') create_date, to_char(r.eff_time,'yyyy-mm-dd hh24:mi:ss') eff_date, to_char(r.exp_time,'yyyy-mm-dd hh24:mi:ss') exp_date,");
		querySql_oracle.append("p.product_nbr,");	
		querySql_oracle.append(" (select o.order_channel from customer_order o where o.cust_order_id=r.cust_order_id and rownum=1) order_channel,r.package_id,r.pprod_offer_id, p.product_name p_name," );
		querySql_oracle.append(" (select pr.prod_offer_name from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_name_a," );	
		querySql_oracle.append(" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.package_id) pr_name_b," ); 
		querySql_oracle.append(" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.pprod_offer_id) pr_name_c," );		
		querySql_oracle.append(" (select t.partner_id from partner t where t.partner_id = p.product_provider_id ) sp_id," );
		querySql_oracle.append(" (select t.partner_name from partner t where t.partner_id = p.product_provider_id ) sp_name," );
		querySql_oracle.append(" (select pr.prod_offer_sub_type from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_type," );
		querySql_oracle.append(" (select pr.chargingpolicy_cn from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_chargeCN,  " );
		querySql_oracle.append(" (select to_char(eff_date,'yyyy-mm-dd hh24:mi:ss')||'#'||to_char(exp_date,'yyyy-mm-dd hh24:mi:ss') from order_relation orl where orl.product_id=r.product_id and orl.PROD_INST_ID=r.PROD_INST_ID and rownum=1) as efxp_date " );
		querySql_oracle.append(" from order_item r inner join product p on p.product_id = r.product_id" );
		querySql_oracle.append(" where r.PROD_INST_ID ='").append(prodInstId);
		querySql_oracle.append(" ' and r.status ='00A' and r.order_item_cd!='04' and r.status_date between to_date " );
		querySql_oracle.append("('").append(startTime).append("','yyyyMMdd') and to_date('").append(endTime).append("','yyyyMMdd')+1 ");
		querySql_oracle.append( "and r.service_offer_id in (").append(activeType).append(")" );		
		querySql_oracle.append(" union all ");
		
		querySql_oracle.append("select r.product_id,r.product_offer_id pr_id, r.status state,r.service_offer_id, to_char(r.status_date,'yyyy-mm-dd hh24:mi:ss') create_date, to_char(r.eff_time,'yyyy-mm-dd hh24:mi:ss') eff_date, to_char(r.exp_time,'yyyy-mm-dd hh24:mi:ss') exp_date," );
		querySql_oracle.append("p.product_nbr,");
		querySql_oracle.append(" (select o.order_channel from customer_order_his o where o.cust_order_id=r.cust_order_id and rownum=1) order_channel,r.package_id,r.pprod_offer_id, p.product_name p_name," );
		querySql_oracle.append(" (select pr.prod_offer_name from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_name_a," );	
		querySql_oracle.append(" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.package_id) pr_name_b," );
		querySql_oracle.append(" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.pprod_offer_id) pr_name_c," );		
		querySql_oracle.append(" (select t.partner_id from partner t where t.partner_id = p.product_provider_id ) sp_id," );
		querySql_oracle.append(" (select t.partner_name from partner t where t.partner_id = p.product_provider_id ) sp_name," );
		querySql_oracle.append(" (select pr.prod_offer_sub_type from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_type," );
		querySql_oracle.append(" (select pr.chargingpolicy_cn from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_chargeCN, " );
		querySql_oracle.append(" (select to_char(eff_date,'yyyy-mm-dd hh24:mi:ss')||'#'||to_char(exp_date,'yyyy-mmddhh24miss') from order_relation orl where orl.product_id=r.product_id and orl.PROD_INST_ID=r.PROD_INST_ID and rownum=1) as efxp_date " );
		querySql_oracle.append(" from order_item_his r inner join product p on p.product_id = r.product_id" );
		querySql_oracle.append(" where r.PROD_INST_ID = '" ).append(prodInstId);
		querySql_oracle.append("' and r.status ='00A' and r.order_item_cd!='04' and r.status_date between to_date " );
		querySql_oracle.append("('").append(startTime).append("','yyyyMMdd') and to_date( '").append(endTime).append(" ','yyyyMMdd')+1 " );	
		querySql_oracle.append( "and r.service_offer_id in (").append(activeType).append(")" );	
		querySql_oracle.append(createPartitionCondistion(startTime,endTime));	
		
		
		querySql_informix.append("select r.product_id,r.product_offer_id pr_id, r.status state,r.service_offer_id, to_char(r.status_date,'yyyy-mm-dd hh24:mi:ss') create_date, to_char(r.eff_time,'yyyy-mm-dd hh24:mi:ss') eff_date, to_char(r.exp_time,'yyyy-mm-dd hh24:mi:ss') exp_date,");
		querySql_informix.append("p.product_nbr,");	
		querySql_informix.append(" (select min(o.order_channel) from customer_order o where o.cust_order_id=r.cust_order_id ) order_channel,r.package_id,r.pprod_offer_id, p.product_name p_name," );
		querySql_informix.append(" (select pr.prod_offer_name from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_name_a," );	
		querySql_informix.append(" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.package_id) pr_name_b," ); 
		querySql_informix.append(" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.pprod_offer_id) pr_name_c," );		
		querySql_informix.append(" (select t.partner_id from partner t where t.partner_id = p.product_provider_id ) sp_id," );
		querySql_informix.append(" (select t.partner_name from partner t where t.partner_id = p.product_provider_id ) sp_name," );
		querySql_informix.append(" (select pr.prod_offer_sub_type from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_type," );
		querySql_informix.append(" (select pr.chargingpolicy_cn from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_chargeCN,  " );
		querySql_informix.append(" (select min(to_char(eff_date,'%Y-%m-%d %H:%M:%S')||'#'||to_char(exp_date,'%Y-%m-%d %H:%M:%S')) from order_relation orl where orl.product_id=r.product_id and orl.PROD_INST_ID=r.PROD_INST_ID ) as efxp_date " );
		querySql_informix.append(" from order_item r inner join product p on p.product_id = r.product_id" );
		querySql_informix.append(" where r.PROD_INST_ID ='").append(prodInstId);
		querySql_informix.append(" ' and r.status ='00A' and r.order_item_cd!='04' and r.status_date between to_date " );
		querySql_informix.append("('").append(startTime).append("','%Y%m%d') and to_date('").append(endTime).append("','%Y%m%d')"+"+(INTERVAL(1)DAY TO DAY)" );
		querySql_informix.append( "and r.service_offer_id in (").append(activeType).append(")" );		
		querySql_informix.append(" union all ");
		
		querySql_informix.append("select r.product_id,r.product_offer_id pr_id, r.status state,r.service_offer_id, to_char(r.status_date,'%Y-%m-%d %H:%M:%S') create_date, to_char(r.eff_time,'%Y-%m-%d %H:%M:%S') eff_date, to_char(r.exp_time,'%Y-%m-%d %H:%M:%S') exp_date," );
		querySql_informix.append("p.product_nbr,");
		querySql_informix.append(" (select min(o.order_channel) from customer_order_his o where o.cust_order_id=r.cust_order_id and ) order_channel,r.package_id,r.pprod_offer_id, p.product_name p_name," );
		querySql_informix.append(" (select pr.prod_offer_name from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_name_a," );	
		querySql_informix.append(" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.package_id) pr_name_b," );
		querySql_informix.append(" (select pr.prod_offer_name from prod_offer pr where prod_offer_id = r.pprod_offer_id) pr_name_c," );		
		querySql_informix.append(" (select t.partner_id from partner t where t.partner_id = p.product_provider_id ) sp_id," );
		querySql_informix.append(" (select t.partner_name from partner t where t.partner_id = p.product_provider_id ) sp_name," );
		querySql_informix.append(" (select pr.prod_offer_sub_type from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_type," );
		querySql_informix.append(" (select pr.chargingpolicy_cn from prod_offer pr where pr.prod_offer_id = r.product_offer_id) pr_chargeCN, " );
		querySql_informix.append(" (select min(to_char(eff_date,'%Y-%m-%d %H:%M:%S')||'#'||to_char(exp_date,'%Y-%m%d%H%M%S')) from order_relation orl where orl.product_id=r.product_id and orl.PROD_INST_ID=r.PROD_INST_ID ) as efxp_date " );
		querySql_informix.append(" from order_item_his r inner join product p on p.product_id = r.product_id" );
		querySql_informix.append(" where r.PROD_INST_ID = '" ).append(prodInstId);
		querySql_informix.append("' and r.status ='00A' and r.order_item_cd!='04' and r.status_date between to_date " );
		querySql_informix.append("('").append(startTime).append("','%Y%m%d') and to_date( '").append(endTime).append(" ','%Y%m%d') "+"+(INTERVAL(1)DAY TO DAY)" );	
		querySql_informix.append( "and r.service_offer_id in (").append(activeType).append(")" );	
		querySql_informix.append(createPartitionCondistion(startTime,endTime));	 
		
		String querySQL = querySql_oracle.toString() ;
		if( CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE").equals(CrmConstants.DB_TYPE_INFORMIX)){
			querySQL = querySql_informix.toString() ;
		}
		String countSQL = "select count(*) as col_counts from (" +querySQL + ") tt where 1=1";
		List para=null;
		
		DynamicDict dto = Base.queryPage(this.getDbName(), querySQL, countSQL,
				para, pageIndex, pageSize, null, Const.ACTION_RESULT, 1,
				Const.UN_JUDGE_ERROR, "");
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT));
	}
	private String createPartitionCondistion(
			String beginStr, String endStr) {
		String sql = "and (";
		String cond = "";
		String pattern = "yyyyMMdd";
		Calendar beginCal = DateUtil.parseStrToCalendar(beginStr, pattern );
		Calendar endCal = DateUtil.parseStrToCalendar(endStr, pattern );
		int beginMonth = beginCal.get(Calendar.MONTH)+1;
		int endMonth = endCal.get(Calendar.MONTH)+1;
		for(int i = beginMonth;i<=endMonth;i++){
			cond += " partition_id = "+String.valueOf(i)+" OR";
		}
		if(cond.length()>2) cond = cond.substring(0, cond.length()-2);
		sql = sql + cond + ")";
		return sql;
	}
}
