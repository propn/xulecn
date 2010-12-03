package com.ztesoft.crm.product.bo;



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

import com.ztesoft.crm.product.dao.AttrDAO ;
import com.ztesoft.crm.product.dao.AttrValueRelaDAO;

public class AttrBO extends DictAction  {
	/**
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchAttrData �ķ����Ĳ�������
 		3. findAttrByCond(String attr_id) ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */

	public boolean insertAttr(DynamicDict dto  ) throws Exception {
		//�����������
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "��ѯ����" ;

		Map param = Const.getParam(dto) ;
		Map Attr = (Map) param.get("Attr") ;
		String sql="select nvl(max(ATTR_ID),0)+1 from ATTRIBUTE ";
		String attrId = Base.query_string( JNDINames.PM_DATASOURCE,sql,1);
		Attr.put("attr_id",attrId);
		Attr.put("state","00A");
		Attr.put("seq","0");

		AttrDAO dao = new AttrDAO();
		boolean result = dao.insert(Attr ,  ecode,  etype,  edesc) ;
		return result ;
	}


	public boolean updateAttr(DynamicDict dto ) throws Exception {
		//�����������
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "��ѯ����" ;

		Map param = Const.getParam(dto) ;
		Map Attr = (Map) param.get("Attr") ;
		String keyStr = "attr_id";
		Map keyCondMap  = Const.getMapForTargetStr(Attr,  keyStr) ;
		AttrDAO dao = new AttrDAO();
		boolean result = dao.updateByKey( Attr, keyCondMap ,
				etype ,  ecode , edesc);

		return result ;
	}


	public PageModel searchAttrData(DynamicDict dto ) throws Exception {
		//�����������
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "��ѯ����" ;

		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		String payWay = param.get("query_way").toString();
		if("1".equals(payWay)){
			String attrDesc = Const.getStrValue(param,"query_data");
			attrDesc = "%"+attrDesc+"%";
			whereCond.append(" and attr_name like ? ");
			para.add(attrDesc) ;
		}else if("0".equals(payWay))
		{
			whereCond.append(" and attr_id = ? ");
			para.add(Const.getStrValue(param , "query_data")) ;
		}else{
			whereCond.append(" and attr_code = ? ");
			para.add(Const.getStrValue(param , "query_data")) ;
		}

		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;


		//����DAO����
		AttrDAO dao = new AttrDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize ,
				 dto,  ecode,  etype,  edesc) ;


		return result ;
	}

	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getAttrById(DynamicDict dto ) throws Exception {
		//�����������
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "��ѯ����" ;

		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		AttrDAO dao = new AttrDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ,  etype ,  ecode , edesc ) ;

		return result ;
	}


	public List findAttrByCond(DynamicDict dto ) throws Exception {
		//�����������
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "��ѯ����" ;

		//��������
		String filterStr = "" ;
		Map changeName = null ;
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;

		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		AttrDAO dao = new AttrDAO();
		List result = dao.findByCond( whereCond, para ,
				  etype ,  ecode , edesc) ;

		return result ;
	}


	public boolean deleteAttrById(DynamicDict dto ) throws Exception {
		//�����������
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "��ѯ����" ;

		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		AttrDAO dao = new AttrDAO();
		boolean result = dao.deleteByKey( keyCondMap ,  etype ,  ecode , edesc ) > 0 ;

		return result ;
	}

	public boolean saveAttrValueRela(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		List attrValueRela = (ArrayList)param.get("attrValueRela");
		AttrValueRelaDAO dao = new AttrValueRelaDAO();
		for(int i=0;i<attrValueRela.size();i++){
			Map temp = (HashMap)attrValueRela.get(i);
			dao.deleteByKey(temp);
			dao.insert(temp) ;
		}

		return true ;
	}
	public List getAttrValue(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		String queryWay = Const.getStrValue(param,"queryWay");
		String queryData = "%"+Const.getStrValue(param,"queryData")+"%";
		List paraList = new ArrayList();
		String andsql="";
		String sql="select a.attr_id,a.attr_name,b.attr_value_id,b.attr_value_desc from attribute a,attribute_value b " +
					"where a.attr_id = b.attr_id and a.state='00A'  ";
		if("1".equals(queryWay)){
			andsql ="and a.attr_name like ?";
		}else{
			andsql ="and b.attr_value_desc like ?";
		}
		paraList.add(queryData);
		//����DAO����
		AttrValueRelaDAO dao = new AttrValueRelaDAO();
		List result = dao.findBySql(sql+andsql, paraList) ;

		return result ;
	}
	public boolean deleteAttrValueRelaById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		AttrValueRelaDAO dao = new AttrValueRelaDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;

		return result ;
	}
}
