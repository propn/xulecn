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
 * 类说明: StaticDataService静态数据服务类，为客户端提供调用获取静态数据的方法。
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
	 * 取得单层的静态属性数据。
	 * 
	 * @param attrCode
	 *            静态属性名称
	 */
	public ArrayList getStaticAttr(String attrCode) throws Exception {

		//long time1 = System.currentTimeMillis();
		
		//cache
		ArrayList dataList = StaticAttrCache.getInstance().getAttrData(attrCode);
		if (dataList == null || dataList.size() == 0) {

			//long time3 = System.currentTimeMillis();

			//从 StaticAttr 查找数据
			//try {
			DynamicDict dto = getServiceDTO("STATICATTRBEAN" ,"getStaticAttr") ;
			dto.setValueByName("parameter", attrCode) ;
			dto = ActionDispatch.dispatch(dto);
			dataList =  ((ArrayList)dto.getValueByName("result"));
				//put into cache
				StaticAttrCache.getInstance().setAttrData(attrCode, dataList);

			/*} catch (RemoteException e) {
				// 获取静态数据出错
				throw new CommonException(new CommonError(CommonError.DATA_NOFOUND_ERROR), e);

			}*/

			//从 StaticData 查找数据
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
						// 找不到相关静态数据的SQL定义
						throw new CommonException(new CommonError(CommonError.DATA_NOFOUND_ERROR), new Exception(
								"找不到相关静态数据的定义, 静态数据定义名：" + attrCode));
						
					}
					if("DC_PRODUCT_PROP".equals(attrCode))
						dcSql = attrCode;
					
					dto = getServiceDTO("STATICDATABEAN" ,"getStaticData") ;
					dto.setValueByName("parameter", dcSql) ;
					dto = ActionDispatch.dispatch(dto);
					dataList = ((ArrayList)dto.getValueByName("result"));
					
//					dataList = staticData.getStaticData(dcSql);
					if (dataList == null || dataList.size() == 0) {
						// 找不到静态数据
						throw new CommonException(new CommonError(CommonError.DATA_NOFOUND_ERROR), new Exception(
								"找不到静态数据, 静态数据定义名：" + attrCode));
						
					}
					
					//put into cache
					StaticAttrCache.getInstance().setAttrData(attrCode, dataList);
					

				/*} catch (RemoteException e) {
					// 获取静态数据出错
					throw new CommonException(new CommonError(CommonError.DATA_NOFOUND_ERROR), e);
				}*/
			}
		}
		return dataList;
	}
	/**
	 * 有条件查询数据。
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

		// 查找父结点			
		for (int i = 0; i < tempDataList.size(); i++) {
			StaticAttrVO dataVO = (StaticAttrVO) tempDataList.get(i);
			// 如果符合父结点，则增加到结果集中
			if (param1 != null && param1.equals(dataVO.getParam1()))
				dataList.add(dataVO);
		}		

		return dataList;

	}
		

	/**
	 * 取得二级静态属性中，符合parentValue的数据。
	 * 
	 * @param dataName
	 *            静态数据名称
	 * @param parentValue
	 *            父结点的属性值代码
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

			// 查找父结点			
			for (int i = 0; i < tempDataList.size(); i++) {
				StaticAttrVO dataVO = (StaticAttrVO) tempDataList.get(i);
				// 如果符合父结点，则增加到结果集中
				if (parentValue != null && parentValue.equals(dataVO.getParentValueId()))
					dataList.add(dataVO);
			}
		}
		return dataList;

	}
	
	/**
	 * 通过定制的属性查找dc_sql数据，
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
	 * 为DropdownTag提供js的对象代码。
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
	 * 刷新attrCode所对应静态数据。
	 * @param attrCode
	 */
	public void clearAttrDataCache(String attrCode) {

		StaticAttrCache.getInstance().clearAttrDataCache(attrCode);

	}
	
	/**
	 * 刷新attrCode所对应静态数据。
	 * @param attrCode
	 */
	public void refreshStaticAttrCache(String attrCode) {

		StaticAttrCache.getInstance().clearAttrDataCache(attrCode);

	}
	
	/**
	 * 刷新所有的静态数据。
	 *
	 */
	public void refreshStaticAttrCache() {
		
		//		静态数据重新刷新载入
		try {
			StaticAttrCache.getInstance().initStaticAttr();
		} catch (Exception e) {
			logger.error("静态数据重新刷新载入不成功:" + e.getMessage());
			e.printStackTrace();
		}
		
		logger.debug("静态数据重新刷新载入成功。");

	}
	
	public void clearControlCache() {
		//ControlLoadEng.clearBufferData();
	}
	
	public void clearStaticCache(){
		//StaticLoadEng.clearBufferData();
	}
	/**
	 * 配置参数重新载入
	 *
	 */
	public void refreshParam(){
		//配置参数载入
		CrmParamsConfig.getInstance().initParams(CrmConstants.WEB_INF_PATH);
		logger.debug("配置参数重新载入成功。");
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
	 * 从crm-param.properties 中取到当前放在内存中的参数的值
	 */
	public String getCurrentParamValue(String paramCode) {
		String retStr = CrmParamsConfig.getInstance().getParamValue(paramCode);
		return retStr;
	}
	
	/*
	 * 更新crm-param.properties 中取到当前放在内存中的参数的值
	 */
	public String updateCurrentParamValue(String paramCode) throws Exception {
		CrmParamsConfig.getInstance().updateProperty(paramCode);
		return this.getCurrentParamValue(paramCode);
	}
	
	
}
