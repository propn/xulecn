package com.ztesoft.vsop.engine.service;

import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.IAction;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.dao.InterfaceLogDao;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.InterfaceLogVO;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class InterfaceLogService implements IAction {
	public void insertLog(Map in){
		//如果dc_system_param配置了CLOSE_INTERFACE_LOG为TRUE，则不写日志。默认写日志
		String closeLog = DcSystemParamManager.getParameter(VsopConstants.CLOSE_INTERFACE_LOG);
		if(closeLog != null && "true".equalsIgnoreCase(closeLog)) return;
		
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		String serviceCode=(String)in.get("serviceCode");
		String startTime=(String)in.get("startTime"); 
		String endTime=(String)in.get("endTime"); 
		String processTime=(String)in.get("processTime"); 
		String accessCode=(String)in.get("accessCode"); 
		String resultCode=(String)in.get("resultCode"); 
		String resultMsg=(String)in.get("resultMsg"); 
		InterfaceLogVO log = new InterfaceLogVO();
		log.setAccNbr(order.getAccNbr());
		log.setEndTime(endTime);
		log.setStartTime(startTime);
		log.setLanCode(order.getLanId());
		log.setProcessTime(processTime);
		log.setSrcSysId(order.getOrderSystem());
		log.setInterfaceId(accessCode);
		log.setNbrType(order.getProdId());
		log.setTransactionId(order.getCustSoNumber());
		log.setResult(resultCode);
		log.setResultDesc(resultMsg);
		log.setServId(serviceCode);
		InterfaceLogDao logDao = new InterfaceLogDao();
		logDao.insert(log);
	}
	public int perform(DynamicDict dto) throws Exception {
		String methodName = (String) dto.getValueByName(Const.ACTION_METHOD);
		Map in =(Map)dto.getValueByName(Const.ACTION_PARAMETER) ;
		if("insertLog".equals(methodName)){
			this.insertLog(in);
			dto.setValueByName(Const.ACTION_RESULT, "0");
		}
		return 0;
	}
}
