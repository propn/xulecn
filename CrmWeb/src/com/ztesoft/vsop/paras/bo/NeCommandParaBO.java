package com.ztesoft.vsop.paras.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.paras.dao.NeCommandParaDAO;

public class NeCommandParaBO extends DictAction  {

	
	public boolean insertNeCommandPara(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCommandPara = (Map) param.get("NeCommandPara") ;
		
		NeCommandParaDAO dao = new NeCommandParaDAO();
		boolean result = dao.insert(NeCommandPara) ;
		return result ;
	}

	
	public boolean updateNeCommandPara(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCommandPara = (Map) param.get("NeCommandPara") ;
		String keyStr = "command_id";
		Map keyCondMap  = Const.getMapForTargetStr(NeCommandPara,  keyStr) ;
		NeCommandParaDAO dao = new NeCommandParaDAO();
		boolean result = dao.updateByKey( NeCommandPara, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchNeCommandParaData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "name")){
			String name = Const.getStrValue(param, "name");
			whereCond.append(" and (p.name like '%"+name+"%' or p.name like '%"+name+"%' )");
		}
		if(Const.containStrValue(param , "para_code")){
			String code = Const.getStrValue(param, "para_code");
			whereCond.append(" and (para_code like '%"+code+"%' or para_code like '%"+code+"%')");
		}
		if(Const.containStrValue(param , "command_catalog_id")){
			whereCond.append(" and p.command_catalog_id = ? ");
			para.add(Const.getStrValue(param , "command_catalog_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		NeCommandParaDAO dao = new NeCommandParaDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getNeCommandParaById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		NeCommandParaDAO dao = new NeCommandParaDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeCommandParaByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		NeCommandParaDAO dao = new NeCommandParaDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCommandParaById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		NeCommandParaDAO dao = new NeCommandParaDAO();
			boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
			return result ;
	}
	
	/**
	 * ɾ��Ŀ��������ʱ����Ƿ��������¼����
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String validateForParaRecord(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("command_id"));
		NeCommandParaDAO dao = new NeCommandParaDAO();
		List l = dao.findBySql(dao.getSqlForValidateParaRecord(), list);
		if (null != l && !l.isEmpty()) {//��ɾ����Ŀ���������б��������¼������
			HashMap hm = (HashMap) l.get(0);
			Object obj = hm.get("record_name");//�������򷵻ض�Ӧ�������¼�����ƣ�����js����ʾ�û�ʱ��ʾ
			return obj != null ? String.valueOf(obj) : "";
		}
		return "-1";
	}
	
	/**
	 * ɾ��Ŀ��������ʱ����Ƿ񱻹�����������
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String validateForOrderType(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("command_id"));
		NeCommandParaDAO dao = new NeCommandParaDAO();
		List l = dao.findBySql(dao.getSqlForValidateOrderType(), list);
		if (null != l && !l.isEmpty()) {//��ɾ����Ŀ���������б���������������
			HashMap hm = (HashMap) l.get(0);
			Object obj = hm.get("order_type_name");//�������򷵻ض�Ӧ�������͵����ƣ�����js����ʾ�û�ʱ��ʾ
			return obj != null ? String.valueOf(obj) : "";
		}
		return "-1";
	}
	
	/**
	 * ɾ��Ŀ��������ʱ����Ƿ�ָ��ģ������������
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String validateForTemplatePara(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("command_id"));
		NeCommandParaDAO dao = new NeCommandParaDAO();
		List l = dao.findBySql(dao.getSQL_VALIDATE_CMD_TEMPLATE(), list);
		if (null != l && !l.isEmpty()) {//��ɾ����Ŀ���������б�ָ��ģ��������������
			HashMap hm = (HashMap) l.get(0);
			Object obj = hm.get("template_name");//�������򷵻ض�Ӧָ��ģ������ƣ�����js����ʾ�û�ʱ��ʾ
			return obj != null ? String.valueOf(obj) : "";
		}
		return "-1";
	}
	
	/**
	 * ��֤�����Ψһ��
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean validateParaCode(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("command_catalog_id"));
		list.add(map.get("para_code"));
		
		NeCommandParaDAO dao = new NeCommandParaDAO();
		String SQL_Query=dao.getSqlForValidateParaCode();
		if(map.get("command_id")!=null&&!"".equals(map.get("command_id").toString())){//�޸Ĳ���ʱ��commmand_id��Ϊ�գ������жϱ���Ψһ��ʱ�������Լ��Ƚ�
			SQL_Query+=" and command_id!=?";
			list.add(map.get("command_id").toString());
		}
		List l = dao.findBySql(SQL_Query, list);
		if (null != l && !l.isEmpty()) {
			return false;
		}
		return true;
	}
}
