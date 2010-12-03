package com.ztesoft.crm.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;

public class ProdOffService {
	/**
	 * ��Ҫ�滻λ�� ˵�� �� 1. ServiceList.MyService �滻ΪServiceListע��ķ��� 2.
	 * searchProdOffData �ķ����Ĳ������� 3. findProdOffByCond(String prod_offer_id)
	 * ������Ҫ����ʵ������޸� 4. ����Ҫ�ķ��������Ը���ʵ��������вü� 5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */

	public boolean insertProdOff(HashMap ProdOff) throws Exception {
		Map param = new HashMap();
		param.put("ProdOff", ProdOff);

		boolean result = DataTranslate._boolean(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffBO, "insertProdOff",
						param));
		return result;
	}

	public boolean updateProdOff(HashMap ProdOff) throws Exception {
		Map param = new HashMap();
		param.put("ProdOff", ProdOff);
		boolean result = DataTranslate._boolean(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffBO, "updateProdOff",
						param));

		return result;
	}

	public PageModel searchProdOffData(String offer_nbr, String offer_code,
			String prod_offer_name, String prod_offer_sub_type, int pageIndex,
			int pageSize) throws Exception {

		Map param = new HashMap();
		param.put("offer_nbr", offer_nbr);
		param.put("offer_code", offer_code);
		param.put("prod_offer_name", prod_offer_name);
		// param.put("iParam3", iParam3) ;
		// param.put("fee_set_flag", fee_set_flag.trim());
		param.put("prod_offer_sub_type", prod_offer_sub_type.trim());
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffBO,
						"searchProdOffData", param));

		return result;
	}

	/**
	 * ��������Ʒ��ϵʱ�����Z����ƷʱZ����Ʒ��ѡ��Χ
	 * 
	 * @param prod_off_id
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProdOff(String offer_nbr, String prod_offer_name,
			String prod_off_id, String state, int pageIndex, int pageSize)
			throws Exception {

		Map param = new HashMap();
		param.put("offer_nbr", offer_nbr);
		param.put("prod_offer_name", prod_offer_name);
		param.put("prod_off_id", prod_off_id);
		param.put("state", state);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffBO, "searchProdOff",
						param));

		return result;
	}

	/**
	 * ����Ʒ�е�tab������Ʒ�����е��õ�
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProduct(String prod_provider_id,
								   String product_nbr,
								   String order_host,
								   String product_name, 
								   String prod_off_id,
			int pageIndex, int pageSize) throws Exception {

		Map param = new HashMap();
		param.put("prod_provider_id", prod_provider_id);
		param.put("product_nbr", product_nbr);
		param.put("order_host", order_host);
		param.put("product_name", product_name);
		param.put("prod_off_id", prod_off_id);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffBO, "searchProduct",
						param));

		return result;
	}
	
	/**
	 * ����Ʒ�е�tab������Ʒ�����е��õ�,ֻ�鴿��ֵ��Ʒ
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchPureProduct(String prod_provider_id,
								   String product_nbr,
								   String order_host,
								   String product_name, 
								   String prod_off_id,
			int pageIndex, int pageSize) throws Exception {

		Map param = new HashMap();
		param.put("prod_provider_id", prod_provider_id);
		param.put("product_nbr", product_nbr);
		param.put("order_host", order_host);
		param.put("product_name", product_name);
		param.put("prod_off_id", prod_off_id);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffBO, "searchPureProduct",
						param));

		return result;
	}

	public Map getProdOffById(String prod_offer_id) throws Exception {
		Map param = getProdOffKeyMap(prod_offer_id);

		Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
				ServiceList.ProdOffBO, "getProdOffById", param));

		return result;
	}

	public List findProdOffByCond(String prod_offer_id) throws Exception {
		Map param = getProdOffKeyMap(prod_offer_id);

		List result = DataTranslate._List(ServiceManager.callJavaBeanService(
				ServiceList.ProdOffBO, "findProdOffByCond", param));

		return result;
	}

	public boolean deleteProdOffById(String prod_offer_id) throws Exception {
		Map param = getProdOffKeyMap(prod_offer_id);

		boolean result = DataTranslate._boolean(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffBO,
						"deleteProdOffById", param));

		return result;
	}

	private Map getProdOffKeyMap(String prod_offer_id) {
		Map param = new HashMap();

		param.put("prod_offer_id", prod_offer_id);

		return param;
	}

	public boolean validateCodeRepeat(String code, String prod_offer_id)
			throws Exception {
		Map param = getProdOffKeyMap(prod_offer_id);
		param.put("code", code);
		boolean result = DataTranslate._boolean(ServiceManager
				.callJavaBeanService(ServiceList.ProdOffBO,
						"validateCodeRepeat", param));

		return result;
	}
}
