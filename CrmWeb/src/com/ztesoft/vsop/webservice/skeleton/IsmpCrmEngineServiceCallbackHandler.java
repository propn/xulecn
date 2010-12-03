/**
 * IsmpCrmEngineServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;


/**
 *  IsmpCrmEngineServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class IsmpCrmEngineServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public IsmpCrmEngineServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public IsmpCrmEngineServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for workOrderNotify method
     * override this method for handling normal response from workOrderNotify operation
     */
    public void receiveResultworkOrderNotify(
        com.ztesoft.vsop.webservice.vo.jx.Response20 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from workOrderNotify operation
     */
    public void receiveErrorworkOrderNotify(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for workOrderNotifyCnfm method
     * override this method for handling normal response from workOrderNotifyCnfm operation
     */
    public void receiveResultworkOrderNotifyCnfm(
        com.ztesoft.vsop.webservice.vo.jx.Response20 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from workOrderNotifyCnfm operation
     */
    public void receiveErrorworkOrderNotifyCnfm(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for createISMPUser method
     * override this method for handling normal response from createISMPUser operation
     */
    public void receiveResultcreateISMPUser(
        com.ztesoft.vsop.webservice.vo.jx.Response20 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from createISMPUser operation
     */
    public void receiveErrorcreateISMPUser(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for notifyUserState method
     * override this method for handling normal response from notifyUserState operation
     */
    public void receiveResultnotifyUserState(
        com.ztesoft.vsop.webservice.vo.jx.Response20 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from notifyUserState operation
     */
    public void receiveErrornotifyUserState(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for notifyUserFeeType method
     * override this method for handling normal response from notifyUserFeeType operation
     */
    public void receiveResultnotifyUserFeeType(
        com.ztesoft.vsop.webservice.vo.jx.Response20 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from notifyUserFeeType operation
     */
    public void receiveErrornotifyUserFeeType(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for serviceCapabilitySubscriptionSync method
     * override this method for handling normal response from serviceCapabilitySubscriptionSync operation
     */
    public void receiveResultserviceCapabilitySubscriptionSync(
        com.ztesoft.vsop.webservice.vo.jx.Response20 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from serviceCapabilitySubscriptionSync operation
     */
    public void receiveErrorserviceCapabilitySubscriptionSync(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for orderRelationUpdateNotify method
     * override this method for handling normal response from orderRelationUpdateNotify operation
     */
    public void receiveResultorderRelationUpdateNotify(
        com.ztesoft.vsop.webservice.vo.jx.Response20 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from orderRelationUpdateNotify operation
     */
    public void receiveErrororderRelationUpdateNotify(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for createSubscription method
     * override this method for handling normal response from createSubscription operation
     */
    public void receiveResultcreateSubscription(
        com.ztesoft.vsop.webservice.vo.jx.CreateSubscriptionRsp17 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from createSubscription operation
     */
    public void receiveErrorcreateSubscription(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for modifySubscription method
     * override this method for handling normal response from modifySubscription operation
     */
    public void receiveResultmodifySubscription(
        com.ztesoft.vsop.webservice.vo.jx.ModifySubscriptionRsp19 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from modifySubscription operation
     */
    public void receiveErrormodifySubscription(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for withdrawSubscription method
     * override this method for handling normal response from withdrawSubscription operation
     */
    public void receiveResultwithdrawSubscription(
        com.ztesoft.vsop.webservice.vo.jx.WithdrawSubscriptionRsp16 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from withdrawSubscription operation
     */
    public void receiveErrorwithdrawSubscription(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getSubscription method
     * override this method for handling normal response from getSubscription operation
     */
    public void receiveResultgetSubscription(
        com.ztesoft.vsop.webservice.vo.jx.GetSubscriptionRsp21 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getSubscription operation
     */
    public void receiveErrorgetSubscription(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for withdrawAllSubscription method
     * override this method for handling normal response from withdrawAllSubscription operation
     */
    public void receiveResultwithdrawAllSubscription(
        com.ztesoft.vsop.webservice.vo.jx.Response20 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from withdrawAllSubscription operation
     */
    public void receiveErrorwithdrawAllSubscription(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for querySubscriptionHistory method
     * override this method for handling normal response from querySubscriptionHistory operation
     */
    public void receiveResultquerySubscriptionHistory(
        com.ztesoft.vsop.webservice.vo.jx.QuerySubscriptionHistoryRsp18 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from querySubscriptionHistory operation
     */
    public void receiveErrorquerySubscriptionHistory(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for suspendSubscription method
     * override this method for handling normal response from suspendSubscription operation
     */
    public void receiveResultsuspendSubscription(
        com.ztesoft.vsop.webservice.vo.jx.Response20 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from suspendSubscription operation
     */
    public void receiveErrorsuspendSubscription(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for resumeSubscription method
     * override this method for handling normal response from resumeSubscription operation
     */
    public void receiveResultresumeSubscription(
        com.ztesoft.vsop.webservice.vo.jx.Response20 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from resumeSubscription operation
     */
    public void receiveErrorresumeSubscription(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for subscriptionSync method
     * override this method for handling normal response from subscriptionSync operation
     */
    public void receiveResultsubscriptionSync(
        com.ztesoft.vsop.webservice.vo.jx.Response20 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from subscriptionSync operation
     */
    public void receiveErrorsubscriptionSync(java.lang.Exception e) {
    }
}
