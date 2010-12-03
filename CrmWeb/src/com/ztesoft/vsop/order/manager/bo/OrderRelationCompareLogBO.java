package com.ztesoft.vsop.order.manager.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.order.OrderCheckBo;
import com.ztesoft.vsop.order.manager.dao.OrderRelationCompareLogDAO ;

public class OrderRelationCompareLogBO extends DictAction  {
	/**
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchOrderRelationCompareLogData �ķ����Ĳ�������
 		3. findOrderRelationCompareLogByCond() ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertOrderRelationCompareLog(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map OrderRelationCompareLog = (Map) param.get("OrderRelationCompareLog") ;
		
		OrderRelationCompareLogDAO dao = new OrderRelationCompareLogDAO();
		boolean result = dao.insert(OrderRelationCompareLog) ;
		return result ;
	}

	
	public boolean updateOrderRelationCompareLog(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map OrderRelationCompareLog = (Map) param.get("OrderRelationCompareLog") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(OrderRelationCompareLog,  keyStr) ;
		OrderRelationCompareLogDAO dao = new OrderRelationCompareLogDAO();
		boolean result = dao.updateByKey( OrderRelationCompareLog, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchOrderRelationCompareLogData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "da")){
			whereCond.append(" and c.da = ? ");
			para.add(Const.getStrValue(param , "da")) ;
		}
		if(Const.containStrValue(param , "oa")){
			whereCond.append(" and c.oa = ? ");
			para.add(Const.getStrValue(param , "oa")) ;
		}
		if(Const.containStrValue(param , "status")){
			whereCond.append(" and c.status = ? ");
			para.add(Const.getStrValue(param , "status")) ;
		}
		if(Const.containStrValue(param , "field_name")){
			whereCond.append(" and c.file_name like ? ");
			para.add("%"+Const.getStrValue(param , "field_name")+"%") ;
		}
		if(Const.containStrValue(param , "start_time")){
			String start_time = Const.getStrValue(param , "start_time");
			start_time += " 00:00:00";
			whereCond.append(" and c.create_date >= to_date('"+start_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "end_time")){
			String end_time = Const.getStrValue(param , "end_time");
			end_time += " 23:59:59";
			whereCond.append(" and c.create_date <= to_date('"+end_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		OrderRelationCompareLogDAO dao = new OrderRelationCompareLogDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getOrderRelationCompareLogById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		OrderRelationCompareLogDAO dao = new OrderRelationCompareLogDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findOrderRelationCompareLogByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		OrderRelationCompareLogDAO dao = new OrderRelationCompareLogDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteOrderRelationCompareLogById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		OrderRelationCompareLogDAO dao = new OrderRelationCompareLogDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	/***
	 * ����checkOrder������߼�
	 * @param type��"D":���ձȶ�;"M":���±ȶ�
	 * @return
	 */
	public int VSOPcompare(DynamicDict dto) throws Exception{
		Map param=Const.getParam(dto);
		OrderCheckBo bo=new OrderCheckBo();
		int k = bo.checkOrder(Const.getStrValue(param, "type"));
		return k;
	}
	
	public boolean NVSOPcompare(DynamicDict dto) throws Exception{
		Map param=Const.getParam(dto);
		String type=Const.getStrValue(param, "type");
		String timeType=Const.getStrValue(param, "timeType");
		OrderCheckBo bo=new OrderCheckBo();
			if("group".equals(type))
				bo.singleExportGroupFile(timeType);
			if("X".equals(type))
				bo.singleExportXFile(timeType);
		   return true;

	}
	public boolean GenFileToODS(DynamicDict dto) throws Exception{
		   OrderCheckBo bo=new OrderCheckBo();
			bo.exportToOdsFile();
		   return true;
	}
	
}
