package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.product.dao.ProdOffRelDAO;

public class ProdOffRelBO extends DictAction  {
	/**
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchProdOffRelData �ķ����Ĳ�������
 		3. findProdOffRelByCond(String prod_offer_rel_id) ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertProdOffRel(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffRel = (Map) param.get("ProdOffRel") ;
		
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.insert(ProdOffRel) ;
		return result ;
	}

	
	public boolean updateProdOffRel(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffRel = (Map) param.get("ProdOffRel") ;
		String keyStr = "prod_offer_rel_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProdOffRel,  keyStr) ;
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.updateByKey( ProdOffRel, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchProdOffRelData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if(Const.containStrValue(param , "offer_a_id")){
			whereCond.append(" and offer_a_id = ? ");
			para.add(Const.getStrValue(param , "offer_a_id"));
		}
		if(Const.containStrValue(param , "offer_z_id")){
			whereCond.append(" or offer_z_id = ? ");
			para.add(Const.getStrValue(param , "offer_z_id"));
		}
		
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		
		
		//����DAO����
		ProdOffRelDAO dao = new ProdOffRelDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getProdOffRelById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		ProdOffRelDAO dao = new ProdOffRelDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProdOffRelByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		ProdOffRelDAO dao = new ProdOffRelDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffRelById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	public boolean deleteProdOffRel(DynamicDict dto ) throws Exception {
		//����DAO����
		Map map = Const.getParam(dto);
		
		List list = new ArrayList();
		list.add(map.get("offer_a_id"));
		list.add(map.get("relation_type_id"));
		list.add(map.get("offer_z_id"));
		
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.delete("", list) > 0;
		
		return result ;
	}
	
	public String getProdOffNameByKey(DynamicDict dto ) throws Exception {
		String productName = "";
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("prod_offer_id"));
		ProdOffRelDAO dao = new ProdOffRelDAO();
		List l = dao.findBySql(dao.getSQLForProdOffName(), list);
		if (null != l && !l.isEmpty()) {
			productName = ((HashMap) l.get(0)).get("prod_offer_name").toString();
		}
		return productName;
	}
}
