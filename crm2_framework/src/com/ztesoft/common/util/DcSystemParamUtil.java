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
 * @author �����
 *
 */
public class DcSystemParamUtil extends DictAction{
	
	//���ڻ���ϵͳ����
	public static HashMap sysParamsCache = new HashMap();
 
	
	public String getSystemParam(DynamicDict dto ) throws Exception {
		String paramCode = (String)dto.getValueByName("parameter") ;		
		String sql = "select param_val from dc_system_param where param_code ='"+paramCode+"'";
		return SqlMapExe.getInstance().querySingleValue(sql);
	}

	
	/**
	 * ��ѯϵͳ�������ȴӻ�����ȡ��
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
	 * ����ϵͳ���������ȡϵͳ���� �÷���ֻ����BO������� ���������
	 * @param param_code
	 * @return
	 */
	public  static String getSysParamByCache(String paramCode){
		if (sysParamsCache.get(paramCode) != null) {
			return sysParamsCache.get(paramCode).toString();
		}else{//���û�У������ݿ������ѯ
			String paramVal = getParamValFromDb(paramCode);
			setVariable(paramCode,paramVal);
			return paramVal;
		}
	}

	/**
	 * ���ݲ���code��ѯ���ݿ�
	 * @param paramCode
	 * @return
	 */
	public static String getParamValFromDb(String paramCode) {
		String sql = "select param_val from dc_system_param where param_code ='"+paramCode+"'";
		String paramVal = SqlMapExe.getInstance().querySingleValue(sql);
		return paramVal;
	}
	
/*	*//**
	 * ����ϵͳ���������ȡϵͳ���� �÷���ֻ����service������� �������
	 * @param param_code
	 * @return
	 *//*
	public static String getSysParamByCacheServ(String paramCode){
		if (sysParamsCache.get(paramCode) != null) {
			return sysParamsCache.get(paramCode).toString();
		}else{//���û�У������ݿ������ѯ
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
	 * ���û������ݡ�
	 * @param key
	 * @param value
	 */
	private static void setVariable(String key, String value) {
		sysParamsCache.put(key, value);
    }
	
}
