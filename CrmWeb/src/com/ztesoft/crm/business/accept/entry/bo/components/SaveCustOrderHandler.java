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
		//�ͻ���Ų�Ϊ�ղŽ��д���
		if(cust_id != null && !"".equals(cust_id)){

			//��ѯ�ͻ��Ƿ���δȷ�ϵĿͻ�������
			 cust_ord_id = getCustOrderInfo(paramMap);
			
			if(cust_ord_id == null || "".equals(cust_ord_id)){
				//������δȷ�ϵĿͻ���������Ҫ�������ɡ�
				//��֯���ڱ������ݵ�Map
				HashMap custOrderMap =  createCustOrderMap(paramMap);
				try {
					OrdCustOrderDAO ordCustOrderDao = new OrdCustOrderDAO();
					//�����µĿͻ�������
					ordCustOrderDao.insert(custOrderMap);
					//ȡ���ͻ�������ʶ��
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
	 * ��֯�ͻ�������Map
	 * @param paramMap
	 * @return
	 */
	public HashMap createCustOrderMap(HashMap paramMap){
		//��ȡ���������
		String cust_id = (String) paramMap.get(ORD_CUSTOMER_ORDER.cust_id);
		String lan_id = (String) paramMap.get(ORD_CUSTOMER_ORDER.lan_id);
		String business_id = (String) paramMap.get(ORD_CUSTOMER_ORDER.business_id);
		String staff_no = (String) paramMap.get(ORD_CUSTOMER_ORDER.staff_no);
		String site_no = (String) paramMap.get(ORD_CUSTOMER_ORDER.site_no);
		String ask_source = (String) paramMap.get(ORD_CUSTOMER_ORDER.ask_source);
		//��ȡ�ͻ����������С�
		String cust_ord_id = SeqUtil.getInst().getNext("ORD_CUSTOMER_ORDER", "CUST_ORD_ID");
		//��ȡ������ˮ�š� ��ʱ�Ϳͻ�������ʶһ�����Ժ������޸ġ�
		String cust_so_number = cust_ord_id;
		String state = "100"; //����
		String status_date = DAOUtils.getFormatedDate();
		//�����ֶε���Ϣ�Ժ�Ҫ�����޸ġ�
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
	 * ��ѯ�ͻ�δȷ�ϵĿͻ�����
	 * @param paramMap
	 * @return
	 */
	private String getCustOrderInfo(HashMap paramMap){
		String cust_id = (String) paramMap.get(ORD_CUSTOMER_ORDER.cust_id);
		
		OrdCustOrderDAO ordCustOrderDao = new OrdCustOrderDAO();
		//���ж����ݿ����Ƿ��д���δȷ�ϵĿͻ������� �Ժ�Ҫ���޳�Ԥ����Ĺ�����
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
			//����δȷ�ϵĿͻ�������
			Map voMap = (Map) ordCustOrderIter.next();
			cust_ord_id = (String) voMap.get(ORD_CUSTOMER_ORDER.cust_ord_id);
		}
		return cust_ord_id;
	}
	
	
}
