package com.ztesoft.vsop.engine.service;

import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.IAction;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.CRMSynchUtil;
import com.ztesoft.vsop.engine.dao.SynORInfoToCrmHelpDao;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.web.DcSystemParamManager;
/**
 * 接入组件统一超类:统一事务管理、统一接入入口
 * @author cooltan
 *
 */

public  class CommonAccessService implements IAccess,IAction{
	protected static Logger logger = Logger.getLogger(CommonAccessService.class);
	private String accessCode;//接入编码
	private String accessType;//接入类型
	private boolean commonInOperationBefore=true;//事前是否需要公共的接入功能操作
	private boolean commonInOperationAfter=true;//事后是否需要公共的接入功能操作
	private boolean commonOutOperationBefore=true;//事前是否需要公共的返回功能操作
	private boolean commonOutOperationAfter=true;//事后是否需要公共的返回功能操作
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//省份代码
	protected DcSystemParamManager aDcSystemParamManager=DcSystemParamManager.getInstance();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * 
	 * @param in
	 * @return
	 */
	public Map innerIn(Map in) throws Exception {//key includes :busiObject   interfaceCode interfaceType
		Map commonInOperationBeforeRet=null;
		Map commonInOperationAfterRet=null;
		Map concreteInOpertionRet=null;
		try {
			//统一的可选事前公共接入操作
			if(commonInOperationBefore){
				commonInOperationBeforeRet=this.commonInOperationBefore(in);
			}
			//个性化接入操作
			concreteInOpertionRet=this.concreteInOpertion(in);
			//统一的可选事后公共接入操作
			if(commonInOperationAfter){
				commonInOperationAfterRet=this.commonInOperationAfter(in);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//logger.error("",e);
			//统一的异常处理操作
			this.inExceptionHandle(in,e);
			throw e;
		}
		return in;
	}
	
	/**
	 * 接入操作
	 * 接口对象转换成核心对象
	 * @param in
	 * @return
	 */
	public Map in(Map in,String accessBoName) {//key includes :busiObject   interfaceCode interfaceType
		Map ret=null;
		try {
			//服务名字、方法名字、入参
			ret = DataTranslate._Map(ServiceManager.callJavaBeanService(accessBoName,"innerIn" ,in));
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
			ret=in;
		}
		return ret;
	}
	
	/**
	 * 统一的可选事后公共接入操作
	 * @param in
	 * @return
	 */
	public Map commonInOperationBefore(Map in) throws Exception{
		Object requestXML = in.get("accessInObject");
		logger.info("requestXML:"+requestXML);
		return in;
	}
	/**
	 * 个性化接入操作  由每个子类重写
	 * @param in
	 * @return
	 */
	public  Map concreteInOpertion(Map in) throws Exception{
		return null;
	}
	
	/*
	 * 统一的可选事后公共接入操作
	 */
	public Map commonInOperationAfter(Map in) throws Exception{
		
		return null;
	}
	/**
	 * 公共的异常处理操作 可由具体子类重写
	 * @param in
	 * @return
	 */
	public Map inExceptionHandle(Map in,Exception e){
		in.put("resultCode", "-98");
		in.put("resultMsg", e.toString());
		return in;
	}
	
	/**
	 * 返回操作
	 * 核心对象转换成接口对象
	 * @param in
	 * @return
	 */
	public Map out(Map in,String accessBoName) {//key includes :busiObject   interfaceCode interfaceType
		Map ret=null;
		try {
			//服务名字、方法名字、入参
			ret = DataTranslate._Map(ServiceManager.callJavaBeanService(accessBoName,"innerOut" ,in));
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
			ret=in;
		}
		return ret;
	}
	
	public Map innerOut(Map in) throws Exception{//key includes :busiObject   interfaceCode interfaceType
		Map commonOutOperationBeforeRet=null;
		Map commonOutOperationAfterRet=null;
		Map concreteOutOpertionRet=null;
		try {
			//统一的可选事前公共接入操作
			if(commonOutOperationBefore){
				commonOutOperationBeforeRet=this.commonOutOperationBefore(in);
			}
			//个性化接入操作
			concreteOutOpertionRet=this.concreteOutOpertion(in);
			//统一的可选事后公共接入操作
			if(commonOutOperationAfter){
				commonOutOperationAfterRet=this.commonOutOperationAfter(in);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//logger.error("",e);
			//统一的异常处理操作
			this.outExceptionHandle(in);
			throw e;
		}
		return in;
	}
	
	/**
	 * 统一的可选事后公共返回操作
	 * @param in
	 * @return
	 */
	public Map commonOutOperationBefore(Map in) throws Exception{
		
		return in;
	}
	/**
	 * 个性化返回操作  由每个子类重写
	 * @param in
	 * @return
	 */
	public  Map concreteOutOpertion(Map in) throws Exception{
		
		return in;
	}
	
	/*
	 * 统一的可选事后公共返回操作
	 */
	public Map commonOutOperationAfter(Map in) throws Exception{
		Object responseXml=in.get("accessOutObject");
		logger.info("responseXml:"+responseXml);
		
		//****
		//广西本地化，只R8.4才反向同步订购关系到crm,且R8.4条件下，如果属于捆绑套餐中的增值产品也不同步
		//****
		if(this.provinceCode.equals(CrmConstants.GX_PROV_CODE)){
			String code=(String) in.get("accessCode");
			String resultCode = (String) in.get("resultCode");
			if(null != resultCode && "0".equals(resultCode)){ //成功的时候才执行
				if(null != code && ("R8.4".equals(code))//R8.4是订购关系同步接口，R1.1是订购申请接口
				   && null != CRMSynchUtil.CRM_SYN && "1".equals(CRMSynchUtil.CRM_SYN.trim()) ){
					CustomerOrder order = (CustomerOrder) in.get("busiObject");
					if(in.get("GX_PPROD_TYPE")!=null && "1".equals(in.get("GX_PPROD_TYPE"))){//订购关系同步过来时，如果是属于传统+增值里的话，就不反向同步到CRM, GX
						return in;
					}
					SynORInfoToCrmHelpDao sysDao = new SynORInfoToCrmHelpDao();
					sysDao.createChangeORQueueByCustomerOrder(order);
				}
			}
			return in;
		}else {
			String code=(String) in.get("accessCode");
			String resultCode = (String) in.get("resultCode");
			if(null != resultCode && "0".equals(resultCode)){ //成功的时候才执行
				if(null != code && ("R8.4".equals(code)|| "R1.1" .equals(code))//R8.4是订购关系同步接口，R1.1是订购申请接口
				   && null != CRMSynchUtil.CRM_SYN && "1".equals(CRMSynchUtil.CRM_SYN.trim()) ){
					CustomerOrder order = (CustomerOrder) in.get("busiObject");
					SynORInfoToCrmHelpDao sysDao = new SynORInfoToCrmHelpDao();
					sysDao.createChangeORQueueByCustomerOrder(order);
				}
			}
			return in;
		}
	}
	/**
	 * 公共的返回异常处理操作 可由具体子类重写
	 * @param in
	 * @return
	 */
	public Map outExceptionHandle(Map in){
		return in;
	}
	
	public int perform(DynamicDict dto) throws Exception {
		String methodName = (String) dto.getValueByName(Const.ACTION_METHOD);
		Map in =(Map)dto.getValueByName(Const.ACTION_PARAMETER) ;
		if("innerOut".equals(methodName)){
			Map out=this.innerOut(in);
			dto.setValueByName(Const.ACTION_RESULT, out);
		}else if("innerIn".equals(methodName)){
			Map out=this.innerIn(in);
			dto.setValueByName(Const.ACTION_RESULT, out);
		}
		return 0;
	}
	
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public boolean isCommonInOperationBefore() {
		return commonInOperationBefore;
	}
	public void setCommonInOperationBefore(boolean commonInOperationBefore) {
		this.commonInOperationBefore = commonInOperationBefore;
	}
	public boolean isCommonInOperationAfter() {
		return commonInOperationAfter;
	}
	public void setCommonInOperationAfter(boolean commonInOperationAfter) {
		this.commonInOperationAfter = commonInOperationAfter;
	}
	public boolean isCommonOutOperationBefore() {
		return commonOutOperationBefore;
	}
	public void setCommonOutOperationBefore(boolean commonOutOperationBefore) {
		this.commonOutOperationBefore = commonOutOperationBefore;
	}
	public boolean isCommonOutOperationAfter() {
		return commonOutOperationAfter;
	}
	public void setCommonOutOperationAfter(boolean commonOutOperationAfter) {
		this.commonOutOperationAfter = commonOutOperationAfter;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

}
