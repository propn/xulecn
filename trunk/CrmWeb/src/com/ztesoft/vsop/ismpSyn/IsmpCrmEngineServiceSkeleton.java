/**
 * IsmpCrmEngineServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.ismpSyn;


/**
 *  IsmpCrmEngineServiceSkeleton java skeleton for the axisService
 */
public class IsmpCrmEngineServiceSkeleton {
    /**
     * ����  liuyuming 20100804
     * Auto generated method signature
     * @param subscriptionSync
     */
    /**
	public com.ztesoft.vsop.ismpSyn.vo.SubscriptionSyncResponse subscriptionSync(
        com.ztesoft.vsop.ismpSyn.vo.SubscriptionSync subscriptionSync) {
        //TODO : fill this with the necessary business logic
    	//����  liuyuming 20100804
    	//����ismp��vsopͬ��������ϵ���������ػ��ӿڣ�ismp�������Ź淶������ismpԭ����crmͬ��������ϵ�ӿ�Э��
    	SubscriptionSyncReq req = subscriptionSync.getSubscriptionSyncRequest();
    	System.out.println("-----request:"+req.getOPType());
    	//2����ҵ����
    	SubscriptionSyncResponse synResp = new SubscriptionSyncResponse();
		com.ztesoft.vsop.ismpSyn.vo.Response resp = new com.ztesoft.vsop.ismpSyn.vo.Response();
		try {
			resp = SubscribeServiceBo.getInstance().subsInstSynIsmpToVsopGX(req);
			
		} catch (DocumentException e) {
			String streamNo=req.getStreamingNo();
			resp.setResultCode(-99);
			resp.setStreamingNo(streamNo);
//			responseXml=StringUtil.getInstance().getVsopResponse("SubsInstSynFromISMPResp", streamNo, 
//					VsopConstants.VSOP_SYSTEM_ERROR_RESPONSECODE, e.toString(), 
//					"");
			e.printStackTrace();
		} catch (SQLException e) {
			String streamNo=req.getStreamingNo();
			resp.setResultCode(-99);
			resp.setStreamingNo(streamNo);
//			responseXml=StringUtil.getInstance().getVsopResponse("SubsInstSynFromISMPResp", streamNo, 
//					VsopConstants.VSOP_SYSTEM_ERROR_RESPONSECODE, e.toString(), 
//					"");
			e.printStackTrace();
		}
		synResp.setSubscriptionSyncReturn(resp);
		return synResp;
    }
    */
}
