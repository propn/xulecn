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
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchProdOffData �ķ����Ĳ�������
 		3. findProdOffByCond(String prod_offer_id) ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertProdOff(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		//��ʱ����Ҫ�趨һЩ�ֶε�Ĭ��ֵ
		Map ProdOff = (Map) param.get("ProdOff") ;
		
		//ProdOffDAO dao = new ProdOffDAO();
		SiProdOffDAO dao = new SiProdOffDAO();
		boolean result = dao.insert(ProdOff) ;
		return result ;
	}

	public String insertProdOffSelf(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto) ;
		//��ʱ����Ҫ�趨һЩ�ֶε�Ĭ��ֵ
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
		//��������
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
		
		
		//����DAO����
		//ProdOffDAO dao = new ProdOffDAO();
		SiProdOffDAO dao = new SiProdOffDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ��������Ʒ��ϵʱ�����Z����ƷʱZ����Ʒ��ѡ��Χ
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProdOff(DynamicDict dto) throws Exception {
		//��������
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
	 * ����Ʒ�е�tab������Ʒ�����е��õ�
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProduct(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		//����DAO����
		ProductDAO dao = new ProductDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getProdOffById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		//ProdOffDAO dao = new ProdOffDAO();
		SiProdOffDAO dao = new SiProdOffDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProdOffByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		//ProdOffDAO dao = new ProdOffDAO();
		SiProdOffDAO dao = new SiProdOffDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		//ProdOffDAO dao = new ProdOffDAO();
		SiProdOffDAO dao = new SiProdOffDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
