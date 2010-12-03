package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.powerise.ibss.framework.Base;

import com.ztesoft.crm.product.dao.BrandDAO ;

public class BrandBO extends DictAction  {
	/**
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchBrandData �ķ����Ĳ�������
 		3. findBrandByCond(String brand_id) ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	private SequenceManageDAOImpl  sequenceManageDAO=new SequenceManageDAOImpl();

	public String getParentBrand(DynamicDict dto  ) throws Exception {
		StringBuffer sbXml = new StringBuffer();
		BrandDAO dao = new BrandDAO();
		String sql="select brand_id,brand_name from brand where parent_brand_id =-1";
		List list = dao.findBySql(sql);
		sbXml.append("<items>");
 	    for(int i=0;i<list.size();i++){
 	    	  Map temp = (HashMap)list.get(i);
 	    	  sbXml.append("<item ");
 	    	  sbXml.append(" nodeId = '"+temp.get("brand_id").toString()+"'");
 	    	  sbXml.append(" nodeName = '"+temp.get("brand_name").toString()+"'");
 	    	  sbXml.append(" nodeType = '"+"1"+"'");
 	    	  sbXml.append(">");
 	    	  sbXml.append("</item>");
 	   }
 	   sbXml.append("</items>");
 	  return sbXml.toString();
	}
	public List getBrandItem(DynamicDict dto  ) throws Exception {
		Map temp = Const.getParam(dto) ;
		List param = new ArrayList();
		List reList = new ArrayList();
		param.add(Const.getStrValue(temp,"nodeId"));
		String nodeType = Const.getStrValue(temp,"nodeType");
		if("1".equals(nodeType)){
			BrandDAO dao = new BrandDAO();
			String sql="select brand_id node_id,brand_name node_name,2 node_type from brand where parent_brand_id = ?";
			reList = dao.findBySql(sql,param);
		}
		return reList;
	}
	public boolean insertBrand(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		String brandId=sequenceManageDAO.getNextSequenceWithDB("s_brand_id",JNDINames.PM_DATASOURCE);
   		param.put("brand_id",brandId);
		param.put("state","00A");
		param.put("seq","0");
		Map loginMap = (HashMap)RequestContext.getContext().
		getHttpSession().getAttribute("LoginRtnParamsHashMap");
   		param.put("party_id",Const.getStrValue(loginMap,"vg_depart_id"));//������
   		param.put("party_role_id",Const.getStrValue(loginMap,"vg_oper_id"));//����Ա
   		param.put("oper_region_id",Const.getStrValue(loginMap,"vg_business_id"));//����Ա����
   		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   		java.util.Date currentTime = new java.util.Date();//�õ���ǰϵͳʱ��
   		String str_date1 = formatter.format(currentTime); //������ʱ���ʽ��
   		param.put("created_date",str_date1);
   		param.put("oper_date",str_date1);
		BrandDAO dao = new BrandDAO();
		boolean result = dao.insert(param) ;
		return result ;
	}


	public boolean updateBrand(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		String keyStr = "brand_id";
		Map keyCondMap  = Const.getMapForTargetStr(param,  keyStr) ;
		BrandDAO dao = new BrandDAO();
		boolean result = dao.updateByKey( param, keyCondMap );

		return result ;
	}


	public PageModel searchBrandData(DynamicDict dto ) throws Exception {
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
		BrandDAO dao = new BrandDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;


		return result ;
	}

	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getBrandById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		BrandDAO dao = new BrandDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		String parentBrandId = Const.getStrValue(result,"parent_brand_id");
		String sql="select brand_name from brand where brand_id = ?";
		String parentName = Base.query_string(JNDINames.PM_DATASOURCE,sql,new String[]{parentBrandId},-1);
		result.put("parent_brand_name",parentName);
		return result ;
	}


	public List findBrandByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ;
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;

		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		BrandDAO dao = new BrandDAO();
		List result = dao.findByCond( whereCond, para) ;

		return result ;
	}


	public boolean deleteBrandById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		BrandDAO dao = new BrandDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;

		return result ;
	}
}
