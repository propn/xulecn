package com.ztesoft.crm.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;

public class ProdOffDetaRoleService {
	/**
	 * 销售品和产品关系表.<ul>
	 * 	<li>先插入记录到ROLE_PROD_RELA;
	 * 	<li>再插入记录到PROD_OFFER_DETAIL_ROLE
	 * 
	 * @param ProdOffDetaRole
	 * @return
	 * @throws Exception
	 */
	public boolean insertProdOffDetaRole(HashMap ProdOffDetaRole)
			throws Exception {
		Map param = new HashMap();
		param.put("ProdOffDetaRole", ProdOffDetaRole);

		boolean result = DataTranslate._boolean(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffDetaRoleBO,
						"insertProdOffDetaRole", param));
		return result;
	}

	public boolean updateProdOffDetaRole(HashMap ProdOffDetaRole)
			throws Exception {
		Map param = new HashMap();
		param.put("ProdOffDetaRole", ProdOffDetaRole);
		boolean result = DataTranslate._boolean(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffDetaRoleBO,
						"updateProdOffDetaRole", param));

		return result;
	}

	public PageModel searchProdOffDetaRoleData(String prod_offer_id,
			int pageIndex, int pageSize) throws Exception {

		Map param = new HashMap();
		param.put("prod_offer_id", prod_offer_id);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffDetaRoleBO,
						"searchProdOffDetaRoleData", param));

		return result;
	}

	public PageModel searchProdOffDetaRoleDataPro(String prod_offer_id,
			int pageIndex, int pageSize) throws Exception {
		Map param = new HashMap();
		param.put("prod_offer_id", prod_offer_id);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffDetaRoleBO,
						"searchProdOffDetaRoleDataPro", param));

		return result;
	}

	public Map getProdOffDetaRoleById(String prod_offer_role_cd)
			throws Exception {
		Map param = getProdOffDetaRoleKeyMap(prod_offer_role_cd);

		Map result = DataTranslate
				._Map(ServiceManager.callJavaBeanService(
						ServiceList.ProdOffDetaRoleBO,
						"getProdOffDetaRoleById", param));

		return result;
	}

	public List findProdOffDetaRoleByCond(String prod_offer_role_cd)
			throws Exception {
		Map param = getProdOffDetaRoleKeyMap(prod_offer_role_cd);

		List result = DataTranslate._List(ServiceManager.callJavaBeanService(
				ServiceList.ProdOffDetaRoleBO, "findProdOffDetaRoleByCond",
				param));

		return result;
	}

	public boolean deleteProdOffDetaRoleById(String prod_offer_role_cd, String product_id)
			throws Exception {
		Map param = getProdOffDetaRoleKeyMap(prod_offer_role_cd);
		param.put("product_id", product_id);

		boolean result = DataTranslate._boolean(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffDetaRoleBO,
						"deleteProdOffDetaRoleById", param));

		return result;
	}

	private Map getProdOffDetaRoleKeyMap(String prod_offer_role_cd) {
		Map param = new HashMap();

		param.put("prod_offer_role_cd", prod_offer_role_cd);

		return param;
	}
}
