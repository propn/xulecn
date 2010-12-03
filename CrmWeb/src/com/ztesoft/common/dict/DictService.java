package com.ztesoft.common.dict;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.buffalo.BaseService;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;

public class DictService extends BaseService{
	//	����Action (����java beanʹ��)
	public DynamicDict getServiceDTO(String actionName , String methodName ) {
		DynamicDict dto = new DynamicDict(1);// 1��JSP���ʣ�0��BHO����
		dto.flag = 0;// 1:Action;0:Service
		
		dto.m_ActionId = actionName ;
		try {
			dto.setValueByName("execMethod", methodName) ;
		} catch (FrameException e) {
			e.printStackTrace();
		}
		return dto ;
	}
	
	//����Action (����SQLʹ��)
	public  DynamicDict getActionDTO(String actionName ) {
		DynamicDict dto = new DynamicDict(1);// 1��JSP���ʣ�0��BHO����
		dto.flag = 1;// 1:Action;0:Service
		dto.m_ActionId = actionName ;
		return dto ;
	}
	/**
     * ��ȡȫ�ֱ���(��½��Ϣ)
     *
     * @param strGlobalName
     * @return
     * @throws Exception
     */
    public String getGlobalVar(String strGlobalName) throws Exception {
        //GlobalVariableHelper helper = new GlobalVariableHelper(getRequest());
    	GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() );
        String strGlobal = helper.getVariable(strGlobalName);
        return strGlobal==null?"":strGlobal;
    }
}
