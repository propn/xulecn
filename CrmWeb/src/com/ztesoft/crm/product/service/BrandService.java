package com.ztesoft.crm.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.dict.DataTranslate;


public class BrandService  {


	public String getParentBrand(HashMap temp) throws Exception {
		String result = DataTranslate._String(ServiceManager.callJavaBeanService("BrandBO","getParentBrand",temp));
		return result;
	}
	public List getBrandItem(HashMap temp) throws Exception {
		return DataTranslate._List(ServiceManager.callJavaBeanService("BrandBO","getBrandItem",temp));
	}
	public Map getBrandById(String brandId) throws Exception {
		Map temp = new HashMap();
		temp.put("brand_id",brandId);
		return DataTranslate._Map(ServiceManager.callJavaBeanService("BrandBO","getBrandById",temp));
	}

	public boolean insertBrand(HashMap temp) throws Exception {
		return DataTranslate._boolean(ServiceManager.callJavaBeanService("BrandBO","insertBrand",temp));
	}
	public boolean updateBrand(HashMap temp) throws Exception {
		return DataTranslate._boolean(ServiceManager.callJavaBeanService("BrandBO","updateBrand",temp));
	}
	public boolean deleteBrandById(HashMap temp) throws Exception {
		return DataTranslate._boolean(ServiceManager.callJavaBeanService("BrandBO","deleteBrandById",temp));
	}
}
