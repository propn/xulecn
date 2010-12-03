package com.powerise.ibss.framework.component;

import com.powerise.ibss.framedata.bo.FrameServiceController;
import com.powerise.ibss.framedata.vo.TfmServicesVO;
import com.powerise.ibss.framework.DynamicDict;

/**
 * 
 * 服务组件：重构与ActionDispatch
 * 
 * @author easonwu 2010-02-10
 *
 */
public class ServiceComponent implements IComponet {

	//单例模式
	private ServiceComponent(){
		
	}
	
	public static IComponet getInstance(){
		return SingletonHolder.instance ;
	}
	
	static class SingletonHolder{
		static IComponet instance = new ServiceComponent() ;
	}
	
	//执行服务逻辑
	public DynamicDict execute(IComponet component, DynamicDict dto)
			throws Exception {
		//获取服务信息VO
		TfmServicesVO service = ComponentUtil.getTfmServicesVO(dto) ;
        
		 //执行服务
		FrameServiceController controller = FrameServiceController.getInstance() ;        
        controller.callService(service, dto) ;
         
        return dto ;
	}

}
