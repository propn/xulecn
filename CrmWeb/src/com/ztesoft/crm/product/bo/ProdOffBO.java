package com.ztesoft.crm.product.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.product.dao.ProdOffDAO;
import com.ztesoft.vsop.product.dao.ProductDAO;

public class ProdOffBO extends DictAction {
	/**
	 * 需要替换位置 说明 ： 1. 错误参数处理,根据实际情况修改 2. searchProdOffData 改方法的参数名称 3.
	 * findProdOffByCond(String prod_offer_id) 参数需要根据实际情况修改 4.
	 * 不需要的方法，可以根据实际情况进行裁剪 5. 此段啰嗦话，完成后替换工作后，请删除！
	 */

	public boolean insertProdOff(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		// 到时可能要设定一些字段的默认值
		Map ProdOff = (Map) param.get("ProdOff");

		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		String prodOffId = seqDao.getNextSequence("SEQ_PROD_OFFER_ID");
		ProdOff.put("prod_offer_id", prodOffId);

		ProdOffDAO dao = new ProdOffDAO();
		boolean result = dao.insert(ProdOff);
		return result;
	}

	public boolean updateProdOff(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map ProdOff = (Map) param.get("ProdOff");
		String keyStr = "prod_offer_id";
		Map keyCondMap = Const.getMapForTargetStr(ProdOff, keyStr);
		ProdOffDAO dao = new ProdOffDAO();
		boolean result = dao.updateByKey(ProdOff, keyCondMap);

		return result;
	}

	public PageModel searchProdOffData(DynamicDict dto) throws Exception {
		// 条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "offer_nbr")) {
			// whereCond.append(" and offer_nbr = ? ");
			// para.add(Const.getStrValue(param , "offer_nbr")) ;
			String offerNbr = Const.getStrValue(param, "offer_nbr");
			whereCond.append(" and offer_nbr like'%" + offerNbr + "%'");
		}
		if (Const.containStrValue(param, "offer_code")) {
			// whereCond.append(" and offer_nbr = ? ");
			// para.add(Const.getStrValue(param , "offer_nbr")) ;
			String offer_code = Const.getStrValue(param, "offer_code");
			whereCond.append(" and offer_code like'%" + offer_code + "%'");
		}
		if (Const.containStrValue(param, "prod_offer_name")) {
			// whereCond.append(" and prod_offer_name = ? ");
			// para.add(Const.getStrValue(param , "prod_offer_name")) ;
			String name = Const.getStrValue(param, "prod_offer_name");
			whereCond.append(" and prod_offer_name like'%" + name + "%'");
		}
		if (Const.containStrValue(param, "fee_set_flag")) {
			String offer_flag = Const.getStrValue(param, "fee_set_flag");
			whereCond.append(" and fee_set_flag = " + offer_flag);
		}
		if (Const.containStrValue(param, "prod_offer_sub_type")) {
			String offer_sub_flag = Const.getStrValue(param,
					"prod_offer_sub_type");
			whereCond.append(" and prod_offer_sub_type = " + offer_sub_flag);
		}
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// 调用DAO代码
		ProdOffDAO dao = new ProdOffDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * 新增销售品关系时，点击Z销售品时Z销售品的选择范围
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProdOff(DynamicDict dto) throws Exception {
		// 条件处理
		Map param = Const.getParam(dto);
		List para = new ArrayList();
		String prodOffId = Const.getStrValue(param, "prod_off_id");
		StringBuffer sb = new StringBuffer();

		if (Const.containStrValue(param, "offer_nbr")) {
			String offerNbr = Const.getStrValue(param, "offer_nbr");
			sb.append(" and offer_nbr like'%" + offerNbr + "%'");
		}
		if (Const.containStrValue(param, "prod_offer_name")) {
			String name = Const.getStrValue(param, "prod_offer_name");
			sb.append(" and prod_offer_name like'%" + name + "%'");
		}
		if(null != prodOffId && !"".equals(prodOffId)){
			sb.append(" and prod_offer_id not in (select r.offer_z_id from prod_offer_rel r where  r.offer_a_id ="+prodOffId+")");
			sb.append(" and prod_offer_id <> "+prodOffId);
		}
		if(Const.containStrValue(param, "state")){
			String state=Const.getStrValue(param, "state");
			sb.append(" and state = " + state);
		}
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		ProdOffDAO dao = new ProdOffDAO();
		PageModel result = dao.searchByCond(sb.toString(), para, pageIndex,
				pageSize);

		return result;
	}

	/**
	 * 销售品中的tab，销售品构成中调用到(只查纯增值产品)
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchPureProduct(DynamicDict dto) throws Exception {
		// 条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		String prodOffId = Const.getStrValue(param, "prod_off_id");
		String prodProviderId = Const.getStrValue(param, "prod_provider_id");
		String prodNbr = Const.getStrValue(param, "product_nbr");
		String orderHost = Const.getStrValue(param, "order_host");
		List para = new ArrayList();
		//配置销售品构成时，仅可以选择纯增值产品
		whereCond.append(" and p.prod_func_type ='1'");
		if(null != prodOffId && !"".equals(prodOffId)){
			whereCond.append(" and p.product_id not in (select r.pro_product_id from prod_offer_detail_role a, role_prod_rela b, product_relation r where a.prod_offer_role_cd = b.prod_offer_role_cd and a.prod_offer_id = " + prodOffId + " and r.product_id = b.product_id) ");
			whereCond.append(" and p.product_id not in (select b.product_id from prod_offer_detail_role a, role_prod_rela b where a.prod_offer_role_cd = b.prod_offer_role_cd and a.prod_offer_id = " + prodOffId + ") ");
		}
		if (Const.containStrValue(param, "product_name")) {
			String name = Const.getStrValue(param, "product_name");
			whereCond.append(" and p.product_name like'%" + name + "%'");
		}
		if (prodProviderId != null && !"".equals(prodProviderId.trim())) {
			whereCond.append(" and p.product_provider_id = " + prodProviderId);
		}
		if (prodNbr != null && !"".equals(prodNbr.trim())) {
			whereCond.append(" and p.product_nbr = '" + prodNbr + "'");
		}
		if (orderHost != null && !"".equals(orderHost.trim())) {
			whereCond.append(" and p.order_host = '" + orderHost + "'");
		}
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// 调用DAO代码
		ProductDAO dao = new ProductDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}
	
	/**
	 * 销售品中的tab，销售品构成中调用到
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProduct(DynamicDict dto) throws Exception {
		// 条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		String prodOffId = Const.getStrValue(param, "prod_off_id");
		String prodProviderId = Const.getStrValue(param, "prod_provider_id");
		String prodNbr = Const.getStrValue(param, "product_nbr");
		String orderHost = Const.getStrValue(param, "order_host");
		List para = new ArrayList();

		if(null != prodOffId && !"".equals(prodOffId)){
			whereCond.append(" and p.product_id not in (select r.pro_product_id from prod_offer_detail_role a, role_prod_rela b, product_relation r where a.prod_offer_role_cd = b.prod_offer_role_cd and a.prod_offer_id = " + prodOffId + " and r.product_id = b.product_id) ");
			whereCond.append(" and p.product_id not in (select b.product_id from prod_offer_detail_role a, role_prod_rela b where a.prod_offer_role_cd = b.prod_offer_role_cd and a.prod_offer_id = " + prodOffId + ") ");
		}
		if (Const.containStrValue(param, "product_name")) {
			String name = Const.getStrValue(param, "product_name");
			whereCond.append(" and p.product_name like'%" + name + "%'");
		}
		if (prodProviderId != null && !"".equals(prodProviderId.trim())) {
			whereCond.append(" and p.product_provider_id = " + prodProviderId);
		}
		if (prodNbr != null && !"".equals(prodNbr.trim())) {
			whereCond.append(" and p.product_nbr = '" + prodNbr + "'");
		}
		if (orderHost != null && !"".equals(orderHost.trim())) {
			whereCond.append(" and p.order_host = '" + orderHost + "'");
		}
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// 调用DAO代码
		ProductDAO dao = new ProductDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProdOffById(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProdOffDAO dao = new ProdOffDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findProdOffByCond(DynamicDict dto) throws Exception {
		// 条件处理
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// 组织调用DAO代码参数、
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		// 调用DAO代码
		ProdOffDAO dao = new ProdOffDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteProdOffById(DynamicDict dto) throws Exception {
		Map keyCondMap = Const.getParam(dto);
		String prod_offer_id = (String) keyCondMap.get("prod_offer_id");
		ProdOffDAO dao = new ProdOffDAO();
		dao.cancelProdOfferById(prod_offer_id);
		return true;
	}
	public boolean validateCodeRepeat(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map param = Const.getParam(dto);
		ProdOffDAO dao = new ProdOffDAO();
		StringBuffer bf = new StringBuffer();
		List list = new ArrayList();
		
		if (Const.containStrValue(param, "code")) {
			String offerNbr = Const.getStrValue(param, "code");
			bf.append(" and offer_nbr = ?");
			list.add(offerNbr);
		}
		if (Const.containStrValue(param, "prod_offer_id")) {
			String prod_offer_id = Const.getStrValue(param, "prod_offer_id");
			bf.append(" and prod_offer_id != ? ");
			list.add(prod_offer_id);
		}
		List result = dao.findByCond(bf.toString(), list);
		if(result.size()>0)
			return true;
		return false;
	}
	public Map findProdOffByOfferNbr(String offerNbr) throws Exception {
		// 条件处理
		String filterStr = "";
		// 调用DAO代码
		ProdOffDAO dao = new ProdOffDAO();
		String where = " and offer_nbr=?";
		List whereCondParams = new ArrayList();
		whereCondParams.add(offerNbr);
		List result = dao.findBySql(dao.getSelectSQL()+where, whereCondParams);
		if(null != result && result.size()>0){
			return (Map) result.get(0);
		}
		return null;
	}

}
