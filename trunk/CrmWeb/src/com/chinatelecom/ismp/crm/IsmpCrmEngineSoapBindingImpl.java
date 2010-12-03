/**
 * IsmpCrmEngineSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.crm;

import com.ztesoft.vsop.engine.VariedServerEngine;

public class IsmpCrmEngineSoapBindingImpl implements com.chinatelecom.ismp.crm.IsmpCrmEngine{
    public com.chinatelecom.ismp.crm.rsp.Response workOrderNotify(com.chinatelecom.ismp.crm.req.WorkOrderNotifyReq workOrderNotifyReq) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.crm.rsp.Response workOrderNotifyCnfm(com.chinatelecom.ismp.crm.req.WorkOrderNotifyCnfmReq workOrderNotifyCnfmReq) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.crm.rsp.Response createISMPUser(com.chinatelecom.ismp.crm.req.CreateISMPUserReq createISMPUserReq) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.crm.rsp.Response notifyUserState(com.chinatelecom.ismp.crm.req.NotifyUserStateReq notifyUserStateReq) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.crm.rsp.Response notifyUserFeeType(com.chinatelecom.ismp.crm.req.NotifyUserFeeTypeReq notifyUserFeeTypeReq) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.crm.rsp.Response serviceCapabilitySubscriptionSync(com.chinatelecom.ismp.crm.req.ServiceCapabilitySubscriptionSyncReq serviceCapabilitySubscriptionSyncReq) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.crm.rsp.Response orderRelationUpdateNotify(com.chinatelecom.ismp.crm.req.OrderRelationUpdateNotifyReq orderRelationUpdateNotifyReq) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.crm.rsp.CreateSubscriptionRsp createSubscription(com.chinatelecom.ismp.crm.req.CreateSubscriptionReq createSubscriptionReq) throws java.rmi.RemoteException {
    	com.chinatelecom.ismp.crm.rsp.CreateSubscriptionRsp resp = new com.chinatelecom.ismp.crm.rsp.CreateSubscriptionRsp();
    	resp.setResultCode(0);
    	resp.setStreamingNo(createSubscriptionReq.getStreamingNo());
        return resp;
    }

    public com.chinatelecom.ismp.crm.rsp.ModifySubscriptionRsp modifySubscription(com.chinatelecom.ismp.crm.req.ModifySubscriptionReq modifySubscriptionReq) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.crm.rsp.WithdrawSubscriptionRsp withdrawSubscription(com.chinatelecom.ismp.crm.req.WithdrawSubscriptionReq withdrawSubscriptionReq) throws java.rmi.RemoteException {
    	com.chinatelecom.ismp.crm.rsp.WithdrawSubscriptionRsp resp = new com.chinatelecom.ismp.crm.rsp.WithdrawSubscriptionRsp();
    	resp.setResultCode(0);
    	resp.setStreamingNo(withdrawSubscriptionReq.getStreamingNo());
        return resp;
    }

    public com.chinatelecom.ismp.crm.rsp.GetSubscriptionRsp getSubscription(com.chinatelecom.ismp.crm.req.GetSubscriptionReq getSubscriptionRsp) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.crm.rsp.Response withdrawAllSubscription(com.chinatelecom.ismp.crm.req.WithdrawAllSubscriptionReq withdrawAllSubscriptionReq) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.crm.rsp.QuerySubscriptionHistoryRsp querySubscriptionHistory(com.chinatelecom.ismp.crm.req.QuerySubscriptionHistoryReq querySubscriptionHistoryReq) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.crm.rsp.Response suspendSubscription(com.chinatelecom.ismp.crm.req.SuspendSubscriptionReq suspendSubscriptionReq) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.crm.rsp.Response resumeSubscription(com.chinatelecom.ismp.crm.req.ResumeSubscriptionReq resumeSubscriptionReq) throws java.rmi.RemoteException {
        return null;
    }

    /**
     * 江西ismp订购关系同步
     */
    public com.chinatelecom.ismp.crm.rsp.Response subscriptionSync(com.chinatelecom.ismp.crm.req.SubscriptionSyncReq subscriptionSyncReq) throws java.rmi.RemoteException {
    	com.chinatelecom.ismp.crm.rsp.Response resp = new com.chinatelecom.ismp.crm.rsp.Response();
    	VariedServerEngine engine = new VariedServerEngine();
    	resp = engine.SubsInstSynFromJXISMP(subscriptionSyncReq);
        return resp;
    }

}
