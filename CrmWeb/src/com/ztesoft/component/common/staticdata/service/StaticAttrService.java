package com.ztesoft.component.common.staticdata.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.exception.CommonError;
import com.ztesoft.common.exception.CommonException;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.component.common.staticdata.StaticAttrCache;
import com.ztesoft.component.common.staticdata.vo.StaticAttrVO;

/**
 * ��˵��: StaticDataService��̬���ݷ����࣬Ϊ�ͻ����ṩ���û�ȡ��̬���ݵķ�����
 * 
 * Copyright ? 2006 Guangzhou Nanfang telecom system software Co.Ltd. All rights
 * reserved.
 * 
 * @author fjy
 * @version 1.0
 * @since 2006-02-10
 * @modified by nfdx 2006-02-10
 */

public class StaticAttrService extends DictService {
	/**
	 * Logger for this class
	 */
	private HttpSession session = null;
	
	private static final Logger logger = Logger.getLogger(StaticAttrService.class);

	/**
	 * ȡ�õ���ľ�̬�������ݡ�
	 * 
	 * @param attrCode
	 *            ��̬��������
	 */
	public ArrayList getStaticAttr(String attrCode) throws Exception {

		//long time1 = System.currentTimeMillis();
		
		//cache
		ArrayList dataList = StaticAttrCache.getInstance().getAttrData(attrCode);
		if (dataList == null || dataList.size() == 0) {

			//long time3 = System.currentTimeMillis();

			//�� StaticAttr ��������
			//try {
			DynamicDict dto = getServiceDTO("STATICATTRBEAN" ,"getStaticAttr") ;
			dto.setValueByName("parameter", attrCode) ;
			dto = ActionDispatch.dispatch(dto);
			dataList =  ((ArrayList)dto.getValueByName("result"));
				//put into cache
				StaticAttrCache.getInstance().setAttrData(attrCode, dataList);

			/*} catch (RemoteException e) {
				// ��ȡ��̬���ݳ���
				throw new CommonException(new CommonError(CommonError.DATA_NOFOUND_ERROR), e);

			}*/

			//�� StaticData ��������
			if (dataList == null || dataList.size() == 0) {
				String dcSql = null;
				
				dto = getServiceDTO("STATICDATABEAN" ,"getSql") ;
				dto.setValueByName("parameter", attrCode) ;
				dto = ActionDispatch.dispatch(dto);
				dcSql =  ((String)dto.getValueByName("result"));
//				
//				StaticDataBean staticData = StaticDataBean.getInstance();//(StaticData) EJBHelper.creatEJB("ejb/StaticData", StaticDataHome.class);
//
//				
//
//				//try {
//					
//					dcSql = staticData.getSql(attrCode);
					if (dcSql == null) {
						// �Ҳ�����ؾ�̬���ݵ�SQL����
						throw new CommonException(new CommonError(CommonError.DATA_NOFOUND_ERROR), new Exception(
								"�Ҳ�����ؾ�̬���ݵĶ���, ��̬���ݶ�������" + attrCode));
						
					}
					if("DC_PRODUCT_PROP".equals(attrCode))
						dcSql = attrCode;
					
					dto = getServiceDTO("STATICDATABEAN" ,"getStaticData") ;
					dto.setValueByName("parameter", dcSql) ;
					dto = ActionDispatch.dispatch(dto);
					dataList = ((ArrayList)dto.getValueByName("result"));
					
//					dataList = staticData.getStaticData(dcSql);
					if (dataList == null || dataList.size() == 0) {
						// �Ҳ�����̬����
						throw new CommonException(new CommonError(CommonError.DATA_NOFOUND_ERROR), new Exception(
								"�Ҳ�����̬����, ��̬���ݶ�������" + attrCode));
						
					}
					
					//put into cache
					StaticAttrCache.getInstance().setAttrData(attrCode, dataList);
					

				/*} catch (RemoteException e) {
					// ��ȡ��̬���ݳ���
					throw new CommonException(new CommonError(CommonError.DATA_NOFOUND_ERROR), e);
				}*/
			}
		}
		return dataList;
	}
	/**
	 * ��������ѯ���ݡ�
	 * @param attrCode
	 * @param param1
	 * @return
	 * @throws Exception
	 */
	public ArrayList getFilteredStaticAttr(String attrCode, String param1) throws Exception {

		if(param1==null || "".equals(param1)) return getStaticAttr(attrCode);

		ArrayList dataList = new ArrayList();

		ArrayList tempDataList = new ArrayList();
		tempDataList = getStaticAttr(attrCode);
		if (null == tempDataList || tempDataList.size() == 0) {
			return dataList;
		} 

		// ���Ҹ����			
		for (int i = 0; i < tempDataList.size(); i++) {
			StaticAttrVO dataVO = (StaticAttrVO) tempDataList.get(i);
			// ������ϸ���㣬�����ӵ��������
			if (param1 != null && param1.equals(dataVO.getParam1()))
				dataList.add(dataVO);
		}		

		return dataList;

	}
		

	/**
	 * ȡ�ö�����̬�����У�����parentValue�����ݡ�
	 * 
	 * @param dataName
	 *            ��̬��������
	 * @param parentValue
	 *            ����������ֵ����
	 */
	public ArrayList getSubStaticAttr(String attrCode, String parentValue) throws Exception {

		ArrayList dataList = new ArrayList();
		
		if(parentValue==null || "".equals(parentValue) || "null".equals(parentValue)) return dataList;
		
		
		/*boolean isException = false;
		try{
			if(this.getSession()==null){
				this.setSession(this.getRequest().getSession());
			}
		}catch(Exception e){
			isException= true;
		}*/
		
		ArrayList tempDataList = new ArrayList();
		tempDataList = getStaticAttr(attrCode);
		if (null == tempDataList || tempDataList.size() == 0) {
			return dataList;
		} else {

			// ���Ҹ����			
			for (int i = 0; i < tempDataList.size(); i++) {
				StaticAttrVO dataVO = (StaticAttrVO) tempDataList.get(i);
				// ������ϸ���㣬�����ӵ��������
				if (parentValue != null && parentValue.equals(dataVO.getParentValueId()))
					dataList.add(dataVO);
			}
		}
		return dataList;

	}
	
	/**
	 * ͨ�����Ƶ����Բ���dc_sql���ݣ�
	 * @param dcName
	 * @param params
	 * @return
	 */
	public ArrayList loadDataByConfigParams(String dcName, String params)
	{
		/*
		SqlDataSourceLoader sqlDataSourceLoader = new SqlDataSourceLoader();
		List sqlValueList = new ArrayList();
		if(dcName.startsWith("DC_")){
			sqlValueList = sqlDataSourceLoader.load(dcName,params);
		}else if(dcName.startsWith("DE_")){
			sqlValueList = sqlDataSourceLoader.load(dcName,params);
		}else{
			sqlValueList = ProdAttrLoader.load(dcName,params);
		}
	
		ArrayList dataList = new ArrayList();
		SqlParam sqlVO = null;
		StaticAttrVO attrVO = null;
		for (Iterator iter = sqlValueList.iterator(); iter.hasNext();) {
			sqlVO = (SqlParam) iter.next();
			attrVO = new StaticAttrVO();
			attrVO.setAttrValue(sqlVO.getValue());
			attrVO.setAttrValueDesc(sqlVO.getName());
			attrVO.setAttrValueId(sqlVO.getValueId());
			dataList.add(attrVO);
		}
				
		return dataList;*/
		return null;
	}

	/**
	 * ΪDropdownTag�ṩjs�Ķ�����롣
	 * @param attrCode
	 * @return
	 * @throws Exception
	 */
	public String getStaticAttrJsArray(String attrCode) throws Exception {
		ArrayList attrList = getStaticAttr(attrCode);
		StringBuffer sbf = new StringBuffer();

		for (Iterator iter = attrList.iterator(); iter.hasNext();) {
			StaticAttrVO item = (StaticAttrVO) iter.next();
			sbf.append("items[items.length]={label:" + item.getAttrValueDesc() + ",value:" + item.getAttrValue()
					+ ",valueId:" + item.getAttrValueId() + "}");
		}

		return sbf.toString();
	}

	
	/**
	 * ˢ��attrCode����Ӧ��̬���ݡ�
	 * @param attrCode
	 */
	public void clearAttrDataCache(String attrCode) {

		StaticAttrCache.getInstance().clearAttrDataCache(attrCode);

	}
	
	/**
	 * ˢ��attrCode����Ӧ��̬���ݡ�
	 * @param attrCode
	 */
	public void refreshStaticAttrCache(String attrCode) {

		StaticAttrCache.getInstance().clearAttrDataCache(attrCode);

	}
	
	/**
	 * ˢ�����еľ�̬���ݡ�
	 *
	 */
	public void refreshStaticAttrCache() {
		
		//		��̬��������ˢ������
		try {
			StaticAttrCache.getInstance().initStaticAttr();
		} catch (Exception e) {
			logger.error("��̬��������ˢ�����벻�ɹ�:" + e.getMessage());
			e.printStackTrace();
		}
		
		logger.debug("��̬��������ˢ������ɹ���");

	}
	
	public void clearControlCache() {
		//ControlLoadEng.clearBufferData();
	}
	
	public void clearStaticCache(){
		//StaticLoadEng.clearBufferData();
	}
	/**
	 * ���ò�����������
	 *
	 */
	public void refreshParam(){
		//���ò�������
		CrmParamsConfig.getInstance().initParams(CrmConstants.WEB_INF_PATH);
		logger.debug("���ò�����������ɹ���");
	}
	
	public String getDcSystemParam( String paramCode ) throws Exception {
		DynamicDict dto = getServiceDTO("DcSysParam" ,"getSystemParam") ;
		dto.setValueByName("parameter", paramCode) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
	}
	
	public void setSession(HttpSession session) {
		if (session != null)
			this.session = session;
	}
	/**
	 * @return the session
	 */
	public HttpSession getSession() {
		return session;
	}

	public PageModel listDataOfDcSql(String dcName  , int pi , int ps ) throws Exception {
		Map param = new HashMap() ;
		param.put("dcName" , dcName ) ;
		param.put("pageSize" , new Integer(ps) ) ;
		param.put("pageIndex" , new Integer(pi) ) ;
		return DataTranslate._PageModel(ServiceManager.callJavaBeanService("STATICATTRBEAN" ,"listDataOfDcSql" , param )) ;
	}
	
	/**
	 * 
	 * @param dcNames
	 * @return
	 * @throws Exception
	 */
	public boolean clearStaticData(String dcNames) throws Exception{
		
		if(dcNames == null || dcNames.equalsIgnoreCase("")) {
			return false;
		}
		
		String[] aDcName = dcNames.split(",");
		
		for(int i = 0 ; i < aDcName.length ; i++) {
			clearAttrDataCache(aDcName[i]);
		}
					
		return true;
	}
	
	/*
	 * ��crm-param.properties ��ȡ����ǰ�����ڴ��еĲ�����ֵ
	 */
	public String getCurrentParamValue(String paramCode) {
		String retStr = CrmParamsConfig.getInstance().getParamValue(paramCode);
		return retStr;
	}
	
	/*
	 * ����crm-param.properties ��ȡ����ǰ�����ڴ��еĲ�����ֵ
	 */
	public String updateCurrentParamValue(String paramCode) throws Exception {
		CrmParamsConfig.getInstance().updateProperty(paramCode);
		return this.getCurrentParamValue(paramCode);
	}
	
	
}
