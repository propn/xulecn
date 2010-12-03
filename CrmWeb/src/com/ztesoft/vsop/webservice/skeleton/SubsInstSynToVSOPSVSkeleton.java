package com.ztesoft.vsop.webservice.skeleton;

import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq;
import com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPResp;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 * <pre>
 * Title:���Ź淶 Xƽ̨ͬ��������ϵ��VSOP
 * Description: ������ϵͬ��(Xƽ̨-VSOP)
 * </pre>
 * 
 * @author xulei xu.lei55@zte.com.cn
 * @version R7.8
 * 
 * <pre>
 * �޸ļ�¼
 * �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����:
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
		// 1��ȡ��������
		String requestXml = subsInstSynToVSOPReq.getSubsInstSynToVSOPReq()
				.getRequest();
		// 2����ҵ����
		String responseXml = null;
		VariedServerEngine engine = new VariedServerEngine();
		responseXml = engine.SubsInstSynToVSOPSV(requestXml);
		// 3���ؽ��
		SubsInstSynToVSOPResp subsInstSynToVSOPResp = new SubsInstSynToVSOPResp();
		VsopServiceResponse param = new VsopServiceResponse();
		param.setResponse(responseXml);
		subsInstSynToVSOPResp.setSubsInstSynToVSOPResp(param);
		return subsInstSynToVSOPResp;
	}

}
