package com.ztesoft.vsop.engine.service;

import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.IAction;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.engine.OrderEngine;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.web.DcSystemParamManager;
/**
 * 
 * @author cooltan
 * �������ģ����
 *
 */

public abstract class AbstractBusinessService {
	protected static Logger logger = Logger.getLogger(AbstractBusinessService.class);
	private String serviceCode;//�������
	private String serviceType;//��������
	private boolean commonBusinessOperationBefore;//��ǰ�Ƿ���Ҫ������ҵ���ܲ���
	private boolean commonBusinessOperationAfter;//�º��Ƿ���Ҫ������ҵ���ܲ���

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * ����ִ�з���ģ��
	 * @param in
	 * @return
	 */
	public Map service(Map in)  throws Exception{//key includes :serviceCode busiObject   accessType accessType 
		Map commonBusinessOperationBeforeRet=null;
		Map commonBusinessOperationAfterRet=null;
		Map concreteBusinessOpertionRet=null;
		try {
			//ͳһ�Ŀ�ѡ��ǰ����ҵ�����
			if(commonBusinessOperationBefore){
				commonBusinessOperationBeforeRet=this.commonBusinessOperationBefore(in);
			}
			//���Ի�ҵ�����
			concreteBusinessOpertionRet=this.concreteBusinessOpertions(in);
			//ͳһ�Ŀ�ѡ�º󹫹�ҵ�����
			if(commonBusinessOperationAfter){
				commonBusinessOperationAfterRet=this.commonBusinessOperationAfter(in);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			//logger.error("",e);
			//ͳһ���쳣�������
			this.exceptionHandle(in,e);
			throw e;
		}
		return in;
	}
	
	/**
	 * ͳһ�Ŀ�ѡ�º󹫹�ҵ�����
	 * @param in
	 * @return
	 */
	public Map commonBusinessOperationBefore(Map in) throws Exception{
		//��һЩ��������У�����
		Object obj=(CustomerOrder)in.get("busiObject");
		String serviceCode=(String)in.get("serviceCode");
		if(serviceCode==null||"".equals(serviceCode)){
			throw new Exception("���ݲ��Ϸ����������serviceCode���Ϸ�");
		}
		if(obj==null){
			throw new Exception("���ݲ��Ϸ���ҵ�����busiObjectδ��ʼ��");
		}
		int iserviceCode=Integer.parseInt(serviceCode);
		
		CustomerOrder order=null;
		if(obj instanceof CustomerOrder){
			order=(CustomerOrder)obj;
			if(order.getProdInst()!=null){
				if(order.getProdInstId()==null||"".equals(order.getProdInstId())){
					order.setProdInstId(order.getProdInst().getProdInstId());
				}
			}
			//����Ʒʵ���Ƿ����
			if(iserviceCode!=OrderEngine.SERVICE_QUERY32&&iserviceCode!=OrderEngine.SERVICE_WORKSHEETDEAL33){
				//�������ػ������������Ʒʵ���Ƿ����
				if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter("DC_PROVINCE_CODE")) && iserviceCode ==OrderEngine.SERVICE_UNINSTALL15){
					return in;
				}
				String systemId=order.getOrderSystem();//��Դϵͳ
				if ((order.getProdInstId() == null && !"201".equals(systemId))|| 
						("".equals(order.getProdInstId()) && !"201".equals(systemId))) {//crm���ܹ�������װ�ļ�Ȩ����ʱ�������û�ʵ��IDΪ��
					throw new Exception("������Ч�û�");
				}
			}
		}
		return in;
	}
	/**
	 * ���Ի�ҵ�����  ��ÿ��������д
	 * @param in
	 * @return
	 */
	public abstract Map concreteBusinessOpertions(Map in) throws Exception;
	
	/*
	 * ͳһ�Ŀ�ѡ�º󹫹�ҵ�����
	 */
	public Map commonBusinessOperationAfter(Map in) throws Exception{
		
		return null;
	}
	/**
	 * �������쳣������� ���ɾ���������д
	 * @param in
	 * @return
	 */
	public Map exceptionHandle(Map in,Exception e){
		in.put("resultCode", "-99");
		in.put("resultMsg", e.getMessage());
		return in;
	}
	
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public boolean isCommonBusinessOperationBefore() {
		return commonBusinessOperationBefore;
	}
	public void setCommonBusinessOperationBefore(
			boolean commonBusinessOperationBefore) {
		this.commonBusinessOperationBefore = commonBusinessOperationBefore;
	}
	public boolean isCommonBusinessOperationAfter() {
		return commonBusinessOperationAfter;
	}
	public void setCommonBusinessOperationAfter(boolean commonBusinessOperationAfter) {
		this.commonBusinessOperationAfter = commonBusinessOperationAfter;
	}

}
