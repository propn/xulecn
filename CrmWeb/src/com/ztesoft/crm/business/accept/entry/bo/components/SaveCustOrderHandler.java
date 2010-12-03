package com.ztesoft.crm.business.accept.entry.bo.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.crm.business.accept.entry.bo.BusiComponent;
import com.ztesoft.crm.business.common.consts.BusiTables.ORD_CUSTOMER_ORDER;
import com.ztesoft.crm.business.common.order.dao.OrdCustOrderDAO;
import com.ztesoft.crm.business.common.sem.engine.BusiEngine;
import com.ztesoft.crm.business.common.utils.SeqUtil;

public class SaveCustOrderHandler implements BusiComponent{

	public Object execute() {
		
		HashMap paramMap=(HashMap)BusiEngine.get("CUST_ORDER");
		String cust_id = (String) paramMap.get(ORD_CUSTOMER_ORDER.cust_id);
		String cust_ord_id=null;
		//客户编号不为空才进行处理。
		if(cust_id != null && !"".equals(cust_id)){

			//查询客户是否有未确认的客户订单。
			 cust_ord_id = getCustOrderInfo(paramMap);
			
			if(cust_ord_id == null || "".equals(cust_ord_id)){
				//不存在未确认的客户订单。需要重新生成。
				//组织用于保存数据的Map
				HashMap custOrderMap =  createCustOrderMap(paramMap);
				try {
					OrdCustOrderDAO ordCustOrderDao = new OrdCustOrderDAO();
					//生成新的客户订单。
					ordCustOrderDao.insert(custOrderMap);
					//取出客户订单标识。
					cust_ord_id = (String) custOrderMap.get(ORD_CUSTOMER_ORDER.cust_ord_id);
				} catch (FrameException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return cust_ord_id;
		
		
	}
	
	/**
	 * 组织客户订单的Map
	 * @param paramMap
	 * @return
	 */
	public HashMap createCustOrderMap(HashMap paramMap){
		//获取传入的数据
		String cust_id = (String) paramMap.get(ORD_CUSTOMER_ORDER.cust_id);
		String lan_id = (String) paramMap.get(ORD_CUSTOMER_ORDER.lan_id);
		String business_id = (String) paramMap.get(ORD_CUSTOMER_ORDER.business_id);
		String staff_no = (String) paramMap.get(ORD_CUSTOMER_ORDER.staff_no);
		String site_no = (String) paramMap.get(ORD_CUSTOMER_ORDER.site_no);
		String ask_source = (String) paramMap.get(ORD_CUSTOMER_ORDER.ask_source);
		//获取客户订单的序列。
		String cust_ord_id = SeqUtil.getInst().getNext("ORD_CUSTOMER_ORDER", "CUST_ORD_ID");
		//获取订单流水号。 暂时和客户订单标识一样，以后会进行修改。
		String cust_so_number = cust_ord_id;
		String state = "100"; //受理
		String status_date = DAOUtils.getFormatedDate();
		//以下字段的信息以后还要进行修改。
		String pre_handle_flag = "0";
		String priority = "0";
		String ask_source_srl = "1";
		
		HashMap custOrderMap = new HashMap();
		custOrderMap.put(ORD_CUSTOMER_ORDER.cust_ord_id, cust_ord_id);
		custOrderMap.put(ORD_CUSTOMER_ORDER.cust_so_number, cust_so_number);
		custOrderMap.put(ORD_CUSTOMER_ORDER.cust_id, cust_id);
		custOrderMap.put(ORD_CUSTOMER_ORDER.state, state);
		custOrderMap.put(ORD_CUSTOMER_ORDER.status_date, status_date);
		custOrderMap.put(ORD_CUSTOMER_ORDER.pre_handle_flag, pre_handle_flag);
		custOrderMap.put(ORD_CUSTOMER_ORDER.priority, priority);
		custOrderMap.put(ORD_CUSTOMER_ORDER.business_id, business_id);
		custOrderMap.put(ORD_CUSTOMER_ORDER.lan_id, lan_id);
		custOrderMap.put(ORD_CUSTOMER_ORDER.staff_no, staff_no);
		custOrderMap.put(ORD_CUSTOMER_ORDER.site_no, site_no);
		custOrderMap.put(ORD_CUSTOMER_ORDER.ask_source, ask_source);
		custOrderMap.put(ORD_CUSTOMER_ORDER.ask_source_srl, ask_source_srl);
		
		return custOrderMap;
	}
	
	/**
	 * 查询客户未确认的客户订单
	 * @param paramMap
	 * @return
	 */
	private String getCustOrderInfo(HashMap paramMap){
		String cust_id = (String) paramMap.get(ORD_CUSTOMER_ORDER.cust_id);
		
		OrdCustOrderDAO ordCustOrderDao = new OrdCustOrderDAO();
		//先判断数据库中是否有存在未确认的客户订单。 以后要加剔除预受理的工单。
		String whereCond = " and cust_id = ? and state = ?  ";
		ArrayList whereCondParams = new ArrayList();
		whereCondParams.add(cust_id);
		whereCondParams.add("100");
		ArrayList ordCustOrderList=new ArrayList();
		try {
			ordCustOrderList = (ArrayList) ordCustOrderDao.findByCond(whereCond, whereCondParams);
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String cust_ord_id = "";
		Iterator ordCustOrderIter = ordCustOrderList.iterator();
		if(ordCustOrderIter.hasNext()){
			//存在未确认的客户订单。
			Map voMap = (Map) ordCustOrderIter.next();
			cust_ord_id = (String) voMap.get(ORD_CUSTOMER_ORDER.cust_ord_id);
		}
		return cust_ord_id;
	}
	
	
}
