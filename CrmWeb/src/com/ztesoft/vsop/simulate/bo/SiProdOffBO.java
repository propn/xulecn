package com.ztesoft.vsop.simulate.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.simulate.dao.SiProdOffDAO;
import com.ztesoft.vsop.product.dao.ProductDAO;

public class SiProdOffBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchProdOffData 改方法的参数名称
 		3. findProdOffByCond(String prod_offer_id) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertProdOff(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		//到时可能要设定一些字段的默认值
		Map ProdOff = (Map) param.get("ProdOff") ;
		
		//ProdOffDAO dao = new ProdOffDAO();
		SiProdOffDAO dao = new SiProdOffDAO();
		boolean result = dao.insert(ProdOff) ;
		return result ;
	}

	public String insertProdOffSelf(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto) ;
		//到时可能要设定一些字段的默认值
		Map ProdOff = (Map) param.get("ProdOff") ;
		
		SiProdOffDAO dao = new SiProdOffDAO();
		String prodOfferId = dao.insertProdOff(ProdOff);
		return prodOfferId ;
	}
	
	public boolean updateProdOff(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOff = (Map) param.get("ProdOff") ;
		String keyStr = "prod_offer_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProdOff,  keyStr) ;
		//ProdOffDAO dao = new ProdOffDAO();
		SiProdOffDAO dao = new SiProdOffDAO();
		boolean result = dao.updateByKey( ProdOff, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchProdOffData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "offer_nbr")){
			//whereCond.append(" and offer_nbr = ? ");
			//para.add(Const.getStrValue(param , "offer_nbr")) ;
			String offerNbr = Const.getStrValue(param, "offer_nbr");
			whereCond.append(" and offer_nbr like'%" + offerNbr + "%'");
		}
		if(Const.containStrValue(param , "prod_offer_name")){
			//whereCond.append(" and prod_offer_name = ? ");
			//para.add(Const.getStrValue(param , "prod_offer_name")) ;
			String name = Const.getStrValue(param , "prod_offer_name");
			whereCond.append(" and prod_offer_name like'%" + name + "%'");
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		//ProdOffDAO dao = new ProdOffDAO();
		SiProdOffDAO dao = new SiProdOffDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 新增销售品关系时，点击Z销售品时Z销售品的选择范围
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProdOff(DynamicDict dto) throws Exception {
		//条件处理
		Map param = Const.getParam(dto);
		List para = new ArrayList();
		String prodOffId = Const.getStrValue(param , "prod_off_id");
		StringBuffer sb = new StringBuffer();
		
		if(Const.containStrValue(param , "offer_nbr")) {
			String offerNbr = Const.getStrValue(param, "offer_nbr");
			sb.append(" and p.offer_nbr like'%" + offerNbr + "%'");
		}
		if(Const.containStrValue(param , "prod_offer_name")) {
			String name = Const.getStrValue(param , "prod_offer_name");
			sb.append(" and p.prod_offer_name like'%" + name + "%'");
		}
		
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		SiProdOffDAO dao = new SiProdOffDAO();
		PageModel result = dao.searchByCond(prodOffId, sb.toString(), para, pageIndex,  pageSize);
		
		return result ;
	}	
	
	/**
	 * 销售品中的tab，销售品构成中调用到
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProduct(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		//调用DAO代码
		ProductDAO dao = new ProductDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProdOffById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		//ProdOffDAO dao = new ProdOffDAO();
		SiProdOffDAO dao = new SiProdOffDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProdOffByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		//ProdOffDAO dao = new ProdOffDAO();
		SiProdOffDAO dao = new SiProdOffDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		//ProdOffDAO dao = new ProdOffDAO();
		SiProdOffDAO dao = new SiProdOffDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
