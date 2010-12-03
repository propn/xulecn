package com.ztesoft.vsop.product.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.product.dao.prodItemElement.ProductCatgItemElementDAO ;

public class ProductCatgItemElementBO extends DictAction  {
	/**
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchProductCatgItemElementData �ķ����Ĳ�������
 		3. findProductCatgItemElementByCond(String catalog_item_id,String element_type,String element_id) ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertProductCatgItemElement(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductCatgItemElement = (Map) param.get("ProductCatgItemElement") ;
		
		ProductCatgItemElementDAO dao = new ProductCatgItemElementDAO();
		boolean result = dao.insert(ProductCatgItemElement) ;
		return result ;
	}

	
	public boolean updateProductCatgItemElement(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductCatgItemElement = (Map) param.get("ProductCatgItemElement") ;
		String keyStr = "catalog_item_id,element_type,element_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProductCatgItemElement,  keyStr) ;
		ProductCatgItemElementDAO dao = new ProductCatgItemElementDAO();
		boolean result = dao.updateByKey( ProductCatgItemElement, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchProductCatgItemElementData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "param1")){
			whereCond.append(" and param1 = ? ");
			para.add(Const.getStrValue(param , "param1")) ;
		}
		if(Const.containStrValue(param , "param2")){
			whereCond.append(" and param2 = ? ");
			para.add(Const.getStrValue(param , "param2")) ;
		}
		if(Const.containStrValue(param , "param3")){
			whereCond.append(" and param3 = ? ");
			para.add(Const.getStrValue(param , "param3")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		ProductCatgItemElementDAO dao = new ProductCatgItemElementDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getProductCatgItemElementById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		ProductCatgItemElementDAO dao = new ProductCatgItemElementDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProductCatgItemElementByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		ProductCatgItemElementDAO dao = new ProductCatgItemElementDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProductCatgItemElementById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		Map param = (Map)keyCondMap.get("productCatgItemElement");
		ProductCatgItemElementDAO dao = new ProductCatgItemElementDAO();
		boolean result = dao.deleteByKey( param ) > 0 ;
		
		return result ;
	}
	
	/**
	 * ��������
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public int batchInsert(DynamicDict dto ) throws Exception{
		int succ = 0;//�ɹ�������������
		ProductCatgItemElementDAO pciDAO = new ProductCatgItemElementDAO();
		Map keyCondMap = Const.getParam(dto)  ;
		List list = (List)keyCondMap.get("selList");
		List condParam = new ArrayList();
		for(int i =0 ; i<list.size();i++){
			Map map = (Map) list.get(i);
			List param = new ArrayList();
			param.add(map.get("catalog_item_id"));
			param.add(map.get("element_type"));
			param.add(map.get("element_id"));//element_id
			//�������ж�ѡ��ĸü�¼�Ƿ��Ѿ��������ݿ�����
			if(pciDAO.findByPrimaryKey(map).isEmpty())
			{
				succ++;
				condParam.add(param);
			}
		}
		
		String batchInsertSQL = "insert into product_catalog_item_element(catalog_item_id,element_type,element_id) values(?,?,?)";
		String dbName = JNDINames.VSOP_DATASOURCE ;
		Base.batchUpdate(dbName , batchInsertSQL, condParam,  Const.UN_JUDGE_ERROR,  Const.UN_JUDGE_ERROR, null) ;
		return succ;
	}
}
