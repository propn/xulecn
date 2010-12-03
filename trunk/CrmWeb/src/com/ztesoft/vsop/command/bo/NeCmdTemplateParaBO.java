package com.ztesoft.vsop.command.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.command.dao.NeCmdTemplateParaDAO;

public class NeCmdTemplateParaBO extends DictAction  {
	public boolean insertNeCmdTemplatePara(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCmdTemplatePara = (Map) param.get("NeCmdTemplatePara") ;
		
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		boolean result = dao.insert(NeCmdTemplatePara) ;
		return result ;
	}

	
	public boolean updateNeCmdTemplatePara(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCmdTemplatePara = (Map) param.get("NeCmdTemplatePara") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(NeCmdTemplatePara,  keyStr) ;
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		boolean result = dao.updateByKey( NeCmdTemplatePara, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchNeCmdTemplateParaData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "template_id")){
			whereCond.append(" and t.template_id = ? ");
			para.add(Const.getStrValue(param , "template_id")) ;
		}

		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getNeCmdTemplateParaById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeCmdTemplateParaByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCmdTemplateParaById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		boolean result = dao.deleteById( keyCondMap );
		
		return result ;
	}
	public boolean isRelate(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		boolean result = dao.isRelate(Const.getStrValue(keyCondMap, "template_id"));
		
		return result ;
	}
	
	/**
	 * ����ģ���ʶ���������ʶ��ne_cmd_template_para�м��ģ������������Ψһ��
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean validateTemplateCommond(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("template_id"));
		list.add(map.get("command_id"));
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		List l = dao.findBySql(dao.getSqlForValidateTemplatePara(), list);
		if (null != l && !l.isEmpty()) {
			return false;//�Ѵ�����ͬ��¼
		}
		return true;
	}
}
