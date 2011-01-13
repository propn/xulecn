package com.powerise.ibss.framework.component;

import com.powerise.ibss.framedata.bo.FrameServiceController;
import com.powerise.ibss.framedata.vo.TfmServicesVO;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

public class ComponentUtil {
	public static TfmServicesVO getTfmServicesVO(DynamicDict dto) throws FrameException{
		TfmServicesVO service = null ;
		String serviceName =  dto.getServiceName() ;
        if(dto.getValueByName("TfmServicesVO") == null ){
        	service = FrameServiceController.getInstance().getServiceByName(serviceName) ;
        	dto.setValueByName("TfmServicesVO" , service ) ;
        } else{
        	service = (TfmServicesVO)dto.getValueByName("TfmServicesVO") ;
        }
        return service ;
	}

}
