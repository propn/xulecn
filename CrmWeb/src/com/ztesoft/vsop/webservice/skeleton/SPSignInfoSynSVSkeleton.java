/**
 * SPSignInfoSynSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.webservice.vo.SPSignInfoSyncToVSOPResp;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 * 
 * <pre>
 * Title:SPCPҵ������ͬ���ӿ�
 * Description: SPCPҵ������ͬ���ӿ� 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class SPSignInfoSynSVSkeleton {

	private static Logger logger = Logger.getLogger(SPSignInfoSynSVSkeleton.class);
	/**
	 * Auto generated method signature
	 * 
	 * @param sPSignInfoSyncToVSOPReq
	 */
	
	public com.ztesoft.vsop.webservice.vo.SPSignInfoSyncToVSOPResp SPSignInfoSync(
			com.ztesoft.vsop.webservice.vo.SPSignInfoSyncToVSOPReq sPSignInfoSyncToVSOPReq) {
		// TODO : fill this with the necessary business logic
		logger.info("SPSignInfoSynSVSkeleton.subsInstSynToVSOP start");
		//��ȡ�ͻ���������
		String requestXml = sPSignInfoSyncToVSOPReq.getSPSignInfoSyncToVSOPReq().getRequest();
		logger.info("SPSignInfoSynSVSkeleton.subsInstSynToVSOP requestXml:"+requestXml);
		VariedServerEngine engine = new VariedServerEngine();
		String responseXml=null;
		responseXml = engine.SPSignInfoSyn(requestXml);
		VsopServiceResponse param=new VsopServiceResponse();
		param.setResponse(responseXml);
		SPSignInfoSyncToVSOPResp response = new SPSignInfoSyncToVSOPResp();
		response.setSPSignInfoSyncToVSOPResp(param);
		logger.info("SPSignInfoSynSVSkeleton.subsInstSynToVSOP end");
		return response;
		
	}

}
    