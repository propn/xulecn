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
 * �������ͳһ����:ͳһ�������ͳһ�������
 * @author cooltan
 *
 */

public  class CommonAccessService implements IAccess,IAction{
	protected static Logger logger = Logger.getLogger(CommonAccessService.class);
	private String accessCode;//�������
	private String accessType;//��������
	private boolean commonInOperationBefore=true;//��ǰ�Ƿ���Ҫ�����Ľ��빦�ܲ���
	private boolean commonInOperationAfter=true;//�º��Ƿ���Ҫ�����Ľ��빦�ܲ���
	private boolean commonOutOperationBefore=true;//��ǰ�Ƿ���Ҫ�����ķ��ع��ܲ���
	private boolean commonOutOperationAfter=true;//�º��Ƿ���Ҫ�����ķ��ع��ܲ���
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//ʡ�ݴ���
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
			//ͳһ�Ŀ�ѡ��ǰ�����������
			if(commonInOperationBefore){
				commonInOperationBeforeRet=this.commonInOperationBefore(in);
			}
			//���Ի��������
			concreteInOpertionRet=this.concreteInOpertion(in);
			//ͳһ�Ŀ�ѡ�º󹫹��������
			if(commonInOperationAfter){
				commonInOperationAfterRet=this.commonInOperationAfter(in);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//logger.error("",e);
			//ͳһ���쳣�������
			this.inExceptionHandle(in,e);
			throw e;
		}
		return in;
	}
	
	/**
	 * �������
	 * �ӿڶ���ת���ɺ��Ķ���
	 * @param in
	 * @return
	 */
	public Map in(Map in,String accessBoName) {//key includes :busiObject   interfaceCode interfaceType
		Map ret=null;
		try {
			//�������֡��������֡����
			ret = DataTranslate._Map(ServiceManager.callJavaBeanService(accessBoName,"innerIn" ,in));
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
			ret=in;
		}
		return ret;
	}
	
	/**
	 * ͳһ�Ŀ�ѡ�º󹫹��������
	 * @param in
	 * @return
	 */
	public Map commonInOperationBefore(Map in) throws Exception{
		Object requestXML = in.get("accessInObject");
		logger.info("requestXML:"+requestXML);
		return in;
	}
	/**
	 * ���Ի��������  ��ÿ��������д
	 * @param in
	 * @return
	 */
	public  Map concreteInOpertion(Map in) throws Exception{
		return null;
	}
	
	/*
	 * ͳһ�Ŀ�ѡ�º󹫹��������
	 */
	public Map commonInOperationAfter(Map in) throws Exception{
		
		return null;
	}
	/**
	 * �������쳣������� ���ɾ���������д
	 * @param in
	 * @return
	 */
	public Map inExceptionHandle(Map in,Exception e){
		in.put("resultCode", "-98");
		in.put("resultMsg", e.toString());
		return in;
	}
	
	/**
	 * ���ز���
	 * ���Ķ���ת���ɽӿڶ���
	 * @param in
	 * @return
	 */
	public Map out(Map in,String accessBoName) {//key includes :busiObject   interfaceCode interfaceType
		Map ret=null;
		try {
			//�������֡��������֡����
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
			//ͳһ�Ŀ�ѡ��ǰ�����������
			if(commonOutOperationBefore){
				commonOutOperationBeforeRet=this.commonOutOperationBefore(in);
			}
			//���Ի��������
			concreteOutOpertionRet=this.concreteOutOpertion(in);
			//ͳһ�Ŀ�ѡ�º󹫹��������
			if(commonOutOperationAfter){
				commonOutOperationAfterRet=this.commonOutOperationAfter(in);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//logger.error("",e);
			//ͳһ���쳣�������
			this.outExceptionHandle(in);
			throw e;
		}
		return in;
	}
	
	/**
	 * ͳһ�Ŀ�ѡ�º󹫹����ز���
	 * @param in
	 * @return
	 */
	public Map commonOutOperationBefore(Map in) throws Exception{
		
		return in;
	}
	/**
	 * ���Ի����ز���  ��ÿ��������д
	 * @param in
	 * @return
	 */
	public  Map concreteOutOpertion(Map in) throws Exception{
		
		return in;
	}
	
	/*
	 * ͳһ�Ŀ�ѡ�º󹫹����ز���
	 */
	public Map commonOutOperationAfter(Map in) throws Exception{
		Object responseXml=in.get("accessOutObject");
		logger.info("responseXml:"+responseXml);
		
		//****
		//�������ػ���ֻR8.4�ŷ���ͬ��������ϵ��crm,��R8.4�����£�������������ײ��е���ֵ��ƷҲ��ͬ��
		//****
		if(this.provinceCode.equals(CrmConstants.GX_PROV_CODE)){
			String code=(String) in.get("accessCode");
			String resultCode = (String) in.get("resultCode");
			if(null != resultCode && "0".equals(resultCode)){ //�ɹ���ʱ���ִ��
				if(null != code && ("R8.4".equals(code))//R8.4�Ƕ�����ϵͬ���ӿڣ�R1.1�Ƕ�������ӿ�
				   && null != CRMSynchUtil.CRM_SYN && "1".equals(CRMSynchUtil.CRM_SYN.trim()) ){
					CustomerOrder order = (CustomerOrder) in.get("busiObject");
					if(in.get("GX_PPROD_TYPE")!=null && "1".equals(in.get("GX_PPROD_TYPE"))){//������ϵͬ������ʱ����������ڴ�ͳ+��ֵ��Ļ����Ͳ�����ͬ����CRM, GX
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
			if(null != resultCode && "0".equals(resultCode)){ //�ɹ���ʱ���ִ��
				if(null != code && ("R8.4".equals(code)|| "R1.1" .equals(code))//R8.4�Ƕ�����ϵͬ���ӿڣ�R1.1�Ƕ�������ӿ�
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
	 * �����ķ����쳣������� ���ɾ���������д
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
