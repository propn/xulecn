package com.ztesoft.vsop.order.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.crm.product.bo.ProdOffBO;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.order.ServiceRequestXmlHelper;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.order.manager.bo.OrderRelationBO;
import com.ztesoft.vsop.order.vo.ProductOfferInfo;
import com.ztesoft.vsop.order.vo.VSubProdInfo;
import com.ztesoft.vsop.order.vo.request.SubscribeToVSOPRequest;

public class BatchImportRelationDAO extends AbstractDAO {
	private static Logger logger = Logger.getLogger(OrderDao.class);
	// 查询SQL
	private String SQL_SELECT = "select t.id,t.batch,(select p.product_name from product p where p.product_id = t.product_id) product_id,t.acc_nbr,(select o.prod_offer_name from prod_offer o where o.offer_code=prod_offer_code) prod_offer_code,t.act_type,t.state,t.operator,(select r.party_role_name from party_role r where r.party_role_id=operator) userinfo,t.create_time,result from BATCH_IMPORT_RELATION t where 1=1 ";

	// 统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from batch_import_relation t where 1=1 ";

	// insert SQl
	private String SQL_INSERT = "insert into BATCH_IMPORT_RELATION (id, batch, product_id, acc_nbr, prod_offer_code, act_type, state, operator, create_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// 普通update SQL
	private String SQL_UPDATE = "update BATCH_IMPORT_RELATION set id=?, batch=?, product_id=?, acc_nbr=?, prod_offer_code=?, act_type=?, state=?, operator=?, create_time=? where 1=1 ";

	// 普通delete SQL
	private String SQL_DELETE = "delete from BATCH_IMPORT_RELATION where 1=1 ";

	// 根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from BATCH_IMPORT_RELATION where id=?";

	// 根据主键update SQL
	private String SQL_UPDATE_KEY = "update BATCH_IMPORT_RELATION set id=?, batch=?, product_id=?, acc_nbr=?, prod_offer_code=?, act_type=?, state=?, operator=?, create_time=? where id=?";

	// 根据主键查询SQL
	private String SQL_SELECT_KEY = "select id,batch,product_id,acc_nbr,prod_offer_code,act_type,state,operator,create_time from BATCH_IMPORT_RELATION where id=? ";

	// 当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE;

	public BatchImportRelationDAO() {

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

		params.add(map.get("id"));

		params.add(map.get("batch"));

		params.add(map.get("product_id"));

		params.add(map.get("acc_nbr"));

		params.add(map.get("prod_offer_code"));

		params.add(map.get("act_type"));

		params.add(map.get("state"));

		params.add(map.get("operator"));

		params.add(DAOUtils.parseDateTime(map.get("create_time")));

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

		params.add(vo.get("id"));

		params.add(vo.get("batch"));

		params.add(vo.get("product_id"));

		params.add(vo.get("acc_nbr"));

		params.add(vo.get("prod_offer_code"));

		params.add(vo.get("act_type"));

		params.add(vo.get("state"));

		params.add(vo.get("operator"));

		params.add(DAOUtils.parseDateTime(vo.get("create_time")));

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

		params.add(vo.get("id"));

		params.add(vo.get("batch"));

		params.add(vo.get("product_id"));

		params.add(vo.get("acc_nbr"));

		params.add(vo.get("prod_offer_code"));

		params.add(vo.get("act_type"));

		params.add(vo.get("state"));

		params.add(vo.get("operator"));

		params.add(DAOUtils.parseDateTime(vo.get("create_time")));

		params.add(keyCondMap.get("id"));

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

		params.add(keyCondMap.get("id"));

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

	public String importData(List ls,String userId) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = LegacyDAOUtil.getConnection();
		String sql = "insert into BATCH_IMPORT_RELATION(id,batch,product_id,acc_nbr,prod_offer_code,act_type,operator,create_time,result)"
				+ "values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		try {
			ps = conn.prepareStatement(sql);
			int cyNum = 0;
			for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
				String line = (String) iterator.next();
				String[] linearray = line.split(",");
				int index = 1;
				ps.setLong(index++, getSequence("SEQ_BATCH_IMPORT_RELATION",conn));// batch,
				ps.setString(index++, linearray[0]);// batch,
				ps.setString(index++, linearray[3]);// product_id
				ps.setString(index++, linearray[2]);// acc_nbr
				ps.setString(index++, linearray[1]);// prod_offer_code
				ps.setString(index++, linearray[4]);// act_type
				ps.setString(index++, userId);// operator
				ps.setDate(index++, DAOUtils.getCurrentDate());// create_time
				ps.setString(index++, "");// result
				ps.addBatch();
				++cyNum;
				if (cyNum >= 500) {
					ps.executeBatch();
					cyNum = 0;
				}
			}
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
		}
		String batch = "";
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			String line = (String) iterator.next();
			String[] linearray = line.split(",");
			batch = linearray[0];
			break;
		}
		return batch;
	}

	public long getSequence(String sequenceName, Connection conn)
			throws SQLException {
		PreparedStatement ps = null;
		long seq = 0;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT " + sequenceName
					+ ".NEXTVAL FROM DUAL");
			rs = ps.executeQuery();
			if (rs.next()) {
				seq = rs.getLong(1);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
			DAOUtils.closeResultSet(rs);
		}
		return seq;
	}

	public void batchOrder(String batch) throws Exception {
		String sql = "select * from BATCH_IMPORT_RELATION t where t.batch = "
				+ batch + " and state is null order by id desc ";
		List list = this.findBySql(sql);
		String updateSql = "update BATCH_IMPORT_RELATION set state = ?,result = ? where id = ?";
		OrderRelationBO orderBo = new OrderRelationBO();
		PreparedStatement ps = null;
		List resultList = new ArrayList();
		try {
			int psnum = 0;
			for (int i = 0; i < list.size(); i++) {
				Map orderMap = (Map)list.get(i);
				resultList.add(orderProducts(orderMap));
			}
			Connection conn = LegacyDAOUtil.getConnection();
			ps = conn.prepareStatement(updateSql);
			for (int k = 0; k < resultList.size(); k++) {
				int index = 1;
				Map resMap = (Map)resultList.get(k);
				ps.setString(index++, Const.getStrValue(resMap,"state"));
				ps.setString(index++, Const.getStrValue(resMap,"ResultDesc"));
				ps.setString(index++, Const.getStrValue(resMap,"id"));
				ps.addBatch();
				psnum ++;
			}
			if(psnum >0)
				ps.executeBatch();
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
		}
	}
	/**
	 * 增值产品订购，退订操作
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	private Map orderProducts(Map orderMap) throws Exception{
		String id = Const.getStrValue(orderMap,"id");
		String act_type = Const.getStrValue(orderMap,"act_type");
		String acc_nbr = Const.getStrValue(orderMap,"acc_nbr");
		String prod_offer_code = Const.getStrValue(orderMap,"prod_offer_code");
		String product_id = Const.getStrValue(orderMap,"product_id"); //主产品

		String reqXml = "";
		boolean dataFail = false;
		if(!act_type.equals("2")){
			List prodOffers = new ArrayList();
			ProductOfferInfo prodOffer = new ProductOfferInfo();
			ProdOffBO offerBo = new ProdOffBO();
			Map prodOfferMap = offerBo.findProdOffByOfferNbr(prod_offer_code);
			prodOffer.setOffNbr(prod_offer_code);
			if(null != prodOfferMap){
				prodOffer.setProductOfferID(Const.getStrValue(prodOfferMap,"prod_offer_id"));
				prodOffer.setProductOfferType(Const.getStrValue(prodOfferMap,"prod_offer_sub_type"));
			
			VSubProdInfo prodinfo = new VSubProdInfo();
			List subProdInfoList = new ArrayList();
			prodOffer.setVSubProdInfoList(subProdInfoList);
			prodOffers.add(prodOffer);
			SubscribeToVSOPRequest order = new SubscribeToVSOPRequest();
			order.setActionType(act_type);
			order.setSystemId("200"); // 不用二次确认，默认为 从vsop发起
			order.setProductNo(acc_nbr);
			order.setProductOfferInfo(prodOffers);
			order.setProductId(product_id);
			order.setProdSpecCode(product_id);
			reqXml = order.orderProductstoXml();
			}else dataFail = true;
		}
		else {
			ServiceRequestXmlHelper helper  =ServiceRequestXmlHelper.instance();
			reqXml =helper.unSubscribeAllToVSOP("200", act_type, "", product_id, acc_nbr);
		}
		String ResultCode="";
		String ResultDesc="数据异常";
		if(!dataFail){
			/*	OrderService orderService = new OrderService();
				String respXml = orderService.subscribeToVSOP(reqXml);*/
		    	VariedServerEngine engine = new VariedServerEngine();
		    	String respXml = engine.subscribeToVSOP(reqXml);
				ResultCode = StringUtil.getInstance().getTagValue("ResultCode", respXml);
				ResultDesc =StringUtil.getInstance().getTagValue("ResultDesc", respXml);
			}
		String state = "00X";
		if("0".equals(ResultCode))
			state ="00A";
		Map resultMap = new HashMap();
		resultMap.put("state", state);
		resultMap.put("ResultDesc", ResultDesc);
		resultMap.put("id", id);
		return resultMap;
	}
}
