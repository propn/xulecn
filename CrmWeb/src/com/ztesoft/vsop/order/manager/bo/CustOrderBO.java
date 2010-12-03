package com.ztesoft.vsop.order.manager.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.QueryHelper;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.order.manager.dao.CustOrderDAO ;

public class CustOrderBO extends DictAction  {
	/**
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchCustOrderData �ķ����Ĳ�������
 		3. findCustOrderByCond(String cust_order_id) ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	
	
	public PageModel searchCustOrderData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "acc_nbr")){
			whereCond.append(" and acc_nbr = ? ");
			para.add(Const.getStrValue(param , "acc_nbr")) ;
		}
		if(Const.containStrValue(param , "product_id")){
			whereCond.append(" and product_id = ? ");
			para.add(Const.getStrValue(param , "product_id")) ;
		}
		if(Const.containStrValue(param , "lan_id")){
			whereCond.append(" and lan_id = ? ");
			para.add(Const.getStrValue(param , "lan_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		CustOrderDAO dao = new CustOrderDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	public PageModel searchCustOrderHisData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "acc_nbr")){
			whereCond.append(" and acc_nbr = ? ");
			para.add(Const.getStrValue(param , "acc_nbr")) ;
		}
		if(Const.containStrValue(param , "product_id")){
			whereCond.append(" and product_id = ? ");
			para.add(Const.getStrValue(param , "product_id")) ;
		}
		if(Const.containStrValue(param , "lan_id")){
			whereCond.append(" and lan_id = ? ");
			para.add(Const.getStrValue(param , "lan_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		 String SQL_SELECT = "select cust_order_id,other_sys_order_id,cust_so_number,cust_order_type,time_name_id,cust_id,staff_id,channel_id,status,status_date,pre_handle_flag,handle_people_name,contact_phone,contact_people,priority,reason,order_channel,order_system,receive_date,disposal_result,disposal_result_desc,acc_nbr,(select product_name from product p where p.PROD_FUNC_TYPE='0' and p.product_id=o.product_id) as product_name,product_id,lan_id,prod_inst_id from CUSTOMER_ORDER_HIS o where 1=1 ";
		//����DAO����
		CustOrderDAO dao = new CustOrderDAO();
		dao.setSelectSQL(SQL_SELECT);
		dao.setSelectCountSQL("select count(*) as col_counts from customer_order_his where 1=1 ");
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}

	public List findCustOrderByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		CustOrderDAO dao = new CustOrderDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	public List getOrderItemList(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer("select order_item_id,cust_order_id,acc_nbr,product_id,(select product_name from product p where p.product_id=o.product_id) as product_name,ORDER_ITEM_CD,status,status_date,PRE_HANDLE_FLAG,HANDLE_TIME,ARCHIVE_DATE from order_item o where 1=1 ") ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "cust_order_id")){
			whereCond.append(" and cust_order_id = ? ");
			para.add(Const.getStrValue(param , "cust_order_id")) ;
		}
		
		//����DAO����
		CustOrderDAO dao = new CustOrderDAO();
		List result = dao.findBySql(whereCond.toString() , para) ;
		
		
		return result ;
	}
	
	public List getOrderItemHisList(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer("select order_item_id,cust_order_id,acc_nbr,product_id,(select product_name from product p where p.product_id=o.product_id) as product_name,ORDER_ITEM_CD,status,status_date,PRE_HANDLE_FLAG,HANDLE_TIME,ARCHIVE_DATE from order_item_his o where 1=1 ") ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "cust_order_id")){
			whereCond.append(" and cust_order_id = ? ");
			para.add(Const.getStrValue(param , "cust_order_id")) ;
		}
		
		//����DAO����
		CustOrderDAO dao = new CustOrderDAO();
		List result = dao.findBySql(whereCond.toString() , para) ;
		
		
		return result ;
	}
	public List getPfOrder(DynamicDict dto )throws Exception {
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer("select INF_SYSTEM,CREATE_DATE,decode(state,'U','������','D','������','F','����ʧ��','S','����ɹ�','δ֪״̬') STATE,STATE_DATE,CUST_ORDER_ID,OTHER_SYS_ORDER_ID from INF_MSG a where 1=1 ") ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "out_order_id")){
			whereCond.append(" and a.OTHER_SYS_ORDER_ID = ? ");
			para.add(Const.getStrValue(param , "out_order_id")) ;
		}
		whereCond.append(" union all select INF_SYSTEM,CREATE_DATE,decode(state,'U','������','D','������','F','����ʧ��','S','����ɹ�','δ֪״̬') STATE,STATE_DATE,CUST_ORDER_ID,OTHER_SYS_ORDER_ID from INF_MSG_l b where 1=1 ");
		if(Const.containStrValue(param , "out_order_id")){
			whereCond.append(" and b.OTHER_SYS_ORDER_ID = ? ");
			para.add(Const.getStrValue(param , "out_order_id")) ;
		}
//		System.out.println("getPfOrder sql:" + whereCond.toString() + ",orderId:"+Const.getStrValue(param , "out_order_id"));
		CustOrderDAO dao = new CustOrderDAO();
		List result = dao.getPfOrder(whereCond.toString(),para);
		return result;
	}

}
