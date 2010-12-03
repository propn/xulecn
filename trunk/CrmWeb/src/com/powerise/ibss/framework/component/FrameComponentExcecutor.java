package com.powerise.ibss.framework.component;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.GlobalNames;
import com.powerise.ibss.util.SysSet;
import com.ztesoft.common.dao.ConnectionContextUtil;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;

public class FrameComponentExcecutor {
	//service
	private IComponet service = ServiceComponent.getInstance() ;
	//log
	private IComponet log = LogComponent.getInstance() ;
	//transaction 
	private IComponet transaction = TransactionComponent.getInstance() ;
	
	private FrameComponentExcecutor() {
		init() ;
    	
	}
	
	public static FrameComponentExcecutor getInstance(){
		return SingletonHolder.instance ;
	}
	
	static class SingletonHolder{
		static FrameComponentExcecutor instance = new FrameComponentExcecutor() ;
	}
	
	private void init(){
		if(!GlobalNames.CONFIG_LOADED){
    		// 配置参数载入
			CrmParamsConfig.getInstance().initParams(CrmConstants.WEB_INF_PATH);
			try {
				SysSet.initSystem(3);
			} catch (FrameException e) {
				e.printStackTrace();
			}
    	}
	}
	
	/**
	 * 执行服务
	 * @param dto
	 * @throws Exception
	 */
	public void execute(DynamicDict dto)  throws Exception {
		ConnectionContextUtil.createConnectionContext() ;//创建数据库连接上下文 add by easonwu 2009-12-22
		transaction.execute(service, dto) ;
	}

}
