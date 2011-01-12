package com.ztesoft.common.dict;

import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;

/**
 * 
 * @author easonwu 2009-01-05 
 * ��������࣬����Dict�ȷ���getter/setter�ȣ��򻯳������
 *
 */
public class ServiceManager {
	
	public static final int SERVICE_SQL = 1 ;
	public static final int SERVICE_JAVABEAN = 0 ;
	
//	����Action (����java beanʹ��)
	public static  DynamicDict getServiceDTO(String actionName , String methodName ) {
		DynamicDict dto = new DynamicDict(1);// 1��JSP���ʣ�0��BHO����
		dto.flag = SERVICE_JAVABEAN;// 1:Action;0:Service
		Logger.getLogger(ActionDispatch.class).debug("ִ�з���:" + methodName);
		dto.m_ActionId = actionName ;
		try {
			dto.setValueByName("execMethod", methodName) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto ;
	}
	
	//����Action (����SQLʹ��)
	public static  DynamicDict getActionDTO(String actionName ) {
		DynamicDict dto = new DynamicDict(1);// 1��JSP���ʣ�0��BHO����
		dto.flag = SERVICE_SQL;// 1:Action;0:Service
		dto.m_ActionId = actionName ;
		return dto ;
	}
	
	private ServiceManager(){
		
	}
	/**
	 * �򻯵��÷�ʽ��JavaBeanʽ
	 * @param service
	 * @param method
	 * @param param
	 * @return
	 * @throws Exception
	 * @author easonwu 2010-01-05
	 */
	public static Object callJavaBeanService(String service, String method , Map param ) throws Exception{
		DynamicDict dto = getServiceDTO(service ,method);
		dto.setValueByName(Const.ACTION_PARAMETER, param);
		ActionDispatch.dispatch(dto);
		return dto.getValueByName(Const.ACTION_RESULT);
	}
	
	/**
	 * �򻯵��÷�ʽ��SQLʽ
	 * @param service
	 * @param method
	 * @param param
	 * @return
	 * @throws Exception
	 * @author easonwu 2010-01-05
	 */
	public static Object callSQLService(String service , Map param ) throws Exception{
		DynamicDict dto = getActionDTO(service) ;
		dto.setValueByName(Const.ACTION_PARAMETER, param) ;
		ActionDispatch.dispatch(dto);
		return dto.m_Values.get(service);
	}

	/**
	 * ����SQL��ʽ������ֵΪDTO���ɻ�ȡDTO��������Ϣ���������ʾ��Ϣ��
	 * @param service
	 * @param param
	 * @return
	 * @throws Exception
	 * @author easonwu 2010-01-11
	 */
	public static DynamicDict callSQLServiceReturnWithDto(String service , Map param ) throws Exception{
		DynamicDict dto = getActionDTO(service) ;
		dto.setValueByName(Const.ACTION_PARAMETER, param) ;
		ActionDispatch.dispatch(dto);
		return dto;
	}
	
	/**
	 * javabean��ʽ���ã�����ֵΪDTO���ɻ�ȡDTO��������Ϣ���������ʾ��Ϣ��
	 * @param service
	 * @param method
	 * @param param
	 * @return
	 * @throws Exception
	 * @author easonwu 2010-01-11
	 * 
	 */
	public static DynamicDict callJavaBeanServiceReturnWithDto(String service, String method , Map param ) throws Exception{
		DynamicDict dto = getServiceDTO(service ,method) ;
		dto.setValueByName(Const.ACTION_PARAMETER, param) ;
		ActionDispatch.dispatch(dto);
		return dto ;
	}

}
