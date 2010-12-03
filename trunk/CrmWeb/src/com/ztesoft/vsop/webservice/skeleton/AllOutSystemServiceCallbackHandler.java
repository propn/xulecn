/**
 * AllOutSystemServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;


/**
 *  AllOutSystemServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class AllOutSystemServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public AllOutSystemServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public AllOutSystemServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for subResultFromVSOP method
     * override this method for handling normal response from subResultFromVSOP operation
     */
    public void receiveResultsubResultFromVSOP(
        com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from subResultFromVSOP operation
     */
    public void receiveErrorsubResultFromVSOP(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sendRQMessage method
     * override this method for handling normal response from sendRQMessage operation
     */
    public void receiveResultsendRQMessage(
        com.ztesoft.vsop.webservice.vo.SendRQMessageReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sendRQMessage operation
     */
    public void receiveErrorsendRQMessage(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for subResulToUser method
     * override this method for handling normal response from subResulToUser operation
     */
    public void receiveResultsubResulToUser(
        com.ztesoft.vsop.webservice.vo.SubResulToUserReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from subResulToUser operation
     */
    public void receiveErrorsubResulToUser(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for workListVSOPToFK method
     * override this method for handling normal response from workListVSOPToFK operation
     */
    public void receiveResultworkListVSOPToFK(
        com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from workListVSOPToFK operation
     */
    public void receiveErrorworkListVSOPToFK(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for feeCheck method
     * override this method for handling normal response from feeCheck operation
     */
    public void receiveResultfeeCheck(
        com.ztesoft.vsop.webservice.vo.FeeCheckResp result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from feeCheck operation
     */
    public void receiveErrorfeeCheck(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for subsInstSynToHB method
     * override this method for handling normal response from subsInstSynToHB operation
     */
    public void receiveResultsubsInstSynToHB(
        com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from subsInstSynToHB operation
     */
    public void receiveErrorsubsInstSynToHB(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for subActionFromVSOP method
     * override this method for handling normal response from subActionFromVSOP operation
     */
    public void receiveResultsubActionFromVSOP(
        com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from subActionFromVSOP operation
     */
    public void receiveErrorsubActionFromVSOP(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for chgActionFromVSOP method
     * override this method for handling normal response from chgActionFromVSOP operation
     */
    public void receiveResultchgActionFromVSOP(
        com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from chgActionFromVSOP operation
     */
    public void receiveErrorchgActionFromVSOP(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for unSubActionFromVSOP method
     * override this method for handling normal response from unSubActionFromVSOP operation
     */
    public void receiveResultunSubActionFromVSOP(
        com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from unSubActionFromVSOP operation
     */
    public void receiveErrorunSubActionFromVSOP(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for staffAuthFromVSOP method
     * override this method for handling normal response from staffAuthFromVSOP operation
     */
    public void receiveResultstaffAuthFromVSOP(
        com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from staffAuthFromVSOP operation
     */
    public void receiveErrorstaffAuthFromVSOP(java.lang.Exception e) {
    }
}
