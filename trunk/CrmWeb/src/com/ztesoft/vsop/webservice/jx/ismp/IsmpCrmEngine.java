/**
 * IsmpCrmEngine.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.webservice.jx.ismp;

public interface IsmpCrmEngine extends java.rmi.Remote {
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response workOrderNotify(com.ztesoft.vsop.webservice.jx.ismp.req.WorkOrderNotifyReq workOrderNotifyReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response workOrderNotifyCnfm(com.ztesoft.vsop.webservice.jx.ismp.req.WorkOrderNotifyCnfmReq workOrderNotifyCnfmReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response createISMPUser(com.ztesoft.vsop.webservice.jx.ismp.req.CreateISMPUserReq createISMPUserReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response notifyUserState(com.ztesoft.vsop.webservice.jx.ismp.req.NotifyUserStateReq notifyUserStateReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response notifyUserFeeType(com.ztesoft.vsop.webservice.jx.ismp.req.NotifyUserFeeTypeReq notifyUserFeeTypeReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response serviceCapabilitySubscriptionSync(com.ztesoft.vsop.webservice.jx.ismp.req.ServiceCapabilitySubscriptionSyncReq serviceCapabilitySubscriptionSyncReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response orderRelationUpdateNotify(com.ztesoft.vsop.webservice.jx.ismp.req.OrderRelationUpdateNotifyReq orderRelationUpdateNotifyReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.CreateSubscriptionRsp createSubscription(com.ztesoft.vsop.webservice.jx.ismp.req.CreateSubscriptionReq createSubscriptionReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.ModifySubscriptionRsp modifySubscription(com.ztesoft.vsop.webservice.jx.ismp.req.ModifySubscriptionReq modifySubscriptionReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.WithdrawSubscriptionRsp withdrawSubscription(com.ztesoft.vsop.webservice.jx.ismp.req.WithdrawSubscriptionReq withdrawSubscriptionReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.GetSubscriptionRsp getSubscription(com.ztesoft.vsop.webservice.jx.ismp.req.GetSubscriptionReq getSubscriptionRsp) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response withdrawAllSubscription(com.ztesoft.vsop.webservice.jx.ismp.req.WithdrawAllSubscriptionReq withdrawAllSubscriptionReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.QuerySubscriptionHistoryRsp querySubscriptionHistory(com.ztesoft.vsop.webservice.jx.ismp.req.QuerySubscriptionHistoryReq querySubscriptionHistoryReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response suspendSubscription(com.ztesoft.vsop.webservice.jx.ismp.req.SuspendSubscriptionReq suspendSubscriptionReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response resumeSubscription(com.ztesoft.vsop.webservice.jx.ismp.req.ResumeSubscriptionReq resumeSubscriptionReq) throws java.rmi.RemoteException;
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response subscriptionSync(com.ztesoft.vsop.webservice.jx.ismp.req.SubscriptionSyncReq subscriptionSyncReq) throws java.rmi.RemoteException;
}
