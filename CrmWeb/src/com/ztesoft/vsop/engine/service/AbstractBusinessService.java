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
 * 服务组件模板类
 *
 */

public abstract class AbstractBusinessService {
	protected static Logger logger = Logger.getLogger(AbstractBusinessService.class);
	private String serviceCode;//服务编码
	private String serviceType;//服务类型
	private boolean commonBusinessOperationBefore;//事前是否需要公共的业务功能操作
	private boolean commonBusinessOperationAfter;//事后是否需要公共的业务功能操作

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * 服务执行方法模板
	 * @param in
	 * @return
	 */
	public Map service(Map in)  throws Exception{//key includes :serviceCode busiObject   accessType accessType 
		Map commonBusinessOperationBeforeRet=null;
		Map commonBusinessOperationAfterRet=null;
		Map concreteBusinessOpertionRet=null;
		try {
			//统一的可选事前公共业务操作
			if(commonBusinessOperationBefore){
				commonBusinessOperationBeforeRet=this.commonBusinessOperationBefore(in);
			}
			//个性化业务操作
			concreteBusinessOpertionRet=this.concreteBusinessOpertions(in);
			//统一的可选事后公共业务操作
			if(commonBusinessOperationAfter){
				commonBusinessOperationAfterRet=this.commonBusinessOperationAfter(in);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			//logger.error("",e);
			//统一的异常处理操作
			this.exceptionHandle(in,e);
			throw e;
		}
		return in;
	}
	
	/**
	 * 统一的可选事后公共业务操作
	 * @param in
	 * @return
	 */
	public Map commonBusinessOperationBefore(Map in) throws Exception{
		//做一些公共数据校验操作
		Object obj=(CustomerOrder)in.get("busiObject");
		String serviceCode=(String)in.get("serviceCode");
		if(serviceCode==null||"".equals(serviceCode)){
			throw new Exception("数据不合法，服务编码serviceCode不合法");
		}
		if(obj==null){
			throw new Exception("数据不合法，业务对象busiObject未初始化");
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
			//检查产品实例是否存在
			if(iserviceCode!=OrderEngine.SERVICE_QUERY32&&iserviceCode!=OrderEngine.SERVICE_WORKSHEETDEAL33){
				//江西本地化，拆机不检查产品实例是否存在
				if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter("DC_PROVINCE_CODE")) && iserviceCode ==OrderEngine.SERVICE_UNINSTALL15){
					return in;
				}
				String systemId=order.getOrderSystem();//来源系统
				if ((order.getProdInstId() == null && !"201".equals(systemId))|| 
						("".equals(order.getProdInstId()) && !"201".equals(systemId))) {//crm可能过来的新装的鉴权，此时请允许用户实例ID为空
					throw new Exception("不是有效用户");
				}
			}
		}
		return in;
	}
	/**
	 * 个性化业务操作  由每个子类重写
	 * @param in
	 * @return
	 */
	public abstract Map concreteBusinessOpertions(Map in) throws Exception;
	
	/*
	 * 统一的可选事后公共业务操作
	 */
	public Map commonBusinessOperationAfter(Map in) throws Exception{
		
		return null;
	}
	/**
	 * 公共的异常处理操作 可由具体子类重写
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
