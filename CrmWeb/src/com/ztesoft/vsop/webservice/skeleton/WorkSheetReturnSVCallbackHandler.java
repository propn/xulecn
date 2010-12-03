/**
 * WorkSheetReturnSVCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;


/**
 *  WorkSheetReturnSVCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class WorkSheetReturnSVCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public WorkSheetReturnSVCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public WorkSheetReturnSVCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
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
}
