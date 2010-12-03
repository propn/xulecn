package com.ztesoft.vsop.product.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.product.dao.ProdOffDAO;
import com.ztesoft.vsop.product.dao.ProductDAO;

public class ProductTraManager extends DictAction {
	private static final String ADD = "1";

	private static final String UPDATE = "2";

	private static final String DELETE = "3";

	private static final String REL_ADD = "0";

	private static final String REL_UPDATE = "2";

	private static final String REL_DELETE = "1";

	private static final String ResultCode_SUCESS = "0";
	private static final String ResultCode_FAIL = "-1";

	private String getSequence(String sequenceCode) {
		SequenceManageDAOImpl sequenceManageDAO = new SequenceManageDAOImpl();
		return sequenceManageDAO.getNextSequenceWithDB(sequenceCode,
				JNDINames.VSOP_DATASOURCE);
	}

	private boolean insertProductTra(DynamicDict dto, boolean createSeq)
			throws Exception {
		Map param = Const.getParam(dto);
		Map Product = (Map) param.get("Product");
		ProductDAO dao = new ProductDAO();

		if (createSeq)
			Product.put("product_id", getSequence("seq_product_id"));
		Product.put("product_state_cd", "0");// 正常状态
		Product.put("create_date", DAOUtils.getShortFormatedDate());
		Product.put("state_date", DAOUtils.getShortFormatedDate());
		//对传统产品的provider_id默认为-1
		Product.put("product_provider_id", "-1");

		boolean result = dao.insertProductTra(Product);

		return result;
	}

	/**
	 * 传统产品查询
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductTraData(DynamicDict dto) throws Exception {
		// 条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "product_name")) {
			whereCond.append(" and product_name like ? ");
			para.add("%" + Const.getStrValue(param, "product_name") + "%");
		}
		if (Const.containStrValue(param, "product_code")) {
			whereCond.append(" and product_code like ? ");
			para.add("%" + Const.getStrValue(param, "product_code") + "%");
		}
		if (Const.containStrValue(param, "prod_func_type")) {
			whereCond.append(" and prod_func_type = ? ");
			para.add(Const.getStrValue(param, "prod_func_type"));
		} else {
			whereCond.append(" and prod_func_type in (?,?) ");
			para.add("0");
			para.add("2");			
		}
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		// 调用DAO代码
		ProductDAO dao = new ProductDAO();
		PageModel result = dao.searchProductTraByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * 更新
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean updateProductTra(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map Product = (Map) param.get("Product");
		ProductDAO dao = new ProductDAO();
		String product_id = Const.getStrValue(Product, "product_id");
		Product.put("state_date", DAOUtils.getShortFormatedDate());
		//对传统产品的provider_id默认为-1
		Product.put("product_provider_id", "-1");
		String keyStr = "product_id";
		Map keyCondMap = Const.getMapForTargetStr(Product, keyStr);
		boolean result = dao.updateProductTraByKey(Product, keyCondMap);
		return result;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductTraById(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProductDAO dao = new ProductDAO();
		Map result = dao.findProductTraByPrimaryKey(keyCondMap);

		return result;
	}

	/**
	 * 新增
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean insertProductTra(DynamicDict dto) throws Exception {
		return insertProductTra(dto, true);
	}

	public boolean deleteProductTraById(DynamicDict dto) throws Exception {
		Map keyCondMap = Const.getParam(dto);
		String product_id = (String) keyCondMap.get("product_id");
		String updateStr = "update PRODUCT set PRODUCT_STATE_CD=4, STATE_DATE=sysdate where PRODUCT_ID="
				+ product_id;
		
		//支持informix
		String updateStr_informix = "update PRODUCT set PRODUCT_STATE_CD=4, STATE_DATE=current where PRODUCT_ID="
			+ product_id;
	
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			
		  updateStr=updateStr_informix;
		}
		
		
		ProdOffDAO dao = new ProdOffDAO();
		boolean result = dao.updateBySQL(updateStr, new HashMap());
		return result;
	}
}
