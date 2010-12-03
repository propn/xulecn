package com.ztesoft.vsop.webservice.skeleton;

import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq;
import com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPResp;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 * <pre>
 * Title:集团规范 X平台同步订购关系到VSOP
 * Description: 订购关系同步(X平台-VSOP)
 * </pre>
 * 
 * @author xulei xu.lei55@zte.com.cn
 * @version R7.8
 * 
 * <pre>
 * 修改记录
 * 修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class SubsInstSynToVSOPSVSkeleton {

	/**
	 * 
	 * @param subsInstSynToVSOPReq
	 * @return
	 */
	public SubsInstSynToVSOPResp SubsInstSynToVSOPSV(
			SubsInstSynToVSOPReq subsInstSynToVSOPReq) {
		// 1获取请求内容
		String requestXml = subsInstSynToVSOPReq.getSubsInstSynToVSOPReq()
				.getRequest();
		// 2具体业务处理
		String responseXml = null;
		VariedServerEngine engine = new VariedServerEngine();
		responseXml = engine.SubsInstSynToVSOPSV(requestXml);
		// 3返回结果
		SubsInstSynToVSOPResp subsInstSynToVSOPResp = new SubsInstSynToVSOPResp();
		VsopServiceResponse param = new VsopServiceResponse();
		param.setResponse(responseXml);
		subsInstSynToVSOPResp.setSubsInstSynToVSOPResp(param);
		return subsInstSynToVSOPResp;
	}

}
