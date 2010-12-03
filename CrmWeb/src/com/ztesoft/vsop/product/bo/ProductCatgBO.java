package com.ztesoft.vsop.product.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.crm.business.common.utils.SeqUtil;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.oaas.dao.organization.OrganizationDAO;
import com.ztesoft.oaas.dao.organization.OrganizationDAOFactory;
import com.ztesoft.oaas.vo.ProductCatg;
import com.ztesoft.oaas.vo.ProductCatgItem;
import com.ztesoft.vsop.product.dao.prodCatg.ProductCatgDAO;
import com.ztesoft.vsop.product.dao.prodCatg.ProductCatgDAOFactory;
import com.ztesoft.vsop.product.vo.ProductType;

public class ProductCatgBO extends DictAction {
	/**
	 * 需要替换位置 说明 ： 1. 错误参数处理,根据实际情况修改 2. searchProductCatgData 改方法的参数名称 3.
	 * findProductCatgByCond(String catalog_id) 参数需要根据实际情况修改 4.
	 * 不需要的方法，可以根据实际情况进行裁剪 5. 此段啰嗦话，完成后替换工作后，请删除！
	 */

	/*
	 * 加载产品根目录树
	 */
	public String getRootProductCatgList(DynamicDict dto) throws Exception {
		ProductCatgDAO daoProductCatg = ProductCatgDAOFactory
				.getProductCatgDAO();
		final String newSQL = "select catalog_id,catalog_type,catalog_name,'-1' as catalog_item_id,'' as parent_catalog_item_id from PRODUCT_CATALOG where 1=1 ";
		ArrayList treeList = ptMap2VO((ArrayList) daoProductCatg
				.findBySql(newSQL));
		String result = XMLSegBuilder.toXmlItems(treeList);
		return result;
	}

	/*
	 * 加载子目录
	 */
	public String getTelecomProductCatgListByParentId(DynamicDict dto)
			throws Exception {
		// catalogId,parentCatalogItemId
		Map param = Const.getParam(dto);
		String catalogId = (String) param.get("catalogId");
		String catalogItemId = (String) param.get("catalogItemId");
		StringBuffer newSQL = new StringBuffer("");
		
		newSQL.append("select catalog_item_id,catalog_item_name , catalog_id, catalog_item_type as catalog_type,parent_catalog_item_id");
		newSQL.append(" from product_catalog_item where 1=1");
		if(catalogId!=null && !catalogId.equals("")){
			newSQL.append(" and catalog_id='" + catalogId + "' ");
		}
		if(catalogItemId!=null && !catalogItemId.equals("")){
			newSQL.append(" and parent_catalog_item_id='" + catalogItemId + "'");
		}
		// 查找子目录
		/*final String newSQL = "select catalog_item_id,catalog_item_name , catalog_id, catalog_item_type as catalog_type,parent_catalog_item_id "
				+ " from product_catalog_item"
				+ " where catalog_id='"
				+ catalogId
				+ "' and parent_catalog_item_id='"
				+ parentCatalogItemId + "'";*/
		ProductCatgDAO daoProductCatg = ProductCatgDAOFactory
				.getProductCatgDAO();
		ArrayList treeList = pciMap2VO((ArrayList) daoProductCatg
				.findBySql(newSQL.toString()));
		String result = XMLSegBuilder.toXmlItems(treeList);
		return result;

	}

	// 获得productcatg详细信息
	public Map getDetailProductCatg(DynamicDict dto) throws Exception {
		String newSQL = "";
		// 调用DAO代码
		Map param = Const.getParam(dto);
		String catalog_id = (String) param.get("catalog_id");
		String parent_catalog_item_id = (String) param
				.get("parent_catalog_item_id");
		//子目录ID
		String catalog_item_id = (String) param.get("catalog_item_id");// 如果catalog_item_id的值是-1的话，说明点击的是根目录
		
		if ("-1".equals(catalog_item_id)) {
			// 显示根目录详细信息
			/*
			 * SELECT catalog_name as catalog_item_name,'' as
			 * catalog_name,catalog_desc as catalog_item_desc FROM
			 * product_catalog where catalog_id='100000';
			 */
			newSQL = "SELECT catalog_name as catalog_item_name,'' as catalog_name,catalog_desc as catalog_item_desc,"
					+ " catalog_id"//主键，add和upadate要用到
					+ " FROM product_catalog where catalog_id='"
					+ catalog_id + "'";
		} else if("-1".equals(parent_catalog_item_id)){
			newSQL = "SELECT catalog_item_name,(select catalog_name from product_catalog where catalog_id='"
				+ catalog_id
				+ "') as catalog_name,catalog_item_desc ,catalog_item_id"//主键，add和upadate要用到
				+ " FROM PRODUCT_CATALOG_ITEM "
				+ " WHERE catalog_item_id='"
				+ catalog_item_id
				+ "' and parent_catalog_item_id='"
				+ parent_catalog_item_id + "'";//parent_catalog_item_id
			
			
		}else {
			newSQL = "SELECT catalog_item_name,(select catalog_item_name from product_catalog_item where catalog_item_id='"
				+ parent_catalog_item_id
				+ "') as catalog_name,catalog_item_desc ,catalog_item_id"//主键，add和upadate要用到
				+ " FROM PRODUCT_CATALOG_ITEM "
				+ " WHERE catalog_item_id='"
				+ catalog_item_id
				+ "' and parent_catalog_item_id='"
				+ parent_catalog_item_id + "'";//parent_catalog_item_id
		}
		
		ProductCatgDAO daoProductCatg = ProductCatgDAOFactory
				.getProductCatgDAO();
		ArrayList list = (ArrayList) daoProductCatg.findBySql(newSQL);
		if (list != null && !list.isEmpty()) {
			return (Map) list.get(0);
		}
		return null;
	}

	private ArrayList ptMap2VO(ArrayList treeList) {
		if (treeList == null || treeList.isEmpty())
			return null;

		ArrayList result = null;
		for (int i = 0; i < treeList.size(); i++) {
			Object o = treeList.get(i);
			if (o instanceof ProductCatg)
				return treeList;

			if (result == null) {
				result = new ArrayList();
			}

			HashMap m = (HashMap) o;
			ProductCatg type = new ProductCatg();
			type.loadFromHashMap(m);
			result.add(type);
		}
		return result;
	}

	private ArrayList pciMap2VO(ArrayList treeList) {
		if (treeList == null || treeList.isEmpty())
			return null;

		ArrayList result = null;
		for (int i = 0; i < treeList.size(); i++) {
			Object o = treeList.get(i);
			if (o instanceof ProductCatgItem)
				return treeList;

			if (result == null) {
				result = new ArrayList();
			}

			HashMap m = (HashMap) o;
			ProductCatgItem type = new ProductCatgItem();
			type.loadFromHashMap(m);
			result.add(type);
		}
		return result;
	}

	public PageModel searchProductCatgItemData(DynamicDict dto)
			throws Exception {
		String newSQL = "";
		Map param = Const.getParam(dto);
		String catalog_item_id = (String) param.get("catalog_item_id");
		String catalog_type = (String) param.get("catalog_type");
		if (catalog_type == null || catalog_type.equals("")) {
			return null;
		}
		if ("12A".equals(catalog_type)) {
			newSQL = "select a.element_type,a.element_id,b.product_name,b.prod_offer_id"
					+ " from PRODUCT_CATALOG_ITEM_ELEMENT a,product b"
					+ " where a.catalog_item_id='"
					+ catalog_item_id
					+ " ' and a.element_id=b.product_id";
		} else if ("12C".equals(catalog_type)) {
			newSQL = "select a.element_type ,a.element_id,b.prod_offer_name as product_name,b.prod_offer_sub_type as product_offer_id"
					+ " from PRODUCT_CATALOG_ITEM_ELEMENT a,prod_offer b "
					+ " where a.catalog_item_id='"
					+ catalog_item_id
					+ " ' and a.element_id=b.prod_offer_id";
		}
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		ProductCatgDAO dao = new ProductCatgDAO();
		PageModel result = dao.searchBySql(newSQL, null, pageIndex, pageSize);
		return result;
	}

	public boolean insertProductCatg(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map ProductCatg = (Map) param.get("ProductCatg");
		ProductCatgDAO dao = new ProductCatgDAO();
		boolean result = dao.insert(ProductCatg);
		return result;
	}

	public boolean updateProductCatg(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map ProductCatg = (Map) param.get("ProductCatg");
		String keyStr = "catalog_id";
		Map keyCondMap = Const.getMapForTargetStr(ProductCatg, keyStr);
		ProductCatgDAO dao = new ProductCatgDAO();
		boolean result = dao.updateByKey(ProductCatg, keyCondMap);
		return result;
	}

	public PageModel searchProductCatgData(DynamicDict dto) throws Exception {
		// 条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "param1")) {
			whereCond.append(" and param1 = ? ");
			para.add(Const.getStrValue(param, "param1"));
		}
		if (Const.containStrValue(param, "param2")) {
			whereCond.append(" and param2 = ? ");
			para.add(Const.getStrValue(param, "param2"));
		}
		if (Const.containStrValue(param, "param3")) {
			whereCond.append(" and param3 = ? ");
			para.add(Const.getStrValue(param, "param3"));
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// 调用DAO代码
		ProductCatgDAO dao = new ProductCatgDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductCatgById(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProductCatgDAO dao = new ProductCatgDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findProductCatgByCond(DynamicDict dto) throws Exception {
		// 条件处理
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// 组织调用DAO代码参数、
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		// 调用DAO代码
		ProductCatgDAO dao = new ProductCatgDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteProductCatgById(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProductCatgDAO dao = new ProductCatgDAO();
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		return result;
	}
}
