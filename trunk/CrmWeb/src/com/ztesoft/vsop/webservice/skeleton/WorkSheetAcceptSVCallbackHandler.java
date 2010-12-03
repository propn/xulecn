/**
 * WorkSheetAcceptSVCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;


/**
 *  WorkSheetAcceptSVCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class WorkSheetAcceptSVCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public WorkSheetAcceptSVCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public WorkSheetAcceptSVCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for workListFKToVSOP method
     * override this method for handling normal response from workListFKToVSOP operation
     */
    public void receiveResultworkListFKToVSOP(
        com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from workListFKToVSOP operation
     */
    public void receiveErrorworkListFKToVSOP(java.lang.Exception e) {
    }
}
