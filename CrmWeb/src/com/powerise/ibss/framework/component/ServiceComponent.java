package com.powerise.ibss.framework.component;

import com.powerise.ibss.framedata.bo.FrameServiceController;
import com.powerise.ibss.framedata.vo.TfmServicesVO;
import com.powerise.ibss.framework.DynamicDict;

/**
 * 
 * ����������ع���ActionDispatch
 * 
 * @author easonwu 2010-02-10
 *
 */
public class ServiceComponent implements IComponet {

	//����ģʽ
	private ServiceComponent(){
		
	}
	
	public static IComponet getInstance(){
		return SingletonHolder.instance ;
	}
	
	static class SingletonHolder{
		static IComponet instance = new ServiceComponent() ;
	}
	
	//ִ�з����߼�
	public DynamicDict execute(IComponet component, DynamicDict dto)
			throws Exception {
		//��ȡ������ϢVO
		TfmServicesVO service = ComponentUtil.getTfmServicesVO(dto) ;
        
		 //ִ�з���
		FrameServiceController controller = FrameServiceController.getInstance() ;        
        controller.callService(service, dto) ;
         
        return dto ;
	}

}
