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
import com.ztesoft.vsop.paras.dao.SpParaRecordDAO;

public class SpParaRecordBO extends DictAction  {
	
	public boolean insertSpParaRecord(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpParaRecord = (Map) param.get("SpParaRecord") ;
		
		SpParaRecordDAO dao = new SpParaRecordDAO();
		boolean result = dao.insert(SpParaRecord) ;
		return result ;
	}

	
	public boolean updateSpParaRecord(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpParaRecord = (Map) param.get("SpParaRecord") ;
		String keyStr = "record_id";
		Map keyCondMap  = Const.getMapForTargetStr(SpParaRecord,  keyStr) ;
		SpParaRecordDAO dao = new SpParaRecordDAO();
		boolean result = dao.updateByKey( SpParaRecord, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSpParaRecordData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "record_name")){
			String name = Const.getStrValue(param , "record_name");
			whereCond.append(" and (record_name like '%"+name+"%' or record_name like '%"+name.toUpperCase()+"%')");
		}
		if(Const.containStrValue(param , "record_path")){
			String path = Const.getStrValue(param , "record_path");
			whereCond.append(" and (record_path like '%"+path+"%' or record_path like '%"+path.toUpperCase()+"%')");
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		SpParaRecordDAO dao = new SpParaRecordDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getSpParaRecordById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		SpParaRecordDAO dao = new SpParaRecordDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpParaRecordByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		SpParaRecordDAO dao = new SpParaRecordDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpParaRecordById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		SpParaRecordDAO dao = new SpParaRecordDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	/**
	 * �����������¼��ʶ��SP_ORDER_Type_RECORDS����Ƿ񱻹�����������
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String validateRecordId(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("record_id"));
		
		SpParaRecordDAO dao = new SpParaRecordDAO();
		List l = dao.findBySql(dao.getSQLForValidateRecordId(), list);
		if (null != l && !l.isEmpty()) {
			HashMap hm = (HashMap) l.get(0);
			Object obj = hm.get("order_type_name");
			return obj != null ? String.valueOf(obj) : "";
		}
		//���ֱ�ӷ���nullֵ(���ַ���)�Ļ�����jsҳ���õ���Ҳ��""������ֵ��
		//�뵱order_type_nameΪ""ֵʱ�Ľ��һ�������Է����ַ���"-1"���Ա�js�жϣ�
		//�׶�����һorder_type_name��ֵΪ"-1"�������������Ӧ�ò������
		return "-1";
	}
	
	/**
	 * �������¼���������Ŀ��������ʱ��֤Ŀ���������Ψһ��
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean validateCommandPara(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("record_id"));
		list.add(map.get("command_id"));
		SpParaRecordDAO dao = new SpParaRecordDAO();
		List l = dao.findBySql(dao.getSqlForValidateCommandPara(), list);
		if (null != l && !l.isEmpty()) {
			return false;//���Ѵ�����ͬ��Ŀ��������
		}
		return true;
	}
}
