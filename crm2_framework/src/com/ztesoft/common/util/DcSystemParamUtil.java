/**
 * 
 */
package com.ztesoft.common.util;

import java.util.HashMap;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SqlMapExe;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.dict.ServiceManager;

/**
 * @author 许锐豪
 *
 */
public class DcSystemParamUtil extends DictAction{
	
	//用于缓存系统参数
	public static HashMap sysParamsCache = new HashMap();
 
	
	public String getSystemParam(DynamicDict dto ) throws Exception {
		String paramCode = (String)dto.getValueByName("parameter") ;		
		String sql = "select param_val from dc_system_param where param_code ='"+paramCode+"'";
		return SqlMapExe.getInstance().querySingleValue(sql);
	}

	
	/**
	 * 查询系统参数，先从缓存中取。
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public  String getSystemParamByCache(DynamicDict dto ) throws Exception {
		String paramCode = (String)dto.getValueByName("parameter") ;
		if (sysParamsCache.get(paramCode) != null) {
			return sysParamsCache.get(paramCode).toString();
		}else{
			String paramVal = getSystemParam(dto);
			setVariable(paramCode,paramVal);
			return paramVal;
		}
			
	}
	
	/**
	 * 根据系统参数编码获取系统参数 该方法只能在BO里面调用 不经过框架
	 * @param param_code
	 * @return
	 */
	public  static String getSysParamByCache(String paramCode){
		if (sysParamsCache.get(paramCode) != null) {
			return sysParamsCache.get(paramCode).toString();
		}else{//如果没有，则到数据库里面查询
			String paramVal = getParamValFromDb(paramCode);
			setVariable(paramCode,paramVal);
			return paramVal;
		}
	}

	/**
	 * 根据参数code查询数据库
	 * @param paramCode
	 * @return
	 */
	public static String getParamValFromDb(String paramCode) {
		String sql = "select param_val from dc_system_param where param_code ='"+paramCode+"'";
		String paramVal = SqlMapExe.getInstance().querySingleValue(sql);
		return paramVal;
	}
	
/*	*//**
	 * 根据系统参数编码获取系统参数 该方法只能在service里面调用 经过框架
	 * @param param_code
	 * @return
	 *//*
	public static String getSysParamByCacheServ(String paramCode){
		if (sysParamsCache.get(paramCode) != null) {
			return sysParamsCache.get(paramCode).toString();
		}else{//如果没有，则到数据库里面查询
			HashMap param = new HashMap();
			param.put("paramCode", paramCode);
			String paramVal = 
				 (String) ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.EXECUTEQUERY7 ,param) ;
			
			
			String sql = "select param_val from dc_system_param where param_code ='"+paramCode+"'";
			String paramVal = SqlMapExe.getInstance().querySingleValue(sql);
			setVariable(paramCode,paramVal);
			return paramVal;
		}
	}*/
	
	/**
	 * 设置缓存数据。
	 * @param key
	 * @param value
	 */
	private static void setVariable(String key, String value) {
		sysParamsCache.put(key, value);
    }
	
}
