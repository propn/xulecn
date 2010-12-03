package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
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

import com.ztesoft.crm.product.dao.AttrValueDAO ;

public class AttrValueBO extends DictAction  {
	/**
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchAttrValueData �ķ����Ĳ�������
 		3. findAttrValueByCond(String attr_value_id,String seq) ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */

	public boolean insertAttrValue(DynamicDict dto  ) throws Exception {
		//�����������
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "��ѯ����" ;

		Map param = Const.getParam(dto) ;
		Map AttrValue = (Map) param.get("AttrValue") ;
		String sql="select nvl(max(attr_value_id),0)+1 from attribute_value ";
		String attrValueId = Base.query_string( JNDINames.PM_DATASOURCE,sql,1);
		AttrValue.put("attr_value_id",attrValueId);

		AttrValueDAO dao = new AttrValueDAO();
		boolean result = dao.insert(AttrValue ,  ecode,  etype,  edesc) ;
		return result ;
	}


	public boolean updateAttrValue(DynamicDict dto ) throws Exception {
		//�����������
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "��ѯ����" ;

		Map param = Const.getParam(dto) ;
		Map AttrValue = (Map) param.get("AttrValue") ;
		String keyStr = "attr_value_id,seq";
		Map keyCondMap  = Const.getMapForTargetStr(AttrValue,  keyStr) ;
		AttrValueDAO dao = new AttrValueDAO();
		boolean result = dao.updateByKey( AttrValue, keyCondMap ,
				etype ,  ecode , edesc);

		return result ;
	}


	public List searchAttrValueData(DynamicDict dto ) throws Exception {
		//�����������

		//��������
		Map param = Const.getParam(dto) ;
		String whereCond = "" ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "attr_id")){
			whereCond = " and attr_id = ? ";
			para.add(Const.getStrValue(param , "attr_id")) ;
		}
		//����DAO����
		AttrValueDAO dao = new AttrValueDAO();
		List result = dao.findByCond(whereCond,para);

		return result ;
	}

	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getAttrValueById(DynamicDict dto ) throws Exception {
		//�����������
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "��ѯ����" ;

		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		AttrValueDAO dao = new AttrValueDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ,  etype ,  ecode , edesc ) ;

		return result ;
	}


	public List findAttrValueByCond(DynamicDict dto ) throws Exception {
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
		AttrValueDAO dao = new AttrValueDAO();
		List result = dao.findByCond( whereCond, para ,
				  etype ,  ecode , edesc) ;

		return result ;
	}


	public boolean deleteAttrValueById(DynamicDict dto ) throws Exception {
		//�����������
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "��ѯ����" ;

		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		AttrValueDAO dao = new AttrValueDAO();
		boolean result = dao.deleteByKey( keyCondMap ,  etype ,  ecode , edesc ) > 0 ;

		return result ;
	}
}
